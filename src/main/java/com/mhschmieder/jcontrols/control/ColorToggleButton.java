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
 * This file is part of the jcontrols Library
 *
 * You should have received a copy of the MIT License along with the jcontrols
 * Library. If not, see <https://opensource.org/licenses/MIT>.
 *
 * Project: https://github.com/mhschmieder/jcontrols
 */
package com.mhschmieder.jcontrols.control;

import com.mhschmieder.jcommons.util.ClientProperties;

import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Font;

public class ColorToggleButton extends JxToggleButton {
    /**
     * 
     */
    private static final long   serialVersionUID    = -8865610998422183221L;

    private static final int    FONT_SIZE_DEFAULT   = 14;

    // Cache all of the configurable colors, for callbacks.
    private final Color         _selectedColor;
    private final Color         _deselectedColor;
    private final Color         _selectedTextColor;
    private final Color         _deselectedTextColor;

    public ColorToggleButton(   final ClientProperties clientProperties,
                                final String selectedText,
                                final String deselectedText,
                                final String selectedToolTipText,
                                final String deselectedToolTipText,
                                final String iconFilename,
                                final Color selectedColor,
                                final Color deselectedColor,
                                final Color selectedTextColor,
                                final Color deselectedTextColor,
                                final boolean enabled ) {
        this(   clientProperties,
                selectedText,
                deselectedText,
                selectedToolTipText,
                deselectedToolTipText,
                iconFilename,
                selectedColor,
                deselectedColor,
                deselectedColor,
                deselectedTextColor,
                FONT_SIZE_DEFAULT,
                WIDTH_FACTOR_DEFAULT,
                HEIGHT_FACTOR_DEFAULT,
                enabled );
    }

    // TODO: Review the apparently mothballed deselected tool tip text.
    public ColorToggleButton(   final ClientProperties clientProperties,
                                final String selectedText,
                                final String deselectedText,
                                final String selectedToolTipText,
                                final String deselectedToolTipText,
                                final String iconFilename,
                                final Color selectedColor,
                                final Color deselectedColor,
                                final Color selectedTextColor,
                                final Color deselectedTextColor,
                                final int fontSize,
                                final int widthFactor,
                                final int heightFactor,
                                final boolean selected ) {
        // Always call the superclass constructor first!
        super(  clientProperties,
                selectedText,
                deselectedText,
                selectedToolTipText,
                //deselectedToolTipText,
                iconFilename,
                widthFactor,
                heightFactor,
                true,
                selected );

        _selectedColor = selectedColor;
        _deselectedColor = deselectedColor;
        _selectedTextColor = selectedTextColor;
        _deselectedTextColor = deselectedTextColor;

        try {
            colorToggleButtonInit( fontSize );
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    private void colorToggleButtonInit( final int fontSize ) {
        // Set the painting modes to be used for all states.
        setBorderPainted( true );
        setFocusPainted( true );
        setRolloverEnabled( true );
        setContentAreaFilled( true );
        setOpaque( false );

        // Set a raised, beveled border, to make toggle state more obvious.
        setBorder( BorderFactory.createRaisedBevelBorder() );

        // Use a plain SanSerif font, for a consistent look and feel.
        // setFont( getFont().deriveFont( Font.PLAIN, fontSize ) );
        setFont( new Font( "SansSerif", Font.PLAIN, fontSize ) ); //$NON-NLS-1$
    }

    @Override
    public Color getBackground() {
        return isSelected() ? _selectedColor : _deselectedColor;
    }

    @Override
    public Color getForeground() {
        return isSelected() ? _selectedTextColor : _deselectedTextColor;
    }
}
