package org.pjp.radar.db;

public enum TargetSize {

    SMALL(3),

    MEDIUM(5),

    LARGE(8);

    private final int pixels;

    private TargetSize(int pixels) {
        this.pixels = pixels;
    }

    public int getPixels() {
        return pixels;
    }


}
