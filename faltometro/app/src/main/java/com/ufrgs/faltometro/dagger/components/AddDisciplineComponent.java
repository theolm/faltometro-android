package com.ufrgs.faltometro.dagger.components;

import com.ufrgs.faltometro.dagger.CustomScope;
import com.ufrgs.faltometro.dagger.modules.AddDisciplineScreenModule;
import com.ufrgs.faltometro.ui.addscreen.AddDisciplineActivity;

import dagger.Component;

/**
 * Created by theo on 16/02/17.
 */

@CustomScope
@Component(modules = AddDisciplineScreenModule.class)
public interface AddDisciplineComponent {
    void inject(AddDisciplineActivity addDisciplineActivity);
}