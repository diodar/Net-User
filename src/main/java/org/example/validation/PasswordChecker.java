package org.example.validation;

import org.example.database.DBConn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordChecker {

    public static boolean isPasswordExists(String userName, String password) {

        try (Connection connection = DBConn.connect()) {
            String query = "SELECT password FROM " + Constants.TABLE_USERS + " WHERE user_name = ?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, userName);

                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        String pass = rs.getString("password");
                        if (password.equals(pass)) {
                            System.out.println(Constants.PASS_EXISTS_MSG);
                            return true;
                        } else {
                            System.out.println(Constants.PASS_NO_EXISTS_MSG);
                        }
                    } else {
                        System.out.println(Constants.USER_NO_EXISTS_MSG);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}