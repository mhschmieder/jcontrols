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

import com.mhschmieder.jcontrols.table.TableConstants;
import com.mhschmieder.jcontrols.util.ForegroundManager;
import com.mhschmieder.jgraphics.color.ColorUtilities;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JRootPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.JTextComponent;
import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.EventObject;

/**
 * {@code XTable} is an enhanced table base class for Swing that adds
 * boilerplate code to reduce copy/paste mistakes and inconsistencies in new
 * projects. It isn't declared as abstract, as often it is enough on its own.
 * <p>
 * Note that several of the {@link JTable} override methods have effectively
 * been turned into simple redirections to the parent class, as other approaches
 * were taken to solve some of the original problems with {@link JTable}. Once
 * evaluated more thoroughly, that code might simply get deleted as unneeded.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class JxTable extends JTable implements ForegroundManager {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = -6177284560161112128L;

    /**
     * Auto-edit settings for each individual column.
     */
    private Boolean[]         columnAutoEditable;

    /**
     * Auto-select settings for each individual column.
     */
    private Boolean[]         columnAutoSelectable;

    /**
     * Keep track of whether a change selection event came from a TAB or ENTER.
     */
    private boolean           tabEnterTraversalEventReceived;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs an {@code XTable} that only specifies the Table Model to use.
     *
     * @param tableModel
     *            The Table Model to use for mapping the data type of each
     *            column in this table
     *
     * @since 1.0
     */
    public JxTable(final TableModel tableModel ) {
        this( tableModel, true, true );
    }

    /**
     * Constructs an {@code XTable} that only specifies the Table Model to use,
     * and whether to show horizontal and/or vertical grid lines within the
     * table as visual dividers between individual cells.
     *
     * @param tableModel
     *            The Table Model to use for mapping the data type of each
     *            column in this table
     * @param showHorizontalGridLines
     *            {@code true} if horizontal grid lines should be displayed
     *            between table rows
     * @param showVerticalGridLines
     *            {@code true} if vertical grid lines should be displayed
     *            between table columns
     *
     * @since 1.0
     */
    public JxTable(final TableModel tableModel,
                   final boolean showHorizontalGridLines,
                   final boolean showVerticalGridLines ) {
        this( tableModel,
              null,
              null,
              JTable.AUTO_RESIZE_ALL_COLUMNS,
              ListSelectionModel.SINGLE_SELECTION,
              false,
              false,
              false,
              showHorizontalGridLines,
              showVerticalGridLines );
    }

    /**
     * Constructs an {@code XTable} that only specifies the Table Model to use,
     * and the editable status of each column.
     *
     * @param tableModel
     *            The Table Model to use for mapping the data type of each
     *            column in this table
     * @param columnAutoEdit
     *            Auto-edit settings for each individual column
     *
     * @since 1.0
     */
    public JxTable(final TableModel tableModel, final Boolean[] columnAutoEdit ) {
        this( tableModel,
              columnAutoEdit,
              null,
              ListSelectionModel.SINGLE_SELECTION,
              true,
              false,
              false );
    }

    /**
     * Constructs an {@code XTable} that is partially specified with most of the
     * available parameters that are supported by this custom class.
     *
     * @param tableModel
     *            The Table Model to use for mapping the data type of each
     *            column in this table
     * @param columnAutoEdit
     *            Auto-edit settings for each individual column
     * @param columnAutoSelect
     *            Auto-select settings for each individual column
     * @param selectionMode
     *            Not an enum, so must be a valid int matching single selection,
     *            single interval selection, or multiple interval selection, as
     *            defined in {@code ListSelectionModel}
     * @param columnBasedSelectionAllowed
     *            {@code true} if entire columns can be selected as a cell group
     * @param rowBasedSelectionAllowed
     *            {@code true} if entire rows can be selected as a cell group
     * @param autoCreateRowSorter
     *            {@code true} if an automatic row sorter should be set up that
     *            is strictly UTF-8 alphabetical
     *
     * @since 1.0
     */
    public JxTable(final TableModel tableModel,
                   final Boolean[] columnAutoEdit,
                   final Boolean[] columnAutoSelect,
                   final int selectionMode,
                   final boolean columnBasedSelectionAllowed,
                   final boolean rowBasedSelectionAllowed,
                   final boolean autoCreateRowSorter ) {
        this( tableModel,
              columnAutoEdit,
              columnAutoSelect,
              JTable.AUTO_RESIZE_OFF,
              selectionMode,
              columnBasedSelectionAllowed,
              rowBasedSelectionAllowed,
              autoCreateRowSorter,
              true,
              true );
    }

    /**
     * Constructs an {@code XTable} that is fully specified with all of the
     * available parameters that are supported by this custom class.
     *
     * @param tableModel
     *            The Table Model to use for mapping the data type of each
     *            column in this table
     * @param columnAutoEdit
     *            Auto-edit settings for each individual column
     * @param columnAutoSelect
     *            Auto-select settings for each individual column
     * @param columnAutoResizeMode
     *            Not an enum, so must match one of the five defined int values
     *            in {@link JTable}
     * @param selectionMode
     *            Not an enum, so must be a valid int matching single selection,
     *            single interval selection, or multiple interval selection, as
     *            defined in {@code ListSelectionModel}
     * @param columnBasedSelectionAllowed
     *            {@code true} if entire columns can be selected as a cell group
     * @param rowBasedSelectionAllowed
     *            {@code true} if entire rows can be selected as a cell group
     * @param autoCreateRowSorter
     *            {@code true} if an automatic row sorter should be set up that
     *            is strictly UTF-8 alphabetical
     * @param showHorizontalGridLines
     *            {@code true} if horizontal grid lines should be displayed
     *            between table rows
     * @param showVerticalGridLines
     *            {@code true} if vertical grid lines should be displayed
     *            between table columns
     *
     * @since 1.0
     */
    public JxTable(final TableModel tableModel,
                   final Boolean[] columnAutoEdit,
                   final Boolean[] columnAutoSelect,
                   final int columnAutoResizeMode,
                   final int selectionMode,
                   final boolean columnBasedSelectionAllowed,
                   final boolean rowBasedSelectionAllowed,
                   final boolean autoCreateRowSorter,
                   final boolean showHorizontalGridLines,
                   final boolean showVerticalGridLines ) {
        // Always call the superclass constructor first!
        super( tableModel );

        tabEnterTraversalEventReceived = false;

        // Avoid constructor failure by wrapping the layout initialization in an
        // exception handler that logs the exception and then returns an object.
        try {
            initTable( tableModel,
                       columnAutoEdit,
                       columnAutoSelect,
                       columnAutoResizeMode,
                       selectionMode,
                       columnBasedSelectionAllowed,
                       rowBasedSelectionAllowed,
                       autoCreateRowSorter,
                       showHorizontalGridLines,
                       showVerticalGridLines );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
        }
    }

    /////////////////////// Initialization methods ///////////////////////////

    /**
     * Initializes this table in an encapsulated way that protects all
     * constructors from run-time exceptions that might prevent instantiation.
     * <p>
     * The method is declared final, as any derived classes should avoid
     * unwanted side effects and simply write their own GUI initialization
     * method that adds any extended behaviour or components to the layout.
     *
     * @param tableModel
     *            The Table Model to use for mapping the data type of each
     *            column in this table
     * @param columnAutoEdit
     *            Auto-edit settings for each individual column
     * @param columnAutoSelect
     *            Auto-select settings for each individual column
     * @param columnAutoResizeMode
     *            Not an enum, so must match one of the five defined int values
     *            in {@link JTable}
     * @param selectionMode
     *            Not an enum, so must be a valid int matching single selection,
     *            single interval selection, or multiple interval selection, as
     *            defined in {@code ListSelectionModel}
     * @param columnBasedSelectionAllowed
     *            {@code true} if entire columns can be selected as a cell group
     * @param rowBasedSelectionAllowed
     *            {@code true} if entire rows can be selected as a cell group
     * @param autoCreateRowSorter
     *            {@code true} if an automatic row sorter should be set up that
     *            is strictly UTF-8 alphabetical
     * @param showHorizontalGridLines
     *            {@code true} if horizontal grid lines should be displayed
     *            between table rows
     * @param showVerticalGridLines
     *            {@code true} if vertical grid lines should be displayed
     *            between table columns
     *
     * @since 1.0
     */
    @SuppressWarnings("nls")
    private final void initTable( final TableModel tableModel,
                                  final Boolean[] columnAutoEdit,
                                  final Boolean[] columnAutoSelect,
                                  final int columnAutoResizeMode,
                                  final int selectionMode,
                                  final boolean columnBasedSelectionAllowed,
                                  final boolean rowBasedSelectionAllowed,
                                  final boolean autoCreateRowSorter,
                                  final boolean showHorizontalGridLines,
                                  final boolean showVerticalGridLines ) {
        final int numberOfColumns = tableModel.getColumnCount();
        columnAutoEditable = new Boolean[ numberOfColumns ];
        columnAutoSelectable = new Boolean[ numberOfColumns ];
        for ( int i = 0; i < numberOfColumns; i++ ) {
            columnAutoEditable[ i ] = ( columnAutoEdit != null )
                ? columnAutoEdit[ i ]
                : Boolean.FALSE;
            columnAutoSelectable[ i ] = ( columnAutoSelect != null )
                ? columnAutoSelect[ i ]
                : Boolean.TRUE;
        }

        // Attempt to limit the user's ability to cause weird side effects with
        // resizing, selection, reordering, editing, etc., and try to ensure
        // that the table's initial height accommodates our default row count.
        //
        // Auto-resize must be turned off in order to guarantee custom column
        // widths. If "AUTO_RESIZE_ALL_COLUMNS" is used instead, the horizontal
        // scroll bar never appears, so when the user widens one column, other
        // columns get narrower, potentially hiding them.
        //
        // Setting "row selection allowed" is needed for add/remove row.
        //
        // Column resizing disallowed, to prevent scrollbars blocking visible
        // table in cases where there isn't enough room to make the table itself
        // any wider.
        tableHeader.setReorderingAllowed( false );
        tableHeader.setResizingAllowed( false );
        setAutoResizeMode( columnAutoResizeMode );
        setSelectionMode( selectionMode );
        setCellSelectionEnabled( columnBasedSelectionAllowed && rowBasedSelectionAllowed );
        setColumnSelectionAllowed( columnBasedSelectionAllowed );
        setRowSelectionAllowed( rowBasedSelectionAllowed );

        // Set up a generic string-based table row sorter for all columns.
        //
        // Verify that this also flips the sort order.
        //
        // Evaluate whether we prefer the auto row sorter.
        if ( autoCreateRowSorter ) {
            final TableRowSorter< TableModel > sorter = new TableRowSorter<>( tableModel );
            setRowSorter( sorter );
            // setAutoCreateRowSorter( true );
        }

        // Conditionally turn off the grid, for presentation purposes.
        setShowHorizontalLines( showHorizontalGridLines );
        setShowVerticalLines( showVerticalGridLines );

        // Set table properties to force auto-start editing and to put the focus
        // in the cell after the user starts typing (when the cell is selected).
        putClientProperty( "JTable.autoStartsEdit", Boolean.TRUE );

        // Set table properties to force editor changes to be committed when
        // table focus is lost, as this is more reliable than implementing
        // coarse-grained focus listeners.
        putClientProperty( "JTable.terminateEditOnFocusLost", Boolean.TRUE );

        // Verify that these client properties exist at this level.
        putClientProperty( "JComboBox.isTableCellEditor", Boolean.TRUE );
        putClientProperty( "JTextField.isTableCellEditor", Boolean.TRUE );

        // Attempt to force editing mode to engage when cell focus is gained.
        setSurrendersFocusOnKeystroke( true );

        // Add SelectionListeners to track selection changes across columns.
        //
        // This doesn't really work, and isn't tab-specific anyway.
        // getSelectionModel()
        // .addListSelectionListener( new ExploreSelectionListener() );
        // getColumnModel().getSelectionModel()
        // .addListSelectionListener( new ExploreSelectionListener() );
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Returns {@code true} if the most recent change selection event came from
     * a TAB or ENTER key.
     *
     * @return {@code true} if a change selection event came from a TAB or ENTER
     *
     * @since 1.0
     */
    public final boolean isTabEnterTraversalEventReceived() {
        return tabEnterTraversalEventReceived;
    }

    //
    /**
     * Adjusts the value at the specified cell, accompanied by a user alert.
     * <p>
     * This method is used to adjust an invalid value and alert the user.
     *
     * @param adjustedValue
     *            The pre-adjusted value to set the cell to
     * @param row
     *            The row for the cell whose value is to be set
     * @param column
     *            The column for the cell whose value is to be set
     *
     * @since 1.0
     */
    public void adjustInvalidValueAt( final Object adjustedValue,
                                      final int row,
                                      final int column ) {
        super.setValueAt( adjustedValue, row, column );

        // EventQueue.invokeLater( ( ) -> {
        requestFocusInWindow();
        editCellAt( row, column );
        Toolkit.getDefaultToolkit().beep();
        // } );
    }

    /////////////// ForegroundManager implementation methods /////////////////

    /**
     * Sets the appropriate foreground color for this table based on the
     * specified background color.
     * <p>
     * Both the background and the foreground are applied to the entire layout
     * hierarchy, with the foreground color chosen to provide adequate contrast
     * against the background for text rendering as well as for line graphics.
     * <p>
     * This method should be overridden and called as the first line in the
     * method override, before adding support for GUI elements unique to the
     * derived class hierarchy.
     *
     * @param backColor
     *            The current background color to apply to this table
     *
     * @since 1.0
     */
    @Override
    public void setForegroundFromBackground( final Color backColor ) {
        // Make sure the foreground color is never masked by the background.
        final Color foreColor = ColorUtilities.getForegroundFromBackground( backColor );

        // Tables are more legible when using our preselected dark-against-light
        // color scheme (usually black text on a white background) than when
        // matching the surrounding panel layout background.
        setBackground( TableConstants.DEFAULT_CELL_BACKGROUND_COLOR );
        setForeground( TableConstants.DEFAULT_CELL_FOREGROUND_COLOR );

        // This clears up problems with background color not being honored by
        // the Mac OS X Look and Feel for Swing when a Spring Layout is used.
        final JRootPane rootPane = getRootPane();
        if ( rootPane != null ) {
            rootPane.setBackground( backColor );
            rootPane.setForeground( foreColor );
        }

        // Always check for the presence of a Titled Border, as it has specific
        // API for matching its title text color to the foreground in use.
        final Border border = getBorder();
        if ( border instanceof TitledBorder ) {
            final TitledBorder titledBorder = ( TitledBorder ) border;
            titledBorder.setTitleColor( foreColor );
        }
    }

    /////////////////////// JTable method overrides //////////////////////////

    /**
     * Updates the selection models of the table, depending on the state of the
     * two flags: {@code toggle} and {@code extend}. Most changes to the
     * selection that are the result of keyboard or mouse events received by the
     * UI are channeled through this method so that the behavior may be
     * overridden by a subclass. Some UIs may need more functionality than this
     * method provides, such as when manipulating the lead for a non-contiguous
     * selection, and may not call into this method for some selection changes.
     *
     * @param rowIndex
     *            Affects the selection at <code>row</code>
     * @param columnIndex
     *            Affects the selection at <code>column</code>
     * @param toggle
     *            See the description in
     *            {@code JTable.changeSelection(int, int, boolean, boolean)}
     * @param extend
     *            If {@code true}, extend the current selection
     *
     * @since 1.0
     */
    @Override
    public void changeSelection( final int rowIndex,
                                 final int columnIndex,
                                 final boolean toggle,
                                 final boolean extend ) {
        // Detect cases where a cell of interest was reached via TAB or ENTER.
        //
        // Although this is generalized, it usually applies just as well to
        // cells that host regular Action Buttons, as they are modeled as Check
        // Boxes and thus launch their action if given focus programmatically.
        tabEnterTraversalEventReceived = false;
        final AWTEvent currentEvent = EventQueue.getCurrentEvent();
        if ( currentEvent instanceof KeyEvent ) {
            final KeyEvent keyEvent = ( KeyEvent ) currentEvent;
            if ( keyEvent.getSource() == this ) {
                // Focus changed via the keyboard; see if TAB or ENTER went to a
                // column that needs to avoid acting upon auto-selection events.
                //
                // Do we really need to get the Key Stroke vs. the Key Event?
                final KeyStroke keyStroke = KeyStroke.getKeyStrokeForEvent( keyEvent );
                if ( !columnAutoSelectable[ columnIndex ].booleanValue() && ( KeyStroke
                        .getKeyStroke( KeyEvent.VK_TAB, 0 ).equals( keyStroke )
                        || KeyStroke.getKeyStroke( KeyEvent.VK_ENTER, 0 ).equals( keyStroke ) ) ) {
                    tabEnterTraversalEventReceived = true;
                }
            }
        }

        // Process the actual focus change in the Table Model.
        super.changeSelection( rowIndex, columnIndex, toggle, extend );

        // Combo Boxes always have valid cell editors; whereas Text Fields do
        // not until after invoking editCellAt() successfully. Therefore we must
        // call this method twice, since we don't want Combo Boxes to call
        // editCellAt() due to the side effect of it dismissing the drop-list
        // from the screen and just showing the currently selected value.
        //
        // This is also where we want to avoid invoking the editor on Check
        // Boxes, as that sometimes causes action buttons to execute while
        // tab-traversing a table.
        //
        // Although this didn't solve that problem, it is still better to exit
        // early if the cell contains a Check Box as its editor.
        final Component preEditor = getEditorComponent();
        if ( ( preEditor instanceof JComboBox ) || ( preEditor instanceof JCheckBox ) ) {
            return;
        }

        // The three-argument editCellAt() method was causing toggle buttons to
        // be non-clickable (non-focusable or non-editable)?
        if ( editCellAt( rowIndex, columnIndex ) ) {
            final Component editor = getEditorComponent();

            // Rather than check for null editor, we check for whether it is a
            // text component or not, as that is the easiest way to prevent
            // combo box drop-lists from merely selecting the current displayed
            // value vs. actually dropping the list for a new selection.
            if ( editor instanceof JTextComponent ) {
                EventQueue.invokeLater( () -> editor.requestFocusInWindow() );
            }
        }

        // Force focus to the table cell so it looks like Excel editing.
        EventQueue.invokeLater( this::transferFocus );
    }

    /**
     * Programmatically starts editing the cell at {@code row} and
     * {@code column}, if those indices are in the valid range, and the cell at
     * those indices is editable. Note that this is a convenience method for
     * {@code JTable.editCellAt(int, int, EventObject)}.
     *
     * @param row
     *            The row to be edited
     * @param column
     *            The column to be edited
     * @return {@code false} if for any reason the cell cannot be edited, or if
     *         the indices are invalid
     *
     * @since 1.0
     */
    @Override
    public boolean editCellAt( final int row, final int column ) {
        if ( ( columnAutoEditable != null ) && ( columnAutoEditable.length > column )
                && Boolean.FALSE.equals( columnAutoEditable[ column ] ) ) {
            return false;
        }

        // The code below auto-selects the entire text, but we generally only
        // want to do this on mouse double-clicks, which is taken care of in the
        // cell editors.
        final boolean result = super.editCellAt( row, column );
        // final Component editor = getEditorComponent();
        // if ( editor instanceof JTextComponent ) {
        // ( ( JTextComponent ) editor ).selectAll();
        // }

        return result;
    }

    /**
     * Programmatically starts editing the cell at {@code row} and
     * {@code column}, if those indices are in the valid range, and the cell at
     * those indices is editable.
     * <p>
     * To prevent the {@code JTable} from editing a particular table, column or
     * cell value, return {@code false} from
     * {@code TableModel.isCellEditable(int, int)}.
     *
     * @param row
     *            The row to be edited
     * @param column
     *            The column to be edited
     * @param eventObject
     *            The event to pass into {@code shouldSelectCell()}
     * @return {@code false} if for any reason the cell cannot be edited, or if
     *         the indices are invalid
     *
     * @since 1.0
     */
    @Override
    public boolean editCellAt( final int row, final int column, final EventObject eventObject ) {
        if ( ( columnAutoEditable != null ) && ( columnAutoEditable.length > column )
                && Boolean.FALSE.equals( columnAutoEditable[ column ] ) ) {
            return false;
        }

        // The code below auto-selects the entire text, but we generally only
        // want to do this on mouse double-clicks, which is taken care of in the
        // cell editors.
        final boolean result = super.editCellAt( row, column, eventObject );
        // final Component editor = getEditorComponent();
        // if ( editor instanceof JTextComponent ) {
        // if ( eo == null ) {
        // ( ( JTextComponent ) editor ).selectAll();
        // }
        // else {
        // EventQueue.invokeLater( () -> { ( ( JTextComponent ) editor
        // ).selectAll(); } );
        // }
        // }

        return result;
    }

    /**
     * Select the text when the cell starts editing.
     * <p>
     * a) text will be replaced when you start typing in a cell.
     * <p>
     * b) text will be selected when you use F2 to start editing.
     * <p>
     * c) caret is placed at end of text when double clicking to start editing.
     *
     * @since 1.0
     */
    @Override
    public Component prepareEditor( final TableCellEditor editor,
                                    final int row,
                                    final int column ) {
        final Component component = super.prepareEditor( editor, row, column );

        // if ( component instanceof JTextComponent ) {
        // ( ( JTextComponent ) component ).selectAll();
        // }

        return component;
    }

}
