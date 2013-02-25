package org.hkfree.ospf.gui.lltddialog;

import java.awt.Container;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.hkfree.ospf.gui.ospfwin.OspfWinManager;
import org.hkfree.ospf.model.lltd.LLTDModel;
import org.hkfree.ospf.tools.Factory;

/**
 * Dialog zobrazující přehled načtených LLTD modelu, umožňuje nahrát modely a zařadit je do OSPF modelů.
 * Je možné zobrazit mapy modelů.
 * @author Jan Schovánek
 */
public class LLTDSummaryDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private ResourceBundle rb = Factory.getRb();
    private OspfWinManager manager = null;
    private JButton btnLoadData = new JButton();
    private JButton btnAddToModels = new JButton();
    private List<LLTDModel> lltdModels = null;
    private JTable table = null;


    public LLTDSummaryDialog(Frame frame, OspfWinManager ospfWinManager, List<LLTDModel> lltdModels) {
	super(frame);
	manager = ospfWinManager;
	this.lltdModels = lltdModels;
	createGUI();
	actualizeTable();
	this.setTitle(rb.getString("lltdsd.title"));
	this.pack();
	this.setModal(false);
	this.setLocationRelativeTo(null);
	this.setResizable(true);
    }


    /**
     * Vytvoření GUI
     */
    private void createGUI() {
	// tlacitko pro nacteni dat
	btnLoadData.setText(rb.getString("lltdsd.0"));
	btnLoadData.addActionListener(this);
	// tlacitko pro zaraznei LLTD modelu do OSPF modlu
	btnAddToModels.setText(rb.getString("lltdsd.1"));
	btnAddToModels.addActionListener(this);
	// tabulka obsahujici nactene LLTD modely
	table = new JTable();
	table.setFillsViewportHeight(true);
	table.setAutoCreateRowSorter(true);
	JScrollPane scrollPane = new JScrollPane(table);
	// pridani komponent do dialogu
	Container c = this.getContentPane();
	GroupLayout layout = new GroupLayout(c);
	c.setLayout(layout);
	layout.setAutoCreateContainerGaps(true);
	layout.setAutoCreateGaps(true);
	layout.setHorizontalGroup(layout.createSequentialGroup()
	        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
	                .addComponent(scrollPane)
	                .addGroup(layout.createSequentialGroup()
	                        .addComponent(btnLoadData)
	                        .addComponent(btnAddToModels))));
	layout.setVerticalGroup(layout.createSequentialGroup()
	        .addComponent(scrollPane)
	        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                .addComponent(btnLoadData)
	                .addComponent(btnAddToModels)));
    }


    private void actualizeTable() {
	String[] columnNames = { rb.getString("lltdsd.2"), rb.getString("lltdsd.3"), "" };
	Object data[][] = new Object[lltdModels.size()][columnNames.length];
	int i = 0;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	for (LLTDModel m : lltdModels) {
	    data[i][0] = m.getPublicIP();
	    data[i][1] = df.format(m.getDate());
	    data[i][2] = rb.getString("lltdsd.4");
	    i++;
	}
	TableModel model = new DefaultTableModel(data, columnNames);
	table.setModel(model);
	// nastaveni sloupce jako tlacitko pro zobrazeni LLTD mapy
	Action showLLTDMap = new AbstractAction()
	{

	    private static final long serialVersionUID = 1L;


	    public void actionPerformed(ActionEvent e)
	    {
		JTable table = (JTable) e.getSource();
		int modelRow = Integer.valueOf(e.getActionCommand());
		LLTDDialog dialog = new LLTDDialog(
		        getModelByPublicIP((String) ((DefaultTableModel) table.getModel()).getValueAt(modelRow, 0)));
		dialog.setVisible(true);
	    }
	};
	new ButtonColumn(table, showLLTDMap, 2);
    }


    /**
     * Vrací LLTD model dle publicIP
     * @param name
     * @return
     */
    private LLTDModel getModelByPublicIP(String publicIP) {
	for (LLTDModel model : lltdModels) {
	    if (model.getPublicIP().equals(publicIP)) {
		return model;
	    }
	}
	return null;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
	if (e.getSource() == btnLoadData) {
	    manager.loadLLTDData();
	    actualizeTable();
	} else if (e.getSource() == btnAddToModels) {
	    manager.addLLTDtoOspfModels();
	}
    }
}
