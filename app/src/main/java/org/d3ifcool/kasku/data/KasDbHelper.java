package org.d3ifcool.kasku.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import org.d3ifcool.kasku.data.KasContract.KasEntry;
import org.d3ifcool.kasku.data.KasContract.KKMEMBER;
import org.d3ifcool.kasku.data.KasContract.ImpianEntry;
import org.d3ifcool.kasku.data.KasContract.KelompokEntry;

public class KasDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "kaskuSQL.db";
    private static final int DATABASE_VERSION = 3;

    public KasDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_KAS_TABLE = "CREATE TABLE " + KasEntry.TABLE_NAME + "("
                + KasEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KasEntry.COLUMN_KAS_MEMBER_ID + " NUMBER NOT NULL DEFAULT -1, "
                + KasEntry.COLUMN_KAS_MONEY + " NUMBER NOT NULL DEFAULT 0, "
                + KasEntry.COLUMN_KAS_TYPE + " TEXT, "
                + KasEntry.COLUMN_KAS_DESC + " TEXT, "
                + KasEntry.COLUMN_KAS_DATE + " TEXT);";
        db.execSQL(SQL_CREATE_KAS_TABLE);

        String SQL_CREATE_BI_TABLE = "CREATE TABLE " + ImpianEntry.TABLE_NAME + "("
                + ImpianEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ImpianEntry.COLUMN_IMPIAN_NAME + " TEXT NOT NULL, "
                + ImpianEntry.COLUMN_IMPIAN_SIZE + " NUMBER NOT NULL DEFAULT 0, "
                + ImpianEntry.COLUMN_IMPIAN_PRICE + " NUMBER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_BI_TABLE);

        String SQL_CREATE_KK_TABLE = "CREATE TABLE " + KelompokEntry.TABLE_NAME + "("
                + KelompokEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KelompokEntry.COLUMN_KELOMPOK_NAME + " TEXT NOT NULL, "
                + KelompokEntry.COLUMN_KELOMPOK_BALANCE + " NUMBER NOT NULL DEFAULT 0, "
                + KelompokEntry.COLUMN_KELOMPOK_IMAGE + " BLOB);";
        db.execSQL(SQL_CREATE_KK_TABLE);

        String SQL_CREATE_KKMEMBER_TABLE = "CREATE TABLE " + KKMEMBER.TABLE_NAME + "("
                + KKMEMBER._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KKMEMBER.COLUMN_KKMEMBER_GROUP_ID + " NUMBER NOT NULL DEFAULT 0, "
                + KKMEMBER.COLUMN_KKMEMBER_NAME + " TEXT NOT NULL, "
                + KKMEMBER.COLUMN_KKMEMBER_BALANCE + " NUMBER NOT NULL DEFAULT 0, "
                + KKMEMBER.COLUMN_KKMEMBER_DESC + " TEXT NOT NULL, "
                + KKMEMBER.COLUMN_KKMEMBER_GENDER + " NUMBER NOT NULL DEFAULT 0);";
        db.execSQL(SQL_CREATE_KKMEMBER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
