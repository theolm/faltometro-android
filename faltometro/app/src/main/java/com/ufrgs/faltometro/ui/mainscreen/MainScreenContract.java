package com.ufrgs.faltometro.ui.mainscreen;

import android.view.View;

import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.List;

/**
 * Created by theolm on 11/02/17.
 */

public interface MainScreenContract {
    interface View {
        void showDisciplines(List<DisciplineVo> disciplineVos);
        void showError(String error);
        void showOptionsDialog(int pos, CharSequence options[]);
        void openAddDiscipline();
        void openDiscipline(android.view.View view, int pos);
    }

    interface Presenter {
        void loadDisciplines();
        void onClickFab();
        void onClickDiscipline(android.view.View view, int pos);
        void onLongClickDiscipline(int pos);
    }
}
