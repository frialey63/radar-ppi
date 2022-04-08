package org.pjp.radar.db;

import org.pjp.radar.sim.TargetSize;

public final class Plot {

    private final String id;

    private final double range;		// metre

    private final double bearing;	// radian

    private final TargetSize targetSize;

    public Plot(String id, double range, double bearing, TargetSize targetSize) {
        super();
        this.id = id;
        this.range = range;
        this.bearing = bearing;
        this.targetSize = targetSize;
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

    public TargetSize getTargetSize() {
        return targetSize;
    }

    @Override
    public String toString() {
        return "Plot [id=" + id + ", range=" + range + ", bearing=" + bearing + ", targetSize=" + targetSize + "]";
    }

}
