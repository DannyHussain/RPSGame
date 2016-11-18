package rockpaperscissors;

/**
 * The main class to run the program sets up a referee 
 * and the specified players along with their turns and then 
 * sets them to run as concurrent threads. Once the games are over the 
 * referee thread is notified to close itself down
 */

public class Game {

	private static int NPLAYERS = 10; 	//NPlayers specified, in this instance 10 players 
	private static int NTURNS = 10;		//NTurns specified, so each player will have a 100 turns

	public static void main (String[] args)
	{
		Referee referee = new Referee(); 				// Creates a new instance of the referee
		Thread[] playerThread = new Thread[NPLAYERS];   // Creates a new player threads of NPlayers
		Thread refereeThread = new Thread(referee);	    // Create a new referee thread
		refereeThread.start(); 						    // The referee thread is started first by calling the start() method

		for(int x = 0; x<NPLAYERS; x++){ 				//For every instance that x is less than NPLAYERS
			playerThread[x] = new Thread(new Player(x+1,referee,getNTURNS()));	//Player thread is created using a custom constructor, the x+1 determines the players thread number
			playerThread[x].start();					// the player thread is started by calling the start() method
		}

		try 
		{
			for(int x = 0; x<NPLAYERS; x++)				//For every instance that x is less than NPLAYERS
				playerThread[x].join();					//The join method is called for each thread so that one waits for the other to end
		} catch(InterruptedException e) {
			e.printStackTrace();						//Exception handler prints the stack trace if an exception occurs
		}

		referee.close();								//Referee's close method is called to stop the thread gracefully
	}

	public static int getNTURNS() {
		return NTURNS;
	}
}


