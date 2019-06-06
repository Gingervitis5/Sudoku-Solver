import java.util.*;
import java.io.*;

public class Main {

	public static String possibles;
	public static int width;
	public static int height;
	public static int size;
	
	public static void main(String[] args) throws FileNotFoundException {
		Scanner ns = new Scanner(new File ("input.txt"));
		//Scanner ns = new Scanner(System.in);
		
		size = ns.nextInt();
		while (size != 0) {
			char[][] Sudoku = new char[size][size]; 
			width = ns.nextInt(); 
			height = size/width; ns.nextLine();
			possibles = setPossibles(size);
			for (int x = 0; x < Sudoku.length; x++) {
				String line = ns.nextLine(); line = line.replaceAll(" ", "");
				for (int y = 0; y < Sudoku[0].length; y++) {
					Sudoku[x][y] = line.charAt(y);
				}
			}
			
			solve(Sudoku, 0, findNextZero(Sudoku[0]));
			if (containsAny(Sudoku)) {
				System.out.println("CAN'T! WON'T\n");
			}
			else {
				System.out.println("Solved:\n");
			}
			
			int countArr = 0;
			for (char[] array : Sudoku) {
				for (int i = 0; i < array.length; i++) {
					if (i >= array.length-1) {
						System.out.print(array[i] + "  ");
					}
					else if (i != 0 && i % width == 0) {
						System.out.print(" " + array[i] + " ");
					}
					else {
						System.out.print(array[i] + " ");
					}
				}
				System.out.println();
				countArr++;
				if (/*countArr != Sudoku.length && */countArr % height == 0) {
					System.out.println();
				}
			}
			size = ns.nextInt();
		}
		ns.close();
	}
	
	public static boolean solve(char[][] array, int row, int col) {
		if (array[0].length <= col) { return true; }
		boolean passing = false; 	
		for (int i = 0; !passing && i < possibles.length(); i++) {
			array[row][col] = possibles.charAt(i);
			if (promising(array, array[row][col], row, col)) {
				passing = next(array, row, col);
			}
		}
		if (!passing) { array[row][col] = '0'; }
		return passing;
	}
	
	public static boolean next(char[][] array, int row, int col) {
		if (row >= array.length-1 && col >= array[0].length-1) {
			return true;
		}
		while (col < array[0].length && array[row][col] != '0') {
			col++;
		}
		if (col >= array[0].length) { 
			for (int n = 1; row + n < array.length; n++) {
				col = findNextZero(array[row+n]);
				if (col <= array[0].length) {
					return solve(array, row+n,  col);
				}
			}
			return true;
		}
		return solve(array, row, col);
	}
	
	public static void deleteChars(char[][] array, int row, int col) {
		for (int i = 0; i < possibles.length(); i++) {
			if (!countRow(array, possibles.charAt(i), row)  ||
				!countCol(array, possibles.charAt(i), col)  ||
				!countRegion(array, possibles.charAt(i), row, col)) {
				String replace = Character.toString(possibles.charAt(i));
				possibles = possibles.replaceAll(replace, "");
			}
		}
	}
	
	public static int findNextZero(char[] array) {
		int i = 0;
		for (; i < array.length; i++) {
			if (array[i] == '0') {
				return i;
			}
		}
		return 0;
	}
	
	public static boolean promising(char[][] array, char c, int row, int col) {
		if (!countRow(array, c, row) ||
			!countCol(array, c, col) || 
			!countRegion(array, c, row, col)) {
				return false;
			}
		return true;
	}
	
	public static boolean countRow(char[][] array, char c, int row) {
		int count = 0;
		for (int i = 0; i < array[0].length; i++) {
			if (array[row][i] == c) { 
				count++; 
				if (count > 1) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static boolean countCol(char[][] array, char c, int col) {
		int count = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i][col] == c) { 
				count++; 
				if (count > 1) {
					return false;
				}
			}
		}
		return true;
	}
	 
	public static boolean countRegion(char[][] array, char check, int row, int col) {
		int count = 0;
		int subRow = (row/height)*height, rowEnd = subRow+height;
		int subCol = (col/width)*width, colEnd = subCol+width;
		for (int r = subRow; r < rowEnd; r++) {
			for (int c = subCol; c < colEnd; c++) {
				if (array[r][c] == check) {
					count++;
					if (count > 1) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public static String setPossibles(int parameter) {
		String poss = "";
		for (int i = 1; i <= parameter; i++) {
			if (i < 10) {
				poss += i;
			}
			else {
				poss += Integer.toString(i, parameter+1).toUpperCase();
			}
		}
		return poss;
	}
	
	public static boolean containsAny(char[][] array){
		for (int i = 0; i < array.length; i++ ) {
			for (int t = 0; t < array[0].length; t++) {
				if (array[i][t] == '0') {
					return true;
				}
			}
		}
		return false;
	}
	

}
