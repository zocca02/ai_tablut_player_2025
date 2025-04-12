package tablut_gui.model;

public enum CellType {
	NORMAL, EXIT, BLACK_BASE, THRONE;

	public boolean isBlockingCell(){
		return this==BLACK_BASE || this==THRONE;
	}
}
