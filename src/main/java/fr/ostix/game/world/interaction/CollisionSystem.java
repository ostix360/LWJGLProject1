package fr.ostix.game.world.interaction;

import com.flowpowered.react.body.CollisionBody;
import com.flowpowered.react.body.RigidBody;
import com.flowpowered.react.collision.shape.BoxShape;
import com.flowpowered.react.collision.shape.CollisionShape;
import com.flowpowered.react.engine.DynamicsWorld;
import com.flowpowered.react.engine.Material;
import com.flowpowered.react.math.Quaternion;
import com.flowpowered.react.math.Transform;
import com.flowpowered.react.math.Vector3;
import fr.ostix.game.entity.BoundingModel;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.Player;
import fr.ostix.game.toolBox.Maths;
import fr.ostix.game.world.World;
import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import org.joml.AxisAngle4d;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollisionSystem {

    private static final Material PHYSICS_MATERIAL = Material.asUnmodifiableMaterial
            (new Material(0.2f, 0.8f));
    private final Vector3 gravity = new Vector3(0, -9.81f, 0);
    private final Map<CollisionBody, Entity> shapes = new HashMap<>();
    private final Map<RigidBody, Entity> motionShape = new HashMap<>();
    private final TFloatList meshPositions = new TFloatArrayList();
    private final TIntList meshIndices = new TIntArrayList();
    private DynamicsWorld dynamicsWorld;

    public void init(float timeStep, List<Entity> entities) {
        dynamicsWorld = new DynamicsWorld(gravity, timeStep);
        //addImmobileBody(entities.get(0), new BoxShape(new Vector3(25, 1, 25)), 100, new Vector3(0, 1.8f, 0), Quaternion.identity()).setMaterial(PHYSICS_MATERIAL);
        addAllEntity(entities);
        // addTerrain(terrains);
        dynamicsWorld.start();
    }


    private void addAllEntity(List<Entity> entities) {
        for (Entity e : entities) {
//            if (e.isUseBondingModels()) {
//                int index = 0;
//                for (BoundingModel b : e.getBoundingModels()) {
//                    for (int i = 0; i < b.getModel().getVAO().getPosition().length; i++) {
//                        meshPositions.add(b.getModel().getVAO().getPosition()[i] * b.getScale());
//                    }
//                    meshIndices.addAll(b.getModel().getVAO().getIndices());
//                    TFloatList positions = new TFloatArrayList(meshPositions);
//                    TIntList indices = new TIntArrayList(meshIndices);
//                    MeshGenerator.toWireframe(positions, indices, false);
//                    final ConvexMeshShape meshShape = new ConvexMeshShape(positions.toArray(), positions.size() / 3, 12);
//                    for (int i = 0; i < indices.size(); i += 2) {
//                        meshShape.addEdge(indices.get(i), indices.get(i + 1));
//                    }
//                    meshShape.setIsEdgesInformationUsed(true);
//                    addBody(e, meshShape, 100, index);
//
//                    meshPositions.clear();
//                    meshIndices.clear();
//                    index++;
//                }
//                       } else {
            if (e.getCollision() != null) {
                for (BoundingModel b : e.getCollision().getProperties().getBoundingModels()) {
                    if (b instanceof CollisionShape) {
                        Transform t = new Transform(b.getTransform());
                        this.addBody(e, (CollisionShape) b, t);
                    } else {
                        //...
                    }
                }
            }

//            for (int i = 0; i < e.getModel().getMeshModel().getVAO().getPosition().length; i++) {
//                meshPositions.add(e.getModel().getMeshModel().getVAO().getPosition()[i] * e.getScale());
//            }
//            meshIndices.addAll(e.getModel().getMeshModel().getVAO().getIndices());
//            TFloatList positions = new TFloatArrayList(meshPositions);
//            TIntList indices = new TIntArrayList(meshIndices);
//            MeshGenerator.toWireframe(positions, indices, false);
//            final ConvexMeshShape meshShape = new ConvexMeshShape(positions.toArray(), positions.size() / 3, 12);
//            for (int i = 0; i < indices.size(); i += 2) {
//                meshShape.addEdge(indices.get(i), indices.get(i + 1));
//            }
//            meshShape.setIsEdgesInformationUsed(true);
//            addBody(e, meshShape, 100);
//
//            meshPositions.clear();
//            meshIndices.clear();
        }
        BoxShape box = new BoxShape(20, 20, 20);
        addBody(box);
    }

    public void update() {
        dynamicsWorld.update();
        updatePlayer();
        for (Map.Entry<RigidBody, Entity> entry : motionShape.entrySet()) {
            final CollisionBody body = entry.getKey();
            Entity shape = null;

            if (entry.getValue() != null) shape = entry.getValue();
            Transform transform = body.getInterpolatedTransform();
            Quaternionf q = new Quaternionf(transform.getOrientation().getX(),
                    transform.getOrientation().getY(),
                    transform.getOrientation().getZ(),
                    transform.getOrientation().getW());
            assert shape != null;
            //   q.getEulerAnglesXYZ(shape.getRotation());
            if (entry.getValue() != null) {
                shape.setPosition(Maths.toVector3f(transform.getPosition()));
            }

           // if (entry.getValue() != null) shape.getTransform().setQ(q);
            float terrainHeight = World.getTerrainHeight(transform.getPosition().getX(), transform.getPosition().getZ());
            if (transform.getPosition().getY() < terrainHeight) {
                if (entry.getValue() != null) {
                    shape.setPosition(new Vector3f(transform.getPosition().getX(), terrainHeight, transform.getPosition().getZ()));
                }
                body.setTransform(new Transform(new Vector3(transform.getPosition().getX(), terrainHeight, transform.getPosition().getZ()), transform.getOrientation()));
            }


        }


    }

    private void updatePlayer() {
        for (Map.Entry<RigidBody, Entity> entry : motionShape.entrySet()) {
            RigidBody body = entry.getKey();
            Entity shape = entry.getValue();
            body.setLinearVelocity(shape.getForceToCenter().multiply(10));//new Vector3(shape.getForceToCenter().getX(), shape.getForceToCenter().getY(), shape.getForceToCenter().getZ()));
            body.setAngularVelocity(shape.getTorque().multiply(30));
        }
    }

    public void finish() {
        dynamicsWorld.stop();
        shapes.clear();
    }

    private void addBody(CollisionShape shape) {
        RigidBody body = dynamicsWorld.createRigidBody(new Transform(new Vector3(50, 00, 0),
                        Quaternion.identity()),
                (float) 100, shape);

        body.enableMotion(false);
        //body.enableMotion(true);
        body.enableGravity(true);
        body.enableCollision(true);
        body.setMaterial(PHYSICS_MATERIAL);
        addBody(body, null);
    }

    private void addBody(Entity e, CollisionShape shape, Transform transform) {
        AxisAngle4d angles = new AxisAngle4d();
        e.getTransform().getTransformation().getRotation(angles);
        Quaternionf q = new Quaternionf(angles);
        Transform tra = new Transform(
                new Vector3(e.getPosition().x(), e.getPosition().y(), e.getPosition().z()),
                new Quaternion(q.x(), q.y(), q.z(), q.w()));

        RigidBody body = dynamicsWorld.createRigidBody(Transform.multiply(tra, transform),
                (float) 1, shape);

        body.enableMotion(e.getCollision().getProperties().canMove());
        //body.enableMotion(true);
        body.enableGravity(true);
        body.enableCollision(true);
        body.setMaterial(PHYSICS_MATERIAL);
        addBody(body, e);
    }
    private void addBody(CollisionBody body, Entity e) {
        if (body.isMotionEnabled() && e instanceof Player) {
            motionShape.put((RigidBody) body, e);
        }
        shapes.put(body, e);
    }

}
