package learn.models;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private Host host;
    private Guest guest;
    int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal total = BigDecimal.ZERO;

    public Reservation() {

    }

    public Reservation(Host host, Guest guest, int id, LocalDate startDate, LocalDate endDate, BigDecimal total) {
        this.host = host;
        this.guest = guest;
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.total = total;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "host=" + host +
                ", guest=" + guest +
                ", id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", total=" + total +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id &&
                Objects.equals(host, that.host) &&
                Objects.equals(guest, that.guest) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(total, that.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, guest, id, startDate, endDate, total);
    }

    public BigDecimal calcTotal() {
        LocalDate start = startDate;
        LocalDate end = endDate;
        total = BigDecimal.ZERO;
        for (; start.isBefore(end); start = start.plusDays(1)) {
            if (!(start.getDayOfWeek() == DayOfWeek.FRIDAY) ||  !(start.getDayOfWeek() == DayOfWeek.SATURDAY)) {
                total = total.add(host.getStdRate());
            } else {
                total = total.add(host.getWkndRate());
            }
        }
        return total;
    }
}
