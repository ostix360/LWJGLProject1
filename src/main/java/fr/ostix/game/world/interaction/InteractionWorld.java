package fr.ostix.game.world.interaction;


import com.flowpowered.caustic.api.util.MeshGenerator;
import com.flowpowered.react.body.CollisionBody;
import com.flowpowered.react.body.RigidBody;
import com.flowpowered.react.collision.shape.BoxShape;
import com.flowpowered.react.collision.shape.CollisionShape;
import com.flowpowered.react.collision.shape.ConvexMeshShape;
import com.flowpowered.react.engine.DynamicsWorld;
import com.flowpowered.react.engine.Material;
import com.flowpowered.react.math.Quaternion;
import com.flowpowered.react.math.Transform;
import com.flowpowered.react.math.Vector3;
import fr.ostix.game.entity.Entity;
import fr.ostix.game.entity.Player;
import fr.ostix.game.toolBox.Maths;
import fr.ostix.game.world.Terrain;
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

public class InteractionWorld {

    private static final Material PHYSICS_MATERIAL = Material.asUnmodifiableMaterial(
            new Material(0.001f, 0.99f));


    private DynamicsWorld dynamicsWorld;
    private final Vector3 gravity = new Vector3(0, -9.81f, 0);

    private final Map<CollisionBody, Entity> shapes = new HashMap<>();
    private final Map<RigidBody, Player> playerShape = new HashMap<>();
    private final TFloatList meshPositions = new TFloatArrayList();
    private final TIntList meshIndices = new TIntArrayList();

    public void init(float timeStep, List<Entity> entities, List<Terrain> terrains) {
        dynamicsWorld = new DynamicsWorld(gravity, timeStep);
        //addImmobileBody(entities.get(0), new BoxShape(new Vector3(25, 1, 25)), 100, new Vector3(0, 1.8f, 0), Quaternion.identity()).setMaterial(PHYSICS_MATERIAL);
        addAllEntity(entities);
        // addTerrain(terrains);
        dynamicsWorld.start();
    }

    private void addTerrain(List<Terrain> terrains) {
        for (Terrain t : terrains) {
            meshPositions.addAll(t.getModel().getVAO().getPosition());
            meshIndices.addAll(t.getModel().getVAO().getIndices());
            TFloatList positions = new TFloatArrayList(meshPositions);
            TIntList indices = new TIntArrayList(meshIndices);
            MeshGenerator.toWireframe(positions, indices, false);
            final ConvexMeshShape meshShape = new ConvexMeshShape(positions.toArray(), positions.size() / 3, 12);
            for (int i = 0; i < indices.size(); i += 2) {
                meshShape.addEdge(indices.get(i), indices.get(i + 1));
            }
            meshShape.setIsEdgesInformationUsed(true);
            addTerrain(t, meshShape);
            meshPositions.clear();
            meshIndices.clear();
        }
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
 //           } else {
                for (int i = 0; i < e.getModel().getMeshModel().getVAO().getPosition().length; i++) {
                    meshPositions.add(e.getModel().getMeshModel().getVAO().getPosition()[i] * e.getScale());
                }
                meshIndices.addAll(e.getModel().getMeshModel().getVAO().getIndices());
                TFloatList positions = new TFloatArrayList(meshPositions);
                TIntList indices = new TIntArrayList(meshIndices);
                MeshGenerator.toWireframe(positions, indices, false);
                final ConvexMeshShape meshShape = new ConvexMeshShape(positions.toArray(), positions.size() / 3, 12);
                for (int i = 0; i < indices.size(); i += 2) {
                    meshShape.addEdge(indices.get(i), indices.get(i + 1));
                }
                meshShape.setIsEdgesInformationUsed(true);
                addBody(e, meshShape, 100);

                meshPositions.clear();
                meshIndices.clear();
            }

        }
        BoxShape box = new BoxShape(20, 9, 20);
        //addBody(box);
