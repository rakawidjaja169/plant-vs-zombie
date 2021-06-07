import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Peashooter extends Plant
{
	public Peashooter(Level level,int x, int y)
	{
		super(level, x, y, (byte)6, new ImageIcon("images/pea_shooter.gif"));
		step = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shoot();
			}
		};
		clock = new Timer(1500,step);
		clock.start();
	}
	private void shoot()
	{
		// TODO Auto-generated method stub
		level.getBg().add(new Bullet(level,getPosX()+45,getPosY()+15).getIcon());
	}
}
