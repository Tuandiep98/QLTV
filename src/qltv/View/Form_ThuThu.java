package QLTV.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import QLTV.DAO.NhanVien_DB;
import QLTV.Model.NhanVien;
import QLTV.tien_ich.datahelper;
import QLTV.tien_ich.dialoghelp;

public class Form_ThuThu extends JFrame {
	JPanel pnlQuanLyTT = new JPanel();
	JPanel pnlLeft = new JPanel();
	JPanel pnlRight = new JPanel();
	JPanel pnlTop = new JPanel();
	JPanel pnlBottom = new JPanel();
	JPanel pnlTopNORTH = new JPanel();
	JPanel pnlShow = new JPanel();
	JPanel pnlBotNORTH = new JPanel();
	JPanel pnlds = new JPanel();
	JPanel pnlChucNang = new JPanel();
	JLabel lbl_dschude = new JLabel("TÀI KHOẢN");
	JTextField txt_matt = new JTextField(16);
	JTextField txt_tentt = new JTextField(16);
	JTextField txt_taikhoan = new JTextField(16);
	JPasswordField txt_matkhau = new JPasswordField(16);

	// JButton
	JButton btn_them = new JButton("Thêm");
	JButton btn_sua = new JButton("Sửa");
	JButton btn_xoa = new JButton("Xóa");
	JButton btn_luu = new JButton("Lưu");
	JButton btn_boqua = new JButton("Bỏ qua");

	// JTable
	private String header1[] = { "STT", "Danh Mục" };
	Object[][] data1 = { { "1", "Sách" }, { "2", "Danh Mục" }, { "3", "Nhà Xuất Bản" }, { "4", "Người Đọc" },
			{ "5", "Sách Mượn" } };
	JTable tbl_thamchieu = new JTable(new DefaultTableModel(data1, header1));

	private String header2[] = { "STT", "Mã Người Dùng", "Tên Người Dùng", "Tài Khoản", "Mật Khẩu" };
	private DefaultTableModel tblModel2 = new DefaultTableModel(header2, 0);

	JTable tbl_tk = new JTable(tblModel2);

	DefaultTableModel model;
	int vitri;
	boolean cnluu;
	List<NhanVien> listnv = null;
	NhanVien_DB dao = new NhanVien_DB();

	public Form_ThuThu() {
		init();
		vitri = 0;
		fillToThuthu();
		listnv = dao.select();
		setModel(vitri);
		setStatus(false);
		rowCountTable();

	}

	private void fillToThuthu() {
		model = (DefaultTableModel) tbl_tk.getModel();
		model.setRowCount(0);
		try {
			listnv = dao.select();
			int stt = 0;
			for (NhanVien s : listnv) {
				stt++;
				Object row[] = new Object[] { stt, s.getManv(), s.getTennv(), s.getTaikhoan(), s.getPass() };
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setStatus(boolean tt) {
		txt_matt.setEditable(tt);
		txt_tentt.setEditable(tt);
		txt_taikhoan.setEditable(tt);
		txt_matkhau.setEditable(tt);
		btn_luu.setEnabled(tt);
		btn_boqua.setEnabled(tt);
		btn_them.setEnabled(!tt);
		btn_sua.setEnabled(!tt);
		btn_xoa.setEnabled(!tt);

	}

	private void rowCountTable() {
		if (tbl_tk.getRowCount() == 0) {
			btn_sua.setEnabled(false);
			btn_xoa.setEnabled(false);
		} else {
			setModel(vitri);
			btn_sua.setEnabled(true);
			btn_xoa.setEnabled(true);
		}
	}

	private void setModel(int vitri) {
		if (listnv.size() > 0) {
			NhanVien s = listnv.get(vitri);
			txt_matt.setText(s.getManv());
			txt_tentt.setText(s.getTennv());
			txt_taikhoan.setText(s.getTaikhoan());
			txt_matkhau.setText(s.getPass());
			tbl_tk.setRowSelectionInterval(vitri, vitri);
		}
	}

	public boolean check() {
		String matt = txt_matt.getText();
		if (!(datahelper.checkChar(matt, 10, "mã thủ thư"))) {
			txt_matt.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(txt_tentt.getText(), 30, "tên thủ thư"))) {
			txt_tentt.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(txt_taikhoan.getText(), 30, "tài khoản"))) {
			txt_taikhoan.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(txt_matkhau.getText(), 30, "mật khẩu"))) {
			txt_matkhau.requestFocus();
			return false;
		}

		return true;
	}

