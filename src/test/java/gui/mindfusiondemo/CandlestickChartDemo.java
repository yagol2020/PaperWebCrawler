package gui.mindfusiondemo;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.animation.*;
import com.mindfusion.charting.swing.*;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.*;

/**
 * 箱图
 */
public class CandlestickChartDemo extends JFrame {
    CandlestickChart candlestickChart;

    void initChart() {
        candlestickChart = new CandlestickChart() {{
            setSeries(createSeries());

            setCandlestickWidth(8);
            setLegendTitle("Legend");
            setShowLegend(true);

            setTitle("The Great Corporation, Berlin, Germany");
            setShowZoomWidgets(true);
            setShowLegend(false);
            setShowXCoordinates(false);
            setShowXTicks(false);
            setXLabelRotationAngle(45);
            getTheme().setAxisLabelsFontSize(11);
            setTitleMargin(new Margins(10));

            // background appearance
            setGridType(GridType.Vertical);
            setBackground(new Color(0, 52, 102));
        }};

        Theme theme = candlestickChart.getTheme();
        theme.setGridColor1(new Color(245, 245, 245));
        theme.setGridColor2(new Color(225, 225, 225));
        theme.setGridLineColor(new Color(210, 210, 210));

        // series style
        candlestickChart.getPlot().setSeriesStyle(
                new CandlestickSeriesStyle(
                        new SolidBrush(new Color(206, 0, 0)),
                        new SolidBrush(new Color(0, 52, 102)),
                        new SolidBrush(new Color(160, 160, 160)),
                        2, DashStyle.Solid,
                        (CandlestickRenderer) candlestickChart.getPlot().getSeriesRenderers().get(0)));

        SolidBrush lightBlue = new SolidBrush(new Color(224, 233, 233));
        theme.setUniformSeriesStroke(lightBlue);
        theme.setHighlightStroke(new SolidBrush(new Color(102, 154, 204)));
        theme.setDataLabelsBrush(lightBlue);
        theme.setAxisLabelsBrush(lightBlue);
        theme.setAxisTitleBrush(lightBlue);
        theme.setTitleBrush(lightBlue);
        theme.setTitleFontSize(18);
        theme.setTitleFontStyle(EnumSet.of(FontStyle.BOLD));
        theme.setAxisTitleFontSize(14);
        theme.setAxisStroke(lightBlue);

        // animate the candles
        Animation animation = new Animation();
        AnimationTimeline timeline = new AnimationTimeline();
        timeline.addAnimation(
                AnimationType.PerSeriesAnimation, 3f,
                (Renderer2D) candlestickChart.getSeriesRenderer());
        animation.addTimeline(timeline);
        animation.runAnimation();
    }

