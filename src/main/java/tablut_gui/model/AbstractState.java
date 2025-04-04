package tablut_gui.model;

import lombok.Getter;
import lombok.Setter;
import tablut_gui.exceptions.PawnException;
import tablut_gui.exceptions.SelectionException;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
public abstract class AbstractState implements State {


	protected Cell[][] board;
	protected Player turn;
	protected GameState gameState;

	protected int[][] exitCells;
	protected int[][] blackCells;

	public AbstractState() {
		super();
	}

	public String boardString() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board.length; j++) {
				result.append(this.board[i][j].toString());
				if (j == 8) {
					result.append("\n");
				}
			}
		}
		return result.toString();
	}

	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();

		// board
		result.append("");
		result.append(this.boardString());

		result.append("-");
		result.append("\n");

		// TURNO
		result.append(this.turn.toString());

		return result.toString();
	}

	public String toLinearString() {
		StringBuffer result = new StringBuffer();

		// board
		result.append("");
		result.append(this.boardString().replace("\n", ""));
		result.append(this.turn.toString());

		return result.toString();
	}

	public Pawn getPawn(int row, int column) {
		return this.board[row][column].getPawn();
	}

	public Cell getCell(int row, int column) {
		return this.board[row][column];
	}

	public void removePawn(int row, int column) {
		this.board[row][column].setPawn(Pawn.EMPTY);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		AbstractState other = (AbstractState) obj;
		if (this.board == null) {
			if (other.board != null)
				return false;
		} else {
			if (other.board == null)
				return false;
			if (this.board.length != other.board.length)
				return false;
			if (this.board[0].length != other.board[0].length)
				return false;
			for (int i = 0; i < other.board.length; i++)
				for (int j = 0; j < other.board[i].length; j++)
					if (!this.board[i][j].equals(other.board[i][j]))
						return false;
		}
		if (this.turn != other.turn)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.board == null) ? 0 : deepHashCode(board));
		result = prime * result + ((this.turn == null) ? 0 : this.turn.hashCode());
		return result;
	}
	
	private static <T> int deepHashCode(T[][] matrix) {
		int tmp[] = new int[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			tmp[i] = Arrays.hashCode(matrix[i]);
		}
		return Arrays.hashCode(tmp);
	}

	public String getBox(int row, int column) {
		String ret;
		char col = (char) (column + 97);
		ret = col + "" + (row + 1);
		return ret;
	}




	public AbstractState clone() {
		Class<? extends AbstractState> stateclass = this.getClass();
		Constructor<? extends AbstractState> cons = null;
		AbstractState result = null;
		try {
			cons = stateclass.getConstructor(stateclass);
			result = cons.newInstance(new Object[0]);
		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

		Cell[][] oldBoard = this.getBoard();
		Cell[][] newBoard = result.getBoard();

		for (int i = 0; i < this.board.length; i++) {
			for (int j = 0; j < this.board[i].length; j++) {
				newBoard[i][j] = oldBoard[i][j];
			}
		}

		result.setBoard(newBoard);
		result.setTurn(this.turn);
		return result;
	}

	public int getNumberOf(Pawn color) {
		int count = 0;
        for (Cell[] cells : board) {
            for (Cell cell : cells) {
                if (cell.getPawn() == color)
                    count++;
            }
        }
		return count;
	}


	public abstract List<Cell> legalMovesFor(int i, int j);

	public abstract boolean isLegalTo(int iFrom, int jFrom, int iTo, int jTo);



}
