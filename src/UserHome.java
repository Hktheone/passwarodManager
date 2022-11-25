import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class UserHome extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    JScrollPane scrollPane=new JScrollPane();
    int id;
    String uname;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserHome frame = new UserHome();
                    frame.setTitle("Welcome");
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UserHome() {

    }

    /**
     * Create the frame.
     */
    public UserHome(String uname,int id) {
        this.id=id;
        this.uname=uname;
        int x=820;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        JButton btnNewButton = new JButton("Logout");
        btnNewButton.setForeground(new Color(0, 0, 0));
        btnNewButton.setBackground(UIManager.getColor("Button.disabledForeground"));
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(btnNewButton, "Are you sure?");
                // JOptionPane.setRootFrame(null);
                if (a == JOptionPane.YES_OPTION) {
                    dispose();
                }
                dispose();
                UserLogin obj = new UserLogin();
                obj.setTitle("User-Login");
                obj.setVisible(true);

            }
        });
        btnNewButton.setBounds(x, 10, 120, 80);
        contentPane.add(btnNewButton);
        JButton button = new JButton("<html>Change password</html>");
        button.setBackground(UIManager.getColor("Button.disabledForeground"));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChangePassword bo = new ChangePassword(uname);
                bo.setTitle("Change Password");
                bo.setVisible(true);

            }
        });
        button.setFont(new Font("Tahoma", Font.PLAIN, 20));
        button.setBounds(x, 80, 120, 80);
        contentPane.add(button);

        JButton addcredentials = new JButton("<html>Add Credentials</html>");
        addcredentials.setBackground(UIManager.getColor("Button.disabledForeground"));
        addcredentials.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Addcredentials a= new Addcredentials(uname,id);
                a.setVisible(true);

                dispose();
            }
        });
        addcredentials.setFont(new Font("Tahoma", Font.PLAIN, 20));
        addcredentials.setBounds(x, 200, 120, 80);
        contentPane.add(addcredentials);

        //DataFrame df[]= new DataFrame[]{new DataFrame("2"),new DataFrame("3")};
        RefreshJpanel();

    }

    void RefreshJpanel()
    {
        ArrayList<String> data= new ArrayList<>();
        try {
            Connection connection = (Connection) DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/mydatabase",
                    "postgres", "123");

            PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("Select id,accountname, accountpass from credentials where user_id=? ");

            st.setInt(1, this.id);
            ResultSet rs = st.executeQuery();




            while (rs.next())
            {
                data.add(rs.getInt(1)+","+rs.getString(2)+","+rs.getString(3));
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout(data.size(), 1));

        for (int i = 0; i < data.size(); i++)
        {
            pane.add( new DataFrame(data.get(i).toString(),this));
        }
        pane.setBounds(20,20,500,500);
        scrollPane.removeAll();
        scrollPane= new JScrollPane(pane);
        this.scrollPane.setVerticalScrollBar(new JScrollBar());
        this.scrollPane.setBounds(20,20,500,500);
        this.contentPane.add(scrollPane);
        scrollPane.revalidate();
        scrollPane.repaint();
        System.out.println("here");
    }
}