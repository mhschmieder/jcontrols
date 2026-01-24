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

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * {@code PropertiesTableModel} is a specialization of {@link DefaultTableModel}
 * that allows for class-specific per-column properties handling within a table.
 * <p>
 * This class may be expanded at some point, to pass in a vector of booleans to
 * indicate which columns are editable, and also use this to determine the
 * number of valid data columns. This would satisfy most sub-classing needs.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class PropertiesTableModel extends DefaultTableModel {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = 7714745412164574786L;

    /**
     * Flag for whether to log exceptions (if {@code true}) or not.
     */
    private final boolean     logExceptions;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a specific {@code PropertiesTableModel} that has column names
     * but no initial or default data. These are generally editable tables.
     * <p>
     * As the other base class constructors are public, it is still possible to
     * invoke them instead, when one doesn't have initial data or doesn't have
     * it in the specified format. But it is not advised, as clients of this
     * data model might as a result end of with null or empty data references.
     *
     * @param columnNames
     *            The names of the table columns
     * @param rowCount
     *            The number of rows the table holds
     * @param shouldLogExceptions
     *            {@code true} if exceptions should be logged; {@code false}
     *            otherwise
     * @see #setDataVector
     * @see #setValueAt
     *
     * @version 1.0
     */
    public PropertiesTableModel( final Object[] columnNames,
                                 final int rowCount,
                                 final boolean shouldLogExceptions ) {
        // Always call the superclass constructor first!
        super( columnNames, rowCount );

        logExceptions = shouldLogExceptions;
    }

    ////////////////// DefaultTableModel method overrides ////////////////////

    /**
     * Returns {@code true} regardless of parameter values, because a properties
     * table is mostly for user interaction and input.
     * <p>
     * This method models the lowest common denominator for global behavior, and
     * represents the only table model property that is always valid no matter
     * what else changes in the table. As such, it should not be used by client
     * code to determine when a specific cell is editable; only the JVM should
     * invoke this method.
     * <p>
     * This class should be sub-classed and this method overridden if there is a
     * need to exclude any columns from keyboard focus.
     *
     * @param row
     *            The row whose value is to be queried
     * @param column
     *            The column whose value is to be queried
     * @return {@code true} always
     *
     * @version 1.0
     */
    @Override
    public boolean isCellEditable( final int row, final int column ) {
        // All cells are editable if they are modifiable in any way. This is not
        // the same thing as saying that a combo box can have new items added to
        // the list. Unless a cell containing a combo box returns "true" from
        // this method, it will never be able to display a drop-list for
        // selection. Note that check boxes must be editable.
        return true;
    }

    /**
     * Sets the object value for the cell at {@code column} and {@code row}.
     * {@code value} is the new value. This method will generate a
     * {@code tableChanged} notification.
     * <p>
     * The purpose of overriding this method is to capture and log any run-time
     * exceptions, as a table purposed solely for data display should only throw
     * exceptions due to programming errors in the client code vs. mistakes by
     * the end user, so generally should not punish the full calling context and
     * cause application state ambiguity.
     *
     * @param value
     *            The new value; this can be {@code null}
     * @param row
     *            The row whose value is to be changed
     * @param column
     *            The column whose value is to be changed
     *
     * @version 1.0
     */
    @Override
    public void setValueAt( final Object value, final int row, final int column ) {
        try {
            super.setValueAt( value, row, column );
        }
        catch ( final Exception e ) {
            if ( logExceptions ) {
                e.printStackTrace();
            }
        }
    }

    ////////////////// AbstractTableModel method overrides ///////////////////

    /**
     * Returns <code>Object.class</code> regardless of <code>columnIndex</code>.
     * <p>
     * {@code JTable} uses this method to determine the default renderer/editor
     * for each cell. If we didn't implement this method, all columns would
     * always contain text instead of check boxes, combo boxes, etc.
     *
     * @param columnIndex
     *            The column being queried
     * @return The class of the specified column, or String.class if an
     *         exception is thrown
     *
     * @version 1.0
     */
    @Override
    public Class< ? > getColumnClass( final int columnIndex ) {
        try {
            // This isn't made generic until Java 9; switch the code then.
            // final List< ? > v = dataVector.get( 0 );
            final List< ? > v = ( List< ? > ) dataVector.get( 0 );
            return v.get( columnIndex ).getClass();
        }
        catch ( final Exception e ) {
            if ( logExceptions ) {
                e.printStackTrace();
            }
            return String.class;
        }
    }

}
