package MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.*;

public class Entry
{
	
	public Entry()
	{
		initComponents();//initialises components, called at the start of the program
	}
	
	public static void main(String[] args) 
	{	
		//runs the program
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run()
			{
				Entry entry = new Entry();
			}
		});
	}
	
	private void initComponents()
	{
		//initialise a window
		JFrame windowFrame = new JFrame("Challange Window");
		windowFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
		//loads background
        try
        {       	
        	BufferedImage background = ImageIO.read(new File("Resources/spaceBackground.jpg"));
        	windowFrame.add(new SpacePanel(background));
        }
        catch (IOException e)
        {
        	System.out.println(e.getMessage());
        }
               
		//displays window
		windowFrame.pack();
		windowFrame.setVisible(true);
	}
}

class GravityObject//object with mass, which has methods for the physics calculations
{
	private double mass;//object's mass
	public double[] position = new double[2];//the position on the panel
	public double[] velocity = new double[2];//the velocity vector
	private double[] acceleration = new double[2];//the acceleration vector
	
	//constructor for the Gravity Object
	public GravityObject(double mass, double positionX, double positionY, double velocityX, double velocityY)
	{
		this.mass = mass;
		this.position[0] = positionX;
		this.position[1] = positionY;
		this.velocity[0] = velocityX;
		this.velocity[1] = velocityY;
	}
	
	//calculates the acceleration
	public void CalcAcceleration(int mouseX, int mouseY, double mouseMass)
	{
		
		double[] accVector = new double[2];//the vector between the two objects
		accVector[0] = position[0] - mouseX;
		accVector[1] = position[1] - mouseY;
		
		double r = Math.sqrt(accVector[0]*accVector[0] + accVector[1]*accVector[1]);//length of the vector
		
		double a = Math.pow(10, -10)*6.67408*mouseMass/r;//scalar value of the acceleration
		
		//first makes it a unit vector, then multiplys it to the scalar of the acceleration to get the acceleration vector
		accVector[0] = -(accVector[0]/r)*a;
		accVector[1] = -(accVector[1]/r)*a;
		acceleration = accVector;
	}
	
	//calculates the velocity(depending on the tick cycle time)
	public void CalcVelocity(int milliseconds)
	{
		//adds the velocity vector and the acceleration vector
		velocity[0] += acceleration[0]*milliseconds/1000;
		velocity[1] += acceleration[1]*milliseconds/1000;
	}
}

class SpacePanel extends JPanel implements MouseMotionListener
{
	private Image background;
	
	private Timer timer = null;
	
	private int mouseX = 0;
	private int mouseY = 0;
	
	private double mouseMass = 1000000000000000.0;//random number for the mass of the pointer
	private GravityObject helloWorld = new GravityObject(100000, 0, 100 , 500, 0);//arbitrary values in the constructor
	
	public SpacePanel(Image image)
	{
		this.background = image;
		
		timer = new Timer(2, new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				double k = 2/1000;
				
				helloWorld.position[0] += helloWorld.velocity[0]*k;
				helloWorld.position[1] += helloWorld.velocity[1]*k;
				
				helloWorld.CalcAcceleration(mouseX, mouseY, mouseMass);
				
				helloWorld.CalcVelocity(2);
				
				repaint();
			}
		});
		timer.start();
		addMouseMotionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);//invokes the paintComponent method of JPanel
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(
		        RenderingHints.KEY_TEXT_ANTIALIASING,
		        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2d.drawImage(background, 0, 0, 1340, 720, this);
		g2d.setColor(Color.white);
		g2d.setFont(new Font("Franklin Gothic Demi", Font.PLAIN, 20));
		
		g2d.drawString("HelloWorld!", (int)helloWorld.position[0], (int)helloWorld.position[1]);
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(1340, 720);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
}
