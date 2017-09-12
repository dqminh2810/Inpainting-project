package Salle.Mechant;

import java.awt.GridLayout;

import javax.swing.JFrame;

public class FrameScreen extends JFrame{
	public FrameScreen(){
		this.setTitle("Salle Méchant");
		Enigme enigme = new Enigme();
		this.add(enigme);
		setSize(300, 300);
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		setResizable(true); //On permet le redimensionnement
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
//		pack();
	}
	public static void main(String[] args){
		FrameScreen f = new FrameScreen();
		f.setVisible(true);
	}
}
