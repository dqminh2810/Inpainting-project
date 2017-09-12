package Salle.Mechant;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Enigme extends JPanel implements ActionListener{
	String[] question = new String[4];
	String[] reponse = new String[4];
	boolean valid =true;
	JLabel wims[] = new JLabel[4];
	JTextField res[] = new JTextField[4];
	GroupLayout layout;
	
	public Enigme() {
		//Liste des questions et des réponses correspondant
		question[0]="HTML ra doi khi nao";
		question[1]="Code cua color red la gi";
		question[2]="Java ra doi khi nao";
		question[3]="Code cua color blue la gi";
		reponse[0]="1990";
		reponse[1]="Fx0000";
		reponse[2]="1991";
		reponse[3]="Fx0001";
		
		//Créer les nouvelles components
		wims[0]=new JLabel(question[0]);
		wims[1]=new JLabel(question[1]);
		wims[2]=new JLabel(question[2]);
		wims[3]=new JLabel(question[3]);
		res[0]=new JTextField(20);
		res[1]=new JTextField(20);
		res[2]=new JTextField(20);
		res[3]=new JTextField(20);
		
		//ajouter content
	    this.add(wims[0]);
	    this.add(res[0]);
	    this.add(wims[1]);
	    this.add(res[1]);
	    this.add(wims[2]);
	    this.add(res[2]);
	    this.add(wims[3]);
	    this.add(res[3]);
	    this.add(createJButton("valider"));
	}
	//Méthode vérifier la réponse est correcte ou pas
	public void process() {
		for(int i=0; i<4; i++) {
			String text = res[i].getText();
			System.out.println(text);
			if(text.equals(reponse[i])) 
				 valid=valid&true;
			else
				valid=valid&false;
			System.out.print(valid);
		}
		
	}
	private JButton createJButton(String buttonName) {
		JButton btn = new JButton(buttonName);
		btn.addActionListener(this);
		return btn;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.process();
		if(valid==true)
			JOptionPane.showMessageDialog(null, "congratulations, you finded the key to go to next room!!!");
		else
			JOptionPane.showMessageDialog(null, "oupss..Try again");
	}
	
}