package org.d3ifcool.kasku.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class KasProvider extends ContentProvider {

    public static final String LOG_TAG = KasProvider.class.getSimpleName();
    private static final int KAS = 102;
    private static final int KAS_ID = 103;
    private static final int BI = 100;
    private static final int BI_ID = 101;
    private static final int KK = 104;
    private static final int KK_ID = 105;
    private static final int KKMEMBER = 106;
    private static final int KKMEMBER_ID = 107;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(KasContract.CONTENT_AUTHORITY, KasContract.PATH_KASKU , KAS);
        sUriMatcher.addURI(KasContract.CONTENT_AUTHORITY, KasContract.PATH_KASKU + "/#", KAS_ID);
        sUriMatcher.addURI(KasContract.CONTENT_AUTHORITY, KasContract.PATH_BI , BI);
        sUriMatcher.addURI(KasContract.CONTENT_AUTHORITY, KasContract.PATH_BI + "/#", BI_ID);
        sUriMatcher.addURI(KasContract.CONTENT_AUTHORITY, KasContract.PATH_KK , KK);
        sUriMatcher.addURI(KasContract.CONTENT_AUTHORITY, KasContract.PATH_KK + "/#", KK_ID);
        sUriMatcher.addURI(KasContract.CONTENT_AUTHORITY, KasContract.PATH_MEMBER , KKMEMBER);
        sUriMatcher.addURI(KasContract.CONTENT_AUTHORITY, KasContract.PATH_MEMBER + "/#", KKMEMBER_ID);
    }

    private KasDbHelper mKasDbHelper;

    @Override
    public boolean onCreate() {
        mKasDbHelper = new KasDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase kasDB = mKasDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case KAS:
                cursor = kasDB.query(KasContract.KasEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case KAS_ID:
                selection = KasContract.KasEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = kasDB.query(KasContract.KasEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BI:
                cursor = kasDB.query(KasContract.ImpianEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BI_ID:
                selection = KasContract.ImpianEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = kasDB.query(KasContract.ImpianEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case KK:
                cursor = kasDB.query(KasContract.KelompokEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case KK_ID:
                selection = KasContract.KelompokEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = kasDB.query(KasContract.KelompokEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case KKMEMBER:
                cursor = kasDB.query(KasContract.KKMEMBER.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case KKMEMBER_ID:
                selection = KasContract.KelompokEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = kasDB.query(KasContract.KKMEMBER.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KAS:
                return KasContract.KasEntry.CONTENT_LIST_TYPE;
            case KAS_ID:
                return KasContract.KasEntry.CONTENT_ITEM_TYPE;
            case BI:
                return KasContract.ImpianEntry.CONTENT_LIST_TYPE;
            case BI_ID:
                return KasContract.ImpianEntry.CONTENT_ITEM_TYPE;
            case KK:
                return KasContract.KelompokEntry.CONTENT_LIST_TYPE;
            case KK_ID:
                return KasContract.KelompokEntry.CONTENT_ITEM_TYPE;
            case KKMEMBER:
                return KasContract.KKMEMBER.CONTENT_LIST_TYPE;
            case KKMEMBER_ID:
                return KasContract.KKMEMBER.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KAS:
                return inserData(uri, values, 0);
            case BI :
                return inserData(uri, values, 1);
            case KK :
                return inserData(uri, values, 2);
            case KKMEMBER :
                return inserData(uri, values, 3);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri inserData(Uri uri, ContentValues values, int type) {
        long id = -1;
        SQLiteDatabase database = mKasDbHelper.getWritableDatabase();
        switch (type) {
            case 0 :
                 id = database.insert(KasContract.KasEntry.TABLE_NAME, null, values);
                 break;
            case 1 :
                id = database.insert(KasContract.ImpianEntry.TABLE_NAME, null, values);
                break;
            case 2 :
                id = database.insert(KasContract.KelompokEntry.TABLE_NAME, null, values);
                break;
            case 3 :
                id = database.insert(KasContract.KKMEMBER.TABLE_NAME, null, values);
                break;
        }
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase kasDB = mKasDbHelper.getWritableDatabase();


        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case KAS:
                rowsDeleted = kasDB.delete(KasContract.KasEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case KAS_ID:
                selection = KasContract.KasEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = kasDB.delete(KasContract.KasEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case BI:
                rowsDeleted = kasDB.delete(KasContract.ImpianEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case BI_ID:
                selection = KasContract.ImpianEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = kasDB.delete(KasContract.ImpianEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case KK:
                rowsDeleted = kasDB.delete(KasContract.KelompokEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case KK_ID:
                selection = KasContract.KelompokEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = kasDB.delete(KasContract.KelompokEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case KKMEMBER:
                rowsDeleted = kasDB.delete(KasContract.KKMEMBER.TABLE_NAME, selection, selectionArgs);
                break;
            case KKMEMBER_ID:
                selection = KasContract.KelompokEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = kasDB.delete(KasContract.KKMEMBER.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case KAS:
                return updateData(uri, values, selection, selectionArgs, 1);
            case KAS_ID:
                selection = KasContract.KasEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateData(uri, values, selection, selectionArgs, 1 );
            case BI:
                return updateData(uri, values, selection, selectionArgs, 2);
            case BI_ID:
                selection = KasContract.ImpianEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateData(uri, values, selection, selectionArgs, 2);
            case KK:
                return updateData(uri, values, selection, selectionArgs, 3);
            case KK_ID:
                selection = KasContract.KelompokEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateData(uri, values, selection, selectionArgs, 3);
            case KKMEMBER:
                return updateData(uri, values, selection, selectionArgs, 4);
            case KKMEMBER_ID:
                selection = KasContract.KelompokEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateData(uri, values, selection, selectionArgs, 4);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateData(Uri uri, ContentValues values, String selection, String[] selectionArgs, int type) {
        SQLiteDatabase database = mKasDbHelper.getWritableDatabase();
        if (values.size() == 0) {
            return 0;
        }
        int rowsUpdated = 0;
        switch (type) {
            case 1:
                rowsUpdated = database.update(KasContract.KasEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 2:
                rowsUpdated = database.update(KasContract.ImpianEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 3:
                rowsUpdated = database.update(KasContract.KelompokEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case 4:
                rowsUpdated = database.update(KasContract.KKMEMBER.TABLE_NAME, values, selection, selectionArgs);
                break;

        }


        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }



}
