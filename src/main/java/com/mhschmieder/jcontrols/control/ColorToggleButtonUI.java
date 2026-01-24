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

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicToggleButtonUI;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * A possibly superseded c lass that was used until 2008 for dealing with some
 * look-and-feel based approaches to setting colors in custom toggle buttons.
 */
public class ColorToggleButtonUI extends BasicToggleButtonUI {
    public static final int HSSIZE  = 4;
    public static final int VSSIZE  = 4;

    public ColorToggleButtonUI() {}

    @Override
    public void paint( final Graphics g, final JComponent c ) {
        final AbstractButton b = ( AbstractButton ) ( c );
        paintButtonPressed( g, b );
    }

    @Override
    protected void paintButtonPressed( final Graphics g, final AbstractButton b ) {
        // Save the original colour to prevent side effects.
        final Color color = g.getColor();

        // Choose the inset colour and text colour based on the button's
        // background colour, to make sure we don't end up with invisible
        // text if painting black on black (or dark grey) or white on
        // white (or light grey).
        final Color backgroundColor = b.getBackground();
        final Color insetColor = ( Color.BLACK.equals( backgroundColor ) || Color.DARK_GRAY
                .equals( backgroundColor ) )
            ? Color.LIGHT_GRAY
            : Color.DARK_GRAY;
        final Color textColor = ( Color.BLACK.equals( backgroundColor ) || Color.DARK_GRAY
                .equals( backgroundColor ) ) ? Color.WHITE : Color.BLACK;

        // Fill the insets, followed by the main button background.
        g.setColor( insetColor );
        g.fillRect( 0, 0, b.getSize().width, b.getSize().height );
        g.setColor( backgroundColor );
        g.fillRect( HSSIZE, VSSIZE, b.getSize().width - ( HSSIZE * 2 ), b
                .getSize().height
                - ( VSSIZE * 2 ) );

        // Paint the text, centered based on current vs. default text.
        final FontMetrics fontMetrics = b.getFontMetrics( b.getFont() );
        final int slen = fontMetrics.stringWidth( b.getText() );
        final int sht = fontMetrics.getHeight();
        final int trxstart = ( b.getWidth() - slen ) / 2;
        final int trystart = ( b.getHeight() - sht ) / 2;
        g.setColor( textColor );
        super.paintText(    g,
                            b,
                            new Rectangle( trxstart, trystart, slen, sht ),
                            b.getText() );

        // Restore the original colour to prevent side effects.
        g.setColor( color );
    }
}
