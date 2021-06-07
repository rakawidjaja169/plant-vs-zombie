import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ButtonPlanter extends JButton
{
	private Level level;
	public ButtonPlanter(Level levelIn)
	{
		super();
		level = levelIn;
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				click();
			}
		});
	}
	private void click()
	{
		if(level.getShovelMode())
		{
			for(Plant target:level.plants)
			{
				if(target.getPosX()==getX()&&target.getPosY()==getY())
				{
					level.plants.remove(target);
					target.killClock();
					level.getBg().remove(target.getIcon());
					break;
				}
			}
		}
		else
		{
			level.placePlant(level.getPlantID(),getX(),getY());
		}
		level.shopShovelClose();
	}
}
