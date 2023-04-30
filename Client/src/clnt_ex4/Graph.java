package clnt_ex4;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Base64;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import com.fasterxml.jackson.annotation.JsonIgnore;

import graphicLayer.GBounded;
import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import graphicLayer.GString;



// fonctionne seulement pour toutes les fonctions graphiques qui ont des entiers en paramètre
// il faut gérer des cas particuliers pour les autres
// setColor, drawString, ...

public class Graph {

	String cmd = null;			// nom de la commande awt graphics
	int [] entiers = null;		// paramètres de type int
	String chaines = "";	// paramètres de type String
	int [] couleurs = {0,0,0 };		// couleurs RGB (3 entiers)
	
	@JsonIgnore
	public HashMap<String, String[]> com = new HashMap<String,  String[]>();
	
	
	
	public Graph() {
		com.put("rect", new String[]{"drawRect","fillRect"});
		com.put("oval", new String[]{"drawOval","fillOval"});
		com.put("image", new String[]{"drawImage"});
		com.put("label", new String[]{"drawString"});
		com.put("space", new String[]{"setBackground"});
	}

	public void draw(JComponent co) {
		Graphics g = co.getGraphics();
		try {
			Class<Graphics> c = Graphics.class;
			// si 4 params de type int
			// Method m1 = c.getDeclaredMethod(cmd,int.class,int.class,int.class,int.class);
			Method[] lm = c.getMethods();
			Color col = null;
			if (couleurs != null) {							
				col = new Color(couleurs[0],couleurs[1],couleurs[2]);
			}

			for (Method m : lm) {
				
				for(String commande : com.get(cmd)) {
					
					if (m.getName().equals(commande)) {

						if (m.getName().equals("drawString")) {
							System.out.println("Exec drawString");
							g.setColor(col);
							System.out.println("param drawString : "+chaines+" "+entiers[0]+" "+entiers[1]);
							g.drawString(chaines,entiers[0],entiers[1]);
						} else if (m.getName().equals("drawImage")) {
							if(m.getParameters().length == 6) {
								byte[] imageBytes = Base64.getDecoder().decode(chaines);
								BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
								g.drawImage(image,entiers[3],entiers[2],entiers[1],entiers[0],co);
								//m.invoke(g,image,entiers[0],entiers[1],entiers[2],entiers[3],co);
							}
							
						}
						else {
							Parameter [] lp = m.getParameters();
							System.out.println("Exec " + commande + " nb params = "+lp.length);
							g.setColor(col);
							if (lp.length == 0) {
								m.invoke(g);
							}
							else if (lp.length == 1) {
								m.invoke(g,entiers[0]);
							}
							else if (lp.length == 2) {
								m.invoke(g,entiers[0],entiers[1]);
							}
							else if (lp.length == 3) {
								m.invoke(g,entiers[0],entiers[1],entiers[2]);
							}
							else if (lp.length == 4) {
								m.invoke(g,entiers[2],entiers[3],entiers[1],entiers[0]);
							}
							else {
								System.out.println("Erreur Graph.draw : trop de paramètres : "+lp.length);
							}
						}
						
						break;
					}
				}
				if (com.get(cmd)[0].equals("setBackground")) {	
					//co.setBackground(col);
					System.out.println("color change");
					g.setColor(col);
					g.drawRect(0, 0, 1000, 1000);
					g.fillRect(0, 0, 1000, 1000);
					break;
				}
			}
			
		}
		catch (Exception e) {
			System.out.println("Erreur Graph.draw : "+e.getMessage());
		}
	} 

	public Graph(GBounded gbound) {
		int h,w,x,y;
		h = gbound.getHeight();
		w = gbound.getWidth();
		x = gbound.getX();
		y = gbound.getY();
		this.cmd = this.getclass(gbound);
		this.entiers = new int[] {h,w,x,y};
		if(gbound instanceof GString) {
			this.chaines = ((GString)gbound).getStr();
		} else if(gbound instanceof GImage) {
			this.chaines = getImageBase64(((GImage)gbound).getRawImage());
		}
		this.couleurs[0] = gbound.getColor().getRed();
		this.couleurs[1] = gbound.getColor().getGreen();
		this.couleurs[2] = gbound.getColor().getBlue();
	}
	
	public Graph(GSpace space) {
		int h,w;
		h = space.getHeight();
		w = space.getWidth();
		this.entiers = new int[] {h,w};
		this.cmd = "space";
		this.couleurs[0] = space.getBackground().getRed();
		this.couleurs[1] = space.getBackground().getGreen();
		this.couleurs[2] = space.getBackground().getBlue();
	}
	public String getclass(GBounded gbound) {
		if(gbound instanceof GRect) {
			return "rect";
		} else if(gbound instanceof GOval) {
			return "oval";
		} else if(gbound instanceof GImage) {
			return "image";
		} else if(gbound instanceof GString) {
			return "label";
		} else {
			return "meh";
		}		
	}
	public String getImageBase64(Image img) {

		 // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    try {
			ImageIO.write(bimage, cmd, baos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    byte[] bytes = baos.toByteArray();
	    return Base64.getEncoder().encodeToString(bytes);
    }
	
	
	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public int[] getEntiers() {
		return entiers;
	}

	public void setEntiers(int[] entiers) {
		this.entiers = entiers;
	}

	public int[] getCouleurs() {
		return couleurs;
	}

	public void setCouleurs(int[] couleurs) {
		this.couleurs = couleurs;
	}

	public String getChaines() {
		return chaines;
	}

	public void setChaines(String chaines) {
		this.chaines = chaines;
	}
	

	
	
	
	
}
