package gui.view;

import bean.analysis.CountDataOneYear;
import bean.analysis.CountDataPerYear;
import bean.result.BaseResult;
import bean.result.PaperInfo;
import cn.hutool.core.collection.CollectionUtil;
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
 * @description
 **/
public class ChartGui implements BaseGui {
    /**
     * x轴数据
     */
    java.util.List<Double> xData;
    /**
     * y轴数据
     */
    Object[] yData;
    /**
     * x轴的说明，目前被隐藏了，但是数量需要徐x轴元素数量相同
     */
    String[] xDescription;
    Dashboard chart;
    java.util.List<Series> seriesList;
    Plot2D plot;
    JFrame f;
    List<Double> y;
    List<String> labels;

    @Override
    public void start(HashMap<String, Object> data) {
        f = new JFrame();
        plot = new Plot2D();
        plot.setGridType(GridType.Horizontal);
        plot.setGridLineColor(new Color(220, 220, 220));
        f.setSize(1100, 600);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chart = new Dashboard();

        y = new ArrayList<>();
        if (data.containsKey(CountDataPerYear.class.getSimpleName())) {
            if (data.get(CountDataPerYear.class.getSimpleName()) instanceof CountDataPerYear) {
                CountDataPerYear countDataPerYear = (CountDataPerYear) data.get(CountDataPerYear.class.getSimpleName());
                List<CountDataOneYear> countDataOneYearData = countDataPerYear.getData();
                xData = new ArrayList<>();
                yData = new Object[countDataOneYearData.size()];
                xDescription = new String[countDataOneYearData.size()];
                int j = 0;
                for (int i = countDataPerYear.getStartYear(); i <= countDataPerYear.getEndYear(); i++, j++) {
                    xData.add((double) i);
                    yData[j] = countDataOneYearData.get(j).getCount();
                    xDescription[j] = String.valueOf(i);
                }
                y = new ArrayList<>(Arrays.asList(Objects.requireNonNull(TypeConvertUtil.objArray2Type(Double.class, yData))));
                labels = new ArrayList<>(Arrays.asList(xDescription));
                Series2D barSeries = new Series2D(xData, y, labels);
                seriesList = new ArrayList<>();
                seriesList.add(barSeries);
                //柱状图 纵坐标
                java.util.List<Double> x1 = new ArrayList<>(xData);
                Double[] yValues = TypeConvertUtil.objArray2Type(Double.class, yData);
                assert yValues != null;
                List<Double> y1 = new ArrayList<>(Arrays.asList(yValues));
                String[] elements = TypeConvertUtil.objArray2Type(String.class, yData);
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

                Axis countOfPaperAxis = new Axis();
                countOfPaperAxis.setInterval(1.0);
                countOfPaperAxis.setTitle("Count of Paper");

                Axis yearsAxis = new Axis();
                yearsAxis.setInterval(1.0);
                yearsAxis.setTitle("Years");

                plot.setXAxis(yearsAxis);
                plot.setYAxis(countOfPaperAxis);

                barRenderer.setYAxis(countOfPaperAxis);
                lineRenderer.setYAxis(countOfPaperAxis);


                LayoutBuilder builder = new LayoutBuilder(chart);
                chart.getTheme().setAxisLabelsFontSize(12.0);
                chart.getTheme().setAxisTitleFontSize(12.0);
                chart.getTheme().setAxisTitleFontStyle(EnumSet.of(FontStyle.BOLD));
                chart.getTheme().setDataLabelsFontSize(12.0);
                chart.getTheme().setLegendTitleFontSize(14.0);

                List<Series> sl = new ArrayList<>();
                sl.add(lineSeries);
                ObservableList<Series> observableList = new ObservableList<>(sl);
                AnnotationRenderer annotationRenderer = new AnnotationRenderer(observableList);
                annotationRenderer.setLabelFontSize(16.0);
                annotationRenderer.setLabelBrush(new SolidBrush(new Color(206, 0, 0)));

                UniformSeriesStyle aStyle = new UniformSeriesStyle(
                        new com.mindfusion.drawing.SolidBrush(new Color(224, 233, 233)),
                        new com.mindfusion.drawing.SolidBrush(new Color(0, 52, 102)), 2.0,
                        com.mindfusion.drawing.DashStyle.Solid);
                annotationRenderer.setSeriesStyle(aStyle);
                plot.getSeriesRenderers().add(annotationRenderer);

                XAxisRenderer xAxisRenderer = new XAxisRenderer(yearsAxis);
                xAxisRenderer.setLabelsSource(plot);
                builder.createAndAddPlotAndAxes(plot,
                        null,
                        //左
                        new YAxisRenderer[]{new YAxisRenderer(countOfPaperAxis)},
                        //下
                        new XAxisRenderer[]{xAxisRenderer},
                        //右
                        null
                );
                f.add(chart);
            }
        }
    }

    @Override
    public ChartGui init() {
        f = new JFrame();
        plot=new Plot2D();
        plot.setGridType(GridType.Horizontal);
        plot.setGridLineColor(new Color(220, 220, 220));
        f.setSize(1100, 600);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        return this;
    }

    @Override
    public void show() {
        f.setVisible(true);
    }

    @Override
    public void initComponentFunctions() {

    }
}
