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
 */
public class MultipleAxes
{
	public static void main(String[] args)
	{
		com.mindfusion.charting.swing.Dashboard dashboard1 = new com.mindfusion.charting.swing.Dashboard();
		Plot2D plot = new Plot2D();
		plot.setGridType(GridType.Horizontal);
		plot.setGridLineColor(new Color(220, 220, 220));
		
		List<Double> x = new ArrayList<Double>();
		for(int i = 1; i<=12; i++)
			x.add((double) i);
		List<Double> y = new ArrayList<Double>();
		y.add(-3.0);
		y.add(-7.0);
		y.add(10.0);
		y.add(12.0);
		y.add(20.0);
		y.add(29.0);
		y.add(33.0);
		y.add(30.0);
		y.add(24.0);
		y.add(15.0);
		y.add(2.0);
		y.add(-8.0);
		String[] months = new String[]
		{
			"January", "February", "March", "April", "May", "June",
			"July", "August", "September", "Octomber", "November", "December"
		};
		List<String> labels = new ArrayList<String>();
		for(int i = 0; i<months.length; i++)
			labels.add(months[i]);
		 
		Series2D barSeries = new Series2D(x, y, labels);
		barSeries.setTitle("Temperature");
		barSeries.setSupportedLabels(LabelKinds.XAxisLabel);

		List<Double> x1 = new ArrayList<Double>(x);
		double[] yValues = new double[]{ 100120, 101044, 101503, 103499, 102122, 100593, 
				95542, 102223, 103002, 102994, 102843, 101332};
		List<Double> y1 = new ArrayList<Double>();
		for(int i = 0; i<yValues.length; i++)
			y1.add(yValues[i]);
		String[] elements = new String[]{ "", "", "", "", "", "", "TORNADO", "", "", "", "", ""};
		List<String> labels1 = new ArrayList<String>();
		for(int i = 0; i<elements.length; i++)
			labels1.add(elements[i]);
		
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

		// animate bars, and then lines
		Animation animation = new Animation();
		AnimationTimeline timeline = new AnimationTimeline();
		timeline.addAnimation(
			AnimationType.PerElementAnimation, 2f, (Renderer2D) barRenderer);
		//AnimationTimeline timeline2 = new AnimationTimeline();
		timeline.addAnimation(
			AnimationType.PerElementAnimation, 1f, (Renderer2D) lineRenderer);
		animation.addTimeline(timeline);
		//animation.addTimeline(timeline2);
		animation.runAnimation();

		UniformSeriesStyle style = new UniformSeriesStyle(
				new com.mindfusion.drawing.SolidBrush(new Color (206, 0, 0)),
				new com.mindfusion.drawing.SolidBrush(new Color(206, 0, 0)), 4.0,
				com.mindfusion.drawing.DashStyle.Solid);		
		lineRenderer.setSeriesStyle(style);		
		
		plot.getSeriesRenderers().add(barRenderer);
		plot.getSeriesRenderers().add(lineRenderer);

		Axis celsiusAxis = new Axis();
		celsiusAxis.setInterval(5.0);
		celsiusAxis.setMinValue(-20.0);
		celsiusAxis.setMaxValue(50.0);
		celsiusAxis.setTitle("Celsius");
		Axis fahrenheitAxis = new Axis();
		fahrenheitAxis.setMinValue(-20 * 1.8 + 32);
		fahrenheitAxis.setMaxValue(50 * 1.8 + 32);
		fahrenheitAxis.setInterval(10.0);
		fahrenheitAxis.setTitle("Fahrenheit");
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
		Axis pasquals = new Axis();
		pasquals.setMinValue(90000.0);
		pasquals.setMaxValue(106000.0);
		pasquals.setInterval(1000.0);
		pasquals.setTitle("Pressure");

		YAxisRenderer celsiusRenderer = new YAxisRenderer(celsiusAxis);
		plot.setYAxis(celsiusAxis);
		plot.setXAxis(monthAxis);
		lineRenderer.setYAxis(pasquals);

		com.mindfusion.charting.swing.LayoutBuilder builder = new com.mindfusion.charting.swing.LayoutBuilder(dashboard1);
		dashboard1.getTheme().setAxisLabelsFontSize(12.0);
		dashboard1.getTheme().setAxisTitleFontSize(12.0);
		dashboard1.getTheme().setAxisTitleFontStyle(EnumSet.of(FontStyle.BOLD));
		dashboard1.getTheme().setDataLabelsFontSize(12.0);
		dashboard1.getTheme().setLegendTitleFontSize(14.0);
		
		LegendRenderer legendRenderer = new LegendRenderer();
		List<SeriesRenderer> seriesRendererList = new ArrayList<SeriesRenderer>();
		seriesRendererList.add(barRenderer);
		seriesRendererList.add(lineRenderer);
		ObservableList<SeriesRenderer> olsr = new ObservableList<SeriesRenderer>(seriesRendererList);
		legendRenderer.setContent(olsr);
		legendRenderer.setBackground(new SolidBrush(new Color (235, 235, 235)));
		legendRenderer.setAllowMove(true);
		
		dashboard1.getRootPanel().getChildren().add(legendRenderer);
		
		List<Series> sl = new ArrayList<Series>();
		sl.add(lineSeries);
		ObservableList<Series> olss = new ObservableList<Series>(sl);
		AnnotationRenderer annotationRenderer = new AnnotationRenderer(olss);
		annotationRenderer.setYAxis(pasquals);
		annotationRenderer.setLabelFontSize(16.0);
		annotationRenderer.setLabelBrush(new SolidBrush(new Color(206, 0, 0)));
		
		//customize the appearance of the annotation
		UniformSeriesStyle aStyle = new UniformSeriesStyle(
				new com.mindfusion.drawing.SolidBrush(new Color (224, 233, 233)),
				new com.mindfusion.drawing.SolidBrush(new Color(0, 52, 102)), 2.0,
				com.mindfusion.drawing.DashStyle.Solid);	
		annotationRenderer.setSeriesStyle(aStyle);
		plot.getSeriesRenderers().add(annotationRenderer);
		
		XAxisRenderer xAxisRenderer =  new XAxisRenderer(monthAxis);
		xAxisRenderer.setLabelsSource(plot);
		//builder.CreateAndAddPlotAndAxes(plot, null, new YAxisRenderer[] { celsiusRenderer, new YAxisRenderer(fahrenheitAxis), new YAxisRenderer(kelvinAxis)}, new XAxisRenderer[] { new XAxisRenderer(monthAxis)
		//}, new YAxisRenderer[]{new YAxisRenderer(pasquals)});
		builder.createAndAddPlotAndAxes(plot, null, new YAxisRenderer[] { celsiusRenderer, 
				new YAxisRenderer(fahrenheitAxis), 
				new YAxisRenderer(kelvinAxis) }, 
				new XAxisRenderer[] {xAxisRenderer}, 
				new YAxisRenderer[] { new YAxisRenderer(pasquals) });
		
		JFrame f = new JFrame();
		f.setTitle("MindFusion.Charting sample: Multiple Axes");
		f.setSize(800, 600);
		f.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		f.add(dashboard1);
		f.setVisible(true);
	}
}
