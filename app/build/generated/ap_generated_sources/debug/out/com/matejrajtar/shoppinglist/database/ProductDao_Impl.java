package com.matejrajtar.shoppinglist.database;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.matejrajtar.shoppinglist.model.Product;
import java.lang.Boolean;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class ProductDao_Impl implements ProductDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Product> __insertionAdapterOfProduct;

  private final EntityDeletionOrUpdateAdapter<Product> __deletionAdapterOfProduct;

  private final SharedSQLiteStatement __preparedStmtOfUpdateCategory;

  private final SharedSQLiteStatement __preparedStmtOfUpdate;

  private final SharedSQLiteStatement __preparedStmtOfSetSelection;

  private final SharedSQLiteStatement __preparedStmtOfMoveToCart;

  public ProductDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfProduct = new EntityInsertionAdapter<Product>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Product` (`id`,`category`,`name`,`image`,`inCart`,`selected`) VALUES (?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Product value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
        if (value.category == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.category);
        }
        if (value.name == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.name);
        }
        if (value.image == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.image);
        }
        if (value.inCart == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.inCart);
        }
        final Integer _tmp;
        _tmp = value.selected == null ? null : (value.selected ? 1 : 0);
        if (_tmp == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindLong(6, _tmp);
        }
      }
    };
    this.__deletionAdapterOfProduct = new EntityDeletionOrUpdateAdapter<Product>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Product` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Product value) {
        if (value.id == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindLong(1, value.id);
        }
      }
    };
    this.__preparedStmtOfUpdateCategory = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Product SET category=? WHERE category=?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdate = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Product SET name=?, category=?, image=? WHERE id=?";
        return _query;
      }
    };
    this.__preparedStmtOfSetSelection = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Product SET selected=? WHERE id=?";
        return _query;
      }
    };
    this.__preparedStmtOfMoveToCart = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE Product SET inCart=?, selected=? WHERE id=?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Product... products) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfProduct.insert(products);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Product product) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfProduct.handle(product);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void updateCategory(final String oldCategory, final String newCategory) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateCategory.acquire();
    int _argIndex = 1;
    if (newCategory == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, newCategory);
    }
    _argIndex = 2;
    if (oldCategory == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, oldCategory);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateCategory.release(_stmt);
    }
  }

  @Override
  public void update(final Integer id, final String name, final String category,
      final String image) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdate.acquire();
    int _argIndex = 1;
    if (name == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, name);
    }
    _argIndex = 2;
    if (category == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, category);
    }
    _argIndex = 3;
    if (image == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, image);
    }
    _argIndex = 4;
    if (id == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, id);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdate.release(_stmt);
    }
  }

  @Override
  public void setSelection(final Integer id, final Boolean selected) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfSetSelection.acquire();
    int _argIndex = 1;
    final Integer _tmp;
    _tmp = selected == null ? null : (selected ? 1 : 0);
    if (_tmp == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, _tmp);
    }
    _argIndex = 2;
    if (id == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, id);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfSetSelection.release(_stmt);
    }
  }

  @Override
  public void moveToCart(final Integer id, final String inCart, final Boolean selected) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfMoveToCart.acquire();
    int _argIndex = 1;
    if (inCart == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, inCart);
    }
    _argIndex = 2;
    final Integer _tmp;
    _tmp = selected == null ? null : (selected ? 1 : 0);
    if (_tmp == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    if (id == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindLong(_argIndex, id);
    }
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfMoveToCart.release(_stmt);
    }
  }

  @Override
  public List<Product> all() {
    final String _sql = "SELECT * FROM Product";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
      final int _cursorIndexOfInCart = CursorUtil.getColumnIndexOrThrow(_cursor, "inCart");
      final int _cursorIndexOfSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "selected");
      final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Product _item;
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpImage;
        if (_cursor.isNull(_cursorIndexOfImage)) {
          _tmpImage = null;
        } else {
          _tmpImage = _cursor.getString(_cursorIndexOfImage);
        }
        final String _tmpInCart;
        if (_cursor.isNull(_cursorIndexOfInCart)) {
          _tmpInCart = null;
        } else {
          _tmpInCart = _cursor.getString(_cursorIndexOfInCart);
        }
        final Boolean _tmpSelected;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfSelected)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfSelected);
        }
        _tmpSelected = _tmp == null ? null : _tmp != 0;
        _item = new Product(_tmpCategory,_tmpName,_tmpImage,_tmpInCart,_tmpSelected);
        if (_cursor.isNull(_cursorIndexOfId)) {
          _item.id = null;
        } else {
          _item.id = _cursor.getInt(_cursorIndexOfId);
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
  public List<Product> inCart() {
    final String _sql = "SELECT * FROM Product WHERE inCart!='0'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
      final int _cursorIndexOfInCart = CursorUtil.getColumnIndexOrThrow(_cursor, "inCart");
      final int _cursorIndexOfSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "selected");
      final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Product _item;
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpImage;
        if (_cursor.isNull(_cursorIndexOfImage)) {
          _tmpImage = null;
        } else {
          _tmpImage = _cursor.getString(_cursorIndexOfImage);
        }
        final String _tmpInCart;
        if (_cursor.isNull(_cursorIndexOfInCart)) {
          _tmpInCart = null;
        } else {
          _tmpInCart = _cursor.getString(_cursorIndexOfInCart);
        }
        final Boolean _tmpSelected;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfSelected)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfSelected);
        }
        _tmpSelected = _tmp == null ? null : _tmp != 0;
        _item = new Product(_tmpCategory,_tmpName,_tmpImage,_tmpInCart,_tmpSelected);
        if (_cursor.isNull(_cursorIndexOfId)) {
          _item.id = null;
        } else {
          _item.id = _cursor.getInt(_cursorIndexOfId);
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
  public List<Product> byCategory(final String category, final Boolean inCart) {
    final String _sql = "SELECT * FROM Product WHERE category=? AND inCart=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
    _argIndex = 2;
    final Integer _tmp;
    _tmp = inCart == null ? null : (inCart ? 1 : 0);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
      final int _cursorIndexOfInCart = CursorUtil.getColumnIndexOrThrow(_cursor, "inCart");
      final int _cursorIndexOfSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "selected");
      final List<Product> _result = new ArrayList<Product>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Product _item;
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpImage;
        if (_cursor.isNull(_cursorIndexOfImage)) {
          _tmpImage = null;
        } else {
          _tmpImage = _cursor.getString(_cursorIndexOfImage);
        }
        final String _tmpInCart;
        if (_cursor.isNull(_cursorIndexOfInCart)) {
          _tmpInCart = null;
        } else {
          _tmpInCart = _cursor.getString(_cursorIndexOfInCart);
        }
        final Boolean _tmpSelected;
        final Integer _tmp_1;
        if (_cursor.isNull(_cursorIndexOfSelected)) {
          _tmp_1 = null;
        } else {
          _tmp_1 = _cursor.getInt(_cursorIndexOfSelected);
        }
        _tmpSelected = _tmp_1 == null ? null : _tmp_1 != 0;
        _item = new Product(_tmpCategory,_tmpName,_tmpImage,_tmpInCart,_tmpSelected);
        if (_cursor.isNull(_cursorIndexOfId)) {
          _item.id = null;
        } else {
          _item.id = _cursor.getInt(_cursorIndexOfId);
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
  public Product byName(final String name) {
    final String _sql = "SELECT * FROM Product WHERE name=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
      final int _cursorIndexOfInCart = CursorUtil.getColumnIndexOrThrow(_cursor, "inCart");
      final int _cursorIndexOfSelected = CursorUtil.getColumnIndexOrThrow(_cursor, "selected");
      final Product _result;
      if(_cursor.moveToFirst()) {
        final String _tmpCategory;
        if (_cursor.isNull(_cursorIndexOfCategory)) {
          _tmpCategory = null;
        } else {
          _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
        }
        final String _tmpName;
        if (_cursor.isNull(_cursorIndexOfName)) {
          _tmpName = null;
        } else {
          _tmpName = _cursor.getString(_cursorIndexOfName);
        }
        final String _tmpImage;
        if (_cursor.isNull(_cursorIndexOfImage)) {
          _tmpImage = null;
        } else {
          _tmpImage = _cursor.getString(_cursorIndexOfImage);
        }
        final String _tmpInCart;
        if (_cursor.isNull(_cursorIndexOfInCart)) {
          _tmpInCart = null;
        } else {
          _tmpInCart = _cursor.getString(_cursorIndexOfInCart);
        }
        final Boolean _tmpSelected;
        final Integer _tmp;
        if (_cursor.isNull(_cursorIndexOfSelected)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(_cursorIndexOfSelected);
        }
        _tmpSelected = _tmp == null ? null : _tmp != 0;
        _result = new Product(_tmpCategory,_tmpName,_tmpImage,_tmpInCart,_tmpSelected);
        if (_cursor.isNull(_cursorIndexOfId)) {
          _result.id = null;
        } else {
          _result.id = _cursor.getInt(_cursorIndexOfId);
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
  public boolean containsWithName(final String name) {
    final String _sql = "SELECT EXISTS(SELECT * FROM Product WHERE name=?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (name == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, name);
    }
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
  public boolean containsWithCategory(final String category) {
    final String _sql = "SELECT EXISTS(SELECT * FROM Product WHERE category=?)";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (category == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, category);
    }
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
