package fr.ostix.game.entity.component;


import fr.ostix.game.entity.Entity;

public interface ComponentCreator {
    Component createComponent(Entity entity);

    Component loadComponent(String component, Entity entity);
}
