import java.util.Scanner;

class Date {

    private int day;
    private int month;
    private int year;

    // Setter / Mutator
    public void setDate(int dd, int mm, int yy) {

        year = yy;

        // Validate month
        if (mm < 1 || mm > 12) {
            month = 1;
        } else {
            month = mm;
        }

        // Validate day
        if (dd < 1 || dd > getDaysInMonth(month, year)) {
            day = 1;
        } else {
            day = dd;
        }
    }

    // Check Leap Year
    private boolean isLeapYear(int year) {

        return (year % 400 == 0) ||
               (year % 4 == 0 && year % 100 != 0);
    }

    // Get number of days in month
    private int getDaysInMonth(int month, int year) {

        if (month == 2) {
            if (isLeapYear(year))
                return 29;
            else
                return 28;
        }

        if (month == 4 || month == 6 ||
            month == 9 || month == 11) {
            return 30;
        }

        return 31;
    }

    // Add Days
    public void addDays(int days) {

        while (days > 0) {

            int maxDays = getDaysInMonth(month, year);

            if (day < maxDays) {
                day++;
            } else {

                day = 1;

                if (month == 12) {
                    month = 1;
                    year++;
                } else {
                    month++;
                }
            }

            days--;
        }
    }

    // Add Months
    public void addMonths(int months) {

        while (months > 0) {

            if (month == 12) {
                month = 1;
                year++;
            } else {
                month++;
            }

            int maxDays = getDaysInMonth(month, year);

            if (day > maxDays) {
                day = maxDays;
            }

            months--;
        }
    }

    // Add Years
    public void addYears(int years) {

        year = year + years;

        // 29 Feb -> 28 Feb if new year is not leap year
        if (month == 2 && day == 29 && !isLeapYear(year)) {
            day = 28;
        }
    }

    // Getter / Accessor
    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    // Display Date
    public void display() {

        System.out.println(
            getDay() + "/" +
            getMonth() + "/" +
            getYear()
        );
    }

    // Compare Dates
    public int compare(Date other) {

        if (this.year < other.year)
            return -1;

        if (this.year > other.year)
            return 1;

        if (this.month < other.month)
            return -1;

        if (this.month > other.month)
            return 1;

        if (this.day < other.day)
            return -1;

        if (this.day > other.day)
            return 1;

        return 0;
    }
}


// ============================================
// Main Class
// ============================================

public class Calendar {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Date objDate = new Date();

        int choice;

        do {

            System.out.println();
            System.out.println("--------------------------------");
            System.out.println("Date Menu");
            System.out.println("--------------------------------");
            System.out.println("1. Set Date");
            System.out.println("2. Add Days");
            System.out.println("3. Add Months");
            System.out.println("4. Add Years");
            System.out.println("5. Display");
            System.out.println("6. Compare Dates");
            System.out.println("7. Exit");
            System.out.println("--------------------------------");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Enter the day: ");
                    int day = sc.nextInt();

                    System.out.print("Enter the month: ");
                    int month = sc.nextInt();

                    System.out.print("Enter the year: ");
                    int year = sc.nextInt();

                    objDate.setDate(day, month, year);

                    System.out.println("Date set successfully.");

                    break;


                case 2:

                    System.out.print("Enter number of days: ");
                    int days = sc.nextInt();

                    objDate.addDays(days);

                    System.out.println("Days added successfully.");

                    break;


                case 3:

                    System.out.print("Enter number of months: ");
                    int months = sc.nextInt();

                    objDate.addMonths(months);

                    System.out.println("Months added successfully.");

                    break;


                case 4:

                    System.out.print("Enter number of years: ");
                    int years = sc.nextInt();

                    objDate.addYears(years);

                    System.out.println("Years added successfully.");

                    break;


                case 5:

                    System.out.print("Current Date: ");
                    objDate.display();

                    break;


                case 6:

                    Date compareDate = new Date();

                    System.out.print("Enter day: ");
                    int d = sc.nextInt();

                    System.out.print("Enter month: ");
                    int m = sc.nextInt();

                    System.out.print("Enter year: ");
                    int y = sc.nextInt();

                    compareDate.setDate(d, m, y);

                    int result = objDate.compare(compareDate);

                    if (result == 0) {

                        System.out.println(
                            "Both dates are equal."
                        );

                    } else if (result < 0) {

                        System.out.println(
                            "Current date is earlier."
                        );

                    } else {

                        System.out.println(
                            "Current date is later."
                        );
                    }

                    break;


                case 7:

                    System.out.println("Exiting...");

                    break;


                default:

                    System.out.println(
                        "Invalid choice."
                    );
            }

        } while (choice != 7);

        sc.close();
    }
}