// Copyright 2020 Kalkidan Betre Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.digitalpanther.ui.demo;

import com.digitalpanther.ui.CustomJFrame;
import com.digitalpanther.ui.theme.Theme;
import com.digitalpanther.ui.usercontrols.ButtonType;
import com.digitalpanther.ui.usercontrols.ControlBoxJButton;
import com.digitalpanther.ui.customdecoration.CustomDecorationParameters;
import com.digitalpanther.ui.theme.DarkTheme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DemoApp {
   public static void main(String[] args) {
      SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            try {
               UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
               e.printStackTrace();
            }

            final Theme darkTheme = new DarkTheme();
            CustomJFrame frame = new CustomJFrame(darkTheme ,"Custom Decorated Window");
            frame.setLayout(new BorderLayout());

            JPanel controlBox = new JPanel();
            controlBox.setLayout(new GridLayout(1, 3, -1, 0));
            controlBox.setOpaque(false);
            setupFrameControlBox(frame, controlBox, darkTheme);

            JPanel titleBar = new JPanel();
            titleBar.setLayout(new BorderLayout());
            titleBar.setOpaque(false);
            titleBar.add(controlBox, BorderLayout.EAST);

            JPanel frameContentPane = new JPanel();
            frameContentPane.setLayout(new BorderLayout());
            frameContentPane.setOpaque(false);
            frameContentPane.add(titleBar, BorderLayout.NORTH);

            JPanel clientContentPane = new JPanel();
            clientContentPane.setLayout(new FlowLayout());
            clientContentPane.setOpaque(false);
            frameContentPane.add(clientContentPane);

            frame.setContentPane(frameContentPane);
            frame.setBackground(darkTheme.getDefaultBackgroundColor());
            frame.setMinimumSize(new Dimension(600, 400));
            frame.addComponentListener(new ComponentAdapter() {
               @Override
               public void componentResized(ComponentEvent e) {
                  super.componentResized(e);
                  // TODO: Set the Necessary Widths
                  CustomJFrame mainFrame = (CustomJFrame) e.getSource();
                  if (mainFrame.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                     CustomDecorationParameters.setMaximizedWindowFrameThickness(12);
                     mainFrame.getRootPane().setBorder(BorderFactory.createLineBorder(mainFrame.getBackground(),
                             CustomDecorationParameters.getMaximizedWindowFrameThickness()));
                     if(mainFrame.getRestoreButton() != null){
                        mainFrame.getRestoreButton().setControlBoxButtonType(ButtonType.RESTORE);
                     }
                  } else {
                     CustomDecorationParameters.setMaximizedWindowFrameThickness(0);
                     mainFrame.getRootPane().setBorder(BorderFactory.createLineBorder(darkTheme.getFrameBorderColor(),
                             CustomDecorationParameters.getFrameBorderThickness()));
                     if(mainFrame.getRestoreButton() != null && mainFrame.getRestoreButton().controlBoxButtonType != ButtonType.MAXIMIZE){
                        mainFrame.getRestoreButton().setControlBoxButtonType(ButtonType.MAXIMIZE);
                     }
                  }
               }
            });
            frame.pack();
            CustomDecorationParameters.setControlBoxWidth(controlBox.getWidth() + 10);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
         }
      });
   }

   private static void setupFrameControlBox(CustomJFrame frame, JPanel controlBox, Theme theme){
      ControlBoxJButton minimizeBtn = new ControlBoxJButton(ButtonType.MINIMIZE, theme);
      minimizeBtn.setPreferredSize(new Dimension(50, CustomDecorationParameters.getTitleBarHeight()));
      minimizeBtn.setBackground(theme.getDefaultBackgroundColor());
      minimizeBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            frame.setExtendedState(JFrame.ICONIFIED);
         }
      });

      ControlBoxJButton maximizeBtn = new ControlBoxJButton(ButtonType.MAXIMIZE, theme);
      maximizeBtn.setPreferredSize(new Dimension(50, 27));
      maximizeBtn.setBackground(theme.getDefaultBackgroundColor());
      maximizeBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH)
               frame.setExtendedState(JFrame.NORMAL);
            else
               frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
         }
      });

      ControlBoxJButton closeBtn = new ControlBoxJButton(ButtonType.CLOSE,theme);
      closeBtn.setPreferredSize(new Dimension(50, 27));
      closeBtn.setBackground(theme.getDefaultBackgroundColor());
      closeBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the app ?");
            if (response == JOptionPane.OK_OPTION) {
               System.exit(0);
            }
         }
      });

      controlBox.add(minimizeBtn);
      controlBox.add(maximizeBtn);
      controlBox.add(closeBtn);

      frame.setRestoreButton(maximizeBtn);
   }

}

