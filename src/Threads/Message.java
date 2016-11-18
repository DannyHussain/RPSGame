package Threads;

import Additional.Shape;

/**
 * The message class is essentially what the player thread
 * will put into the BlockingQueue of the referee, which includes the 
 * shape, a link to an MVar for return communication, a unique playerId and the player itself.
 */

public class Message {

	//Fields
	private Shape shape;			//Shape that is selected by each thread
	private MVar <String> result;	//MVar which is used for the return communication
	private long playerId;			//Unique playerId for each thread
	private Player p;				//Reference to the player thread

	//Constructors 

	//Custom constructor for the message which specifies the parameters it will take 
	public Message(Player p, long playerid, Shape s, MVar<String> r){
		this.p = p;
		this.setShape(s);
		this.setPlayerId(playerid);
		this.setResult(r);
	}

	//Methods

	/*
	 * Method used to get the player
	 * @return p
	 */
	public Player getPlayer(){
		return p;
	}

	/*
	 * Method used to get the shape
	 * @return shape
	 */
	public Shape getShape() {

		return shape;
	}

	/*
	 * Method used to get the player
	 * @param Shape 
	 */
	public void setShape(Shape shape) {
		this.shape = shape;
	}

	/*
	 * Method used to get the result 
	 * @return result
	 */	
	public MVar <String> getResult() {
		return result;
	}

	/*
	 * Method used to set the result
	 */
	public void setResult(MVar<String>result) {
		this.result = result;
	}

	/*
	 * Method used to get the playerId
	 * @return playerId
	 */
	public long getPlayerId() {
		return playerId;
	}

	/*
	 * Method used to set the playerId
	 */
	public void setPlayerId(long playerId) {
		this.playerId = playerId;
	}
}
