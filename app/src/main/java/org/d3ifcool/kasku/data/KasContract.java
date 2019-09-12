package org.d3ifcool.kasku.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class KasContract {

    public static final String CONTENT_AUTHORITY = "org.d3ifcool.kasku";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_KASKU = "kasku";
    public static final String PATH_BI = "bi";
    public static final String PATH_KK = "kk";
    public static final String PATH_MEMBER = "member";

    private KasContract() {
    }


    public static final class ImpianEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BI);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BI;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BI;

        public final static String TABLE_NAME = "barang_impian";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_IMPIAN_NAME = "name";

        public final static String COLUMN_IMPIAN_SIZE = "size";

        public final static String COLUMN_IMPIAN_PRICE = "price";
    }

    public static final class KasEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_KASKU);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_KASKU;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_KASKU;

        public final static String TABLE_NAME = "kas_pribadi";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_KAS_MEMBER_ID = "memberid";

        public final static String COLUMN_KAS_MONEY = "money";

        public final static String COLUMN_KAS_TYPE = "type";

        public final static String COLUMN_KAS_DESC = "description";

        public final static String COLUMN_KAS_DATE = "date";

    }

    public static final class KelompokEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_KK);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_KK;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_KK;

        public final static String TABLE_NAME = "kas_kelompok";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_KELOMPOK_NAME = "name";

        public final static String COLUMN_KELOMPOK_BALANCE = "balance";
        public final static String COLUMN_KELOMPOK_IMAGE = "image";
    }

    public static final class KKMEMBER implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MEMBER);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEMBER;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEMBER;

        public final static String TABLE_NAME = "kkmembers";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_KKMEMBER_GROUP_ID = "groupid";
        public final static String COLUMN_KKMEMBER_NAME = "name";
        public final static String COLUMN_KKMEMBER_BALANCE = "balance";
        public final static String COLUMN_KKMEMBER_DESC = "description";
        public final static String COLUMN_KKMEMBER_GENDER = "gender";
    }
}