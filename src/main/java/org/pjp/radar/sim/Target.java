package org.pjp.radar.sim;

public class Target {

    private final String id;

    private double lat;		// degrees

    private double lon;		// degrees

    private TargetSize targetSize;

    private long tov;			// millis

    private double course;		// degrees

    private double speed;		// knots

    public Target(String id, double lat, double lon, TargetSize targetSize, long tov, double course, double speed) {
        super();
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.targetSize = targetSize;
        this.tov = tov;
        this.course = course;
        this.speed = speed;
    }

    public Target(String id, double lat, double lon, TargetSize targetSize, long tov) {
        this(id, lat, lon, targetSize, tov, 0.0, 0.0);
    }

    public String getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public TargetSize getTargetSize() {
        return targetSize;
    }

    public void setTargetSize(TargetSize targetSize) {
        this.targetSize = targetSize;
    }

    public long getTov() {
        return tov;
    }

    public void setTov(long tov) {
        this.tov = tov;
    }

    public double getCourse() {
        return course;
    }

    public void setCourse(double course) {
        this.course = course;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Target [id=" + id + ", lat=" + lat + ", lon=" + lon + ", targetSize=" + targetSize + ", tov=" + tov + ", course=" + course + ", speed=" + speed + "]";
    }

}
