/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLTV.View;

import QLTV.DAO.DanhMuc_DB;
import QLTV.DAO.NhaXB_DB;
import QLTV.DAO.Sach_DB;
import QLTV.Model.Danhmuc;
import QLTV.Model.Nhaxb;
import QLTV.Model.Sach;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
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
import javax.swing.JSplitPane;
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
import QLTV.tien_ich.datahelper;
import QLTV.tien_ich.dialoghelp;
import QLTV.tien_ich.jdbcHelper;

/**
 *
 * @author Admin
 */
public class Form_QuanLySach extends JFrame {

	JPanel pnlQuanLySach = new JPanel();
	JPanel pnlLeft = new JPanel();
	JPanel pnlRight = new JPanel();
	JPanel pnlTop = new JPanel();
	JPanel pnlBottom = new JPanel();
	JPanel pnlTopNORTH = new JPanel();
	JPanel pnlShow = new JPanel();
	JPanel pnlBotNORTH = new JPanel();
	JPanel pnlTimKiem = new JPanel();
	JPanel pnlChucNang = new JPanel();
	JLabel lbl_chude = new JLabel();
	JTextField txt_masach = new JTextField(16);
	JTextField txt_tieude = new JTextField(16);
	JTextField txt_sotrang = new JTextField(16);
	JTextField txt_tacgia = new JTextField(16);
	JTextField txt_gia = new JTextField(16);
	JTextField txt_namxb = new JTextField(16);
	JTextField txt_sobanluu = new JTextField(16);
	JTextField txt_test = new JTextField(16);
	JTextField txtHSD = new JTextField(16);
	JTextField txtGiaNhap = new JTextField(16);
	JTextField txtGiaBan = new JTextField(16);
	JTextField txtSLTon = new JTextField(16);
	JTextField txt_timkiem = new JTextField(16);

	private JComboBox cbo_danhmuc, cbo_nxb;

	// JCheckBox
	JCheckBox chk_bandientu = new JCheckBox("Bản điện tử");
	JCheckBox chk_muonvenha = new JCheckBox("Được mượn về nhà");
	JCheckBox chk_chude = new JCheckBox("Chỉ hiển thị danh mục có sách");
	// JButton
	JButton btn_them = new JButton("Thêm");
	JButton btn_sua = new JButton("Sửa");
	JButton btn_xoa = new JButton("Xóa");
	JButton btn_luu = new JButton("Lưu");
	JButton btn_boqua = new JButton("Bỏ qua");
	JButton btn_timkiem = new JButton("Tìm");
	JButton btn_kietxuat = new JButton("Kết xuất");
	JRadioButton rdo_biacung = new JRadioButton("Bìa cứng");
	JRadioButton rdo_biamem = new JRadioButton("Bìa mềm");
	private ButtonGroup btg;

	// JTable
	private String header1[] = { "STT", "Danh Mục" };
	private String header2[] = { "STT", "Mã Sách", "Tiêu Đề", "Số bản Lưu" };
	private DefaultTableModel tblModel1 = new DefaultTableModel(header1, 0);
	private DefaultTableModel tblModel2 = new DefaultTableModel(header2, 0);
	JTable tbl_danhmuc = new JTable(tblModel1);
	JTable tbl_sach = new JTable(tblModel2);

	DefaultTableModel model;
	int vitri;
	boolean cnluu;
	boolean cnkhoa;
	List<Sach> listS = null;
	List<Danhmuc> listcd = null;
	List<Nhaxb> listnxb = null;
	Sach_DB dao = new Sach_DB();
	DanhMuc_DB daocd = new DanhMuc_DB();

	public Form_QuanLySach() {
		init();
		vitri = 0;
		setStatus(false);
		fillComBoBOx();
		listcd = daocd.select();
		fillSachToTblChude(listcd);
		tbl_danhmuc.setRowSelectionInterval(vitri, vitri);
		chude_Sach();
		setModel(vitri);

	}

