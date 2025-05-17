package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestDataReader {

    public static String[] readLastUserCredentials(String filePath){
        String email = "";
        String password = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            String lastEmail= "";
            String lastPassword= "";
            while ((line = reader.readLine()) != null){
                if (line.startsWith("Email: ")){
                    lastEmail = line.replace("Email: ", "").trim();
                } else if (line.startsWith("Password: ")) {
                    lastPassword = line.replace("Password: ", "").trim();
                }
            }
            email = lastEmail;
            password = lastPassword;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return new String[]{email, password};
    }
}
