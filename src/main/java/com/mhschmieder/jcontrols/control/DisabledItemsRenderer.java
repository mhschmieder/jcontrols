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
package com.mhschmieder.jcontrols.control;

import javax.swing.Icon;
import javax.swing.JList;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.Color;
import java.awt.Component;

/**
 * {@code DisabledItemsRenderer} is a specialized Combo Box list cell renderer
 * that gives the user visual feedback when a list item is currently disabled.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class DisabledItemsRenderer extends BasicComboBoxRenderer {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = -7037535725607183944L;

    /**
     * The {@link XComboBox} that is using this list cell renderer.
     */
    private final XComboBox   comboBox;

    /**
     * The background color to use for list items that are disabled.
     */
    private final Color       disabledItemForeground;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a {@code DisabledItemsRenderer} for giving the user useful
     * visual feedback that certain items in a {@code ComboBox} drop-list are
     * currently disabled.
     *
     * @param comboBoxOwner
     *            The {@link XComboBox} that is using this list cell renderer
     *
     * @version 1.0
     */
    @SuppressWarnings("nls")
    public DisabledItemsRenderer( final XComboBox comboBoxOwner ) {
        // Always call the superclass constructor first!
        super();

        comboBox = comboBoxOwner;

        final LookAndFeel lookAndFeel = UIManager.getLookAndFeel();
        disabledItemForeground = ( lookAndFeel instanceof NimbusLookAndFeel )
            ? Color.GRAY
            : UIManager.getColor( "Label.disabledForeground" );
    }

    //////////////// BasicComboBoxRenderer method overrides //////////////////

    /**
     * Returns the {@link Component} associated with rendering the current list
     * cell, which in this case is always the current instance of this custom
     * renderer class.
     *
     * @param list
     *            The full list for the associated {@code ComboBox}
     * @param value
     *            The value of the current list cell
     * @param index
     *            The index of the current list cell
     * @param isSelected
     *            {@code true} if the list cell is currently selected
     * @param cellHasFocus
     *            {@code true} if the list cell currently has focus
     *
     * @version 1.0
     */
    @SuppressWarnings("nls")
    @Override
    public Component getListCellRendererComponent( final JList list,
                                                   final Object value,
                                                   final int index,
                                                   final boolean isSelected,
                                                   final boolean cellHasFocus ) {
        final boolean listCellDisabled = comboBox.isItemDisabledAt( index );
        final Color background = listCellDisabled
            ? list.getBackground()
            : isSelected ? list.getSelectionBackground() : list.getBackground();
        final Color foreground = listCellDisabled
            ? disabledItemForeground
            : isSelected ? list.getSelectionForeground() : list.getForeground();

        setBackground( background );
        setForeground( foreground );

        setFont( list.getFont() );

        if ( value instanceof Icon ) {
            setIcon( ( Icon ) value );
        }
        else {
            setText( ( value == null ) ? "" : value.toString() );
        }

        return this;
    }

}