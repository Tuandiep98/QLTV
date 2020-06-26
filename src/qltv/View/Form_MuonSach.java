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
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
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
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import QLTV.DAO.MuonSach_DB;
import QLTV.DAO.Sach_DB;
import QLTV.Model.Sach;
import QLTV.Model.MuonSach;

import QLTV.tien_ich.datahelper;
import QLTV.tien_ich.dialoghelp;

public class Form_MuonSach extends JFrame {
	JPanel pnlquanlyms = new JPanel();
	JPanel pnlLeft = new JPanel();
	JPanel pnlRight = new JPanel();
	JPanel pnlTop = new JPanel();
	JPanel pnlBottom = new JPanel();
	JPanel pnlTopNORTH = new JPanel();
	JPanel pnlShow = new JPanel();
	JPanel pnlBotNORTH = new JPanel();
	JPanel pnlds = new JPanel();
	JPanel pnlChucNang = new JPanel();
	JLabel lbl_chude = new JLabel();
	JLabel lbl_dsmuonsach = new JLabel("MƯỢN SÁCH");
	JTextField txt_mathe = new JTextField(16);
	JTextField txt_tennguoimuon = new JTextField(16);
	JTextField txt_soluongmuon = new JTextField(16);
	JTextField txt_soluongtra = new JTextField(16);
	JTextField txt_ngaymuon = new JTextField(16);
	JTextField txt_ngaytra = new JTextField(16);

	private JComboBox cbo_tensachmuon, cbo_tensachtra;

	// JButton
	JButton btn_them = new JButton("Thêm");
	JButton btn_sua = new JButton("Sửa");
	JButton btn_xoa = new JButton("Xóa");
	JButton btn_luu = new JButton("Lưu");
	JButton btn_boqua = new JButton("Bỏ qua");

	// JTable
	private String header1[] = { "STT", "Danh Mục" };
	Object[][] data1 = { { "1", "Sách" }, { "2", "Danh Mục" }, { "3", "Nhà Xuất Bản" }, { "4", "Người Đọc" } };
	JTable tbl_thamchieu = new JTable(new DefaultTableModel(data1, header1));
	private String header2[] = { "STT", "Mã Người Mượn", "Tên Người Mượn", "Mã Sách Mượn", "Ngày Mượn", "Số Lượng Mượn",
			"Mã Sách Trả", "Ngày Trả", "Số Lượng Trả", "Còn Lại" };
	private DefaultTableModel tblModel2 = new DefaultTableModel(header2, 0);
	JTable tbl_ms = new JTable(tblModel2);

	DefaultTableModel model;
	int vitri;
	boolean cnluu;
	boolean cnkhoa;
	List<Sach> listS = null;
	List<MuonSach> listms = null;
	Sach_DB dao = new Sach_DB();
	MuonSach_DB daoms = new MuonSach_DB();

	public Form_MuonSach() {
		init();
		vitri = 0;
		fillTotblmuonsach();
		fillComBoBOx();
		listms = daoms.select();
		setModel(vitri);
		setStatus(false);
		rowCountTable();

	}

