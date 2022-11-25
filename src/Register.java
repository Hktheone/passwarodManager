import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Register extends JFrame{
    public static void main(String[] args) {
        new Register().setVisible(true);
    }
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton btnNewButton;
    private JLabel label;
    private JPanel contentPane;
    PasswordChk passwordChk= new PasswordChk();
    public Register(){
        int x=400;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
            contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Register");
            lblNewLabel.setForeground(Color.BLACK);
            lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 46));
            lblNewLabel.setBounds(423, 13, 273, 93);
            contentPane.add(lblNewLabel);

        textField = new JTextField();
            textField.setFont(new Font("Tahoma", Font.PLAIN, 32));
            textField.setBounds(x, 170, 281, 68);
            contentPane.add(textField);
            textField.setColumns(10);

        passwordField = new JPasswordField();
            passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
            passwordField.setBounds(x, 286, 281, 68);
            contentPane.add(passwordField);


        JLabel jb= new JLabel("<html>Enter a password of length at least 8, having at least a character and at least a number</html>");
        jb.setFont(new Font("Tahoma", Font.PLAIN, 15));
        jb.setBounds(x+288, 270, 281, 100);
        contentPane.add(jb);

        JButton passshow = new JButton("Show password");
        passshow.setForeground(new Color(0, 0, 0));
        passshow.setBackground(UIManager.getColor("Button.disabledForeground"));
        passshow.setFont(new Font("Tahoma", Font.PLAIN, 15));
        passshow.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(passshow,passwordField.getText(),"Password",JOptionPane.INFORMATION_MESSAGE);

            }
        });
        passshow.setBounds(x, 350, 150, 30);
        contentPane.add(passshow);

        JButton genpass = new JButton("Generate Password");
        genpass.setForeground(new Color(0, 0, 0));
        genpass.setBackground(UIManager.getColor("Button.disabledForeground"));
        genpass.setFont(new Font("Tahoma", Font.PLAIN, 15));
        genpass.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                passwordField.setText(passwordChk.generatePassword(8));
                JOptionPane.showMessageDialog(genpass,passwordField.getText(),"Generated Password",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        genpass.setBounds(x+200, 350, 150, 30);
        contentPane.add(genpass);



        JLabel lblUsername = new JLabel("Username");
            lblUsername.setBackground(Color.BLACK);
            lblUsername.setForeground(Color.BLACK);
            lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 31));
            lblUsername.setBounds(250, 166, 193, 52);
            contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
            lblPassword.setForeground(Color.BLACK);
            lblPassword.setBackground(Color.CYAN);
            lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 31));
            lblPassword.setBounds(250, 286, 193, 52);
            contentPane.add(lblPassword);

        btnNewButton = new JButton("Register");
            btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 26));
            btnNewButton.setBounds(545, 392, 162, 73);
            btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String userName = textField.getText();
                String password = passwordField.getText();
                if(!passwordChk.checkPass(password))
                {
                    JOptionPane.showMessageDialog(btnNewButton, "Password is weak");
                    return;
                }


                try {

                    Connection connection = (Connection) DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/mydatabase",
                            "postgres", "123");

                    PreparedStatement st1 = (PreparedStatement) connection
                            .prepareStatement("select name from myuser where name like ?");
                    st1.setString(1,userName);
                    ResultSet rs= st1.executeQuery();
                    if(rs.next()){

                        JOptionPane.showMessageDialog(btnNewButton, "Username Already Exists");
                        return;
                    }

                    PreparedStatement st = (PreparedStatement) connection
                            .prepareStatement("insert into myuser (\"name\",\"password\")\n" +
                                    "values (?,?)");

                    st.setString(1, userName);
                    st.setString(2, password);
                    st.executeUpdate();

                    JOptionPane.showMessageDialog(btnNewButton, "You have successfully Registered");

                } catch (SQLException sqlException) {
                    sqlException.printStackTrace();
                    JOptionPane.showMessageDialog(btnNewButton, "Something went wrong");
                }
            }
        });
        JButton login = new JButton("Login");
        login.setFont(new Font("Tahoma", Font.PLAIN, 26));
        login.setBounds(300, 392, 162, 73);
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserLogin obj= new UserLogin();
                obj.setVisible(true);
            }
        });
        contentPane.add(login);
        contentPane.add(btnNewButton);

        label = new JLabel("");
            label.setBounds(0, 0, 1008, 562);
            contentPane.add(label);
    }
}
