package learn.data;

import learn.models.Guest;
import learn.models.Host;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepository implements HostRepository {

    private final String HEADER = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";
    private final String DELIMITER = ",";
    private final String filePath;

    public HostFileRepository(String filePath) {this.filePath = filePath;}

    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                String[] fields = line.split(DELIMITER,-1);
                if (fields.length == 10) {
                    result.add(deserialize(fields));
                }
            }
        } catch (IOException ex) {
            // do nothing
        }
        return result;
    }

    @Override
    public Host findByEmail(String email) {
        List<Host> all = findAll();
        for (Host h : all) {
            if (h.getEmail().equals(email)) {
                return h;
            }
        }
        return null;
    }

    @Override
    public Host add(Host host) throws DataException {
        if (host == null) {
            return null;
        }
        List<Host> all = findAll();;
        host.setId(java.util.UUID.randomUUID().toString());
        all.add(host);
        writeAll(all);
        return host;
    }

    @Override
    public boolean update(Host host) throws DataException {
        if (host == null) {
            return false;
        }

        List<Host> all = findAll();
        for (int index = 0; index < all.size(); index++) {
            if (host.getId().equals(all.get(index).getId())) {
                all.set(index, host);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Host host) throws DataException {
        if (host == null) {
            return false;
        }

        List<Host> all = findAll();
        for (int index = 0; index < all.size(); index++) {
            if (host.getId().equals(all.get(index).getId())) {
                all.remove(index);
                writeAll(all);
                return true;
            }
        }
        return false;
    }

    private String serialize(Host host) {
        return String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s",
                host.getId(),
                DELIMITER,
                host.getLastName(),
                DELIMITER,
                host.getEmail(),
                DELIMITER,
                host.getPhoneNumber(),
                DELIMITER,
                host.getAddress(),
                DELIMITER,
                host.getCity(),
                DELIMITER,
                host.getState(),
                DELIMITER,
                host.getPostalCode(),
                DELIMITER,
                host.getStdRate(),
                DELIMITER,
                host.getWkndRate());
    }

    private Host deserialize(String[] fields) {
        Host result = new Host();
        result.setId(fields[0]);
        result.setLastName(fields[1]);
        result.setEmail(fields[2]);
        result.setPhoneNumber(fields[3]);
        result.setAddress(fields[4]);
        result.setCity(fields[5]);
        result.setState(fields[6]);
        result.setPostalCode(Integer.parseInt(fields[7]));
        result.setStdRate(new BigDecimal(fields[8]));
        result.setWkndRate(new BigDecimal(fields[9]));

        return result;
    }

    private void writeAll(List<Host> hosts) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(HEADER);

            if (hosts == null) {
                return;
            }

            for (Host host : hosts) {
                writer.println(serialize(host));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }
}
