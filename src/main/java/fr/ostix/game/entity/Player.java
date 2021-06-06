package fr.ostix.game.entity;

import com.flowpowered.react.math.Vector3;
import fr.ostix.game.core.Input;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.world.World;
import org.joml.Vector3f;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {

    private final Vector3 forceToCenter = new Vector3();
    private final Vector3 torque = new Vector3();

    private static final float RUN_SPEED = 160;
    private static final float TURN_SPEED = 780;
    public static final float GRAVITY = 100f;
    private static final float JUMP_POWER = 1000;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    public boolean canJump = true;

    private final int health = 10;
    private final int sprintTime = 60;
    private final boolean isSprinting = false;

    public Player(Entity e) {
        super(e.getModel(),e.position,e.rotation,e.getScale(),e.boundingModels);
        this.boundingModels = e.boundingModels;
        this.useBondingModels = e.useBondingModels;
        this.canMove = true;
    }

    public Player(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
        this.canMove = true;
    }


    public void move() {
        checkInputs();
        super.increaseRotation(new Vector3f(0, this.currentTurnSpeed * 0.0023f, 0));
        float distance = currentSpeed * 0.006f;
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotation().y())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotation().y())));
        forceToCenter.set(new Vector3(dx, 0, dz));
    //    upwardsSpeed += GRAVITY;
        if (upwardsSpeed <= 0f){
            canJump = true;
            upwardsSpeed = 0f;
        }
        forceToCenter.add(new Vector3(0, upwardsSpeed , 0));
        if (!canJump){
            this.upwardsSpeed -= GRAVITY;
        }

    }

    private void jump() {
        if (canJump) {
            this.upwardsSpeed = JUMP_POWER;
            canJump = false;
        }
    }

    private void checkInputs() {
        if (Input.keys[GLFW_KEY_W] || Input.keys[GLFW_KEY_UP]) {
            this.currentSpeed = RUN_SPEED;
        } else if (Input.keys[GLFW_KEY_S] || Input.keys[GLFW_KEY_DOWN]) {
            this.currentSpeed = -RUN_SPEED;
        } else {
            this.currentSpeed = 0;
        }

        if (Input.keys[GLFW_KEY_A] || Input.keys[GLFW_KEY_LEFT]) {
            this.currentTurnSpeed = TURN_SPEED;
        } else if (Input.keys[GLFW_KEY_D] || Input.keys[GLFW_KEY_RIGHT]) {
            this.currentTurnSpeed = -TURN_SPEED;
        } else {
            this.currentTurnSpeed = 0;
        }

        if (Input.keys[GLFW_KEY_SPACE]) {
            this.jump();
        }
        if (Input.keys[GLFW_KEY_LEFT_SHIFT]){
            this.upwardsSpeed -= RUN_SPEED;
        }
    }

    public Vector3 getForceToCenter() {
        return forceToCenter;
    }

    public int getHealth() {
        return health;
    }

    public int getSprintTime() {
        return sprintTime;
    }

    public Vector3 getTorque() {
        return torque.multiply(100);
    }

}

