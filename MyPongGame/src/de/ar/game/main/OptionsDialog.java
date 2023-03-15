package de.ar.game.main;
import static de.ar.game.main.GameControl.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class OptionsDialog extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GamePanel gp;

	public OptionsDialog (GamePanel gp) {
		super(gp.getWindow(), "MyPong V1.0", Dialog.ModalityType.DOCUMENT_MODAL);
		this.gp = gp;
		
		Container dialogContainer = getContentPane();
		dialogContainer.setLayout(new BorderLayout());
		
		 Font font =new Font("Arial", Font.BOLD, 18);
		 setFont(font);
		 
		 
		
		setLayout(new BorderLayout());
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout());
		dialogContainer.add(centerPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		
		buttonPanel.setLayout(new BorderLayout());
		JRadioButton button_playmode_1 = new JRadioButton("1 Player");
		button_playmode_1.setFont(font);
		JRadioButton button_playmode_2 = new JRadioButton("2 Player");
		button_playmode_2.setFont(font);
		buttonPanel.add(button_playmode_1,BorderLayout.NORTH);
		buttonPanel.add(button_playmode_2,BorderLayout.SOUTH);
		
		
		ButtonGroup butgr = new ButtonGroup();
		butgr.add(button_playmode_1);
		butgr.add(button_playmode_2);
		centerPanel.add(buttonPanel);
		button_playmode_1.setSelected(true);
		
			
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());

		JButton button_start = new JButton("Start Game");
		

		southPanel.add(button_start);
		dialogContainer.add(southPanel, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(150, 150));
		pack();
		setLocationRelativeTo(gp.getWindow());
		
		
		//Actions
		
		ActionListener startGameActionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gp.getGameControl().startLevelCountDown();
				setVisible(false);
			}
		};
		button_start.addActionListener(startGameActionListener);
		
		
		
		ActionListener playModeActionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JRadioButton b = (JRadioButton) e.getSource();
				    if (b ==  button_playmode_1 ) {
				    	if (b.isSelected()) {
				    		gp.getGameControl().setPlayMode(PLAY_MODE_ONE_PLAYER);
				    	}
				    }
				    if (b ==  button_playmode_2 ) {
				    	if (b.isSelected()) {
				    		gp.getGameControl().setPlayMode(PLAY_MODE_TWO_PLAYER);
				    	}
				    }
				
			}
		};
		button_playmode_1.addActionListener(playModeActionListener);
		button_playmode_2.addActionListener(playModeActionListener);
		
	}	
}
