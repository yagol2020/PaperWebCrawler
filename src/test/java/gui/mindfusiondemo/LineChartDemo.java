package gui.mindfusiondemo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.animation.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.*;


public class LineChartDemo extends JFrame {
    LineChart lineChart;

    LineChart initChart() {
        lineChart = new LineChart();

        // create line brushes
        firstBrush = new SolidBrush(new Color(102, 154, 204));
        secondBrush = new SolidBrush(new Color(0, 0, 99));
        thirdBrush = new SolidBrush(new Color(206, 0, 0));

        // chart appearance
        lineChart.getLegendRenderer().setBackground(
                new SolidBrush(new Color(220, 220, 220)));
        lineChart.getTheme().setDataLabelsBackground(
                new SolidBrush(Color.white));
        lineChart.getTheme().setDataLabelsBorderStroke(
                new SolidBrush(Color.gray));

        // create sample data series
        Series2D series1 = new Series2D(
                Arrays.asList(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0),
                Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0, 12.0),
                labels);
        series1.setTitle("Series 1");
        lineChart.getSeries().add(series1);

        Series2D series2 = new Series2D(
                Arrays.asList(0.0, 1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0, 11.0),
                Arrays.asList(2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0, 20.0, 22.0, 24.0),
                labels);
        series2.setTitle("Series 2");
        lineChart.getSeries().add(series2);

        FunctionSeries series3;
        try {
            series3 = new FunctionSeries(
                    "x * Sin(x) + x + 5", 12, 12);
            lineChart.getSeries().add(series3);
            series3.setTitle("Series 3");
        } catch (Exception e) {
            // could not parse the expression
            e.printStackTrace();
        }

        lineChart.getXAxis().setInterval(1.0);
        lineChart.setGridType(GridType.Crossed);

        // assign one brush per series
        List<Brush> brushes = Arrays.asList(firstBrush, secondBrush, thirdBrush);
        MixedSeriesStyle seriesStyle = new MixedSeriesStyle();
        seriesStyle.setCommonFills(brushes);
        seriesStyle.setCommonStrokes(brushes);
        seriesStyle.setUniformStrokeThickness(5.0);
        lineChart.getPlot().setSeriesStyle(seriesStyle);

