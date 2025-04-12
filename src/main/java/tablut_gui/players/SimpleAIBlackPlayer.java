package tablut_gui.players;

import lombok.AllArgsConstructor;
import tablut_gui.ai.RandomAI;
import tablut_gui.ai.SimpleBlackHeuristicAI;
import tablut_gui.client.Client;
import tablut_gui.controller.AIPlayerController;
import tablut_gui.controller.LocalPlayerController;
import tablut_gui.gui.GameType;
import tablut_gui.gui.Gui;
import tablut_gui.model.Player;
import tablut_gui.model.StateTablut;

@AllArgsConstructor
public class SimpleAIBlackPlayer extends Thread{

    private String name;
    private int timeoutSec;
    private String serverIp;
    private boolean enableGui;
    private boolean enableLog;

    @Override
    public void run(){

        Client client = new Client(Player.BLACK, name, timeoutSec, serverIp);

        if(enableGui){
            Gui gui = new Gui(GameType.TABLUT, Player.BLACK, StateTablut.empty());
            LocalPlayerController controller = new AIPlayerController(client, gui, new SimpleBlackHeuristicAI(timeoutSec), enableLog);
        }
        else{
            LocalPlayerController controller = new AIPlayerController(client, new SimpleBlackHeuristicAI(timeoutSec));
        }


    }

}
