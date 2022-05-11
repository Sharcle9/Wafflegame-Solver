package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SolvingController {
	
	public static final String DICT_PATH = "words.txt";
	
	public boolean isWord(String s)
	{
		Scanner scanner;
		try {
			scanner = new Scanner(new File(DICT_PATH));
			String word = "";
			do 
			{
				word = scanner.nextLine();
				if(word.equalsIgnoreCase(s)) return true;
			} 
			while(scanner.hasNextLine());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
