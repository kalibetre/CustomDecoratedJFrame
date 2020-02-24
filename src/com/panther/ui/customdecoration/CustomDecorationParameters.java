// Copyright 2020 Kalkidan Betre Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
package com.panther.ui.customdecoration;

import java.util.concurrent.atomic.AtomicInteger;

public class CustomDecorationParameters {
   private static AtomicInteger titleBarHeight = new AtomicInteger(27);
   private static AtomicInteger controlBoxWidth = new AtomicInteger(150);
   private static AtomicInteger iconWidth = new AtomicInteger(27);
   private static AtomicInteger extraLeftReservedArea = new AtomicInteger(0);
   private static AtomicInteger extraRightReservedArea = new AtomicInteger(0);
   private static AtomicInteger maximizedWindowFrameThickness = new AtomicInteger(10);
   private static AtomicInteger frameResizeBorderThickness = new AtomicInteger(4);
   private static AtomicInteger frameBorderThickness = new AtomicInteger(1);

   public static int getControlBoxWidth() {
      return controlBoxWidth.get();
   }

   public static void setControlBoxWidth(int value) {
      controlBoxWidth.set(value);
   }

   public static int getIconWidth() {
      return iconWidth.get();
   }

   public static void setIconWidth(int value) {
      iconWidth.set(value);
   }

   public static int getExtraLeftReservedArea() {
      return extraLeftReservedArea.get();
   }

   public static void setExtraLeftReservedArea(int value) {
      extraLeftReservedArea.set(value);
   }

   public static int getExtraRightReservedArea() {
      return extraRightReservedArea.get();
   }

   public static void setExtraRightReservedArea(int value) {
      extraRightReservedArea.set(value);
   }

   public static int getMaximizedWindowFrameThickness() {
      return maximizedWindowFrameThickness.get();
   }

   public static void setMaximizedWindowFrameThickness(int maximizedWindowFrameThickness) {
      CustomDecorationParameters.maximizedWindowFrameThickness.set(maximizedWindowFrameThickness);
   }

   public static int getTitleBarHeight() {
      return titleBarHeight.get();
   }

   public static void setTitleBarHeight(int value) {
      CustomDecorationParameters.titleBarHeight.set(value);
   }

   public static int getFrameResizeBorderThickness() {
      return frameResizeBorderThickness.get();
   }

   public static void setFrameResizeBorderThickness(int value) {
      CustomDecorationParameters.frameResizeBorderThickness.set(value);
   }

   public static int getFrameBorderThickness() {
      return frameBorderThickness.get();
   }

   public static void setFrameBorderThickness(int value) {
      CustomDecorationParameters.frameBorderThickness.set(value);
   }
}
