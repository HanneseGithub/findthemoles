// Whack a mole with 2 grids
package findthemoles;

import java.util.Random;
import java.util.Scanner;

public class FindTheMoles {

    // Instance variables
    static int score;
    static int molesLeft;
    static int molesToPlace;
    static int attemptsTotal;
    static int attemptsLeft;
    // Create a variable for in-game announcements
    static String announcement = "";
    static Integer[] gamemodes = new Integer[3];
    // Define the grid - multidimensional array (arrays inside array)
    static char[][] moleGrid;
    static char [][] userGrid;
    
    // In goes the array
    // Constructor
    public FindTheMoles(int numAttempts, int gridDimensions, int moles) {
	attemptsTotal = numAttempts;
	attemptsLeft = numAttempts;
	// Create a gridDimensions * gridDimensions 2 dimensional array
	moleGrid = new char[gridDimensions][gridDimensions];
	userGrid = new char[gridDimensions][gridDimensions];
	molesLeft = moles;
	molesToPlace = moles;
    }
    
    // Methods
    
    // Create both grids clean and with O's everywhere
    public void createCleanGrid() {
	// Run through each array's elements and fill the empty space
	for (int i = 0; i < moleGrid.length; i++) {
	    for (int j = 0; j < moleGrid[i].length; j++) {
		moleGrid[i][j] = 'O';
		userGrid[i][j] = 'O';
	    }
	}
    }
    
    // Random number generator
    public int randomNumber() {
	Random randomNumber = new Random();
	int high = moleGrid.length;
	return randomNumber.nextInt(high);
    }
    
    // Place mole on the moleGrid
    public boolean place(int x, int y) {
	if (moleGrid[x][y] == 'O') {
	    moleGrid[x][y] = 'M';
	    molesToPlace --;
	    return true;
	}
	else {
	    return false;
	}
    }
    
    // Print the grid with moles
    public void printMoleGrid() {
	for (int i = 0; i < moleGrid.length; i++) {
	    System.out.println(moleGrid[i]);
	}
    }
    
    // Print grid for user
    public void printUserGrid() {
	for (int i = 0; i < userGrid.length; i++) {
	    System.out.println(userGrid[i]);
	}
    }
    
    public static void main(String[] args) {
	// Ask for gamemode input
	Scanner gamemodeInput = new Scanner(System.in);
	
	System.out.println("Choose the game difficulty: 1 (easy) / 2 (medium) / 3 (hard)");
	int userModeInput = gamemodeInput.nextInt();
	
	// Give game parameters to object
	if (userModeInput == 1) {
	    gamemodes[0] = 7;
	    gamemodes[1] = 3;
	    gamemodes[2] = 3;
	} else if (userModeInput == 2) {
	    gamemodes[0] = 12;
	    gamemodes[1] = 4;
	    gamemodes[2] = 7; 
	} else {
	    gamemodes[0] = 20;
	    gamemodes[1] = 5;
	    gamemodes[2] = 12; 
	}
	
	// Create the game
	FindTheMoles findTheMolesGame = new FindTheMoles(gamemodes[0], gamemodes[1], gamemodes[2]);

	// Clean state for both grids
	findTheMolesGame.createCleanGrid();
	
	// Create a loop for placing all the moles
	int moleValidator = 0;
	while (moleValidator < molesToPlace) {
	    findTheMolesGame.place(findTheMolesGame.randomNumber(), findTheMolesGame.randomNumber());
	}
	
	// Loop the entire game
	int roundCounter = 0;
	while (roundCounter < attemptsLeft && roundCounter < molesLeft) {
	    	// Create a scanner for user searching coordinates
	    	Scanner coordinates = new Scanner(System.in);
        	int[] userCoordinates = new int[2];
        	
        	findTheMolesGame.printUserGrid();
        	System.out.println("Moles left: "+ molesLeft);
        	System.out.println("Attempts left: "+ attemptsLeft);
        	System.out.println(announcement);
        	
        	System.out.println("Where would you like to search this time? (Enter x-coordinate, then y-coordinate): ");
        	for (int i = 0; i < userCoordinates.length; i++) {
        	    // Subtract -1 the user can enter coordinates logically, not by array
        	    userCoordinates[i] = coordinates.nextInt() - 1;
        	}
        	
        	// Surrender by entering -1 & -1 (-2 here because we alter the user input by - 1)
        	if (userCoordinates[0] == -2 && userCoordinates[1] == -2) {
        	    break;
        	}
        	
        	// If moleGrid has "M" and userGrid has 'O'
        	if (moleGrid[userCoordinates[0]][userCoordinates[1]] == 'M' && userGrid[userCoordinates[0]][userCoordinates[1]] == 'O') {
        	    // Change user grid's coordinates to 'M'
        	    userGrid[userCoordinates[0]][userCoordinates[1]] = 'M';
        	    molesLeft --;
        	    attemptsLeft --;
        	    announcement = "You found a mole!";
        	    score += 2500;
        	// If moleGrid has 'O' and userGrid has 'O'    
        	} else if (moleGrid[userCoordinates[0]][userCoordinates[1]] == 'O' && userGrid[userCoordinates[0]][userCoordinates[1]] == 'O') {
        	    userGrid[userCoordinates[0]][userCoordinates[1]] = '*';
        	    attemptsLeft --;
        	    announcement = "You missed!";
        	    score -= 500;
        	} else {
        	    announcement = "You've already searched this spot! Try another one!";
        	}
	}
	
	// End of the game
	System.out.println("Game Over!");
	// Win
	if (molesLeft == 0 && attemptsLeft >= 0) {
	    System.out.println("You won! You are an absolute madlad!");
	    System.out.println("Here's your grid:");
	    findTheMolesGame.printUserGrid();
	    System.out.println("You beat the game in " + (attemptsTotal - attemptsLeft) + " attempts.");
	    score += attemptsLeft * 1000;
	// Lose    
	} else if (attemptsLeft == 0 && molesLeft > 0) {
	    System.out.println("You lost! Better luck next time!"); 
	    System.out.println("Here were the placed moles:");
	    findTheMolesGame.printMoleGrid();
	// Surrender    
	} else {
	    System.out.println("Only peasants surrender! What is wrong with you?");  
	    System.out.println("Here were the placed moles:");
	    findTheMolesGame.printMoleGrid();
	}
	System.out.println("Your total score is: "+ score + " points.");
    }
}

//Score system:
// Find a mole = +2500 points
// Miss = -500 points
// Attempts left after game = +1000 points