package com.example.javafx_project.dao;

import com.example.javafx_project.entities.Supplier;

import java.io.FileReader;
import java.util.List;

public interface SupplierDao {
    void insert(Supplier Supplier);

    void update(Supplier Supplier);

    void deleteById(Integer id);

    Supplier findById(Integer id);

    List<Supplier> findAll();

    List<Supplier> readFromTextFile(FileReader fileReader);

    void readFromDatabaseToTextFile();

    void readFromStylSheetAndInsertInDatabase(String path);
}
