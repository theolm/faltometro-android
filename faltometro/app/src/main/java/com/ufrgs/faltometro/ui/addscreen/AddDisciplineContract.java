package com.ufrgs.faltometro.ui.addscreen;

import android.widget.EditText;

/**
 * Created by theo on 15/02/17.
 */

public interface AddDisciplineContract {
    interface View {
        void showMaxCredits(String maxCredits);
        void showSnackbar(String message);
        void closeScreen();

    }

    interface Presenter {
        void calculateMaxCredits(String totalCredits);
        void onClickAdd(int disciplineId, EditText inputName, EditText inputCredits, EditText inputMaxAbsences, String days, String time, String cor);
    }
}
