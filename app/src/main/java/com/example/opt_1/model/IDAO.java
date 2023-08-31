package com.example.opt_1.model;

import java.sql.SQLException;

public interface IDAO {
    void createData(int userid) throws SQLException;
    void updateData() throws SQLException;
}
