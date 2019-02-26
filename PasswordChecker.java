import javafx.scene.shape.Path;

import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class PasswordChecker {
    private static final String dictionaryFile = "english.txt";
    private static String brokenPassword;
    public static void main(String[] args) {
        var checker = new PasswordChecker();
        var frame = new JFrame("Password Checker");
        while (true) {
            var password = readPassword();
            if (password == null)
                break;
            var score = checker.check(password);
            JOptionPane.showMessageDialog(frame, "Your score: " + score);
//            System.out.println(String.format("Your score: %d.", score));
        }
    }

    private static boolean isDictionaryWord(String word) {
        var stream = ClassLoader.getSystemResourceAsStream(dictionaryFile);
        return new BufferedReader(new InputStreamReader(stream)).lines()
                .anyMatch(word::equals);
    }

    private static boolean containsDictionaryWord(String word) {
        var stream = ClassLoader.getSystemResourceAsStream(dictionaryFile);
        return new BufferedReader(new InputStreamReader(stream)).lines()
                .anyMatch(word::contains);
    }

    private static String readPassword() {
//        System.out.print("Enter your password: ");
//        return new Scanner(System.in).nextLine();
        return JOptionPane.showInputDialog("Enter a password:");
    }

    public static String[] splitPassword(String password) {
        return password.split("[^a-zA-z]+");
    }

    public int check(String password) {
        if (isDictionaryWord(password)) {
            System.out.println("That password is a dictionary word!");
            return 0;
        }
//        else if ((isWordInDictionary(brokenPassword)) && (password.matches(".*\\d+.*"))){
//            System.out.println("Looks like you're just using a normal word with letters attached. This is more secure than a simple word but still very easy to crack.");
//        }

        var upperCase = 0;
        var lowerCase = 0;
        var digit = 0;
        var other = 0;
        var dictionaryWord = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c))
                upperCase++;
            else if (Character.isLowerCase(c))
                lowerCase++;
            else if (Character.isDigit(c))
                digit++;
            else
                other++;
        }

        if (containsDictionaryWord(password)) {
            System.out.println("That password contains dictionary words!");
            dictionaryWord = true;
        }

        return password.length()
                  + upperCase
                  + lowerCase
                  + digit * 9
                  + other * 15
                  + (dictionaryWord ? 0 : -10);
    }
}
