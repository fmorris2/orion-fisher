package org.mission.tasks;

import org.mission.OrionFisher;
import org.mission.data.vars.Vars;
import viking.api.Timing;
import viking.api.skills.fishing.enums.FishingEquipment;
import viking.framework.task.Task;

/**
 * Created by Sphiinx on 1/3/2017.
 */
public class DepositItems extends Task<OrionFisher> {

    public DepositItems(OrionFisher mission) {
        super(mission);
    }

    public boolean validate() {
        return inventory.isFull();
    }

    public void execute() {
        if (Vars.get().fishing_location.shouldUseDepositBox()) {
            if (depositBox.isOpen()) {
                if (depositBox.depositAllExcept(FishingEquipment.getItemIDs()))
                    Timing.waitCondition(() -> !inventory.isFull(), 150, random(2000, 2500));
            } else {
                if (bankUtils.isInBank()) {
                    if (depositBox.open())
                        Timing.waitCondition(() -> depositBox.isOpen(), 150, random(2000, 2500));
                } else {
                    if (getWalking().webWalk(bankUtils.getAllBanks(false, true)))
                        Timing.waitCondition(() -> bankUtils.isInBank(), 150, random(2000, 2500));
                }
            }
        } else {
            if (bank.isOpen()) {
                if (bank.depositAllExcept(FishingEquipment.getItemIDs()))
                    Timing.waitCondition(() -> !inventory.isFull(), 150, random(2000, 2500));
            } else {
                if (bankUtils.isInBank()) {
                    if (bankUtils.open())
                        Timing.waitCondition(conditions.BANK_OPEN, 150, random(2000, 2500));
                } else {
                    if (getWalking().webWalk(bankUtils.getAllBanks(false, false)))
                        Timing.waitCondition(() -> bankUtils.isInBank(), 150, random(2000, 2500));
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Depositing items";
    }
}