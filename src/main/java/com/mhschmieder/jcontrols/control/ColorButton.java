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

import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;

public class ColorButton extends XButton {
    /**
     * 
     */
    private static final long   serialVersionUID            = 5920993556602906029L;
    
    private static final Color  DISABLED_COLOR_DEFAULT      = Color.GRAY;
    private static final Color  DISABLED_TEXT_COLOR_DEFAULT = Color.BLACK;

    private static final int    FONT_SIZE_DEFAULT           = 14;

    private static final int    WIDTH_FACTOR_DEFAULT        = 1;
    private static final int    HEIGHT_FACTOR_DEFAULT       = 1;

    // Cache all of the configurable colors, for callbacks.
    private final Color         _enabledColor;
    private final Color         _disabledColor;
    private final Color         _enabledTextColor;
    private final Color         _disabledTextColor;

    public ColorButton( final ClientProperties clientProperties,
                        final String text,
                        final String toolTipText,
                        final String iconFilename,
                        final Color enabledColor,
                        final Color enabledTextColor,
                        final boolean enabled ) {
        this(   clientProperties,
                text,
                toolTipText,
                iconFilename,
                enabledColor,
                DISABLED_COLOR_DEFAULT,
                enabledTextColor,
                DISABLED_TEXT_COLOR_DEFAULT,
                FONT_SIZE_DEFAULT,
                WIDTH_FACTOR_DEFAULT,
                HEIGHT_FACTOR_DEFAULT,
                enabled );
    }

    public ColorButton( final ClientProperties clientProperties,
                        final String text,
                        final String toolTipText,
                        final String iconFilename,
                        final Color enabledColor,
                        final Color disabledColor,
                        final Color enabledTextColor,
                        final Color disabledTextColor,
                        final int fontSize,
                        final int widthFactor,
                        final int heightFactor,
                        final boolean enabled ) {
        // Always call the superclass constructor first!
        super( clientProperties, text, toolTipText, iconFilename );

        _enabledColor = enabledColor;
        _disabledColor = disabledColor;
        _enabledTextColor = enabledTextColor;
        _disabledTextColor = disabledTextColor;

        try {
            colorButtonInit( fontSize );
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void colorButtonInit( final int fontSize ) {
        // Set the painting modes to be used for all states.
        setBorderPainted( true );
        setFocusPainted( true );
        setRolloverEnabled( true );
        setContentAreaFilled( true );
        setOpaque( false );

        // Set a raised, beveled border, to make enabled state more obvious.
        setBorder( BorderFactory.createRaisedBevelBorder() );

        // Use a plain SanSerif font, for a consistent look and feel.
        // setFont( getFont().deriveFont( Font.PLAIN, fontSize ) );
        setFont( new Font( "SansSerif", Font.PLAIN, fontSize ) ); //$NON-NLS-1$
    }

    @Override
    public Color getBackground() {
        return isEnabled() ? _enabledColor : _disabledColor;
    }

    @Override
    public Color getForeground() {
        return isEnabled() ? _enabledTextColor : _disabledTextColor;
    }
}
