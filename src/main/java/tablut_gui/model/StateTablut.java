package tablut_gui.model;


import tablut_gui.dto.StateDTO;
import tablut_gui.exceptions.SelectionException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * This class represents a state of a match of Tablut (classical or second
 * version)
 *
 * @author A.Piretti
 *
 */
public class StateTablut extends AbstractState implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final int[][] exitCells = new int[][]{
            {0, 1}, {0, 2}, {0, 6}, {0, 7},
            {1, 0}, {1, 8}, {2, 0}, {2, 8},
            {6, 0}, {6, 8}, {7, 0}, {7, 8},
            {8, 1}, {8, 2}, {8, 6}, {8, 7}};

    private static final int[][] blackCells = new int[][]{
            {0, 3}, {0, 4}, {0, 5}, {1, 4},
            {3, 0}, {4, 0}, {5, 0}, {4, 1},
            {3, 8}, {4, 8}, {5, 8}, {4, 7},
            {8, 3}, {8, 4}, {8, 5}, {7, 4}};

    private static final int[][] whiteStartPositions = new int[][] {
            {2, 4}, {3, 4}, {5, 4}, {6, 4},
            {4, 2}, {4, 3}, {4, 5}, {4, 6}};

    private static final int[][] blackStartPositions = new int[][] {
            {0, 3}, {0, 4}, {0, 5}, {1, 4},
            {8, 3}, {8, 4}, {8, 5}, {7, 4},
            {3, 0}, {4, 0}, {5, 0}, {4, 1},
            {3, 8}, {4, 8}, {5, 8}, {4, 7}
    };

    public static StateTablut empty(){
        return new StateTablut();
    }

    public static StateTablut startBoard(){
        StateTablut st = StateTablut.empty();

        st.getBoard()[4][4].setPawn(Pawn.KING);

        for(var point : whiteStartPositions){
            st.getBoard()[point[0]][point[1]].setPawn(Pawn.WHITE);
        }

        for(var point : blackStartPositions){
            st.getBoard()[point[0]][point[1]].setPawn(Pawn.BLACK);
        }

        return st;
    }

    public static StateTablut fromDTO(StateDTO stateDTO){
        StateTablut st = StateTablut.empty();

        st.setTurn(stateDTO.getTurn());

        for(var row : st.getBoard()){
            for(var cell : row){
                cell.setPawn(stateDTO.getBoard()[cell.getI()][cell.getJ()]);
            }
        }

        return st;
    }


    private StateTablut() {
        super();
        this.board = new Cell[9][9];

        board[4][4] = new Cell(CellType.THRONE, 4, 4);

        for(var point : exitCells){
            board[point[0]][point[1]] = new Cell(CellType.EXIT, point[0], point[1]);
        }

        for(var point : blackCells){
            board[point[0]][point[1]] = new Cell(CellType.BLACK_BASE, point[0], point[1]);
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board[i][j]==null)
                    this.board[i][j] = new Cell(CellType.NORMAL, i, j);
            }
        }


        this.turn = Player.WHITE;

    }

    public StateTablut clone() {
        StateTablut result = new StateTablut();

        Cell[][] oldboard = this.getBoard();
        Cell[][] newboard = result.getBoard();

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[i].length; j++) {
                newboard[i][j] = oldboard[i][j];
            }
        }

        result.setBoard(newboard);
        result.setTurn(this.turn);
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (this.getClass() != obj.getClass())
            return false;
        StateTablut other = (StateTablut) obj;
        System.out.println("b");
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
            System.out.println("a");
            for (int i = 0; i < other.board.length; i++)
                for (int j = 0; j < other.board[i].length; j++)
                    if (!this.board[i][j].equals(other.board[i][j]))
                        return false;
            System.out.println("c");
        }
        if (this.turn != other.turn)
            return false;
        return true;
    }



    public List<Cell> legalMovesFor(int i, int j){
        if(!board[i][j].hasPawn()) throw new SelectionException("No pawn in cell "+i+", "+j);

        List<Cell> legalMoves = new ArrayList<>();

        for(int di=1; i+di<=8; di++) {
            if(board[i+di][j].canBlock() ||
                    (board[i+di][j].getType()==CellType.BLACK_BASE && (!canEnterInBlackBase(i, j) || di>2))) break;
            legalMoves.add(board[i+di][j]);
        }
        for(int di=-1; i+di>=0; di--) {
            if(board[i+di][j].canBlock()||
                    (board[i+di][j].getType()==CellType.BLACK_BASE && (!canEnterInBlackBase(i, j) || di<-2))) break;
            legalMoves.add(board[i+di][j]);
        }
        for(int dj=1; j+dj<=8; dj++) {
            if(board[i][j+dj].canBlock()||
                    (board[i][j+dj].getType()==CellType.BLACK_BASE && (!canEnterInBlackBase(i, j) || dj>2))) break;
            legalMoves.add(board[i][j+dj]);
        }
        for(int dj=-1; j+dj>=0; dj--) {
            if(board[i][j+dj].canBlock()||
                    (board[i][j+dj].getType()==CellType.BLACK_BASE && (!canEnterInBlackBase(i, j) || dj<-2))) break;
            legalMoves.add(board[i][j+dj]);
        }

        return legalMoves;
    }

    public boolean isLegalTo(int iFrom, int jFrom, int iTo, int jTo){
        return legalMovesFor(iFrom, jFrom).stream().anyMatch(c -> c.getI() == iTo && c.getJ() == jTo);
    }

    @Override
    public State applyMove(Action action) {
        int iFrom = action.getRowFrom();
        int jFrom = action.getColumnFrom();
        int iTo = action.getRowTo();
        int jTo = action.getColumnTo();

        StateTablut newState = this.clone();
        Pawn p = newState.getBoard()[iFrom][jFrom].getPawn();
        newState.getBoard()[iFrom][jFrom].setPawn(Pawn.EMPTY);
        newState.getBoard()[iTo][jTo].setPawn(p);

        if(newState.getTurn()==Player.WHITE)
            updateWhiteMove(iFrom, jFrom, iTo, jTo, p, newState);
        else
            updateBlackMove(iFrom, jFrom, iTo, jTo, p, newState);

        newState.setTurn(newState.getTurn().getOpponent());
        return newState;
    }



    private void updateWhiteMove(int iFrom, int jFrom, int iTo, int jTo, Pawn p, StateTablut newState){

        //capture
        checkCaptureFor(iTo, jTo, newState, Player.BLACK);

        //white win
        if(p==Pawn.KING){
            if(Arrays.stream(exitCells).anyMatch(c -> c[0]==iTo && c[1]==jTo))
                newState.setGameState(GameState.WHITE_WIN);
        }
    }


    private void updateBlackMove(int iFrom, int jFrom, int iTo, int jTo, Pawn p, StateTablut newState){

        //capture
        checkCaptureFor(iTo, jTo, newState, Player.BLACK);

        //black win
        Cell kingCell = newState.getKingCell();
        int iK = kingCell.getI();
        int jK = kingCell.getJ();
        boolean win=false;
        List<Cell> kingNeighbors = newState.getNeighbors(kingCell);
        if(kingNeighbors.stream().anyMatch(c -> c.getI()==iTo && c.getJ()==jTo)){
            if(kingCell.getType() == CellType.THRONE){
                win = newState.getPawn(3, 4)==Pawn.BLACK
                        && newState.getPawn(5, 4)==Pawn.BLACK
                        && newState.getPawn(4, 3)==Pawn.BLACK
                        && newState.getPawn(4, 5)==Pawn.BLACK;
            }
            else if(kingNeighbors.stream().anyMatch(c -> c.getType()==CellType.THRONE)){
                win=true;
                for(var n : kingNeighbors){
                    win = win && (n.getType()==CellType.THRONE || n.getPawn().belongsTo(Player.BLACK));
                }
            }
            else{
                win = ((newState.getPawn(iK-1, jK).belongsTo(Player.BLACK) || newState.getBoard()[iK-1][jK].getType()==CellType.BLACK_BASE)
                        && (newState.getPawn(iK+1, jK).belongsTo(Player.BLACK) || newState.getBoard()[iK+1][jK].getType()==CellType.BLACK_BASE))
                        ||
                        ((newState.getPawn(iK-1, jK).belongsTo(Player.BLACK) || newState.getBoard()[iK][jK-1].getType()==CellType.BLACK_BASE)
                                && (newState.getPawn(iK+1, jK).belongsTo(Player.BLACK) || newState.getBoard()[iK][jK+1].getType()==CellType.BLACK_BASE));
            }

            if(win)
                newState.setGameState(GameState.BLACK_WIN);

        }
    }

    private void checkCaptureFor(int iTo, int jTo, StateTablut newState, Player player){
        List<Cell> pawnNeighbors = newState.getNeighbors(iTo, jTo);
        for(var c : pawnNeighbors){
            if(c.getPawn()!=Pawn.KING && c.getPawn().belongsTo(player.getOpponent())){
                int di = c.getI()-iTo;
                int dj = c.getJ()-jTo;

                if(di!=0 && iTo+2*di>=0 && iTo+2*di<=8){
                    Cell other = newState.getBoard()[iTo+2*di][jTo];
                    if(other.getPawn().belongsTo(player) || other.getType() == CellType.THRONE
                            || (other.getType() == CellType.BLACK_BASE && c.getType()!=CellType.BLACK_BASE)){
                        c.setPawn(Pawn.EMPTY);
                        continue;
                    }

                }

                if(dj!=0 && jTo+2*dj>=0 && jTo+2*dj<=8){
                    Cell other = newState.getBoard()[iTo][jTo+2*dj];
                    if(other.getPawn().belongsTo(player) || other.getType() == CellType.THRONE
                            || (other.getType() == CellType.BLACK_BASE && c.getType()!=CellType.BLACK_BASE)){
                        c.setPawn(Pawn.EMPTY);
                        continue;
                    }
                }

            }
        }
    }

    public List<Cell> getNeighbors(Cell c){
        int i = c.getI();
        int j = c.getJ();
        var l = new ArrayList<Cell>();
        if(i+1<=8)
            l.add(board[i+1][j]);
        if(j+1<=8)
            l.add(board[i][j+1]);
        if(i-1>=0)
            l.add(board[i-1][j]);
        if(j-1>=0)
            l.add(board[i][j-1]);
        return l;
    }

    public List<Cell> getNeighbors(int i, int j){
        var l = new ArrayList<Cell>();
        if(i+1<=8)
            l.add(board[i+1][j]);
        if(j+1<=8)
            l.add(board[i][j+1]);
        if(i-1>=0)
            l.add(board[i-1][j]);
        if(j-1>=0)
            l.add(board[i][j-1]);
        return l;
    }

    public Cell getKingCell(){
        for(var row : board){
            for(var c : row){
                if(c.getPawn()==Pawn.KING) return c;
            }
        }
        return null;
    }

    private boolean canEnterInBlackBase(int i, int j){
        return board[i][j].getPawn() == Pawn.BLACK && board[i][j].getType() == CellType.BLACK_BASE;
    }

    public List<Action> getAllLegalMovesFor(Player player){
        List<Action> legalMoves = new ArrayList<>();
        for(var row : board){
            for(var fromCell : row){
                if(fromCell.getPawn().belongsTo(player)){
                    for(var toCell : legalMovesFor(fromCell.getI(), fromCell.getJ())){
                        legalMoves.add(new Action(fromCell.getI(), fromCell.getJ(), toCell.getI(), toCell.getJ(), player));
                    }
                }

            }
        }

        return legalMoves;
    }

    @Override
    public String toString(){
        String s = "";
        for(var row : board){
            for(var c : row){
                s += c.getPawn().toString() + " ";
            }
            s+="\n";
        }

        return s;
    }


}

