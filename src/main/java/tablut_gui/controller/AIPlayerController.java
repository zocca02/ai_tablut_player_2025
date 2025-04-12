package tablut_gui.controller;

import tablut_gui.ai.AIAlgorithm;
import tablut_gui.client.Client;
import tablut_gui.dto.StateDTO;
import tablut_gui.gui.Gui;
import tablut_gui.model.State;
import tablut_gui.model.StateTablut;

import java.io.IOException;
import java.util.Random;

public class AIPlayerController extends LocalPlayerController {

    private AIAlgorithm ai;

    public AIPlayerController(Client client, Gui gui, AIAlgorithm ai, boolean enableLog) {
        super(client, gui, enableLog);
        this.ai = ai;
    }

    public AIPlayerController(Client client, AIAlgorithm ai) {
        super(client);
        this.ai = ai;
    }

    @Override
    protected void onStateReceived(StateDTO stateDTO) {
        State newState = StateTablut.fromDTO(stateDTO);

        getGui().ifPresent(g -> {
            g.setTurn(newState.getTurn());
            g.update(newState);
        });

        if(newState.getTurn()==getClient().getPlayer()){
            var move = ai.choseNextMove(newState);

            try {
                super.getClient().sendAction(move);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    protected void onMoveTimeoutExpired(){
        ai.onTimeoutExpired();
    }

    @Override
    protected void onGameOver(){
        if(isEnableLog())
            System.out.println("Player "+super.getClient().getPlayer()+": Game over");
        Thread.currentThread().interrupt();
    }
}
