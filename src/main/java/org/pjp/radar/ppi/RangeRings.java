package org.pjp.radar.ppi;

final class RangeRings {
    static final RangeRings[] RANGE_RINGS = { new RangeRings(60, 5), new RangeRings(50, 5), new RangeRings(40, 5), new RangeRings(25, 5), new RangeRings(15, 5), };

    final int maxRange;	// nm

    final int numRings;

    public RangeRings(int maxRange, int numRings) {
        super();
        this.maxRange = maxRange;
        this.numRings = numRings;
    }

    public int rangeStep() {
        return maxRange / numRings;
    }
}