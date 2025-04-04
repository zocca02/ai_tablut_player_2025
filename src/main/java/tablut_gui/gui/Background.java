package tablut_gui.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import lombok.Getter;
import lombok.Setter;
import tablut_gui.model.State;

public abstract class Background extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Image background;
	protected Image black;
	protected Image white;
	protected Image king;
	@Getter @Setter
	protected State state;

	@Getter	@Setter
	private int selectedI;
	@Getter @Setter
	private int selectedJ;
	
	public Background()
	{
		super();
		selectedI = -1;
		selectedJ = -1;
		
	}



	@Override
	public void paintComponent( Graphics g ) {
	    super.paintComponent(g);
	    g.drawImage(background, 10, 30, null);
	    for(int i = 0; i<this.state.getBoard().length; i++)
	    {
	    	for(int j = 0; j<this.state.getBoard().length; j++)
	    	{

	    		if(this.state.getPawn(i, j).equalsPawn("B"))
	    		{
	    			int posX= 34 + (i*37);
	    			int posY= 12 + (j*37);
	    			g.drawImage(black, posY, posX,null);
	    		}	
	    		if(this.state.getPawn(i, j).equalsPawn("W"))
	    		{
	    			int posX= 35 + (i*37);
	    			int posY= 12 + (j*37);
	    			g.drawImage(white, posY, posX,null);
	    		}	
	    		if(this.state.getPawn(i, j).equalsPawn("K"))
	    		{
	    			int posX= 34 + (i*37);
	    			int posY= 12 + (j*37);
	    			g.drawImage(king, posY, posX,null);
	    		}	
	    		
	    		
	    	}
	    }
	    if(hasPawnSelected()) {
	    	g.setColor(Color.RED);

			var leagalMoves = state.legalMovesFor(selectedI, selectedJ);

			for(var c : leagalMoves){
				int posX= 12 + (c.getJ()*37);
				int posY= 34 + (c.getI()*37);
				g.fillOval(posX+13, posY+13, 10, 10);
			}

		}
	    g.dispose();
	}

	public boolean hasPawnSelected() {
		return selectedI >= 0 && selectedJ >= 0;
	}



}
