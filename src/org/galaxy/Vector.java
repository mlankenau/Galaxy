package org.galaxy;

import java.util.List;
import java.util.ArrayList;

public class Vector {
	float x;
	float y;
	
	public static void main(String ... args){
	  Vector start = new Vector(1.0f, 1.0f);
	  Vector sp1 = new Vector(30.0f, 1.0f);
	  Vector sp2 = new Vector(5.0f, 45.0f);
	  Vector end = new Vector(80.0f, 80.0f);
	  List<Vector> curve = Vector.bezier(start, sp1, sp2, end, 10);
	  for(Vector v : curve)
	    System.out.println(v);
	}
	
	public Vector() {
		x = 0.0f;
		y = 0.0f;
	}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector(double x, double y) {
		this.x = (float)x;
		this.y = (float)y;
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
	
	public String toString(){
	  return "<"+x+";"+y+">";
	}
	
	public static List<Vector> bezier(Vector start, Vector sp1, Vector sp2, Vector end, int steps){
	  float t = 1.0f / steps;
    float temp = t*t;
    Vector fd = sp1.sub(start).multiply(3.0f).multiply(t);
    Vector fdd_per_2 = start.sub(sp1).multiply(2.0f).add(sp2).multiply(3.0f).multiply(temp);
    Vector fddd_per_2 = sp1.sub(sp2).multiply(3.0f).add(end).sub(start).multiply(3.0f).multiply(temp).multiply(t);
    Vector fddd = fddd_per_2.multiply(2.0f);
    Vector fdd = fdd_per_2.multiply(2.0f);
    Vector fddd_per_6 = fddd_per_2.multiply(0.33333333f);
    
    List<Vector> curve = new ArrayList<Vector>();
    for(;steps>0; steps--){
      curve.add(start);
      start = start.add(fd).add(fdd_per_2).add(fddd_per_6);
      fd = fd.add(fdd).add(fddd_per_2);
      fdd = fdd.add(fddd);
      fdd_per_2 = fdd_per_2.add(fddd_per_2);
    }
    curve.add(end);
    return curve;
	}
	
}
