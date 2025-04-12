package tablut_gui;

import tablut_gui.model.Player;
import tablut_gui.players.HumanPlayer;
import tablut_gui.players.RandomPlayer;
import tablut_gui.players.SimpleAIBlackPlayer;

import java.util.Random;

public class Main {

	public static void main(String[] args) {

		int timeoutSec = 60;
		String serverIp = "localhost";

		RandomPlayer white = new RandomPlayer(Player.WHITE, "whitePlayer", 2, serverIp, true, false);
		//RandomPlayer black = new RandomPlayer(Player.BLACK, "blackPlayer", 5, serverIp, false, false);
		SimpleAIBlackPlayer black = new SimpleAIBlackPlayer("blackPlayer", timeoutSec, serverIp, false, false);

		white.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}

		black.start();



    }

}
