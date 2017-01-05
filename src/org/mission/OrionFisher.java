package org.mission;

import org.mission.tasks.DepositItems;
import org.mission.tasks.Fish;
import org.mission.tasks.GetEquipment;
import org.mission.tasks.WalkToFishingLocation;
import viking.api.item_managment.ItemManagment;
import viking.api.skills.fishing.enums.FishType;
import viking.api.skills.fishing.enums.FishingEquipment;
import viking.framework.command.CommandReceiver;
import viking.framework.goal.GoalList;
import viking.framework.goal.impl.InfiniteGoal;
import viking.framework.mission.Mission;
import viking.framework.script.VikingScript;
import viking.framework.task.TaskManager;

public class OrionFisher extends Mission implements CommandReceiver {

	private final TaskManager<OrionFisher> TASK_MANAGER = new TaskManager<>(this);

	private CommandReceiver orion_main;

	OrionFisher(VikingScript script, FishType target_type) {
		super(script);
		orion_main = script instanceof CommandReceiver ? (CommandReceiver)script : null;
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
		TASK_MANAGER.addTask(new DepositItems(this), new GetEquipment(this), new WalkToFishingLocation(this), new Fish(this));
	}

	@Override
	public void resetPaint() {
	}

	@Override
	public void receiveCommand(String command) {

	}

}