package gui.mindfusiondemo;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.components.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.*;

/**
 * 雷达图
 */
public class RadarChartDemo extends JFrame {
    RadarChart radarChart;

    void initChart() {
        radarChart = new RadarChart();

        Axis axis1 = new Axis();
        Axis axis2 = new Axis();
        Axis axis3 = new Axis();
        Axis axis4 = new Axis();
        Axis axis5 = new Axis();
        Axis axis6 = new Axis();
        Axis axis7 = new Axis();
        Axis axis8 = new Axis();
        Axis[] axes = {axis1, axis2, axis3, axis4, axis5, axis6, axis7, axis8};
        for (int i = 0; i < 8; i++) axes[i].setTitle(String.format("Axis %d", i + 1));
        for (int i = 0; i < 8; i++) radarChart.getAxes().add(axes[i]);
        radarChart.setDefaultAxis(axes[0]);

        radarChart.setTheme(new Theme());
        radarChart.getTheme().setAxisLabelsBrush(new SolidBrush(new Color(82, 82, 82)));
        radarChart.getTheme().setAxisLabelsFontSize(12.0);
        radarChart.getTheme().setDataLabelsFontSize(12.0);
        radarChart.getTheme().setAxisTitleFontSize(12.0);
        radarChart.getTheme().setAxisStroke(new SolidBrush(Colors.LightGray));
        radarChart.getTheme().setDataLabelsBackground(new SolidBrush(Color.white));
        radarChart.getTheme().setDataLabelsBorderStroke(new SolidBrush(Color.gray));

        radarChart.setGridColor1(Color.WHITE);
        radarChart.setGridColor2(new Color(235, 235, 235));
        radarChart.getDefaultAxis().setMaxValue(55.0);
        radarChart.getDefaultAxis().setMinValue(0.0);
        radarChart.getDefaultAxis().setNumberFormat("%d");

        radarChart.setSeries(ols);
        radarChart.setAllowRotate(true);
        radarChart.setAreaOpacity(opacity);
        radarChart.setGridType(RadarGridType.Spiderweb);
        radarChart.setGridDivisions(5);
        radarChart.setRadarType(RadarType.Polygon);
        radarChart.setShowLegend(false);
    }

