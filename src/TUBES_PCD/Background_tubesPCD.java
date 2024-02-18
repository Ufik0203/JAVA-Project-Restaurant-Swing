/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TUBES_PCD;

import com.twelvemonkeys.image.ImageUtil;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import G_Border.FancyBorderRadius;
import G_Shadow.ShadowRenderer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mario
 */
public class Background_tubesPCD extends JComponent {

    private Icon image;
    private BufferedImage bufferedImage;
    private List<Component> blurComponents = new ArrayList<>();

    public Background_tubesPCD() {
        image = new ImageIcon(getClass().getResource("/TUBES_PCD/bcg.jpg"));
    }

    public void addBlurComponent(Component blur) {
        blurComponents.add(blur);
        SwingUtilities.invokeLater(() -> {
            createImage();
            repaint();
        });
    }

    public void removeBlurComponent(Component blur) {
        blurComponents.remove(blur);
        SwingUtilities.invokeLater(() -> {
            createImage();
            repaint();
        });
    }

    private void createImage() {
        if (image != null) {
            int width = getWidth();
            int height = getHeight();
            if (width > 0 && height > 0) {
                bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = bufferedImage.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                Rectangle rec = getAutoSize(image);
                g2.drawImage(((ImageIcon) image).getImage(), rec.x, rec.y, rec.width, rec.height, null);
                for (Component blur : blurComponents) {
                    if (blur != null) {
                        createBlurImage(g2, blur);
                    }
                }
                g2.dispose();
            }
        }
    }

    private void createBlurImage(Graphics2D g, Component blur) {
        int x = blur.getX();
        int y = blur.getY();
        int width = blur.getWidth();
        int height = blur.getHeight();
        int shadow = 8;
        x = Math.max(0, x);
        y = Math.max(0, y);
        width = Math.min(width, bufferedImage.getWidth() - x);
        height = Math.min(height, bufferedImage.getHeight() - y);
        if (width > 0 && height > 0) {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            RoundRectangle2D shape = new RoundRectangle2D.Double(0, 0, width, height, 0, 0);
            g2.fill(shape);
            g2.setComposite(AlphaComposite.SrcIn);
        //    System.out.println("x: " + x + ", y: " + y + ", width: " + width + ", height: " + height); check size
            g2.drawImage(ImageUtil.blur(bufferedImage.getSubimage(x, y, width, height), 30f), 0, 0, null);
            g2.setComposite(AlphaComposite.SrcOver);
            g2.setColor(new Color(255, 255, 255, 50));
            g2.fill(shape);
            g2.dispose();
            g.drawImage(new ShadowRenderer(shadow, 0.3f, new Color(0, 0, 0)).createShadow(img), (int) (x - shadow * 0.8f), (int) (y - shadow * 0.8f), null);
            g.drawImage(img, x, y, null);
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        if (bufferedImage != null) {
            BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
            g2.setComposite(AlphaComposite.SrcIn);
            g2.drawImage(bufferedImage, 0, 0, null);
            g2.dispose();
            grphcs.drawImage(img, 0, 0, null);
        }
        super.paintComponent(grphcs);
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
