package edu.ucsb.cs56.w14.drawings.blakezimmerman.advanced;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AnimatedPictureViewer {

    private DrawPanel panel = new DrawPanel();
    
    private TireSwing tireSwing = new TireSwing(320,320,0,0);
    
    Thread anim;   
    
    private double x = 320;
    private double y = 320;
    private double r = 0;
    private double w = 0;

    private int dr = 2;
    private int dw = 2;

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

         // Clear the panel first
          g2.setColor(Color.white);
          g2.fillRect(0,0,this.getWidth(), this.getHeight());

          // Draw the TireSwing
          g2.setColor(Color.RED);
          TireSwing test = new TireSwing(x, y, r, w);
          g2.draw(test);
       }
    }
    
    class Animation extends Thread {
      public void run() {
        try {
          while (true) {

            if (w >= 320) {
		dr = -1;
		dw = -1;
	    }
	    if (w <= 1) {
		dr = 1; 
		dw = 1;
	    }
	    
            
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
