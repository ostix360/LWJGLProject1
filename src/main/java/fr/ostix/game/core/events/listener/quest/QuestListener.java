package fr.ostix.game.core.events.listener.quest;

import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.entity.npc.*;
import fr.ostix.game.core.events.listener.*;
import fr.ostix.game.core.events.player.*;
import fr.ostix.game.core.events.quest.*;
import fr.ostix.game.core.quest.*;

import java.util.*;

public class QuestListener implements Listener {
    private final List<Integer> questing;
    private final QuestManager QM;
    private final List<QuestLocation> questLocationsSafeDel = new ArrayList<>();
    private final List<QuestItem> questItemsSafeDel = new ArrayList<>();
    private final List<QuestDialog> questDialogsSafeDel = new ArrayList<>();
    private List<QuestLocation> questLocations;
    private List<QuestItem> questItems;
    private List<QuestDialog> questDialogs;


    public QuestListener(QuestManager QM) {
        this.QM = QM;
        this.questing = QM.getQuesting();
    }


    @EventHandler
    public void onQuestStarted(QuestStartedEvent e) {
        questing.add(e.getQuestID());
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!questLocations.isEmpty()) {
            questLocations.forEach((ql) -> {
                if (e.getPlayer().getPosition().equals(ql.getPos(), ql.getRange())) {
                    ql.done();
                    questLocationsSafeDel.add(ql);
                }
            });
            questLocationsSafeDel.forEach((ql) -> questLocations.remove(ql));
            questLocationsSafeDel.clear();
        }
    }

    @EventHandler
    public void onTalkToNPC(NPCTalkEvent e) {
        if (!questDialogs.isEmpty()) {
            questDialogs.forEach((qd) -> {
                if (e.getNpc().equals(qd.getNpc())) {
                    e.getNpc().talke(qd.getDialogs());
                    qd.done();
                    questDialogsSafeDel.add(qd);
                }
            });
            questDialogsSafeDel.forEach((ql) -> questDialogs.remove(ql));
            questDialogsSafeDel.clear();
        }
    }

    @EventHandler
    public void onTakeItem(PlayerGiveItemEvent e) {
        if (!questItems.isEmpty()) {
            questItems.forEach((qi) -> {
                if (qi.getItem().equals(e.getStack())) {
                    qi.done();
                    questItemsSafeDel.add(qi);
                }
            });
            questItemsSafeDel.forEach((ql) -> questItems.remove(ql));
            questItemsSafeDel.clear();
        }
    }
}
