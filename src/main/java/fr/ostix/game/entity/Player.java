package fr.ostix.game.entity;

import com.flowpowered.react.math.*;
import fr.ostix.game.graphics.model.*;
import fr.ostix.game.inventory.*;
import org.joml.*;

public class Player extends Entity {

    private static final float RUN_SPEED = 160;
    private static final float TURN_SPEED = 780;
    public static final float GRAVITY = 0.12f;
    private static final float JUMP_POWER = 2;

    private final float currentSpeed = 0;
    private final float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    public boolean canJump = true;

    private final int health = 10;
    private int sprintTime = 60 * 5;
    private final boolean isSprinting = false;

    private PlayerInventory inventory;


    public Player(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(0, model, position, rotation, scale);
    }


//    public Player(Entity e) {
//        super(e.getModel(), e.getPosition(), e.getRotation(), e.getScale());
//    }

    @Override
    public void update() {
        this.move();
        if (this.getMovement() == MovementType.FORWARD) {
            this.sprintTime--;
            if (this.sprintTime < 0) {
                sprintTime = 0;
            }
        } else {
            this.sprintTime++;
            if (this.sprintTime > 60 * 5) {
                sprintTime = 60 * 5;
            }
        }
        super.update();
    }

    private void move() {
        if (forceToCenter.isZero()) {
            this.movement = MovementType.STATIC;
        }
        super.increaseRotation(new Vector3f(0, this.currentTurnSpeed * 0.0023f, 0));
        torque.set(new Vector3(0, this.currentTurnSpeed, 0));
//        float distance = currentSpeed * 0.006f;
//        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotation().y())));
//        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotation().y())));
        forceToCenter.setToZero();
        //upwardsSpeed -= GRAVITY;

        if (upwardsSpeed <= -9.18f) {
            upwardsSpeed = -9.18f;
        }
        forceToCenter.add(new Vector3(0, upwardsSpeed, 0));

        this.upwardsSpeed = 0;
        //super.increasePosition(new Vector3f(dx, upwardsSpeed, dz));
        //if (!canJump) {
        //  }
//        float terrainHeight = World.getTerrainHeight(this.getPosition().x(), this.getPosition().z())+2.4f;
//        if (this.getPosition().y() <= terrainHeight) {
//            canJump = true;
//            position.set(this.getPosition().x(), terrainHeight, this.getPosition().z());
//        }

    }

    public void jump() {
        //if (canJump) {
        this.upwardsSpeed = 2;
        canJump = false;
        // }
    }


    public int getHealth() {
        return health;
    }

    public float getSprintTime() {
        return (float) sprintTime / 5;
    }


    public PlayerInventory getInventory() {
        return inventory;
    }

    public void setInventory(PlayerInventory playerInventory) {
        this.inventory = playerInventory;
    }
}

