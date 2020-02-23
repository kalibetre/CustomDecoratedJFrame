package com.panther.ui;

import com.panther.ui.customdecoration.CustomDecorationWindowProc;
import com.panther.ui.theme.Theme;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;

import javax.swing.*;
import java.awt.*;

public class CustomJFrame extends JFrame {
   final Theme theme;
   final CustomDecorationWindowProc windowProcEx;

   public CustomJFrame(Theme theme, String title) throws HeadlessException {
      super(title);
      this.theme = theme;
      windowProcEx = new CustomDecorationWindowProc();
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

}
