package org.pjp.radar.db;

public final class Plot {

    private final double range;

    private final double bearing;

    private final TargetSize targetSize;

    public Plot(double range, double bearing, TargetSize targetSize) {
        super();
        this.range = range;
        this.bearing = bearing;
        this.targetSize = targetSize;
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
        return "Plot [range=" + range + ", bearing=" + bearing + ", targetSize=" + targetSize + "]";
    }

}
