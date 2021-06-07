import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Entity
{
	private JLabel icon;
	public Level level;
	public Entity(int x, int y, int w, int h, Level level, ImageIcon pic)
	{
		setIcon(new JLabel(pic));
		getIcon().setVisible(true);
		setPos(x,y);
		setSize(w,h);
		this.level = level;
	}
	public void setPos(int x, int y)
	{
		getIcon().setLocation(x,y);
	}
	public int getPosX()
	{
		return getIcon().getX();
	}
	public int getPosY()
	{
		return getIcon().getY();
	}
	public int getSizeX()
	{
		return getIcon().getWidth();
	}
	public int getSizeY()
	{
		return getIcon().getHeight();
	}
	public void setSize(int w, int h)
	{
		getIcon().setSize(w,h);
	}
	public JLabel getIcon() {
		return icon;
	}
	public void setIcon(JLabel icon) {
		this.icon = icon;
	}
}
