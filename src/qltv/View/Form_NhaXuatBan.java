/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.View;

import QLTV.DAO.NhaXB_DB;
import QLTV.Model.Danhmuc;
import QLTV.Model.Nhaxb;

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
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import QLTV.tien_ich.datahelper;
import QLTV.tien_ich.dialoghelp;

/**
 *
 * @author ASUS
 */
public class Form_NhaXuatBan extends JFrame {

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
	JLabel lbl_dschude = new JLabel("NHÀ XUẤT BẢN");
	JTextField txt_manxb = new JTextField(16);
	JTextField txt_tennxb = new JTextField(16);

	// JButton
	JButton btn_them = new JButton("Thêm");
	JButton btn_sua = new JButton("Sửa");
	JButton btn_xoa = new JButton("Xóa");
	JButton btn_luu = new JButton("Lưu");
	JButton btn_boqua = new JButton("Bỏ qua");

	// JTable
	private String header1[] = { "STT", "Danh Mục" };
	Object[][] data1 = { { "1", "Sách" }, { "2", "Danh Mục" }, { "3", "Người Đọc" }, { "4", "Mượn Sách" } };
	JTable tbl_thamchieu = new JTable(new DefaultTableModel(data1, header1));

	private String header2[] = { "STT", "Mã Nhà Xuất Bản", "Tên Nhà Xuất Bản" };
	private DefaultTableModel tblModel2 = new DefaultTableModel(header2, 0);

	JTable tbl_nxb = new JTable(tblModel2);

	DefaultTableModel model;
	int vitri;
	boolean cnluu;
	List<Nhaxb> listnxb = null;
	NhaXB_DB dao = new NhaXB_DB();

	public Form_NhaXuatBan() {
		init();
		vitri = 0;
		fillSachToTblSach();
		listnxb = dao.select();
		setModel(vitri);
		setStatus(false);

	}

