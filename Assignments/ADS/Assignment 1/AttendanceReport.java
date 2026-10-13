public class AttendanceReport {

    static int countPresent(int[] att) {
        int count = 0;
        for (int day : att) {
            if (day == 1) count++;
        }
        return count;
    }

    static double percentage(int present, int total) {
        if (total == 0) return 0.0;
        return ((double) present / total) * 100.0;
    }

    static void longestStreak(int[] att, int value) {
        int current = 0;
        int best = 0;
        int bestEnd = -1;

        for (int i = 0; i < att.length; i++) {
            if (att[i] == value) {
                current++;
                if (current > best) {
                    best = current;
                    bestEnd = i;
                }
            } else {
                current = 0;
            }
        }

        String label = (value == 1) ? "Longest presence" : "Longest absence ";
        if (best == 0) {
            System.out.printf("%s: 0 days%n", label);
        } else {
            int startDay = (bestEnd - best + 1) + 1;
            int endDay = bestEnd + 1;
            System.out.printf("%s: %d days (day %d to day %d)%n", label, best, startDay, endDay);
        }
    }

    static int daysNeeded(int present, int total) {
        int needed = 0;
        while (percentage(present + needed, total + needed) < 75.0) {
            needed++;
        }
        return needed;
    }

    public static void main(String[] args) {
        int[] att = {1, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 0};
        int total = att.length;
        int present = countPresent(att);
        double pct = percentage(present, total);

        System.out.printf("Days present    : %d of %d%n", present, total);
        System.out.printf("Attendance      : %.2f %%%n", pct);
        System.out.printf("Eligible        : %s%n", (pct >= 75.0 ? "YES" : "NO"));
        longestStreak(att, 1);
        longestStreak(att, 0);

        if (pct < 75.0) {
            int needed = daysNeeded(present, total);
            int newPresent = present + needed;
            int newTotal = total + needed;
            double newPct = percentage(newPresent, newTotal);
            System.out.printf("Days needed for 75%%: %d (%d of %d = %.2f %%)%n", needed, newPresent, newTotal, newPct);
        }
    }
}