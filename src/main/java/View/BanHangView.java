/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import DomainModels.HoaDon;
import DomainModels.NhanVien;
import Services.HoaDonService;
import Services.SanPhamServices;
import Services.impl.HoaDonServiceImpl;
import Services.impl.SanPhamServicesImpl;
import ViewModels.SanPhamResponse;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BanHangView extends JFrame {
    
    private SanPhamServices spService = new SanPhamServicesImpl();
    private HoaDonService hoaDonService = new HoaDonServiceImpl();
    
    // Components cho bảng Hóa Đơn Chờ
    private JTable tblPending;
    private DefaultTableModel dtmPending;
    
    // Thêm vào phần khai báo thuộc tính Class ở đầu file
private JTextField txtSdt; // Ô nhập SĐT
private JLabel lblMaHoaDon; // Label hiển thị mã HD hoặc trạng thái "Vui lòng tạo"
    
    private JPanel gridProduct;
    private final Color COLOR_SIDEBAR = new Color(23, 32, 42); 
    private final Color COLOR_ORANGE_ACTIVE = new Color(243, 156, 18); 
    private final Color COLOR_YELLOW_BTN = new Color(255, 215, 0); 
    private final Color COLOR_BG_MAIN = new Color(213, 216, 220); 

    public BanHangView() {
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

        // --- CỘT TRÁI: Sản phẩm & Hóa đơn chờ ---
        JPanel leftCol = new JPanel(new BorderLayout(0, 10));
        leftCol.setOpaque(false);

        // 1. KHU VỰC SẢN PHẨM (FIX THANH CUỘN)
        JPanel pnlProduct = new JPanel(new BorderLayout(0, 10));
        pnlProduct.setBackground(Color.WHITE);
        pnlProduct.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Thanh tìm kiếm
        JPanel pnlFilter = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        pnlFilter.setOpaque(false);
        pnlFilter.add(new JLabel("Tìm kiếm sản phẩm"));
        pnlFilter.add(new JTextField(12));
        pnlFilter.add(new JLabel("Size"));
        pnlFilter.add(new JComboBox<>(new String[]{"ALL", "S", "M", "L"}));
        pnlFilter.add(new JLabel("Loại sản phẩm"));
        pnlFilter.add(new JComboBox<>(new String[]{"ALL", "Trà sữa", "Coffee"}));
        pnlProduct.add(pnlFilter, BorderLayout.NORTH);

        // Container chứa các Card sản phẩm
        gridProduct = new JPanel(new GridLayout(0, 4, 15, 15));
        gridProduct.setBackground(Color.WHITE);
        loadDataToGrid();
        
        // SỬA LỖI TẠI ĐÂY: Dùng JScrollPane và ép kích thước để hiện scroll
        JPanel pnlWrapper = new JPanel(new BorderLayout());
        pnlWrapper.setBackground(Color.WHITE);
        pnlWrapper.add(gridProduct, BorderLayout.NORTH); // NORTH sẽ ép các card về phía trên

        JScrollPane spProduct = new JScrollPane(pnlWrapper); // Cho wrapper vào scroll thay vì grid
        spProduct.setBorder(null);
        spProduct.getVerticalScrollBar().setUnitIncrement(25);
        pnlProduct.add(spProduct, BorderLayout.CENTER);

        // 2. KHU VỰC HÓA ĐƠN CHỜ
        JPanel pnlPending = new JPanel(new BorderLayout());
        pnlPending.setBackground(Color.WHITE);
        pnlPending.setPreferredSize(new Dimension(0, 250)); // Tăng nhẹ chiều cao
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

        gbc.gridx = 0; gbc.weightx = 0.65; gbc.weighty = 1.0;
        mainPanel.add(leftCol, gbc);

        // --- CỘT PHẢI: Giỏ hàng & Thanh toán ---
        JPanel rightCol = new JPanel(new BorderLayout(0, 10));
        rightCol.setOpaque(false);

        // 3. KHU VỰC GIỎ HÀNG (FIX THANH CUỘN)
        JPanel pnlCart = new JPanel(new BorderLayout(0, 5));
        pnlCart.setBackground(Color.WHITE);
        pnlCart.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));
        
        String[] colsCart = {"Mã SP", "Tên SP", "Đơn giá", "Số lượng", "Thành tiền"};
        JTable tblCart = new JTable(new DefaultTableModel(colsCart, 25)); // Thêm nhiều dòng mẫu
        
        // SỬA LỖI TẠI ĐÂY: Phải đặt trong JScrollPane và đặt kích thước ưu tiên
        JScrollPane spCart = new JScrollPane(tblCart);
        spCart.setPreferredSize(new Dimension(0, 350)); // Ép giỏ hàng có chiều cao tối đa trước khi cuộn
        spCart.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        pnlCart.add(spCart, BorderLayout.CENTER);
        
        JPanel pnlCartBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        pnlCartBtn.setOpaque(false);
        pnlCartBtn.add(createYellowBtn("Xóa sản phẩm"));
        pnlCartBtn.add(createYellowBtn("Xóa tất cả"));
        pnlCart.add(pnlCartBtn, BorderLayout.SOUTH);

        rightCol.add(pnlCart, BorderLayout.CENTER);
        rightCol.add(createPaymentPanel(), BorderLayout.SOUTH);

        gbc.gridx = 1; gbc.weightx = 0.35; gbc.weighty = 1.0;
        mainPanel.add(rightCol, gbc);

        add(mainPanel, BorderLayout.CENTER);
    }
