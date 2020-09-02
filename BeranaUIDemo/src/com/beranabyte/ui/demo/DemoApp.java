// Copyright 2020 Kalkidan Betre Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.beranabyte.ui.demo;

import com.beranabyte.ui.customjframe.CustomJFrame;
import com.beranabyte.ui.customjframe.WindowFrameType;
import com.beranabyte.ui.theme.DarkTheme;
import com.beranabyte.ui.theme.Theme;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
            CustomJFrame frame = new CustomJFrame(darkTheme ,"Custom Decorated Window", WindowFrameType.NORMAL);
            frame.setMinimumSize(new Dimension(600, 400));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            JMenuBar jMenuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
            JMenu editMenu = new JMenu("Edit");
            JMenu viewMenu = new JMenu("View");

            JMenuItem openMenu = new JMenuItem("Open");
            JMenuItem closeMenu = new JMenuItem("Close");
            fileMenu.add(openMenu);
            fileMenu.add(closeMenu);

            JMenuItem copyMenu = new JMenuItem("Copy");
            editMenu.add(copyMenu);

            JMenuItem toolsMenu = new JMenuItem("Tools");
            viewMenu.add(toolsMenu);

            jMenuBar.add(fileMenu);
            jMenuBar.add(editMenu);
            jMenuBar.add(viewMenu);
            frame.addUserControlsToTitleBar(jMenuBar);

            ImageIcon imageIcon = new ImageIcon("resources/appicon.png");
            frame.setIcon(imageIcon.getImage());

            frame.addJFrameCloseEventAdapter(new MouseAdapter() {
               @Override
               public void mouseClicked(MouseEvent e) {
                  int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the app ?");
                  if (response == JOptionPane.OK_OPTION) {
                         System.exit(0);
                  }
               }
            });


         }
      });
   }
}

