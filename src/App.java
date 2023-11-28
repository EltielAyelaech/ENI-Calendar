/*
 * ENI Ecole Informatique project
 * Paul TANGUY (paul.tanguy2022@campus-eni.fr)
 * src
 * App.java
 */


import java.time.LocalDate;
import java.util.Scanner;

public class App {
    public static Scanner stdin = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        boolean isProgramRunning = true;
        LocalDate chosenDate = MyCalendar.getDate();
        String userAction = "";

        while (isProgramRunning) {
            MyCalendar.display(chosenDate);

            userAction = MyCalendar.getUserAction();

            switch (userAction) {
                case "q":
                    isProgramRunning = false;
                    break;
                case "n":
                    chosenDate = chosenDate.plusMonths(1);
                    break;
                case "p":
                    chosenDate = chosenDate.plusMonths(-1);
                    break;
                case "s":
                    chosenDate = MyCalendar.getDate();
                    break;
                default:
                    break;
            }
        }
        stdin.close();
    }
}
