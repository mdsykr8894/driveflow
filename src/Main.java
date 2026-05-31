import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CarRentalSystem system = new CarRentalSystem();
            CarRentalApp app = new CarRentalApp(system);
            app.setVisible(true);
        });
    }
}