    ObservableList<Series> createSeries() {
        List<StockPrice> dataList = new ArrayList<StockPrice>();

        double[][] stockData = new double[][]
                {
                        {15.99, 16.07, 15.80, 15.94},
                        {15.93, 16.03, 15.89, 15.97},
                        {15.97, 16.43, 15.94, 16.40},
                        {16.36, 16.52, 16.34, 16.46},
                        {16.44, 16.47, 16.23, 16.38},
                        {16.38, 17.08, 16.37, 17.02},
                        {17.01, 17.32, 16.93, 17.27},
                        {17.24, 17.36, 17.14, 17.32},
                        {17.28, 17.44, 17.08, 17.41},
                        {17.40, 17.47, 17.21, 17.38},
                        {17.34, 17.59, 17.32, 17.50},
                        {17.51, 17.85, 17.14, 17.15},
                        {17.16, 17.48, 17.16, 17.45},
                        {17.41, 17.62, 17.27, 17.47},
                        {17.46, 17.56, 17.16, 17.27},
                        {17.22, 17.32, 17.10, 17.24},
                        {17.15, 17.47, 17.14, 17.28},
                        {17.03, 18.30, 17.02, 17.74},
                        {17.72, 17.90, 17.61, 17.72},
                        {17.71, 17.83, 17.51, 17.75},
                        {17.74, 18.46, 17.74, 18.26},
                        {18.21, 18.80, 18.17, 18.69},
                        {18.70, 19.88, 18.65, 19.68},
                        {19.72, 21.12, 19.66, 20.40},
                        {20.29, 20.44, 19.52, 19.92},
                        {19.87, 20.49, 19.84, 20.07},
                        {20.06, 20.25, 19.45, 19.66},
                        {19.65, 20.20, 19.17, 20.19},
                        {20.30, 20.66, 20.09, 20.27},
                        {20.26, 20.51, 19.95, 20.13},
                        {20.10, 20.46, 19.88, 20.32},
                        {20.31, 20.54, 19.99, 20.28},
                        {20.28, 20.32, 19.98, 20.07},
                        {20.11, 20.22, 19.69, 20.01},
                        {19.98, 20.05, 19.79, 19.89},
                        {19.87, 19.97, 19.31, 19.39},
                        {19.36, 19.86, 19.20, 19.76},
                        {19.76, 19.93, 19.55, 19.63},
                        {19.63, 19.70, 19.29, 19.55},
                        {19.48, 19.73, 19.42, 19.61},
                        {19.57, 20.39, 19.51, 20.33},
                        {20.32, 20.50, 20.09, 20.13},
                        {20.13, 20.39, 19.95, 20.33},
                        {20.30, 20.63, 20.28, 20.43},
                        {20.44, 20.77, 20.32, 20.58},
                        {20.59, 20.71, 20.36, 20.38},
                        {20.37, 20.50, 20.04, 20.34},
                        {20.33, 20.42, 19.68, 19.69},
                        {19.49, 19.85, 19.48, 19.72},
                        {19.70, 19.86, 19.57, 19.83},
                        {19.83, 20.48, 19.83, 20.11},
                        {20.08, 20.31, 19.87, 19.95},
                        {19.95, 20.21, 19.64, 19.69},
                        {19.74, 20.03, 19.69, 19.79},
                        {19.80, 20.10, 19.70, 19.78},
                        {19.72, 19.88, 19.35, 19.66},
                        {19.63, 19.92, 19.60, 19.71},
                        {19.72, 19.77, 19.22, 19.42}
                };

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -30);

        for (int i = 1; i <= 30; i++) {
            Date date = cal.getTime();
            StockPrice dataItem = new StockPrice(
                    stockData[i][0], stockData[i][3],
                    stockData[i][2], stockData[i][1],
                    date);
            dataList.add(dataItem);
            cal.add(Calendar.DATE, 1);
        }

        StockPriceSeries stockPriceSeries = new StockPriceSeries(dataList);
        stockPriceSeries.setDateTimeFormat(DateTimeFormat.ShortDate);

