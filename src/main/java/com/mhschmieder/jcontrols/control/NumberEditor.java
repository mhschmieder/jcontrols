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

import javax.swing.JTextField;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * {@code NumberEditor} is a specialization of {@link JTextField} that takes
 * care of some initialization issues in that class as well as providing for
 * some number formating specifics.
 * <p>
 * This class assumes that all numbers are displayed using double-precision
 * floating-point, but the Number Formatter can still be customized.
 * <p>
 * This is a very old custom class from Java 1.3 days, and I never got around to
 * comparing thoroughly to {@code JFormattedTextField} added in Java 1.4.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class NumberEditor extends JTextField {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID          = -5109248965458722439L;

    /**
     * The default number of columns to use for initial sizing constraints.
     */
    private static final int  DEFAULT_NUMBER_OF_COLUMNS = 10;

    /**
     * Returns a copy of the original numeric string, switched to negative
     * magnitude if the positive sign is not present.
     * <p>
     * This method takes a string that is known to represent a number, and
     * defaults it to negative. This means checking for a qualified "+", without
     * which the number is assumed negative. Numbers qualified with a "+" are
     * pass-through; whereas other numbers insert a "-" sign (if not already
     * present) to negate the number. Numbers already qualified with a "-" are
     * also pass-through. This method does not deal with "negative-zero".
     * <p>
     * It might be best to re-implement this using Regular Expressions, which
     * were added to the language long after this method was written.
     * <p>
     * This method is borrowed from a more general utility toolkit that has not
     * yet been published, so is a temporary copy/paste placeholder and will be
     * removed once the basic commons toolkits are published.
     *
     * @param numericString
     *            The numeric string to search for a missing positive sign
     * @return The numeric string switched to negative magnitude if the positive
     *         sign is not present
     */
    public static final String defaultToNegativeNumber( final String numericString ) {
        // Effectively treat this as a no-op if we have a null or empty string.
        if ( ( numericString == null ) || numericString.trim().isEmpty() ) {
            return numericString;
        }

        final StringBuilder defaultToNegativeNumberStringBuilder =
                                                                 new StringBuilder( numericString );

        // We should never get an exception here as we pre-check for null or
        // empty strings, so the try-catch-throw block is a fail-safe for
        // future-proof code.
        try {
            final char[] chars = new char[ 1 ];
            defaultToNegativeNumberStringBuilder.getChars( 0, 1, chars, 0 );
            if ( chars[ 0 ] == '+' ) {
                defaultToNegativeNumberStringBuilder.deleteCharAt( 0 );
            }
            else if ( chars[ 0 ] != '-' ) {
                defaultToNegativeNumberStringBuilder.insert( 0, '-' );
            }
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return numericString;
        }

        final String defaultToNegativeNumberString =
                                                   defaultToNegativeNumberStringBuilder.toString();

        return defaultToNegativeNumberString;
    }

    /**
     * The default value to use when a double-click erases the current value.
     */
    protected double       defaultValue;

    /**
     * Number format cache used for locale-specific number formatting.
     */
    protected NumberFormat numberFormat;

    /**
     * Number format cache used for locale-specific number parsing.
     */
    protected NumberFormat numberParse;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs an initially blank Text Field set to the level of numeric
     * precision specified by the caller.
     *
     * @param minimumFractionDigitsFormat
     *            The minimum number of fraction digits to be shown
     * @param maximumFractionDigitsFormat
     *            The maximum number of fraction digits to be shown
     * @param minimumFractionDigitsParse
     *            The minimum number of fraction digits to parse
     * @param maximumFractionDigitsParse
     *            The maximum number of fraction digits to parse
     *
     * @version 1.0
     */
    public NumberEditor( final int minimumFractionDigitsFormat,
                         final int maximumFractionDigitsFormat,
                         final int minimumFractionDigitsParse,
                         final int maximumFractionDigitsParse ) {
        // Always call the superclass constructor first!
        super( DEFAULT_NUMBER_OF_COLUMNS );

        try {
            initTextField( minimumFractionDigitsFormat,
                           maximumFractionDigitsFormat,
                           minimumFractionDigitsParse,
                           maximumFractionDigitsParse );
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    /////////////////////// Initialization methods ///////////////////////////

    /**
     * Initializes this cell renderer in an encapsulated way that protects all
     * constructors from run-time exceptions that might prevent instantiation.
     * <p>
     * The method is declared final, as any derived classes should avoid
     * unwanted side effects and simply write their own GUI initialization
     * method that adds any extended behaviour or components to the layout.
     *
     * @param minimumFractionDigitsFormat
     *            The minimum number of fraction digits to be shown
     * @param maximumFractionDigitsFormat
     *            The maximum number of fraction digits to be shown
     * @param minimumFractionDigitsParse
     *            The minimum number of fraction digits to parse
     * @param maximumFractionDigitsParse
     *            The maximum number of fraction digits to parse
     *
     * @version 1.0
     */
    @SuppressWarnings("nls")
    private final void initTextField( final int minimumFractionDigitsFormat,
                                      final int maximumFractionDigitsFormat,
                                      final int minimumFractionDigitsParse,
                                      final int maximumFractionDigitsParse ) {
        // This column initialization hack is necessary to secure the desired
        // size for the display area of the Text Field.
        setColumns( 0 );
        setText( "1234567890" );
        if ( isPreferredSizeSet() ) {
            setPreferredSize( getPreferredSize() );
        }

        // Initialize the Number Formatters, using the host's default Locale.
        initNumberFormatters( Locale.getDefault(),
                              minimumFractionDigitsFormat,
                              maximumFractionDigitsFormat,
                              minimumFractionDigitsParse,
                              maximumFractionDigitsParse );

        // Make sure there is an initial numeric value, to prevent exceptions.
        setText( "0.0" );
    }

    /**
     * Initializes and caches the number formatters, as the system-level query
     * for the host's default locale is expensive and can cause a freeze-up.
     * <p>
     * The method is declared final, as any derived classes should avoid
     * unwanted side effects and simply write their own number formatter
     * initialization method that resets precision to what is preferred in the
     * context of that tighter scope. These defaults are fairly common ones.
     *
     * @param locale
     *            The locale to use for number formatting purposes
     * @param minimumFractionDigitsFormat
     *            The minimum number of fraction digits to be shown
     * @param maximumFractionDigitsFormat
     *            The maximum number of fraction digits to be shown
     * @param minimumFractionDigitsParse
     *            The minimum number of fraction digits to parse
     * @param maximumFractionDigitsParse
     *            The maximum number of fraction digits to parse
     *
     * @version 1.0
     */
    private final void initNumberFormatters( final Locale locale,
                                             final int minimumFractionDigitsFormat,
                                             final int maximumFractionDigitsFormat,
                                             final int minimumFractionDigitsParse,
                                             final int maximumFractionDigitsParse ) {
        // Cache the number formats so that we don't have to get information
        // about locale from the OS each time we format a number.
        numberFormat = NumberFormat.getNumberInstance( locale );
        numberParse = ( NumberFormat ) numberFormat.clone();

        // Set the precision for floating-point text formatting.
        numberFormat.setMinimumFractionDigits( minimumFractionDigitsFormat );
        numberFormat.setMaximumFractionDigits( maximumFractionDigitsFormat );

        // Set the precision for floating-point text parsing.
        numberParse.setMinimumFractionDigits( minimumFractionDigitsParse );
        numberParse.setMaximumFractionDigits( maximumFractionDigitsParse );
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Sets the new default value to use when double-clicking erases a cell.
     *
     * @param newDefaultValue
     *            The new default value to use when double-clicking erases a
     *            cell
     *
     * @version 1.0
     */
    public final void setDefaultValue( final double newDefaultValue ) {
        defaultValue = newDefaultValue;
    }

    ///////////////// Specialized methods for private data ///////////////////

    /**
     * Sets the current value back to the default value.
     *
     * @version 1.0
     */
    public final void reset() {
        setValue( defaultValue );
    }

    /**
     * Returns the current text value, converted to a double-precision number.
     *
     * @return The current text value, converted to a double-precision number,
     *         or zero if invalid or otherwise unable to parse
     *
     * @version 1.0
     */
    public double getValue() {
        try {
            final String text = getText();
            return ( numberParse.parse( text ) ).doubleValue();
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    /**
     * Sets the current text value, converted from a double-precision number.
     *
     * @param value
     *            The double-precision number to convert to a text value
     *
     * @version 1.0
     */
    public void setValue( final double value ) {
        setText( numberFormat.format( value ) );
    }

    /**
     * Returns the current text value, converted to a double-precision number,
     * but switched to negative magnitude if the positive sign is not present in
     * the displayed text.
     * <p>
     * This is a specialized function that must be invoked directly by the
     * client strictly in contexts where this is the desired behavior.
     * <p>
     * As with {@code NumberCellRenderer}, it might be better to set a
     * flag for when to do this specialized parsing, and make that part of the
     * regular {@link #getValue} method.
     *
     * @return The current text value, converted to a double-precision number,
     *         or zero if invalid or otherwise unable to parse
     *
     * @version 1.0
     */
    public double getPositiveQualifiedValue() {
        try {
            final String text = getText();
            final String revisedText = defaultToNegativeNumber( text );
            return ( numberParse.parse( revisedText ) ).doubleValue();
        }
        catch ( final Exception e ) {
            e.printStackTrace();
            return 0.0d;
        }
    }

    /**
     * Sets the current text value, converted from a double-precision number,
     * but prepended with the positive sign if the number is zero or positive.
     * <p>
     * This is a specialized function that must be invoked directly by the
     * client strictly in contexts where this is the desired behavior.
     * <p>
     * As with {@code NumberCellRenderer}, it might be better to set a
     * flag for when to do this specialized parsing, and make that part of the
     * regular {@link #getValue} method.
     *
     * @param value
     *            The double-precision number to convert to a text value
     *
     * @version 1.0
     */
    @SuppressWarnings("nls")
    public void setPositiveQualifiedValue( final double value ) {
        if ( value > 0.0d ) {
            final StringBuffer positiveQualifiedNumber = numberFormat
                    .format( value,
                             new StringBuffer( "+" ),
                             new FieldPosition( NumberFormat.INTEGER_FIELD ) );
            setText( positiveQualifiedNumber.toString() );
        }
        else {
            setValue( value );
        }
    }

}
