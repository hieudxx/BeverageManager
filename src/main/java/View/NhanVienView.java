package View;

import DomainModels.NhanVien;
import ViewModels.NhanVienViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import Services.INhanVienService;
import Services.impl.NhanVienServiceImpl;

public class NhanVienView extends JFrame {

    private JTextField txtMaNV, txtTenDangNhap, txtHoTen, txtTimKiem;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboVaiTro, cboTrangThai, cboLocVaiTro;
    private JButton btnThem, btnSua, btnXoa, btnReset;
    private JTable tableNhanVien;
    private DefaultTableModel tableModel;
    private INhanVienService INvService;

    public NhanVienView() {
        INvService = new NhanVienServiceImpl();
        initComponents();
        loadDataToTable();
        setTitle("Quản lý nhân viên");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        // 1. TẠO KHE HỞ 15px VÀ ĐẶT MÀU NỀN CỬA SỔ (Màu xám nhạt để lộ khe hở)
        setLayout(new BorderLayout(15, 0));
        getContentPane().setBackground(new Color(225, 225, 225));

        // --- SIDEBAR ---
        JPanel sidebar = new JPanel(null);
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(10, 40, 60));

        // Khoảng trống vàng ở đầu Sidebar (To dài hơn - 250px)
        JPanel pnlYellowHeader = new JPanel();
        pnlYellowHeader.setBounds(0, 0, 220, 250);
        pnlYellowHeader.setBackground(new Color(240, 190, 90));
        sidebar.add(pnlYellowHeader);

        // Các nút Menu - Bắt đầu từ y = 250
        sidebar.add(menuBtn("Bán hàng", 250, false));
        sidebar.add(menuBtn("Sản phẩm", 310, false));

        sidebar.add(menuBtn("Nhân viên", 370, true));

        sidebar.add(menuBtn("Khách hàng", 430, false));
        sidebar.add(menuBtn("Thống kê", 490, false));

        JButton btnThoat = menuBtn("🚪 Thoát", 0, false);
        sidebar.add(btnThoat);

