package tablut_gui.controller;

import tablut_gui.client.Client;
import tablut_gui.gui.Gui;
import tablut_gui.model.Action;

public abstract class GuiInPlayerController extends LocalPlayerController {

    public GuiInPlayerController(Client client, Gui gui) {
        super(client, gui);

        if(gui==null) throw new IllegalArgumentException("Gui can't be null");
        gui.setOnNewMoveHandler(this::onNewMoveSelected);
        gui.setEnableInput(true);

    }

    protected abstract void onNewMoveSelected(Action move);

}
