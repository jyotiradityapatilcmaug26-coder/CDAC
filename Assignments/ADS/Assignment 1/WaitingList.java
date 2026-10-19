class Patient {
    String name;
    Patient prev;
    Patient next;

    Patient(String n) {
        this.name = n;
    }
}

public class WaitingList {
    private Patient head;
    private Patient tail;
    private int size;

    // Checks whether a patient with the given name already exists in the list (case-insensitive)
    private boolean exists(String name) {
        if (name == null) return false;
        Patient curr = head;
        while (curr != null) {
            if (curr.name.equalsIgnoreCase(name)) {
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    // Normal patients join at the tail of the list
    public void addNormal(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Patient name cannot be empty.");
            return;
        }
        name = name.trim();

        if (exists(name)) {
            System.out.println("Error: Patient '" + name + "' is already on the waiting list.");
            return;
        }

        Patient p = new Patient(name);
        if (head == null) {
            head = tail = p;
        } else {
            tail.next = p;
            p.prev = tail;
            tail = p;
        }
        size++;
    }

    // Emergency patients are placed at the front (head) of the list
    public void addEmergency(String name) {
        if (name == null || name.trim().isEmpty()) {
            System.out.println("Error: Patient name cannot be empty.");
            return;
        }
        name = name.trim();

        if (exists(name)) {
            System.out.println("Error: Patient '" + name + "' is already on the waiting list.");
            return;
        }

        Patient p = new Patient(name);
        if (head == null) {
            head = tail = p;
        } else {
            p.next = head;
            head.prev = p;
            head = p;
        }
        size++;
    }

    // Unlinks a node from the doubly linked list safely
    private void unlink(Patient p) {
        if (p == null) return;

        if (p.prev != null) {
            p.prev.next = p.next;
        } else {
            head = p.next; // p was head
        }

        if (p.next != null) {
            p.next.prev = p.prev;
        } else {
            tail = p.prev; // p was tail
        }

        p.prev = null;
        p.next = null;
        size--;
    }

    // Doctor calls the next patient from the front (head)
    public String callNext() {
        if (head == null) {
            return "No patients waiting";
        }
        String patientName = head.name;
        unlink(head);
        return patientName + ", please go to the doctor";
    }

    // Patient leaves from anywhere in the line
    public boolean leave(String name) {
        if (name == null || head == null) {
            return false;
        }
        name = name.trim();

        Patient curr = head;
        while (curr != null) {
            if (curr.name.equalsIgnoreCase(name)) {
                unlink(curr);
                return true;
            }
            curr = curr.next;
        }
        return false;
    }

    // Returns total count of waiting patients
    public int waiting() {
        return size;
    }

    // Displays the list from front (head) to back (tail)
    public void display() {
        if (head == null) {
            System.out.println("display()        -> [Waiting list is empty]");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Patient curr = head;
        while (curr != null) {
            sb.append(curr.name);
            if (curr.next != null) sb.append(", ");
            curr = curr.next;
        }
        System.out.println("display()        -> " + sb.toString());
    }

    // Displays the list in reverse from back (tail) to front (head) using prev links
    public void displayReverse() {
        if (tail == null) {
            System.out.println("displayReverse() -> [Waiting list is empty]");
            return;
        }
        StringBuilder sb = new StringBuilder();
        Patient curr = tail;
        while (curr != null) {
            sb.append(curr.name);
            if (curr.prev != null) sb.append(", ");
            curr = curr.prev;
        }
        System.out.println("displayReverse() -> " + sb.toString());
    }

    public static void main(String[] args) {
        WaitingList wl = new WaitingList();

        System.out.println("--- Running Example from PDF ---");
        wl.addNormal("Riya");
        wl.addNormal("Sam");
        wl.addEmergency("Tom");
        wl.addNormal("Uma");

        // list: Tom, Riya, Sam, Uma
        System.out.print("list: ");
        Patient curr = wl.head;
        while (curr != null) {
            System.out.print(curr.name + (curr.next != null ? ", " : ""));
            curr = curr.next;
        }
        System.out.println();

        System.out.println("callNext() -> \"" + wl.callNext() + "\"");

        wl.leave("Sam");
        System.out.print("leave(\"Sam\") -> list: ");
        curr = wl.head;
        while (curr != null) {
            System.out.print(curr.name + (curr.next != null ? ", " : ""));
            curr = curr.next;
        }
        System.out.println();

        wl.addEmergency("Vik");

        wl.display();
        wl.displayReverse();
        System.out.println("waiting() -> " + wl.waiting());

        System.out.println("\n--- Testing Requested Edge Cases & Improvements ---");
        // 1. Duplicate check with extra spaces and case difference
        wl.addNormal("  vik  ");
        wl.addEmergency("RIYA");

        // 2. Attempt to remove non-existent patient
        boolean removedNonExistent = wl.leave("Ghost");
        System.out.println("leave(\"Ghost\") -> " + removedNonExistent + " (Not found)");

        // 3. Emptying the list completely
        wl.leave("Vik");
        wl.leave("Riya");
        wl.leave("Uma");
        System.out.println("\nAfter emptying all patients:");
        wl.display();
        wl.displayReverse();

        // 4. Repeated calls to callNext() on empty list
        System.out.println("callNext() #1 on empty: " + wl.callNext());
        System.out.println("callNext() #2 on empty: " + wl.callNext());

        // 5. Adding a patient after list becomes empty
        System.out.println("\nAdding new patient 'Arun' after list was empty:");
        wl.addNormal("Arun");
        wl.display();
        wl.displayReverse();
        System.out.println("waiting() -> " + wl.waiting());
    }
}