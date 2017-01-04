package org.mission.data.vars;

import viking.api.skills.fishing.enums.FishType;
import viking.api.skills.fishing.enums.FishingLocation;

/**
 * Created by Sphiinx on 1/3/2017.
 */
public class Vars {

    public static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }

    public static void reset() {
        vars = null;
    }

    public FishType fish_type = FishType.SHRIMP;
    public FishingLocation fishing_location = FishingLocation.F_LUMBRIDGE_NORTH_1;

}

