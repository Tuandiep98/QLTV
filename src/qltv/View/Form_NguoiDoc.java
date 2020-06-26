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
import java.util.ArrayList;
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

import org.apache.commons.codec.digest.Md5Crypt;

import QLTV.DAO.NguoiDoc_DB;
import QLTV.Model.NguoiDoc;
import QLTV.tien_ich.datahelper;
import QLTV.tien_ich.dialoghelp;

public class Form_NguoiDoc extends JFrame {
	JPanel pnlQuanLyNXB = new JPanel();
	JPanel pnlLeft = new JPanel();
	JPanel pnlRight = new JPanel();
	JPanel pnlTop = new JPanel();
	JPanel pnlBottom = new JPanel();
	JPanel pnlTopNORTH = new JPanel();
	JPanel pnlShow = new JPanel();
	JPanel pnlBotNORTH = new JPanel();
	JPanel pnlds = new JPanel();
	JPanel pnlChucNang = new JPanel();
	JLabel lbl_dschude = new JLabel("NGƯỜI ĐỌC");
	JTextField txt_mand = new JTextField(16);
	JTextField txt_taikhoan = new JTextField(16);
	JPasswordField txt_matkhau = new JPasswordField(16);
	JTextField txt_tennd = new JTextField(16);
	JTextField txt_sodt = new JTextField(16);

	// JButton
	JButton btn_them = new JButton("Thêm");
	JButton btn_sua = new JButton("Sửa");
	JButton btn_xoa = new JButton("Xóa");
	JButton btn_luu = new JButton("Lưu");
	JButton btn_boqua = new JButton("Bỏ qua");
	JRadioButton rdo_nam = new JRadioButton("Nam");
	JRadioButton rdo_nu = new JRadioButton("Nữ");
	private ButtonGroup btg;

	// JTable
	private String header1[] = { "STT", "Danh Mục" };
	Object[][] data1 = { { "1", "Sách" }, { "2", "Danh Mục" }, { "3", "Nhà Xuất Bản" }, { "4", "Sách Mượn" } };
	JTable tbl_thamchieu = new JTable(new DefaultTableModel(data1, header1));

	private String header2[] = { "STT", "Mã Người Đọc", "Tài Khoản", "Mật Khẩu", "Tên Người Đọc", "Giới Tính",
			"Số Điện Thoại" };
	private DefaultTableModel tblModel2 = new DefaultTableModel(header2, 0);

	JTable tbl_nd = new JTable(tblModel2);

	DefaultTableModel model;
	int vitri;
	boolean cnluu;
	List<NguoiDoc> listnd = null;
	NguoiDoc_DB dao = new NguoiDoc_DB();

	public Form_NguoiDoc() {
		init();
		vitri = 0;
		fillToTblNguoidoc();
		listnd = dao.select();
		setModel(vitri);
		setStatus(false);
		rowCountTable();

	}

