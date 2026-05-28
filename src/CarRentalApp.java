import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;

public class CarRentalApp extends JFrame {
    private CarRentalSystem system;
    private User currentUser;

    private CardLayout rootLayout;
    private JPanel rootPanel;
    private CardLayout contentLayout;
    private JPanel contentPanel;

    private JTextField txtLoginUsername;
    private JPasswordField txtLoginPassword;

    private JButton activeSidebarButton;

    private JLabel lblTotalCars;
    private JLabel lblAvailableCars;
    private JLabel lblRentedCars;
    private JLabel lblTotalRentals;

    private DefaultTableModel dashboardRentalTableModel;
    private DefaultTableModel dashboardInventoryTableModel;

    private JTable tblCars;
    private DefaultTableModel carTableModel;
    private JTextField txtSearchCar;

    private JTable tblRentalCars;
    private DefaultTableModel rentalCarTableModel;
    private JTable tblRentals;
    private DefaultTableModel rentalTableModel;

    private JTable tblUsers;
    private DefaultTableModel userTableModel;

    private JLabel lblReportTotalCars;
    private JLabel lblReportAvailableCars;
    private JLabel lblReportRentedCars;
    private JLabel lblReportTotalRentals;

    private final Color DARK = new Color(24, 24, 27);
    private final Color DARK_ACTIVE = new Color(39, 39, 42);
    private final Color BG = new Color(245, 245, 245);
    private final Color TEXT = new Color(28, 28, 30);
    private final Color MUTED = new Color(110, 110, 115);
    private final Color BORDER = new Color(220, 220, 220);
    private final Color PRIMARY = new Color(52, 58, 64);
    private final Color GREEN = new Color(46, 125, 50);
    private final Color AMBER = new Color(181, 101, 29);
    private final Color RED = new Color(170, 45, 45);

    public CarRentalApp(CarRentalSystem system) {
        this.system = system;
        setTitle("DriveFlow - Car Rental Management System");
        setSize(1200, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        buildUI();
    }

    private void buildUI() {
        rootLayout = new CardLayout();
        rootPanel = new JPanel(rootLayout);
        rootPanel.add(createLoginPanel(), "LOGIN");
        add(rootPanel);
        rootLayout.show(rootPanel, "LOGIN");
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        JPanel mainSplit = new JPanel(new GridLayout(1, 2));
        mainSplit.setBackground(BG);

        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(DARK);
        left.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(50, 50, 55)));

        JPanel leftContent = new JPanel(new GridBagLayout());
        leftContent.setOpaque(false);

        GridBagConstraints leftContentGbc = new GridBagConstraints();
        leftContentGbc.gridx = 0;
        leftContentGbc.anchor = GridBagConstraints.WEST;
        leftContentGbc.fill = GridBagConstraints.NONE;

        JLabel logo = new JLabel("DRIVEFLOW");
        logo.setFont(new Font("SansSerif", Font.BOLD, 36));
        logo.setForeground(Color.WHITE);

        JLabel title = new JLabel("Car Rental Management System");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(new Color(210, 210, 215));

        JSeparator line = new JSeparator();
        line.setPreferredSize(new Dimension(120, 1));
        line.setForeground(new Color(120, 120, 125));
        line.setBackground(new Color(120, 120, 125));

        JLabel desc1 = new JLabel("Manage cars, rentals, users, and reports");
        desc1.setFont(new Font("SansSerif", Font.PLAIN, 12));
        desc1.setForeground(new Color(155, 155, 160));

        JLabel desc2 = new JLabel("in one simple desktop application.");
        desc2.setFont(new Font("SansSerif", Font.PLAIN, 12));
        desc2.setForeground(new Color(155, 155, 160));

        leftContentGbc.gridy = 0;
        leftContentGbc.insets = new Insets(0, 0, 14, 0);
        leftContent.add(logo, leftContentGbc);

        leftContentGbc.gridy++;
        leftContentGbc.insets = new Insets(0, 0, 20, 0);
        leftContent.add(title, leftContentGbc);

        leftContentGbc.gridy++;
        leftContentGbc.insets = new Insets(0, 0, 18, 0);
        leftContent.add(line, leftContentGbc);

        leftContentGbc.gridy++;
        leftContentGbc.insets = new Insets(0, 0, 4, 0);
        leftContent.add(desc1, leftContentGbc);

        leftContentGbc.gridy++;
        leftContentGbc.insets = new Insets(0, 0, 0, 0);
        leftContent.add(desc2, leftContentGbc);

