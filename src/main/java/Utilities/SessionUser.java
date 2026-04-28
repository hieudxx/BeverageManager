package Utilities;

import DomainModels.NhanVien;

public class SessionUser {

    private static SessionUser instance;
    private NhanVien currentUser;

    private SessionUser() {
    }

    public static SessionUser getInstance() {
        if (instance == null) {
            instance = new SessionUser();
        }
        return instance;
    }

    public void setCurrentUser(NhanVien user) {
        this.currentUser = user;
    }

    public NhanVien getCurrentUser() {
        return currentUser;
    }
}
