import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class CarRentalService {
    JFrame frame;
    CardLayout cardLayout;
    JPanel mainPanel;
    JTextField nameField, mobileField, addressField;
    String selectedCar = "";
    String selectedCarId = "";
    String selectedCarNumber = "";
    int pricePerDay = 0;
    int totalBill = 0;
    int rentalDays = 0;
    String paymentMethod = "";
    String upiId = "";
    public CarRentalService() {
        frame = new JFrame("Car Rental Service");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.add(welcomePage(), "welcome");
        mainPanel.add(registrationPage(), "register");
        mainPanel.add(carsPage(), "cars");
        mainPanel.add(durationPage(), "duration");
        mainPanel.add(billingPage(), "billing");
        mainPanel.add(finalPage(), "final");
        mainPanel.add(thankYouPage(), "thanks");

        frame.add(mainPanel);
        frame.setSize(750, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        // Start directly from Welcome page
        cardLayout.show(mainPanel, "welcome");
    }
    // Page 1 – Welcome
    private JPanel welcomePage() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(173, 216, 230));

        JLabel heading = new JLabel(" WELCOME TO CAR RENTAL SERVICE ", JLabel.CENTER);
        heading.setFont(new Font("Serif", Font.BOLD, 30));
        heading.setForeground(Color.BLACK);

        JLabel subHeading = new JLabel("Drive Your Dream Car Anytime, Anywhere!", JLabel.CENTER);
        subHeading.setFont(new Font("Arial", Font.ITALIC, 18));
        subHeading.setForeground(Color.DARK_GRAY);

        JPanel headingPanel = new JPanel(new GridLayout(2, 1));
        headingPanel.setOpaque(false);
        headingPanel.add(heading);
        headingPanel.add(subHeading);

        panel.add(headingPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonPanel.setOpaque(false);
        JButton rentBtn = new JButton("WANT TO RENT A CAR?");
        rentBtn.setBackground(new Color(255, 204, 102));

        JButton enterBtn = new JButton("➡ ENTER");
        enterBtn.setBackground(new Color(144, 238, 144));
        enterBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));

        // Only keep Rent + Enter
        buttonPanel.add(rentBtn);
        buttonPanel.add(enterBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    // Page 2 – Registration
    private JPanel registrationPage() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        panel.add(new JLabel("Enter Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Enter Mobile Number:"));
        mobileField = new JTextField();
        panel.add(mobileField);

        panel.add(new JLabel("Enter Address:"));
        addressField = new JTextField();
        panel.add(addressField);

        JButton backBtn = new JButton("⬅ BACK");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "welcome"));

        JButton nextBtn = new JButton("NEXT ➡");
        nextBtn.setBackground(Color.YELLOW);
        nextBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String mobile = mobileField.getText().trim();
            String address = addressField.getText().trim();
            if (name.isEmpty() || mobile.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "⚠ Please fill all fields!");
            } else if (!mobile.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(frame, "⚠ Mobile number must be exactly 10 digits!");
            } else {
                cardLayout.show(mainPanel, "cars");
            }
        });
        panel.add(backBtn);
        panel.add(nextBtn);
        return panel;
    }
    // Page 3 – Cars
    private JPanel carsPage() {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("CAR FOR PROVIDING SERVICE"));

        String[][] cars = {
                {"Honda City", "C001", "1234", "2000", "available"},
                {"Toyota Innova", "C002", "5678", "2500", "unavailable"},
                {"Swift Dzire", "C003", "1111", "1800", "available"},
                {"Hyundai i20", "C004", "2222", "1500", "unavailable"},
                {"Kia Seltos", "C005", "3333", "2200", "available"},
                {"Tata Nexon", "C006", "4444", "2100", "available"},
                {"Ford EcoSport", "C007", "5555", "2300", "unavailable"},
                {"BMW X1", "C008", "6666", "4000", "available"},
                {"Audi A4", "C009", "7777", "4500", "available"},
                {"Mercedes C", "C010", "8888", "5000", "available"}
        };
        for (String[] car : cars) {
            JButton carBtn = new JButton("<html>" + car[0] +
                    "<br>ID: " + car[1] +
                    "<br>No: " + car[2] +
                    "<br>₹" + car[3] + "/day</html>");

            if (car[4].equals("unavailable")) {
                carBtn.addActionListener(e -> JOptionPane.showMessageDialog(frame,
                        "Sorry, " + car[0] + " is not available at this time!"));
            } else {
                carBtn.addActionListener(e -> {
                    selectedCar = car[0];
                    selectedCarId = car[1];
                    selectedCarNumber = car[2];
                    pricePerDay = Integer.parseInt(car[3]);
                    cardLayout.show(mainPanel, "duration");
                });
            }
            panel.add(carBtn);
        }
        JButton backBtn = new JButton("BACK");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "register"));
        panel.add(backBtn);
        return panel;
    }
    // Page 4 – Duration
    private JPanel durationPage() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        JLabel info = new JLabel("Enter Number of Days to Rent: ", JLabel.CENTER);
        JTextField daysField = new JTextField();

        JButton backBtn = new JButton("BACK");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "cars"));

        JButton submitBtn = new JButton("SUBMIT");
        submitBtn.setBackground(Color.GREEN);
        submitBtn.addActionListener(e -> {
            try {
                rentalDays = Integer.parseInt(daysField.getText());
                totalBill = rentalDays * pricePerDay;
                cardLayout.show(mainPanel, "billing");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid number of days!");
            }
        });
        panel.add(info);
        panel.add(daysField);
        panel.add(backBtn);
        panel.add(submitBtn);
        return panel;
    }
    // Page 5 – Billing
    private JPanel billingPage() {
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
        JLabel billLabel = new JLabel("Your total will appear here.", JLabel.CENTER);

        JButton upiBtn = new JButton("Pay via UPI");
        upiBtn.addActionListener(e -> {
            String upi = JOptionPane.showInputDialog(frame, "Enter your UPI ID:");
            if (upi != null && !upi.isEmpty()) {
                upiId = upi;
                paymentMethod = "UPI (" + upi + ")";
                JOptionPane.showMessageDialog(frame, "Payment Successful via UPI!");
                cardLayout.show(mainPanel, "final");
            }
        });
        JButton cashBtn = new JButton("Pay via Cash");
        cashBtn.addActionListener(e -> {
            paymentMethod = "Cash";
            JOptionPane.showMessageDialog(frame, "Cash Payment Successful!");
            cardLayout.show(mainPanel, "final");
        });
        JButton backBtn = new JButton("BACK");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "duration"));

        panel.add(new JLabel("Billing Page", JLabel.CENTER));
        panel.add(billLabel);
        panel.add(upiBtn);
        panel.add(cashBtn);
        panel.add(backBtn);

        panel.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                billLabel.setText("You have to pay ₹" + totalBill + " for " + rentalDays + " days of " + selectedCar);
            }
        });
        return panel;
    }
    // Page 6 – Final Summary
    private JPanel finalPage() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Final Booking Summary", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        JTextArea details = new JTextArea();
        details.setFont(new Font("Monospaced", Font.PLAIN, 16));
        details.setEditable(false);
        details.setBackground(new Color(245, 245, 245));
        details.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        JButton backBtn = new JButton("BACK");
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "billing"));

        JButton finishBtn = new JButton("FINISH");
        finishBtn.setBackground(Color.GREEN);
        finishBtn.addActionListener(e -> cardLayout.show(mainPanel, "thanks"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backBtn);
        buttonPanel.add(finishBtn);

        panel.add(new JScrollPane(details), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        panel.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                details.setText(
                        "===== CUSTOMER DETAILS =====\n" +
                        "Name           : " + nameField.getText() + "\n" +
                        "Mobile         : " + mobileField.getText() + "\n" +
                        "Address        : " + addressField.getText() + "\n\n" +
                        "===== CAR DETAILS =====\n" +
                        "Car Name       : " + selectedCar + "\n" +
                        "Car ID         : " + selectedCarId + "\n" +
                        "Car Number     : " + selectedCarNumber + "\n" +
                        "Days Rented    : " + rentalDays + " days\n\n" +
                        "===== BILLING DETAILS =====\n" +
                        "Total Bill     : ₹" + totalBill + "\n" +
                        "Payment Method : " + paymentMethod + "\n" +
                        "Payment Status : Successful"
                );
            }
        });
        return panel;
    }
    // Page 7 – Thank You
    private JPanel thankYouPage() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("THANK YOU FOR RENTING… KEEP VISITING US!", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(CarRentalService::new);
    }
}
