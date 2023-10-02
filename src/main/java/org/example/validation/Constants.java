package org.example.validation;

public final class Constants {

    public final static String DB_DRIVER = "jdbc:sqlite:";
    public final static String DB_BASE_URL = "src/main/java/org/example/database/";
    public final static String DB_NAME = "users_db.db";
    public final static String TABLE_USERS = "users";

    public final static String WRONG_USERNAME_PASS = "Wrong USER_NAME or PASSWORD. Try again.";
    public final static String SUCCESS_AUTH = "Your authorisation was successful. Wellcome to the server.";
    public final static String PASS_NO_EXISTS_MSG = "Password is incorrect.";
    public final static String PASS_EXISTS_MSG = "Password is correct.";
    public final static String USER_NO_EXISTS_MSG = "There is no Users with such User_name.";
}
