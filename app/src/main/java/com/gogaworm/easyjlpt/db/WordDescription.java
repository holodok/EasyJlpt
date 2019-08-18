package com.gogaworm.easyjlpt.db;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class WordDescription {
    @Embedded
    public Word word;

    @Relation(parentColumn = "id", entityColumn = "groupId")
    public List<Expression> expressions;
}
