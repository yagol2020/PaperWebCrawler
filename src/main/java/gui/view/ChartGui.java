package gui.view;

import cn.hutool.core.util.ArrayUtil;
import com.mindfusion.charting.*;
import com.mindfusion.charting.animation.Animation;
import com.mindfusion.charting.animation.AnimationTimeline;
import com.mindfusion.charting.animation.AnimationType;
import com.mindfusion.charting.swing.Dashboard;
import com.mindfusion.charting.swing.LayoutBuilder;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.FontStyle;
import com.mindfusion.drawing.SolidBrush;
import util.TypeConvertUtil;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author yagol
 * @date 17:45
 * @deprecated
 **/
public class ChartGui implements BaseGui {
    public static void main(String[] args) {
        Object[] countData = new Object[]{"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14"};
        Dashboard chart = new Dashboard();
        java.util.List<Series> seriesList = new ArrayList<>();
        Plot2D plot = new Plot2D();
        plot.setGridType(GridType.Horizontal);
        plot.setGridLineColor(new Color(220, 220, 220));

        //柱状图 横坐标
        java.util.List<Double> yearsData = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            yearsData.add((double) i);
        }
        //柱状图 纵坐标
        List<Double> y = new ArrayList<>(Arrays.asList(Objects.requireNonNull(TypeConvertUtil.objArray2Type(Double.class, countData))));
        String[] months = new String[]
                {
                        "January", "February", "March", "April", "May", "June",
                        "July", "August", "September", "October", "November", "December"
                };
        List<String> labels = new ArrayList<>(Arrays.asList(months));
        Series2D barSeries = new Series2D(yearsData, y, labels);
        seriesList.add(barSeries);
        java.util.List<Double> x1 = new ArrayList<>(yearsData);
        Double[] yValues = TypeConvertUtil.objArray2Type(Double.class, countData);
        assert yValues != null;
        List<Double> y1 = new ArrayList<>(Arrays.asList(yValues));
        String[] elements = TypeConvertUtil.objArray2Type(String.class, countData);
        assert elements != null;
        List<String> labels1 = new ArrayList<>(Arrays.asList(elements));

        Series2D lineSeries = new Series2D(x1, y1, labels1);


        ObservableList<Series> ols = new ObservableList<>(seriesList);
        BarRenderer barRenderer = new BarRenderer(ols);

        barRenderer.setSeriesStyle(new UniformSeriesStyle(
                new com.mindfusion.drawing.SolidBrush(new Color(102, 154, 204)),
                new com.mindfusion.drawing.SolidBrush(new Color(0, 52, 102)),
                2.0, com.mindfusion.drawing.DashStyle.Solid));

        java.util.List<Series> seriesList1 = new ArrayList<>();
        seriesList1.add(lineSeries);
        ObservableList<Series> ols1 = new ObservableList<>(seriesList1);
        LineRenderer lineRenderer = new LineRenderer(ols1);
        Animation animation = new Animation();
        AnimationTimeline timeline = new AnimationTimeline();
        timeline.addAnimation(
                AnimationType.PerElementAnimation, 2f, barRenderer);
        timeline.addAnimation(
                AnimationType.PerElementAnimation, 1f, lineRenderer);
        animation.addTimeline(timeline);
        animation.runAnimation();

        UniformSeriesStyle style = new UniformSeriesStyle(
                new com.mindfusion.drawing.SolidBrush(new Color(206, 0, 0)),
                new com.mindfusion.drawing.SolidBrush(new Color(206, 0, 0)), 4.0,
                com.mindfusion.drawing.DashStyle.Solid);
        lineRenderer.setSeriesStyle(style);
        plot.getSeriesRenderers().add(barRenderer);
        plot.getSeriesRenderers().add(lineRenderer);
        Axis kelvinAxis = new Axis();
        kelvinAxis.setMinValue(-20 + 273.15);
        kelvinAxis.setMaxValue(50 + 273.15);
        kelvinAxis.setInterval(5.0);
        kelvinAxis.setTitle("Kelvin");
        Axis monthAxis = new Axis();
        monthAxis.setInterval(1.0);
        monthAxis.setMinValue(0.0);
        monthAxis.setMaxValue(12.0);
        monthAxis.setTitle("Months");

        plot.setXAxis(monthAxis);

        LayoutBuilder builder = new LayoutBuilder(chart);
        chart.getTheme().setAxisLabelsFontSize(12.0);
        chart.getTheme().setAxisTitleFontSize(12.0);
        chart.getTheme().setAxisTitleFontStyle(EnumSet.of(FontStyle.BOLD));
        chart.getTheme().setDataLabelsFontSize(12.0);
        chart.getTheme().setLegendTitleFontSize(14.0);

        List<Series> sl = new ArrayList<>();
        sl.add(lineSeries);
        ObservableList<Series> olss = new ObservableList<>(sl);
        AnnotationRenderer annotationRenderer = new AnnotationRenderer(olss);
        annotationRenderer.setLabelFontSize(16.0);
        annotationRenderer.setLabelBrush(new SolidBrush(new Color(206, 0, 0)));

        UniformSeriesStyle aStyle = new UniformSeriesStyle(
                new com.mindfusion.drawing.SolidBrush(new Color(224, 233, 233)),
                new com.mindfusion.drawing.SolidBrush(new Color(0, 52, 102)), 2.0,
                com.mindfusion.drawing.DashStyle.Solid);
        annotationRenderer.setSeriesStyle(aStyle);
        plot.getSeriesRenderers().add(annotationRenderer);

        XAxisRenderer xAxisRenderer = new XAxisRenderer(monthAxis);
        xAxisRenderer.setLabelsSource(plot);
        builder.createAndAddPlotAndAxes(plot,
                null,
                new YAxisRenderer[]{new YAxisRenderer(kelvinAxis)},
                new XAxisRenderer[]{xAxisRenderer}, new YAxisRenderer[]{});

        JFrame f = new JFrame();
        f.setSize(1100, 600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.add(chart);
        f.setVisible(true);
    }

    @Override
    public void start(HashMap<String, Object> data) {

    }

    @Override
    public BaseGui init() {
        return null;
    }

    @Override
    public void show() {

    }

    @Override
    public void initComponentFunctions() {

    }
}
