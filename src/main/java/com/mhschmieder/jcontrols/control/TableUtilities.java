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
package com.mhschmieder.jcontrols.control;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import java.awt.Component;
import java.awt.Dimension;

public final class TableUtilities {

    /**
     * The default constructor is disabled, as this is a static utilities class
     */
    private TableUtilities() {}

    /**
     * Returns a wrapper panel for a table that may need scrollbars.
     * <p>
     * This generally results in fewer than hosting a table directly.
     * 
     * @return a wrapper panel for a table that may need scrollbars
     * 
     * @since 1.0
     */
    public static JPanel makeScrollableTablePanel( final JTable table ) {
        // Layout the scrollable table panel with its components, using the box
        // layout to stack them vertically.
        final JPanel scrollableTablePanel = new JPanel();
        scrollableTablePanel
                .setLayout( new BoxLayout( scrollableTablePanel, BoxLayout.PAGE_AXIS ) );
        scrollableTablePanel.add( table );
        return scrollableTablePanel;
    }

    /**
     * Creates and lays out the scroll pane (and its scrollbars) that may be
     * needed for avoiding clipping when the viewport is smaller than the table.
     * <p>
     * Pass in -1 for width or height if you don't care about one dimension or
     * the other, and it will preserve the default preferred scroll pane dimension.
     *
     * @param table The reference table for the scrollbars
     * @param scrollableTablePanel The panel that wraps the table and that is
     *                             the component supplied to the scroll pane, or
     *                             null to force it to be auto-created here
     * @param tableWidthPixels
     *            The width of the table's viewport, in pixels
     * @param tableHeightPixels
     *            The height of the table's viewport, in pixels
     * @return a scroll pane for a supplied table and its JPanel viewport wrapper
     *
     * @since 1.0
     */
    public static JScrollPane makeTableScrollPane(
            final JTable table,
            final JPanel scrollableTablePanel,
            final int tableWidthPixels,
            final int tableHeightPixels ) {
        final Component scrollPaneComponent = ( scrollableTablePanel != null )
                ? scrollableTablePanel
                : makeScrollableTablePanel( table );
        final JScrollPane scrollPane = new JScrollPane( scrollPaneComponent );
        
        // Set the preferred size of the scroll pane. This limits the contained
        // widget size such that if it gets larger than this specified size, the
        // scroll pane will set the scroll bars (if they were not disabled).
        //
        // The scroll pane's preferred size is only used when the scroll pane
        // wraps a non-scrollable widget, such as a JPanel vs. a JTable (even if
        // the table is in the panel), or if the user supplies negative dimensions.
        // Otherwise, the scrollable viewport size is used, based on table size.
        final int preferredWidth = ( tableWidthPixels > 0 )
                ? tableWidthPixels : scrollPane.getPreferredSize().width;
        final int preferredHeight = ( tableHeightPixels > 0 )
                ? tableHeightPixels : scrollPane.getPreferredSize().height;
        final Dimension preferredSize = new Dimension(
                preferredWidth, preferredHeight );
        table.setPreferredScrollableViewportSize( preferredSize );
        scrollPane.setPreferredSize( preferredSize );

        // Try to make mouse scroll wheel ticks move to the next table row.
        //
        // NOTE: This might need to be based on the table's row height.
        scrollPane.getVerticalScrollBar().setUnitIncrement( 8 );

        return scrollPane;
    }

    /**
     * Returns a panel that lays out the table with all of its additional
     * controls, scrollbars, and optional title.
     *  
     * @param tableTitle An optional title to display above the table
     * @param tableControlPanel An optional control panel to host buttons
     *                          for working with tables, usually add and
     *                          remove row buttons for dynamic tables
     * @param tableHeaderInUse {@code true} if the table set its header
     * @param table The table to wrap in a full contextual layout hierarchy
     * @param width The maximum width of the table
     * @param height The maximum height of the table
     * @return a panel that lays out the table with all of its components
     */
    public static JPanel makeTablePanel( final String tableTitle,
                                         final JPanel tableControlPanel,
                                         final boolean tableHeaderInUse,
                                         final JTable table,
                                         final int width,
                                         final int height ) {
       // We have to build a Scroll panel just for the table in order for the
       // headers to show up.
       final JScrollPane tableScrollPane = makeTableScrollPane(
               table, null, width, height );

       // Build our panel for this table and its controls.
       return makeTablePanel(
               tableTitle,
               tableControlPanel,
               true,
               tableScrollPane,
               table );
    }

    /**
     * Returns a panel that lays out the table with all of its additional
     * controls, scrollbars, and optional title.
     *  
     * @param tableTitle An optional title to display above the table
     * @param tableControlPanel An optional control panel to host buttons
     *                          for working with tables, usually add and
     *                          remove row buttons for dynamic tables
     * @param tableHeaderInUse {@code true} if the table set its header
     * @param tableScrollPane The scroll pane that wraps the table
     * @param table The table to wrap in a full contextual layout hierarchy
     * @return a panel that lays out the table with all of its components
     */
    public static JPanel makeTablePanel( final String tableTitle,
                                         final JPanel tableControlPanel,
                                         final boolean tableHeaderInUse,
                                         final JScrollPane tableScrollPane,
                                         final JTable table ) {
        // Layout the table panel with its components, using the box layout to
        // stack them vertically.
        //
        // Wrapping a panel with a scroll pane vs. a table loses the table
        // header, so we have to re-add it here. This is the preferred approach
        // though, as it means no empty corners when the scroll bars are not
        // present, and also that there is no gap between the table's right hand
        // edge and its enclosing panel.
        //
        // TODO: Make the scroll pane conditional on whether the table is
        //  dynamic and/or could have too many rows to fit in its container?
        final JPanel tablePanel = new JPanel();
        tablePanel.setLayout( new BoxLayout( tablePanel, BoxLayout.PAGE_AXIS ) );        
        tablePanel.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 0 ) );
        
        // Some tables need a title above the main table and column headers.
        if ( ( tableTitle != null ) && !tableTitle.isEmpty() ) {
            tablePanel.add( new JLabel( tableTitle, SwingConstants.CENTER ) );
        }
        
        // If a table control panel (add/remove buttons, etc.) was supplied, add
        // it above the table header but below the table title.
        if ( tableControlPanel != null ) {
            tablePanel.add( tableControlPanel );
        }
       
        // If a table header is know to have been set, grab it and re-add it as
        // wrapping a panel with a scroll pane vs. a table loses the table header.
        // This is the preferred approach though, as it means no empty corners 
        // when the scroll bars are not present, and also that there is no gap
        // between the table's right hand edge and its enclosing panel.
        if ( tableHeaderInUse ) {
            final JTableHeader tableHeader = table.getTableHeader();
            tablePanel.add( tableHeader );
        }
        
        // Now add the main table scroll pane, which does not include the header
        // as otherwise the header (column labels) could easily scroll out of view.
        tablePanel.add( tableScrollPane );

        return tablePanel;
    }
}
