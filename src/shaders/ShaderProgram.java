package shaders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {
	private int modelMatLocation;
	private int viewMatLocation;
	private int projMatLocation;
	
	private int programID;
	private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	public ShaderProgram() {
		String vertexShaderSource = loadShaderFile("C:/Users/pjhub/eclipse-workspace/newGhostEngine/src/shaders/vertex.vert");
		String fragmentShaderSource = loadShaderFile("C:/Users/pjhub/eclipse-workspace/newGhostEngine/src/shaders/fragment.frag");
		
		//creates and compiles the vertex shader
		int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		GL20.glShaderSource(vertexShader, vertexShaderSource);
		GL20.glCompileShader(vertexShader);
		
		//creates and compiles the fragment shader
		int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
		GL20.glShaderSource(fragmentShader, fragmentShaderSource);
		GL20.glCompileShader(fragmentShader);
		
		//links the vertex and fragment shaders to a shader program
		programID = GL20.glCreateProgram();
		GL20.glAttachShader(programID, vertexShader);
		GL20.glAttachShader(programID, fragmentShader);
		GL20.glLinkProgram(programID);
		GL20.glDeleteShader(vertexShader);
		GL20.glDeleteShader(fragmentShader);
		getLocations();
	}

	private void getLocations() {
		modelMatLocation = GL20.glGetUniformLocation(programID, "model");
		viewMatLocation = GL20.glGetUniformLocation(programID, "view");
		projMatLocation = GL20.glGetUniformLocation(programID, "proj");
	}

	public void start() {
		GL20.glUseProgram(programID);
	}

	public void clean() {
		GL20.glUseProgram(0);
		GL20.glDeleteProgram(programID);
	}
	
	
	
	//load the model, view, and projection matrices from shaders
	public void loadModelMatrix(Matrix4f matrix) {
		matrix.get(matrixBuffer);
		GL20.glUniformMatrix4fv(modelMatLocation, false, matrixBuffer);
	}
	
	public void loadViewMatrix(Matrix4f matrix) {
		matrix.get(matrixBuffer);
		GL20.glUniformMatrix4fv(viewMatLocation, false, matrixBuffer);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		matrix.get(matrixBuffer);
		GL20.glUniformMatrix4fv(projMatLocation, false, matrixBuffer);
	}
	
	
	//reads text data from shaders and puts them into strings
	private static String loadShaderFile(String file) {
		StringBuilder sb = new StringBuilder();
		try {
			
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			while(line != null) {
				sb.append(line).append("\n");
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.print("shader file not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public int getID() {
		return programID;
	}
}
