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

import com.mhschmieder.jcontrols.control.JxTable;

import javax.swing.ListSelectionModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.Color;

/**
 * {@code TableFactory} is a factory class for making customized Swing based
 * tables, without having to unnecessarily derive subclasses from {@link JxTable}
 * that add no data or extra functionality but simply parameterize to a purpose.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public final class TableFactory {

    /**
     * The default constructor is disabled, as this is a static factory class.
     */
    private TableFactory() {}

    //////////////// Column Header Table pseudo-constructors /////////////////

    /**
     * Returns an {@link JxTable} that acts as a one-row table without a regular
     * table header, where the one and only data row is actually the column
     * names. This allows for stacking of different tables as though they are
     * all part of an Excel spreadsheet that supports heterogeneous data.
     *
     * @param columnNames
     *            The names of the table columns
     * @param headerRow
     *            The header row, which is comprised of the column names
     * @return An {@link JxTable} that acts as a single Header Row
     *
     * @since 1.0
     */
    public static JxTable makeColumnHeaderTable(final Object[] columnNames,
                                                final Object[] headerRow ) {
        final JxTable table = makeColumnHeaderTable( columnNames, headerRow, 12f );
        return table;
    }

    /**
     * Returns an {@link JxTable} that acts as a one-row table without a regular
     * table header, where the one and only data row is actually the column
     * names. This allows for stacking of different tables as though they are
     * all part of an Excel spreadsheet that supports heterogeneous data.
     *
     * @param columnNames
     *            The names of the table columns
     * @param headerRow
     *            The header row, which is comprised of the column names
     * @param fontSize
     *            The preferred size of the fonts to be used by the table cells
     * @return An {@link JxTable} that acts as a single Header Row
     *
     * @since 1.0
     */
    public static JxTable makeColumnHeaderTable(final Object[] columnNames,
                                                final Object[] headerRow,
                                                final float fontSize ) {
        final JxTable table =
                           makeColumnHeaderTable( columnNames,
                                                  headerRow,
                                                  fontSize,
                                                  TableConstants.DEFAULT_HEADER_BACKGROUND_COLOR,
                                                  TableConstants.DEFAULT_HEADER_FOREGROUND_COLOR );
        return table;
    }

    /**
     * Returns an {@link JxTable} that acts as a one-row table without a regular
     * table header, where the one and only data row is actually the column
     * names. This allows for stacking of different tables as though they are
     * all part of an Excel spreadsheet that supports heterogeneous data.
     *
     * @param columnNames
     *            The names of the table columns
     * @param headerRow
     *            The header row, which is comprised of the column names
     * @param fontSize
     *            The preferred size of the fonts to be used by the table cells
     * @param cellBackgroundColor
     *            The {@link Color} to use for header cell background
     * @param cellForegroundColor
     *            The {@link Color} to use for header cell foreground
     * @return An {@link JxTable} that acts as a single Header Row
     *
     * @since 1.0
     */
    public static JxTable makeColumnHeaderTable(final Object[] columnNames,
                                                final Object[] headerRow,
                                                final float fontSize,
                                                final Color cellBackgroundColor,
                                                final Color cellForegroundColor ) {
        // Make a custom data model for non-editable header columns in the first
        // data row, and no traditional table column headers.
        final TableModel tableModel = new ColumnHeaderTableModel( columnNames, headerRow );

        // Set up the Column Header Table to disallow cell editing as well as
        // row, column, and cell based selection.
        final JxTable table = new JxTable( tableModel,
                                         null,
                                         null,
                                         ListSelectionModel.SINGLE_SELECTION,
                                         false,
                                         false,
                                         false );

        // Set a Text Field Cell Editor to display the header cells as labels.
        final TableCellEditor cellEditor = new JxTextFieldCellEditor( false, false, true );
        table.setCellEditor( cellEditor );

        // Set a custom Cell Renderer so we can have colored headers (the
        // default renderer for Table Columns is grey).
        final TableCellRenderer cellRenderer = new TableHeaderRenderer( false, fontSize );
        table.setDefaultRenderer( String.class, cellRenderer );

        return table;
    }
}
