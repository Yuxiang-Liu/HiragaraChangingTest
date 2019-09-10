package com.example.hiragarachangetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements IHiragaraChangeView{

    private static final String TAG = "MainActivity";
    private TextView mHiragaraTextView;
    private Button mChangeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GooLabPresenter presenter = new GooLabPresenter(this);
        final EditText inputBox = findViewById(R.id.inputBox);
        inputBox.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager inputMethodManager = (InputMethodManager)textView.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    String sentence = inputBox.getText().toString();
                    if (TextUtils.isEmpty(sentence.trim())) {
                        showErrorToast(R.string.output_err_empty_input_msg);
                    } else {
                        presenter.getChangeResult(sentence);
                    }
                    Log.i(TAG, "input done, sentence change to hiragara is " + sentence);
                    return true;

                }
                return false;
            }
        });

        mHiragaraTextView = findViewById(R.id.outputBox);
        mChangeButton = findViewById(R.id.ChangeButton);
        mChangeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String sentence = inputBox.getText().toString();
                if (TextUtils.isEmpty(sentence.trim())) {
                    showErrorToast(R.string.output_err_empty_input_msg);
                } else {
                    presenter.getChangeResult(sentence);
                }
                Log.i(TAG, "onClick, sentence change to hiragara is " + sentence);
            }
        });
    }

    @Override
    public void onChangeSucceeded(final String result) {
        Log.i(TAG, "onChangeSucceeded, Change result is " + result);
         new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHiragaraTextView.setText(result);
                        Log.i(TAG, "set result to  " + result);

                    }
                });
            }
        }).start();
    }

    @Override
    public void onChangeFailure() {
        Log.i(TAG, "Change hiragara error!");
        showErrorToast(R.string.output_err_general_msg);
    }

    private void showErrorToast(int errorMsgId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast toast = Toast.makeText(MainActivity.this, errorMsgId,
                                Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                    }
                });
            }
        }).start();
    }
}
