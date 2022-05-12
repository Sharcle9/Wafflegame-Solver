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
		waffle.printColorWaffle();
		System.out.println("\nSolving Now...");
		
		String word = solvingController.getWord(waffle.getWaffle(), 'h', 1);
		System.out.println("Word: " + word + ". Is a word? " + solvingController.isWord(word));
		
		System.out.println(waffle.getUnusedCharArray());

		System.out.println(solvingController.getAllChar(waffle.getWaffle()));

		System.out.println(solvingController.verify(waffle, waffle.getWaffle()));
		
	}
}
