package com.example.popularmovies.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.example.popularmovies.datamodel.DaoSession;
import com.example.popularmovies.datamodel.MovieDao;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

public class MoviesContentProvider extends ContentProvider {

    public static final String TAG = MoviesContentProvider.class.getSimpleName();
    //BASE
    public static final String AUTHORITY = "com.example.popularmovies";
    public static final String BASE_PATH = "api";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
    public static final String PATH_MOVIE = "/MOVIES";
    //URIs
    public static final String MOVIE_ENDPOINT = BASE_PATH + PATH_MOVIE;
    public static final Uri MOVIE_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH + PATH_MOVIE);
    //MIME TYPES
    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE  + "/vnd." + AUTHORITY;
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTHORITY;
    //URI MATCHER
    private static final UriMatcher uriMatcher;
    private static final int MOVIES = 1;
    private static final int MOVIES_ID = 2;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, MOVIE_ENDPOINT, MOVIES);
        uriMatcher.addURI(AUTHORITY, MOVIE_ENDPOINT + "/#", MOVIES_ID);
    }

    private static final String TABLENAME = MovieDao.TABLENAME; // = "MOVIE"
    private static final String PK = MovieDao.Properties.Id.columnName;  // == "_id"

    public static DaoSession daoSession;

    //CONSTRUCTOR & INIT
    public MoviesContentProvider( ) {

    }
    @Override
    public boolean onCreate() {
        Log.i(TAG,"Content Provider started: " + CONTENT_URI);
        return true;
    }

    private Database getDatabase() {
        if(daoSession == null) {
            throw new IllegalStateException("DaoSession must be set during content provider is active");
        }
        return daoSession.getDatabase();
    }

    @Override
    public String getType(Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match)
        {
            case MOVIES:
                return CONTENT_TYPE;
            case MOVIES_ID:
                return CONTENT_ITEM_TYPE;
            default:
                return null;
        }
    }

    //OPERATIONS
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = ((StandardDatabase) getDatabase()).getSQLiteDatabase();
        int rowsDeleted = 0;
        String id;
        switch (uriType) {
            case MOVIES:
            rowsDeleted = db.delete(TABLENAME, selection, selectionArgs);
            break;
            case MOVIES_ID:
            id = uri.getLastPathSegment();
            if (TextUtils.isEmpty(selection)) {
                rowsDeleted = db.delete(TABLENAME, PK + "=" + id, null);
            } else {
                rowsDeleted = db.delete(TABLENAME, PK + "=" + id + " and "
                        + selection, selectionArgs);
            }
            break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = ((StandardDatabase) getDatabase()).getSQLiteDatabase();
        long id = 0;
        String path = "";
        switch (uriType) {
            case MOVIES:
            id = db.insert(TABLENAME, null, values);
            path = BASE_PATH + "/" + id;
            break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(path);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case MOVIES:
            queryBuilder.setTables(TABLENAME);
            break;
            case MOVIES_ID:
            queryBuilder.setTables(TABLENAME);
            queryBuilder.appendWhere(PK + "=" + uri.getLastPathSegment());
            break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        Database db = getDatabase();
        Cursor cursor = queryBuilder.query(((StandardDatabase) db).getSQLiteDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase db = ((StandardDatabase) getDatabase()).getSQLiteDatabase();
        int rowsUpdated = 0;
        String id;

        switch (uriType) {
            case MOVIES:
                rowsUpdated = db.update(TABLENAME, values, selection, selectionArgs);
                break;
            case MOVIES_ID:
                id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = db.update(TABLENAME, values, PK + "=" + id, null);
                } else {
                    rowsUpdated = db.update(TABLENAME, values, PK + "=" + id
                            + " and " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }
}