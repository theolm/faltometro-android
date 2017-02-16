package com.ufrgs.faltometro.ui.addscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by theolm on 11/02/17.
 */

public class AddDisciplineActivity extends AppCompatActivity implements View.OnClickListener, AddDisciplineContract.View, TextWatcher {

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

    private int disciplineId;
    private String colorString = Tags.BLUE_COLOR;
    private AddDisciplinePresenter mPresenter;
    private DatabaseHandler db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discipline);
        ButterKnife.bind(this);
        LayoutUtils.setStatusBarColor(this, "#212121");


        mPresenter = new AddDisciplinePresenter(this, this);
        db = new DatabaseHandler(this);

        disciplineId = getIntent().getIntExtra(Tags.DISCIPLINE_ID, -1);

        if(disciplineId != -1){
            configureEditableActivity();
        }

        inputCredits.addTextChangedListener(this);
        timePicker.setIs24HourView(true);
        btnAdd.setOnClickListener(this);

        colorBlue.setOnClickListener(this);
        colorYellow.setOnClickListener(this);
        colorRed.setOnClickListener(this);
        colorOrange.setOnClickListener(this);
        colorPurple.setOnClickListener(this);
        colorGrey.setOnClickListener(this);
        colorGreen.setOnClickListener(this);
        colorPink.setOnClickListener(this);
        colorBrown.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                mPresenter.onClickAdd(disciplineId, inputName, inputCredits, inputMaxAbsences, returnDays(), returnTime(), colorString);
                break;
            case R.id.color_blue:
                colorString = Tags.BLUE_COLOR;
                deselectAllColors();
                colorBlue.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case R.id.color_yellow:
                colorString = Tags.YELLOW_COLOR;
                deselectAllColors();
                colorYellow.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case R.id.color_red:
                colorString = Tags.RED_COLOR;
                deselectAllColors();
                colorRed.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case R.id.color_orange:
                colorString = Tags.ORANGE_COLOR;
                deselectAllColors();
                colorOrange.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case R.id.color_purple:
                colorString = Tags.PURPLE_COLOR;
                deselectAllColors();
                colorPurple.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case R.id.color_grey:
                colorString = Tags.GREY_COLOR;
                deselectAllColors();
                colorGrey.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case R.id.color_green:
                colorString = Tags.GREEN_COLOR;
                deselectAllColors();
                colorGreen.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case R.id.color_pink:
                colorString = Tags.PINK_COLOR;
                deselectAllColors();
                colorPink.setImageResource(R.drawable.ic_check_white_24dp);
                break;
            case R.id.color_brown:
                colorString = Tags.BROWN_COLOR;
                deselectAllColors();
                colorBrown.setImageResource(R.drawable.ic_check_white_24dp);
                break;
        }
    }

    @Override
    public void showMaxCredits(String maxCredits) {
        inputMaxAbsences.setText(maxCredits);
    }

    @Override
    public void showSnackbar(String message) {
        Snackbar.make(inputName, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void closeScreen() {
        this.finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mPresenter.calculateMaxCredits(inputCredits.getText().toString());
    }

    private void configureEditableActivity(){
        DisciplineVo disciplineVo = db.getDiscipline(disciplineId);
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

    private void deselectAllColors(){
        colorBlue.setImageBitmap(null);
        colorYellow.setImageBitmap(null);
        colorRed.setImageBitmap(null);
        colorOrange.setImageBitmap(null);
        colorPurple.setImageBitmap(null);
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
}
