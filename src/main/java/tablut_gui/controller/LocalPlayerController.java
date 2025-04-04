package tablut_gui.controller;


import tablut_gui.client.Client;
import tablut_gui.dto.StateDTO;
import tablut_gui.gui.Gui;
import tablut_gui.model.*;

import java.io.IOException;
import java.util.Optional;

public abstract class LocalPlayerController {

	private Client client;
	private Optional<Gui> gui;
	private boolean useGui;



	public LocalPlayerController(Client client, Gui gui) {

		this.client = client;
		this.client.setOnStateReceivedHandler(this::onStateReceived);
		this.client.setOnMoveTimeoutExpiredHandler(this::onMoveTimeoutExpired);
		this.client.setOnGameOverHandler(this::onGameOver);

		if(gui!=null){
			gui.setEnableInput(false);
			this.gui = Optional.of(gui);
		}
		else{
			this.gui = Optional.empty();
		}

		clientSetup();
	}

	public LocalPlayerController(Client client) {
		this(client, null);
	}

	protected Optional<Gui> getGui(){
		return gui;
	}

	protected Client getClient(){
		return client;
	}
	
	private void clientSetup() {
        try {
            client.connect();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



	protected abstract void onStateReceived(StateDTO stateDTO);

	protected abstract void onMoveTimeoutExpired();

	protected abstract void onGameOver();


}
