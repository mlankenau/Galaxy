package org.galaxy;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GalaxyScreen extends View implements View.OnTouchListener {

	Game game = new Game();
	HashSet<Planet> dragFrom = new HashSet<Planet>();
	Planet possibleTarget = null;

	Hashtable<Integer, Paint> paints = new Hashtable<Integer, Paint>();
	

	float fps = 15;

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
		game.init();
		
		
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

				game.cycle(1.0f / fps);

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

	private Paint getPaint(int color) {
		Paint p = paints.get(color);
		if (p == null) {
			p = new Paint();
			p.setColor(color);
			paints.put(color, p);
		}
		
		return p;
	}
	
	@Override
	public void draw(Canvas canvas) {
		Paint paintText = new Paint();
		paintText.setColor(0xffffffff);
		paintText.setTextAlign(Align.CENTER);

		Paint paintPlanetSelect = new Paint();
		paintPlanetSelect.setColor(0x50ffffff);
		for (Planet planet : game.getPlanets()) {

			canvas.drawCircle(planet.getPos().getX(), planet.getPos().getY(), planet.getSize(),
					getPaint(planet.getParty().getColor()));
			canvas.drawText("" + planet.getEnergy(), planet.getPos().getX(),
					planet.getPos().getY() + 3, paintText);
			
		  if (dragFrom.contains(planet) || planet == possibleTarget) {
			  canvas.drawCircle(planet.getPos().getX(), planet.getPos().getY(),
					  planet.getSize()+15, paintPlanetSelect); 
		  }
			 
		}
		for (Ship ship : game.getShips()) {
			canvas.drawCircle(ship.getPos().getX(), ship.getPos().getY(), 2, getPaint(ship.getParty().getColor()));
		}

		super.draw(canvas);
	}

	

	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			Planet planetDragFrom = game.findPlanet(event.getX(), event.getY(),
					game.getPlayer());
			if (planetDragFrom != null)
				dragFrom.add(planetDragFrom);

		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			possibleTarget = null;
			if (dragFrom.size() > 0) {
				Planet planetDragFrom = game.findPlanet(event.getX(), event.getY(),
						game.getPlayer());
				if (planetDragFrom != null)
					dragFrom.add(planetDragFrom);

				possibleTarget = game.findPlanet(event.getX(), event.getY(), null);
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			Planet dragTo = game.findPlanet(event.getX(), event.getY(), null);
			if (dragTo != null) {
				for (Planet planetDragFrom : dragFrom) {
					game.getShips().addAll(planetDragFrom.launch(dragTo, new Date().getTime()));
				}
			}
			dragFrom.clear();
			possibleTarget = null;
		}

		return true;
	}

}
