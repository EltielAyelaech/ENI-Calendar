import java.lang.reflect.Method;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/*
 * ENI Ecole Informatique project
 * Paul TANGUY (paul.tanguy2022@campus-eni.fr)
 * src
 * MyCalendar.java
 */


public class MyCalendar {
    public static boolean isFormatValid(String userInput, DateTimeFormatter dateFormat) {
        try {
            LocalDate localDate = LocalDate.parse(userInput, dateFormat);

            return userInput.equals(localDate.format(dateFormat));
        } catch (DateTimeParseException exception) {
            return false;
        }
    }

    public static LocalDate getDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String userInput;

        System.out.println("\033[32mInput a date in the following pattern: MM/dd/yyyy (or leave empty for current day)\033[39m");
        System.out.print("> ");
        userInput = App.stdin.nextLine();

        while (!userInput.equals("") && !isFormatValid(userInput, dateFormat)) {
            System.out.println("\033[31mThe chosen date does not have the right format (MM/dd/yyyy)\033[39m");
            System.out.print("> ");
            userInput = App.stdin.nextLine();
        }

        if (userInput.equals("")) {
            return LocalDate.now();
        }
        return LocalDate.parse(userInput, dateFormat);
    }

    private static boolean isValidUserAction(String userInput) {
        return (
            userInput.equals("p")
            || userInput.equals("n")
            || userInput.equals("s")
            || userInput.equals("q")
        );
    }

    public static String getUserAction() {
        String userInput = "";

        System.out.print("[\033[1;33mP\033[0m]revious Month");
        System.out.print(" - ");
        System.out.print("[\033[1;33mN\033[0m]ext Month");
        System.out.print(" - ");
        System.out.print("[\033[1;33mS\033[0m]earch for date");
        System.out.print(" - ");
        System.out.println("[\033[1;33mQ\033[0m]uit");

        System.out.print("> ");
        userInput = App.stdin.nextLine().toLowerCase();
        while (!isValidUserAction(userInput)) {
            System.out.println("\033[31mInvalid action\033[39m");
            System.out.print("> ");
            userInput = App.stdin.nextLine().toLowerCase();
        }

        return userInput;
    }

    private static String centered(String str, int maxLength) {
        int paddingLeftAmount = (int) Math.ceil((maxLength - str.length()) / 2);
        int paddingRightAmount = (int) Math.floor((maxLength - str.length()) / 2);
        String centeredString = "";

        for (int i = 0; i < paddingLeftAmount; i += 1) {
            centeredString += " ";
        }
        centeredString += str;
        for (int i = 0; i < paddingRightAmount; i += 1) {
            centeredString += " ";
        }
        return centeredString;
    }

    public static void display(LocalDate date) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter fullDateFormat = DateTimeFormatter.ofPattern("EEEE LLLL dd yyyy", new Locale("us"));
        DateTimeFormatter monthYearFormat = DateTimeFormatter.ofPattern("LLLL yyyy", new Locale("us"));
        DateFormatSymbols dateSymbols = new DateFormatSymbols(new Locale("us"));
        int dayOfWeekOfFirstDay = date.withDayOfMonth(1).getDayOfWeek().getValue() % 7;
        int monthLength = date.lengthOfMonth();

        System.out.println();
        System.out.println(centered(String.format("%02d:%02d", currentDateTime.getHour(), currentDateTime.getMinute()), 27));
        System.out.println(centered(date.format(fullDateFormat), 27));
        System.out.println();
        System.out.println(centered(date.format(monthYearFormat), 27));
        System.out.println(String.join(" ", dateSymbols.getShortWeekdays()).trim());

        for (int i = 1 - dayOfWeekOfFirstDay; i <= monthLength; i++) {
            if (i < 1) {
                System.out.print("   ");
            } else {
                if (i == date.getDayOfMonth()) {
                    System.out.printf("\033[33m%02d\033[0m ", i);
                } else {
                    System.out.printf("%02d ", i);
                }
            }
            if (((i + dayOfWeekOfFirstDay) % 7 == 0) || i == monthLength) {
                System.out.println();
            } else {
                System.out.print(" ");
            }
        }
    }
}
