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

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Font;

/**
 * {@code DataViewCellRenderer} is a customized extension of
 * {@link DefaultTableCellRenderer} that has an awareness of the table contents
 * being non-editable, as well as whether the first content row of the table
 * (not to be confused with the actual Table Header) is to be used as the table
 * header. Sometimes this is necessary for style purposes; especially when
 * emulating a row-oriented table by stacking single-row tables and applying row
 * header rendering to the first column of each.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class DataViewCellRenderer extends DefaultTableCellRenderer {
    /**
     *
     */
    private static final long serialVersionUID = -4734346409222078810L;

    /**
     * Flag for whether the first row is treated as a column header or not.
     */
    private boolean           firstRowHeader;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a {@code DataViewCellRenderer} as a specialization of
     * {@link DefaultTableCellRenderer}.
     *
     * @version 1.0
     */
    public DataViewCellRenderer() {
        // Always call the superclass constructor first!
        super();

        firstRowHeader = false;
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Sets the flag for whether the first row of the table is used as a header.
     *
     * @param isFirstRowHeader
     *            {@code true} if the first row is to be treated as column
     *            headers; {@code false} if it is just a regular data row
     *
     * @version 1.0
     */
    public void setFirstRowHeader( final boolean isFirstRowHeader ) {
        firstRowHeader = isFirstRowHeader;
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
        final Component component = super.getTableCellRendererComponent( table,
                                                                         value,
                                                                         isSelected,
                                                                         hasFocus,
                                                                         row,
                                                                         column );

        // Customize the font for better sizing, conditionally setting the first
        // row to use a larger, bold, font, as column headers.
        final Font defaultFont = component.getFont();
        final Font tableCellFont = ( firstRowHeader && ( row == 0 ) )
            ? defaultFont.deriveFont( Font.BOLD, 11 )
            : defaultFont.deriveFont( Font.PLAIN, 10 );
        component.setFont( tableCellFont );

        return component;
    }

}
