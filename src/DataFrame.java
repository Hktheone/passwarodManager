import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DataFrame extends JPanel {
    String acname;
    int id;
    UserHome home;
    DataFrame(String name,UserHome home){
        String data[]=name.split(",");
        JButton dl8= new JButton("Delete");
        JButton b= new JButton(data[1]);
        JButton copy= new JButton("Coppy passwod");
        JPasswordField jPasswordField= new JPasswordField(data[2]);

        this.id= Integer.parseInt(data[0]);
        this.acname= data[1];
        this.home=home;

        b.setBounds(20,20,20,20);

        dl8.setBounds(150,20,20,20);
        jPasswordField.setBounds(50,20,20,20);
        copy.setBounds(100,20,20,20);
        this.add(b);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(b,jPasswordField.getText());
            }
        });
        copy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String myString = jPasswordField.getText();
                StringSelection stringSelection = new StringSelection(myString);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
            }
        });

        dl8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = JOptionPane.showConfirmDialog(dl8, "Are you sure?");
                // JOptionPane.setRootFrame(null);
                if (a == JOptionPane.YES_OPTION) {
                        deleteit();
                }else  return;
            }
        });
        this.add(dl8);
        this.add(jPasswordField);
        this.add(copy);
        this.setBounds(0,0,300,50);

    }
    void deleteit()
    {

        try {
            Connection connection = (Connection) DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/mydatabase",
                    "postgres", "123");

            PreparedStatement st = (PreparedStatement) connection
                    .prepareStatement("delete from credentials\n" +
                            "where id = ? and accountname = ?");

            st.setInt(1, this.id);
            st.setString(2, this.acname);
            int i = st.executeUpdate();
            if(i==1){
                JOptionPane.showMessageDialog(this,"Credentials Deleted!");
                new UserHome(this.home.uname,this.home.id).setVisible(true);
                this.home.dispose();
            }
        }catch (Exception e){

        }

    }
}
