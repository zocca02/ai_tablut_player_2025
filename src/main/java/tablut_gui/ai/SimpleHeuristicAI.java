package tablut_gui.ai;

import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import tablut_gui.model.Action;
import tablut_gui.model.State;

public class SimpleHeuristicAI implements AIAlgorithm{

    private IterativeDeepeningAlphaBetaSearch iterDeep;
    private int timeSec;

    public SimpleHeuristicAI(int timeSec){
        this.timeSec = timeSec;
        iterDeep = new IterativeDeepeningAlphaBetaSearch(new TablutGame(new SimpleUtilityFunction()), Double.MIN_VALUE,
                Double.MAX_VALUE, timeSec);
    }

    @Override
    public Action choseNextMove(State currentState) {
        Object o = iterDeep.makeDecision(currentState);
        if(!(o instanceof Action)) throw new IllegalArgumentException();
        return (Action)o;
    }

    @Override
    public void onTimeoutExpired() {    }
}
