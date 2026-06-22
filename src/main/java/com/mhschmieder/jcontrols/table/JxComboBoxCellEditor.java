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

import javax.swing.JComboBox;
import javax.swing.JTable;
import java.awt.Component;
import java.util.List;

/**
 * {@code ComboBoxCellEditor} is a specialization of {@link JxCellEditor} to
 * customize how Combo Boxes are handled in the context of table cells.
 * <p>
 * The Combo Box is user configurable as dynamic, which means that anything the
 * user types in gets added to the initially empty drop-list.
 * <p>
 * In the future, we might add a method that accepts a new drop-list.
 * <p>
 * This is ancient code like the rest of the table subproject. We might be able
 * to re-implement as a generic Combo Box Cell Editor with a dynamic usage flag
 * and then utilize this Cell Editor in all tables, noting that not all tables
 * are column-major.
 * <p>
 * Another enhancement might be to add an initial list with just one blank
 * string.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class JxComboBoxCellEditor extends JxCellEditor {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = -8790087474542183677L;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a Cell Editor that customizes how Combo Boxes are handled.
     * <p>
     * This is the preferred constructor in most cases, for text-based lists.
     *
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param isEnabled
     *            {@code true} if this cell should be enabled by default
     * @param isVisible
     *            {@code true} if this cell should be visible by default
     * @param editable
     *            {@code true} if the Combo Box should be editable
     * @param maximumRowCount
     *            The maximum number of rows allowed in the Combo Box
     *
     * @version 1.0
     */
    public JxComboBoxCellEditor(final boolean isRowHeader,
                                final boolean isEnabled,
                                final boolean isVisible,
                                final boolean editable,
                                final int maximumRowCount ) {
        this( new JComboBox<>(), isRowHeader, isEnabled, isVisible, editable, maximumRowCount );
    }

    /**
     * Constructs a Cell Editor that customizes how Combo Boxes are handled.
     * <p>
     * This special constructor is provided for cases where a subclass of
     * {@link JComboBox} may be needed; mostly because those classes have unique
     * constructors.
     *
     * @param comboBox
     *            The Combo Box (or derived class) to use as the cell editor
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param isEnabled
     *            {@code true} if this cell should be enabled by default
     * @param isVisible
     *            {@code true} if this cell should be visible by default
     * @param editable
     *            {@code true} if the Combo Box should be editable
     * @param maximumRowCount
     *            The maximum number of rows allowed in the Combo Box
     *
     * @version 1.0
     */
    public JxComboBoxCellEditor(final JComboBox< String > comboBox,
                                final boolean isRowHeader,
                                final boolean isEnabled,
                                final boolean isVisible,
                                final boolean editable,
                                final int maximumRowCount ) {
        // Always call the superclass constructor first!
        super( comboBox, isRowHeader, isEnabled, isVisible );

        // Make sure the list is manageable on small screens!
        comboBox.setMaximumRowCount( maximumRowCount );

        // Conditionally allow the user to type in new items that get
        // dynamically added to the drop-list.
        comboBox.setEditable( editable );

        // Allow combo boxes to participate in focus (i.e. tab) traversal.
        editorComponent.setFocusable( true );
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Clears the current drop-list in the cell editor's Combo Box.
     */
    protected void clearList() {
        ( ( JComboBox< ? > ) editorComponent ).removeAllItems();
    }

    /**
     * Set the drop-list of choices using a static array of strings.
     *
     * @param list
     *            The new drop-list to use in the cell's Combo Box
     */
    @SuppressWarnings("unchecked")
    public void setList( final String[] list ) {
        if ( list != null ) {
            // Save the selection to reinstate after replacing the drop-list.
            final String selection = getSelectedItem();

            // Clear the list, to make sure no garbage gets left behind. If
            // editable, user can still type a value.
            clearList();

            // Re-populate the drop-list with all currently used items.
            for ( final String element : list ) {
                ( ( JComboBox< String > ) editorComponent ).addItem( element );
            }

            // Reinstate the previous selection if possible (the default action
            // is to set to the first item in the new list).
            setSelectedItem( selection );
        }
    }

    /**
     * Set the drop-list of choices using a linked list of strings.
     *
     * @param list
     *            The new drop-list to use in the cell's Combo Box
     */
    @SuppressWarnings("unchecked")
    public void setList( final List< String > list ) {
        // Save the selection to reinstate after replacing the drop-list.
        final String selection = getSelectedItem();

        // Clear the list, to make sure no garbage gets left behind. If
        // editable, the user can still type a value.
        clearList();

        // Re-populate the drop-list with all currently used items.
        for ( final String item : list ) {
            ( ( JComboBox< String > ) editorComponent ).addItem( item );
        }

        // Reinstate the previous selection if possible (the default action is
        // to set to the first item in the new list).
        setSelectedItem( selection );
    }

    /**
     * Returns the list index of the currently selected Combo Box item.
     *
     * @return The list index of the currently selected Combo Box item
     */
    public int getSelectedIndex() {
        return ( ( JComboBox< ? > ) editorComponent ).getSelectedIndex();
    }

    /**
     * Returns the item at the currently selected index in the Combo Box
     * drop-list.
     *
     * @return The item at the currently selected index in the Combo Box
     *         drop-list
     */
    protected String getSelectedItem() {
        return ( String ) ( ( JComboBox< ? > ) editorComponent ).getSelectedItem();
    }

    /**
     * Sets the selected item in the Combo Box's drop-list (if present).
     *
     * @param selectedItem
     *            The item to select in the Combo Box drop-list (if present)
     */
    protected void setSelectedItem( final Object selectedItem ) {
        if ( selectedItem != null ) {
            ( ( JComboBox< ? > ) editorComponent ).setSelectedItem( selectedItem );
        }
    }

    ////////////////// DefaultCellEditor method overrides ////////////////////

    /**
     * Returns the current Combo Box selection if enabled; otherwise
     * {@code null}.
     * <p>
     * This method checks to see whether the Combo Box is enabled, and if so, it
     * returns its current selected item. Otherwise it returns {@code null} so
     * that renderers and other downstream processing are cued to blank the cell
     * rather than present a grayed out value that has no meaning and could
     * confuse users; it is generally better to blank out the cell.
     *
     * @return The current Combo Box selection if enabled; otherwise
     *         {@code null}
     *
     * @version 1.0
     */
    @Override
    public Object getCellEditorValue() {
        return editorComponent.isEnabled()
            ? ( ( JComboBox< ? > ) editorComponent ).getSelectedItem()
            : null;
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
            // Disable the Combo Box but make it visible.
            editorComponent.setEnabled( false );
            editorComponent.setVisible( true );
        }
        else {
            // Set the model component to match the edited state.
            if ( value instanceof String ) {
                delegate.setValue( value );

                // Conditionally enable the Combo Box and make it visible.
                //
                // In cases where the Combo Box is enabled without making it
                // visible, the custom renderer takes over but the cell can
                // still receive edits.
                editorComponent.setEnabled( defaultEnabled );
                editorComponent.setVisible( defaultVisible );
            }
            else {
                // Disable the Combo Box but conditionally make it visible.
                editorComponent.setEnabled( false );
                editorComponent.setVisible( defaultVisible );
            }
        }

        final Component component = super.getTableCellEditorComponent( table,
                                                                       value,
                                                                       isSelected,
                                                                       row,
                                                                       column );

        return component;
    }

}
