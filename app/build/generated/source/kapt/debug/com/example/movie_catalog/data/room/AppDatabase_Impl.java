package com.example.movie_catalog.data.room;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile DataDao _dataDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `films` (`idFilm` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `msg` TEXT NOT NULL, `filmId` INTEGER, `imdbId` TEXT, `nameRu` TEXT, `nameEn` TEXT, `rating` REAL, `posterUrlPreview` TEXT, `countries` TEXT, `genres` TEXT, `favorite` INTEGER, `viewed` INTEGER, `bookmark` INTEGER, `professionKey` TEXT, `startYear` INTEGER, `images` TEXT, `posterUrl` TEXT, `logoUrl` TEXT, `nameOriginal` TEXT, `ratingImdb` REAL, `ratingAwait` REAL, `ratingGoodReview` REAL, `year` INTEGER, `totalSeasons` INTEGER, `listSeasons` TEXT, `description` TEXT, `shortDescription` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `collections` (`idCollection` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `count` INTEGER, `image` INTEGER, `included` INTEGER)");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_collections_name` ON `collections` (`name`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `crossFC` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `film_id` INTEGER NOT NULL, `collection_id` INTEGER NOT NULL, `value` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '41d989caaf76ca212d9496e46fd02e16')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `films`");
        _db.execSQL("DROP TABLE IF EXISTS `collections`");
        _db.execSQL("DROP TABLE IF EXISTS `crossFC`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsFilms = new HashMap<String, TableInfo.Column>(27);
        _columnsFilms.put("idFilm", new TableInfo.Column("idFilm", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("msg", new TableInfo.Column("msg", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("filmId", new TableInfo.Column("filmId", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("imdbId", new TableInfo.Column("imdbId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("nameRu", new TableInfo.Column("nameRu", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("nameEn", new TableInfo.Column("nameEn", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("rating", new TableInfo.Column("rating", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("posterUrlPreview", new TableInfo.Column("posterUrlPreview", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("countries", new TableInfo.Column("countries", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("genres", new TableInfo.Column("genres", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("favorite", new TableInfo.Column("favorite", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("viewed", new TableInfo.Column("viewed", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("bookmark", new TableInfo.Column("bookmark", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("professionKey", new TableInfo.Column("professionKey", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("startYear", new TableInfo.Column("startYear", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("images", new TableInfo.Column("images", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("posterUrl", new TableInfo.Column("posterUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("logoUrl", new TableInfo.Column("logoUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("nameOriginal", new TableInfo.Column("nameOriginal", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("ratingImdb", new TableInfo.Column("ratingImdb", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("ratingAwait", new TableInfo.Column("ratingAwait", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("ratingGoodReview", new TableInfo.Column("ratingGoodReview", "REAL", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("year", new TableInfo.Column("year", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("totalSeasons", new TableInfo.Column("totalSeasons", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("listSeasons", new TableInfo.Column("listSeasons", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsFilms.put("shortDescription", new TableInfo.Column("shortDescription", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFilms = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFilms = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFilms = new TableInfo("films", _columnsFilms, _foreignKeysFilms, _indicesFilms);
        final TableInfo _existingFilms = TableInfo.read(_db, "films");
        if (! _infoFilms.equals(_existingFilms)) {
          return new RoomOpenHelper.ValidationResult(false, "films(com.example.movie_catalog.data.room.tables.FilmDB).\n"
                  + " Expected:\n" + _infoFilms + "\n"
                  + " Found:\n" + _existingFilms);
        }
        final HashMap<String, TableInfo.Column> _columnsCollections = new HashMap<String, TableInfo.Column>(5);
        _columnsCollections.put("idCollection", new TableInfo.Column("idCollection", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollections.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollections.put("count", new TableInfo.Column("count", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollections.put("image", new TableInfo.Column("image", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCollections.put("included", new TableInfo.Column("included", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCollections = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCollections = new HashSet<TableInfo.Index>(1);
        _indicesCollections.add(new TableInfo.Index("index_collections_name", true, Arrays.asList("name"), Arrays.asList("ASC")));
        final TableInfo _infoCollections = new TableInfo("collections", _columnsCollections, _foreignKeysCollections, _indicesCollections);
        final TableInfo _existingCollections = TableInfo.read(_db, "collections");
        if (! _infoCollections.equals(_existingCollections)) {
          return new RoomOpenHelper.ValidationResult(false, "collections(com.example.movie_catalog.data.room.tables.CollectionDB).\n"
                  + " Expected:\n" + _infoCollections + "\n"
                  + " Found:\n" + _existingCollections);
        }
        final HashMap<String, TableInfo.Column> _columnsCrossFC = new HashMap<String, TableInfo.Column>(4);
        _columnsCrossFC.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCrossFC.put("film_id", new TableInfo.Column("film_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCrossFC.put("collection_id", new TableInfo.Column("collection_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCrossFC.put("value", new TableInfo.Column("value", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCrossFC = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCrossFC = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCrossFC = new TableInfo("crossFC", _columnsCrossFC, _foreignKeysCrossFC, _indicesCrossFC);
        final TableInfo _existingCrossFC = TableInfo.read(_db, "crossFC");
        if (! _infoCrossFC.equals(_existingCrossFC)) {
          return new RoomOpenHelper.ValidationResult(false, "crossFC(com.example.movie_catalog.data.room.tables.CrossFC).\n"
                  + " Expected:\n" + _infoCrossFC + "\n"
                  + " Found:\n" + _existingCrossFC);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "41d989caaf76ca212d9496e46fd02e16", "bb997fd110f5bd5188920a451f009c9a");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "films","collections","crossFC");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `films`");
      _db.execSQL("DELETE FROM `collections`");
      _db.execSQL("DELETE FROM `crossFC`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(DataDao.class, DataDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public DataDao dataDao() {
    if (_dataDao != null) {
      return _dataDao;
    } else {
      synchronized(this) {
        if(_dataDao == null) {
          _dataDao = new DataDao_Impl(this);
        }
        return _dataDao;
      }
    }
  }
}
