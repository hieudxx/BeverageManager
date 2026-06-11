package View;

import DomainModels.DanhMuc;
import Services.impl.DanhMucServiceImpl;
import ViewModels.DanhMucViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import Services.DanhMucService;

public class DanhMucView extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtMa, txtTen, txtTim;
    private JComboBox<String> cboTrangThai;

    private DanhMucService service = new DanhMucServiceImpl();

    private final Color COLOR_SIDEBAR = new Color(23, 32, 42);
    private final Color COLOR_ORANGE = new Color(243, 156, 18);
    private final Color COLOR_BG_MAIN = new Color(213, 216, 220);

    public DanhMucView() {
        initUI();
        loadTable(service.getAllView());
    }

    private void initUI() {
        setTitle("Quản lý danh mục");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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

        txtId = new JTextField();
        txtId.setEditable(false);
        txtMa = new JTextField();
        txtTen = new JTextField();
        txtTim = new JTextField();

        cboTrangThai = new JComboBox<>(new String[]{"Hiển thị", "Ẩn"});

        String[] labels = {"ID:", "Mã:", "Tên:", "Trạng thái:"};
        JComponent[] inputs = {txtId, txtMa, txtTen, cboTrangThai};

        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);

            if (i == 0) {
                lbl.setVisible(false);
                inputs[i].setVisible(false);
            }
            f.gridx = 0;
            f.gridy = i;
            pnlForm.add(lbl, f);

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
        f.gridy = 5;
        f.gridwidth = 2;
        pnlForm.add(btnPanel, f);

        gbc.gridx = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1;
        main.add(pnlForm, gbc);

        // ===== TABLE =====
        JPanel pnlTable = new JPanel(new BorderLayout(10, 10));
        pnlTable.setBackground(Color.WHITE);
        pnlTable.setBorder(new EmptyBorder(15, 15, 15, 15));

        // ===== SEARCH (ĐÃ FIX) =====
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setOpaque(false);

        GridBagConstraints s = new GridBagConstraints();
        s.insets = new Insets(5, 5, 5, 5);
        s.fill = GridBagConstraints.HORIZONTAL;

        // label
        s.gridx = 0;
        s.gridy = 0;
        s.weightx = 0;
        searchPanel.add(new JLabel("Tìm mã:"), s);

        // input
        txtTim.setPreferredSize(new Dimension(100, 30));
        s.gridx = 1;
        s.weightx = 0;
        searchPanel.add(txtTim, s);

        // button
        JButton btnSearch = createYellowBtn("Tìm");
        btnSearch.setPreferredSize(new Dimension(90, 30));

        s.gridx = 2;
        s.weightx = 0;
        searchPanel.add(btnSearch, s);

//        pnlTable.add(searchPanel, BorderLayout.NORTH);
        JPanel wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        wrapper.setOpaque(false);
        wrapper.add(searchPanel);

        pnlTable.add(wrapper, BorderLayout.NORTH);

        // TABLE
        model = new DefaultTableModel(
                new String[]{"ID", "Mã", "Tên", "Trạng thái"}, 0
        );

        table = new JTable(model);
        table.setRowHeight(25);

        pnlTable.add(new JScrollPane(table), BorderLayout.CENTER);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        main.add(pnlTable, gbc);

        add(main, BorderLayout.CENTER);

        // ===== EVENT =====
        // Search
        btnSearch.addActionListener(e -> {
            String ma = txtTim.getText().trim();
            if (ma.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nhập mã cần tìm");
                return;
            }
            loadTable(service.search(ma));
        });

        // Add
        btnAdd.addActionListener(e -> {
            try {
                String ma = txtMa.getText().trim();
                String ten = txtTen.getText().trim();
                boolean trangThai = cboTrangThai.getSelectedItem().equals("Hiển thị");

                if (ma.isEmpty() || ten.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không được để trống!");
                    return;
                }

                //DanhMucViewModel dm = new DanhMucViewModel();
                DanhMuc dm = new DanhMuc();
                dm.setMaDanhMuc(ma);
                dm.setTenDanhMuc(ten);
                dm.setTrangThaiHienThi(trangThai);

                boolean check = service.add(dm);

                if (check) {
                    JOptionPane.showMessageDialog(this, "Thêm thành công!");
                    loadTable(service.getAllView());
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Update
        btnUpdate.addActionListener(e -> {
            try {
                // Lấy ID
                if (txtId.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng cần sửa!");
                    return;
                }

                int id = Integer.parseInt(txtId.getText());
                String ma = txtMa.getText().trim();
                String ten = txtTen.getText().trim();
                boolean trangThai = cboTrangThai.getSelectedItem().equals("Hiển thị");

                if (ma.isEmpty() || ten.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Không được để trống!");
                    return;
                }

                // Tạo object
                DanhMuc dm = new DanhMuc();
                dm.setId(id);
                dm.setMaDanhMuc(ma);
                dm.setTenDanhMuc(ten);
                dm.setTrangThaiHienThi(trangThai);

                // Gọi update
                boolean check = service.update(dm, dm.getMaDanhMuc());

                if (check) {
                    JOptionPane.showMessageDialog(this, "Sửa thành công!");
                    loadTable(service.getAllView());
                    resetForm();
                } else {
                    JOptionPane.showMessageDialog(this, "Sửa thất bại!");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Delete
        btnDelete.addActionListener(e -> {
            try {
                // Lấy mã từ textbox
                String maDM = txtMa.getText().trim();

                // Kiểm tra chưa chọn dòng
                if (maDM.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Chọn dòng cần xóa!");
                    return;
                }

                // Hộp thoại xác nhận
                int confirm = JOptionPane.showConfirmDialog(
                        this,
                        "Bạn có chắc muốn xóa?",
                        "Xác nhận",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }

                // Gọi service để xóa
                boolean check = service.delete(maDM);

                if (check) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    loadTable(service.getAllView()); // reload bảng
                    resetForm(); // reset form
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!");
                }

            } catch (Exception ex) {
                ex.printStackTrace(); // debug lỗi
            }
        });

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    return;
                }

                txtId.setText(model.getValueAt(row, 0).toString());
                txtMa.setText(model.getValueAt(row, 1).toString());
                txtTen.setText(model.getValueAt(row, 2).toString());
                cboTrangThai.setSelectedItem(model.getValueAt(row, 3).toString());
                txtId.setEditable(false);
            }
        });
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
            btn.setBackground(m.equals("Danh Mục") ? COLOR_ORANGE : COLOR_SIDEBAR);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setBorder(new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY));

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

    private JButton createYellowBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(255, 215, 0));
        b.setFont(new Font("Arial", Font.BOLD, 12));
        return b;
    }

    private void loadTable(List<DanhMucViewModel> list) {
        model.setRowCount(0);
        for (DanhMucViewModel dm : list) {
            model.addRow(new Object[]{
                dm.getId(),
                dm.getMaDanhMuc(),
                dm.getTenDanhMuc(),
                dm.isTrangThaiHienThi() ? "Hiển thị" : "Ẩn"
            });
        }
        if (table.getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setMinWidth(0);
            table.getColumnModel().getColumn(0).setMaxWidth(0);
            table.getColumnModel().getColumn(0).setPreferredWidth(0);
        }
    }

    public static void main(String[] args) {
        try {
            // Thêm dòng này để đồng bộ giao diện
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new DanhMucView().setVisible(true));
    }

    private void resetForm() {
        txtId.setText("");
        txtMa.setText("");
        txtTen.setText("");
        cboTrangThai.setSelectedIndex(0);
        txtId.setEditable(true);
    }
}
