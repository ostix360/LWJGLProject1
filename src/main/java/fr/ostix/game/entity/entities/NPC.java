package fr.ostix.game.entity.entities;

import fr.ostix.game.entity.*;
import fr.ostix.game.graphics.model.*;
import org.joml.*;

import java.util.*;

public class NPC extends Entity {
    private int id;
    private String name;


    public NPC(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }

    public void talke(List<String> dialogs) {

    }

    public int getId() {
        return id;
    }
}
