package tablut_gui.model;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidParameterException;

@Getter
@Setter
public class Action implements Serializable {

	private static final long serialVersionUID = 1L;

	private String from;
	private String to;

	private Player turn;



	public Action(String from, String to, Player t) throws IOException {
		if (from.length() != 2 || to.length() != 2) {
			throw new InvalidParameterException("the FROM and the TO string must have length=2");
		} else {
			this.from = from;
			this.to = to;
			this.turn = t;
		}
	}

	public Action(int iFrom, int jFrom, int iTo, int jTo, Player p){
		if(iFrom<0 || iFrom>8 || jFrom<0 || jFrom>8 || iTo<0 || iTo>8 || jTo<0 || jTo>8)
			throw new InvalidParameterException("Wrong parameters for from and to indexes");

		this.from = indexToLetter(jFrom)+""+(iFrom+1);
		this.to = indexToLetter(jTo)+""+(iTo+1);
		this.turn = p;
	}


	public String toString() {
		return "Turn: " + this.turn + " " + "Pawn from " + from + " to " + to;
	}

	public int getColumnFrom() {
		return Character.toLowerCase(this.from.charAt(0)) - 97;
	}

	public int getColumnTo() {
		return Character.toLowerCase(this.to.charAt(0)) - 97;
	}

	public int getRowFrom() {
		return Integer.parseInt(this.from.charAt(1) + "") - 1;
	}

	public int getRowTo() {
		return Integer.parseInt(this.to.charAt(1) + "") - 1;
	}

	private char indexToLetter(int i){
		return (char)('A'+i);
	}
}
