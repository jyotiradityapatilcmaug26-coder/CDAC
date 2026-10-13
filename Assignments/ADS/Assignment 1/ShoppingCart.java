import java.util.ArrayList;

class CartItem {
    String name;
    int price;
    int qty;

    CartItem(String name, int price, int qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    int lineTotal() {
        return price * qty;
    }
}

public class ShoppingCart {
    private ArrayList<CartItem> items = new ArrayList<>();

    // Returns position of item ignoring case, or -1 if not in cart
    private int indexOf(String name) {
        if (name == null) return -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).name.equalsIgnoreCase(name.trim())) {
                return i;
            }
        }
        return -1;
    }

    // Adds item or increases quantity if already present
    public void add(String name, int price, int qty) {
        if (name == null || name.trim().isEmpty() || price < 0 || qty <= 0) {
            System.out.println("Error: Invalid item details");
            return;
        }

        int idx = indexOf(name);
        if (idx != -1) {
            CartItem existing = items.get(idx);
            existing.qty += qty;
            System.out.println("add(\"" + name + "\", " + price + ", " + qty + ") -> " + existing.name + " already in cart: quantity becomes " + existing.qty);
        } else {
            items.add(new CartItem(name, price, qty));
            System.out.println("add(\"" + name + "\", " + price + ", " + qty + ")");
        }
    }

    // Removes item from cart
    public void remove(String name) {
        int idx = indexOf(name);
        if (idx != -1) {
            items.remove(idx);
            System.out.print("remove(\"" + name + "\") -> ");
            printBill();
        } else {
            System.out.println("remove(\"" + name + "\") -> Item '" + name + "' not found in cart");
        }
    }

    // Updates quantity; removes item if qty <= 0
    public void updateQty(String name, int qty) {
        int idx = indexOf(name);
        if (idx == -1) {
            System.out.println("updateQty(\"" + name + "\", " + qty + ") -> Item '" + name + "' not found in cart");
            return;
        }

        if (qty <= 0) {
            items.remove(idx);
        } else {
            items.get(idx).qty = qty;
        }

        System.out.print("updateQty(\"" + name + "\", " + qty + ") -> ");
        printBill();
    }

    // Computes bill following discount & delivery rules
    public void printBill() {
        // Improved: Empty cart check based on items.isEmpty()
        if (items.isEmpty()) {
            System.out.println("Subtotal 0 | Discount 0 | Delivery 0 | Total 0");
            return;
        }

        int subtotal = 0;
        for (CartItem item : items) {
            subtotal += item.lineTotal();
        }

        // Rule 1: 10% discount if subtotal is Rs. 1000 or more (integer division)
        int discount = (subtotal >= 1000) ? (subtotal / 10) : 0;
        int amountAfterDiscount = subtotal - discount;

        // Rule 2: Rs. 40 delivery charge if amount after discount is below Rs. 500
        int delivery = (amountAfterDiscount < 500) ? 40 : 0;
        int total = amountAfterDiscount + delivery;

        System.out.println("Subtotal " + subtotal + " | Discount " + discount + " | Delivery " + delivery + " | Total " + total);
    }

    // Task 3: Returns the item with the highest line total
    public CartItem mostExpensiveLine() {
        if (items.isEmpty()) return null;
        CartItem best = items.get(0);
        for (CartItem item : items) {
            if (item.lineTotal() > best.lineTotal()) {
                best = item;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        ShoppingCart cart = new ShoppingCart();

        System.out.println("--- Running Example from PDF ---");
        cart.add("Pen", 20, 5);
        cart.add("Notebook", 60, 10);
        cart.add("Bag", 450, 1);
        cart.add("Pen", 20, 5);

        System.out.print("Bill: ");
        cart.printBill();

        cart.remove("Bag");
        cart.updateQty("Notebook", 2);

        System.out.println("\n--- Additional Checks & Edge Cases ---");
        CartItem expensive = cart.mostExpensiveLine();
        if (expensive != null) {
            System.out.println("Most expensive line: " + expensive.name + " (" + expensive.qty + " @ Rs." + expensive.price + " = Rs." + expensive.lineTotal() + ")");
        }

        cart.remove("Eraser");
        cart.updateQty("Pen", 0);
        cart.add("notebook", 60, 1);

        cart.remove("notebook");
        System.out.print("Empty cart: ");
        cart.printBill();
    }
}