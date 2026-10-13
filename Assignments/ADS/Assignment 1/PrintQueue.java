class Job {
    String id;
    String owner;
    int pages;

    Job(String id, String owner, int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("Page count must be positive, got: " + pages);
        }
        this.id = id;
        this.owner = owner;
        this.pages = pages;
    }
}

class Node {
    Job job;
    Node next;

    Node(Job j) {
        this.job = j;
    }
}

public class PrintQueue {
    private Node front;
    private Node rear;
    private int size;

    // Enqueues a new job at the rear in O(1) time
    public void enqueue(Job j) {
        if (j == null) {
            System.out.println("Error: Cannot enqueue a null job.");
            return;
        }
        Node newNode = new Node(j);
        if (isEmpty()) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // Dequeues the job at the front in O(1) time
    public Job dequeue() {
        if (isEmpty()) {
            return null;
        }
        Job removed = front.job;
        front = front.next;
        if (front == null) {
            rear = null; // Queue is now empty
        }
        size--;
        return removed;
    }

    public Job peek() {
        return isEmpty() ? null : front.job;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    // Cancels a waiting job by ID from anywhere in the queue
    public boolean cancel(String id) {
        if (isEmpty() || id == null) {
            return false;
        }

        // Case 1: Cancelling the front node
        if (front.job.id != null && front.job.id.equals(id)) {
            front = front.next;
            if (front == null) {
                rear = null; // The only job was removed
            }
            size--;
            return true;
        }

        // Case 2: Cancelling a middle or rear node
        Node prev = front;
        while (prev.next != null && (prev.next.job.id == null || !prev.next.job.id.equals(id))) {
            prev = prev.next;
        }

        if (prev.next == null) {
            return false; // Job ID not found
        }

        // If cancelling the last node, update rear pointer to prev
        if (prev.next == rear) {
            rear = prev;
        }
        prev.next = prev.next.next;
        size--;
        return true;
    }

    // Walks from front to rear without modifying the queue
    public void printSchedule() {
        int time = 0;
        Node temp = front;
        while (temp != null) {
            time += temp.job.pages * 6; // 6 seconds per page
            System.out.println(" " + temp.job.id + " finishes at " + time + " s");
            temp = temp.next;
        }
    }

    public static void main(String[] args) {
        PrintQueue queue = new PrintQueue();

        System.out.println("--- Running Example from PDF ---");
        // All jobs arrive at time 0
        queue.enqueue(new Job("J1", "Ravi", 25));
        queue.enqueue(new Job("J2", "Asha", 10));
        queue.enqueue(new Job("J3", "Karan", 5));

        System.out.println("Schedule without cancellation:");
        queue.printSchedule();

        System.out.println("\nAfter cancel(\"J2\"):");
        queue.cancel("J2");
        queue.printSchedule();

        System.out.println("\n--- Additional Edge Case Testing ---");
        // Cancelling last job (J3)
        System.out.println("Cancelling last job (J3): " + queue.cancel("J3"));
        queue.printSchedule();

        // Cancelling non-existent job ID
        System.out.println("Cancelling non-existent job (J99): " + queue.cancel("J99"));

        // Cancelling the only remaining job (J1)
        System.out.println("Cancelling the only remaining job (J1): " + queue.cancel("J1"));
        System.out.println("Is queue empty? " + queue.isEmpty());

        // Attempting to enqueue null
        queue.enqueue(null);

        // Attempting invalid page count
        try {
            new Job("J4", "Invalid", 0);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected validation error: " + e.getMessage());
        }
    }
}