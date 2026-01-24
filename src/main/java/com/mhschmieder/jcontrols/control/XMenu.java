/**
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
import com.mhschmieder.jcommons.util.GlobalUtilities;
import com.mhschmieder.jcommons.util.SystemType;

import javax.swing.JMenu;
import java.util.ResourceBundle;

public class XMenu extends JMenu {
    /**
    *
    */
   private static final long   serialVersionUID    = -4966855058902105917L;

   // Cache the Client Properties (System Type, Locale, etc.).
   protected ClientProperties    _clientProperties;

   // Default constructor
   protected XMenu( final ClientProperties clientProperties,
                       final String bundleName,
                       final String menuName,
                       final String iconFilename ) {
       // Always call the superclass constructor first!
       super();

       _clientProperties = clientProperties;

       try {
           initMenu( bundleName, menuName, iconFilename );
       }
       catch ( final Exception ex ) {
           ex.printStackTrace();
       }
   }

   // Fully qualified constructor
   public XMenu(    final ClientProperties clientProperties,
                       final String bundleName,
                       final String menuName,
                       final String enabledIconFilename,
                       final String disabledIconFilename ) {
       // Always call the superclass constructor first!
       super();

       _clientProperties = clientProperties;

       try {
           initMenu(   bundleName,
                       menuName,
                       enabledIconFilename,
                       disabledIconFilename );
       }
       catch ( final Exception ex ) {
           ex.printStackTrace();
       }
   }

   private final void initMenu(    final String bundleName,
                                   final String menuName,
                                   final String iconFilename ) {
       final ResourceBundle resourceBundle = GlobalUtilities
               .getResourceBundle( _clientProperties, bundleName, false );

       // Set all of the resources for the menu item (e.g. text, mnemonic,
       // icon).
       ButtonUtilities
               .setButtonResources(    this,
                                       menuName,
                                       null,
                                       iconFilename,
                                       resourceBundle,
                                       !SystemType.MACOS
                                               .equals( _clientProperties.systemType ) );
   }

   private final void initMenu(    final String bundleName,
                                   final String menuName,
                                   final String enabledIconFilename,
                                   final String disabledIconFilename ) {
       final ResourceBundle resourceBundle = GlobalUtilities
               .getResourceBundle( _clientProperties, bundleName, false );

       // Set all of the resources for the menu item (e.g. text, mnemonic,
       // icon).
       ButtonUtilities
               .setButtonResources(    this,
                                       menuName,
                                       null,
                                       enabledIconFilename,
                                       disabledIconFilename,
                                       resourceBundle,
                                       !SystemType.MACOS
                                               .equals( _clientProperties.systemType ) );
   }
}
