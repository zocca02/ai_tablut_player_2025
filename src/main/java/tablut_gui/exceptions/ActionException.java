package tablut_gui.exceptions;

import tablut_gui.model.Action;

/**
 * This exception represent an action with the wrong format
 * @author A.Piretti
 *
 */
public class ActionException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ActionException(tablut_gui.model.Action a)
	{
		super("The format of the action is not correct: "+a.toString());
	}

}
