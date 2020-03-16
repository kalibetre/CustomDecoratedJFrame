// Copyright 2020 Kalkidan Betre Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.digitalpanther.ui.customjframe;

import com.digitalpanther.ui.customdecoration.CustomDecorationParameters;
import com.digitalpanther.ui.theme.Theme;
import com.digitalpanther.ui.usercontrols.ButtonType;
import com.digitalpanther.ui.usercontrols.ControlBoxJButton;
import com.digitalpanther.ui.customdecoration.CustomDecorationWindowProc;
import com.digitalpanther.ui.usercontrols.IconJPanel;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CustomJFrame extends JFrame {
   final Theme theme;
   final CustomDecorationWindowProc windowProcEx;
   private ControlBoxJButton restoreButton = null;
   private WindowFrameType windowFrameType = WindowFrameType.NORMAL;
   private JPanel titleBar;
   private JPanel titleBarCustomContent;
   private JPanel controlBox;
   private JPanel frameContentPane;
   private JPanel iconContainer;

   public CustomJFrame(Theme theme, String title) throws HeadlessException {
      super(title);
      this.theme = theme;
      windowProcEx = new CustomDecorationWindowProc();
      initializeFrame();
   }

   public CustomJFrame(Theme theme, String title, WindowFrameType windowFrameType){
      this(theme,title);
      this.windowFrameType = windowFrameType;
      initializeFrame();
   }

   @Override
   public void setVisible(boolean b) {
      super.setVisible(b);
      windowProcEx.init(getHwnd());
   }

   private WinDef.HWND getHwnd(){
      WinDef.HWND hwnd = new WinDef.HWND();
      hwnd.setPointer(Native.getComponentPointer(this));
      return hwnd;
   }

   public void setRestoreButton(ControlBoxJButton restoreButton) {
      this.restoreButton = restoreButton;
   }

   public ControlBoxJButton getRestoreButton() {
      return restoreButton;
   }

   private void initializeFrame(){
      setLayout(new BorderLayout());

      frameContentPane = new JPanel();
      frameContentPane.setLayout(new BorderLayout());
      frameContentPane.setOpaque(false);
      setupFrameTitleBar();

      JPanel clientContentPane = new JPanel();
      clientContentPane.setLayout(new FlowLayout());
      clientContentPane.setOpaque(false);
      frameContentPane.add(clientContentPane);

      setContentPane(frameContentPane);
      setBackground(theme.getDefaultBackgroundColor());

      addComponentListener(new ComponentAdapter() {
         @Override
         public void componentResized(ComponentEvent e) {
            super.componentResized(e);
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
               mainFrame.getRootPane().setBorder(BorderFactory.createLineBorder(theme.getFrameBorderColor(),
                       CustomDecorationParameters.getFrameBorderThickness()));
               if(mainFrame.getRestoreButton() != null && mainFrame.getRestoreButton().controlBoxButtonType != ButtonType.MAXIMIZE){
                  mainFrame.getRestoreButton().setControlBoxButtonType(ButtonType.MAXIMIZE);
               }
            }
         }
      });

      pack();
      CustomDecorationParameters.setControlBoxWidth(controlBox.getWidth() + 10);
   }

   private void setupFrameTitleBar(){
      if(windowFrameType == WindowFrameType.NONE){
         CustomDecorationParameters.setTitleBarHeight(0);
      } else {
         titleBar = new JPanel();
         titleBar.setLayout(new BorderLayout());
         titleBar.setOpaque(false);

         iconContainer = new JPanel();
         iconContainer.setOpaque(false);

         setupFrameControlBox();
         titleBar.add(controlBox, BorderLayout.EAST);

         titleBarCustomContent = new JPanel();
         titleBarCustomContent.setLayout(new FlowLayout(3,0,0));
         titleBarCustomContent.setOpaque(false);
         titleBarCustomContent.add(iconContainer);

         titleBar.add(titleBarCustomContent, BorderLayout.WEST);
         frameContentPane.add(titleBar, BorderLayout.NORTH);
      }
   }

   private void setupFrameControlBox(){
      controlBox = new JPanel();
      controlBox.setOpaque(false);

      if(windowFrameType == WindowFrameType.NORMAL){
         controlBox.setLayout(new GridLayout(1, 3, -1, 0));
         addMinimizeButton();
         addMaximizeButton();
         addCloseButton();
      } else if(windowFrameType == WindowFrameType.TOOL){
         controlBox.setLayout(new GridLayout(1, 1, -1, 0));
         addCloseButton();
      }
   }

   private void addCloseButton(){
      ControlBoxJButton closeBtn = new ControlBoxJButton(ButtonType.CLOSE,theme);
      closeBtn.setPreferredSize(new Dimension(50, CustomDecorationParameters.getTitleBarHeight()));
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
      controlBox.add(closeBtn);
   }

   private void addMaximizeButton(){
      ControlBoxJButton maximizeBtn = new ControlBoxJButton(ButtonType.MAXIMIZE, theme);
      maximizeBtn.setPreferredSize(new Dimension(50, CustomDecorationParameters.getTitleBarHeight()));
      maximizeBtn.setBackground(theme.getDefaultBackgroundColor());
      maximizeBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            if (getExtendedState() == JFrame.MAXIMIZED_BOTH)
               setExtendedState(JFrame.NORMAL);
            else
               setExtendedState(JFrame.MAXIMIZED_BOTH);
         }
      });
      controlBox.add(maximizeBtn);
      setRestoreButton(maximizeBtn);
   }

   private void addMinimizeButton(){
      ControlBoxJButton minimizeBtn = new ControlBoxJButton(ButtonType.MINIMIZE, theme);
      minimizeBtn.setPreferredSize(new Dimension(50, CustomDecorationParameters.getTitleBarHeight()));
      minimizeBtn.setBackground(theme.getDefaultBackgroundColor());
      minimizeBtn.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            setExtendedState(JFrame.ICONIFIED);
         }
      });
      controlBox.add(minimizeBtn);
   }

   public void addUserControlsToTitleBar(Component component){
      titleBarCustomContent.add(component);
      pack();
      CustomDecorationParameters.setExtraLeftReservedWidth(titleBarCustomContent.getWidth() + 10);
   }

   public int getTitleBarHeight(){
      pack();
      return titleBarCustomContent.getHeight();
   }

   public void setIcon(Image image){
      iconContainer.setLayout(new FlowLayout(1,0,0));
      iconContainer.setPreferredSize(new Dimension(
              CustomDecorationParameters.getIconWidth(),
              CustomDecorationParameters.getTitleBarHeight()));

      IconJPanel iconJPanel = new IconJPanel(image);
      iconContainer.add(iconJPanel);
      pack();
      CustomDecorationParameters.setExtraLeftReservedWidth(titleBarCustomContent.getWidth() + 10);
   }
}
