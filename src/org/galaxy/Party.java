package org.galaxy;

import android.graphics.Paint;

public class Party {
	String name = "";
	Paint paint;
	
	public Party(String name, Paint paint) {
		this.name = name;
		this.paint = paint;
	}

	public String getName() {
		return name;
	}

	public Paint getPaint() {
		return paint;
	}
	
	public boolean hasGrows() {
		return !name.equals(""); 
	}
	
}
