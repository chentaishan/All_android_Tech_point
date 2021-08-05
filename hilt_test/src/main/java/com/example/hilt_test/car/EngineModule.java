package com.example.hilt_test.car;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class EngineModule {
    @BindGasEngine
    @Binds
    abstract Engine bindGasEngine(  GasEngine gasEngine);



    @BindElectricEngine
    @Binds
    abstract Engine bindEleEngine(  EleEngine eleEngine);

}
