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
package com.mhschmieder.jcontrols.event;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * {@code TableSelectionListener} is a listener dedicated to watching for table
 * selection events and interpreting them based on row and column ranges and
 * single vs. multiple selection models.
 * <p>
 * This is very old code that is no longer used in this library, so it is
 * provided at your own risk; especially given the portions that were commented
 * out a while back. It has been brought up to date style-wise and for proper
 * Javadocs documentation. My recollection is that it fell out of use solely due
 * to my conversion to an almost pure JavaFX based application code base (2015).
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class TableSelectionListener implements ListSelectionListener {

    /**
     * The table to which this listener should be attached.
     */
    private final JTable table;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a specialized version of a List Selection Listener as it
     * pertains to lists of rows and columns (or simply cells) in a table.
     *
     * @param tableOwner
     *            The table owner for the selection listener
     */
    public TableSelectionListener( final JTable tableOwner ) {
        table = tableOwner;
    }

    //////////////// ListSelectionListener method overrides //////////////////

    /**
     * Called whenever the value of the selection changes. In this context, it
     * refers to selected rows and/or columns or just selected cells, in the
     * table to which this listener is attached.
     *
     * @param lse
     *            The list selection event that characterizes the change of
     *            selected rows and columns (or cells) within the table
     *
     * @version 1.0
     */
    @Override
    public void valueChanged( final ListSelectionEvent lse ) {
        if ( !lse.getValueIsAdjusting() ) {
            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();

            // Make sure we start with legal values.
            if ( ( row < 0 ) || ( column < 0 ) ) {
                return;
            }

            // Find the next editable cell.
            while ( !table.isCellEditable( row, column ) ) {
                column++;
                if ( column > ( table.getColumnCount() - 1 ) ) {
                    column = 0;
                    row = ( row == ( table.getRowCount() - 1 ) ) ? 0 : row + 1;
                }
            }
            // Select the cell in the table.
            final int r = row, c = column;
            // EventQueue.invokeLater( ( ) -> {
            table.changeSelection( r, c, true, true );
            // } );
            // Edit.
            // if(table.isCellEditable(row, col))
            // {
            // table.editCellAt(row, col);
            // ((JTextField)editorComponent).selectAll();
            // editorComponent.requestFocusInWindow();
            // }
        }
    }

}
