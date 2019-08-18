package com.gogaworm.easyjlpt.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExpressionDao {
    @Insert
    public void insert(Expression expression);

    @Query("select * from expression")
    LiveData<List<Expression>> getAllExpressions();

    @Query("select * from Expression where type=1 and groupId=:id")
    List<Expression> getExpressionForWord(int id);

    @Query("select * from Expression where type=2 and groupId=:id")
    LiveData<List<Expression>> getExpressionForKanji(int id);

    @Query("select * from Expression where type=2 and groupId=:id")
    List<Expression> getExpressionForKanjiRaw(int id);

    @Query("select * from Expression where code=:code")
    Expression getExpressionByCode(String code);

    @Update
    void update(Expression expression);
}
