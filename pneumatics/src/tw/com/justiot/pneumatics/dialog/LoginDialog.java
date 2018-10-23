package tw.com.justiot.pneumatics.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.WindowAdapter;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import tw.com.justiot.pneumatics.PneumaticsCAD;

import java.awt.event.ActionListener;

public class LoginDialog extends JDialog {
	private PneumaticsCAD pneumaticscad;
    private JOptionPane optionPane;
    private String account;
    private String password;
    private JTextField textField;
    
    public String getAccount() {return account;}
    public String getPassword() {return password;}

//    public LoginDialog(Frame aFrame,String title,String msgString,int vtype) 
    public LoginDialog(PneumaticsCAD pneumaticscad)
     {super(pneumaticscad.frame, true);
     this.pneumaticscad=pneumaticscad;
        setPreferredSize(new Dimension(200,300));
//        Image iconImage=Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/images/webladdercad.gif"));
//        setIconImage(iconImage);
        setTitle("登入");
        textField = new JTextField(6);
        final JPasswordField passwordField1 = new JPasswordField(6);
//        passwordField1.setEchoChar('#');
        final JPasswordField passwordField2 = new JPasswordField(6);
//        passwordField2.setEchoChar('#');
 
        Object[] array = {"帳號:", textField,"密碼:",passwordField1,"確認密碼:",passwordField2};
        final String btnString1 = "確定";
        final String btnString2 = "取消";
        Object[] options = {btnString1, btnString2};
        optionPane = new JOptionPane(array, 
                                    JOptionPane.QUESTION_MESSAGE,
                                    JOptionPane.YES_NO_OPTION,
                                    null,
                                    options,
                                    options[1]);
        setContentPane(optionPane);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                    optionPane.setValue(new Integer(
                                        JOptionPane.CLOSED_OPTION));
            }
        });

        textField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionPane.setValue(btnString1);
            }
        });

        optionPane.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                String prop = e.getPropertyName();

                if (isVisible() 
                 && (e.getSource() == optionPane)
                 && (prop.equals(JOptionPane.VALUE_PROPERTY) ||
                     prop.equals(JOptionPane.INPUT_VALUE_PROPERTY))) {
                    Object value = optionPane.getValue();

                    if (value == JOptionPane.UNINITIALIZED_VALUE) {
                        //ignore reset
                        return;
                    }

                    // Reset the JOptionPane's value.
                    // If you don't do this, then if the user
                    // presses the same button next time, no
                    // property change event will be fired.
                    optionPane.setValue(
                            JOptionPane.UNINITIALIZED_VALUE);

                    if (value.equals(btnString1)) {
                        account = textField.getText();
                        String pass1=new String(passwordField1.getPassword());
                        String pass2=new String(passwordField2.getPassword());
                        if(account==null || account.length()==0)
                         {JOptionPane.showMessageDialog(
                                            LoginDialog.this,
                                            "帳號不能空白!!","請輸入!",
                                            JOptionPane.ERROR_MESSAGE);                                                     
                         }
                        else if(pass1==null || pass1.length()==0)
                         {JOptionPane.showMessageDialog(
                                            LoginDialog.this,
                                            "密碼不能空白!!","請輸入!",
                                            JOptionPane.ERROR_MESSAGE);                                                     
                         }                        
                        else if(!pass1.equals(pass2))
                         {JOptionPane.showMessageDialog(
                                            LoginDialog.this,
                                            "密碼不相符!!","請再輸入!",
                                            JOptionPane.ERROR_MESSAGE);                                                     
                         }
                        else
                         {password=pass1;
                          setVisible(false);
                         }
                    } else { 
                        account = null;
                        password = null;
                        setVisible(false);
                    }
                }
            }
        });
     pack();   
    }
}

