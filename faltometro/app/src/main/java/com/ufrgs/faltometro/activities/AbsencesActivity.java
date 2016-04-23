package com.ufrgs.faltometro.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ufrgs.faltometro.R;
import com.ufrgs.faltometro.adapters.AbsenceAdapter;
import com.ufrgs.faltometro.adapters.DisciplineAdapter;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.vos.DisciplineVo;

import butterknife.Bind;
import butterknife.ButterKnife;
import carbon.widget.CardView;
import carbon.widget.LinearLayout;

/**
 * Created by theo on 2/13/16.
 */
public class AbsencesActivity extends AppCompatActivity {

    @Bind(R.id.absence_card) CardView cardView;
    @Bind(R.id.absence_name) TextView disciplineName;
    @Bind(R.id.absence_recyclerview) RecyclerView recyclerView;
    @Bind(R.id.absence_ll_add) LinearLayout llAddAbsence;

    private DisciplineVo disciplineVo;
    private AbsenceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absences);
        ButterKnife.bind(this);

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





    }
}
