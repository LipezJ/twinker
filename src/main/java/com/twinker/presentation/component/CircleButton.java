package com.twinker.presentation.component;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class CircleButton extends JToggleButton {
    private final int diameter;
    private Shape shape;

    public CircleButton(int diameter) {
        this.diameter = diameter;
        Dimension size = new Dimension(diameter, diameter);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Color fill;
        if (getModel().isPressed()) {
            fill = new Color(10, 100, 200);
        } else if (getModel().isRollover() || isSelected()) {
            fill = new Color(30, 144, 255);
        } else {
            fill = new Color(65, 105, 225);
        }

        g2.setColor(fill);
        g2.fillOval(0, 0, diameter, diameter);

        String txt = getText();
        FontMetrics fm = g2.getFontMetrics(getFont());
        Rectangle2D tb = fm.getStringBounds(txt, g2);
        int x = (diameter - (int)tb.getWidth())/2;
        int y = (diameter - (int)tb.getHeight())/2 + fm.getAscent();

        g2.setFont(getFont());
        g2.setColor(getForeground());
        g2.drawString(txt, x, y);

        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null
                || !shape.getBounds().equals(new Rectangle(0, 0, diameter, diameter))) {
            shape = new Ellipse2D.Float(0, 0, diameter, diameter);
        }
        return shape.contains(x, y);
    }
}
