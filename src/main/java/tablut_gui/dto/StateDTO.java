package tablut_gui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tablut_gui.model.Pawn;
import tablut_gui.model.Player;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class StateDTO {
    private Pawn[][] board;
    private Player turn;

    public String toString(){
        String boardPrint = "";
        for(var raw : board){
            for(var p : raw){
                boardPrint+=p+" ";
            }
            boardPrint+="\n";
        }
        return "Turn: "+turn+"\n"+boardPrint;
    }
}
