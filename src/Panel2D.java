import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.PaintContext;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

import javax.swing.JPanel;

public class Panel2D extends JPanel{
	public Panel2D() {
		this.setPreferredSize(new Dimension(330,330));
		this.setLayout(new FlowLayout());
	}
	public void paint(Graphics g) {
		paintChildren(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(2));
		g2D.drawRect(4,1, 320, 317);
		g2D.drawLine(112, 0, 112, 317);
		g2D.drawLine(217, 0, 217, 317);
		g2D.drawLine(4, 108, 323, 108);
		g2D.drawLine(4, 213, 323, 213);
	}
}
