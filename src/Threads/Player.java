package Threads;

import Additional.Shape;
import Main.Game;

/**
 * The player class sets up the fields and the constructors for each
 * player thread, which includes the players message that contains
 * the players shape and a link to an MVar. When each player thread
 * has exhausted all of their turns, the player prints out its final score
 * which includes their wins and losses and the gracefully stops running. 
 */

public class Player implements Runnable {	//Player implements the runnable interface to be able to use the run method that the thread will execute


	//Fields
	private int games = Game.getNTURNS();
	private int NTURNS; 							//Number of turns that each player will have
	private int playerId;							//Each player has a unique id
	private int win, draw, lose;					//Used as counters for each thread to update it's internal state 
	private MVar<String> mvar = new MVar<String>();	//MVar of type string which is used for return communication from the referee
	private Referee referee;						//Referee object to pass the players message to
	private Shape s;								//Shape that is selected by each thread
	private volatile boolean isRunning = false;		//used to close the threads gracefully when their turns are finished


	//Constructors

	//Custom constructor for the player, which is called in the main program
	public Player(int playerid,Referee referee, int NTURNS) {	
		this.playerId = playerid;
		this.referee = referee;
		this.NTURNS = NTURNS;
	}


	//Methods

	/*
	 * This method is used to return each players unique id
	 * @return playerId - unique id.
	 */
	public int getId(){
		return playerId;	
	}

	/*
	 * This method is used to return the MVar of type string
	 */
	public MVar<String> getMVar(){
		return mvar;
	}

	/* 
	 * This method used by player threads to calculate their scores, each counter is incremented 
	 * based upon the result given by the referee thread 
	 */
	public void incrementResult() {
		String s = mvar.takeMVar();		//take from the MVar, to be able to use it for comparison
		if(s ==  "Win"){				//if the taken result matches the string increment counter win
			win++;
		}
		else if(s == "Lose"){			//if the taken result matches the string increment counter lose
			lose++;
		}
		else if(s == "Draw"){			//if the taken result matches the string increment counter draw
			draw++;
		}
	}

	/*
	 * The run method taken from the runnable interface is used to 
	 * describe what each thread is supposed to do. 
	 */
	@Override
	public void run() {
		while(!isRunning)	{									//While the run method is not equal to false 
			Message m = new Message(this, playerId, s, mvar);	//Create a new instance of Message which contains the player thread, the unique id, the shape, and an MVar link
			try {											
				for(int i = 0; i < NTURNS; NTURNS--){			//For every NTURNS execute the following code, then decrease the NTURNS by 1
					m.setShape(Shape.Random());					//The shape in the message is given the value of a random shape (called from the Shape class)
					referee.addtoQueue(m);						//Add to queue method called from the referee class allows the thread to add its message to the queue
					Thread.sleep(10);							//Thread sleeps for the given milliseconds, which generally suspends the execution for the time specified
				}
				incrementResult();								//Method called within this class to allow each thread to take from the MVar and update its internal state (keep count of game outcomes)
				games--;										//Each thread decrements the number of games it has left
				if(games == 0){									//When the games are exhausted, each thread prints in final score for the outcome of its games.
					threadStopped();							//Inner class method called to print the final scores for each thread
					close(); 									//Thread is closed gracefully after it has exhausted all of its turns
				}
			}catch (InterruptedException e) {				
				e.printStackTrace();							//Exception handler prints the stack trace if an exception occurs
			}
		}
	}

	/*
	 * This method will simply print a message when the player thread
	 * has exhausted all of their NTURNS
	 */
	public void threadStopped(){
		Message m = new Message(this, playerId, s, mvar);
		System.out.println("\t\t\t\t\t\tPlayer "  + m.getPlayer().getId() + " - I have used all of my turns here are my scores --> " 
				+ "[" + win + " Wins," + draw + " Draws," + lose + " Losses]");
	}


	/*
	 * This method is used to stop each thread when their turns have finished
	 * in other word when the isRunning is equal to true. 
	 */
	public void close() {
		isRunning = true;
	}

}





