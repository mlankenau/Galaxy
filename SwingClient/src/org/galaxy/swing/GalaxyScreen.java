package org.galaxy.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Date;
import java.util.HashSet;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import org.galaxy.Game;
import org.galaxy.GameFactory;
import org.galaxy.Planet;
import org.galaxy.Ship;



public class GalaxyScreen extends JPanel implements MouseListener, MouseMotionListener {
	Game game;
	int fps = 10;
	
	HashSet<Planet> dragFrom = new HashSet<Planet>();
	Planet possibleTarget = null;
	
	 
	
	
	public GalaxyScreen() {
		super();
		setBackground(new Color(0x000000));
		
		game = GameFactory.getLevel(1);
		
		addMouseListener(this);		
		addMouseMotionListener(this);
	
		this.setDoubleBuffered(true);
		
		Thread thread = new Thread(new Runnable() {
			public void run() {
				cycle();
			}
		});
		thread.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Planet planet : game.getPlanets()) {
			int x = (int) planet.getPos().getX();
			int y = (int) planet.getPos().getY();
			int size = (int) planet.getSize();
	        

			if (dragFrom.contains(planet) || planet == possibleTarget) {
				g.setColor(Color.white);
				g.fillOval(x-size-5, y-size-5, 2*size+10, 2*size+10);
			}
			
			
			g.setColor(new Color(planet.getParty().getColor()));
			g.fillOval(x-size, y-size, 2*size, 2*size);

			
			g.setColor(Color.white);
			g.drawString("" + planet.getEnergy(), -5 + (int) planet.getPos().getX(), (int) planet.getPos().getY() + 3);			
		}
		for (Ship ship : game.getShips()) {
			g.setColor(new Color(ship.getParty().getColor()));
			g.fillOval((int) ship.getPos().getX()-1, (int) ship.getPos().getY()-1, 4, 4);					
		}
		
		
	}
	
	/**
	 * main thread loop
	 */
	private void cycle() {
		
		try {
			while (true) {

				Thread.sleep((int) (1000.f / fps));

				game.cycle(1.0f / fps);

				Runnable redraw = new Runnable() {
					public void run() {
						
						paintImmediately(0, 0, getWidth(), getHeight());
					}
				};
				SwingUtilities.invokeLater(redraw);
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
		Planet planetDragFrom = game.findPlanet(event.getX(), event.getY(),
				game.getMe());
		if (planetDragFrom != null)
			dragFrom.add(planetDragFrom);
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		
		
		Planet dragTo = game.findPlanet(event.getX(), event.getY(), null);
		
		if (dragTo != null && !dragFrom.contains(dragTo)) {
			for (Planet planetDragFrom : dragFrom) {
				System.out.println("Launching sups... ");
				game.move(planetDragFrom, dragTo);
			}
		}
		dragFrom.clear();
		possibleTarget = null;
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		possibleTarget = null;
		if (dragFrom.size() > 0) {
			Planet planetDragFrom = game.findPlanet(event.getX(), event.getY(),
					game.getMe());
			if (planetDragFrom != null)
				dragFrom.add(planetDragFrom);

			possibleTarget = game.findPlanet(event.getX(), event.getY(), null);
		}	
	}

	@Override
	public void mouseMoved(MouseEvent event) {
	
	}



	
}
