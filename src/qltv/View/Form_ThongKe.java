/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.View;

import QLTV.DAO.Baocao_DB;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Vector;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Admin
 */
public class Form_ThongKe extends JFrame{
    private JLabel cbo_tknamxb,cbo_danhmuc,cbo_bandientu;
    private JTable tbl_namxb, tbl_danhmuc, tbl_bandientu;
    private JPanel pnl_tkNamxb,pnl_tdTkNamxb, pnl_tkChude,pnl_tdTkchude, pnl_tkBandientu, pnl_tdTkbandientu,pnl_thongke;
    private DefaultTableModel model;
    List<Vector> list= null;

    Baocao_DB dao = new Baocao_DB();
    public Form_ThongKe() {
        innit();
        fill1();
        fill2();
        fill3();
    }
    
    private void fill1(){
        model = (DefaultTableModel) tbl_namxb.getModel();
        model.setRowCount(0);
        list = dao.selectNamXB();
        try {
            int stt = 0;
            for (Vector vt : list) {
                stt++;
                Object row[] = new Object[] {stt,vt.get(0),vt.get(1)};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fill2(){
        model = (DefaultTableModel) tbl_danhmuc.getModel();
        model.setRowCount(0);
        list = dao.selectChude();
        try {
            int stt = 0;
            for (Vector vt : list) {
                stt++;
                Object row[] = new Object[] {stt,vt.get(0),vt.get(1)};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void fill3(){
        model = (DefaultTableModel) tbl_bandientu.getModel();
        model.setRowCount(0);
        list = dao.selectBanDT();
        try {
            int stt = 0;
            for (Vector vt : list) {
                stt++;
                Object row[] = new Object[] {stt,vt.get(0),vt.get(1),vt.get(2),vt.get(3)};
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void innit(){
    	setTitle("Quản Lý Thống Kê");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        JMenuBar menubar = new JMenuBar();
        JMenu m1 = new JMenu("Hệ thống");
        JMenuItem jmn_dangxaut = new JMenuItem("đăng xuất", null);
        jmn_dangxaut.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Form_DangNhap();
                setVisible(false);
            }
        });
        JMenuItem jmn_ketthuc = new JMenuItem("kết thúc", null);
        jmn_ketthuc.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        m1.add(jmn_dangxaut);
        m1.add(jmn_ketthuc);
        JMenu m2 = new JMenu("Quản lý");
        JMenuItem mi4 = new JMenuItem("Sách", null);
        mi4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new Form_QuanLySach();
                setVisible(false);
            }
        });
        JMenuItem mi5 = new JMenuItem("Danh Mục", null);
        mi5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new Form_DanhMuc();
                setVisible(false);
            }
        });
        JMenuItem mi6 = new JMenuItem("Nhà Xuất Bản", null);
        mi6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new Form_NhaXuatBan();
                setVisible(false);
            }
        });
        JMenuItem mi7 = new JMenuItem("Người Đọc", null);
        mi7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new Form_NguoiDoc();
                setVisible(false);
            }
        });
        JMenuItem mi9 = new JMenuItem("Mượn Sách", null);
        mi9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new Form_MuonSach();
                setVisible(false);
            }
        });
        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);
        m2.add(mi7);
        m2.add(mi9);
        menubar.add(m1);
        menubar.add(m2);

        setJMenuBar(menubar);
        
        cbo_tknamxb = new JLabel("Thống kê theo năm xuất bản");       
        pnl_tkNamxb = new JPanel();
        pnl_tkNamxb.setLayout(new BorderLayout());
        
        pnl_tdTkNamxb  = new JPanel();
        pnl_tdTkNamxb.setLayout(new BoxLayout(pnl_tdTkNamxb,BoxLayout.X_AXIS));
        
        pnl_tdTkNamxb.add(new JLabel("Loại báo cáo:   "));
        pnl_tdTkNamxb.add(cbo_tknamxb);
        
        String[] name = {
            "STT", "Năm Xuất Bản","Số Lượng Sách"
        };
        Object[][] data = {
            {null, null,null}
        };
        tbl_namxb = new JTable(new DefaultTableModel(data, name));
        JScrollPane jsc = new JScrollPane(tbl_namxb);
        tbl_namxb.setFillsViewportHeight(true);

        pnl_tkNamxb.add(pnl_tdTkNamxb, BorderLayout.NORTH);
        pnl_tkNamxb.add(jsc,BorderLayout.CENTER);//add bảng chủ đề vào panel2
        
        cbo_danhmuc = new  JLabel("Thống kê theo danh mục");
        
        
        pnl_tkChude = new JPanel();
        pnl_tkChude.setLayout(new BorderLayout());
        
        pnl_tdTkchude  = new JPanel();
        pnl_tdTkchude.setLayout(new BoxLayout(pnl_tdTkchude,BoxLayout.X_AXIS));
        
        pnl_tdTkchude.add(new JLabel("Loại báo cáo:   "));
        pnl_tdTkchude.add(cbo_danhmuc);
        
        String[] nameTblchude= {
            "STT", "Danh Mục","Số Lượng Sách"
        };
        Object[][] dataTblchude = {
            {null, null,null}
        };
        tbl_danhmuc = new JTable(new DefaultTableModel(dataTblchude, nameTblchude));
        JScrollPane jscchude = new JScrollPane(tbl_danhmuc);
        tbl_danhmuc.setFillsViewportHeight(true);

        pnl_tkChude.add(pnl_tdTkchude, BorderLayout.NORTH);
        pnl_tkChude.add(jscchude,BorderLayout.CENTER);
        
        cbo_bandientu = new JLabel("Liệt kê sách có bản điện tử");
        pnl_tkBandientu = new JPanel();
        pnl_tkBandientu.setLayout(new BorderLayout());
        
        
        pnl_tdTkbandientu  = new JPanel();
        pnl_tdTkbandientu.setLayout(new BoxLayout(pnl_tdTkbandientu,BoxLayout.X_AXIS));
        
        pnl_tdTkbandientu.add(new JLabel("Loại báo cáo:   "));
        pnl_tdTkbandientu.add(cbo_bandientu);
        
        String[] nameTblbandt= {
            "STT","Mã", "Tiêu Đề","Tác Giả","Năm Xuất Bản"
        };
        Object[][] dataTblbandt = {
            {null, null,null,null,null}
        };
        tbl_bandientu = new JTable(new DefaultTableModel(dataTblbandt, nameTblbandt));

        JScrollPane jscbanDT = new JScrollPane(tbl_bandientu);
        tbl_bandientu.setFillsViewportHeight(true);


        pnl_tkBandientu.add(pnl_tdTkbandientu, BorderLayout.NORTH);
        pnl_tkBandientu.add(jscbanDT,BorderLayout.CENTER);
        
        pnl_thongke = new JPanel();
        pnl_thongke.setLayout(new GridLayout(3, 1, 20, 20));
        pnl_thongke.add(pnl_tkNamxb);
        pnl_thongke.add(pnl_tkChude);
        pnl_thongke.add(pnl_tkBandientu);
        
        
        add(pnl_thongke,BorderLayout.CENTER);
        setVisible(true);
		m1.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Brick house.png"));
		m2.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Gear.png"));
		mi4.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Book.png"));
		mi5.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\List.png"));
		mi7.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\user.png"));
		mi6.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Clien list.png"));
		mi9.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Notes.png"));
		jmn_ketthuc.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Log out.png"));
		jmn_dangxaut.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Exit.png"));
    }
    
    public static void main(String[] args) {
        new Form_DangNhap();
    }
}
