package tablut_gui.gui;

import java.util.function.Consumer;

import javax.swing.JFrame;

import lombok.Getter;
import lombok.Setter;
import tablut_gui.gui.listeners.GuiMouseListener;
import tablut_gui.model.Action;
import tablut_gui.model.Player;
import tablut_gui.model.State;

@Getter
public class Gui {
	
	private Background background;
	private JFrame frame;
	private GameType game;
	private GuiMouseListener mouseListener;

	@Getter
	private Player player;
	@Getter @Setter
	private Player turn;

	@Getter @Setter
	private Consumer<Action> onNewMoveHandler;
	
	public Gui(GameType game, Player player, State initState) {
		super();

		this.turn = Player.WHITE;
		this.player = player;
		this.game = game;
		this.onNewMoveHandler = m -> {};

		initGUI(initState);

		this.mouseListener = new GuiMouseListener(this, false);
		background.addMouseListener(mouseListener);

		show();
	}

	public void setEnableInput(boolean enable){
		mouseListener.setEnableInput(enable);
		if(!enable) refresh();
	}



	public void update(State state) {
		background.setState(state);
		frame.repaint();
	}

	public void refresh() {
		frame.repaint();
	}

	

	private void initGUI(State initState) {
		switch (game) {
		case CLASSIC_TABLUT, MODERN_TABLUT, TABLUT:
			background = new BackgroundTablut();
			break;
		case BRANDUB:
			background = new BackgroundBrandub();
			break;
		default:
			System.out.println("Error in GUI init");
			System.exit(4);
		}
		background.setState(initState);
		

		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Più performante fare paint su
		// JPanel (cioè background) che
		// direttamente sul frame
		frame.getContentPane().add(background);
		frame.pack();
		frame.setLocationByPlatform(true);
	}
	
	//non toccare le dimensioni se no si sminchia tutto e non ho voglia di farlo responsive
	private void show() {
		frame.setSize(game.getWidth(), game.getHeight());
		frame.setTitle(game.getName());
		frame.setVisible(true);
	}
	

	
	public static int yPosToI(int ypos) {
		return (ypos-34)/37;
	}
	
	public static int xPosToJ(int xpos) {
		return (xpos-12)/37;
	}

	

}
