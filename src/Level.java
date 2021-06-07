import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Level
{
	public static final int ROWS[] = {78,173,268,363,458};
	public static final int COLUMNS[] = {240,318,396,474,552,630,708,786,864};
	
	
	public static final Font defaultFont = new Font("Tahoma", Font.BOLD, 12);
	public static final Font defaultFontLarge = new Font("Tahoma", Font.BOLD, 20);
	
	private JFrame backyard;
	private final int level;
	private final byte fieldSize;	//2 for small, 1 for medium, 0 for large.
	private final float footballChance;
	private final int timeLimit;
	private Level nextlevel;
	private JButton btnExit = new JButton("EXIT");	//For debugging
	
	private int timer;
	private int sunTimer = 0;
	private int zombieTimer = 0;
	private int sunPoints = 0;
	private int plantID = -1;
	private boolean shovelMode = false;
	
	public ArrayList<Zombie> zombies = new ArrayList<Zombie>();
	public ArrayList<Plant> plants = new ArrayList<Plant>();
	public ArrayList<Sun> suns = new ArrayList<Sun>();
	
	private ButtonPlanter[][] plantPlacer;
	
	private ActionListener step = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			work();
			bg.repaint();
		}
	};
	private Timer clock = new Timer(100,step);
	
	private final JLabel bg;
	private JLabel fg;
	
	private JLabel sunShow;
	private JLabel lvlShow;
	private JLabel timeLeft;
	
	private JButton shopSunflower;
	private JButton shopPeashooter;
	private JButton shopWallnut;
	private JButton shovel;
	
	public Level(int lvl,int time, float footballProbability,byte size,Level nextlvl)
	{
		footballChance = Math.max(0,Math.min(1, footballProbability));
		timeLimit = time;
		level = lvl;
		fieldSize = (byte)Math.max(0,Math.min(2, size));
		nextlevel = nextlvl;
		
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stop((byte)(0));
			}
		});
		btnExit.setBounds(10, 10, 85, 21);
		
		lvlShow = new JLabel("Level: "+level);
		lvlShow.setFont(defaultFontLarge);
		lvlShow.setBounds(850, 20, 105, 23);
		
		sunShow = new JLabel(Integer.toString(sunPoints));
		sunShow.setFont(defaultFontLarge);
		sunShow.setBounds(40, 450, 95, 150);
		
		timeLeft = new JLabel("");
		timeLeft.setFont(defaultFontLarge);
		timeLeft.setBounds(170, 500, 180, 30);
		
		fg = new JLabel();
		fg.setBounds(0,-14,1340,576);
		fg.setLayout(null);
		
		if(fieldSize == 2)
		{
			bg = new JLabel(new ImageIcon("images/backyard_small.jpg"));
			plantPlacer = new ButtonPlanter[9][1];
		}
		else if(fieldSize == 1)
		{
			bg = new JLabel(new ImageIcon("images/backyard_medium.jpg"));
			plantPlacer = new ButtonPlanter[9][3];
		}
		else
		{
			bg = new JLabel(new ImageIcon("images/backyard_large.jpg"));
			plantPlacer = new ButtonPlanter[9][5];
		}
		
		for(int i=0;i<9;i++)
		{
			for(int j=0;j<5-(fieldSize*2);j++)
			{
				plantPlacer[i][j] = new ButtonPlanter(this);
				plantPlacer[i][j].setBounds(COLUMNS[i],ROWS[j+fieldSize],75,75);
			}
		}
		
		bg.setBounds(0,-14,1340,576);
		bg.setLayout(null);
		
		
		
		shopSunflower = new JButton("Buy sunflower (50)");
		shopSunflower.setFont(defaultFont);
		shopSunflower.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				plantID = 0;
				shopOpen();
			}
		});
		shopSunflower.setBounds(20,50,170,30);
		
		shopPeashooter = new JButton("Buy peashooter (100)");
		shopPeashooter.setFont(defaultFont);
		shopPeashooter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				plantID = 1;
				shopOpen();
			}
		});
		shopPeashooter.setBounds(20,100,170,30);
		
		shopWallnut = new JButton("Buy wallnut (50)");
		shopWallnut.setFont(defaultFont);
		shopWallnut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				plantID = 2;
				shopOpen();
			}
		});
		shopWallnut.setBounds(20,150,170,30);
		
		shovel = new JButton("Remove plant");
		shovel.setFont(defaultFont);
		shovel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shovelOpen();
			}
		});
		shovel.setBounds(20,200,170,30);
		
	}
	public Level(int lvl,int time,float footballProbability,byte size) {
		this(lvl, time, footballProbability,size, null);
	}
	public void start()
	{
		backyard = new JFrame("Plant vs Zombie");
		backyard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		backyard.setLayout(null);
		backyard.setBounds(200,100,1040,600);
		backyard.setResizable(false);
		backyard.add(btnExit);
		backyard.add(lvlShow);
		backyard.add(sunShow);
		
		backyard.add(shopSunflower);
		backyard.add(shopPeashooter);
		backyard.add(shopWallnut);
		backyard.add(shovel);
		
		backyard.add(timeLeft);
		
		backyard.add(fg);
		backyard.add(bg);
		backyard.setVisible(true);
		bg.setVisible(true);
		timer = timeLimit;
		sunPoints = 100; //Initial sunmoney
		sunShow.setText(Integer.toString(sunPoints));
		sunTimer = (int)(Math.random()*20)+80;
		zombieTimer = 130;
		
		//Sunflower a = new Sunflower(this,COLUMNS[0],ROWS[0]);
		//Peashooter b = new Peashooter(this,COLUMNS[1],ROWS[1]);
		//Peashooter c = new Peashooter(this,COLUMNS[8],ROWS[2]);
		//Peashooter d = new Peashooter(this,300,ROWS[3]);
		//Peashooter e = new Peashooter(this,300,ROWS[4]);
		//getBg().add(a.getIcon());
		//getBg().add(b.getIcon());
		//getBg().add(c.getIcon());
		//getBg().add(d.getIcon());
		//getBg().add(e.getIcon());
		
		clock.start();
	}
	public void stop(byte win)
	{
		clock.stop();
		
		bg.setVisible(false);
		bg.removeAll();
		//backyard.removeAll();
		bg.revalidate();
		//backyard.revalidate();
		
		backyard.remove(btnExit);
		backyard.remove(lvlShow);
		backyard.remove(sunShow);
		backyard.remove(timeLeft);
		
		backyard.remove(shopSunflower);
		backyard.remove(shopPeashooter);
		backyard.remove(shopWallnut);
		backyard.remove(shovel);
		
		backyard.remove(fg);
		backyard.remove(bg);
		
		for(Zombie target:zombies)
		{
			target.killClock();
		}
		zombies.clear();
		for(Plant target:plants)
		{
			target.killClock();
		}
		for(Sun target:suns)
		{
			target.killClock();
		}
		plants.clear();
		if(win<0)
		{
			//lose
			JLabel loseBg = new JLabel(new ImageIcon("images/gameOver.jpg"));
			//Go back to main menu
			JButton btnNextLvl = new JButton("Back");
			btnNextLvl.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					backyard.setVisible(false);
					backyard.remove(loseBg);
					backyard.remove(btnNextLvl);
					Game.open();
				}
			});
			btnNextLvl.setFont(defaultFont);
			btnNextLvl.setBounds(289, 334, 500, 128);
			backyard.add(btnNextLvl);
			loseBg.setBounds(0,0,1116,602);
			backyard.add(loseBg);
		}
		else if(win>0)
		{
			//win
			JLabel winBg = new JLabel(new ImageIcon("images/Win.png"));
			if(nextlevel == null)
			{
				//Go back to main menu
				JButton btnNextLvl = new JButton("Back");
				btnNextLvl.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						backyard.setVisible(false);
						backyard.remove(winBg);
						backyard.remove(btnNextLvl);
						Game.open();
					}
				});
				btnNextLvl.setFont(defaultFont);
				btnNextLvl.setBounds(289, 334, 500, 128);
				backyard.add(btnNextLvl);
			}
			else
			{
				//Go to next level
				JButton btnNextLvl = new JButton("Next");
				btnNextLvl.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						backyard.setVisible(false);
						backyard.remove(winBg);
						backyard.remove(btnNextLvl);
						nextlevel.start();
					}
				});
				btnNextLvl.setFont(defaultFont);
				btnNextLvl.setBounds(289, 334, 500, 128);
				backyard.add(btnNextLvl);
			}
			winBg.setBounds(0,0,1116,602);
			backyard.add(winBg);
		}
		else
		{
			//exit
			backyard.setVisible(false);
			Game.open();
		}
	}
	private void work() {
		if(sunTimer<=0)
		{
			//Spawn new sun from sky
			fg.add(new Sun(this).icon);
			sunTimer = (int)(Math.random()*20)+60;
		}
		
		if(timer==0) //Time's up
		{
			if(zombies.size()==0)
			{
				zombieTimer--;
			}
			else
			{
			}
			if(zombieTimer==0)
			{
				stop((byte)(1)); //Win!
			}
		}
		else if(timer==1)
		{
			zombieTimer = 10;
			timer--;
		}
		else //Time still ticking
		{
			if(zombieTimer<=0)
			{
				//Spawn new zombie
				if(fieldSize==2)
				{
					bg.add(new Zombie(this,1000,ROWS[2]-30,(Math.random()<footballChance)).getIcon());
					zombieTimer = (int)(Math.random()*50)+90;
					
				}
				else if(fieldSize==1)
				{
					bg.add(new Zombie(this,1000,ROWS[(int)(Math.random()*3)+1]-30,(Math.random()<footballChance)).getIcon());
					zombieTimer = (int)(Math.random()*40)+80;
				}
				else
				{
					bg.add(new Zombie(this,1000,ROWS[(int)(Math.random()*5)]-30,(Math.random()<footballChance)).getIcon());
					zombieTimer = (int)(Math.random()*30)+70;
				}
			}
			timer--;
			zombieTimer--;
		}
		for(Zombie target:zombies) //Check if zombies have get past the defense perimeter
		{
			if(target.getPosX()<150)
			{
				stop((byte)-1);
				break;
			}
		}
		timeLeft.setText("Time left: "+timer);
		sunTimer--;
	}
	public void addSun(int sunadd)
	{
		sunPoints+=sunadd;
		sunShow.setText(Integer.toString(sunPoints));
	}
	public void shovelOpen()
	{
		shovelMode = true;
		int i,j;
		for(i=0;i<9;i++)
		{
			for(j=0;j<5-(fieldSize*2);j++)
			{
				if(!(checkEmptyGrid(i,j+fieldSize)))
				{
					bg.add(plantPlacer[i][j]);
				}
			}
		}
		bg.repaint();
	}
	public void shopOpen()
	{
		int i,j;
		for(i=0;i<9;i++)
		{
			for(j=0;j<5-(fieldSize*2);j++)
			{
				if(checkEmptyGrid(i,j+fieldSize))
				{
					bg.add(plantPlacer[i][j]);
				}
			}
		}
		bg.repaint();
	}
	public void shopShovelClose()
	{
		int i,j;
		for(i=0;i<9;i++)
		{
			for(j=0;j<5-(fieldSize*2);j++)
			{
				if(plantPlacer[i][j]!=null)
				{
					bg.remove(plantPlacer[i][j]);
				}
			}
		}
		shovelMode = false;
		bg.repaint();
	}
	public void placePlant(int a,int x, int y)
	{
		if(a==0&&sunPoints>=50)
		{
			getBg().add(new Sunflower(this,x,y).getIcon());
			sunPoints-=50;
		}
		else if(a==1&&sunPoints>=100)
		{
			getBg().add(new Peashooter(this,x,y).getIcon());
			sunPoints-=100;
		}
		else if(a==2&&sunPoints>=50)
		{
			getBg().add(new Wallnut(this,x,y).getIcon());
			sunPoints-=50;
		}
		sunShow.setText(Integer.toString(sunPoints));
		plantID = -1;
	}
	public JLabel getBg()
	{
		return bg;
	}
	public int getPlantID()
	{
		return plantID;
	}
	public boolean getShovelMode()
	{
		return shovelMode;
	}
	public boolean checkEmptyGrid(int i, int j)
	{
		for(Plant plant:plants)
		{
			if(plant.getPosY()==ROWS[j]&&plant.getPosX()==COLUMNS[i])
			{
				return false;
			}
		}
		return true;
	}
}
