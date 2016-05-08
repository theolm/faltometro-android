package com.ufrgs.faltometro.activities;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.ufrgs.faltometro.R;
import com.ufrgs.faltometro.adapters.AbsenceAdapter;
import com.ufrgs.faltometro.adapters.DisciplineAdapter;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.utils.ItemClickSupport;
import com.ufrgs.faltometro.vos.AbsenceVo;
import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import carbon.widget.CardView;
import carbon.widget.LinearLayout;
import carbon.widget.RelativeLayout;

/**
 * Created by theo on 2/13/16.
 */
public class AbsencesActivity extends AppCompatActivity {

    @Bind(R.id.absence_card) CardView cardView;
    @Bind(R.id.absence_name) TextView disciplineName;
    @Bind(R.id.absence_recyclerview) RecyclerView recyclerView;
    @Bind(R.id.absence_ll_add) LinearLayout llAddAbsence;
    @Bind(R.id.absence_bg) RelativeLayout bg;

    private DisciplineVo disciplineVo;
    private AbsenceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absences);
        ButterKnife.bind(this);
        Snackbar.make(disciplineName, "Segure em uma data para edita-la.", Snackbar.LENGTH_LONG).show();

        disciplineVo = DisciplineAdapter.mList.get(getIntent().getIntExtra("position", 0));

        cardView.setBackgroundColor(Color.parseColor(disciplineVo.cor));
        disciplineName.setText(disciplineVo.name);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        mAdapter = new AbsenceAdapter(this, disciplineVo);
        recyclerView.setAdapter(mAdapter);

        llAddAbsence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(AbsencesActivity.this);
                db.addAbsence(disciplineVo);
                disciplineVo = db.getDiscipline(disciplineVo.id);
                mAdapter.updateList(disciplineVo);
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                openCalendar(mAdapter.getItemAt(position));
                return false;
            }
        });

        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void openCalendar(final AbsenceVo absence){
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
}
