package org.galaxy.swing;

import java.awt.Color;

import javax.swing.JFrame;


public class Galaxy extends JFrame {

	public Galaxy() {
		setTitle("Simple example");
		setSize(400, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setContentPane(new GalaxyScreen());
	}
	
	public static void main(String[] args) {
		 Galaxy galaxy = new Galaxy();
		 galaxy.show();
	}
}
