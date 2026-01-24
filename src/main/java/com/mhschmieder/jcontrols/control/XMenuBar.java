/*
 * MIT License
 *
 * Copyright (c) 2020, 2026 Mark Schmieder. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * This file is part of the GuiToolkit Library
 *
 * You should have received a copy of the MIT License along with the
 * GuiToolkit Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/guitoolkit
 */
package com.mhschmieder.jcontrols.control;

import com.mhschmieder.jcommons.util.ClientProperties;

import javax.swing.JMenuBar;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class XMenuBar  extends JMenuBar {
    /**
    *
    */
   private static final long   serialVersionUID    = -6016060768634838520L;

   // Keep a cached copy of the global rendering hints.
   private RenderingHints      _renderingHints;

   // Cache the Client Properties (System Type, Locale, etc.).
   protected ClientProperties    _clientProperties;

   // Default constructor
   protected XMenuBar( final ClientProperties clientProperties ) {
       // Always call the superclass constructor first!
       super();

       _clientProperties = clientProperties;
   }

   // Override the repaint callback function, so we can force rendering hints.
   // :NOTE: If this is done at initialization time, we have a null graphics
   // context.
   @Override
   public void paintComponent( final Graphics g ) {
       // :NOTE: We have to call the "super" class first, to make sure we
       // preserve the look-and-feel of the owner component.
       super.paintComponent( g );

       if ( _renderingHints != null ) {
           // Cast the graphics object so we can set rendering hints etc.
           final Graphics2D g2 = ( Graphics2D ) g;

           // Set the default rendering hints for this component.
           g2.addRenderingHints( _renderingHints );
       }
   }

   public final void setRenderingHints( final RenderingHints renderingHints ) {
       _renderingHints = renderingHints;
   }
}