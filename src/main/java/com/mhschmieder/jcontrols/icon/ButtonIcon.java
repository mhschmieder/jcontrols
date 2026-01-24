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
package com.mhschmieder.jcontrols.icon;

import javax.swing.Icon;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

/**
 * {@code ButtonIcon} is a specific implementation of the {@link Icon} interface
 * that takes into account the constraints of menu bar and tool bar use of icons
 * in button-derived classes.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class ButtonIcon implements Icon {

    /**
     * The width of the icon, in pixels.
     */
    private final int    iconWidth;

    /**
     * The height of the icon, in pixels.
     */
    private final int    iconHeight;

    /**
     * The insets to use on all four sides of the icon, in its host component.
     */
    private final Insets iconInsets;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a {@code ButtonIcon} as a perfect square using the size (in
     * pixels) that is established by the {@link IconContext}.
     *
     * @param iconContext
     *            The icon context for determining the icon size in pixels
     *
     * @version 1.0
     */
    protected ButtonIcon( final IconContext iconContext ) {
        this( IconUtilities.getIconSize( iconContext ),
              IconUtilities.getIconInsetsValue( iconContext ) );
    }

    /**
     * Constructs a {@code ButtonIcon} as a perfect square using the supplied
     * size and insets (in pixels).
     *
     * @param iconSize
     *            The size to use for both the width and height of the icon
     * @param iconInsetsValue
     *            The preferred icon insets, in pixels
     *
     * @version 1.0
     */
    protected ButtonIcon( final int iconSize, final int iconInsetsValue ) {
        this( iconSize, iconSize, iconInsetsValue );
    }

    /**
     * Constructs a {@code ButtonIcon} with the preferred width and height.
     *
     * @param preferredIconWidth
     *            The preferred width of the icon, in pixels
     * @param preferredIconHeight
     *            The preferred height of the icon, in pixels
     * @param iconInsetsValue
     *            The preferred icon insets, in pixels
     *
     * @version 1.0
     */
    protected ButtonIcon( final int preferredIconWidth,
                          final int preferredIconHeight,
                          final int iconInsetsValue ) {
        iconWidth = preferredIconWidth;
        iconHeight = preferredIconHeight;
        iconInsets =
                   new Insets( iconInsetsValue, iconInsetsValue, iconInsetsValue, iconInsetsValue );
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Returns the current icon insets (a set of four values; one for each
     * side).
     *
     * @return The current icon insets (a set of four values; one for each side)
     *
     * @version 1.0
     */
    protected Insets getIconInsets() {
        return iconInsets;
    }

    //////////////////////// Icon method overrides ///////////////////////////

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
    @Override
    public void paintIcon( final Component component,
                           final Graphics graphicsContext,
                           final int x,
                           final int y ) {
        // We may need to implement generic decorations that are shared by all
        // icons (if relevant), but the derived classes are primarily the ones
        // responsible for overriding and implementing this method.
    }

    /**
     * Returns the width of the icon, in pixels.
     *
     * @return The width of the icon, in pixels
     *
     * @version 1.0
     */
    @Override
    public int getIconWidth() {
        return iconWidth;
    }

    /**
     * Returns the height of the icon, in pixels.
     *
     * @return The height of the icon, in pixels
     *
     * @version 1.0
     */
    @Override
    public int getIconHeight() {
        return iconHeight;
    }

}
