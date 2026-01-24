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

import com.mhschmieder.jcontrols.icon.IconFactory;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.KeyStroke;
import java.util.Locale;
import java.util.ResourceBundle;

public class ButtonUtilities {
    
    /**
     * The mnemonic marker in our action properties files was chosen many years
     * ago and was set to match Qt and other GUI toolkit traditions in hopes
     * that property files could be shared across languages. Rather than edit
     * thousands of lines in properties files to match the enforced mnemonic 
     * for JavaFX, this one has been retained for Swing menus.
     */
    public static final char SWING_MNEMONIC_MARKER = '&';
    
    /**
     * JavaFX has an enforced mnemonic character, which is the underscore. As
     * it would be a Herculean effort to change existing action property files,
     * the older Swing mnemonic is retained, with a mapper method for JavaFX.
     */
    public static final char JAVAFX_MNEMONIC_MARKER = '_';

    public static final String getButtonDeselectedText( final String groupName,
                                                        final String itemName,
                                                        final ResourceBundle resourceBundle ) {
        //return getButtonText( groupName, itemName, false, resourceBundle );
        return getButtonText( groupName, itemName, resourceBundle );
    }

    // TODO: Review all calling hierarchies for how selected vs. deselected
    //  is handled, as well as examining the properties files to see if present.
    public static String getButtonLabel( final String groupName,
                                         final String itemName,
                                         //final boolean selected,
                                         final ResourceBundle resourceBundle ) {
        // There must always at least be a group name for each button.
        if ( ( groupName == null ) || groupName.trim().isEmpty() ) {
            return "";
        }

        // Composite the button name from the group and item names.
        final String buttonName = ( ( itemName == null ) || itemName.trim().isEmpty() )
            ? groupName
            : groupName + "." + itemName;

        // Generate the resource lookup key for the button label.
        final String resourceKey = buttonName + ".label";
        //+ ( selected ? ".selected" : "deselected" )
        
        try {
            return resourceBundle.getString( resourceKey );
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return '!' + buttonName + '!';
        }
    }

    public static final String getButtonSelectedText(   final String groupName,
                                                        final String itemName,
                                                        final ResourceBundle resourceBundle ) {
        //return getButtonText( groupName, itemName, true, resourceBundle );
        return getButtonText( groupName, itemName, resourceBundle );
    }

    // Get the button text from the resource bundle, if applicable.
    public static final String getButtonText(   final String groupName,
                                                final String itemName,
                                                //final boolean selected,
                                                final ResourceBundle resourceBundle ) {
        // Get the button label from the resource bundle, if applicable.
        final String buttonLabel = getButtonLabel(  groupName,
                                                    itemName,
                                                    //selected,
                                                    resourceBundle );
        if ( ( buttonLabel == null ) || buttonLabel.trim().isEmpty() ) {
            return null;
        }

        // Strip the mnemonic marker from the button label.
        return handleMnemonicMarker( buttonLabel, false );
    }

    @SuppressWarnings("nls")
    public static String getButtonToolTipText( final String groupName,
                                               final String itemName,
                                               final ResourceBundle resourceBundle ) {
        // There must always at least be a group name for each Button.
        if ( ( groupName == null ) || groupName.trim().isEmpty() ) {
            return null;
        }

        // Composite the button name from the group and item names.
        final String buttonName = ( ( itemName == null ) || itemName.trim().isEmpty() )
            ? groupName
            : groupName + "." + itemName;

        // Generate the resource lookup key for the Button Tool Tip.
        final String resourceKey = buttonName + ".toolTip";

        try {
            // NOTE: Not all actions have Tool Tips, so we have to check first
            // to see if one is present, to avoid unnecessary exceptions.
            if ( !resourceBundle.containsKey( resourceKey ) ) {
                return null;
            }
            return resourceBundle.getString( resourceKey );
        }
        catch ( final Exception e ) {
            // NOTE: It is OK to be missing a Tool Tip, but as we first check
            //  for a key entry, this exception indicates a structural problem
            //  that shouldn't be allowed to confuse the end users, but which
            //  might benefit the developers or indicate file corruption.
            //e.printStackTrace();
            return null;
        }
    }

    // NOTE: This function should only be used for Latin alphanumeric
    //  characters. See the Mnemonics.java example for a more complex and
    //  foolproof methodology for finding the key code for a mnemonic.
    public static final char getMnemonicChar( final String key ) {
        final int mnemonicMarkerIndex = getMnemonicMarkerIndex( key );
        final int mnemonicIndex = ( mnemonicMarkerIndex >= 0 )
            ? mnemonicMarkerIndex + 1
            : 0;
        //final char mnemonicLabel = key.toUpperCase( Locale.getDefault() )
        return key.toUpperCase( new Locale( "en", "US" ) ) //$NON-NLS-1$ //$NON-NLS-2$
                .charAt( mnemonicIndex );
    }

