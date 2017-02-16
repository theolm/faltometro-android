package com.ufrgs.faltometro.ui.mainscreen;

import android.content.Context;
import android.view.View;

import com.ufrgs.faltometro.support.DatabaseHandler;

/**
 * Created by theolm on 11/02/17.
 */

public class MainScreenPresenter implements MainScreenContract.Presenter {

    private MainScreenContract.View mView;
    private Context mContext;

    public MainScreenPresenter(MainScreenContract.View view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    @Override
    public void loadDisciplines() {
        DatabaseHandler db = new DatabaseHandler(mContext);
        mView.showDisciplines(db.getAllDisciplines());
    }

    @Override
    public void onClickFab() {
        mView.openAddDiscipline();
    }

    @Override
    public void onClickDiscipline(View view, int pos) {
        mView.openDiscipline(view, pos);
    }

    @Override
    public void onLongClickDiscipline(int pos) {
        CharSequence options[] = new CharSequence[] {"Editar Disciplina", "Deletar Disciplina"};
        mView.showOptionsDialog(pos, options);
    }
}
