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

import javax.swing.JTable;
import java.awt.Component;

/**
 * {@code BlankingCellEditor} is a further specialization of
 * {@link TextFieldCellEditor} to handle cells that need to be blank due to he
 * irrelevance of data in that cell position.
 * <p>
 * An example would be a table that has alternating rows of different data
 * types, where a customized rendering of cells not in use for each row is more
 * intuitive to the user than allowing a blank white cell that might be seen as
 * a run-time error or missing data.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class BlankingCellEditor extends TextFieldCellEditor {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = -2613702930890626349L;

    /**
     * The text to use to indicate that a table cell is legitimately blank.
     */
    private final String      blankingText;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a Cell Editor that customizes blank cells to be indicated in
     * some special way, such as with a special background color and a symbol
     * for data not applicable (often this is the minus sign).
     *
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     *
     * @version 1.0
     */
    public BlankingCellEditor( final boolean isRowHeader ) {
        this( isRowHeader, TableConstants.DEFAULT_BLANKING_TEXT );
    }

    /**
     * Constructs a Cell Editor that customizes blank cells to be indicated in
     * some special way, such as with a special background color and a symbol
     * for data not applicable (often this is the minus sign).
     *
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param blankingSymbol
     *            The text to use to indicate that a table cell is legitimately
     *            blank
     *
     * @version 1.0
     */
    public BlankingCellEditor( final boolean isRowHeader, final String blankingSymbol ) {
        // Always call the superclass constructor first!
        super( isRowHeader, false, false );

        blankingText = blankingSymbol;

        // Don't allow blanking text fields to participate in focus (i.e. tab)
        // traversal.
        editorComponent.setFocusable( false );
    }

    ////////////////// DefaultCellEditor method overrides ////////////////////

    /**
     * Returns the current cell editor value, which in this case is always the
     * special assigned blanking character.
     * <p>
     * This method is called when editing is completed. It must return the new
     * value to be stored in the cell, which is always a blanking string.
     *
     * @return The special blanking character in all cases, regardless of other
     *         factors
     *
     * @version 1.0
     */
    @Override
    public Object getCellEditorValue() {
        return blankingText;
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

        final Object newValue = applyRowHeaderStyle ? value : blankingText;

        final Component component = super.getTableCellEditorComponent( table,
                                                                       newValue,
                                                                       isSelected,
                                                                       row,
                                                                       column );

        return component;
    }

}
