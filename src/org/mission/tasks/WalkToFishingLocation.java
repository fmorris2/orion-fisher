package org.mission.tasks;

import org.mission.OrionFisher;
import org.mission.data.vars.Vars;
import org.osbot.rs07.api.model.NPC;
import viking.api.Timing;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/3/2017.
 */
public class WalkToFishingLocation extends Task<OrionFisher> {
    
    public WalkToFishingLocation(OrionFisher mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        final NPC fishing_spot = npcs.closest(Vars.get().fishing_location.getArea(), "Fishing spot");
        return fishing_spot == null && !inventory.isFull();
    }

    @Override
    public void execute() {
        if (walkUtils.walkToArea(Vars.get().fishing_location.getArea(), () -> {
            final NPC fishing_spot = npcs.closest(Vars.get().fishing_location.getArea(), "Fishing spot");
            return fishing_spot != null && fishing_spot.isVisible();
        })) {
            Timing.waitCondition(() -> Vars.get().fishing_location.getArea().contains(myPlayer()), 150, random(2000, 2500));
        }
    }

    @Override
    public String toString() {
        return "Walking to fishing location";
    }
}