	private void fillTotblmuonsach() {
		model = (DefaultTableModel) tbl_ms.getModel();
		model.setRowCount(0);
		try {
			listms = daoms.select();
			int stt = 0;

			for (MuonSach s : listms) {
				stt++;

				Object row[] = new Object[] { stt, s.getMathe(), s.getTennguoimuon(), s.getTieude(), s.getNgaymuon(),
						s.getSobanluu(), s.getTensachtra(), s.getNgaytra(), s.getSoluongtra(),
						s.getSobanluu() - s.getSoluongtra() };
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillComBoBOx() {
		try {
			listS = new Sach_DB().select();
			for (Sach sach : listS) {
				cbo_tensachmuon.addItem(sach.getTiede());
			}
			listS = new Sach_DB().select();
			for (Sach s : listS) {
				cbo_tensachtra.addItem(s.getTiede());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setStatus(boolean tt) {
		txt_mathe.setEditable(tt);
		txt_tennguoimuon.setEditable(tt);
		txt_soluongmuon.setEditable(tt);
		txt_soluongtra.setEditable(tt);
		txt_ngaymuon.setEditable(tt);
		txt_ngaytra.setEditable(tt);
		btn_luu.setEnabled(tt);
		btn_boqua.setEnabled(tt);
		btn_them.setEnabled(!tt);
		btn_sua.setEnabled(!tt);
		btn_xoa.setEnabled(!tt);
		cbo_tensachmuon.setEnabled(tt);
		cbo_tensachtra.setEnabled(tt);

	}

	private void setModel(int vitri) {
		if (listms.size() > 0) {
			MuonSach s = listms.get(vitri);

			txt_mathe.setText(s.getMathe());
			txt_tennguoimuon.setText(s.getTennguoimuon());
			for (int i = 0; i < listS.size(); i++) {
				Sach sach = listS.get(i);
				if (sach.getMasach().equals(s.getTieude())) {
					cbo_tensachmuon.setSelectedItem(sach.getTiede());
					break;
				}
			}
			txt_ngaymuon.setText(s.getNgaymuon());
			txt_soluongmuon.setText(Integer.toString(s.getSobanluu()));
			for (int i = 0; i < listS.size(); i++) {
				Sach sach1 = listS.get(i);
				if (sach1.getMasach().equals(s.getTieude())) {
					cbo_tensachtra.setSelectedItem(sach1.getTiede());
					break;
				}
			}
			txt_ngaytra.setText(s.getNgaytra());
			txt_soluongtra.setText(Integer.toString(s.getSoluongtra()));

			tbl_ms.setRowSelectionInterval(vitri, vitri);
		}

	}

	public int getSoBLuu() {
		int sobanluu = 0;
		for (int i = 0; i < listS.size(); i++) {
			Sach sach = listS.get(i);
			if (sach.getTiede().equals(cbo_tensachmuon.getSelectedItem())) {
				sobanluu = sach.getSobanluu();
				break;
			}
		}
		return sobanluu;
	}

	public boolean check() {

		String mathe = txt_mathe.getText();

		if (!(datahelper.checkChar(mathe, 10, "mã người mượn"))) {
			txt_mathe.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(txt_tennguoimuon.getText(), 30, "tên người mượn"))) {
			txt_tennguoimuon.requestFocus();
			return false;
		}
		if (cbo_tensachmuon.getSelectedIndex() == 0) {
			dialoghelp.alert(null, "Vui lòng chọn tên sách mượn");
			cbo_tensachmuon.requestFocus();
			return false;
		}
		SimpleDateFormat dateFormater = new SimpleDateFormat("dd/MM/yyyy");
		ParsePosition position = new ParsePosition(0);
		String ngay = txt_ngaymuon.getText();
		if (ngay.length() == 0) {
			dialoghelp.alert(null, "Không được để trống ngày mượn!");
			txt_ngaymuon.requestFocus();
			return false;
		} else if (dateFormater.parse(ngay, position) == null) {
			dialoghelp.alert(null, "Sai định dạng ngày, vui lòng nhập lại!");
			txt_ngaymuon.requestFocus();
			return false;
		}

		if (!(datahelper.checkNum(txt_soluongmuon.getText(), 5, 0, 300, "số lượng mượn"))) {
			txt_soluongmuon.requestFocus();
			return false;
		}
		if (Integer.parseInt(txt_soluongmuon.getText()) > getSoBLuu()) {
			dialoghelp.alert(null, "Số lượng sách có trong thư viện là " + getSoBLuu());
			txt_soluongmuon.requestFocus();
			return false;
		}
		return true;

	}

	private void clear() {
		txt_mathe.setText("");
		txt_tennguoimuon.setText("");
		txt_soluongmuon.setText("");
		txt_soluongtra.setText("");
		txt_ngaymuon.setText("");
		txt_ngaytra.setText("");
		cbo_tensachmuon.setSelectedIndex(0);
		cbo_tensachtra.setSelectedIndex(0);

	}

	private void rowCountTable() {
		if (tbl_ms.getRowCount() == 0) {
			btn_sua.setEnabled(false);
			btn_xoa.setEnabled(false);
		} else {
			setModel(vitri);
			btn_sua.setEnabled(true);
			btn_xoa.setEnabled(true);
		}
	}

	private MuonSach getModel() {

		String mathe, tennguoimuon, masach = null, ngaymuon, tensachtra = null, ngaytra;
		int soluongmuon, soluongtra;
		MuonSach s = null;
		if (check()) {
			mathe = txt_mathe.getText().trim().toUpperCase();

			for (int i = 0; i < listS.size(); i++) {
				Sach sach = listS.get(i);
				if (sach.getTiede().equals(cbo_tensachmuon.getSelectedItem())) {
					masach = sach.getMasach();
					break;
				}
			}
			for (int i = 0; i < listS.size(); i++) {
				Sach sach1 = listS.get(i);
				if (sach1.getTiede().equals(cbo_tensachtra.getSelectedItem())) {
					tensachtra = sach1.getMasach();
					break;
				}
			}
			String tnm = txt_tennguoimuon.getText().trim();
			tennguoimuon = tnm;
			soluongmuon = Integer.parseInt(txt_soluongmuon.getText());
			if (txt_soluongtra.getText().equals("")) {
				soluongtra = 0;
			} else {
				soluongtra = Integer.parseInt(txt_soluongtra.getText());
			}
			String nm = txt_ngaymuon.getText().trim();
			ngaymuon = nm;
			String nt = txt_ngaytra.getText().trim();
			ngaytra = nt;
			s = new MuonSach(mathe, tennguoimuon, masach, ngaymuon, soluongmuon, tensachtra, ngaytra, soluongtra);

		}
		return s;
	}

	private void them() {
		try {
			MuonSach s = getModel();
			if (!(s == null)) {
				int kt = 0;
				for (int i = 0; i < daoms.select().size(); i++) {
					if (daoms.select().get(i).getMathe().equals(s.getMathe())) {
						dialoghelp.alert(null, "Mã thẻ đã tồn tại, vui lòng nhập mã mới!");
						txt_mathe.requestFocus();
						kt++;
						break;
					}
				}
				if (kt == 0) {
					daoms.insert(s);
					this.fillTotblmuonsach();
					setStatus(false);
					for (int i = 0; i < listms.size(); i++) {
						if (s.getMathe().equals(listms.get(i).getMathe())) {
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
			MuonSach s = getModel();
			if (!(s == null)) {
				daoms.update(s);
				this.fillTotblmuonsach();
				setStatus(false);
				for (int i = 0; i < listms.size(); i++) {
					if (s.getMathe().equals(listms.get(i).getMathe())) {
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
			if (tbl_ms.getRowCount() > 0) {
				vitri = tbl_ms.getSelectedRow();
				String ma = (String) tbl_ms.getValueAt(vitri, 1);
				if (!(ma == null)) {
					daoms.Delete(ma);
					this.fillTotblmuonsach();
					clear();
					if (vitri >= tbl_ms.getRowCount()) {
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
		setTitle("Quản Lý Mượn Sách");
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

				int chose = JOptionPane.showConfirmDialog(null, "bạn có muốn thoát không ?", "hệ thống quản lý sách",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
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
		JMenuItem mi6 = new JMenuItem("Danh Mục", null);
		mi6.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				new Form_DanhMuc();
				setVisible(false);
			}
		});
		JMenuItem mi10 = new JMenuItem("Nhà Xuất Bản", null);
		mi10.addActionListener(new java.awt.event.ActionListener() {
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

		m2.add(mi5);
		m2.add(mi6);
		m2.add(mi10);
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

		pnlquanlyms.setLayout(new BorderLayout());

		pnlRight.setLayout(new BorderLayout());
		pnlLeft.setLayout(new BorderLayout());
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

		// JComboBox
		cbo_tensachmuon = new JComboBox();
		cbo_tensachmuon.setModel(new DefaultComboBoxModel<>(new String[] { "Chọn sách mượn" }));
		cbo_tensachtra = new JComboBox();
		cbo_tensachtra.setModel(new DefaultComboBoxModel<>(new String[] { "Chọn sách trả" }));

		// add pnlShow
		pnlShow.add(new JLabel("Mã Người Mượn"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_mathe, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Tên Người Mượn"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_tennguoimuon, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Tên Sách Mượn"), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(cbo_tensachmuon, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 229, 5));
		pnlShow.add(new JLabel("Ngày Mượn"), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_ngaymuon, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Số Lượng Mượn"), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_soluongmuon, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Tên Sách Trả"), new GridBagConstraints(5, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(cbo_tensachtra, new GridBagConstraints(6, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 204, 5));
		pnlShow.add(new JLabel("Ngày Trả"), new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(txt_ngaytra, new GridBagConstraints(6, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 200, 5));
		pnlShow.add(new JLabel("Số Lượng Trả"), new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(txt_soluongtra, new GridBagConstraints(6, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 200, 5));

		pnlTopNORTH.add(lbl_dsmuonsach, BorderLayout.LINE_START);
		pnlTopNORTH.add(pnlChucNang, BorderLayout.LINE_END);
		// add pnlTop
		pnlTop.add(pnlTopNORTH, BorderLayout.NORTH);
		pnlTop.add(pnlShow, BorderLayout.CENTER);
		// add pnlds
		pnlds.add(new JLabel("DANH SÁCH MƯỢN SÁCH"));
		// add pnlBotNORTH
		pnlBotNORTH.add(pnlds, BorderLayout.WEST);
		// add pnlBottom
		pnlBottom.add(pnlBotNORTH, BorderLayout.NORTH);
		pnlBottom.add(tbl_ms, BorderLayout.CENTER);

		// add pnlRight
		pnlRight.add(pnlTop, BorderLayout.NORTH);
		pnlRight.add(pnlBottom, BorderLayout.CENTER);

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

		tbl_ms.setPreferredScrollableViewportSize(new Dimension(200, 200));
		tbl_ms.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tbl_ms.setFillsViewportHeight(true);
		JScrollPane scrollPane2 = new JScrollPane(tbl_ms);
		pnlBottom.add(scrollPane2);

		pnlRight.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		pnlBotNORTH.setBorder(BorderFactory.createEmptyBorder(0, -4, 5, 2));
		pnlTopNORTH.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		pnlBottom.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		pnlquanlyms.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		// add frame
		pnlquanlyms.add(pnlLeft, BorderLayout.WEST);
		pnlquanlyms.add(pnlRight, BorderLayout.CENTER);
		this.add(pnlquanlyms);
		//addbutton
		btn_them.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				btn_themActionPerformed(evt);
			}
		});

		btn_sua.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
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
		tbl_ms.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tbl_msMouseClicked(evt);
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
		mi8.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Bar chart.png"));
		mi10.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Clien list.png"));
		jmn_ketthuc.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Log out.png"));
		jmn_dangxaut.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Exit.png"));
	}

	// hàm chọn các dòng trên bảng sách
	private void tbl_msMouseClicked(MouseEvent evt) {
		if (cnkhoa == false) {
			vitri = tbl_ms.getSelectedRow();
			setModel(vitri);
		}
	}

	private void btn_luuActionPerformed(ActionEvent evt) {
		luu();
	}

	private void btn_boquaActionPerformed(ActionEvent evt) {
		clear();
		setModel(vitri);
		setStatus(false);
	}

	private void btn_themActionPerformed(ActionEvent e) {
		setStatus(true);
		clear();
		txt_mathe.requestFocus();
		cbo_tensachtra.setEnabled(false);
		txt_ngaytra.setEditable(false);
		txt_soluongtra.setEditable(false);
		cnluu = true;

	}

	private void btn_suaActionPerformed(ActionEvent evt) {
		setStatus(true);
		txt_mathe.setEditable(false);
		txt_tennguoimuon.requestFocus();
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
		}

	}

	public static void main(String[] args) {
		new Form_DangNhap();
	}

}
