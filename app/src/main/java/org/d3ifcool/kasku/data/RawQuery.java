package org.d3ifcool.kasku.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.CursorLoader;

public class RawQuery {

    public int[] getsum(Context context, int type, String id) {
        KasDbHelper mKasDbHelper = new KasDbHelper(context);
        SQLiteDatabase kasDB = mKasDbHelper.getReadableDatabase();
        int total[];
        switch (type) {
            case 1:
                total = new int[2];
                Cursor add = kasDB.rawQuery("SELECT SUM(money) FROM kas_pribadi WHERE type = ? AND memberid = ?", new String[] {"1",id});
                Cursor rem = kasDB.rawQuery("SELECT SUM(money) FROM kas_pribadi WHERE type = ? AND memberid = ?",new String[] {"2",id});
                Cursor addSave = kasDB.rawQuery("SELECT SUM(money) FROM kas_pribadi WHERE type = ? AND memberid = ?", new String[] {"3",id});
                Cursor remSave = kasDB.rawQuery("SELECT SUM(money) FROM kas_pribadi WHERE type = ? AND memberid = ?", new String[] {"4",id});
                try {
                    if (add.moveToFirst()) total[0] = add.getInt(0);
                    if (rem.moveToFirst()) total[0] -= rem.getInt(0);
                    if (addSave.moveToFirst()) total[1] = addSave.getInt(0);
                    if (remSave.moveToFirst()) total[1] -= remSave.getInt(0);
                } finally {
                    kasDB.close();
                }


                add.close();
                rem.close();
                addSave.close();
                remSave.close();
                ContentValues values = new ContentValues();
                values.put(KasContract.KKMEMBER.COLUMN_KKMEMBER_BALANCE, total[0]);

                if (!id.equals("-1") && !id.contains("-99"))
                    context.getContentResolver().update(KasContract.KKMEMBER.CONTENT_URI, values,KasContract.KKMEMBER._ID + " = ? ",new String[]{id} );
                return total;

            case 2 :
                total = new int[2];
                total[0] = 0;
                total[1] = 0;
                Cursor cur = kasDB.rawQuery("SELECT price, size FROM barang_impian", null);
                try {
                    for (int i = 0; i < cur.getCount();i++){
                        if(cur.moveToNext()) {
                            total[0] += cur.getInt(0) * cur.getInt(1);
                            total[1] += cur.getInt(1);
                        }
                    }
                } finally {
                    kasDB.close();
                }
                    cur.close();

                return total;
            case 3 :
                total = new int[2];
                Cursor cur1 = kasDB.rawQuery("SELECT SUM(balance), COUNT(balance) FROM kkmembers WHERE groupid = ?", new String[] {id});

                try {
                    if(cur1.moveToFirst()) {
                        total[0] = cur1.getInt(0);
                        total[1] = cur1.getInt(1);
                    }
                } finally {
                    kasDB.close();
                }
                String kasId = "-99"+id;
                total[0] += getsum(context,1,kasId)[0];

                ContentValues values3 = new ContentValues();
                values3.put(KasContract.KelompokEntry.COLUMN_KELOMPOK_BALANCE, total[0]);

                if (!id.equals("-1") && !id.contains("-99"))
                    context.getContentResolver().update(KasContract.KelompokEntry.CONTENT_URI, values3,KasContract.KelompokEntry._ID + " = ? ",new String[]{id} );

                return total;

            default:
                return null;
        }
    }
}
