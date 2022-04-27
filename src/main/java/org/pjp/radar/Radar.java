package org.pjp.radar;

public final class Radar {

    public static final Radar RADAR = new Radar("ASR 9", "Northrop Grumman", "AN/GPN-27", 2_800_000_000L, 325, 0.000_001, 1_300_000, 60, 450, 1.4, 12.5);

    private final String name;

    private final String manufacturer;

    private final String milDesign;

    private final long frequency;		// Hz

    private final int prf;				// PPS

    private final double pulseWidth;	// sec

    private final long peakPower;		// Watt

    private final int instrumentedRange;	// nm

    private final int rangeResolution;		// feet

    private final double beamwidth;			// degree

    private final double antennaRotation;	// RPM

    public Radar(String name, String manufacturer, String milDesign,
            long frequency, int prf, double pulseWidth, long peakPower, int instrumentedRange, int rangeResolution, double beamwidth, double antennaRotation) {
        super();
        this.name = name;
        this.manufacturer = manufacturer;
        this.milDesign = milDesign;
        this.frequency = frequency;
        this.prf = prf;
        this.pulseWidth = pulseWidth;
        this.peakPower = peakPower;
        this.instrumentedRange = instrumentedRange;
        this.rangeResolution = rangeResolution;
        this.beamwidth = beamwidth;
        this.antennaRotation = antennaRotation;
    }

    public String getName() {
        return name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getMilDesign() {
        return milDesign;
    }

    public long getFrequency() {
        return frequency;
    }

    public int getPrf() {
        return prf;
    }

    public double getPulseWidth() {
        return pulseWidth;
    }

    public long getPeakPower() {
        return peakPower;
    }

    public int getInstrumentedRange() {
        return instrumentedRange;
    }

    public int getRangeResolution() {
        return rangeResolution;
    }

    public double getBeamwidth() {
        return beamwidth;
    }

    public double getAntennaRotation() {
        return antennaRotation;
    }

    public double getSecsPerScan() {
        return 60.0 / antennaRotation;
    }

    public int getPulsesPerScan() {
        return (int) (prf * getSecsPerScan());
    }

    public double getRadsPerPulse() {
        return 2 * Math.PI / getPulsesPerScan();
    }

    public int getPulsesPerBeam() {
        return (int) (beamwidth * getPulsesPerScan() / 360.0);
    }

}
