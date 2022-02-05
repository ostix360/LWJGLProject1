package fr.ostix.game.world.interaction;

import com.flowpowered.react.body.*;
import com.flowpowered.react.collision.shape.*;
import com.flowpowered.react.engine.*;
import com.flowpowered.react.math.Quaternion;
import com.flowpowered.react.math.Transform;
import com.flowpowered.react.math.*;
import fr.ostix.game.entity.*;
import fr.ostix.game.toolBox.*;
import fr.ostix.game.world.*;
import gnu.trove.list.*;
import gnu.trove.list.array.*;
import org.joml.Math;
import org.joml.*;

import java.util.*;

public class CollisionSystem {

    private static final Material PHYSICS_MATERIAL = Material.asUnmodifiableMaterial
            (new Material(0.0f, 1.0f));

    private final Vector3 gravity = new Vector3(0, -9.81f, 0);
    private final Map<RigidBody, Entity> playerShapes = new HashMap<>();
    private final List<Entity> interactEntity = new ArrayList<>();
    private Player player;
    private final Map<Entity, CollisionBody> shapes = new HashMap<>();
    private static final Map<CollisionBody, Entity> aabbs = new HashMap<>();
    private final TFloatList meshPositions = new TFloatArrayList();
    private final TIntList meshIndices = new TIntArrayList();
    private DynamicsWorld dynamicsWorld;

    public void init(float timeStep, List<Entity> entities) {
        dynamicsWorld = new DynamicsWorld(gravity, timeStep);
        //addImmobileBody(entities.get(0), new BoxShape(new Vector3(25, 1, 25)), 100, new Vector3(0, 1.8f, 0), Quaternion.identity()).setMaterial(PHYSICS_MATERIAL);
        addAllEntity(entities);
        dynamicsWorld.enableSleeping(false);
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
                if (e.getCollision().getProperties().canMove()) {
                    interactEntity.add(e);
                }
                for (BoundingModel b : e.getCollision().getProperties().getBoundingModels()) {
                    if (b instanceof CollisionShape) {
                        this.addBody(e, (CollisionShape) b);
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
//        BoxShape box = new BoxShape(20, 20, 20);
//        addBody(box);
        World.doAABBToRender();
    }

    public void update(List<Entity> entities) {
        boolean contain;
        for (Entity e : entities) {
            contain = false;
            for (Entity e1 : shapes.keySet()) {
                if (e1.equals(e)) {
                    contain = true;
                    break;
                }
            }
            if (!contain) {
                if (e.getCollision() != null) {
                    if (e.getCollision().getProperties().canMove()) {
                        interactEntity.add(e);
                    }
                    for (BoundingModel b : e.getCollision().getProperties().getBoundingModels()) {
                        if (b instanceof CollisionShape) {
                            this.addBody(e, (CollisionShape) b);
                        } else {
                            //...
                        }
                    }
                }
            }

        }
//        for (Entity e : shapes.keySet()) {
//            if (!entities.contains(e)) {
//                shapes.remove(e);
//            }
//        }
        updatePlayer();
        dynamicsWorld.update();
        updateInteraction();
        for (Map.Entry<RigidBody, Entity> entry : playerShapes.entrySet()) {
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
                shape.setPosition(Maths.toVector3f(transform.getPosition()).add(0, -2.4f, 0));
            }

            // if (entry.getValue() != null) shape.getTransform().setQ(q);
            float terrainHeight = World.getTerrainHeight(transform.getPosition().getX(), transform.getPosition().getZ()) + 2.4f;
            if (transform.getPosition().getY() < terrainHeight) {
                if (entry.getValue() != null) {
                    shape.setPosition(new Vector3f(transform.getPosition().getX(), terrainHeight - 2.4f, transform.getPosition().getZ()));
                }
                body.setTransform(new Transform(new Vector3(transform.getPosition().getX(), terrainHeight, transform.getPosition().getZ()), transform.getOrientation()));
            }


        }


    }
    /*
     *
     * Event avec priorité
     * Lorsque l'objet est proche on le met dans une liste et si il y a le bouton d'interaction donc onIteract General
     * on interagie sur un objet par ordre de priorité mais quelque chose on va pouvoir lui mettre un evenemment  d'action.
     *
     *
     */


    private void updateInteraction() {
        for (Entity e : interactEntity) {
            float d = player.getPosition().distance(e.getPosition());
            if (d < 100) {
                if (e.canInteract()) e.getInteractionListener().playerIsNear();
            } else if (d < 101) {
                if (e.canInteract()) e.getInteractionListener().playerGone();
            }
        }
    }

    private void updatePlayer() {
        for (Map.Entry<RigidBody, Entity> entry : playerShapes.entrySet()) {
            RigidBody body = entry.getKey();
            Entity shape = entry.getValue();
            body.setLinearVelocity(shape.getForceToCenter().multiply(30));//new Vector3(shape.getForceToCenter().getX(), shape.getForceToCenter().getY(), shape.getForceToCenter().getZ()));
            //body.setAngularVelocity(shape.getTorque().multiply(30));
        }
    }

    public void finish() {
        dynamicsWorld.stop();
        playerShapes.clear();
    }

    private void addBody(CollisionShape shape) {
        RigidBody body = dynamicsWorld.createRigidBody(new Transform(new Vector3(50, 0, 0),
                        Quaternion.identity()),
                (float) 100, shape);

        body.enableMotion(false);
        //body.enableMotion(true);
        body.enableGravity(true);
        body.enableCollision(true);
        body.setMaterial(PHYSICS_MATERIAL);
        addBody(body, null);
    }

    private void addBody(Entity e, CollisionShape shape) {
        fr.ostix.game.entity.Transform trans = shape.getTransform();

        final Vector3 pos = Maths.toVector3(e.getPosition().add(trans.getPosition(), new Vector3f()).add(shape.applyCorrection()));//.sub(e.getScale().div(6,new Vector3f())));
        shape.scale(e.getScale().mul(1.0f, new Vector3f()));
        AxisAngle4d angles = new AxisAngle4d();
        Quaternionf q = new Quaternionf();
        q.rotateLocalY(Math.toRadians(e.getRotation().y() * 2));
        trans.getTransformation().getRotation(angles);
        Quaternionf q2 = new Quaternionf(angles);
        q.add(q2);
        Transform theTransform = new Transform(pos, new Quaternion(q.x(), q.y(), q.z(), q.w()));
        e.getTransform().getTransformation().getUnnormalizedRotation(q);
        RigidBody body = dynamicsWorld.createRigidBody(theTransform,
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
            playerShapes.put((RigidBody) body, e);
            this.player = (Player) e;
        }
        shapes.put(e, body);
        AABB aabb = body.getAABB();
        Transform bodyTransform = body.getTransform();
        Vector3 bodyPosition = bodyTransform.getPosition();
        Entity aabbModel = World.addAABB(bodyPosition, Vector3.subtract(aabb.getMax(), aabb.getMin()).divide(6));
        aabbs.put(body, aabbModel);
    }

}
