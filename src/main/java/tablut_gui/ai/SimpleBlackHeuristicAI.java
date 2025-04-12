package tablut_gui.ai;

import aima.core.search.adversarial.IterativeDeepeningAlphaBetaSearch;
import tablut_gui.model.Action;
import tablut_gui.model.Player;
import tablut_gui.model.State;

public class SimpleBlackHeuristicAI implements AIAlgorithm{

    private TablutIterativeDeeping iterDeep;
    private int timeSec;

    public SimpleBlackHeuristicAI(int timeSec){
        this.timeSec = timeSec;
        iterDeep = new TablutIterativeDeeping(new TablutGame(new SimpleBlackUtilityFunction()), Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY, timeSec);
        iterDeep.setLogEnabled(true);
    }

    @Override
    public Action choseNextMove(State currentState) {
        Object o = iterDeep.makeDecision(currentState);
        System.out.println(iterDeep.getMetrics());
        if(!(o instanceof Action a)) throw new IllegalArgumentException();
        return a;
    }

    @Override
    public void onTimeoutExpired() {    }
}
