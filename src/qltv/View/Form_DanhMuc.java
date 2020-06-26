package QLTV.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import QLTV.DAO.DanhMuc_DB;
import QLTV.Model.Danhmuc;
import QLTV.Model.Nhaxb;
import QLTV.Model.Sach;
import QLTV.tien_ich.datahelper;
import QLTV.tien_ich.dialoghelp;

public class Form_DanhMuc extends JFrame {
	JPanel pnlQuanLyDanhMuc = new JPanel();
	JPanel pnlLeft = new JPanel();
	JPanel pnlRight = new JPanel();
	JPanel pnlTop = new JPanel();
	JPanel pnlBottom = new JPanel();
	JPanel pnlTopNORTH = new JPanel();
	JPanel pnlShow = new JPanel();
	JPanel pnlBotNORTH = new JPanel();
	JPanel pnlds = new JPanel();
	JPanel pnlChucNang = new JPanel();
	JLabel lbl_dsdanhmuc = new JLabel("DANH MỤC");
	JTextField txt_madanhmuc = new JTextField(16);
	JTextField txt_tendanhmuc = new JTextField(16);

	// JButton
	JButton btn_them = new JButton("Thêm");
	JButton btn_sua = new JButton("Sửa");
	JButton btn_xoa = new JButton("Xóa");
	JButton btn_luu = new JButton("Lưu");
	JButton btn_boqua = new JButton("Bỏ qua");

	// JTable
	private String header1[] = { "STT", "Danh Mục" };
	Object[][] data1 = { { "1", "Sách" }, { "2", "Nhà Xuất Bản" }, { "3", "Người Đọc" }, { "4", "Mượn Sách" } };
	JTable tbl_thamchieu = new JTable(new DefaultTableModel(data1, header1));

	private String header2[] = { "STT", "Mã Danh Mục", "Tên Danh Mục" };
	private DefaultTableModel tblModel2 = new DefaultTableModel(header2, 0);

	JTable tbl_danhmuc = new JTable(tblModel2);

