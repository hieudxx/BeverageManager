package View;

import DomainModels.HoaDon;
import DomainModels.HoaDonChiTiet;
import DomainModels.NhanVien;
import DomainModels.Size;
import DomainModels.DanhMuc;
import DomainModels.SanPham;
import Services.DanhMucServices;
import Services.HoaDonChiTietService;
import Services.HoaDonService;
import Services.SanPhamServices;
import Services.SizeServices;
import Services.impl.DanhMucServicesImpl;
import Services.impl.HoaDonChiTietServicesImpl;
import Services.impl.HoaDonServiceImpl;
import Services.impl.SanPhamServicesImpl;
import Services.impl.SizeServicesImpl;
import ViewModels.SanPhamResponse;
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

public class BanHangView extends JFrame {

    private SanPhamServices spService = new SanPhamServicesImpl();
    private HoaDonService hoaDonService = new HoaDonServiceImpl();
    private HoaDonChiTietService hdctService = new HoaDonChiTietServicesImpl();
    private SizeServices sizeService = new SizeServicesImpl();
    private DefaultTableModel dtmCart; 
    private JTable tblCart; 
    
    private DanhMucServices dmService = new DanhMucServicesImpl();
    private JTextField txtSearchSP;
    private JComboBox<Object> cboDanhMuc; 
    private List<SanPhamResponse> allProducts;

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

        // Sản phẩm & Hóa đơn chờ
        JPanel leftCol = new JPanel(new BorderLayout(0, 10));
        leftCol.setOpaque(false);

        // KHU VỰC SẢN PHẨM (THANH CUỘN)
        JPanel pnlProduct = new JPanel(new BorderLayout(0, 10));
        pnlProduct.setBackground(Color.WHITE);
        pnlProduct.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Thanh tìm kiếm
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


// Sự kiện gõ phím trong ô tìm kiếm
        txtSearchSP.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                loadDataToGrid();
            }
        });

//  Sự kiện chọn trong ComboBox
        cboDanhMuc.addActionListener(e -> loadDataToGrid());

        // Container chứa các Card sản phẩm
        // 1. Khởi tạo gridProduct
        gridProduct = new JPanel(new GridLayout(0, 4, 15, 15));
        gridProduct.setBackground(Color.WHITE);

//  GỌI HÀM ĐỔ DỮ LIỆU VÀO
        loadDataToGrid();

//  Thiết lập ScrollPane để chứa grid đã có dữ liệu
        JPanel pnlWrapper = new JPanel(new BorderLayout());
        pnlWrapper.setBackground(Color.WHITE);
        pnlWrapper.add(gridProduct, BorderLayout.NORTH);

        JScrollPane spProduct = new JScrollPane(pnlWrapper);
        spProduct.setBorder(null);
        spProduct.getVerticalScrollBar().setUnitIncrement(25);
        pnlProduct.add(spProduct, BorderLayout.CENTER);

        //  KHU VỰC HÓA ĐƠN CHỜ
        JPanel pnlPending = new JPanel(new BorderLayout());
        pnlPending.setBackground(Color.WHITE);
        pnlPending.setPreferredSize(new Dimension(0, 250));
        pnlPending.setBorder(BorderFactory.createTitledBorder("Hóa đơn chờ"));

        String[] colsHD = {"Mã HĐ", "Người tạo", "Khách hàng", "Thời gian tạo", "Trạng thái"};
        dtmPending = new DefaultTableModel(colsHD, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên table
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
                    // Lấy mã hóa đơn từ cột 0
                    String maHD = tblPending.getValueAt(row, 0).toString();
 
                    HoaDon hdSelected = hoaDonService.selectByMaHD(maHD);

                    if (hdSelected != null) {
                        
                        loadTableGioHang(hdSelected.getId());
                        // CẬP NHẬT TỔNG TIỀN
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
                            // Trường hợp Khách lẻ (Object null hoặc tên bị rỗng)
                            txtSdt.setText("");
                            lblHoTenKH.setText("Khách lẻ");
                            lblHoTenKH.setForeground(Color.GRAY); 
                        }

                        // --- BỔ SUNG: LÀM MỚI Ô NHẬP TIỀN KHI ĐỔI HÓA ĐƠN ---
                        txtTienKhachDua.setText("");    // Xóa trống ô nhập tiền khách đưa
                        lblTienThua.setText("0 VNĐ");  // Đưa tiền thừa về 0
                    }
                }
            }
        });

        //Giỏ hàng & Thanh toán 
        JPanel rightCol = new JPanel(new BorderLayout(0, 10));
        rightCol.setOpaque(false);
        rightCol.setPreferredSize(new Dimension(450, 0));

        // KHU VỰC GIỎ HÀNG (FIX THANH CUỘN)
        JPanel pnlCart = new JPanel(new BorderLayout(0, 5));
        pnlCart.setBackground(Color.WHITE);
        pnlCart.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));

        String[] colsCart = {"STT", "Tên SP", "Đơn giá", "Số lượng", "Thành tiền", "ID"};
        dtmCart = new DefaultTableModel(colsCart, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho sửa trực tiếp trên bảng
            }
        };
        tblCart = new JTable(dtmCart);
        tblCart.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        tblCart.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); 
        tblCart.getColumnModel().getColumn(0).setPreferredWidth(30);  // STT
        tblCart.getColumnModel().getColumn(1).setPreferredWidth(190); // Tên SP
        tblCart.getColumnModel().getColumn(2).setPreferredWidth(65);  // Đơn giá
        tblCart.getColumnModel().getColumn(3).setPreferredWidth(60);  // Số lượng
        tblCart.getColumnModel().getColumn(4).setPreferredWidth(70);  // Thành tiền
        // Ẩn cột ID (cột số 5)
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

