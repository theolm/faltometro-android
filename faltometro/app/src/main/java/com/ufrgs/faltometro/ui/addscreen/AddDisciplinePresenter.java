package com.ufrgs.faltometro.ui.addscreen;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.widget.EditText;

import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.vos.DisciplineVo;
import com.ufrgs.faltometro.widget.WidgetUpdate;

/**
 * Created by theo on 15/02/17.
 */

public class AddDisciplinePresenter implements AddDisciplineContract.Presenter{

    private Context mContext;
    private AddDisciplineContract.View mView;

    public AddDisciplinePresenter(Context context, AddDisciplineContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void calculateMaxCredits(String totalCredits) {
        String maxCredits;
        if(totalCredits.isEmpty())
            maxCredits = "";
        else
            maxCredits = String.valueOf(Integer.valueOf(totalCredits) * 2);

        mView.showMaxCredits(maxCredits);
    }

    @Override
    public void onClickAdd(int disciplineId, EditText inputName, EditText inputCredits, EditText inputMaxAbsences, String days, String time, String cor) {
        if (!inputName.getText().toString().isEmpty()){
            if(!inputCredits.getText().toString().isEmpty()){
                DatabaseHandler db = new DatabaseHandler(mContext);

                DisciplineVo disciplineVo =  db.getDiscipline(disciplineId);
                if(disciplineVo == null)
                    disciplineVo = new DisciplineVo();
                disciplineVo.name = inputName.getText().toString();
                disciplineVo.credits = Integer.valueOf(inputCredits.getText().toString());
                disciplineVo.maxFaults = Integer.valueOf(inputMaxAbsences.getText().toString());
                disciplineVo.days = days;
                disciplineVo.time = time;
                disciplineVo.cor = cor;

                if(disciplineId == -1){
                    db.addDiscipline(disciplineVo);
                } else {
                    db.updateDiscipline(disciplineVo);
                }

                WidgetUpdate.update((Activity) mContext);
                mView.closeScreen();
            }
            mView.showSnackbar("Número de créditos necessário.");
        }
        mView.showSnackbar("Favor adicionar nome a disciplina.");
    }

}
