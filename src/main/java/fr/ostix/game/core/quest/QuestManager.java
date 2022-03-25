package fr.ostix.game.core.quest;

import java.util.*;

public class QuestManager {
    public static final QuestManager INSTANCE = new QuestManager();
    private final Map<Integer, Quest> quests;
    private final List<Integer> questing;

    //TODO Event system

    public QuestManager() {
        quests = new HashMap<>();
        questing = new ArrayList<>();
    }


    public Map<Integer, Quest> getQuests() {
        return quests;
    }

    public List<Integer> getQuesting() {
        return questing;
    }
}
