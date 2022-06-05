package fr.ostix.game.core.events.listener.quest;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.quest.*;
import fr.ostix.game.core.quest.*;

public class QuestCategoryListener implements Listener {
    private final QuestCategory questCategory;

    public QuestCategoryListener(QuestCategory questManager) {
        this.questCategory = questManager;
    }

    @EventHandler
    public void onQuestCategoryStart(QuestStartedEvent event) {
        Quest quest = questCategory.quests().get(event.getQuestID() - 1);
        if (quest instanceof QuestLocation) {
            EventManager.getInstance().register(new QuestLocationListener((QuestLocation) quest));
        } else if (quest instanceof QuestItem) {
            EventManager.getInstance().register(new QuestGiveItemListener((QuestItem) quest));
        } else if (quest instanceof QuestDialog) {
            EventManager.getInstance().register(new QuestTalkListener((QuestDialog) quest));
        } else {
            System.err.println("QuestCategoryListener : Unknown quest type");
        }
    }

    @EventHandler
    public void onQuestCategoryComplete(QuestFinishedEvent event) {
        //TODO : Add listener for each quest type to unregister it
    }
}
