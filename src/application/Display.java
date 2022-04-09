package application;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JPanel;



public class Display extends JPanel {
	private static final long serialVersionUID = 1L;
		
	static final int cols = 8;
	static final int rows = 8;
	
	static final int X = 1;
	static final int Y = 1;
	static final int Side = 72;
	
	@Override
	protected void paintComponent(Graphics grid) {
		super.paintComponent(grid);
		
		for (int i = 0; i < rows + 1; i++) {
			grid.drawLine(X, Y + i * Side, X + cols * Side, Y + i * Side);
		}
		for (int i = 0; i < cols + 1; i++) {
			grid.drawLine(X + i * Side, Y, X + i * Side, Y + rows * Side);
	}
		
		}
}
