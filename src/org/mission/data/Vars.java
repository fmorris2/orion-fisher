package org.mission.data;

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

    public FishType fish_type = FishType.LOBSTER;
    public FishingLocation fishing_location = FishingLocation.F_KARAMJA_1;

}

