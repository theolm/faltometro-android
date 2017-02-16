package com.ufrgs.faltometro.vos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by theo on 12/29/15.
 */
public class DisciplineVo {

    public int id;
    public String name;
    public String days;
    public int totalFaults;
    public int maxFaults;
    public int credits;
    public String time;
    public String cor;
    public List<AbsenceVo> absenceVoList = new ArrayList<>();
    public int hash;

}
