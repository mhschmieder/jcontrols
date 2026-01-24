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

import org.apache.commons.math3.util.FastMath;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

/**
 * {@code TableHeaderRenderer} customizes the {@link DefaultTableCellRenderer}
 * for the behavior and look to be used for header columns.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class TableHeaderRenderer extends DefaultTableCellRenderer {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID     = 5253201210489004788L;

    /**
     * Flag for whether to apply stateless cell borders for the header cells.
     */
    private boolean           statelessCellBorders = false;

    /**
     * The preferred font size to use for table cell rendering, if available.
     */
    private final float       preferredFontSize;

    /**
     * The {@link Color} to use for header cell background
     */
    private final Color       cellBackground;

    /**
     * The {@link Color} to use for header cell foreground
     */
    private final Color       cellForeground;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a Table Cell Renderer that is optimized for use in rendering
     * table headers, such as specifying whether to show cell borders for the
     * header columns, and sets default background and foreground colors to use
     * in place of the default gray.
     *
     * @param setStatelessCellBorders
     *            {@code true} if cell borders should be set the same regardless
     *            of focus and selection status; {@code false} otherwise
     * @param fontSize
     *            The preferred size of the fonts to be used by this table cell
     *            renderer
     *
     * @version 1.0
     */
    public TableHeaderRenderer( final boolean setStatelessCellBorders, final float fontSize ) {
        this( setStatelessCellBorders,
              fontSize,
              TableConstants.DEFAULT_HEADER_BACKGROUND_COLOR,
              TableConstants.DEFAULT_HEADER_FOREGROUND_COLOR );
    }

    /**
     * Constructs a Table Cell Renderer that is optimized for use in rendering
     * table headers, such as specifying whether to show cell borders for the
     * header columns, and the preferred custom background and foreground colors
     * to use in place of the default gray.
     *
     * @param setStatelessCellBorders
     *            {@code true} if cell borders should be set the same regardless
     *            of focus and selection status; {@code false} otherwise
     * @param fontSize
     *            The preferred size of the fonts to be used by this table cell
     *            renderer
     * @param cellBackgroundColor
     *            The {@link Color} to use for header cell background
     * @param cellForegroundColor
     *            The {@link Color} to use for header cell foreground
     *
     * @version 1.0
     */
    public TableHeaderRenderer( final boolean setStatelessCellBorders,
                                final float fontSize,
                                final Color cellBackgroundColor,
                                final Color cellForegroundColor ) {
        // Always call the superclass constructor first!
        super();

        statelessCellBorders = setStatelessCellBorders;
        preferredFontSize = fontSize;
        cellBackground = cellBackgroundColor;
        cellForeground = cellForegroundColor;

        setHorizontalAlignment( SwingConstants.CENTER );
        setHorizontalTextPosition( SwingConstants.CENTER );
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
    @SuppressWarnings("nls")
    @Override
    public Component getTableCellRendererComponent( final JTable table,
                                                    final Object value,
                                                    final boolean isSelected,
                                                    final boolean hasFocus,
                                                    final int row,
                                                    final int column ) {
        final String newValue = ( value instanceof String ) ? ( String ) value : "";

        final Component component = super.getTableCellRendererComponent( table,
                                                                         newValue,
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
        final Font defaultFont = component.getFont();
        final float boldFontSize = FastMath.max( 11f, preferredFontSize );
        final Font tableCellFont = defaultFont.deriveFont( Font.BOLD | Font.ITALIC, boldFontSize );
        component.setFont( tableCellFont );

        // Set the background and foreground colors for the table header cells.
        component.setBackground( cellBackground );
        component.setForeground( cellForeground );

        // If stateless cell borders are set, make sure the border of each cell
        // paints the same regardless of focus and selection, as resetting the
        // background color of the host layout panel may cause it to disappear
        // on table column headers.
        if ( statelessCellBorders ) {
            setBorder( UIManager.getBorder( "TableHeader.cellBorder" ) );
        }

        return component;
    }

}
