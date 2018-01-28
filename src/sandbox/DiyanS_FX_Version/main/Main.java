package main;

import javax.swing.UIManager;

import model.Model;
import model.VerticalLine;
import view.RunGui;

/**
 * @author Murray Wood Demonstration of MVC and MIT Physics Collisions 2014
 */

public class Main {

	public static void main(String[] args) {
		try {
			// Use the platform look and feel
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println("Look and Feel error in Main");
		}

		Model model = new Model();

		model.setBallSpeed(200, 200);

		// Vertical line at (100,100), width 300
		model.addLine(new VerticalLine(100, 100, 300));
		model.addLine(new VerticalLine(100, 200, 300));
		model.addLine(new VerticalLine(100, 300, 300));
		model.addLine(new VerticalLine(100, 400, 300));

		RunGui gui = new RunGui(model);
		gui.createAndShowGUI();
	}
}
