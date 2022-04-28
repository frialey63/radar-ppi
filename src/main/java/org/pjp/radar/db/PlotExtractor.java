package org.pjp.radar.db;

import static org.williams.st.Utils.toRad;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.pjp.radar.Radar;
import org.pjp.radar.sim.Target;
import org.pjp.radar.sim.TargetDatabase;
import org.pjp.radar.util.Constants;
import org.williams.st.FEPoint;
import org.williams.st.FlatEarth;

public class PlotExtractor implements Runnable {

    private static final int INITIAL_DELAY = 4800;

    private static final int PERIOD = 4800;

    public static void extract() {
        Runnable plotExtractor = new PlotExtractor();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        });
        executorService.scheduleAtFixedRate(plotExtractor, INITIAL_DELAY, PERIOD, TimeUnit.MILLISECONDS);
    }

    private static final FEPoint RADAR_POINT = new FEPoint(toRad(51.38287), toRad(1.33574));

    @Override
    public void run() {
        FlatEarth flatEarth = new FlatEarth(RADAR_POINT);
        double radarRange = Constants.NM_TO_M * Radar.RADAR.getInstrumentedRange();

        PlotDatabase plotDatabase = PlotDatabase.getInstance();

        plotDatabase.clearPlots();

        for (Target target : TargetDatabase.getInstance().getTargets()) {
            FEPoint targetPoint = new FEPoint(toRad(target.getLat()), toRad(target.getLon()));

            double range = flatEarth.distance(targetPoint);
            double bearing = flatEarth.bearing(targetPoint);

            if (range <= radarRange) {
                Plot plot = new Plot(target.getId(), range, bearing, target.getSize());
                plotDatabase.storePlot(plot);
            }
        }
    }
}
