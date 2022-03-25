package fr.ostix.game.core.quest;

import java.util.*;

public class QuestCategory {

    public HashMap<Integer, Quest> quests;
    public int id;
    public String title;

    public QuestCategory() {
        this.id = 0;
        this.title = "";
        this.quests = new HashMap<>();
    }

    public void load() {

    }

    public void save() {

    }

    public Collection<Quest> quests() {
        return this.quests.values();
    }

    public String getName() {
        return this.title;
    }

}