        GridBagConstraints leftGbc = new GridBagConstraints();
        leftGbc.gridx = 0;
        leftGbc.gridy = 0;
        leftGbc.weightx = 1.0;
        leftGbc.weighty = 1.0;
        leftGbc.anchor = GridBagConstraints.WEST;
        leftGbc.insets = new Insets(0, 105, 0, 0);

        left.add(leftContent, leftGbc);

        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(BG);

        final int FORM_WIDTH = 410;
        final int FIELD_HEIGHT = 40;
        final int BUTTON_HEIGHT = 44;

        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridx = 0;
        formGbc.weightx = 1.0;
        formGbc.anchor = GridBagConstraints.WEST;
        formGbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel formTitle = new JLabel("Account Login");
        formTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        formTitle.setForeground(TEXT);

        JLabel formSub = new JLabel("Sign in to access the management panel.");
        formSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        formSub.setForeground(MUTED);

        JLabel userLabel = label("Username");
        JLabel passLabel = label("Password");

        txtLoginUsername = field();

        txtLoginPassword = new JPasswordField();
        styleField(txtLoginPassword);

        txtLoginUsername.setPreferredSize(new Dimension(FORM_WIDTH, FIELD_HEIGHT));
        txtLoginPassword.setPreferredSize(new Dimension(FORM_WIDTH, FIELD_HEIGHT));

        JButton loginButton = filledButton("Login", PRIMARY);
        JButton exitButton = outlineButton("Exit");

        loginButton.addActionListener(e -> performLogin());
        exitButton.addActionListener(e -> System.exit(0));

        JPanel buttons = new JPanel(new GridLayout(1, 2, 18, 0));
        buttons.setOpaque(false);
        buttons.setPreferredSize(new Dimension(FORM_WIDTH, BUTTON_HEIGHT));
        buttons.add(loginButton);
        buttons.add(exitButton);

        formGbc.gridy = 0;
        formGbc.insets = new Insets(0, 0, 8, 0);
        form.add(formTitle, formGbc);

        formGbc.gridy++;
        formGbc.insets = new Insets(0, 0, 32, 0);
        form.add(formSub, formGbc);

        formGbc.gridy++;
        formGbc.insets = new Insets(0, 0, 8, 0);
        form.add(userLabel, formGbc);

        formGbc.gridy++;
        formGbc.insets = new Insets(0, 0, 20, 0);
        form.add(txtLoginUsername, formGbc);

        formGbc.gridy++;
        formGbc.insets = new Insets(0, 0, 8, 0);
        form.add(passLabel, formGbc);

        formGbc.gridy++;
        formGbc.insets = new Insets(0, 0, 28, 0);
        form.add(txtLoginPassword, formGbc);

        formGbc.gridy++;
        formGbc.insets = new Insets(0, 0, 0, 0);
        form.add(buttons, formGbc);

        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.weightx = 1.0;
        rightGbc.weighty = 1.0;

        rightGbc.anchor = GridBagConstraints.CENTER;

        rightGbc.insets = new Insets(0, 0, 0, 0);

        right.add(form, rightGbc);

        mainSplit.add(left);
        mainSplit.add(right);

        panel.add(mainSplit, BorderLayout.CENTER);

