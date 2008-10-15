package gui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import java.awt.Dimension;

public class StatusBar extends JLabel {
  
    public StatusBar() {
        super();
        super.setPreferredSize(new Dimension(100, 16));

	setBorder(BorderFactory.createEtchedBorder());
        setMessage("Pronto");
    }
    
    public void setMessage(String messagem) {
        setText(" "+messagem);
    }        
}