package com.ufrgs.faltometro.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ufrgs.faltometro.R;
import com.ufrgs.faltometro.adapters.DisciplineAdapter;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.utils.LayoutUtils;
import com.ufrgs.faltometro.utils.Tags;
import com.ufrgs.faltometro.vos.DisciplineVo;
import com.ufrgs.faltometro.widget.WidgetUpdate;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by theo on 12/29/15.
 */
public class AddDisciplineActivity extends AppCompatActivity {

    private static final String TAG = "AddDisciplineActivity";

    @BindView(R.id.input_name) EditText inputName;
    @BindView(R.id.input_credits) EditText inputCredits;
    @BindView(R.id.input_max_absence) EditText inputMaxAbsences;
    @BindView(R.id.add_toolbar_title) TextView toolbarTitle;
    @BindView(R.id.checkbox_seg) CheckBox checkboxSeg;
    @BindView(R.id.checkbox_ter) CheckBox checkboxTer;
    @BindView(R.id.checkbox_qua) CheckBox checkboxQua;
    @BindView(R.id.checkbox_qui) CheckBox checkboxQui;
    @BindView(R.id.checkbox_sex) CheckBox checkboxSex;
    @BindView(R.id.checkbox_sab) CheckBox checkboxSab;
    @BindView(R.id.checkbox_dom) CheckBox checkboxDom;
    @BindView(R.id.timePicker) TimePicker timePicker;
    @BindView(R.id.btn_add) Button btnAdd;
    @BindView(R.id.color_blue) ImageView colorBlue;
    @BindView(R.id.color_yellow) ImageView colorYellow;
    @BindView(R.id.color_red) ImageView colorRed;
    @BindView(R.id.color_orange) ImageView colorOrange;
    @BindView(R.id.color_purple) ImageView colorPurple;
    @BindView(R.id.color_grey) ImageView colorGrey;
    @BindView(R.id.color_green) ImageView colorGreen;
    @BindView(R.id.color_pink) ImageView colorPink;
    @BindView(R.id.color_brown) ImageView colorBrown;

