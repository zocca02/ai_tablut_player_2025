package tablut_gui.client;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import tablut_gui.dto.StateDTO;
import tablut_gui.exceptions.GameOverException;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientInputListener extends Thread{

    private Client client;
    private boolean gameOver;

    public ClientInputListener(Client client){
        this.client = client;
        this.gameOver = false;
    }

    @Override
    public void run(){
        while(!gameOver){
            try {
                StateDTO s = client.readNewState();
                client.getOnStateReceivedHandler().accept(s);
            } catch (IOException e) {
                gameOver = true;
                e.printStackTrace();
            }
            catch (GameOverException e){
                gameOver = true;
            }
        }
        client.getOnGameOverHandler().run();
    }
}
