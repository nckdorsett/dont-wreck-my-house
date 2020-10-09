package learn.models;

import java.math.BigDecimal;
import java.util.Objects;

public class Host {
    private String id;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private BigDecimal stdRate;
    private BigDecimal wkndRate;

    public Host() {

    }

    public Host(String id, String lastName, String email, String phoneNumber, String address, String city, String state, String postalCode, BigDecimal stdRate, BigDecimal wkndRate) {
        this.id = id;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.stdRate = stdRate;
        this.wkndRate = wkndRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public BigDecimal getStdRate() {
        return stdRate;
    }

    public void setStdRate(BigDecimal stdRate) {
        this.stdRate = stdRate;
    }

    public BigDecimal getWkndRate() {
        return wkndRate;
    }

    public void setWkndRate(BigDecimal wkndRate) {
        this.wkndRate = wkndRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return postalCode == host.postalCode &&
                Objects.equals(id, host.id) &&
                Objects.equals(lastName, host.lastName) &&
                Objects.equals(email, host.email) &&
                Objects.equals(phoneNumber, host.phoneNumber) &&
                Objects.equals(address, host.address) &&
                Objects.equals(city, host.city) &&
                Objects.equals(state, host.state) &&
                Objects.equals(stdRate, host.stdRate) &&
                Objects.equals(wkndRate, host.wkndRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, email, phoneNumber, address, city, state, postalCode, stdRate, wkndRate);
    }

    @Override
    public String toString() {
        return "Host{" +
                "id='" + id + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postalCode=" + postalCode +
                ", stdRate=" + stdRate +
                ", wkndRate=" + wkndRate +
                '}';
    }
}
