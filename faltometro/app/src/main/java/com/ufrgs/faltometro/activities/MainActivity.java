package com.ufrgs.faltometro.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.ufrgs.faltometro.R;
import com.ufrgs.faltometro.adapters.DisciplineAdapter;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.utils.ItemClickSupport;
import com.ufrgs.faltometro.utils.LayoutUtils;
import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_toolbar) Toolbar toolbar;
    @Bind(R.id.recyclerview) RecyclerView recyclerView;
    @Bind(R.id.fab) FloatingActionButton fab;

    private DisciplineAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        mAdapter = new DisciplineAdapter(this, getDisciplineList());
        recyclerView.setAdapter(mAdapter);

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                //Snackbar.make(v, "Click longo para editar", Snackbar.LENGTH_SHORT).show();
            }
        });

        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(new ItemClickSupport.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
                showOptionDialog(position);
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddDisciplineActivity.class);
                startActivity(i);
            }
        });

        LayoutUtils.setStatusBarColor(this, "#ff292929");

    }


    @Override
    protected void onResume() {
        super.onResume();
        reloadAllDisciplines();
    }

    private List<DisciplineVo> getDisciplineList() {

        DatabaseHandler db = new DatabaseHandler(this);
        return db.getAllDisciplines();
    }

    private void reloadAllDisciplines(){
        if (mAdapter != null)
            mAdapter.reloadAdapter(getDisciplineList());
    }

    private void showOptionDialog(final int position){
        CharSequence options[] = new CharSequence[] {"Adicionar Falta", "Remover Falta", "Editar Disciplina", "Deletar Disciplina"};


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DisciplineVo discipline = mAdapter.getDisciplineAt(position);
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                switch (which){
                    case 0:
                        db.addAbsence(discipline);
                        reloadAllDisciplines();
                        break;
                    case 1:
                        db.removeAbsence(discipline);
                        reloadAllDisciplines();
                        break;
                    case 2:
                        Intent i = new Intent(MainActivity.this, AddDisciplineActivity.class);
                        i.putExtra("position", position);
                        startActivity(i);
                        break;
                    case 3:
                        db.deleteDiscipline(discipline);
                        reloadAllDisciplines();
                        break;
                    default:
                        break;
                }
            }
        });
        builder.show();
    }

}
