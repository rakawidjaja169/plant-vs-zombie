import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.Timer;

public class Sunflower extends Plant
{
	private Sun sunProduce;
	public Sunflower(Level level,int x, int y)
	{
		super(level, x, y, (byte)6, new ImageIcon("images/sun_flower.gif"));
		step = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				produce();
			}
		};
		clock = new Timer(12000,step);
		clock.start();
	}
	private void produce()
	{
		sunProduce = new Sun(level,this,30,30);
		getIcon().add(sunProduce.icon);
		//Stop producing until the sun is taken
		clock.stop();
	}
}
