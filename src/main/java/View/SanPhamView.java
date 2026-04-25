package View;

import Services.SanPhamServices;
import Services.impl.SanPhamServicesImpl;
import Services.DanhMucServices;
import Services.impl.DanhMucServicesImpl;

import ViewModels.SanPhamResponse;
import DomainModels.DanhMuc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SanPhamView extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtMa, txtTen, txtGia, txtHinhAnh, txtTim;
    private JComboBox<String> cboDanhMuc, cboDangBan, cboTrangThai;

    private JLabel lblHinhAnh;
    private List<DanhMuc> listDanhMuc;

    private SanPhamServices service = new SanPhamServicesImpl();
    private DanhMucServices danhMucService = new DanhMucServicesImpl();

    private final Color COLOR_SIDEBAR = new Color(23, 32, 42);
    private final Color COLOR_ORANGE = new Color(243, 156, 18);
    private final Color COLOR_BG_MAIN = new Color(213, 216, 220);

    public SanPhamView() {
        initUI();
        loadDanhMuc1();
        loadTable(service.getAll());
    }

    private void initUI() {
        setTitle("Quản lý sản phẩm");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);

        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(COLOR_BG_MAIN);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // ===== FORM =====
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(10, 10, 10, 10);
        f.fill = GridBagConstraints.HORIZONTAL;

        txtMa = new JTextField();
        txtTen = new JTextField();
        txtGia = new JTextField();

        txtHinhAnh = new JTextField();
        txtHinhAnh.setEditable(false);

        JButton btnChonAnh = new JButton("Chọn ảnh");

        JPanel pnlImage = new JPanel(new BorderLayout(5, 5));
        pnlImage.add(txtHinhAnh, BorderLayout.CENTER);
        pnlImage.add(btnChonAnh, BorderLayout.EAST);

        cboDanhMuc = new JComboBox<>();
        cboDangBan = new JComboBox<>(new String[]{"Còn hàng", "Hết hàng"});
        cboTrangThai = new JComboBox<>(new String[]{"Đang kinh doanh", "Ngừng kinh doanh"});

        String[] labels = {
            "Mã sản phẩm:",
            "Tên sản phẩm:",
            "Danh mục:",
            "Giá:",
            "Hình ảnh:",
            "Đang bán:",
            "Trạng thái:"
        };

        JComponent[] inputs = {
            txtMa, txtTen, cboDanhMuc, txtGia,
            pnlImage, cboDangBan, cboTrangThai
        };

        for (int i = 0; i < labels.length; i++) {
            f.gridx = 0;
            f.gridy = i;
            form.add(new JLabel(labels[i]), f);

            f.gridx = 1;
            form.add(inputs[i], f);
        }

        // ===== HIỂN THỊ ẢNH =====
        lblHinhAnh = new JLabel();
        lblHinhAnh.setPreferredSize(new Dimension(180, 180));
        lblHinhAnh.setBorder(BorderFactory.createTitledBorder("Ảnh sản phẩm"));
        lblHinhAnh.setHorizontalAlignment(JLabel.CENTER);

        f.gridx = 0;
        f.gridy = labels.length;
        f.gridwidth = 2;
        form.add(lblHinhAnh, f);

        // ===== BUTTON =====
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setOpaque(false);

        JButton btnAdd = createYellowBtn("Thêm");
        btnPanel.add(btnAdd);
        JButton btnUpdate = createYellowBtn("Sửa");
        btnPanel.add(btnUpdate);
        JButton btnDelete = createYellowBtn("Xóa");
        btnPanel.add(btnDelete);
        btnPanel.add(createYellowBtn("Reset"));

        f.gridy = labels.length + 1;
        form.add(btnPanel, f);

        gbc.gridx = 0;
        gbc.weightx = 0.4;
        main.add(form, gbc);

        // ===== TABLE =====
        JPanel pnlTable = new JPanel(new BorderLayout(10, 10));
        pnlTable.setBackground(Color.WHITE);
        pnlTable.setBorder(new EmptyBorder(15, 15, 15, 15));

        txtTim = new JTextField(15);
        JButton btnSearch = createYellowBtn("Tìm");
        btnSearch.addActionListener(e -> {
            String keyword = txtTim.getText().trim();
            loadTable(service.search(keyword));
        });

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);
        searchPanel.add(new JLabel("Tìm mã:"));
        searchPanel.add(txtTim);
        searchPanel.add(btnSearch);

        pnlTable.add(searchPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"Mã", "Tên", "Danh mục", "Giá", "Ảnh", "Đang bán", "Trạng thái"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(25);

        pnlTable.add(new JScrollPane(table), BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        main.add(pnlTable, gbc);

        add(main, BorderLayout.CENTER);

        // ===== EVENT =====
        // chọn ảnh
        btnChonAnh.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileFilter(new FileNameExtensionFilter("Image", "jpg", "png", "jpeg"));

            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                txtHinhAnh.setText(path);
                showImage(path);
            }
        });

        // click table
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    return;
                }

                txtMa.setText(model.getValueAt(row, 0).toString());
                txtTen.setText(model.getValueAt(row, 1).toString());

                cboDanhMuc.setSelectedItem(model.getValueAt(row, 2).toString());

                txtGia.setText(model.getValueAt(row, 3).toString());
                txtHinhAnh.setText(model.getValueAt(row, 4).toString());
                cboDangBan.setSelectedItem(model.getValueAt(row, 5).toString());
                cboTrangThai.setSelectedItem(model.getValueAt(row, 6).toString());

                showImage(txtHinhAnh.getText());
            }
        });

        // Add
        btnAdd.addActionListener(e -> {
            try {
                // ===== 1. Lấy dữ liệu từ form =====
                String ma = txtMa.getText().trim();
                String ten = txtTen.getText().trim();
                String giaStr = txtGia.getText().trim();
                String path = txtHinhAnh.getText().trim();

                int indexDanhMuc = cboDanhMuc.getSelectedIndex();

                boolean dangBan = cboDangBan.getSelectedIndex() == 0;
                boolean trangThai = cboTrangThai.getSelectedIndex() == 0;

                // ===== 2. Validate =====
                if (ma.isEmpty() || ten.isEmpty() || giaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không được để trống!");
                    return;
                }

                if (indexDanhMuc < 0) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn danh mục!");
                    return;
                }

                // ===== 3. Convert dữ liệu =====
                java.math.BigDecimal gia;
                try {
                    gia = new java.math.BigDecimal(giaStr);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Giá phải là số!");
                    return;
                }

                DanhMuc dm = listDanhMuc.get(indexDanhMuc);

                // chỉ lấy tên file ảnh
                String tenFile = "";
                if (!path.isEmpty()) {
                    tenFile = new java.io.File(path).getName();
                }

                // ===== 4. Tạo object =====
                DomainModels.SanPham sp = new DomainModels.SanPham();

                sp.setMaSanPham(ma);
                sp.setTenSanPham(ten);
                sp.setGiaCoBan(gia);
                sp.setHinhAnh(tenFile);
                sp.setDangBan(dangBan);
                sp.setTrangThaiHienThi(trangThai);
                sp.setDanhMuc(dm);

                // ===== 5. Gọi service =====
                boolean result = service.add(sp);

                // ===== 6. Kết quả =====
                if (result) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");

                    loadTable(service.getAll()); // reload bảng
                    clearForm(); // reset form

                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống!");
            }
        });

        // Update
        btnUpdate.addActionListener(e -> {
            try {
                int selectedRow = table.getSelectedRow();

                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần sửa!");
                    return;
                }

                // ===== 1. Lấy dữ liệu từ form =====
                String ma = txtMa.getText().trim();
                String ten = txtTen.getText().trim();
                String giaStr = txtGia.getText().trim();
                String path = txtHinhAnh.getText().trim();

                int indexDanhMuc = cboDanhMuc.getSelectedIndex();

                boolean dangBan = cboDangBan.getSelectedIndex() == 0;
                boolean trangThai = cboTrangThai.getSelectedIndex() == 0;

                // ===== 2. Validate =====
                if (ma.isEmpty() || ten.isEmpty() || giaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không được để trống!");
                    return;
                }

                if (indexDanhMuc < 0) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn danh mục!");
                    return;
                }

                // ===== 3. Convert giá =====
                java.math.BigDecimal gia;
                try {
                    gia = new java.math.BigDecimal(giaStr);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Giá phải là số!");
                    return;
                }

                DanhMuc dm = listDanhMuc.get(indexDanhMuc);

                // chỉ lấy tên file ảnh
                String tenFile = "";
                if (!path.isEmpty()) {
                    tenFile = new java.io.File(path).getName();
                }

                // ===== 4. Tạo object =====
                DomainModels.SanPham sp = new DomainModels.SanPham();

                sp.setMaSanPham(ma);
                sp.setTenSanPham(ten);
                sp.setGiaCoBan(gia);
                sp.setHinhAnh(tenFile);
                sp.setDangBan(dangBan);
                sp.setTrangThaiHienThi(trangThai);
                sp.setDanhMuc(dm);

                // ===== 5. Gọi service =====
                boolean result = service.update(sp);

                // ===== 6. Kết quả =====
                if (result) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                    loadTable(service.getAll());
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi hệ thống!");
            }
        });

        // Delete
        btnDelete.addActionListener(e -> {
            try {
                int row = table.getSelectedRow();

                // ===== 1. Check chọn dòng =====
                if (row == -1) {
                    JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần xóa!");
                    return;
                }

                // ===== 2. Lấy mã sản phẩm =====
                String ma = model.getValueAt(row, 0).toString();

                // ===== 3. Confirm =====
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có chắc muốn xóa sản phẩm này?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                // ===== 4. Gọi service =====
                boolean result = service.delete(ma);

                // ===== 5. Kết quả =====
                if (result) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadTable(service.getAll());
                    clearForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại hoặc không tồn tại!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Không thể xóa! Có thể dữ liệu đang được sử dụng.");
            }
        });

        // search
        txtTim.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                if (txtTim.getText().trim().isEmpty()) {
                    loadTable(service.getAll());
                }
            }
        });
    }

    private void loadDanhMuc1() {
        cboDanhMuc.removeAllItems();

        listDanhMuc = danhMucService.getAll();

        for (DanhMuc dm : listDanhMuc) {
            cboDanhMuc.addItem(dm.getTenDanhMuc());
        }
    }

    // ===== LOAD TABLE =====
    private void loadTable(List<SanPhamResponse> list) {
        model.setRowCount(0);

        for (SanPhamResponse sp : list) {
            model.addRow(new Object[]{
                sp.getMaSanPham(),
                sp.getTenSanPham(),
                sp.getTenDanhMuc(),
                sp.getGiaCoBan(),
                sp.getHinhAnh(),
                sp.isDangBan() ? "Còn hàng" : "Hết hàng",
                sp.isTrangThaiHienThi() ? "Đang kinh doanh" : "Ngừng kinh doanh"
            });
        }
    }

    // ===== HIỂN THỊ ẢNH =====
    private void showImage(String fileName) {
        try {
            java.net.URL url = getClass().getResource("/images/" + fileName);

            if (url == null) {
                lblHinhAnh.setIcon(null);
                return;
            }

            ImageIcon icon = new ImageIcon(url);
            Image img = icon.getImage();

            int w = 180;
            int h = 180;

            Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
            lblHinhAnh.setIcon(new ImageIcon(scaled));

        } catch (Exception e) {
            lblHinhAnh.setIcon(null);
        }
    }

    // ===== SIDEBAR =====
    private JPanel createSidebar() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(COLOR_SIDEBAR);
        p.setPreferredSize(new Dimension(200, 0));

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        JPanel logo = new JPanel();
        logo.setBackground(new Color(255, 204, 0));
        logo.setPreferredSize(new Dimension(200, 150));
        g.gridy = 0;
        p.add(logo, g);

        String[] menu = {"Bán hàng", "Danh Mục", "Sản phẩm", "Size", "Nhân viên", "Khách hàng", "Thống kê"};
        int y = 1;

        for (String m : menu) {
            JButton btn = new JButton(m);
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setBackground(m.equals("Sản phẩm") ? COLOR_ORANGE : COLOR_SIDEBAR);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setBorder(new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY));

            g.gridy = y++;
            p.add(btn, g);
        }

        g.gridy = y++;
        g.weighty = 1;
        p.add(new JLabel(""), g);

        JButton btnExit = new JButton("Thoát");
        btnExit.setBackground(COLOR_SIDEBAR);
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));
        btnExit.setBorder(new MatteBorder(1, 0, 0, 0, Color.DARK_GRAY));

        g.gridy = y;
        g.weighty = 0;
        p.add(btnExit, g);

        return p;
    }

    private JButton createYellowBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(255, 215, 0));
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SanPhamView().setVisible(true));
    }

    // ===== CLEAR FORM =====
    private void clearForm() {
        txtMa.setText("");
        txtTen.setText("");
        txtGia.setText("");
        txtHinhAnh.setText("");
        cboDanhMuc.setSelectedIndex(0);
        cboDangBan.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        lblHinhAnh.setIcon(null);
    }
}
