/*
 * MIT License
 *
 * Copyright (c) 2025, 2026 Mark Schmieder. All rights reserved.
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
package com.mhschmieder.jcontrols.component;

import javax.swing.JComponent;
import java.awt.Dimension;

public final class ComponentUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    private ComponentUtilities() {}

    /*
     * setMaxWidthToPreferred
     *
     * Usually we want the initial max width to match the initial preferred size
     * so that the component doesn't initially show up clipped or minimized.
     */
    public static void setMaxWidthToPreferred( final JComponent component ) {
        final Dimension size = component.getPreferredSize();
        component.setMaximumSize( size );
    }

    /*
     * setMaxWidthToInfinite
     *
     * Sometimes we want the max width to be much larger than the Component
     * typically has set.
     */
    public static void setMaxWidthToInfinite( final JComponent component ) {
        final Dimension size = component.getMaximumSize();
        size.width = 32000; // this is the biggest window I expect
        component.setMaximumSize( size );
    }
}
