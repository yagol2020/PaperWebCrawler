package gui.jfreedemo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.Range;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class JFreeChartComplex extends ApplicationFrame {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public JFreeChartComplex(final String title) {
        super(title);
        final XYDataset dataset = createDataset();
        final JFreeChart chart = createJFreeChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        int length = 600;
        int height = 600;
        chartPanel.setPreferredSize(new Dimension(length, height));
        setContentPane(chartPanel);
    }

    private static XYDataset createDataset() {
        XYSeries xyseries = new XYSeries("First");
        xyseries.add(1.0D, 11.0D);
        xyseries.add(2D, 14D);
        xyseries.add(3D, 10D);
        xyseries.add(4D, 15D);
        xyseries.add(5D, 19D);
        xyseries.add(6D, 17D);
        xyseries.add(7D, 14D);
        xyseries.add(8D, 18D);

        XYSeries xyseries1 = new XYSeries("Second");
        xyseries1.add(1.0D, 15D);
        xyseries1.add(2D, 17D);
        xyseries1.add(3D, 16D);
        xyseries1.add(4D, 18D);
        xyseries1.add(5D, 14D);
        xyseries1.add(6D, 14D);
        xyseries1.add(7D, 12D);
        xyseries1.add(8D, 11.0D);

        XYSeries xyseries2 = new XYSeries("Third");
        xyseries2.add(1D, 16D);
        xyseries2.add(2D, 19D);
        xyseries2.add(3D, 14D);
        xyseries2.add(4D, 13D);
        xyseries2.add(5D, 12D);
        xyseries2.add(6D, 13D);
        xyseries2.add(7D, 16D);
        xyseries2.add(8D, 13D);

        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        xyseriescollection.addSeries(xyseries);
        xyseriescollection.addSeries(xyseries1);
        xyseriescollection.addSeries(xyseries2);
        return xyseriescollection;
    }


    public JFreeChart createJFreeChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart("achievements compare",
                "achievements compare about Company A and B",
                "achievements compare", dataset, PlotOrientation.VERTICAL,
                true, true, false);

        chart.addSubtitle(new TextTitle("title", new Font("Arial", Font.BOLD, 15)));


        chart.setBackgroundImageAlpha(0.1f);

        chart.setBorderPaint(Color.RED);
        chart.setBorderVisible(true);

        XYPlot plot = (XYPlot) chart.getPlot();

        plot.setBackgroundPaint(Color.lightGray);

        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        rangeAxis.setAutoRangeIncludesZero(true);

        rangeAxis.setLabelAngle(0);

        float start = 0;
        float end = 40;
        rangeAxis.setRange(new Range(start, end));

        int node = 30;
        int nodenum = (int) ((end - start) / node);
        if (nodenum == 1) {
            nodenum = 2;
        }
        rangeAxis.setTickUnit(new NumberTickUnit(nodenum));
        rangeAxis.setTickLabelFont(new Font("Arial", Font.BOLD, 15));

        ValueAxis valueaxis = plot.getDomainAxis();
        valueaxis.setPositiveArrowVisible(true);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.cyan);
        renderer.setSeriesPaint(1, new Color(0, 100, 255));
        renderer.setSeriesPaint(2, Color.green);
        return chart;

    }


    public static void main(String[] args) {
        JFreeChartComplex lcm = new JFreeChartComplex("Compare A and B");
        lcm.pack();
        RefineryUtilities.centerFrameOnScreen(lcm);
        lcm.setVisible(true);
    }

}