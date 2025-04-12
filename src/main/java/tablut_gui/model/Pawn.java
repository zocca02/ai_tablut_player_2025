package tablut_gui.model;


public enum Pawn {
	//THRONE is kept only for compatibility with the server json
	EMPTY("O"), WHITE("W"), BLACK("B"), KING("K"), THRONE("T");
	private final String pawn;

	private Pawn(String s) {
		pawn = s;
	}
	
	public static Pawn fromString(String s) {
		switch (s) {
		case "O":
			return Pawn.EMPTY;
		case "W":
			return Pawn.WHITE;
		case "B":
			return Pawn.BLACK;
		case "K":
			return Pawn.KING;
		default:
			return null;
		}
	}

	public boolean equalsPawn(String otherPawn) {
		return (otherPawn == null) ? false : pawn.equals(otherPawn);
	}

	public String toString() {
		return pawn;
	}
	
	public boolean isEmpty() {
		return this==WHITE || this == BLACK || this == KING;
	}

	public boolean belongsTo(Player player){
		if(player == Player.WHITE)
			return this==WHITE || this==KING;
		else if(player == Player.BLACK)
			return this == BLACK;
		else
			return false;
	}
    public Player getOwner(){
        if(this==WHITE || this==THRONE) return Player.WHITE;
        else return Player.BLACK;
    }
}