package tablut_gui.ai;

import tablut_gui.model.Action;
import tablut_gui.model.Player;
import tablut_gui.model.State;

import java.util.Random;

public class RandomAI implements AIAlgorithm {

    private Player player;
    private Random rand;
    private boolean timeotExpired;

    public RandomAI(Player player){
        this.rand = new Random();
        this.player = player;
    }

    @Override
    public Action choseNextMove(State currentState) {
        synchronized (this){
            timeotExpired = false;
        }


        while(!timeotExpired){
            //do things
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
            //System.out.println("ccc");
        }

        var legalMoves = currentState.getAllLegalMovesFor(player);
        var move = legalMoves.get(rand.nextInt(legalMoves.size()));
        return move;
    }

    @Override
    public void onTimeoutExpired() {
        synchronized (this){
            timeotExpired = true;
        }
    }


}
