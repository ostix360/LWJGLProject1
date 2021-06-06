package fr.ostix.game.entity;


import fr.ostix.game.audio.AudioManager;
import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.graphics.model.Model;
import org.joml.Vector3f;
import org.lwjgl.openal.AL11;


public class Entity {

    private final Model model;
    protected Vector3f position;
    protected Vector3f rotation;
    protected float scale;
    protected BoundingModel[] boundingModels;
    protected SoundSource sound;

    protected boolean useBondingModels;
    protected boolean canMove = false;
    private Transform transform;

    public Entity(Model model, Vector3f position, Vector3f rotation, float scale, BoundingModel[] boundingModels) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.boundingModels = boundingModels;
        useBondingModels = true;
    }

    public Entity(Model model, Vector3f position, Vector3f rotation, float scale) {
        this.model = model;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }



    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void increasePosition(Vector3f value) {
        position.add(value);
    }

    public void increaseRotation(Vector3f value) {
        rotation.add(value);
        transform.setRotation(rotation);
    }

    public boolean isUseBondingModels() {
        return useBondingModels;
    }

    public Entity setUseBondingModels(boolean useBondingModels) {
        this.useBondingModels = useBondingModels;
        return this;
    }


    public void playSound(){
        if (!sound.isPlaying()){
            sound.play();
        }
    }

    public void setSound(String sound) {
       this.sound = AudioManager.loadSound(sound,2f,20,40);
       this.sound.setGain(1);
       this.sound.setPosition(getPosition());
       this.sound.setSpeed(new Vector3f());
    }

    public BoundingModel[] getBoundingModels() {
        return boundingModels;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }


    public Transform getTransform() {
        return transform = new Transform(position, rotation,scale);
    }


    public boolean canMove() {
        return canMove;
    }
}