    // NOTE: This always returns zero, due to a quirk in how programmatic key
    //  code retrieval is not directly supported and thus results in an undefined
    //  key code. The solution is found in Mnemonics.java from the Java Source
    //  Code Warehouse project, using a properties file to map all the Latin
    //  characters to their respective key codes.
    public static final int getMnemonicKeyCode( final String key ) {
        final int mnemonicMarkerIndex = getMnemonicMarkerIndex( key );
        final int mnemonicIndex = ( mnemonicMarkerIndex >= 0 )
            ? mnemonicMarkerIndex + 1
            : 0;
        //final char mnemonicLabel = key.toUpperCase( Locale.getDefault() )
        final char mnemonicLabel = key.toUpperCase( new Locale( "en", "US" ) ) //$NON-NLS-1$ //$NON-NLS-2$
                .charAt( mnemonicIndex );
        final KeyStroke mnemonicKeyStroke = KeyStroke
                .getKeyStroke( mnemonicLabel );
        //final AWTKeyStroke mnemonicAWTKeyStroke = KeyStroke
        //.getAWTKeyStroke( mnemonicLabel );
        //return mnemonicAWTKeyStroke.getKeyCode();
        return mnemonicKeyStroke.getKeyCode();
    }

    /**
     * Return the index of the Swing mnemonic marker in the provided action label.
     * 
     * @param actionLabel The action label that contains a Swing mnemonic marker
     * @return the index of the Swing mnemonic marker in the provided action label
     */
    public static final int getMnemonicMarkerIndex( final String actionLabel ) {
        return actionLabel.indexOf( SWING_MNEMONIC_MARKER );
    }

    /**
     * Returns the provided action label with its Swing mnemonic marker either 
     * stripped or replaced with the JavaFX mnemonic marker.
     * 
     * @param actionLabel The action label whose Swing mnemonic marker should either
     *                    be stripped or replaced
     * @param replaceMnemonic {@code true} if the Swing mnemonic marker should be
     *                        replaced with the JavaFX mnemonic marker; {@code false}
     *                        if it should simply be stripped without replacement
     * @return the provided action label with its Swing mnemonic marker stripped
     */
    public static final String handleMnemonicMarker( final String actionLabel,
                                                     final boolean replaceMnemonic ) {
        final int mnemonicMarkerIndex = getMnemonicMarkerIndex( actionLabel );
 
        // NOTE: If no mnemonic marker is found, "-1" is returned, which is then
        //  incremented to use the first character as the mnemonic (by default).
        final int mnemonicIndex = ( mnemonicMarkerIndex >= 0 )
            ? mnemonicMarkerIndex + 1
            : 0;
        
        try {
            final String labelPreMnemonic = actionLabel
                    .substring( 0, mnemonicMarkerIndex );
            final String labelPostMnemonic = actionLabel.substring( mnemonicIndex );

            // Conditionally strip the Swing mnemonic marker from the label, or
            // replace the Swing mnemonic marker with the one for JavaFX.
            final StringBuilder adjustedLabel = new StringBuilder();
            adjustedLabel.append( labelPreMnemonic );
            if ( replaceMnemonic ) {
                adjustedLabel.append( JAVAFX_MNEMONIC_MARKER );
            }
            adjustedLabel.append( labelPostMnemonic );
            return adjustedLabel.toString();
        }
        catch ( final IndexOutOfBoundsException ioobe ) {
            ioobe.printStackTrace();
            return actionLabel;
        }
    }

    public static final boolean setButtonIcons( final AbstractButton button,
                                                final Icon enabledIcon,
                                                final Icon disabledIcon ) {
        // Fail-safe check to avoid unnecessary null pointer exceptions.
        if ( button == null ) {
            return false;
        }

        // Set the button icons to the supplied enabled icon reference.
        // NOTE: The same icon is generally used for all cases, with specific
        //  state being represented by rendering details, background and
        //  foreground color, etc. -- but disabled icons may be different.
        button.setIcon( enabledIcon );
        button.setDisabledIcon( disabledIcon );
        button.setPressedIcon( enabledIcon );
        button.setRolloverIcon( enabledIcon );
        button.setSelectedIcon( enabledIcon );

        // Inform the caller if the enabled icon reference is null.
        return enabledIcon != null;
    }

