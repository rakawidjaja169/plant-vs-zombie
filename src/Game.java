import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Game
{
	public static JFrame frame = new JFrame();
	public static JLabel menus;
	public static ImageIcon menuIcon;
	public static void main(String[] args) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setBounds(200, 200, 1126, 602);
		frame.setResizable(false);
		
		
		
		Level levels[] = new Level[5];
		
		Level levelHell = new Level(666,3000,1F,(byte)0);
		Level levelEndless = new Level(42,-1,0.3F,(byte)0);
		levels[4] = new Level(5,2100,0.3F,(byte)0);
		levels[3] = new Level(4,2100,0.1F,(byte)0,levels[4]);
		levels[2] = new Level(3,1400,0F,(byte)0,levels[3]);
		levels[1] = new Level(2,1000,0F,(byte)1,levels[2]);
		levels[0] = new Level(1,700,0F,(byte)2,levels[1]);
		
		JButton btnPlay = new JButton("Level 1");
		frame.add(btnPlay);
		btnPlay.setBounds(0, 400, 100, 200);
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				levels[0].start();
			}
		});
		
		JButton btnPlay2 = new JButton("Level 2");
		frame.add(btnPlay2);
		btnPlay2.setBounds(150, 400, 100, 200);
		btnPlay2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				levels[1].start();
			}
		});
		
		JButton btnPlay3 = new JButton("Level 3");
		frame.add(btnPlay3);
		btnPlay3.setBounds(300, 400, 100, 200);
		btnPlay3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				levels[2].start();
			}
		});
		
		JButton btnPlay4 = new JButton("Level 4");
		frame.add(btnPlay4);
		btnPlay4.setBounds(450, 400, 100, 200);
		btnPlay4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				levels[3].start();
			}
		});
		
		JButton btnPlay5 = new JButton("Level 5");
		frame.add(btnPlay5);
		btnPlay5.setBounds(600, 400, 100, 200);
		btnPlay5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				levels[4].start();
			}
		});
		
		JButton btnPlayEndless = new JButton("Endless");
		frame.add(btnPlayEndless);
		btnPlayEndless.setBounds(750, 400, 100, 200);
		btnPlayEndless.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				levelEndless.start();
			}
		});
		
		JButton btnPlayHell = new JButton("Good Luck");
		frame.add(btnPlayHell);
		btnPlayHell.setBounds(900, 400, 100, 200);
		btnPlayHell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				levelHell.start();
			}
		});
		
		menuIcon = new ImageIcon("images/PVZ_edit.png");
	    menus = new JLabel(menuIcon);
	    menus.setBounds(-5, 0, 1126, 602);
		open();
	}
	public static void open() {
		frame.setVisible(true);
		frame.add(menus);
	}
}

//TODO