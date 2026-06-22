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
package com.mhschmieder.jcontrols.table;

import javax.swing.DefaultCellEditor;
import javax.swing.InputVerifier;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 * {@code XCellEditor} is an abstract base class that extends
 * {@link DefaultCellEditor} for the behavior and look to be used for regular
 * cells vs. row headers (when present).
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public abstract class JxCellEditor extends DefaultCellEditor {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = 7560893014592073280L;

    /**
     * Flag for whether this cell (if {@code true}) is a row header or not.
     */
    protected final boolean   cellIsRowHeader;

    /**
     * Flag for whether this cell (if {@code true}) should be enabled by
     * default. This might change contextual, so is not marked as final.
     */
    protected boolean         defaultEnabled;

    /**
     * Flag for whether this cell (if {@code true}) should be visible by
     * default. This might change contextual, so is not marked as final.
     */
    protected boolean         defaultVisible;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a Cell Editor that customizes how Text Fields are handled.
     *
     * @param textField
     *            The Text Field (or derived class) to use as the cell editor
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param isEnabled
     *            {@code true} if this cell should be enabled by default
     * @param isVisible
     *            {@code true} if this cell should be visible by default
     *
     * @version 1.0
     */
    public JxCellEditor(final JTextField textField,
                        final boolean isRowHeader,
                        final boolean isEnabled,
                        final boolean isVisible ) {
        // Always call the superclass constructor first!
        super( textField );

        cellIsRowHeader = isRowHeader;
        defaultEnabled = isEnabled;
        defaultVisible = isVisible;
    }

    /**
     * Constructs a Cell Editor that customizes how Check Boxes are handled.
     *
     * @param checkBox
     *            The Check Box (or derived class) to use as the cell editor
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param isEnabled
     *            {@code true} if this cell should be enabled by default
     * @param isVisible
     *            {@code true} if this cell should be visible by default
     *
     * @version 1.0
     */
    public JxCellEditor(final JCheckBox checkBox,
                        final boolean isRowHeader,
                        final boolean isEnabled,
                        final boolean isVisible ) {
        // Always call the superclass constructor first!
        super( checkBox );

        cellIsRowHeader = isRowHeader;
        defaultEnabled = isEnabled;
        defaultVisible = isVisible;
    }

    /**
     * Constructs a Cell Editor that customizes how Combo Boxes are handled.
     *
     * @param comboBox
     *            The Combo Box (or derived class) to use as the cell editor
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param isEnabled
     *            {@code true} if this cell should be enabled by default
     * @param isVisible
     *            {@code true} if this cell should be visible by default
     *
     * @version 1.0
     */
    public JxCellEditor(final JComboBox< String > comboBox,
                        final boolean isRowHeader,
                        final boolean isEnabled,
                        final boolean isVisible ) {
        // Always call the superclass constructor first!
        super( comboBox );

        cellIsRowHeader = isRowHeader;
        defaultEnabled = isEnabled;
        defaultVisible = isVisible;
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Returns {@code true} if this cell is enabled by default;
     * {@code false} otherwise.
     *
     * @return {@code true} if this cell is enabled by default; {@code false}
     *         otherwise
     *
     * @version 1.0
     */
    public final boolean isDefaultEnabled() {
        return defaultEnabled;
    }

    /**
     * Sets the flag for whether this cell (if {@code true}) should be enabled
     * by default.
     *
     * @param isEnabled
     *            {@code true} if this cell should be enabled by default;
     *            {@code false} otherwise
     *
     * @version 1.0
     */
    public final void setDefaultEnabled( final boolean isEnabled ) {
        defaultEnabled = isEnabled;

        // Immediately apply this new setting to the editor component.
        editorComponent.setEnabled( defaultEnabled );
    }

    /**
     * Returns {@code true} if this cell is visible by default;
     * {@code false} otherwise.
     *
     * @return {@code true} if this cell is visible by default; {@code false}
     *         otherwise
     *
     * @version 1.0
     */
    public final boolean isDefaultVisible() {
        return defaultVisible;
    }

    /**
     * Sets the flag for whether this cell (if {@code true}) should be visible
     * by default.
     *
     * @param isVisible
     *            {@code true} if this cell should be visible by default;
     *            {@code false} otherwise
     *
     * @version 1.0
     */
    public void setDefaultVisible( final boolean isVisible ) {
        defaultVisible = isVisible;
    }

    /**
     * Sets the status of whether the editor component should be focusable.
     * {@code true} if focusable; {@code false} otherwise
     *
     * @param focusable
     *            {@code true} if the editor component should be focusable;
     *            {@code false} otherwise
     *
     * @version 1.0
     */
    public final void setFocusable( final boolean focusable ) {
        // Override whether the editor component is focusable or not. This is
        // primarily provided so that editor components that are the first
        // column in a row can be excluded from tab traversal lest they
        // auto-select and cause an action or toggle as though they had been
        // clicked.
        //
        // Unfortunately, this appears to have no effect whatsoever.
        editorComponent.setFocusable( focusable );
        editorComponent.setFocusTraversalKeysEnabled( false );
    }

    /////////////////////////// Validator methods ////////////////////////////

    /**
     * Sets the {@link InputVerifier} to apply to the Text Field's value.
     *
     * @param inputVerifier
     *            The {@link InputVerifier} to apply to the Text Field's value
     *
     * @version 1.0
     */
    public void setInputVerifier( final InputVerifier inputVerifier ) {
        editorComponent.setInputVerifier( inputVerifier );
    }

}
