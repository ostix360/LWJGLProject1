package fr.ostix.game.entity;

import com.flowpowered.react.math.*;
import fr.ostix.game.core.events.*;
import fr.ostix.game.core.events.keyEvent.*;
import fr.ostix.game.graphics.model.*;
import org.joml.*;

import java.lang.Math;

import static org.lwjgl.glfw.GLFW.*;

public class Player extends Entity {

    private static final float RUN_SPEED = 160;
    private static final float TURN_SPEED = 780;
    public static final float GRAVITY = 0.12f;
    private static final float JUMP_POWER = 2;

    private float currentSpeed = 0;
    private float currentTurnSpeed = 0;
    private float upwardsSpeed = 0;

    public boolean canJump = true;

    private final int health = 10;
    private int sprintTime = 60 * 5;
    private final boolean isSprinting = false;


    public Player(Model model, Vector3f position, Vector3f rotation, float scale) {
        super(model, position, rotation, scale);
        setInputListener();
    }

    private void setInputListener() {
        EventManager.getInstance().addListener(new InteractionKeyListener() {
            @EventHandler
            @Override
            public void onKeyPressed(KeyPressedEvent e) {

            }

            @EventHandler
            @Override
            public void onKeyReleased(KeyReleasedEvent e) {

            }

            @EventHandler
            @Override
            public void onKeyMaintained(KeyMaintainedEvent e) {
                if (e.getKEY() == GLFW_KEY_W || e.getKEY() == GLFW_KEY_UP) {
                    movement = MovementType.FORWARD;
                    currentSpeed = RUN_SPEED;
                } else if (e.getKEY() == GLFW_KEY_S || e.getKEY() == GLFW_KEY_DOWN) {
                    movement = MovementType.BACK;
                    currentSpeed = -RUN_SPEED;
                } else {
                    currentSpeed = 0;
                }

                if (e.getKEY() == GLFW_KEY_A || e.getKEY() == GLFW_KEY_LEFT) {
                    currentTurnSpeed = TURN_SPEED;
                } else if (e.getKEY() == GLFW_KEY_D || e.getKEY() == GLFW_KEY_RIGHT) {
                    currentTurnSpeed = -TURN_SPEED;
                } else {
                    currentTurnSpeed = 0;
                }

                if (e.getKEY() == GLFW_KEY_SPACE) {
                    movement = MovementType.JUMP;
                    jump();
                }
                if (e.getKEY() == GLFW_KEY_LEFT_SHIFT) {
                    upwardsSpeed = -2;
                }
            }
        });
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
        if (currentSpeed == 0 && currentTurnSpeed == 0) {
            this.movement = MovementType.STATIC;
        }
        super.increaseRotation(new Vector3f(0, this.currentTurnSpeed * 0.0023f, 0));
        torque.set(new Vector3(0, this.currentTurnSpeed, 0));
        float distance = currentSpeed * 0.006f;
        float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotation().y())));
        float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotation().y())));
        forceToCenter.set(new Vector3(dx, 0, dz));
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

    private void jump() {
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


}

