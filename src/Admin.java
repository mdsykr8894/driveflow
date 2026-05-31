public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, "Admin", "ACTIVE");
    }

    public Admin(String username, String password, String status) {
        super(username, password, "Admin", status);
    }

    @Override
    public boolean canDeleteCar() {
        return true;
    }

    @Override
    public boolean canManageUsers() {
        return true;
    }

    @Override
    public String getDashboardTitle() {
        return "Admin Dashboard";
    }
}
