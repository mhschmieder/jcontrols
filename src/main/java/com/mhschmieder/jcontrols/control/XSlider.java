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

import com.mhschmieder.jcontrols.util.ForegroundManager;
import com.mhschmieder.jgraphics.color.ColorUtilities;
import org.apache.commons.math3.util.FastMath;

import javax.swing.BorderFactory;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

/**
 * {@code XSlider} is an enhancement of the regular {@link JSlider} to formalize
 * much of the parameterization that would otherwise be needed as boilerplate
 * code anywhere a slider is in use. This way, we avoid copy/paste divergence.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public class XSlider extends JSlider implements ForegroundManager {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     */
    private static final long serialVersionUID = 6962976993029100166L;

    /**
     * The {@link NumberEditor} whose values must stay in sync with this slider.
     */
    protected NumberEditor    numberEditor;

    /**
     * The default value to set the slider to, when the mouse is double-clicked.
     */
    protected double          defaultValue;

    //////////////////////////// Constructors ////////////////////////////////

    /**
     * Constructs an {@code XSlider} paired to a {@link NumberEditor} and fully
     * initialized for its numeric bounds and tick spacing criteria.
     *
     * @param pairedNumberEditor
     *            The {@link NumberEditor} that will be paired with this slider
     * @param sliderOrientation
     *            The orientation of the slider; horizontal or vertical
     * @param minimumValue
     *            The initial minimum value allowed for this slider
     * @param maximumValue
     *            The initial maximum value allowed for this slider
     * @param initialValue
     *            The initial value to set this slider to
     *
     * @version 1.0
     */
    public XSlider( final NumberEditor pairedNumberEditor,
                    final int sliderOrientation,
                    final int minimumValue,
                    final int maximumValue,
                    final int initialValue ) {
        // Always call the superclass constructor first!
        super( sliderOrientation, minimumValue, maximumValue, initialValue );

        numberEditor = pairedNumberEditor;

        defaultValue = initialValue;

        // Avoid constructor failure by wrapping the layout initialization in an
        // exception handler that logs the exception and then returns an object.
        try {
            initSlider( minimumValue, maximumValue );
        }
        catch ( final Exception ex ) {
            ex.printStackTrace();
        }
    }

    /////////////////////// Initialization methods ///////////////////////////

    /**
     * Initializes this slider in an encapsulated way that protects all
     * constructors from run-time exceptions that might prevent instantiation.
     * <p>
     * The method is declared final, as any derived classes should avoid
     * unwanted side effects and simply write their own GUI initialization
     * method that adds any extended behaviour or components to the layout.
     *
     * @param minimumValue
     *            The initial minimum value allowed for this slider
     * @param maximumValue
     *            The initial maximum value allowed for this slider
     *
     * @version 1.0
     */
    @SuppressWarnings("nls")
    private final void initSlider( final int minimumValue, final int maximumValue ) {
        // Layout the slider for proper labeling, tick marks, etc.
        //
        // The "filled" property only seems to work with Metal look-and-feel.
        setTickSpacing( minimumValue, maximumValue );
        setSnapToTicks( false );
        putClientProperty( "JSlider.isFilled", Boolean.TRUE );

        Border border = null;
        final int sliderOrientation = getOrientation();
        switch ( sliderOrientation ) {
        case SwingConstants.HORIZONTAL:
            border = BorderFactory.createEmptyBorder( 0, 10, 0, 0 );
            break;
        case SwingConstants.VERTICAL:
            border = BorderFactory.createEmptyBorder( 3, 6, 3, 6 );
            break;
        default:
            // For something this minor, there is no sense in punishing
            // incorrect client code. It is unfortunate that the Swing Constants
            // were added before enumerations were available in Java.
            border = BorderFactory.createEmptyBorder();
            break;
        }
        if ( border != null ) {
            setBorder( border );
        }

        // This callback causes double clicks to return to the default value.
        //
        // To preserve floating-point precision, it is important to set the
        // Slider value before the paired Text Field value.
        addMouseListener( new MouseAdapter() {
            @Override
            public void mouseClicked( final MouseEvent me ) {
                if ( me.getClickCount() == 2 ) {
                    setValue( ( int ) FastMath.round( defaultValue ) );
                    numberEditor.reset();
                }
            }
        } );
    }

    ////////////////// Accessor methods for private data /////////////////////

    /**
     * Sets the new default value to use when double-clicking the slider.
     *
     * @param newDefaultValue
     *            The new default value to use when double-clicking the slider
     *
     * @version 1.0
     */
    protected final void setDefaultValue( final double newDefaultValue ) {
        defaultValue = newDefaultValue;
    }

    ////////////////////// Slider manipulation methods ///////////////////////

    /**
     * Sets new tick spacing values using double-precision floating-point
     * numbers so that the integer conversion strategy consolidated and
     * consistent across an application.
     *
     * @param minimumValue
     *            The minimum value to use for determining tick spacing
     * @param maximumValue
     *            The maximum value to use for determining tick spacing
     *
     * @version 1.0
     */
    public final void setTickSpacing( final double minimumValue, final double maximumValue ) {
        setTickSpacing( ( int ) FastMath.round( minimumValue ), ( int ) FastMath.round( maximumValue ) );
    }

    /**
     * Sets new tick spacing values using integer numbers, which are the only
     * supported resolution of the {@link JSlider} class.
     *
     * @param minimumValue
     *            The minimum value to use for determining tick spacing
     * @param maximumValue
     *            The maximum value to use for determining tick spacing
     *
     * @version 1.0
     */
    public void setTickSpacing( final int minimumValue, final int maximumValue ) {
        // We should eventually re-code this to re-enable custom tick spacing
        // based on the actual value range.
        // final int valueRange = maximumValue - minimumValue;
        // final int majorTickSpacingCandidate = valueRange / 5;
        final int majorTickSpacingCandidate = 10;
        final int minorTickSpacingCandidate = majorTickSpacingCandidate / 5;
        setTickSpacing( majorTickSpacingCandidate, minorTickSpacingCandidate, true );
    }

    /**
     * Sets new values for the major and minor tick spacing, and conditionally
     * regenerates the tick labels afterwards.
     *
     * @param majorTickSpacingCandidate
     *            The preferred major tick spacing to use for this slider
     * @param minorTickSpacingCandidate
     *            The preferred minor tick spacing to use for this slider
     * @param regenerateTickLabels
     *            If {@code true}, regenerate the tick labels after changing the
     *            tick spacing
     *
     * @version 1.0
     */
    public final void setTickSpacing( final int majorTickSpacingCandidate,
                                      final int minorTickSpacingCandidate,
                                      final boolean regenerateTickLabels ) {
        // We have to turn off tick labels until after setting the new tick
        // spacing, as the automated labeling doesn't re-scale properly after
        // it's set for the first time, if the range changes later on.
        setPaintTicks( false );
        setPaintLabels( false );

        setMajorTickSpacing( majorTickSpacingCandidate );
        setMinorTickSpacing( minorTickSpacingCandidate );

        if ( regenerateTickLabels ) {
            final Hashtable< ?, ? > labelTable = createStandardLabels( majorTickSpacingCandidate,
                                                                       getMinimum() );
            setLabelTable( labelTable );
        }

        setPaintTicks( true );
        setPaintLabels( true );
    }

    /////////////// ForegroundManager implementation methods /////////////////

    /**
     * Sets the appropriate foreground color for this slider based on the
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
     *            The current background color to apply to this slider
     *
     * @since 1.0
     */
    @Override
    public void setForegroundFromBackground( final Color backColor ) {
        // Make sure the foreground color is never masked by the background.
        final Color foreColor = ColorUtilities.getForegroundFromBackground( backColor );

        // This takes care of the unused parts of the layout, to avoid gaps of
        // the wrong background color between layout regions for subcomponents.
        setBackground( backColor );
        setForeground( foreColor );
    }

    ////////////////////// JSlider method overloads //////////////////////////

    /**
     * Sets the new minimum value allowed for this slider, overloading the
     * parent class method so that double-precision floating-point numbers can
     * have their integer conversion strategy consolidated and consistent across
     * an application.
     *
     * @param minimumValue
     *            The new minimum value allowed for this slider
     *
     * @version 1.0
     */
    public final void setMinimum( final double minimumValue ) {
        setMinimum( ( int ) FastMath.round( minimumValue ) );
    }

    /**
     * Sets the new maximum value allowed for this slider, overloading the
     * parent class method so that double-precision floating-point numbers can
     * have their integer conversion strategy consolidated and consistent across
     * an application.
     *
     * @param maximumValue
     *            The new maximum value allowed for this slider
     *
     * @version 1.0
     */
    public final void setMaximum( final double maximumValue ) {
        setMaximum( ( int ) FastMath.round( maximumValue ) );
    }

}
