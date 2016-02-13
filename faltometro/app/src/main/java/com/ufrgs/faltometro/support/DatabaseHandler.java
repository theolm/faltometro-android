package com.ufrgs.faltometro.support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ufrgs.faltometro.vos.DisciplineVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by theo on 12/29/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "disciplineManager";
    private static final String TABLE_DISCIPLINE = "discipline";

    //colunas

    private static final String KEY_ID = "id";
    private static final String KEY_NOME = "nome";
    private static final String KEY_MAXFALTAS = "maximo_de_faltas";
    private static final String KEY_FALTAS = "faltas";
    private static final String KEY_DIAS = "dias";
    private static final String KEY_HORARIO = "horario";
    private static final String KEY_CREDITOS = "creditos";
    private static final String KEY_COR = "cor";

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
                + KEY_COR + " TEXT"
                + ")";
        db.execSQL(CREATE_DISCIPLINE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISCIPLINE);

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

        // Inserting Row
        db.insert(TABLE_DISCIPLINE, null, values);
        db.close(); // Closing database connection

    }

    public DisciplineVo getDiscipline(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DISCIPLINE, new String[] {
                KEY_ID, KEY_NOME, KEY_MAXFALTAS, KEY_FALTAS, KEY_DIAS, KEY_CREDITOS, KEY_HORARIO, KEY_COR }, KEY_ID + "=?",
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

        return disciplineVo;

    }

    public List<DisciplineVo> getAllDisciplines()
    {
        List<DisciplineVo> disciplineList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_DISCIPLINE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

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

                // Adding to list
                disciplineList.add(disciplineVo);

            } while (cursor.moveToNext());
        }

        db.close();
        return disciplineList;
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

        int update_status = db.update(TABLE_DISCIPLINE, values, KEY_ID + " = ?", new String[] { String.valueOf(disciplineVo.id) });

        db.close();
        return update_status;
    }

    public int addAbsence(DisciplineVo disciplineVo){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FALTAS, disciplineVo.totalFaults + 1);

        int update_status = db.update(TABLE_DISCIPLINE, values, KEY_ID + " = ?", new String[] { String.valueOf(disciplineVo.id) });
        db.close();
        return update_status;
    }

    public int removeAbsence(DisciplineVo disciplineVo){
        if (disciplineVo.totalFaults > 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_FALTAS, disciplineVo.totalFaults - 1);

            int update_status = db.update(TABLE_DISCIPLINE, values, KEY_ID + " = ?", new String[]{String.valueOf(disciplineVo.id)});
            db.close();
            return update_status;
        } else{
            return -1;
        }
    }

    public void deleteDiscipline(DisciplineVo disciplineVo)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISCIPLINE, KEY_ID + " = ?",
                new String[] { String.valueOf(disciplineVo.id) });
        db.close();
    }

    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISCIPLINE,null,null);
        db.execSQL("delete from "+ TABLE_DISCIPLINE);
        //db.execSQL("TRUNCATE table" + TABLE_DISCIPLINE);
        db.close();
    }
}
