/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.View;

import QLTV.DAO.TaiKhoan_DB;
import QLTV.Model.NhanVien;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import QLTV.tien_ich.datahelper;
import QLTV.tien_ich.dialoghelp;
import QLTV.tien_ich.sharehelper;

/**
 *
 * @author ASUS
 */
public class Form_DangNhap {

    JFrame frame = new JFrame("ĐĂNG NHẬP");
    
    //JLabel lblLogin = new JLabel("ĐĂNG NHẬP");
    
    JLabel lblUsername = new JLabel("Tài khoản :");
    JLabel lblPassword = new JLabel("Mật khẩu :");
    JPanel panel = new JPanel();
    GridBagLayout gb1 = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    JButton btnDangNhap = new JButton("Đăng nhập");
    JButton btnExit = new JButton("Thoát");
    JTextField txtUsername = new JTextField();
    JPasswordField txtPassword = new JPasswordField();
    JCheckBox chkmatkhau = new JCheckBox("Hiển thị mật khẩu");
    int index = 0;
    TaiKhoan_DB dao = new TaiKhoan_DB();
    List<NhanVien> listnv = null;

    public Form_DangNhap() {
        this.form();

    }

    public boolean check() {

        if (!(datahelper.checkChar(txtUsername.getText(), 30, "tên đăng nhập"))) {
            txtUsername.requestFocus();
            return false;
        }
        if (!(datahelper.checkChar(txtPassword.getText(), 16, "mật khẩu"))) {
            txtPassword.requestFocus();
            return false;
        }
        return true;
    }

    public void form() {

        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        panel.setLayout(gb1);
        panel.setSize(500, 250);
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        //panel.add(lblLogin, gbc);
        //lblLogin.setFont(new Font("Tahoma", Font.BOLD, 30));
        //gbc.gridy++;
        panel.add(lblUsername, gbc);
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
        gbc.gridy++;
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
        panel.add(lblPassword, gbc);
        
        //TaiKhoan
        txtUsername.setPreferredSize(new Dimension(250, 35));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridy++;
        panel.add(txtUsername);
        //MatKhau
        txtPassword.setPreferredSize(new Dimension(250, 35));
        panel.add(txtPassword, gbc);
        gbc.gridx++;

        JPanel btnButton = new JPanel();
        btnButton.setPreferredSize(new Dimension(200, 80));
        btnButton.setLayout(new FlowLayout());
        btnDangNhap.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        btnButton.add(btnDangNhap, FlowLayout.LEFT);
        btnDangNhap.setFont(new Font("Tahoma", Font.BOLD, 13));
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dialoghelp.confirm(null, "Bạn chắc chắn muốn thoát?")) {
                    System.exit(0);
                }
            }
        });
        btnButton.add(btnExit, FlowLayout.CENTER);
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 13));
        frame.add(btnButton, BorderLayout.SOUTH);
        frame.add(panel);
        btnDangNhap.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Tick.png"));
        btnExit.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Exit.png"));
        
    }
    
	

    public void login() {
        if (check()){
        String user = txtUsername.getText();
        String pass = txtPassword.getText();
      
        try {
            NhanVien nv = dao.findById(user);
            if (nv != null) {
                String pass2 = nv.getPass();
                if (pass.equals(pass2)) {
                    TaiKhoan_DB.users = nv;
                    new Form_Main();
                    frame.dispose();

                } else {
                    dialoghelp.alert(null, "Sai mật khẩu!");
                    txtPassword.requestFocus();
                }
            } else {
                dialoghelp.alert(null, "Sai tên đăng nhập!");
                txtUsername.requestFocus();
            }
        } catch (Exception e) {
            dialoghelp.alert(null, "Lỗi truy vấn dữ liệu!");
            e.printStackTrace();
            System.exit(0);
        }
        }
    }
    

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Form_DangNhap();
            }
        });
    }

}
