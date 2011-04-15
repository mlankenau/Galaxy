package org.galaxy;

public class Vector {
	float x;
	float y;
	
	
	public Vector() {
		x = 0;
		y = 0;
	}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}
	
	public float length() {
		return (float) Math.sqrt(x*x+y*y);
	}
	
	public Vector add(Vector v) {
		return new Vector(x+v.x, y+v.y);
	}
	
	public Vector sub(Vector v) {
		return new Vector(x-v.x, y-v.y);
	}
	
	public Vector multiply(float s) {
		return new Vector(x*s, y*s);
	}
	
	
	public Vector normalize() {
		float l = length();
		return this.multiply(1.f / l);
	}
}
