package com.example.lab2a;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    private CombinedChart chart;
    private final int count = 12;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        Bundle arguments = getIntent().getExtras();
        double[] weights = (double[]) arguments.get("weights");
        int limit = (int) arguments.get("limit");

        chart = findViewById(R.id.chart);
        textView = findViewById(R.id.textResult);
        textView.setText("Result:\nw1 = " + weights[0] + "\nw2 = " + weights[1] + "\nw_bias = " + weights[2]);

        chart.getDescription().setEnabled(false);
        chart.setBackgroundColor(Color.WHITE);
        chart.setDrawGridBackground(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);

        xAxis.setGranularity(1f);

        CombinedData data = new CombinedData();

        data.setData(generateLineData(weights, limit));
        data.setData(generateScatterData());

        xAxis.setAxisMaximum(data.getXMax() + 0.25f);
        xAxis.setAxisMinimum(-1);

        chart.setData(data);
        chart.invalidate();
    }

    private LineData generateLineData(double[] weights, int limit) {

        LineData d = new LineData();

        ArrayList<Entry> entries = new ArrayList<>();

        for (int x = 0; x < count; x++)
            entries.add(new Entry(x, y(x, limit, weights)));

        LineDataSet set = new LineDataSet(entries, "Perceptron`s guess");

        set.setColor(Color.rgb(255, 0, 0));
        set.setLineWidth(2.5f);

        set.setDrawCircles(false);
        set.setCircleColor(Color.rgb(0, 0, 255));
        set.setDrawValues(false);

        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        d.addDataSet(set);

        return d;
    }

    private ScatterData generateScatterData() {

        ScatterData d = new ScatterData();

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 6));
        entries.add(new Entry(1, 5));
        entries.add(new Entry(2, 4));
        entries.add(new Entry(3, 3));

        ScatterDataSet set = new ScatterDataSet(entries, "Points: A, B, C, D");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setScatterShapeSize(15.5f);
        set.setScatterShape(ScatterChart.ScatterShape.CIRCLE);
        set.setDrawValues(false);

        d.addDataSet(set);

        return d;
    }

    private float y(int x, int limit, double[] weights) {
        return (float) ((limit - weights[0] * x - weights[2]) / weights[1]);
    }
}