        return panel;
    }

    private void performLogin() {
        String username = txtLoginUsername.getText().trim();
        String password = new String(txtLoginPassword.getPassword());
        User user = system.login(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        currentUser = user;
        showMainApp();
    }

    private void showMainApp() {
        JPanel appPanel = createMainLayout();
        rootPanel.add(appPanel, "APP");
        rootLayout.show(rootPanel, "APP");
        refreshAll();
    }

    private JPanel createMainLayout() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.add(createSidebar(), BorderLayout.WEST);

        contentLayout = new CardLayout();
        contentPanel = new JPanel(contentLayout);
        contentPanel.add(createDashboardPanel(), "DASHBOARD");
        contentPanel.add(createCarManagementPanel(), "CARS");
        contentPanel.add(createRentalManagementPanel(), "RENTALS");
        if (currentUser.canManageUsers()) {
            contentPanel.add(createUserManagementPanel(), "USERS");
        }
        contentPanel.add(createReportsPanel(), "REPORTS");
        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.setPreferredSize(new Dimension(240, 0));
        sidebar.setBackground(DARK);

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBackground(DARK);
        top.setBorder(new EmptyBorder(32, 22, 25, 22));

        JLabel logo = new JLabel("DRIVEFLOW");
        logo.setFont(new Font("SansSerif", Font.BOLD, 24));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel sub = new JLabel("MANAGEMENT PANEL");
        sub.setFont(new Font("SansSerif", Font.BOLD, 10));
        sub.setForeground(new Color(180, 180, 185));
        sub.setAlignmentX(Component.LEFT_ALIGNMENT);
        top.add(logo);
        top.add(Box.createRigidArea(new Dimension(0, 3)));
        top.add(sub);
        sidebar.add(top, BorderLayout.NORTH);

        JPanel menu = new JPanel();
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBackground(DARK);
        menu.setBorder(new EmptyBorder(10, 12, 10, 12));

        menu.add(sidebarButton("Dashboard", "DASHBOARD"));
        menu.add(Box.createRigidArea(new Dimension(0, 5)));
        menu.add(sidebarButton("Car Management", "CARS"));
        menu.add(Box.createRigidArea(new Dimension(0, 5)));
        menu.add(sidebarButton("Rental Management", "RENTALS"));
        menu.add(Box.createRigidArea(new Dimension(0, 5)));
        if (currentUser.canManageUsers()) {
            menu.add(sidebarButton("User Management", "USERS"));
            menu.add(Box.createRigidArea(new Dimension(0, 5)));
        }
        menu.add(sidebarButton("Reports", "REPORTS"));
        sidebar.add(menu, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(DARK);
        bottom.setBorder(new EmptyBorder(18, 18, 18, 18));
        JLabel user = new JLabel(currentUser.getUsername());
        user.setFont(new Font("SansSerif", Font.BOLD, 13));
        user.setForeground(Color.WHITE);
        user.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel role = new JLabel(currentUser.getRole());
        role.setFont(new Font("SansSerif", Font.PLAIN, 11));
        role.setForeground(new Color(180, 180, 185));
        role.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton logout = filledButton("Logout", DARK_ACTIVE);
        logout.setForeground(RED);
        logout.setMaximumSize(new Dimension(200, 34));
        logout.addActionListener(e -> performLogout());
        bottom.add(user);
        bottom.add(role);
        bottom.add(Box.createRigidArea(new Dimension(0, 12)));
        bottom.add(logout);
        sidebar.add(bottom, BorderLayout.SOUTH);
        return sidebar;
    }

    private JButton sidebarButton(String text, String screenName) {
        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setForeground(new Color(190, 190, 195));
        button.setBackground(DARK);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(216, 36));
        button.setBorder(new EmptyBorder(8, 14, 8, 14));

        // Default active screen
        if ("DASHBOARD".equals(screenName) && activeSidebarButton == null) {
            activeSidebarButton = button;
            button.setBackground(DARK_ACTIVE);
            button.setForeground(Color.WHITE);
            button.setFont(new Font("SansSerif", Font.BOLD, 14));
        }

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (button != activeSidebarButton) {
                    button.setBackground(new Color(32, 32, 36));
                    button.setForeground(Color.WHITE);
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                if (button != activeSidebarButton) {
                    button.setBackground(DARK);
                    button.setForeground(new Color(190, 190, 195));
                }
            }
        });

        button.addActionListener(e -> {
            if (activeSidebarButton != null) {
                activeSidebarButton.setBackground(DARK);
                activeSidebarButton.setForeground(new Color(190, 190, 195));
                activeSidebarButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
            }

            activeSidebarButton = button;
            activeSidebarButton.setBackground(DARK_ACTIVE);
            activeSidebarButton.setForeground(Color.WHITE);
            activeSidebarButton.setFont(new Font("SansSerif", Font.BOLD, 14));

            contentLayout.show(contentPanel, screenName);
            refreshAll();
        });

        return button;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = pagePanel("Dashboard", "Overview of your car rental business.");

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);

        JPanel cards = new JPanel(new GridLayout(1, 4, 26, 0));
        cards.setBackground(BG);
        cards.setPreferredSize(new Dimension(0, 105));

        lblTotalCars = metricCard(cards, "Total Cars");
        lblAvailableCars = metricCard(cards, "Available Cars");
        lblRentedCars = metricCard(cards, "Rented Cars");
        lblTotalRentals = metricCard(cards, "Total Rentals");

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(BG);
        topArea.setBorder(new EmptyBorder(0, 0, 18, 0));
        topArea.add(cards, BorderLayout.CENTER);

        body.add(topArea, BorderLayout.NORTH);

        JPanel tableArea = new JPanel(new GridLayout(2, 1, 0, 18));
        tableArea.setBackground(BG);

        JPanel recentPanel = new JPanel(new BorderLayout());
        recentPanel.setBackground(Color.WHITE);
        recentPanel.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel recentHeader = new JPanel(new BorderLayout());
        recentHeader.setBackground(Color.WHITE);
        recentHeader.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel recentTitle = new JLabel("Recent Rentals");
        recentTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        recentTitle.setForeground(TEXT);

        recentHeader.add(recentTitle, BorderLayout.WEST);
        recentPanel.add(recentHeader, BorderLayout.NORTH);

        dashboardRentalTableModel = new DefaultTableModel(
                new String[] { "Rental ID", "Car", "Customer", "Days", "Total Price", "Status" }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable recentTable = new JTable(dashboardRentalTableModel);
        styleTable(recentTable);

        JScrollPane recentScroll = new JScrollPane(recentTable);
        recentScroll.setBorder(BorderFactory.createEmptyBorder());
        recentScroll.getViewport().setBackground(Color.WHITE);

        recentPanel.add(recentScroll, BorderLayout.CENTER);
        tableArea.add(recentPanel);

        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBackground(Color.WHITE);
        inventoryPanel.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel inventoryHeader = new JPanel(new BorderLayout());
        inventoryHeader.setBackground(Color.WHITE);
        inventoryHeader.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel inventoryTitle = new JLabel("Car Inventory Summary");
        inventoryTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        inventoryTitle.setForeground(TEXT);

        inventoryHeader.add(inventoryTitle, BorderLayout.WEST);
        inventoryPanel.add(inventoryHeader, BorderLayout.NORTH);

        dashboardInventoryTableModel = new DefaultTableModel(
                new String[] { "Status", "Count" }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        JTable inventoryTable = new JTable(dashboardInventoryTableModel);
        styleTable(inventoryTable);

        JScrollPane inventoryScroll = new JScrollPane(inventoryTable);
        inventoryScroll.setBorder(BorderFactory.createEmptyBorder());
        inventoryScroll.getViewport().setBackground(Color.WHITE);

        inventoryPanel.add(inventoryScroll, BorderLayout.CENTER);
        tableArea.add(inventoryPanel);

        body.add(tableArea, BorderLayout.CENTER);

        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    private JLabel metricCard(JPanel parent, String title) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(20, 22, 18, 22)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setForeground(MUTED);

        JLabel value = new JLabel("0");
        value.setFont(new Font("SansSerif", Font.BOLD, 30));
        value.setForeground(TEXT);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        card.add(label, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(value, gbc);

        parent.add(card);

        return value;
    }

    private JPanel createCarManagementPanel() {
        JPanel panel = pagePanel("Car Management", "Add, edit, delete, search and display rental cars.");

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel leftTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftTools.setBackground(Color.WHITE);

        txtSearchCar = field();
        txtSearchCar.setPreferredSize(new Dimension(230, 36));

        JButton search = filledButton("Search", PRIMARY);
        search.setPreferredSize(new Dimension(105, 36));

        JButton showAll = outlineButton("Show All");
        showAll.setPreferredSize(new Dimension(105, 36));

        search.addActionListener(e -> performCarSearch());
        showAll.addActionListener(e -> {
            txtSearchCar.setText("");
            refreshCarTable();
        });

        leftTools.add(txtSearchCar);
        leftTools.add(search);
        leftTools.add(showAll);

        JPanel rightTools = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightTools.setBackground(Color.WHITE);

        JButton add = filledButton("Add Car", GREEN);
        add.setPreferredSize(new Dimension(105, 36));

        JButton edit = outlineButton("Edit Selected");
        edit.setPreferredSize(new Dimension(125, 36));

        JButton delete = filledButton("Delete Selected", RED);
        delete.setPreferredSize(new Dimension(135, 36));

        add.addActionListener(e -> showAddCarDialog());
        edit.addActionListener(e -> showEditCarDialog());
        delete.addActionListener(e -> deleteSelectedCar());

        rightTools.add(add);
        rightTools.add(edit);

        if (currentUser.canDeleteCar()) {
            rightTools.add(delete);
        }

        toolbar.add(leftTools, BorderLayout.WEST);
        toolbar.add(rightTools, BorderLayout.EAST);

        container.add(toolbar, BorderLayout.NORTH);

        String[] cols = { "Car ID", "Brand", "Model", "Year", "Type", "Price / Day", "Status" };

        carTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblCars = new JTable(carTableModel);
        styleTable(tblCars);

        JScrollPane scrollPane = new JScrollPane(tblCars);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        container.add(scrollPane, BorderLayout.CENTER);

        body.add(container, BorderLayout.CENTER);
        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createRentalManagementPanel() {
        JPanel panel = pagePanel("Rental Management", "Rent available cars and return rented cars.");

        JPanel split = new JPanel(new GridLayout(2, 1, 0, 18));
        split.setBackground(BG);

        rentalCarTableModel = new DefaultTableModel(
                new String[] { "Car ID", "Brand", "Model", "Year", "Type", "Price / Day", "Status" }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblRentalCars = new JTable(rentalCarTableModel);
        styleTable(tblRentalCars);

        split.add(sectionWithButton(
                "Fleet Selection",
                "Rent Selected Car",
                GREEN,
                tblRentalCars,
                e -> showRentCarDialog()));

        rentalTableModel = new DefaultTableModel(
                new String[] { "Rental ID", "Car ID", "Car Model", "Customer", "Phone",
                        "Days", "Total Price", "Status" },
                0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblRentals = new JTable(rentalTableModel);
        styleTable(tblRentals);

        split.add(sectionWithButton(
                "Rental Records",
                "Return Selected Car",
                AMBER,
                tblRentals,
                e -> returnSelectedCar()));

        panel.add(split, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = pagePanel("User Management", "Create staff users for the system.");

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(Color.WHITE);
        container.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(Color.WHITE);
        toolbar.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel title = new JLabel("Staff Users");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(TEXT);

        JButton addStaff = filledButton("Create Staff", PRIMARY);
        addStaff.setPreferredSize(new Dimension(120, 36));
        addStaff.addActionListener(e -> showAddStaffDialog());

        toolbar.add(title, BorderLayout.WEST);
        toolbar.add(addStaff, BorderLayout.EAST);

        container.add(toolbar, BorderLayout.NORTH);

        userTableModel = new DefaultTableModel(new String[] { "Username", "Role" }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblUsers = new JTable(userTableModel);
        styleTable(tblUsers);

        JScrollPane scrollPane = new JScrollPane(tblUsers);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        container.add(scrollPane, BorderLayout.CENTER);

        body.add(container, BorderLayout.CENTER);
        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReportsPanel() {
        JPanel panel = pagePanel("Reports", "View summary and export inventory report.");

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);

        JPanel summaryCards = new JPanel(new GridLayout(1, 4, 26, 0));
        summaryCards.setBackground(BG);
        summaryCards.setPreferredSize(new Dimension(0, 105));

        lblReportTotalCars = metricCard(summaryCards, "Total Cars");
        lblReportAvailableCars = metricCard(summaryCards, "Available Cars");
        lblReportRentedCars = metricCard(summaryCards, "Rented Cars");
        lblReportTotalRentals = metricCard(summaryCards, "Total Rentals");

        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(BG);
        topArea.setBorder(new EmptyBorder(0, 0, 18, 0));
        topArea.add(summaryCards, BorderLayout.CENTER);

        body.add(topArea, BorderLayout.NORTH);

        JPanel reportPanel = new JPanel(new BorderLayout());
        reportPanel.setBackground(Color.WHITE);
        reportPanel.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel reportHeader = new JPanel();
        reportHeader.setLayout(new BoxLayout(reportHeader, BoxLayout.Y_AXIS));
        reportHeader.setBackground(Color.WHITE);
        reportHeader.setBorder(new EmptyBorder(22, 22, 18, 22));

        JLabel title = new JLabel("Inventory Report");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel("Export the current car inventory summary into a text report file.");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        desc.setForeground(MUTED);
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);

        reportHeader.add(title);
        reportHeader.add(Box.createRigidArea(new Dimension(0, 6)));
        reportHeader.add(desc);

        reportPanel.add(reportHeader, BorderLayout.NORTH);

        JPanel reportContent = new JPanel(new BorderLayout());
        reportContent.setBackground(Color.WHITE);
        reportContent.setBorder(new EmptyBorder(0, 22, 22, 22));

        JPanel infoBox = new JPanel();
        infoBox.setLayout(new BoxLayout(infoBox, BoxLayout.Y_AXIS));
        infoBox.setBackground(Color.WHITE);

        JLabel fileLabel = new JLabel("File path:");
        fileLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        fileLabel.setForeground(MUTED);
        fileLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel filePath = new JLabel("reports/car_inventory_report.txt");
        filePath.setFont(new Font("SansSerif", Font.PLAIN, 12));
        filePath.setForeground(TEXT);
        filePath.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoBox.add(fileLabel);
        infoBox.add(Box.createRigidArea(new Dimension(0, 6)));
        infoBox.add(filePath);

        JButton export = filledButton("Export Report", PRIMARY);
        export.setPreferredSize(new Dimension(150, 38));
        export.addActionListener(e -> exportReport());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(export);

        reportContent.add(infoBox, BorderLayout.WEST);
        reportContent.add(buttonPanel, BorderLayout.EAST);

        reportPanel.add(reportContent, BorderLayout.CENTER);

        body.add(reportPanel, BorderLayout.CENTER);

        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    private JPanel pagePanel(String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        panel.setBorder(new EmptyBorder(36, 25, 25, 25));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(BG);

        header.setBorder(new EmptyBorder(0, 0, 28, 0));

        JLabel t = new JLabel(title);
        t.setFont(new Font("SansSerif", Font.BOLD, 24));
        t.setForeground(TEXT);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("SansSerif", Font.BOLD, 10));
        s.setForeground(MUTED);
        s.setAlignmentX(Component.LEFT_ALIGNMENT);

        header.add(t);
        header.add(Box.createRigidArea(new Dimension(0, 6)));
        header.add(s);

        panel.add(header, BorderLayout.NORTH);

        return panel;
    }

    private JPanel sectionWithButton(String title, String buttonText, Color color, JTable table,
            java.awt.event.ActionListener action) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel head = new JPanel(new BorderLayout());
        head.setBackground(Color.WHITE);
        head.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(TEXT);

        JButton button = filledButton(buttonText, color);
        button.setPreferredSize(new Dimension(150, 36));
        button.addActionListener(action);

        head.add(label, BorderLayout.WEST);
        head.add(button, BorderLayout.EAST);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(Color.WHITE);

        panel.add(head, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void refreshAll() {
        refreshDashboard();
        refreshCarTable();
        refreshRentalTables();
        refreshUserTable();
        refreshReports();
    }

    private void refreshDashboard() {
        int totalCars = system.getTotalCars();
        int availableCars = system.getAvailableCarCount();
        int rentedCars = system.getRentedCarCount();
        int totalRentals = system.getTotalRentalCount();

        if (lblTotalCars != null)
            lblTotalCars.setText(String.valueOf(totalCars));

        if (lblAvailableCars != null)
            lblAvailableCars.setText(String.valueOf(availableCars));

        if (lblRentedCars != null)
            lblRentedCars.setText(String.valueOf(rentedCars));

        if (lblTotalRentals != null)
            lblTotalRentals.setText(String.valueOf(totalRentals));

        if (dashboardRentalTableModel != null) {
            dashboardRentalTableModel.setRowCount(0);

            int count = 0;

            for (Rental rental : system.getAllRentals()) {
                if (count >= 5)
                    break;

                dashboardRentalTableModel.addRow(new Object[] {
                        rental.getRentalId(),
                        rental.getCar().getBrand() + " " + rental.getCar().getModel()
                                + " (" + rental.getCar().getCarId() + ")",
                        rental.getCustomer().getName(),
                        rental.getDays(),
                        String.format("RM%.2f", rental.getTotalPrice()),
                        rental.getStatus()
                });

                count++;
            }
        }

        if (dashboardInventoryTableModel != null) {
            dashboardInventoryTableModel.setRowCount(0);

            dashboardInventoryTableModel.addRow(new Object[] { "Available Cars", availableCars });
            dashboardInventoryTableModel.addRow(new Object[] { "Rented Cars", rentedCars });
            dashboardInventoryTableModel.addRow(new Object[] { "Total Cars", totalCars });
        }
    }

    private void refreshCarTable() {
        if (carTableModel == null)
            return;
        carTableModel.setRowCount(0);
        for (Car car : system.getAllCars()) {
            carTableModel.addRow(carRow(car));
        }
    }

    private void refreshRentalTables() {
        if (rentalCarTableModel != null) {
            rentalCarTableModel.setRowCount(0);
            for (Car car : system.getAllCars()) {
                rentalCarTableModel.addRow(carRow(car));
            }
        }
        if (rentalTableModel != null) {
            rentalTableModel.setRowCount(0);
            for (Rental rental : system.getAllRentals()) {
                rentalTableModel.addRow(new Object[] { rental.getRentalId(), rental.getCar().getCarId(),
                        rental.getCar().getBrand() + " " + rental.getCar().getModel(), rental.getCustomer().getName(),
                        rental.getCustomer().getPhone(), rental.getDays(),
                        String.format("RM%.2f", rental.getTotalPrice()), rental.getStatus() });
            }
        }
    }

    private void refreshUserTable() {
        if (userTableModel == null)
            return;
        userTableModel.setRowCount(0);
        for (User user : system.getStaffUsers()) {
            userTableModel.addRow(new Object[] { user.getUsername(), user.getRole() });
        }
    }

    private void refreshReports() {
        if (lblReportTotalCars != null)
            lblReportTotalCars.setText(String.valueOf(system.getTotalCars()));
        if (lblReportAvailableCars != null)
            lblReportAvailableCars.setText(String.valueOf(system.getAvailableCarCount()));
        if (lblReportRentedCars != null)
            lblReportRentedCars.setText(String.valueOf(system.getRentedCarCount()));
        if (lblReportTotalRentals != null)
            lblReportTotalRentals.setText(String.valueOf(system.getTotalRentalCount()));
    }

    private Object[] carRow(Car car) {
        return new Object[] { car.getCarId(), car.getBrand(), car.getModel(), car.getYear(), car.getType(),
                String.format("RM%.2f", car.getPricePerDay()), car.getStatus() };
    }

    private void performCarSearch() {
        carTableModel.setRowCount(0);
        for (Car car : system.searchCars(txtSearchCar.getText())) {
            carTableModel.addRow(carRow(car));
        }
    }

    private void showAddCarDialog() {
        showCarDialog(null);
    }

    private void showEditCarDialog() {
        int row = tblCars.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a car to edit.");
            return;
        }
        String carId = tblCars.getValueAt(row, 0).toString();
        showCarDialog(system.findCarById(carId));
    }

    private void showCarDialog(Car editCar) {
        JTextField brand = new JTextField();
        JTextField model = new JTextField();
        JTextField year = new JTextField();

        JComboBox<String> type = new JComboBox<>(new String[] {
                "Sedan",
                "Hatchback",
                "SUV",
                "MPV",
                "Pickup",
                "Van",
                "Coupe"
        });

        JTextField price = new JTextField();

        JPanel form;

        if (editCar != null) {
            brand.setText(editCar.getBrand());
            model.setText(editCar.getModel());
            year.setText(String.valueOf(editCar.getYear()));
            type.setSelectedItem(editCar.getType());
            price.setText(String.valueOf(editCar.getPricePerDay()));

            JLabel carIdLabel = new JLabel(editCar.getCarId());

            form = formPanel(
                    new String[] { "Car ID", "Brand", "Model", "Year", "Type", "Price / Day" },
                    new JComponent[] { carIdLabel, brand, model, year, type, price });
        } else {
            form = formPanel(
                    new String[] { "Brand", "Model", "Year", "Type", "Price / Day" },
                    new JComponent[] { brand, model, year, type, price });
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                form,
                editCar == null ? "Add Car" : "Edit Car",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String brandText = brand.getText().trim();
                String modelText = model.getText().trim();
                String typeText = type.getSelectedItem().toString();

                if (brandText.isEmpty() || modelText.isEmpty() || typeText.isEmpty()) {
                    throw new IllegalArgumentException();
                }

                int carYear = Integer.parseInt(year.getText().trim());
                double carPrice = Double.parseDouble(price.getText().trim());

                if (carYear <= 0 || carPrice <= 0) {
                    throw new IllegalArgumentException();
                }

                boolean success;

                if (editCar == null) {
                    success = system.addCar(brandText, modelText, carYear, typeText, carPrice);
                } else {
                    success = system.editCar(
                            editCar.getCarId(),
                            brandText,
                            modelText,
                            carYear,
                            typeText,
                            carPrice);
                }

                if (success) {
                    JOptionPane.showMessageDialog(
                            this,
                            editCar == null ? "Car added successfully." : "Car updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Operation failed. Please check the entered data.");
                }

                refreshAll();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Year and price must be valid numbers.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter complete and valid car information.");
            }
        }
    }

    private void deleteSelectedCar() {
        int row = tblCars.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a car to delete.");
            return;
        }
        String carId = tblCars.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Delete car " + carId + "?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = system.deleteCar(carId, currentUser);
            JOptionPane.showMessageDialog(this,
                    success ? "Car deleted." : "Delete failed. Rented cars cannot be deleted.");
            refreshAll();
        }
    }

    private void showRentCarDialog() {
        int row = tblRentalCars.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a car to rent.");
            return;
        }
        String carId = tblRentalCars.getValueAt(row, 0).toString();
        Car car = system.findCarById(carId);
        if (car == null || !car.isAvailable()) {
            JOptionPane.showMessageDialog(this, "This car is not available.");
            return;
        }
        JTextField name = new JTextField();
        JTextField phone = new JTextField();
        JTextField days = new JTextField();
        JPanel form = formPanel(new String[] { "Customer Name", "Phone", "Rental Days" },
                new JComponent[] { name, phone, days });
        int result = JOptionPane.showConfirmDialog(this, form, "Rent Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int rentalDays = Integer.parseInt(days.getText().trim());
                Rental rental = system.rentCar(carId, name.getText().trim(), phone.getText().trim(), rentalDays);
                JOptionPane.showMessageDialog(this,
                        rental == null ? "Rental failed." : "Rental created: " + rental.getRentalId());
                refreshAll();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid rental information.");
            }
        }
    }

    private void returnSelectedCar() {
        int row = tblRentals.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a rental to return.");
            return;
        }
        String rentalId = tblRentals.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Return rental " + rentalId + "?", "Confirm Return",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = system.returnCar(rentalId);
            JOptionPane.showMessageDialog(this, success ? "Car returned." : "Return failed.");
            refreshAll();
        }
    }

    private void showAddStaffDialog() {
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        JPanel form = formPanel(new String[] { "Username", "Password" }, new JComponent[] { username, password });
        int result = JOptionPane.showConfirmDialog(this, form, "Create Staff", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            boolean success = system.addStaff(username.getText().trim(), new String(password.getPassword()));
            JOptionPane.showMessageDialog(this, success ? "Staff created." : "Failed. Username may already exist.");
            refreshAll();
        }
    }

    private void exportReport() {
        boolean success = system.exportInventoryReport();
        JOptionPane.showMessageDialog(this,
                success ? "Report exported to reports/car_inventory_report.txt" : "Export failed.");
    }

    private void performLogout() {
        currentUser = null;
        txtLoginUsername.setText("");
        txtLoginPassword.setText("");
        rootLayout.show(rootPanel, "LOGIN");
    }

    private JPanel formPanel(String[] labels, JComponent[] fields) {
        JPanel panel = new JPanel(new GridLayout(labels.length, 2, 8, 8));
        for (int i = 0; i < labels.length; i++) {
            panel.add(new JLabel(labels[i]));
            panel.add(fields[i]);
        }
        return panel;
    }

    private JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField field() {
        JTextField field = new JTextField();
        styleField(field);
        return field;
    }

    private void styleField(JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(5, 8, 5, 8)));
        field.setMaximumSize(new Dimension(350, 32));
        field.setPreferredSize(new Dimension(200, 32));
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT);
    }

    private JButton filledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 32));
        return button;
    }

    private JButton outlineButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setForeground(PRIMARY);
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(BORDER));
        button.setPreferredSize(new Dimension(120, 32));
        return button;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(42);
        table.setFont(new Font("SansSerif", Font.PLAIN, 12));

        table.setSelectionBackground(new Color(235, 235, 235));
        table.setSelectionForeground(TEXT);

        table.setGridColor(BORDER);
        table.setShowGrid(false);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.setBackground(Color.WHITE);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setFont(new Font("SansSerif", Font.BOLD, 12));
                label.setForeground(MUTED);
                label.setBackground(Color.WHITE);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(new EmptyBorder(0, 16, 8, 0));

                return label;
            }
        };

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setFont(new Font("SansSerif", Font.PLAIN, 12));
                label.setForeground(TEXT);
                label.setBackground(isSelected ? new Color(235, 235, 235) : Color.WHITE);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(new EmptyBorder(0, 16, 0, 0));

                return label;
            }
        };

        table.getTableHeader().setDefaultRenderer(headerRenderer);
        table.getTableHeader().setBackground(Color.WHITE);
        table.getTableHeader().setForeground(MUTED);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(true);
        table.getTableHeader().setPreferredSize(new Dimension(0, 40));
        table.getTableHeader().setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER));

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
        }
    }
}
