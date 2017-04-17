package com.ufrgs.faltometro.ui.mainscreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crashlytics.android.Crashlytics;
import com.ufrgs.faltometro.R;
import com.ufrgs.faltometro.adapters.DisciplineAdapter;
import com.ufrgs.faltometro.dagger.components.DaggerMainScreenComponent;
import com.ufrgs.faltometro.dagger.modules.MainScreenModule;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.ui.absencescreen.AbsencesActivity;
import com.ufrgs.faltometro.ui.addscreen.AddDisciplineActivity;
import com.ufrgs.faltometro.utils.ItemClickSupport;
import com.ufrgs.faltometro.utils.LayoutUtils;
import com.ufrgs.faltometro.utils.Tags;
import com.ufrgs.faltometro.vos.DisciplineVo;

import io.fabric.sdk.android.Fabric;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by theolm on 11/02/17.
 */

public class MainActivity extends AppCompatActivity implements MainScreenContract.View, View.OnClickListener,
        ItemClickSupport.OnItemClickListener, ItemClickSupport.OnItemLongClickListener {

    @BindView(R.id.main_toolbar) Toolbar toolbar;
    @BindView(R.id.recyclerview) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton mFab;
    @Inject MainScreenPresenter mainScreenPresenter;
    @Inject DisciplineAdapter mAdapter;
    @Inject LinearLayoutManager mLlm;
    @Inject DatabaseHandler mDatabaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LayoutUtils.setStatusBarColor(this, "#ff292929");

        DaggerMainScreenComponent.builder()
                .mainScreenModule(new MainScreenModule(this, this))
                .build().inject(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLlm);
        recyclerView.setAdapter(mAdapter);

        mFab.setOnClickListener(this);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(this);
        ItemClickSupport.addTo(recyclerView).setOnItemLongClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mainScreenPresenter.loadDisciplines();
    }

    @Override
    public void showDisciplines(List<DisciplineVo> disciplineVos) {
        mAdapter.reloadAdapter(disciplineVos);
    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void showOptionsDialog(final int position, CharSequence options[]) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DisciplineVo discipline = mAdapter.getDisciplineAt(position);
                switch (which){
                    case 0:
                        Intent i = new Intent(MainActivity.this, AddDisciplineActivity.class);
                        i.putExtra(Tags.DISCIPLINE_ID, discipline.id);
                        startActivity(i);
                        break;
                    case 1:
                        mDatabaseHandler.deleteDiscipline(discipline);
                        mainScreenPresenter.loadDisciplines();
                        break;
                    default:
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void openAddDiscipline() {
        Intent i = new Intent(this, AddDisciplineActivity.class);
        startActivity(i);
    }

    @Override
    public void openDiscipline(View view, int pos) {
        DisciplineVo discipline = mAdapter.getDisciplineAt(pos);
        Intent i = new Intent(this, AbsencesActivity.class);
        i.putExtra(Tags.DISCIPLINE_ID, discipline.id);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this , view, getString(R.string.transition_card));
            this.startActivity(i, options.toBundle());
        }
        else {
            this.startActivity(i);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
                mainScreenPresenter.onClickFab();
        }
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        mainScreenPresenter.onClickDiscipline(v, position);
    }

    @Override
    public boolean onItemLongClicked(RecyclerView recyclerView, int position, View v) {
        mainScreenPresenter.onLongClickDiscipline(position);
        return false;
    }
}
