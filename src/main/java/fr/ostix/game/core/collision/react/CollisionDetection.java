/*
 * This file is part of React, licensed under the MIT License (MIT).
 *
 * Copyright (c) 2013 Flow Powered <https://flowpowered.com/>
 * Original ReactPhysics3D C++ library by Daniel Chappuis <http://danielchappuis.ch>
 * React is re-licensed with permission from ReactPhysics3D author.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.ostix.game.core.collision.react;


import fr.ostix.game.core.collision.react.body.CollisionBody;
import fr.ostix.game.core.collision.react.body.RigidBody;
import fr.ostix.game.core.collision.react.broadphase.BroadPhaseAlgorithm;
import fr.ostix.game.core.collision.react.broadphase.PairManager.BodyPair;
import fr.ostix.game.core.collision.react.broadphase.SweepAndPruneAlgorithm;
import fr.ostix.game.core.collision.react.constraint.ContactPoint.ContactPointInfo;
import fr.ostix.game.core.collision.react.maths.Utilities.IntPair;
import fr.ostix.game.core.collision.react.narrowphase.GJK.GJKAlgorithm;
import fr.ostix.game.core.collision.react.narrowphase.NarrowPhaseAlgorithm;
import fr.ostix.game.core.collision.react.narrowphase.SphereVsSphereAlgorithm;
import fr.ostix.game.core.collision.react.shape.CollisionShape;
import fr.ostix.game.core.collision.react.shape.CollisionShape.CollisionShapeType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class computes the collision detection algorithms. We first perform a broad-phase algorithm to know which pairs of bodies can collide and then we run a narrow-phase algorithm to compute the
 * collision contacts between the bodies.
 */
public class CollisionDetection {
    private final CollisionWorld mWorld;
    private final Map<IntPair, BroadPhasePair> mOverlappingPairs = new HashMap<>();
    private final BroadPhaseAlgorithm mBroadPhaseAlgorithm;
    private final GJKAlgorithm mNarrowPhaseGJKAlgorithm = new GJKAlgorithm();
    private final SphereVsSphereAlgorithm mNarrowPhaseSphereVsSphereAlgorithm = new SphereVsSphereAlgorithm();
    private final Set<IntPair> mNoCollisionPairs = new HashSet<>();

    /**
     * Constructs a new collision detection from the collision world.
     *
     * @param world The world
     */
    public CollisionDetection(CollisionWorld world) {
        mWorld = world;
        mBroadPhaseAlgorithm = new SweepAndPruneAlgorithm(this);
    }

    /**
     * Adds a body to the collision detection.
     *
     * @param body The body to add
     */
    public void addBody(CollisionBody body) {
        mBroadPhaseAlgorithm.addObject(body, body.getAABB());
    }

    /**
     * Removes a body from the collision detection.
     *
     * @param body The body to remove
     */
    public void removeBody(CollisionBody body) {
        mBroadPhaseAlgorithm.removeObject(body);
    }

    /**
     * Add a pair of bodies that cannot collide with each other.
     *
     * @param body1 The first body
     * @param body2 The second body
     */
    public void addNoCollisionPair(CollisionBody body1, CollisionBody body2) {
        mNoCollisionPairs.add(BroadPhasePair.computeBodiesIndexPair(body1, body2));
    }

    /**
     * Removes a pair of bodies that cannot collide with each other.
     *
     * @param body1 The first body
     * @param body2 The second body
     */
    public void removeNoCollisionPair(CollisionBody body1, CollisionBody body2) {
        mNoCollisionPairs.remove(BroadPhasePair.computeBodiesIndexPair(body1, body2));
    }

    /**
     * Computes the collision detection.
     */
    public void computeCollisionDetection() {

        computeBroadPhase();
        computeNarrowPhase();
    }


    // Computes the broad-phase collision detection.
    private void computeBroadPhase() {
        for (CollisionBody body : mWorld.getBodies()) {
            if (body.getHasMoved()) {
                mBroadPhaseAlgorithm.updateObject(body, body.getAABB());
            }
        }
    }

    // Computes the narrow-phase collision detection.
    private void computeNarrowPhase() {
        for (Entry<IntPair, BroadPhasePair> entry : mOverlappingPairs.entrySet()) {
            final ContactPointInfo contactInfo = new ContactPointInfo();
            final BroadPhasePair pair = entry.getValue();
            if (pair == null) {
                throw new IllegalStateException("pair cannot be null");
            }
            final CollisionBody body1 = pair.getFirstBody();
            final CollisionBody body2 = pair.getSecondBody();
            mWorld.updateOverlappingPair(pair);
            if (mNoCollisionPairs.contains(pair.getBodiesIndexPair())) {
                continue;
            }
            if (body1.isSleeping() && body2.isSleeping()) {
                continue;
            }
            final NarrowPhaseAlgorithm narrowPhaseAlgorithm = selectNarrowPhaseAlgorithm(body1.getCollisionShape(), body2.getCollisionShape());
            narrowPhaseAlgorithm.setCurrentOverlappingPair(pair);
            if (narrowPhaseAlgorithm.testCollision(body1.getCollisionShape(), body1.getTransform(), body2.getCollisionShape(), body2.getTransform(), contactInfo)) {
                contactInfo.setFirstBody((RigidBody) body1);
                contactInfo.setSecondBody((RigidBody) body2);
                mWorld.notifyNewContact(pair, contactInfo);
            }
        }
    }

    /**
     * Allows the broad phase to notify the collision detection about an overlapping pair. This method is called by a broad-phase collision detection algorithm.
     *
     * @param addedPair The pair that was added
     */
    public void broadPhaseNotifyAddedOverlappingPair(BodyPair addedPair) {
        final IntPair indexPair = addedPair.getBodiesIndexPair();
        final BroadPhasePair broadPhasePair = new BroadPhasePair(addedPair.getFirstBody(), addedPair.getSecondBody());
        final BroadPhasePair old = mOverlappingPairs.put(indexPair, broadPhasePair);
        if (old != null) {
            throw new IllegalStateException("the pair already existed in the overlapping pairs map");
        }
        mWorld.notifyAddedOverlappingPair(broadPhasePair);
    }

    /**
     * Allows the broad phase to notify the collision detection about a removed overlapping pair.
     *
     * @param removedPair The pair that was removed
     */
    public void broadPhaseNotifyRemovedOverlappingPair(BodyPair removedPair) {
        final IntPair indexPair = removedPair.getBodiesIndexPair();
        final BroadPhasePair broadPhasePair = mOverlappingPairs.get(indexPair);
        if (broadPhasePair == null) {
            throw new IllegalStateException("the removed pair must be in the map");
        }
        mWorld.notifyRemovedOverlappingPair(broadPhasePair);
        mOverlappingPairs.remove(indexPair);
    }

    // Selects the narrow-phase collision algorithm to use given two collision shapes.
    private NarrowPhaseAlgorithm selectNarrowPhaseAlgorithm(CollisionShape collisionShape1, CollisionShape collisionShape2) {
        if (collisionShape1.getType() == CollisionShapeType.SPHERE && collisionShape2.getType() == CollisionShapeType.SPHERE) {
            return mNarrowPhaseSphereVsSphereAlgorithm;
        } else {
            return mNarrowPhaseGJKAlgorithm;
        }
    }
}
