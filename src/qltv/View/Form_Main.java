
package QLTV.View;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;
import javax.swing.border.*;

import org.apache.commons.collections4.functors.IfClosure;

import QLTV.DAO.NhanVien_DB;
import QLTV.DAO.TaiKhoan_DB;
import QLTV.tien_ich.dialoghelp;
import QLTV.Model.NhanVien;

@SuppressWarnings("serial")
public class Form_Main extends JFrame {

	JFrame jf;
	JMenuBar menu3 = new JMenuBar();
	JMenu system = new JMenu("Hệ Thống");
	JMenu manage = new JMenu("Quản Lý");
	JMenu help = new JMenu("Trợ Giúp");
	JMenuItem mniDangxuat = new JMenuItem("Đăng Xuất");
	JMenuItem mniThoat = new JMenuItem("Thoát");
	JMenuItem mniSach = new JMenuItem("Quản lý sách");
	JMenuItem mniChude = new JMenuItem("Quản lý danh mục");
	JMenuItem mninxb = new JMenuItem("Quản lý nhà xuất bản");
	JMenuItem mnind = new JMenuItem("Quản lý người đọc");
	JMenuItem mnims = new JMenuItem("Quản lý mượn sách");
	JMenuItem mnitt = new JMenuItem("Quản lý thủ thư");
	JMenuItem mnibc = new JMenuItem("Thống Kê");
	JPanel pn1, pn2, pn3;
	JButton btnSach = new JButton("Sách");
	JButton btnChude = new JButton("Danh Mục");
	JButton btnnxb = new JButton("Nhà xuất bản");
	JButton btnnd = new JButton("Người Đọc");
	JButton btnms = new JButton("Mượn Sách");
	JButton btntt = new JButton("Thủ Thư");
	JButton btnBaocao = new JButton("Thống kê");
	JButton btnDangxuat = new JButton("Đăng Xuất");
	JButton btndmk = new JButton("Đổi Mật Khẩu");
	JButton btnThoat = new JButton("Thoát");
	JLabel lblUser = new JLabel();
	GridBagLayout gbl = new GridBagLayout();
	GridBagConstraints gbc = new GridBagConstraints();
	GridBagConstraints gbc2 = new GridBagConstraints();
	JLabel lblgiamdoc = new JLabel("Quản Trị Viên: ");
	JToolBar toolbar = new JToolBar();

	public Form_Main() {
		initComponent();
		lblUser.setText(TaiKhoan_DB.users.getTennv());
		handleEvent();
	}
	
