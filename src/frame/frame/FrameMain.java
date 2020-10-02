package frame.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.Histogram;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.Styler.LegendPosition;

import frame.panel.PanelLabelComboBox;
import utils.DataCalculator;
import utils.DataIO;

@SuppressWarnings("serial")
public class FrameMain extends JFrame implements IFrame {

	private JPanel contentPane, ctrlPanel, infoPanel, chartPanel;
	private JLabel lblTitle;
	private JLabel[] lblInformation;
	private PanelLabelComboBox cmbStep;
	private JButton btnChange;
	private Double min, max, ave, lsl, usl, cpk, stdev, cnt, var;
	private DataCalculator dc = DataCalculator.getInstance();
	private CategoryChart chart;

	public FrameMain() {
		setFrameSize();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		makeDesignFrame();
		addListener();
	}

	@Override
	public void setFrameSize() {
		setSize(850, 800);

		Dimension dim = this.getSize();
		Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.setLocation((scrSize.width - dim.width) / 2, (scrSize.height - dim.height) / 2);
	}

	@Override
	public void makeDesignFrame() {
		setTitle("CPK Viewer");
		contentPane = new JPanel();
		setContentPane(contentPane);

		lblTitle = new JLabel("CPK Viewer");
		lblTitle.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBorder(new EmptyBorder(10, 10, 10, 10));

		contentPane.setLayout(new BorderLayout());
		contentPane.add(lblTitle, BorderLayout.NORTH);

		ctrlPanel = new JPanel();
		ctrlPanel.setLayout(new GridLayout(1, 0));
		contentPane.add(ctrlPanel, BorderLayout.SOUTH);

		cmbStep = new PanelLabelComboBox("STEP NAME", DataIO.getInstance().getArrColumn());
		ctrlPanel.add(cmbStep);

		btnChange = new JButton("MEASURE");
		ctrlPanel.add(btnChange);

		infoPanel = new JPanel();
		infoPanel.setLayout(new GridLayout(0, 1));
		contentPane.add(infoPanel, BorderLayout.WEST);

		infoPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		lblInformation = new JLabel[9];

		for (int i = 0; i < lblInformation.length; i++) {
			lblInformation[i] = new JLabel();
			infoPanel.add(lblInformation[i]);
		}

		chartPanel = new JPanel();
		contentPane.add(chartPanel, BorderLayout.CENTER);
	}

	@Override
	public void addListener() {
		btnChange.addActionListener(e -> {
			List<Double> calcData = new ArrayList<Double>();
			String[][] refData = DataIO.getInstance().getArrData();
			int selIndex = cmbStep.getSelectedIndex();

			lsl = Double.valueOf(refData[0][selIndex]);
			usl = Double.valueOf(refData[1][selIndex]);
			cnt = (double) (refData.length - 2);
			lblTitle.setText("CPK of " + cmbStep.getSelectedItem());

			for (int i = 2; i < refData.length; i++) {
				try {
					calcData.add(Double.valueOf(refData[i][selIndex]));
				} catch (NumberFormatException e1) {
					lblTitle.setText("ERROR!");
					if (chartPanel.getComponentCount() != 0)
						chartPanel.remove(0);
					revalidate();
					repaint();
					return;
				}
			}
			calcValue(calcData);
			setValue();
			makeChart(calcData);
		});
	}

	private void calcValue(List<Double> list) {
		min = dc.getMin(list);
		max = dc.getMax(list);
		ave = dc.getAve(list);
		stdev = dc.getStdev(list, 0);
		var = dc.getVar(list);
		cpk = dc.getCPK(lsl, usl, ave, stdev);
		DataCalculator.getInstance().sortData(list);
	}

	private void setValue() {
		lblInformation[0].setText("LSL : " + String.format("%.4f", lsl));
		lblInformation[1].setText("USL : " + String.format("%.4f", usl));
		lblInformation[2].setText("MIN : " + String.format("%.4f", min));
		lblInformation[3].setText("MAX : " + String.format("%.4f", max));
		lblInformation[4].setText("CNT : " + String.format("%.4f", cnt));
		lblInformation[5].setText("AVE : " + String.format("%.4f", ave));
		lblInformation[6].setText("STDEV : " + String.format("%.4f", stdev));
		lblInformation[7].setText("VAR : " + String.format("%.4f", var));
		lblInformation[8].setText("CPK : " + String.format("%.4f", cpk));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void makeChart(List<Double> list) {
		if (chartPanel.getComponentCount() != 0)
			chartPanel.remove(0);
		chart = new CategoryChartBuilder().width(600).height(600).title(lblTitle.getText()).xAxisTitle("Limit")
				.yAxisTitle("Count").build();
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
//		chart.getStyler().setOverlapped(true);
		chart.getStyler().setStacked(true);
		chart.getStyler().setAvailableSpaceFill(1.5);
		chart.getStyler().setXAxisTicksVisible(false);

		Histogram histogram = new Histogram(list, list.size());
		chart.addSeries(cmbStep.getSelectedItem().toString(), histogram.getxAxisData(), histogram.getyAxisData());

		chart.getStyler().setXAxisMin(lsl);
		chart.getStyler().setXAxisMax(usl);
		chart.getStyler().setYAxisMin(0.0);
		chartPanel.add(new XChartPanel(chart), BorderLayout.CENTER);
		revalidate();
		repaint();
	}
}
