package tablut_gui.ai;

import tablut_gui.model.Action;
import tablut_gui.model.State;

public interface AIAlgorithm {

    public Action choseNextMove(State currentState);
    public void onTimeoutExpired();

}
