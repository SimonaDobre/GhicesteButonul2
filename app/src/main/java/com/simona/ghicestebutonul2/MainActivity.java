package com.simona.ghicestebutonul2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText btnCntEditText;
    int numberOfButtons, winnerNumber;
    LinearLayout btnContainerLinearLayout;
    ArrayList<Button> buttons = new ArrayList<>();
    Button btnGenerate, yesBtn, noBtn;
    TextView tvInfo1, tvInfo2, tvInfo3;
    LinearLayout inputNumLinearLayout, playAgainLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }


    void generateButtons() {
        int numberOfRows = numberOfButtons / 4; // pe cate randuri vor fi afisate butoanele
        int k = 0;
        if (numberOfButtons % 4 != 0) {
            numberOfRows = numberOfButtons / 4 + 1;
        }
        for (int i = 0; i < numberOfRows; i++) {
            LinearLayout row = new LinearLayout(this);
            row.setWeightSum(4);
            LinearLayout.LayoutParams rowParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT, 4.0f);
            row.setLayoutParams(rowParam);
            for (int j = 0; j < 4; j++) { // afiseaza 4 butoane pe fiecare rand
                Button b = new Button(this);
                b.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f));
                if (k < numberOfButtons) {
                    b.setText("Buton " + (k));
                    b.setTag(k);
                    buttons.add(b);
                    buttons.get(k).setOnClickListener(this::onClick);
                    k++;
                    row.addView(b);
                } else {
                    k++;
                    row.addView(b);
                    buttons.add(b); // ca sa-l pot face GONE in cazul unui newGame
                    b.setVisibility(View.INVISIBLE);
                }
            }
            btnContainerLinearLayout.addView(row);
        }
        hideKeyboard();
   }

    @Override
    public void onClick(View view) {
        Button clickedbutton = (Button) view;
        int clickedTag = -1;
        if (clickedbutton.getTag().toString().equals("generateBtn")) {
            try {
                numberOfButtons = Integer.parseInt(btnCntEditText.getText().toString());
                tvInfo1.setText(" Ai ales sa generezi " + numberOfButtons + " butoane");
                setWinnerNumber();
                generateButtons();
                btnCntEditText.setText(null);
                inputNumLinearLayout.setVisibility(View.INVISIBLE);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else if (clickedbutton.getTag().toString().equals("yesBtn")) {
            newGame();
        } else if (clickedbutton.getTag().toString().equals("noBtn")) {
            exitApp();
        } else {
            clickedTag = Integer.parseInt((clickedbutton.getTag().toString()));
            Log.i("clickat pe ", clickedTag + "");
            if (clickedTag == winnerNumber) {
                tvInfo3.setText(" Ai castigat!");
                playAgainLinearLayout.setVisibility(View.VISIBLE);
                for (Button b: buttons){
                    b.setEnabled(false);
                }
            } else {
                tvInfo3.setText(" Butonul " + clickedTag + " este necastigator.");
            }
        }
    }

    void exitApp(){
        MainActivity.this.finish();
        System.exit(0);
    }

    void newGame() {
        inputNumLinearLayout.setVisibility(View.VISIBLE);
        playAgainLinearLayout.setVisibility(View.INVISIBLE);
        for (Button b: buttons){
            b.setVisibility(View.GONE);
        }
        buttons.clear();
        tvInfo1.setText(null);
        tvInfo2.setText(null);
        tvInfo3.setText(null);
    }

    void hideKeyboard() {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(btnCntEditText.getWindowToken(), 0);
    }

    void setWinnerNumber() {
        winnerNumber = new Random().nextInt(numberOfButtons);
        tvInfo2.setText(" Hint: nr castigator este " + winnerNumber);
    }

    void initViews() {
        btnCntEditText = findViewById(R.id.btnCntEditText);
        btnContainerLinearLayout = findViewById(R.id.btnContainerLinearLayout);
        btnGenerate = findViewById(R.id.btnGenerate);
        btnGenerate.setOnClickListener(this::onClick);
        yesBtn = findViewById(R.id.yesButton);
        yesBtn.setOnClickListener(this::onClick);
        noBtn = findViewById(R.id.noButton);
        noBtn.setOnClickListener(this::onClick);
        tvInfo1 = findViewById(R.id.tvInfo1TextView);
        tvInfo2 = findViewById(R.id.tvInfo2TextView);
        tvInfo3 = findViewById(R.id.tvInfo3TextView);
        inputNumLinearLayout = findViewById(R.id.inputNumLinearLayout);
        playAgainLinearLayout = findViewById(R.id.playAgainLinearLayout);
        playAgainLinearLayout.setVisibility(View.INVISIBLE);
    }

}