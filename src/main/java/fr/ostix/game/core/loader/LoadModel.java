package fr.ostix.game.core.loader;

import fr.ostix.game.graphics.model.MeshModel;
import fr.ostix.game.toolBox.FileType;
import org.lwjgl.assimp.*;

import java.util.Objects;

import static fr.ostix.game.toolBox.ToolDirectory.RES_FOLDER;

public class LoadModel {

    public static MeshModel loadModel(String fileName, Loader loader) {
        AIScene scene = Assimp.aiImportFile(RES_FOLDER + "\\model\\" + fileName,
                Assimp.aiProcess_Triangulate|
                        Assimp.aiProcess_CalcTangentSpace|
                        Assimp.aiProcess_LimitBoneWeights|
                        Assimp.aiProcess_GenSmoothNormals|
                Assimp.aiProcess_FlipUVs
        );


        if (scene == null) {
            System.err.println("the imported file does not contain any animations.");
            //System.exit(0);
        }

        assert scene != null;
        AIMesh mesh = AIMesh.create(Objects.requireNonNull(scene.mMeshes()).get(0));


        float[] pos = new float[mesh.mNumVertices() * 3];
        float[] texCoords = new float[mesh.mNumVertices() * 2];
        float[] normals = new float[mesh.mNumVertices() * 3];
        int[] indices = new int[mesh.mNumFaces() * mesh.mFaces().get(0).mNumIndices()];

        int indexPos = 0;
        int indexTex = 0;
        int indexNorm = 0;

        for (int v = 0; v < mesh.mNumVertices(); v++) {
            AIVector3D position = mesh.mVertices().get(v);
            AIVector3D normal = Objects.requireNonNull(mesh.mNormals()).get(v);
            AIVector3D texCoord = mesh.mTextureCoords(0).get(v);

            /*
             * The above assumes that the program has texture coordinates, if it doesn't the program will throw a null pointer exception.
             */

            pos[indexPos++] = position.x();
            pos[indexPos++] = position.y();
            pos[indexPos++] = position.z();

            texCoords[indexTex++] = texCoord.x();
            texCoords[indexTex++] = texCoord.y();

            normals[indexNorm++] = normal.x();
            normals[indexNorm++] = normal.y();
            normals[indexNorm++] = normal.z();

        }

        int index = 0;

        for (int f = 0; f < mesh.mNumFaces(); f++) {
            AIFace face = mesh.mFaces().get(f);
            for (int ind = 0; ind < face.mNumIndices(); ind++) {
                indices[index++] = (face.mIndices().get(ind));
            }
        }
        return loader.loadToVAO(pos, texCoords, normals, indices);
    }

    public static MeshModel loadModel(String fileName, FileType extension, Loader loader) {
        AIScene scene = Assimp.aiImportFile(RES_FOLDER + "\\model\\" + fileName + extension.getExtension(),
                Assimp.aiProcess_Triangulate|
                        Assimp.aiProcess_CalcTangentSpace|
                        Assimp.aiProcess_LimitBoneWeights|
                        Assimp.aiProcess_GenSmoothNormals|
                        Assimp.aiProcess_FlipUVs
        );


        if (scene == null) {
            System.err.println("the imported file does not contain any animations.");
            //System.exit(0);
        }

        assert scene != null;
        AIMesh mesh = AIMesh.create(Objects.requireNonNull(scene.mMeshes()).get(0));


        float[] pos = new float[mesh.mNumVertices() * 3];
        float[] texCoords = new float[mesh.mNumVertices() * 2];
        float[] normals = new float[mesh.mNumVertices() * 3];
        int[] indices = new int[mesh.mNumFaces() * mesh.mFaces().get(0).mNumIndices()];

        int indexPos = 0;
        int indexTex = 0;
        int indexNorm = 0;

        for (int v = 0; v < mesh.mNumVertices(); v++) {
            AIVector3D position = mesh.mVertices().get(v);
            AIVector3D normal = Objects.requireNonNull(mesh.mNormals()).get(v);
            AIVector3D texCoord = Objects.requireNonNull(mesh.mTextureCoords(0)).get(v);

            /*
             * The above assumes that the program has texture coordinates, if it doesn't the program will throw a null pointer exception.
             */

            pos[indexPos++] = position.x();
            pos[indexPos++] = position.y();
            pos[indexPos++] = position.z();

            texCoords[indexTex++] = texCoord.x();
            texCoords[indexTex++] = texCoord.y();

            normals[indexNorm++] = normal.x();
            normals[indexNorm++] = normal.y();
            normals[indexNorm++] = normal.z();

        }

        int index = 0;

        for (int f = 0; f < mesh.mNumFaces(); f++) {
            AIFace face = mesh.mFaces().get(f);
            for (int ind = 0; ind < face.mNumIndices(); ind++) {
                indices[index++] = (face.mIndices().get(ind));
            }
        }
        return loader.loadToVAO(pos, texCoords, normals, indices);
    }
}
