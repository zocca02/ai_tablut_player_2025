package tablut_gui.ai;

import aima.core.search.adversarial.Game;
import lombok.AllArgsConstructor;
import tablut_gui.model.*;

import java.util.List;

@AllArgsConstructor
public class TablutGame implements Game<State, Action, Player> {

    private UtilityFunction utilityFn;

    @Override
    public State getInitialState() {
        return null;
    }

    @Override
    public Player[] getPlayers() {
        return new Player[] {Player.WHITE, Player.BLACK};
    }

    @Override
    public Player getPlayer(State state) {
        return state.getTurn();
    }

    @Override
    public List<Action> getActions(State state) {
        return state.getAllLegalMovesFor(state.getTurn());
    }

    @Override
    public State getResult(State state, Action action) {
        return state.applyMove(action);
    }

    @Override
    public boolean isTerminal(State state) {
        return state.getGameState() != GameState.NOT_ENDED;
    }

    @Override
    public double getUtility(State state, Player player) {
        return utilityFn.getUtility(state, player);
    }

}