        sidebar.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                btnThoat.setLocation(0, sidebar.getHeight() - 60);
            }
        });

        add(sidebar, BorderLayout.WEST);

        // --- MAIN PANEL (Vùng quản lý) ---
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 190, 90));

        // 2. CHỐNG TRÀN: Thêm viền đen và khoảng cách bên trong (Padding)
        main.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), // Viền đen mỏng bao quanh
                BorderFactory.createEmptyBorder(10, 20, 10, 20) // Khoảng cách để component không dính lề
        ));

        add(main, BorderLayout.CENTER);

        JPanel topMain = new JPanel();
        topMain.setLayout(new BoxLayout(topMain, BoxLayout.Y_AXIS));
        topMain.setOpaque(false);

        JLabel title = new JLabel("Quản lý nhân viên", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        topMain.add(title);

        // --- FORM INPUT ---
        JPanel formContainer = new JPanel(new GridBagLayout());
        formContainer.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formContainer.add(new JLabel("Mã NV:"), gbc);
        gbc.gridx = 1;
        txtMaNV = new JTextField(25);
        txtMaNV.setEditable(false);  // Không cho phép sửa
        txtMaNV.setBackground(new Color(230, 230, 230)); // Màu nền xám báo hiệu không nhập
        formContainer.add(txtMaNV, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formContainer.add(new JLabel("Tài khoản:"), gbc);
        gbc.gridx = 1;
        txtTenDangNhap = new JTextField(25);
        formContainer.add(txtTenDangNhap, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formContainer.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtMatKhau = new JPasswordField(25);
        formContainer.add(txtMatKhau, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        formContainer.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 3;
        txtHoTen = new JTextField(25);
        formContainer.add(txtHoTen, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        formContainer.add(new JLabel("Chức vụ:"), gbc);
        gbc.gridx = 3;
        cboVaiTro = new JComboBox<>(new String[]{"", "Nhân viên", "Quản lý"});
        formContainer.add(cboVaiTro, gbc);

        gbc.gridx = 2;
        gbc.gridy = 2;
        formContainer.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 3;
        cboTrangThai = new JComboBox<>(new String[]{"", "Đang làm", "Đã nghỉ"});
        formContainer.add(cboTrangThai, gbc);

        topMain.add(formContainer);

        // --- BUTTONS PANEL ---
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        pnlButtons.setOpaque(false);

        // Đã xóa Emoji ở các nút chức năng
        btnThem = btn("Thêm");
        btnSua = btn("Sửa");
        btnXoa = btn("Xóa");
        btnReset = btn("Reset");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnReset);
        topMain.add(pnlButtons);

        // --- FILTER & SEARCH ---
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlFilter.setOpaque(false);
        pnlFilter.add(new JLabel("Lọc chức vụ:"));
        cboLocVaiTro = new JComboBox<>(new String[]{"Tất cả", "Nhân viên", "Quản lý"});
        pnlFilter.add(cboLocVaiTro);

        pnlFilter.add(new JLabel("      Tìm kiếm:"));
        txtTimKiem = new JTextField(30);
        pnlFilter.add(txtTimKiem);
        topMain.add(pnlFilter);

        main.add(topMain, BorderLayout.NORTH);

        // --- TABLE ---
        String[] cols = {"Mã NV", "Tài khoản", "Họ tên", "Vai trò", "Trạng thái"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tableNhanVien = new JTable(tableModel);
        tableNhanVien.setRowHeight(30);

        tableNhanVien.setShowGrid(true);
        tableNhanVien.setShowHorizontalLines(true);
        tableNhanVien.setShowVerticalLines(true);
        tableNhanVien.setGridColor(Color.GRAY);

        tableNhanVien.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, s, f, r, c);
                if (v != null) {
                    comp.setForeground(v.toString().equals("Đang làm") ? new Color(0, 150, 0) : Color.RED);
                }
                return comp;
            }
        });

        JScrollPane sp = new JScrollPane(tableNhanVien);
        sp.getViewport().setBackground(Color.WHITE);
        sp.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 50, 20, 50),
                BorderFactory.createLineBorder(Color.GRAY, 1)
        ));

        main.add(sp, BorderLayout.CENTER);

        // --- EVENTS ---
        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnReset.addActionListener(e -> resetForm());
        cboLocVaiTro.addActionListener(e -> filterData());
        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterData();
            }
        });
        tableNhanVien.getSelectionModel().addListSelectionListener(e -> loadSelectedRowToForm());
    }

    private JButton menuBtn(String text, int y, boolean active) {
        JButton b = new JButton(text);
        b.setBounds(0, y, 220, 50);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15)); // Chỉnh lại font đậm và to hơn một chút
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setMargin(new Insets(0, 30, 0, 0)); // Căn lề lùi vào một chút cho đẹp
        b.setBackground(active ? new Color(255, 153, 51) : new Color(10, 40, 60));

        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!active) {
                    b.setBackground(new Color(20, 60, 90));
                }
            }

            public void mouseExited(MouseEvent e) {
                if (!active) {
                    b.setBackground(new Color(10, 40, 60));
                }
            }
        });
        return b;
    }

    private JButton btn(String t) {
        JButton b = new JButton(t);
        b.setPreferredSize(new Dimension(100, 40));
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
    }

    // --- LOGIC ---
    private void filterData() {
        String role = cboLocVaiTro.getSelectedItem().toString();
        String keyword = txtTimKiem.getText().toLowerCase();
        tableModel.setRowCount(0);
        List<NhanVienViewModel> list = INvService.getAll();
        for (NhanVienViewModel nv : list) {
            boolean matchRole = role.equals("Tất cả") || nv.getVaiTro().equals(role);
            boolean matchText = nv.getMaNhanVien().toLowerCase().contains(keyword) || nv.getHoTen().toLowerCase().contains(keyword);
            if (matchRole && matchText) {
                tableModel.addRow(new Object[]{nv.getMaNhanVien(), nv.getTenDangNhap(), nv.getHoTen(), nv.getVaiTro(), nv.isTrangThai()});
            }
        }
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<NhanVienViewModel> list = INvService.getAll();
        for (NhanVienViewModel nv : list) {
            tableModel.addRow(new Object[]{nv.getMaNhanVien(), nv.getTenDangNhap(), nv.getHoTen(), nv.getVaiTro(), nv.isTrangThai() ? "Đang làm" : "Đã nghỉ việc"});
        }
    }

    private void loadSelectedRowToForm() {
        int i = tableNhanVien.getSelectedRow();
        if (i >= 0) {
            txtMaNV.setText(tableModel.getValueAt(i, 0).toString());
            txtTenDangNhap.setText(tableModel.getValueAt(i, 1).toString());
            txtHoTen.setText(tableModel.getValueAt(i, 2).toString());
            cboVaiTro.setSelectedItem(tableModel.getValueAt(i, 3));
            cboTrangThai.setSelectedItem(tableModel.getValueAt(i, 4));
            txtMaNV.setEnabled(false);
        }
    }

    private void themNhanVien() {
        String maNvMoi = "NV" + System.currentTimeMillis();

        if (!validateInput()) {
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this, "Có muốn thêm nhân viên", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            try {
                int result = INvService.add(getDataFromForm(maNvMoi));
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Thêm nhân viên thành công!");
                    loadDataToTable();
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Thêm thất bại! Mã NV hoặc tài khoản có thể đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void suaNhanVien() {
        if (!validateInputForUpdate()) {
            return;
        }
        int rows = tableNhanVien.getSelectedRow();
        if (rows >= 0) {
            int choice = JOptionPane.showConfirmDialog(this, "Có muốn sửa nhân viên không ?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                NhanVienViewModel nv = INvService.getAll().get(rows);
                INvService.update(nv.getMaNhanVien(), getDataFromForm(""));
                loadDataToTable();
                resetForm();
            }
        }
    }

    private void xoaNhanVien() {
        int rows = tableNhanVien.getSelectedRow();
        if (rows >= 0) {
            int choice = JOptionPane.showConfirmDialog(this, "Có muốn xóa nhân viên không ?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                INvService.delete(txtMaNV.getText());
                loadDataToTable();
                resetForm();
            } else {
                return;
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chọn nhân viên cần xóa");
            return;
        }

//        if (txtMaNV.isEnabled()) {
//            return;
//        }
    }

    private void resetForm() {
        txtMaNV.setText("");
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        txtHoTen.setText("");
        cboVaiTro.setSelectedIndex(0);   // item rỗng
        cboTrangThai.setSelectedIndex(0); // ite
        txtMaNV.setEnabled(true);
        tableNhanVien.clearSelection();   // thêm dòng này để bỏ chọn dòng trên bảng
    }

    private boolean validateInput() {

        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống!");
            txtHoTen.requestFocus();
            return false;
        }
        if (txtTenDangNhap.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tài khoản không được để trống!");
            txtTenDangNhap.requestFocus();
            return false;
        }
        if (txtMatKhau.getPassword().length == 0) {
            JOptionPane.showMessageDialog(this, "Mật khẩu không được để trống!");
            txtMatKhau.requestFocus();
            return false;
        }
        if (cboVaiTro.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ!");
            cboVaiTro.requestFocus();
            return false;
        }
        if (cboTrangThai.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái!");
            cboTrangThai.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateInputForUpdate() {
        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống!");
            txtHoTen.requestFocus();
            return false;
        }
        if (txtTenDangNhap.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tài khoản không được để trống!");
            txtTenDangNhap.requestFocus();
            return false;
        }

        if (cboVaiTro.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chức vụ!");
            cboVaiTro.requestFocus();
            return false;
        }
        if (cboTrangThai.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái!");
            cboTrangThai.requestFocus();
            return false;
        }
        return true;
    }

    private NhanVien getDataFromForm(String maNv) {
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(maNv);
        nv.setTenDangNhap(txtTenDangNhap.getText());
        nv.setMatKhau(new String(txtMatKhau.getPassword()));
        nv.setHoTen(txtHoTen.getText());
        nv.setVaiTro(cboVaiTro.getSelectedItem().toString());
        nv.setTrangThai(cboTrangThai.getSelectedItem().toString().equals("Đang làm")); // true nếu đang làm, false nếu đã nghỉ
        return nv;
    }

//    private NhanVien convert(NhanVienViewModel vm) {
//        NhanVien nv = new NhanVien();
//        nv.setMaNhanVien(vm.getMaNhanVien());
//        nv.setTenDangNhap(vm.getTenDangNhap());
//        nv.setMatKhau(vm.getMatKhau());
//        nv.setHoTen(vm.getHoTen());
//        nv.setVaiTro(vm.getVaiTro());
//        nv.setTrangThai(vm.getTrangThai().equals("Đang làm"));
//        return nv;
//    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        SwingUtilities.invokeLater(() -> new NhanVienView().setVisible(true));
    }
}
