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

/**
 * {@code IconUtilities} is a utility class for Swing based icon methods.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public final class IconUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private IconUtilities() {}

    /**
     * Returns the number of pixels to use as the icon size in both dimensions.
     *
     * @param iconContext
     *            The icon context for determining the icon size in pixels
     * @return The number of pixels to use as the icon size in both dimensions
     *
     * @version 1.0
     */
    public static int getIconSize( final IconContext iconContext ) {
        int iconSize = 0;
        
        switch ( iconContext ) {
        case FRAME_TITLE:
            iconSize = IconConstants.FRAME_TITLE_ICON_SIZE;
            break;
        case MENU:
            iconSize = IconConstants.MENU_ICON_SIZE;
            break;
        case TOOLBAR:
            iconSize = IconConstants.TOOLBAR_ICON_SIZE;
            break;
        case CONTROL_PANEL:
            iconSize = IconConstants.CONTROL_PANEL_ICON_SIZE;
            break;
        default:
            break;
        }
        
        return iconSize;
    }

    /**
     * Returns the number of pixels to use as the offset for the icon within its
     * host.
     *
     * @param iconContext
     *            The icon context for determining the icon insets value
     * @return The number of pixels to use as the offset for the icon within its
     *         host
     *
     * @version 1.0
     */
    public static int getIconInsetsValue( final IconContext iconContext ) {
        int iconInset = 0;
        
        switch ( iconContext ) {
        case FRAME_TITLE:
            iconInset = IconConstants.FRAME_TITLE_ICON_INSET;
            break;
        case MENU:
            iconInset = IconConstants.MENU_ICON_INSET;
            break;
        case TOOLBAR:
            iconInset = IconConstants.TOOLBAR_ICON_INSET;
            break;
        case CONTROL_PANEL:
            iconInset = IconConstants.CONTROL_PANEL_ICON_INSET;
            break;
        default:
            break;
        }
        
        return iconInset;
    }

}
