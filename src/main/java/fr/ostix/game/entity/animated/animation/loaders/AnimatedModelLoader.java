package fr.ostix.game.entity.animated.animation.loaders;


import fr.ostix.game.core.loader.Loader;
import fr.ostix.game.entity.animated.animation.animatedModel.AnimatedModel;
import fr.ostix.game.entity.animated.animation.animatedModel.Joint;
import fr.ostix.game.entity.animated.colladaParser.colladaLoader.ColladaLoader;
import fr.ostix.game.entity.animated.colladaParser.dataStructures.AnimatedModelData;
import fr.ostix.game.entity.animated.colladaParser.dataStructures.JointData;
import fr.ostix.game.entity.animated.colladaParser.dataStructures.MeshData;
import fr.ostix.game.entity.animated.colladaParser.dataStructures.SkeletonData;
import fr.ostix.game.graphics.model.MeshModel;
import fr.ostix.game.graphics.model.Texture;
import fr.ostix.game.toolBox.ToolDirectory;
import org.joml.Vector3f;

public class AnimatedModelLoader {

    /**
     * Creates an AnimatedEntity from the data in an entity file. It loads up
     * the collada model data, stores the extracted data in a VAO, sets up the
     * joint heirarchy, and loads up the entity's texture.
     *
     * @param modelFile - the file containing the data for the entity.
     * @return The animated entity (no animation applied though)
     */
    public static AnimatedModel loadEntity(String modelFile, Texture texture, int maxWeights,Loader loader) {
        AnimatedModelData entityData = ColladaLoader.loadColladaModel(ToolDirectory.RES_FOLDER + "/model/"+modelFile + ".dae",maxWeights);
        MeshModel model = createVao(entityData.getMeshData(),loader);
        SkeletonData skeletonData = entityData.getJointsData();
        Joint headJoint = createJoints(skeletonData.headJoint);
        return new AnimatedModel(model, texture, new Vector3f(55,5,55),new Vector3f(0),1,headJoint, skeletonData.jointCount);
    }

    /**
     * Constructs the joint-hierarchy skeleton from the data extracted from the
     * collada file.
     *
     * @param data - the joints data from the collada file for the head joint.
     * @return The created joint, with all its descendants added.
     */
    private static Joint createJoints(JointData data) {
        Joint joint = new Joint(data.index, data.nameId, data.bindLocalTransform);
        for (JointData child : data.children) {
            joint.addChild(createJoints(child));
        }
        return joint;
    }

    /**
     * Stores the mesh data in a VAO.
     *
     * @param data - all the data about the mesh that needs to be stored in the
     *             VAO.
     * @return The VAO containing all the mesh data for the model.
     */
    private static MeshModel createVao(MeshData data, Loader loader) {
        return loader.loadToVAO(data.getIndices(), data.getVertices(), data.getTextureCoords(), data.getNormals(), data.getJointIds(), data.getVertexWeights());
    }

}
