package tablut_gui.exceptions;

import tablut_gui.model.Action;

/**
 * This exception represent an action that is trying to move a pawn into the throne
 * @author A.Piretti
 *
 */
public class ThroneException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ThroneException(Action a)
	{
		super("Player "+a.getTurn().toString()+" is tryng to go into the castle: "+a.toString());
	}	

}