	private void fillSachToTblSach() {
		model = (DefaultTableModel) tbl_nxb.getModel();
		model.setRowCount(0);
		try {
			listnxb = dao.select();
			int stt = 0;
			for (Nhaxb s : listnxb) {
				stt++;
				Object row[] = new Object[] { stt, s.getManxb(), s.getTennxb() };
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setStatus(boolean tt) {
		txt_manxb.setEditable(tt);
		txt_tennxb.setEditable(tt);
		btn_luu.setEnabled(tt);
		btn_boqua.setEnabled(tt);
		btn_them.setEnabled(!tt);
		btn_sua.setEnabled(!tt);
		btn_xoa.setEnabled(!tt);

	}

	private void setModel(int vitri) {
		if (listnxb.size() > 0) {
			Nhaxb s = listnxb.get(vitri);
			txt_manxb.setText(s.getManxb());
			txt_tennxb.setText(s.getTennxb());
			tbl_nxb.setRowSelectionInterval(vitri, vitri);
		}
	}

	public boolean check() {
		String manxb = txt_manxb.getText();
		String tennxb = txt_tennxb.getText();

		if (!(datahelper.checkChar(manxb, 10, "mã nhà xuất bản"))) {
			txt_manxb.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(tennxb, 30, "tên nhà xuất bản"))) {
			txt_tennxb.requestFocus();
			return false;
		}
		return true;
	}

	private void clear() {
		txt_manxb.setText("");
		txt_tennxb.setText("");
	}

	private Nhaxb getModel() {
		String manxb, tennxb;

		Nhaxb nxb = null;
		if (check()) {

			manxb = txt_manxb.getText().trim().toUpperCase();
			String tcd = txt_tennxb.getText().trim(), tcdUp = "";
			String split[] = tcd.split(" ");
			for (String string : split) {
				tcdUp += string.substring(0, 1).toUpperCase() + string.substring(1) + " ";
			}
			tennxb = tcdUp.trim();
			nxb = new Nhaxb(manxb, tennxb);
		}
		return nxb;
	}

	// CÁC NÚT THÊM,SỬA,XÓA
	private void them() {
		try {
			Nhaxb s = getModel();
			if (!(s == null)) {
				int kt = 0;
				for (int i = 0; i < dao.select().size(); i++) {
					if (dao.select().get(i).getManxb().equals(s.getManxb())) {
						dialoghelp.alert(null, "Mã nhà xuất bản đã tồn tại, vui lòng nhập lại");
						txt_manxb.requestFocus();
						kt++;
						break;
					}
					if (dao.select().get(i).getTennxb().equals(s.getTennxb())) {
						dialoghelp.alert(null, "Tên nhà xuất bản đã tồn tại, vui lòng nhập lại");
						txt_tennxb.requestFocus();
						kt++;
						break;
					}
				}
				if (kt == 0) {
					dao.insert(s);
					this.fillSachToTblSach();
					setStatus(false);
					for (int i = 0; i < listnxb.size(); i++) {
						if (s.getManxb().equals(listnxb.get(i).getManxb())) {
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
			Nhaxb s = getModel();
			if (!(s == null)) {
				int kt = 0;
				for (int i = 0; i < dao.select().size(); i++) {
					if (dao.select().get(i).getTennxb().equals(s.getTennxb())) {
						dialoghelp.alert(null, "Tên nhà xuất bản đã tồn tại, vui lòng nhập lại");
						txt_tennxb.requestFocus();
						kt++;
						break;
					}
				}
				if (kt == 0) {
					dao.update(s);
					this.fillSachToTblSach();
					setStatus(false);
					for (int i = 0; i < listnxb.size(); i++) {
						if (s.getManxb().equals(listnxb.get(i).getManxb())) {
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

	private void xoa() {
		try {
			if (tbl_nxb.getRowCount() > 0) {
				vitri = tbl_nxb.getSelectedRow();
				String ma = (String) tbl_nxb.getValueAt(vitri, 1);
				if (!(ma == null)) {
					dao.Delete(ma);
					this.fillSachToTblSach();
					clear();
					if (vitri >= tbl_nxb.getRowCount()) {
						vitri--;
						setModel(vitri);
					} else {
						setModel(vitri);
					}
				}
			} else {
				dialoghelp.alert(null, "danh sách trống");
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Nhà xuất bản có sách không thể xóa");
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
		setTitle("Quản Lý Nhà Xuất Bản");
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
		JMenuItem mi7 = new JMenuItem("Người đọc", null);
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
		pnlShow.add(new JLabel("Mã Nhà Xuất Bản:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_manxb, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Tên Nhà Xuất Bản:"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
				GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_tennxb, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlTopNORTH.add(lbl_dschude, BorderLayout.LINE_START);
		pnlTopNORTH.add(pnlChucNang, BorderLayout.LINE_END);
		// add pnlTop
		pnlTop.add(pnlTopNORTH, BorderLayout.NORTH);
		pnlTop.add(pnlShow, BorderLayout.CENTER);

		// add pnlBottom
		pnlBottom.add(pnlBotNORTH, BorderLayout.NORTH);
		pnlBottom.add(tbl_nxb, BorderLayout.CENTER);

		// add pnlRight
		pnlRight.add(pnlTop, BorderLayout.NORTH);
		pnlRight.add(pnlBottom, BorderLayout.CENTER);
		// add pnlTimKiem
		pnlds.add(new JLabel("DANH SÁCH NHÀ XUẤT BẢN"));
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

		tbl_nxb.setPreferredScrollableViewportSize(new Dimension(200, 200));
		tbl_nxb.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tbl_nxb.setFillsViewportHeight(true);
		JScrollPane scrollPane2 = new JScrollPane(tbl_nxb);
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

		// nut button
		btn_them.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_themActionPerformed(evt);
			}
		});
		btn_them.setMnemonic(KeyEvent.VK_1);

		btn_sua.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_suaActionPerformed(evt);
			}
		});
		btn_sua.setMnemonic(KeyEvent.VK_2);

		btn_xoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (dialoghelp.confirm(null, "Bạn có muốn xóa không?")) {
					xoa();
				}
			}
		});
		btn_xoa.setMnemonic(KeyEvent.VK_3);

		btn_luu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btn_luuActionPerformed(evt);
			}
		});
		btn_luu.setMnemonic(KeyEvent.VK_4);

		btn_boqua.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btn_boquaActionPerformed(e);
			}
		});
		btn_boqua.setMnemonic(KeyEvent.VK_5);

		tbl_nxb.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tbl_nxbMouseClicked(evt);
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
		mi7.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\user.png"));
		mi9.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Notes.png"));
		mi8.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Bar chart.png"));
		jmn_ketthuc.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Log out.png"));
		jmn_dangxaut.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Exit.png"));
	}

	// hàm chọn các dòng trên bảng sách
	private void tbl_nxbMouseClicked(MouseEvent evt) {
		vitri = tbl_nxb.getSelectedRow();
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
		txt_manxb.requestFocus();
		cnluu = true;
	}

	private void btn_suaActionPerformed(ActionEvent evt) {
		setStatus(true);
		txt_manxb.setEditable(false);
		txt_tennxb.requestFocus();
		cnluu = false;
	}

	private void tbl_thamchieuMouseClicked(MouseEvent evt) {
		int n = tbl_thamchieu.getSelectedRow();
		if (n == 0) {
			new Form_QuanLySach();
		} else if (n == 1) {
			new Form_DanhMuc();
		} else if (n == 2) {
			new Form_NguoiDoc();
		} else if (n == 3) {
			new Form_MuonSach();

		}

	}

	public static void main(String[] args) {
		new Form_DangNhap();
	}
}
