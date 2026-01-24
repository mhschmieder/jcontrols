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

import com.mhschmieder.jcommons.util.SystemType;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

// TODO: See how much of this got replicated in fxguitoolkit, merge the
//  best code into this utility, then delete the redundant copies.
public class MenuUtilities {
    // The MRU cache size is currently limited to in-line menu items using
    // numeric mnemonics from 1-9.
    public static final int MRU_CACHE_SIZE  = 9;

    // If an accelerator is assigned, get it from a resource bundle.
    private static KeyStroke getAcceleratorKeyStroke(   final String menuName,
                                                        final String itemName,
                                                        final ResourceBundle resourceBundle,
                                                        final SystemType systemType ) {
        // There must always be both a menu name and an item name for each menu
        // item, in order to find its accelerator from the resource bundle.
        if ( ( menuName == null ) || menuName.isEmpty() || ( itemName == null )
                || itemName.isEmpty() ) {
            return null;
        }

        // Composite the menu item name from the menu and item names.
        final String menuItemName = menuName + "." + itemName; //$NON-NLS-1$

        // Generate the resource lookup key for the menu item accelerator.
        final String resourceKey = menuItemName + ".accelerator" //$NON-NLS-1$
                + ( SystemType.MACOS.equals( systemType ) ? ".mac" : "" ); //$NON-NLS-1$ //$NON-NLS-2$

        try {
            // :NOTE: Not all menu items have accelerators, so we have to check
            // first to see if one is present, to avoid unnecessary exceptions.
            // :NOTE: The "containsKey()" function isn't available on the Mac,
            // so we instead have to just ignore the missing resource exception.
            final String acceleratorText = resourceBundle
                    .getString( resourceKey );
            if ( acceleratorText != null ) {
                final KeyStroke acceleratorKeyStroke = KeyStroke
                        .getKeyStroke( acceleratorText );
                return acceleratorKeyStroke;
            }
        }
        catch ( final MissingResourceException mre ) {
            // mre.printStackTrace();
            return null;
        }

        return null;
    }

    // If an accelerator is assigned, set it by platform.
    public static void setMenuItemAccelerator(  final JCheckBoxMenuItem menuItem,
                                                final String menuName,
                                                final String itemName,
                                                final ResourceBundle resourceBundle,
                                                final SystemType systemType ) {
        // Fail-safe check to avoid unnecessary null pointer exceptions.
        if ( menuItem == null ) {
            return;
        }

        // If an accelerator is assigned, get it from a resource bundle.
        final KeyStroke acceleratorKeyStroke = getAcceleratorKeyStroke( menuName,
                                                                        itemName,
                                                                        resourceBundle,
                                                                        systemType );
        if ( acceleratorKeyStroke != null ) {
            menuItem.setAccelerator( acceleratorKeyStroke );
        }
    }

    // If an accelerator is assigned, set it by platform.
    public static void setMenuItemAccelerator(  final JMenuItem menuItem,
                                                final String menuName,
                                                final String itemName,
                                                final ResourceBundle resourceBundle,
                                                final SystemType systemType ) {
        // Fail-safe check to avoid unnecessary null pointer exceptions.
        if ( menuItem == null ) {
            return;
        }

        // If an accelerator is assigned, get it from a resource bundle.
        final KeyStroke acceleratorKeyStroke = getAcceleratorKeyStroke( menuName,
                                                                        itemName,
                                                                        resourceBundle,
                                                                        systemType );
        if ( acceleratorKeyStroke != null ) {
            menuItem.setAccelerator( acceleratorKeyStroke );
        }
    }

    // If an accelerator is assigned, set it by platform.
    public static void setMenuItemAccelerator(  final JRadioButtonMenuItem menuItem,
                                                final String menuName,
                                                final String itemName,
                                                final ResourceBundle resourceBundle,
                                                final SystemType systemType ) {
        // Fail-safe check to avoid unnecessary null pointer exceptions.
        if ( menuItem == null ) {
            return;
        }

        // If an accelerator is assigned, get it from a resource bundle.
        final KeyStroke acceleratorKeyStroke = getAcceleratorKeyStroke( menuName,
                                                                        itemName,
                                                                        resourceBundle,
                                                                        systemType );
        if ( acceleratorKeyStroke != null ) {
            menuItem.setAccelerator( acceleratorKeyStroke );
        }
    }

    // NOTE: The constructor is disabled, as this is a static class.
    private MenuUtilities() {}
}
