package tablut_gui.gui.listeners;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tablut_gui.gui.Gui;
import tablut_gui.model.Action;
import tablut_gui.model.Pawn;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

@AllArgsConstructor
public class GuiMouseListener implements MouseListener {

    private Gui gui;
    @Getter @Setter
    private boolean enableInput;

    @Override
    public void mouseClicked(MouseEvent ev) {
        if(!enableInput) return;
        if(gui.getTurn()!=gui.getPlayer()) return;

        int i = Gui.yPosToI(ev.getY());
        int j = Gui.xPosToJ(ev.getX());

        if(gui.getBackground().hasPawnSelected())
            pawnMoveClick(i, j);
        else
            pawnSelectionClick(i, j);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(!enableInput) return;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(!enableInput) return;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(!enableInput) return;
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(!enableInput) return;
    }

    private void pawnSelectionClick(int i, int j){

        if(i<=8 && j<=8) {
            Pawn p = gui.getBackground().getState().getPawn(i, j);
            if(p.belongsTo(gui.getPlayer())){
                gui.getBackground().setSelectedI(i);
                gui.getBackground().setSelectedJ(j);
            }
            else{
                gui.getBackground().setSelectedI(-1);
                gui.getBackground().setSelectedJ(-1);
            }
        }
        else {
            gui.getBackground().setSelectedI(-1);
            gui.getBackground().setSelectedJ(-1);
        }
        gui.getFrame().repaint();
    }

    private void pawnMoveClick(int i, int j){
        int selI = gui.getBackground().getSelectedI();
        int selJ = gui.getBackground().getSelectedJ();
        if(gui.getBackground().getState().isLegalTo(selI, selJ, i, j)){
            gui.getOnNewMoveHandler().accept(new Action(selI, selJ, i, j, gui.getPlayer()));
            gui.getBackground().setSelectedI(-1);
            gui.getBackground().setSelectedJ(-1);
        }
        else{
            gui.getBackground().setSelectedI(-1);
            gui.getBackground().setSelectedJ(-1);
        }
        gui.getFrame().repaint();
    }


}
