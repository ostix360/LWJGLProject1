package fr.ostix.game.core.quest;

import java.util.*;

public class QuestManager {
    public static final QuestManager INSTANCE = new QuestManager();
    private final Map<Integer, Quest> quests;
    private Map<Integer, Quest> questing;

    //TODO Event system

    public QuestManager() {
        quests = new HashMap<>();
    }


}
