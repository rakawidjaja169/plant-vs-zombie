import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Plant extends Entity
{
	protected Timer clock;
	protected ActionListener step;
	protected byte hp;
	public Plant(Level level,int x, int y, byte hp, ImageIcon icon) {
		super(x, y, 75, 75, level, icon);
		this.hp = hp;
		level.plants.add(this);
	}
	public void die()
	{
		killClock();
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
	public void takeDamage()
	{
		hp--;
		if(hp<=0)
		{
			//Die
			level.plants.remove(this);
			
			killClock();
			
			
			
			getIcon().setVisible(false);
			level.getBg().remove(getIcon());
			setIcon(null);
		}
	}
}
