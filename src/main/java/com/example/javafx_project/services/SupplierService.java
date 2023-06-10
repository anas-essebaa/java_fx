package com.example.javafx_project.services;

import com.example.javafx_project.dao.SupplierDao;
import com.example.javafx_project.dao.impl.SupplierDaoImp;
import com.example.javafx_project.entities.Supplier;

import java.io.FileReader;
import java.util.List;

public class SupplierService {
    private SupplierDao SupplierDao =new SupplierDaoImp();

    public List<Supplier> findAll() {
        return SupplierDao.findAll();
    }

    public void save(Supplier Supplier) {

        SupplierDao.insert(Supplier);

    }
    public void update(Supplier Supplier) {
        SupplierDao.update(Supplier);
    }
    public void remove(Supplier Supplier) {
        SupplierDao.deleteById(Supplier.getId());
    }

    public List<Supplier> readFromTextFile(FileReader fileReader){
        return SupplierDao.readFromTextFile(fileReader);
    }

    public void readFromDatabaseToTextFile(){
        SupplierDao.readFromDatabaseToTextFile();
    }

    public void readFromStyleSheetAndInsertInDatabase(String path){
        SupplierDao.readFromStylSheetAndInsertInDatabase(path);
    }
}
