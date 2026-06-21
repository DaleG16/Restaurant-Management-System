
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class MenuItem {
    private String name;
    private int price;

    public MenuItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name + " - ₹" + price;
    }
}

public class RestaurantManagementSystem extends JFrame implements ActionListener {

    private JLabel title;
    private JButton orderButton, viewMenuButton, exitButton;

    private static final int MAIN_COURSE_END = 5;
    private static final int APPETIZER_END = 9;

    private MenuItem[] menu = {
        new MenuItem("Chicken Biriyani", 150),
        new MenuItem("Mutton Biriyani", 220),
        new MenuItem("Paneer Tikka Makhni", 185),
        new MenuItem("Veg Fried Rice", 95),
        new MenuItem("Cheese Burger", 110),

        new MenuItem("Fruit Salad", 65),
        new MenuItem("Nuggets (4 pc)", 40),
        new MenuItem("Veg Sandwich", 50),
        new MenuItem("French Fries", 60),

        new MenuItem("Tea", 25),
        new MenuItem("Coffee", 25),
        new MenuItem("Pepsi", 45),
        new MenuItem("Strawberry Milkshake", 55)
    };

    public RestaurantManagementSystem() {

        title = new JLabel("Welcome to DELIGHT Restaurant", JLabel.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 36));
        title.setForeground(Color.WHITE);

        orderButton = new JButton("Place an Order");
        viewMenuButton = new JButton("View Menu");
        exitButton = new JButton("Exit");

        orderButton.setFont(new Font("Arial", Font.PLAIN, 20));
        viewMenuButton.setFont(new Font("Arial", Font.PLAIN, 20));
        exitButton.setFont(new Font("Arial", Font.PLAIN, 20));

        orderButton.setBackground(Color.BLUE);
        orderButton.setForeground(Color.WHITE);

        viewMenuButton.setBackground(new Color(128, 0, 128));
        viewMenuButton.setForeground(Color.WHITE);

        exitButton.setBackground(Color.RED);
        exitButton.setForeground(Color.WHITE);

        orderButton.addActionListener(this);
        viewMenuButton.addActionListener(this);
        exitButton.addActionListener(this);

        setLayout(new FlowLayout());

        add(title);
        add(orderButton);
        add(viewMenuButton);
        add(exitButton);

        getContentPane().setBackground(Color.DARK_GRAY);

        setTitle("Restaurant Management System");
        setSize(650, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == orderButton) {
            placeOrder();
        }
        else if (e.getSource() == viewMenuButton) {
            displayMenu();
        }
        else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    private void displayMenu() {

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(menu.length + 3, 1));

        JLabel mainCourse = new JLabel("MAIN COURSE", JLabel.CENTER);
        mainCourse.setFont(new Font("Arial", Font.BOLD, 24));
        mainCourse.setForeground(Color.RED);

        menuPanel.add(mainCourse);

        for (int i = 0; i < MAIN_COURSE_END; i++) {
            menuPanel.add(new JLabel(menu[i].toString(), JLabel.CENTER));
        }

        JLabel appetizers = new JLabel("APPETIZERS", JLabel.CENTER);
        appetizers.setFont(new Font("Arial", Font.BOLD, 24));
        appetizers.setForeground(new Color(128, 0, 128));

        menuPanel.add(appetizers);

        for (int i = MAIN_COURSE_END; i < APPETIZER_END; i++) {
            menuPanel.add(new JLabel(menu[i].toString(), JLabel.CENTER));
        }

        JLabel beverages = new JLabel("BEVERAGES", JLabel.CENTER);
        beverages.setFont(new Font("Arial", Font.BOLD, 24));
        beverages.setForeground(Color.BLUE);

        menuPanel.add(beverages);

        for (int i = APPETIZER_END; i < menu.length; i++) {
            menuPanel.add(new JLabel(menu[i].toString(), JLabel.CENTER));
        }

        JOptionPane.showMessageDialog(
                this,
                new JScrollPane(menuPanel),
                "Restaurant Menu",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private void placeOrder() {

        JCheckBox[] checkBoxes = new JCheckBox[menu.length];
        JTextField[] quantityFields = new JTextField[menu.length];

        JPanel panel = new JPanel(new GridLayout(menu.length, 2));

        for (int i = 0; i < menu.length; i++) {

            checkBoxes[i] = new JCheckBox(menu[i].toString());
            quantityFields[i] = new JTextField("1");

            panel.add(checkBoxes[i]);
            panel.add(quantityFields[i]);
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                panel,
                "Place Order",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {

            int total = 0;
            StringBuilder summary = new StringBuilder();

            summary.append("ORDER SUMMARY\n\n");

            for (int i = 0; i < menu.length; i++) {

                if (checkBoxes[i].isSelected()) {

                    try {

                        int quantity =
                                Integer.parseInt(quantityFields[i].getText());

                        if (quantity <= 0) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Quantity must be greater than 0 for "
                                            + menu[i].getName()
                            );

                            return;
                        }

                        int itemTotal =
                                menu[i].getPrice() * quantity;

                        total += itemTotal;

                        summary.append(menu[i].getName())
                               .append(" x ")
                               .append(quantity)
                               .append(" = ₹")
                               .append(itemTotal)
                               .append("\n");

                    }
                    catch (NumberFormatException ex) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Invalid quantity entered!"
                        );

                        return;
                    }
                }
            }

            summary.append("\nTotal Bill = ₹").append(total);

            JTextArea output =
                    new JTextArea(summary.toString());

            output.setEditable(false);
            output.setFont(new Font("Arial", Font.PLAIN, 18));

            JOptionPane.showMessageDialog(
                    this,
                    new JScrollPane(output),
                    "Bill",
                    JOptionPane.INFORMATION_MESSAGE
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Thank You! Visit Again 😊"
            );
        }
    }

    public static void main(String[] args) {
        new RestaurantManagementSystem();
    }
}

