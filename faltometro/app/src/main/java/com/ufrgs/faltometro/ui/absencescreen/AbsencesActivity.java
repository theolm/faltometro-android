package com.ufrgs.faltometro.ui.absencescreen;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ufrgs.faltometro.R;
import com.ufrgs.faltometro.adapters.AbsenceAdapter;
import com.ufrgs.faltometro.dagger.components.DaggerAbsenceScreenComponent;
import com.ufrgs.faltometro.dagger.modules.AbsenceScreenModule;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.utils.ItemClickSupport;
import com.ufrgs.faltometro.utils.Tags;
import com.ufrgs.faltometro.vos.AbsenceVo;
import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by theolm on 11/02/17.
 */

public class AbsencesActivity extends AppCompatActivity implements AbsenceScreenContract.View, View.OnClickListener, ItemClickSupport.OnItemLongClickListener {

    @BindView(R.id.absence_card) CardView cardView;
    @BindView(R.id.absence_name) TextView disciplineName;
    @BindView(R.id.absence_recyclerview) RecyclerView recyclerView;
    @BindView(R.id.absence_ll_add) LinearLayout llAddAbsence;
    @BindView(R.id.absence_bg) RelativeLayout bg;
    @Inject AbsenceScreenPresenter mAbsenceScreenPresenter;
    @Inject AbsenceAdapter mAdapter;
    @Inject DatabaseHandler db;
    @Inject LinearLayoutManager linearLayoutManager;

    private int disciplineId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absences);
        ButterKnife.bind(this);
        DaggerAbsenceScreenComponent.builder()
                .absenceScreenModule(new AbsenceScreenModule(this, this))
                .build().inject(this);


        disciplineId = getIntent().getIntExtra(Tags.DISCIPLINE_ID, -1);
        DisciplineVo disciplineVo = db.getDiscipline(disciplineId);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);

        mAbsenceScreenPresenter.configureCard(disciplineVo);
        mAbsenceScreenPresenter.loadAbscences(disciplineVo);

        llAddAbsence.setOnClickListener(this);
        bg.setOnClickListener(this);

        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(this);

    }


    @Override
    public void showSnackbar(String text) {
        Snackbar.make(disciplineName, text, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showAbscences() {
        mAdapter.updateList(db.getDiscipline(disciplineId));
    }

    @Override
    public void showCard(String color, String name) {
        cardView.setBackgroundColor(Color.parseColor(color));
        disciplineName.setText(name);
    }

    @Override
    public void showCalendar(final AbsenceVo absence) {
        final Calendar newCalendar = Calendar.getInstance();
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                absence.date = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year);
                DatabaseHandler db = new DatabaseHandler(AbsencesActivity.this);
                db.editAbsence(absence);
                mAdapter.notifyDataSetChanged();
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void closeScreen() {
        onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.absence_ll_add:
                mAbsenceScreenPresenter.onClickAdd(db.getDiscipline(disciplineId));
                break;
            case R.id.absence_bg:
                mAbsenceScreenPresenter.onClickBg();
                break;
        }

    }

    @Override
    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
        mAbsenceScreenPresenter.onClickAbsence(mAdapter.getItemAt(position));
        return false;
    }
}
