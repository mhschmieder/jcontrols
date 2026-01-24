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

/**
 * {@code AngleVerifier} is a specialization of {@link NumberVerifier} whose
 * main purpose is to take care of special behavior for angles, such as wrapping
 * at 360 degrees. This helps avoid false positives when checking for invalid
 * data, and also affects the data normalization tactics.
 * <p>
 * Note that this class does not override the
 * {@link NumberVerifier#verify(javax.swing.JComponent)} method, so the basic
 * verification does not account for unwrapping the period. The specialized
 * method that first verifies the absolute range and then normalizes the value
 * does do so though. This is generally the best method for clients to invoke.
 * <p>
 * As with its parent class, this verifier's utility within modern Java, even
 * when working with Swing components, is in question, due to the same caveats.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class AngleVerifier extends NumberVerifier {

    /**
     * The value range (in degrees, not radians) for allowed angles. Not every
     * context allows a full 360 degrees rotation; some allow less, but others
     * (especially in math and science applications) may care about period.
     */
    protected double angleRange;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs an {@code AngleVerifier} set to the given valid range.
     *
     * @param minimumAngle
     *            The initial minimum angle (degrees) allowed for this verifier
     * @param maximumAngle
     *            The initial maximum angle (degrees) allowed for this verifier
     *
     * @version 1.0
     */
    public AngleVerifier( final double minimumAngle, final double maximumAngle ) {
        // Always call the superclass constructor first!
        super( minimumAngle, maximumAngle );

        angleRange = maximumAngle - minimumAngle;
    }

    /////////////////// NumberVerifier method overloads //////////////////////

    /**
     * Returns the normalized value for the given value within the valid range.
     *
     * @param value
     *            The original value to test and correct against valid range
     * @return The normalized value for the given value within the valid range
     *
     * @version 1.0
     */
    @Override
    public final double getNormalizedValue( final double value ) {
        // It is much more reliable to first get the angle to within the range
        // of a full 360 degree cycle, and only then limit it further if the
        // pre-computed allowed range is less.
        //
        // There is a more robust version of angle unwrapping in the math
        // toolkit, so this code should be replaced by a call to that method
        // once that toolkit is published to GitHub and is accessible from here.
        double unwrappedValue = value;
        while ( unwrappedValue < -360d ) {
            unwrappedValue += 360d;
        }
        while ( unwrappedValue >= 360d ) {
            unwrappedValue -= 360d;
        }

        final double normalizedValue = ( unwrappedValue < minimum )
            ? ( angleRange >= 360d ) ? unwrappedValue + 360d : minimum
            : ( unwrappedValue > maximum )
                ? ( angleRange >= 360d ) ? unwrappedValue - 360d : maximum
                : unwrappedValue;

        return normalizedValue;
    }
}
