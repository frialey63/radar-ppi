package org.pjp.radar.sim;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.pjp.radar.rest.RestClient;
import org.pjp.radar.rest.dto.Aircraft;


public class TargetRequestor implements Runnable {

    private static final int INITIAL_DELAY = 1;

    private static final int PERIOD = 1;

    private static final long STALE_MILLIS = 2 * 60 * 1000;

    public static void request() {
        Runnable targetRequestor = new TargetRequestor();

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
            }
        });
        executorService.scheduleAtFixedRate(targetRequestor, INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
    }

    private final RestClient client = new RestClient();

    @Override
    public void run() {
        TargetDatabase targetDatabase = TargetDatabase.getInstance();

        for (Aircraft aircraft : client.getAllAircraft()) {
            targetDatabase.updateTarget(new Target(aircraft.getIcaoAddress(), aircraft.getLatitude(), aircraft.getLongitude(), TargetSize.MEDIUM, aircraft.getTov()));
        }

        long tov = System.currentTimeMillis() - STALE_MILLIS;

        targetDatabase.getTargets().stream().filter(t -> t.getTov() < tov).forEach(targetDatabase::removeTarget);
    }

}
