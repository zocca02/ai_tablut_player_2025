package tablut_gui.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Cell {
	private CellType type;
	private Pawn pawn;
	private int i;
	private int j;

	public Cell(CellType type, int i, int j){
		this(type, Pawn.EMPTY, i, j);
	}

	public boolean hasPawn(){
		return pawn!=Pawn.EMPTY;
	}

	public boolean canBlock(){
		return hasPawn() || type==CellType.THRONE;
	}

	public Cell clone(){
		return new Cell(type, pawn, i, j);
	}

	@Override
	public boolean equals(Object o){
		if(o==null) return false;
		if(!(o instanceof Cell c)) return false;
		return i==c.getI() && j==c.getJ() && pawn == c.getPawn() && type == c.getType();
	}

}
