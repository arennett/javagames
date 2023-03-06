package de.ar.game.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OptionsDialog extends JDialog {
	
	private GamePanel gp;

	public OptionsDialog (GamePanel gp) {
		super(gp.getWindow(), "MyPong V1.0", Dialog.ModalityType.DOCUMENT_MODAL);
		this.gp = gp;
		
		Container dialogContainer = getContentPane();
		dialogContainer.setLayout(new BorderLayout());
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		dialogContainer.add(centerPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JRadioButton player_1 = new JRadioButton("1 Player");
		JRadioButton player_2 = new JRadioButton("2 Player");
		buttonPanel.add(player_1,BorderLayout.NORTH);
		buttonPanel.add(player_2,BorderLayout.SOUTH);
		
		
		ButtonGroup butgr = new ButtonGroup();
		butgr.add(player_1);
		butgr.add(player_2);
		centerPanel.add(buttonPanel);
		player_1.setSelected(true);
		
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());

		JButton okButton = new JButton("Start Game");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gp.getGameControl().startGame();
				setVisible(false);
			}
		});

		southPanel.add(okButton);
		dialogContainer.add(southPanel, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(gp.getWindow());
		
	}
	

}