	private void fillSachToTblSach(List<Sach> list) {
		model = (DefaultTableModel) tbl_sach.getModel();
		model.setRowCount(0);
		try {
//            listS = dao.select();
			int stt = 0;
			for (Sach s : list) {
				stt++;
				Object row[] = new Object[] { stt, s.getMasach(), s.getTiede(), s.getSobanluu() };
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fillSachToTblChude(List<Danhmuc> list) {
		model = (DefaultTableModel) tbl_danhmuc.getModel();
		model.setRowCount(0);
		try {
			int stt = 0;
			for (Danhmuc cd : list) {
				stt++;
				Object row[] = new Object[] { stt, cd.getTendm() };
				model.addRow(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void chude_Sach() {
		int n = tbl_danhmuc.getSelectedRow();
		if (cnkhoa == false) {
			String tendm = (String) tbl_danhmuc.getValueAt(n, 1);
			for (int i = 0; i < listcd.size(); i++) {
				if (listcd.get(i).getTendm().equals(tendm)) {
					String madm = listcd.get(i).getMadm();
					listS = dao.selectDanhmuc(madm);
					fillSachToTblSach(listS);
					if (tbl_sach.getRowCount() == 0) {
						clear();
						btn_sua.setEnabled(false);
						btn_xoa.setEnabled(false);
					} else {
						vitri = 0;
						setModel(vitri);
						btn_sua.setEnabled(true);
						btn_xoa.setEnabled(true);
					}
					lbl_chude.setText("Sách trong danh mục " + tendm);
					break;
				}
			}
		}

	}

	private void fillComBoBOx() {
		try {
			listnxb = new NhaXB_DB().select();
			for (Nhaxb nxb : listnxb) {
				cbo_nxb.addItem(nxb.getTennxb());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setStatus(boolean tt) {
		txt_masach.setEditable(tt);
		txt_gia.setEditable(tt);
		txt_namxb.setEditable(tt);
		txt_sobanluu.setEditable(tt);
		txt_sotrang.setEditable(tt);
		txt_tacgia.setEditable(tt);
		txt_tieude.setEditable(tt);
		btn_luu.setEnabled(tt);
		btn_boqua.setEnabled(tt);
		btn_them.setEnabled(!tt);
		btn_sua.setEnabled(!tt);
		btn_xoa.setEnabled(!tt);
//        cbo_danhmuc.setEnabled(tt);
		cbo_nxb.setEnabled(tt);
		rdo_biacung.setEnabled(tt);
		rdo_biamem.setEnabled(tt);
		chk_bandientu.setEnabled(tt);
		chk_muonvenha.setEnabled(tt);
		tbl_danhmuc.setEnabled(!tt);
		tbl_sach.setEnabled(!tt);
		chk_chude.setEnabled(!tt);
		cnkhoa = tt;
	}

	private void setModel(int vitri) {
		if (listS.size() > 0) {
			Sach s = listS.get(vitri);

			txt_masach.setText(s.getMasach());
			txt_tieude.setText(s.getTiede());
			txt_sotrang.setText(Integer.toString(s.getSotrang()));
			txt_tacgia.setText(s.getTacgia());
			txt_gia.setText(Double.toString(s.getGia()));
			txt_namxb.setText(Integer.toString(s.getNamxb()));
			txt_sobanluu.setText(Integer.toString(s.getSobanluu()));
			if (s.getPhienban() == 1) {
				rdo_biacung.setSelected(true);
			} else {
				rdo_biamem.setSelected(true);
			}
			if (s.getBandientu() == 1) {
				chk_bandientu.setSelected(true);
			} else {
				chk_bandientu.setSelected(false);
			}
			if (s.getMuonvenha() == 1) {
				chk_muonvenha.setSelected(true);
			} else {
				chk_muonvenha.setSelected(false);
			}
			for (int i = 0; i < listnxb.size(); i++) {
				Nhaxb nxb = listnxb.get(i);
				if (nxb.getManxb().equals(s.getManxb())) {
					cbo_nxb.setSelectedItem(nxb.getTennxb());
					break;
				}
			}

			tbl_sach.setRowSelectionInterval(vitri, vitri);
		}

	}

	public boolean check() {

		String masach = txt_masach.getText();

		if (!(datahelper.checkChar(masach, 10, "mã sách"))) {
			txt_masach.requestFocus();
			return false;
		}

		if (!(datahelper.checkChar(txt_tieude.getText(), 25, "tiêu đề"))) {
			txt_tieude.requestFocus();
			return false;
		}
		if (!(datahelper.checkChar(txt_tacgia.getText(), 20, "tác giả"))) {
			txt_tacgia.requestFocus();
			return false;
		}
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String Dateformat = formater.format(date);
		int year = Integer.parseInt(Dateformat.substring(0, 4));

		if (!(datahelper.checkNum(txt_namxb.getText(), 100, 1900, year, "năm xuất bản"))) {
			txt_namxb.requestFocus();
			return false;
		}
		if (!(datahelper.checkNum(txt_sotrang.getText(), 10, 1, 10000, "số trang"))) {
			txt_sotrang.requestFocus();
			return false;
		}
		if (!(datahelper.checkNumDouble(txt_gia.getText(), 10, 0, 10000000, "giá"))) {
			txt_gia.requestFocus();
			return false;
		}
		if (!(datahelper.checkNum(txt_sobanluu.getText(), 3, 0, 200, "số bản lưu"))) {
			txt_sobanluu.requestFocus();
			return false;
		}
		try {
			int n = tbl_danhmuc.getSelectedRow();
			String macd = listcd.get(n).getMadm();
		} catch (Exception e) {
			dialoghelp.alert(null, "chưa chọn danh mục");
			return false;
		}
		if (cbo_nxb.getSelectedIndex() == 0) {
			dialoghelp.alert(null, "vui lòng chọn nhà xuất bản");
			cbo_nxb.requestFocus();
			return false;
		}
		return true;
	}

	private void clear() {
		txt_masach.setText("");
		txt_tieude.setText("");
		txt_tacgia.setText("");
		txt_gia.setText("");
		txt_namxb.setText("");
		txt_sobanluu.setText("");
		txt_sotrang.setText("");
		txt_timkiem.setText("");
		cbo_nxb.setSelectedIndex(0);
		chk_bandientu.setSelected(false);
		chk_muonvenha.setSelected(false);
		rdo_biamem.setSelected(true);
	}

	private Sach getModel() {
		String masach, macd = null, manxb = null, tieude, tacgia;
		int namxuatban, sotrang, sobanluu, phienban, bandientu, muonvenha;
		double gia;
		Sach s = null;
		if (check()) {
			masach = txt_masach.getText().trim().toUpperCase();

			int n = tbl_danhmuc.getSelectedRow();
			macd = listcd.get(n).getMadm();

			for (int i = 0; i < listnxb.size(); i++) {
				Nhaxb nbx = listnxb.get(i);
				if (nbx.getTennxb().equals(cbo_nxb.getSelectedItem())) {
					manxb = nbx.getManxb();
					break;
				}

			}
			String td = txt_tieude.getText().trim(), tdUper = "";
			String split[] = td.split(" ");
			for (String string : split) {
				tdUper += string.substring(0, 1).toUpperCase() + string.substring(1) + " ";
			}
			tieude = tdUper.trim();
			String tg = txt_tacgia.getText().trim(), tgUper = "";
			split = tg.split(" ");
			for (String string : split) {
				tgUper += string.substring(0, 1).toUpperCase() + string.substring(1) + " ";
			}
			tacgia = tgUper.trim();
			namxuatban = Integer.parseInt(txt_namxb.getText());
			sotrang = Integer.parseInt(txt_sotrang.getText());
			sobanluu = Integer.parseInt(txt_sobanluu.getText());
			if (btg.getSelection().equals(rdo_biacung.getModel())) {
				phienban = 1;
			} else {
				phienban = 0;
			}
			if (chk_bandientu.isSelected() == true) {
				bandientu = 1;
			} else {
				bandientu = 0;
			}
			if (chk_muonvenha.isSelected() == true) {
				muonvenha = 1;
			} else {
				muonvenha = 0;
			}
			gia = Double.parseDouble(txt_gia.getText());
			s = new Sach(masach, macd, manxb, tieude, tacgia, namxuatban, sotrang, sobanluu, phienban, bandientu,
					muonvenha, gia);
		}
		return s;
	}

	private void them() {
		try {
			Sach s = getModel();
			if (!(s == null)) {
				int kt = 0;
				for (int i = 0; i < dao.select().size(); i++) {
					if (dao.select().get(i).getMasach().equals(s.getMasach())) {
						dialoghelp.alert(null, "Mã sách đã tồn tại, vui lòng nhập lại");
						txt_masach.requestFocus();
						kt++;
						break;
					}
				}
				if (kt == 0) {
					dao.insert(s);
					setStatus(false);
					chude_Sach();

					for (int i = 0; i < listS.size(); i++) {
						if (s.getMasach().equals(listS.get(i).getMasach())) {
							vitri = i;
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
			Sach s = getModel();
			if (!(s == null)) {
				dao.update(s);
				setStatus(false);
				chude_Sach();

				for (int i = 0; i < listS.size(); i++) {
					if (s.getMasach().equals(listS.get(i).getMasach())) {
						vitri = i;
						setModel(vitri);
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
			if (tbl_sach.getRowCount() > 0) {
				int n = tbl_sach.getSelectedRow();
				String ma = (String) tbl_sach.getValueAt(vitri, 1);
				if (!(ma == null)) {
					dao.Delete(ma);
					chude_Sach();
					vitri = n;
					clear();

					if (vitri >= tbl_sach.getRowCount()) {
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
			JOptionPane.showMessageDialog(null, "Sách có người mượn không thể xóa");
		}
	}

	private void luu() {
		if (cnluu == true) {
			them();
		} else {
			sua();
		}
	}

	public void kietxuat() throws IOException {
		JFileChooser chooser = new JFileChooser();
		int jfc = chooser.showSaveDialog(chooser);
		if (jfc == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			Workbook wb = new HSSFWorkbook();
			Sheet sh = (Sheet) wb.createSheet("sách");
			String[] columns = { "STT", "Mã sách", "tiêu đề", "số bản lưu" };
			Row headerRow = sh.createRow(0);
			for (int i = 0; i < columns.length; i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(columns[i]);
			}
			int rowID = 1;
			for (int i = 0; i < listS.size(); i++) {
				Sach s = listS.get(i);
				Row row = sh.createRow(rowID++);
				row.createCell(0).setCellValue(i + 1);
				row.createCell(1).setCellValue(s.getMasach());
				row.createCell(2).setCellValue(s.getTiede());
				row.createCell(3).setCellValue(s.getSobanluu());
			}

			for (int i = 0; i < columns.length; i++) {
				sh.autoSizeColumn(i);
			}
			try {
				FileOutputStream out = new FileOutputStream(file + ".xls");
				wb.write(out);
				out.close();
				//wb.close();
//                btnxtt.setEnabled(false);
				JOptionPane.showMessageDialog(null, "Xuất thông tin thành công");

				File fil1 = file;
				fil1.getParentFile().mkdirs();

				try {
					FileOutputStream outFile = new FileOutputStream(fil1);
					wb.write(outFile);
					outFile.close();
					//wb.close();
					Desktop.getDesktop().open(fil1);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private void init() {
		setTitle("Quản Lý Sách");
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
					String sql = "delete book where masach = ?";
					System.exit(0);
				}
			}
		});
		m1.add(jmn_dangxaut);
		m1.add(jmn_ketthuc);
		JMenu m2 = new JMenu("Quản lý");
//        JMenuItem mi4 = new JMenuItem("danh mục sách", null);
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
//        m2.add(mi4);
		m2.add(mi5);
		m2.add(mi6);
		m2.add(mi7);
		m2.add(mi9);
		JMenu m3 = new JMenu("Báo cáo");
		JMenuItem mi8 = new JMenuItem("thống kê sách", null);

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

		pnlQuanLySach.setLayout(new BorderLayout());
		pnlLeft.setLayout(new BorderLayout());
		pnlRight.setLayout(new BorderLayout());
		pnlTop.setLayout(new BorderLayout());
		pnlTopNORTH.setLayout(new BorderLayout());
		pnlShow.setLayout(new GridBagLayout());
//        pnlShow.setBackground(Color.red);
		pnlBottom.setLayout(new BorderLayout());
		pnlBotNORTH.setLayout(new BorderLayout());
		pnlTimKiem.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnlChucNang.setLayout(new FlowLayout(FlowLayout.RIGHT));
		GridBagConstraints gbc = new GridBagConstraints();

		// add pnlLeft
		pnlLeft.add(chk_chude, BorderLayout.NORTH);
//        pnlLeft.add(tbl_danhmuc, BorderLayout.SOUTH);

		// add pnlTopNORTH
		pnlChucNang.add(btn_them);
		pnlChucNang.add(btn_sua);
		pnlChucNang.add(btn_xoa);
		pnlChucNang.add(btn_luu);
		pnlChucNang.add(btn_boqua);

		// JComboBox
		cbo_nxb = new JComboBox();
		cbo_nxb.setModel(new DefaultComboBoxModel<>(new String[] { "chọn nhà xuất bản" }));

		// add pnlShow
		pnlShow.add(new JLabel("Mã sách:"), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_masach, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Nhà xuất bản:"), new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 100, 0, 0), 0, 0));
		pnlShow.add(cbo_nxb, new GridBagConstraints(3, 0, 3, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 320, 0));
		pnlShow.add(new JLabel("Tiêu đề:"), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_tieude, new GridBagConstraints(1, 1, 3, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 400, 5));
		pnlShow.add(new JLabel("Số trang:"), new GridBagConstraints(4, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 252, 0, 0), 0, 0));
		pnlShow.add(txt_sotrang, new GridBagConstraints(5, 1, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 200, 5));
		pnlShow.add(new JLabel("Giá:"), new GridBagConstraints(4, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 252, 0, 0), 0, 0));
		pnlShow.add(txt_gia, new GridBagConstraints(5, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 200, 5));
		pnlShow.add(new JLabel("Số bản lưu:"), new GridBagConstraints(4, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 252, 0, 0), 0, 0));
		pnlShow.add(txt_sobanluu, new GridBagConstraints(5, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 200, 5));
		pnlShow.add(new JLabel("Tác giả:"), new GridBagConstraints(0, 2, 3, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_tacgia, new GridBagConstraints(1, 2, 3, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 400, 5));
		pnlShow.add(new JLabel("Năm xuất bản:"), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(txt_namxb, new GridBagConstraints(1, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 225, 5));
		pnlShow.add(new JLabel("Phiên bản:"), new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 0, 0, 0), 0, 0));
		pnlShow.add(rdo_biacung, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(rdo_biamem, new GridBagConstraints(1, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(chk_bandientu, new GridBagConstraints(4, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));
		pnlShow.add(chk_muonvenha, new GridBagConstraints(5, 4, 1, 1, 0.0, 0.0, GridBagConstraints.EAST,
				GridBagConstraints.NONE, new Insets(5, 20, 0, 0), 0, 0));

//        add radio vào buttongroup
		btg = new ButtonGroup();
		btg.add(rdo_biacung);
		btg.add(rdo_biamem);

		pnlTopNORTH.add(lbl_chude, BorderLayout.LINE_START);
		pnlTopNORTH.add(pnlChucNang, BorderLayout.LINE_END);
		// add pnlTop
		pnlTop.add(pnlTopNORTH, BorderLayout.NORTH);
		pnlTop.add(pnlShow, BorderLayout.CENTER);

		// add pnlTimKiem
		pnlTimKiem.add(new JLabel("Tìm kiếm"));
		pnlTimKiem.add(txt_timkiem);
		pnlTimKiem.add(btn_timkiem);

		// add pnlBotNORTH
		pnlBotNORTH.add(pnlTimKiem, BorderLayout.WEST);
		pnlBotNORTH.add(btn_kietxuat, BorderLayout.EAST);

		// add pnlBottom
		pnlBottom.add(pnlBotNORTH, BorderLayout.NORTH);
		pnlBottom.add(tbl_sach, BorderLayout.CENTER);

		// add pnlRight
		pnlRight.add(pnlTop, BorderLayout.NORTH);
		pnlRight.add(pnlBottom, BorderLayout.CENTER);

		tbl_danhmuc.setPreferredScrollableViewportSize(new Dimension(250, 280));
		tbl_danhmuc.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
		tbl_danhmuc.setFillsViewportHeight(true);
		JScrollPane scrollPane1 = new JScrollPane(tbl_danhmuc);
		TableColumn column = null;
		for (int i = 0; i < 2; i++) {
			column = tbl_danhmuc.getColumnModel().getColumn(i);
			if (i == 1) {
				column.setPreferredWidth(160); // cột thứ 2 sẽ rộng hơn
			} else {
				column.setPreferredWidth(40);
			}
		}
		pnlLeft.add(scrollPane1);

		tbl_sach.setPreferredScrollableViewportSize(new Dimension(1000, 400));
		tbl_sach.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tbl_sach.setFillsViewportHeight(true);
		JScrollPane scrollPane2 = new JScrollPane(tbl_sach);
		pnlBottom.add(scrollPane2);

		pnlRight.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
		pnlBotNORTH.setBorder(BorderFactory.createEmptyBorder(0, -4, 5, 2));
		pnlTopNORTH.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		pnlBottom.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
		pnlQuanLySach.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		// add frame
		pnlQuanLySach.add(pnlLeft, BorderLayout.WEST);
		pnlQuanLySach.add(pnlRight, BorderLayout.CENTER);
		this.add(pnlQuanLySach);

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
				xoa();
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
			@Override
			public void mouseClicked(MouseEvent evt) {
				tbl_danhmucMouseClicked(evt);
			}
		});

		chk_chude.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				chk_chudeItemStateChanged();
			}
		});

		txt_timkiem.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = txt_timkiem.getText();

				if (!(text.trim().length() == 0)) {
					for (int i = 0; i < tbl_sach.getRowCount(); i++) {
						String t = (String) tbl_sach.getValueAt(i, 2);
						if (t.contains(text)) {
							vitri = i;
							setModel(vitri);
							break;
						}
					}
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = txt_timkiem.getText();

				if (!(text.trim().length() == 0)) {
					for (int i = 0; i < tbl_sach.getRowCount(); i++) {
						String t = (String) tbl_sach.getValueAt(i, 2);
						if (t.contains(text)) {
							vitri = i;
							setModel(vitri);
							break;
						}
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
																				// choose Tools | Templates.
			}

		});

		btn_timkiem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btn_timkiemActionPerformed(e);
			}
		});

		btn_kietxuat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					kietxuat();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		});

		tbl_sach.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				tbl_sachMouseClicked(evt);
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
		mi5.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\List.png"));
		mi6.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Clien list.png"));
		mi7.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\user.png"));
		mi8.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Bar chart.png"));
		mi9.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Notes.png"));
		jmn_ketthuc.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Log out.png"));
		jmn_dangxaut.setIcon(new ImageIcon("C:\\Users\\USER\\Desktop\\QLTV\\QLTV\\src\\Icon\\Exit.png"));
	}

	// hàm chọn các dòng trên bảng sách
	private void tbl_sachMouseClicked(MouseEvent evt) {
		if (cnkhoa == false) {
			vitri = tbl_sach.getSelectedRow();
			setModel(vitri);
		}
	}

	private void tbl_danhmucMouseClicked(MouseEvent evt) {
		chude_Sach();

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
		txt_masach.requestFocus();
		cnluu = true;
	}

	private void btn_suaActionPerformed(ActionEvent evt) {
		setStatus(true);
		txt_masach.setEditable(false);
		txt_tieude.requestFocus();
		cnluu = false;
	}

	private void btn_timkiemActionPerformed(ActionEvent evt) {
		String text = txt_timkiem.getText();
		if (!(text.trim().length() == 0)) {
			if (vitri < tbl_sach.getRowCount()) {
				for (int i = vitri + 1; i < tbl_sach.getRowCount(); i++) {
					String t = (String) tbl_sach.getValueAt(i, 2);
					if (t.contains(text)) {
						vitri = i;
						setModel(vitri);
						break;
					}
				}
			}

		}

	}

	private void chk_chudeItemStateChanged() {
		int n = tbl_danhmuc.getSelectedRow();
		if (chk_chude.isSelected() == true) {
			String tencd = (String) tbl_danhmuc.getValueAt(n, 1);
			listcd = daocd.selectCDCS();
			fillSachToTblChude(listcd);
			n = 0;
			for (int i = 0; i < tbl_danhmuc.getRowCount(); i++) {
				String cdd = (String) tbl_danhmuc.getValueAt(i, 1);
				if (cdd.equals(tencd)) {
					tbl_danhmuc.setRowSelectionInterval(i, i);
					chude_Sach();
					break;
				} else {
					n++;
				}
			}
			if (n > 0) {
				tbl_danhmuc.setRowSelectionInterval(0, 0);
				chude_Sach();
			}

		} else {

			listcd = daocd.select();
			fillSachToTblChude(listcd);
			tbl_danhmuc.setRowSelectionInterval(n, n);
			chude_Sach();
		}
	}

	public static void main(String[] args) {
		new Form_QuanLySach();
	}
}
