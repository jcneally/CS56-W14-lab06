package edu.ucsb.cs56.w14.drawings.zhansaya.advanced;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


/**
 * A class that makes animation with ApplePie by changing its size,color and bouncing of walls
 * 
 * @author Zhansaya Abdikarimova
 * @version for CS56, lab07, W14
 */


public class AnimatedPictureViewer extends JFrame{
    
    private DrawPanel panel = new DrawPanel();
    
    Thread animate;   
    
    //array for random colors
    private Color[] colors = new Color[]{Color.PINK,
					 Color.GREEN, 
					 Color.BLACK,
					 Color.RED,
    					 Color.CYAN,
    					 Color.GRAY,
    					 Color.ORANGE,
					 Color.YELLOW};  

    
    private int x = 200;
    private int y = 200;
    
    private int frameW = 640;
    private int frameH = 480;
    
    private int pieRadius = 100;
    
    private int dx = 5;
    private int dy = 5;
    private int dr = 5;


    private int colorIndex = 0;
    
    public static void main (String[] args) {
	new AnimatedPictureViewer().go();
    }

    public void go() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	getContentPane().add(panel);
	setSize(frameW,frameH);
	setVisible(true);
      
	getContentPane().addMouseListener(new MouseAdapter() {
		public void mouseEntered(MouseEvent e){
		    animate = new animateation();
		    animate.start();
		}

		public void mouseExited(MouseEvent e){        
		    // Kill the animateation thread
		    animate.interrupt();
		    while (animate.isAlive()){}
		    animate = null;         
		    panel.repaint();        
		}
	});
      
    } // go()

    class DrawPanel extends JPanel {
	public void paintComponent(Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;

	    // Clear the panel first
	    g2.setColor(Color.white);
	    g2.fillRect(0,0,this.getWidth(), this.getHeight());

	    // Draw the ApplePie
	    g2.setColor(colors[colorIndex]);
	    ApplePie p = new ApplePie(x, y, pieRadius);
	    g2.draw(p);
       }
    }
    
    class animateation extends Thread {
	public void run() {
	    try {
		while (true) {
		    // Bounce off the walls

		    if (x > frameW - pieRadius - 30) { dx = -5; } 
		    if (y > frameH - pieRadius - 30) {dy = -5; }
		    if (x < pieRadius + 20) { dx = 5; }
		    if (y < pieRadius + 20){ dy = 5; }      
		    if (pieRadius < 50) { dr = 5; }
		    if (pieRadius > 100) { dr = -5; }      
		    
		    x += dx;   
		    y += dy;  
		    pieRadius += dr;   
		    colorIndex = (int)(Math.random() * 7);      
            
		    panel.repaint();
		    Thread.sleep(75);
		}
	    } catch(Exception ex) {
		if (ex instanceof InterruptedException) {
		    // Do nothing - expected on mouseExited
		} else {
		    ex.printStackTrace();
		    System.exit(1);
		}
	    }
	}
    }    
}
