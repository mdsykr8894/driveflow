public class Admin extends User {
    public Admin(String username, String password) {
        super(username, password, "Admin");
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
