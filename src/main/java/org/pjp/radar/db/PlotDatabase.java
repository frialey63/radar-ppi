package org.pjp.radar.db;

import java.util.ArrayList;
import java.util.List;

public final class PlotDatabase {

    private static final PlotDatabase instance = new PlotDatabase();

    public static PlotDatabase getInstance() {
        return instance;
    }

    private final List<Plot> plots = new ArrayList<>();

    {
        plots.add(new Plot(50114.66590698046, 0.6064153449524564, TargetSize.MEDIUM));
    }

    public List<Plot> getPlots() {
        return plots;
    }

}
