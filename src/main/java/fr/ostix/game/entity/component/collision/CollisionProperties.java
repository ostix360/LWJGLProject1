package fr.ostix.game.entity.component.collision;

import fr.ostix.game.entity.BoundingModel;

import java.util.List;

public class CollisionProperties {
    private boolean canMove = false;
    private boolean useSpecialBoundingBox;
    private List<BoundingModel> boundingModels;

    public CollisionProperties() {
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public List<BoundingModel> getBoundingModels() {
        return boundingModels;
    }

    public boolean canMove() {
        return canMove;
    }

    public boolean useSpecialBoundingBox() {
        return useSpecialBoundingBox;
    }

    public void setBoundingModels(List<BoundingModel> boundingModels) {
        this.boundingModels = boundingModels;
    }
}
