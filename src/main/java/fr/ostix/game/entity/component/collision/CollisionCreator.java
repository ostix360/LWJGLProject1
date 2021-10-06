package fr.ostix.game.entity.component.collision;

import fr.ostix.game.core.collision.react.shape.*;
import fr.ostix.game.core.collision.react.shape.CollisionShape.CollisionShapeType;
import fr.ostix.game.entity.BoundingModel;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.Transform;
import fr.ostix.game.entity.component.Component;
import fr.ostix.game.entity.component.ComponentCreator;

import java.util.ArrayList;
import java.util.List;

public class CollisionCreator implements ComponentCreator {


    @Override
    public Component createComponent(Entity entity) {
        return null;
    }

    @Override
    public Component loadComponent(String component, Entity entity) {
        CollisionProperties prop = new CollisionProperties();
        List<BoundingModel> bounds = new ArrayList<>();
        String[] lines = component.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            CollisionShapeType type = isType(line);
            if (type != null) {
                BoundingModel model;
                switch (type) {
                    case BOX:
                        model = BoxShape.load(lines[i++]);
                        break;
                    case CAPSULE:
                        model = CapsuleShape.load(lines[i++]);
                        break;
                    case CONE:
                        model = ConeShape.load(lines[i++]);
                        break;
                    case CYLINDER:
                        model = CylinderShape.load(lines[i++]);
                        break;
                    default:
                        model = SphereShape.load(lines[i++]);
                }
                Transform t = Transform.load(lines[i++]);
                model.setTransform(t);
                bounds.add(model);
            } else {
                String sb = line + "\n" +
                        lines[i++];
                bounds.add(BoundingModel.load(sb));
            }
        }
        prop.setBoundingModels(bounds);
        return new CollisionComponent(entity, prop);
    }

    private CollisionShapeType isType(String line) {
        for (CollisionShapeType type : CollisionShapeType.values()) {
            if (line.equalsIgnoreCase(type.toString())) {
                return type;
            }
        }
        return null;
    }
}
