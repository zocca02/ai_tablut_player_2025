package tablut_gui.client;

import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import tablut_gui.dto.StateDTO;
import tablut_gui.exceptions.GameOverException;
import tablut_gui.model.*;
import tablut_gui.util.Configuration;
import tablut_gui.util.StreamUtils;
import tablut_gui.util.Timer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.Optional;
import java.util.function.Consumer;

public class Client {

    @Getter
    private String name;
    @Getter
    private String ip;
    @Getter
    private int port;
    @Getter
    private int timeout;
    @Getter
    private Player player;

    private Gson gson;

    private boolean connected;
    private Socket playerSocket;
    private DataInputStream in;
    private DataOutputStream out;

    private ClientInputListener inputListener;

    private Optional<Timer> currentTimer;

    @Getter @Setter
    private Consumer<StateDTO> onStateReceivedHandler;
    @Getter @Setter
    private Runnable onGameOverHandler;
    @Getter @Setter
    private Runnable onMoveTimeoutExpiredHandler;


    public Client(Player player, String name, int timeout, String ip) {

        this.ip = ip;
        this.timeout = timeout;
        this.gson = new Gson();
        this.player = player;
        this.connected = false;
        this.name = name;
        this.currentTimer = Optional.empty();

        if (player == Player.WHITE) {
            port = Configuration.whitePort;
        } else if (player == Player.BLACK) {
            port = Configuration.blackPort;
        } else {
            throw new InvalidParameterException("Player role must be BLACK or WHITE");
        }


        this.onStateReceivedHandler = s -> {};
        this.onGameOverHandler = () -> {};
        this.onMoveTimeoutExpiredHandler = () -> {};
    }

    public Client(Player player, String name, int timeout) throws UnknownHostException, IOException {
        this(player, name, timeout, "localhost");
    }

    public Client(Player player, String name) throws UnknownHostException, IOException {
        this(player, name, 60, "localhost");
    }


    public void connect() throws IOException, ClassNotFoundException {
        if(connected) throw new IllegalStateException("Client already connected to server");

        connected = true;

        try {
            playerSocket = new Socket(ip, port);
            out = new DataOutputStream(playerSocket.getOutputStream());
            in = new DataInputStream(playerSocket.getInputStream());

            inputListener = new ClientInputListener(this);
            inputListener.start();

        } catch (IOException e) {
            throw new IOException("Error connecting to server");
        }

        declareName();
    }

    public void sendAction(Action action) throws IOException, ClassNotFoundException {
        currentTimer.ifPresent(Timer::stopTimer);
        StreamUtils.writeString(out, this.gson.toJson(action));
    }

    private void declareName() throws IOException, ClassNotFoundException {
        StreamUtils.writeString(out, this.gson.toJson(this.name));
    }

    StateDTO readNewState() throws IOException {
        try{
            String msg = StreamUtils.readString(in);
            StateDTO data = this.gson.fromJson(msg, StateDTO.class);
            if(data.getTurn()==player){
                currentTimer = Optional.of(new Timer(timeout-1, onMoveTimeoutExpiredHandler));
                currentTimer.get().start();
            }
            else{
                currentTimer.ifPresent(Thread::interrupt);
                currentTimer = Optional.empty();
            }
            return data;
        } catch (SocketException e){
            throw new GameOverException("game over");
        }

    }


}
