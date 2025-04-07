package tablut_gui.ai.first_try;

import aima.core.search.adversarial.Game;
import tablut_gui.model.Action;
import tablut_gui.model.Player;
import tablut_gui.model.State;

import java.util.List;

public class TablutTryGame implements Game<State, Action, Player> {


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
        //TO DO
        return null;
    }

    @Override
    public boolean isTerminal(State state) {
        //TO DO
        return false;
    }

    @Override
    public double getUtility(State state, Player player) {
        //TO DO
        return 0;
    }
}
