/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package FT_menu.image;


import FT_menu.WrapLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;


/**
 *
 * @author mario
 */

public class Sp_background extends JPanel {
    
    private BufferedImage bufferedImage;
    private Icon image;
    public Sp_background() {
        setLayout(new WrapLayout(WrapLayout.LEFT, 10, 10));
        image = new ImageIcon(getClass().getResource("/FT_menu/image/background-mnu.jpg"));
    }
    
    private void createImage() {
            int width = getWidth();
            int height = getHeight();
            if (width > 0 && height > 0) {
                bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = bufferedImage.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                Rectangle rec = getAutoSize(image);
                g2.drawImage(((ImageIcon) image).getImage(), rec.x, rec.y, rec.width, rec.height, null);
                g2.dispose();
            }
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        
        super.paintComponent(grphcs);
        BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        g2.drawImage(bufferedImage, 0, 0, null);
        g2.dispose();
        grphcs.drawImage(img, 0, 0, null);
    }
    
    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createImage();
                repaint();
            }
        });
    }
    
    private Rectangle getAutoSize(Icon image) {
        int w = getWidth();
        int h = getHeight();
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.max(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        if (width < 1) {
            width = 1;
        }
        if (height < 1) {
            height = 1;
        }
        int x = (w - width) / 2;
        int y = (h - height) / 2;
        return new Rectangle(new Point(x, y), new Dimension(width, height));
    }
}