// Chỉnh sửa kiểu trả về từ void sang int
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
private void loadDataToGrid() {
        gridProduct.removeAll();
        List<SanPhamResponse> list = spService.getAll();
        
        for (SanPhamResponse sp : list) {
            if (sp.isDangBan() && sp.isTrangThaiHienThi()) {
                String giaVND = String.format("%,.0f VNĐ", sp.getGiaCoBan());
                
                // Lấy tên file từ DB, nếu null/trống thì dùng ảnh mặc định
                // Ví dụ trong DB lưu: HongTraDaoNhietDoi.png
                String tenFile = (sp.getHinhAnh() == null || sp.getHinhAnh().isBlank()) 
                                 ? "default.png" : sp.getHinhAnh();

                JPanel card = createProductCard(sp.getTenSanPham(), giaVND, tenFile);

                card.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        System.out.println("Đã chọn: " + sp.getTenSanPham());
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
        
        // Giữ nguyên kích thước Card đã tăng chiều cao
        card.setPreferredSize(new Dimension(180, 300)); 
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 1. Tên sản phẩm (NORTH)
        JLabel lblName = new JLabel("<html><center>" + name + "</center></html>", SwingConstants.CENTER);
        lblName.setFont(new Font("Arial", Font.BOLD, 13));
        // Cố định chiều cao vùng tên để không chiếm chỗ của ảnh
        lblName.setPreferredSize(new Dimension(0, 48)); 
        card.add(lblName, BorderLayout.NORTH);

        // 2. Khu vực ảnh (SỬA ĐỂ GIẢM KHOẢNG XÁM)
        
        // JLabel thực sự chứa ảnh
        JLabel lblImageContent = new JLabel("", SwingConstants.CENTER);
        
        // Bỏ màu xám ở đây, chuyển sang JPanel bọc ngoài
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
                // Kích thước scale ảnh, bạn có thể tăng chiều cao lên 200 hoặc hơn
                // để ảnh chiếm nhiều chỗ hơn, giảm vùng xám bao quanh
                Image img = icon.getImage().getScaledInstance(160, 200, Image.SCALE_SMOOTH);
                lblImageContent.setIcon(new ImageIcon(img));
            } else {
                lblImageContent.setText("NOT FOUND");
                // Thêm padding cho chữ NOT FOUND để không dính sát mép xám
                lblImageContent.setBorder(new EmptyBorder(10, 10, 10, 10));
            }
        } catch (Exception e) {
            lblImageContent.setText("ERROR");
        }
        
        // Thêm JPanel bọc ảnh vào vùng CENTER của Card
        card.add(pnlImageWrapper, BorderLayout.CENTER);

        // 3. Giá tiền (SOUTH)
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
        g.gridx = 0; g.fill = GridBagConstraints.HORIZONTAL; g.weightx = 1.0;

        JPanel logo = new JPanel();
        logo.setBackground(new Color(255, 204, 0));
        logo.setPreferredSize(new Dimension(200, 150));
        g.gridy = 0; p.add(logo, g);

        String[] menu = {"Bán hàng", "Sản phẩm", "Nhân viên", "Khách hàng", "Thống kê"};
        int y = 1;
        for (String m : menu) {
            JButton btn = new JButton(m);
            btn.setPreferredSize(new Dimension(200, 60));
            btn.setBackground(m.equals("Bán hàng") ? COLOR_ORANGE_ACTIVE : COLOR_SIDEBAR);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setBorder(new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY));
            g.gridy = y++; p.add(btn, g);
        }

        g.gridy = y++; g.weighty = 1.0; p.add(new JLabel(""), g);

        JButton btnExit = new JButton("Thoát");
        btnExit.setPreferredSize(new Dimension(200, 50));
        btnExit.setBackground(COLOR_SIDEBAR);
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));
        btnExit.setBorder(new MatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
        g.gridy = y; g.weighty = 0; p.add(btnExit, g);

        return p;
    }
    
    private void taoHoaDonMoi() {
    // 1. Khởi tạo đối tượng hóa đơn
    HoaDon hd = new HoaDon();
        NhanVien nvGia = new NhanVien();
    // Thay chuỗi dưới đây bằng ID thực tế bạn lấy trong DB của bạn
    nvGia.setId("1"); 
    hd.setNhanVien(nvGia);
    String maMoi = "HD" + System.currentTimeMillis();
    hd.setMaHoaDon(maMoi);
    hd.setNgayTao(java.time.LocalDateTime.now());
    hd.setTongTien(BigDecimal.ZERO);
    hd.setTienThanhToan(BigDecimal.ZERO);
    hd.setTrangThai("Chờ thanh toán");
    
    // 2. Set nhân viên (Giả sử bạn có thông tin nhân viên đang đăng nhập)
    // NhanVien nv = ...; hd.setNhanVien(nv);

    // 3. Xử lý khách hàng
    String sdt = txtSdt.getText().trim();
    if (sdt.isEmpty()) {
        // Nếu SĐT trống -> Mặc định khách lẻ (Để null hoặc object Khách lẻ tùy DB thiết kế)
        hd.setKhachHang(null); 
    } else {
        // Nếu có SĐT -> Gọi service tìm khách hàng theo SĐT
        // KhachHang kh = khachHangService.findBySdt(sdt);
        // hd.setKhachHang(kh);
    }

    // 4. Gọi Service để Insert
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

        lblMaHoaDon.setText(hd.getMaHoaDon());
        lblMaHoaDon.setForeground(Color.BLUE);
    } else {
        JOptionPane.showMessageDialog(this, "Tạo hóa đơn thất bại!");
    }
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
            g.gridx = 0; g.gridy = i; g.weightx = 0.3;
            p.add(new JLabel(fields[i]), g);
            g.gridx = 1; g.weightx = 0.7;
            if (i == 0) {
                JPanel pnlSdt = new JPanel(new BorderLayout(5, 0));
                pnlSdt.setOpaque(false);
                txtSdt = new JTextField();
                pnlSdt.add(txtSdt, BorderLayout.CENTER);
                pnlSdt.add(new JButton("👤"), BorderLayout.EAST);
                p.add(pnlSdt, g);
            } else if (i == 2) {
    JPanel pnlMa = new JPanel(new BorderLayout(5, 0));
    pnlMa.setOpaque(false);
    
    lblMaHoaDon = new JLabel("Vui lòng tạo!"); 
    lblMaHoaDon.setForeground(Color.RED);
    
    JButton btnTaoHD = createYellowBtn("Tạo");
    
    // SỰ KIỆN CLICK NÚT TẠO
    btnTaoHD.addActionListener(e -> {
        taoHoaDonMoi();
    });

    pnlMa.add(lblMaHoaDon, BorderLayout.CENTER);
    pnlMa.add(btnTaoHD, BorderLayout.EAST);
    p.add(pnlMa, g);
}
        }

        g.gridy = 8; g.gridx = 0; g.gridwidth = 2;
        p.add(createYellowBtn("Làm mới"), g);

        g.gridy = 9; g.ipady = 15;
        JButton btnPay = new JButton("THANH TOÁN");
        btnPay.setBackground(COLOR_YELLOW_BTN);
        btnPay.setFont(new Font("Arial", Font.BOLD, 22));
        p.add(btnPay, g);

        return p;
    }

    private JButton createYellowBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(COLOR_YELLOW_BTN);
        b.setFont(new Font("Arial", Font.BOLD, 11));
        return b;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BanHangView().setVisible(true));
    }
}