    public RadarChartDemo() {
        super();
        setTitle("MindFusion.Charting sample: Radar Chart");
        setSize(800, 600);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private ObservableList<Series> createSeries() {
        List<Double> values1 = Arrays.asList(
                20.0d, 30.0d, 43.0d, 40.0d, 44.0d, 37.0d, 35.0d, 51.0d);

        List<Double> values2 = Arrays.asList(
                12.0d, 40.0d, 23.0d, 30.0d, 34.0d, 47.0d, 45.0d, 21.0d);

        List<String> labels1 = Arrays.asList(
                "20", "30", "43", "40", "44", "37", "35", "51");

        List<String> labels2 = Arrays.asList(
                "12", "40", "23", "30", "34", "47", "45", "21");

        SimpleSeries s1 = new SimpleSeries(values1, labels1);
        SimpleSeries s2 = new SimpleSeries(values2, labels2);

        ObservableList<Series> ols = new ObservableList<Series>();
        ols.add(s1);
        ols.add(s2);

        return ols;
    }

    private List<Brush> fill() {
        List<Brush> fills = new ArrayList<Brush>();
        fills.add(new SolidBrush(new Color(206, 0, 0)));
        fills.add(new SolidBrush(new Color(0, 52, 102)));
        return fills;
    }

    private List<Brush> stroke() {
        List<Brush> strokes = new ArrayList<Brush>();
        strokes.add(new SolidBrush(new Color(206, 0, 0)));
        strokes.add(new SolidBrush(new Color(0, 52, 102)));
        return strokes;
    }

    private List<Double> strokeThicknesses() {
        List<Double> thicknesses = new ArrayList<Double>();
        thicknesses.add(5.0);

        return thicknesses;
    }

    private Plot createPlot() {
        List<Brush> fills = fill();
        List<Brush> strokes = stroke();
        List<Double> strokeThicknesses = strokeThicknesses();

        Plot out = radarChart.getPlot();
        out.setGridColumn(0);
        out.setGridRow(0);

        out.setSeriesStyle(
                new PerSeriesStyle(fills, strokes, strokeThicknesses, null));
        out.setHighlightStroke(
                new SolidBrush(Colors.BlanchedAlmond));
        out.setHighlightStrokeDashStyle(
                DashStyle.Dash);

        return out;
    }

    private GridPanel constructPanel() {
        GridPanel panel = new GridPanel();
        panel.getRows().add(new GridRow());

        panel.getChildren().add(plot);
        return panel;
    }

    private void initRadarChartSample() {
        ols = createSeries();

        initChart();

        plot = createPlot();

        JPanel controls = new JPanel();
        controls.setLayout(null);

        addComponent(controls, new RotationCheckbox());
        addComponent(controls, new DataLabelsCheckbox());
        yStart += 25;
        addComponent(controls,
                combine("Chart Padding:", new ChartPaddingSlider()));

        newRow();
        addComponent(controls,
                combine("Grid Divisions: ", new GridDivisionsSlider()));
        yStart += 25;
        addLargeComponent(controls,
                combine("Start Angle:", new StartAngleSlider()));

        newRow();
        addComponent(controls,
                combine("Axis Min Value:", new AxisMinSlider()));

        newRow();
        addComponent(controls,
                combine("Axis Max Value:", new AxisMaxSlider()));

        newRow();
        addComponent(controls,
                combine("Area Opacity:", new AreaOpacitySlider()));

        newRow();
        addComboComponent(controls,
                combineComboBox("Grid Type:", new GridTypeComboBox()));
        addComboComponent(controls,
                combineComboBox("Radar Type:", new RadarTypeComboBox()));
        controls.setPreferredSize(new Dimension(800, 220));

        setLayout(new BorderLayout());
        this.add(controls, BorderLayout.SOUTH);
        this.add(radarChart);

        this.setVisible(true);

    }

    public static void main(String[] args) {
        RadarChartDemo radarChart = new RadarChartDemo();
        radarChart.initRadarChartSample();
    }

    private class RotationCheckbox
            extends JCheckBox implements ActionListener {
        public RotationCheckbox() {
            super("Allow Rotate", true);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            radarChart.setAllowRotate(
                    !radarChart.getAllowRotate());
            plot.invalidate();
        }
    }

    private class DataLabelsCheckbox
            extends JCheckBox implements ActionListener {
        private EnumSet<LabelKinds> unchecked;
        private EnumSet<LabelKinds> checked;

        public DataLabelsCheckbox() {
            super("Show Data Label", true);
            addActionListener(this);
            checked = EnumSet.allOf(LabelKinds.class);
            unchecked = EnumSet.of(LabelKinds.ToolTip, LabelKinds.XAxisLabel, LabelKinds.YAxisLabel);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            if (isSelected()) {
                radarChart.setShowDataLabels(checked);
            } else {
                radarChart.setShowDataLabels(unchecked);
            }
            plot.invalidate();
        }
    }

    private class ChartPaddingSlider
            extends JSlider implements ChangeListener {
        public ChartPaddingSlider() {
            super(0, 30);
            this.setValue(15);
            addChangeListener(this);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            radarChart.setChartPadding(getValue());
            plot.invalidate();
        }
    }

    private class GridDivisionsSlider
            extends JSlider implements ChangeListener {
        public GridDivisionsSlider() {
            super(5, 15);
            this.setValue(5);
            addChangeListener(this);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            radarChart.setGridDivisions(getValue());
            plot.invalidate();
        }
    }

    private class StartAngleSlider
            extends JSlider implements ChangeListener {
        public StartAngleSlider() {
            super(0, 360);
            this.setValue(0);
            addChangeListener(this);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            radarChart.setStartAngle(getValue());
            plot.invalidate();
        }
    }

    private class AreaOpacitySlider
            extends JSlider implements ChangeListener {
        public AreaOpacitySlider() {
            super(0, 10);
            this.setValue(0);
            addChangeListener(this);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            opacity = (double) getValue() / 10.0;
            radarChart.setAreaOpacity(opacity);
            plot.invalidate();
        }
    }

    private class AxisMinSlider
            extends JSlider implements ChangeListener {
        public AxisMinSlider() {
            super(0, 10);
            this.setValue(0);
            addChangeListener(this);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            radarChart.getDefaultAxis().setMinValue((double) getValue());
            plot.invalidate();
        }
    }

    private class AxisMaxSlider
            extends JSlider implements ChangeListener {
        public AxisMaxSlider() {
            super(55, 65);
            this.setValue(55);
            addChangeListener(this);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            radarChart.getDefaultAxis().setMaxValue((double) getValue());
            plot.invalidate();
        }
    }

    private class GridTypeComboBox
            extends JComboBox implements ActionListener {
        GridTypeComboBox() {
            super(new String[]{"Spiderweb", "Radar"});
            this.setSelectedIndex(0);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = getSelectedIndex();
            switch (index) {
                case 0:
                    radarChart.setGridType(RadarGridType.Spiderweb);
                    break;

                case 1:
                    radarChart.setGridType(RadarGridType.Radar);
                    break;
            }
            plot.invalidate();
        }
    }

    private class RadarTypeComboBox
            extends JComboBox implements ActionListener {
        RadarTypeComboBox() {
            super(new String[]{"Polygon", "PieChart"});
            this.setSelectedIndex(0);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = getSelectedIndex();
            switch (index) {
                case 0:
                    radarChart.setRadarType(RadarType.Polygon);
                    break;

                case 1:
                    radarChart.setRadarType(RadarType.Pie);
                    break;
            }
            plot.invalidate();
        }
    }

    private JPanel combine(String text, Container element) {
        JPanel out = new JPanel();
        out.setLayout(new BoxLayout(out, BoxLayout.Y_AXIS));
        out.add(new JLabel(text, null, JLabel.CENTER));
        out.add(element);
        return out;
    }

    private JPanel combineComboBox(String text, Container element) {
        JPanel out = new JPanel();
        out.setLayout(new BoxLayout(out, BoxLayout.X_AXIS));
        out.add(new JLabel(text, null, JLabel.CENTER));
        out.add(element);
        return out;
    }

    private JPanel addComponent(JPanel panel, Container element) {
        yStart += 35;
        if (element != null) {
            element.setBounds(xStart, yStart, 150, 40);
            panel.add(element);
        }
        return panel;
    }

    private JPanel addComboComponent(JPanel panel, Container element) {
        yStart += 35;
        if (element != null) {
            element.setBounds(xStart, yStart, 180, 40);
            panel.add(element);
        }
        return panel;
    }

    private JPanel addLargeComponent(JPanel panel, Container element) {
        yStart += 70;
        if (element != null) {
            element.setBounds(xStart, yStart, 650, 40);
            panel.add(element);
        }
        return panel;
    }

    private void newRow() {
        xStart += 175;
        yStart = 0;
    }

    int xStart = 20;
    int yStart = 0;
    double opacity = 0;
    Plot plot;
    ObservableList<Series> ols;
}
