package com.example.lab2a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private EditText editTextLimit;
    private EditText editTextLearnRate;
    private EditText editTextIterations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        editTextLimit = findViewById(R.id.editTextLimit);
        editTextLearnRate = findViewById(R.id.editTextLearRate);
        editTextIterations = findViewById(R.id.editTextIterations);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String limitText = editTextLimit.getText().toString();
                String learnRateText = editTextLearnRate.getText().toString();
                String iterationsText = editTextIterations.getText().toString();

                int limit;
                double learnRate;
                int iterations;
                if (limitText.equals("") || learnRateText.equals("") || iterationsText.equals("")) {
                    Toast.makeText(getApplicationContext(), "Fields can`t be empty!", Toast.LENGTH_LONG).show();
                } else {

                    limit = Integer.parseInt(limitText);
                    learnRate = Double.parseDouble(learnRateText);
                    iterations = Integer.parseInt(iterationsText);

                    Point[] points = {
                            new Point(0, 6, limit),
                            new Point(1, 5, limit),
                            new Point(2, 4, limit),
                            new Point(3, 3, limit)
                    };

                    Network network = new Network(points, limit, learnRate, iterations);
                    double[] weights = network.train();

                    Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                    intent.putExtra("weights", weights);
                    intent.putExtra("limit", limit);
                    startActivity(intent);
                }
            }
        });
    }
}