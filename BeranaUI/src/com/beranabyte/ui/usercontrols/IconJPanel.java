// Copyright 2020 Kalkidan Betre Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.beranabyte.ui.usercontrols;

import javax.swing.*;
import java.awt.*;

public class IconJPanel extends JPanel {
   private Image image;

   public IconJPanel(Image image){
      if(image != null){
         this.image = image;
         Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
         setPreferredSize(size);
         setMinimumSize(size);
         setMaximumSize(size);
         setSize(size);
         setLayout(null);
      }
   }

   @Override
   public void paintComponent(Graphics g){
      if(image != null)
         g.drawImage(image, 0, 0, null);
   }
}
