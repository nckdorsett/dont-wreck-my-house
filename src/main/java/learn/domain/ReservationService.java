package learn.domain;

import learn.data.DataException;
import learn.data.GuestRepository;
import learn.data.HostRepository;
import learn.data.ReservationRepository;
import learn.models.Host;
import learn.models.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final HostRepository hostRepository;

    public ReservationService(ReservationRepository reservationRepository
            , GuestRepository guestRepository, HostRepository hostRepository) {
        this.reservationRepository = reservationRepository;
        this.guestRepository = guestRepository;
        this.hostRepository = hostRepository;
    }

    public List<Reservation> findByHost(Host host) {

        List<Reservation> result = reservationRepository.findByHost(host);
        for (Reservation r : result) {
            r.setGuest(guestRepository.findById(r.getGuest().getId()));
        }

        return result;
    }

    public Result<Reservation> add(Reservation reservation) throws DataException {
        Result<Reservation> result = validate(reservation);
        if (!result.isSuccess()) {
            return result;
        }
        result.setPayload(reservationRepository.add(reservation));
        return result;
    }




    private Result<Reservation> validate(Reservation reservation) {
        Result<Reservation> result = new Result<>();

        if (reservation == null) {
            result.addErrorMessage("Reservation is empty");
            return result;
        }

        if (!isADate(reservation.getStartDate())) {
            result.addErrorMessage("Start date must be a date");
        }

        if (!isADate(reservation.getEndDate())) {
            result.addErrorMessage("End date must be a date");
        }

        if (reservation.getHost() == null) {
            result.addErrorMessage("Host is empty");
        } else if (reservation.getHost().getId() == null
                || hostRepository.findById(reservation.getHost().getId()) == null) {
            result.addErrorMessage("Host does not exist");
        }

        if (reservation.getGuest() == null) {
            result.addErrorMessage("Guest is empty");
        } else if (guestRepository.findById(reservation.getGuest().getId()) == null) {
            result.addErrorMessage("Guest does not exist");
        }

        if (reservation.getTotal() == null) {
            result.addErrorMessage("Total is empty");
        }

        dateAvailability(reservation, result);

        return result;
    }

    private boolean isADate(LocalDate date) {
        if (date == null) {
            return false;
        }
        String dateString = date.toString();
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";
        Pattern datePattern = Pattern.compile(dateRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = datePattern.matcher(dateString);
        return matcher.find();
    }

    private boolean dateAvailability(Reservation reservation, Result<Reservation> result) {

        if (reservation.getStartDate() == null || reservation.getEndDate() == null) {
            return false;
        }

        if (reservation.getStartDate().isBefore(LocalDate.now())
                || reservation.getEndDate().isBefore(LocalDate.now())) {
            result.addErrorMessage("Date is not in the future");
        }
        if (reservation.getStartDate().isAfter(reservation.getEndDate())) {
            result.addErrorMessage("Start Date is after End Date");
        }

        int scheduleCounter = 0;
        List<Reservation> all = findByHost(reservation.getHost());
        for (Reservation r : all) {
            if (r.getEndDate().isBefore(reservation.getStartDate())
                    || r.getEndDate().equals(reservation.getStartDate())
                    || r.getStartDate().isAfter(reservation.getEndDate())) {
                scheduleCounter++;
            }
        }
        if (scheduleCounter == all.size()) {
            return true;
        }
        return false;
    }



}
