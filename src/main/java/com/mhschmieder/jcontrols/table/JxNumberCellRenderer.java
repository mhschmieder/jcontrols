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

import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * {@code NumberCellRenderer} is a further specialization of
 * {@link JxTextFieldCellRenderer} to deal with the specifics of numeric text
 * strings. This includes model/view syncing issues with the rendering of
 * positive numbers that may have been typed with the plus sign by the user.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class JxNumberCellRenderer extends JxTextFieldCellRenderer {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = -8435290828619739802L;

    /**
     * Numbers are generally right-justified so that the magnitude is obvious.
     */
    public static final int   CELL_ALIGNMENT   = SwingConstants.RIGHT;

    /**
     * Returns a copy of the original numeric string, stripped of the positive
     * sign if present.
     * <p>
     * This is often necessary when there is recursion during mode/view syncing,
     * as renderers shouldn't display the superfluous "+" sign as numbers are
     * positive by default (when the minus sign isn't present), and as most
     * number parsers can't handle the character and will throw an exception.
     * <p>
     * This method is borrowed from a more general utility toolkit that has not
     * yet been published, so is a temporary copy/paste placeholder and will be
     * removed once the basic commons toolkits are published.
     *
     * @param numericString
     *            The numeric string to search for the superfluous positive sign
     * @return The numeric string stripped of the positive sign if present
     *
     * @version 1.0
     */
    public static final String stripPositiveSign( final String numericString ) {
        // Effectively treat this as a no-op if we have a null or empty string.
        if ( ( numericString == null ) || numericString.trim().isEmpty() ) {
            return numericString;
        }

        final StringBuilder signStrippedNumericStringBuilder = new StringBuilder( numericString );
        if ( numericString.charAt( 0 ) == '+' ) {
            signStrippedNumericStringBuilder.deleteCharAt( 0 );
        }

        final String signStrippedNumericString = signStrippedNumericStringBuilder.toString();

        return signStrippedNumericString;
    }

    /**
     * The units label to attach to each numeric value.
     */
    private String         unitsLabel;

    /**
     * Flag that determines whether to strip the positive sign if present.
     */
    private final boolean  trimPlusSign;

    /**
     * Number format cache used for locale-specific number formatting.
     */
    protected NumberFormat numberFormat;

    /**
     * Number format cache used for locale-specific number parsing.
     */
    protected NumberFormat numberParse;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a Table Cell Renderer that is specialized for rendering
     * numeric text, using the default background and foreground colors.
     *
     * @param setAsRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param fontSize
     *            The preferred size of the fonts to be used by this table cell
     *            renderer
     * @param units
     *            The units to use for the numeric values, in string format
     * @param stripPositiveSign
     *            If {@code true}, strip the positive sign from the current
     *            displayed value if present
     *
     * @version 1.0
     */
    public JxNumberCellRenderer(final boolean setAsRowHeader,
                                final float fontSize,
                                final String units,
                                final boolean stripPositiveSign ) {
        // Always call the superclass constructor first!
        this( setAsRowHeader,
              fontSize,
              TableConstants.DEFAULT_HEADER_BACKGROUND_COLOR,
              TableConstants.DEFAULT_HEADER_FOREGROUND_COLOR,
              TableConstants.DEFAULT_CELL_BACKGROUND_COLOR,
              TableConstants.DEFAULT_CELL_FOREGROUND_COLOR,
              units,
              stripPositiveSign );
    }

    /**
     * Constructs a Table Cell Renderer that is specialized for rendering
     * numeric text, using custom background and foreground colors.
     *
     * @param isRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param fontSize
     *            The preferred size of the fonts to be used by this table cell
     *            renderer
     * @param rowHeaderBackgroundColor
     *            The {@link Color} to use for row header cell background
     * @param rowHeaderForegroundColor
     *            The {@link Color} to use for row header cell foreground
     * @param cellBackgroundColor
     *            The {@link Color} to use for regular cell background
     * @param cellForegroundColor
     *            The {@link Color} to use for regular cell foreground
     * @param units
     *            The units to use for the numeric values, in string format
     * @param stripPositiveSign
     *            If {@code true}, strip the positive sign from the current
     *            displayed value if present
     *
     * @version 1.0
     */
    public JxNumberCellRenderer(final boolean isRowHeader,
                                final float fontSize,
                                final Color rowHeaderBackgroundColor,
                                final Color rowHeaderForegroundColor,
                                final Color cellBackgroundColor,
                                final Color cellForegroundColor,
                                final String units,
                                final boolean stripPositiveSign ) {
        // Always call the superclass constructor first!
        super( isRowHeader,
               CELL_ALIGNMENT,
               fontSize,
               rowHeaderBackgroundColor,
               rowHeaderForegroundColor,
               cellBackgroundColor,
               cellForegroundColor );

        unitsLabel = units;
        trimPlusSign = stripPositiveSign;

        try {
            initCellRenderer();
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    /////////////////////// Initialization methods ///////////////////////////

    /**
     * Initializes this cell renderer in an encapsulated way that protects all
     * constructors from run-time exceptions that might prevent instantiation.
     * <p>
     * The method is declared final, as any derived classes should avoid
     * unwanted side effects and simply write their own GUI initialization
     * method that adds any extended behaviour or components to the layout.
     *
     * @version 1.0
     */
    private final void initCellRenderer() {
        // Initialize the Number Formatters, using the host's default Locale.
        initNumberFormatters( Locale.getDefault() );
    }

    /**
     * Initializes and caches the number formatters, as the system-level query
     * for the host's default locale is expensive and can cause a freeze-up.
     * <p>
     * The method is declared final, as any derived classes should avoid
     * unwanted side effects and simply write their own number formatter
     * initialization method that resets precision to what is preferred in the
     * context of that tighter scope. These defaults are fairly common ones.
     *
     * @param locale
     *            The locale to use for number formatting purposes
     *
     * @version 1.0
     */
    private final void initNumberFormatters( final Locale locale ) {
        // Cache the number formats so that we don't have to get information
        // about locale from the OS each time we format a number.
        numberFormat = NumberFormat.getNumberInstance( locale );
        numberParse = ( NumberFormat ) numberFormat.clone();

        // Set the default precision for floating-point text formatting.
        numberFormat.setMinimumFractionDigits( 0 );
        numberFormat.setMaximumFractionDigits( 2 );

        // Set the default precision for floating-point text parsing.
        numberParse.setMinimumFractionDigits( 0 );
        numberParse.setMaximumFractionDigits( 10 );
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * @param units
     *            The units to use for the numeric values, in string format
     *
     * @version 1.0
     */
    public final void setUnitsLabel( final String units ) {
        unitsLabel = units;
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
        final boolean applyRowHeaderStyle = cellIsRowHeader
                && ( column == TableConstants.COLUMN_ROW_HEADER );

        // Provide exception-proof handling of the cell's current value.
        String newValue = ( value instanceof String ) ? ( ( String ) value ).trim() : "";
        if ( !newValue.isEmpty() && ( !applyRowHeaderStyle ) ) {
            try {
                if ( trimPlusSign ) {
                    newValue = stripPositiveSign( newValue );
                }
                newValue = numberFormat.format( numberParse.parse( newValue ).doubleValue() )
                        + unitsLabel;
            }
            catch ( final Exception e ) {
                e.printStackTrace();
            }
        }

        final Component component = super.getTableCellRendererComponent( table,
                                                                         newValue,
                                                                         isSelected,
                                                                         hasFocus,
                                                                         row,
                                                                         column );

        return component;
    }

}
