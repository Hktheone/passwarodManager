import java.util.Random;

public class PasswordChk {
    public boolean checkPass(String password)
    {
        int passwordLength=8, upChars=0, lowChars=0;
        int special=0, digits=0;
        char ch;
        int total = password.length();
        if(total<passwordLength)
        {
            System.out.println("\nThe Password is invalid!");
            return false;
        }
        else
        {
            for(int i=0; i<total; i++)
            {
                ch = password.charAt(i);
                if(Character.isUpperCase(ch))
                    upChars = 1;
                else if(Character.isLowerCase(ch))
                    lowChars = 1;
                else if(Character.isDigit(ch))
                    digits = 1;
                else
                    special = 1;
            }
        }
        if(upChars==1 && lowChars==1 && digits==1 && special==1)
        {
            System.out.println("\nThe Password is Strong.");
            return true;
        }
        else
        {
            System.out.println("\nThe Password is Weak.");
            return false;
        }
    }
    public String generatePassword(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];

        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));

        for(int i = 4; i< length ; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return password.toString();
    }

    public static void main(String[] args) {
        System.out.println(new PasswordChk().checkPass(new PasswordChk().generatePassword(8)));
    }
}
