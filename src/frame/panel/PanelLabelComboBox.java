package frame.panel;

import java.awt.BorderLayout;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class PanelLabelComboBox extends JPanel {

	private JLabel lbl;
	private JComboBox<Object> cmb;

	public PanelLabelComboBox(String lblText, Object[] items) {
		lbl = new JLabel(lblText);
		cmb = new JComboBox<Object>(items);

		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setBorder(new EmptyBorder(5, 5, 5, 5));
		cmb.setBorder(new EmptyBorder(5, 5, 5, 5));

		setLayout(new BorderLayout());
		add(lbl, BorderLayout.NORTH);
		add(cmb, BorderLayout.SOUTH);
	}

	public void setLabelText(String text) {
		lbl.setText(text);
	}

	public void setComBoText(Object[] items) {
		cmb.setModel(new DefaultComboBoxModel<Object>(items));
	}

	public int getSelectedIndex() {
		return cmb.getSelectedIndex();
	}
	
	public Object getSelectedItem() {
		return cmb.getSelectedItem();
	}
	
}