	//form
	public void initComponent() {
		jf = new JFrame("Màn hình chính");
		jf.setLayout(new BorderLayout());
		jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
		jf.setSize(1350, 700);
		jf.setDefaultCloseOperation(EXIT_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);

		// Menu Bar
		system.add(mniDangxuat);
		system.addSeparator();
		system.add(mniThoat);
		menu3.add(system);
		manage.add(mniSach);
		manage.addSeparator();
		manage.add(mniChude);
		manage.addSeparator();
		manage.add(mninxb);
		manage.addSeparator();
		manage.add(mnind);
		manage.addSeparator();
		manage.add(mnims);
		manage.addSeparator();
		manage.add(mnitt);
		manage.addSeparator();
		manage.add(mnibc);
		menu3.add(manage);
		menu3.add(help);
		jf.setJMenuBar(menu3);
		//
		pn2 = new JPanel();
		pn2.setBorder(new EtchedBorder());
		pn2.setPreferredSize(new Dimension(700, 400));
		ImageIcon icon = new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\logo.png");
		JLabel lblLogo = new JLabel();
		lblLogo.setIcon(icon);
		pn2.add(lblLogo, gbc);
		jf.add(pn2, BorderLayout.CENTER);
		//
		btnSach.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Book.png"));
		btnChude.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\List.png"));
		btnnxb.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Clien list.png"));
		btnnd.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\User.png"));
		btnms.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Notes.png"));
		btntt.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\User group.png"));
		btnBaocao.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Bar chart.png"));
		btnThoat.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Log out.png"));
		btnDangxuat.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Exit.png"));
		btndmk.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Lock.png"));
		system.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Brick house.png"));
		manage.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Gear.png"));
		help.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Info.png"));
		mniSach.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Book.png"));
		mniChude.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\List.png"));
		mninxb.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Clien list.png"));
		mnind.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\User.png"));
		mnims.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Notes.png"));
		mnitt.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\User group.png"));
		mnibc.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Bar chart.png"));
		mniThoat.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Log out.png"));
		mniDangxuat.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Exit.png"));

		//
		toolbar.add(btnSach);
		toolbar.add(btnChude);
		toolbar.add(btnnxb);
		toolbar.add(btnnd);
		toolbar.add(btnms);
		toolbar.add(btntt);
		toolbar.add(btnBaocao);
		jf.add(toolbar, BorderLayout.NORTH);
		//
		pn3 = new JPanel(gbl);
		pn3.setBorder(new EtchedBorder());
		pn3.setPreferredSize(new Dimension(800, 150));
		gbc2.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc2.insets = new Insets(0, 0, 0, 0);
		gbc2.weightx = 1;
		gbc2.weighty = 1;
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.gridy++;
		JPanel pnUser = new JPanel(new FlowLayout());
		pnUser.add(lblgiamdoc);
		lblgiamdoc.setFont(new Font("Tohoma", Font.BOLD, 20));
		lblUser.setFont(new Font("Tohoma", Font.BOLD, 20));
		pnUser.add(lblUser);
		pn3.add(pnUser, gbc2);
		gbc2.gridy++;
		JPanel pnlDangxuat = new JPanel(new FlowLayout());
		pnlDangxuat.add(btnDangxuat);
		pnlDangxuat.add(btndmk);
		pnlDangxuat.add(btnThoat);
		pn3.add(pnlDangxuat, gbc2);
		gbc2.gridy++;
		JLabel copy = new JLabel("© Copyright by Hutech");
		copy.setFont(new Font("Time New Roman", Font.ITALIC, 11));
		pn3.add(copy, gbc2);
		jf.add(pn3, BorderLayout.SOUTH);
	}

	public void handleEvent() {
		btnSach.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_QuanLySach();
				setVisible(false);
			}
		});
		btnChude.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_DanhMuc();
				setVisible(false);
			}
		});
		btnnxb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_NhaXuatBan();
			}
		});
		btnnd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_NguoiDoc();
			}
		});
		btnms.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_MuonSach();
			}
		});

		btntt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (TaiKhoan_DB.users.isRole() == true) {
					new Form_ThuThu();
				} else {
					dialoghelp.alert(null, "Bạn không phải giám đốc!");
				}

			}
		});

		btnBaocao.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_ThongKe();
			}
		});
		btnDangxuat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jf.dispose();
				new Form_DangNhap();
			}
		});
		btndmk.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_DoiMatKhau();
			}
		});
		btnThoat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialoghelp.confirm(null, "Bạn có muốn thoát không?")) {
					System.exit(0);
				}
			}
		});
		mniDangxuat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jf.dispose();
				new Form_DangNhap();
			}
		});
		mniThoat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialoghelp.confirm(null, "Bạn có muốn thoát chương trình hay không?")) {
					System.exit(0);
				}
			}
		});
		mniSach.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_QuanLySach();
				setVisible(false);
			}
		});
		mniChude.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_DanhMuc();
			}
		});
		mninxb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_NhaXuatBan();
			}
		});
		mnitt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (TaiKhoan_DB.users.isRole() == true) {
					new Form_ThuThu();
				} else {
					dialoghelp.alert(null, "Bạn không phải giám đốc!");
				}
			}
		});
		mnind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_NguoiDoc();
			}
		});
		mnims.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_MuonSach();
			}
		});
		mnibc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_ThongKe();
			}
		});
	}

	public static void main(String[] agrs) {
		new Form_Main();
	}

}