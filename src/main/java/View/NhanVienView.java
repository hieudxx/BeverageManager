package View;

import DomainModels.NhanVien;
import Services.INhanVienUIService;
import Services.impl.NhanVienUIServiceImpl;
import ViewModels.NhanVienViewModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class NhanVienView extends JFrame {

    private JTextField txtMaNV;
    private JTextField txtTenDangNhap;
    private JPasswordField txtMatKhau;
    private JTextField txtHoTen;
    private JComboBox<String> cboVaiTro;
    private JComboBox<String> cboTrangThai;
    private ButtonGroup genderGroup;

    private JButton btnThem;
    private JButton btnSua;
    private JButton btnXoa;
    private JButton btnReset;
    private JButton btnThongKe;
    private JButton btnThoat;

    private JTable tableNhanVien;
    private DefaultTableModel tableModel;

    private INhanVienUIService INvService;

    // ==================== Constructor ====================
    public NhanVienView() {
        INvService = new NhanVienUIServiceImpl();
        initComponents();
        loadDataToTable();
        setTitle("Quản Lý Nhân Viên");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(1100, 600);
    }

    // ==================== Khởi tạo giao diện ====================
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(240, 248, 255));

        // Panel chính
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(new Color(240, 248, 255));

        // Panel nhập liệu
//        JPanel inputPanel = createInputPanel();
//
//        // Panel button
//        JPanel buttonPanel = createButtonPanel();
//
//        // Panel chứa input + button
//        JPanel northPanel = new JPanel(new BorderLayout(5, 5));
//        northPanel.add(inputPanel, BorderLayout.CENTER);
//        northPanel.add(buttonPanel, BorderLayout.SOUTH);
//
//        // Panel tìm kiếm
//        JPanel searchPanel = createSearchPanel();
//
//        // Panel table
//        JPanel tablePanel = createTablePanel();
//
//        // Ghép các panel
//        mainPanel.add(northPanel, BorderLayout.NORTH);
//        mainPanel.add(searchPanel, BorderLayout.CENTER);
//        mainPanel.add(tablePanel, BorderLayout.SOUTH);
//
//        add(mainPanel);
//
//        // Menu bar
//        setJMenuBar(createMenuBar());
//
//        setSize(1300, 750);
        JPanel formPanel = createFormPanel();

        // Button panel
        JPanel buttonPanel = createButtonPanel();

        // Search panel
        JPanel searchPanel = createSearchPanel();

        // Table panel
        JPanel tablePanel = createTablePanel();

        // North panel (form + buttons)
        JPanel northPanel = new JPanel(new BorderLayout(10, 10));
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add to main
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(searchPanel, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Menu bar
        setJMenuBar(createMenuBar());

        setSize(1100, 600);
    }

    // ==================== Panel nhập liệu ====================
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 15, 15));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "THÔNG TIN NHÂN VIÊN",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(52, 152, 219)
        ));
        panel.setBackground(Color.WHITE);

        // Row 1
        panel.add(createLabel("Mã NV:"));
        txtMaNV = createTextField();
        panel.add(txtMaNV);

        panel.add(createLabel("Tài khoản:"));
        txtTenDangNhap = createTextField();
        panel.add(txtTenDangNhap);

        // Row 2
        panel.add(createLabel("Mật khẩu:"));
        txtMatKhau = new JPasswordField();
        styleTextField(txtMatKhau);
        panel.add(txtMatKhau);

        panel.add(createLabel("Họ tên:"));
        txtHoTen = createTextField();
        panel.add(txtHoTen);

        // Row 3
        panel.add(createLabel("Vai trò:"));
        cboVaiTro = new JComboBox<>(new String[]{"Nhân viên", "Quản lý"});
        styleComboBox(cboVaiTro);
        panel.add(cboVaiTro);

        panel.add(createLabel("Trạng thái:"));
        cboTrangThai = new JComboBox<>(new String[]{"Đang làm", "Đã nghỉ"});
        styleComboBox(cboTrangThai);
        panel.add(cboTrangThai);

        return panel;
    }

    // ==================== Panel button ====================
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(new Color(240, 248, 255));

        btnThem = createButton("THÊM", new Color(76, 175, 80));
        btnSua = createButton("SỬA", new Color(33, 150, 243));
        btnXoa = createButton("XÓA", new Color(244, 67, 54));
        btnReset = createButton("RESET", new Color(255, 152, 0));

        panel.add(btnThem);
        panel.add(btnSua);
        panel.add(btnXoa);
        panel.add(btnReset);

        // Thêm sự kiện
        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnReset.addActionListener(e -> resetForm());

        return panel;
    }

    // ==================== Panel tìm kiếm ====================
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("TÌM KIẾM"));
        panel.setBackground(Color.WHITE);

        JPanel searchInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        searchInputPanel.setBackground(Color.WHITE);

        JTextField txtTimKiem = new JTextField(20);
        JButton btnTimKiem = new JButton("🔍 Tìm kiếm");

        styleTextField(txtTimKiem);
        styleButton(btnTimKiem, new Color(52, 152, 219));

        searchInputPanel.add(new JLabel("Nhập mã NV hoặc tên:"));
        searchInputPanel.add(txtTimKiem);
        searchInputPanel.add(btnTimKiem);