//    }

    public void update(Player player) {
        dynamicsWorld.update();
        player.move();
        updatePlayer();
        for (Map.Entry<CollisionBody, Entity> entry : shapes.entrySet()) {
            final CollisionBody body = entry.getKey();
            Entity shape = null;
            if (entry.getValue() != null) shape = entry.getValue();
            Transform transform = body.getInterpolatedTransform();
            Quaternionf q = new Quaternionf(transform.getOrientation().getX(),
                    transform.getOrientation().getY(),
                    transform.getOrientation().getZ(),
                    transform.getOrientation().getW());
            if (entry.getValue() != null) {
                assert shape != null;
                shape.setPosition(Maths.toVector3f(transform.getPosition()));
            }

            if (entry.getValue() != null) shape.getTransform().setQ(q);
            float terrainHeight = World.getTerrainHeight(transform.getPosition().getX(), transform.getPosition().getZ());
            if (transform.getPosition().getY() < terrainHeight) {
                if (entry.getValue() != null) {
                    assert shape != null;
                    shape.setPosition(new Vector3f(transform.getPosition().getX(), terrainHeight, transform.getPosition().getZ()));
                }
                body.setTransform(new Transform(new Vector3(transform.getPosition().getX(), terrainHeight, transform.getPosition().getZ()), transform.getOrientation()));
            }


        }


    }

    private void updatePlayer() {
        for (Map.Entry<RigidBody, Player> entry : playerShape.entrySet()) {
            RigidBody body = entry.getKey();
            Player shape = entry.getValue();
            body.applyForceToCenter(new Vector3(0, shape.getForceToCenter().getY(), 0).multiply(500));
            body.setLinearVelocity(new Vector3(shape.getForceToCenter().multiply(30).getX(), -9.81f, shape.getForceToCenter().getZ()));
            body.setAngularVelocity(shape.getTorque());
        }
    }

    public void finish() {
        dynamicsWorld.disperseCache();
        dynamicsWorld.stop();
        shapes.clear();
    }

    private void addBody(Entity e, CollisionShape shape, float mass) {
        AxisAngle4d angles = new AxisAngle4d();
        e.getTransform().getTransformation().getRotation(angles);
        Quaternionf q = new Quaternionf(angles);
        RigidBody body = dynamicsWorld.createRigidBody(new Transform(
                        new Vector3(e.getPosition().x(), e.getPosition().y(), e.getPosition().z()),
                        new Quaternion(q.x(), q.y(), q.z(), q.w())),
                mass, shape);
//        RigidBody body = dynamicsWorld.createRigidBody(new Transform(
//                new Vector3(0,0,0),
//        new Quaternion(0,0,0,1)),10,shape);
        body.enableMotion(true);
        body.enableGravity(true);
        body.enableCollision(true);
        body.setMaterial(PHYSICS_MATERIAL);
        addBody(body, e);
    }

//    private void addBody(Entity e, CollisionShape shape, float mass, int index) {
//        AxisAngle4d angles = new AxisAngle4d();
//        Matrix4f m = e.getTransform().getTransformation();
//        m.rotateXYZ(e.getBoundingModels()[index].getRot());
//        m.getRotation(angles);
//        Quaternionf q = new Quaternionf(angles);
//        RigidBody body = dynamicsWorld.createRigidBody(new Transform(
//                        new Vector3(e.getPosition().x() + e.getBoundingModels()[index].getPos().x(),
//                                e.getPosition().y() + e.getBoundingModels()[index].getPos().y(),
//                                e.getPosition().z() + e.getBoundingModels()[index].getPos().z()),
//                        new Quaternion(q.x(), q.y(), q.z(), q.w())),
//                            mass, shape);
////        RigidBody body = dynamicsWorld.createRigidBody(new Transform(
////                new Vector3(0,0,0),
////        new Quaternion(0,0,0,1)),10,shape);
//        body.enableMotion(e.canMove());
//        body.enableGravity(true);
//        body.enableCollision(true);
//        body.setMaterial(PHYSICS_MATERIAL);
//        addBody(body, e);
//    }

    private void addTerrain(Terrain t, CollisionShape shape) {
        RigidBody body = dynamicsWorld.createRigidBody(new Transform(new Vector3(t.getX(), 0, t.getZ()),
                new Quaternion(0, 0, 0, 1)), 1, shape);
        body.enableMotion(false);
        body.enableGravity(false);
        body.enableCollision(true);
        body.setMaterial(PHYSICS_MATERIAL);
        addBody(body, null);
    }

    private void addBody(CollisionShape shape) {
        RigidBody body = dynamicsWorld.createRigidBody(new Transform(new Vector3(55, -1, 55),
                new Quaternion(0, 0, 0, 1)), 100, shape);
        body.enableMotion(false);
        body.enableGravity(false);
        body.enableCollision(true);
        body.setMaterial(PHYSICS_MATERIAL);
        addBody(body, null);
    }


    private void addBody(CollisionBody body, Entity e) {
        if (e instanceof Player) {
            playerShape.put((RigidBody) body, (Player) e);
        }
        shapes.put(body, e);
    }
}
