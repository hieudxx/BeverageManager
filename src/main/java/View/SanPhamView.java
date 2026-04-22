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

    private SanPhamServices service = new SanPhamServicesImpl();
    private DanhMucServices danhMucService = new DanhMucServicesImpl();

    private final Color COLOR_SIDEBAR = new Color(23, 32, 42);
    private final Color COLOR_ORANGE = new Color(243, 156, 18);
    private final Color COLOR_BG_MAIN = new Color(213, 216, 220);

    public SanPhamView() {
        initUI();
        loadDanhMuc();
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
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;

        // ===== FORM =====
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(new EmptyBorder(20,20,20,20));

        GridBagConstraints f = new GridBagConstraints();
        f.insets = new Insets(10,10,10,10);
        f.fill = GridBagConstraints.HORIZONTAL;

        txtMa = new JTextField();
        txtTen = new JTextField();
        txtGia = new JTextField();

        txtHinhAnh = new JTextField();
        txtHinhAnh.setEditable(false);

        JButton btnChonAnh = new JButton("Chọn ảnh");

        JPanel pnlImage = new JPanel(new BorderLayout(5,5));
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
        lblHinhAnh.setPreferredSize(new Dimension(180,180));
        lblHinhAnh.setBorder(BorderFactory.createTitledBorder("Ảnh sản phẩm"));
        lblHinhAnh.setHorizontalAlignment(JLabel.CENTER);

        f.gridx = 0;
        f.gridy = labels.length;
        f.gridwidth = 2;
        form.add(lblHinhAnh, f);

        // ===== BUTTON =====
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setOpaque(false);

        btnPanel.add(createYellowBtn("Thêm"));
        btnPanel.add(createYellowBtn("Sửa"));
        btnPanel.add(createYellowBtn("Xóa"));
        btnPanel.add(createYellowBtn("Reset"));

        f.gridy = labels.length + 1;
        form.add(btnPanel, f);

        gbc.gridx = 0;
        gbc.weightx = 0.4;
        main.add(form, gbc);

        // ===== TABLE =====
        JPanel pnlTable = new JPanel(new BorderLayout(10,10));
        pnlTable.setBackground(Color.WHITE);
        pnlTable.setBorder(new EmptyBorder(15,15,15,15));

        txtTim = new JTextField(15);
        JButton btnSearch = createYellowBtn("Tìm");

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setOpaque(false);
        searchPanel.add(new JLabel("Tìm:"));
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
            fc.setFileFilter(new FileNameExtensionFilter("Image", "jpg","png","jpeg"));

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
                if (row == -1) return;

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

//        btnSearch.addActionListener(e -> {
//            String keyword = txtTim.getText().trim();
//            loadTable(service.search(keyword));
//        });
    }

    // ===== LOAD DANH MỤC =====
    private void loadDanhMuc() {
        cboDanhMuc.removeAllItems();

        List<DanhMuc> list = danhMucService.getAll();

        for (DanhMuc dm : list) {
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
    private void showImage(String path) {
        try {
            Image img = new ImageIcon(path).getImage()
                    .getScaledInstance(180,180,Image.SCALE_SMOOTH);
            lblHinhAnh.setIcon(new ImageIcon(img));
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

        String[] menu = {"Bán hàng","Danh Mục", "Sản phẩm", "Size", "Nhân viên", "Khách hàng", "Thống kê"};
        int y = 1;

        for (String m : menu) {
            JButton btn = new JButton(m);
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setBackground(m.equals("Sản phẩm") ? COLOR_ORANGE : COLOR_SIDEBAR);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setBorder(new MatteBorder(0,0,1,0, Color.DARK_GRAY));

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
        btnExit.setBorder(new MatteBorder(1,0,0,0, Color.DARK_GRAY));

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
}