package org.mission.tasks;

import org.mission.OrionFisher;
import org.mission.data.vars.Vars;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import viking.api.Timing;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/3/2017.
 */
public class Fish extends Task<OrionFisher> {


    public Fish(OrionFisher mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        final NPC FISHING_SPOT = npcs.closest(Vars.get().fishing_location.getArea(), "Fishing spot");
        return FISHING_SPOT != null && !inventory.isFull() && inventory.contains(Vars.get().fish_type.getRequiredEquipmentIDs());
    }

    @Override
    public void execute() {
        final NPC FISHING_SPOT = npcs.closest(Vars.get().fishing_location.getArea(), "Fishing spot");
        if (FISHING_SPOT == null)
            return;

        if (myPlayer().getAnimation() != -1 || myPlayer().isMoving())
            return;

        if (FISHING_SPOT.isVisible()) {
            if (FISHING_SPOT.interact(Vars.get().fish_type.FISH_ACTION))
                Timing.waitCondition(() -> myPlayer().getAnimation() != -1, 150, random(2000, 2500));
        } else {
            if (walkUtils.walkToArea(FISHING_SPOT.getArea(3)))
                Timing.waitCondition(FISHING_SPOT::isVisible, 150, random(2000, 2500));
        }
    }

    @Override
    public String toString() {
        return "Fishing";
    }

}

