package com.example.hiragarachangetest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements IHiragaraChangeView{

    private final String TAG = "MainActivity";
    private TextView mHiragaraTextView;
    private Button mChangeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GooLabPresenter presenter = new GooLabPresenter(this);
        final EditText inputBox = findViewById(R.id.inputBox);
        mHiragaraTextView = findViewById(R.id.outputBox);
        mChangeButton = findViewById(R.id.ChangeButton);
        mChangeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String sentence = inputBox.getText().toString();
                presenter.getChangeResult(sentence);
                Log.i(TAG, "onClick, sentence change to hiragara is " + sentence);
            }
        });
    }

    @Override
    public void onChangeSucceeded(final String result) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHiragaraTextView.setText(result);
                        Log.i(TAG, "onChangeSucceeded, Change result is " + result);

                    }
                });
            }
        });
    }

    @Override
    public void onChangeFailure() {
            Log.i(TAG, "Change hiragara error!");
    }
}
