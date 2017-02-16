package com.ufrgs.faltometro.dagger.components;

import com.ufrgs.faltometro.dagger.CustomScope;
import com.ufrgs.faltometro.dagger.modules.MainScreenModule;
import com.ufrgs.faltometro.ui.mainscreen.MainActivity;

import dagger.Component;

/**
 * Created by theolm on 11/02/17.
 */

@CustomScope
@Component(modules = MainScreenModule.class)
public interface MainScreenComponent {
    void inject(MainActivity activity);
}
