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

import com.mhschmieder.jgraphics.color.ColorUtilities;
import org.apache.commons.math3.util.FastMath;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.HashSet;

/**
 * {@code TableVectorizationUtilities} is a utility class for Swing based table
 * methods related to vectorization for vector graphics output formats.
 * <p>
 * These utilities help to avoid copy/paste code in parallel implementation
 * hierarchies for containers that include tables.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public final class TableVectorizationUtilities {

    //////////////////////// Vectorization methods ///////////////////////////

    /**
     * This method vectorizes the full Table 9except for excluded Table Rows).
     *
     * @param graphicsContext
     *            The {@link Graphics2D} Graphics Context for vectorizing the
     *            content of this table
     * @param offsetX
     *            The initial offset to apply along the x-axis for positioning
     *            the table rows in the vectorized output
     * @param offsetY
     *            The initial offset to apply along the y-axis for positioning
     *            the table columns in the vectorized output
     * @param table
     *            The Table that hosts the data
     * @param tableHeaderIsInUse
     *            {@code true} if the Table Header is in use
     * @param rowsToExclude
     *            A {@link HashSet} of the rows to exclude from vectorization;
     *            not required to be contiguous
     * @param backgroundColor
     *            The Background Color of the host component; needed strictly
     *            for a workaround related to several vector graphics formatter
     *            libraries that destructively change the color vs. save/restore
     *
     * @version 1.0
     */
    public static void vectorizeTable( final Graphics2D graphicsContext,
                                       final int offsetX,
                                       final int offsetY,
                                       final JTable table,
                                       final boolean tableHeaderIsInUse,
                                       final HashSet< Integer > rowsToExclude,
                                       final Color backgroundColor ) {
        final TableModel tableModel = table.getModel();
        final int columnCount = tableModel.getColumnCount();
        final int rowCount = tableModel.getRowCount();

        final int rowHeight = table.getRowHeight();
        final int rowMargin = table.getRowMargin();

        int rowX = offsetX;
        for ( int columnIndex = 0; columnIndex < columnCount; columnIndex++ ) {
            final TableColumn column = table.getColumnModel().getColumn( columnIndex );
            final int columnWidth = column.getWidth();

            int rowY = offsetY;

            // Vectorize the table header, when present and in use.
            if ( tableHeaderIsInUse ) {
                rowY = vectorizeTableHeader( graphicsContext,
                                             table,
                                             column,
                                             columnIndex,
                                             rowX,
                                             rowY,
                                             columnWidth,
                                             rowHeight,
                                             rowMargin,
                                             backgroundColor );
            }

            // Vectorize the non-excluded table rows.
            vectorizeTableRows( graphicsContext,
                                table,
                                rowCount,
                                rowsToExclude,
                                columnIndex,
                                rowX,
                                rowY,
                                columnWidth,
                                rowHeight,
                                rowMargin,
                                backgroundColor );

            rowX += columnWidth;
        }
    }

    /**
     * Returns the adjusted value of the vertical offset for rendering data, so
     * that the next export target in the pipeline gets positioned correctly.
     * <p>
     * This method vectorizes the Table Header, when present.
     *
     * @param graphicsContext
     *            The {@link Graphics2D} Graphics Context for vectorizing the
     *            content of this table
     * @param table
     *            The Table that hosts the data
     * @param column
     *            The current Table Column to render
     * @param columnIndex
     *            The current Table Column index to render
     * @param rowX
     *            The x-coordinate for placing and rendering this Table Cell
     * @param rowY
     *            The y-coordinate for placing and rendering this Table Cell
     * @param columnWidth
     *            The width of the current Table Column
     * @param rowHeight
     *            The height of each Table Row, in pixels
     * @param rowMargin
     *            The amount of spacing to leave around each Table Cell, in
     *            pixels
     * @param backgroundColor
     *            The Background Color of the host component; needed strictly
     *            for a workaround related to several vector graphics formatter
     *            libraries that destructively change the color vs. save/restore
     * @return The adjusted value of the vertical offset for rendering data
     *
     * @version 1.0
     */
    public static int vectorizeTableHeader( final Graphics2D graphicsContext,
                                            final JTable table,
                                            final TableColumn column,
                                            final int columnIndex,
                                            final int rowX,
                                            final int rowY,
                                            final int columnWidth,
                                            final int rowHeight,
                                            final int rowMargin,
                                            final Color backgroundColor ) {
        final JTableHeader tableHeader = table.getTableHeader();
        if ( tableHeader == null ) {
            return rowY;
        }

        final Object headerData = column.getHeaderValue();
        TableCellRenderer headerRenderer = column.getHeaderRenderer();
        if ( headerRenderer == null ) {
            headerRenderer = tableHeader.getDefaultRenderer();
        }

        vectorizeTableCell( graphicsContext,
                            table,
                            headerData,
                            headerRenderer,
                            -1,
                            columnIndex,
                            rowX,
                            rowY,
                            columnWidth,
                            backgroundColor );

        final int rowYAdjusted = rowY + rowHeight + rowMargin;

        return rowYAdjusted;
    }

    /**
     * Returns the adjusted value of the vertical offset for rendering data, so
     * that the next export target in the pipeline gets positioned correctly.
     * <p>
     * This method vectorizes all of the non-excluded Table Rows.
     *
     * @param graphicsContext
     *            The {@link Graphics2D} Graphics Context for vectorizing the
     *            content of this table
     * @param table
     *            The Table that hosts the data
     * @param rowCount
     *            The total numbers of rows in the Table
     * @param rowsToExclude
     *            A {@link HashSet} of the rows to exclude from vectorization;
     *            not required to be contiguous
     * @param columnIndex
     *            The current Table Column index to render
     * @param rowX
     *            The x-coordinate for placing and rendering this Table Cell
     * @param rowY
     *            The y-coordinate for placing and rendering this Table Cell
     * @param columnWidth
     *            The width of the current Table Column
     * @param rowHeight
     *            The height of each Table Row, in pixels
     * @param rowMargin
     *            The amount of spacing to leave around each Table Cell, in
     *            pixels
     * @param backgroundColor
     *            The Background Color of the host component; needed strictly
     *            for a workaround related to several vector graphics formatter
     *            libraries that destructively change the color vs. save/restore
     * @return The adjusted value of the vertical offset for rendering data
     *
     * @version 1.0
     */
    public static int vectorizeTableRows( final Graphics2D graphicsContext,
                                          final JTable table,
                                          final int rowCount,
                                          final HashSet< Integer > rowsToExclude,
                                          final int columnIndex,
                                          final int rowX,
                                          final int rowY,
                                          final int columnWidth,
                                          final int rowHeight,
                                          final int rowMargin,
                                          final Color backgroundColor ) {
        int rowYAdjusted = rowY;

        for ( int rowIndex = 0; rowIndex < rowCount; rowIndex++ ) {
            // Make sure the current table row isn't marked for exclusion.
            if ( !rowsToExclude.contains( Integer.valueOf( rowIndex ) ) ) {
                final Object cellData = table.getValueAt( rowIndex, columnIndex );
                final TableCellRenderer cellRenderer =
                                                     table.getCellRenderer( rowIndex, columnIndex );

                vectorizeTableCell( graphicsContext,
                                    table,
                                    cellData,
                                    cellRenderer,
                                    rowIndex,
                                    columnIndex,
                                    rowX,
                                    rowYAdjusted,
                                    columnWidth,
                                    backgroundColor );

                rowYAdjusted += rowHeight + rowMargin;
            }
        }

        return rowYAdjusted;
    }

    /**
     * This method vectorizes a specific Table Cell, as long as it is a String.
     *
     * @param graphicsContext
     *            The {@link Graphics2D} Graphics Context for vectorizing the
     *            content of this table
     * @param table
     *            The Table that hosts the data
     * @param cellData
     *            The Cell Data for this Table Cell
     * @param cellRenderer
     *            The Cell Renderer to use for this specific Table Cell
     * @param rowIndex
     *            The current Table Row index to render
     * @param columnIndex
     *            The current Table Column index to render
     * @param rowX
     *            The x-coordinate for placing and rendering this Table Cell
     * @param rowY
     *            The y-coordinate for placing and rendering this Table Cell
     * @param columnWidth
     *            The width of the current Table Column
     * @param backgroundColor
     *            The Background Color of the host component; needed strictly
     *            for a workaround related to several vector graphics formatter
     *            libraries that destructively change the color vs. save/restore
     *
     * @version 1.0
     */
    public static void vectorizeTableCell( final Graphics2D graphicsContext,
                                           final JTable table,
                                           final Object cellData,
                                           final TableCellRenderer cellRenderer,
                                           final int rowIndex,
                                           final int columnIndex,
                                           final int rowX,
                                           final int rowY,
                                           final int columnWidth,
                                           final Color backgroundColor ) {
        // Make sure the current table cell can be rendered.
        final String cellString = ( cellData instanceof String )
            ? ( String ) cellData
            : ( cellData instanceof StringBuffer )
                ? cellData.toString()
                : ( cellData instanceof StringBuilder ) ? cellData.toString() : null;
        if ( cellString == null ) {
            return;
        }

        final Component cellComponent = cellRenderer.getTableCellRendererComponent( table,
                                                                                    cellString,
                                                                                    false,
                                                                                    false,
                                                                                    rowIndex,
                                                                                    columnIndex );

        final Font cellFont = cellComponent.getFont();
        graphicsContext.setFont( cellFont );

        final JLabel cellLabel = ( JLabel ) cellComponent;
        final int cellAlignment = cellLabel.getHorizontalAlignment();
        final FontMetrics cellFontMetrics = cellComponent.getFontMetrics( cellFont );
        final int cellDataWidth = cellFontMetrics.stringWidth( cellString );

        int rowXAligned = rowX;

        switch ( cellAlignment ) {
        case SwingConstants.LEFT:
            // Nothing to do here, as left-justification is the default
            // alignment.
            break;
        case SwingConstants.CENTER:
            rowXAligned += ( int ) FastMath.round( 0.5d * ( columnWidth - cellDataWidth ) );
            break;
        case SwingConstants.RIGHT:
            rowXAligned += columnWidth - cellDataWidth;
            break;
        case SwingConstants.LEADING:
            // Figure out what (if anything) to do for this case.
            break;
        case SwingConstants.TRAILING:
            // Figure out what (if anything) to do for this case.
            break;
        default:
            break;
        }

        // JFreePDF writes white text even against a white background, unless we
        // reset the foreground for the current background. Additionally,
        // JFreeSVG needs the table background to be used as the basis vs. the
        // Graphics Context's current background. Fortunately, EpsToolkit works
        // properly with or without any of these edits.
        final Color oldColor = graphicsContext.getColor();
        final Color newColor = ColorUtilities.getForegroundFromBackground( backgroundColor );
        graphicsContext.setColor( newColor );
        graphicsContext.drawString( cellString, rowXAligned, rowY );
        graphicsContext.setColor( oldColor );
    }

}
