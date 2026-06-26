package View;

import DomainModels.NhanVien;
import Services.impl.NhanVienServiceImpl;
import Utilities.Auth;
import Utilities.SessionUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Services.NhanVienService;

public class DangNhap extends JFrame {

    private JTextField txtTaiKhoan;
    private JPasswordField txtMatKhau;
    private JButton btnDangNhap;
    private JLabel lblMessage;

    private NhanVienService nvService = new NhanVienServiceImpl();

    public DangNhap() {
        initComponents();
        setTitle("Đăng nhập - NOO TEA");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void initComponents() {

        JPanel main = new JPanel(new GridLayout(1, 2));
        add(main);

        // ===== LEFT =====
        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(new Color(92, 35, 65));

        JLabel lblLogo = new JLabel();

        try {
            ImageIcon icon = new ImageIcon(
                    getClass().getResource("/images/logoNootea.png")
            );
            Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            lblLogo.setText("Logo");
            lblLogo.setForeground(Color.WHITE);
        }

        left.add(lblLogo);

        // ===== RIGHT =====
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(new Color(191, 77, 77));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblTaiKhoan = new JLabel("Tài khoản");
        lblTaiKhoan.setForeground(Color.WHITE);
        lblTaiKhoan.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtTaiKhoan = new JTextField(20);
        styleInput(txtTaiKhoan);

        gbc.gridy = 0;
        right.add(lblTaiKhoan, gbc);
        gbc.gridy = 1;
        right.add(txtTaiKhoan, gbc);

        JLabel lblMatKhau = new JLabel("Mật khẩu");
        lblMatKhau.setForeground(Color.WHITE);
        lblMatKhau.setFont(new Font("Segoe UI", Font.BOLD, 14));

        txtMatKhau = new JPasswordField(20);
        styleInput(txtMatKhau);

        gbc.gridy = 2;
        right.add(lblMatKhau, gbc);
        gbc.gridy = 3;
        right.add(txtMatKhau, gbc);

        btnDangNhap = new JButton("Đăng nhập");
        btnDangNhap.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDangNhap.setBackground(new Color(170, 60, 60));
        btnDangNhap.setForeground(Color.WHITE);
        btnDangNhap.setFocusPainted(false);
        btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangNhap.setPreferredSize(new Dimension(200, 45));

        btnDangNhap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDangNhap.setBackground(new Color(200, 80, 80));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDangNhap.setBackground(new Color(170, 60, 60));
            }
        });

        gbc.gridy = 4;
        gbc.insets = new Insets(30, 0, 0, 0);
        right.add(btnDangNhap, gbc);

        lblMessage = new JLabel(" ");
        lblMessage.setForeground(Color.YELLOW);
        lblMessage.setFont(new Font("Segoe UI", Font.ITALIC, 12));

        gbc.gridy = 5;
        gbc.insets = new Insets(10, 0, 0, 0);
        right.add(lblMessage, gbc);

        main.add(left);
        main.add(right);

        // EVENT
        btnDangNhap.addActionListener(e -> dangNhap());

        txtTaiKhoan.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dangNhap();
                }
            }
        });

        txtMatKhau.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dangNhap();
                }
            }
        });
    }

    private void styleInput(JTextField txt) {
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txt.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
    }

  
    private void dangNhap() {
        try {
            String taiKhoan = txtTaiKhoan.getText().trim();
            String pass = new String(txtMatKhau.getPassword());

            // Validate
            if (taiKhoan.isEmpty()) {
                lblMessage.setText("Vui lòng nhập tài khoản!");
                txtTaiKhoan.requestFocus();
                return;
            }

            if (pass.isEmpty()) {
                lblMessage.setText("Vui lòng nhập mật khẩu!");
                txtMatKhau.requestFocus();
                return;
            }

            // Check DB
            NhanVien nv = nvService.getOne(taiKhoan);

            if (nv == null) {
                lblMessage.setText("Sai tài khoản!");
                return;
            }

            if (!pass.equals(nv.getMatKhau())) {
                lblMessage.setText("Sai mật khẩu!");
                return;
            }

            // SUCCESS
            lblMessage.setText(" ");
            Auth.user = nv;
            SessionUser.getInstance().setCurrentUser(nv);

            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            System.out.println("Đăng nhập thành công!");
            System.out.println("id: " + nv.getId());
            System.out.println("Mã NV: " + nv.getMaNhanVien());
            System.out.println("Tên đăng nhập: " + nv.getTenDangNhap());
            System.out.println("Họ tên: " + nv.getHoTen());
            System.out.println("Vai trò: " + nv.getVaiTro());
            System.out.println("Trạng thái: " + nv.isTrangThai());

            this.dispose();
            new BanHangView().setVisible(true);

        } catch (Exception e) {
            lblMessage.setText("Lỗi hệ thống!");
            e.printStackTrace();
        }
    }

    private void lbCloseMouseClicked(java.awt.event.MouseEvent evt) {
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DangNhap().setVisible(true));
    }
}
