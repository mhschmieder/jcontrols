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

/**
 * {@code ComponentFactory} is a factory class for making customized Swing based
 * components, without having to unnecessarily derive subclasses from various
 * Swing classes or even from custom classes in this library, when it would add
 * no data or extra functionality and simply parameterizes to a purpose.
 *
 * @version 1.0
 *
 * @author Mark Schmieder
 */
public final class ControlFactory {

    /**
     * Default minimum percentile, for full range editing.
     */
    public static final int MINIMUM_PERCENTILE_DEFAULT = 0;

    /**
     * Default maximum percentile, for full range editing.
     */
    public static final int MAXIMUM_PERCENTILE_DEFAULT = 100;

    /**
     * The default constructor is disabled, as this is a static factory class.
     */
    private ControlFactory() {}

    ////////////////////// Slider pseudo-constructors ////////////////////////

    /**
     * Returns an {@code XSlider} paired to a {@link NumberEditor} and fully
     * initialized for default percentile bounds and tick spacing criteria.
     *
     * @param pairedNumberEditor
     *            The {@link NumberEditor} that will be paired with this slider
     * @param sliderOrientation
     *            The orientation of the slider; horizontal or vertical
     * @param initialPercentile
     *            The initial percentile to set this slider to
     * @return An {@code XSlider} paired to a {@link NumberEditor}
     *
     * @version 1.0
     */
    public static JxSlider makePercentSlider(final NumberEditor pairedNumberEditor,
                                             final int sliderOrientation,
                                             final int initialPercentile ) {
        return makePercentSlider( pairedNumberEditor,
                                  sliderOrientation,
                                  MINIMUM_PERCENTILE_DEFAULT,
                                  MAXIMUM_PERCENTILE_DEFAULT,
                                  initialPercentile );
    }

    /**
     * Returns an {@code XSlider} paired to a {@link NumberEditor} and fully
     * initialized for its percentile bounds and tick spacing criteria.
     *
     * @param pairedNumberEditor
     *            The {@link NumberEditor} that will be paired with this slider
     * @param sliderOrientation
     *            The orientation of the slider; horizontal or vertical
     * @param minimumPercentile
     *            The initial minimum percentile allowed for this slider
     * @param maximumPercentile
     *            The initial maximum percentile allowed for this slider
     * @param initialPercentile
     *            The initial percentile to set this slider to
     * @return An {@code XSlider} paired to a {@link NumberEditor}
     *
     * @version 1.0
     */
    public static JxSlider makePercentSlider(final NumberEditor pairedNumberEditor,
                                             final int sliderOrientation,
                                             final int minimumPercentile,
                                             final int maximumPercentile,
                                             final int initialPercentile ) {
        return new JxSlider( pairedNumberEditor,
                            sliderOrientation,
                            minimumPercentile,
                            maximumPercentile,
                            initialPercentile );
    }
}