        return lineChart;
    }

    void initFrame() {
        setTitle("MindFusion.Charting sample: Line Chart");
        setSize(800, 600);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().add(
                initChart(),
                BorderLayout.CENTER);

        getContentPane().add(
                initUI(),
                BorderLayout.SOUTH);

        setVisible(true);
    }

    JPanel initUI() {
        JPanel ui = new JPanel();
        FlowLayout flow = new FlowLayout(FlowLayout.LEFT);
        ui.setLayout(flow);

        JPanel col1 = new JPanel();
        col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
        ui.add(col1);

        JPanel col2 = new JPanel();
        col2.setLayout(new BoxLayout(col2, BoxLayout.Y_AXIS));
        ui.add(col2);

        JPanel col3 = new JPanel();
        col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
        ui.add(col3);

        JPanel col4 = new JPanel();
        col4.setLayout(new BoxLayout(col4, BoxLayout.Y_AXIS));
        ui.add(col4);

        JPanel col5 = new JPanel();
        col5.setLayout(new BoxLayout(col5, BoxLayout.Y_AXIS));
        ui.add(col5);

        ui.add(
                new JTextArea(
                        "This sample demonstrates various properties of the LineChart control. " +
                                "Change property values in this panel to see their effect in chart above."));

        JCheckBox showXTicksCheck = new JCheckBox("Show X Ticks");
        showXTicksCheck.setSelected(lineChart.getShowXTicks());
        showXTicksCheck.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lineChart.setShowXTicks(showXTicksCheck.isSelected());
                    }
                });
        col1.add(showXTicksCheck);

        JCheckBox showYTicksCheck = new JCheckBox("Show Y Ticks");
        showYTicksCheck.setSelected(lineChart.getShowYTicks());
        showYTicksCheck.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lineChart.setShowYTicks(showYTicksCheck.isSelected());
                    }
                });
        col1.add(showYTicksCheck);

        JCheckBox showXCoordsCheck = new JCheckBox("Show X Coordinates");
        showXCoordsCheck.setSelected(lineChart.getShowXCoordinates());
        showXCoordsCheck.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lineChart.setShowXCoordinates(showXCoordsCheck.isSelected());
                    }
                });
        col1.add(showXCoordsCheck);

        JCheckBox showYCoordsCheck = new JCheckBox("Show Y Coordinates");
        showYCoordsCheck.setSelected(lineChart.getShowYCoordinates());
        showYCoordsCheck.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lineChart.setShowYCoordinates(showYCoordsCheck.isSelected());
                    }
                });
        col1.add(showYCoordsCheck);

        JCheckBox showLegendCheck = new JCheckBox("Show Legend");
        showLegendCheck.setSelected(lineChart.getShowLegend());
        showLegendCheck.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lineChart.setShowLegend(showLegendCheck.isSelected());
                    }
                });
        col2.add(showLegendCheck);

        JCheckBox showDataLabelsCheck = new JCheckBox("Show Data Labels");
        showDataLabelsCheck.setSelected(true);
        showDataLabelsCheck.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (showDataLabelsCheck.isSelected())
                            lineChart.setShowDataLabels(EnumSet.allOf(LabelKinds.class));
                        else
                            lineChart.setShowDataLabels(EnumSet.noneOf(LabelKinds.class));
                    }
                });
        col2.add(showDataLabelsCheck);

        JCheckBox scrollGridCheck = new JCheckBox("Scroll Grid");
        scrollGridCheck.setSelected(!lineChart.getPinGrid());
        scrollGridCheck.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lineChart.setPinGrid(!scrollGridCheck.isSelected());
                    }
                });
        col2.add(scrollGridCheck);

        JLabel xAxisMinLabel = new JLabel("X Axis Min:");
        col3.add(xAxisMinLabel);

        JSlider xAxisMinSlider = new JSlider(0, 10, 0);
        xAxisMinSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        double max = 10;
                        if (lineChart.getXAxis().getMaxValue() != null)
                            max = lineChart.getXAxis().getMaxValue();
                        double val = Math.min(
                                (double) xAxisMinSlider.getValue(), max);
                        lineChart.getXAxis().setMinValue(val);
                    }
                });
        col3.add(xAxisMinSlider);

        JLabel xAxisMaxLabel = new JLabel("X Axis Max:");
        col3.add(xAxisMaxLabel);

        JSlider xAxisMaxSlider = new JSlider(0, 20, 11);
        xAxisMaxSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        double min = 0;
                        if (lineChart.getXAxis().getMinValue() != null)
                            min = lineChart.getXAxis().getMinValue();
                        double val = Math.max(
                                (double) xAxisMaxSlider.getValue(), min);
                        lineChart.getXAxis().setMaxValue(val);
                    }
                });
        col3.add(xAxisMaxSlider);

        JLabel yAxisMinLabel = new JLabel("Y Axis Min:");
        col4.add(yAxisMinLabel);

        JSlider yAxisMinSlider = new JSlider(0, 10, 0);
        yAxisMinSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        double max = 20;
                        if (lineChart.getYAxis().getMaxValue() != null)
                            max = lineChart.getYAxis().getMaxValue();
                        double val = Math.min(
                                (double) yAxisMinSlider.getValue(), max);
                        lineChart.getYAxis().setMinValue(val);
                    }
                });
        col4.add(yAxisMinSlider);

        JLabel yAxisMaxLabel = new JLabel("Y Axis Max:");
        col4.add(yAxisMaxLabel);

        JSlider yAxisMaxSlider = new JSlider(0, 35, 21);
        yAxisMaxSlider.addChangeListener(
                new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        double min = 0;
                        if (lineChart.getYAxis().getMinValue() != null)
                            min = lineChart.getYAxis().getMinValue();
                        double val = Math.max(
                                (double) yAxisMaxSlider.getValue(), min);
                        lineChart.getYAxis().setMaxValue(val);
                    }
                });
        col4.add(yAxisMaxSlider);

        JLabel lineTypeLabel = new JLabel("Line Type:");
        col5.add(lineTypeLabel);

        JComboBox<String> lineTypeCombo = new JComboBox<String>(
                new String[]{"Polyline", "Step", "Curve"});
        lineTypeCombo.setSelectedIndex(0);
        lineTypeCombo.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lineChart.setLineType(
                                LineType.valueOf(lineTypeCombo.getSelectedItem().toString()));
                    }
                });
        lineTypeCombo.setAlignmentX(0);
        col5.add(lineTypeCombo);

        JLabel gridTypeLabel = new JLabel("Grid Type:");
        col5.add(gridTypeLabel);

        JComboBox<String> gridTypeCombo = new JComboBox<String>(
                new String[]{"Crossed", "Horizontal", "None", "Vertical"});
        gridTypeCombo.setSelectedIndex(0);
        gridTypeCombo.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lineChart.setGridType(
                                GridType.valueOf(gridTypeCombo.getSelectedItem().toString()));
                    }
                });
        gridTypeCombo.setAlignmentX(0);
        col5.add(gridTypeCombo);

        return ui;
    }

    static public void main(String[] args) {
        LineChartDemo frame = new LineChartDemo();
        frame.initFrame();
    }

    SolidBrush firstBrush;
    SolidBrush secondBrush;
    SolidBrush thirdBrush;

    List<String> labels = Arrays.asList(
            "one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine", "ten", "eleven", "twelve"
    );

    static private final long serialVersionUID = 1L;
}
