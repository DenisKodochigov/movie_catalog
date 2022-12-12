package com.example.movie_catalog.data.room;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DataDao_Impl implements DataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FilmDB> __insertionAdapterOfFilmDB;

  private final EntityDeletionOrUpdateAdapter<FilmDB> __updateAdapterOfFilmDB;

  private final SharedSQLiteStatement __preparedStmtOfNukeTable;

  public DataDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFilmDB = new EntityInsertionAdapter<FilmDB>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `films` (`id`,`filmId`,`msg`,`timestamp`,`viewed`,`bookmark`,`favorite`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FilmDB value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getFilmId());
        if (value.getMsg() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMsg());
        }
        stmt.bindLong(4, value.getTimestamp());
        final int _tmp = value.getViewed() ? 1 : 0;
        stmt.bindLong(5, _tmp);
        final int _tmp_1 = value.getBookmark() ? 1 : 0;
        stmt.bindLong(6, _tmp_1);
        final int _tmp_2 = value.getFavorite() ? 1 : 0;
        stmt.bindLong(7, _tmp_2);
      }
    };
    this.__updateAdapterOfFilmDB = new EntityDeletionOrUpdateAdapter<FilmDB>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `films` SET `id` = ?,`filmId` = ?,`msg` = ?,`timestamp` = ?,`viewed` = ?,`bookmark` = ?,`favorite` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FilmDB value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getFilmId());
        if (value.getMsg() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getMsg());
        }
        stmt.bindLong(4, value.getTimestamp());
        final int _tmp = value.getViewed() ? 1 : 0;
        stmt.bindLong(5, _tmp);
        final int _tmp_1 = value.getBookmark() ? 1 : 0;
        stmt.bindLong(6, _tmp_1);
        final int _tmp_2 = value.getFavorite() ? 1 : 0;
        stmt.bindLong(7, _tmp_2);
        stmt.bindLong(8, value.getId());
      }
    };
    this.__preparedStmtOfNukeTable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM films";
        return _query;
      }
    };
  }

  @Override
  public void insert(final FilmDB... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFilmDB.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final FilmDB film) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfFilmDB.handle(film);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void nukeTable() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfNukeTable.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfNukeTable.release(_stmt);
    }
  }

  @Override
  public FilmDB getAll() {
    final String _sql = "SELECT * FROM films ORDER BY filmId DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFilmId = CursorUtil.getColumnIndexOrThrow(_cursor, "filmId");
      final int _cursorIndexOfMsg = CursorUtil.getColumnIndexOrThrow(_cursor, "msg");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfViewed = CursorUtil.getColumnIndexOrThrow(_cursor, "viewed");
      final int _cursorIndexOfBookmark = CursorUtil.getColumnIndexOrThrow(_cursor, "bookmark");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final FilmDB _result;
      if(_cursor.moveToFirst()) {
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final int _tmpFilmId;
        _tmpFilmId = _cursor.getInt(_cursorIndexOfFilmId);
        final String _tmpMsg;
        if (_cursor.isNull(_cursorIndexOfMsg)) {
          _tmpMsg = null;
        } else {
          _tmpMsg = _cursor.getString(_cursorIndexOfMsg);
        }
        final long _tmpTimestamp;
        _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        final boolean _tmpViewed;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfViewed);
        _tmpViewed = _tmp != 0;
        final boolean _tmpBookmark;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfBookmark);
        _tmpBookmark = _tmp_1 != 0;
        final boolean _tmpFavorite;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfFavorite);
        _tmpFavorite = _tmp_2 != 0;
        _result = new FilmDB(_tmpId,_tmpFilmId,_tmpMsg,_tmpTimestamp,_tmpViewed,_tmpBookmark,_tmpFavorite);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public FilmDB getFilm(final int id) {
    final String _sql = "SELECT * FROM films WHERE filmId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFilmId = CursorUtil.getColumnIndexOrThrow(_cursor, "filmId");
      final int _cursorIndexOfMsg = CursorUtil.getColumnIndexOrThrow(_cursor, "msg");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfViewed = CursorUtil.getColumnIndexOrThrow(_cursor, "viewed");
      final int _cursorIndexOfBookmark = CursorUtil.getColumnIndexOrThrow(_cursor, "bookmark");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final FilmDB _result;
      if(_cursor.moveToFirst()) {
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        final int _tmpFilmId;
        _tmpFilmId = _cursor.getInt(_cursorIndexOfFilmId);
        final String _tmpMsg;
        if (_cursor.isNull(_cursorIndexOfMsg)) {
          _tmpMsg = null;
        } else {
          _tmpMsg = _cursor.getString(_cursorIndexOfMsg);
        }
        final long _tmpTimestamp;
        _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        final boolean _tmpViewed;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfViewed);
        _tmpViewed = _tmp != 0;
        final boolean _tmpBookmark;
        final int _tmp_1;
        _tmp_1 = _cursor.getInt(_cursorIndexOfBookmark);
        _tmpBookmark = _tmp_1 != 0;
        final boolean _tmpFavorite;
        final int _tmp_2;
        _tmp_2 = _cursor.getInt(_cursorIndexOfFavorite);
        _tmpFavorite = _tmp_2 != 0;
        _result = new FilmDB(_tmpId,_tmpFilmId,_tmpMsg,_tmpTimestamp,_tmpViewed,_tmpBookmark,_tmpFavorite);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean getViewed(final int id) {
    final String _sql = "SELECT viewed FROM films WHERE filmId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean getFavorite(final int id) {
    final String _sql = "SELECT favorite FROM films WHERE filmId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean getBookmark(final int id) {
    final String _sql = "SELECT bookmark FROM films WHERE filmId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean setViewed(final int id) {
    final String _sql = "SELECT viewed FROM films WHERE filmId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean setFavorite(final int id) {
    final String _sql = "SELECT favorite FROM films WHERE filmId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean setBookmark(final int id) {
    final String _sql = "SELECT bookmark FROM films WHERE filmId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final boolean _result;
      if(_cursor.moveToFirst()) {
        final int _tmp;
        _tmp = _cursor.getInt(0);
        _result = _tmp != 0;
      } else {
        _result = false;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
