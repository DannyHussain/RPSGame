package rockpaperscissors;
import java.util.Random;

/**
 * The shape class is simply an ENUM of rock, paper or scissors
 * which will be selected by each player thread randomly before
 * passing their message to the of the referee.
 */

public enum Shape {
	ROCK, PAPER, SCISSORS;					//The three ENUM types, rock paper and scissors

	//Method

	/*
	 * This method is used to randomly selected a shape
	 * @return s - random shape
	 */

	public static Shape Random(){				
		Shape[] s = Shape.values();				//Setting the variable to the value of the shapes
		Random selection = new Random();		//The random method is used to randomly select one of the ENUMS
		return s[selection.nextInt(s.length)];	//returns the random shape
	}


}

