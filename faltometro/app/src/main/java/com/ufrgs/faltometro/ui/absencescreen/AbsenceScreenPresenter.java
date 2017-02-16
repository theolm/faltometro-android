package com.ufrgs.faltometro.ui.absencescreen;

import android.content.Context;

import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.vos.AbsenceVo;
import com.ufrgs.faltometro.vos.DisciplineVo;

/**
 * Created by theo on 14/02/17.
 */

public class AbsenceScreenPresenter implements AbsenceScreenContract.Presenter {

    private Context mContext;
    private AbsenceScreenContract.View mView;

    public AbsenceScreenPresenter(Context context, AbsenceScreenContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void loadAbscences(DisciplineVo disciplineVo) {
        mView.showAbscences();
    }

    @Override
    public void configureCard(DisciplineVo disciplineVo) {
        mView.showCard(disciplineVo.cor, disciplineVo.name);
        mView.showSnackbar("Segure em uma data para edita-la.");
    }

    @Override
    public void onClickAbsence(AbsenceVo absence) {
        mView.showCalendar(absence);
    }

    @Override
    public void onClickBg() {
        mView.closeScreen();
    }

    @Override
    public void onClickAdd(DisciplineVo disciplineVo) {
        DatabaseHandler db = new DatabaseHandler(mContext);
        db.addAbsence(disciplineVo);
        mView.showAbscences();
    }
}
