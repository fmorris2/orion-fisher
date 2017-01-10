package org.mission.tasks;

import org.mission.OrionFisher;
import org.mission.data.Vars;
import org.osbot.rs07.api.model.Item;
import viking.api.Timing;
import viking.api.skills.fishing.enums.FishingEquipment;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/3/2017.
 */
public class GetEquipment extends Task<OrionFisher> {

    public GetEquipment(OrionFisher mission) {
        super(mission);
    }

    @Override
    public boolean validate() {
        return !inventory.contains(Vars.get().fish_type.getRequiredEquipmentIDs());
    }

    @Override
    public void execute() {
        if (bank.isOpen()) {
            for (FishingEquipment equipment : Vars.get().fish_type.getRequiredEquipment()) {
                if (!inventory.contains(equipment.getItemID())) {
                    final Item[] INVENTORY_CACHE = inventory.getItems();
                    if (bank.withdraw(equipment.getItemID(), equipment.getItemAmount()))
                        Timing.waitCondition(() -> INVENTORY_CACHE.length != inventory.getItems().length, 150, random(2000, 2500));
                }
            }
        } else {
            if (bankUtils.isInBank()) {
                if (bankUtils.open())
                    Timing.waitCondition(() -> bank.isOpen(), 150, random(2000, 2500));
            } else {
                if (getWalking().webWalk(bankUtils.getAllBanks(false, false)))
                    Timing.waitCondition(() -> bankUtils.isInBank(), 150, random(2000, 2500));
            }
        }
    }

    @Override
    public String toString() {
        return "Getting required equipment";
    }
}

