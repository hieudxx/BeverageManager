/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import Services.ThongKeService;
import Services.impl.ThongKeServiceImpl;
import ViewModels.ThongKeSanPhamViewModel;
import ViewModels.ThongKeTongQuanViewModel;
import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.time.ZoneId;
import java.util.List;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class ThongKeView extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbThoiGian;
    private JButton btnLoc;
//    private JButton btnXuatExcel;
//    private JButton btnInBaoCao;
    private JDateChooser dateTuNgay;
    private JDateChooser dateDenNgay;
    private JPanel panelKhoangNgay;

    private JLabel lblTongDoanhThu;
    private JLabel lblTongHoaDon;
    private JLabel lblMonBanChayNhat;

    private final ThongKeService thongKeService = new ThongKeServiceImpl();

    private final Color COLOR_SIDEBAR = new Color(23, 32, 42);
    private final Color COLOR_ORANGE = new Color(243, 156, 18);
    private final Color COLOR_BG_MAIN = new Color(240, 190, 90);

    private static final DateTimeFormatter DATE_INPUT_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("#,##0");

    public ThongKeView() {
        initUI();
        loadThongKe();
    }

    private void initUI() {
        setTitle("Thống kê doanh thu");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setMinimumSize(new Dimension(1200, 800));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(createSidebar(), BorderLayout.WEST);

        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBackground(COLOR_BG_MAIN);
        main.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JPanel panelTop = new JPanel();
        panelTop.setLayout(new BoxLayout(panelTop, BoxLayout.Y_AXIS));
        panelTop.setOpaque(false);

        JPanel panelLoaiThoiGian = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 15, 5)
        );
        panelLoaiThoiGian.setOpaque(false);

        JLabel lblThoiGian = new JLabel("Thời gian thống kê:");
        lblThoiGian.setFont(new Font("Arial", Font.BOLD, 14));

        cbThoiGian = new JComboBox<>(new String[]{
            "Hôm nay",
            "Tuần này",
            "Tháng này",
            "Tùy chọn"
        });

        cbThoiGian.setPreferredSize(
                new Dimension(130, 30)
        );

        btnLoc = createYellowBtn("Lọc báo cáo");
        btnLoc.setPreferredSize(
                new Dimension(110, 30)
        );

        panelLoaiThoiGian.add(lblThoiGian);
        panelLoaiThoiGian.add(cbThoiGian);
        panelLoaiThoiGian.add(btnLoc);

        panelKhoangNgay = createKhoangNgayPanel();

