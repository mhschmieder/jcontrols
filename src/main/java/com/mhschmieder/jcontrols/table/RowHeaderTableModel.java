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

import javax.swing.table.AbstractTableModel;

/**
 * {@code RowHeaderTableModel} is a specialization of {@link AbstractTableModel}
 * for custom tables that use a Row Header (first column) in place of the
 * traditional Table Header (for Column Headers at the top of the Table).
 * <p>
 * This highly specialized custom data model is often used when stacking several
 * tables that may switch data types at a certain row but which need to "look"
 * like one table. It was an early attempt to start modeling Excel in Swing.
 * <p>
 * This is the base class for single-row tables that need a row header in place
 * of the usual column headers that are used for property tables and multi-row
 * data tables and property tables. This can be thought of as a headless table.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class RowHeaderTableModel extends AbstractTableModel {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long  serialVersionUID = 2902218461650797475L;

    /**
     * Flag for whether the Row Header (first column) is editable or not.
     */
    private final boolean      rowHeaderEditable;

    /**
     * The names of the table columns.
     */
    private final String[]     columnNames;

    /**
     * List of class types by Table Column.
     */
    private final Class< ? >[] types;

    /**
     * The data for the Table Model; expected to be just one row.
     */
    protected final Object[][] data;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a {@code RowHeaderTableModel} that is specialized to use the
     * first column as a Row Header, which is a feature that is missing from
     * Swing's table implementation.
     *
     * @param rowName
     *            The label (name) to use for the Row Header (first) column
     * @param rowHeaderIsEditable
     *            {@code true} if the Row Header (first column) is editable
     * @param numberOfDataColumns
     *            The number of columns for this model
     * @param dataType
     *            The data type for all of the columns in this model
     *
     * @version 1.0
     */
    public RowHeaderTableModel( final String rowName,
                                final boolean rowHeaderIsEditable,
                                final int numberOfDataColumns,
                                final Class< ? > dataType ) {
        this( rowName, rowHeaderIsEditable, numberOfDataColumns, dataType, null );
    }

    /**
     * Constructs a {@code RowHeaderTableModel} that is specialized to use the
     * first column as a Row Header, which is a feature that is missing from
     * Swing's table implementation.
     *
     * @param rowName
     *            The label (name) to use for the Row Header (first) column
     * @param rowHeaderIsEditable
     *            {@code true} if the Row Header (first column) is editable
     * @param numberOfDataColumns
     *            The number of columns for this model
     * @param dataType
     *            The data type for all of the columns in this model
     * @param dataValues
     *            The initial data, or {@code null} if all should be defaulted
     *
     * @version 1.0
     */
    @SuppressWarnings("nls")
    public RowHeaderTableModel( final String rowName,
                                final boolean rowHeaderIsEditable,
                                final int numberOfDataColumns,
                                final Class< ? > dataType,
                                final Object[] dataValues ) {
        this( rowName, rowHeaderIsEditable, numberOfDataColumns, dataType, dataValues, "" );
    }

    /**
     * Constructs a {@code RowHeaderTableModel} that is specialized to use the
     * first column as a Row Header, which is a feature that is missing from
     * Swing's table implementation.
     *
     * @param rowName
     *            The label (name) to use for the Row Header (first) column
     * @param rowHeaderIsEditable
     *            {@code true} if the Row Header (first column) is editable
     * @param numberOfDataColumns
     *            The number of columns for this model
     * @param dataType
     *            The data type for all of the columns in this model
     * @param defaultDataValue
     *            The default data value to use when there is no initial data
     *
     * @version 1.0
     */
    public RowHeaderTableModel( final String rowName,
                                final boolean rowHeaderIsEditable,
                                final int numberOfDataColumns,
                                final Class< ? > dataType,
                                final Object defaultDataValue ) {
        this( rowName, rowHeaderIsEditable, numberOfDataColumns, dataType, null, defaultDataValue );
    }

    /**
     * Constructs a {@code RowHeaderTableModel} that is specialized to use the
     * first column as a Row Header, which is a feature that is missing from
     * Swing's table implementation.
     *
     * @param rowName
     *            The label (name) to use for the Row Header (first) column
     * @param rowHeaderIsEditable
     *            {@code true} if the Row Header (first column) is editable
     * @param numberOfDataColumns
     *            The number of columns for this model
     * @param dataType
     *            The data type for all of the columns in this model
     * @param dataValues
     *            The initial data, or {@code null} if all should be defaulted
     * @param defaultDataValue
     *            The default data value to use when there is no initial data
     *
     * @version 1.0
     */
    @SuppressWarnings("nls")
    public RowHeaderTableModel( final String rowName,
                                final boolean rowHeaderIsEditable,
                                final int numberOfDataColumns,
                                final Class< ? > dataType,
                                final Object[] dataValues,
                                final Object defaultDataValue ) {
        // Always call the superclass constructor first!
        super();

        rowHeaderEditable = rowHeaderIsEditable;

        // Allocate the data vectors based on number of data columns.
        final int numberOfColumns = numberOfDataColumns + 1;
        columnNames = new String[ numberOfColumns ];
        types = new Class[ numberOfColumns ];
        data = new Object[ 1 ][ numberOfColumns ];

        // The first column serves as a custom Row Header.
        columnNames[ TableConstants.COLUMN_ROW_HEADER ] = "";
        types[ TableConstants.COLUMN_ROW_HEADER ] = dataType;
        data[ 0 ][ TableConstants.COLUMN_ROW_HEADER ] = rowName;

        // Fill the data columns with the supplied data, or default data.
        for ( int i = 1; i < numberOfColumns; i++ ) {
            // Initial empty column headers to work around some Java weirdness.
            columnNames[ i ] = "";
            types[ i ] = dataType;
            data[ 0 ][ i ] = ( dataValues != null ) ? dataValues[ i - 1 ] : defaultDataValue;
        }
    }

    ////////////////// AbstractTableModel method overrides ///////////////////

    /**
     * Returns the name of the specified Table Column
     *
     * @param column
     *            The index of the Table Column whose name is needed
     * @return The name of the specified Table Column
     *
     * @version 1.0
     */
    @Override
    public String getColumnName( final int column ) {
        return columnNames[ column ];
    }

    /**
     * Returns the class type for the specified Table Column
     *
     * @param column
     *            The index of the Table Column whose class type is needed
     * @return The class type for the specified Table Column
     *
     * @version 1.0
     */
    @Override
    public Class< ? > getColumnClass( final int column ) {
        return types[ column ];
    }

    /**
     * Returns {@code true} if the specified cell is editable; {@code false}
     * otherwise.
     *
     * @param row
     *            The row index for the cell whose editable status is needed
     * @param column
     *            The column index for the cell whose editable status is needed
     * @return {@code true} if the specified cell is editable; {@code false}
     *         otherwise
     *
     * @version 1.0
     */
    @Override
    public boolean isCellEditable( final int row, final int column ) {
        return ( column != TableConstants.COLUMN_ROW_HEADER ) || rowHeaderEditable;
    }

    ////////////////////// TableModel method overrides ///////////////////////

    /**
     * Returns the number of rows in the Table Model. A {@code JTable} uses this
     * method to determine how many rows it should display. This method should
     * be quick, as it is called frequently during rendering.
     *
     * @return The number of rows in the Table Model
     * @see #getColumnCount
     *
     * @version 1.0
     */
    @Override
    public int getRowCount() {
        return data.length;
    }

    /**
     * Returns the number of columns in the Table Model. A {@code JTable} uses
     * this method to determine how many columns it should create and display by
     * default.
     *
     * @return The number of columns in the Table Model
     * @see #getRowCount
     *
     * @version 1.0
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Returns the value for the cell at {@code columnIndex} and
     * {@code rowIndex}.
     *
     * @param rowIndex
     *            The row whose value is to be queried
     * @param columnIndex
     *            The column whose value is to be queried
     * @return The value Object at the specified cell
     */
    @Override
    public Object getValueAt( final int rowIndex, final int columnIndex ) {
        return data[ rowIndex ][ columnIndex ];
    }

}
