/**
 * @(#)StatusBar.java
 * @author Danilo Lutz
 * @version 1.00 
 * @date 17/10/2008
 **/
/*
    This file is part of Batalha Naval.

    Batalha Naval is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Batalha Naval is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Batalha Naval.  If not, see <http://www.gnu.org/licenses/>.
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