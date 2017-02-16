package com.ufrgs.faltometro.ui.absencescreen;

import com.ufrgs.faltometro.vos.AbsenceVo;
import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.List;

/**
 * Created by theolm on 12/02/17.
 */

public interface AbsenceScreenContract {

    interface View {
        void showSnackbar(String text);
        void showAbscences();
        void showCard(String color, String name);
        void showCalendar(AbsenceVo absence);
        void closeScreen();
    }

    interface Presenter {
        void loadAbscences(DisciplineVo disciplineVo);
        void configureCard(DisciplineVo disciplineVo);
        void onClickAbsence(AbsenceVo absence);
        void onClickAdd(DisciplineVo disciplineVo);
        void onClickBg();
    }
}
