public class Staff extends User {
    public Staff(String username, String password) {
        super(username, password, "Staff");
    }

    @Override
    public boolean canDeleteCar() {
        return false;
    }

    @Override
    public boolean canManageUsers() {
        return false;
    }

    @Override
    public String getDashboardTitle() {
        return "Staff Dashboard";
    }
}
