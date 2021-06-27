package fr.ostix.game.entity.component.animation;


import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.animated.animation.animatedModel.AnimatedModel;
import fr.ostix.game.entity.animated.animation.animation.Animation;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.ComponentType;
import fr.ostix.game.toolBox.Logger;

import java.util.HashMap;

public class AnimationComponent extends Component {

    private final HashMap<String, Animation> animations;

    public AnimationComponent(Entity e, HashMap<String, Animation> animations) {
        super(ComponentType.ANIMATED_COMPONENT, e);
        if (!(e.getModel() instanceof AnimatedModel)) {
            System.err.println("Your Model is not an AnimatedModel");
            System.exit(-1);
        }
        this.animations = animations;
    }

    @Override
    public void update() {
        AnimatedModel model = (AnimatedModel) e.getModel();
        Animation a = animations.get(e.getMovement().getId());
        if (a == null) {
            Logger.warn("The animation, " + e.getMovement().getId() + " for the model " + " is not available");
        } else {
            if (model.getPriorityAnimation() == null) {
                model.doAnimation(a);
            } else if (!model.getPriorityAnimation().equals(a)) {
                model.doAnimation(a);
            }
        }
        if (model.getPriorityAnimation() != null) {
            model.update(1f / 60f);
        }
    }
}
