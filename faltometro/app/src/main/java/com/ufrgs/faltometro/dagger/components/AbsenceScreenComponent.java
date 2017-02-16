package com.ufrgs.faltometro.dagger.components;

import com.ufrgs.faltometro.dagger.CustomScope;
import com.ufrgs.faltometro.dagger.modules.AbsenceScreenModule;
import com.ufrgs.faltometro.dagger.modules.MainScreenModule;
import com.ufrgs.faltometro.ui.absencescreen.AbsencesActivity;
import com.ufrgs.faltometro.ui.mainscreen.MainActivity;

import dagger.Component;

/**
 * Created by theolm on 11/02/17.
 */

@CustomScope
@Component(modules = AbsenceScreenModule.class)
public interface AbsenceScreenComponent {
    void inject(AbsencesActivity absencesActivity);
}
