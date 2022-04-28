package org.pjp.radar.sim;

public enum TargetSize {

    SMALL(3, 0.5),

    MEDIUM(5, 1.0),

    LARGE(8, 1.5);

    private final double mean;

    private final double standardDeviation;

    private TargetSize(double mean, double standardDeviation) {
        this.mean = mean;
        this.standardDeviation = standardDeviation;
    }

    public double getMean() {
        return mean;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }

}
