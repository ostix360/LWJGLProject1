package fr.ostix.game.core.quest;

import fr.ostix.game.core.loader.json.*;
import org.joml.*;

public class QuestLocation extends Quest {
    private final Vector3f pos;
    private final float range;


    public QuestLocation() {
        this.pos = new Vector3f();
        this.range = 5;
    }

    public static QuestLocation load(String questData) {
        return JsonUtils.gsonInstance().fromJson(questData, QuestLocation.class);
    }

    public Vector3f getPos() {
        return pos;
    }

    public float getRange() {
        return range;
    }

    @Override
    public String save() {
        return JsonUtils.gsonInstance().toJson(this);
    }
}