    private DisciplineVo disciplineVo = null;
    private String colorString = Tags.BLUE_COLOR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discipline);
        ButterKnife.bind(this);

        int pos = getIntent().getIntExtra("position", -1);

        if(pos != -1){
            disciplineVo =  DisciplineAdapter.mList.get(pos);
            configureEditableActivity();
        }

        LayoutUtils.setStatusBarColor(this, "#212121");

        timePicker.setIs24HourView(true);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!inputName.getText().toString().isEmpty()) {
                    if (!inputCredits.getText().toString().isEmpty()) {

                        if (disciplineVo == null){
                            DatabaseHandler db = new DatabaseHandler(AddDisciplineActivity.this);
                            db.addDiscipline(createDiscipline());
                        } else {
                            DatabaseHandler db = new DatabaseHandler(AddDisciplineActivity.this);
                            db.updateDiscipline(createDiscipline());
                        }
                        WidgetUpdate.update(AddDisciplineActivity.this);
                        finish();

                    } else {
                        Snackbar.make(v, "Número de créditos necessário.", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(v, "Favor adicionar nome a disciplina.", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        inputCredits.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String credits = inputCredits.getText().toString();

                if((!credits.isEmpty()) && (Integer.valueOf(credits) > 0)) {
                    int maxAbsences = calcMaxAbsences();
                    inputMaxAbsences.setText(String.valueOf(maxAbsences));
                }

            }
        });
        
        setColorPicker();
    }

    private DisciplineVo createDiscipline() {
        DisciplineVo out = new DisciplineVo();

        if (disciplineVo != null) {
            out = disciplineVo;
            out.name = inputName.getText().toString();
            out.credits = Integer.valueOf(inputCredits.getText().toString());
            out.maxFaults = Integer.valueOf(inputMaxAbsences.getText().toString());
            out.days = returnDays();
            out.time = returnTime();
            out.cor = colorString;

        } else {

            out.name = inputName.getText().toString();
            out.credits = Integer.valueOf(inputCredits.getText().toString());
            out.totalFaults = 0;
            out.maxFaults = Integer.valueOf(inputMaxAbsences.getText().toString());
            out.days = returnDays();
            out.time = returnTime();
            out.cor = colorString;
        }



        return out;
    }

    private String returnDays() {
        String days;
        if (checkboxSeg.isChecked()) days = "1";
        else days = "0";
        if (checkboxTer.isChecked()) days = days + "1";
        else days = days + "0";
        if (checkboxQua.isChecked()) days = days + "1";
        else days = days + "0";
        if (checkboxQui.isChecked()) days = days + "1";
        else days = days + "0";
        if (checkboxSex.isChecked()) days = days + "1";
        else days = days + "0";
        if (checkboxSab.isChecked()) days = days + "1";
        else days = days + "0";
        if (checkboxDom.isChecked()) days = days + "1";
        else days = days + "0";

        return days;
    }

    private String returnTime() {
        timePicker.clearFocus();

        int hora = timePicker.getCurrentHour();
        int min = timePicker.getCurrentMinute();

        String horaString, minString;

        if (hora < 10)
            horaString = "0" + String.valueOf(hora);
        else
            horaString = String.valueOf(hora);

        if (min < 10)
            minString = "0" + String.valueOf(min);
        else
            minString = String.valueOf(min);


        String time = String.valueOf(horaString + "h" + minString);
        return time;
    }

    private void setColorPicker(){
        colorBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "setColorPicker: ");
                colorString = Tags.BLUE_COLOR;
                deselectAllColors();
                colorBlue.setImageResource(R.drawable.ic_check_white_24dp);
            }
        });

        colorYellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorString = Tags.YELLOW_COLOR;
                deselectAllColors();
                colorYellow.setImageResource(R.drawable.ic_check_white_24dp);
            }
        });

        colorRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorString = Tags.RED_COLOR;
                deselectAllColors();
                colorRed.setImageResource(R.drawable.ic_check_white_24dp);
            }
        });

        colorOrange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorString = Tags.ORANGE_COLOR;
                deselectAllColors();
                colorOrange.setImageResource(R.drawable.ic_check_white_24dp);
            }
        });

        colorPurple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorString = Tags.PURPLE_COLOR;
                deselectAllColors();
                colorPurple.setImageResource(R.drawable.ic_check_white_24dp);
            }
        });

        colorGrey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorString = Tags.GREY_COLOR;
                deselectAllColors();
                colorGrey.setImageResource(R.drawable.ic_check_white_24dp);
            }
        });

        colorGreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorString = Tags.GREEN_COLOR;
                deselectAllColors();
                colorGreen.setImageResource(R.drawable.ic_check_white_24dp);
            }
        });

        colorPink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorString = Tags.PINK_COLOR;
                deselectAllColors();
                colorPink.setImageResource(R.drawable.ic_check_white_24dp);
            }
        });

        colorBrown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorString = Tags.BROWN_COLOR;
                deselectAllColors();
                colorBrown.setImageResource(R.drawable.ic_check_white_24dp);
            }
        });
    }

    private void deselectAllColors(){
        colorBlue.setImageBitmap(null);
        colorYellow.setImageBitmap(null);
        colorRed.setImageBitmap(null);
        colorOrange.setImageBitmap(null);
        colorPurple.setImageBitmap(null);
    }

    private void configureEditableActivity(){
        inputName.setText(disciplineVo.name);
        inputCredits.setText(String.valueOf(disciplineVo.credits));
        inputMaxAbsences.setText(String.valueOf(disciplineVo.maxFaults));

        String days = disciplineVo.days;

        if (days.charAt(0) == '1' )
            checkboxSeg.setChecked(true);
        if (days.charAt(1) == '1' )
            checkboxTer.setChecked(true);
        if (days.charAt(2) == '1' )
            checkboxQua.setChecked(true);
        if (days.charAt(3) == '1' )
            checkboxQui.setChecked(true);
        if (days.charAt(4) == '1' )
            checkboxSex.setChecked(true);
        if (days.charAt(5) == '1' )
            checkboxSab.setChecked(true);
        if (days.charAt(6) == '1' )
            checkboxDom.setChecked(true);

        switch (disciplineVo.cor){

            case Tags.BLUE_COLOR:
                deselectAllColors();
                colorBlue.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case Tags.YELLOW_COLOR:
                deselectAllColors();
                colorYellow.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case Tags.RED_COLOR:
                deselectAllColors();
                colorRed.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case Tags.ORANGE_COLOR:
                deselectAllColors();
                colorOrange.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case Tags.PURPLE_COLOR:
                deselectAllColors();
                colorPurple.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case Tags.GREY_COLOR:
                deselectAllColors();
                colorGrey.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case Tags.GREEN_COLOR:
                deselectAllColors();
                colorGreen.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case Tags.PINK_COLOR:
                deselectAllColors();
                colorPink.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case Tags.BROWN_COLOR:
                deselectAllColors();
                colorBrown.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            default:
                deselectAllColors();
                colorBlue.setImageResource(R.drawable.ic_check_white_24dp);
                break;
        }

        timePicker.setCurrentHour(Integer.valueOf(disciplineVo.time.substring(0,2)));
        timePicker.setCurrentMinute(Integer.valueOf(disciplineVo.time.substring(3,5)));

        btnAdd.setText("EDITAR");
        toolbarTitle.setText("Editar Disciplina");

    }

    private int calcMaxAbsences(){
        return Integer.valueOf(inputCredits.getText().toString()) * 2;
    }
}
