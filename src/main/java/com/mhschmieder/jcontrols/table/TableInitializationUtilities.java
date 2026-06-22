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

import org.apache.commons.math3.util.FastMath;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.Component;
import java.awt.EventQueue;

/**
 * {@code TableInitializationUtilities} is a utility class for Swing based table
 * methods related to initializations of cell renderers, row sizing, etc.
 * <p>
 * These utilities help to avoid copy/paste code in parallel implementation
 * hierarchies for containers that include tables.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class TableInitializationUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class.
     */
    private TableInitializationUtilities() {}

    /////////////////////// Initialization methods ///////////////////////////

    /**
     * Initializes all of the table metrics, such as row height, column width.
     *
     * @param table
     *            The Table that hosts the data
     * @param columnWidths
     *            A list of the widths for each table column
     *
     * @since 1.0
     */
    public static void initTableMetrics( final JTable table, final int[] columnWidths ) {
        // These actions MUST be done on the event-dispatching thread!
        EventQueue.invokeLater( () -> {
            // Customize the column widths for best fit and balance.
            initColumnWidths( table, columnWidths );

            // Pack all rows for best height based on cell components.
            packAllRows( table );

            // Re-validate the table to display at the new cell sizes.
            table.revalidate();
        } );
    }

    /**
     * Customizes the column widths for best fit and balance.
     *
     * @param table
     *            The Table that hosts the data
     * @param columnWidths
     *            A list of the widths for each table column
     *
     * @since 1.0
     */
    public static void initColumnWidths( final JTable table, final int[] columnWidths ) {
        final TableColumnModel tableColumnModel = table.getColumnModel();
        TableColumn column = null;
        for ( int i = 0; i < columnWidths.length; i++ ) {
            column = tableColumnModel.getColumn( i );
            if ( column != null ) {
                column.setPreferredWidth( columnWidths[ i ] );
                column.setMinWidth( columnWidths[ i ] );
            }
        }
    }

    /**
     * Packs all rows for best height based on cell components.
     *
     * @param table
     *            The Table that hosts the data
     *
     * @since 1.0
     */
    public static void packAllRows( final JTable table ) {
        final int firstRow = 0;
        final int lastRow = table.getRowCount() - 1;
        packRows( table, firstRow, lastRow );
    }

    /**
     * Packs the selected row range for best height based on cell components.
     *
     * @param table
     *            The Table that hosts the data
     * @param firstRow
     *            The lowest index for the range of rows to use for row height
     *            metrics
     * @param lastRow
     *            The highest index for the range of rows to use for row height
     *            metrics
     *
     * @since 1.0
     */
    public static void packRows( final JTable table, final int firstRow, final int lastRow ) {
        // Find out what the intra-cell spacing is between table rows.
        final int margin = table.getRowMargin();

        // Get the current row height default for all table rows.
        int rowHeight = table.getRowHeight();

        // Find the tallest cell in the selected row range.
        for ( int row = firstRow; row <= lastRow; row++ ) {
            // Get the preferred height for the current row, and use it to
            // update overall preferred row height for the selected row range.
            final int preferredRowHeight = getPreferredRowHeight( table, row, rowHeight, margin );
            rowHeight = FastMath.max( rowHeight, preferredRowHeight );
        }

        // Set the row heights for the entire table, based on the assigned cell
        // renderers, so that selected text is not occluded when a cell with a
        // drop-list is selected.
        table.setRowHeight( rowHeight );
    }

    /**
     * Returns the preferred row height for a given row based on cell renderers.
     *
     * @param table
     *            The Table that hosts the data
     * @param row
     *            The index of the table row whose preferred height is needed
     * @param rowHeight
     *            The cumulatively accrued row height from previous rows
     * @param margin
     *            The intra-cell spacing that is applied between table rows
     * @return The preferred row height for a given row based on cell renderers
     *
     * @since 1.0
     */
    public static int getPreferredRowHeight( final JTable table,
                                             final int row,
                                             final int rowHeight,
                                             final int margin ) {
        int preferredRowHeight = rowHeight;

        // Determine the highest cell in the current row.
        //
        // We factor for the margin due to extra decoration used for drop-lists.
        final int firstColumn = 0;
        final int lastColumn = table.getColumnCount() - 1;
        for ( int column = firstColumn; column <= lastColumn; column++ ) {
            final TableCellRenderer renderer = table.getCellRenderer( row, column );
            final Component component = table.prepareRenderer( renderer, row, column );
            final int preferredCellHeight = component.getPreferredSize().height + ( margin * 2 );
            preferredRowHeight = FastMath.max( preferredRowHeight, preferredCellHeight );
        }

        return preferredRowHeight;
    }

}