	private void clear() {
		txt_matt.setText("");
		txt_tentt.setText("");
		txt_taikhoan.setText("");
		txt_matkhau.setText("");
	}

	private NhanVien getModel() {
		String manv, tennv, taikhoan, matkhau;
		boolean role;
		NhanVien nvm = null;
		if (check()) {
			manv = txt_matt.getText().trim().toUpperCase();
			String nv = txt_tentt.getText().trim(), nvUp = "";
			String split[] = nv.split(" ");
			for (String string : split) {
				nvUp += string.substring(0, 1).toUpperCase() + string.substring(1) + " ";
			}
			tennv = nvUp.trim();
			String tknv = txt_taikhoan.getText().trim();
			taikhoan = tknv;
			String mk = txt_matkhau.getText().trim();
			matkhau = mk;
			role = false;
			nvm = new NhanVien(manv, tennv, taikhoan, matkhau, role);
		}
		return nvm;
	}

//	// CÁC NÚT THÊM,SỬA,XÓA
	private void them() {
		try {
			NhanVien s = getModel();
			if (!(s == null)) {
				int kt = 0;
				for (int i = 0; i < dao.select().size(); i++) {
					if (dao.select().get(i).getManv().equals(s.getManv())) {
						dialoghelp.alert(null, "Mã thủ thư đã tồn tại, vui lòng nhập lại");
						txt_matt.requestFocus();
						kt++;
						break;
					}
					if (dao.select().get(i).getTaikhoan().equals(s.getTaikhoan())) {
						dialoghelp.alert(null, "Tài khoản đã tồn tại, vui lòng nhập lại");
						txt_taikhoan.requestFocus();
						kt++;
						break;
					}
				}
				if (kt == 0) {
					dao.insert(s);
					this.fillToThuthu();
					setStatus(false);
					for (int i = 0; i < listnv.size(); i++) {
						if (s.getManv().equals(listnv.get(i).getManv())) {
							setModel(vitri);
							break;
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sua() {
		try {
			NhanVien s = getModel();
			if (!(s == null)) {
				dao.update(s);
				this.fillToThuthu();
				setStatus(false);
				for (int i = 0; i < listnv.size(); i++) {
					if (s.getManv().equals(listnv.get(i).getManv())) {
						setModel(i);
						break;
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void xoa() {
		try {
			if (tbl_tk.getRowCount() > 0) {
				vitri = tbl_tk.getSelectedRow();
				String ma = (String) tbl_tk.getValueAt(vitri, 1);
				if (!(ma == null)) {
					dao.Delete(ma);
					this.fillToThuthu();
					clear();
					if (vitri >= tbl_tk.getRowCount()) {
						vitri--;
						setModel(vitri);
					} else {
						setModel(vitri);
					}
					rowCountTable();
				}
			} else {
				dialoghelp.alert(null, "danh sách trống");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void luu() {
		if (cnluu == true) {
			them();
		} else if (cnluu == false) {
			sua();
		}
	}

	private void init() {
		setTitle("Quản Lý Tài Khoản");
		setSize(1350, 700);
		setLocationRelativeTo(null);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosed(java.awt.event.WindowEvent evt) {
				new Form_QuanLySach();
			}
		});

		JMenuBar menubar = new JMenuBar();
		JMenu m1 = new JMenu("Hệ thống");
		JMenuItem jmn_dangxaut = new JMenuItem("Đăng xuất", null);
		jmn_dangxaut.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Form_DangNhap();
				setVisible(false);
			}
		});
		JMenuItem jmn_ketthuc = new JMenuItem("Kết thúc", null);
		jmn_ketthuc.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				int chose = JOptionPane.showConfirmDialog(null, "Bạn Có Muốn Thoát Không ?",
						"Hệ Thống Quản Lý Thư Viện", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (chose == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		m1.add(jmn_dangxaut);
		m1.add(jmn_ketthuc);
		JMenu m2 = new JMenu("Quản lý");
		JMenuItem mi5 = new JMenuItem("Sách", null);
		mi5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new Form_QuanLySach();
				setVisible(false);
			}
		});
		JMenuItem mi6 = new JMenuItem("Danh mục", null);
		mi6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new Form_DanhMuc();
				setVisible(false);
			}
		});
		JMenuItem mi7 = new JMenuItem("Nhà Xuất Bản", null);
		mi7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new Form_NhaXuatBan();
				setVisible(false);
			}
		});
		JMenuItem mi9 = new JMenuItem("Người Đọc", null);
		mi7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new Form_NguoiDoc();
				setVisible(false);
			}
		});
		JMenuItem mi10 = new JMenuItem("Mượn Sách", null);
		mi7.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new Form_MuonSach();
				setVisible(false);
			}
		});
