package com.example.lab1a;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editTextNumber);
        button = findViewById(R.id.button);
        result = findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.parseInt(editText.getText().toString());
                FermMethod method = new FermMethod(n);
                int[] res = method.run();
                String resultText = "Результат: n = " + res[0] + " * " + res[1];
                result.setText(resultText);
            }
        });
    }
}

class FermMethod {

    public int n;
    public int[] result = new int [2];

    public FermMethod(int n) {
        this.n = n;
    }

    public int [] run() {
        int a = (int) Math.ceil(Math.sqrt(this.n));
        double b;

        // Якщо число є парним
        if (this.n % 2 == 0) {
            result[0] = 2;
            result[1] = this.n / 2;
            return result;
        }

        // Якщо число є точним квадратом
        if (a * a == this.n) {
            result[0] = a;
            result[1] = a;
            return result;
        }

        while (true) {
            double tmp = (a * a) - this.n;
            b = (int) Math.sqrt(tmp);

            if (b * b == tmp) {
                break;
            }
            a++;
        }

        result[0] = (int) (a - b);
        result[1] = (int) (a + b);
        return result;
    }
}