package frame.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import frame.panel.PanelScrpTable;
import utils.DataIO;

@SuppressWarnings("serial")
public class FrameData extends JFrame implements IFrame {

	private JPanel contentPane;
	private PanelScrpTable pnlTable;

	public FrameData() {
		DataIO.getInstance();
		setFrameSize();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		makeDesignFrame();
	}

	@Override
	public void setFrameSize() {
		setSize(800, 800);

		Dimension dim = this.getSize();
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.setLocation((scrSize.width - dim.width) / 4, (scrSize.height - dim.height) / 4);
	}

	@Override
	public void makeDesignFrame() {
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout());

		pnlTable = new PanelScrpTable();
		contentPane.add(pnlTable, BorderLayout.CENTER);
	}

	@Override
	public void addListener() {}

}
