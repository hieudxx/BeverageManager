package Utilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Chạy MỘT LẦN để đổi toàn bộ mật khẩu thô hiện có trong bảng NhanVien sang dạng băm.
 * Bản ghi nào đã băm rồi sẽ được bỏ qua nên chạy lại nhiều lần cũng an toàn.
 *
 * Trước khi chạy: sao lưu DB và mở rộng cột mat_khau (xem hướng dẫn kèm theo).
 */
public class MigratePasswords {

    public static void main(String[] args) {
        int daDoi = 0;
        int boQua = 0;
        try {
            Connection con = DBConnect.getConnect();
            con.setAutoCommit(false);
            try (PreparedStatement select = con.prepareStatement("SELECT id, mat_khau FROM NhanVien");
                 PreparedStatement update = con.prepareStatement("UPDATE NhanVien SET mat_khau = ? WHERE id = ?");
                 ResultSet rs = select.executeQuery()) {

                while (rs.next()) {
                    int id = rs.getInt("id");
                    String matKhau = rs.getString("mat_khau");
                    if (matKhau == null || matKhau.isEmpty() || PasswordUtil.isHashed(matKhau)) {
                        boQua++;
                        continue;
                    }
                    update.setString(1, PasswordUtil.hash(matKhau));
                    update.setInt(2, id);
                    update.addBatch();
                    daDoi++;
                }
                update.executeBatch();
            }
            con.commit(); // chỉ ghi khi tất cả đều thành công
            con.setAutoCommit(true);
            System.out.println("Hoàn tất. Đã băm: " + daDoi + " | Bỏ qua: " + boQua);
        } catch (SQLException e) {
            System.out.println("Lỗi, không có thay đổi nào được ghi: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
