package exercice1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import graphicLayer.GRect;
import graphicLayer.GSpace;

public class Exercice1_0 {
	GSpace space = new GSpace("Exercice 1", new Dimension(200, 150));
	GRect robi = new GRect();

	public Exercice1_0() {
		space.addElement(robi);
		space.open();
		robi.setColor(new Color((int) (Math.random() * 0x1000000)));
		
		
		
		int i =0;
		int j = 0;
		
		while(true) {

			robi.setColor(new Color((int) (Math.random() * 0x1000000)));
			for(j = 0;j< space.getWidth()-robi.getWidth();j++) {
				Point d = new Point(j, i);
				robi.setPosition(d);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(i = 0;i< space.getHeight()-robi.getHeight();i++) {
				Point d = new Point(j, i);
				robi.setPosition(d);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(;j>0;j--) {
				Point d = new Point(j, i);
				robi.setPosition(d);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			for(;i> 0;i--) {
				Point d = new Point(j, i);
				robi.setPosition(d);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		
	}

	public static void main(String[] args) {
		new Exercice1_0();
	}

}