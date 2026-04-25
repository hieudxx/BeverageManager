package View;

import Services.SizeServices;
import Services.impl.SizeServicesImpl;
import ViewModels.SizeViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class SizeView extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField txtId, txtMaSize, txtMaSanPham, txtTenSize, txtGia, txtTim;
    private JComboBox<String> cboTrangThai;

    private SizeServices service = new SizeServicesImpl();

    private final Color COLOR_SIDEBAR = new Color(23, 32, 42);
    private final Color COLOR_ORANGE = new Color(243, 156, 18);
    private final Color COLOR_BG_MAIN = new Color(213, 216, 220);

    public SizeView() {
        initUI();
        loadTable(service.getAll()); // load list ban đầu
    }

    private void initUI() {
        setTitle("Quản lý Size");
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

        txtMaSize = new JTextField();
        txtMaSanPham = new JTextField();
        txtTenSize = new JTextField();
        txtGia = new JTextField();
        txtTim = new JTextField();

        cboTrangThai = new JComboBox<>(new String[]{"Hiển thị", "Ẩn"});

        String[] labels = {"ID:", "Mã Size:", "Mã Sản phẩm:", "Tên Size:", "Giá:", "Trạng thái:"};
        JComponent[] inputs = {txtId, txtMaSize, txtMaSanPham, txtTenSize, txtGia, cboTrangThai};

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
        gbc.weightx = 0.4;
        gbc.weighty = 1;
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
        gbc.weightx = 0.6;
        main.add(pnlTable, gbc);

        add(main, BorderLayout.CENTER);

        // ===== EVENT =====

        // SEARCH (ĐÃ FIX)
        btnSearch.addActionListener(e -> {
            String key = txtTim.getText().trim();

            if (key.isEmpty()) {
                loadTable(service.getAll());
            } else {
                loadTable(service.search(key));
            }
        });

        // RESET
        btnReset.addActionListener(e -> {
            resetForm();
            loadTable(service.getAll());
        });

        // CLICK TABLE
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row == -1) return;

                txtId.setText(model.getValueAt(row, 0).toString());
                txtMaSize.setText(model.getValueAt(row, 1).toString());
                txtMaSanPham.setText(model.getValueAt(row, 2).toString());
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
                    s.getTenSizel(),
                    s.getGiaChenhLech(),
                    s.isTrangThaiHienThi() ? "Hiển thị" : "Ẩn"
            });
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
        g.gridy = 0;
        p.add(logo, g);

        String[] menu = {"Bán hàng", "Danh Mục", "Sản phẩm", "Size", "Nhân viên", "Khách hàng", "Thống kê"};
        int y = 1;

        for (String m : menu) {
            JButton btn = new JButton(m);
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setBackground(m.equals("Size") ? COLOR_ORANGE : COLOR_SIDEBAR);
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

        g.gridy = y;
        p.add(btnExit, g);

        return p;
    }

    private JButton createYellowBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(255, 215, 0));
        b.setFont(new Font("Arial", Font.BOLD, 12));
        return b;
    }

    private void resetForm() {
        txtId.setText("");
        txtMaSize.setText("");
        txtMaSanPham.setText("");
        txtTenSize.setText("");
        txtGia.setText("");
        cboTrangThai.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SizeView().setVisible(true));
    }
}