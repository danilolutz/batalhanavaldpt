/**
 * @(#)StatusBar.java
 *
 *
 * @Danilo Lutz 
 * @version 1.00 14/10/2008
 */
package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.awt.Dimension;

public class StatusBar extends JLabel {
  
    public StatusBar() {
        super();
        super.setPreferredSize(new Dimension(100, 20));

		this.setBorder(BorderFactory.createEtchedBorder());
        this.setMessage("");
    }
    
    public void setMessage(String message) {
    	this.setText("");
        this.setText(" " + message);
    }        
}