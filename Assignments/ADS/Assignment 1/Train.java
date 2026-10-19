class Coach {
    String id;
    String type;
    Coach next;

    Coach(String id, String type) {
        this.id = id;
        this.type = type;
        this.next = null;
    }
}

public class Train {
    // Engine is always the head node and cannot be detached
    private Coach head = new Coach("ENGINE", "ENGINE");
    private Coach tail = head;
    private int count = 0; // Number of coaches, excluding the engine

    // Helper method to enforce uniqueness of coach IDs
    private boolean exists(String id) {
        if (id == null) return false;
        Coach curr = head;
        while (curr != null) {
            if (curr.id.equalsIgnoreCase(id.trim())) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    // Attaches a coach at the end in O(1) time using the tail reference
    public void attach(String id, String type) {
        if (id == null || id.trim().isEmpty() || type == null || type.trim().isEmpty()) {
            System.out.println("Error: Coach ID and type cannot be null or empty.");
            return;
        }
        id = id.trim();
        type = type.trim();

        if (exists(id)) {
            System.out.println("Error: Duplicate coach ID '" + id + "' is not allowed.");
            return;
        }

        Coach newCoach = new Coach(id, type);
        tail.next = newCoach;
        tail = newCoach;
        count++;
    }

    // Inserts a coach immediately after afterId
    public boolean insertAfter(String afterId, String id, String type) {
        if (afterId == null || afterId.trim().isEmpty() ||
            id == null || id.trim().isEmpty() ||
            type == null || type.trim().isEmpty()) {
            System.out.println("Error: Arguments for insertAfter cannot be null or empty.");
            return false;
        }
        afterId = afterId.trim();
        id = id.trim();
        type = type.trim();

        if (exists(id)) {
            System.out.println("Error: Duplicate coach ID '" + id + "' is not allowed.");
            return false;
        }

        Coach curr = head;
        while (curr != null && !curr.id.equalsIgnoreCase(afterId)) {
            curr = curr.next;
        }

        if (curr == null) {
            System.out.println("Error: Coach '" + afterId + "' not found.");
            return false;
        }

        Coach newCoach = new Coach(id, type);
        newCoach.next = curr.next;
        curr.next = newCoach;

        // If inserted after the current tail, update the tail pointer
        if (curr == tail) {
            tail = newCoach;
        }
        count++;
        return true;
    }

    // Detaches coach with the specified ID (ENGINE can never be detached)
    public boolean detach(String id) {
        if (id == null || id.trim().isEmpty()) {
            System.out.println("Error: Coach ID cannot be null or empty.");
            return false;
        }
        id = id.trim();

        if (id.equalsIgnoreCase("ENGINE")) {
            System.out.println("Error: ENGINE cannot be detached.");
            return false;
        }

        Coach prev = head;
        while (prev.next != null && !prev.next.id.equalsIgnoreCase(id)) {
            prev = prev.next;
        }

        if (prev.next == null) {
            System.out.println("Error: Coach '" + id + "' not found.");
            return false;
        }

        // If detaching the last coach, update tail to prev
        if (prev.next == tail) {
            tail = prev;
        }
        prev.next = prev.next.next;
        count--;
        return true;
    }

    // Returns position of coach from engine (1 = first coach after ENGINE)
    public int position(String id) {
        if (id == null || id.trim().isEmpty()) {
            return -1;
        }
        id = id.trim();

        Coach curr = head.next;
        int pos = 1;
        while (curr != null) {
            if (curr.id.equalsIgnoreCase(id)) {
                return pos;
            }
            curr = curr.next;
            pos++;
        }
        return -1; // Not found
    }

    // Counts number of coaches of a specific type (e.g., SLEEPER, GENERAL)
    public int countType(String type) {
        if (type == null || type.trim().isEmpty()) {
            return 0;
        }
        type = type.trim();

        int typeCount = 0;
        Coach curr = head.next;
        while (curr != null) {
            if (curr.type.equalsIgnoreCase(type)) {
                typeCount++;
            }
            curr = curr.next;
        }
        return typeCount;
    }

    // Returns the total number of coaches (excluding the engine)
    public int totalCoaches() {
        return count;
    }

    // Displays the train from engine to the last coach
    public void display() {
        StringBuilder sb = new StringBuilder();
        Coach curr = head;
        while (curr != null) {
            sb.append(curr.id);
            if (curr.next != null) {
                sb.append(" -> ");
            }
            curr = curr.next;
        }
        System.out.println("display() -> " + sb.toString());
    }

    // Task 3: Reverses the order of coaches while keeping the engine first
    public void reverseCoaches() {
        if (head.next == null || head.next.next == null) {
            return; // 0 or 1 coach: nothing to reverse
        }

        Coach prev = null;
        Coach curr = head.next;
        tail = curr; // The original first coach becomes the new tail

        while (curr != null) {
            Coach nextNode = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextNode;
        }
        head.next = prev;
    }

    public static void main(String[] args) {
        Train train = new Train();

        System.out.println("--- Running Example from PDF ---");
        // Attach initial coaches
        train.attach("S1", "SLEEPER");
        train.attach("S2", "SLEEPER");
        train.attach("S3", "SLEEPER");
        train.attach("GEN1", "GENERAL");

        // Insert pantry car after S2
        train.insertAfter("S2", "PC", "PANTRY");

        // Detach coach S3
        train.detach("S3");

        // Display results
        train.display();
        System.out.println("countType(SLEEPER) -> " + train.countType("SLEEPER"));
        System.out.println("position(\"PC\")       -> Coach PC is at position " + train.position("PC") + " from the engine");
        System.out.println("totalCoaches()       -> " + train.totalCoaches() + " (engine not counted)");

        System.out.println("\n--- Testing Task 3: reverseCoaches() ---");
        train.reverseCoaches();
        train.display();

        System.out.println("\n--- Testing Edge Cases & Null Handling ---");
        train.attach("S1", "SLEEPER"); // Duplicate rejection
        train.detach("ENGINE");        // Engine protection
        train.detach(null);            // Handled separately from ENGINE
        train.detach("B1");            // Non-existent coach
        System.out.println("position(null)  -> " + train.position(null));
        System.out.println("countType(null) -> " + train.countType(null));
    }
}