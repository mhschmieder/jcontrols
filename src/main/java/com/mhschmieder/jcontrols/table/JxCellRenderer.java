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
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Font;

/**
 * {@code XCellRenderer} is a custom base class that extends
 * {@link DefaultTableCellRenderer} for the behavior and look to be used for
 * regular cells vs. row headers (when present).
 * <p>
 * This class will usually not be used directly, as it is designed to be
 * sub-classed for type-specific behavior (such as for text, numbers, etc.).
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class JxCellRenderer extends DefaultTableCellRenderer {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = 2502047223102184045L;

    /**
     * Flag for whether this cell (if {@code true}) is a row header or not.
     */
    protected boolean         cellIsRowHeader;

    /**
     * The horizontal alignment to use for row header cells.
     */
    private final int         rowHeaderAlignment;

    /**
     * The horizontal alignment to use for regular cells.
     */
    private final int         horizontalAlignment;

    /**
     * The preferred font size to use for table cell rendering, if available.
     */
    private final float       preferredFontSize;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a Table Cell Renderer that is generalized for use in regular
     * cells but can be sub-classed for specific type-based rendering.
     *
     * @param setAsRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param rowHeaderCellAlignment
     *            The alignment to use if this cell is a row header
     * @param cellAlignment
     *            The alignment to use if this cell is not a row header
     * @param fontSize
     *            The preferred size of the fonts to be used by this table cell
     *            renderer
     *
     * @version 1.0
     */
    public JxCellRenderer(final boolean setAsRowHeader,
                          final int rowHeaderCellAlignment,
                          final int cellAlignment,
                          final float fontSize ) {
        // Always call the superclass constructor first!
        super();

        cellIsRowHeader = setAsRowHeader;
        rowHeaderAlignment = rowHeaderCellAlignment;
        horizontalAlignment = cellAlignment;
        preferredFontSize = fontSize;
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

        final int horizontalAlignmentAdjusted = applyRowHeaderStyle
            ? rowHeaderAlignment
            : horizontalAlignment;
        setHorizontalAlignment( horizontalAlignmentAdjusted );
        setHorizontalTextPosition( horizontalAlignmentAdjusted );

        final Component component = super.getTableCellRendererComponent( table,
                                                                         value,
                                                                         isSelected,
                                                                         hasFocus,
                                                                         row,
                                                                         column );

        // Customize the font for better sizing, conditionally setting the first
        // column to use a bold italic font as a row header.
        //
        // As the system doesn't seem able to find BOLD and/or ITALIC fonts
        // smaller than 11-point, we use 11-point BOLD and/or ITALIC in such
        // cases. This may currently be a Windows-specific issue.
        //
        // We also avoid fonts larger than 12-point, as Nimbus uses large fonts
        // by default, which causes clipping problems when inside cell editors.
        final Font defaultFont = component.getFont();
        final float boldFontSize = FastMath.max( 11f, preferredFontSize );
        final float plainFontSize = FastMath.min( 12f, preferredFontSize );
        final Font tableCellFont = applyRowHeaderStyle
            ? defaultFont.deriveFont( Font.BOLD | Font.ITALIC, boldFontSize )
            : defaultFont.deriveFont( Font.PLAIN, plainFontSize );
        component.setFont( tableCellFont );

        // This is temporary code for debugging purposes, to see what fonts are
        // selected on each platform.
        // System.out.println( tableCellFont.getName() );

        return component;
    }

}
