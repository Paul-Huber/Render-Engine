package rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class DataLoader {

	private VAO vao;
	private VBO vbo;
	private EBO ebo;

	public void load(float[] vertices, int[] indices) {
		vao = new VAO();
		vao.bindVAO();
		vbo = new VBO(vertices);
		ebo = new EBO(indices);
		
		//load the vertex positions, color data, and texture coordinates
		vao.linkAttrib(vbo, 0, 3, GL11.GL_FLOAT, 8 * Float.BYTES, 0);
		vao.linkAttrib(vbo, 1, 3, GL11.GL_FLOAT, 8 * Float.BYTES, (3 * Float.BYTES));
		vao.linkAttrib(vbo, 2, 2, GL11.GL_FLOAT, 8 * Float.BYTES, (6 * Float.BYTES));

		//unbind objects
		vao.unbindVAO();
		vbo.unbindVBO();
		ebo.unbindEBO();
	}

	public void bind() {
		vao.bindVAO();
	}

	public void delete() {
		vao.deleteVAO();
		vbo.deleteVBO();
		ebo.deleteEBO();
	}


	public class VAO {	
		private int id;

		public VAO() {
			id = GL30.glGenVertexArrays();
		}

		public void linkAttrib(VBO vbo, int layout, int numComponents, int type, int stride, int offset) {	//load VBOs into VAOs
			vbo.bindVBO();
			GL20.glVertexAttribPointer(layout, numComponents, GL11.GL_FLOAT, false, stride, offset);
			GL20.glEnableVertexAttribArray(layout);
			vbo.unbindVBO();
		}

		public void bindVAO() {
			GL30.glBindVertexArray(id); 
		}

		public void unbindVAO() {
			GL30.glBindVertexArray(0);
		}

		public void deleteVAO() {
			GL30.glDeleteVertexArrays(id);
		}
	}

	public class VBO {
		private int id;

		public VBO(float[] vertices) {															//generate a VBO with the given data
			id = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
			FloatBuffer buffer = storeDataInFloatBuffer(vertices);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		}

		public void bindVBO() {
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
		}

		public void unbindVBO() {
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		}

		public void deleteVBO() {
			GL15.glDeleteBuffers(id);
		}
	}

	public class EBO {	
		private int id;
		public EBO(int[] indices) {																//generate an EBO with the given data
			id = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id);
			IntBuffer buffer = storeDataInIntBuffer(indices);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		}

		public void bindEBO() {
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, id);
		}

		public void unbindEBO() {
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		}

		public void deleteEBO() {
			GL15.glDeleteBuffers(id);
		}
	}

	private FloatBuffer storeDataInFloatBuffer(float[] data) {									//generate a float buffer from float array data
		FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}

	private IntBuffer storeDataInIntBuffer(int[] data) {										//generate an int buffer from integer array data
		IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
}
