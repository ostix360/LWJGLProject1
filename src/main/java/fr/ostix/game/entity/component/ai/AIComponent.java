package fr.ostix.game.entity.component.ai;

import com.flowpowered.react.math.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.entity.component.*;
import org.joml.*;

import java.lang.Math;

public class AIComponent extends Component {

    private final Random r = new Random();
    private final AIProperties properties;
    private float time = 0;
    private float pos = 0;
    private float rotY = 0;

    public AIComponent(Entity e, AIProperties properties) {
        super(ComponentType.AI_COMPONENT, e);
        this.properties = properties;
    }


    @Override
    public void update() {
        time++;
        float dx;
        float dz;
        if (time % (r.nextInt((int) properties.getUpdate()) + 30) == 0) {
            if (r.nextInt((int) properties.getStaticTime()) == 0) {
                pos = 0;
                e.setMovement(Entity.MovementType.STATIC);
            } else {
                pos = generatePositiveValue(properties.getSpeed(), properties.getSpeedError());


                e.setMovement(Entity.MovementType.FORWARD);
            }
            if (r.nextInt((int) properties.getRotateProbabilities()) <= 1) {
                rotY = generateRotation(properties.getSpeedTurn(), properties.getSpeedTurnError());

                dx = (float) (pos * Math.sin(Math.toRadians(e.getRotation().y())));
                dz = (float) (pos * Math.cos(Math.toRadians(e.getRotation().y())));

                float deltaX = Math.abs((e.getPosition().x() + dx) - properties.getPos().x());
                float deltaZ = Math.abs((e.getPosition().z() + dz) - properties.getPos().z());
                float tpos = pos;
                if (deltaX >= properties.getDistance()) {
                    deltaX /= properties.getDistance();
                    tpos -= deltaX;
                    rotY = (float) Math.toDegrees(Math.asin(dx / tpos));

                }
                if (deltaZ >= properties.getDistance()) {
                    deltaZ /= properties.getDistance();
                    tpos -= deltaZ;
                    rotY = (float) Math.toDegrees(Math.asin(dz / tpos));
                }
            } else {
                rotY = 0;
            }

        }
        dx = (float) (pos * Math.sin(Math.toRadians(e.getRotation().y())));
        dz = (float) (pos * Math.cos(Math.toRadians(e.getRotation().y())));

        e.getTorque().set(new Vector3(0, rotY, 0));
        e.increaseRotation(new Vector3f(0, rotY, 0));
        e.getForceToCenter().add(new Vector3(dx, 0, dz));
    }

    private float generateRotation(float average, float errorMargin) {
        float offset = (r.nextFloat() - 0.5f) * 2f * errorMargin;
        return average + offset;
    }


    private float generatePositiveValue(float average, float errorMargin) {
        float offset = (r.nextFloat()) * errorMargin;
        return average + offset;
    }
}
