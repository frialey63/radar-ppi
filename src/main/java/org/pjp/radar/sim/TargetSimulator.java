package org.pjp.radar.sim;

import static org.williams.st.Utils.toDeg;
import static org.williams.st.Utils.toRad;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.williams.st.Point;
import org.williams.st.RhumbLine;
import org.williams.st.Utils;


public class TargetSimulator implements Runnable {

    private static final int INITIAL_DELAY = 1;

    private static final int PERIOD = 1;

    public static void simulate() {
        Runnable targetSimulator = new TargetSimulator();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        });
        executorService.scheduleAtFixedRate(targetSimulator, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
    }

    @Override
    public void run() {
        TargetDatabase targetDatabase = TargetDatabase.getInstance();

        for (Target target : targetDatabase.getTargets()) {

            Point point = new Point(toRad(target.getLat()), toRad(target.getLon()));

            RhumbLine rhumbLine = new RhumbLine(point, null);

            double distance = Utils.distRad(target.getSpeed() / 3600.0);
            double course = toRad(target.getCourse());

            Point newPoint = rhumbLine.radialDistance(course, distance);

            target.setLat(toDeg(newPoint.lat));
            target.setLon(toDeg(newPoint.lon));

            targetDatabase.updateTarget(target);
        }

    }

}
