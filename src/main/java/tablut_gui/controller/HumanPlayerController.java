package tablut_gui.controller;

import tablut_gui.client.Client;
import tablut_gui.dto.StateDTO;
import tablut_gui.gui.Gui;
import tablut_gui.model.Action;
import tablut_gui.model.State;
import tablut_gui.model.StateTablut;

import java.io.IOException;

public class HumanPlayerController extends GuiInPlayerController {

    private State computedState;

    public HumanPlayerController(Client client, Gui gui) {
        super(client, gui);
        computedState = StateTablut.startBoard();
        gui.setEnableInput(true);
    }


    @Override
    protected void onStateReceived(StateDTO stateDTO){
        System.out.println(stateDTO);

        State newState = StateTablut.fromDTO(stateDTO);
        if(newState.getTurn()==getClient().getPlayer()){
            System.out.println("Griglia calcolate");
            System.out.println(computedState);
            System.out.println("Confronto stati: "+computedState.equals(newState));
        }
        else{
            computedState = newState;
        }


        getGui().ifPresent(g -> {
            g.setTurn(newState.getTurn());
            g.update(newState);
        });

    }

    @Override
    protected void onNewMoveSelected(Action move){
        System.out.println(move);
        computedState = computedState.applyMove(move);
        try {
            super.getClient().sendAction(move);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onMoveTimeoutExpired(){
        System.out.println("Player "+super.getClient().getPlayer()+": Timeout expired, you lost");
    }

    @Override
    protected void onGameOver(){
        System.out.println("Player "+super.getClient().getPlayer()+": Game over");
        Thread.currentThread().interrupt();
    }
}
