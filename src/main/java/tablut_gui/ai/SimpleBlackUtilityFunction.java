package tablut_gui.ai;

import tablut_gui.model.CellType;
import tablut_gui.model.GameState;
import tablut_gui.model.Player;
import tablut_gui.model.State;

public class SimpleBlackUtilityFunction implements UtilityFunction {
    @Override
    public double getUtility(State state, Player player) {

        if(state.getGameState() == GameState.BLACK_WIN) return Double.POSITIVE_INFINITY;
        if(state.getGameState() == GameState.WHITE_WIN) return Double.NEGATIVE_INFINITY;
        if(state.getGameState() == GameState.DRAW) return 0;

        int playerPawnNum = state.getPawnNumber(player);
        int opponentPawnNum = state.getPawnNumber(player.getOpponent());

        double kingExitMalus = switch(state.getKingImmediateExit()){
            case 0 -> 2;
            case 1 -> -5;
            default -> Double.NEGATIVE_INFINITY;
        };

        var kingCell = state.getKingCell();
        var kingNeighbors = state.getNeighbors(kingCell);
        double kingEncirclementBonus = 0;
        if(kingCell.getType() == CellType.THRONE || kingNeighbors.stream().anyMatch(c -> c.getType()==CellType.THRONE)){
            int num = (int)kingNeighbors.stream()
                    .filter(c -> c.getType()==CellType.THRONE || c.getPawn().belongsTo(player))
                    .count();
            kingEncirclementBonus = switch(num){
                case 0 -> 0;
                case 1 -> 1;
                case 2 -> 2;
                case 3 -> 8;
                default -> Double.POSITIVE_INFINITY;
            };
        }
        else {
            int num = (int)kingNeighbors.stream()
                    .filter(c -> c.getType()==CellType.THRONE || c.getPawn().belongsTo(player) || c.getType() == CellType.BLACK_BASE)
                    .count();

            kingEncirclementBonus = switch(num){
                case 0 -> 0;
                case 1 -> 2;
                case 2 -> 4;
                default -> Double.POSITIVE_INFINITY;
            };
        }

        return playerPawnNum-2*opponentPawnNum+kingExitMalus+kingEncirclementBonus;
    }


}
