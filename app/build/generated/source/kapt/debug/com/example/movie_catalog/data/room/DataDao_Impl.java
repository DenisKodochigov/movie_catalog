package com.example.movie_catalog.data.room;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.movie_catalog.data.api.home.seasons.SeasonDTO;
import com.example.movie_catalog.data.room.tables.CollectionDB;
import com.example.movie_catalog.data.room.tables.ConverterForFilmDB;
import com.example.movie_catalog.data.room.tables.CrossFC;
import com.example.movie_catalog.data.room.tables.FilmDB;
import com.example.movie_catalog.entity.Collection;
import com.example.movie_catalog.entity.Country;
import com.example.movie_catalog.entity.Film;
import com.example.movie_catalog.entity.Genre;
import com.example.movie_catalog.entity.filminfo.ImageFilm;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Double;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class DataDao_Impl implements DataDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<FilmDB> __insertionAdapterOfFilmDB;

  private final ConverterForFilmDB __converterForFilmDB = new ConverterForFilmDB();

  private final EntityInsertionAdapter<CollectionDB> __insertionAdapterOfCollectionDB;

  private final EntityInsertionAdapter<CrossFC> __insertionAdapterOfCrossFC;

  private final EntityDeletionOrUpdateAdapter<FilmDB> __updateAdapterOfFilmDB;

  private final EntityDeletionOrUpdateAdapter<CrossFC> __updateAdapterOfCrossFC;

  private final SharedSQLiteStatement __preparedStmtOfNukeTable;

  private final SharedSQLiteStatement __preparedStmtOfSetAllViewed;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByIdFilmDB;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByIdCollection;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByIdCrossFC;

  public DataDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFilmDB = new EntityInsertionAdapter<FilmDB>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `films` (`idFilm`,`msg`,`filmId`,`imdbId`,`nameRu`,`nameEn`,`rating`,`posterUrlPreview`,`countries`,`genres`,`favorite`,`viewed`,`bookmark`,`professionKey`,`startYear`,`images`,`posterUrl`,`logoUrl`,`nameOriginal`,`ratingImdb`,`ratingAwait`,`ratingGoodReview`,`year`,`totalSeasons`,`listSeasons`,`description`,`shortDescription`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FilmDB value) {
        stmt.bindLong(1, value.getIdFilm());
        if (value.getMsg() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMsg());
        }
        final Film _tmpFilm = value.getFilm();
        if(_tmpFilm != null) {
          if (_tmpFilm.getFilmId() == null) {
            stmt.bindNull(3);
          } else {
            stmt.bindLong(3, _tmpFilm.getFilmId());
          }
          if (_tmpFilm.getImdbId() == null) {
            stmt.bindNull(4);
          } else {
            stmt.bindString(4, _tmpFilm.getImdbId());
          }
          if (_tmpFilm.getNameRu() == null) {
            stmt.bindNull(5);
          } else {
            stmt.bindString(5, _tmpFilm.getNameRu());
          }
          if (_tmpFilm.getNameEn() == null) {
            stmt.bindNull(6);
          } else {
            stmt.bindString(6, _tmpFilm.getNameEn());
          }
          if (_tmpFilm.getRating() == null) {
            stmt.bindNull(7);
          } else {
            stmt.bindDouble(7, _tmpFilm.getRating());
          }
          if (_tmpFilm.getPosterUrlPreview() == null) {
            stmt.bindNull(8);
          } else {
            stmt.bindString(8, _tmpFilm.getPosterUrlPreview());
          }
          final String _tmp = __converterForFilmDB.countryToJSON(_tmpFilm.getCountries());
          if (_tmp == null) {
            stmt.bindNull(9);
          } else {
            stmt.bindString(9, _tmp);
          }
          final String _tmp_1 = __converterForFilmDB.genreToJSON(_tmpFilm.getGenres());
          if (_tmp_1 == null) {
            stmt.bindNull(10);
          } else {
            stmt.bindString(10, _tmp_1);
          }
          final int _tmp_2 = _tmpFilm.getFavorite() ? 1 : 0;
          stmt.bindLong(11, _tmp_2);
          final int _tmp_3 = _tmpFilm.getViewed() ? 1 : 0;
          stmt.bindLong(12, _tmp_3);
          final int _tmp_4 = _tmpFilm.getBookmark() ? 1 : 0;
          stmt.bindLong(13, _tmp_4);
          if (_tmpFilm.getProfessionKey() == null) {
            stmt.bindNull(14);
          } else {
            stmt.bindString(14, _tmpFilm.getProfessionKey());
          }
          if (_tmpFilm.getStartYear() == null) {
            stmt.bindNull(15);
          } else {
            stmt.bindLong(15, _tmpFilm.getStartYear());
          }
          final String _tmp_5 = __converterForFilmDB.imageToJSON(_tmpFilm.getImages());
          if (_tmp_5 == null) {
            stmt.bindNull(16);
          } else {
            stmt.bindString(16, _tmp_5);
          }
          if (_tmpFilm.getPosterUrl() == null) {
            stmt.bindNull(17);
          } else {
            stmt.bindString(17, _tmpFilm.getPosterUrl());
          }
          if (_tmpFilm.getLogoUrl() == null) {
            stmt.bindNull(18);
          } else {
            stmt.bindString(18, _tmpFilm.getLogoUrl());
          }
          if (_tmpFilm.getNameOriginal() == null) {
            stmt.bindNull(19);
          } else {
            stmt.bindString(19, _tmpFilm.getNameOriginal());
          }
          if (_tmpFilm.getRatingImdb() == null) {
            stmt.bindNull(20);
          } else {
            stmt.bindDouble(20, _tmpFilm.getRatingImdb());
          }
          if (_tmpFilm.getRatingAwait() == null) {
            stmt.bindNull(21);
          } else {
            stmt.bindDouble(21, _tmpFilm.getRatingAwait());
          }
          if (_tmpFilm.getRatingGoodReview() == null) {
            stmt.bindNull(22);
          } else {
            stmt.bindDouble(22, _tmpFilm.getRatingGoodReview());
          }
          if (_tmpFilm.getYear() == null) {
            stmt.bindNull(23);
          } else {
            stmt.bindLong(23, _tmpFilm.getYear());
          }
          if (_tmpFilm.getTotalSeasons() == null) {
            stmt.bindNull(24);
          } else {
            stmt.bindLong(24, _tmpFilm.getTotalSeasons());
          }
          final String _tmp_6 = __converterForFilmDB.seasonToJSON(_tmpFilm.getListSeasons());
          if (_tmp_6 == null) {
            stmt.bindNull(25);
          } else {
            stmt.bindString(25, _tmp_6);
          }
          if (_tmpFilm.getDescription() == null) {
            stmt.bindNull(26);
          } else {
            stmt.bindString(26, _tmpFilm.getDescription());
          }
          if (_tmpFilm.getShortDescription() == null) {
            stmt.bindNull(27);
          } else {
            stmt.bindString(27, _tmpFilm.getShortDescription());
          }
        } else {
          stmt.bindNull(3);
          stmt.bindNull(4);
          stmt.bindNull(5);
          stmt.bindNull(6);
          stmt.bindNull(7);
          stmt.bindNull(8);
          stmt.bindNull(9);
          stmt.bindNull(10);
          stmt.bindNull(11);
          stmt.bindNull(12);
          stmt.bindNull(13);
          stmt.bindNull(14);
          stmt.bindNull(15);
          stmt.bindNull(16);
          stmt.bindNull(17);
          stmt.bindNull(18);
          stmt.bindNull(19);
          stmt.bindNull(20);
          stmt.bindNull(21);
          stmt.bindNull(22);
          stmt.bindNull(23);
          stmt.bindNull(24);
          stmt.bindNull(25);
          stmt.bindNull(26);
          stmt.bindNull(27);
        }
      }
    };
    this.__insertionAdapterOfCollectionDB = new EntityInsertionAdapter<CollectionDB>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `collections` (`idCollection`,`name`,`count`,`image`,`included`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CollectionDB value) {
        stmt.bindLong(1, value.getIdCollection());
        final Collection _tmpCollection = value.getCollection();
        if(_tmpCollection != null) {
          if (_tmpCollection.getName() == null) {
            stmt.bindNull(2);
          } else {
            stmt.bindString(2, _tmpCollection.getName());
          }
          stmt.bindLong(3, _tmpCollection.getCount());
          stmt.bindLong(4, _tmpCollection.getImage());
          final int _tmp = _tmpCollection.getIncluded() ? 1 : 0;
          stmt.bindLong(5, _tmp);
        } else {
          stmt.bindNull(2);
          stmt.bindNull(3);
          stmt.bindNull(4);
          stmt.bindNull(5);
        }
      }
    };
    this.__insertionAdapterOfCrossFC = new EntityInsertionAdapter<CrossFC>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `crossFC` (`id`,`film_id`,`collection_id`,`value`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CrossFC value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getFilm_id());
        stmt.bindLong(3, value.getCollection_id());
        final int _tmp = value.getValue() ? 1 : 0;
        stmt.bindLong(4, _tmp);
      }
    };
    this.__updateAdapterOfFilmDB = new EntityDeletionOrUpdateAdapter<FilmDB>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `films` SET `idFilm` = ?,`msg` = ?,`filmId` = ?,`imdbId` = ?,`nameRu` = ?,`nameEn` = ?,`rating` = ?,`posterUrlPreview` = ?,`countries` = ?,`genres` = ?,`favorite` = ?,`viewed` = ?,`bookmark` = ?,`professionKey` = ?,`startYear` = ?,`images` = ?,`posterUrl` = ?,`logoUrl` = ?,`nameOriginal` = ?,`ratingImdb` = ?,`ratingAwait` = ?,`ratingGoodReview` = ?,`year` = ?,`totalSeasons` = ?,`listSeasons` = ?,`description` = ?,`shortDescription` = ? WHERE `idFilm` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, FilmDB value) {
        stmt.bindLong(1, value.getIdFilm());
        if (value.getMsg() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getMsg());
        }
        final Film _tmpFilm = value.getFilm();
        if(_tmpFilm != null) {
          if (_tmpFilm.getFilmId() == null) {
            stmt.bindNull(3);
          } else {
            stmt.bindLong(3, _tmpFilm.getFilmId());
          }
          if (_tmpFilm.getImdbId() == null) {
            stmt.bindNull(4);
          } else {
            stmt.bindString(4, _tmpFilm.getImdbId());
          }
          if (_tmpFilm.getNameRu() == null) {
            stmt.bindNull(5);
          } else {
            stmt.bindString(5, _tmpFilm.getNameRu());
          }
          if (_tmpFilm.getNameEn() == null) {
            stmt.bindNull(6);
          } else {
            stmt.bindString(6, _tmpFilm.getNameEn());
          }
          if (_tmpFilm.getRating() == null) {
            stmt.bindNull(7);
          } else {
            stmt.bindDouble(7, _tmpFilm.getRating());
          }
          if (_tmpFilm.getPosterUrlPreview() == null) {
            stmt.bindNull(8);
          } else {
            stmt.bindString(8, _tmpFilm.getPosterUrlPreview());
          }
          final String _tmp = __converterForFilmDB.countryToJSON(_tmpFilm.getCountries());
          if (_tmp == null) {
            stmt.bindNull(9);
          } else {
            stmt.bindString(9, _tmp);
          }
          final String _tmp_1 = __converterForFilmDB.genreToJSON(_tmpFilm.getGenres());
          if (_tmp_1 == null) {
            stmt.bindNull(10);
          } else {
            stmt.bindString(10, _tmp_1);
          }
          final int _tmp_2 = _tmpFilm.getFavorite() ? 1 : 0;
          stmt.bindLong(11, _tmp_2);
          final int _tmp_3 = _tmpFilm.getViewed() ? 1 : 0;
          stmt.bindLong(12, _tmp_3);
          final int _tmp_4 = _tmpFilm.getBookmark() ? 1 : 0;
          stmt.bindLong(13, _tmp_4);
          if (_tmpFilm.getProfessionKey() == null) {
            stmt.bindNull(14);
          } else {
            stmt.bindString(14, _tmpFilm.getProfessionKey());
          }
          if (_tmpFilm.getStartYear() == null) {
            stmt.bindNull(15);
          } else {
            stmt.bindLong(15, _tmpFilm.getStartYear());
          }
          final String _tmp_5 = __converterForFilmDB.imageToJSON(_tmpFilm.getImages());
          if (_tmp_5 == null) {
            stmt.bindNull(16);
          } else {
            stmt.bindString(16, _tmp_5);
          }
          if (_tmpFilm.getPosterUrl() == null) {
            stmt.bindNull(17);
          } else {
            stmt.bindString(17, _tmpFilm.getPosterUrl());
          }
          if (_tmpFilm.getLogoUrl() == null) {
            stmt.bindNull(18);
          } else {
            stmt.bindString(18, _tmpFilm.getLogoUrl());
          }
          if (_tmpFilm.getNameOriginal() == null) {
            stmt.bindNull(19);
          } else {
            stmt.bindString(19, _tmpFilm.getNameOriginal());
          }
          if (_tmpFilm.getRatingImdb() == null) {
            stmt.bindNull(20);
          } else {
            stmt.bindDouble(20, _tmpFilm.getRatingImdb());
          }
          if (_tmpFilm.getRatingAwait() == null) {
            stmt.bindNull(21);
          } else {
            stmt.bindDouble(21, _tmpFilm.getRatingAwait());
          }
          if (_tmpFilm.getRatingGoodReview() == null) {
            stmt.bindNull(22);
          } else {
            stmt.bindDouble(22, _tmpFilm.getRatingGoodReview());
          }
          if (_tmpFilm.getYear() == null) {
            stmt.bindNull(23);
          } else {
            stmt.bindLong(23, _tmpFilm.getYear());
          }
          if (_tmpFilm.getTotalSeasons() == null) {
            stmt.bindNull(24);
          } else {
            stmt.bindLong(24, _tmpFilm.getTotalSeasons());
          }
          final String _tmp_6 = __converterForFilmDB.seasonToJSON(_tmpFilm.getListSeasons());
          if (_tmp_6 == null) {
            stmt.bindNull(25);
          } else {
            stmt.bindString(25, _tmp_6);
          }
          if (_tmpFilm.getDescription() == null) {
            stmt.bindNull(26);
          } else {
            stmt.bindString(26, _tmpFilm.getDescription());
          }
          if (_tmpFilm.getShortDescription() == null) {
            stmt.bindNull(27);
          } else {
            stmt.bindString(27, _tmpFilm.getShortDescription());
          }
        } else {
          stmt.bindNull(3);
          stmt.bindNull(4);
          stmt.bindNull(5);
          stmt.bindNull(6);
          stmt.bindNull(7);
          stmt.bindNull(8);
          stmt.bindNull(9);
          stmt.bindNull(10);
          stmt.bindNull(11);
          stmt.bindNull(12);
          stmt.bindNull(13);
          stmt.bindNull(14);
          stmt.bindNull(15);
          stmt.bindNull(16);
          stmt.bindNull(17);
          stmt.bindNull(18);
          stmt.bindNull(19);
          stmt.bindNull(20);
          stmt.bindNull(21);
          stmt.bindNull(22);
          stmt.bindNull(23);
          stmt.bindNull(24);
          stmt.bindNull(25);
          stmt.bindNull(26);
          stmt.bindNull(27);
        }
        stmt.bindLong(28, value.getIdFilm());
      }
    };
    this.__updateAdapterOfCrossFC = new EntityDeletionOrUpdateAdapter<CrossFC>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `crossFC` SET `id` = ?,`film_id` = ?,`collection_id` = ?,`value` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, CrossFC value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getFilm_id());
        stmt.bindLong(3, value.getCollection_id());
        final int _tmp = value.getValue() ? 1 : 0;
        stmt.bindLong(4, _tmp);
        stmt.bindLong(5, value.getId());
      }
    };
    this.__preparedStmtOfNukeTable = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM films";
        return _query;
      }
    };
    this.__preparedStmtOfSetAllViewed = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE films SET viewed = ? ";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByIdFilmDB = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM films WHERE idFilm = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByIdCollection = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM collections WHERE idCollection = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteByIdCrossFC = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM crossFC WHERE id = ?";
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
  public void insert(final CollectionDB... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCollectionDB.insert(data);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insert(final CrossFC... data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfCrossFC.insert(data);
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
  public void update(final CrossFC data) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfCrossFC.handle(data);
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
  public void setAllViewed(final boolean value) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSetAllViewed.acquire();
    int _argIndex = 1;
    final int _tmp = value ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfSetAllViewed.release(_stmt);
    }
  }

  @Override
  public void deleteByIdFilmDB(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByIdFilmDB.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByIdFilmDB.release(_stmt);
    }
  }

  @Override
  public void deleteByIdCollection(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByIdCollection.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByIdCollection.release(_stmt);
    }
  }

  @Override
  public void deleteByIdCrossFC(final int id) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByIdCrossFC.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, id);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteByIdCrossFC.release(_stmt);
    }
  }

  @Override
  public FilmDB getAll() {
    final String _sql = "SELECT * FROM films ORDER BY idFilm DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdFilm = CursorUtil.getColumnIndexOrThrow(_cursor, "idFilm");
      final int _cursorIndexOfMsg = CursorUtil.getColumnIndexOrThrow(_cursor, "msg");
      final int _cursorIndexOfFilmId = CursorUtil.getColumnIndexOrThrow(_cursor, "filmId");
      final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdbId");
      final int _cursorIndexOfNameRu = CursorUtil.getColumnIndexOrThrow(_cursor, "nameRu");
      final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfPosterUrlPreview = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrlPreview");
      final int _cursorIndexOfCountries = CursorUtil.getColumnIndexOrThrow(_cursor, "countries");
      final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfViewed = CursorUtil.getColumnIndexOrThrow(_cursor, "viewed");
      final int _cursorIndexOfBookmark = CursorUtil.getColumnIndexOrThrow(_cursor, "bookmark");
      final int _cursorIndexOfProfessionKey = CursorUtil.getColumnIndexOrThrow(_cursor, "professionKey");
      final int _cursorIndexOfStartYear = CursorUtil.getColumnIndexOrThrow(_cursor, "startYear");
      final int _cursorIndexOfImages = CursorUtil.getColumnIndexOrThrow(_cursor, "images");
      final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
      final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
      final int _cursorIndexOfNameOriginal = CursorUtil.getColumnIndexOrThrow(_cursor, "nameOriginal");
      final int _cursorIndexOfRatingImdb = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingImdb");
      final int _cursorIndexOfRatingAwait = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAwait");
      final int _cursorIndexOfRatingGoodReview = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingGoodReview");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfTotalSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSeasons");
      final int _cursorIndexOfListSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "listSeasons");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfShortDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "shortDescription");
      final FilmDB _result;
      if(_cursor.moveToFirst()) {
        final int _tmpIdFilm;
        _tmpIdFilm = _cursor.getInt(_cursorIndexOfIdFilm);
        final String _tmpMsg;
        if (_cursor.isNull(_cursorIndexOfMsg)) {
          _tmpMsg = null;
        } else {
          _tmpMsg = _cursor.getString(_cursorIndexOfMsg);
        }
        final Film _tmpFilm;
        if (! (_cursor.isNull(_cursorIndexOfFilmId) && _cursor.isNull(_cursorIndexOfImdbId) && _cursor.isNull(_cursorIndexOfNameRu) && _cursor.isNull(_cursorIndexOfNameEn) && _cursor.isNull(_cursorIndexOfRating) && _cursor.isNull(_cursorIndexOfPosterUrlPreview) && _cursor.isNull(_cursorIndexOfCountries) && _cursor.isNull(_cursorIndexOfGenres) && _cursor.isNull(_cursorIndexOfFavorite) && _cursor.isNull(_cursorIndexOfViewed) && _cursor.isNull(_cursorIndexOfBookmark) && _cursor.isNull(_cursorIndexOfProfessionKey) && _cursor.isNull(_cursorIndexOfStartYear) && _cursor.isNull(_cursorIndexOfImages) && _cursor.isNull(_cursorIndexOfPosterUrl) && _cursor.isNull(_cursorIndexOfLogoUrl) && _cursor.isNull(_cursorIndexOfNameOriginal) && _cursor.isNull(_cursorIndexOfRatingImdb) && _cursor.isNull(_cursorIndexOfRatingAwait) && _cursor.isNull(_cursorIndexOfRatingGoodReview) && _cursor.isNull(_cursorIndexOfYear) && _cursor.isNull(_cursorIndexOfTotalSeasons) && _cursor.isNull(_cursorIndexOfListSeasons) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfShortDescription))) {
          final Integer _tmpFilmId;
          if (_cursor.isNull(_cursorIndexOfFilmId)) {
            _tmpFilmId = null;
          } else {
            _tmpFilmId = _cursor.getInt(_cursorIndexOfFilmId);
          }
          final String _tmpImdbId;
          if (_cursor.isNull(_cursorIndexOfImdbId)) {
            _tmpImdbId = null;
          } else {
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
          }
          final String _tmpNameRu;
          if (_cursor.isNull(_cursorIndexOfNameRu)) {
            _tmpNameRu = null;
          } else {
            _tmpNameRu = _cursor.getString(_cursorIndexOfNameRu);
          }
          final String _tmpNameEn;
          if (_cursor.isNull(_cursorIndexOfNameEn)) {
            _tmpNameEn = null;
          } else {
            _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
          }
          final Double _tmpRating;
          if (_cursor.isNull(_cursorIndexOfRating)) {
            _tmpRating = null;
          } else {
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
          }
          final String _tmpPosterUrlPreview;
          if (_cursor.isNull(_cursorIndexOfPosterUrlPreview)) {
            _tmpPosterUrlPreview = null;
          } else {
            _tmpPosterUrlPreview = _cursor.getString(_cursorIndexOfPosterUrlPreview);
          }
          final List<Country> _tmpCountries;
          final String _tmp;
          if (_cursor.isNull(_cursorIndexOfCountries)) {
            _tmp = null;
          } else {
            _tmp = _cursor.getString(_cursorIndexOfCountries);
          }
          _tmpCountries = __converterForFilmDB.countryFromJSON(_tmp);
          final List<Genre> _tmpGenres;
          final String _tmp_1;
          if (_cursor.isNull(_cursorIndexOfGenres)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _cursor.getString(_cursorIndexOfGenres);
          }
          _tmpGenres = __converterForFilmDB.genreFromJSON(_tmp_1);
          final boolean _tmpFavorite;
          final int _tmp_2;
          _tmp_2 = _cursor.getInt(_cursorIndexOfFavorite);
          _tmpFavorite = _tmp_2 != 0;
          final boolean _tmpViewed;
          final int _tmp_3;
          _tmp_3 = _cursor.getInt(_cursorIndexOfViewed);
          _tmpViewed = _tmp_3 != 0;
          final boolean _tmpBookmark;
          final int _tmp_4;
          _tmp_4 = _cursor.getInt(_cursorIndexOfBookmark);
          _tmpBookmark = _tmp_4 != 0;
          final String _tmpProfessionKey;
          if (_cursor.isNull(_cursorIndexOfProfessionKey)) {
            _tmpProfessionKey = null;
          } else {
            _tmpProfessionKey = _cursor.getString(_cursorIndexOfProfessionKey);
          }
          final Integer _tmpStartYear;
          if (_cursor.isNull(_cursorIndexOfStartYear)) {
            _tmpStartYear = null;
          } else {
            _tmpStartYear = _cursor.getInt(_cursorIndexOfStartYear);
          }
          final List<ImageFilm> _tmpImages;
          final String _tmp_5;
          if (_cursor.isNull(_cursorIndexOfImages)) {
            _tmp_5 = null;
          } else {
            _tmp_5 = _cursor.getString(_cursorIndexOfImages);
          }
          _tmpImages = __converterForFilmDB.imageFromJSON(_tmp_5);
          final String _tmpPosterUrl;
          if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
            _tmpPosterUrl = null;
          } else {
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
          }
          final String _tmpLogoUrl;
          if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
            _tmpLogoUrl = null;
          } else {
            _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
          }
          final String _tmpNameOriginal;
          if (_cursor.isNull(_cursorIndexOfNameOriginal)) {
            _tmpNameOriginal = null;
          } else {
            _tmpNameOriginal = _cursor.getString(_cursorIndexOfNameOriginal);
          }
          final Double _tmpRatingImdb;
          if (_cursor.isNull(_cursorIndexOfRatingImdb)) {
            _tmpRatingImdb = null;
          } else {
            _tmpRatingImdb = _cursor.getDouble(_cursorIndexOfRatingImdb);
          }
          final Double _tmpRatingAwait;
          if (_cursor.isNull(_cursorIndexOfRatingAwait)) {
            _tmpRatingAwait = null;
          } else {
            _tmpRatingAwait = _cursor.getDouble(_cursorIndexOfRatingAwait);
          }
          final Double _tmpRatingGoodReview;
          if (_cursor.isNull(_cursorIndexOfRatingGoodReview)) {
            _tmpRatingGoodReview = null;
          } else {
            _tmpRatingGoodReview = _cursor.getDouble(_cursorIndexOfRatingGoodReview);
          }
          final Integer _tmpYear;
          if (_cursor.isNull(_cursorIndexOfYear)) {
            _tmpYear = null;
          } else {
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
          }
          final Integer _tmpTotalSeasons;
          if (_cursor.isNull(_cursorIndexOfTotalSeasons)) {
            _tmpTotalSeasons = null;
          } else {
            _tmpTotalSeasons = _cursor.getInt(_cursorIndexOfTotalSeasons);
          }
          final List<SeasonDTO> _tmpListSeasons;
          final String _tmp_6;
          if (_cursor.isNull(_cursorIndexOfListSeasons)) {
            _tmp_6 = null;
          } else {
            _tmp_6 = _cursor.getString(_cursorIndexOfListSeasons);
          }
          _tmpListSeasons = __converterForFilmDB.seasonFromJSON(_tmp_6);
          final String _tmpDescription;
          if (_cursor.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
          }
          final String _tmpShortDescription;
          if (_cursor.isNull(_cursorIndexOfShortDescription)) {
            _tmpShortDescription = null;
          } else {
            _tmpShortDescription = _cursor.getString(_cursorIndexOfShortDescription);
          }
          _tmpFilm = new Film(_tmpFilmId,_tmpImdbId,_tmpNameRu,_tmpNameEn,_tmpRating,_tmpPosterUrlPreview,_tmpCountries,_tmpGenres,_tmpFavorite,_tmpViewed,_tmpBookmark,_tmpProfessionKey,_tmpStartYear,_tmpImages,_tmpPosterUrl,_tmpLogoUrl,_tmpNameOriginal,_tmpRatingImdb,_tmpRatingAwait,_tmpRatingGoodReview,_tmpYear,_tmpTotalSeasons,_tmpListSeasons,_tmpDescription,_tmpShortDescription);
        }  else  {
          _tmpFilm = null;
        }
        _result = new FilmDB(_tmpIdFilm,_tmpMsg,_tmpFilm);
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
    final String _sql = "SELECT * FROM films WHERE idFilm = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdFilm = CursorUtil.getColumnIndexOrThrow(_cursor, "idFilm");
      final int _cursorIndexOfMsg = CursorUtil.getColumnIndexOrThrow(_cursor, "msg");
      final int _cursorIndexOfFilmId = CursorUtil.getColumnIndexOrThrow(_cursor, "filmId");
      final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdbId");
      final int _cursorIndexOfNameRu = CursorUtil.getColumnIndexOrThrow(_cursor, "nameRu");
      final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfPosterUrlPreview = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrlPreview");
      final int _cursorIndexOfCountries = CursorUtil.getColumnIndexOrThrow(_cursor, "countries");
      final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfViewed = CursorUtil.getColumnIndexOrThrow(_cursor, "viewed");
      final int _cursorIndexOfBookmark = CursorUtil.getColumnIndexOrThrow(_cursor, "bookmark");
      final int _cursorIndexOfProfessionKey = CursorUtil.getColumnIndexOrThrow(_cursor, "professionKey");
      final int _cursorIndexOfStartYear = CursorUtil.getColumnIndexOrThrow(_cursor, "startYear");
      final int _cursorIndexOfImages = CursorUtil.getColumnIndexOrThrow(_cursor, "images");
      final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
      final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
      final int _cursorIndexOfNameOriginal = CursorUtil.getColumnIndexOrThrow(_cursor, "nameOriginal");
      final int _cursorIndexOfRatingImdb = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingImdb");
      final int _cursorIndexOfRatingAwait = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAwait");
      final int _cursorIndexOfRatingGoodReview = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingGoodReview");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfTotalSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSeasons");
      final int _cursorIndexOfListSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "listSeasons");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfShortDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "shortDescription");
      final FilmDB _result;
      if(_cursor.moveToFirst()) {
        final int _tmpIdFilm;
        _tmpIdFilm = _cursor.getInt(_cursorIndexOfIdFilm);
        final String _tmpMsg;
        if (_cursor.isNull(_cursorIndexOfMsg)) {
          _tmpMsg = null;
        } else {
          _tmpMsg = _cursor.getString(_cursorIndexOfMsg);
        }
        final Film _tmpFilm;
        if (! (_cursor.isNull(_cursorIndexOfFilmId) && _cursor.isNull(_cursorIndexOfImdbId) && _cursor.isNull(_cursorIndexOfNameRu) && _cursor.isNull(_cursorIndexOfNameEn) && _cursor.isNull(_cursorIndexOfRating) && _cursor.isNull(_cursorIndexOfPosterUrlPreview) && _cursor.isNull(_cursorIndexOfCountries) && _cursor.isNull(_cursorIndexOfGenres) && _cursor.isNull(_cursorIndexOfFavorite) && _cursor.isNull(_cursorIndexOfViewed) && _cursor.isNull(_cursorIndexOfBookmark) && _cursor.isNull(_cursorIndexOfProfessionKey) && _cursor.isNull(_cursorIndexOfStartYear) && _cursor.isNull(_cursorIndexOfImages) && _cursor.isNull(_cursorIndexOfPosterUrl) && _cursor.isNull(_cursorIndexOfLogoUrl) && _cursor.isNull(_cursorIndexOfNameOriginal) && _cursor.isNull(_cursorIndexOfRatingImdb) && _cursor.isNull(_cursorIndexOfRatingAwait) && _cursor.isNull(_cursorIndexOfRatingGoodReview) && _cursor.isNull(_cursorIndexOfYear) && _cursor.isNull(_cursorIndexOfTotalSeasons) && _cursor.isNull(_cursorIndexOfListSeasons) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfShortDescription))) {
          final Integer _tmpFilmId;
          if (_cursor.isNull(_cursorIndexOfFilmId)) {
            _tmpFilmId = null;
          } else {
            _tmpFilmId = _cursor.getInt(_cursorIndexOfFilmId);
          }
          final String _tmpImdbId;
          if (_cursor.isNull(_cursorIndexOfImdbId)) {
            _tmpImdbId = null;
          } else {
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
          }
          final String _tmpNameRu;
          if (_cursor.isNull(_cursorIndexOfNameRu)) {
            _tmpNameRu = null;
          } else {
            _tmpNameRu = _cursor.getString(_cursorIndexOfNameRu);
          }
          final String _tmpNameEn;
          if (_cursor.isNull(_cursorIndexOfNameEn)) {
            _tmpNameEn = null;
          } else {
            _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
          }
          final Double _tmpRating;
          if (_cursor.isNull(_cursorIndexOfRating)) {
            _tmpRating = null;
          } else {
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
          }
          final String _tmpPosterUrlPreview;
          if (_cursor.isNull(_cursorIndexOfPosterUrlPreview)) {
            _tmpPosterUrlPreview = null;
          } else {
            _tmpPosterUrlPreview = _cursor.getString(_cursorIndexOfPosterUrlPreview);
          }
          final List<Country> _tmpCountries;
          final String _tmp;
          if (_cursor.isNull(_cursorIndexOfCountries)) {
            _tmp = null;
          } else {
            _tmp = _cursor.getString(_cursorIndexOfCountries);
          }
          _tmpCountries = __converterForFilmDB.countryFromJSON(_tmp);
          final List<Genre> _tmpGenres;
          final String _tmp_1;
          if (_cursor.isNull(_cursorIndexOfGenres)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _cursor.getString(_cursorIndexOfGenres);
          }
          _tmpGenres = __converterForFilmDB.genreFromJSON(_tmp_1);
          final boolean _tmpFavorite;
          final int _tmp_2;
          _tmp_2 = _cursor.getInt(_cursorIndexOfFavorite);
          _tmpFavorite = _tmp_2 != 0;
          final boolean _tmpViewed;
          final int _tmp_3;
          _tmp_3 = _cursor.getInt(_cursorIndexOfViewed);
          _tmpViewed = _tmp_3 != 0;
          final boolean _tmpBookmark;
          final int _tmp_4;
          _tmp_4 = _cursor.getInt(_cursorIndexOfBookmark);
          _tmpBookmark = _tmp_4 != 0;
          final String _tmpProfessionKey;
          if (_cursor.isNull(_cursorIndexOfProfessionKey)) {
            _tmpProfessionKey = null;
          } else {
            _tmpProfessionKey = _cursor.getString(_cursorIndexOfProfessionKey);
          }
          final Integer _tmpStartYear;
          if (_cursor.isNull(_cursorIndexOfStartYear)) {
            _tmpStartYear = null;
          } else {
            _tmpStartYear = _cursor.getInt(_cursorIndexOfStartYear);
          }
          final List<ImageFilm> _tmpImages;
          final String _tmp_5;
          if (_cursor.isNull(_cursorIndexOfImages)) {
            _tmp_5 = null;
          } else {
            _tmp_5 = _cursor.getString(_cursorIndexOfImages);
          }
          _tmpImages = __converterForFilmDB.imageFromJSON(_tmp_5);
          final String _tmpPosterUrl;
          if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
            _tmpPosterUrl = null;
          } else {
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
          }
          final String _tmpLogoUrl;
          if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
            _tmpLogoUrl = null;
          } else {
            _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
          }
          final String _tmpNameOriginal;
          if (_cursor.isNull(_cursorIndexOfNameOriginal)) {
            _tmpNameOriginal = null;
          } else {
            _tmpNameOriginal = _cursor.getString(_cursorIndexOfNameOriginal);
          }
          final Double _tmpRatingImdb;
          if (_cursor.isNull(_cursorIndexOfRatingImdb)) {
            _tmpRatingImdb = null;
          } else {
            _tmpRatingImdb = _cursor.getDouble(_cursorIndexOfRatingImdb);
          }
          final Double _tmpRatingAwait;
          if (_cursor.isNull(_cursorIndexOfRatingAwait)) {
            _tmpRatingAwait = null;
          } else {
            _tmpRatingAwait = _cursor.getDouble(_cursorIndexOfRatingAwait);
          }
          final Double _tmpRatingGoodReview;
          if (_cursor.isNull(_cursorIndexOfRatingGoodReview)) {
            _tmpRatingGoodReview = null;
          } else {
            _tmpRatingGoodReview = _cursor.getDouble(_cursorIndexOfRatingGoodReview);
          }
          final Integer _tmpYear;
          if (_cursor.isNull(_cursorIndexOfYear)) {
            _tmpYear = null;
          } else {
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
          }
          final Integer _tmpTotalSeasons;
          if (_cursor.isNull(_cursorIndexOfTotalSeasons)) {
            _tmpTotalSeasons = null;
          } else {
            _tmpTotalSeasons = _cursor.getInt(_cursorIndexOfTotalSeasons);
          }
          final List<SeasonDTO> _tmpListSeasons;
          final String _tmp_6;
          if (_cursor.isNull(_cursorIndexOfListSeasons)) {
            _tmp_6 = null;
          } else {
            _tmp_6 = _cursor.getString(_cursorIndexOfListSeasons);
          }
          _tmpListSeasons = __converterForFilmDB.seasonFromJSON(_tmp_6);
          final String _tmpDescription;
          if (_cursor.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
          }
          final String _tmpShortDescription;
          if (_cursor.isNull(_cursorIndexOfShortDescription)) {
            _tmpShortDescription = null;
          } else {
            _tmpShortDescription = _cursor.getString(_cursorIndexOfShortDescription);
          }
          _tmpFilm = new Film(_tmpFilmId,_tmpImdbId,_tmpNameRu,_tmpNameEn,_tmpRating,_tmpPosterUrlPreview,_tmpCountries,_tmpGenres,_tmpFavorite,_tmpViewed,_tmpBookmark,_tmpProfessionKey,_tmpStartYear,_tmpImages,_tmpPosterUrl,_tmpLogoUrl,_tmpNameOriginal,_tmpRatingImdb,_tmpRatingAwait,_tmpRatingGoodReview,_tmpYear,_tmpTotalSeasons,_tmpListSeasons,_tmpDescription,_tmpShortDescription);
        }  else  {
          _tmpFilm = null;
        }
        _result = new FilmDB(_tmpIdFilm,_tmpMsg,_tmpFilm);
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
  public List<FilmDB> getFilms() {
    final String _sql = "SELECT * FROM films";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdFilm = CursorUtil.getColumnIndexOrThrow(_cursor, "idFilm");
      final int _cursorIndexOfMsg = CursorUtil.getColumnIndexOrThrow(_cursor, "msg");
      final int _cursorIndexOfFilmId = CursorUtil.getColumnIndexOrThrow(_cursor, "filmId");
      final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdbId");
      final int _cursorIndexOfNameRu = CursorUtil.getColumnIndexOrThrow(_cursor, "nameRu");
      final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfPosterUrlPreview = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrlPreview");
      final int _cursorIndexOfCountries = CursorUtil.getColumnIndexOrThrow(_cursor, "countries");
      final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfViewed = CursorUtil.getColumnIndexOrThrow(_cursor, "viewed");
      final int _cursorIndexOfBookmark = CursorUtil.getColumnIndexOrThrow(_cursor, "bookmark");
      final int _cursorIndexOfProfessionKey = CursorUtil.getColumnIndexOrThrow(_cursor, "professionKey");
      final int _cursorIndexOfStartYear = CursorUtil.getColumnIndexOrThrow(_cursor, "startYear");
      final int _cursorIndexOfImages = CursorUtil.getColumnIndexOrThrow(_cursor, "images");
      final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
      final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
      final int _cursorIndexOfNameOriginal = CursorUtil.getColumnIndexOrThrow(_cursor, "nameOriginal");
      final int _cursorIndexOfRatingImdb = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingImdb");
      final int _cursorIndexOfRatingAwait = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAwait");
      final int _cursorIndexOfRatingGoodReview = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingGoodReview");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfTotalSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSeasons");
      final int _cursorIndexOfListSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "listSeasons");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfShortDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "shortDescription");
      final List<FilmDB> _result = new ArrayList<FilmDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final FilmDB _item;
        final int _tmpIdFilm;
        _tmpIdFilm = _cursor.getInt(_cursorIndexOfIdFilm);
        final String _tmpMsg;
        if (_cursor.isNull(_cursorIndexOfMsg)) {
          _tmpMsg = null;
        } else {
          _tmpMsg = _cursor.getString(_cursorIndexOfMsg);
        }
        final Film _tmpFilm;
        if (! (_cursor.isNull(_cursorIndexOfFilmId) && _cursor.isNull(_cursorIndexOfImdbId) && _cursor.isNull(_cursorIndexOfNameRu) && _cursor.isNull(_cursorIndexOfNameEn) && _cursor.isNull(_cursorIndexOfRating) && _cursor.isNull(_cursorIndexOfPosterUrlPreview) && _cursor.isNull(_cursorIndexOfCountries) && _cursor.isNull(_cursorIndexOfGenres) && _cursor.isNull(_cursorIndexOfFavorite) && _cursor.isNull(_cursorIndexOfViewed) && _cursor.isNull(_cursorIndexOfBookmark) && _cursor.isNull(_cursorIndexOfProfessionKey) && _cursor.isNull(_cursorIndexOfStartYear) && _cursor.isNull(_cursorIndexOfImages) && _cursor.isNull(_cursorIndexOfPosterUrl) && _cursor.isNull(_cursorIndexOfLogoUrl) && _cursor.isNull(_cursorIndexOfNameOriginal) && _cursor.isNull(_cursorIndexOfRatingImdb) && _cursor.isNull(_cursorIndexOfRatingAwait) && _cursor.isNull(_cursorIndexOfRatingGoodReview) && _cursor.isNull(_cursorIndexOfYear) && _cursor.isNull(_cursorIndexOfTotalSeasons) && _cursor.isNull(_cursorIndexOfListSeasons) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfShortDescription))) {
          final Integer _tmpFilmId;
          if (_cursor.isNull(_cursorIndexOfFilmId)) {
            _tmpFilmId = null;
          } else {
            _tmpFilmId = _cursor.getInt(_cursorIndexOfFilmId);
          }
          final String _tmpImdbId;
          if (_cursor.isNull(_cursorIndexOfImdbId)) {
            _tmpImdbId = null;
          } else {
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
          }
          final String _tmpNameRu;
          if (_cursor.isNull(_cursorIndexOfNameRu)) {
            _tmpNameRu = null;
          } else {
            _tmpNameRu = _cursor.getString(_cursorIndexOfNameRu);
          }
          final String _tmpNameEn;
          if (_cursor.isNull(_cursorIndexOfNameEn)) {
            _tmpNameEn = null;
          } else {
            _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
          }
          final Double _tmpRating;
          if (_cursor.isNull(_cursorIndexOfRating)) {
            _tmpRating = null;
          } else {
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
          }
          final String _tmpPosterUrlPreview;
          if (_cursor.isNull(_cursorIndexOfPosterUrlPreview)) {
            _tmpPosterUrlPreview = null;
          } else {
            _tmpPosterUrlPreview = _cursor.getString(_cursorIndexOfPosterUrlPreview);
          }
          final List<Country> _tmpCountries;
          final String _tmp;
          if (_cursor.isNull(_cursorIndexOfCountries)) {
            _tmp = null;
          } else {
            _tmp = _cursor.getString(_cursorIndexOfCountries);
          }
          _tmpCountries = __converterForFilmDB.countryFromJSON(_tmp);
          final List<Genre> _tmpGenres;
          final String _tmp_1;
          if (_cursor.isNull(_cursorIndexOfGenres)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _cursor.getString(_cursorIndexOfGenres);
          }
          _tmpGenres = __converterForFilmDB.genreFromJSON(_tmp_1);
          final boolean _tmpFavorite;
          final int _tmp_2;
          _tmp_2 = _cursor.getInt(_cursorIndexOfFavorite);
          _tmpFavorite = _tmp_2 != 0;
          final boolean _tmpViewed;
          final int _tmp_3;
          _tmp_3 = _cursor.getInt(_cursorIndexOfViewed);
          _tmpViewed = _tmp_3 != 0;
          final boolean _tmpBookmark;
          final int _tmp_4;
          _tmp_4 = _cursor.getInt(_cursorIndexOfBookmark);
          _tmpBookmark = _tmp_4 != 0;
          final String _tmpProfessionKey;
          if (_cursor.isNull(_cursorIndexOfProfessionKey)) {
            _tmpProfessionKey = null;
          } else {
            _tmpProfessionKey = _cursor.getString(_cursorIndexOfProfessionKey);
          }
          final Integer _tmpStartYear;
          if (_cursor.isNull(_cursorIndexOfStartYear)) {
            _tmpStartYear = null;
          } else {
            _tmpStartYear = _cursor.getInt(_cursorIndexOfStartYear);
          }
          final List<ImageFilm> _tmpImages;
          final String _tmp_5;
          if (_cursor.isNull(_cursorIndexOfImages)) {
            _tmp_5 = null;
          } else {
            _tmp_5 = _cursor.getString(_cursorIndexOfImages);
          }
          _tmpImages = __converterForFilmDB.imageFromJSON(_tmp_5);
          final String _tmpPosterUrl;
          if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
            _tmpPosterUrl = null;
          } else {
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
          }
          final String _tmpLogoUrl;
          if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
            _tmpLogoUrl = null;
          } else {
            _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
          }
          final String _tmpNameOriginal;
          if (_cursor.isNull(_cursorIndexOfNameOriginal)) {
            _tmpNameOriginal = null;
          } else {
            _tmpNameOriginal = _cursor.getString(_cursorIndexOfNameOriginal);
          }
          final Double _tmpRatingImdb;
          if (_cursor.isNull(_cursorIndexOfRatingImdb)) {
            _tmpRatingImdb = null;
          } else {
            _tmpRatingImdb = _cursor.getDouble(_cursorIndexOfRatingImdb);
          }
          final Double _tmpRatingAwait;
          if (_cursor.isNull(_cursorIndexOfRatingAwait)) {
            _tmpRatingAwait = null;
          } else {
            _tmpRatingAwait = _cursor.getDouble(_cursorIndexOfRatingAwait);
          }
          final Double _tmpRatingGoodReview;
          if (_cursor.isNull(_cursorIndexOfRatingGoodReview)) {
            _tmpRatingGoodReview = null;
          } else {
            _tmpRatingGoodReview = _cursor.getDouble(_cursorIndexOfRatingGoodReview);
          }
          final Integer _tmpYear;
          if (_cursor.isNull(_cursorIndexOfYear)) {
            _tmpYear = null;
          } else {
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
          }
          final Integer _tmpTotalSeasons;
          if (_cursor.isNull(_cursorIndexOfTotalSeasons)) {
            _tmpTotalSeasons = null;
          } else {
            _tmpTotalSeasons = _cursor.getInt(_cursorIndexOfTotalSeasons);
          }
          final List<SeasonDTO> _tmpListSeasons;
          final String _tmp_6;
          if (_cursor.isNull(_cursorIndexOfListSeasons)) {
            _tmp_6 = null;
          } else {
            _tmp_6 = _cursor.getString(_cursorIndexOfListSeasons);
          }
          _tmpListSeasons = __converterForFilmDB.seasonFromJSON(_tmp_6);
          final String _tmpDescription;
          if (_cursor.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
          }
          final String _tmpShortDescription;
          if (_cursor.isNull(_cursorIndexOfShortDescription)) {
            _tmpShortDescription = null;
          } else {
            _tmpShortDescription = _cursor.getString(_cursorIndexOfShortDescription);
          }
          _tmpFilm = new Film(_tmpFilmId,_tmpImdbId,_tmpNameRu,_tmpNameEn,_tmpRating,_tmpPosterUrlPreview,_tmpCountries,_tmpGenres,_tmpFavorite,_tmpViewed,_tmpBookmark,_tmpProfessionKey,_tmpStartYear,_tmpImages,_tmpPosterUrl,_tmpLogoUrl,_tmpNameOriginal,_tmpRatingImdb,_tmpRatingAwait,_tmpRatingGoodReview,_tmpYear,_tmpTotalSeasons,_tmpListSeasons,_tmpDescription,_tmpShortDescription);
        }  else  {
          _tmpFilm = null;
        }
        _item = new FilmDB(_tmpIdFilm,_tmpMsg,_tmpFilm);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean getViewed(final int id) {
    final String _sql = "SELECT viewed FROM films WHERE idFilm = ?";
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
  public List<Integer> getViewedFilms(final boolean viewed) {
    final String _sql = "SELECT filmId FROM films WHERE viewed = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final int _tmp = viewed ? 1 : 0;
    _statement.bindLong(_argIndex, _tmp);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<Integer> _result = new ArrayList<Integer>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Integer _item;
        if (_cursor.isNull(0)) {
          _item = null;
        } else {
          _item = _cursor.getInt(0);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Flow<Boolean> setViewedFlow(final int id) {
    final String _sql = "SELECT viewed FROM films WHERE idFilm = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"films"}, new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Boolean _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp == null ? null : _tmp != 0;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public List<FilmDB> getFilmInList(final List<Integer> listId) {
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT * FROM films WHERE idFilm = ");
    final int _inputSize = listId.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (Integer _item : listId) {
      if (_item == null) {
        _statement.bindNull(_argIndex);
      } else {
        _statement.bindLong(_argIndex, _item);
      }
      _argIndex ++;
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdFilm = CursorUtil.getColumnIndexOrThrow(_cursor, "idFilm");
      final int _cursorIndexOfMsg = CursorUtil.getColumnIndexOrThrow(_cursor, "msg");
      final int _cursorIndexOfFilmId = CursorUtil.getColumnIndexOrThrow(_cursor, "filmId");
      final int _cursorIndexOfImdbId = CursorUtil.getColumnIndexOrThrow(_cursor, "imdbId");
      final int _cursorIndexOfNameRu = CursorUtil.getColumnIndexOrThrow(_cursor, "nameRu");
      final int _cursorIndexOfNameEn = CursorUtil.getColumnIndexOrThrow(_cursor, "nameEn");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfPosterUrlPreview = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrlPreview");
      final int _cursorIndexOfCountries = CursorUtil.getColumnIndexOrThrow(_cursor, "countries");
      final int _cursorIndexOfGenres = CursorUtil.getColumnIndexOrThrow(_cursor, "genres");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfViewed = CursorUtil.getColumnIndexOrThrow(_cursor, "viewed");
      final int _cursorIndexOfBookmark = CursorUtil.getColumnIndexOrThrow(_cursor, "bookmark");
      final int _cursorIndexOfProfessionKey = CursorUtil.getColumnIndexOrThrow(_cursor, "professionKey");
      final int _cursorIndexOfStartYear = CursorUtil.getColumnIndexOrThrow(_cursor, "startYear");
      final int _cursorIndexOfImages = CursorUtil.getColumnIndexOrThrow(_cursor, "images");
      final int _cursorIndexOfPosterUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "posterUrl");
      final int _cursorIndexOfLogoUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "logoUrl");
      final int _cursorIndexOfNameOriginal = CursorUtil.getColumnIndexOrThrow(_cursor, "nameOriginal");
      final int _cursorIndexOfRatingImdb = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingImdb");
      final int _cursorIndexOfRatingAwait = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingAwait");
      final int _cursorIndexOfRatingGoodReview = CursorUtil.getColumnIndexOrThrow(_cursor, "ratingGoodReview");
      final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
      final int _cursorIndexOfTotalSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "totalSeasons");
      final int _cursorIndexOfListSeasons = CursorUtil.getColumnIndexOrThrow(_cursor, "listSeasons");
      final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
      final int _cursorIndexOfShortDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "shortDescription");
      final List<FilmDB> _result = new ArrayList<FilmDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final FilmDB _item_1;
        final int _tmpIdFilm;
        _tmpIdFilm = _cursor.getInt(_cursorIndexOfIdFilm);
        final String _tmpMsg;
        if (_cursor.isNull(_cursorIndexOfMsg)) {
          _tmpMsg = null;
        } else {
          _tmpMsg = _cursor.getString(_cursorIndexOfMsg);
        }
        final Film _tmpFilm;
        if (! (_cursor.isNull(_cursorIndexOfFilmId) && _cursor.isNull(_cursorIndexOfImdbId) && _cursor.isNull(_cursorIndexOfNameRu) && _cursor.isNull(_cursorIndexOfNameEn) && _cursor.isNull(_cursorIndexOfRating) && _cursor.isNull(_cursorIndexOfPosterUrlPreview) && _cursor.isNull(_cursorIndexOfCountries) && _cursor.isNull(_cursorIndexOfGenres) && _cursor.isNull(_cursorIndexOfFavorite) && _cursor.isNull(_cursorIndexOfViewed) && _cursor.isNull(_cursorIndexOfBookmark) && _cursor.isNull(_cursorIndexOfProfessionKey) && _cursor.isNull(_cursorIndexOfStartYear) && _cursor.isNull(_cursorIndexOfImages) && _cursor.isNull(_cursorIndexOfPosterUrl) && _cursor.isNull(_cursorIndexOfLogoUrl) && _cursor.isNull(_cursorIndexOfNameOriginal) && _cursor.isNull(_cursorIndexOfRatingImdb) && _cursor.isNull(_cursorIndexOfRatingAwait) && _cursor.isNull(_cursorIndexOfRatingGoodReview) && _cursor.isNull(_cursorIndexOfYear) && _cursor.isNull(_cursorIndexOfTotalSeasons) && _cursor.isNull(_cursorIndexOfListSeasons) && _cursor.isNull(_cursorIndexOfDescription) && _cursor.isNull(_cursorIndexOfShortDescription))) {
          final Integer _tmpFilmId;
          if (_cursor.isNull(_cursorIndexOfFilmId)) {
            _tmpFilmId = null;
          } else {
            _tmpFilmId = _cursor.getInt(_cursorIndexOfFilmId);
          }
          final String _tmpImdbId;
          if (_cursor.isNull(_cursorIndexOfImdbId)) {
            _tmpImdbId = null;
          } else {
            _tmpImdbId = _cursor.getString(_cursorIndexOfImdbId);
          }
          final String _tmpNameRu;
          if (_cursor.isNull(_cursorIndexOfNameRu)) {
            _tmpNameRu = null;
          } else {
            _tmpNameRu = _cursor.getString(_cursorIndexOfNameRu);
          }
          final String _tmpNameEn;
          if (_cursor.isNull(_cursorIndexOfNameEn)) {
            _tmpNameEn = null;
          } else {
            _tmpNameEn = _cursor.getString(_cursorIndexOfNameEn);
          }
          final Double _tmpRating;
          if (_cursor.isNull(_cursorIndexOfRating)) {
            _tmpRating = null;
          } else {
            _tmpRating = _cursor.getDouble(_cursorIndexOfRating);
          }
          final String _tmpPosterUrlPreview;
          if (_cursor.isNull(_cursorIndexOfPosterUrlPreview)) {
            _tmpPosterUrlPreview = null;
          } else {
            _tmpPosterUrlPreview = _cursor.getString(_cursorIndexOfPosterUrlPreview);
          }
          final List<Country> _tmpCountries;
          final String _tmp;
          if (_cursor.isNull(_cursorIndexOfCountries)) {
            _tmp = null;
          } else {
            _tmp = _cursor.getString(_cursorIndexOfCountries);
          }
          _tmpCountries = __converterForFilmDB.countryFromJSON(_tmp);
          final List<Genre> _tmpGenres;
          final String _tmp_1;
          if (_cursor.isNull(_cursorIndexOfGenres)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = _cursor.getString(_cursorIndexOfGenres);
          }
          _tmpGenres = __converterForFilmDB.genreFromJSON(_tmp_1);
          final boolean _tmpFavorite;
          final int _tmp_2;
          _tmp_2 = _cursor.getInt(_cursorIndexOfFavorite);
          _tmpFavorite = _tmp_2 != 0;
          final boolean _tmpViewed;
          final int _tmp_3;
          _tmp_3 = _cursor.getInt(_cursorIndexOfViewed);
          _tmpViewed = _tmp_3 != 0;
          final boolean _tmpBookmark;
          final int _tmp_4;
          _tmp_4 = _cursor.getInt(_cursorIndexOfBookmark);
          _tmpBookmark = _tmp_4 != 0;
          final String _tmpProfessionKey;
          if (_cursor.isNull(_cursorIndexOfProfessionKey)) {
            _tmpProfessionKey = null;
          } else {
            _tmpProfessionKey = _cursor.getString(_cursorIndexOfProfessionKey);
          }
          final Integer _tmpStartYear;
          if (_cursor.isNull(_cursorIndexOfStartYear)) {
            _tmpStartYear = null;
          } else {
            _tmpStartYear = _cursor.getInt(_cursorIndexOfStartYear);
          }
          final List<ImageFilm> _tmpImages;
          final String _tmp_5;
          if (_cursor.isNull(_cursorIndexOfImages)) {
            _tmp_5 = null;
          } else {
            _tmp_5 = _cursor.getString(_cursorIndexOfImages);
          }
          _tmpImages = __converterForFilmDB.imageFromJSON(_tmp_5);
          final String _tmpPosterUrl;
          if (_cursor.isNull(_cursorIndexOfPosterUrl)) {
            _tmpPosterUrl = null;
          } else {
            _tmpPosterUrl = _cursor.getString(_cursorIndexOfPosterUrl);
          }
          final String _tmpLogoUrl;
          if (_cursor.isNull(_cursorIndexOfLogoUrl)) {
            _tmpLogoUrl = null;
          } else {
            _tmpLogoUrl = _cursor.getString(_cursorIndexOfLogoUrl);
          }
          final String _tmpNameOriginal;
          if (_cursor.isNull(_cursorIndexOfNameOriginal)) {
            _tmpNameOriginal = null;
          } else {
            _tmpNameOriginal = _cursor.getString(_cursorIndexOfNameOriginal);
          }
          final Double _tmpRatingImdb;
          if (_cursor.isNull(_cursorIndexOfRatingImdb)) {
            _tmpRatingImdb = null;
          } else {
            _tmpRatingImdb = _cursor.getDouble(_cursorIndexOfRatingImdb);
          }
          final Double _tmpRatingAwait;
          if (_cursor.isNull(_cursorIndexOfRatingAwait)) {
            _tmpRatingAwait = null;
          } else {
            _tmpRatingAwait = _cursor.getDouble(_cursorIndexOfRatingAwait);
          }
          final Double _tmpRatingGoodReview;
          if (_cursor.isNull(_cursorIndexOfRatingGoodReview)) {
            _tmpRatingGoodReview = null;
          } else {
            _tmpRatingGoodReview = _cursor.getDouble(_cursorIndexOfRatingGoodReview);
          }
          final Integer _tmpYear;
          if (_cursor.isNull(_cursorIndexOfYear)) {
            _tmpYear = null;
          } else {
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
          }
          final Integer _tmpTotalSeasons;
          if (_cursor.isNull(_cursorIndexOfTotalSeasons)) {
            _tmpTotalSeasons = null;
          } else {
            _tmpTotalSeasons = _cursor.getInt(_cursorIndexOfTotalSeasons);
          }
          final List<SeasonDTO> _tmpListSeasons;
          final String _tmp_6;
          if (_cursor.isNull(_cursorIndexOfListSeasons)) {
            _tmp_6 = null;
          } else {
            _tmp_6 = _cursor.getString(_cursorIndexOfListSeasons);
          }
          _tmpListSeasons = __converterForFilmDB.seasonFromJSON(_tmp_6);
          final String _tmpDescription;
          if (_cursor.isNull(_cursorIndexOfDescription)) {
            _tmpDescription = null;
          } else {
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
          }
          final String _tmpShortDescription;
          if (_cursor.isNull(_cursorIndexOfShortDescription)) {
            _tmpShortDescription = null;
          } else {
            _tmpShortDescription = _cursor.getString(_cursorIndexOfShortDescription);
          }
          _tmpFilm = new Film(_tmpFilmId,_tmpImdbId,_tmpNameRu,_tmpNameEn,_tmpRating,_tmpPosterUrlPreview,_tmpCountries,_tmpGenres,_tmpFavorite,_tmpViewed,_tmpBookmark,_tmpProfessionKey,_tmpStartYear,_tmpImages,_tmpPosterUrl,_tmpLogoUrl,_tmpNameOriginal,_tmpRatingImdb,_tmpRatingAwait,_tmpRatingGoodReview,_tmpYear,_tmpTotalSeasons,_tmpListSeasons,_tmpDescription,_tmpShortDescription);
        }  else  {
          _tmpFilm = null;
        }
        _item_1 = new FilmDB(_tmpIdFilm,_tmpMsg,_tmpFilm);
        _result.add(_item_1);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<CollectionDB> getCollection() {
    final String _sql = "SELECT * FROM collections ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdCollection = CursorUtil.getColumnIndexOrThrow(_cursor, "idCollection");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCount = CursorUtil.getColumnIndexOrThrow(_cursor, "count");
      final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
      final int _cursorIndexOfIncluded = CursorUtil.getColumnIndexOrThrow(_cursor, "included");
      final List<CollectionDB> _result = new ArrayList<CollectionDB>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CollectionDB _item;
        final int _tmpIdCollection;
        _tmpIdCollection = _cursor.getInt(_cursorIndexOfIdCollection);
        final Collection _tmpCollection;
        if (! (_cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfCount) && _cursor.isNull(_cursorIndexOfImage) && _cursor.isNull(_cursorIndexOfIncluded))) {
          final String _tmpName;
          if (_cursor.isNull(_cursorIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _cursor.getString(_cursorIndexOfName);
          }
          final int _tmpCount;
          _tmpCount = _cursor.getInt(_cursorIndexOfCount);
          final int _tmpImage;
          _tmpImage = _cursor.getInt(_cursorIndexOfImage);
          final boolean _tmpIncluded;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfIncluded);
          _tmpIncluded = _tmp != 0;
          _tmpCollection = new Collection(_tmpName,_tmpCount,_tmpImage,_tmpIncluded);
        }  else  {
          _tmpCollection = null;
        }
        _item = new CollectionDB(_tmpIdCollection,_tmpCollection);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public CollectionDB getCollectionRecord(final String collectionName) {
    final String _sql = "SELECT * FROM collections WHERE name = ? ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (collectionName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, collectionName);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfIdCollection = CursorUtil.getColumnIndexOrThrow(_cursor, "idCollection");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfCount = CursorUtil.getColumnIndexOrThrow(_cursor, "count");
      final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
      final int _cursorIndexOfIncluded = CursorUtil.getColumnIndexOrThrow(_cursor, "included");
      final CollectionDB _result;
      if(_cursor.moveToFirst()) {
        final int _tmpIdCollection;
        _tmpIdCollection = _cursor.getInt(_cursorIndexOfIdCollection);
        final Collection _tmpCollection;
        if (! (_cursor.isNull(_cursorIndexOfName) && _cursor.isNull(_cursorIndexOfCount) && _cursor.isNull(_cursorIndexOfImage) && _cursor.isNull(_cursorIndexOfIncluded))) {
          final String _tmpName;
          if (_cursor.isNull(_cursorIndexOfName)) {
            _tmpName = null;
          } else {
            _tmpName = _cursor.getString(_cursorIndexOfName);
          }
          final int _tmpCount;
          _tmpCount = _cursor.getInt(_cursorIndexOfCount);
          final int _tmpImage;
          _tmpImage = _cursor.getInt(_cursorIndexOfImage);
          final boolean _tmpIncluded;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfIncluded);
          _tmpIncluded = _tmp != 0;
          _tmpCollection = new Collection(_tmpName,_tmpCount,_tmpImage,_tmpIncluded);
        }  else  {
          _tmpCollection = null;
        }
        _result = new CollectionDB(_tmpIdCollection,_tmpCollection);
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
  public Flow<Boolean> setFavoriteFlow(final int id) {
    final String _sql = "SELECT value FROM crossFC WHERE collection_id = '1' AND film_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"crossFC"}, new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfValue = 0;
          final Boolean _result;
          if(_cursor.moveToFirst()) {
            final boolean _tmpValue;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfValue);
            _tmpValue = _tmp != 0;
            _result = new Boolean(_tmpValue);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public boolean getFavorite(final int id) {
    final String _sql = "SELECT value FROM crossFC WHERE collection_id = '1' AND film_id = ?";
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
  public Flow<Boolean> setBookmarkFlow(final int id) {
    final String _sql = "SELECT value FROM crossFC WHERE collection_id = '2' AND film_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"crossFC"}, new Callable<Boolean>() {
      @Override
      public Boolean call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfValue = 0;
          final Boolean _result;
          if(_cursor.moveToFirst()) {
            final boolean _tmpValue;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfValue);
            _tmpValue = _tmp != 0;
            _result = new Boolean(_tmpValue);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public boolean getBookmark(final int id) {
    final String _sql = "SELECT value FROM crossFC WHERE collection_id = '2' AND film_id = ?";
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
  public List<CrossFC> getCountFilmCollection(final int id) {
    final String _sql = "SELECT * FROM crossFC WHERE collection_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfFilmId = CursorUtil.getColumnIndexOrThrow(_cursor, "film_id");
      final int _cursorIndexOfCollectionId = CursorUtil.getColumnIndexOrThrow(_cursor, "collection_id");
      final int _cursorIndexOfValue = CursorUtil.getColumnIndexOrThrow(_cursor, "value");
      final List<CrossFC> _result = new ArrayList<CrossFC>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final CrossFC _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final int _tmpFilm_id;
        _tmpFilm_id = _cursor.getInt(_cursorIndexOfFilmId);
        final int _tmpCollection_id;
        _tmpCollection_id = _cursor.getInt(_cursorIndexOfCollectionId);
        final boolean _tmpValue;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfValue);
        _tmpValue = _tmp != 0;
        _item = new CrossFC(_tmpId,_tmpFilm_id,_tmpCollection_id,_tmpValue);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Integer getFilmInCollection(final int filmId, final int collectionId) {
    final String _sql = "SELECT id FROM crossFC WHERE collection_id = ? AND film_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, collectionId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, filmId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Integer _result;
      if(_cursor.moveToFirst()) {
        if (_cursor.isNull(0)) {
          _result = null;
        } else {
          _result = _cursor.getInt(0);
        }
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
  public boolean existFilmInCollections(final int filmId) {
    final String _sql = "SELECT 1 FROM crossFC WHERE film_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, filmId);
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
  public List<Integer> getListFilmsInCollection(final int collectionId) {
    final String _sql = "SELECT film_id FROM crossFC WHERE collection_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, collectionId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final List<Integer> _result = new ArrayList<Integer>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Integer _item;
        if (_cursor.isNull(0)) {
          _item = null;
        } else {
          _item = _cursor.getInt(0);
        }
        _result.add(_item);
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
