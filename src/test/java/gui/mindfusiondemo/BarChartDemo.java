package gui.mindfusiondemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.animation.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.*;

/**
 * 柱状图
 */
public class BarChartDemo extends JFrame
{
	BarChart barChart;

	void initChart()
	{
		barChart = new BarChart();

		barChart.setSeries(
			createSeries());
		
		// fill all elements of a series using a common brush
		barChart.getTheme().setCommonSeriesFills(
			Arrays.asList(
				new SolidBrush( new Color (102, 154, 204)),
				new SolidBrush(new Color (156, 170, 198)),
				new SolidBrush(new Color (206, 0, 0))));
		
		barChart.getTheme().setCommonSeriesStrokes(
				Arrays.asList(
					new SolidBrush( new Color (0, 52, 102))));
		barChart.getTheme().setCommonSeriesStrokeThicknesses(
				Arrays.asList(0.5));
		barChart.getTheme().setDataLabelsBrush(
				new SolidBrush( new Color (0, 52, 102)));
		barChart.getTheme().setDataLabelsFontSize(12);
		barChart.getTheme().setDataLabelsFontStyle(EnumSet.of(FontStyle.BOLD));
		barChart.getTheme().setLegendTitleFontSize(12);
		barChart.getTheme().setGridColor1(Color.white);
		barChart.getTheme().setGridColor2(new Color(240, 240, 240));
		barChart.getTheme().setGridLineColor(new Color(192, 192, 192));
		barChart.getTheme().setAxisTitleFontSize(14);
		barChart.getTheme().setAxisLabelsFontSize(12);

		// set appearance properties
		barChart.setBarSpacingRatio(2);
		barChart.getXAxis().setInterval(1.0);
		barChart.setShowLegend(true);
		barChart.setLegendTitle("Legend");
		barChart.setGridType(GridType.Horizontal);

		// animate the bars
		Animation animation = new Animation(AnimationSpeed.SlowDown);
		AnimationTimeline timeline = new AnimationTimeline();
		timeline.addAnimation(
			AnimationType.PerElementAnimation,
			8.0f, (Renderer2D)barChart.getSeriesRenderer());
		animation.addTimeline(timeline);
		animation.runAnimation();
	}

	ObservableList<Series> createSeries()
	{
		List<String> labels = Arrays.asList(
			"January", "February", "March", "April", "May", "June", "July",
			"August", "September", "October", "November", "December");

		BarSeries series1 = new BarSeries(
			Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0),
			labels, null);
		series1.setTitle("2020");

		BarSeries series2 = new BarSeries(
			Arrays.asList(2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0, 22.0, 24.0),
			labels, null);
		series2.setTitle("2019");

		BarSeries series3 = new BarSeries(
			Arrays.asList(2.0, 8.0, 13.0, 15.0, 13.0, 8.0, 2.0, 8.0, 13.0, 15.0, 13.0, 8.0),
			labels, null);
		series3.setTitle("2018");

