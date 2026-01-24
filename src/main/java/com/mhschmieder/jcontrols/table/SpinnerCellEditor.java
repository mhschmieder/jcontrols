/**
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

import javax.swing.AbstractCellEditor;
import javax.swing.AbstractSpinnerModel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

/**
 * {@code SpinnerCellEditor} extends the {@link AbstractCellEditor} base class
 * to allow a {@link JSpinner} to be used as a table cell editor. This control
 * was added to Java Swing quite late, and was not retrofitted into the
 * {@code DefaultCellEditor} class, so it is up to us to do at least a partial
 * implementation of what that might have looked like if it had become part of
 * the Swing toolkit that is distributed with the JDK and the JRE.
 * <p>
 * There is a fair amount of overlap in what this class does, and what our
 * abstract {@code XCellEditor} base class does, so we might find a way later on
 * to consolidate the class hierarchy a bit and thus reduce redundant copy/paste
 * code that is always at risk of getting out of sync during enhancement work.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class SpinnerCellEditor extends AbstractCellEditor implements TableCellEditor {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = 6113762886619942075L;

    /**
     * The {@link JSpinner} to use as the editor component for the table cell.
     */
    protected final JSpinner  editorComponent;

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
     * Constructs a Cell Editor that customizes how Spinners are handled.
     *
     * @param spinner
     *            The {@link JSpinner} to use as the editor component for the
     *            table cell.
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param isEnabled
     *            {@code true} if this cell should be enabled by default
     * @param isVisible
     *            {@code true} if this cell should be visible by default
     *
     * @version 1.0
     */
    public SpinnerCellEditor( final JSpinner spinner,
                              final boolean isRowHeader,
                              final boolean isEnabled,
                              final boolean isVisible ) {
        // At the moment, there is no super-constructor to call.
        editorComponent = spinner;

        cellIsRowHeader = isRowHeader;
        defaultEnabled = isEnabled;
        defaultVisible = isVisible;

        // Allow spinners to participate in focus (i.e. tab) traversal.
        editorComponent.setFocusable( true );
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Sets the Spinner Model for the allowed values.
     *
     * @param spinnerModel
     *            The Spinner Model for the allowed values
     *
     * @version 1.0
     */
    public void setSpinnerModel( final AbstractSpinnerModel spinnerModel ) {
        // Cache the currently selected value to persist.
        //
        // Commented out as it causes problems if the old value is invalid.
        // final Object value = editorComponent.getValue();

        // Set the supplied model (could be a list or a range).
        editorComponent.setModel( spinnerModel );

        // Attempt to persist the previous selected value.
        //
        // Commented out as it causes problems if the old value is invalid.
        // if ( ( value != null ) ) {
        // editorComponent.setValue( value );
        // }
    }

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

    ////////////////// AbstractCellEditor method overrides ///////////////////

    /**
     * Returns {@code true} if the cell is editable; {@code false} otherwise.
     * <p>
     * As there is no delegate in this implementation, this implementation is a
     * minor modification of what is found in {@code DefaultCellEditor}, but it
     * is modified to check the mouse click count against a limit of 2.
     * <p>
     * In simplified terms, direct editing (that is, typing to find something in
     * the list or to add it to the list) is only suppoprted in the spinner when
     * the mouse is double-clicked.
     *
     * @return {@code true} if the cell is editable; {@code false} otherwise
     *
     * @version 1.0
     */
    @Override
    public boolean isCellEditable( final EventObject evt ) {
        if ( evt instanceof MouseEvent ) {
            return ( ( MouseEvent ) evt ).getClickCount() >= 2;
        }
        return true;
    }

    /////////////////// TableCellEditor method overrides /////////////////////

    /**
     * Returns the current cell editor value stored in the {@link JSpinner}.
     *
     * @return The current cell editor value stored in the {@link JSpinner}
     *
     * @version 1.0
     */
    @Override
    public Object getCellEditorValue() {
        return editorComponent.getValue();
    }

    /**
     * Sets an initial {@code value} for the editor. This will cause the editor
     * to {@code stopEditing} and lose any partially edited value if the editor
     * is editing when this method is called.
     * <p>
     * Return {@code Component} hierarchy. Once installed in the client's
     * hierarchy this component will then be able to draw and receive user
     * input.
     * <p>
     * This method is called when a cell value is edited by the user.
     *
     * @param table
     *            The {@code JTable} that is asking the editor to edit; can be
     *            {@code null}
     * @param value
     *            The value of the cell to be edited; it is up to the specific
     *            editor to interpret and draw the value. For example, if value
     *            is the string "true", it could be rendered as a string or it
     *            could be rendered as a check box that is checked. {@code null}
     *            is a valid value
     * @param isSelected
     *            {@code true }if the cell is to be rendered with highlighting
     * @param row
     *            The row of the cell being edited
     * @param column
     *            The column of the cell being edited
     * @return The component for editing
     *
     * @version 1.0
     */
    @Override
    public Component getTableCellEditorComponent( final JTable table,
                                                  final Object value,
                                                  final boolean isSelected,
                                                  final int row,
                                                  final int column ) {
        final boolean applyRowHeaderStyle = cellIsRowHeader
                && ( column == TableConstants.COLUMN_ROW_HEADER );

        if ( applyRowHeaderStyle ) {
            // Disable the Spinner but make it visible.
            editorComponent.setEnabled( false );
            editorComponent.setVisible( true );
        }
        else {
            // Set the model component to match the edited state.
            editorComponent.setValue( value );

            // Conditionally enable the Spinner and make it visible.
            //
            // In cases where the Spinner is enabled without making it visible,
            // the custom renderer takes over but the cell can still receive
            // edits.
            editorComponent.setEnabled( defaultEnabled );
            editorComponent.setVisible( defaultVisible );
        }

        return editorComponent;
    }

}