import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.SyncFailedException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

public class App extends JFrame {
    private JPanel pnlMain;
    private JRadioButton rbCustomer;
    private JRadioButton rbClerk;
    private JRadioButton rbManager;
    private JTextField tfName;
    private JTextArea taPersons;
    private JButton btnSave;
    private JTextField tfAge;
    private JTextField tfMonths;
    private JTextField tfSalary;
    private JButton btnClear;
    private JTextField tfLoad;
    private JButton btnLoad;
    private JButton btnSayHi;
    private JButton btnSavePerson;
    private JButton btnLoadPerson;
    private JButton btnReward;

    private List<Person> persons;

    private void displayError(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    private void clearInputs() {
        tfName.setText("");
        tfAge.setText("");
        tfMonths.setText("");
        tfSalary.setText("");
    }

    private boolean flag = true;
    public App() {
        persons = new ArrayList<>();
        // TODO add implementations for all milestones here

        taPersons.setEditable(false);
        tfName.setEditable(false);
        tfAge.setEditable(false);
        tfMonths.setEditable(false);
        tfSalary.setEditable(false);

        ButtonGroup group = new ButtonGroup();
        group.add(rbCustomer);
        group.add(rbClerk);
        group.add(rbManager);

        btnReward.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = Integer.parseInt(tfLoad.getText()) - 1;
                    Person p = persons.get(index);

                    if (!(p instanceof Employee)) throw new Exception();

                    JOptionPane.showMessageDialog(null, String.format("%dth Employee (%s)'s thirteenth month: %.2f",
                            index + 1, p.getName(), ((Employee)p).thirteenthmonth()) );

                } catch (Exception exc) {
                    displayError("Enter a valid index!");
                }
            }
        });

        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int index = Integer.parseInt(tfLoad.getText()) - 1;
                    Person p = persons.get(index);
                    
                    tfName.setText(p.getName());
                    tfAge.setText(p.getAge() + "");
                    
                    if (!(p instanceof Customer)) {
                        tfSalary.setText( ((Employee)p).getSalary() + "");
                        tfMonths.setText( ((Employee)p).getMonthsWorked() + "");
                        if (p instanceof Clerk) {
                            rbClerk.setSelected(true);
                        }
                        if (p instanceof Manager) {
                            rbManager.setSelected(true);
                        }
                        tfSalary.setEditable(true);
                        tfMonths.setEditable(true);
                    } else {
                        rbCustomer.setSelected(true);
                        tfSalary.setEditable(false);
                        tfMonths.setEditable(false);
                        tfSalary.setText("");
                        tfMonths.setText("");
                    }

                } catch (Exception exc) {
                    displayError("Enter a valid index!");
                }
            }
        });
        
        
        rbCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfName.setEditable(true);
                tfAge.setEditable(true);
                tfSalary.setEditable(false);
                tfMonths.setEditable(false);
                tfSalary.setText("");
                tfMonths.setText("");
                flag = false;
            }
        });
        rbClerk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfSalary.setEditable(true);
                tfMonths.setEditable(true);
                tfName.setEditable(true);
                tfAge.setEditable(true);
                flag = true;
            }
        });
        rbCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tfSalary.setEditable(true);
                tfMonths.setEditable(true);
                tfName.setEditable(true);
                tfAge.setEditable(true);
                flag = true;
            }
        });

        btnSave.addActionListener(new ActionListener() {
            final JRadioButton[] buttons = {rbClerk, rbCustomer, rbManager};
            Person p = null;
            @Override
            public void actionPerformed(ActionEvent e) {
                String classType = "";
                for (JRadioButton btn: buttons) {
                    if (btn.isSelected()) {
                        classType = btn.getText();
                    }
                }
                try {
                    String name = tfName.getText();
                    int age = Integer.parseInt(tfAge.getText());
                    int monthsWorked = 0;
                    double salary = 0;
                    if (flag) {
                        monthsWorked = Integer.parseInt(tfMonths.getText());
                        salary = Double.parseDouble(tfSalary.getText());
                    }

                    switch (classType) {
                        case "Clerk":
                            p = new Clerk(name, age, monthsWorked, salary);
                            persons.add(p);
                            int pos = persons.indexOf(p) + 1;
                            taPersons.append(String.format("%d. Clerk - %s (%d)\n", pos, name, age));
                            break;
                        case "Customer":
                            p = new Customer(name, age);
                            persons.add(p);
                            pos = persons.indexOf(p) + 1;
                            taPersons.append(String.format("%d. Customer - %s (%d)\n", pos, name, age));
                            break;
                        case "Manager":
                            p = new Manager(name, age, monthsWorked, salary);
                            persons.add(p);
                            pos = persons.indexOf(p) + 1;
                            taPersons.append(String.format("%d. Manager - %s (%d)\n", pos, name, age));
                            break;
                        default: break;
                    }

                } catch (InputMismatchException | NumberFormatException exc) {
                    displayError(exc.getMessage());
                    clearInputs();
                }
            }
        });

        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                group.clearSelection();
                clearInputs();
            }
        });

        btnSayHi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Person p: persons) {
                    System.out.println(p);
                }
            }
        });

    }

    public static void main(String[] args) {
        App a = new App();
        a.setContentPane(a.pnlMain);
        a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        a.setSize(500, 350);
        a.setLocationRelativeTo(null);
        a.setVisible(true);
    }

    static void giveReward(int n) {

    }
}