	private void fillToTblNguoidoc() {
		model = (DefaultTableModel) tbl_nd.getModel();
		model.setRowCount(0);
		try {
			listnd = dao.select();
			int stt = 0;
			for (NguoiDoc s : listnd) {
				stt++;
				Object row[] = new Object[] { stt, s.getMaNguoiDoc(), s.getTaiKhoan(), s.getMatKhau(),
						s.getTenNguoiDoc(), s.isGioiTinh() ? "Nam" : "Nữ", s.getSoDT() };
				model.addRow(row);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	private void rowCountTable() {
		if (tbl_nd.getRowCount() == 0) {
			btn_sua.setEnabled(false);
			btn_xoa.setEnabled(false);
		} else {
			setModel(vitri);
			btn_sua.setEnabled(true);
			btn_xoa.setEnabled(true);
		}
	}

	void setStatus(boolean tt) {
		txt_mand.setEditable(tt);
		txt_taikhoan.setEditable(tt);
		txt_matkhau.setEditable(tt);
		txt_sodt.setEditable(tt);
		txt_tennd.setEditable(tt);
		rdo_nam.setEnabled(tt);
		rdo_nu.setEnabled(tt);
		btn_luu.setEnabled(tt);
		btn_boqua.setEnabled(tt);
		btn_them.setEnabled(!tt);
		btn_sua.setEnabled(!tt);
		btn_xoa.setEnabled(!tt);

	}

	private void setModel(int vitri) {
		if (listnd.size() > 0) {
			NguoiDoc s = listnd.get(vitri);
			txt_mand.setText(s.getMaNguoiDoc());
			txt_taikhoan.setText(s.getTaiKhoan());
			txt_matkhau.setText(s.getMatKhau());
			txt_tennd.setText(s.getTenNguoiDoc());
			if (s.isGioiTinh()) {
				rdo_nam.setSelected(true);

			} else {
				rdo_nu.setSelected(true);
			}
			tbl_nd.setRowSelectionInterval(vitri, vitri);
			txt_sodt.setText(Integer.toString(s.getSoDT()));
		}
	}

	public boolean check() {

		if (!(datahelper.checkChar(txt_mand.getText(), 10, "mã người đọc"))) {
			txt_mand.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(txt_taikhoan.getText(), 30, "tài khoản"))) {
			txt_taikhoan.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(txt_tennd.getText(), 30, "tên người đọc"))) {
			txt_tennd.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(txt_matkhau.getText(), 30, "mật khẩu"))) {
			txt_matkhau.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(txt_sodt.getText(), 12, "số điện thoại"))) {
			txt_sodt.requestFocus();
			return false;
		}
		return true;
	}

	private void clear() {
		txt_mand.setText("");
		txt_taikhoan.setText("");
		txt_matkhau.setText("");
		txt_tennd.setText("");
		txt_sodt.setText("");
		rdo_nam.setSelected(false);

	}

	private NguoiDoc getModel() {
		String mand, taikhoan, matkhau, tennd;
		boolean gioitinh;
		int sodt;

		NguoiDoc nd = null;
		if (check()) {
			mand = txt_mand.getText().trim().toUpperCase();
			String tknv = txt_taikhoan.getText().trim();
			taikhoan = tknv;
			String mk = txt_matkhau.getText().trim();
			matkhau = mk;
			String tnd = txt_tennd.getText().trim(), tndUper = "";
			String split[] = tnd.split(" ");
			for (String string : split) {
				tndUper += string.substring(0, 1).toUpperCase() + string.substring(1) + " ";
			}
			tennd = tndUper.trim();
			if (btg.getSelection().equals(rdo_nam.getModel())) {
				gioitinh = true;

			} else {
				gioitinh = false;
			}
			sodt = Integer.parseInt(txt_sodt.getText());
			nd = new NguoiDoc(mand, taikhoan, matkhau, tennd, gioitinh, sodt);
		}
		return nd;
	}

	// CÁC NÚT THÊM,SỬA,XÓA
	private void them() {
		try {
			NguoiDoc s = getModel();
			if (!(s == null)) {
				int kt = 0;
				for (int i = 0; i < dao.select().size(); i++) {
					if (dao.select().get(i).getMaNguoiDoc().equals(s.getMaNguoiDoc())) {
						dialoghelp.alert(null, "Mã người đọc đã tồn tại, vui lòng nhập lại");
						txt_mand.requestFocus();
						kt++;
						break;
					}
					if (dao.select().get(i).getTaiKhoan().equals(s.getTaiKhoan())) {
						dialoghelp.alert(null, "Tài khoản đã tồn tại, vui lòng nhập lại");
						txt_taikhoan.requestFocus();
						kt++;
						break;
					}
				}
				if (kt == 0) {
					dao.insert(s);
					this.fillToTblNguoidoc();
					setStatus(false);
					for (int i = 0; i < listnd.size(); i++) {
						if (s.getMaNguoiDoc().equals(listnd.get(i).getMaNguoiDoc())) {
							setModel(i);
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
			NguoiDoc s = getModel();
			if (!(s == null)) {
				dao.update(s);
				this.fillToTblNguoidoc();
				setStatus(false);

				for (int i = 0; i < listnd.size(); i++) {
					if (s.getMaNguoiDoc().equals(listnd.get(i).getMaNguoiDoc())) {
						setModel(i);
						vitri = i;
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
			if (tbl_nd.getRowCount() > 0) {
				vitri = tbl_nd.getSelectedRow();
				String ma = (String) tbl_nd.getValueAt(vitri, 1);
				if (!(ma == null)) {
					dao.Delete(ma);
					this.fillToTblNguoidoc();
					clear();

					if (vitri >= tbl_nd.getRowCount()) {
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
		setTitle("Quản Lý Người Đọc");
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
		JMenuItem mi9 = new JMenuItem("Mượn Sách", null);
		mi9.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new Form_MuonSach();
				setVisible(false);
			}
		});
//        m2.add(mi4);
		m2.add(mi5);
		m2.add(mi6);
		m2.add(mi7);
		m2.add(mi9);
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

		pnlQuanLyNXB.setLayout(new BorderLayout());
		pnlLeft.setLayout(new BorderLayout());
		pnlRight.setLayout(new BorderLayout());
		pnlTop.setLayout(new BorderLayout());
		pnlTopNORTH.setLayout(new BorderLayout());
		pnlShow.setLayout(new GridBagLayout());
//        pnlShow.setBackground(Color.red);
		pnlBottom.setLayout(new BorderLayout());
		pnlBotNORTH.setLayout(new BorderLayout());
		pnlds.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnlChucNang.setLayout(new FlowLayout(FlowLayout.RIGHT));
		GridBagConstraints gbc = new GridBagConstraints();

//        pnlLeft.add(tbl_nxb, BorderLayout.SOUTH);

		// add pnlTopNORTH
		pnlChucNang.add(btn_them);
		pnlChucNang.add(btn_sua);
		pnlChucNang.add(btn_xoa);
		pnlChucNang.add(btn_luu);
		pnlChucNang.add(btn_boqua);

		// add pnlShow
		pnlShow.add(new JLabel("Mã Người Đọc:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_mand, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 5));
		pnlShow.add(new JLabel("Tên Người Đọc:"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_tennd, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 300, 5));
		pnlShow.add(new JLabel("Giới Tính:"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(rdo_nam, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(rdo_nu, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(new JLabel("Tài Khoản:"), new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(txt_taikhoan, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 150, 5));
		pnlShow.add(new JLabel("Mật Khẩu:"), new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(txt_matkhau, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 150, 5));
		pnlShow.add(new JLabel("Số Điện Thoại:"), new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(txt_sodt, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 150, 5));

		pnlTopNORTH.add(lbl_dschude, BorderLayout.LINE_START);
		pnlTopNORTH.add(pnlChucNang, BorderLayout.LINE_END);
		// add pnlTop
		pnlTop.add(pnlTopNORTH, BorderLayout.NORTH);
		pnlTop.add(pnlShow, BorderLayout.CENTER);
//      add radio vào buttongroup
		btg = new ButtonGroup();
		rdo_nam.setText("Nam");
		btg.add(rdo_nam);
		btg.add(rdo_nu);

		// add pnlBottom
		pnlBottom.add(pnlBotNORTH, BorderLayout.NORTH);
		pnlBottom.add(tbl_nd, BorderLayout.CENTER);

		// add pnlRight
		pnlRight.add(pnlTop, BorderLayout.NORTH);
		pnlRight.add(pnlBottom, BorderLayout.CENTER);
		// add pnlds
		pnlds.add(new JLabel("DANH SÁCH NGƯỜI ĐỌC"));
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

		tbl_nd.setPreferredScrollableViewportSize(new Dimension(200, 200));
		tbl_nd.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tbl_nd.setFillsViewportHeight(true);
		JScrollPane scrollPane2 = new JScrollPane(tbl_nd);
		pnlBottom.add(scrollPane2);

		pnlRight.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		pnlBotNORTH.setBorder(BorderFactory.createEmptyBorder(0, -4, 5, 2));
		pnlTopNORTH.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		pnlBottom.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		pnlQuanLyNXB.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		// add frame
		pnlQuanLyNXB.add(pnlLeft, BorderLayout.WEST);
		pnlQuanLyNXB.add(pnlRight, BorderLayout.CENTER);
		this.add(pnlQuanLyNXB);
		// add chuc nang button
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

		// add click tblnd
		tbl_nd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tbl_ndMouseClicked(evt);
			}
		});
		// Click tblthamchieu
		tbl_thamchieu.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tbl_thamchieuMouseClicked(evt);
				setVisible(false);
			}
		});

		setLocationRelativeTo(null);
		setVisible(true);
		btn_them.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Add.png"));
		btn_sua.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\edit.png"));
		btn_xoa.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Delete.png"));
		btn_luu.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Tick.png"));
		btn_boqua.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Exit.png"));
		m1.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Brick house.png"));
		m2.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Gear.png"));
		m3.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Info.png"));
		mi5.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Book.png"));
		mi6.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\List.png"));
		mi7.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Clien list.png"));
		mi8.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Bar chart.png"));
		mi9.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Notes.png"));
		jmn_ketthuc.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Log out.png"));
		jmn_dangxaut.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Exit.png"));
	}

	// hàm chọn các dòng trên bảng sách
	private void tbl_ndMouseClicked(MouseEvent evt) {
		vitri = tbl_nd.getSelectedRow();
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
		txt_mand.requestFocus();
		cnluu = true;
	}

	private void btn_suaActionPerformed(ActionEvent evt) {
		setStatus(true);
		txt_mand.setEditable(false);
		txt_taikhoan.requestFocus();
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
			new Form_MuonSach();
		}

	}

	public static void main(String[] args) {
		new Form_DangNhap();
	}

}
