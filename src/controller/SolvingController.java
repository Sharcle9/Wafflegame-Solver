package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
			if(waffleChars[i] != arrayChars[i])
			{
//				System.out.println("letter list mismatch");
				return false;
			}
		}
		
		int[][] color = waffle.getColor();
//		for(int i = 0; i < 5; i ++)
//		{
//			for(int j = 0; j < 5; j ++)
//			{
//				System.out.print(color[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		int[][] generatedColor = generateColor(waffle.getWaffle(), array);
//		for(int i = 0; i < 5; i ++)
//		{
//			for(int j = 0; j < 5; j ++)
//			{
//				System.out.print(generatedColor[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		for(int i = 0; i < 5; i ++)
		{
			for(int j = 0; j < 5; j ++)
			{
				if(color[i][j] != generatedColor[i][j]) return false;
			}
		}
		
		return isWord(getWord(array, 'v', 1)) && isWord(getWord(array, 'v', 2)) && isWord(getWord(array, 'v', 3))
				&& isWord(getWord(array, 'h', 1)) && isWord(getWord(array, 'h', 2)) && isWord(getWord(array, 'h', 3));
	
	}
	
	public int[][] generateColor(char[][] array, char[][] sol)
	{
		int[][] result = generateGreenColor(array, sol);
		
		// fill yellow/white on rows
		result[0][0] = generateYellowWhiteColor(array, sol, 0, 0, result);
		result[0][1] = generateYellowWhiteColor(array, sol, 0, 1, result);
		result[0][2] = generateYellowWhiteColor(array, sol, 0, 2, result);
		result[0][3] = generateYellowWhiteColor(array, sol, 0, 3, result);
		result[0][4] = generateYellowWhiteColor(array, sol, 0, 4, result);
		
		result[1][0] = generateYellowWhiteColor(array, sol, 1, 0, result);
		result[2][0] = generateYellowWhiteColor(array, sol, 2, 0, result);
		result[3][0] = generateYellowWhiteColor(array, sol, 3, 0, result);
		result[4][0] = generateYellowWhiteColor(array, sol, 4, 0, result);

		result[1][2] = generateYellowWhiteColor(array, sol, 1, 2, result);
		result[2][1] = generateYellowWhiteColor(array, sol, 2, 1, result);
		result[2][2] = generateYellowWhiteColor(array, sol, 2, 2, result);
		
		result[1][4] = generateYellowWhiteColor(array, sol, 1, 4, result);
		result[4][1] = generateYellowWhiteColor(array, sol, 4, 1, result);

		result[2][3] = generateYellowWhiteColor(array, sol, 2, 3, result);
		result[3][2] = generateYellowWhiteColor(array, sol, 3, 2, result);
		
		result[2][4] = generateYellowWhiteColor(array, sol, 2, 4, result);
		result[4][2] = generateYellowWhiteColor(array, sol, 4, 2, result);
		
		result[3][4] = generateYellowWhiteColor(array, sol, 3, 4, result);
		result[4][3] = generateYellowWhiteColor(array, sol, 4, 3, result);

		result[4][4] = generateYellowWhiteColor(array, sol, 4, 4, result);
		
		return result;
	}
	
	public int countLetter(String word, char c)
	{
		int count = 0;
		for(int i = 0; i < word.length(); i ++)
		{
			if(word.charAt(i) == c) count ++;
		}
		
		return count;
	}
	
	public int generateYellowWhiteColor(char[][] array, char[][] sol, int row, int col, int[][] color)
	{
		if(color[row][col] == 2) return 2;
		char letter = array[row][col];
		
		if(row % 2 == 0)
		{
			String word = getWord(sol, 'h', row/2 + 1);
				
			int count = countLetter(word, letter);
			
			int countedLetters = 0;
			for(int j = 0; j < col; j ++)
			{
				if(array[row][j] == letter && color[row][j] != 0) countedLetters ++;
			}
			
			if(countedLetters < count) return 1;
		}
		
		if(col % 2 == 0)
		{
			String word = getWord(sol, 'v', col/2 + 1);
				
			int count = countLetter(word, letter);
			
			int countedLetters = 0;
			for(int i = 0; i < 5; i ++)
			{
				if(array[i][col] == letter && color[i][col] != 0) countedLetters ++;
			}
			
			if(countedLetters < count) return 1;
		}
		
		return 0;
	}
	
	public int[][] generateGreenColor(char[][] array, char[][] sol)
	{
		int[][] result = new int[5][5];
		for(int i = 0; i < 5; i ++)
		{
			for(int j = 0; j < 5; j ++)
			{
				if(i % 2 == 1 && j % 2 == 1)
				{
					result[i][j] = -1;
					continue;
				}
				char correctLetter = sol[i][j];
				if(array[i][j] == correctLetter) result[i][j] = 2;
			}
		}
		
		return result;
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
	
	public int[] getColor(int[][] array, char c, int i)
	{
		int[] result = new int[5];
		int index = 0;
		if(c == 'h')
		{
			for(int j = 0; j < 5; j ++)
			{
				result[index] = array[i * 2 - 2][j];
				index ++;
			}
		}
		else if(c == 'v')
		{
			for(int j = 0; j < 5; j ++)
			{
				result[index] = array[j][i * 2 - 2];
				index ++;
			}
		}
		
		return result;
	}
	
	public void solve(Waffle waffle)
	{
		ArrayList<String> h1 = new ArrayList<String>();
		ArrayList<String> h2 = new ArrayList<String>();
		ArrayList<String> h3 = new ArrayList<String>();
		ArrayList<String> v1 = new ArrayList<String>();
		ArrayList<String> v2 = new ArrayList<String>();
		ArrayList<String> v3 = new ArrayList<String>();
		
		for(int i = 0; i < 5757; i ++)
		{
			if(canWork(waffle, 'h', 1, dict[i])) h1.add(dict[i].toUpperCase());
			if(canWork(waffle, 'h', 2, dict[i])) h2.add(dict[i].toUpperCase());
			if(canWork(waffle, 'h', 3, dict[i])) h3.add(dict[i].toUpperCase());
			if(canWork(waffle, 'v', 1, dict[i])) v1.add(dict[i].toUpperCase());
			if(canWork(waffle, 'v', 2, dict[i])) v2.add(dict[i].toUpperCase());
			if(canWork(waffle, 'v', 3, dict[i])) v3.add(dict[i].toUpperCase());
		}

		System.out.println(h1.toString());
		System.out.println(h2.toString());
		System.out.println(h3.toString());
		System.out.println(v1.toString());
		System.out.println(v2.toString());
		System.out.println(v3.toString());
		
		for(int ih1 = 0; ih1 < h1.size(); ih1 ++)
		{
			for(int ih2 = 0; ih2 < h2.size(); ih2 ++)
			{
				for(int ih3 = 0; ih3 < h3.size(); ih3 ++)
				{
					for(int iv1 = 0; iv1 < v1.size(); iv1 ++)
					{
						for(int iv2 = 0; iv2 < v2.size(); iv2 ++)
						{
							for(int iv3 = 0; iv3 < v3.size(); iv3 ++)
							{
								Waffle attempt = new Waffle(h1.get(ih1), h2.get(ih2), h3.get(ih3), v1.get(iv1), v2.get(iv2), v3.get(ih3));
//								attempt.printWaffle();
//								System.out.println(verify(waffle, attempt.getWaffle()));
								if(verify(waffle, attempt.getWaffle()))
								{
									attempt.printWaffle();
									return;
								}
							}
						}
					}
				}
			}
		}
	}
	
	public boolean canWork(Waffle waffle, char pos, int index, String s)
	{
		s = s.toUpperCase();
		
		String word = getWord(waffle.getWaffle(), pos, index);
		int[] color = getColor(waffle.getColor(), pos, index);
		
		for(int i = 0; i < 5; i ++)
		{
			if(color[i] == 2 && s.charAt(i) != word.charAt(i)) return false;
			if(color[i] == 0 && s.indexOf(word.charAt(i)) >= 0 && s.indexOf(word.charAt(i), s.indexOf(word.charAt(i))) < 0) return false;
			if(color[i] == 1 && i % 2 == 1 && s.indexOf(word.charAt(i)) < 0) return false;
		}
		

		char[] unusedChars = waffle.getUnusedCharArray();
		
		loop: for(int i = 0; i < 5; i ++)
		{
			if(color[i] != 2)
			{
				for(int j = 0; j < 21; j ++)
				{
					if(unusedChars[j] == s.charAt(i)) 
					{
						unusedChars[j] = ' ';
						continue loop;
					}
				}
				return false;
			}
		}
		return true;
	}
}
