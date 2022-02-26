package fr.ostix.game.core.quest;

import fr.ostix.game.entity.entities.*;

import java.util.*;

public class Quest {
    private final int id;
    private final NPC npc;
    private final String title;
    private final List<QuestAdvancement> advancements;
    private final List<Object> gains;

    private final boolean isStarted = false;
    private final boolean isFinished = false;


    public Quest(int id, NPC npc, String title, List<QuestAdvancement> advancements, List<Object> gains) {
        this.id = id;
        this.npc = npc;
        this.title = title;
        this.advancements = advancements;
        this.gains = gains;
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
