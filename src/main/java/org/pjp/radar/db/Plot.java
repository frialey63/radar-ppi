package org.pjp.radar.db;

public final class Plot {

    private final String id;		// TODO maybe remove plot ID

    private final double range;		// metre

    private final double bearing;	// radian

    private final double size;

    public Plot(String id, double range, double bearing, double size) {
        super();
        this.id = id;
        this.range = range;
        this.bearing = bearing;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public double getRange() {
        return range;
    }

    public double getBearing() {
        return bearing;
    }

    public double getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Plot [id=" + id + ", range=" + range + ", bearing=" + bearing + ", size=" + size + "]";
    }

}
