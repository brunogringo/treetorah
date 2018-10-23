package com.devbruto.treetorrah.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devbruto.treetorrah.Model.Registro;

import java.util.ArrayList;

public class RegistroDAO extends SQLiteOpenHelper {

    private static final String Database = "DbTreetorah";
    private static final int Version = 1;


    public RegistroDAO(Context context) {
        super(context, Database, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String registro = "CREATE TABLE tbRegistro (Id INTEGER PRIMARY KEY NOT NULL, Ano INTEGER, Volume INTEGER, ArvCortada INTEGER, ArvReposta INTEGER, Estado TEXT, ValorPagar FLOAT);";
        db.execSQL(registro);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String registro = "DROP TABLE IF EXISTS tbProduto";
        db.execSQL(registro);
    }

    //Salvar novo
    public void salvarRegistro(Registro registro) {
        ContentValues values = new ContentValues();

        values.put("Ano", registro.getAno());
        values.put("Volume", registro.getVolume());
        values.put("ArvCortada", registro.getArvoresCortadas());
        values.put("ArvReposta", registro.getArvoresRespostas());
        values.put("Estado", registro.getEstado());
        values.put("ValorPagar", registro.getValorMulta());

        getWritableDatabase().insert("tbRegistro", null, values);
    }

    //Apagar
    public void apagar(Registro registro) {
        String[] args = {Integer.toString(registro.getId())};
        getWritableDatabase().delete("tbRegistro", "Id=?", args);
    }

    //Alterar
    public void alterarRegistro(Registro registro) {
        ContentValues values = new ContentValues();

        values.put("Ano", registro.getAno());
        values.put("Volume", registro.getVolume());
        values.put("ArvCortada", registro.getArvoresCortadas());
        values.put("ArvReposta", registro.getArvoresRespostas());
        values.put("Estado", registro.getEstado());
        values.put("ValorPagar", registro.getValorMulta());

        String[] args = {Integer.toString(registro.getId())};
        getWritableDatabase().update("tbRegistro", values, "Id=?", args);
    }

    //Exibir todos
    public ArrayList<Registro> getLista() {
        String[] columns = new String[]{"Id", "Ano", "Volume", "ArvCortada", "ArvReposta", "Estado", "ValorPagar"};
        Cursor cursor = getWritableDatabase().query("tbRegistro", columns, null, null, null, null, null, null);
        ArrayList<Registro> registros = new ArrayList<Registro>();

        while (cursor.moveToNext()) {
            Registro registro = new Registro();
            registro.setId(cursor.getInt(0));
            registro.setAno(cursor.getInt(1));
            registro.setVolume(cursor.getInt(2));
            registro.setArvoresCortadas(cursor.getInt(3));
            registro.setArvoresRespostas(cursor.getInt(4));
            registro.setEstado(cursor.getString(5));
            registro.setValorMulta(cursor.getFloat(6));

            registros.add(registro);
        }

        return registros;
    }

    public ArrayList<Registro> getListaAno() {

        ArrayList<Registro> registros = new ArrayList<Registro>();

        String query = "SELECT Ano, sum([Volume]), sum([ArvCortada]), sum([ArvReposta]), sum([ValorPagar])FROM tbRegistro group by Ano";
        Cursor cursor = getWritableDatabase().rawQuery(query, null);

        while (cursor.moveToNext()) {
            Registro registro = new Registro();
            registro.setAno(cursor.getInt(0));
            registro.setVolume(cursor.getInt(1));
            registro.setArvoresCortadas(cursor.getInt(2));
            registro.setArvoresRespostas(cursor.getInt(3));
            registro.setValorMulta(cursor.getInt(4));

            registros.add(registro);
        }

        return registros;
    }

    public ArrayList<Registro> getListaEstado() {

        ArrayList<Registro> registros = new ArrayList<Registro>();

        String query = "SELECT Estado, sum([Volume]), sum([ArvCortada]), sum([ArvReposta]), sum([ValorPagar])FROM tbRegistro group by Estado";
        Cursor cursor = getWritableDatabase().rawQuery(query, null);

        while (cursor.moveToNext()) {
            Registro registro = new Registro();
            registro.setEstado(cursor.getString(0));
            registro.setVolume(cursor.getInt(1));
            registro.setArvoresCortadas(cursor.getInt(2));
            registro.setArvoresRespostas(cursor.getInt(3));
            registro.setValorMulta(cursor.getInt(4));

            registros.add(registro);
        }

        return registros;
    }
}
