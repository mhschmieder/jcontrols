/*
 * MIT License
 *
 * Copyright (c) 2023, 2026 Mark Schmieder. All rights reserved.
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

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TabButton extends JButton implements ActionListener {
    /**
     * Unique Serial Version ID for this class, to avoid class loader conflicts.
     * <p>
     * TODO: Regenerate this once the Eclipse bug is fixed that treats unique and
     *  default Serial Version ID's exactly the same (not the case previously!).
     */
    private static final long serialVersionUID = 1L;
    
    private final static MouseListener buttonMouseListener = new MouseAdapter() {
        @Override 
        public void mouseEntered( final MouseEvent e ) {
            Component component = e.getComponent();
            if ( component instanceof AbstractButton ) {
                AbstractButton button = ( AbstractButton ) component;
                button.setBorderPainted( true );
            }
        }

        @Override 
        public void mouseExited( final MouseEvent e ) {
            Component component = e.getComponent();
            if ( component instanceof AbstractButton ) {
                AbstractButton button = ( AbstractButton ) component;
                button.setBorderPainted( false );
            }
        }
    };

    
    // The TabbedPane that owns this custom TabButton.
    private final JTabbedPane tabbedPane;
    
    // The Component that owns this custom TabButton.
    private final Component parent;

    /**
     * Create a TabButton instance, using the supplied arguments.
     * 
     * @param pTabbedPane The Tabbed Pane that owns this custom Tab Button
     * @param pParent The Button Tab Component that owns this Tab Button
     */
    public TabButton( final JTabbedPane pTabbedPane,
                      final Component pParent ) {
        // Always call the superclass constructor first!
        super();
        
        tabbedPane = pTabbedPane;
        parent = pParent;
        
        int size = 17;
        setPreferredSize( new Dimension( size, size ) );
        
        setToolTipText( "Close This Tab" );
        
        // Make the button looks the same for all LAF's.
        setUI( new BasicButtonUI() );
        
        // Make it transparent.
        setContentAreaFilled( false );
        
        // No need to be focusable.
        setFocusable( false );
        setBorder( BorderFactory.createEtchedBorder() );
        setBorderPainted( false );
        
        // Make a roll-over effect (we use the same listener for all buttons).
        addMouseListener(buttonMouseListener);
        setRolloverEnabled( true );
        
        // Close the proper tab by clicking the "x" button.
        addActionListener( this );
    }

    @Override 
    public void actionPerformed( final ActionEvent e ) {
        int tabIndex = tabbedPane.indexOfTabComponent( parent );
        if ( tabIndex != -1 ) {
            tabbedPane.remove( tabIndex);
        }
    }

    //we don't want to update UI for this button
    @Override 
    public void updateUI() {
    }

    //paint the cross
    @Override 
    protected void paintComponent( final Graphics g ) {
        super.paintComponent( g );
        
        final Graphics2D g2 = ( Graphics2D ) g.create();
        
        // Shift the image for pressed buttons.
        if ( getModel().isPressed() ) {
            g2.translate( 1, 1 );
        }
        
        g2.setStroke( new BasicStroke( 2 ) );
        
        final Color color = getModel().isRollover() 
                ? Color.MAGENTA 
                : Color.BLACK;
        g2.setColor( color );
        
        // Draw the "x" in the upper right corner of the tab.
        int delta = 6;
        g2.drawLine( delta, delta, getWidth() - delta - 1, getHeight() - delta - 1 );
        g2.drawLine( getWidth() - delta - 1, delta, delta, getHeight() - delta - 1 );

        g2.dispose();
    }
}