        ObservableList<Series> ols = new ObservableList<Series>();
        ols.add(stockPriceSeries);
        return ols;
    }

    void initFrame() {
        setTitle("MindFusion.Charting sample: Candlestick Chart");
        setSize(800, 600);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initChart();

        JPanel controls = new JPanel();

        controls.setLayout(null);
        controls.setBounds(getBounds());

        addComponent(controls,
                combine("CandlestickWidth ", new WidthSlider()));
        addComponent(controls,
                combine("DateFormat ", new DateFormatComboBox()));
        addComponent(controls,
                new CustomDateTextField());
        addComponent(controls,
                new ShowZoomWidgetsCheckBox());
        addComponent(controls,
                combine("GridType ", new GridComboBox()));
        newLine();
        addComponent(controls,
                new PinGridCheckBox());

        controls.setPreferredSize(new Dimension(800, 80));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(controls, BorderLayout.NORTH);
        getContentPane().add(candlestickChart, BorderLayout.CENTER);

        setVisible(true);
    }

    static public void main(String[] args) {
        CandlestickChartDemo frame = new CandlestickChartDemo();
        frame.initFrame();
    }

    class ShowZoomWidgetsCheckBox
            extends JCheckBox implements ActionListener {
        public ShowZoomWidgetsCheckBox() {
            super("ShowZoomWidgets", false);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            candlestickChart.setShowZoomWidgets(isSelected());
        }
    }

    class PinGridCheckBox
            extends JCheckBox implements ActionListener {
        public PinGridCheckBox() {
            super("PinGrid", false);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent arg0) {
            candlestickChart.setPinGrid(isSelected());
        }

    }

    private static JPanel combine(String text, Container element) {
        JPanel out = new JPanel();
        out.setLayout(new BoxLayout(out, BoxLayout.X_AXIS));
        out.add(new JLabel(text));
        out.add(element);
        return out;
    }

    private static JPanel addComponent(JPanel panel, Container element) {
        if (element != null) {
            if (panel.getComponentCount() == 2 || panel.getComponentCount() == 3) {
                element.setBounds(xStart, yStart, 160, 20);
                xStart += 160;
            } else {
                element.setBounds(xStart, yStart, 240, 20);
                xStart += 240;
            }
            panel.add(element);
        }

        return panel;
    }

    class GridComboBox extends JComboBox implements ActionListener {
        public GridComboBox() {
            super(new String[]{"Crossed", "Horisontal", "None", "Vertical"});
            this.setSelectedIndex(3);
            this.setMaximumSize(new Dimension(100, 30));
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = getSelectedIndex();

            switch (index) {
                case 0:
                    candlestickChart.setGridType(GridType.Crossed);
                    break;
                case 1:
                    candlestickChart.setGridType(GridType.Horizontal);
                    break;
                case 2:
                    candlestickChart.setGridType(GridType.None);
                    break;
                case 3:
                    candlestickChart.setGridType(GridType.Vertical);
                    break;
            }
        }
    }

    class DateFormatComboBox
            extends JComboBox implements ActionListener {
        public DateFormatComboBox() {
            super(new String[]{
                    "CustomDateTime", "FullDateTime", "LongDate", "LongTime",
                    "MonthDateTime", "None", "ShortDate", "ShortTime", "YearDateTime"});
            this.setSelectedIndex(7);
            addActionListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            StockPriceSeries stockPriceSeries = (StockPriceSeries) candlestickChart.getSeries().get(0);
            int index = getSelectedIndex();
            switch (index) {
                case 0:
                    break;
                case 1:
                    stockPriceSeries.setDateTimeFormat(DateTimeFormat.FullDateTime);
                    break;
                case 2:
                    stockPriceSeries.setDateTimeFormat(DateTimeFormat.LongDate);
                    break;
                case 3:
                    stockPriceSeries.setDateTimeFormat(DateTimeFormat.LongTime);
                    break;
                case 4:
                    stockPriceSeries.setDateTimeFormat(DateTimeFormat.MonthDateTime);
                    break;
                case 5:
                    stockPriceSeries.setDateTimeFormat(DateTimeFormat.None);
                    break;
                case 6:
                    stockPriceSeries.setDateTimeFormat(DateTimeFormat.ShortDate);
                    break;
                case 7:
                    stockPriceSeries.setDateTimeFormat(DateTimeFormat.ShortTime);
                    break;
                case 8:
                    stockPriceSeries.setDateTimeFormat(DateTimeFormat.YearDateTime);
                    break;
            }
            candlestickChart.repaint();
        }
    }

    class WidthSlider extends JSlider implements ChangeListener {

        public WidthSlider() {
            super(2, 20);
            this.setValue(8);
            addChangeListener(this);
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            candlestickChart.setCandlestickWidth((double) getValue());
        }
    }

    class CustomDateTextField
            extends JTextField implements ActionListener {
        public CustomDateTextField() {
            super("dd", 1);

            addActionListener(this);
        }

        public void actionPerformed(ActionEvent evt) {
            Object source = evt.getSource();
            if (source == this) {
                StockPriceSeries stockPriceSeries = (StockPriceSeries) candlestickChart.getSeries().get(0);
                text = this.getText();
                stockPriceSeries.setCustomDateTimeFormat(text);
                candlestickChart.repaint();
            }
        }
    }

    private static void newLine() {
        xStart = 800;
        yStart = 30;
    }

    private static int xStart = 6;
    private static int yStart = 10;
    private static String text;
}
