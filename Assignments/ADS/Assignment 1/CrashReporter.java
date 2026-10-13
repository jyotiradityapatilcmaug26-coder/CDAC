class MethodStack {
    private String[] data = new String[10];
    private int top = -1; // -1 means the stack is empty

    boolean push(String m) {
        if (top == data.length - 1) {
            return false; // Stack is full (capacity 10 reached)
        }
        data[++top] = m;
        return true;
    }

    String pop() {
        if (isEmpty()) {
            return null;
        }
        return data[top--];
    }

    String peek() {
        if (isEmpty()) {
            return null;
        }
        return data[top];
    }

    boolean isEmpty() {
        return top == -1;
    }

    int size() {
        return top + 1;
    }

    void printTrace() {
        // Print from top down to index 0 (most recent call first)
        for (int i = top; i >= 0; i--) {
            System.out.println(" at " + data[i]);
        }
    }
}

public class CrashReporter {

    public static void runLog(String[] log) {
        MethodStack stack = new MethodStack();
        int maxDepth = 0;

        for (int i = 0; i < log.length; i++) {
            int lineNo = i + 1;
            String line = log[i].trim();

            // Safely skip blank lines
            if (line.isEmpty()) {
                continue;
            }

            // Split by one or more whitespace characters
            String[] parts = line.split("\\s+");
            String command = parts[0];

            if (command.equals("ENTER")) {
                String method = parts.length > 1 ? parts[1] : "";
                boolean success = stack.push(method);
                if (!success) {
                    System.out.println("StackOverflowError: call depth exceeded 10");
                    return;
                }
                if (stack.size() > maxDepth) {
                    maxDepth = stack.size();
                }
            } else if (command.equals("EXIT")) {
                String method = parts.length > 1 ? parts[1] : "";
                if (stack.isEmpty() || !method.equals(stack.peek())) {
                    System.out.println("Invalid log at line " + lineNo + ": EXIT " + method);
                    return;
                }
                stack.pop();
            } else if (command.equals("CRASH")) {
                System.out.println("Application crashed at line " + lineNo + ". Stack trace:");
                stack.printTrace();
                System.out.println("Maximum call depth reached: " + maxDepth);
                return;
            }
        }

        // Reached only if no CRASH encountered
        System.out.println("Program finished normally");
    }

    public static void main(String[] args) {
        // Example from PDF
        String[] log = {
            "ENTER main",
            "ENTER placeOrder",
            "ENTER validateCart",
            "EXIT validateCart",
            "ENTER processPayment",
            "ENTER connectBank",
            "CRASH"
        };

        runLog(log);
    }
}