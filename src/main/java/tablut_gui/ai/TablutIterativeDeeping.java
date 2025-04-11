package tablut_gui.ai;

import aima.core.search.adversarial.Game;
import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import tablut_gui.model.Action;
import tablut_gui.model.Player;
import tablut_gui.model.State;

public class TablutIterativeDeeping extends IterativeDeepeningAlphaBetaSearch<State, Action, Player> {
    /**
     * Creates a new search object for a given game.
     *
     * @param game    The game.
     * @param utilMin Utility value of worst state for this player. Supports
     *                evaluation of non-terminal states and early termination in
     *                situations with a safe winner.
     * @param utilMax Utility value of best state for this player. Supports
     *                evaluation of non-terminal states and early termination in
     *                situations with a safe winner.
     * @param time    Maximal computation time in seconds.
     */
    public TablutIterativeDeeping(Game<State, Action, Player> game, double utilMin, double utilMax, int time) {
        super(game, utilMin, utilMax, time);
    }

    @Override
    protected double eval(State state, Player player) {
        // needed to make heuristicEvaluationUsed = true, if the state evaluated isn't terminal
        super.eval(state, player);

        // return heuristic value for given state
        return game.getUtility(state, player);
    }

}
