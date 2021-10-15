package fr.ostix.game.world;

import fr.ostix.game.audio.SoundListener;
import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.core.Game;
import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.resources.ResourcePack;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.Light;
import fr.ostix.game.entity.Player;
import fr.ostix.game.entity.animated.animation.animatedModel.AnimatedModel;
import fr.ostix.game.entity.camera.Camera;
import fr.ostix.game.entity.component.ComponentType;
import fr.ostix.game.entity.component.ai.AIProperties;
import fr.ostix.game.entity.component.animation.AnimationComponent;
import fr.ostix.game.entity.component.collision.CollisionComponent;
import fr.ostix.game.graphics.font.meshCreator.GUIText;
import fr.ostix.game.graphics.font.rendering.MasterFont;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.model.Texture;
import fr.ostix.game.graphics.particles.*;
import fr.ostix.game.graphics.particles.particleSpawn.Sphere;
import fr.ostix.game.graphics.render.MasterRenderer;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.world.interaction.CollisionSystem;
import fr.ostix.game.world.texture.TerrainTexture;
import fr.ostix.game.world.texture.TerrainTexturePack;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.openal.AL10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class World {

    private MasterRenderer renderer;

    private boolean isInit = false;

    public static final int MAX_LIGHTS = 2;

    private final CollisionSystem collision = new CollisionSystem();

    private HashMap<String, Texture> textures;
    private HashMap<String, SoundSource> sounds;
    private HashMap<String, Model> models;

    private final List<Entity> entities = new ArrayList<>();
    private final List<Light> lights = new ArrayList<>();
    private static final List<Terrain> terrains = new ArrayList<>();

    private static int[][] worldIndex;

    SoundListener listener;
    private Player player;
    Camera cam;

    public World() {
    }



    public void initWorld(Loader loader, ResourcePack pack) {
        this.textures = ResourcePack.getTextureByName();
        this.sounds = pack.getSoundByName();
        this.models = pack.getModelByName();

        renderer = new MasterRenderer(loader);

        MasterParticle.init(loader, MasterRenderer.getProjectionMatrix());

        AnimatedModel an = pack.getAnimatedModelByName().get("player");
        player = new Player(an, new Vector3f(55, 5, 55), new Vector3f(0), 1);
        ParticleTargetProperties targetProperties = new ParticleTargetProperties(0, 6, 0, 80, 6);
        ParticleSystem system = new ParticleSystem(new ParticleTexture(textures.get("fire").getID(), 8, true),
                360, 0.1f, -0, 60 * 2.2f, 4);
        system.randomizeRotation();
        system.setLifeError(0.2f);
        system.setDirection(new Vector3f(0, 0.1f, 0), 0.01f);
        system.setTarget(new ParticleTarget(targetProperties, player));
        system.setPositionOffset(new Vector3f(0, 4, 50));
        system.setSpeedError(0.5f);
        system.setScaleError(0.1f);
        system.setSpawn(((Sphere) SpawnParticleType.SPHERE.getSpawn()).setRadius(10));
        AIProperties ai = new AIProperties(2f, 1, 0.25f, 0.25f, 0.65f, 6, 3);
        // player.addComponent(new AIComponent(player, ai));
        // player.addComponent(new ParticleComponent(system, player));
        player.addComponent(new AnimationComponent(player, ResourcePack.getAnimationByName().get(an)));
        CollisionComponent cp = (CollisionComponent) ComponentType.COLLISION_COMPONENT.loadComponent(player, pack.getComponents().get(1705233732));
        player.setCollision(cp);
        listener = new SoundListener(player.getPosition(), new Vector3f(), player.getRotation());
        cam = new Camera(player);
        entities.add(player);

        Model box = models.get("box");
        Entity boxE = new Entity(box, new Vector3f(0, 10, 0), new Vector3f(0), 10);
        CollisionComponent cc = (CollisionComponent) ComponentType.COLLISION_COMPONENT.loadComponent(boxE, pack.getComponents().get(2026772471));
        boxE.setCollision(cc);

        entities.add(boxE);
        Light sun = new Light(new Vector3f(100000, 100000, -100000), Color.SUN);
        lights.add(sun);
        //  lights.add(sunc);

        initTerrain(loader);
        initEntity();
        GUIText text1 = new GUIText("Bienvenu dans ce jeu magique", 1f, Game.gameFont, new Vector2f(0, 1080f / 2f), 1920f, true);
        text1.setColour(Color.RED);
        MasterFont.add(text1);


        collision.init(1 / 75f, entities);

        SoundSource back = pack.getSoundByName().get("ambient");

        // SoundSource back2 = AudioManager.loadSound("test1", 1, 10, 20);


        back.setGain(0.2f);
        back.setPosition(new Vector3f(0, 0, 0));
        back.setLooping(true);
        back.setProperty(AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
        back.play();
//        back2.setGain(0.2f);
//        back2.setPosition(new Vector3f(0,0,0));
//        back2.setLooping(true);
//        back2.setProperty(AL10.AL_SOURCE_RELATIVE,AL10.AL_TRUE);
        // back2.play();
        isInit = true;
    }

    private void initEntity() {


        // Model treeModel = new Model(LoadModel.loadModel("tree",".dae", loader), new TextureModel(loader.loadTexture("tree")).setTransparency(true));
//        Model grassModel = new Model(LoadModel.loadModel("grassModel", loader),
//                new TextureModel(loader.loadTexture("flower")));
//        grassModel.getTexture().setTransparency(true).setUseFakeLighting(true);
//        Model fernModel = new Model(LoadModel.loadModel("fern", loader),
//                new TextureModel(loader.loadTexture("fern")));
//        fernModel.getTexture().setTransparency(true);
        Random r = new Random();
        for (int i = 0; i < 5; i++) {
            float x = r.nextFloat() * 400;
            float z = r.nextFloat() * 400;
//            entities.add(new Entity(treeModel, new Vector3f(x, getTerrainHeight(x, z), z),
//                    new Vector3f(0, 0, 0), 1f));
//            x = r.nextFloat() * 1600;
//            z = r.nextFloat() * 1600;
//            entities.add(new Entity(grassModel, new Vector3f(x, getTerrainHeight( x, z),z),
//                    new Vector3f(0, 0, 0), 0.6f));
//            x = r.nextFloat() * 1600;
//            z = r.nextFloat() * 1600;
//            entities.add(new Entity(fernModel, new Vector3f(x, getTerrainHeight(x, z),z),
//                    new Vector3f(0, 0, 0), 5f));
        }
    }

    private void initTerrain(Loader loader) {
        TerrainTexture backgroundTexture = new TerrainTexture(textures.get("grassy2").getID());
        TerrainTexture rTexture = new TerrainTexture(textures.get("mud").getID());
        TerrainTexture gTexture = new TerrainTexture(textures.get("grassFlowers").getID());
        TerrainTexture bTexture = new TerrainTexture(textures.get("path").getID());

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(textures.get("blendMap").getID());

        worldIndex = new int[2][2];
        int index = 0;
        for (int x = 0; x < 2; x++) {
            for (int z = 0; z < 2; z++) {
                terrains.add(new Terrain(x, z, loader, texturePack, blendMap, "heightmap"));
                worldIndex[x][z] = index;
                index++;
            }
        }

    }

    public void update() {
        //entity.increaseRotation(new Vector3f(0, 1, 0));

        for (Entity e : entities) {
            e.update();
        }
        System.out.println("----------player pos--------- " + player.getPosition());
        collision.update();



        listener.updateTransform(player.getPosition(), player.getRotation());
        MasterParticle.update(cam);

    }



    public void render() {
        cam.move();
        renderer.renderScene(entities, terrains, lights, cam);
        MasterParticle.render(cam);
    }

    public static float getTerrainHeight(float worldX, float worldZ) {
        int x = (int) Math.floor(worldX / Terrain.getSIZE());
        int z = (int) Math.floor(worldZ / Terrain.getSIZE());
        try {
            return terrains.get(worldIndex[x][z]).getHeightOfTerrain(worldX, worldZ);
        } catch (Exception e) {
            // Logger.err("World doesn't exist in this coordinates xIndex : " + x + " ZIndex : " + z);
        }
        return 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void cleanUp() {
        collision.finish();
        renderer.cleanUp();
        MasterParticle.cleanUp();
    }

    public boolean isInit() {
        return isInit;
    }
}
