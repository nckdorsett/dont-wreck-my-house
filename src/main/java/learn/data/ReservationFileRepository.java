package learn.data;

import learn.models.Guest;
import learn.models.Host;
import learn.models.Reservation;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.format.datetime.joda.LocalDateParser;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository {

    private final String HEADER = "id,start_date,end_date,guest_id,total";
    private final String DELIMITER = ",";
    private final String directory;

    public ReservationFileRepository(String directory) {
        this.directory = directory;
    }

    @Override
    public List<Reservation> findByHost(Host host) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(host.getId())))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(DELIMITER,-1);
                if (fields.length == 5) {
                    result.add(deserialize(fields, host));
                }
            }
        } catch (IOException ex) {
            // do nothing
        }
        return result;
    }

    @Override
    public Reservation add(Reservation reservation) throws DataException {
        if (reservation == null) {
            return null;
        }
        List<Reservation> all = findByHost(reservation.getHost());
        int nextId = all.stream().mapToInt(Reservation::getId).max().orElse(0) + 1;
        reservation.setId(nextId);
        all.add(reservation);
        writeAll(all, reservation.getHost());
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation) throws DataException {
        if (reservation == null) {
            return false;
        }

        List<Reservation> all = findByHost(reservation.getHost());
        for (int index = 0; index < all.size(); index++) {
            if (reservation.getId() == all.get(index).getId()) {
                all.set(index, reservation);
                writeAll(all, reservation.getHost());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation) throws DataException {
        if (reservation == null) {
            return false;
        }

        List<Reservation> all = findByHost(reservation.getHost());
        for (int index = 0; index < all.size(); index++) {
            if (reservation.getId() == all.get(index).getId()) {
                all.remove(index);
                writeAll(all, reservation.getHost());
                return true;
            }
        }
        return false;
    }

    private String getFilePath(String id) {
        return Paths.get(directory, id + ".csv").toString();
    }

    private String serialize(Reservation reservation) {
        return String.format("%s%s%s%s%s%s%s%s%s",
                reservation.getId(),
                DELIMITER,
                reservation.getStartDate(),
                DELIMITER,
                reservation.getEndDate(),
                DELIMITER,
                reservation.getGuest().getId(),
                DELIMITER,
                reservation.getTotal());
    }

    private Reservation deserialize(String[] fields, Host host) {
        Reservation result = new Reservation();
        result.setId(Integer.parseInt(fields[0]));
        result.setStartDate(getDate(fields[1]));
        result.setEndDate(getDate(fields[2]));
        result.setTotal(new BigDecimal(fields[4]));

        result.setHost(host);

        Guest guest = new Guest();
        guest.setId(Integer.parseInt(fields[3]));
        result.setGuest(guest);

        return result;
    }

    private void writeAll(List<Reservation> reservations, Host host) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(host.getId()))) {

            writer.println(HEADER);

            if (reservations == null) {
                return;
            }

            for (Reservation r : reservations) {
                writer.println(serialize(r));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private LocalDate getDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }
}
