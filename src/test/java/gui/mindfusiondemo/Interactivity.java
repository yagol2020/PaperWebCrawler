package gui.mindfusiondemo;

import com.mindfusion.charting.*;
import com.mindfusion.charting.swing.Dashboard;
import com.mindfusion.charting.swing.PieChart;
import com.mindfusion.drawing.Brush;
import com.mindfusion.drawing.Colors;
import com.mindfusion.drawing.SolidBrush;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;


public class Interactivity {
    PieChart pieChart = null;
    Dashboard dashboard = null;
    LinkedHashMap<String, Double> webSitePaperCountData;

    Dashboard initializeDashboardChart() {
        return new Dashboard();
    }

    PieChart initializePieChart() {
        PieChart chart = new PieChart();
        chart.setTitle("Pie Chart");
        chart.setBackground(Colors.WhiteSmoke);
        chart.setAllowRotate(true);
        chart.setAllowZoom(true);
        chart.setShowLegend(false);
        chart.setSeries(createPieSeries());
        chart.setShowDataLabels(EnumSet.of(LabelKinds.OuterLabel));
        chart.getSeriesRenderer().setLabelFontSize(15.0d);
        chart.setTheme(new Theme());
        chart.getTheme().setSeriesFills(pieFill());
        chart.getTheme().setHighlightStroke(new SolidBrush(Colors.White));
        chart.getTheme().setHighlightStrokeThickness(5.0d);
        chart.getTheme().setUniformSeriesStroke(new SolidBrush(Colors.GhostWhite));
        chart.getTheme().setSeriesStrokeThicknesses(toList(toList(new double[]{0})));
        chart.getTheme().setSeriesStrokes(toListBrush(toList(new Brush[]{new SolidBrush(chart.getBackground())})));

        PerElementSeriesStyle style = new PerElementSeriesStyle();
        style.setFills(createPieBrushes());
        chart.getPlot().setSeriesStyle(style);
        return chart;
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

    JTabbedPane constructJTabbedPane() {
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

    void initFrame() {
        webSitePaperCountData = new LinkedHashMap<>();
        webSitePaperCountData.put("acm", 1.0);
        webSitePaperCountData.put("springer", 2.0);
        webSitePaperCountData.put("ieee", 4.0);
        JFrame frame = new JFrame();
        frame.setTitle("Chart");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        dashboard = initializeDashboardChart();
        pieChart = initializePieChart();

        JTabbedPane tabbedPane = constructJTabbedPane();

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Interactivity interactivity = new Interactivity();
        interactivity.initFrame();
    }
}
