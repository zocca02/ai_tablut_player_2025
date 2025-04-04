package tablut_gui;

import tablut_gui.model.Player;
import tablut_gui.players.HumanPlayer;
import tablut_gui.players.RandomPlayer;

public class Main {

	public static void main(String[] args) {

		int timeoutSec = 10;
		String serverIp = "localhost";

		HumanPlayer white = new HumanPlayer(Player.WHITE, "whitePlayer", timeoutSec, serverIp);
		RandomPlayer black = new RandomPlayer(Player.BLACK, "blackPlayer", timeoutSec, serverIp, false);

		white.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

		black.start();



    }

}
