package fr.ostix.game.core.quest;

import fr.ostix.game.core.quest.serialization.*;
import fr.ostix.game.entity.entities.*;

public abstract class Quest implements IQuestSerializer {
    private final int id;
    private final NPC npc;
    private final String title;
    private final String description;
    private final Rewards rewards;
    private QuestStatus status;

    private final boolean isStarted = false;
    private final boolean isFinished = false;


    public Quest() {
        this.id = 0;
        this.npc = null;
        this.title = "Null Quest";
        this.description = "The Quest is null";
        this.rewards = new Rewards();
        this.status = QuestStatus.UNAVAILABLE;
    }

    public void done() {
        this.status = QuestStatus.DONE;
    }

    public NPC getNpc() {
        return npc;
    }

    public String getDescription() {
        return description;
    }

    public Rewards getRewards() {
        return rewards;
    }

    public QuestStatus getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public boolean isFinished() {
        return isFinished;
    }
}
