package core;

import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import rendering.Camera;
import rendering.DataLoader;
import rendering.Model;
import rendering.Renderer;
import rendering.Texture;

public class Main {

	public static void main(String[] args) {
		//initialize and create the window
		WindowManager windowManager = new WindowManager();
		windowManager.buildWindow();
		
		float[] points = {
				//vertices		//color data 	//texture coordinates
				-0.5f,0.5f,-0.5f,	1, 1, 1, 	0,0,
				-0.5f,-0.5f,-0.5f,	1, 1, 1, 	0,1,
				0.5f,-0.5f,-0.5f,	1, 1, 1, 	1,1,
				0.5f,0.5f,-0.5f,	1, 1, 1, 	1,0,
				
				-0.5f,0.5f,0.5f,	1, 1, 1, 	0,0,
				-0.5f,-0.5f,0.5f,	1, 1, 1, 	0,1,
				0.5f,-0.5f,0.5f,	1, 1, 1, 	1,1,
				0.5f,0.5f,0.5f,		1, 1, 1, 	1,0,
				                                        
				0.5f,0.5f,-0.5f,	1, 1, 1, 	0,0,
				0.5f,-0.5f,-0.5f,	1, 1, 1, 	0,1,
				0.5f,-0.5f,0.5f,	1, 1, 1, 	1,1,
				0.5f,0.5f,0.5f,		1, 1, 1, 	1,0,
				                                        
				-0.5f,0.5f,-0.5f,	1, 1, 1, 	0,0,
				-0.5f,-0.5f,-0.5f,	1, 1, 1, 	0,1,
				-0.5f,-0.5f,0.5f,	1, 1, 1, 	1,1,
				-0.5f,0.5f,0.5f,	1, 1, 1, 	1,0,
				                                        
				-0.5f,0.5f,0.5f,	1, 1, 1, 	0,0,
				-0.5f,0.5f,-0.5f,	1, 1, 1, 	0,1,
				0.5f,0.5f,-0.5f,	1, 1, 1, 	1,1,
				0.5f,0.5f,0.5f,		1, 1, 1, 	1,0,
				                                        
				-0.5f,-0.5f,0.5f,	1, 1, 1, 	0,0,
				-0.5f,-0.5f,-0.5f,	1, 1, 1, 	0,1,
				0.5f,-0.5f,-0.5f,	1, 1, 1, 	1,1,
				0.5f,-0.5f,0.5f,	1, 1, 1, 	1,0
	};

	int[] indices = {								//a list that tells OpenGL the order in which to render the points
			0,1,3,	
			3,1,2,	
			4,5,7,
			7,5,6,
			8,9,11,
			11,9,10,
			12,13,15,
			15,13,14,	
			16,17,19,
			19,17,18,
			20,21,23,
			23,21,22
	};
	
	DataLoader loader = new DataLoader();
	loader.load(points, indices); 					//load points into system memory

	Renderer renderer = new Renderer();				//initialize shaders
	renderer.start();
	
	//initialize the texture
	Texture texture = new Texture("rock.png");
	texture.bindTexture();
	//initialize the camera and model to be loaded
	Camera cam = new Camera(new Vector3f(0,0,0), -100, 0, 0);
	Model model = new Model(new Vector3f(0,-1,-2), new Vector3f(1,1,1), 0, 0, 0);

	while (!GLFW.glfwWindowShouldClose(windowManager.getWindow())) {
		model.increaseRotX(0.01f); 								//rotates the model
		loader.bind();											//update the VAO
		renderer.render(cam, model); 							//render the model
	}
	//cleans memory and deletes used objects
	texture.unbindTexture();
	loader.delete();
	renderer.clean();
	windowManager.clean();
	}
}
