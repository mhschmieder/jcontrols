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

import org.apache.commons.math3.util.FastMath;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * {@code NumberVerifier} is a simple {@link InputVerifier} implementation that
 * validates double-precision floating-point numbers from user input against a
 * known valid range.
 * <p>
 * It is not yet known whether this is still a useful class, as so many language
 * features were added since this code was last active in 2014 (at which point I
 * was mostly converted to JavaFX from Swing), and it uses ugly class casting so
 * there are a lot of unenforced usage context assumptions and restrictions.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class NumberVerifier extends InputVerifier implements ActionListener {

    /**
     * The minimum double-precision floating-point value allowed for this range.
     */
    protected double minimum;

    /**
     * The maximum double-precision floating-point value allowed for this range.
     */
    protected double maximum;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs a {@code NumberVerifier} set to the given valid range.
     *
     * @param minimumValue
     *            The initial minimum value allowed for this verifier
     * @param maximumValue
     *            The initial maximum value allowed for this verifier
     *
     * @version 1.0
     */
    public NumberVerifier( final double minimumValue,
                           final double maximumValue ) {
        // No need to call the superclass as it's abstract and has no
        // constructor or data fields to set.
        minimum = minimumValue;
        maximum = maximumValue;
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Sets the valid double-precision floating-point range for this verifier.
     *
     * @param minimumValue
     *            The new minimum value allowed for this verifier
     * @param maximumValue
     *            The new maximum value allowed for this verifier
     *
     * @version 1.0
     */
    public void setNumberRange( final double minimumValue,
                                final double maximumValue ) {
        setMinimum( minimumValue );
        setMaximum( maximumValue );
    }

    /**
     * Sets the minimum double-precision floating-point value allowed.
     *
     * @param minimumValue
     *            The new minimum value allowed for this verifier
     *
     * @version 1.0
     */
    public void setMinimum( final double minimumValue ) {
        minimum = minimumValue;
    }

    /**
     * Sets the maximum double-precision floating-point value allowed.
     *
     * @param maximumValue
     *            The new maximum value allowed for this verifier
     *
     * @version 1.0
     */
    public void setMaximum( final double maximumValue ) {
        maximum = maximumValue;
    }

    ///////////////// Specialized methods for private data ///////////////////

    /**
     * Returns the normalized value for the given value within the valid range.
     *
     * @param value
     *            The original value to test and correct against valid range
     * @return The normalized value for the given value within the valid range
     *
     * @version 1.0
     */
    public double getNormalizedValue( final double value ) {
        return ( value < minimum )
                ? minimum
                : FastMath.min( value, maximum );
    }

    /**
     * Checks whether the component's input is valid. This method has the side
     * effect of correcting the component's input to sit within the valid number
     * range. It returns a boolean indicating the status of the argument's
     * input.
     *
     * @param source
     *            The {@link JComponent} to verify
     * @return {@code true} if valid; {@code false} if invalid
     * @see JComponent#setInputVerifier
     * @see JComponent#getInputVerifier
     *
     * @version 1.0
     */
    public boolean verifyAndNormalize( final JComponent source ) {
        final boolean validInput = verify( source );
        if ( validInput ) {
            return true;
        }

        // Normalize the user data to be within range.
        final NumberEditor numberEditor = ( NumberEditor ) source;
        double value = numberEditor.getValue();
        value = getNormalizedValue( value );

        // Reformat the user data to match number formatting rules and locale.
        numberEditor.setValue( value );
        numberEditor.selectAll();

        return false;
    }

    ///////////////// ActionListener implementation methods //////////////////

    /**
     * Invoked when an action occurs.
     *
     * @param actionEvent
     *            The action event with details about the action that occurred
     *
     * @version 1.0
     */
    @Override
    public final void actionPerformed( final ActionEvent actionEvent ) {
        final NumberEditor numberEditor = ( NumberEditor ) actionEvent.getSource();

        // Ignore the return value of the focus change to the event source.
        shouldYieldFocus( numberEditor );
    }

    /////////////////// InputVerifier method overloads ///////////////////////

    /**
     * Checks whether the component's input is valid. This method should have no
     * side effects. It returns a boolean indicating the status of the
     * argument's input.
     *
     * @param source
     *            The {@link JComponent} to verify
     * @return {@code true} if valid; {@code false} if invalid
     * @see JComponent#setInputVerifier
     * @see JComponent#getInputVerifier
     *
     * @version 1.0
     */
    @Override
    public boolean verify( final JComponent source ) {
        final NumberEditor numberEditor = ( NumberEditor ) source;
        final double value = numberEditor.getValue();
        final boolean validInput = ( ( value >= minimum ) && ( value <= maximum ) );

        return validInput;
    }

}
