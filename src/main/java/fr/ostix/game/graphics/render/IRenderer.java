package fr.ostix.game.graphics.render;


import fr.ostix.game.entity.Entity;
import fr.ostix.game.graphics.model.Model;

public interface IRenderer {

    void prepareInstance(Entity entity);

    void prepareTexturedModel(Model model);


    void finish();
}
