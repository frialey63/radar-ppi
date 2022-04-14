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

        Aircraft[] allAircraft = client.getAllAircraft();

        for (Aircraft aircraft : allAircraft) {
            targetDatabase.updateTarget(new Target(aircraft.getIcaoAddress(), aircraft.getLatitude(), aircraft.getLongitude(), TargetSize.MEDIUM));
        }
    }

}
