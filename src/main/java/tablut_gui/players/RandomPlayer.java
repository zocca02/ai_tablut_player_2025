package tablut_gui.players;

import lombok.AllArgsConstructor;
import tablut_gui.ai.RandomAI;
import tablut_gui.client.Client;
import tablut_gui.controller.AIPlayerController;
import tablut_gui.controller.LocalPlayerController;
import tablut_gui.gui.GameType;
import tablut_gui.gui.Gui;
import tablut_gui.model.Player;
import tablut_gui.model.StateTablut;

@AllArgsConstructor
public class RandomPlayer extends Thread{

    private Player player;
    private String name;
    private int timeoutSec;
    private String serverIp;
    private boolean enableGui;
    private boolean enableLog;

    @Override
    public void run(){

        Client client = new Client(player, name, timeoutSec, serverIp);

        if(enableGui){
            Gui gui = new Gui(GameType.TABLUT, player, StateTablut.empty());
            LocalPlayerController controller = new AIPlayerController(client, gui, new RandomAI(player), enableLog);
        }
        else{
            LocalPlayerController controller = new AIPlayerController(client, new RandomAI(player));
        }


    }

}
