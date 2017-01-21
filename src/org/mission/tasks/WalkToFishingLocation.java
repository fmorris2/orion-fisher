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
public class WalkToFishingLocation extends Task<OrionFisher> {

    private final Position LUMBRIDGE_SOUTH_UNFISHABLE = new Position(3246, 3157, 0);
    
    public WalkToFishingLocation(OrionFisher mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        final NPC fishing_spot = getValidFishingSpot();
        return fishing_spot == null && !inventory.isFull();
    }

    @Override
    public void execute() {
        if (walkUtils.walkToArea(Vars.get().fishing_location.getArea(), () -> {
            final NPC fishing_spot = getValidFishingSpot();
            return fishing_spot != null;
        })) {
            Timing.waitCondition(() -> getValidFishingSpot() != null, 150, random(2000, 2500));
        }
    }

    private NPC getValidFishingSpot() {
        final Filter ACTION_FILTER = VFilters.and(new NameFilter<>("Fishing spot"), new AreaFilter<>(Vars.get().fishing_location.getArea()));
        final Filter NOT_FILTER = VFilters.not(new PositionFilter<>(LUMBRIDGE_SOUTH_UNFISHABLE));
        final Filter FISHING_FILTER = VFilters.and(NOT_FILTER, ACTION_FILTER);

        return npcs.closest(FISHING_FILTER);
    }

    @Override
    public String toString() {
        return "Walking to fishing location";
    }
}

