package gui.mindfusiondemo;

import java.awt.Color;
import java.util.*;

import javax.swing.*;

import com.mindfusion.charting.*;
import com.mindfusion.charting.animation.*;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.*;

/**
 * 折线+柱状图
 * 我们选择就是这个
 */
public class MultipleAxes {
    public static void main(String[] args) {
        com.mindfusion.charting.swing.Dashboard dashboard1 = new com.mindfusion.charting.swing.Dashboard();
        Plot2D plot = new Plot2D();
        plot.setGridType(GridType.Horizontal);
        plot.setGridLineColor(new Color(220, 220, 220));

        List<Double> x = new ArrayList<Double>();
        for (int i = 1; i <= 5; i++)
            x.add((double) i);
        List<Double> y = new ArrayList<Double>();
        y.add(1.0);
        y.add(2.0);
        y.add(3.0);
        y.add(4.0);
        y.add(5.0);
        String[] months = new String[]
                {
                        "1", "2", "3", "4", "5"
                };
        List<String> labels = new ArrayList<String>();
        Collections.addAll(labels, months);

        Series2D barSeries = new Series2D(x, y, labels);
        barSeries.setTitle("Temperature");
        barSeries.setSupportedLabels(LabelKinds.XAxisLabel);

        List<Double> x1 = new ArrayList<Double>(x);
        double[] yValues = new double[]{1, 2, 3, 4, 5};
        List<Double> y1 = new ArrayList<Double>();
        for (double yValue : yValues) y1.add(yValue);
        String[] elements = new String[]{"", "", "", "", ""};
        List<String> labels1 = new ArrayList<String>();
        Collections.addAll(labels1, elements);

        Series2D lineSeries = new Series2D(x1, y1, labels1);
        lineSeries.setTitle("Pressure");

        List<Series> seriesList = new ArrayList<Series>();
        seriesList.add(barSeries);
        ObservableList<Series> ols = new ObservableList<Series>(seriesList);
        BarRenderer barRenderer = new BarRenderer(ols);

        barRenderer.setSeriesStyle(new UniformSeriesStyle(
                new com.mindfusion.drawing.SolidBrush(new Color(102, 154, 204)),
                new com.mindfusion.drawing.SolidBrush(new Color(0, 52, 102)),
                2.0, com.mindfusion.drawing.DashStyle.Solid));

        List<Series> seriesList1 = new ArrayList<Series>();
        seriesList1.add(lineSeries);
        ObservableList<Series> ols1 = new ObservableList<Series>(seriesList1);
        LineRenderer lineRenderer = new LineRenderer(ols1);
        //折线的风格
        UniformSeriesStyle style = new UniformSeriesStyle(
                new com.mindfusion.drawing.SolidBrush(new Color(206, 0, 0)),
                new com.mindfusion.drawing.SolidBrush(new Color(206, 0, 0)), 4.0,
                com.mindfusion.drawing.DashStyle.Solid);
        lineRenderer.setSeriesStyle(style);
        //动画
        Animation animation = new Animation();
        AnimationTimeline timeline = new AnimationTimeline();
        timeline.addAnimation(
                AnimationType.PerElementAnimation, 2f, (Renderer2D) barRenderer);
        timeline.addAnimation(
                AnimationType.PerElementAnimation, 1f, (Renderer2D) lineRenderer);
        animation.addTimeline(timeline);
        animation.runAnimation();


        plot.getSeriesRenderers().add(barRenderer);
        plot.getSeriesRenderers().add(lineRenderer);


        Axis monthAxis = new Axis();
        monthAxis.setInterval(1.0);
        monthAxis.setMinValue(0.0);
        monthAxis.setMaxValue(12.0);
        monthAxis.setTitle("Years");
        Axis count = new Axis();
        count.setMinValue(-1.0);
        count.setMaxValue(7.0);
        count.setInterval(1.0);
        count.setTitle("Count");
        plot.setXAxis(count);
        plot.setYAxis(monthAxis);

        barRenderer.setYAxis(count);
        lineRenderer.setYAxis(count);


        com.mindfusion.charting.swing.LayoutBuilder builder = new com.mindfusion.charting.swing.LayoutBuilder(dashboard1);
        dashboard1.getTheme().setAxisLabelsFontSize(12.0);
        dashboard1.getTheme().setAxisTitleFontSize(12.0);
        dashboard1.getTheme().setAxisTitleFontStyle(EnumSet.of(FontStyle.BOLD));
        dashboard1.getTheme().setDataLabelsFontSize(12.0);
        dashboard1.getTheme().setLegendTitleFontSize(14.0);

        XAxisRenderer xAxisRenderer = new XAxisRenderer(monthAxis);
        xAxisRenderer.setLabelsSource(plot);
        YAxisRenderer yAxisRenderer = new YAxisRenderer(count);
        yAxisRenderer.setLabelsSource(plot);
        builder.createAndAddPlotAndAxes(plot,
                null,
                new YAxisRenderer[]{yAxisRenderer},
                new XAxisRenderer[]{xAxisRenderer},
                null
        );

        JFrame f = new JFrame();
        f.setTitle("MindFusion.Charting sample: Multiple Axes");
        f.setSize(800, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(dashboard1);
        f.setVisible(true);
    }
}