// Ban đầu KHÔNG hiển thị
        panelKhoangNgay.setVisible(false);

        panelTop.add(panelLoaiThoiGian);
        panelTop.add(panelKhoangNgay);

        cbThoiGian.addActionListener(e -> {

            String selected
                    = (String) cbThoiGian.getSelectedItem();

            boolean hienKhoangNgay
                    = "Tùy chọn".equals(selected);

            panelKhoangNgay.setVisible(hienKhoangNgay);

            panelTop.revalidate();
            panelTop.repaint();
        });
        btnLoc.addActionListener(e -> loadThongKe());
        main.add(panelTop, BorderLayout.NORTH);

        JPanel panelCenter = new JPanel(new BorderLayout(15, 15));
        panelCenter.setOpaque(false);

        JPanel panelCards = new JPanel(new GridLayout(1, 3, 15, 0));
        panelCards.setOpaque(false);

        JPanel cardDoanhThu = createCard("TỔNG DOANH THU", "0 VNĐ", new Color(46, 204, 113));
        JPanel cardHoaDon = createCard("TỔNG HÓA ĐƠN", "0 đơn", new Color(52, 152, 219));
        JPanel cardBanChay = createCard("MÓN BÁN CHẠY NHẤT", "Chưa có dữ liệu", new Color(155, 89, 182));

        lblTongDoanhThu = (JLabel) cardDoanhThu.getClientProperty("valueLabel");
        lblTongHoaDon = (JLabel) cardHoaDon.getClientProperty("valueLabel");
        lblMonBanChayNhat = (JLabel) cardBanChay.getClientProperty("valueLabel");

        panelCards.add(cardDoanhThu);
        panelCards.add(cardHoaDon);
        panelCards.add(cardBanChay);

        panelCenter.add(panelCards, BorderLayout.NORTH);

        String[] columnNames = {
            "STT",
            "Mã sản phẩm",
            "Tên sản phẩm",
            "Số lượng bán",
            "Đơn giá",
            "Thành tiền"
        };

        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        table.setRowHeight(25);
        table.setAutoCreateRowSorter(true);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel pnlTableWrapper = new JPanel(new BorderLayout());
        pnlTableWrapper.setOpaque(false);
        pnlTableWrapper.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                " Chi tiết doanh thu theo sản phẩm ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Arial", Font.BOLD, 12),
                Color.BLACK
        ));
        pnlTableWrapper.add(scrollPane, BorderLayout.CENTER);

        panelCenter.add(pnlTableWrapper, BorderLayout.CENTER);
        main.add(panelCenter, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        panelBottom.setOpaque(false);

//        btnXuatExcel = createYellowBtn("Xuất Excel");
//        btnXuatExcel.setPreferredSize(new Dimension(110, 35));
//
//        btnInBaoCao = createYellowBtn("In Báo Cáo");
//        btnInBaoCao.setPreferredSize(new Dimension(110, 35));
//
//        panelBottom.add(btnXuatExcel);
//        panelBottom.add(btnInBaoCao);
        main.add(panelBottom, BorderLayout.SOUTH);
        add(main, BorderLayout.CENTER);
    }

    private JPanel createKhoangNgayPanel() {

        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 15, 5)
        );

        panel.setOpaque(false);

        JLabel lblKhoangNgay
                = new JLabel("Khoảng ngày:");

        lblKhoangNgay.setFont(
                new Font("Arial", Font.BOLD, 14)
        );

        JLabel lblTuNgay
                = new JLabel("Từ ngày:");

        dateTuNgay = new JDateChooser();

        dateTuNgay.setDateFormatString("dd/MM/yyyy");

        dateTuNgay.setPreferredSize(
                new Dimension(130, 30)
        );

        JLabel lblDenNgay
                = new JLabel("Đến ngày:");

        dateDenNgay = new JDateChooser();

        dateDenNgay.setDateFormatString("dd/MM/yyyy");

        dateDenNgay.setPreferredSize(
                new Dimension(130, 30)
        );

        panel.add(lblKhoangNgay);

        panel.add(lblTuNgay);
        panel.add(dateTuNgay);

        panel.add(lblDenNgay);
        panel.add(dateDenNgay);

        return panel;
    }

    private void loadThongKe() {
        DateRange range = getDateRange();
        if (range == null) {
            return;
        }

        btnLoc.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        try {
            ThongKeTongQuanViewModel tongQuan = thongKeService.getTongQuan(
                    range.tuNgay,
                    range.denNgay
            );

            ThongKeTongQuanViewModel monBanChay = thongKeService.getMonBanChayNhat(
                    range.tuNgay,
                    range.denNgay
            );

            List<ThongKeSanPhamViewModel> list = thongKeService.getChiTietTheoSanPham(
                    range.tuNgay,
                    range.denNgay
            );

            lblTongDoanhThu.setText(formatMoney(tongQuan.getTongDoanhThu()) + " VNĐ");
            lblTongHoaDon.setText(String.format("%,d đơn", tongQuan.getTongHoaDon()));

            if (monBanChay.getSoLuongMonBanChayNhat() > 0) {
                lblMonBanChayNhat.setText(
                        monBanChay.getMonBanChayNhat()
                        + " ("
                        + String.format("%,d", monBanChay.getSoLuongMonBanChayNhat())
                        + " ly)"
                );
            } else {
                lblMonBanChayNhat.setText("Chưa có dữ liệu");
            }

            model.setRowCount(0);

            for (ThongKeSanPhamViewModel item : list) {
                model.addRow(new Object[]{
                    item.getStt(),
                    item.getMaSanPham(),
                    item.getTenSanPham(),
                    item.getSoLuongBan(),
                    formatMoney(item.getDonGia()) + " VNĐ",
                    formatMoney(item.getThanhTien()) + " VNĐ"
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(
                    this,
                    "Không thể tải dữ liệu thống kê.\nVui lòng kiểm tra kết nối cơ sở dữ liệu.",
                    "Lỗi thống kê",
                    JOptionPane.ERROR_MESSAGE
            );
        } finally {
            btnLoc.setEnabled(true);
            setCursor(Cursor.getDefaultCursor());
        }
    }

    private DateRange getDateRange() {
        String selected = (String) cbThoiGian.getSelectedItem();
        LocalDate today = LocalDate.now();

        switch (selected == null ? "Hôm nay" : selected) {
            case "Tuần này": {
                LocalDate start = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                return new DateRange(start.atStartOfDay(), start.plusDays(7).atStartOfDay());
            }
            case "Tháng này": {
                LocalDate start = today.withDayOfMonth(1);
                return new DateRange(start.atStartOfDay(), start.plusMonths(1).atStartOfDay());
            }
            case "Tùy chọn":
                return getCustomDateRange();
            case "Hôm nay":
            default:
                return new DateRange(today.atStartOfDay(), today.plusDays(1).atStartOfDay());
        }
    }

private DateRange getCustomDateRange() {

    Date tuDate = dateTuNgay.getDate();
    Date denDate = dateDenNgay.getDate();

    if (tuDate == null || denDate == null) {
        JOptionPane.showMessageDialog(
                this,
                "Vui lòng chọn đầy đủ ngày bắt đầu và ngày kết thúc!",
                "Dữ liệu không hợp lệ",
                JOptionPane.WARNING_MESSAGE
        );
        return null;
    }

    LocalDate tuNgay = tuDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

    LocalDate denNgay = denDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();

    if (denNgay.isBefore(tuNgay)) {
        JOptionPane.showMessageDialog(
                this,
                "Ngày kết thúc không được nhỏ hơn ngày bắt đầu!",
                "Dữ liệu không hợp lệ",
                JOptionPane.WARNING_MESSAGE
        );
        return null;
    }

    return new DateRange(
            tuNgay.atStartOfDay(),
            denNgay.plusDays(1).atStartOfDay()
    );
}

    private String formatMoney(BigDecimal value) {
        if (value == null) {
            return "0";
        }
        return MONEY_FORMAT.format(value);
    }

    private JPanel createSidebar() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(COLOR_SIDEBAR);
        p.setPreferredSize(new Dimension(200, 0));

        GridBagConstraints g = new GridBagConstraints();
        g.gridx = 0;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1;

        JPanel logo = new JPanel(new BorderLayout());
        logo.setBackground(new Color(255, 204, 0));
        logo.setPreferredSize(new Dimension(200, 150));
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/logoNootea.png"));
            Image scaledImg = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            logo.add(new JLabel(new ImageIcon(scaledImg), JLabel.CENTER), BorderLayout.CENTER);
        } catch (Exception e) {
            JLabel lblNoLogo = new JLabel("NOO TEA", JLabel.CENTER);
            lblNoLogo.setFont(new Font("Arial", Font.BOLD, 18));
            logo.add(lblNoLogo, BorderLayout.CENTER);
        }
        g.gridy = 0;
        p.add(logo, g);

        String[] menu = {"Bán hàng", "Danh Mục", "Sản phẩm", "Size", "Nhân viên", "Khách hàng", "Thống kê"};
        int y = 1;

        for (String m : menu) {
            JButton btn = new JButton(m);
            btn.setPreferredSize(new Dimension(200, 55));
            btn.setContentAreaFilled(false);
            btn.setOpaque(true);
            btn.setFocusPainted(false);
            btn.setBackground(m.equals("Thống kê") ? COLOR_ORANGE : COLOR_SIDEBAR);
            btn.setForeground(Color.WHITE);
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setBorder(new MatteBorder(0, 0, 1, 0, Color.DARK_GRAY));

            btn.addActionListener(e -> {
                switch (m) {
                    case "Bán hàng":
                        new BanHangView().setVisible(true);
                        this.dispose();
                        break;
                    case "Danh Mục":
                        new DanhMucView().setVisible(true);
                        this.dispose();
                        break;
                    case "Sản phẩm":
                        new SanPhamView().setVisible(true);
                        this.dispose();
                        break;
                    case "Size":
                        new SizeView().setVisible(true);
                        this.dispose();
                        break;
                    case "Nhân viên":
                        new NhanVienView().setVisible(true);
                        this.dispose();
                        break;
                    case "Khách hàng":
                        new KhachHangView().setVisible(true);
                        this.dispose();
                        break;
                    case "Thống kê":
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
        btnExit.setPreferredSize(new Dimension(200, 50));
        btnExit.setBackground(COLOR_SIDEBAR);
        btnExit.setForeground(Color.WHITE);
        btnExit.setFont(new Font("Arial", Font.BOLD, 14));
        btnExit.setBorder(new MatteBorder(1, 0, 0, 0, Color.DARK_GRAY));
        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Bạn có muốn thoát chương trình?",
                    "Xác nhận",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        g.gridy = y;
        g.weighty = 0;
        p.add(btnExit, g);

        return p;
    }

    private JPanel createCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 140, 50)),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 12));
        lblTitle.setForeground(Color.GRAY);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.BOLD, 15));
        lblValue.setForeground(color);
        lblValue.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.putClientProperty("valueLabel", lblValue);

        card.add(lblTitle);
        card.add(Box.createVerticalStrut(8));
        card.add(lblValue);

        return card;
    }

    private JButton createYellowBtn(String text) {
        JButton b = new JButton(text);
        b.setBackground(new Color(255, 215, 0));
        b.setFont(new Font("Arial", Font.BOLD, 12));
        b.setFocusPainted(false);
        return b;
    }

    private static class DateRange {

        private final LocalDateTime tuNgay;
        private final LocalDateTime denNgay;

        private DateRange(LocalDateTime tuNgay, LocalDateTime denNgay) {
            this.tuNgay = tuNgay;
            this.denNgay = denNgay;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new ThongKeView().setVisible(true));
    }
}
