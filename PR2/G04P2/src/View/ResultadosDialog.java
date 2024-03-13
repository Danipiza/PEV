package View;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Model.FilaInd;
import Model.MejorIndividuo;



public class ResultadosDialog extends JDialog {

	private static final long serialVersionUID = 1L;	
		
	private Border border = BorderFactory.createLineBorder(Color.black, 3);
	
	private int num_pistas;
	private JPanel[] views;
	private DefaultTableModel[] models;
	
	
	public ResultadosDialog(Frame parent, int num_pistas) {
		super(parent, true);
		this.num_pistas=num_pistas;
		views=new JPanel[num_pistas];
		models=new DefaultTableModel[num_pistas];
		initGUI();		
	}
	
	
	private void initGUI() {
		
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);		
		
		JPanel viewsPanel = new JPanel(new GridLayout(1, 1));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		JPanel tablesPanel = new JPanel();		
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		int tam_dialog=200*num_pistas;
		
		
		// TABLES
		String[] nombre_cols = {"Vuelo", "Nombre", "TEL","TLA", "RET"};
		
		
		
		//DefaultTableModel model = new DefaultTableModel(nombre_cols, 0);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    
	    for(int i=0;i<num_pistas;i++) {
	    	models[i]= new DefaultTableModel(nombre_cols, 0);
	    	
	    	views[i]= createViewPanel(new JTable(models[i]),"Pista "+(i+1));
			views[i].setPreferredSize(new Dimension(200, 200));	
			tablesPanel.add(views[i]);
	    }
	        
		
		
        
        
		setPreferredSize(new Dimension(tam_dialog,tam_dialog));
		pack();
		setResizable(false);
		setVisible(false);
		
	}
	
	public void open(MejorIndividuo mejor_individuo) {
		setLocation(getParent().getLocation().x+600, getParent().getLocation().y + 180);

				
		for(int i=0;i<num_pistas;i++) {
			for(FilaInd fila: mejor_individuo.l[i]) {
				models[i].addRow(new Object[]{fila.vuelo+1, fila.Nombre, fila.TEL, fila.TLA, fila.RET});
			}			
		}
		
		setVisible(true);
	}
	
	
	protected ImageIcon loadImage(String path) {
		return new ImageIcon(Toolkit.getDefaultToolkit().createImage(path));
	}

	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout());
		
		// AÑADE UN BORDE
		p.setBorder(BorderFactory.createTitledBorder(border, title, TitledBorder.LEFT, TitledBorder.TOP));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		
		((JTable) c).setDefaultRenderer(Object.class, centerRenderer);
		p.add(new JScrollPane(c));
		return p;
	}

}
