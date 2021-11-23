//package com.example.taskmaster;
//
//
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.Query;
//
//import java.util.List;
//
//@Dao
//public interface TaskDao {
//
//    @Query("select * from tasks")
//    List<Task> getAll();
//
//    @Query("select * from tasks where id = :id")
//    Task findTaskById(int id);
//
//    @Insert
//    void insert(Task task);
//
//    @Insert
//    void insertAll(Task... tasks);
//
//    @Delete
//    int delete(Task task);
//
//    @Query("Delete from tasks where title = :title ")
//    int deleteTitle(String title);
//}
