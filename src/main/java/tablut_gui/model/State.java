package tablut_gui.model;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public interface State {


    public String boardString();
    public String toLinearString();

    public Pawn getPawn(int row, int column);
    public Cell getCell(int row, int column);
    public GameState getGameState();
    public void setGameState(GameState g);
    public void removePawn(int row, int column);


    public String getBox(int row, int column);
    public State clone();

    public Cell[][] getBoard();
    public Player getTurn();

    public List<Cell> legalMovesFor(int i, int j);
    public List<Action> getAllLegalMovesFor(Player player);
    public boolean isLegalTo(int iFrom, int jFrom, int iTo, int jTo);

    public State applyMove(Action action);
    public boolean equals(Object s);

    public int getPawnNumber(Player player);
    public int getKingImmediateExit();
    public Cell getKingCell();
    public List<Cell> getNeighbors(Cell c);
    public List<Cell> getNeighbors(int i, int j);
}
