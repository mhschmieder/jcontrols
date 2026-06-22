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
package com.mhschmieder.jcontrols.icon;

import org.apache.commons.math3.util.FastMath;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;

/**
 * {@code TextIcon} is a specialization of {@link ButtonIcon} that renders text
 * directly to an icon.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class TextIcon extends ButtonIcon {

    /**
     * The text to display as the main graphic on the icon.
     */
    private final String text;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a {@code TextIcon} as a perfect square using the size (in
     * pixels) that is established by the {@link IconContext}, and the supplied
     * text to serve as the main graphics.
     *
     * @param iconContext
     *            The icon context for determining the icon size in pixels
     * @param iconText
     *            The text to display as the main graphic on the icon
     *
     * @version 1.0
     */
    public TextIcon( final IconContext iconContext, final String iconText ) {
        // Always call the superclass constructor first!
        super( iconContext );

        text = iconText;
    }

    /**
     * Constructs a {@code ButtonIcon} as a perfect square using the supplied
     * size and insets (in pixels), and the supplied text to serve as the main
     * graphics.
     *
     * @param iconSize
     *            The size to use for both the width and height of the icon
     * @param iconInsetsValue
     *            The preferred icon insets, in pixels
     * @param iconText
     *            The text to display as the main graphic on the icon
     *
     * @version 1.0
     */
    public TextIcon( final int iconSize, final int iconInsetsValue, final String iconText ) {
        // Always call the superclass constructor first!
        super( iconSize, iconSize, iconInsetsValue );

        text = iconText;
    }

    /**
     * Constructs a {@code ButtonIcon} with the preferred width and height, and
     * the supplied text to serve as the main graphics.
     *
     * @param preferredIconWidth
     *            The preferred width of the icon, in pixels
     * @param preferredIconHeight
     *            The preferred height of the icon, in pixels
     * @param iconInsetsValue
     *            The preferred icon insets, in pixels
     * @param iconText
     *            The text to display as the main graphic on the icon
     *
     * @version 1.0
     */
    public TextIcon( final int preferredIconWidth,
                     final int preferredIconHeight,
                     final int iconInsetsValue,
                     final String iconText ) {
        // Always call the superclass constructor first!
        super( preferredIconWidth, preferredIconHeight, iconInsetsValue );

        text = iconText;
    }

    ////////////////////// ButtonIcon method overrides ////////////////////////

    /**
     * Draw the icon at the specified location. Icon implementations may use the
     * {@code component} argument to get properties useful for painting, e.g.
     * the foreground or background color.
     *
     * @param component
     *            The component to use for getting properties related to
     *            painting
     * @param graphicsContext
     *            The {@link Graphics} to use for getting a canvas to draw or
     *            paint the icon on
     * @param x
     *            The x coordinate for the icon
     * @param y
     *            The y coordinate for the icon
     *
     * @version 1.0
     */
    @SuppressWarnings("nls")
    @Override
    public void paintIcon( final Component component,
                           final Graphics graphicsContext,
                           final int x,
                           final int y ) {
        // Cache the current color, to restore after the custom graphics.
        final Color currentColor = graphicsContext.getColor();

        // Cache the current font, to restore after the custom graphics.
        final Font currentFont = graphicsContext.getFont();

        // Use dark gray vs. black or white, to better match existing icons.
        //
        // Note that this choice is ancient, from when Metal Look and Feel was
        // still being used as the default on all OS platforms. Needs review?
        graphicsContext.setColor( Color.DARK_GRAY );

        // As a rough guess, use a bold font that is 3/4 the icon height.
        final int fontSize = ( int ) FastMath.floor( 0.75f * getIconHeight() );
        final Font iconFont = new Font( "SansSerif", Font.BOLD, fontSize );
        graphicsContext.setFont( iconFont );

        // Draw the icon's text, which may be just a single character.
        final Insets insets = getIconInsets();
        final int baselineX = x + insets.left;
        final int baselineY = ( y + getIconHeight() ) - insets.bottom - 2;
        graphicsContext.drawString( text, baselineX, baselineY );

        // Draw an insets outline that roughly encloses the icon boundaries.
        graphicsContext.drawRect( x, y, getIconWidth() - 2, getIconHeight() - 2 );

        // Invoke any generic decorations (do beforehand?).
        super.paintIcon( component, graphicsContext, x, y );

        // Restore the font that was current before the icon graphics.
        graphicsContext.setFont( currentFont );

        // Restore the color that was current before the icon graphics.
        graphicsContext.setColor( currentColor );
    }

}
