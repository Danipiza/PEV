/* UNUSED


package Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BooleanSwitch extends JToggleButton {
    
	private static final long serialVersionUID = 1L;
	
	private boolean encendido;

    public BooleanSwitch() {
    	setPreferredSize(new Dimension(25, 25)); // Adjust size when switched on
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	encendido = isSelected();
                updateAppearance();
            }
        });
        updateAppearance();
    }

    private void updateAppearance() {
        if (encendido) setIcon(loadImage("resources/icons/Tick.png"));
        else setIcon(loadImage("resources/icons/Cross.png"));
        
        revalidate();
        repaint();
    }

    public boolean getValor() {
        return encendido;
    }
    
    protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}

    public void setSwitchedOn(boolean switchedOn) {
        this.encendido = switchedOn;
        setSelected(switchedOn);
        updateAppearance();
    }
}
*/