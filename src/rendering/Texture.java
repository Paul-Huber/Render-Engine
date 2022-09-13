package rendering;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

public class Texture {
	private int id;
	
	public Texture(String fileName) {
		STBImage.stbi_set_flip_vertically_on_load(true); 				//flips the images being loaded
		
		ByteBuffer image;
		int width, height;
		
		try(MemoryStack stack = MemoryStack.stackPush()) {
			IntBuffer w = stack.mallocInt(1);
			IntBuffer h = stack.mallocInt(1);
			IntBuffer comp = stack.mallocInt(1);
			
			//loads the image into a byte buffer
			image = STBImage.stbi_load("C:/Users/pjhub/eclipse-workspace/newGhostEngine/res/" + fileName, w, h, comp, 0); 
			
			//if the image can't be loaded, print an error
			if(image == null) {
				System.err.println("failed to load texture file");
				return;
			}
			
			width = w.get();
			height = h.get();
			
			//allocates space for the texture
			id = GL11.glGenTextures();
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
		}
		
		//specifications for how the texture should be applied to the model
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_WRAP_S, GL14.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL14.GL_TEXTURE_WRAP_T, GL14.GL_CLAMP_TO_EDGE);
        
		GL20.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL20.GL_RGBA, width, height, 0, GL20.GL_RGBA, GL20.GL_UNSIGNED_BYTE, image); //load the texture into OpenGL
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);																		//generate mipmaps for the texture

		STBImage.stbi_image_free(image); 																			//free memory
	}
	
	public void bindTexture() {
		GL20.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}
	
	public void unbindTexture() {
		GL20.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public int getID() {
		return id;
	}
}
