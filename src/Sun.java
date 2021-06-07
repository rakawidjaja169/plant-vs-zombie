import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Sun
{
	public static final ImageIcon imageSun = new ImageIcon("images/sun.gif");
	JLabel icon;
	public Level level;
	private ActionListener step;
	private Timer clock;
	private int landY;
	private Sunflower maker;
	//Manufactured suns
	public Sun(Level levelIn, Sunflower madeIn, int x, int y)
	{
		maker = madeIn;
		level = levelIn;
		clock = null;
		
		icon = new JLabel(imageSun);
		icon.setSize(50,50);
		icon.setLocation(x,y);
		landY = y;
		
		icon.addMouseListener(new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				die();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		level.suns.add(this);
		//Land at any row
	}
	//Falling sun from sky
	public Sun(Level level)
	{
		this(level, null, (int)(Math.random()*632)+240, -50);
		landY = (int)(Math.random()*400)+40;
		step = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Falling animation
				icon.setLocation(icon.getX(),icon.getY()+1);
				if(icon.getY()>=landY)
				{
					clock.stop();
				}
			}
		};
		clock = new Timer(25,step);
		clock.start();
		//Test
	}
	public void die()
	{
		level.suns.remove(this);
		killClock();
		icon.setVisible(false);
		if(maker != null)
		{
			//Tell the sunflower that the sun is picked up, so it can produce the next sun
			maker.clock.start();
		}
		level.addSun(25);
		level.getBg().remove(icon);
	}
	public void killClock()
	{
		if(clock != null)
		{
			clock.stop();
		}
	}
}
