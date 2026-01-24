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
 * {@code ButtonCellRenderer} is a further specialization of
 * {@link XCellRenderer} to handle cells that host regular action buttons, which
 * we generally want to fill the entire cell.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class ButtonCellRenderer extends XCellRenderer {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID     = -4220421688128009405L;

    /**
     * Action buttons are always centered in their row headers so that borders
     * are balanced on all four sides.
     */
    private static final int  ROW_HEADER_ALIGNMENT = SwingConstants.CENTER;

    /**
     * Action buttons are always centered in their cells so that borders are
     * balanced on all four sides.
     */
    private static final int  CELL_ALIGNMENT       = SwingConstants.CENTER;

    /**
     * The default label to use for the action button; global to all rows.
     */
    private final String      defaultButtonLabel;

    /**
     * The available alternate button labels (optional; can be empty).
     * <p>
     * These alternate labels might get altered at run-time, based on context.
     * <p>
     * If alternates are used in a dynamic table, be sure to provide functions
     * that resize the array via resetting the reference using a new constructor
     * call followed by a re-initialization of the array.
     */
    private final String[]    alternateButtonLabels;

    /**
     * The default {@link Color} to use for the action button background.
     */
    private final Color       defaultBackgroundColor;

    /**
     * The default {@link Color} to use for the action button foreground.
     */
    private final Color       defaultForegroundColor;

    /**
     * The alternate {@link Color} to use for the action button background.
     */
    private final Color       alternateBackgroundColor;

    /**
     * The alternate {@link Color} to use for the action button foreground.
     */
    private final Color       alternateForegroundColor;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a Button Cell Renderer that is specialized to host regular
     * action buttons in place of cell values.
     * <p>
     * This is the default constructor, when no alternate rendering required.
     *
     * @param setAsRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param fontSize
     *            The preferred size of the fonts to be used by this table cell
     *            renderer
     * @param initialButtonLabel
     *            The initial label to use for the action button
     * @param backgroundColor
     *            The default {@link Color} to use for the action button
     *            background
     * @param foregroundColor
     *            The default {@link Color} to use for the action button
     *            foreground
     *
     * @version 1.0
     */
    public ButtonCellRenderer( final boolean setAsRowHeader,
                               final float fontSize,
                               final String initialButtonLabel,
                               final Color backgroundColor,
                               final Color foregroundColor ) {
        this( setAsRowHeader,
              fontSize,
              initialButtonLabel,
              backgroundColor,
              foregroundColor,
              0,
              backgroundColor,
              foregroundColor );
    }

    /**
     * Constructs a Button Cell Renderer that is specialized to host regular
     * action buttons in place of cell values.
     * <p>
     * This is the fully qualified constructor, when alternate rendering is
     * required for the labels (per-row) and the buttons.
     *
     * @param setAsRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param fontSize
     *            The preferred size of the fonts to be used by this table cell
     *            renderer
     * @param initialButtonLabel
     *            The initial label to use for the action button
     * @param backgroundColor
     *            The default {@link Color} to use for the action button
     *            background
     * @param foregroundColor
     *            The default {@link Color} to use for the action button
     *            foreground
     * @param numberOfRows
     *            The number of consecutive rows that need unique alternate
     *            button labels
     * @param backgroundColorAlternate
     *            The alternate {@link Color} to use for the action button
     *            background
     * @param foregroundColorAlternate
     *            The alternate {@link Color} to use for the action button
     *            foreground
     *
     * @version 1.0
     */
    public ButtonCellRenderer( final boolean setAsRowHeader,
                               final float fontSize,
                               final String initialButtonLabel,
                               final Color backgroundColor,
                               final Color foregroundColor,
                               final int numberOfRows,
                               final Color backgroundColorAlternate,
                               final Color foregroundColorAlternate ) {
        // Always call the superclass constructor first!
        super( setAsRowHeader, ROW_HEADER_ALIGNMENT, CELL_ALIGNMENT, fontSize );

        defaultButtonLabel = initialButtonLabel;

        defaultBackgroundColor = backgroundColor;
        defaultForegroundColor = foregroundColor;

        // The alternate labels may be dynamically altered at run-time.
        alternateButtonLabels = new String[ numberOfRows ];
        for ( int i = 0; i < numberOfRows; i++ ) {
            alternateButtonLabels[ i ] = initialButtonLabel;
        }
        alternateBackgroundColor = backgroundColorAlternate;
        alternateForegroundColor = foregroundColorAlternate;
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Returns the default label for the action button; global to all rows.
     *
     * @return The default label for the action button
     *
     * @version 1.0
     */
    public String getDefaultButtonLabel() {
        return defaultButtonLabel;
    }

    /**
     * Sets the new alternate label for the action button at the indicated row
     * index. This method does not change the size of the array of alternate
     * labels, which is static.
     *
     * @param row
     *            The row index of the cell whose alternate button label should
     *            be updated
     * @param alternateButtonLabel
     *            The new alternate label for the action button at the indicated
     *            row index
     *
     * @version 1.0
     */
    public final void setAlternateButtonLabel( final int row, final String alternateButtonLabel ) {
        if ( ( row < alternateButtonLabels.length ) && ( alternateButtonLabel != null )
                && ( !alternateButtonLabel.isEmpty() ) ) {
            alternateButtonLabels[ row ] = alternateButtonLabel;
        }
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
        if ( value instanceof Boolean ) {
            final boolean useAlternates = ( ( alternateButtonLabels != null )
                    && ( alternateButtonLabels.length > row )
                    && ( alternateButtonLabels[ row ] != null )
                    && !alternateButtonLabels[ row ].trim().isEmpty() );
            final String buttonLabelCurrent = useAlternates
                ? alternateButtonLabels[ row ]
                : defaultButtonLabel;

            final Component component = super.getTableCellRendererComponent( table,
                                                                             buttonLabelCurrent,
                                                                             isSelected,
                                                                             hasFocus,
                                                                             row,
                                                                             column );

            // Set the background and foreground colors for the action button,
            // either to the default color or to the alternate colors.
            final Color backgroundColorCurrent = useAlternates
                ? alternateBackgroundColor
                : defaultBackgroundColor;
            final Color foreroundColorCurrent = useAlternates
                ? alternateForegroundColor
                : defaultForegroundColor;
            component.setBackground( backgroundColorCurrent );
            component.setForeground( foreroundColorCurrent );

            return component;
        }

        final Component component = super.getTableCellRendererComponent( table,
                                                                         value,
                                                                         isSelected,
                                                                         hasFocus,
                                                                         row,
                                                                         column );

        // Set the background and foreground colors to their default values, if
        // this table cell is disabled or null and is not selected/highlighted.
        if ( !isSelected ) {
            component.setBackground( TableConstants.DEFAULT_CELL_BACKGROUND_COLOR );
            component.setForeground( TableConstants.DEFAULT_CELL_FOREGROUND_COLOR );
        }

        return component;
    }

}