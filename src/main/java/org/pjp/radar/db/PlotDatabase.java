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

    {
//        String id = UUID.randomUUID().toString();
//        plots.put(id, new Plot(id, 50114.66590698046, 0.6064153449524564, TargetSize.MEDIUM));
    }

    public List<Plot> getPlots() {
        return Collections.unmodifiableList(new ArrayList<Plot>(plots.values()));
    }

    public void updatePlot(Plot plot) {
        String id = plot.getId();

        if (plots.containsKey(id) ) {
            plots.replace(id, plot);
        } else {
            plots.put(id, plot);
        }
    }

}
