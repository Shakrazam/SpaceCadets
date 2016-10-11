package Applet;

import java.applet.*;
import java.awt.Graphics;

public class Main extends Applet{

	public void paint(Graphics g){
		for(int i = 0; i <= 100; i+=0.5)
		{
			g.drawString("Hello fucking applet", i, 20)
			try
			{
				Thread.sleep(80);
			}
			catch(InterruptedException e)
			{
				
			}
			finally
			{
				
			}
			g.clearRect(0,0, 200, 400);
		}
		
	}

}
