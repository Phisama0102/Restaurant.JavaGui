import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RestaurantApp {
    private static JFrame frame;
    private static JPanel mainPanel, welcomePanel, menuPanel, checkoutPanel;
    private static JTextArea orderSummary;
    private static ArrayList<String> orders = new ArrayList<>();
    private static int totalPrice = 0;

    public static void main(String[] args) {
        frame = new JFrame("Restaurant App");
        frame.setSize(500, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the main background color
        Color backgroundColor = new Color(245, 222, 179); // Light beige theme

        // Panels
        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(backgroundColor); // Set background color
        createWelcomePanel(backgroundColor);
        createMenuPanel(backgroundColor);
        createCheckoutPanel(backgroundColor);

        frame.add(mainPanel);
        frame.setVisible(true);
        showWelcome();
    }

    private static void createWelcomePanel(Color backgroundColor) {
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBackground(backgroundColor); // Set background color

        JLabel welcomeLabel = new JLabel("Welcome to our Restaurant!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 50));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeLabel.setForeground(Color.BLACK); // Set text color to black

        JButton enterButton = new JButton("Enter to Menu");
        enterButton.setFont(new Font("Arial", Font.PLAIN, 36));
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.addActionListener(e -> showMenu());

        welcomePanel.add(Box.createVerticalGlue());
        welcomePanel.add(welcomeLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 20)));
        welcomePanel.add(enterButton);
        welcomePanel.add(Box.createVerticalGlue());

        mainPanel.add(welcomePanel, "Welcome");
    }

    private static void createMenuPanel(Color backgroundColor) {
        menuPanel = new JPanel(new BorderLayout());
        menuPanel.setBackground(backgroundColor);

        JLabel menuLabel = new JLabel("Menu:");
        menuLabel.setFont(new Font("Arial", Font.BOLD, 26));
        menuLabel.setHorizontalAlignment(SwingConstants.CENTER);
        menuLabel.setForeground(Color.BLACK);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        buttonPanel.setBackground(backgroundColor);

        String[] menuItems = {
                "1. Fried Rice - 50 B",
                "2. Fried Chicken - 40 B",
                "3. Orange Juice - 30 B",
                "4. Steak - 60 B",
                "5. Chicken Rice - 45 B",
                "6. Fish Sauce Fried Chicken - 50 B",
                "7. Chicken Noodle Soup - 60 B",
                "8. Shaved Ice - 30 B",
                "9. Coconut Water - 30 B"
        };
        int[] prices = {50, 40, 30, 60, 45, 50, 60, 30, 30};

        for (int i = 0; i < menuItems.length; i++) {
            int index = i;
            JButton menuButton = new JButton(menuItems[i]);
            menuButton.setFont(new Font("Arial", Font.PLAIN, 20));
            menuButton.setBackground(new Color(153, 101, 21)); // Warm brown color
            menuButton.setForeground(Color.WHITE);
            menuButton.addActionListener(e -> addOrder(menuItems[index], prices[index]));
            buttonPanel.add(menuButton);
        }

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setFont(new Font("Arial", Font.PLAIN, 20));
        checkoutButton.setBackground(new Color(40, 167, 69)); // Green for checkout
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.addActionListener(e -> showCheckout());
        buttonPanel.add(checkoutButton);

        JButton cancelButton = new JButton("Cancel Order");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 20));
        cancelButton.setBackground(new Color(220, 53, 69)); // Red for cancel
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> cancelOrder());
        buttonPanel.add(cancelButton);

        menuPanel.add(menuLabel, BorderLayout.NORTH);
        menuPanel.add(buttonPanel, BorderLayout.CENTER);

        mainPanel.add(menuPanel, "Menu");
    }

    private static void createCheckoutPanel(Color backgroundColor) {
        checkoutPanel = new JPanel(new BorderLayout());
        checkoutPanel.setBackground(backgroundColor);

        // Title for the order summary
        JLabel orderLabel = new JLabel("Ordered Items:");
        orderLabel.setFont(new Font("Arial", Font.BOLD, 36)); // 00
        orderLabel.setForeground(Color.BLACK);
        orderLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Order summary area
        orderSummary = new JTextArea(10, 20);
        orderSummary.setEditable(false);
        orderSummary.setFont(new Font("Arial", Font.PLAIN, 30));
        orderSummary.setForeground(Color.BLACK);
        orderSummary.setBackground(new Color(255, 255, 240)); // Light ivory color for readability
        JScrollPane scrollPane = new JScrollPane(orderSummary);

        // Panel for the total price and payment input
        JPanel paymentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        paymentPanel.setBackground(backgroundColor);

        JLabel totalPriceLabel = new JLabel("Total Price:");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 36));
        totalPriceLabel.setForeground(Color.BLACK);

        JLabel paymentLabel = new JLabel("Enter payment: ");
        paymentLabel.setFont(new Font("Arial", Font.BOLD, 36));
        paymentLabel.setForeground(Color.BLACK);

        JTextField paymentField = new JTextField(10);
        paymentField.setFont(new Font("Arial", Font.PLAIN, 20));

        JButton payButton = new JButton("Pay");
        payButton.setFont(new Font("Arial", Font.PLAIN, 30));
        payButton.setBackground(new Color(0, 123, 255));
        payButton.setForeground(Color.WHITE);
        payButton.addActionListener(e -> calculateChange(paymentField.getText()));

        paymentPanel.add(totalPriceLabel);
        paymentPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Spacer
        paymentPanel.add(paymentLabel);
        paymentPanel.add(paymentField);
        paymentPanel.add(payButton);

        // Adding components to the checkout panel
        checkoutPanel.add(orderLabel, BorderLayout.NORTH);
        checkoutPanel.add(scrollPane, BorderLayout.CENTER);
        checkoutPanel.add(paymentPanel, BorderLayout.SOUTH);

        mainPanel.add(checkoutPanel, "Checkout");
    }

    private static void showWelcome() {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "Welcome");
    }

    private static void showMenu() {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "Menu");
    }

    private static void addOrder(String item, int price) {
        orders.add(item);
        totalPrice += price;
        JOptionPane.showMessageDialog(frame, item + " added to order!");
    }

    private static void showCheckout() {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, "Checkout");

        StringBuilder summary = new StringBuilder("Ordered Items:\n");
        for (String order : orders) {
            summary.append("- ").append(order).append("\n");
        }
        summary.append("\nTotal Price: ").append(totalPrice).append(" B\n");
        orderSummary.setText(summary.toString());
    }

    private static void cancelOrder() {
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No items to cancel.");
            return;
        }

        String[] orderArray = orders.toArray(new String[0]);
        String itemToCancel = (String) JOptionPane.showInputDialog(
                frame,
                "Select item to cancel:",
                "Cancel Order",
                JOptionPane.QUESTION_MESSAGE,
                null,
                orderArray,
                orderArray[0]
        );

        if (itemToCancel != null) {
            int index = orders.indexOf(itemToCancel);
            String[] splitItem = itemToCancel.split(" - ");
            int price = Integer.parseInt(splitItem[1].replace(" B", ""));
            orders.remove(index);
            totalPrice -= price;
            JOptionPane.showMessageDialog(frame, itemToCancel + " has been canceled.");
        }
    }

    private static void calculateChange(String paymentInput) {
        try {
            int payment = Integer.parseInt(paymentInput);
            if (payment < totalPrice) {
                JOptionPane.showMessageDialog(frame, "Insufficient amount!");
            } else {
                int change = payment - totalPrice;
                JOptionPane.showMessageDialog(frame, "Payment: " + payment + " B\nTotal: " + totalPrice + " B\nChange: " + change + " B");
                resetOrder();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid input. Please enter a valid number.");
        }
    }

    private static void resetOrder() {
        orders.clear();
        totalPrice = 0;
        showMenu();
    }
}
