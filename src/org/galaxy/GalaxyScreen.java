package org.galaxy;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GalaxyScreen extends View implements View.OnTouchListener {

	ArrayList<Planet> planets = new ArrayList<Planet>();
	ArrayList<Ship> ships = new ArrayList<Ship>();
	Planet planetDragFrom = null;
	Planet possibleTarget = null;

	float fps = 10;

	public GalaxyScreen(Context context) {
		super(context);
		init();
	}

	public GalaxyScreen(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GalaxyScreen(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		planets.add(new Planet(50, 50, 30, 20));
		planets.add(new Planet(200, 300, 20, 20));

		Thread thread = new Thread(new Runnable() {
			public void run() {
				cycle();
			}
		});
		thread.start();
		
		setOnTouchListener(this);
	}

	/**
	 * main thread loop
	 */
	private void cycle() {
		try {		
			while (true) {

				Thread.sleep((int) (1000.f / fps));

				for (Planet planet : planets) {
					planet.grow((1.0f / fps));
				}
				
				ArrayList<Ship> deadShips = new ArrayList<Ship>();
				for (Ship ship : ships) {
					ship.move((1.0f / fps));
					
					for (Planet planet : planets) {
						if (planet.detectCollision(ship) && planet != ship.getSource()) {
							planet.collide(ship);
							deadShips.add(ship);
						}
					}
				}
				for (Ship ship : deadShips) {
					ships.remove(ship);
				}							
				
				((Activity) this.getContext()).runOnUiThread(new Runnable() {
					public void run() {
						invalidate();						
					}					
				});
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(0xffa00000);
		Paint paintText = new Paint();
		paintText.setColor(0xffffffff);
		paintText.setTextAlign(Align.CENTER);
		
		Paint paintShip = new Paint();
		paintShip.setColor(0xff00ff00);
		
		Paint paintPlanetSelect = new Paint();
		paintPlanetSelect.setColor(0x50ffffff);
		for (Planet planet : planets) {
			
			
			canvas.drawCircle(planet.getX(), planet.getY(), planet.getSize(),
					paint);
			canvas.drawText("" + planet.getEnergy(), planet.getX(),
					planet.getY() + 3, paintText);
			
			if (planet == planetDragFrom || planet == possibleTarget) {
				canvas.drawCircle(planet.getX(), planet.getY(), planet.getSize()+15,
						paintPlanetSelect);
			}
		}
		for (Ship ship : ships) {
			canvas.drawCircle(ship.getX(), ship.getY(), 2, paintShip);
		}
		
		super.draw(canvas);
	}
	
	private Planet findPlanet(float x, float y) {
		for (Planet planet : planets) {
			if (planet.isHit(x, y, 30))
				return planet;
		}
		return null;
	}

//	public boolean onTouch(View v, MotionEvent event) {
//		// TODO Auto-generated method stub
//		return false;
//	}

	 
    public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
            	planetDragFrom = findPlanet(event.getX(), event.getY());            	
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            	possibleTarget = findPlanet(event.getX(), event.getY());
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
            	Planet dragTo = findPlanet(event.getX(), event.getY());
            	if (planetDragFrom != null && dragTo != null) {
            		ships.addAll(planetDragFrom.launch(dragTo));   		            		
            	}
            	planetDragFrom = null;
            	possibleTarget = null;
            }

            return true;
    }

	
}
