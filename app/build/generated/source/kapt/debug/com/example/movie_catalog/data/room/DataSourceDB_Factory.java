// Generated by Dagger (https://dagger.dev).
package com.example.movie_catalog.data.room;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DataSourceDB_Factory implements Factory<DataSourceDB> {
  private final Provider<DataDao> dataDaoProvider;

  public DataSourceDB_Factory(Provider<DataDao> dataDaoProvider) {
    this.dataDaoProvider = dataDaoProvider;
  }

  @Override
  public DataSourceDB get() {
    return newInstance(dataDaoProvider.get());
  }

  public static DataSourceDB_Factory create(Provider<DataDao> dataDaoProvider) {
    return new DataSourceDB_Factory(dataDaoProvider);
  }

  public static DataSourceDB newInstance(DataDao dataDao) {
    return new DataSourceDB(dataDao);
  }
}
