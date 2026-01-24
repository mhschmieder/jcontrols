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
package com.mhschmieder.jcontrols.control;

import javax.swing.JComboBox;
import javax.swing.JSlider;
import javax.swing.ListCellRenderer;
import java.util.HashSet;

/**
 * {@code XSlider} is an enhancement of the regular {@link JSlider} to formalize
 * much of the parameterization that would otherwise be needed as boilerplate
 * code anywhere a slider is in use. This way, we avoid copy/paste divergence.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class XComboBox extends JComboBox< Object > {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long  serialVersionUID = 5061275016607596355L;

    /**
     * Maintains a list of currently disabled list items, by list index.
     */
    private HashSet< Integer > disabledItems    = new HashSet<>();

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs an {@code XComboBox} with all list cells initially enabled.
     *
     * @version 1.0
     */
    public XComboBox() {
        // Always call the superclass constructor first!
        super();

        // Avoid constructor failure by wrapping the layout initialization in an
        // exception handler that logs the exception and then returns an object.
        try {
            initComboBox();
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    /////////////////////// Initialization methods ///////////////////////////

    /**
     * Initializes this combo box in an encapsulated way that protects all
     * constructors from run-time exceptions that might prevent instantiation.
     * <p>
     * The method is declared final, as any derived classes should avoid
     * unwanted side effects and simply write their own GUI initialization
     * method that adds any extended behaviour or components to the layout.
     *
     * @version 1.0
     */
    private final void initComboBox() {
        // This gives a compiler warning regarding Generics, but there doesn't
        // seem a way around it, as the BasicComboBoxRenderer superclass
        // discards the Generics used by the ListCellRenderer interface.
        @SuppressWarnings("unchecked") final ListCellRenderer< Object > newRenderer =
                                                                                    new DisabledItemsRenderer( this );
        setRenderer( newRenderer );
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Returns {@code true} if the list cell at the provided index is disabled.
     *
     * @param index
     *            The index of the list cell whose disablement status is
     *            requested
     * @return {@code true} if the list cell at the provided index is disabled
     *
     * @version 1.0
     */
    protected boolean isItemDisabledAt( final int index ) {
        return disabledItems.contains( Integer.valueOf( index ) ) ? true : false;
    }

    /**
     * Sets the list cell at the provided index to be disabled or enabled based
     * on the passed {@code disabled} parameter.
     *
     * @param index
     *            The index of the list cell whose disablement status is to be
     *            updated
     * @param disabled
     *            {@code true} if the list cell at the provided index should be
     *            disabled
     *
     * @version 1.0
     */
    protected void setItemDisabledAt( final int index, final boolean disabled ) {
        if ( disabled ) {
            disabledItems.add( Integer.valueOf( index ) );
        }
        else {
            disabledItems.remove( Integer.valueOf( index ) );
        }
    }

    //////////////////// Combo Box manipulation methods //////////////////////

    /**
     * Adds an object to the item list, and optionally disables it from
     * selection.
     * <p>
     * This method works only if the {@code XComboBox} uses a mutable data
     * model.
     * <p>
     * This is basically just a wrapper around {@link JComboBox#addItem}
     *
     * @param object
     *            The object to add to the list
     * @param disabled
     *            {@code true} if the list cell at the provided index should be
     *            disabled
     *
     * @version 1.0
     */
    public void addItem( final Object object, final boolean disabled ) {
        super.addItem( object );

        if ( disabled ) {
            disabledItems.add( Integer.valueOf( getItemCount() - 1 ) );
        }
    }

    ////////////////////// JComboBox method overrides ////////////////////////

    /**
     * Selects the item in the list at the provided {@code index}.
     *
     * @param index
     *            An integer specifying the list item to select, where 0
     *            specifies the first item in the list and -1 indicates no
     *            selection
     * @exception IllegalArgumentException
     *                If {@code index} &lt; -1 or {@code index} is greater than
     *                or equal to size
     *
     * @version 1.0
     */
    @Override
    public void setSelectedIndex( final int index ) {
        if ( !disabledItems.contains( Integer.valueOf( index ) ) ) {
            super.setSelectedIndex( index );
        }
    }

    /**
     * Removes the specified object from the item list.
     * <p>
     * This method works only if the {@code XComboBox} uses a mutable data
     * model.
     *
     * @param object
     *            The object to remove from the item list
     *
     * @version 1.0
     */
    @Override
    public void removeItem( final Object object ) {
        for ( int index = 0; index < getItemCount(); index++ ) {
            if ( getItemAt( index ).equals( object ) ) {
                disabledItems.remove( Integer.valueOf( index ) );
            }
        }
        super.removeItem( object );
    }

    /**
     * Removes the item at the specified index from the item list.
     * <p>
     * This method works only if the {@code XComboBox} uses a mutable data
     * model.
     *
     * @param index
     *            An integer specifying the list item to remove
     *
     * @version 1.0
     */
    @Override
    public void removeItemAt( final int index ) {
        super.removeItemAt( index );

        disabledItems.remove( Integer.valueOf( index ) );
    }

    /**
     * Removes all of the items from the item list.
     *
     * @version 1.0
     */
    @Override
    public void removeAllItems() {
        super.removeAllItems();

        disabledItems = new HashSet<>();
    }

    ////////////////////// JComponent method overloads ///////////////////////

    /**
     * Sets the preferred size of this combo box.
     * <p>
     * This method overloads the regular {@code setPreferredSize()} method, for
     * cases where the client doesn't know what value to use. It reasserts the
     * current value, which generally causes a necessary re-layout of the
     * component after it has been fully initialized. This is a common hack.
     *
     * @version 1.0
     */
    public void setPreferredSize() {
        if ( isPreferredSizeSet() ) {
            setPreferredSize( getPreferredSize() );
        }
    }

}
