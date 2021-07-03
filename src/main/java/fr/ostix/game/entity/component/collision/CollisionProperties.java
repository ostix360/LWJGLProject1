package fr.ostix.game.entity.component.collision;

import fr.ostix.game.core.resources.CollisionShapeResource;
import fr.ostix.game.entity.BoundingModel;

public class CollisionProperties {
    private final boolean canMove;
    private final boolean useSpecialBoundingBox;
    private final BoundingModel[] boundingModels;
    private final CollisionShapeResource[] collision;

    public CollisionProperties(boolean canMove, boolean useSpecialBoundingBox, BoundingModel[] boundingModels, CollisionShapeResource[] collision) {
        this.canMove = canMove;
        this.useSpecialBoundingBox = useSpecialBoundingBox;
        this.boundingModels = boundingModels;
        this.collision = collision;
    }

    public boolean canMove() {
        return canMove;
    }

    public boolean useSpecialBoundingBox() {
        return useSpecialBoundingBox;
    }

    public BoundingModel[] getBoundingModels() {
        return boundingModels;
    }

    public CollisionShapeResource[] getCollision() {
        return collision;
    }
}
