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

import javax.swing.table.DefaultTableModel;

/**
 * {@code ColumnHeaderTableModel} is a specialization of
 * {@link DefaultTableModel} for custom header tables, when the header must be
 * modeled as a single-row table with no header of its own, in order to use
 * custom colors (for example). The table cells must all be non-editable.
 * <p>
 * This highly specialized custom data model is to be used when stacking several
 * tables that may switch data types at a certain row but which need to "look"
 * like one table. It was an early attempt to start modeling Excel in Swing.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class ColumnHeaderTableModel extends DefaultTableModel {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = -945161643060780325L;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a {@code ColumnHeaderTableModel} with all column names known,
     * along with a single data row that serves as a table header of sorts as it
     * is comprised of the column names but has to be set separately as this is
     * a one row pseudo-header table that doesn't have a regular Table Header.
     *
     * @param columnNames
     *            The names of the table columns
     * @param headerRow
     *            The header row, which is comprised of the column names
     *
     * @since 1.0
     */
    public ColumnHeaderTableModel( final Object[] columnNames, final Object[] headerRow ) {
        // Always call the superclass constructor first!
        super();

        // Set the column identifiers as we aren't using a regular Table Header.
        setColumnIdentifiers( columnNames );

        // Add the actual table columns as a data row; the only row in the
        // table.
        addRow( headerRow );
    }

    ////////////////// DefaultTableModel method overrides ////////////////////

    /**
     * Returns {@code false} regardless of parameter values, because a
     * pseudo-header table is for display only vs. user interaction and input.
     * <p>
     * This method models the lowest common denominator for global behavior, and
     * represents the only table model property that is always valid no matter
     * what else changes in the table. As such, it should not be used by client
     * code to determine when a specific cell is editable; only the JVM should
     * invoke this method.
     *
     * @param row
     *            The row whose value is to be queried
     * @param column
     *            The column whose value is to be queried
     * @return {@code false} always
     *
     * @version 1.0
     */
    @Override
    public boolean isCellEditable( final int row, final int column ) {
        return false;
    }

    ////////////////// AbstractTableModel method overrides ///////////////////

    /**
     * Returns <code>Object.class</code> regardless of <code>columnIndex</code>.
     * <p>
     * {@code JTable} uses this method to determine the default renderer/editor
     * for each cell. If we didn't implement this method, all columns would
     * always contain text instead of check boxes, combo boxes, etc., but that's
     * a moot point in this case as this is a one-row table of column names
     * only. Still, it is best to implement it vs. depending on the superclass.
     *
     * @param columnIndex
     *            The column being queried
     * @return The class of the specified column, which is always String.class
     *
     * @version 1.0
     */
    @Override
    public Class< ? > getColumnClass( final int columnIndex ) {
        return String.class;
    }

}
