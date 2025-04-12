package tablut_gui.controller;


import lombok.Getter;
import lombok.Setter;
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
	@Getter @Setter
	private boolean enableLog;


	public LocalPlayerController(Client client, Gui gui, boolean enableLog) {

		this.client = client;
		this.client.setOnStateReceivedHandler(this::onStateReceived);
		this.client.setOnMoveTimeoutExpiredHandler(this::onMoveTimeoutExpired);
		this.client.setOnGameOverHandler(this::onGameOver);

		this.enableLog = enableLog;

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
		this(client, null, false);
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
