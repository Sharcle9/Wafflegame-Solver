package entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Waffle 
{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[103m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[102m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[107m";
	private char[][] waffle;
	private int[][] color;
	
	public Waffle(File txt)
	{
		char[] row1;
		char[] row2;
		char[] row3;
		char[] row4;
		char[] row5;
		
		int[] crow1;
		int[] crow2;
		int[] crow3;
		int[] crow4;
		int[] crow5;
		
		try {
			Scanner scanner = new Scanner(txt);

			String data = scanner.nextLine();
			row1 = getCharArray(data, 5);
			data = scanner.nextLine();
			row2 = getCharArray(data, 3);
			data = scanner.nextLine();
			row3 = getCharArray(data, 5);
			data = scanner.nextLine();
			row4 = getCharArray(data, 3);
			data = scanner.nextLine();
			row5 = getCharArray(data, 5);
			
			waffle = new char[][] {row1, row2, row3, row4, row5};
			
			scanner.nextLine();

			data = scanner.nextLine();
			crow1 = getIntArray(data, 5);
			data = scanner.nextLine();
			crow2 = getIntArray(data, 3);
			data = scanner.nextLine();
			crow3 = getIntArray(data, 5);
			data = scanner.nextLine();
			crow4 = getIntArray(data, 3);
			data = scanner.nextLine();
			crow5 = getIntArray(data, 5);
			
			color = new int[][] {crow1, crow2, crow3, crow4, crow5};
			
		} catch (FileNotFoundException e) {
			System.out.println("Error");
			e.printStackTrace();
		}
	}
	
	public String getWord(char c, int i)
	{
		String result = "";
		if(c == 'h')
		{
			for(int j = 0; j < 5; j ++)
			{
				result += getWaffle()[i * 2 - 2][j];
			}
		}
		else if(c == 'v')
		{
			for(int j = 0; j < 5; j ++)
			{
				result += getWaffle()[j][i * 2 - 2 - ((j%2) * (i-1))];
			}
		}
		
		return result;
	}
	
	
	
	
	public char[] getCharArray(String s, int len)
	{
		char[] result = new char[len];
		int index = 0;
		for(int i = 0; i < s.length(); i ++)
		{
			if(s.charAt(i) == ' ') continue;
			else
			{
				result[index] = Character.toUpperCase(s.charAt(i));
				index ++;
			}
		}
		
		return result;
	}
	
	public int[] getIntArray(String s, int len)
	{
		int[] result = new int[len];
		int index = 0;
		for(int i = 0; i < s.length(); i ++)
		{
			if(s.charAt(i) == ' ') continue;
			else
			{
				result[index] = Character.getNumericValue(s.charAt(i));
				index ++;
			}
		}
		
		return result;
	}
	
	public void printWaffle() 
	{
		for(int i = 0; i < 5; i ++)
		{
			for(int j = 0; j < waffle[i].length; j ++)
			{
				String backgroundColor = "\u001B[0m";
				
				switch (color[i][j])
				{
				case 0: backgroundColor = ANSI_WHITE_BACKGROUND; break;
				case 1: backgroundColor = ANSI_YELLOW_BACKGROUND; break;
				case 2: backgroundColor = ANSI_GREEN_BACKGROUND; break;
				}
				System.out.print(backgroundColor + waffle[i][j] + ANSI_RESET + " ");
				if (i % 2 == 1)
				{
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}

	public char[][] getWaffle() {
		return waffle;
	}

	public void setWaffle(char[][] waffle) {
		this.waffle = waffle;
	}

	public int[][] getColor() {
		return color;
	}

	public void setColor(int[][] color) {
		this.color = color;
	}
}