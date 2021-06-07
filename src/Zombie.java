import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Zombie extends Entity
{
	public static final ImageIcon imageNormalZombie = new ImageIcon("images/zombie_normal.gif");
	public static final ImageIcon imageFootballZombie = new ImageIcon("images/zombie_football.gif");
	private ActionListener step = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//Walk
			setPos(getPosX()-1,getPosY());
		}
	};
	private Timer clock;
	
	private boolean walking;
	private boolean football;
	private byte eatimer = 10;
	private byte checkTimer = 5;
	private byte hp;
	public Zombie(Level level, int x, int y, boolean footballType)
	{
		super(x, y, 106, 130, level, footballType ? imageFootballZombie : imageNormalZombie);
		football = footballType;
		step = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				hunt();
			}
		};
		clock = new Timer(50,step);
		if(football)
		{
			hp = 30;
		}
		else
		{
			hp = 10;
		}
		
		level.zombies.add(this);
		clock.start();
	}
	private void hunt()
	{
		if(walking)
		{
			if(football)
			{
				setPos(getPosX()-2,getPosY());
			}
			else
			{
				setPos(getPosX()-1,getPosY());
			}
		}
		if(checkTimer<=0)
		{
			collide();
			checkTimer = 5;
		}
		checkTimer--;
	}
	private void collide()
	{
		boolean detected = false;
		for(Plant target:level.plants)
		{
			int x1 = target.getPosX();
			int x2 = target.getPosX()+target.getSizeX();
			int y1 = target.getPosY();
			int y2 = target.getPosY()+target.getSizeY();
			if(getPosX()>x1&&getPosX()<x2&&(getPosY()+65)>y1&&(getPosY()+65)<y2)
			{
				detected = true;
				walking = false;
				if(eatimer<=0)
				{
					target.takeDamage();
					eatimer = 4;
				}
				eatimer--;
				break;
			}
		}
		if(detected == false)
		{
			eatimer = 5;
			walking = true;
		}
		else
		{
			
		}
	}
	public void hurt()
	{
		hp--;
		if(hp<=0)
		{
			//Die
			level.zombies.remove(this);
			
			killClock();
			
			//step = new ActionListener() {
			//	public void actionPerformed(ActionEvent e) {
			//		//Death animation
			//		getIcon().setIcon(new ImageIcon("images/zombie_football.gif"));
			//	}
			//};
			//clock = new Timer(2000,step);
			
			getIcon().setVisible(false);
			level.getBg().remove(getIcon());
			setIcon(null);
		}
	}
	public void killClock()
	{
		if(clock != null)
		{
			clock.stop();
			clock = null;
			step = null;
		}
	}
}
