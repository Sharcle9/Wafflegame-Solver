package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import entities.Waffle;

public class SolvingController {
	
	public static final String DICT_PATH = "words.txt";
	public String[] dict;
	
	public SolvingController()
	{
		dict = new String[5757];
		try {
			Scanner scanner = new Scanner(new File(DICT_PATH));
			
			for(int i = 0; i < 5757; i ++)
			{
				dict[i] = scanner.nextLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isWord(String s)
	{
		int index = Arrays.binarySearch(dict, s.toLowerCase());
		
		if(index < 0) return false;
		else return true;
	}
	
	public char[] getAllChar(char[][] array)
	{
		char[] result = new char[21];
		int index = 0;
		for(int i = 0; i < 5; i ++)
		{
			for(int j = 0; j < 5; j ++)
			{
				if(array[i][j] != ' ')
				{
					result[index] = array[i][j];
					index ++;
				}
			}
		}
		return result;
	}
	
	public boolean verify(Waffle waffle, char[][] array)
	{
		char[][] waffleArray = waffle.getWaffle();
		char[] waffleChars = getAllChar(waffleArray);
		char[] arrayChars = getAllChar(array);
		
		Arrays.sort(waffleChars);
		// System.out.println("Waffle characters: " + Arrays.toString(waffleChars));
		Arrays.sort(arrayChars);
		// System.out.println("Array characters: " + Arrays.toString(arrayChars));
		
		for(int i = 0; i < 21; i ++)
		{
			if(waffleChars[i] != arrayChars[i]) return false;
		}
		
		int[][] color = waffle.getColor();
		
		for(int i = 0; i < 5; i ++)
		{
			loop: for(int j = 0; j < 5; j ++)
			{
				char l = waffleArray[i][j];
				int c = color[i][j];
				
				if(c == -1) continue;
				
				// If green, two char are the same
				if(c == 2 && l != array[i][j]) return false;
				
				// If yellow check row and/or column
				if(c == 1)
				{
					// char is part of a row
					if(i % 2 == 0)
					{
						// iterate through the row
						for(int k = 0; k < 5; k ++)
						{
							// skip the same position
							if(j == k) continue;
							// if found a matching character in the same row, done
							if(array[i][k] == l) continue loop;
						}
					}
					// char is part of a col
					if (j % 2 == 0)
					{
						// iterate through the col
						for(int k = 0; k < 5; k ++)
						{
							// skip the same position
							if(i == k) continue;
							// if found a matching character in the same row, done
							if(array[k][j] == l) continue loop;
						}
					}
					
					return false;
				}
				
				// If white check row and/or column
				if(c == 0)
				{
					// char is part of a row
					if(i % 2 == 0)
					{
						// iterate through the row
						for(int k = 0; k < 5; k ++)
						{
							// if found a matching character in the same row, error
							if(array[i][k] == l) return false;
						}
					}
					// char is part of a col
					if (j % 2 == 0)
					{
						// iterate through the col
						for(int k = 0; k < 5; k ++)
						{
							// if found a matching character in the same row, done
							if(array[k][j] == l) return false;
						}
					}
				}
			}
		}
		return isWord(getWord(array, 'v', 1)) && isWord(getWord(array, 'v', 2)) && isWord(getWord(array, 'v', 3))
				&& isWord(getWord(array, 'h', 1)) && isWord(getWord(array, 'h', 2)) && isWord(getWord(array, 'h', 3));
	
	}
	
	public String getWord(char[][] array, char c, int i)
	{
		String result = "";
		if(c == 'h')
		{
			for(int j = 0; j < 5; j ++)
			{
				result += array[i * 2 - 2][j];
			}
		}
		else if(c == 'v')
		{
			for(int j = 0; j < 5; j ++)
			{
				result += array[j][i * 2 - 2];
			}
		}
		
		return result;
	}
}
