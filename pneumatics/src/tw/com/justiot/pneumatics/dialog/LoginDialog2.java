package tw.com.justiot.pneumatics.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import tw.com.justiot.pneumatics.PneumaticsCAD;

public class LoginDialog2 extends JDialog {
	private PneumaticsCAD pneumaticscad;
  public String account;
  public String password;
  public boolean isOK=false;
  public LoginDialog2(PneumaticsCAD pneumaticscad)
   {super(pneumaticscad.frame, true);
   this.pneumaticscad=pneumaticscad;
   Border blackline, etched, raisedbevel, loweredbevel, empty;
   blackline = BorderFactory.createLineBorder(Color.black);
   etched = BorderFactory.createEtchedBorder();
   raisedbevel = BorderFactory.createRaisedBevelBorder();
   loweredbevel = BorderFactory.createLoweredBevelBorder();
   empty = BorderFactory.createEmptyBorder();
   
    JPanel all=new JPanel();
    all.setLayout(new BorderLayout());
    all.setBorder(loweredbevel);
    setLayout(new BorderLayout());
    JPanel lp=new JPanel();
    lp.setLayout(new GridLayout(0,1));
    JLabel l1=new JLabel("帳號：");
    l1.setHorizontalAlignment(SwingConstants.RIGHT);
    JLabel l2=new JLabel("密碼：");
    l2.setHorizontalAlignment(SwingConstants.RIGHT);
    JLabel l3=new JLabel("確認密碼：");
    l3.setHorizontalAlignment(SwingConstants.RIGHT);
    lp.add(l1);
    lp.add(l2);
    lp.add(l3);
    
    JPanel rp=new JPanel();
    rp.setLayout(new GridLayout(0,1));
    final JTextField acc=new JTextField(12);
    rp.add(acc);
    final JPasswordField pass1f=new JPasswordField(12);
    rp.add(pass1f);
    final JPasswordField pass2f=new JPasswordField(12);
    rp.add(pass2f);
    
    
    JPanel top=new JPanel();
    top.setBorder(etched);
    top.add(lp,BorderLayout.WEST);
    top.add(rp,BorderLayout.EAST);
    
    all.add(top,BorderLayout.NORTH);
    JPanel bp=new JPanel();
    bp.setLayout(new FlowLayout());
    JButton ok=new JButton("確定");
    JButton can=new JButton("取消");
    bp.add(ok);
    bp.add(can);
    all.add(bp,BorderLayout.SOUTH);
    add(all,BorderLayout.CENTER);
    ok.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	account = acc.getText();
            String pass1=new String(pass1f.getPassword());
            String pass2=new String(pass2f.getPassword());
            if(account==null || account.length()==0)
             {JOptionPane.showMessageDialog(
                                LoginDialog2.this,
                                "帳號不能空白!!","請輸入!",
                                JOptionPane.ERROR_MESSAGE);                                                     
             }
            else if(pass1==null || pass1.length()==0)
             {JOptionPane.showMessageDialog(
                                LoginDialog2.this,
                                "密碼不能空白!!","請輸入!",
                                JOptionPane.ERROR_MESSAGE);                                                     
             }                        
            else if(!pass1.equals(pass2))
             {JOptionPane.showMessageDialog(
                                LoginDialog2.this,
                                "密碼不相符!!","請再輸入!",
                                JOptionPane.ERROR_MESSAGE);                                                     
             }
            else
             {password=pass1;
              isOK=true;
              setVisible(false);
             }
        }
    });
    can.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	isOK=false;
        	setVisible(false);
        }
    });
    pack();
   }

}
