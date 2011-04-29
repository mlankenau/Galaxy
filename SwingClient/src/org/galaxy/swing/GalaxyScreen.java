package org.galaxy.swing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.galaxy.Game;
import org.galaxy.Planet;



public class GalaxyScreen extends JPanel {
	Game game;
	int fps = 10;
	
	public GalaxyScreen() {
		super();
		setBackground(new Color(0x000000));
		
		game = new Game();
		game.init();
		
	}

	@Override
	public void paint(Graphics g) {
		for (Planet planet : game.getPlanets()) {
			int x = (int) planet.getPos().getX();
			int y = (int) planet.getPos().getY();
			int size = (int) planet.getSize();
	        g.setColor(Color.white);

			g.drawOval(x, y, size, size);
//			canvas.drawText("" + planet.getEnergy(), planet.getPos().getX(),
//					planet.getPos().getY() + 3, paintText);
//
//			if (dragFrom.contains(planet) || planet == possibleTarget) {
//				canvas.drawCircle(planet.getPos().getX(), planet.getPos()
//						.getY(), planet.getSize() + 15, paintPlanetSelect);
//			}

		}
//		for (Ship ship : game.getShips()) {
//			canvas.drawCircle(ship.getPos().getX(), ship.getPos().getY(), 2,
//					getPaint(ship.getParty().getColor()));
//		}
		
		super.paint(g);
	}
	
	/**
	 * main thread loop
	 */
	private void cycle() {
		try {
			while (true) {

				Thread.sleep((int) (1000.f / fps));

				game.cycle(1.0f / fps);

				new Runnable() {
					public void run() {
						invalidate();
					}
				};
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


//	@Override
//	public void draw(Canvas canvas) {
//		Paint paintText = new Paint();
//		paintText.setColor(0xffffffff);
//		paintText.setTextAlign(Align.CENTER);
//
//		Paint paintPlanetSelect = new Paint();
//		paintPlanetSelect.setColor(0x50ffffff);
//		for (Planet planet : game.getPlanets()) {
//
//			canvas.drawCircle(planet.getPos().getX(), planet.getPos().getY(), planet.getSize(),
//					getPaint(planet.getParty().getColor()));
//			canvas.drawText("" + planet.getEnergy(), planet.getPos().getX(),
//					planet.getPos().getY() + 3, paintText);
//			
//		  if (dragFrom.contains(planet) || planet == possibleTarget) {
//			  canvas.drawCircle(planet.getPos().getX(), planet.getPos().getY(),
//					  planet.getSize()+15, paintPlanetSelect); 
//		  }
//			 
//		}
//		for (Ship ship : game.getShips()) {
//			canvas.drawCircle(ship.getPos().getX(), ship.getPos().getY(), 2, getPaint(ship.getParty().getColor()));
//		}
//
//		super.draw(canvas);
//	}

	
}
