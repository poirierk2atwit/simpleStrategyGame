package application;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Graphics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Artillery");
		frame.setSize(594, 617);
		
		Display panel = new Display();
		frame.add(panel);
		
		frame.setVisible(true);
		frame.setResizable(false);

		
	}

	}

