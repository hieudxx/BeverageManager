package View;

import DomainModels.HoaDon;
import DomainModels.HoaDonChiTiet;
import DomainModels.NhanVien;
import DomainModels.Size;
import DomainModels.DanhMuc;
import DomainModels.KhachHang;
import DomainModels.SanPham;
import Services.HoaDonChiTietService;
import Services.HoaDonService;
import Services.KhachHangService;
import Services.impl.DanhMucServiceImpl;
import Services.impl.HoaDonChiTietServiceImpl;
import Services.impl.HoaDonServiceImpl;
import Services.impl.KhachHangServiceImpl;
import Services.impl.SanPhamServiceImpl;
import Services.impl.SizeServiceImpl;
import Utilities.SessionUser;
import ViewModels.KhachHangViewModel;
import ViewModels.DanhMucViewModel;
import ViewModels.SanPhamViewModel;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

import java.io.File;
import java.io.FileOutputStream;
import java.awt.Desktop;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Element;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.BaseFont;
import java.io.InputStream;
import Services.DanhMucService;
import Services.SanPhamService;
import Services.SizeService;

public class BanHangView extends JFrame {

    private SanPhamService spService = new SanPhamServiceImpl();
    private HoaDonService hoaDonService = new HoaDonServiceImpl();
    private HoaDonChiTietService hdctService = new HoaDonChiTietServiceImpl();
    private SizeService sizeService = new SizeServiceImpl();
    private KhachHangService khService = new KhachHangServiceImpl();
    private DefaultTableModel dtmCart;
    private JTable tblCart;

    private DanhMucService dmService = new DanhMucServiceImpl();
    private JTextField txtSearchSP;
    private JComboBox<Object> cboDanhMuc;
    private List<SanPhamViewModel> allProducts;

    private JTable tblPending;
    private DefaultTableModel dtmPending;

    private JTextField txtSdt;
    private JLabel lblHoTenKH;
    private JLabel lblMaHoaDon;
    private JLabel lblTongTien;
    private JTextField txtTienKhachDua;
    private JLabel lblTienThua;
    private JComboBox<String> cboHinhThucThanhToan;

    private JPanel gridProduct;
    private final Color COLOR_SIDEBAR = new Color(23, 32, 42);
    private final Color COLOR_ORANGE_ACTIVE = new Color(243, 156, 18);
    private final Color COLOR_YELLOW_BTN = new Color(255, 215, 0);
    private final Color COLOR_BG_MAIN = new Color(213, 216, 220);

    public BanHangView() {

        allProducts = spService.getAll();

        initUI();
        loadTableHoaDonCho(null);
    }

