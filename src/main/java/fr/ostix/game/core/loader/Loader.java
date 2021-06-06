package fr.ostix.game.core.loader;

import de.matthiasmann.twl.utils.PNGDecoder;
import fr.ostix.game.graphics.model.MeshModel;
import fr.ostix.game.graphics.textures.Texture;
import fr.ostix.game.graphics.textures.TextureData;
import fr.ostix.game.graphics.textures.TextureUtils;
import fr.ostix.game.openGLUtils.VAO;
import fr.ostix.game.toolBox.Logger;
import org.lwjgl.opengl.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static fr.ostix.game.toolBox.ToolDirectory.RES_FOLDER;

public class Loader {

    public static final Loader INSTANCE = new Loader();

    private final List<VAO> VAOs = new ArrayList<>();
    private final List<Texture> textures = new ArrayList<>();

    public MeshModel loadToVAO(float[] position, float[] texturesCoords,
                               float[] normals, int[] indices) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeIndicesInVAO(indices);
        vao.storePositionInAttributeList(0, 3, position);
        vao.storeDataInAttributeList(1, 2, texturesCoords);
        vao.storeDataInAttributeList(2, 3, normals);
        VAO.unbind();
        return new MeshModel(vao);
    }

    public MeshModel loadFontToVAO(float[] pos, float[] texturesCoords){
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeDataInAttributeList(0, 3, pos);
        vao.storeDataInAttributeList(1, 2, texturesCoords);
        VAO.unbind();
        return new MeshModel(vao);
    }

    public MeshModel loadToVAO(float[] vertices) {
        VAO vao = VAO.createVAO();
        VAOs.add(vao);
        vao.bind();
        vao.storeDataInAttributeList(0, 3, vertices);
        VAO.unbind();
        return new MeshModel(vao);
    }

    //TEXTURES


    public int loadCubMap(String[] fileNames) {
        int texID = glGenTextures();
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_CUBE_MAP, texID);

        for (int i = 0; i < fileNames.length; i++) {
            TextureData data = decodeTextureFile(fileNames[i]);
            glTexImage2D(GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, GL_RGBA, data.getWight(), data.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, data.getBuffer());
        }
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        textures.add(new Texture(texID));
        return texID;
    }

    private TextureData decodeTextureFile(String fileName) {
        int width = 0;
        int height = 0;
        ByteBuffer buffer = null;
        InputStream fis;
        try {
            fis = new FileInputStream(RES_FOLDER+"/textures/skybox/" + fileName + ".png");
            PNGDecoder decoder = new PNGDecoder(fis);
            width = decoder.getWidth();
            height = decoder.getHeight();
            buffer = ByteBuffer.allocateDirect(4 * width * height);
            decoder.decode(buffer, width * 4, PNGDecoder.Format.RGBA);
            buffer.flip();
            fis.close();
        } catch (IOException e) {
            Logger.err("Tried to load " + fileName + " , didn't work", e);
            System.exit(-1);
        }
        return new TextureData(width, height, buffer);
    }


    public Texture loadTexture(String fileName) {
        Texture texture = Texture.loadTexture( fileName, TextureUtils.MIPMAP_ANISOTROPIC_MODE, false);
        textures.add(texture);
        return texture;
    }

    public Texture loadTextureFont(String textureFontName) {
        Texture texture = Texture.loadTexture("font/" + textureFontName,0,false);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST_MIPMAP_LINEAR);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_LOD_BIAS, 0);
        textures.add(texture);
        return texture;
    }

    public void cleanUp() {
        for (VAO vao : VAOs) {
            vao.cleanUP();
        }
        VAOs.clear();
        for (Texture t : textures) {
            GL11.glDeleteTextures(t.getId());
        }
        textures.clear();
    }
}
