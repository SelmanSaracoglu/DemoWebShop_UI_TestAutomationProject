package com.demo.framework.core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TestDataWriter {

    private static final String FILE_PATH = "src/test/resources/test-user-data.txt";

    public static void writeUserDataToFile(String firstName, String lastName, String email, String password) {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH, true));
            writer.println("----- Yeni Test Kullanıcısı -----");
            writer.println("First Name: " + firstName);
            writer.println("Last Name: " + lastName);
            writer.println("Email: " + email);
            writer.println("Password: " + password);
            writer.println("----------------------------------");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
