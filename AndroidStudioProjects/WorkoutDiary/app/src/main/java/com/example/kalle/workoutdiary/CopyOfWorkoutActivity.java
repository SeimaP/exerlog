package com.example.kalle.workoutdiary;

import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static android.graphics.Color.WHITE;
import static android.graphics.Color.rgb;

public class CopyOfWorkoutActivity extends MainActivity {


    Button addExerciseButton;
    Button saveButton;
    Button one;
    Button startNewWorkoutButton;
    LinearLayout row;
    LinearLayout exerciseRowContainer;
    ScrollView rowScroller;
    TextView volumeLabel;
    ArrayList<LinearLayout> rowList = new ArrayList<LinearLayout>();
    ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
    ArrayList<ArrayList> containerFortestxArray = new ArrayList<ArrayList>();
    ArrayList<EditText> textFieldArray;
    int numberOfExercises;
    int vol;

    MainActivity main;

    ArrayList<Exercise> savedExercisez = new ArrayList<Exercise>();


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            buildit();
        setUp();
    }

    public void setUp() {



        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int thirdWidth = (int)(width*0.333333);

        LinearLayout testLayout = new LinearLayout(this);
        testLayout.setBackgroundColor(rgb(43, 43, 43));
        setContentView(testLayout);

        LinearLayout totalLayout = new LinearLayout(this);
        totalLayout.setOrientation(LinearLayout.VERTICAL);


        //Make rows for labels, exercise rows and button
        row = new LinearLayout(this);
        row.setOrientation(LinearLayout.VERTICAL);

        // makeLinearLayoutParams(row);


        LinearLayout topBar = new LinearLayout(this);
        topBar.setBackgroundColor(Color.rgb(10,10,10));

        LinearLayout layoutTop = new LinearLayout(this);
        //layoutTop.setBackgroundColor(Color.BLUE);

        LinearLayout layoutTop2 = new LinearLayout(this);
        layoutTop2.setBackgroundColor(Color.GREEN);
        LinearLayout layoutTop3 = new LinearLayout(this);
        layoutTop3.setBackgroundColor(Color.WHITE);


        int widths = topBar.getWidth();

        layoutTop2.setMinimumHeight(100);
        layoutTop2.setMinimumWidth(thirdWidth);
        layoutTop3.setMinimumHeight(100);
        layoutTop3.setMinimumWidth(thirdWidth);
        layoutTop.setMinimumHeight(100);
        layoutTop.setMinimumWidth(thirdWidth);

        saveButton = new Button(this);
        startNewWorkoutButton = new Button(this);
        one = new Button(this);

        ImageView tick = new ImageView(this);
        tick.setImageResource(R.mipmap.ticks);

        layoutTop.addView(saveButton);
        layoutTop2.addView(startNewWorkoutButton);
        layoutTop3.addView(one);



        //saveButton.setBackgroundResource(R.mipmap.ticks);
        saveButton.setText("✓");
        saveButton.setTextSize(25);
        saveButton.setTextColor(Color.RED);
        saveButton.setBackgroundColor(topBar.getSolidColor());



        GridLayout grid = new GridLayout(this);
        //grid.setBackgroundColor(Color.RED);
        grid.setOrientation(GridLayout.HORIZONTAL);
        grid.addView(layoutTop);
        grid.addView(layoutTop2);
        grid.addView(layoutTop3);

        topBar.addView(grid);
        topBar.setMinimumHeight(100);




        // Make layout for labels
        LinearLayout labelrow = new LinearLayout(this);
        makeLabels(labelrow);

        volumeLabel = new TextView(this);
        volumeLabel.setText("Total Volume: 0");
        volumeLabel.setTextColor(WHITE);
        LinearLayout volumeRow = new LinearLayout(this);



        // Make container for Exercise rows
        exerciseRowContainer = new LinearLayout(this);
        exerciseRowContainer.setOrientation(LinearLayout.VERTICAL);
        rowScroller = new ScrollView(this);
        rowScroller.setLayoutParams(new RelativeLayout.LayoutParams(
                ScrollView.LayoutParams.FILL_PARENT, 900));

        // make save button
        MyButton makbtn = new MyButton();
        addExerciseButton = new Button(this);
        makbtn.makeButton(addExerciseButton);
        addExerciseButton.setText("Add");


        // Button Parameter creator
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        makeButtonAndTextRowParams(buttonParams);

        int listlength = savedExercisez.size();

        ArrayList<LinearLayout> textFieldRows = new ArrayList<LinearLayout>();

        if (listlength != 0) {
            for (int s = 0; s < listlength; s++) {
                System.out.println("We are in the matrix: " + s);
                rowList.add(new LinearLayout(this));
                textFieldRows.add(new LinearLayout(getBaseContext()));
                //LinearLayout textFieldRows = rowList.get(numberOfExercises);
                LinearLayout rowsss = textFieldRows.get(s);
                makeTextFields(rowsss);
                exerciseRowContainer.addView(rowsss, buttonParams);
                numberOfExercises = numberOfExercises + 1;

                Exercise ex = savedExercisez.get(s);

                for (int x = 0; x < 5; x++) {
                    EditText text = textFieldArray.get(x);
                    if (x == 0) {
                        text.setText(ex.getName());
                    } if (x == 1) {
                        int r = ex.getReps();
                        text.setText(Integer.toString(r));
                    } if (x == 2) {
                        int r = ex.getSets();
                        text.setText(Integer.toString(r));
                    } if (x == 3) {
                        int r = ex.getWeight();
                        text.setText(Integer.toString(r));
                    } if (x == 4) {
                        int r = ex.getRest();
                        text.setText(Integer.toString(r));
                    }
                }
            }
        } else {
                System.out.println("We are NOT in the matrix!!");
                rowList.add(new LinearLayout(this));
                LinearLayout textFieldRowz = rowList.get(numberOfExercises);
                makeTextFields(textFieldRowz);
                exerciseRowContainer.addView(textFieldRowz, buttonParams);
                numberOfExercises = numberOfExercises + 1;
        }
        System.out.println("We left the matrix");


        rowScroller.addView(exerciseRowContainer);

        makeLinearLayoutParams(volumeRow);
        volumeRow.addView(volumeLabel);
        makeLinearLayoutParamsTopBar(totalLayout);

        row.addView(topBar);
        row.addView(labelrow);
        row.addView(rowScroller);
        row.addView(addExerciseButton, buttonParams);
        row.addView(volumeRow);
        // totalLayout.addView(topBar);
        totalLayout.addView(row);
        testLayout.addView(totalLayout);


    }

    int c;
    public void buildit() {




            int x = numWorkouts;
            System.out.println("This is numWorkouts: " + x);

            String file1 = "Exercises" + (x -2);

            System.out.println("You are currently creating this workout: " + file1);



            System.out.println("This is something you are now printing: " + getFilesDir());


        File f = new File(file1);
        if(f.exists() == true) {

        } else {


            FileInputStream fis = null;
            try {
                fis = openFileInput(file1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);




            try {
                StringBuffer sb = new StringBuffer();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    c++;
                    System.out.println("This is how many times it runs " + c);
                    sb.append(line);
                    String text = (String) line;
                    System.out.println("This is the total lines int this new one: " + text);

                    String[] data = text.split("END");

                    for (int d = 0; d < data.length; d++) {
                        System.out.println("This is current: " + data[d]);
                        String dat = data[d];
                        String[] info = dat.split("SPLIT");

                        savedExercisez.add(new Exercise());
                        Exercise exer = savedExercisez.get(d);
                        for (int o = 0; o < 5; o++) {
                            if (o == 0) {
                                try {
                                    exer.setName(info[o]);
                                } catch (Exception e) {
                                    exer.setName(" ");
                                }
                                System.out.println("This was your name: " + exer.getName());
                            } if (o == 1) {
                                try {
                                    exer.setReps(info[o]);
                                } catch (Exception e) {
                                    exer.setReps("0");
                                }
                                System.out.println("This was your reps: " + exer.getReps());
                            }
                            if (o == 2) {
                                try {
                                    exer.setSets(info[o]);
                                } catch (Exception e) {
                                    exer.setSets("1");
                                }
                                System.out.println("This was your sets: " + exer.getSets());
                            } if (o == 3) {
                                try {
                                    exer.setWeight(info[o]);
                                } catch (Exception e) {
                                    exer.setWeight("0");
                                }
                                System.out.println("This was your weight: " + exer.getWeight());
                            } if (o == 4) {
                                try {
                                    exer.setRest(info[o]);
                                } catch (Exception e) {
                                    exer.setRest("0");
                                }
                                System.out.println("This was your rest: " + exer.getRest());
                            }


                        }

                    }

                }
            } catch (IOException e) {
                System.out.println("Exception");

            }
            System.out.println("");




        }



    }




    public void makeLinearLayoutParamsTopBar(LinearLayout layout) {
        LinearLayout.LayoutParams layoutpara = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layoutpara.setMargins(0, 0, 0, 0);
        layoutpara.gravity = Gravity.CENTER;
        layout.setLayoutParams(layoutpara);
    }

    public void makeLinearLayoutParams(LinearLayout layout) {
        LinearLayout.LayoutParams layoutpara = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutpara.setMargins(10, 10, 10, 10);
        layoutpara.gravity = Gravity.CENTER;
        layout.setLayoutParams(layoutpara);
    }

    public void makeButtonAndTextRowParams(LinearLayout.LayoutParams params) {
        params.leftMargin = 10;
        params.rightMargin = 10;
        params.topMargin = 20;
        params.bottomMargin = 20;
        params.gravity = Gravity.CENTER_HORIZONTAL;
    }

    public void makeLabels(LinearLayout linear) {
        // linear layoutcreator
        MyLinearLayout creatLinearLayout = new MyLinearLayout();


        //Make Labels:
        TextView nameLabel = new TextView(this);
        nameLabel.setText("Exercise");
        nameLabel.setPadding(60, 0, 0, 0);
        nameLabel.setTextSize(15);
        nameLabel.setTextColor(WHITE);

        TextView repsLabel = new TextView(this);
        repsLabel.setText("Reps");
        repsLabel.setPadding(110, 0, 0, 0);
        repsLabel.setTextSize(15);
        repsLabel.setTextColor(WHITE);

        TextView setsLabel = new TextView(this);
        setsLabel.setText("Sets");
        setsLabel.setPadding(70, 0, 0, 0);
        setsLabel.setTextSize(15);
        setsLabel.setTextColor(WHITE);

        TextView weightLabel = new TextView(this);
        weightLabel.setText("Weight");
        weightLabel.setPadding(60, 0, 0, 0);
        weightLabel.setTextSize(15);
        weightLabel.setTextColor(WHITE);

        TextView restLabel = new TextView(this);
        restLabel.setText("Rest");
        restLabel.setPadding(45, 0, 10, 0);
        restLabel.setTextSize(15);
        restLabel.setTextColor(WHITE);

        creatLinearLayout.setupLayout(linear);
        makeLinearLayoutParams(linear);
        linear.addView(nameLabel);
        linear.addView(repsLabel);
        linear.addView(setsLabel);
        linear.addView(weightLabel);
        linear.addView(restLabel);

    }

    public void makeTextFields(LinearLayout linlay) {

        containerFortestxArray.add(new ArrayList<EditText>());
        textFieldArray = containerFortestxArray.get(numberOfExercises);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(10, 0, 10, 0);
        layoutParams.gravity = Gravity.CENTER;


        LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        leftMarginParams.leftMargin = 10;
        leftMarginParams.rightMargin = 5;

        // make textfield
        EditText exerciseName = new EditText(this);
        myTextField maktxt = new myTextField();
        maktxt.makeTextField(exerciseName);
        //exerciseName.setMaxWidth(300);
        exerciseName.setMinimumWidth(300);
        exerciseName.setPadding(10, 5, 10, 5);
        textFieldArray.add(exerciseName);

        // second textfield
        EditText repsTextField = new EditText(this);
        maktxt.makeTextField(repsTextField);
        // repsTextField.setMaxWidth(150);
        repsTextField.setMinimumWidth(150);
        repsTextField.setPadding(10, 5, 10, 5);
        textFieldArray.add(repsTextField);

        // third textField
        EditText setsTextField = new EditText(this);
        maktxt.makeTextField(setsTextField);
        // setsTextField.setMaxWidth(150);
        setsTextField.setMinimumWidth(150);
        setsTextField.setPadding(10, 5, 10, 5);
        textFieldArray.add(setsTextField);

        //fourth textField
        EditText weightTextField = new EditText(this);
        maktxt.makeTextField(weightTextField);
        // weightTextField.setMaxWidth(150);
        weightTextField.setMinimumWidth(150);
        weightTextField.setPadding(10, 5, 10, 5);
        textFieldArray.add(weightTextField);

        // fifth textField
        EditText restTextField = new EditText(this);
        maktxt.makeTextField(restTextField);
        // restTextField.setMaxWidth(150);
        restTextField.setMinimumWidth(150);
        restTextField.setPadding(10, 5, 10, 5);
        textFieldArray.add(restTextField);

        linlay.setLayoutParams(layoutParams);

        //textFieldRows.setLayoutParams(layoutParams);
        linlay.addView(exerciseName, leftMarginParams);
        linlay.addView(repsTextField, leftMarginParams);
        linlay.addView(setsTextField, leftMarginParams);
        linlay.addView(weightTextField, leftMarginParams);
        linlay.addView(restTextField, leftMarginParams);

    }

}


