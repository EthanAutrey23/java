import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Reservation {
    private static List<ReservationEntry> reservations = new ArrayList<>();
    private static DefaultTableModel tableModel;
    private static final int MAX_OCCUPANCY = 50;
    private static final int BASE_WAIT_TIME = 30;
    private static final int GROUP_WAIT_TIME_INCREMENT = 15;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createReservation();
            }
        });
    }

    public static void createReservation() {
        JFrame reservationFrame = new JFrame("Reservation Page");
        reservationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reservationFrame.setSize(800, 600);
        reservationFrame.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);

        JLabel dateLabel = new JLabel("Date:");
        JTextField dateField = new JTextField(10);

        JLabel timeLabel = new JLabel("Time:");
        JTextField timeField = new JTextField(10);

        JLabel contactLabel = new JLabel("Contact Number:");
        JTextField contactField = new JTextField(15);

        JLabel headCountLabel = new JLabel("Head Count:");
        JSpinner headCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, MAX_OCCUPANCY, 1));


        JButton submitButton = new JButton("Submit");

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(timeLabel);
        formPanel.add(timeField);
        formPanel.add(contactLabel);
        formPanel.add(contactField);
        formPanel.add(headCountLabel);
        formPanel.add(headCountSpinner);
        
        formPanel.add(new JLabel()); 
        formPanel.add(submitButton);

        tableModel = new DefaultTableModel(new String[] {"Name", "Date", "Time", "Contact", "Head Count", "Wait Time"}, 0) {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable reservationTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(reservationTable);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String date = dateField.getText();
                String time = timeField.getText();
                String contact = contactField.getText();
                int headCount = (int) headCountSpinner.getValue();
                int waitTime = calculateWaitTime(headCount);

                if (validateReservationInput(name, date, time, contact, headCount)) {
                    ReservationEntry reservation = new ReservationEntry(name, date, time, contact, headCount, waitTime);
                    reservations.add(reservation);

                    tableModel.addRow(new Object[]{name, date, time, contact, headCount, waitTime});

                    nameField.setText("");
                    dateField.setText("");
                    timeField.setText("");
                    contactField.setText("");
                    headCountSpinner.setValue(1);
                } else {
                    JOptionPane.showMessageDialog(reservationFrame, "Invalid reservation data. Please check your input.");
                }
            }
        });

        JPanel reservationListPanel = new JPanel(new BorderLayout());
        reservationListPanel.add(scrollPane, BorderLayout.CENTER);

        reservationFrame.add(formPanel, BorderLayout.WEST);
        reservationFrame.add(reservationListPanel, BorderLayout.CENTER);
        reservationFrame.setVisible(true);
    }

    private static boolean validateReservationInput(String name, String date, String time, String contact, int headCount) {
        return !name.isEmpty() && !date.isEmpty() && !time.isEmpty() && !contact.isEmpty() && headCount > 0;
    }

    private static int calculateWaitTime(int headCount) {
        int waitTime = BASE_WAIT_TIME;
        if (headCount > 3) {
            int additionalWaitTime = (headCount - 3) * GROUP_WAIT_TIME_INCREMENT;
            waitTime += additionalWaitTime;
            
            waitTime = Math.min(waitTime, 180);
        }
        return waitTime;
    }

static class ReservationEntry {
    private String name;
    private String date;
    private String time;
    private String contact;
    private int headCount;
    private int waitTime;

    public ReservationEntry(String name, String date, String time, String contact, int headCount, int waitTime) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.contact = contact;
        this.headCount = headCount;
        this.waitTime = waitTime;
    }
    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getContact() {
        return contact;
    }

    public int getHeadCount() {
        return headCount;
    }

    public int getWaitTime() {
        return waitTime;
    }
}
}