    // Set button icons from a JAR-resident resource, if applicable.
    public static final boolean setButtonIcons( final AbstractButton button,
                                                final String iconFilename ) {
        // Get button icon from a JAR-resident resource, if applicable.
        final Icon icon = IconFactory.makeImageIcon( iconFilename );

        // Try to set the button icons, and report whether they are null.
        return setButtonIcons( button, icon, icon );
    }

    // Set button icons from JAR-resident resources, if applicable.
    public static final boolean setButtonIcons( final AbstractButton button,
                                                final String enabledIconFilename,
                                                final String disabledIconFilename ) {
        // Get button icons from JAR-resident resources, if applicable.
        final Icon enabledIcon = IconFactory.makeImageIcon( enabledIconFilename );
        final Icon disabledIcon = IconFactory.makeImageIcon( disabledIconFilename );

        // Try to set the button icons, and report whether they are null.
        return setButtonIcons( button, enabledIcon, disabledIcon );
    }

    // Set all of the resources for a button (e.g. icon, text, mnemonic).
    public static final boolean setButtonResources( final AbstractButton button,
                                                    final String groupName,
                                                    final String itemName,
                                                    final Icon icon,
                                                    final ResourceBundle resourceBundle,
                                                    final boolean useMnemonic ) {
        // Set the button icons to the supplied enabled icon reference.
        setButtonIcons( button, icon, icon );

        // Set the button text and mnemonic from a resource bundle, if
        // applicable.
        return setButtonText(   button,
                                groupName,
                                itemName,
                                resourceBundle,
                                useMnemonic );
    }

    // Set all of the resources for a button (e.g. icon, text, mnemonic).
    public static final boolean setButtonResources( final AbstractButton button,
                                                    final String groupName,
                                                    final String itemName,
                                                    final String iconFilename,
                                                    final ResourceBundle resourceBundle,
                                                    final boolean useMnemonic ) {
        // Set the button icon from a JAR-resident resource, if applicable.
        setButtonIcons( button, iconFilename );

        // Set the button text and mnemonic from a resource bundle, if
        // applicable.
        return setButtonText(   button,
                                groupName,
                                itemName,
                                resourceBundle,
                                useMnemonic );

    }

    // Set all of the resources for a button (e.g. icon, text, mnemonic).
    public static final boolean setButtonResources( final AbstractButton button,
                                                    final String groupName,
                                                    final String itemName,
                                                    final String enabledIconFilename,
                                                    final String disabledIconFilename,
                                                    final ResourceBundle resourceBundle,
                                                    final boolean useMnemonic ) {
        // Set the button icons from JAR-resident resources, if applicable.
        setButtonIcons( button, enabledIconFilename, disabledIconFilename );

        // Set the button text and mnemonic from a resource bundle, if
        // applicable.
        return setButtonText(   button,
                                groupName,
                                itemName,
                                resourceBundle,
                                useMnemonic );

    }

    // Set button text and mnemonic from a resource bundle, if applicable.
    public static final boolean setButtonText(  final AbstractButton button,
                                                final String groupName,
                                                final String itemName,
                                                final ResourceBundle resourceBundle,
                                                final boolean useMnemonic ) {
        // Fail-safe check to avoid unnecessary null pointer exceptions.
        if ( button == null ) {
            return false;
        }

        // Get the button label from the resource bundle, if applicable.
        final String buttonLabel = getButtonLabel( 
                groupName, itemName, resourceBundle );
        if ( ( buttonLabel == null ) || buttonLabel.trim().isEmpty() ) {
            return false;
        }

        // Strip the mnemonic marker from the button label.
        final String buttonText = handleMnemonicMarker( buttonLabel, false );
        if ( ( buttonText == null ) || buttonText.trim().isEmpty() ) {
            return false;
        }

        // Set the standard button label, stripped of its mnemonic.
        button.setText( buttonText );

        // Set the button mnemonic.
        // TODO: Switch back to requesting the key code vs. the mnemonic
        //  character, once that function returns a proper key code vs. the
        //  undefined key code (zero).
        if ( useMnemonic ) {
            final char mnemonicCharacter = getMnemonicChar( buttonLabel );
            button.setMnemonic( mnemonicCharacter );

            // Try to set the button displayed mnemonic index.
            // NOTE: The mnemonic marker index on the original label
            //  corresponds to the mnemonic index on the stripped label.
            final int mnemonicMarkerIndex = getMnemonicMarkerIndex( buttonLabel );
            try {
                button.setDisplayedMnemonicIndex( mnemonicMarkerIndex );
            }
            catch ( final IllegalArgumentException iae ) {
                iae.printStackTrace();
                return false;
            }
        }

        return true;
    }

    // NOTE: The constructor is disabled, as this is a static class.
    private ButtonUtilities() {
    }
}
