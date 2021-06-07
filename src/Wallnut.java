import javax.swing.ImageIcon;

public class Wallnut extends Plant
{
	private static ImageIcon fullhp = new ImageIcon("images/walnut_full_life.gif");
	private static ImageIcon halfhp = new ImageIcon("images/walnut_half_life.gif");
	public Wallnut(Level level, int x, int y) {
		super(level, x, y, (byte)35, fullhp);
		step = null;
		clock = null;
	}
	@Override
	public void takeDamage()
	{
		hp--;
		if(hp<=20)
		{
			getIcon().setIcon(halfhp);
		}
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
	//20 + 15 Health
}
