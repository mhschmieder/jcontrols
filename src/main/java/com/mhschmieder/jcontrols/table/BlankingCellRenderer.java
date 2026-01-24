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
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;

/**
 * {@code BlankingCellRenderer} is a further specialization of
 * {@link TextFieldCellRenderer} to handle cells that need to be blank due to
 * the irrelevance of data in that cell position.
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
public class BlankingCellRenderer extends TextFieldCellRenderer {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = 1813023586744172543L;

    /**
     * The "no content" blanking symbol is always centered for clarity.
     */
    public static final int   CELL_ALIGNMENT   = SwingConstants.CENTER;

    /**
     * The text to use to indicate that a table cell is legitimately blank.
     */
    private final String      blankingText;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a Table Cell Renderer that customizes blank cells to be
     * indicated in some special way, such as with a special background color
     * and a symbol for data not applicable (often this is the minus sign).
     *
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param fontSize
     *            The preferred size of the fonts to be used by this table cell
     *            renderer
     *
     * @version 1.0
     */
    public BlankingCellRenderer( final boolean isRowHeader, final float fontSize ) {
        this( isRowHeader,
              fontSize,
              TableConstants.DEFAULT_BLANKING_BACKGROUND_COLOR,
              TableConstants.DEFAULT_BLANKING_FOREGROUND_COLOR,
              TableConstants.DEFAULT_BLANKING_TEXT );
    }

    /**
     * Constructs a Table Cell Renderer that customizes blank cells to be
     * indicated in some special way, such as with a special background color
     * and a symbol for data not applicable (often this is the minus sign).
     *
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param fontSize
     *            The preferred size of the fonts to be used by this table cell
     *            renderer
     * @param cellBackgroundColor
     *            The {@link Color} to use for regular cell background
     * @param cellForegroundColor
     *            The {@link Color} to use for regular cell foreground
     * @param blankingSymbol
     *            The text to use to indicate that a table cell is legitimately
     *            blank
     *
     * @version 1.0
     */
    public BlankingCellRenderer( final boolean isRowHeader,
                                 final float fontSize,
                                 final Color cellBackgroundColor,
                                 final Color cellForegroundColor,
                                 final String blankingSymbol ) {
        // Always call the superclass constructor first!
        super( isRowHeader,
               CELL_ALIGNMENT,
               fontSize,
               TableConstants.DEFAULT_HEADER_BACKGROUND_COLOR,
               TableConstants.DEFAULT_HEADER_FOREGROUND_COLOR,
               cellBackgroundColor,
               cellForegroundColor );

        blankingText = blankingSymbol;
    }

    /////////////// DefaultTableCellRenderer method overrides ////////////////

    /**
     * Returns the default table cell renderer for the specified row and column
     * given the object type of the cell's value.
     * <p>
     * During a printing operation, this method will be called with
     * {@code isSelected} and {@code hasFocus} values of {@code false} to
     * prevent selection and focus from appearing in the printed output. To do
     * other customization based on whether or not the table is being printed,
     * check the return value from
     * {@link javax.swing.JComponent#isPaintingForPrint()}.
     *
     * @param table
     *            The {@link JTable} that uses this header cell renderer
     * @param value
     *            The value to assign to the cell at {@code [row, column]}
     * @param isSelected
     *            {@code true} if cell is selected
     * @param hasFocus
     *            {@code true} if cell has focus
     * @param row
     *            The row index of the cell to render
     * @param column
     *            The column index of the cell to render
     * @return The default table cell renderer for the specified cell
     * @see javax.swing.JComponent#isPaintingForPrint()
     *
     * @version 1.0
     */
    @Override
    public Component getTableCellRendererComponent( final JTable table,
                                                    final Object value,
                                                    final boolean isSelected,
                                                    final boolean hasFocus,
                                                    final int row,
                                                    final int column ) {
        final boolean applyRowHeaderStyle = cellIsRowHeader
                && ( column == TableConstants.COLUMN_ROW_HEADER );

        final Object newValue = applyRowHeaderStyle ? value : blankingText;

        final Component component = super.getTableCellRendererComponent( table,
                                                                         newValue,
                                                                         isSelected,
                                                                         hasFocus,
                                                                         row,
                                                                         column );

        return component;
    }

}
