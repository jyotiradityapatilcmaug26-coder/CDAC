// Custom Node for BST
class BookNode {
    int id;
    String title;
    BookNode left;
    BookNode right;

    BookNode(int id, String title) {
        this.id = id;
        this.title = title;
        this.left = null;
        this.right = null;
    }
}

// Custom Queue Node for Level-Order Traversal (per Condition: using Queue from Question 5)
class QueueNode {
    BookNode data;
    QueueNode next;

    QueueNode(BookNode data) {
        this.data = data;
        this.next = null;
    }
}

// Singly-linked list Queue of BookNodes based on Question 5
class BookQueue {
    private QueueNode front;
    private QueueNode rear;
    private int size;

    public void enqueue(BookNode node) {
        if (node == null) return;
        QueueNode qn = new QueueNode(node);
        if (isEmpty()) {
            front = rear = qn;
        } else {
            rear.next = qn;
            rear = qn;
        }
        size++;
    }

    public BookNode dequeue() {
        if (isEmpty()) return null;
        BookNode val = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return val;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }
}

public class Catalogue {
    private BookNode root;

    public BookNode getRoot() {
        return root;
    }

    // Public insert method with positive ID and non-empty title validation
    public void insert(int id, String title) {
        if (id <= 0) {
            System.out.println("Error: Book ID must be positive, got: " + id);
            return;
        }
        if (title == null || title.trim().isEmpty()) {
            System.out.println("Error: Book title cannot be null or empty.");
            return;
        }
        root = insert(root, id, title.trim());
    }

    // Recursive helper for insert
    private BookNode insert(BookNode node, int id, String title) {
        if (node == null) {
            return new BookNode(id, title);
        }
        if (id < node.id) {
            node.left = insert(node.left, id, title);
        } else if (id > node.id) {
            node.right = insert(node.right, id, title);
        } else {
            System.out.println("Duplicate Book ID: " + id);
        }
        return node;
    }

