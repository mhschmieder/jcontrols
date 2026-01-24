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
 * {@code ToggleButtonCellRenderer} is a further specialization of
 * {@link XCellRenderer} to handle cells that host toggle buttons, which we
 * generally want to fill the entire cell and which replace true/false text.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class ToggleButtonCellRenderer extends XCellRenderer {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID     = -1059104648832704767L;

    /**
     * Toggle buttons are always centered in their row headers so that borders
     * are balanced on all four sides.
     */
    private static final int  ROW_HEADER_ALIGNMENT = SwingConstants.CENTER;

    /**
     * Toggle buttons are always centered in their cells so that borders are
     * balanced on all four sides.
     */
    private static final int  CELL_ALIGNMENT       = SwingConstants.CENTER;

    /**
     * The label to use for the toggle button when it is on.
     */
    private String            onLabel;

    /**
     * The label to use for the toggle button when it is off.
     */
    private String            offLabel;

    /**
     * The {@link Color} to use for the toggle button background when on.
     */
    private final Color       onBackground;

    /**
     * The {@link Color} to use for the toggle button background when off.
     */
    private final Color       offBackground;

    /**
     * The {@link Color} to use for the toggle button foreground when on.
     */
    private final Color       onForeground;

    /**
     * The {@link Color} to use for the toggle button foreground when off.
     */
    private final Color       offForeground;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a Table Cell Renderer that is specialized to host toggle
     * buttons as the renderer for true/false boolean values.
     *
     * @param setAsRowHeader
     *            {@code true} if this cell should be used as a row header
     * @param fontSize
     *            The preferred size of the fonts to be used by this table cell
     *            renderer
     * @param onButtonLabel
     *            The initial label to use for the toggle button when it is on
     * @param offButtonLabel
     *            The initial label to use for the toggle button when it is off
     * @param onBackgroundColor
     *            The {@link Color} to use for toggle button background when on
     * @param offBackgroundColor
     *            The {@link Color} to use for toggle button background when off
     * @param onForegroundColor
     *            The {@link Color} to use for toggle button foreground when on
     * @param offForegroundColor
     *            The {@link Color} to use for toggle button foreground when off
     *
     * @version 1.0
     */
    public ToggleButtonCellRenderer( final boolean setAsRowHeader,
                                     final float fontSize,
                                     final String onButtonLabel,
                                     final String offButtonLabel,
                                     final Color onBackgroundColor,
                                     final Color offBackgroundColor,
                                     final Color onForegroundColor,
                                     final Color offForegroundColor ) {
        // Always call the superclass constructor first!
        super( setAsRowHeader, ROW_HEADER_ALIGNMENT, CELL_ALIGNMENT, fontSize );

        onLabel = onButtonLabel;
        offLabel = offButtonLabel;
        onBackground = onBackgroundColor;
        offBackground = offBackgroundColor;
        onForeground = onForegroundColor;
        offForeground = offForegroundColor;
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * This method replaces the current label to use for the toggle button when
     * it is on, with a new label provided by the caller of this method.
     * <p>
     * In many application contexts, the text for a toggle button may be
     * conditional upon other settings or workflow, so it is important to allow
     * for the label to be changed post-construction.
     *
     * @param onButtonLabel
     *            The new label to use for the toggle button when it is on
     *
     * @version 1.0
     */
    public final void setOnButtonLabel( final String onButtonLabel ) {
        onLabel = onButtonLabel;
    }

    /**
     * This method replaces the current label to use for the toggle button when
     * it is off, with a new label provided by the caller of this method.
     * <p>
     * In many application contexts, the text for a toggle button may be
     * conditional upon other settings or workflow, so it is important to allow
     * for the label to be changed post-construction.
     *
     * @param offButtonLabel
     *            The new label to use for the toggle button when it is off
     *
     * @version 1.0
     */
    public final void setOffButtonLabel( final String offButtonLabel ) {
        offLabel = offButtonLabel;
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
        if ( ( value instanceof Boolean ) || ( value instanceof String ) ) {
            final boolean on = ( value instanceof Boolean )
                ? ( ( Boolean ) value ).booleanValue()
                : isSelected;
            final String newValue = on ? onLabel : offLabel;

            final Component component = super.getTableCellRendererComponent( table,
                                                                             newValue,
                                                                             isSelected,
                                                                             hasFocus,
                                                                             row,
                                                                             column );

            // Set the background and foreground colors based on whether the
            // toggle button is on or off.
            final Color background = on ? onBackground : offBackground;
            final Color foreground = on ? onForeground : offForeground;
            component.setBackground( background );
            component.setForeground( foreground );

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
