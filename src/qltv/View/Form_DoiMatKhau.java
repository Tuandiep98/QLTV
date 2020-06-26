/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.View;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import QLTV.DAO.TaiKhoan_DB;
import QLTV.tien_ich.dialoghelp;
import QLTV.Model.NhanVien;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;
import QLTV.tien_ich.datahelper;


public class Form_DoiMatKhau extends JFrame {

    TaiKhoan_DB dao = new TaiKhoan_DB();

    JFrame jf;
    JLabel lbltaikhoan = new JLabel();
    JPasswordField txtpasscu = new JPasswordField();
    JPasswordField txtpassmoi = new JPasswordField();
    JPasswordField txtxacnhan = new JPasswordField();
    JButton btnluu = new JButton("Lưu");
    JButton btnboqua = new JButton("Bỏ qua");
    GridBagConstraints gbc = new GridBagConstraints();

    public Form_DoiMatKhau() {
        initComponent();
        lbltaikhoan.setText(dao.users.getTaikhoan());
        handleEvent();
        keyEvent();
    }

    public boolean check() {

        if (!(datahelper.checkChar(txtpasscu.getText(), 16, "mật khẩu cũ"))) {
            txtpasscu.requestFocus();
            return false;
        }
        if (!(datahelper.checkChar(txtpassmoi.getText(), 16, "mật khẩu mới"))) {
            txtpassmoi.requestFocus();
            return false;
        }
        if (!(datahelper.checkChar(txtxacnhan.getText(), 16, "xác nhận mật khẩu mới"))) {
            txtxacnhan.requestFocus();
            return false;
        }

        String oldPass = TaiKhoan_DB.users.getPass();
        if (!oldPass.equalsIgnoreCase(txtpasscu.getText())) {
            dialoghelp.alert(null, "Mật khẩu cũ không đúng vui lòng nhập lại");
            txtpasscu.requestFocus();
            return false;
        } else {
            txtpassmoi.requestFocus();
        }
        return true;
    }

    public void initComponent() {
        jf = new JFrame("Đổi Mật Khẩu");
        jf.setLayout(new BorderLayout());
        jf.setSize(450, 300);
        jf.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        jf.setVisible(true);
        //
        JPanel pnlInfo = new JPanel();
        pnlInfo.setLayout(new GridBagLayout());
        EmptyBorder emptyBorder = (EmptyBorder) BorderFactory.createEmptyBorder(10, 10, 0, 0);
        pnlInfo.setBorder(emptyBorder);
        pnlInfo.setPreferredSize(new Dimension(400, 300));
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.insets = new Insets(2, 2, 2, 2);
        gbc.weightx = gbc.weighty = 1;
        gbc.gridx = gbc.gridy = 0;
        pnlInfo.add(new JLabel("Tài khoản:"), gbc);
        gbc.gridy++;
        pnlInfo.add(new JLabel("Mật khẩu cũ:"), gbc);
        gbc.gridy++;
        pnlInfo.add(new JLabel("Mật khẩu mới:"), gbc);
        gbc.gridy++;
        pnlInfo.add(new JLabel("Xác nhận mật khẩu mới:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        lbltaikhoan.setPreferredSize(new Dimension(200, 25));
        pnlInfo.add(lbltaikhoan, gbc);
        gbc.gridy++;
        txtpasscu.setPreferredSize(new Dimension(200, 25));
        pnlInfo.add(txtpasscu, gbc);
        gbc.gridy++;
        txtpassmoi.setPreferredSize(new Dimension(200, 25));
        pnlInfo.add(txtpassmoi, gbc);
        gbc.gridy++;
        txtxacnhan.setPreferredSize(new Dimension(200, 25));
        pnlInfo.add(txtxacnhan, gbc);
        jf.add(pnlInfo, BorderLayout.CENTER);
        //
        JPanel pnlAction = new JPanel(new FlowLayout());
        pnlAction.add(btnluu);
        pnlAction.add(btnboqua);
        jf.add(pnlAction, BorderLayout.SOUTH);
        //
		btnluu.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Tick.png"));
		btnboqua.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Delete.png"));

    }

    @SuppressWarnings("deprecation")
    NhanVien getNd() {
        NhanVien nd = new NhanVien();
        nd.setPass(txtpassmoi.getText());
        nd.setTaikhoan(lbltaikhoan.getText());
        return nd;
    }

    public void luu() {
        if (check()) {
            try {
                NhanVien nd = getNd();
                dao.update(nd);
                dialoghelp.alert(null, "Lưu mật khẩu mới thành công");
                jf.dispose();
            } catch (Exception e) {
                dialoghelp.alert(null, "Lỗi truy vấn dữ liệu!");
                e.printStackTrace();
            }
        }
    }

    public void handleEvent() {
        btnluu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                luu();
            }
        });
        btnboqua.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jf.dispose();
            }
        });
    }

    public void keyEvent() {
        txtpasscu.addKeyListener(new KeyAdapter() {

            @SuppressWarnings("deprecation")
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (txtpasscu.getText().equals("")) {
                        dialoghelp.alert(null, "Phải nhập mật khẩu cũ");
                        txtpasscu.requestFocus();
                        return;
                    }
                    String oldPass = TaiKhoan_DB.users.getPass();
                    if (!oldPass.equalsIgnoreCase(txtpasscu.getText())) {
                        dialoghelp.alert(null, "Mật khẩu cũ không đúng vui lòng nhập lại");
                        txtpasscu.requestFocus();
                        return;
                    } else {
                        txtpassmoi.requestFocus();
                    }
                }
            }

        }
        );
        txtpassmoi.addKeyListener(
                new KeyAdapter() {

            @SuppressWarnings("deprecation")
            @Override
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (txtpassmoi.getText().equals("")) {
                        dialoghelp.alert(null, "Phải nhập mật khẩu mới");
                        txtpassmoi.requestFocus();
                        return;
                    } else {
                        txtxacnhan.requestFocus();
                    }
                }
            }
        }
        );
        txtxacnhan.addKeyListener(
                new KeyAdapter() {

            @SuppressWarnings("deprecation")
            @Override
            public void keyPressed(KeyEvent e
            ) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (txtxacnhan.getText().equals("")) {
                        dialoghelp.alert(null, "Phải xác nhận mật khẩu mới");
                        txtxacnhan.requestFocus();
                        return;
                    }
                    String pass = txtpassmoi.getText();
                    if (!pass.equalsIgnoreCase(txtxacnhan.getText())) {
                        dialoghelp.alert(null, "Mật khẩu mới không đúng,vui lòng nhập lại");
                        txtxacnhan.requestFocus();
                        return;
                    } else {
                        btnluu.requestFocus();
                    }
                }
            }
        }
        );
    }

    private void txtpasscuKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = txtpasscu.getText();
            if (datahelper.checkChar(text, 16, "mật khẩu cũ")) {
                txtpassmoi.requestFocus();
            }
        }
    }

    private void txtpassmoiKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = txtpassmoi.getText();
            if (datahelper.checkChar(text, 16, "mật khẩu mới")) {
                txtxacnhan.requestFocus();
            }
        }
    }

    private void txtxacnhanKeyPressed(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            String text = txtxacnhan.getText();
            if (datahelper.checkChar(text, 16, "xác nhận mật khẩu mới")) {
                txtpasscu.requestFocus();
            }
        }
    }
}
