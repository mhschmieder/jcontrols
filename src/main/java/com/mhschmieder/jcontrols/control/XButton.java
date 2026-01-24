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

import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.util.ResourceBundle;

/**
 * {@code XButton} is an enhanced frame base class for Swing that adds
 * boilerplate code to reduce copy/paste mistakes and inconsistencies in new
 * projects. It isn't declared as abstract, as often it is enough on its own.
 * <p>
 * Much of the purpose of this class is to better manage initialization order of
 * resource loading and container creation, as well as to normalize menu and
 * tool bar handling and visual features such as background to foreground
 * contrast to avoid masking of graphical elements.
 * <p>
 * This class is skeletal for now, so that the release of all related libraries
 * is not held up any further.
 * <p>
 * TODO: Make this use selection-based custom colors (cf. ColorButton).
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class XButton extends JButton {
   /**
    *
    */
   private static final long   serialVersionUID        = -4989429288902434206L;

   private static final int    HSSIZE                  = 12;
   private static final int    VSSIZE                  = 2;

   private static final int    ICON_SIZE_MINIMUM       = 24;
   private static final int    ICON_SIZE_MAXIMUM       = 32;

   private static final int    WIDTH_FACTOR_DEFAULT    = 2;
   private static final int    HEIGHT_FACTOR_DEFAULT   = 1;

   // Cache the Client Properties (System Type, Locale, etc.).
   protected ClientProperties  _clientProperties;

   private final int           _widthFactor;
   private final int           _heightFactor;

   // TODO: Extract resource strings for all buttons that use this
   //  constructor, so we can have it invoke the fully-qualified constructor and
   //  use the resource extraction methodology for localization.
   public XButton( final ClientProperties clientProperties,
                   final String buttonText,
                   final String toolTipText,
                   final String iconFilename ) {
       // Always call the superclass constructor first!
       super();

       _clientProperties = clientProperties;

       // TODO: Pass these in for more custom control over proportions, along
       //  with a "size to fit" flag?
       _widthFactor = WIDTH_FACTOR_DEFAULT;
       _heightFactor = HEIGHT_FACTOR_DEFAULT;

       try {
           initButton( buttonText, toolTipText, iconFilename, false );
       }
       catch ( final Exception ex ) {
           ex.printStackTrace();
       }
   }

   // TODO: Extract resource strings for all buttons that use this
   //  constructor, so we can have it invoke the fully-qualified constructor and
   //  use the resource extraction methodology for localization.
   public XButton( final ClientProperties clientProperties,
                       final String buttonText,
                       final String toolTipText,
                       final String iconFilename,
                       final Color backgroundColor,
                       final Color foregroundColor ) {
       // Always call the superclass constructor first!
       this( clientProperties, buttonText, toolTipText, iconFilename );

       setBackground( backgroundColor );
       setForeground( foregroundColor );
   }

   public XButton( final ClientProperties clientProperties,
                       final String bundleName,
                       final String groupName,
                       final String itemName,
                       final String iconFilename ) {
       // Always call the superclass constructor first!
       super();

       _clientProperties = clientProperties;

       // :TODO: Pass these in for more custom control over proportions, along
       // with a "size to fit" flag?
       _widthFactor = WIDTH_FACTOR_DEFAULT;
       _heightFactor = HEIGHT_FACTOR_DEFAULT;

       try {
           initButton( bundleName, groupName, itemName, iconFilename, false );
       }
       catch ( final Exception ex ) {
           ex.printStackTrace();
       }
   }

   public XButton( final ClientProperties clientProperties,
                       final String bundleName,
                       final String groupName,
                       final String itemName,
                       final String enabledIconFilename,
                       final String disabledIconFilename ) {
       // Always call the superclass constructor first!
       super();

       _clientProperties = clientProperties;

       // :TODO: Pass these in for more custom control over proportions, along
       // with a "size to fit" flag?
       _widthFactor = WIDTH_FACTOR_DEFAULT;
       _heightFactor = HEIGHT_FACTOR_DEFAULT;

       try {
           initButton( bundleName,
                       groupName,
                       itemName,
                       enabledIconFilename,
                       disabledIconFilename,
                       false );
       }
       catch ( final Exception ex ) {
           ex.printStackTrace();
       }
   }

   private final void initButton(  final String buttonText,
                                   final String toolTipText,
                                   final String iconFilename,
                                   final boolean sizeToFit ) {
       // Set the button icon from the JAR-resident resource, if applicable,
       // but set the button text if no icon is loaded.
       if ( ButtonUtilities.setButtonIcons( this, iconFilename ) ) {
           // Nullify the text, so the layout manager doesn't waste space.
           setText( null );

           // Make icon-based buttons square, with soft raised beveled borders
           // (i.e. with rounded corners).
           final Border border = new SoftBevelBorder( BevelBorder.RAISED );
           setBorder( border );
           setSize( new Dimension( ICON_SIZE_MAXIMUM, ICON_SIZE_MAXIMUM ) );
           setPreferredSize( new Dimension(    ICON_SIZE_MAXIMUM,
                                               ICON_SIZE_MAXIMUM ) );
           setMinimumSize( new Dimension( ICON_SIZE_MINIMUM, ICON_SIZE_MINIMUM ) );
           setMaximumSize( new Dimension( ICON_SIZE_MAXIMUM, ICON_SIZE_MAXIMUM ) );
           setContentAreaFilled( false );
           setBorderPainted( false );
       }
       else if ( buttonText != null ) {
           setText( buttonText );

           // Attempt to resize this button to fit the text.
           // :NOTE: It may be necessary to instead override the various
           // getSize() functions to return the desired size.
           if ( sizeToFit ) {
               final FontMetrics fontMetrics = getFontMetrics( getFont() );
               final int slen = fontMetrics.stringWidth( buttonText );
               final int sht = fontMetrics.getHeight();
               final Insets margin = getMargin();
               final Dimension psize = new Dimension( margin.left
                       + ( slen * _widthFactor ) + margin.right, margin.bottom
                       + ( sht * _heightFactor ) + margin.top );
               setSize( psize );
               setPreferredSize( psize );
               setMinimumSize( psize );
               setMaximumSize( psize );
           }

           setOpaque( true );
       }

       // Set the tool tip text for this button, whether an image is used or it
       // only has standard button text.
       setToolTipText( toolTipText );

       // Add indentation (insets/margins), and use centering, for a less
       // cluttered appearance, and try to force border and rollover painting.
       setRolloverEnabled( true );
       setMargin( new Insets( VSSIZE, HSSIZE, VSSIZE, HSSIZE ) );
       setAlignmentX( Component.CENTER_ALIGNMENT );
       setAlignmentY( Component.CENTER_ALIGNMENT );
   }

   private final void initButton(  final String bundleName,
                                   final String groupName,
                                   final String itemName,
                                   final String iconFilename,
                                   final boolean sizeToFit ) {
       final ResourceBundle resourceBundle = GlobalUtilities
               .getResourceBundle( _clientProperties, bundleName, false );

       // Set the button icon from the JAR-resident resource, if applicable,
       // but set the button text if no icon is loaded.
       if ( ButtonUtilities.setButtonIcons( this, iconFilename ) ) {
           // Nullify the text, so the layout manager doesn't waste space.
           setText( null );

           // Make icon-based buttons square, with soft raised beveled borders
           // (i.e. with rounded corners).
           final Border border = new SoftBevelBorder( BevelBorder.RAISED );
           setBorder( border );
           setSize( new Dimension( ICON_SIZE_MAXIMUM, ICON_SIZE_MAXIMUM ) );
           setPreferredSize( new Dimension(    ICON_SIZE_MAXIMUM,
                                               ICON_SIZE_MAXIMUM ) );
           setMinimumSize( new Dimension( ICON_SIZE_MINIMUM, ICON_SIZE_MINIMUM ) );
           setMaximumSize( new Dimension( ICON_SIZE_MAXIMUM, ICON_SIZE_MAXIMUM ) );
           setContentAreaFilled( false );
           setBorderPainted( false );
       }
       else {
           // Set the button text (but not the mnemonic, as they don't work on
           // window-level buttons) from the resource bundle, if applicable.
           ButtonUtilities.setButtonText(  this,
                                           groupName,
                                           itemName,
                                           resourceBundle,
                                           false );

           // Attempt to resize this button to fit the text.
           // :NOTE: It may be necessary to instead override the various
           // getSize() functions to return the desired size.
           if ( sizeToFit ) {
               final FontMetrics fontMetrics = getFontMetrics( getFont() );
               final int slen = fontMetrics.stringWidth( getText() );
               final int sht = fontMetrics.getHeight();
               final Insets margin = getMargin();
               final Dimension psize = new Dimension( margin.left
                       + ( slen * _widthFactor ) + margin.right, margin.bottom
                       + ( sht * _heightFactor ) + margin.top );
               setSize( psize );
               setPreferredSize( psize );
               setMinimumSize( psize );
               setMaximumSize( psize );
           }

           setOpaque( true );
       }

       // Set the tool tip text for this button, whether an image is used or it
       // only has standard button text.
       final String toolTipText = ButtonUtilities
               .getButtonToolTipText( groupName, itemName, resourceBundle );
       if ( toolTipText != null ) {
           setToolTipText( toolTipText );
       }

       // Add indentation (insets/margins), and use centering, for a less
       // cluttered appearance, and try to force border and rollover painting.
       setRolloverEnabled( true );
       setMargin( new Insets( VSSIZE, HSSIZE, VSSIZE, HSSIZE ) );
       setAlignmentX( Component.CENTER_ALIGNMENT );
       setAlignmentY( Component.CENTER_ALIGNMENT );
   }

   private final void initButton(  final String bundleName,
                                   final String groupName,
                                   final String itemName,
                                   final String enabledIconFilename,
                                   final String disabledIconFilename,
                                   final boolean sizeToFit ) {
       final ResourceBundle resourceBundle = GlobalUtilities
               .getResourceBundle( _clientProperties, bundleName, false );

       // Set the button icons from the JAR-resident resources, if applicable,
       // but set the button text if no icon is loaded.
       if ( ButtonUtilities.setButtonIcons(    this,
                                               enabledIconFilename,
                                               disabledIconFilename ) ) {
           // Nullify the text, so the layout manager doesn't waste space.
           setText( null );

           // Make icon-based buttons square, with soft raised beveled borders
           // (i.e. with rounded corners).
           final Border border = new SoftBevelBorder( BevelBorder.RAISED );
           setBorder( border );
           setSize( new Dimension( ICON_SIZE_MAXIMUM, ICON_SIZE_MAXIMUM ) );
           setPreferredSize( new Dimension(    ICON_SIZE_MAXIMUM,
                                               ICON_SIZE_MAXIMUM ) );
           setMinimumSize( new Dimension( ICON_SIZE_MINIMUM, ICON_SIZE_MINIMUM ) );
           setMaximumSize( new Dimension( ICON_SIZE_MAXIMUM, ICON_SIZE_MAXIMUM ) );
           setContentAreaFilled( false );
           setBorderPainted( false );
       }
       else {
           // Set the button text (but not the mnemonic, as they don't work on
           // window-level buttons) from the resource bundle, if applicable.
           ButtonUtilities.setButtonText(  this,
                                           groupName,
                                           itemName,
                                           resourceBundle,
                                           false );

           // Attempt to resize this button to fit the text.
           // :NOTE: It may be necessary to instead override the various
           // getSize() functions to return the desired size.
           if ( sizeToFit ) {
               final FontMetrics fontMetrics = getFontMetrics( getFont() );
               final int slen = fontMetrics.stringWidth( getText() );
               final int sht = fontMetrics.getHeight();
               final Insets margin = getMargin();
               final Dimension psize = new Dimension( margin.left
                       + ( slen * _widthFactor ) + margin.right, margin.bottom
                       + ( sht * _heightFactor ) + margin.top );
               setSize( psize );
               setPreferredSize( psize );
               setMinimumSize( psize );
               setMaximumSize( psize );
           }

           setOpaque( true );
       }

       // Set the tool tip text for this button, whether an image is used or it
       // only has standard button text.
       final String toolTipText = ButtonUtilities
               .getButtonToolTipText( groupName, itemName, resourceBundle );
       if ( toolTipText != null ) {
           setToolTipText( toolTipText );
       }

       // Add indentation (insets/margins), and use centering, for a less
       // cluttered appearance, and try to force border and rollover painting.
       setRolloverEnabled( true );
       setMargin( new Insets( VSSIZE, HSSIZE, VSSIZE, HSSIZE ) );
       setAlignmentX( Component.CENTER_ALIGNMENT );
       setAlignmentY( Component.CENTER_ALIGNMENT );
   }
}
