package edu.ucsb.cs56.w14.drawings.jrcryan.advanced;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class AnimatedPictureViewer {

    DrawPanel panel = new DrawPanel();
    Thread anim;   
    int maxW = 640;
    int maxH = 480;
 
    // makes array to hold popsicles
    //    ArrayList<PopsicleOnAStick> psArray = new ArrayList<PopsicleOnAStick>();
    int n = 5; 
    int w = 80;
    int h = 160;
    int x = maxW / 2;
    int x2 = x - 160;
    int y = maxH / 2;
    int y2 = y - 80;
    int dy = 5;
    int dx = 3;
    int dy2 = 3;
    int dx2 = 1;
    int speed = (int) (Math.random() * 60);
    Color color = new Color((int)(Math.random()*256), (int)(Math.random()*256), 
			     (int)(Math.random()*256));
    Color color2 = new Color((int)(Math.random()*256), (int)(Math.random()*256), 
			     (int)(Math.random()*256));

    /*    public void initPopsicles(int x, int y, int w, int h, int n) {
	int xPos;
	int yPos;
	int width;
	int height;
	for(int i = 0; i < n; i++) {
	    xPos = ((x + (int)(Math.pow(-1, i)) * i * 50) % (maxW - w));
	    yPos = ((y + ((int)(Math.pow(-1, i)) * i * (int)(Math.random()*50))) % (maxH - h));
	    width = (w + (int)(Math.pow(-1, i) * i * (int)(Math.random()*5)));
	    height = (h + (int)(Math.pow(-1, i) * i * (int)(Math.random()*10)));
	    psArray.add(new PopsicleOnAStick((double) xPos, (double) yPos, (double) width, (double) height));
	}
	}*/
    /*
    public AnimatedPictureViewer() {
	initPopsicles(x, y, w, h, n);
	}*/
    
    public static void main (String[] args) {
	new AnimatedPictureViewer().go();
    }
 
    public void go() {
	JFrame frame = new JFrame();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	frame.getContentPane().add(panel);
	frame.setSize(maxW, maxH);
	frame.setVisible(true);
	
	frame.getContentPane().addMouseListener(new MouseAdapter() {
		public void mouseEntered(MouseEvent e){
		    anim = new Animation();
		    color = new Color((int)(Math.random()*256), (int)(Math.random()*256), 
				       (int)(Math.random()*256));
		    color2 = new Color((int)(Math.random()*256), (int)(Math.random()*256), 
				       (int)(Math.random()*256));
		    speed = (int) (Math.random() * 60);
		    anim.start();
		}
		
		public void mouseExited(MouseEvent e){        
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

	    g2.setFont(new Font("Veranda", Font.PLAIN, 10));
	    g2.setColor(Color.BLACK);
	    g2.drawString("Bouncing Popsicles by Jenna Cryan", 0, 15);

	    Stroke thick = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
	    g2.setStroke(thick);
	    g2.setColor(color);
	    // Draw the popsicles
	    /* for(int i = 0; i < psArray.size(); i++) {
		g2.draw(psArray.get(i));
		}*/
     	    PopsicleOnAStick ps1 = new PopsicleOnAStick(x, y, w, h);
	    PopsicleOnAStick ps2 = new PopsicleOnAStick(x2, y2, w, h);
	    g2.draw(ps1);
	    g2.setColor(color2);
	    g2.draw(ps2);
	}
    }
    
    class Animation extends Thread {
	public void run() {
	    try {
		while (true) {
		    // Bounce off the walls
		    /* for(int i = 0; i < psArray.size(); i++) {
			if(psArray.get(i).getY() >= (maxH - h)) { 
			    dy = -5;
			}
			if(psArray.get(i).getY() <= 0) { 
			    dy = 5;
			}
			psArray.get(i).upDown(dy);
			}*/

		    if(y >= (maxH - (h * 11 / 8))) {
			dy = -5;
		    }
		    if(y <= 0) {
			dy = 5;
		    }
		    if(x >= (maxW - (w * 9 / 8))) {
			dx = -3;
		    }
		    if(x <= 0) {
			dx = 3;
		    }
		    y += dy;
		    x += dx;

		    if(y2 >= (maxH - (h * 11 / 8))) {
			dy2 = -3;
		    }
		    if(y2 <= 0) {
			dy2 = 3;
		    }
		    if(x2 >= (maxW - (w * 9 / 8))) {
			dx2 = -2;
		    }
		    if(x2 <= 0) {
			dx2 = 2;
		    }
		    y2 += dy2;
		    x2 += dx2;

		    panel.repaint();
		    Thread.sleep(speed);
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