// Lấy ID HoaDonChiTiet từ cột ẩn
    int idHDCT = Integer.parseInt(tblCart.getValueAt(row, 5).toString());

            int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa sản phẩm này khỏi giỏ?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                
                hdctService.deleteOne(idHDCT);
                // Load lại bảng và tính lại tiền
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
        gbc.weightx = 0.0; // KHÔNG cho phép cột tự co giãn theo tỷ lệ 
        gbc.fill = GridBagConstraints.BOTH;
// Ép kích thước cố định cho cột phải
        rightCol.setPreferredSize(new Dimension(450, 0));
        mainPanel.add(rightCol, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }

    public int loadTableHoaDonCho(String maVuaTao) {
        dtmPending.setRowCount(0);
        List<HoaDon> list = hoaDonService.selectByHDChoTT();
        int targetRow = -1; // Mặc định không tìm thấy

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

            // Nếu mã hóa đơn trùng với mã vừa tạo, lưu lại index i
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
            List<DanhMuc> list = dmService.getAll();
            for (DanhMuc dm : list) {
                cboDanhMuc.addItem(dm.getTenDanhMuc());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//
    private void loadDataToGrid() {
        gridProduct.removeAll();

// Lấy tiêu chí lọc từ giao diện
        String keyword = txtSearchSP.getText().trim().toLowerCase();
        
        String loaiSelected = (cboDanhMuc.getSelectedItem() == null)
                ? "Tất cả" : cboDanhMuc.getSelectedItem().toString();

        for (SanPhamResponse sp : allProducts) {

            // Điều kiện 1: Đang bán và được hiển thị
            boolean isPublic = sp.isDangBan() && sp.isTrangThaiHienThi();

            // Điều kiện 2: Tên sản phẩm chứa từ khóa tìm kiếm
            boolean matchesName = sp.getTenSanPham().toLowerCase().contains(keyword);

            // Điều kiện 3: Loại sản phẩm khớp với ComboBox
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
                        // --- KIỂM TRA HÓA ĐƠN TRƯỚC KHI LÀM ---
                        String maHD = lblMaHoaDon.getText();
                        if (maHD == null || maHD.equals("Vui lòng tạo!") || maHD.isEmpty()) {
                            lblMaHoaDon.setForeground(Color.RED); 
                            JOptionPane.showMessageDialog(null,
                                    "Vui lòng chọn một hóa đơn chờ hoặc nhấn 'Tạo' mới trước khi thêm món!",
                                    "Thông báo",
                                    JOptionPane.WARNING_MESSAGE);
                            return; // Dừng toàn bộ xử lý phía dưới
                        }
                        // Lấy danh sách Size sản phẩm này từ DB
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

                        //Hiển thị Popup chọn Size
                        int choice = JOptionPane.showOptionDialog(null,
                                "Chọn Size cho " + sp.getTenSanPham(),
                                "Lựa chọn Size",
                                JOptionPane.DEFAULT_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null, options, options[0]);

                        //Nếu người dùng đã chọn một Size
                        if (choice != -1) {
                            Size sizeSelected = listSize.get(choice);

                            // QUAN TRỌNG: Gán ngược thông tin sản phẩm vào Size 
                            // để hàm addSanPhamToGioHang có dữ liệu tính giá gốc
                            SanPham spModel = new DomainModels.SanPham();
                            spModel.setId(sp.getId());
                            spModel.setTenSanPham(sp.getTenSanPham());
                            spModel.setGiaCoBan(sp.getGiaCoBan());

                            sizeSelected.setSanPham(spModel);

                            //Gọi hàm thêm vào giỏ hàng
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
        // Giảm khoảng cách dọc (vgap) giữa các vùng xuống 2 hoặc 0 nếu muốn khít hơn
        JPanel card = new JPanel(new BorderLayout(0, 2));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        
        card.setPreferredSize(new Dimension(180, 300));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Tên sản phẩm
        JLabel lblName = new JLabel("<html><center>" + name + "</center></html>", SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 13));
        // Cố định chiều cao vùng tên không chiếm chỗ của ảnh
        lblName.setPreferredSize(new Dimension(0, 48));
        card.add(lblName, BorderLayout.NORTH);

        //Khu vực ảnh
        // JLabel chứa ảnh
        JLabel lblImageContent = new JLabel("", SwingConstants.CENTER);
        lblImageContent.setOpaque(false);

        // JPanel bọc ngoài để căn giữa ảnh và nhận màu nền xám
        // Dùng GridBagLayout để JLabel ảnh không bị kéo giãn
        JPanel pnlImageWrapper = new JPanel(new GridBagLayout());
        // Đặt màu nền xám nhạt tại đây (vùng bao quanh ảnh)
        pnlImageWrapper.setBackground(new Color(245, 245, 245));
        pnlImageWrapper.add(lblImageContent);

        try {
            java.net.URL imgURL = getClass().getResource("/images/" + imagePath);
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                // Kích thước scale ảnh
                Image img = icon.getImage().getScaledInstance(160, 200, Image.SCALE_SMOOTH);
                lblImageContent.setIcon(new ImageIcon(img));
            } else {
                lblImageContent.setText("NOT FOUND");
                // padding cho chữ NOT FOUND để không dính sát mép xám
                lblImageContent.setBorder(new EmptyBorder(10, 10, 10, 10));
            }
        } catch (Exception e) {
            lblImageContent.setText("ERROR");
        }

        // JPanel bọc ảnh vào vùng CENTER của Card
        card.add(pnlImageWrapper, BorderLayout.CENTER);

        // 3. Giá tiền
        JLabel lblPrice = new JLabel(price, SwingConstants.CENTER);
        lblPrice.setForeground(new Color(211, 47, 47));
        lblPrice.setFont(new Font("Arial", Font.BOLD, 14));
        // Điều chỉnh padding để khoảng cách dưới đẹp hơn
        lblPrice.setBorder(new EmptyBorder(5, 0, 10, 0));
        card.add(lblPrice, BorderLayout.SOUTH);

        return card;
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

        String[] menu = {"Bán hàng", "Danh Mục", "Sản phẩm", "Size", "Nhân viên", "Khách hàng", "Thống kê"};
        int y = 1;
        for (String m : menu) {
            JButton btn = new JButton(m);
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setContentAreaFilled(false);
            btn.setOpaque(true);
            btn.setFocusPainted(false);
            btn.setBackground(m.equals("Bán hàng") ? COLOR_ORANGE_ACTIVE : COLOR_SIDEBAR);
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
                    case "Khách hàng":
                        new KhachHangView().setVisible(true);
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
// LẤY NHÂN VIÊN TỪ AUTH 
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
            // Nếu SĐT trống -> Mặc định khách lẻ
            hd.setKhachHang(null);
        } else {
            // Nếu có SĐT -> Gọi service tìm khách hàng theo SĐT
            // KhachHang kh = khachHangService.findBySdt(sdt);
            // hd.setKhachHang(kh);
        }

        int result = hoaDonService.insert(hd);

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Tạo hóa đơn thành công!");

            // Gọi hàm load và lấy về vị trí dòng vừa tạo
            int rowIndex = loadTableHoaDonCho(maMoi);

            // Nếu tìm thấy dòng thì thực hiện chọn dòng đó
            if (rowIndex != -1) {
                tblPending.setRowSelectionInterval(rowIndex, rowIndex);

                // Cuộn thanh cuộn tới dòng vừa chọn (nếu bảng quá dài)
                tblPending.scrollRectToVisible(tblPending.getCellRect(rowIndex, 0, true));
            }

            HoaDon hdMoi = hoaDonService.selectByMaHD(maMoi);

            if (hdMoi != null) {
                loadTableGioHang(hdMoi.getId()); // Gọi hàm load giỏ hàng

                // Cập nhật thông tin khách hàng lên các JTextField
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

        String[] fields = {"SĐT:", "Họ tên:", "Mã hóa đơn:", "Tổng tiền:", "Tiền khách đưa:", "Tiền thừa:", "Hình thức:", "Ghi chú:"};
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
                pnlSdt.add(new JButton("👤"), BorderLayout.EAST);
                p.add(pnlSdt, g);
            } else if (i == 1) {
                lblHoTenKH = new JLabel("Khách lẻ"); // Mặc định là khách lẻ
                lblHoTenKH.setFont(new Font("Arial", Font.ITALIC, 13));
                p.add(lblHoTenKH, g);
            } else if (i == 2) {
                JPanel pnlMa = new JPanel(new BorderLayout(5, 0));
                pnlMa.setOpaque(false);

                lblMaHoaDon = new JLabel("Vui lòng tạo!");
                lblMaHoaDon.setForeground(Color.RED);
                lblMaHoaDon.setPreferredSize(new Dimension(150, 25));

                JButton btnTaoHD = createYellowBtn("Tạo");

                // SỰ KIỆN CLICK NÚT TẠO
                btnTaoHD.addActionListener(e -> {
                    taoHoaDonMoi();
                });

                pnlMa.add(lblMaHoaDon, BorderLayout.CENTER);
                pnlMa.add(btnTaoHD, BorderLayout.EAST);
                p.add(pnlMa, g);
            } else if (i == 3) { // Tổng tiền
                
                lblTongTien = new JLabel("0 VNĐ");
                lblTongTien.setPreferredSize(new Dimension(150, 25));
                lblTongTien.setFont(new Font("Arial", Font.BOLD, 15));
                lblTongTien.setForeground(Color.RED);
                p.add(lblTongTien, g);
            } else if (i == 4) { // Tiền khách đưa
                txtTienKhachDua = new JTextField();

                //sự kiện lắng nghe khi gõ phím
                txtTienKhachDua.addKeyListener(new java.awt.event.KeyAdapter() {
                    @Override
                    public void keyReleased(java.awt.event.KeyEvent evt) {
                        tinhTienThua();
                    }
                });
                p.add(txtTienKhachDua, g);
            } else if (i == 5) { // Tiền thừa
                lblTienThua = new JLabel("0 VNĐ");
                lblTienThua.setFont(new Font("Arial", Font.BOLD, 14));
                lblTienThua.setForeground(new Color(0, 153, 51)); 
                p.add(lblTienThua, g);
            } else if (i == 6) { // Hình thức TT
                
                String[] hinhThuc = {"Tiền mặt", "Chuyển khoản"};
                cboHinhThucThanhToan = new JComboBox<>(hinhThuc);

                //lấy giá trị khi thay đổi hình thức
                cboHinhThucThanhToan.addActionListener(e -> {
                    System.out.println("Hình thức chọn: " + cboHinhThucThanhToan.getSelectedItem());
                });

                p.add(cboHinhThucThanhToan, g);
            } else if (i == 7) { // Ghi chú
                p.add(new JTextField(), g);
            }
        }

        g.gridy = 8;
        g.gridx = 0;
        g.gridwidth = 2;
        p.add(createYellowBtn("Làm mới"), g);

        g.gridy = 9;
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

    public void loadTableGioHang(int idHoaDon) {

        List<HoaDonChiTiet> list = hdctService.selectByID(idHoaDon);

        dtmCart = (DefaultTableModel) tblCart.getModel();
        dtmCart.setRowCount(0); // Xóa dữ liệu cũ

        if (list != null) {
            int stt = 1;
            for (HoaDonChiTiet hdct : list) {
                // Lấy tên SP từ đối tượng liên kết
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
        // Ẩn cột ID
        tblCart.getColumnModel().getColumn(5).setMinWidth(0);
        tblCart.getColumnModel().getColumn(5).setMaxWidth(0);
        tblCart.getColumnModel().getColumn(5).setPreferredWidth(0);
    }

    private void addSanPhamToGioHang(Size sizeSelected) { // Truyền vào đối tượng Size
        //  Kiểm tra hóa đơn đã được chọn chưa
        String maHD = lblMaHoaDon.getText();
        if (maHD == null || maHD.equals("Vui lòng tạo!") || maHD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hoặc tạo hóa đơn trước!");
            return;
        }

        //  Popup nhập số lượng
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

                //  Lấy đối tượng HoaDon hiện tại
                HoaDon hdSelected = hoaDonService.selectByMaHD(maHD);

                //
                HoaDonChiTiet hdct = new HoaDonChiTiet();
                hdct.setHoaDon(hdSelected);
                hdct.setSize(sizeSelected); // Sử dụng đối tượng Size thay vì idChiTietSP
                hdct.setSoLuong(soLuong);

                // Giá bán = Giá gốc sản phẩm + Giá chênh lệch của Size
                BigDecimal giaBan = sizeSelected.getSanPham().getGiaCoBan().add(sizeSelected.getGiaChenhLech());
                hdct.setGiaLucBan(giaBan);

                int result = hdctService.insert(hdct);

                if (result > 0) { // insert trả về int (số dòng thành công), nên so sánh > 0
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
                //Số lượng * Giá lúc bán
                BigDecimal soLuongBD = new BigDecimal(hdct.getSoLuong());
                BigDecimal thanhTien = hdct.getGiaLucBan().multiply(soLuongBD);

                // Cộng dồn tổng tiền
                tongTien = tongTien.add(thanhTien);
            }
        }

        // Hiển thị lên label tổng tiền 
        lblTongTien.setText(String.format("%,.0f VNĐ", tongTien.doubleValue()));
        tinhTienThua();
    }

    private void tinhTienThua() {
        try {
            // Lấy tổng tiền: Loại bỏ tất cả ký tự KHÔNG PHẢI LÀ SỐ
            // regex [^0-9] để chỉ giữ lại các con số
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
                // Định dạng lại có dấu chấm phân cách phần nghìn
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
            //Lấy dữ liệu từ giao diện
            String hinhThuc = cboHinhThucThanhToan.getSelectedItem().toString();

            // Làm sạch chuỗi số (Xử lý trường hợp có VNĐ hoặc dấu phân cách)
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

            // 3. XỬ LÝ LỖI FOREIGN KEY (Quan trọng)
            
            if (lblHoTenKH.getText().equalsIgnoreCase("Khách lẻ") || lblHoTenKH.getText().isBlank()) {
                hd.setKhachHang(null);
            }

            
            hd.setTongTien(tongTien);
            hd.setTienThanhToan(tienKhachDua);
            hd.setPhuongThucTT(hinhThuc);
            hd.setTrangThai("Đã thanh toán");

            
            int check = hoaDonService.update(hd);

            if (check > 0) {
                JOptionPane.showMessageDialog(this, "Thanh toán thành công hóa đơn: " + maHD);

// ---  XUẤT HÓA ĐƠN ---
                int confirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn in hóa đơn PDF không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    // Lấy danh sách chi tiết hóa đơn từ service 
                    List<HoaDonChiTiet> listCT = hdctService.selectByID(hd.getId());

                    if (listCT != null && !listCT.isEmpty()) {
                        exportToPDF(hd, listCT);
                    } else {
                        JOptionPane.showMessageDialog(this, "Không tìm thấy chi tiết hóa đơn để in!");
                    }
                }

                // 6. Làm mới giao diện
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
            // 1. Tạo hộp thoại chọn nơi lưu file
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Chọn nơi lưu hóa đơn");
            fileChooser.setSelectedFile(new File("HoaDon_" + hd.getMaHoaDon() + ".pdf"));

            if (fileChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File fileToSave = fileChooser.getSelectedFile();
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
            document.open();

            // 2. Cấu hình Font (Đọc từ Resources thay vì ổ C)
            BaseFont bf;
            try {
                // Lấy font từ src/main/resources/fonts/arial.ttf
                InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/arial.ttf");
                if (fontStream == null) {
                    // Nếu không tìm thấy trong resources, dự phòng dùng font hệ thống
                    bf = BaseFont.createFont("C:\\Windows\\Fonts\\Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                } else {
                    byte[] fontBytes = fontStream.readAllBytes();
                    bf = BaseFont.createFont("arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, true, fontBytes, null);
                }
            } catch (Exception e) {
                // Nếu hỏng cả 2 cách, buộc phải dùng font mặc định 
                bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            }

            com.itextpdf.text.Font fontTitle = new com.itextpdf.text.Font(bf, 18, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontBold = new com.itextpdf.text.Font(bf, 12, com.itextpdf.text.Font.BOLD);
            com.itextpdf.text.Font fontNormal = new com.itextpdf.text.Font(bf, 12, com.itextpdf.text.Font.NORMAL);
            // 3. Tiêu đề
            Paragraph title = new Paragraph("HÓA ĐƠN THANH TOÁN", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // 4. Thông tin chung
            document.add(new Paragraph("Mã hóa đơn: " + hd.getMaHoaDon(), fontNormal));
            document.add(new Paragraph("Ngày tạo: " + hd.getNgayTao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), fontNormal));
            String khachHang = (hd.getKhachHang() != null) ? hd.getKhachHang().getHoTen() : "Khách lẻ";
            document.add(new Paragraph("Khách hàng: " + khachHang, fontNormal));
            document.add(new Paragraph("--------------------------------------------------------------------------------", fontNormal));

            // 5. Bảng sản phẩm
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

// Đổ dữ liệu từ listCT
            int stt = 1;
            for (HoaDonChiTiet ct : listCT) {
                
                PdfPCell cellStt = new PdfPCell(new Phrase(String.valueOf(stt++), fontNormal));
                cellStt.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellStt);

                // Tên SP + Size 
                String tenSPFull = "N/A";
                if (ct.getSize() != null && ct.getSize().getSanPham() != null) {
                    tenSPFull = ct.getSize().getSanPham().getTenSanPham() + " (" + ct.getSize().getTenSize() + ")";
                }
                table.addCell(new Phrase(tenSPFull, fontNormal));

                // Đơn giá
                table.addCell(new Phrase(String.format("%,.0f", ct.getGiaLucBan()), fontNormal));

                // Số lượng
                PdfPCell cellSL = new PdfPCell(new Phrase(String.valueOf(ct.getSoLuong()), fontNormal));
                cellSL.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cellSL);

                // Thành tiền
                BigDecimal thanhTien = ct.getGiaLucBan().multiply(new java.math.BigDecimal(ct.getSoLuong()));
                table.addCell(new Phrase(String.format("%,.0f", thanhTien), fontNormal));
            }

            document.add(table);

            //Tổngtiền
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
            //đồng bộ giao diện
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new BanHangView().setVisible(true));
    }
}
