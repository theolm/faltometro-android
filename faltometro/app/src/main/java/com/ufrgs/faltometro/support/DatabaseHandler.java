package com.ufrgs.faltometro.support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ufrgs.faltometro.vos.AbsenceVo;
import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by theo on 12/29/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "disciplineManager";
    private static final String TABLE_DISCIPLINE = "discipline";
    private static final String TABLE_ABSENCES = "absences";

    //colunas TABLE_DISCIPLINE
    private static final String KEY_ID = "id";
    private static final String KEY_NOME = "nome";
    private static final String KEY_MAXFALTAS = "maximo_de_faltas";
    private static final String KEY_FALTAS = "faltas";
    private static final String KEY_DIAS = "dias";
    private static final String KEY_HORARIO = "horario";
    private static final String KEY_CREDITOS = "creditos";
    private static final String KEY_COR = "cor";
    private static final String KEY_HASH = "hashcode";

    //colunas TABLE_ABSENCES
    private static final String KEY_ID_ABSENCE = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_FOREIGN_ID = "foreign_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Criando a tabela
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_DISCIPLINE_TABLE = "CREATE TABLE " + TABLE_DISCIPLINE
                + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NOME + " TEXT,"
                + KEY_MAXFALTAS + " INTEGER,"
                + KEY_FALTAS + " INTEGER,"
                + KEY_DIAS + " TEXT,"
                + KEY_CREDITOS + " INTEGER,"
                + KEY_HORARIO + " TEXT,"
                + KEY_COR + " TEXT,"
                + KEY_HASH + " INTEGER"
                + ")";

        String CREATE_ABSENCE_TABLE = "CREATE TABLE " + TABLE_ABSENCES
                + "("
                + KEY_ID_ABSENCE + " INTEGER PRIMARY KEY,"
                + KEY_FOREIGN_ID + " INTEGER,"
                + KEY_DATE + " TEXT"
                + ")";

        db.execSQL(CREATE_DISCIPLINE_TABLE);
        db.execSQL(CREATE_ABSENCE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCIPLINE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ABSENCES);

        // Create tables again
        onCreate(db);
    }

    public void addDiscipline(DisciplineVo disciplineVo) //adicionar cadeira
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NOME, disciplineVo.name); // nome da cadeira
        values.put(KEY_MAXFALTAS, disciplineVo.maxFaults);
        values.put(KEY_FALTAS, disciplineVo.totalFaults);
        values.put(KEY_DIAS, disciplineVo.days);
        values.put(KEY_CREDITOS, disciplineVo.credits);
        values.put(KEY_HORARIO, disciplineVo.time);
        values.put(KEY_COR, disciplineVo.cor);
        values.put(KEY_HASH, disciplineVo.hashCode());

        // Inserting Row
        db.insert(TABLE_DISCIPLINE, null, values);
        db.close();

    }

    public DisciplineVo getDiscipline(int id)
    {
        if(id == -1)
            return null;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DISCIPLINE, new String[] {
                KEY_ID, KEY_NOME, KEY_MAXFALTAS, KEY_FALTAS, KEY_DIAS, KEY_CREDITOS, KEY_HORARIO, KEY_COR, KEY_HASH }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        DisciplineVo disciplineVo = new DisciplineVo();
        disciplineVo.id = Integer.parseInt(cursor.getString(0));
        disciplineVo.name = cursor.getString(1);
        disciplineVo.maxFaults = Integer.parseInt(cursor.getString(2));
        disciplineVo.totalFaults = Integer.parseInt(cursor.getString(3));
        disciplineVo.days = cursor.getString(4);
        disciplineVo.credits = Integer.parseInt(cursor.getString(5));
        disciplineVo.time = cursor.getString(6);
        disciplineVo.cor = cursor.getString(7);
        disciplineVo.hash = cursor.getInt(8);

        String selectQuery = "SELECT  * FROM " + TABLE_ABSENCES + " WHERE " + KEY_FOREIGN_ID + " =?";
        cursor = db.rawQuery(selectQuery, new String[] {String.valueOf(disciplineVo.hash)});


        if (cursor.moveToFirst()) {
            do {

                AbsenceVo absenceVo = new AbsenceVo();
                absenceVo.id = cursor.getInt(0);
                absenceVo.foregin_key = cursor.getInt(1);
                absenceVo.date = cursor.getString(2);
                disciplineVo.absenceVoList.add(absenceVo);

            } while (cursor.moveToNext());
        }

        db.close();

        return disciplineVo;

    }

    public List<DisciplineVo> getAllDisciplines()
    {

        //TODO: MUST BE A BETTER WAY TO DO THIS USING SQLITE EXEC

        List<AbsenceVo> absenceVoList = new ArrayList<>();
        List<DisciplineVo> disciplineList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_DISCIPLINE;
        String selectQueryAbsence = "SELECT  * FROM " + TABLE_ABSENCES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQueryAbsence, null);

        if (cursor.moveToFirst()){
            do {

                AbsenceVo absenceVo = new AbsenceVo();
                absenceVo.id = cursor.getInt(0);
                absenceVo.foregin_key = cursor.getInt(1);
                absenceVo.date = cursor.getString(2);
                absenceVoList.add(absenceVo);

            } while (cursor.moveToNext());
        }

        db = this.getWritableDatabase();
        cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do {
                DisciplineVo disciplineVo = new DisciplineVo();
                disciplineVo.id = Integer.parseInt(cursor.getString(0));
                disciplineVo.name = cursor.getString(1);
                disciplineVo.maxFaults = Integer.parseInt(cursor.getString(2));
                disciplineVo.totalFaults = Integer.parseInt(cursor.getString(3));
                disciplineVo.days = cursor.getString(4);
                disciplineVo.credits = Integer.parseInt(cursor.getString(5));
                disciplineVo.time = cursor.getString(6);
                disciplineVo.cor = cursor.getString(7);
                disciplineVo.hash = cursor.getInt(8);

                for (AbsenceVo absenceVo : absenceVoList){
                    if (absenceVo.foregin_key == disciplineVo.hash)
                        disciplineVo.absenceVoList.add(absenceVo);
                }

                // Adding to list
                disciplineList.add(disciplineVo);

            } while (cursor.moveToNext());
        }

        db.close();
        return disciplineList;
    }

    public List<AbsenceVo> getAllAbsences(){
        List<AbsenceVo> absences = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_ABSENCES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst())
        {
            do {
                AbsenceVo absenceVo = new AbsenceVo();
                absenceVo.id = cursor.getInt(0);
                absenceVo.foregin_key = cursor.getInt(1);
                absenceVo.date = cursor.getString(2);

                absences.add(absenceVo);


            } while (cursor.moveToNext());
        }

        db.close();

        return absences;
    }

    public int getNumberOfDisciplines()
    {

        String countQuery = "SELECT  * FROM " + TABLE_DISCIPLINE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public int updateDiscipline(DisciplineVo disciplineVo)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOME, disciplineVo.name);
        values.put(KEY_MAXFALTAS, disciplineVo.maxFaults);
        values.put(KEY_FALTAS, disciplineVo.totalFaults);
        values.put(KEY_DIAS, disciplineVo.days);
        values.put(KEY_CREDITOS, disciplineVo.credits);
        values.put(KEY_HORARIO, disciplineVo.time);
        values.put(KEY_COR, disciplineVo.cor);
        values.put(KEY_HASH, disciplineVo.hash);

        int update_status = db.update(TABLE_DISCIPLINE, values, KEY_ID + " = ?", new String[] { String.valueOf(disciplineVo.id) });

        db.close();
        return update_status;
    }

    public int addAbsence(DisciplineVo disciplineVo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues disciplineTableValues = new ContentValues();
        disciplineTableValues.put(KEY_FALTAS, disciplineVo.totalFaults + 1);

        Calendar c = Calendar.getInstance();
        String currentDate = String.valueOf(c.get(Calendar.DAY_OF_MONTH)) + "/" + String.valueOf(c.get(Calendar.MONTH) + 1) + "/" + String.valueOf(c.get(Calendar.YEAR));

        ContentValues absenceTableValues = new ContentValues();
        absenceTableValues.put(KEY_FOREIGN_ID, disciplineVo.hash);
        absenceTableValues.put(KEY_DATE, currentDate);

        int update_discipline_status = db.update(TABLE_DISCIPLINE, disciplineTableValues, KEY_ID + " = ?", new String[] { String.valueOf(disciplineVo.id) });
        db.insert(TABLE_ABSENCES, null, absenceTableValues);
        db.close();
        return update_discipline_status;
    }

    public int removeAbsence(DisciplineVo disciplineVo, AbsenceVo absenceVo){

        if (disciplineVo.totalFaults > 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_FALTAS, disciplineVo.totalFaults - 1);

            int update_status = db.update(TABLE_DISCIPLINE, values, KEY_ID + " = ?", new String[]{String.valueOf(disciplineVo.id)});
            db.delete(TABLE_ABSENCES, KEY_ID_ABSENCE + " = ?", new String[] {String.valueOf(absenceVo.id)});
            db.close();
            return update_status;
        } else{
            return -1;
        }
    }

    public int editAbsence(AbsenceVo absenceVo){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, absenceVo.date);
        int update_status = db.update(TABLE_ABSENCES, values, KEY_ID_ABSENCE + " = ?", new String[]{String.valueOf(absenceVo.id)});
        db.close();

        return update_status;
    }

    public void deleteDiscipline(DisciplineVo disciplineVo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISCIPLINE, KEY_ID + " = ?", new String[] { String.valueOf(disciplineVo.id) });
        db.delete(TABLE_ABSENCES, KEY_FOREIGN_ID + " = ?", new String[] {String.valueOf(disciplineVo.hash)});
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISCIPLINE,null,null);
        db.execSQL("delete from "+ TABLE_DISCIPLINE);

        db.delete(TABLE_ABSENCES, null, null);
        db.execSQL("delete from " + TABLE_ABSENCES);

        db.close();
    }
}