//        btnTimKiem.addActionListener(e -> timKiemNhanVien(txtTimKiem.getText().trim()));
        panel.add(searchInputPanel, BorderLayout.CENTER);

        return panel;
    }

    // ==================== Panel bảng ====================
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("DANH SÁCH NHÂN VIÊN"));
        panel.setBackground(Color.WHITE);

        String[] columns = {"Mã NV", "Tên Đăng Nhập", "Mật khẩu", "Họ tên", "Vai Trò", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableNhanVien = new JTable(tableModel);
        tableNhanVien.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tableNhanVien.setRowHeight(25);
        tableNhanVien.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tableNhanVien.getTableHeader().setBackground(new Color(52, 152, 219));
        tableNhanVien.getTableHeader().setForeground(Color.WHITE);

        // Sự kiện click vào bảng
        tableNhanVien.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedRowToForm();
            }
        });

        tableNhanVien.getColumnModel().getColumn(5).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null && value.toString().equals("Đang làm")) {
                    c.setForeground(new Color(76, 175, 80));
                } else {
                    c.setForeground(Color.RED);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(tableNhanVien);
        scrollPane.setPreferredSize(new Dimension(1050, 250));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // ==================== Menu Bar ====================
    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Bán hàng
        JMenu menuBanHang = new JMenu("🏠 Bán hàng");
        JMenuItem miBanHang = new JMenuItem("Bán hàng");
        miBanHang.addActionListener(e -> {
            // Chuyển sang màn hình bán hàng
        });
        menuBanHang.add(miBanHang);

        // Menu Sản phẩm
        JMenu menuSanPham = new JMenu("📦 Sản phẩm");
        JMenuItem miSanPham = new JMenuItem("Quản lý sản phẩm");
        miSanPham.addActionListener(e -> {
            // Chuyển sang màn hình sản phẩm
        });
        menuSanPham.add(miSanPham);

        // Menu Nhân viên (đang active)
        JMenu menuNhanVien = new JMenu("👨‍💼 Nhân viên");
        menuNhanVien.setBackground(new Color(52, 152, 219));
        JMenuItem miNhanVien = new JMenuItem("Quản lý nhân viên");
        miNhanVien.addActionListener(e -> {
            // Refresh
            loadDataToTable();
            resetForm();
        });
        menuNhanVien.add(miNhanVien);

        // Menu Khách hàng
        JMenu menuKhachHang = new JMenu("👤 Khách hàng");
        JMenuItem miKhachHang = new JMenuItem("Quản lý khách hàng");
        miKhachHang.addActionListener(e -> {
            // Chuyển sang màn hình khách hàng
        });
        menuKhachHang.add(miKhachHang);

        // Menu Thống kê
        JMenu menuThongKe = new JMenu("📊 Thống kê");
        JMenuItem miThongKe = new JMenuItem("Thống kê doanh thu");
        miThongKe.addActionListener(e -> {
            // Chuyển sang màn hình thống kê
        });
        menuThongKe.add(miThongKe);

        // Menu Thoát
        JMenu menuThoat = new JMenu("🚪 Thoát");
        JMenuItem miThoat = new JMenuItem("Thoát");
        miThoat.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thoát?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
        menuThoat.add(miThoat);

        menuBar.add(menuBanHang);
        menuBar.add(menuSanPham);
        menuBar.add(menuNhanVien);
        menuBar.add(menuKhachHang);
        menuBar.add(menuThongKe);
        menuBar.add(menuThoat);

        return menuBar;
    }

    // ==================== Helper methods ====================
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(44, 62, 80));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        styleTextField(textField);
        return textField;
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        comboBox.setBackground(Color.WHITE);
    }

    private void styleRadioButton(JRadioButton radioButton) {
        radioButton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        radioButton.setBackground(Color.WHITE);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // ==================== Load dữ liệu lên bảng ====================
    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<NhanVienViewModel> list = INvService.getAll();

        if (list == null) {
            JOptionPane.showMessageDialog(this, "Không thể tải dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (NhanVienViewModel nv : list) {
            Object[] row = {
                nv.getMaNhanVien(),
                nv.getTenDangNhap(),
                "••••••", // Ẩn mật khẩu
                nv.getHoTen(),
                nv.getVaiTro(),
                nv.getTrangThai(),};
            tableModel.addRow(row);
        }
    }

    // ==================== Load dòng được chọn lên form ====================
    private void loadSelectedRowToForm() {
        int selectedRow = tableNhanVien.getSelectedRow();
        if (selectedRow >= 0) {
            txtMaNV.setText(tableModel.getValueAt(selectedRow, 0).toString());
            txtTenDangNhap.setText(tableModel.getValueAt(selectedRow, 1).toString());
            txtHoTen.setText(tableModel.getValueAt(selectedRow, 3).toString());
//            txtMatKhau.setText(tableModel.getValueAt(selectedRow, 4).toString());
            cboVaiTro.setSelectedItem(tableModel.getValueAt(selectedRow, 4).toString());
            cboTrangThai.setSelectedItem(tableModel.getValueAt(selectedRow, 5).toString());

            // Disable mã NV khi đang sửa
            txtMaNV.setEnabled(false);
        }
    }

    // ==================== CRUD Operations ====================
    private void themNhanVien() {
       
        if (validateInput()) {
            NhanVienViewModel nvVM = getDataFromForm();
            NhanVien nv = convertViewModelToDomain(nvVM);
            int result = INvService.add(nv);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                loadDataToTable();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private NhanVien convertViewModelToDomain(NhanVienViewModel vm) {
        NhanVien nv = new NhanVien();
        nv.setMaNhanVien(vm.getMaNhanVien());
        nv.setTenDangNhap(vm.getTenDangNhap());
        nv.setMatKhau(vm.getMatKhau());
        nv.setHoTen(vm.getHoTen());
        nv.setVaiTro(vm.getVaiTro());
        nv.setTrangThai(vm.getTrangThai().equals("Dang lam"));
        return nv;
    }

    private void suaNhanVien() {
        if (txtMaNV.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa từ bảng!");
            return;
        }

        if (!validateInput()) {
            return;
        }

        if (validateInput()) {
            NhanVienViewModel nvVM = getDataFromForm();
            NhanVien nv = convertViewModelToDomain(nvVM);
            int result = INvService.update(txtMaNV.getText(), nv);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");
                loadDataToTable();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void xoaNhanVien() {
        if (txtMaNV.isEnabled()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa từ bảng!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa nhân viên " + txtMaNV.getText() + "?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            int result = INvService.delete(txtMaNV.getText());
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                loadDataToTable();
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

//    private void timKiemNhanVien(String keyword) {
//        if (keyword.isEmpty()) {
//            loadDataToTable();
//            return;
//        }
//        
//        List<NhanVienViewModel> list = INvService.find(keyword);
//        tableModel.setRowCount(0);
//        
//        for (NhanVienViewModel nv : list) {
//            Object[] row = {
//                nv.getMaNV(), nv.getTaiKhoan(), "••••••", nv.getHoTen(),
//                nv.getGioiTinh(), nv.getNgaySinh(), nv.getSdt(),
//                nv.getDiaChi(), nv.getChucVu(), nv.getTrangThai()
//            };
//            tableModel.addRow(row);
//        }
//        
//        if (list.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả nào!");
//        }
//    }
    private void resetForm() {
        txtMaNV.setText("");
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        txtHoTen.setText("");
        cboVaiTro.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        txtMaNV.setEnabled(true);
        txtMaNV.requestFocus();
        tableNhanVien.clearSelection();
    }

    // ==================== Validation ====================
    private boolean validateInput() {
        if (txtMaNV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã nhân viên không được để trống!");
            txtMaNV.requestFocus();
            return false;
        }
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
        return true;
    }

    private NhanVienViewModel getDataFromForm() {
        NhanVienViewModel nv = new NhanVienViewModel();
        nv.setMaNhanVien(txtMaNV.getText().trim());
        nv.setTenDangNhap(txtTenDangNhap.getText().trim());
        nv.setMatKhau(new String(txtMatKhau.getPassword()));
        nv.setHoTen(txtHoTen.getText().trim());
        nv.setVaiTro(cboVaiTro.getSelectedItem().toString());
        nv.setTrangThai(cboTrangThai.getSelectedItem().toString());
        return nv;
    }

    // ==================== Main ====================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NhanVienView().setVisible(true);
        });
    }
}
