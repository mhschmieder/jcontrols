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
package com.mhschmieder.jcontrols.control;

import com.mhschmieder.jcommons.lang.Delimiter;
import com.mhschmieder.jcontrols.component.ComponentUtilities;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

// TODO: Refactor JxComboBox to be template-based, so that we can instantiate
//  it and any derived controls using <String>, as otherwise we must cast.
public final class ControlUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    private ControlUtilities() {}

    public static JComboBox< String > initDelimiterSelector(
            final boolean setMaxWidth,
            final JPanel delimitersRow,
            final String delimiterType,
            final String delimiter ) {
        // Make the selector for the Delimiter Type.
        final JComboBox< String > delimiterSelector
                = makeDelimiterSelector( setMaxWidth );

        // Set our selector to match the provided default index.
        delimiterSelector.setSelectedItem( delimiter );

        // Pair the selector with a unique identifier label.
        final String delimiterSublabel = "Delimiter: ";
        final String delimiterTypeAdjusted = !delimiterType.isEmpty()
                ? delimiterType + " "
                : "";
        final String delimiterLabel = delimiterTypeAdjusted + delimiterSublabel;
        delimitersRow.add( new JLabel( delimiterLabel ) );
        delimitersRow.add( delimiterSelector );

        return delimiterSelector;
    }

    public static JComboBox makeDelimiterSelector(
            final boolean setMaxWidth ) {
        final JComboBox< String > delimiterSelector = new JComboBox<>();

        // We want this selector to stay small, so we set its max size to the
        // same as its preferred size.
        if ( setMaxWidth ) {
            ComponentUtilities.setMaxWidthToPreferred( delimiterSelector );
        }

        // Fill the combo box with the possible delimiter characters.
        // NOTE: These are declared in our preferred presentation order.
        for ( final Delimiter delimiter : Delimiter.values() ) {
            delimiterSelector.addItem( delimiter.label() );
        }

        return delimiterSelector;
    }
}
