package org.mission;

import org.mission.tasks.OF_DepositItems;
import org.mission.tasks.Fish;
import org.mission.tasks.GetEquipment;
import org.mission.tasks.WalkToFishingLocation;
import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.event.webwalk.PathPreferenceProfile;
import viking.api.skills.fishing.enums.FishType;
import viking.api.skills.fishing.enums.FishingEquipment;
import viking.api.skills.fishing.enums.FishingLocation;
import viking.framework.command.CommandReceiver;
import viking.framework.goal.GoalList;
import viking.framework.goal.impl.InfiniteGoal;
import viking.framework.goal.impl.SkillGoal;
import viking.framework.item_management.IMEntry;
import viking.framework.item_management.ItemManagement;
import viking.framework.mission.Mission;
import viking.framework.script.VikingScript;
import viking.framework.task.TaskManager;

public class OrionFisher extends Mission implements CommandReceiver, ItemManagement {

    private final TaskManager<OrionFisher> TASK_MANAGER = new TaskManager<>(this);

    private CommandReceiver orion_main;

    public OrionFisher(VikingScript script, FishType target_type) {
        super(script);
        orion_main = script instanceof CommandReceiver ? (CommandReceiver) script : null;
    }

    @Override
    public boolean canEnd() {
        return false;
    }

    @Override
    public String getMissionName() {
        return null;
    }

    @Override
    public String getCurrentTaskName() {
        return null;
    }

    @Override
    public String getEndMessage() {
        return null;
    }

    @Override
    public GoalList getGoals() {
        return new GoalList(new InfiniteGoal());
    }

    @Override
    public String[] getMissionPaint() {
        return null;
    }

    @Override
    public int execute() {
        TASK_MANAGER.loop();
        return 150;
    }

    @Override
    public void onMissionStart() {
        TASK_MANAGER.addTask(new OF_DepositItems(this), new GetEquipment(this), new WalkToFishingLocation(this), new Fish(this));
    }

    @Override
    public void resetPaint() {
    }

    @Override
    public void receiveCommand(String command) {

    }

    @Override
    public IMEntry[] itemsToBuy() {
        return new IMEntry[]{
                new IMEntry(this, FishingEquipment.FLY_FISHING_ROD.getItemID(), 1, "Fly fishing rod", new SkillGoal(skills, Skill.FISHING, FishingEquipment.FLY_FISHING_ROD.getFishingLevel())),
                new IMEntry(this, FishingEquipment.FEATHER.getItemID(), 3500, "Feather", new SkillGoal(skills, Skill.FISHING, FishingEquipment.FLY_FISHING_ROD.getFishingLevel())),
                new IMEntry(this, FishingEquipment.HARPOON.getItemID(), 1, "Harpoon", new SkillGoal(skills, Skill.FISHING, FishingEquipment.HARPOON.getFishingLevel())),
                new IMEntry(this, FishingEquipment.LOBSTER_POT.getItemID(), 1, "Lobster pot", new SkillGoal(skills, Skill.FISHING, FishingEquipment.LOBSTER_POT.getFishingLevel())),
        };
    }

    @Override
    public int[] itemsToSell() {
        return ItemManagement.ORION_SELL_ITEMS;
    }
}