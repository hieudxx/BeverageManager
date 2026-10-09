package View;

import DomainModels.NhanVien;
import ViewModels.NhanVienViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import Services.impl.NhanVienServiceImpl;
import javax.swing.border.MatteBorder;
import Services.NhanVienService;
import Utilities.Auth;

public class NhanVienView extends JFrame {

    private JTextField txtMaNV, txtTenDangNhap, txtHoTen, txtTimKiem;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboVaiTro, cboTrangThai, cboLocVaiTro;
    private JButton btnThem, btnSua, btnXoa, btnReset;
    private JTable tableNhanVien;
    private DefaultTableModel tableModel;
    private NhanVienService INvService;
    private final Color COLOR_SIDEBAR = new Color(23, 32, 42);
    private final Color COLOR_ORANGE_ACTIVE = new Color(243, 156, 18);
    private final Color COLOR_BG_MAIN = new Color(213, 216, 220);
    private final Color COLOR_YELLOW_BTN = new Color(255, 215, 0);

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

        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(COLOR_BG_MAIN);

        // --- SIDEBAR ---
        add(createSidebar(), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 190, 90));

        main.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
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
        txtMaNV.setEditable(false);
        txtMaNV.setBackground(new Color(230, 230, 230));
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

        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10));
        pnlButtons.setOpaque(false);

        btnThem = btn("Thêm");
        btnSua = btn("Sửa");
        btnXoa = btn("Cho nghỉ");
        btnReset = btn("Reset");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnReset);
        topMain.add(pnlButtons);

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

    private JPanel createSidebar() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(COLOR_SIDEBAR);
        p.setPreferredSize(new Dimension(200, 0));
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;

        JPanel logo = new JPanel();
        logo.setBackground(new Color(255, 204, 0));
        logo.setPreferredSize(new Dimension(200, 150));
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logoNootea.png"));

        Image img = icon.getImage();

        Image scaledImg = img.getScaledInstance(200, 150, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel lblLogo = new JLabel(scaledIcon, JLabel.CENTER);
        logo.add(lblLogo, BorderLayout.CENTER);
        g.gridy = 0;
        p.add(logo, g);

        String[] menu = {"Bán hàng", "Danh Mục", "Sản phẩm", "Size", "Nhân viên", "Khách hàng", "Thống kê"};
        int y = 1;
        for (String m : menu) {
            JButton btn = new JButton(m);
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setContentAreaFilled(false);
            btn.setOpaque(true);

            btn.setBackground(m.equals("Nhân viên") ? COLOR_ORANGE_ACTIVE : COLOR_SIDEBAR);

            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setFocusPainted(false);
            btn.setBorder(new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY));

            btn.addActionListener(e -> {
                switch (m) {
                    case "Bán hàng":
                        new BanHangView().setVisible(true);
                        this.dispose();
                        break;
                    case "Nhân viên":
                        new NhanVienView().setVisible(true);
                        this.dispose();
                        break;
                    case "Danh Mục":
                        new DanhMucView().setVisible(true);
                        this.dispose();
                        break;
                    case "Size":
                        new SizeView().setVisible(true);
                        this.dispose();
                        break;
                    case "Sản phẩm":
                        new SanPhamView().setVisible(true);
                        this.dispose();
                        break;
                    case "Khách hàng":
                        new KhachHangView().setVisible(true);
                        this.dispose();
                        break;
                    case "Thống kê":
                        new ThongKeView().setVisible(true);
                        this.dispose();
                        break;
                    default:

                        break;
                }
            });

            g.gridy = y++;
            p.add(btn, g);
        }

        g.gridy = y++;
        g.weighty = 1.0;
        p.add(new JLabel(""), g);

        JButton btnExit = new JButton("🚪 Thoát");
        btnExit.setPreferredSize(new Dimension(200, 60));
        btnExit.setBackground(COLOR_SIDEBAR);
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));
        btnExit.setFocusPainted(false);
        btnExit.setBorder(new MatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn thoát?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        g.gridy = y;
        g.weighty = 0;
        p.add(btnExit, g);

        return p;
    }

    private JButton menuBtn(String text, int y, boolean active) {
        JButton b = new JButton(text);
        b.setBounds(0, y, 220, 50);
        b.setFont(new Font("Segoe UI", Font.BOLD, 15));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setHorizontalAlignment(SwingConstants.LEFT);
        b.setMargin(new Insets(0, 30, 0, 0));
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

    private void filterData() {
        String role = cboLocVaiTro.getSelectedItem().toString();
        String keyword = txtTimKiem.getText().toLowerCase();
        tableModel.setRowCount(0);
        List<NhanVienViewModel> list = INvService.getAll();
        for (NhanVienViewModel nv : list) {
            boolean matchRole = role.equals("Tất cả") || nv.getVaiTro().equals(role);
            boolean matchText = nv.getMaNhanVien().toLowerCase().contains(keyword) || nv.getHoTen().toLowerCase().contains(keyword);
            if (matchRole && matchText) {
                tableModel.addRow(new Object[]{nv.getMaNhanVien(), nv.getTenDangNhap(), nv.getHoTen(), nv.getVaiTro(), trangThaiText(nv.isTrangThai())});
            }
        }
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<NhanVienViewModel> list = INvService.getAll();
        for (NhanVienViewModel nv : list) {
            tableModel.addRow(new Object[]{nv.getMaNhanVien(), nv.getTenDangNhap(), nv.getHoTen(), nv.getVaiTro(), trangThaiText(nv.isTrangThai())});
        }
    }

    // Đổi trạng thái (true/false) thành chữ hiển thị; renderer cột 4 dựa vào chữ này để tô màu
    private String trangThaiText(boolean dangLam) {
        return dangLam ? "Đang làm" : "Đã nghỉ";
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
            btnThem.setEnabled(false);
            try {
                int result = INvService.add(getDataFromForm(maNvMoi));
                if (result > 0) {
                    JOptionPane.showMessageDialog(this, "✅ Thêm nhân viên thành công!");
                    loadDataToTable();
                    resetForm();
                } else {
//                    JOptionPane.showMessageDialog(this, "❌ Thêm thất bại! Mã NV hoặc tài khoản có thể đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                btnThem.setEnabled(true);
            }
        }

    }

    private void suaNhanVien() {
        if (!validateInputForUpdate()) {
            return;
        }
        int rows = tableNhanVien.getSelectedRow();
        if (rows >= 0) {
            // Lấy mã NV từ chính dòng đang chọn trong bảng (đúng cả khi bảng đang lọc/tìm kiếm)
            String maNv = tableModel.getValueAt(rows, 0).toString();

            // Không cho tự chuyển tài khoản đang đăng nhập sang "Đã nghỉ" (tương đương tự xóa mình)
            if (laTaiKhoanDangDangNhap(maNv) && cboTrangThai.getSelectedItem().toString().equals("Đã nghỉ")) {
                JOptionPane.showMessageDialog(this,
                        "Không thể chuyển tài khoản đang đăng nhập sang trạng thái \"Đã nghỉ\"!",
                        "Thông báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int choice = JOptionPane.showConfirmDialog(this, "Có muốn sửa nhân viên không ?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                if (INvService.update(maNv, getDataFromForm(maNv)) == 1) {
                    JOptionPane.showMessageDialog(this, "✅ Sửa thành công!");
                    loadDataToTable();
                    resetForm();
                }
            }
        }
    }

    // Xóa mềm: chuyển nhân viên sang "Đã nghỉ", không xóa khỏi DB để giữ lịch sử hóa đơn
    private void xoaNhanVien() {
        int rows = tableNhanVien.getSelectedRow();
        if (rows < 0) {
            JOptionPane.showMessageDialog(this, "Chọn nhân viên cần xóa");
            return;
        }

        String maNv = tableModel.getValueAt(rows, 0).toString();
        String hoTen = tableModel.getValueAt(rows, 2).toString();

        // Không cho xóa chính tài khoản đang đăng nhập
        if (laTaiKhoanDangDangNhap(maNv)) {
            JOptionPane.showMessageDialog(this,
                    "Không thể xóa tài khoản đang đăng nhập!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Đã nghỉ rồi thì không cần làm lại
        if ("Đã nghỉ".equals(tableModel.getValueAt(rows, 4))) {
            JOptionPane.showMessageDialog(this, "Nhân viên " + hoTen + " đã ở trạng thái nghỉ việc.");
            return;
        }

        int choice = JOptionPane.showConfirmDialog(this,
                "Cho nhân viên " + hoTen + " (" + maNv + ") nghỉ việc?\n"
                + "Tài khoản sẽ không thể đăng nhập, lịch sử hóa đơn vẫn được giữ lại.",
                "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        if (INvService.delete(maNv) == 1) {
            JOptionPane.showMessageDialog(this, "✅ Đã chuyển nhân viên sang trạng thái nghỉ việc!");
            loadDataToTable();
            resetForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Thao tác thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Mã NV này có phải của người đang đăng nhập không (an toàn khi chưa có ai đăng nhập)
    private boolean laTaiKhoanDangDangNhap(String maNv) {
        return Auth.user != null && maNv.equalsIgnoreCase(Auth.user.getMaNhanVien());
    }

    private void resetForm() {
        txtMaNV.setText("");
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        txtHoTen.setText("");
        cboVaiTro.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        txtMaNV.setEnabled(true);
        tableNhanVien.clearSelection();
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
        nv.setTrangThai(cboTrangThai.getSelectedItem().toString().equals("Đang làm"));
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
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new NhanVienView().setVisible(true));
    }
}