//        m2.add(mi4);
		m2.add(mi5);
		m2.add(mi6);
		m2.add(mi7);
		JMenu m3 = new JMenu("Báo cáo");
		JMenuItem mi8 = new JMenuItem("Thống kê sách", null);

		m3.add(mi8);
		mi8.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new Form_ThongKe();
				setVisible(false);
			}
		});

		menubar.add(m1);
		menubar.add(m2);
		menubar.add(m3);
		setJMenuBar(menubar);

		pnlQuanLyTT.setLayout(new BorderLayout());
		pnlLeft.setLayout(new BorderLayout());
		pnlRight.setLayout(new BorderLayout());
		pnlTop.setLayout(new BorderLayout());
		pnlTopNORTH.setLayout(new BorderLayout());
		pnlShow.setLayout(new GridBagLayout());
		pnlBottom.setLayout(new BorderLayout());
		pnlBotNORTH.setLayout(new BorderLayout());
		pnlds.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnlChucNang.setLayout(new FlowLayout(FlowLayout.RIGHT));
		GridBagConstraints gbc = new GridBagConstraints();
		// add pnlTopNORTH
		pnlChucNang.add(btn_them);
		pnlChucNang.add(btn_sua);
		pnlChucNang.add(btn_xoa);
		pnlChucNang.add(btn_luu);
		pnlChucNang.add(btn_boqua);

		// add pnlShow
		pnlShow.add(new JLabel("Mã Người Dùng:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_matt, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Tên Người Dùng:"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_tentt, new GridBagConstraints(1, 1, 2, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Tài Khoản:"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_taikhoan, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Mật Khẩu:"), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_matkhau, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));

		pnlTopNORTH.add(lbl_dschude, BorderLayout.LINE_START);
		pnlTopNORTH.add(pnlChucNang, BorderLayout.LINE_END);
		// add pnlTop
		pnlTop.add(pnlTopNORTH, BorderLayout.NORTH);
		pnlTop.add(pnlShow, BorderLayout.CENTER);

		// add pnlBottom
		pnlBottom.add(pnlBotNORTH, BorderLayout.NORTH);
		pnlBottom.add(tbl_tk, BorderLayout.CENTER);

		// add pnlRight
		pnlRight.add(pnlTop, BorderLayout.NORTH);
		pnlRight.add(pnlBottom, BorderLayout.CENTER);
		// add pnlTimKiem
		pnlds.add(new JLabel("DANH SÁCH TÀI KHOẢN"));
		// add pnlBotNORTH
		pnlBotNORTH.add(pnlds, BorderLayout.WEST);

		tbl_thamchieu.setPreferredScrollableViewportSize(new Dimension(250, 280));
		tbl_thamchieu.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		tbl_thamchieu.setFillsViewportHeight(true);
		JScrollPane scrollPane1 = new JScrollPane(tbl_thamchieu);
		TableColumn column = null;
		for (int i = 0; i < 2; i++) {
			column = tbl_thamchieu.getColumnModel().getColumn(i);
			if (i == 1) {
				column.setPreferredWidth(160); // cột thứ 2 sẽ rộng hơn
			} else {
				column.setPreferredWidth(40);
			}
		}
		pnlLeft.add(scrollPane1);

		tbl_tk.setPreferredScrollableViewportSize(new Dimension(200, 200));
		tbl_tk.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tbl_tk.setFillsViewportHeight(true);
		JScrollPane scrollPane2 = new JScrollPane(tbl_tk);
		pnlBottom.add(scrollPane2);

		pnlRight.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		pnlBotNORTH.setBorder(BorderFactory.createEmptyBorder(0, -4, 5, 2));
		pnlTopNORTH.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		pnlBottom.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		pnlQuanLyTT.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		// add frame
		pnlQuanLyTT.add(pnlLeft, BorderLayout.WEST);
		pnlQuanLyTT.add(pnlRight, BorderLayout.CENTER);
		this.add(pnlQuanLyTT);

		// nut button
		btn_them.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_themActionPerformed(evt);
			}
		});

		btn_sua.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_suaActionPerformed(evt);
			}
		});

		btn_xoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialoghelp.confirm(null, "Bạn có muốn xóa không?")) {
					xoa();
				}
			}
		});

		btn_luu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_luuActionPerformed(evt);
			}
		});

		btn_boqua.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btn_boquaActionPerformed(e);
			}
		});

		tbl_tk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tbl_tkMouseClicked(evt);
			}
		});
		tbl_thamchieu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tbl_thamchieuMouseClicked(evt);
				setVisible(false);
			}
		});

		setLocationRelativeTo(null);
		setVisible(true);
		btn_them.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Add.png"));
		btn_sua.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\edit.png"));
		btn_xoa.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Delete.png"));
		btn_luu.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Tick.png"));
		btn_boqua.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Exit.png"));
		m1.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Brick house.png"));
		m2.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Gear.png"));
		m3.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Info.png"));
		mi5.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Book.png"));
		mi6.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\List.png"));
		mi7.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Client list.png"));
		mi8.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Bar chart.png"));
		jmn_ketthuc.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Log out.png"));
		jmn_dangxaut.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Exit.png"));
	}

	// hàm chọn các dòng trên bảng sách
	private void tbl_tkMouseClicked(MouseEvent evt) {
		vitri = tbl_tk.getSelectedRow();
		setModel(vitri);
	}

	private void btn_luuActionPerformed(ActionEvent evt) {
		luu();
	}

	private void btn_boquaActionPerformed(ActionEvent evt) {
		setModel(vitri);
		setStatus(false);
	}

	private void btn_themActionPerformed(ActionEvent e) {
		setStatus(true);
		clear();
		txt_matt.requestFocus();
		cnluu = true;
	}

	private void btn_suaActionPerformed(ActionEvent evt) {
		setStatus(true);
		txt_matt.setEditable(false);
		txt_tentt.requestFocus();
		cnluu = false;
	}

	private void tbl_thamchieuMouseClicked(MouseEvent evt) {
		int n = tbl_thamchieu.getSelectedRow();
		if (n == 0) {
			new Form_QuanLySach();
		} else if (n == 1) {
			new Form_DanhMuc();
		} else if (n == 2) {
			new Form_NhaXuatBan();
		} else if (n == 3) {
			new Form_NguoiDoc();
		} else if (n == 4) {
			new Form_MuonSach();
		}

	}

	public static void main(String[] args) {
		new Form_DangNhap();
	}

}
