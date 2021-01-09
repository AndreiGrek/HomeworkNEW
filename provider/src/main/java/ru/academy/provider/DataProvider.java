package ru.academy.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DataProvider extends ContentProvider {

    private ContactDao contactDao;
    private static final String AUTHORITY = "ru.academy.provider";
    public static final int ID_PERSON_DATA = 1;
    public static final int ID_PERSON_DATA_ITEM = 2;
    public static final String TABLE_NAME = "contact";
    private static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME, ID_PERSON_DATA);
        uriMatcher.addURI(AUTHORITY, TABLE_NAME + "/*", ID_PERSON_DATA_ITEM);
    }

    @Override
    public boolean onCreate() {
        contactDao = AppDatabase.getInstance(getContext())
                .contactDao();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case ID_PERSON_DATA:
                cursor = contactDao.FindAll();

                if (getContext() != null) {
                    cursor.setNotificationUri(getContext()
                            .getContentResolver(), uri);
                    return cursor;
                }

            default:
                throw new IllegalArgumentException
                        ("Unknown URI: " + uri);

        }
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
            case ID_PERSON_DATA:
                throw new IllegalArgumentException
                        ("Invalid uri: cannot delete");
            case ID_PERSON_DATA_ITEM:
                if (getContext() != null) {
                    int count = contactDao
                            .delete(ContentUris.parseId(uri));
                    getContext().getContentResolver()
                            .notifyChange(uri, null);
                    return count;
                }
            default:
                throw new IllegalArgumentException
                        ("Unknown URI:" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri,
                      @Nullable ContentValues values) {
        return null;
    }

}
