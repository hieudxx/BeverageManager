package View;

import DomainModels.KhachHang;
import Services.impl.KhachHangServiceImpl;
import ViewModels.KhachHangViewModel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.MatteBorder;
import java.util.List;
import Services.KhachHangService;

public class KhachHangView extends JFrame {

    private JTextField txtMaKH, txtHoTen, txtSDT, txtTimKiem;
    private JButton btnThem, btnSua, btnXoa, btnReset;
    private JTable tableKhachHang;
    private DefaultTableModel tableModel;

    private final Color COLOR_SIDEBAR = new Color(23, 32, 42);
    private final Color COLOR_ORANGE_ACTIVE = new Color(243, 156, 18);
    private final Color COLOR_BG_MAIN = new Color(213, 216, 220);
    private KhachHangService IKhService;

    public KhachHangView() {
        IKhService = new KhachHangServiceImpl();
        initComponents();
        loadDataToTable();
        setTitle("Quản lý khách hàng");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(COLOR_BG_MAIN);

        add(createSidebar(), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(new Color(240, 190, 90));
        main.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        add(main, BorderLayout.CENTER);

        // ===== TITLE =====
        JLabel title = new JLabel("Quản lý khách hàng", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // ===== FORM =====
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Mã KH
        gbc.gridx = 0;
        gbc.gridy = 0;
        form.add(new JLabel("Mã KH:"), gbc);
        gbc.gridx = 1;
        txtMaKH = new JTextField(20);
        txtMaKH.setEditable(false);  // Không cho phép sửa
        txtMaKH.setBackground(new Color(230, 230, 230)); // Màu nền xám báo hiệu không nhập
        form.add(txtMaKH, gbc);

        // Họ tên
        gbc.gridx = 0;
        gbc.gridy = 1;
        form.add(new JLabel("Họ tên:"), gbc);
        gbc.gridx = 1;
        txtHoTen = new JTextField(20);
        form.add(txtHoTen, gbc);

        // SĐT
        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 1;
        txtSDT = new JTextField(20);
        form.add(txtSDT, gbc);

        // ===== BUTTON =====
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        pnlBtn.setOpaque(false);

        btnThem = createButton("Thêm");
        btnSua = createButton("Sửa");
        btnXoa = createButton("Xóa");
        btnReset = createButton("Reset");

        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnReset);

        // ===== SEARCH =====
        JPanel pnlSearch = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlSearch.setOpaque(false);

        pnlSearch.add(new JLabel("Tìm kiếm:"));
        txtTimKiem = new JTextField(30);
        pnlSearch.add(txtTimKiem);

        // ===== TOP =====
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setOpaque(false);

        top.add(title);
        top.add(form);
        top.add(pnlBtn);
        top.add(pnlSearch);

        main.add(top, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] cols = {"Mã KH", "Họ tên", "SĐT"};
        tableModel = new DefaultTableModel(cols, 0);
        tableKhachHang = new JTable(tableModel);
        tableKhachHang.setRowHeight(30);

        JScrollPane sp = new JScrollPane(tableKhachHang);
        main.add(sp, BorderLayout.CENTER);

        // ===== EVENT DEMO (UI ONLY) =====
        btnThem.addActionListener(e -> themKh());

        btnXoa.addActionListener(e -> xoaKhachHang());

        btnSua.addActionListener(e -> suaKhachHang());

        btnReset.addActionListener(e -> {
            txtMaKH.setText("");
            txtHoTen.setText("");
            txtSDT.setText("");
        });

        tableKhachHang.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int i = tableKhachHang.getSelectedRow();
                txtMaKH.setText(tableModel.getValueAt(i, 0).toString());
                txtHoTen.setText(tableModel.getValueAt(i, 1).toString());
                txtSDT.setText(tableModel.getValueAt(i, 2).toString());
            }
        });
    }

    private JButton createButton(String text) {
        JButton b = new JButton(text);
        b.setPreferredSize(new Dimension(100, 40));
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return b;
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
        // Lấy đối tượng Image từ icon
        Image img = icon.getImage();

// Resize ảnh về đúng kích thước panel (200x150)
        Image scaledImg = img.getScaledInstance(200, 150, Image.SCALE_SMOOTH);

// Tạo lại ImageIcon từ ảnh đã resize
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

// Đưa vào JLabel
        JLabel lblLogo = new JLabel(scaledIcon, JLabel.CENTER);
        logo.add(lblLogo, BorderLayout.CENTER);

        g.gridy = 0;
        p.add(logo, g);

        String[] menu = {"Bán hàng", "Danh Mục", "Sản phẩm", "Size", "Nhân viên", "Khách hàng"};
        int y = 1;
        for (String m : menu) {
            JButton btn = new JButton(m);
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setContentAreaFilled(false);
            btn.setOpaque(true);
            btn.setFocusPainted(false);
            btn.setBackground(m.equals("Khách hàng") ? COLOR_ORANGE_ACTIVE : COLOR_SIDEBAR);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setBorder(new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY));

            // --- THÊM SỰ KIỆN CLICK TẠI ĐÂY ---
            // ===== XỬ LÝ SỰ KIỆN CHUYỂN MÀN HÌNH =====
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

        JButton btnExit = new JButton("Thoát");
        btnExit.setPreferredSize(new Dimension(200, 50));
        btnExit.setBackground(COLOR_SIDEBAR);
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));
        btnExit.setBorder(new MatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn thoát chương trình?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0); // Thoát toàn bộ ứng dụng
            }
        });
        g.gridy = y;
        g.weighty = 0;
        p.add(btnExit, g);

        return p;
    }

    private void loadDataToTable() {
        tableModel.setRowCount(0);
        List<KhachHangViewModel> list = IKhService.getAll();
        for (KhachHangViewModel kh : list) {
            tableModel.addRow(new Object[]{kh.getMaKhachHang(), kh.getHoTen(), kh.getSoDienThoai()});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KhachHangView().setVisible(true));
    }

    private void themKh() {
        String maKhMoi = "KH" + System.currentTimeMillis();
        if (!validateInput()) {
            return;
        }
        int choice = JOptionPane.showConfirmDialog(this, "Có muốn thêm khách hàng", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (choice == JOptionPane.YES_OPTION) {
            btnThem.setEnabled(false);
            try {
                boolean result = IKhService.add(getDataFromForm(maKhMoi));
                if (result) {
                    JOptionPane.showMessageDialog(this, "✅ Thêm khách hàng thành công!");
                    loadDataToTable();
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Thêm thất bại! Mã KH hoặc SĐT có thể đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "❌ Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            } finally {
                btnThem.setEnabled(true);

            }
        }
    }

    private boolean validateInput() {
        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống!");
            txtHoTen.requestFocus();
            return false;
        }

        if (txtSDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Số điện thoại không được để trống!");
            txtSDT.requestFocus();
            return false;
        }

        if (!txtSDT.getText().matches("^0\\d{9}$")) {
            JOptionPane.showMessageDialog(this,
                    "SĐT phải gồm 10 số và bắt đầu bằng 0!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            txtSDT.requestFocus();
            return false;
        }
        return true;
    }

    private KhachHang getDataFromForm(String maKh) {
        KhachHang kh = new KhachHang();
        kh.setMaKhachHang(maKh);
        kh.setHoTen(txtHoTen.getText());
        kh.setSoDienThoai(txtSDT.getText());
        kh.setTrangThai(true);
        return kh;
    }

    private void resetForm() {
        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtMaKH.setEnabled(true);
        tableKhachHang.clearSelection();   // thêm dòng này để bỏ chọn dòng trên bảng
    }

    private void xoaKhachHang() {
        int rows = tableKhachHang.getSelectedRow();
        if (rows >= 0) {
            int choice = JOptionPane.showConfirmDialog(this, "Có muốn xóa khách hàng không ?", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                try {
                    boolean result = IKhService.delete(txtMaKH.getText());
                    if (result) {
                        JOptionPane.showMessageDialog(this, "✅ Xóa khách hàng thành công!");
                        loadDataToTable();
                        resetForm();
                    } else {
                        JOptionPane.showMessageDialog(this, "❌ Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "❌ Lỗi: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chọn khách hàng cần xóa");
        }
    }

    private void suaKhachHang() {
        if (!validateInput()) {
            return;
        }
        int rows = tableKhachHang.getSelectedRow();
        if (rows >= 0) {
            int choice = JOptionPane.showConfirmDialog(this, "Có muốn sửa khách hàng", "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (choice == JOptionPane.YES_OPTION) {
                KhachHangViewModel kh = IKhService.getAll().get(rows);
                IKhService.update(kh.getMaKhachHang(), getDataFromForm(txtMaKH.getText()));
                loadDataToTable();
                resetForm();

            }
        }

    }
}
