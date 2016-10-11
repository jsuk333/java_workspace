package com.sds.client;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author CodeMaster
 */ // MASUNDUH II
public class MyPassword extends JPasswordField{
 public MyPassword()
  {
    setOpaque(false);
    setBorder(new EmptyBorder(5, 10, 5, 10));
    //setHorizontalAlignment(0);
    addMouseListener(new MouseAdapter()
    {
      @Override
      public void mouseEntered(MouseEvent e)
      {
        MyPassword.this.setCursor(Cursor.getPredefinedCursor(2));

      }

      @Override
      public void mouseExited(MouseEvent e)
      {
        MyPassword.this.setCursor(Cursor.getPredefinedCursor(0));

      }
    });
  }

 @Override
  protected void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    if (isEnabled()) {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      Color dark = new Color(1.0F, 1.0F, 1.0F, 0.0F);
      Color light = new Color(1.0F, 1.0F, 1.0F, 0.3F);
      GradientPaint paint = new GradientPaint(0.0F, 0.0F, light, 0.0F, getHeight() / 2, dark);
      g2.setPaint(paint);
      g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
      g2.setColor(Color.darkGray);
      g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());

      g2.dispose();
    } else {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

      Color light = new Color(1.0F, 1.0F, 1.0F, 0.3F);
      g2.setColor(light);
      g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight());
      g2.setColor(Color.darkGray);
      g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());

      g2.dispose();
    }
  }    
} 