package org.mission.tasks;

import org.mission.OrionFisher;
import org.mission.data.Vars;
import org.osbot.rs07.api.filter.AreaFilter;
import org.osbot.rs07.api.filter.Filter;
import org.osbot.rs07.api.filter.NameFilter;
import org.osbot.rs07.api.filter.PositionFilter;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import viking.api.Timing;
import viking.api.filter.VFilters;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/3/2017.
 */
public class Fish extends Task<OrionFisher> {

    private final Position LUMBRIDGE_SOUTH_UNFISHABLE = new Position(0, 0, 0);

    public Fish(OrionFisher mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        final NPC FISHING_SPOT = getValidFishingSpot();
        return FISHING_SPOT != null && !inventory.isFull() && inventory.contains(Vars.get().fish_type.getRequiredEquipmentIDs());
    }

    @Override
    public void execute() {
        final NPC FISHING_SPOT = getValidFishingSpot();
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

    private NPC getValidFishingSpot() {
        final Filter ACTION_FILTER = VFilters.and(new NameFilter<>("Fishing spot"), new AreaFilter<>(Vars.get().fishing_location.getArea()));
        final Filter FISHING_FILTER = VFilters.not(new PositionFilter<>(LUMBRIDGE_SOUTH_UNFISHABLE), ACTION_FILTER) ;

        return npcs.closest(FISHING_FILTER);
    }

    @Override
    public String toString() {
        return "Fishing " + Vars.get().fish_type;
    }

}

