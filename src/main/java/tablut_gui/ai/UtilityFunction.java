package tablut_gui.ai;

import tablut_gui.model.Player;
import tablut_gui.model.State;

public interface UtilityFunction {
    public double getUtility(State state, Player player);
}
