package com.ufrgs.faltometro.dagger.modules;

import android.content.Context;

import com.ufrgs.faltometro.dagger.CustomScope;
import com.ufrgs.faltometro.support.DatabaseHandler;
import com.ufrgs.faltometro.ui.addscreen.AddDisciplineContract;
import com.ufrgs.faltometro.ui.addscreen.AddDisciplinePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by theo on 16/02/17.
 */

@Module
public class AddDisciplineScreenModule {

    private AddDisciplineContract.View mView;
    private Context mContext;

    public AddDisciplineScreenModule(AddDisciplineContract.View view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    @Provides
    @CustomScope
    AddDisciplinePresenter providesAddDisciplinePresenter(){
        return new AddDisciplinePresenter(mContext, mView);
    }

    @Provides
    @CustomScope
    DatabaseHandler providesDatabaseHandler(){
        return new DatabaseHandler(mContext);
    }


}
