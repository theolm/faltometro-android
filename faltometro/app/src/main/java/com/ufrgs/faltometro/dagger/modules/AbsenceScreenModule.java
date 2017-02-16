package com.ufrgs.faltometro.dagger.modules;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.ufrgs.faltometro.adapters.AbsenceAdapter;
import com.ufrgs.faltometro.adapters.DisciplineAdapter;
import com.ufrgs.faltometro.dagger.CustomScope;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.ui.absencescreen.AbsenceScreenContract;
import com.ufrgs.faltometro.ui.absencescreen.AbsenceScreenPresenter;
import com.ufrgs.faltometro.ui.mainscreen.MainScreenContract;
import com.ufrgs.faltometro.ui.mainscreen.MainScreenPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by theolm on 11/02/17.
 */

@Module
public class AbsenceScreenModule {

    private final AbsenceScreenContract.View mView;
    private Context mContext;

    public AbsenceScreenModule(AbsenceScreenContract.View view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    @Provides
    @CustomScope
    AbsenceScreenPresenter providesAbsenceScreenPresenter(){
        return new AbsenceScreenPresenter(mContext, mView);
    }

    @Provides
    @CustomScope
    LinearLayoutManager providesLinearLayoutManager(){
        return new LinearLayoutManager(mContext);
    }

    @Provides
    @CustomScope
    AbsenceAdapter providesAbsenceAdapter(){
        return new AbsenceAdapter(mContext);
    }

    @Provides
    @CustomScope
    DatabaseHandler providesDBHandler(){
        return new DatabaseHandler(mContext);
    }

}
