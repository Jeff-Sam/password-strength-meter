import java.util.Scanner;

public class PasswordChecker {
    public static void main(String[] args) {
        var password = readPassword();
        var checker = new PasswordChecker();
        var score = checker.check(password);
        System.out.println(String.format("Your score: %d.", score));
    }

    private static String readPassword() {
        System.out.print("Enter your password: ");
        return new Scanner(System.in).nextLine();
    }

    public int check(String password) {
        var upperCase = 0;
        var lowerCase = 0;
        var digit = 0;
        var other = 0;

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

        return password.length()
                  + upperCase
                  + lowerCase
                  + digit * 9
                  + other * 15;
    }
}
