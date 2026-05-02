package View;

import DomainModels.SanPham;
import DomainModels.Size;
import Services.SizeServices;
import Services.impl.SizeServicesImpl;
import Services.SanPhamServices;
import Services.impl.SanPhamServicesImpl;
import ViewModels.SizeViewModel;
import ViewModels.SanPhamResponse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class SizeView extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtMaSize, txtTenSize, txtGia, txtTim;
    private JComboBox<String> cboTrangThai;
    private JComboBox<SanPhamResponse> cboSanPham;

    private SizeServices service = new SizeServicesImpl();
    private SanPhamServices sanPhamService = new SanPhamServicesImpl();

    private final Color COLOR_SIDEBAR = new Color(23, 32, 42);
    private final Color COLOR_ORANGE = new Color(243, 156, 18);
    private final Color COLOR_BG_MAIN = new Color(213, 216, 220);

    public SizeView() {
        initUI();
        loadCboSanPham();
        loadTable(service.getAll());
    }

    private void initUI() {
        setTitle("Quản lý Size");
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
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(10, 10, 10, 10);
        f.fill = GridBagConstraints.HORIZONTAL;
        f.anchor = GridBagConstraints.NORTHWEST;

        txtId = new JTextField();
        txtId.setEditable(false);

        txtMaSize = new JTextField();
        txtTenSize = new JTextField();
        txtGia = new JTextField();
        txtTim = new JTextField();

        cboSanPham = new JComboBox<>();
        cboTrangThai = new JComboBox<>(new String[]{"Hiển thị", "Ẩn"});
        Dimension inputSize = new Dimension(180, 32);

        txtId.setPreferredSize(inputSize);
        txtMaSize.setPreferredSize(inputSize);
        txtTenSize.setPreferredSize(inputSize);
        txtGia.setPreferredSize(inputSize);
        cboSanPham.setPreferredSize(inputSize);
        cboTrangThai.setPreferredSize(inputSize);

        String[] labels = {"ID:", "Mã Size:", "Mã Sản phẩm:", "Tên Size:", "Giá:", "Trạng thái:"};

        JComponent[] inputs = {
            txtId,
            txtMaSize,
            cboSanPham,
            txtTenSize,
            txtGia,
            cboTrangThai
        };

        for (int i = 0; i < labels.length; i++) {
            f.gridx = 0;
            f.gridy = i;
            pnlForm.add(new JLabel(labels[i]), f);

            f.gridx = 1;
            pnlForm.add(inputs[i], f);
        }

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setOpaque(false);

        JButton btnAdd = createYellowBtn("Thêm");
        JButton btnUpdate = createYellowBtn("Sửa");
        JButton btnDelete = createYellowBtn("Xóa");
        JButton btnReset = createYellowBtn("Reset");

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        btnPanel.add(btnReset);

        f.gridx = 0;
        f.gridy = 7;
        f.gridwidth = 2;
        pnlForm.add(btnPanel, f);

        gbc.gridx = 0;
        gbc.weightx = 0.38;
        gbc.weighty = 1;
        gbc.insets = new Insets(25, 25, 25, 10);
        gbc.anchor = GridBagConstraints.NORTH;
        main.add(pnlForm, gbc);

        // ===== TABLE =====
        JPanel pnlTable = new JPanel(new BorderLayout(10, 10));
        pnlTable.setBackground(Color.WHITE);
        pnlTable.setBorder(new EmptyBorder(15, 15, 15, 15));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);

        searchPanel.add(new JLabel("Tìm mã size:"));
        txtTim.setPreferredSize(new Dimension(150, 30));
        searchPanel.add(txtTim);

        JButton btnSearch = createYellowBtn("Tìm");
        searchPanel.add(btnSearch);

        pnlTable.add(searchPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID", "Mã Size", "Mã Sản phẩm", "Tên Size", "Giá", "Trạng thái"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(25);

        pnlTable.add(new JScrollPane(table), BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.weightx = 0.62;
        gbc.insets = new Insets(25, 10, 25, 25);
        main.add(pnlTable, gbc);

        add(main, BorderLayout.CENTER);

        // ===== EVENT =====
        // Search
        btnSearch.addActionListener(e -> {
            String key = txtTim.getText().trim();
            loadTable(key.isEmpty() ? service.getAll() : service.search(key));
        });

        // Add
        btnAdd.addActionListener(e -> {
            try {
                String ma = txtMaSize.getText().trim();
                String ten = txtTenSize.getText().trim();
                String giaStr = txtGia.getText().trim();

                boolean trangThai = cboTrangThai.getSelectedItem().equals("Hiển thị");
                SanPhamResponse spRes = (SanPhamResponse) cboSanPham.getSelectedItem();

                if (ma.isEmpty() || ten.isEmpty() || giaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không được để trống!");
                    return;
                }

                if (spRes == null) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm!");
                    return;
                }
                if (spRes == null || spRes.getId() == 0) {
                    JOptionPane.showMessageDialog(this, "Mời chọn mã sản phẩm!");
                    return;
                }

                BigDecimal gia = new BigDecimal(giaStr);

                // convert sang SanPham
                SanPham sp = new SanPham();
                sp.setId(spRes.getId());

                Size s = new Size();
                s.setMaSize(ma);
                s.setTenSize(ten);
                s.setGiaChenhLech(gia);
                s.setTrangThaiHienThi(trangThai);
                s.setSanPham(sp);

                if (service.add(s)) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    loadTable(service.getAll());
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá phải là số!");
            }
        });

        // Update
        btnUpdate.addActionListener(e -> {
            try {
                String idStr = txtId.getText().trim();
                String ma = txtMaSize.getText().trim();
                String ten = txtTenSize.getText().trim();
                String giaStr = txtGia.getText().trim();

                if (idStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng cần sửa!");
                    return;
                }

                if (ma.isEmpty() || ten.isEmpty() || giaStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không được để trống!");
                    return;
                }

                SanPhamResponse spRes = (SanPhamResponse) cboSanPham.getSelectedItem();
                if (spRes == null) {
                    JOptionPane.showMessageDialog(this, "Chưa chọn sản phẩm!");
                    return;
                }
                if (spRes == null || spRes.getId() == 0) {
                    JOptionPane.showMessageDialog(this, "Mời chọn mã sản phẩm!");
                    return;
                }

                BigDecimal gia = new BigDecimal(giaStr);
                boolean trangThai = cboTrangThai.getSelectedItem().equals("Hiển thị");

                SanPham sp = new SanPham();
                sp.setId(spRes.getId());

                Size s = new Size();
                s.setId(Integer.parseInt(idStr));
                s.setMaSize(ma);
                s.setTenSize(ten);
                s.setGiaChenhLech(gia);
                s.setTrangThaiHienThi(trangThai);
                s.setSanPham(sp);

                // lấy mã cũ đang chọn trong table
                int row = table.getSelectedRow();
                String maCu = model.getValueAt(row, 1).toString();

                if (service.update(s, maCu)) {
                    JOptionPane.showMessageDialog(this, "Sửa thành công!");
                    loadTable(service.getAll());
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Giá phải là số!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Có lỗi xảy ra!");
            }
        });

        // Delete
        btnDelete.addActionListener(e -> {
            String id = txtId.getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Chọn dòng cần xóa!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có chắc muốn xóa?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            int row = table.getSelectedRow();
            String maSize = model.getValueAt(row, 1).toString();

            if (service.delete(maSize)) {
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                loadTable(service.getAll());
                resetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        });

        // Reset
        btnReset.addActionListener(e -> {
            resetForm();
            loadTable(service.getAll());
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    return;
                }

                txtId.setText(model.getValueAt(row, 0).toString());
                txtMaSize.setText(model.getValueAt(row, 1).toString());

                String maSP = model.getValueAt(row, 2).toString();

                for (int i = 0; i < cboSanPham.getItemCount(); i++) {
                    SanPhamResponse sp = cboSanPham.getItemAt(i);
                    if (sp.getMaSanPham().equals(maSP)) {
                        cboSanPham.setSelectedIndex(i);
                        break;
                    }
                }

                txtTenSize.setText(model.getValueAt(row, 3).toString());
                txtGia.setText(model.getValueAt(row, 4).toString());
                cboTrangThai.setSelectedItem(model.getValueAt(row, 5).toString());
            }
        });
    }

    private void loadTable(List<SizeViewModel> list) {
        model.setRowCount(0);
        for (SizeViewModel s : list) {
            model.addRow(new Object[]{
                s.getId(),
                s.getMaSize(),
                (s.getSanPham() != null) ? s.getSanPham().getMaSanPham() : "",
                s.getTenSize(),
                s.getGiaChenhLech(),
                s.isTrangThaiHienThi() ? "Hiển thị" : "Ẩn"
            });
        }
    }

    private void loadCboSanPham() {
        cboSanPham.removeAllItems();

        SanPhamResponse macDinh = new SanPhamResponse();
        macDinh.setId(0);
        macDinh.setMaSanPham("Chọn mã sản phẩm");

        cboSanPham.addItem(macDinh);

        for (SanPhamResponse sp : sanPhamService.getAll()) {
            cboSanPham.addItem(sp);
        }

        cboSanPham.setSelectedIndex(0);
    }

    private JButton createYellowBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(255, 215, 0));
        return b;
    }

    private void resetForm() {
        txtId.setText("");
        txtMaSize.setText("");
        txtTenSize.setText("");
        txtGia.setText("");
        cboTrangThai.setSelectedIndex(0);

        if (cboSanPham.getItemCount() > 0) {
            cboSanPham.setSelectedIndex(0);
        }
    }

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

        String[] menu = {"Bán hàng", "Danh Mục", "Sản phẩm", "Size", "Nhân viên", "Khách hàng", "Thống kê"};

        int y = 1;
        for (String m : menu) {
            JButton btn = new JButton(m);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setForeground(Color.WHITE);
            btn.setContentAreaFilled(false);
            btn.setOpaque(true);
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setBorder(new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY));

            // Logic tô màu cam cho nút "Size" đang được chọn
            if (m.equals("Size")) {
                btn.setBackground(COLOR_ORANGE);
            } else {
                btn.setBackground(COLOR_SIDEBAR);
            }
            
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
                    default:
                        JOptionPane.showMessageDialog(this, "Chức năng " + m + " đang phát triển!");
                        break;
                }
            });

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

    public static void main(String[] args) {
        try {
            // Thêm dòng này để đồng bộ giao diện
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new SizeView().setVisible(true));
    }
}
