package view;

import java.io.File;
import java.util.Scanner;

import controller.SolvingController;
import entities.Waffle;

public class SolverView {
	Scanner scanner = new Scanner(System.in);
	SolvingController solvingController = new SolvingController();
	
	public void showSolverView()
	{

		File file = new File("test1.txt");
		// System.err.println(file.getAbsolutePath());
		Waffle waffle = new Waffle(file);
		
		System.out.println("-----Waffle-----");
		waffle.printWaffle();
		System.out.println("\nSolving Now...");
		
		String word = waffle.getWord('h', 1);
		System.out.println("Word: " + word + ". Is a word? " + solvingController.isWord(word));
		
	}
}
