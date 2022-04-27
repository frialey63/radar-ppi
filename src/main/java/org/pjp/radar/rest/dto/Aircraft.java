package org.pjp.radar.rest.dto;

public class Aircraft {

    private String icaoAddress;

    private double latitude;

    private double longitude;

    private long tov;

    public Aircraft() {
        super();
    }

    public Aircraft(String icaoAddress, double latitude, double longitude, long tov) {
        super();
        this.icaoAddress = icaoAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.tov = tov;
    }

    public String getIcaoAddress() {
        return icaoAddress;
    }

    public void setIcaoAddress(String icaoAddress) {
        this.icaoAddress = icaoAddress;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getTov() {
        return tov;
    }

    public void setTov(long tov) {
        this.tov = tov;
    }

    @Override
    public String toString() {
        return "Aircraft [icaoAddress=" + icaoAddress + ", latitude=" + latitude + ", longitude=" + longitude + ", tov=" + tov + "]";
    }

}
