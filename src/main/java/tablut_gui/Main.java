package tablut_gui;

import tablut_gui.model.Player;
import tablut_gui.players.HumanPlayer;
import tablut_gui.players.RandomPlayer;

public class Main {

	public static void main(String[] args) {

		int timeoutSec = 60;
		String serverIp = "localhost";

		HumanPlayer white = new HumanPlayer(Player.WHITE, "whitePlayer", timeoutSec, serverIp);
		//HumanPlayer black = new HumanPlayer(Player.BLACK, "blackPlayer", timeoutSec, serverIp);

		white.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

		//black.start();



    }

}
