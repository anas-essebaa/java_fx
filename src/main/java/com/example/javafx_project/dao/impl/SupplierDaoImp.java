package com.example.javafx_project.dao.impl;


import com.example.javafx_project.dao.SupplierDao;
import com.example.javafx_project.entities.Supplier;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoImp implements SupplierDao {

    private Connection conn= DB.getConnection();
    @Override
    public void insert(Supplier Supplier) {

        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO supplier (Name,Address,cin,PhoneNumber) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, Supplier.getName());
            ps.setString(2, Supplier.getCIN());
            ps.setString(3, Supplier.getAddress());
            ps.setInt(4, Supplier.getPhoneNumber());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);
                    Supplier.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'un propriétaire");;
        } finally {
            DB.closeStatement(ps);
        }

    }

    @Override
    public void update(Supplier Supplier) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("UPDATE Supplier SET Name = ?,Cin = ?,Address = ?,PhoneNumber = ?  WHERE Id = ?");
            ps.setString(1, Supplier.getName());
            ps.setString(2,Supplier.getCIN());
            ps.setString(3,Supplier.getAddress());
            ps.setInt(4,Supplier.getPhoneNumber());
            ps.setInt(5, Supplier.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'un propriétaire");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM Supplier WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'un propriétaire");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Supplier findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM Supplier WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Supplier Supplier = new Supplier();

                Supplier.setId(rs.getInt("Id"));
                Supplier.setName(rs.getString("Name"));
                Supplier.setCIN(rs.getString("Cin"));
                Supplier.setAddress(rs.getString("Address"));
                Supplier.setPhoneNumber(rs.getInt("PhoneNumber"));

                return Supplier;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un propriétaire");
            e.printStackTrace();
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Supplier> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM Supplier");
            rs = ps.executeQuery();

            List<Supplier> listSupplier = new ArrayList<>();

            while (rs.next()) {
                Supplier Supplier = new Supplier();

                Supplier.setId(rs.getInt("Id"));
                Supplier.setName(rs.getString("Name"));
                Supplier.setCIN(rs.getString("Cin"));
                Supplier.setAddress(rs.getString("Address"));
                Supplier.setPhoneNumber(rs.getInt("PhoneNumber"));

                listSupplier.add(Supplier);
            }

            return listSupplier;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner un propriétaire");
            e.printStackTrace();
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Supplier> readFromTextFile(FileReader fileReader) {
        ArrayList<Supplier> list = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(fileReader);
            Supplier o = null;
            String readLine = br.readLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            while(readLine != null){
                String [] Supplier  = readLine.split(",");
                o = new Supplier();
                o.setId(Integer.valueOf(Supplier[0].trim()));
                o.setName(Supplier[1].trim());
                o.setCIN(Supplier[2].trim());
                o.setAddress(Supplier[3].trim());
                o.setPhoneNumber(Integer.valueOf(Supplier[4].trim()));
                list.add(o);
                readLine = br.readLine();

            }
            System.out.println(list);

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }



    @Override
    public void readFromDatabaseToTextFile() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Supplier> listSupplier = new ArrayList<>();
        try {
            ps = conn.prepareStatement("SELECT * FROM Supplier");
            rs = ps.executeQuery();
            FileOutputStream fout = new FileOutputStream("src/main/resources/Suppliers.txt");
            while (rs.next()) {
                Supplier Supplier = new Supplier();
                Supplier.setId(rs.getInt("Id"));
                Supplier.setName(rs.getString("Name"));
                Supplier.setCIN(rs.getString("Cin"));
                Supplier.setAddress(rs.getString("Adress"));
                Supplier.setPhoneNumber(rs.getInt("PhoneNumber"));
                listSupplier.add(Supplier);
            }
            for(Supplier Supplier : listSupplier){
                fout.write(Supplier.toString().getBytes());
                fout.write('\n');
                System.out.println("Supplier :"+Supplier.toString());
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            System.err.println("problème de requête pour sélectionner un propriétaire");;
        }finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public void readFromStylSheetAndInsertInDatabase(String path) {
        PreparedStatement ps = null;
        try (FileInputStream fis = new FileInputStream(new File(path))) {
            ps = conn.prepareStatement("INSERT INTO supplier (Name, CIN, Address, PhoneNumber) " +
                            "SELECT ?, ?, ?, ? FROM supplier WHERE NOT EXISTS " +
                            "(SELECT 1 FROM supplier WHERE CIN = ?) LIMIT 1",
                    Statement.RETURN_GENERATED_KEYS);

            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            // Iterate over rows
            for (Row row : sheet) {
                // Skip the first row if it contains headers
                if (row.getRowNum() == 0) {
                    continue;
                }

                // Extract data from cells
                Cell nameCell = row.getCell(1);
                Cell cinCell = row.getCell(2);
                Cell addressCell = row.getCell(3);
                Cell phoneNumberCell = row.getCell(4);
                System.out.println(nameCell.getStringCellValue());

                // Set values to the PreparedStatement
                ps.setString(1, nameCell.getStringCellValue());
                ps.setString(2, cinCell.getStringCellValue());
                ps.setString(3, addressCell.getStringCellValue());
                ps.setInt(4, (int) phoneNumberCell.getNumericCellValue());
                ps.setString(5, cinCell.getStringCellValue());

                // Execute the insert statement
                int rowsAffected = ps.executeUpdate();

                if (rowsAffected > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        System.out.println("Inserted supplier with ID: " + id);
                    }
                    rs.close();
                } else {
                    System.out.println("Skipped duplicate supplier.");
                }
            }

            workbook.close();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