    private void initUI() {
        setTitle("Hệ Thống Quản Lý Bán Hàng - Fixed Scroll Version");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1300, 850));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(COLOR_BG_MAIN);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel leftCol = new JPanel(new BorderLayout(0, 10));
        leftCol.setOpaque(false);

        JPanel pnlProduct = new JPanel(new BorderLayout(0, 10));
        pnlProduct.setBackground(Color.WHITE);
        pnlProduct.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pnlFilter.setOpaque(false);

        pnlFilter.add(new JLabel("Tìm kiếm sản phẩm"));
        txtSearchSP = new JTextField(12);
        pnlFilter.add(txtSearchSP);

        pnlFilter.add(new JLabel("Loại sản phẩm"));
        cboDanhMuc = new JComboBox<>();
        loadComboDanhMuc();
        pnlFilter.add(cboDanhMuc);

        pnlProduct.add(pnlFilter, BorderLayout.NORTH);

        txtSearchSP.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                loadDataToGrid();
            }
        });

        cboDanhMuc.addActionListener(e -> loadDataToGrid());

        gridProduct = new JPanel(new GridLayout(0, 4, 15, 15));
        gridProduct.setBackground(Color.WHITE);

        loadDataToGrid();

        JPanel pnlWrapper = new JPanel(new BorderLayout());
        pnlWrapper.setBackground(Color.WHITE);
        pnlWrapper.add(gridProduct, BorderLayout.NORTH);

        JScrollPane spProduct = new JScrollPane(pnlWrapper);
        spProduct.setBorder(null);
        spProduct.getVerticalScrollBar().setUnitIncrement(25);
        pnlProduct.add(spProduct, BorderLayout.CENTER);

        JPanel pnlPending = new JPanel(new BorderLayout());
        pnlPending.setBackground(Color.WHITE);
        pnlPending.setPreferredSize(new Dimension(0, 250));
        pnlPending.setBorder(BorderFactory.createTitledBorder("Hóa đơn chờ"));

        String[] colsHD = {"Mã HĐ", "Người tạo", "Khách hàng", "Thời gian tạo", "Trạng thái"};
        dtmPending = new DefaultTableModel(colsHD, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblPending = new JTable(dtmPending);
        pnlPending.add(new JScrollPane(tblPending), BorderLayout.CENTER);

        leftCol.add(pnlProduct, BorderLayout.CENTER);
        leftCol.add(pnlPending, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.weightx = 0.65;
        gbc.weighty = 1.0;
        mainPanel.add(leftCol, gbc);

        tblPending.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tblPending.getSelectedRow();
                if (row != -1) {
                    String maHD = tblPending.getValueAt(row, 0).toString();
                    HoaDon hdSelected = hoaDonService.selectByMaHD(maHD);

                    if (hdSelected != null) {

                        loadTableGioHang(hdSelected.getId());
                        tinhTongTienHoaDon(hdSelected.getId());

                        lblMaHoaDon.setText(hdSelected.getMaHoaDon());
                        lblMaHoaDon.setForeground(Color.BLUE);

                        if (hdSelected.getKhachHang() != null
                                && hdSelected.getKhachHang().getHoTen() != null
                                && !hdSelected.getKhachHang().getHoTen().isBlank()) {

                            txtSdt.setText(hdSelected.getKhachHang().getSoDienThoai());
                            lblHoTenKH.setText(hdSelected.getKhachHang().getHoTen());
                            lblHoTenKH.setForeground(Color.BLACK);
                        } else {
                            txtSdt.setText("");
                            lblHoTenKH.setText("Khách lẻ");
                            lblHoTenKH.setForeground(Color.GRAY);
                        }

                        txtTienKhachDua.setText("");
                        lblTienThua.setText("0 VNĐ");
                    }
                }
            }
        });

        JPanel rightCol = new JPanel(new BorderLayout(0, 10));
        rightCol.setOpaque(false);
        rightCol.setPreferredSize(new Dimension(450, 0));

        JPanel pnlCart = new JPanel(new BorderLayout(0, 5));
        pnlCart.setBackground(Color.WHITE);
        pnlCart.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));

        String[] colsCart = {"STT", "Tên SP", "Đơn giá", "Số lượng", "Thành tiền", "ID"};
        dtmCart = new DefaultTableModel(colsCart, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblCart = new JTable(dtmCart);
        tblCart.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tblCart.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblCart.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblCart.getColumnModel().getColumn(1).setPreferredWidth(190);
        tblCart.getColumnModel().getColumn(2).setPreferredWidth(65);
        tblCart.getColumnModel().getColumn(3).setPreferredWidth(60);
        tblCart.getColumnModel().getColumn(4).setPreferredWidth(70);
        // Ẩn cột ID
        tblCart.getColumnModel().getColumn(5).setMinWidth(0);
        tblCart.getColumnModel().getColumn(5).setMaxWidth(0);
        tblCart.getColumnModel().getColumn(5).setPreferredWidth(0);

        JScrollPane spCart = new JScrollPane(tblCart);

        spCart.setPreferredSize(new Dimension(430, 350));
        spCart.setMinimumSize(new Dimension(430, 300));
        spCart.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        pnlCart.add(spCart, BorderLayout.CENTER);

        JPanel pnlCartBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlCartBtn.setOpaque(false);

        JButton btnXoaSp = createYellowBtn("Xóa sản phẩm");
        btnXoaSp.addActionListener(e -> {
            int row = tblCart.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sản phẩm trong giỏ hàng để xóa!");
                return;
            }

            int idHDCT = Integer.parseInt(tblCart.getValueAt(row, 5).toString());

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa sản phẩm này khỏi giỏ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                hdctService.deleteOne(idHDCT);
                HoaDon hd = hoaDonService.selectByMaHD(lblMaHoaDon.getText());
                loadTableGioHang(hd.getId());
                tinhTongTienHoaDon(hd.getId());
            }
        });

        JButton btnXoaTatCa = createYellowBtn("Xóa tất cả");
        btnXoaTatCa.addActionListener(e -> {
            String maHD = lblMaHoaDon.getText();
            if (maHD.equals("Vui lòng tạo!") || maHD.isEmpty()) {
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa TOÀN BỘ giỏ hàng?", "Cảnh báo", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                HoaDon hd = hoaDonService.selectByMaHD(maHD);

                hdctService.delete(hd.getId());

                loadTableGioHang(hd.getId());
                tinhTongTienHoaDon(hd.getId());
            }
        });

        pnlCartBtn.add(btnXoaSp);
        pnlCartBtn.add(btnXoaTatCa);
        pnlCart.add(pnlCartBtn, BorderLayout.SOUTH);

        rightCol.add(pnlCart, BorderLayout.CENTER);
        rightCol.add(createPaymentPanel(), BorderLayout.SOUTH);

        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.BOTH;
        rightCol.setPreferredSize(new Dimension(450, 0));
        mainPanel.add(rightCol, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    public int loadTableHoaDonCho(String maVuaTao) {
        dtmPending.setRowCount(0);
        List<HoaDon> list = hoaDonService.selectByHDChoTT();
        int targetRow = -1;

        if (list == null || list.isEmpty()) {
            return targetRow;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

        for (int i = 0; i < list.size(); i++) {
            HoaDon hd = list.get(i);
            String tenNV = (hd.getNhanVien() != null) ? hd.getNhanVien().getHoTen() : "N/A";
            String tenKH = (hd.getKhachHang() != null && hd.getKhachHang().getHoTen() != null)
                    ? hd.getKhachHang().getHoTen() : "Khách lẻ";
            String thoiGian = (hd.getNgayTao() != null) ? hd.getNgayTao().format(formatter) : "";

            dtmPending.addRow(new Object[]{
                hd.getMaHoaDon(),
                tenNV,
                tenKH,
                thoiGian,
                hd.getTrangThai(),
                ""
            });

            if (maVuaTao != null && hd.getMaHoaDon().equals(maVuaTao)) {
                targetRow = i;
            }
        }
        return targetRow;
    }

    private void loadComboDanhMuc() {
        cboDanhMuc.removeAllItems();
        cboDanhMuc.addItem("Tất cả");
        try {
            List<DanhMucViewModel> list = dmService.getAllByTrangThai();
            for (DanhMucViewModel dm : list) {
                cboDanhMuc.addItem(dm.getTenDanhMuc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadDataToGrid() {
        gridProduct.removeAll();

        String keyword = txtSearchSP.getText().trim().toLowerCase();

        String loaiSelected = (cboDanhMuc.getSelectedItem() == null)
                ? "Tất cả" : cboDanhMuc.getSelectedItem().toString();

        for (SanPhamViewModel sp : allProducts) {

            boolean isPublic = sp.isDangBan() && sp.isTrangThaiHienThi();

            boolean matchesName = sp.getTenSanPham().toLowerCase().contains(keyword);

            boolean matchesCategory = loaiSelected.equals("Tất cả")
                    || sp.getTenDanhMuc().equalsIgnoreCase(loaiSelected);

            if (isPublic && matchesName && matchesCategory) {
                String giaVND = String.format("%,.0f VNĐ", sp.getGiaCoBan());
                String tenFile = (sp.getHinhAnh() == null || sp.getHinhAnh().isBlank())
                        ? "default.png" : sp.getHinhAnh();

                JPanel card = createProductCard(sp.getTenSanPham(), giaVND, tenFile);

                card.addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent evt) {

                        String maHD = lblMaHoaDon.getText();
                        if (maHD == null || maHD.equals("Vui lòng tạo!") || maHD.isEmpty()) {
                            lblMaHoaDon.setForeground(Color.RED);
                            JOptionPane.showMessageDialog(null,
                                    "Vui lòng chọn một hóa đơn chờ hoặc nhấn 'Tạo' mới trước khi thêm món!",
                                    "Thông báo",
                                    JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        List<Size> listSize = sizeService.getSizesBySPId(sp.getId());

                        if (listSize == null || listSize.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "Sản phẩm này chưa cấu hình Size!");
                            return;
                        }

                        Object[] options = new Object[listSize.size()];
                        for (int i = 0; i < listSize.size(); i++) {
                            Size s = listSize.get(i);
                            options[i] = s.getTenSize() + " (+" + String.format("%,.0f", s.getGiaChenhLech()) + "đ)";
                        }

                        int choice = JOptionPane.showOptionDialog(null,
                                "Chọn Size cho " + sp.getTenSanPham(),
                                "Lựa chọn Size",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null, options, options[0]);

                        if (choice != -1) {
                            Size sizeSelected = listSize.get(choice);

                            SanPham spModel = new DomainModels.SanPham();
                            spModel.setId(sp.getId());
                            spModel.setTenSanPham(sp.getTenSanPham());
                            spModel.setGiaCoBan(sp.getGiaCoBan());

                            sizeSelected.setSanPham(spModel);

                            addSanPhamToGioHang(sizeSelected);
                        }
                    }
                });
                gridProduct.add(card);
            }
        }
        gridProduct.revalidate();
        gridProduct.repaint();
    }

    private JPanel createProductCard(String name, String price, String imagePath) {
        JPanel card = new JPanel(new BorderLayout(0, 2));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        card.setPreferredSize(new Dimension(180, 300));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblName = new JLabel("<html><center>" + name + "</center></html>", SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 13));

        lblName.setPreferredSize(new Dimension(0, 48));
        card.add(lblName, BorderLayout.NORTH);

        JLabel lblImageContent = new JLabel("", SwingConstants.CENTER);
        lblImageContent.setOpaque(false);

        JPanel pnlImageWrapper = new JPanel(new GridBagLayout());
        pnlImageWrapper.setBackground(new Color(245, 245, 245));
        pnlImageWrapper.add(lblImageContent);

        try {
            java.net.URL imgURL = getClass().getResource("/images/" + imagePath);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(160, 200, Image.SCALE_SMOOTH);
                lblImageContent.setIcon(new ImageIcon(img));
            } else {
                lblImageContent.setText("NOT FOUND");
                lblImageContent.setBorder(new EmptyBorder(10, 10, 10, 10));
            }
        } catch (Exception e) {
            lblImageContent.setText("ERROR");
        }

        card.add(pnlImageWrapper, BorderLayout.CENTER);

        JLabel lblPrice = new JLabel(price, SwingConstants.CENTER);
        lblPrice.setForeground(new Color(211, 47, 47));
        lblPrice.setFont(new Font("Arial", Font.BOLD, 14));
        lblPrice.setBorder(new EmptyBorder(5, 0, 10, 0));
        card.add(lblPrice, BorderLayout.SOUTH);

        return card;
    }

    private JPanel createSidebar() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(COLOR_SIDEBAR);
        p.setPreferredSize(new Dimension(200, 0));
        p.setMinimumSize(new Dimension(200, 0));
        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;

        JPanel logo = new JPanel();
        logo.setBackground(new Color(255, 204, 0));
        logo.setPreferredSize(new Dimension(200, 150));
        logo.setMinimumSize(new Dimension(200, 150));

        ImageIcon icon = new ImageIcon(getClass().getResource("/images/logoNootea.png"));
        Image img = icon.getImage();

        Image scaledImg = img.getScaledInstance(200, 150, Image.SCALE_SMOOTH);

        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel lblLogo = new JLabel(scaledIcon, JLabel.CENTER);
        logo.add(lblLogo, BorderLayout.CENTER);

        g.gridy = 0;
        p.add(logo, g);

        String[] menu = {"Bán hàng", "Danh Mục", "Sản phẩm", "Size", "Nhân viên", "Khách hàng"};

        NhanVien user = SessionUser.getInstance().getCurrentUser();
        String role = user.getVaiTro();

        int y = 1;
        for (String m : menu) {
            JButton btn = new JButton(m);
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setMinimumSize(new Dimension(200, 60));
            btn.setContentAreaFilled(false);
            btn.setOpaque(true);
            btn.setFocusPainted(false);
            btn.setBackground(m.equals("Bán hàng") ? COLOR_ORANGE_ACTIVE : COLOR_SIDEBAR);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setBorder(new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY));

            if (role.equalsIgnoreCase("Nhân viên") && !m.equals("Bán hàng")) {
                btn.setEnabled(false);
                btn.setBackground(new Color(60, 60, 60));
            }

            btn.addActionListener(e -> {

                if (role.equalsIgnoreCase("Nhân viên") && !m.equals("Bán hàng")) {
                    JOptionPane.showMessageDialog(this, "Bạn không có quyền truy cập!");
                    return;
                }

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
                System.exit(0);
            }
        });
        g.gridy = y;
        g.weighty = 0;
        p.add(btnExit, g);

        return p;
    }

    private void taoHoaDonMoi() {

        if (Utilities.Auth.user == null) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy thông tin nhân viên đăng nhập. Vui lòng đăng nhập lại!");
            return;
        }
        HoaDon hd = new HoaDon();

        hd.setNhanVien(Utilities.Auth.user);

        if (Utilities.Auth.user == null) {
            JOptionPane.showMessageDialog(this, "Lỗi: Không tìm thấy thông tin nhân viên đăng nhập. Vui lòng đăng nhập lại!");
            return;
        }

        hd.setNhanVien(Utilities.Auth.user);

        String maMoi = "HD" + System.currentTimeMillis();
        String hinhThucChon = cboHinhThucThanhToan.getSelectedItem().toString();
        hd.setMaHoaDon(maMoi);
        hd.setNgayTao(java.time.LocalDateTime.now());
        hd.setTongTien(BigDecimal.ZERO);
        hd.setTienThanhToan(BigDecimal.ZERO);
        hd.setPhuongThucTT(hinhThucChon);
        hd.setTrangThai("Chờ thanh toán");

        String sdt = txtSdt.getText().trim();
        if (sdt.isEmpty()) {
            hd.setKhachHang(null);
        } else {

            KhachHangViewModel kh = khService.getBySdt(sdt);

            if (kh != null) {

                KhachHang khModel = new KhachHang();
                khModel.setId(kh.getId());
                khModel.setMaKhachHang(kh.getMaKhachHang());
                khModel.setSoDienThoai(kh.getSoDienThoai());
                khModel.setHoTen(kh.getHoTen());
                khModel.setTrangThai(kh.isTrangThai());

                hd.setKhachHang(khModel);
            } else {
                hd.setKhachHang(null);
            }
        }

        int result = hoaDonService.insert(hd);

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!");

            int rowIndex = loadTableHoaDonCho(maMoi);

            if (rowIndex != -1) {
                tblPending.setRowSelectionInterval(rowIndex, rowIndex);

                tblPending.scrollRectToVisible(tblPending.getCellRect(rowIndex, 0, true));
            }

            HoaDon hdMoi = hoaDonService.selectByMaHD(maMoi);

            if (hdMoi != null) {
                loadTableGioHang(hdMoi.getId());

                txtSdt.setText(hdMoi.getKhachHang() != null ? hdMoi.getKhachHang().getSoDienThoai() : "");

            }

            lblMaHoaDon.setText(hd.getMaHoaDon());
            lblMaHoaDon.setForeground(Color.BLUE);
        } else {
            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thất bại!");
        }
        lblHoTenKH.setText("Khách lẻ");
        txtSdt.setText("");
    }

    private JPanel createPaymentPanel() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 5, 8, 5);
        g.fill = GridBagConstraints.HORIZONTAL;

        String[] fields = {"SĐT:", "Họ tên:", "Mã hóa đơn:", "Tổng tiền:", "Tiền khách thanh toán:", "Tiền thừa:", "Hình thức:"};
        for (int i = 0; i < fields.length; i++) {
            g.gridx = 0;
            g.gridy = i;
            g.weightx = 0.3;
            p.add(new JLabel(fields[i]), g);
            g.gridx = 1;
            g.weightx = 0.7;
            if (i == 0) {
                JPanel pnlSdt = new JPanel(new BorderLayout(5, 0));
                pnlSdt.setOpaque(false);
                txtSdt = new JTextField();
                pnlSdt.add(txtSdt, BorderLayout.CENTER);
                JButton btnTimKhachHang = new JButton("👤");
                btnTimKhachHang.addActionListener(e -> {
                    timKiemKhachHangTheoSdt();
                });

                txtSdt.addActionListener(e -> {
                    timKiemKhachHangTheoSdt();
                });
                p.add(pnlSdt, g);
            } else if (i == 1) {
                lblHoTenKH = new JLabel("Khách lẻ");
                lblHoTenKH.setFont(new Font("Arial", Font.ITALIC, 13));
                p.add(lblHoTenKH, g);
            } else if (i == 2) {
                JPanel pnlMa = new JPanel(new BorderLayout(5, 0));
                pnlMa.setOpaque(false);

                lblMaHoaDon = new JLabel("Vui lòng tạo!");
                lblMaHoaDon.setForeground(Color.RED);
                lblMaHoaDon.setPreferredSize(new Dimension(150, 25));

                JButton btnTaoHD = createYellowBtn("Tạo");

                btnTaoHD.addActionListener(e -> {
                    taoHoaDonMoi();
                });

                pnlMa.add(lblMaHoaDon, BorderLayout.CENTER);
                pnlMa.add(btnTaoHD, BorderLayout.EAST);
                p.add(pnlMa, g);
            } else if (i == 3) {

                lblTongTien = new JLabel("0 VNĐ");
                lblTongTien.setPreferredSize(new Dimension(150, 25));
                lblTongTien.setFont(new Font("Arial", Font.BOLD, 15));
                lblTongTien.setForeground(Color.RED);
                p.add(lblTongTien, g);
            } else if (i == 4) {
                txtTienKhachDua = new JTextField();

                txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyReleased(java.awt.event.KeyEvent evt) {
                        tinhTienThua();
                    }
                });
                p.add(txtTienKhachDua, g);
            } else if (i == 5) {
                lblTienThua = new JLabel("0 VNĐ");
                lblTienThua.setFont(new Font("Arial", Font.BOLD, 14));
                lblTienThua.setForeground(new Color(0, 153, 51));
                p.add(lblTienThua, g);
            } else if (i == 6) {

                String[] hinhThuc = {"Tiền mặt", "Chuyển khoản"};
                cboHinhThucThanhToan = new JComboBox<>(hinhThuc);

                cboHinhThucThanhToan.addActionListener(e -> {
                    System.out.println("Hình thức chọn: " + cboHinhThucThanhToan.getSelectedItem());
                });

                p.add(cboHinhThucThanhToan, g);
            }
        }

        g.gridy = 8;
        g.gridx = 0;
        g.gridwidth = 2;
        //p.add(createYellowBtn("Làm mới"), g);

        g.gridy = 8;
        g.ipady = 15;
        JButton btnPay = new JButton("THANH TOÁN");
        btnPay.setBackground(COLOR_YELLOW_BTN);
        btnPay.setFont(new Font("Arial", Font.BOLD, 22));
        btnPay.addActionListener(e -> {
            thanhToan();
        });
        p.add(btnPay, g);
        return p;
    }

    private void timKiemKhachHangTheoSdt() {
        String sdt = txtSdt.getText().trim();

        if (sdt.isEmpty()) {
            lblHoTenKH.setText("Khách lẻ");
            lblHoTenKH.setFont(new Font("Arial", Font.ITALIC, 13));
            return;
        }

        KhachHangViewModel kh = khService.getBySdt(sdt);

        if (kh != null) {

            lblHoTenKH.setText(kh.getHoTen());
            lblHoTenKH.setFont(new Font("Arial", Font.BOLD, 13));
        } else {

            lblHoTenKH.setText("Khách lẻ (SĐT chưa đăng ký)");
            lblHoTenKH.setFont(new Font("Arial", Font.ITALIC, 13));

            int option = JOptionPane.showConfirmDialog(null,
                    "Số điện thoại chưa tồn tại. Bạn có muốn thêm mới khách hàng này không?",
                    "Thông báo", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {

                new KhachHangView().setVisible(true);
                BanHangView.this.dispose();
            }
        }
    }

    public void loadTableGioHang(int idHoaDon) {

        List<HoaDonChiTiet> list = hdctService.selectByID(idHoaDon);

        dtmCart = (DefaultTableModel) tblCart.getModel();
        dtmCart.setRowCount(0);

        if (list != null) {
            int stt = 1;
            for (HoaDonChiTiet hdct : list) {

                String tenSP = hdct.getSize().getSanPham().getTenSanPham() + " (" + hdct.getSize().getTenSize() + ")";

                dtmCart.addRow(new Object[]{
                    stt++,
                    tenSP,
                    String.format("%,.0f", hdct.getGiaLucBan()),
                    hdct.getSoLuong(),
                    String.format("%,.0f", hdct.getGiaLucBan().multiply(new java.math.BigDecimal(hdct.getSoLuong()))),
                    hdct.getId()
                });
            }
        }

        tblCart.getColumnModel().getColumn(5).setMinWidth(0);
        tblCart.getColumnModel().getColumn(5).setMaxWidth(0);
        tblCart.getColumnModel().getColumn(5).setPreferredWidth(0);
    }

    private void addSanPhamToGioHang(Size sizeSelected) {

        String maHD = lblMaHoaDon.getText();
        if (maHD == null || maHD.equals("Vui lòng tạo!") || maHD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoặc tạo hóa đơn trước!");
            return;
        }

        String input = JOptionPane.showInputDialog(this,
                "Nhập số lượng cho: " + sizeSelected.getSanPham().getTenSanPham() + " (" + sizeSelected.getTenSize() + ")",
                "Số lượng", JOptionPane.QUESTION_MESSAGE);

        if (input != null && !input.isEmpty()) {
            try {
                int soLuong = Integer.parseInt(input);
                if (soLuong <= 0) {
                    JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
                    return;
                }

                HoaDon hdSelected = hoaDonService.selectByMaHD(maHD);

                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setHoaDon(hdSelected);
                hdct.setSize(sizeSelected);
                hdct.setSoLuong(soLuong);

                BigDecimal giaBan = sizeSelected.getSanPham().getGiaCoBan().add(sizeSelected.getGiaChenhLech());
                hdct.setGiaLucBan(giaBan);

                int result = hdctService.insert(hdct);

                if (result > 0) {
                    loadTableGioHang(hdSelected.getId());
                    tinhTongTienHoaDon(hdSelected.getId());
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Số lượng phải là số nguyên!");
            }
        }
    }

    private void tinhTongTienHoaDon(int idHoaDon) {

        List<HoaDonChiTiet> list = hdctService.selectByID(idHoaDon);

        BigDecimal tongTien = BigDecimal.ZERO;

        if (list != null) {
            for (HoaDonChiTiet hdct : list) {
                BigDecimal soLuongBD = new BigDecimal(hdct.getSoLuong());
                BigDecimal thanhTien = hdct.getGiaLucBan().multiply(soLuongBD);

                tongTien = tongTien.add(thanhTien);
            }
        }

        lblTongTien.setText(String.format("%,.0f VNĐ", tongTien.doubleValue()));
        tinhTienThua();
    }

    private void tinhTienThua() {
        try {

            String sTongTien = lblTongTien.getText().replaceAll("[^0-9]", "");
            if (sTongTien.isEmpty()) {
                return;
            }
            double tongTien = Double.parseDouble(sTongTien);

            String sKhachDua = txtTienKhachDua.getText().trim().replaceAll("[^0-9]", "");
            if (sKhachDua.isEmpty()) {
                lblTienThua.setText("0 VNĐ");
                return;
            }
            double khachDua = Double.parseDouble(sKhachDua);

            double tienThua = khachDua - tongTien;

            if (tienThua < 0) {
                lblTienThua.setText("0 VNĐ");
            } else {

                lblTienThua.setText(String.format("%,.0f VNĐ", tienThua).replace(",", "."));
            }
        } catch (Exception e) {
            lblTienThua.setText("0 VNĐ");
        }
    }

    private void lamMoiGiaoDien() {
        lblMaHoaDon.setText("Vui lòng tạo!");
        lblMaHoaDon.setForeground(Color.RED);
        lblTongTien.setText("0 VNĐ");
        txtTienKhachDua.setText("");
        lblTienThua.setText("0 VNĐ");
        txtSdt.setText("");
        lblHoTenKH.setText("Khách lẻ");
        cboHinhThucThanhToan.setSelectedIndex(0);
    }

    private void thanhToan() {
        String maHD = lblMaHoaDon.getText();
        if (maHD == null || maHD.equals("Vui lòng tạo!") || maHD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoặc tạo hóa đơn trước khi thanh toán!");
            return;
        }

        HoaDon hd = hoaDonService.selectByMaHD(maHD);
        if (hd == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thông tin hóa đơn trên hệ thống!");
            return;
        }

        try {
            String hinhThuc = cboHinhThucThanhToan.getSelectedItem().toString();

            String sKhachDua = txtTienKhachDua.getText().trim().replaceAll("[^0-9]", "");
            String sTongTien = lblTongTien.getText().replaceAll("[^0-9]", "");

            if (sKhachDua.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền khách đưa!");
                return;
            }

            BigDecimal tienKhachDua = new BigDecimal(sKhachDua);
            BigDecimal tongTien = sTongTien.isEmpty() ? BigDecimal.ZERO : new BigDecimal(sTongTien);

            if (tienKhachDua.compareTo(tongTien) < 0) {
                JOptionPane.showMessageDialog(this, "Tiền khách đưa không đủ để thanh toán!");
                return;
            }

            String sdt = txtSdt.getText().trim();

            if (sdt.isEmpty() || lblHoTenKH.getText().equalsIgnoreCase("Khách lẻ") || lblHoTenKH.getText().isBlank()) {

                hd.setKhachHang(null);
            } else {

                KhachHangViewModel khView = khService.getBySdt(sdt);

                if (khView != null) {

                    KhachHang khModel = new KhachHang();
                    khModel.setId(khView.getId());
                    khModel.setMaKhachHang(khView.getMaKhachHang());
                    khModel.setSoDienThoai(khView.getSoDienThoai());
                    khModel.setHoTen(khView.getHoTen());
                    khModel.setTrangThai(khView.isTrangThai());

                    hd.setKhachHang(khModel);
                } else {
                    hd.setKhachHang(null);
                }
            }

            hd.setTongTien(tongTien);
            hd.setTienThanhToan(tienKhachDua);
            hd.setPhuongThucTT(hinhThuc);
            hd.setTrangThai("Đã thanh toán");

            int check = hoaDonService.update(hd);

            if (check > 0) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công hóa đơn: " + maHD);

                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn PDF không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {

                    List<HoaDonChiTiet> listCT = hdctService.selectByID(hd.getId());

                    if (listCT != null && !listCT.isEmpty()) {
                        exportToPDF(hd, listCT);
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết hóa đơn để in!");
                    }
                }

                loadTableHoaDonCho(null);
                dtmCart.setRowCount(0);
                lamMoiGiaoDien();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật hóa đơn thất bại!");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số tiền không hợp lệ! Vui lòng chỉ nhập số.");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void exportToPDF(HoaDon hd, List<HoaDonChiTiet> listCT) {
        try {

            String userHome = System.getProperty("user.home");
            String defaultPath = userHome + File.separator + "Desktop" + File.separator + "HoaDon_Java";

            File folder = new File(defaultPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            File fileToSave = new File(folder, "HoaDon_" + hd.getMaHoaDon() + ".pdf");

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
            document.open();

            BaseFont bf;
            try {

                InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/arial.ttf");
                if (fontStream == null) {

                    bf = BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                } else {
                    byte[] fontBytes = fontStream.readAllBytes();
                    bf = BaseFont.createFont("arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, fontBytes, null);
                }
            } catch (Exception e) {

                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            }

            com.itextpdf.text.Font fontTitle = new com.itextpdf.text.Font(bf, 18, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontBold = new com.itextpdf.text.Font(bf, 12, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(bf, 12, com.itextpdf.text.Font.NORMAL);

            Paragraph title = new Paragraph("HÓA ĐƠN THANH TOÁN", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("Mã hóa đơn: " + hd.getMaHoaDon(), fontNormal));
            document.add(new Paragraph("Ngày tạo: " + hd.getNgayTao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
            String khachHang = (hd.getKhachHang() != null) ? hd.getKhachHang().getHoTen() : "Khách lẻ";
            document.add(new Paragraph("Khách hàng: " + khachHang, fontNormal));
            document.add(new Paragraph("--------------------------------------------------------------------------------", fontNormal));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setWidths(new float[]{1f, 4f, 2f, 2f, 2f});

            String[] headers = {"STT", "Tên SP", "Đơn giá", "SL", "Thành tiền"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, fontBold));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            int stt = 1;
            for (HoaDonChiTiet ct : listCT) {

                PdfPCell cellStt = new PdfPCell(new Phrase(String.valueOf(stt++), fontNormal));
                cellStt.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellStt);

                String tenSPFull = "N/A";
                if (ct.getSize() != null && ct.getSize().getSanPham() != null) {
                    tenSPFull = ct.getSize().getSanPham().getTenSanPham() + " (" + ct.getSize().getTenSize() + ")";
                }
                table.addCell(new Phrase(tenSPFull, fontNormal));

                table.addCell(new Phrase(String.format("%,.0f", ct.getGiaLucBan()), fontNormal));

                PdfPCell cellSL = new PdfPCell(new Phrase(String.valueOf(ct.getSoLuong()), fontNormal));
                cellSL.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellSL);

                BigDecimal thanhTien = ct.getGiaLucBan().multiply(new java.math.BigDecimal(ct.getSoLuong()));
                table.addCell(new Phrase(String.format("%,.0f", thanhTien), fontNormal));
            }

            document.add(table);

            document.add(new Paragraph(" "));
            Paragraph tongTienPara = new Paragraph("Tổng tiền: " + String.format("%,.0f", hd.getTongTien()) + " VNĐ", fontBold);
            tongTienPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(tongTienPara);

            document.add(new Paragraph("Hình thức thanh toán: " + hd.getPhuongThucTT(), fontNormal));

            document.close();
            JOptionPane.showMessageDialog(this, "Đã xuất hóa đơn PDF thành công!");
            Desktop.getDesktop().open(fileToSave);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi xuất PDF: " + e.getMessage());
        }
    }

    private JButton createYellowBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(COLOR_YELLOW_BTN);
        b.setFont(new Font("Arial", Font.BOLD, 11));
        return b;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new BanHangView().setVisible(true));
    }
}
