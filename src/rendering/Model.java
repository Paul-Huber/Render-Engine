package rendering;

import org.joml.Vector3f;

public class Model {
	private Vector3f position;
	private Vector3f scale;
	private float rotX;
	private float rotY;
	private float rotZ;
	
	public Model(Vector3f position, Vector3f scale, float rotX, float rotY, float rotZ) {
		this.position = position;
		this.scale = scale;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = (float) (rotZ + Math.toRadians(180));
	}
	
	public void increaseRotX(float delta) {
		this.rotX += delta;
	}
	
	public void increaseRotY(float delta) {
		this.rotY += delta;
	}
	
	public void increaseRotZ(float delta) {
		this.rotZ += delta;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Vector3f getScale() {
		return scale;
	}

	public void setScale(Vector3f scale) {
		this.scale = scale;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
}
