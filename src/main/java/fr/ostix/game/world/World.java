package fr.ostix.game.world;

import com.flowpowered.react.math.Vector3;
import fr.ostix.game.audio.AudioManager;
import fr.ostix.game.audio.SoundListener;
import fr.ostix.game.audio.SoundSource;
import fr.ostix.game.core.Input;
import fr.ostix.game.core.loader.LoadEntity;
import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.core.loader.LoadModel;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.Light;
import fr.ostix.game.entity.Player;
import fr.ostix.game.entity.camera.Camera;
import fr.ostix.game.graphics.model.MeshModel;
import fr.ostix.game.graphics.model.Model;
import fr.ostix.game.graphics.model.TextureModel;
import fr.ostix.game.graphics.render.MasterRenderer;
import fr.ostix.game.toolBox.Color;
import fr.ostix.game.toolBox.FileType;
import fr.ostix.game.world.interaction.InteractionWorld;
import fr.ostix.game.world.texture.TerrainTexture;
import fr.ostix.game.world.texture.TerrainTexturePack;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {

    private MasterRenderer renderer;
    private final InteractionWorld interactionWorld = new InteractionWorld();

    public static final int MAX_LIGHTS = 2;

    private final List<Entity> entities = new ArrayList<>();
    private final List<Light> lights = new ArrayList<>();
    private static final List<Terrain> terrains = new ArrayList<>();


    private static int[][] worldIndex;

    SoundListener listener;

    MeshModel firstModel;
    TextureModel textureModel;
    Model model;
    Entity entity;
    Player player;
    Camera cam;

    public void initWorld(Loader loader) {
        firstModel = LoadModel.loadModel("lowPolyTree.obj", loader);
        Model playerModel = new Model(LoadModel.loadModel("player/player",FileType.OBJ,loader),new TextureModel(loader.loadTexture("player")));

        textureModel = new TextureModel(loader.loadTexture("lowPolyTree"));
        textureModel.setReflectivity(0.2f);
        textureModel.setShineDamper(5);
        model = new Model(firstModel, textureModel);
        entity = new Entity(model, new Vector3f(0, getTerrainHeight(0,0), 0), new Vector3f(0, 0, 0), 1);
        entity.setSound("test");
        player = new Player(LoadEntity.loadEntity("player", FileType.OBJ));

        Light sun = new Light(new Vector3f(100000, 100000, -100000), Color.SUN);
        Light sunc = new Light(new Vector3f(-100000, 100000, 100000), Color.SUN, 0.2f);
        listener = new SoundListener(player.getPosition(), new Vector3f(),player.getRotation());
        entities.add(entity);
        cam = new Camera(player);
        entities.add(player);
        lights.add(sun);
        //  lights.add(sunc);

        initTerrain(loader);
        initEntity(loader);


        renderer = new MasterRenderer(loader);

        interactionWorld.init(1f / 60f, entities,terrains);

//        Quaternion q = Maths.angleAxisToQuaternion(90,1,1,1);
//        Quaternionf q2 = new Quaternionf(q.getX(),q.getY(),q.getZ(),q.getW());
//        Logger.err(q.toString());
//        Logger.err(q2.toString());
//        Vector3 v2 = new Vector3((float)Math.toDegrees(q.getVectorV().getX()),
//                (float)Math.toDegrees(q.getVectorV().getY()),
//                (float)Math.toDegrees(q.getVectorV().getZ()));
//        Vector3f v = new Vector3f();
//        v = q2.getEulerAnglesXYZ(v);
//        Logger.err(v2.toString());
//        Logger.err(v.toString());
//        System.err.println(entity.getTransformationMatrix().getEulerAnglesZYX(new Vector3f()).toString());
//        Quaternion q = Quaternion.fromMatrix(entity.getTransformationMatrix());
//        Matrix3f m = new Matrix3f(entity.getTransformationMatrix());
//        System.out.println(Maths.matrix3x3ToVector3f(m));

        SoundSource back = AudioManager.loadSound("ambiant",1,100,1000);
        back.setGain(0.2f);
        back.setPosition(new Vector3f(0,0,0));
        back.setLooping(true);
        back.play();

    }

    private void initEntity(Loader loader) {

       // Model treeModel = new Model(LoadModel.loadModel("tree",".dae", loader), new TextureModel(loader.loadTexture("tree")).setTransparency(true));
//        Model grassModel = new Model(LoadModel.loadModel("grassModel", loader),
//                new TextureModel(loader.loadTexture("flower")));
//        grassModel.getModelTexture().setTransparency(true).setUseFakeLighting(true);
//        Model fernModel = new Model(LoadModel.loadModel("fern", loader),
//                new TextureModel(loader.loadTexture("fern")));
//        fernModel.getModelTexture().setTransparency(true);
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
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("terrain/grassy2").getId());
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("terrain/mud").getId());
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("terrain/grassFlowers").getId());
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("terrain/path").getId());

        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("terrain/blendMap").getId());

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
        processInteraction();
        listener.updateTransform(player.getPosition(), player.getRotation());

        if (Input.keys[GLFW.GLFW_KEY_P]){
            entity.playSound();
        }
    }

    private void processInteraction() {

        interactionWorld.update(player);
    }


    public void render() {
        cam.move();
        renderer.renderScene(entities, terrains, lights, cam);
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

    public void cleanUp() {
        interactionWorld.finish();
        renderer.cleanUp();
    }
}
