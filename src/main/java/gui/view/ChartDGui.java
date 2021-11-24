package gui.view;

import bean.analysis.CountDataOneYear;
import bean.analysis.CountDataPerYear;
import com.mindfusion.charting.*;
import com.mindfusion.charting.animation.Animation;
import com.mindfusion.charting.animation.AnimationTimeline;
import com.mindfusion.charting.animation.AnimationType;
import com.mindfusion.charting.swing.Dashboard;
import com.mindfusion.charting.swing.LayoutBuilder;
import com.mindfusion.charting.swing.PieChart;
import com.mindfusion.common.ObservableList;
import com.mindfusion.drawing.Brush;
import com.mindfusion.drawing.Colors;
import com.mindfusion.drawing.FontStyle;
import com.mindfusion.drawing.SolidBrush;
import gui.bean.ChartData;
import util.TypeConvertUtil;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * 图表gui
 *
 * @author yagol
 */
public class ChartDGui implements BaseGui {
    PieChart pieChart = null;
    Dashboard dashboard = null;
    LinkedHashMap<String, Double> webSitePaperCountData;
    JFrame frame;


    void initializePieChart(LinkedHashMap<String, Double> data) {
        webSitePaperCountData = data;
        pieChart = new PieChart();
        pieChart.setTitle("Pie Chart");
        pieChart.setBackground(Colors.WhiteSmoke);
        pieChart.setAllowRotate(true);
        pieChart.setAllowZoom(true);
        pieChart.setShowLegend(false);
        pieChart.setSeries(createPieSeries());
        pieChart.setShowDataLabels(EnumSet.of(LabelKinds.OuterLabel));
        pieChart.getSeriesRenderer().setLabelFontSize(15.0d);
        pieChart.setTheme(new Theme());
        pieChart.getTheme().setSeriesFills(pieFill());
        pieChart.getTheme().setHighlightStroke(new SolidBrush(Colors.White));
        pieChart.getTheme().setHighlightStrokeThickness(5.0d);
        pieChart.getTheme().setUniformSeriesStroke(new SolidBrush(Colors.GhostWhite));
        pieChart.getTheme().setSeriesStrokeThicknesses(toList(toList(new double[]{0})));
        pieChart.getTheme().setSeriesStrokes(toListBrush(toList(new Brush[]{new SolidBrush(pieChart.getBackground())})));

        PerElementSeriesStyle style = new PerElementSeriesStyle();
        style.setFills(createPieBrushes());
        pieChart.getPlot().setSeriesStyle(style);
    }

    List<List<Brush>> createPieBrushes() {
        List<Brush> fills = new ArrayList<Brush>();
        fills.add(new SolidBrush(new Color(224, 233, 233)));
        fills.add(new SolidBrush(new Color(102, 154, 204)));
        fills.add(new SolidBrush(new Color(206, 0, 0)));
        fills.add(new SolidBrush(new Color(45, 57, 86)));

        List<List<Brush>> pieBrushes = new ArrayList<List<Brush>>();
        pieBrushes.add(fills);

        return pieBrushes;
    }

