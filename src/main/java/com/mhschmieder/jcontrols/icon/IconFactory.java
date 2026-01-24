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

import javax.swing.ImageIcon;
import java.net.URL;

/**
 * {@code IconFactory} is a factory class for making Swing based icons.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public final class IconFactory {

    /**
     * The default constructor is disabled, as this is a static factory class.
     */
    private IconFactory() {}

    /**
     * Returns an {@link ImageIcon} that contains the image specified by the
     * resource name.
     * <p>
     * This method makes a URL out of a provided image resource name, and uses
     * it to load the image into an {@link ImageIcon} container that can be
     * added to a Swing {@code JLabel} for display in the GUI. In most cases,
     * the resource will be a JAR-relative path within the client JAR, as
     * long-term availability of a web-hosted image might shorten the usefulness
     * of application code should the image resource move or get deleted.
     *
     * @param imageResourceName
     *            The name of the resource that contains the image
     * @return An {@link ImageIcon} that contains the image specified by the
     *         resource name
     *
     * @since 1.0
     */
    public static ImageIcon makeImageIcon( final String imageResourceName ) {
        // If no valid non-empty resource name provided, return a null icon.
        if ( ( imageResourceName == null ) || imageResourceName.isEmpty() ) {
            return null;
        }
        
        // Demand-load the image icon resource to be immediately available.
        try {
            final URL imageUrl = IconFactory.class.getResource( imageResourceName );
            return ( imageUrl != null ) ? new ImageIcon( imageUrl ) : null;
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return null;
        }
    }
}
