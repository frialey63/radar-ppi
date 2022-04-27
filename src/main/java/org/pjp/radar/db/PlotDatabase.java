package org.pjp.radar.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class PlotDatabase {

    private static final PlotDatabase instance = new PlotDatabase();

    public static PlotDatabase getInstance() {
        return instance;
    }

    private final Map<String, Plot> plots = new ConcurrentHashMap<>();

    public void clearPlots() {
        plots.clear();
    }

    public List<Plot> getPlots() {
        return Collections.unmodifiableList(new ArrayList<Plot>(plots.values()));
    }

    public void storePlot(Plot plot) {
        plots.put(plot.getId(), plot);
    }

}
