package org.pjp.radar.sim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class TargetDatabase {

    private static final TargetDatabase instance = new TargetDatabase();

    public static TargetDatabase getInstance() {
        return instance;
    }

    private final Map<String, Target> targets = new ConcurrentHashMap<>();

    public void generateTargets() {
        long tov = System.currentTimeMillis();
        String id;

        id = "KLM59M";
        targets.put(id, new Target(id, 51.581, 1.316, TargetSize.MEDIUM, tov, 263, 309));

        id = "EXS58RH";
        targets.put(id, new Target(id, 51.316, 1.633, TargetSize.MEDIUM, tov, 109, 467));

        id = "RYR27YY";
        targets.put(id, new Target(id, 50.614, 1.220, TargetSize.MEDIUM, tov, 314, 400));

        id = "BEL7EK";
        targets.put(id, new Target(id, 51.515, 2.191, TargetSize.MEDIUM, tov, 283, 379));
    }

    public List<Target> getTargets() {
        return Collections.unmodifiableList(new ArrayList<Target>(targets.values()));
    }

    public void updateTarget(Target target) {
        String id = target.getId();

        if (targets.containsKey(id) ) {
            targets.replace(id, target);
        } else {
            targets.put(id, target);
        }
    }

    public void removeTarget(Target target) {
        targets.remove(target.getId());
    }
}
