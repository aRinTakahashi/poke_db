package site.rinax.poke_db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "master.db";
    private static final int DATABASE_VERSION = 1;
    Context mContext;

    public  DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {





        String create = "CREATE TABLE master (_id INTEGER PRIMARY KEY, num INTEGER, name TEXT, hira TEXT);";
        db.execSQL(create);
        InputStream is = null;
        BufferedReader buffer = null;
        String line = "";

        try {
            try {
                is = mContext.getAssets().open("master.csv");
                buffer = new BufferedReader(new InputStreamReader(is));
                while ((line = buffer.readLine()) != null) {
                    String[] str = line.split(",");
                    String splInsert = "INSERT INTO master (_id, num, name, hira) VALUES (?, ?, ?, ?)";
                    SQLiteStatement stmt = db.compileStatement(splInsert);

                    stmt.bindLong(1,Integer.parseInt(str[0]));
                    stmt.bindLong(2,Integer.parseInt(str[1]));
                    stmt.bindString(3,str[2]);
                    stmt.bindString(4,str[3]);

                    stmt.executeInsert();
                }
            }finally {
                if (is != null) is.close();
                if (buffer != null) buffer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }




        create = "CREATE TABLE type (num INTEGER, type_num INTEGER, type INTEGER);";
        db.execSQL(create);
        is = null;
        buffer = null;
        line = "";
        try {
            try {
                is = mContext.getAssets().open("type.csv");
                buffer = new BufferedReader(new InputStreamReader(is));
                while ((line = buffer.readLine()) != null) {
                    String[] str = line.split(",");
                    String splInsert = "INSERT INTO type (num, type_num, type) VALUES (?, ?, ?)";
                    SQLiteStatement stmt = db.compileStatement(splInsert);

                    stmt.bindLong(1,Integer.parseInt(str[0]));
                    stmt.bindLong(2,Integer.parseInt(str[1]));
                    stmt.bindLong(3,Integer.parseInt(str[2]));
                    stmt.executeInsert();
                }
            }finally {
                if (is != null) is.close();
                if (buffer != null) buffer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }









        create = "CREATE TABLE img (_id INTEGER, file_name TEXT);";
        db.execSQL(create);
        is = null;
        buffer = null;
        line = "";
        try {
            try {
                is = mContext.getAssets().open("urlList.csv");
                buffer = new BufferedReader(new InputStreamReader(is));
                while ((line = buffer.readLine()) != null) {
                    String[] str = line.split(",");
                    String splInsert = "INSERT INTO img (_id, file_name) VALUES (?, ?)";
                    SQLiteStatement stmt = db.compileStatement(splInsert);

                    stmt.bindLong(1,Integer.parseInt(str[0]));
                    stmt.bindString(2,str[1]);
                    stmt.executeInsert();
                }
            }finally {
                if (is != null) is.close();
                if (buffer != null) buffer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        create = "CREATE TABLE param (_id INTEGER, h INTEGER, a INTEGER, b INTEGER, c INTEGER, d INTEGER, s INTEGER, sum INTEGER);";
        db.execSQL(create);
        is = null;
        buffer = null;
        line = "";
        try {
            try {
                is = mContext.getAssets().open("status.csv");
                buffer = new BufferedReader(new InputStreamReader(is));
                while ((line = buffer.readLine()) != null) {
                    String[] str = line.split(",");
                    String splInsert = "INSERT INTO param (_id, h,a,b,c,d,s,sum) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    SQLiteStatement stmt = db.compileStatement(splInsert);

                    stmt.bindLong(1,Integer.parseInt(str[0]));
                    stmt.bindLong(2,Integer.parseInt(str[1]));
                    stmt.bindLong(3,Integer.parseInt(str[2]));
                    stmt.bindLong(4,Integer.parseInt(str[3]));
                    stmt.bindLong(5,Integer.parseInt(str[4]));
                    stmt.bindLong(6,Integer.parseInt(str[5]));
                    stmt.bindLong(7,Integer.parseInt(str[6]));
                    int sum = 0;
                    for (int i = 0;i<6;i++){
                        sum+=Integer.parseInt(str[i+1]);
                    }
                    stmt.bindLong(8,sum);
                    stmt.executeInsert();
                }
            }finally {
                if (is != null) is.close();
                if (buffer != null) buffer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        create = "CREATE TABLE chara (_id INTEGER, chara_num INTEGER, chara TEXT);";
        db.execSQL(create);
        is = null;
        buffer = null;
        line = "";
        try {
            try {
                is = mContext.getAssets().open("characteristics.csv");
                buffer = new BufferedReader(new InputStreamReader(is));
                while ((line = buffer.readLine()) != null) {
                    String[] str = line.split(",");
                    String splInsert = "INSERT INTO chara (_id, chara_num, chara) VALUES (?, ?, ?)";
                    SQLiteStatement stmt = db.compileStatement(splInsert);

                    stmt.bindLong(1,Integer.parseInt(str[0]));
                    stmt.bindLong(2,Integer.parseInt(str[1]));
                    stmt.bindString(3,str[2]);
                    stmt.executeInsert();
                }
            }finally {
                if (is != null) is.close();
                if (buffer != null) buffer.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }

        @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

