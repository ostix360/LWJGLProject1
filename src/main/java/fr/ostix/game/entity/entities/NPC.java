package fr.ostix.game.entity.entities;

import fr.ostix.game.entity.*;
import fr.ostix.game.graphics.model.*;
import org.joml.*;

public class NPC extends Entity {
    private int ID;
    private String name;


    public NPC(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
    }

    public void talke() {

    }

}
