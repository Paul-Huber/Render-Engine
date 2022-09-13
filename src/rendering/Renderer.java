package rendering;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import core.WindowManager;
import shaders.ShaderProgram;

public class Renderer {
	private Matrix4f modelMat;
	private Matrix4f viewMat;
	private Matrix4f projMat;
	private ShaderProgram shaderProgram = new ShaderProgram();
	private WindowManager window = new WindowManager();
	
	private static final float FOV = 70f;
	private static final float NEARPLANE = 0.1f;
	private static final float FARPLANE = 1000f;
	
	public void render(Camera cam, Model model) {							
		GLFW.glfwPollEvents(); 												//updates the event handler
		GLFW.glfwSwapBuffers(window.getWindow()); 							//swaps the front and back buffers to create the frame rate
		GL11.glClearColor(0.07f, 0.13f, 0.17f, 1.0f);						//sets the background color
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	//reset buffers to presets

		//create rendering matrices
		makeModelMatrix(model);												
		makeViewMatrix(cam);
		makeProjectionMatrix();

		GL11.glDrawElements(GL11.GL_TRIANGLES, 36, GL11.GL_UNSIGNED_INT, 0); //draws the model to the screen using triangles
	}
	
	public void start() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		shaderProgram.start();
	}
	
	public void clean() {
		shaderProgram.clean();
	}
	
	private void makeModelMatrix(Model model) {				//multiplied with each point in 3D world space to allow control of the model
		modelMat = new Matrix4f();
		modelMat.translate(model.getPosition())
				.rotateY(model.getRotX())
				.rotateX(model.getRotY())
				.rotateZ(model.getRotZ())
				.scale(model.getScale());
		shaderProgram.loadModelMatrix(modelMat);
	}
	
	private void makeViewMatrix(Camera cam) {				//converts each point in world space to camera space
		viewMat = new Matrix4f();
		viewMat.translate(cam.getPosition())
				.rotateX(cam.getRotX())
				.rotateY(cam.getRotY())
				.rotateZ(cam.getRotZ());
		shaderProgram.loadViewMatrix(viewMat);
	}
	
	private void makeProjectionMatrix() {					//finds the 2D screen space coordinate of each point in camera space using rasterization
		projMat = new Matrix4f();
		projMat.perspective((float) Math.toRadians(FOV), (float)(window.getWidth() / window.getHeight()), NEARPLANE, FARPLANE);
		shaderProgram.loadProjectionMatrix(projMat);
	}
}
