public class SleeperCoach {
    private boolean[] booked;
    private int n;

    // Constructor with validation
    public SleeperCoach(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number of berths must be positive");
        }
        this.n = n;
        // Size n + 1 so berth numbers 1..N match array indices directly (index 0 is unused)
        this.booked = new boolean[n + 1];
    }

    // Berth type logic based on seatNo % 8
    public static String berthType(int seatNo) {
        int rem = seatNo % 8;
        switch (rem) {
            case 1:
            case 4:
                return "LB"; // Lower Berth
            case 2:
            case 5:
                return "MB"; // Middle Berth
            case 3:
            case 6:
                return "UB"; // Upper Berth
            case 7:
                return "SL"; // Side Lower
            case 0:
                return "SU"; // Side Upper
            default:
                return "";
        }
    }

    // Books preferred berth if available; else books any available berth; else reports Waiting List
    public int book(String preferred) {
        if (preferred == null) {
            System.out.println("Error: Preferred berth type cannot be null");
            return -1;
        }

        String pref = preferred.trim().toUpperCase();

        // Validate preferred berth type
        if (!pref.equals("LB") && !pref.equals("MB") && !pref.equals("UB") && !pref.equals("SL") && !pref.equals("SU")) {
            System.out.println("Error: Invalid preferred berth type '" + preferred + "'");
            return -1;
        }

        // Pass 1: First free berth of preferred type
        for (int s = 1; s <= n; s++) {
            if (!booked[s] && berthType(s).equalsIgnoreCase(pref)) {
                booked[s] = true;
                System.out.println("book(\"" + preferred + "\") -> Berth " + s + " (" + berthType(s) + ")");
                return s;
            }
        }

        // Pass 2: First free berth of any type
        for (int s = 1; s <= n; s++) {
            if (!booked[s]) {
                booked[s] = true;
                System.out.println("book(\"" + preferred + "\") -> No " + pref + " free. Berth " + s + " (" + berthType(s) + ") allotted instead");
                return s;
            }
        }

        // Coach is full
        System.out.println("book(\"" + preferred + "\") -> Waiting List");
        return -1;
    }

    // Cancels a booking with error handling for invalid or unbooked berths
    public boolean cancel(int seatNo) {
        if (seatNo < 1 || seatNo > n) {
            System.out.println("cancel(" + seatNo + ") -> Error: Invalid berth number " + seatNo);
            return false;
        }
        if (!booked[seatNo]) {
            System.out.println("cancel(" + seatNo + ") -> Error: Berth " + seatNo + " is not currently booked");
            return false;
        }
        booked[seatNo] = false;
        System.out.println("cancel(" + seatNo + ") -> Berth " + seatNo + " cancelled");
        return true;
    }

    // Returns total count of available berths
    public int available() {
        int freeCount = 0;
        for (int s = 1; s <= n; s++) {
            if (!booked[s]) {
                freeCount++;
            }
        }
        return freeCount;
    }

    // Prints how many berths of each type are free
    public void availableByType() {
        int lb = 0, mb = 0, ub = 0, sl = 0, su = 0;
        for (int s = 1; s <= n; s++) {
            if (!booked[s]) {
                switch (berthType(s)) {
                    case "LB": lb++; break;
                    case "MB": mb++; break;
                    case "UB": ub++; break;
                    case "SL": sl++; break;
                    case "SU": su++; break;
                }
            }
        }
        System.out.println("Free Berths by Type -> LB: " + lb + ", MB: " + mb + ", UB: " + ub + ", SL: " + sl + ", SU: " + su);
    }

    // Prints berth status chart (X = booked, _ = free)
    public void printChart() {
        StringBuilder sb = new StringBuilder();
        for (int s = 1; s <= n; s++) {
            sb.append(s).append(":").append(berthType(s)).append(":").append(booked[s] ? "X" : "_").append(" ");
        }
        System.out.println("Chart: " + sb.toString().trim());
    }

    public static void main(String[] args) {
        // Coach with 16 berths
        SleeperCoach coach = new SleeperCoach(16);

        // Pre-book berths 1, 2, 4, 7 as described in the example
        coach.booked[1] = true;
        coach.booked[2] = true;
        coach.booked[4] = true;
        coach.booked[7] = true;

        System.out.println("--- Running Example from PDF ---");
        coach.book("LB");
        coach.book("SL");
        coach.book("SL");
        coach.cancel(4);
        coach.book("LB");
        System.out.println("available() -> " + coach.available() + " berths free");

        System.out.println("\n--- Additional Checks ---");
        coach.availableByType();
        coach.printChart();
        coach.cancel(4);  // Successfully cancels berth 4
        coach.cancel(4);  // Error: berth 4 is not booked
        coach.cancel(99); // Error: invalid berth number
    }
}