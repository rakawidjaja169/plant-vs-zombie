import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Bullet extends Entity
{
	public static final ImageIcon imagePea = new ImageIcon("images/pea.png");
	private ActionListener step = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//Move
			setPos(getPosX()+2,getPosY());
			//Stop when reaches offscreen
			if(getPosX() >= 1111)
			{
				clock.stop();
				clock = null;
				level.getBg().remove(getIcon());
			}
			//Collision detection
			if(checkTimer <= 0)
			{
				collide();
				checkTimer = 5;
			}
			--checkTimer;
		}
	};
	
	private Timer clock = new Timer(10,step);
	
	private byte checkTimer = 5;
	
	public Bullet(Level level, int x, int y) {
		super(x, y, 20, 20, level, imagePea);
		clock.start();
	}
	private void collide()
	{
		for(Zombie target:level.zombies)
		{
			int x1 = target.getPosX();
			int x2 = target.getPosX()+target.getSizeX();
			int y1 = target.getPosY();
			int y2 = target.getPosY()+target.getSizeY();
			//If hit
			if(getPosX()>x1&&getPosX()<x2&&(getPosY()+10)>y1&&(getPosY()+10)<y2)
			{
				clock.stop();
				clock = null;
				level.getBg().remove(getIcon());
				target.hurt();
				break;
			}
		}
	}
}