	DefaultTableModel model;
	int vitri;
	boolean cnluu;
	List<Danhmuc> listcd = null;
	DanhMuc_DB dao = new DanhMuc_DB();
	public Form_DanhMuc (){
		init();
		this.fillSachToTblSach();
		listcd = dao.select();
		setModel(vitri);
		vitri = 0;
		setStatus(false);
	}
	private void fillSachToTblSach() {
		model = (DefaultTableModel) tbl_danhmuc.getModel();
		model.setRowCount(0);
		try {
			listcd = dao.select();
			int stt = 0;
			for (Danhmuc s : listcd) {
				stt++;
				Object row[] = new Object[] {stt,s.getMadm(),s.getTendm()};
				model.addRow(row);
				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	void setStatus(boolean tt) {
		txt_madanhmuc.setEditable(tt);
		txt_tendanhmuc.setEditable(tt);
		btn_luu.setEnabled(tt);
		btn_boqua.setEnabled(tt);
		btn_them.setEnabled(!tt);
		btn_sua.setEnabled(!tt);
		btn_xoa.setEnabled(!tt);

	}

	private void setModel(int vitri) {
		if (listcd.size() > 0) {
			Danhmuc s = listcd.get(vitri);
			txt_madanhmuc.setText(s.getMadm());
			txt_tendanhmuc.setText(s.getTendm());
			tbl_danhmuc.setRowSelectionInterval(vitri, vitri);
		}
	}

	public boolean check() {

		if (!(datahelper.checkChar(txt_madanhmuc.getText(), 10, "mã danh mục"))) {
			txt_madanhmuc.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(txt_tendanhmuc.getText(), 30, "tên danh mục"))) {
			txt_tendanhmuc.requestFocus();
			return false;
		}
		return true;
	}

	private void clear() {
		txt_madanhmuc.setText("");
		txt_tendanhmuc.setText("");
	}

	private Danhmuc getModel() {
		String machude, tencd;

		Danhmuc cd = null;
		if (check()) {

			machude = txt_madanhmuc.getText().trim().toUpperCase();
			String tcd = txt_tendanhmuc.getText().trim(), tcdUp = "";
			String split[] = tcd.split(" ");
			for (String string : split) {
				tcdUp += string.substring(0, 1).toUpperCase() + string.substring(1) + " ";
			}
			tencd = tcdUp.trim();
			cd = new Danhmuc(machude, tencd);
		}
		return cd;
	}

	// CÁC NÚT THÊM,SỬA,XÓA
	private void them() {
		try {
			Danhmuc s = getModel();
			if (!(s == null)) {
				int kt = 0;
				for (int i = 0; i < dao.select().size(); i++) {
					if (dao.select().get(i).getMadm().equals(s.getMadm())) {
						dialoghelp.alert(null, "Mã danh mục đã tồn tại, vui lòng nhập lại");
						txt_madanhmuc.requestFocus();
						kt++;
						break;
					}
					if (dao.select().get(i).getTendm().equals(s.getTendm())) {
						dialoghelp.alert(null, "Tên danh mục đã tồn tại, vui lòng nhập lại");
						txt_tendanhmuc.requestFocus();
						kt++;
						break;
					}
				}
				if (kt == 0) {
					dao.insert(s);
					this.fillSachToTblSach();
					setStatus(false);
					for (int i = 0; i < listcd.size(); i++) {
						if (s.getMadm().equals(listcd.get(i).getMadm())) {
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
			Danhmuc s = getModel();
			if (!(s == null)) {
				int kt = 0;
				for (int i = 0; i < dao.select().size(); i++) {
					if (dao.select().get(i).getTendm().equals(s.getTendm())) {
						dialoghelp.alert(null, "Tên danh mục đã tồn tại, vui lòng nhập lại");
						txt_tendanhmuc.requestFocus();
						kt++;
						break;
					}
				}
				if (kt == 0) {
					dao.update(s);
					this.fillSachToTblSach();
					setStatus(false);
					for (int i = 0; i < listcd.size(); i++) {
						if (s.getMadm().equals(listcd.get(i).getMadm())) {
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
			if (tbl_danhmuc.getRowCount() > 0) {
				vitri = tbl_danhmuc.getSelectedRow();
				String ma = (String) tbl_danhmuc.getValueAt(vitri, 1);
				if (!(ma == null)) {
					dao.Delete(ma);
					this.fillSachToTblSach();
					clear();
					if (vitri >= tbl_danhmuc.getRowCount()) {
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
			JOptionPane.showMessageDialog(null, "Danh mục có sách không thể xóa");
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
		setTitle("Quản Lý Danh Mục");
		setSize(1350, 700);
		setLocationRelativeTo(null);

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
		JMenuItem mi6 = new JMenuItem("Nhà xuất bản", null);
		mi6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new Form_NhaXuatBan();
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

		pnlQuanLyDanhMuc.setLayout(new BorderLayout());
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
		pnlShow.add(new JLabel("Mã Danh Mục:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_madanhmuc, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Tên Danh Mục:"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_tendanhmuc, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));

		pnlTopNORTH.add(lbl_dsdanhmuc, BorderLayout.LINE_START);
		pnlTopNORTH.add(pnlChucNang, BorderLayout.LINE_END);
		// add pnlTop
		pnlTop.add(pnlTopNORTH, BorderLayout.NORTH);
		pnlTop.add(pnlShow, BorderLayout.CENTER);

		// add pnlBottom
		pnlBottom.add(pnlBotNORTH, BorderLayout.NORTH);
		pnlBottom.add(tbl_danhmuc, BorderLayout.CENTER);

		// add pnlRight
		pnlRight.add(pnlTop, BorderLayout.NORTH);
		pnlRight.add(pnlBottom, BorderLayout.CENTER);
		// add pnlTimKiem
		pnlds.add(new JLabel("DANH SÁCH DANH MỤC"));
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

		tbl_danhmuc.setPreferredScrollableViewportSize(new Dimension(200, 200));
		tbl_danhmuc.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tbl_danhmuc.setFillsViewportHeight(true);
		JScrollPane scrollPane2 = new JScrollPane(tbl_danhmuc);
		pnlBottom.add(scrollPane2);

		pnlRight.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		pnlBotNORTH.setBorder(BorderFactory.createEmptyBorder(0, -4, 5, 2));
		pnlTopNORTH.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		pnlBottom.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		pnlQuanLyDanhMuc.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		// add frame
		pnlQuanLyDanhMuc.add(pnlLeft, BorderLayout.WEST);
		pnlQuanLyDanhMuc.add(pnlRight, BorderLayout.CENTER);
		this.add(pnlQuanLyDanhMuc);

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

		tbl_danhmuc.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tbl_danhmucMouseClicked(evt);
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
		btn_them.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Add.png"));
		btn_sua.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\edit.png"));
		btn_xoa.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Delete.png"));
		btn_luu.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Tick.png"));
		btn_boqua.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Exit.png"));
		m1.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Brick house.png"));
		m2.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Gear.png"));
		m3.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Info.png"));
		mi5.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Book.png"));
		mi6.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Clien list.png"));
		mi7.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\user.png"));
		mi8.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Bar chart.png"));
		mi9.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Notes.png"));
		jmn_ketthuc.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Log out.png"));
		jmn_dangxaut.setIcon(new ImageIcon("C:\\Users\\USER\\Documents\\NetBeansProjects\\QLTV\\src\\Icon\\Exit.png"));
	}

	// hàm chọn các dòng trên bảng sách
	private void tbl_danhmucMouseClicked(MouseEvent evt) {
		vitri = tbl_danhmuc.getSelectedRow();
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
		txt_madanhmuc.requestFocus();
		cnluu = true;
	}

	private void btn_suaActionPerformed(ActionEvent evt) {
		setStatus(true);
		txt_madanhmuc.setEditable(false);
		txt_tendanhmuc.requestFocus();
		cnluu = false;
	}

	private void tbl_thamchieuMouseClicked(MouseEvent evt) {
		int n = tbl_thamchieu.getSelectedRow();
		if (n == 0) {
			new Form_QuanLySach();
		} else if (n == 1) {
			new Form_NhaXuatBan();
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
