/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package G_Swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSpinnerUI;

/**
 *
 * @author mario
 */
public class SpinnerUI extends BasicSpinnerUI {

    @Override
    public void installUI(JComponent jc) {
        super.installUI(jc);
        spinner.setEditor(new Editor(spinner));
    }

    @Override
    protected Component createNextButton() {
        ArrowButton cmd = new ArrowButton(true);
        installNextButtonListeners(cmd);
        return cmd;
    }

    @Override
    protected Component createPreviousButton() {
        ArrowButton cmd = new ArrowButton(false);
        installPreviousButtonListeners(cmd);
        return cmd;
    }

    public class Editor extends TextField_spinner implements ChangeListener {

        public Editor(JSpinner spinner) {
            spinner.addChangeListener(this);
            setEditable(false);
            setText("1");
        }

        @Override
        public void stateChanged(ChangeEvent ce) {
            JSpinner spinner = (JSpinner) ce.getSource();
            setText(spinner.getValue().toString());
        }
    }

    private class ArrowButton extends JButton {

        private final boolean next;

        public ArrowButton(boolean next) {
            this.next = next;
            setContentAreaFilled(false);
            setFocusable(false);
            setBorder(new EmptyBorder(5, 7, 5, 7));
            setBackground(new Color(250, 250, 250));
            setForeground(new Color(89, 89, 89));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent me) {
                    setSelected(true);
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                    setSelected(false);
                }
                
                @Override
                public void mouseEntered(MouseEvent e) {
                    spinner.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    spinner.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });
        }

        @Override
        public void paint(Graphics grphcs) {
            super.paint(grphcs);
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 1, getWidth(), getHeight() - 2, 5, 5);
            if (next) {
                int width = getWidth();
                int height = getHeight();
                int size = 10;
                int x = (width - size) / 2;
                int y = (height - size) / 2;
                int px[] = {x + size / 2, x + size, x};
                int py[] = {y, y + size, y + size};
                if (isSelected()) {
                    g2.setColor(new Color(18, 181, 181));
                } else {
                    g2.setColor(getForeground());
                }
                g2.fillPolygon(px, py, px.length);
            } else {
                int width = getWidth();
                int height = getHeight();
                int size = 10;
                int x = (width - size) / 2;
                int y = (height - size) / 2;
                int px[] = {x, x + size, x + size / 2};
                int py[] = {y, y, y + size};
                if (isSelected()) {
                    g2.setColor(new Color(18, 181, 181));
                } else {
                    g2.setColor(getForeground());
                }
                g2.fillPolygon(px, py, px.length);
            }
            g2.dispose();
        }
    }
}