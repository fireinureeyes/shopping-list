package com.matejrajtar.shoppinglist.database;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.arch.persistence.room.SharedSQLiteStatement;
import android.database.Cursor;
import com.matejrajtar.shoppinglist.model.Category;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class CategoryDao_Impl implements CategoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfCategory;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfCategory;

  private final SharedSQLiteStatement __preparedStmtOfRename;

  public CategoryDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCategory = new EntityInsertionAdapter<Category>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Category`(`name`) VALUES (?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Category value) {
        if (value.name == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.name);
        }
      }
    };
    this.__deletionAdapterOfCategory = new EntityDeletionOrUpdateAdapter<Category>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Category` WHERE `name` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Category value) {
        if (value.name == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.name);
        }
      }
    };
    this.__preparedStmtOfRename = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Category SET name=? WHERE name=?";
        return _query;
      }
    };
  }

  @Override
  public void insert(Category... categories) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfCategory.insert(categories);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(Category category) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfCategory.handle(category);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void rename(String oldName, String newName) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfRename.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      if (newName == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, newName);
      }
      _argIndex = 2;
      if (oldName == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, oldName);
      }
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfRename.release(_stmt);
    }
  }

  @Override
  public List<Category> all() {
    final String _sql = "SELECT * FROM Category";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
      final List<Category> _result = new ArrayList<Category>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Category _item;
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item = new Category(_tmpName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public boolean contains(String name) {
    final String _sql = "SELECT EXISTS(SELECT * FROM Category WHERE name=?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    final Cursor _cursor = __db.query(_statement);
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
}
