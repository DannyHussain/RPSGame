package rockpaperscissors;

/**
 * The result class is simply used to determine the result of each game
 * and is called from the referee class
 */

public class Result {

	//Fields
	private static int result, lose, draw, win;	

	//Method

	/*
	 * This method is used to compare the two shapes of each player thread
	 * and then returns the result. 
	 */
	public static int getResult(Shape FirstShape, Shape SecondShape) {
		draw = 0;		
		lose = 1;
		win = 2;

		/*
		 * Each statement in the if-else is based upon the traditional rules of the game
		 * rock beats scissors, scissors beats paper, paper beats rock, anything else is a draw
		 */
		if(FirstShape == Shape.ROCK && SecondShape == Shape.PAPER) {		
			result = lose;														
		} 
		else if(FirstShape == Shape.ROCK && SecondShape == Shape.SCISSORS) {
			result = win;
		} 
		else if(FirstShape == Shape.ROCK && SecondShape == Shape.ROCK) {
			result = draw;
		}
		else if(FirstShape == Shape.PAPER && SecondShape == Shape.PAPER) {
			result = draw;
		} 
		else if(FirstShape == Shape.PAPER && SecondShape == Shape.SCISSORS) {
			result = lose;
		} 
		else if(FirstShape == Shape.PAPER && SecondShape == Shape.ROCK) {
			result = win;
		}
		else if(FirstShape == Shape.SCISSORS && SecondShape == Shape.PAPER) {
			result = win;
		} 
		else if(FirstShape == Shape.SCISSORS && SecondShape == Shape.SCISSORS) {
			result = draw;
		} 
		else if(FirstShape == Shape.SCISSORS && SecondShape == Shape.ROCK) {
			result = lose;
		}
		return result;


	}
}


