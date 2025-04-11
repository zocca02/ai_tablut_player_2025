package tablut_gui.model;

public enum Player {
	BLACK, WHITE;

	public Player getOpponent(){
		return this==BLACK ? WHITE : BLACK;
	}
}