    // Iterative search that cleanly tracks path and handles missing child indicators
    public BookNode search(int id) {
        if (root == null) {
            System.out.println("search(" + id + ") -> Not found. Catalogue is empty.");
            return null;
        }

        StringBuilder path = new StringBuilder();
        BookNode curr = root;

        while (curr != null) {
            path.append(curr.id);
            if (id == curr.id) {
                System.out.println("search(" + id + ") -> Found. Path: " + path.toString());
                return curr;
            } else if (id < curr.id) {
                if (curr.left == null) {
                    System.out.println("search(" + id + ") -> Not found. Path: " + path.toString() + " -> (no left child)");
                    return null;
                }
                path.append(" -> ");
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    System.out.println("search(" + id + ") -> Not found. Path: " + path.toString() + " -> (no right child)");
                    return null;
                }
                path.append(" -> ");
                curr = curr.right;
            }
        }
        return null;
    }

    // Displays the book title along with the search result
    public void displayBook(int id) {
        BookNode book = search(id);
        if (book != null) {
            System.out.println("  [Book Details] ID: " + book.id + ", Title: \"" + book.title + "\"");
        }
    }

    // Inorder traversal: Left -> Node -> Right (outputs in sorted key order)
    public void inorder(BookNode n) {
        if (n == null) return;
        inorder(n.left);
        System.out.print(n.id + " ");
        inorder(n.right);
    }

    // Preorder traversal: Node -> Left -> Right
    public void preorder(BookNode n) {
        if (n == null) return;
        System.out.print(n.id + " ");
        preorder(n.left);
        preorder(n.right);
    }

    // Postorder traversal: Left -> Right -> Node
    public void postorder(BookNode n) {
        if (n == null) return;
        postorder(n.left);
        postorder(n.right);
        System.out.print(n.id + " ");
    }

    // Level-order traversal formatted with '|' between levels
    public void levelOrder() {
        if (root == null) return;
        BookQueue queue = new BookQueue();
        queue.enqueue(root);

        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                BookNode curr = queue.dequeue();
                sb.append(curr.id).append(" ");
                if (curr.left != null) queue.enqueue(curr.left);
                if (curr.right != null) queue.enqueue(curr.right);
            }
            if (!queue.isEmpty()) {
                sb.append("| ");
            }
        }
        System.out.println(sb.toString().trim());
    }

    // Counts total number of nodes in tree
    public int count(BookNode n) {
        if (n == null) return 0;
        return 1 + count(n.left) + count(n.right);
    }

    // Height counting edges: -1 for null, 0 for single leaf node
    public int height(BookNode n) {
        if (n == null) return -1;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    // Minimum key (leftmost node) with empty catalogue reporting
    public int min() {
        if (root == null) {
            System.out.println("Catalogue is empty: no minimum ID.");
            return -1;
        }
        BookNode curr = root;
        while (curr.left != null) {
            curr = curr.left;
        }
        return curr.id;
    }

    // Maximum key (rightmost node) with empty catalogue reporting
    public int max() {
        if (root == null) {
            System.out.println("Catalogue is empty: no maximum ID.");
            return -1;
        }
        BookNode curr = root;
        while (curr.right != null) {
            curr = curr.right;
        }
        return curr.id;
    }

    public static void main(String[] args) {
        Catalogue cat = new Catalogue();

        // 1. Insert Book IDs in exact example order: 105, 62, 148, 40, 87, 120, 173, 95
        int[] ids = {105, 62, 148, 40, 87, 120, 173, 95};
        for (int id : ids) {
            cat.insert(id, "Title_" + id);
        }

        // 2. Traversals matching exact PDF format
        System.out.print("Inorder (catalogue) : ");
        cat.inorder(cat.root);
        System.out.println();

        System.out.print("Preorder            : ");
        cat.preorder(cat.root);
        System.out.println();

        System.out.print("Postorder           : ");
        cat.postorder(cat.root);
        System.out.println();

        System.out.print("Level order         : ");
        cat.levelOrder();

        System.out.println();

        // 3. Search Paths and Book Details
        cat.search(95);
        cat.search(100);
        cat.displayBook(95);

        // 4. Metrics
        System.out.println("min = " + cat.min() + ", max = " + cat.max() +
                           ", count = " + cat.count(cat.root) +
                           ", height = " + cat.height(cat.root) + " (counting edges)");

        // 5. Task 3: Inserting in sorted order
        System.out.println("\n--- Task 3: Tree Built from Sorted IDs ---");
        Catalogue sortedCat = new Catalogue();
        int[] sortedIds = {40, 62, 87, 95, 105, 120, 148, 173};
        for (int id : sortedIds) {
            sortedCat.insert(id, "Title_" + id);
        }
        System.out.println("Sorted IDs tree height = " + sortedCat.height(sortedCat.root) + " (counting edges)");
        System.out.println("Observation: The tree has degenerated into a linear linked list (skewed right).");

        // 6. Testing Input Validations & Edge Cases
        System.out.println("\n--- Testing Validations & Edge Cases ---");
        cat.insert(105, "Duplicate Book"); // Duplicate ID rejection
        cat.insert(-10, "Negative ID");     // Negative ID rejection
        cat.insert(200, "   ");            // Blank title rejection

        Catalogue emptyCat = new Catalogue();
        emptyCat.min();
        emptyCat.max();
        emptyCat.search(50);

        // 7. Testing BookQueue FIFO Order & Size Integrity
        System.out.println("\n--- Testing BookQueue FIFO & Size ---");
        BookQueue bq = new BookQueue();
        bq.enqueue(new BookNode(1, "Book 1"));
        bq.enqueue(new BookNode(2, "Book 2"));
        bq.enqueue(new BookNode(3, "Book 3"));
        System.out.println("Queue size after 3 enqueues: " + bq.size());
        System.out.println("Dequeued: ID " + bq.dequeue().id); // Should be 1
        System.out.println("Dequeued: ID " + bq.dequeue().id); // Should be 2
        System.out.println("Queue size after 2 dequeues: " + bq.size());
        bq.enqueue(new BookNode(4, "Book 4"));
        System.out.println("Dequeued: ID " + bq.dequeue().id); // Should be 3
        System.out.println("Dequeued: ID " + bq.dequeue().id); // Should be 4
        System.out.println("Is queue empty? " + bq.isEmpty());
    }
}