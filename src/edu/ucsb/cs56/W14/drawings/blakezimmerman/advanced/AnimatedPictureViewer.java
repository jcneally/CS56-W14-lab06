package edu.ucsb.cs56.w14.drawings.blakezimmerman.advanced;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AnimatedPictureViewer {

    private DrawPanel panel = new DrawPanel();
    
    private TireSwing tireSwing = new TireSwing(320,320,0,0);
    
    Thread anim;   
    
    private double x = 320;
    private double y = 320;
    private double r = 0;
    private double w = 0;

    private double drad = 0;
    private int dr = 0;
    private int dw = 0;

    Stroke thick = new BasicStroke(4.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);

    public static void main (String[] args) {
      new AnimatedPictureViewer().go();
    }

    public void go() {
      JFrame frame = new JFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      frame.getContentPane().add(panel);
      frame.setSize(640,640);
      frame.setVisible(true);
      
      frame.getContentPane().addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e){
        System.out.println("mouse entered");
          anim = new Animation();
          anim.start();
        }

        public void mouseExited(MouseEvent e){        
          System.out.println("Mouse exited");
          // Kill the animation thread
          anim.interrupt();
          while (anim.isAlive()){}
          anim = null;         
          panel.repaint();        
        }
      });
      
    } // go()

    class DrawPanel extends JPanel {
       public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

	int red = (int) (Math.random() * 256);
	int green = (int) (Math.random() * 256);
	int blue = (int) (Math.random() * 256);
	Color randColor = new Color(red,green,blue);

	// Clear the panel first
	g2.setColor(Color.BLACK);
	g2.fillRect(0,0,this.getWidth(), this.getHeight());
	
	// Draw the TireSwing
	g2.setStroke(thick);
	g2.setColor(randColor);
	TireSwing test = new TireSwing(x, y, r, w);
	g2.rotate(Math.toRadians(drad), x, y);
	g2.draw(test);
       }
    }
    
    class Animation extends Thread {
      public void run() {
        try {
          while (true) {

	    if (r <= 3) {
		dr = 10; 
		dw = 10;
	    }
            if (r >= 320) {
		dr = -10;
		dw = -10;
	    }
	    
	    drad++;
	    r += dr;
	    w += dw;
            panel.repaint();
            Thread.sleep(50);
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