    JTabbedPane constructTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("论文发表", dashboard);
        tabbedPane.addTab("网站统计", pieChart);
        return tabbedPane;
    }


    Series createPieSeries() {
        PieSeries series = new PieSeries(
                new ArrayList<>(webSitePaperCountData.values()),
                new ArrayList<>(webSitePaperCountData.keySet()),
                new ArrayList<>(webSitePaperCountData.keySet()));
        series.setTitle("Pie Series");
        return series;
    }

    List<Double> toList(double[] arr) {
        List<Double> list = new ArrayList<>(arr.length);
        for (double v : arr) {
            list.add(v);
        }
        return list;
    }

    List<List<Double>> toList(List<Double> list) {
        List<List<Double>> rList = new ArrayList<>(list.size());
        rList.add(list);
        return rList;
    }

    List<Brush> toList(Brush[] arr) {
        List<Brush> list = new ArrayList<>(arr.length);
        Collections.addAll(list, arr);
        return list;
    }

    List<List<Brush>> toListBrush(List<Brush> list) {
        List<List<Brush>> rList = new ArrayList<>(list.size());
        rList.add(list);
        return rList;
    }


    List<List<Brush>> pieFill() {
        List<Brush> fills = new ArrayList<Brush>();
        fills.add(new SolidBrush(Colors.RosyBrown));
        fills.add(new SolidBrush(Colors.Coral));
        fills.add(new SolidBrush(Colors.Crimson));
        fills.add(new SolidBrush(Colors.DarkRed));

        List<List<Brush>> lists = new ArrayList<List<Brush>>();
        lists.add(fills);

        return lists;
    }


    @Override
    public void start(HashMap<String, Object> data) {
        if (data.containsKey(ChartData.class.getSimpleName())) {
            if (data.get(ChartData.class.getSimpleName()) instanceof ChartData) {
                ChartData chartData = (ChartData) data.get(ChartData.class.getSimpleName());
                initDashboard(chartData.getPaperInfo());
                initializePieChart(chartData.getWebsiteInfos());
                JTabbedPane tabbedPane = constructTabbedPane();
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
            }
        }
    }

    private void initDashboard(HashMap<String, Object> data) {
        Plot2D plot = new Plot2D();
        plot.setGridType(GridType.Horizontal);
        plot.setGridLineColor(new Color(220, 220, 220));
        dashboard = new Dashboard();
        if (data.containsKey(CountDataPerYear.class.getSimpleName())) {
            if (data.get(CountDataPerYear.class.getSimpleName()) instanceof CountDataPerYear) {
                CountDataPerYear countDataPerYear = (CountDataPerYear) data.get(CountDataPerYear.class.getSimpleName());
                List<CountDataOneYear> countDataOneYearData = countDataPerYear.getData();
                List<Double> xData = new ArrayList<>();
                Object[] yData = new Object[countDataOneYearData.size()];
                String[] xDescription = new String[countDataOneYearData.size()];
                int j = 0;
                for (int i = countDataPerYear.getStartYear(); i <= countDataPerYear.getEndYear(); i++, j++) {
                    xData.add((double) i);
                    yData[j] = countDataOneYearData.get(j).getCount();
                    xDescription[j] = String.valueOf(i);
                }
                List<Double> y = new ArrayList<>(Arrays.asList(Objects.requireNonNull(TypeConvertUtil.objArray2Type(Double.class, yData))));
                List<String> labels = new ArrayList<>(Arrays.asList(xDescription));
                Series2D barSeries = new Series2D(xData, y, labels);
                List<Series> seriesList = new ArrayList<>();
                seriesList.add(barSeries);
                //柱状图 纵坐标
                List<Double> x1 = new ArrayList<>(xData);
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
                        new SolidBrush(new Color(102, 154, 204)),
                        new SolidBrush(new Color(0, 52, 102)),
                        2.0, com.mindfusion.drawing.DashStyle.Solid));
                List<Series> seriesList1 = new ArrayList<>();
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
                UniformSeriesStyle style = new UniformSeriesStyle(
                        new SolidBrush(new Color(206, 0, 0)),
                        new SolidBrush(new Color(206, 0, 0)), 4.0,
                        com.mindfusion.drawing.DashStyle.Solid);
                lineRenderer.setSeriesStyle(style);
                plot.getSeriesRenderers().add(barRenderer);
                plot.getSeriesRenderers().add(lineRenderer);

                Axis countOfPaperAxis = new Axis();
                countOfPaperAxis.setInterval(1.0);
                //文献的最低数量是0
                countOfPaperAxis.setMinValue(0.0);
                countOfPaperAxis.setTitle("Count of Paper");

                Axis yearsAxis = new Axis();
                yearsAxis.setInterval(1.0);
                yearsAxis.setTitle("Years");

                plot.setXAxis(yearsAxis);
                plot.setYAxis(countOfPaperAxis);

                barRenderer.setYAxis(countOfPaperAxis);
                lineRenderer.setYAxis(countOfPaperAxis);


                LayoutBuilder builder = new LayoutBuilder(dashboard);
                dashboard.getTheme().setAxisLabelsFontSize(12.0);
                dashboard.getTheme().setAxisTitleFontSize(12.0);
                dashboard.getTheme().setAxisTitleFontStyle(EnumSet.of(FontStyle.BOLD));
                dashboard.getTheme().setDataLabelsFontSize(12.0);
                dashboard.getTheme().setLegendTitleFontSize(14.0);

                List<Series> sl = new ArrayList<>();
                sl.add(lineSeries);
                ObservableList<Series> observableList = new ObservableList<>(sl);
                AnnotationRenderer annotationRenderer = new AnnotationRenderer(observableList);
                annotationRenderer.setLabelFontSize(16.0);
                annotationRenderer.setLabelBrush(new SolidBrush(new Color(206, 0, 0)));

                UniformSeriesStyle aStyle = new UniformSeriesStyle(
                        new SolidBrush(new Color(224, 233, 233)),
                        new SolidBrush(new Color(0, 52, 102)), 2.0,
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
            }
        }
    }

    @Override
    public ChartDGui init() {
        frame = new JFrame();
        frame.setTitle("论文信息统计");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        return this;
    }

    @Override
    public void show() {
        frame.setVisible(true);
    }

    @Override
    public void initComponentFunctions() {

    }
}