		return new ObservableList<Series>(
			Arrays.asList(series1, series2, series3));
	}

	void initFrame()
	{
		setTitle("MindFusion.Charting sample: Bar Chart");
		setSize(800, 600);
		setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initChart();

		JPanel controls = new JPanel();
		controls.setLayout(null);
		controls.setPreferredSize(new Dimension(1000, 200));
		
		JCheckBox xTicks = new JCheckBox("Show X Ticks");
		xTicks.setSelected(barChart.getShowXTicks());
		xTicks.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					barChart.setShowXTicks(xTicks.isSelected());
				}
			});
		addComponent(controls, xTicks);

		JCheckBox yTicks = new JCheckBox("Show Y Ticks");
		yTicks.setSelected(barChart.getShowYTicks());
		yTicks.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					barChart.setShowYTicks(yTicks.isSelected());
				}
			});
		addComponent(controls, yTicks);
		
		JCheckBox xCoords = new JCheckBox("Show X Coordinates");
		xCoords.setSelected(barChart.getShowXCoordinates());
		xCoords.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					barChart.setShowXCoordinates(xCoords.isSelected());
				}
			});
		addComponent(controls, xCoords);
		
		JCheckBox yCoords = new JCheckBox("Show Y Coordinates");
		yCoords.setSelected(barChart.getShowYCoordinates());
		yCoords.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					barChart.setShowYCoordinates(yCoords.isSelected());
				}
			});
		addComponent(controls, yCoords);
		
		newCol();

		addComponent(controls,
			combine("BarLayout ", new LayoutComboBox()));
		addComponent(controls,
			combine("Grid Type   ", new GridComboBox()));
		
		JCheckBox labelsCheckbox = new JCheckBox("Show Data Labels");
		labelsCheckbox.setSelected(true);
		labelsCheckbox.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (labelsCheckbox.isSelected())
						barChart.setShowDataLabels(EnumSet.allOf(LabelKinds.class));
					else
						barChart.setShowDataLabels(EnumSet.noneOf(LabelKinds.class));
					barChart.repaint();
				}
			});
		addComponent(controls, labelsCheckbox);
		
		JCheckBox horizontalCheckbox = new JCheckBox("Horisontal bars");
		horizontalCheckbox.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					barChart.setHorizontalBars(horizontalCheckbox.isSelected());
				}
			});
		addComponent(controls, horizontalCheckbox);
		
		newCol();
		
		xMin = new MinSlider(true);
		yMin = new MinSlider(false);

		xMax = new MaxSlider(true, 12);
		yMax = new MaxSlider(false, 24);

		addComponent(controls,
			combine("XAxis Min", xMin));
		addComponent(controls,
			combine("YAxis Min", yMin));

		JSlider spacingSlider = new JSlider(1,10);
		spacingSlider.setValue(1);
		spacingSlider.addChangeListener(
			new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent e)
				{
					barChart.setBarSpacingRatio((double)spacingSlider.getValue());
				}
			});
		addComponent(controls,
			combine("Space Ratio", spacingSlider));
		
		newCol();
		
		addComponent(controls,combine("XAxis Max", xMax));
		addComponent(controls,combine("YAxis Max", yMax));
		
		JSlider originSlider = new JSlider(0, 30);
		originSlider.setValue(0);
		originSlider.addChangeListener(
			new ChangeListener()
			{
				@Override
				public void stateChanged(ChangeEvent e)
				{
					barChart.getYAxis().setOrigin((double)originSlider.getValue());
				}
			});
		addComponent(controls,
			combine("Y origin", originSlider));
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(controls, BorderLayout.SOUTH);
		getContentPane().add(barChart, BorderLayout.CENTER);
		setVisible(true);
	}
	
	static public void main(String[] args)
	{
		BarChartDemo frame = new BarChartDemo();
		frame.initFrame();
	}

	class GridComboBox extends JComboBox<String> implements ActionListener
	{
		public GridComboBox()
		{
			super(new String[] { "Crossed", "Horizontal", "None", "Vertical" });
			this.setSelectedIndex(2);
			this.setMaximumSize(new Dimension(100,30));
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			barChart.setGridType(
				GridType.valueOf(getSelectedItem().toString()));
		}
		
		static private final long serialVersionUID = 1L;
	}
	
	class LayoutComboBox extends JComboBox<String> implements ActionListener
	{
		public LayoutComboBox()
		{
			super(new String[] { "Overlay", "Side By Side", "Stack" });
			this.setSelectedIndex(1);

			this.setMaximumSize(new Dimension(100,30));
			addActionListener(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int index = getSelectedIndex();
			switch(index)
			{
			case 0:
				barChart.setBarLayout(BarLayout.Overlay);
				break;
			case 1:
				barChart.setBarLayout(BarLayout.SideBySide);
				break;
			case 2:
				barChart.setBarLayout(BarLayout.Stack);
				break;
			}		
		}
		static private final long serialVersionUID = 1L;
	}
		
	class MinSlider extends JSlider implements ChangeListener
	{
		private boolean xAxis;
		
		public MinSlider(boolean xAxis)
		{
			setMinimum(-1);
			setMaximum(11);
			setValue(0);
			addChangeListener(this);
			this.xAxis = xAxis;
		}
		
		@Override
		public void stateChanged(ChangeEvent event)
		{
			if (xAxis)
			{
				double xMin = (double)getValue();
				xMin = Math.min(xMin,xMax.getValue() - 1);
				barChart.getXAxis().setMinValue(xMin);
			}
			else
			{
				double yMin = (double)getValue();
				yMin = Math.min(yMin,yMax.getValue() - 1);
				barChart.getYAxis().setMinValue(yMin);
			}
		}

		static private final long serialVersionUID = 1L;
	}

	private class MaxSlider extends JSlider implements ChangeListener
	{
		private boolean xAxis;
		
		public MaxSlider(boolean xAxis, int initialValue)
		{
			setMaximum(30);
			setValue(initialValue);
			addChangeListener(this);
			this.xAxis=xAxis;
		}
		
		@Override
		public void stateChanged(ChangeEvent arg0)
		{
			if (xAxis)
			{
				double xMax = (double)getValue();
				xMax = Math.max(xMax,xMin.getValue() + 1);
				barChart.getXAxis().setMaxValue(xMax);
			}
			else
			{
				double yMax = (double)getValue();
				yMax = Math.max(yMax,yMin.getValue() + 1);
				barChart.getYAxis().setMaxValue(yMax);
			}
		}
		static private final long serialVersionUID = 1L;
	}
	
	private static JPanel combine(String text,Container element)
	{
		JPanel out=new JPanel();
		out.setLayout(new BoxLayout(out, BoxLayout.X_AXIS));
		out.add(new JLabel(text));
		out.add(element);
		return out;
	}
	
	JPanel addComponent(JPanel panel, Container element)
	{
		yStart += 25;
		if (element != null)
		{
			element.setBounds(xStart, yStart, 200, 20);
			panel.add(element);
		}
		return panel;
	}

	void newCol()
	{
		xStart += 200;
		yStart = 0;
	}
	
	int xStart=0;
	int yStart=0;
	
	MaxSlider xMax;
	MaxSlider yMax;
	
	MinSlider xMin;
	MinSlider yMin;
	
	static private final long serialVersionUID = 1L;
}