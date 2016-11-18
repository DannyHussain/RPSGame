package Threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import Additional.Result;


/**
 * The referee class maintains a BlockingQueue of messages in which player threads
 * waiting to play, it then selects two of them and allows them to show their respected shapes
 * then determines the winner and notifies each thread individually of the result. When
 * the games are over the referee thread shuts itself down gracefully
 */

public class Referee implements Runnable{


	//Fields
	private static BlockingQueue<Message> queue = new LinkedBlockingQueue<>();	//Creating a blocking queue of type message that will maintain the player messages
	private int win = 2, lose = 1, draw = 0;									//Assigning values to each variable to be able to determine the result easily
	private MVar<String> mvar;													//MVar of type string to return the outcome of the match to the player threads
	private Message Player1, Player2;											//The two player variables that will be used for each game
	private volatile boolean finished = false;									//used to close the threads gracefully when their turns are finished

	//Constructors
	//Default constructor for the referee, which is called in the main program
	public Referee (){} 	


	//Methods

	/*
	 * This method is used to allow the player threads to 
	 * add their messages to the queue, which can then be removed and compared
	 */
	public void addtoQueue(Message q){
		queue.add(q);
	}

	/*
	 * This method is used to get the MVar
	 * @return the taken result from the MVar as a string 
	 */
	public String getMVar(){
		return mvar.takeMVar();
	}


	/*
	 * The run method taken from the runnable interface is used to 
	 * describe what each thread is supposed to do. 
	 */
	@Override
	public void run() {		
		mvar = new MVar<String>();			//Creating a new instance of the MVar of type string
		while(!finished)					//While the run method is not equal to false 
			try{
				Player1 = queue.take();		//Referee takes Player1 from the queue
				Player2 = queue.take();		//Referee takes Player2 from the queue

				int result = Result.getResult(Player1.getShape(), Player2.getShape());	//Get result method called from the Result class, which is used to compare the two shapes each player selects

				if(result == win) {										//if the result is equal to win
					getDisplay1();										//Print game players and result to the terminal by calling the inner class method
					Player1.getPlayer().getMVar().putMVar("Win");		//Notify Player1 of the outcome of this game by getting the player and putting the result into an MVar
					Player2.getPlayer().getMVar().putMVar("Lose");		//Notify Player2 of the outcome of this game by getting the player and putting the result into an MVar
				} 

				else if(result == lose) {								//if the result is equal to lose
					getDisplay2();										//Print game players and result to the terminal by calling the inner class method
					Player1.getPlayer().getMVar().putMVar("Lose");		//Notify Player1 of the outcome of this game by getting the player and putting the result into an MVar
					Player2.getPlayer().getMVar().putMVar("Win");		//Notify Player2 of the outcome of this game by getting the player and putting the result into an MVar
				} 

				else if(result == draw){								//if the result is equal to draw
					getDisplay3();										//Print game players and result to the terminal by calling the inner class method
					Player1.getPlayer().getMVar().putMVar("Draw");		//Notify Player1 of the outcome of this game by getting the player and putting the result into an MVar
					Player2.getPlayer().getMVar().putMVar("Draw");		//Notify Player2 of the outcome of this game by getting the player and putting the result into an MVar
				}
				Thread.sleep(1000);										//Thread sleeps for the given milliseconds, which generally suspends the execution for the time specified
			}catch (InterruptedException e) {
				e.printStackTrace();									//Exception handler prints the stack trace if an exception occurs

			}
	}

	/*
	 * The following methods are all used for the display 
	 * each display matches the respected outcome of the game
	 */

	public void getDisplay1(){
		System.out.println("*******************************************");
		System.out.println("_|/_ \t\t\t\t ///");
		System.out.println("(_oo)" + "\t\t\t\t(oo_)");
		System.out.println(" |"   + "\t\t\t\t   |");
		System.out.println(" |-3" + "\t\t\t\t " + "E-|");
		System.out.println(" |"    + "\t\t\t\t   |");
		System.out.println(" ll"   + "\t\t\t\t  ll");
		System.out.println("\nPlayer " + Player1.getPlayer().getId() + "[" + Player1.getShape() + "]" + "   <---> "  +  "[" + Player2.getShape() + "]" + "Player " + Player2.getPlayer().getId());  
		System.out.print("\nResult: Player " + Player1.getPlayer().getId() + " wins\n");
		System.out.println("\n*******************************************");
	}

	public void getDisplay2(){
		System.out.println("*******************************************");
		System.out.println(" /// \t\t\t\t _|/_");
		System.out.println("(_oo)" + "\t\t\t\t(oo_)");
		System.out.println(" |"   + "\t\t\t\t   |");
		System.out.println(" |-E" + "\t\t\t\t " + "3-|");
		System.out.println(" |"    + "\t\t\t\t   |");
		System.out.println(" ll"   + "\t\t\t\t  ll");
		System.out.println("\nPlayer " + Player1.getPlayer().getId() + "[" + Player1.getShape() + "]" + "   <---> " +  "[" + Player2.getShape() + "]" + "Player " + Player2.getPlayer().getId());  
		System.out.print("\nResult: Player " + Player2.getPlayer().getId() + " wins\n");
		System.out.println("\n*******************************************");
	}


	public void getDisplay3(){
		System.out.println("*******************************************");
		System.out.println(" /// \t\t\t\t ///");
		System.out.println("(_oo)" + "\t\t\t\t(oo_)");
		System.out.println(" |"   + "\t\t\t\t   |");
		System.out.println(" |-3" + "\t\t\t\t " + "3-|");
		System.out.println(" |"    + "\t\t\t\t   |");
		System.out.println(" ll"   + "\t\t\t\t  ll");
		System.out.println("\nPlayer " + Player1.getPlayer().getId() + "[" + Player1.getShape() + "]" + "   <---> " + "[" + Player2.getShape() + "]" + "Player " + Player2.getPlayer().getId());    
		System.out.print("\nResult: " + "Its a bore draw!\n");
		System.out.println("\n*******************************************");
	}

	/*
	 * This method is used to stop the referee thread when each player thread 
	 * has exhausted all of their NTURNS, and then prints a message
	 */
	public void close()
	{
		finished = true;
		System.out.println("\n\t\t\t\t\t\t\t\t\t[Referee thread stopped]");
		System.out.println("\t\t\t\t\t\t\t\t\t\t o/" );
		System.out.println("\t\t\t\t\t\t\t\t\t\t/| " );  
		System.out.println("\t\t\t\t\t\t\t\t\t\t/ L\n");

		System.out.print("Thank you for your attention and take care of yourself");
	}

}

