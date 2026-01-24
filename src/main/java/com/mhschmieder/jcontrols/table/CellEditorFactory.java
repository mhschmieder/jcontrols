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
package com.mhschmieder.jcontrols.table;

import com.mhschmieder.jcontrols.control.NumberEditor;

import javax.swing.DefaultCellEditor;

/**
 * {@code CellEditorFactory} is a factory class for making customized Table
 * Cell Editors, without having to unnecessarily derive subclasses from
 * existing classes in the library when there is no new data or extra
 * functionality to add.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public final class CellEditorFactory {

    /**
     * The default constructor is disabled, as this is a static factory class.
     */
    private CellEditorFactory() {}

    /**
     * Returns a {@link BlankingCellEditor} that is stylized for blanking.
     *
     * @return A {@link BlankingCellEditor} that is stylized for blanking
     */
    public static DefaultCellEditor makeBlankingCellEditor() {
        return new BlankingCellEditor( false );
    }

    /**
     * Returns a {@link TextFieldCellEditor} that is stylized either for
     * read-only labels or for editable labels (if {@code defaultEnabled} is
     * {@code true}).
     *
     * @param defaultEnabled
     *            If {@code true}, make an editable {@link TextFieldCellEditor};
     *            otherwise make a read-only {@link TextFieldCellEditor}
     * @return A {@link TextFieldCellEditor} that is stylized either for
     *         read-only labels or for editable labels
     */
    public static DefaultCellEditor makeLabelCellEditor( final boolean defaultEnabled ) {
        return defaultEnabled ? makeDynamicLabelCellEditor() : makeStaticLabelCellEditor();
    }

    /**
     * Returns a {@link TextFieldCellEditor} that is stylized for read-only
     * labels.
     *
     * @return A {@link TextFieldCellEditor} that is stylized for read-only
     *         labels
     */
    public static DefaultCellEditor makeStaticLabelCellEditor() {
        return new TextFieldCellEditor( false, // row headers not in use
                                        false, // disabled for editing
                                        true ); // visible by default

    }

    /**
     * Returns a {@link TextFieldCellEditor} that is stylized for editable
     * labels.
     *
     * @return A {@link TextFieldCellEditor} that is stylized for editable
     *         labels
     */
    public static DefaultCellEditor makeDynamicLabelCellEditor() {
        return new TextFieldCellEditor( false, // row header not in use
                                        true, // enabled for editing
                                        true ); // visible by default
    }

    /**
     * Returns a {@link TextFieldCellEditor} that uses a {@link NumberEditor}
     * for the cell editing and is visible by default.
     *
     * @param minimumFractionDigitsFormat
     *            The minimum number of fraction digits to be shown
     * @param maximumFractionDigitsFormat
     *            The maximum number of fraction digits to be shown
     * @param minimumFractionDigitsParse
     *            The minimum number of fraction digits to parse
     * @param maximumFractionDigitsParse
     *            The maximum number of fraction digits to parse
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param isEnabled
     *            {@code true} if this cell should be enabled by default
     * @return A {@link TextFieldCellEditor} that uses a
     *         {@link NumberEditor} for the cell editing
     */
    public static TextFieldCellEditor makeNumberCellEditor( final int minimumFractionDigitsFormat,
                                                            final int maximumFractionDigitsFormat,
                                                            final int minimumFractionDigitsParse,
                                                            final int maximumFractionDigitsParse,
                                                            final boolean isRowHeader,
                                                            final boolean isEnabled ) {
        final NumberEditor textField = new NumberEditor( minimumFractionDigitsFormat,
                                                         maximumFractionDigitsFormat,
                                                         minimumFractionDigitsParse,
                                                         maximumFractionDigitsParse );

        // Return a Text Field Cell Editor instance that uses a Number Editor
        // for the cell editing and is visible by default.
        return new TextFieldCellEditor( textField, isRowHeader, isEnabled, true );
    }

    /**
     * Returns a {@link CheckBoxCellEditor} that behaves like a regular Action
     * Button.
     *
     * @return A {@link CheckBoxCellEditor} that behaves like a regular Action
     *         Button
     */
    public static CheckBoxCellEditor makeActionButtonCellEditor() {
        return new CheckBoxCellEditor( false, // row header not in use
                                       true, // enabled for editing
                                       true ); // visible by default

    }

    /**
     * Returns a {@link CheckBoxCellEditor} that behaves like a Toggle Button.
     *
     * @return A {@link CheckBoxCellEditor} that behaves like a Toggle Button
     */
    public static CheckBoxCellEditor makeToggleButtonCellEditor() {
        return new CheckBoxCellEditor( false, // row header not in use
                                       true, // enabled for editing
                                       true ); // visible by default
    }

    /**
     * Returns a {@link ComboBoxCellEditor} that is stylized either for
     * read-only lists or for editable lists (if {@code defaultEnabled} is
     * {@code true}).
     *
     * @param defaultEnabled
     *            If {@code true}, make an editable {@link ComboBoxCellEditor};
     *            otherwise make a read-only {@link ComboBoxCellEditor}
     * @return A {@link ComboBoxCellEditor} that is stylized either for
     *         read-only lists or for editable lists
     */
    public static DefaultCellEditor makeListCellEditor( final boolean defaultEnabled ) {
        return defaultEnabled ? makeDynamicListCellEditor() : makeStaticListCellEditor();
    }

    /**
     * Returns a {@link ComboBoxCellEditor} that is stylized either read-only
     * lists.
     *
     * @return A {@link ComboBoxCellEditor} that is stylized for read-only lists
     */
    public static ComboBoxCellEditor makeStaticListCellEditor() {
        return new ComboBoxCellEditor( false, // row header not in use
                                       true, // enabled for editing
                                       true, // visible by default
                                       false, // not editable by default
                                       20 );
    }

    /**
     * Returns a {@link ComboBoxCellEditor} that is stylized for editable lists.
     *
     * @return A {@link ComboBoxCellEditor} that is stylized for editable lists
     */
    public static ComboBoxCellEditor makeDynamicListCellEditor() {
        return new ComboBoxCellEditor( false, // row header not in use
                                       true, // enabled for editing
                                       true, // visible by default
                                       true, // editable by default
                                       10 );
    }

}
