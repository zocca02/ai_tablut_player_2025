package tablut_gui.ai;

import tablut_gui.model.Player;
import tablut_gui.model.State;

public class SimpleUtilityFunction implements UtilityFunction {
    @Override
    public double getUtility(State state, Player player) {
        return 0;
    }
}
