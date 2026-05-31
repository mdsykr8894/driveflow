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

    private CardLayout internalSysLayout;
    private JPanel internalSysPanel;

    private CardLayout internalRentLayout;
    private JPanel internalRentPanel;

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
    private JTextField txtSearchUser;

    private JTable tblCustomers;
    private DefaultTableModel customerTableModel;
    private JTextField txtSearchCustomer;
    private JTextField txtSearchRentalCar;
    private JTextField txtSearchActiveRental;
    
    private JTable tblRentalRecords;
    private DefaultTableModel rentalRecordTableModel;
    private JTextField txtSearchRentalRecord;

    private JTable tblCarList;
    private DefaultTableModel carListTableModel;
    private JTextField txtSearchCarList;
    private JButton btnSidebarRentalDesk;
    private JButton btnToggleRentTab;
    private JButton btnToggleReturnTab;

    private JLabel lblReportTotalCars;
    private JLabel lblReportAvailableCars;
    private JLabel lblReportRentedCars;
    private JLabel lblReportTotalRentals;

// Deep slate darks (Replaces your neutral charcoals)
    private final Color DARK = new Color(15, 23, 42);         // Deep space slate
    private final Color DARK_ACTIVE = new Color(30, 41, 59);  // Polished cockpit alloy
    private final Color BG = new Color(241, 245, 249);        // Clean, bright lab white

    // Sharp, readable structures
    private final Color TEXT = new Color(15, 23, 42);         // High-contrast ink dark
    private final Color MUTED = new Color(100, 116, 139);     // Cool titanium grey
    private final Color BORDER = new Color(226, 232, 240);    // Clean interface grid line

    // Premium, sophisticated accents
    private final Color PRIMARY = new Color(99, 102, 241);    // Indigo/Synthwave purple-blue
    private final Color GREEN = new Color(16, 185, 129);      // Crisp mint/emerald green
    private final Color AMBER = new Color(245, 158, 11);      // Laser orange / Warning amber
    private final Color RED = new Color(239, 68, 68);   
    private final Color FUCHSIA = new Color(239, 68, 68);      // Vibrant plasma red

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
        logo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        logo.setForeground(Color.WHITE);

        JLabel title = new JLabel("Car Rental Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(new Color(210, 210, 215));

        JSeparator line = new JSeparator();
        line.setPreferredSize(new Dimension(240, 2));
        line.setForeground(new Color(120, 120, 125));
        line.setBackground(new Color(120, 120, 125));

        JLabel desc1 = new JLabel("Manage cars, rentals, users, and reports");
        desc1.setFont(new Font("Arial", Font.PLAIN, 12));
        desc1.setForeground(new Color(155, 155, 160));

        JLabel desc2 = new JLabel("in one simple desktop application.");
        desc2.setFont(new Font("Arial", Font.PLAIN, 12));
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
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        formTitle.setForeground(TEXT);

        JLabel formSub = new JLabel("Sign in to access the management panel.");
        formSub.setFont(new Font("Segoe UI", Font.BOLD, 12));
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

        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                loginButton.setBackground(PRIMARY.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                loginButton.setBackground(PRIMARY);
            }
        });

        exitButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                exitButton.setBackground(DARK_ACTIVE);
                exitButton.setOpaque(true);
            }

            public void mouseExited(java.awt.event.MouseEvent e) {
                exitButton.setBackground(new java.awt.Color(0, 0, 0, 0)); // Transparent again
                exitButton.setOpaque(false);
            }
        });

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
        
        if (system.isUserDeactivated(username, password)) {
            JOptionPane.showMessageDialog(this, "This account has been deactivated. Please contact Admin.", "Login Failed",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        User user = system.login(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        currentUser = user;
        showMainApp();
    }

    private boolean containsPipeSymbol(String... inputs) {
        for (String input : inputs) {
            if (input != null && input.contains("|")) {
                return true;
            }
        }
        return false;
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
        contentPanel.add(createCarListPanel(), "CAR_LIST");
        contentPanel.add(createRentalDeskPanel(), "RENTAL_DESK");
        contentPanel.add(createSystemManagementPanel(), "SYSTEM_MANAGEMENT");
        // contentPanel.add(createRentalManagementPanel(), "RENTALS");
        contentPanel.add(createReportsPanel(), "REPORTS");
        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRentalDeskPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);
        panel.setBorder(new EmptyBorder(36, 25, 25, 25));

        // Left Header (Title/Subtitle)
        JPanel leftHeader = new JPanel();
        leftHeader.setLayout(new BoxLayout(leftHeader, BoxLayout.Y_AXIS));
        leftHeader.setBackground(BG);

        JLabel t = new JLabel("Rental Desk");
        t.setFont(new Font("Segoe UI", Font.BOLD, 24));
        t.setForeground(TEXT);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel s = new JLabel("Rent available cars and return active rentals.");
        s.setFont(new Font("Segoe UI", Font.BOLD, 10));
        s.setForeground(MUTED);
        s.setAlignmentX(Component.LEFT_ALIGNMENT);

        leftHeader.add(t);
        leftHeader.add(Box.createRigidArea(new Dimension(0, 6)));
        leftHeader.add(s);

        // Horizontal Row Header
        JPanel rowHeader = new JPanel(new BorderLayout());
        rowHeader.setBackground(BG);
        rowHeader.setBorder(new EmptyBorder(0, 0, 28, 0));
        rowHeader.add(leftHeader, BorderLayout.WEST);

        // Toggle buttons on the right (EAST)
        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        togglePanel.setBackground(BG);
        togglePanel.setBorder(new EmptyBorder(8, 0, 0, 0)); // Center vertically with Title

        btnToggleRentTab = filledButton("Rent Car", PRIMARY);
        btnToggleRentTab.setPreferredSize(new Dimension(130, 36));

        btnToggleReturnTab = outlineButton("Return Car");
        btnToggleReturnTab.setPreferredSize(new Dimension(130, 36));

        btnToggleRentTab.addActionListener(e -> {
            internalRentLayout.show(internalRentPanel, "RENT");
            btnToggleRentTab.setBackground(PRIMARY);
            btnToggleRentTab.setForeground(Color.WHITE);
            btnToggleRentTab.setOpaque(true);
            btnToggleRentTab.setContentAreaFilled(true);
            btnToggleRentTab.setBorderPainted(false);

            btnToggleReturnTab.setBackground(Color.WHITE);
            btnToggleReturnTab.setForeground(PRIMARY);
            btnToggleReturnTab.setOpaque(true);
            btnToggleReturnTab.setContentAreaFilled(true);
            btnToggleReturnTab.setBorderPainted(true);
            btnToggleReturnTab.setBorder(BorderFactory.createLineBorder(BORDER));

            refreshAll();
        });

        btnToggleReturnTab.addActionListener(e -> {
            internalRentLayout.show(internalRentPanel, "RETURN");
            btnToggleReturnTab.setBackground(PRIMARY);
            btnToggleReturnTab.setForeground(Color.WHITE);
            btnToggleReturnTab.setOpaque(true);
            btnToggleReturnTab.setContentAreaFilled(true);
            btnToggleReturnTab.setBorderPainted(false);

            btnToggleRentTab.setBackground(Color.WHITE);
            btnToggleRentTab.setForeground(PRIMARY);
            btnToggleRentTab.setOpaque(true);
            btnToggleRentTab.setContentAreaFilled(true);
            btnToggleRentTab.setBorderPainted(true);
            btnToggleRentTab.setBorder(BorderFactory.createLineBorder(BORDER));

            refreshAll();
        });

        togglePanel.add(btnToggleRentTab);
        togglePanel.add(btnToggleReturnTab);
        rowHeader.add(togglePanel, BorderLayout.EAST);

        panel.add(rowHeader, BorderLayout.NORTH);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);

        internalRentLayout = new CardLayout();
        internalRentPanel = new JPanel(internalRentLayout);
        internalRentPanel.setBackground(BG);

        // 2. Tab 1: Rent Car Panel
        JPanel rentTab = new JPanel(new BorderLayout());
        rentTab.setBackground(BG);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(DARK_ACTIVE);
        container.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(DARK_ACTIVE);
        toolbar.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel leftTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftTools.setBackground(DARK_ACTIVE);

        txtSearchRentalCar = field();
        txtSearchRentalCar.setPreferredSize(new Dimension(230, 36));

        JButton search = filledButton("Search", PRIMARY);
        search.setPreferredSize(new Dimension(105, 36));

        JButton showAll = outlineButton("Show All");
        showAll.setPreferredSize(new Dimension(105, 36));

        search.addActionListener(e -> refreshRentalTables());
        showAll.addActionListener(e -> {
            txtSearchRentalCar.setText("");
            refreshRentalTables();
        });

        leftTools.add(txtSearchRentalCar);
        leftTools.add(search);
        leftTools.add(showAll);

        JPanel rightTools = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightTools.setBackground(DARK_ACTIVE);

        JButton btnRent = filledButton("Rent Selected Car", GREEN);
        btnRent.setPreferredSize(new Dimension(160, 36));
        btnRent.addActionListener(e -> showRentCarDialog());
        rightTools.add(btnRent);

        toolbar.add(leftTools, BorderLayout.WEST);
        toolbar.add(rightTools, BorderLayout.EAST);
        container.add(toolbar, BorderLayout.NORTH);

        String[] cols = { "Car ID", "Brand", "Model", "Year", "Type", "Price / Day" };
        rentalCarTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblRentalCars = new JTable(rentalCarTableModel) {
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        };
        styleTable(tblRentalCars);

        JScrollPane scrollPane = new JScrollPane(tblRentalCars);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BG);
        container.add(scrollPane, BorderLayout.CENTER);

        rentTab.add(container, BorderLayout.CENTER);
        internalRentPanel.add(rentTab, "RENT");

        // Tab 2: Return Car Panel
        JPanel returnTab = new JPanel(new BorderLayout());
        returnTab.setBackground(BG);

        JPanel returnContainer = new JPanel(new BorderLayout());
        returnContainer.setBackground(DARK_ACTIVE);
        returnContainer.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel returnToolbar = new JPanel(new BorderLayout());
        returnToolbar.setBackground(DARK_ACTIVE);
        returnToolbar.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel returnLeftTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        returnLeftTools.setBackground(DARK_ACTIVE);

        txtSearchActiveRental = field();
        txtSearchActiveRental.setPreferredSize(new Dimension(230, 36));

        JButton returnSearch = filledButton("Search", PRIMARY);
        returnSearch.setPreferredSize(new Dimension(105, 36));

        JButton returnShowAll = outlineButton("Show All");
        returnShowAll.setPreferredSize(new Dimension(105, 36));

        returnSearch.addActionListener(e -> refreshRentalTables());
        returnShowAll.addActionListener(e -> {
            txtSearchActiveRental.setText("");
            refreshRentalTables();
        });

        returnLeftTools.add(txtSearchActiveRental);
        returnLeftTools.add(returnSearch);
        returnLeftTools.add(returnShowAll);

        JPanel returnRightTools = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        returnRightTools.setBackground(DARK_ACTIVE);

        JButton btnReturn = filledButton("Return Selected Car", FUCHSIA);
        btnReturn.setPreferredSize(new Dimension(160, 36));
        btnReturn.addActionListener(e -> returnSelectedCar());
        returnRightTools.add(btnReturn);

        returnToolbar.add(returnLeftTools, BorderLayout.WEST);
        returnToolbar.add(returnRightTools, BorderLayout.EAST);
        returnContainer.add(returnToolbar, BorderLayout.NORTH);

        String[] returnCols = { "Rental ID", "Car ID", "Customer", "Phone", "Start Date", "Expected Return", "Total Price" };
        rentalTableModel = new DefaultTableModel(returnCols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblRentals = new JTable(rentalTableModel) {
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        };
        styleTable(tblRentals);

        JScrollPane returnScrollPane = new JScrollPane(tblRentals);
        returnScrollPane.setBorder(BorderFactory.createEmptyBorder());
        returnScrollPane.getViewport().setBackground(BG);
        returnContainer.add(returnScrollPane, BorderLayout.CENTER);

        returnTab.add(returnContainer, BorderLayout.CENTER);
        internalRentPanel.add(returnTab, "RETURN");

        internalRentLayout.show(internalRentPanel, "RENT");
        body.add(internalRentPanel, BorderLayout.CENTER);
        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createSystemManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG);

        internalSysLayout = new CardLayout();
        internalSysPanel = new JPanel(internalSysLayout);
        internalSysPanel.setBackground(BG);

        // 1. Build the HUB Screen
        JPanel hubPanel = pagePanel("System Management", "Select a management section to audit and update records.");
        JPanel hubBody = new JPanel(new GridBagLayout());
        hubBody.setBackground(BG);

        // Grid container for cards (2x2 for Admin, 1x3 for Staff)
        boolean isAdmin = currentUser.canManageUsers();
        int rows = isAdmin ? 2 : 1;
        int cols = isAdmin ? 2 : 3;
        JPanel cardsGrid = new JPanel(new GridLayout(rows, cols, 24, 18));
        cardsGrid.setBackground(BG);

        // Card 1: Cars
        JPanel cardCars = createManagementCard(
            "Car Management",
            "Add, edit, delete, search and display cars.",
            "Manage Cars",
            TEXT,
            e -> {
                internalSysLayout.show(internalSysPanel, "CARS");
                refreshAll();
            }
        );
        cardsGrid.add(cardCars);

        // Card 2: Customers
        JPanel cardCustomers = createManagementCard(
            "Customer Management",
            "View, search and display customer records.",
            "View Customers",
            GREEN,
            e -> {
                internalSysLayout.show(internalSysPanel, "CUSTOMERS");
                refreshAll();
            }
        );
        cardsGrid.add(cardCustomers);

        // Card 3: Rental Records
        JPanel cardRentals = createManagementCard(
            "Rental Records",
            "View, search and display rental history.",
            "View Records",
            AMBER,
            e -> {
                internalSysLayout.show(internalSysPanel, "RENTAL_RECORDS");
                refreshAll();
            }
        );
        cardsGrid.add(cardRentals);

        // Card 4: Staff Users (Admin only)
        if (isAdmin) {
            JPanel cardStaff = createManagementCard(
                "Staff Management",
                "Create staff, manage status and view passwords.",
                "Manage Staff",
                PRIMARY,
                e -> {
                    internalSysLayout.show(internalSysPanel, "USERS");
                    refreshAll();
                }
            );
            cardsGrid.add(cardStaff);
        }

        // Wrap grid and anchor to top-left (NORTHWEST)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        hubBody.add(cardsGrid, gbc);

        hubPanel.add(hubBody, BorderLayout.CENTER);
        internalSysPanel.add(hubPanel, "HUB");

        // 2. Build Sub-Panels and register to internal CardLayout
        internalSysPanel.add(addBackButtonToPanel(createCarManagementPanel()), "CARS");

        // Customers Panel
        internalSysPanel.add(addBackButtonToPanel(createCustomerManagementPanel()), "CUSTOMERS");

        // Rental Records Panel
        internalSysPanel.add(addBackButtonToPanel(createRentalRecordsManagementPanel()), "RENTAL_RECORDS");

        if (isAdmin) {
            internalSysPanel.add(addBackButtonToPanel(createUserManagementPanel()), "USERS");
        }

        internalSysLayout.show(internalSysPanel, "HUB");
        panel.add(internalSysPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createManagementCard(String title, String description, String buttonText, Color accentColor, java.awt.event.ActionListener action) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(4, 1, 1, 1, accentColor),
                new EmptyBorder(20, 22, 20, 22)
        ));
        card.setPreferredSize(new Dimension(320, 180));
        card.setMinimumSize(new Dimension(320, 180));
        card.setMaximumSize(new Dimension(320, 180));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblTitle.setForeground(TEXT);

        JTextArea taDesc = new JTextArea(description);
        taDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        taDesc.setForeground(MUTED);
        taDesc.setLineWrap(true);
        taDesc.setWrapStyleWord(true);
        taDesc.setEditable(false);
        taDesc.setFocusable(false);
        taDesc.setBackground(Color.WHITE);

        JButton btnAction = filledButton(buttonText, accentColor);
        btnAction.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAction.setPreferredSize(new Dimension(140, 34));
        btnAction.addActionListener(action);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 6, 0);
        card.add(lblTitle, gbc);

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 12, 0);
        card.add(taDesc, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(btnAction, gbc);

        return card;
    }

    private JPanel addBackButtonToPanel(JPanel panel) {
        BorderLayout layout = (BorderLayout) panel.getLayout();
        Component north = layout.getLayoutComponent(BorderLayout.NORTH);
        if (north instanceof JPanel) {
            JPanel header = (JPanel) north;
            header.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // tight header

            // Create horizontal row header container
            JPanel rowHeader = new JPanel(new BorderLayout());
            rowHeader.setBackground(BG);
            rowHeader.setBorder(new EmptyBorder(0, 0, 28, 0));

            // Align Title/Subtitle to the left
            rowHeader.add(header, BorderLayout.WEST);

            // Align Back button to the right (EAST)
            JButton btnBack = outlineButton("Back to Hub");
            btnBack.setPreferredSize(new Dimension(130, 36));
            btnBack.setMaximumSize(new Dimension(130, 36));
            btnBack.setForeground(MUTED);
            btnBack.setBorder(BorderFactory.createLineBorder(BORDER));
            btnBack.setFont(new Font("Segoe UI", Font.BOLD, 12));
            btnBack.addActionListener(e -> {
                internalSysLayout.show(internalSysPanel, "HUB");
                refreshAll();
            });

            JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            btnWrapper.setBackground(BG);
            btnWrapper.setBorder(new EmptyBorder(8, 0, 0, 0)); // Center vertically with heading text
            btnWrapper.add(btnBack);

            rowHeader.add(btnWrapper, BorderLayout.EAST);

            // Replace header component on the panel with our rowHeader
            panel.remove(header);
            panel.add(rowHeader, BorderLayout.NORTH);
            panel.revalidate();
            panel.repaint();
        }
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
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(Color.WHITE);
        logo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sub = new JLabel("MANAGEMENT PANEL");
        sub.setFont(new Font("Segoe UI", Font.BOLD, 10));
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
        menu.add(sidebarButton("Car List", "CAR_LIST"));
        menu.add(Box.createRigidArea(new Dimension(0, 5)));
        
        btnSidebarRentalDesk = sidebarButton("Rental Desk", "RENTAL_DESK");
        menu.add(btnSidebarRentalDesk);
        menu.add(Box.createRigidArea(new Dimension(0, 5)));
        menu.add(sidebarButton("System Management", "SYSTEM_MANAGEMENT"));
        menu.add(Box.createRigidArea(new Dimension(0, 5)));
        menu.add(sidebarButton("Reports", "REPORTS"));
        sidebar.add(menu, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        bottom.setBackground(DARK);
        bottom.setBorder(new EmptyBorder(18, 18, 18, 18));

        JLabel user = new JLabel(currentUser.getUsername());
        user.setFont(new Font("Segoe UI", Font.BOLD, 13));
        user.setForeground(Color.WHITE);
        user.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel role = new JLabel(currentUser.getRole());
        role.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        role.setForeground(new Color(180, 180, 185));
        role.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton logout = filledButton("Logout", DARK_ACTIVE);
        logout.setForeground(RED);
        logout.setMaximumSize(new Dimension(200, 34));

        logout.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mouseEntered(java.awt.event.MouseEvent e) {
                // Brighter red background when hovered to make it pop
                logout.setBackground(RED);
                logout.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                // Return to original styles when mouse leaves
                logout.setBackground(DARK_ACTIVE);
                logout.setForeground(RED);
            }
        });

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

        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
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
            button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                if (button != activeSidebarButton) {
                    button.setBackground(new Color(32, 32, 36));
                    button.setForeground(Color.WHITE);
                }
            }

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
                activeSidebarButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            }

            activeSidebarButton = button;
            activeSidebarButton.setBackground(DARK_ACTIVE);
            activeSidebarButton.setForeground(Color.WHITE);
            activeSidebarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

            if ("RENTAL_DESK".equals(screenName)) {
                resetRentalDeskToDefault();
            } else if ("SYSTEM_MANAGEMENT".equals(screenName)) {
                if (internalSysLayout != null && internalSysPanel != null) {
                    internalSysLayout.show(internalSysPanel, "HUB");
                }
            }

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
        recentTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        recentTitle.setForeground(TEXT);

        recentHeader.add(recentTitle, BorderLayout.WEST);
        recentPanel.add(recentHeader, BorderLayout.NORTH);

        dashboardRentalTableModel = new DefaultTableModel(
                new String[] { "Rental ID", "Car ID", "Brand", "Model", "Customer Name", "Days", "Total Price", "Rental Status" }, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        // Injected alternating zebra background + status badge highlighting for Recent Rentals Table
        JTable recentTable = new JTable(dashboardRentalTableModel) {
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    // Default base background style is white/neutral
                    c.setBackground(Color.WHITE);
                    
                    // Check Status column (Index 7: "Rental ID", "Car ID", "Brand", "Model", "Customer Name", "Days", "Total Price", "Rental Status")
                    Object val = getValueAt(row, 7);
                    if (val != null) {
                        String status = val.toString().toUpperCase();
                        if (status.contains("RETURNED") || status.contains("AVAILABLE")) {
                            c.setBackground(new java.awt.Color(209, 250, 229)); // Soft translucent Mint Green
                        } else if (status.contains("RENTED") || status.contains("ACTIVE")) {
                            c.setBackground(new java.awt.Color(254, 243, 199)); // Soft translucent Warning Amber / Pale Yellow
                        }
                    }
                }
                return c;
            }
        };
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
        inventoryTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        inventoryTitle.setForeground(TEXT);

        inventoryHeader.add(inventoryTitle, BorderLayout.WEST);
        inventoryPanel.add(inventoryHeader, BorderLayout.NORTH);

        dashboardInventoryTableModel = new DefaultTableModel(
                new String[] { "Status", "Count" }, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        // Injected alternating zebra background + status badge highlighting for Inventory Summary Table
        JTable inventoryTable = new JTable(dashboardInventoryTableModel) {
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    // Default base zebra striping style
                    c.setBackground(row % 2 == 0 ? BG : BORDER);
                    
                    // Check Status column (Index 0: "Status", "Count")
                    Object val = getValueAt(row, 0);
                    if (val != null) {
                        String status = val.toString().toUpperCase();
                        if (status.contains("AVAILABLE") || status.contains("RETURNED")) {
                            c.setBackground(new java.awt.Color(209, 250, 229)); // Soft translucent Mint Green
                        } else if (status.contains("RENTED")) {
                            c.setBackground(new java.awt.Color(254, 243, 199)); // Soft translucent Warning Amber
                        }
                    }
                }
                return c;
            }
        };
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

        // Define a dynamic accent color based on the card title
        Color cardAccent;
        if (title.equalsIgnoreCase("Total Cars")) {
            cardAccent = TEXT;      
        } else if (title.equalsIgnoreCase("Available Cars")) {
            cardAccent = GREEN;      
        } else if (title.equalsIgnoreCase("Rented Cars")) {
            cardAccent = AMBER;     
        } else if (title.equalsIgnoreCase("Total Rentals")) {
            cardAccent = PRIMARY;   
        } else {
            cardAccent = BORDER;    
        }

        // Set the layout border with a 4px premium top-accent highlight line
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(4, 1, 1, 1, cardAccent), // Top is thicker, others thin
                new EmptyBorder(16, 22, 18, 22)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel label = new JLabel(title);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(MUTED);

        JLabel value = new JLabel("0");
        value.setFont(new Font("Segoe UI", Font.BOLD, 30));
        value.setForeground(cardAccent); // Matches the big number directly with the accent line color for a pro finish

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
        container.setBackground(DARK_ACTIVE); // Upgraded from generic DARK_GRAY to premium system alloy
        container.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(DARK_ACTIVE); // Upgraded from generic DARK_GRAY to blend with container
        toolbar.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel leftTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftTools.setBackground(DARK_ACTIVE); // Upgraded from generic DARK_GRAY

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
        rightTools.setBackground(DARK_ACTIVE); // Upgraded from generic DARK_GRAY

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

        String[] cols = { "Car ID", "Brand", "Model", "Year", "Type", "Price / Day", "Car Status" };

        carTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        // Injected alternating zebra + dynamic status background loop into the car management table
        tblCars = new JTable(carTableModel) {
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(Color.WHITE);
                    
                    // Check Status column (Index 6: Last column)
                    Object val = getValueAt(row, 6);
                    if (val != null) {
                        String status = val.toString().toUpperCase();
                        if (status.contains("AVAILABLE")) {
                            c.setBackground(new java.awt.Color(209, 250, 229)); // Soft translucent Mint Green
                        } else if (status.contains("RENTED")) {
                            c.setBackground(new java.awt.Color(254, 243, 199)); // Soft translucent Warning Amber
                        }
                    }
                }
                return c;
            }
        };
        styleTable(tblCars);

        JScrollPane scrollPane = new JScrollPane(tblCars);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BG); 

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
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        // Injected alternating zebra + dynamic status background loop for Fleet Selection Table
        tblRentalCars = new JTable(rentalCarTableModel) {
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    // Default base zebra striping style
                    c.setBackground(row % 2 == 0 ? BG : BORDER);
                    
                    // Check Status column (Index 6)
                    Object val = getValueAt(row, 6);
                    if (val != null) {
                        String status = val.toString().toUpperCase();
                        if (status.contains("AVAILABLE")) {
                            c.setBackground(new java.awt.Color(209, 250, 229)); // Soft translucent Mint Green
                        } else if (status.contains("RENTED")) {
                            c.setBackground(new java.awt.Color(254, 243, 199)); // Soft translucent Warning Amber
                        }
                    }
                }
                return c;
            }
        };
        styleTable(tblRentalCars);

        split.add(sectionWithButton("Fleet Selection","Rent Selected Car",GREEN, tblRentalCars, e -> showRentCarDialog()));

        rentalTableModel = new DefaultTableModel(
                new String[] { "Rental ID", "Car ID", "Car Model", "Customer Name", "Phone","Days", "Total Price", "Status" },
                0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        // Injected alternating zebra + dynamic status background loop for Rental Records Table
        tblRentals = new JTable(rentalTableModel) {
            
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    // Default base zebra striping style
                    c.setBackground(row % 2 == 0 ? BG : BORDER);
                    
                    // Check Status column (Index 7)
                    Object val = getValueAt(row, 7);
                    if (val != null) {
                        String status = val.toString().toUpperCase();
                        if (status.contains("RETURNED")) {
                            c.setBackground(new java.awt.Color(209, 250, 229)); // Soft translucent Mint Green
                        } else if (status.contains("RENTED")) {
                            c.setBackground(new java.awt.Color(254, 243, 199)); // Soft translucent Warning Amber
                        }
                    }
                }
                return c;
            }
        };
        styleTable(tblRentals);

        split.add(sectionWithButton("Rental Records","Return Selected Car",FUCHSIA,tblRentals,e -> returnSelectedCar()));

        panel.add(split, BorderLayout.CENTER);

        return panel;
    }
    private JPanel createUserManagementPanel() {
        JPanel panel = pagePanel("User Management", "Create and manage staff users for the system.");

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(DARK_ACTIVE);
        container.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(DARK_ACTIVE);
        toolbar.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel leftTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftTools.setBackground(DARK_ACTIVE);

        txtSearchUser = field();
        txtSearchUser.setPreferredSize(new Dimension(230, 36));

        JButton search = filledButton("Search", PRIMARY);
        search.setPreferredSize(new Dimension(105, 36));
        search.addActionListener(e -> refreshUserTable());

        JButton showAll = outlineButton("Show All");
        showAll.setPreferredSize(new Dimension(105, 36));
        showAll.addActionListener(e -> {
            txtSearchUser.setText("");
            refreshUserTable();
        });

        leftTools.add(txtSearchUser);
        leftTools.add(search);
        leftTools.add(showAll);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightButtons.setBackground(DARK_ACTIVE);

        JButton addStaff = filledButton("Create Staff", PRIMARY);
        addStaff.setPreferredSize(new Dimension(120, 36));
        addStaff.addActionListener(e -> showAddStaffDialog());

        JButton toggleStatus = filledButton("Toggle Status", AMBER);
        toggleStatus.setPreferredSize(new Dimension(125, 36));
        toggleStatus.addActionListener(e -> toggleSelectedUserStatus());

        JButton viewPassword = filledButton("View Password", GREEN);
        viewPassword.setPreferredSize(new Dimension(125, 36));
        viewPassword.addActionListener(e -> viewSelectedUserPassword());

        rightButtons.add(addStaff);
        rightButtons.add(toggleStatus);
        rightButtons.add(viewPassword);

        toolbar.add(leftTools, BorderLayout.WEST);
        toolbar.add(rightButtons, BorderLayout.EAST);

        container.add(toolbar, BorderLayout.NORTH);

        userTableModel = new DefaultTableModel(new String[] { "Username", "Role", "Status", "Password" }, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        // Alternating zebra + dynamic status background loop for Staff Users Table
        tblUsers = new JTable(userTableModel) {
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(Color.WHITE);
                    
                    // Check Status column (Index 2)
                    Object val = getValueAt(row, 2);
                    if (val != null) {
                        String status = val.toString().toUpperCase();
                        if (status.contains("ACTIVE")) {
                            c.setBackground(new java.awt.Color(209, 250, 229)); // Soft translucent Mint Green
                        } else if (status.contains("DEACTIVATED")) {
                            c.setBackground(new java.awt.Color(254, 226, 226)); // Soft translucent Warning Red
                        }
                    }
                }
                return c;
            }
        };
        
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
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(TEXT);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel desc = new JLabel("Export the current car inventory summary into a text report file.");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
        fileLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        fileLabel.setForeground(MUTED);
        fileLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel filePath = new JLabel("reports/driveflow_report.txt");
        filePath.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        filePath.setForeground(TEXT);
        filePath.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoBox.add(fileLabel);
        infoBox.add(Box.createRigidArea(new Dimension(0, 6)));
        infoBox.add(filePath);

        JButton export = filledButton("Export Report", AMBER);
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
        t.setFont(new Font("Segoe UI", Font.BOLD, 24));
        t.setForeground(TEXT);
        t.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Segoe UI", Font.BOLD, 10));
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
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
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
        refreshCustomerTable();
        refreshRentalRecordTable();
        refreshCarListTable();
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

            java.util.List<Rental> rentals = system.getAllRentals();
            int count = 0;

            for (int i = rentals.size() - 1; i >= 0; i--) {
                if (count >= 5)
                    break;

                Rental rental = rentals.get(i);
                dashboardRentalTableModel.addRow(new Object[] {
                        rental.getRentalId(),
                        rental.getCar().getCarId(),
                        rental.getCar().getBrand(),
                        rental.getCar().getModel(),
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
            String query = (txtSearchRentalCar != null) ? txtSearchRentalCar.getText().trim() : "";
            java.util.ArrayList<Car> carsToDisplay = query.isEmpty() ? system.getAllCars() : system.searchCars(query);
            for (Car car : carsToDisplay) {
                if (car.getStatus() != null && car.getStatus().trim().equalsIgnoreCase("AVAILABLE")) {
                    rentalCarTableModel.addRow(new Object[] {
                            car.getCarId(),
                            car.getBrand(),
                            car.getModel(),
                            car.getYear(),
                            car.getType(),
                            String.format("RM%.2f", car.getPricePerDay())
                    });
                }
            }
        }
        if (rentalTableModel != null) {
            rentalTableModel.setRowCount(0);
            String query = (txtSearchActiveRental != null) ? txtSearchActiveRental.getText().trim().toLowerCase() : "";
            for (Rental rental : system.getAllRentals()) {
                if (rental.getStatus() != null && rental.getStatus().trim().equalsIgnoreCase("ACTIVE")) {
                    boolean matches = query.isEmpty()
                            || rental.getRentalId().toLowerCase().contains(query)
                            || rental.getCustomer().getName().toLowerCase().contains(query)
                            || rental.getCustomer().getPhone().toLowerCase().contains(query)
                            || rental.getCar().getCarId().toLowerCase().contains(query)
                            || rental.getCar().getBrand().toLowerCase().contains(query)
                            || rental.getCar().getModel().toLowerCase().contains(query);
                    if (matches) {
                        rentalTableModel.addRow(new Object[] {
                                rental.getRentalId(),
                                rental.getCar().getCarId(),
                                rental.getCustomer().getName(),
                                rental.getCustomer().getPhone(),
                                rental.getRentalStartDate(),
                                rental.getExpectedReturnDate(),
                                String.format("RM%.2f", rental.getTotalPrice())
                        });
                    }
                }
            }
        }
    }

    private void refreshUserTable() {
        if (userTableModel == null)
            return;
        userTableModel.setRowCount(0);
        String query = (txtSearchUser != null) ? txtSearchUser.getText().trim().toLowerCase() : "";
        for (User user : system.getStaffUsers()) {
            boolean matches = query.isEmpty()
                    || user.getUsername().toLowerCase().contains(query)
                    || user.getRole().toLowerCase().contains(query)
                    || user.getStatus().toLowerCase().contains(query);
            if (matches) {
                userTableModel.addRow(new Object[] { user.getUsername(), user.getRole(), user.getStatus(), "••••••••" });
            }
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

                if (containsPipeSymbol(brandText, modelText, typeText)) {
                    JOptionPane.showMessageDialog(this, "Input cannot contain the pipe symbol '|'.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
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
        java.time.format.DateTimeFormatter dtf = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy");
        JTextField startDate = new JTextField(java.time.LocalDate.now().format(dtf));
        JPanel form = formPanel(new String[] { "Customer Name", "Phone", "Rental Days", "Start Date (dd-MM-yyyy)" },
                new JComponent[] { name, phone, days, startDate });
        int result = JOptionPane.showConfirmDialog(this, form, "Rent Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String custName = name.getText().trim();
                String custPhone = phone.getText().trim();
                String dateStr = startDate.getText().trim();
                
                if (containsPipeSymbol(custName, custPhone, dateStr)) {
                    JOptionPane.showMessageDialog(this, "Input cannot contain the pipe symbol '|'.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    java.time.LocalDate.parse(dateStr, dtf);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Please enter start date in dd-MM-yyyy format.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int rentalDays = Integer.parseInt(days.getText().trim());
                Rental rental = system.rentCar(carId, custName, custPhone, rentalDays, dateStr);
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
            String userVal = username.getText().trim();
            String passVal = new String(password.getPassword());
            
            if (containsPipeSymbol(userVal, passVal)) {
                JOptionPane.showMessageDialog(this, "Input cannot contain the pipe symbol '|'.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            boolean success = system.addStaff(userVal, passVal);
            JOptionPane.showMessageDialog(this, success ? "Staff created." : "Failed. Username may already exist.");
            refreshAll();
        }
    }

    private void toggleSelectedUserStatus() {
        int row = tblUsers.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a staff user to toggle status.");
            return;
        }
        String username = tblUsers.getValueAt(row, 0).toString();
        boolean success = system.toggleUserStatus(username);
        if (success) {
            JOptionPane.showMessageDialog(this, "User status toggled successfully.");
            refreshAll();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to toggle status. Main Admin cannot be deactivated.");
        }
    }

    private void viewSelectedUserPassword() {
        int row = tblUsers.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a staff user to view password.");
            return;
        }
        String username = tblUsers.getValueAt(row, 0).toString();
        for (User user : system.getStaffUsers()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                JOptionPane.showMessageDialog(this, "Password untuk staff '" + username + "' ialah: " + user.getPassword(), "Staff Password Details", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }

    private void exportReport() {
        boolean success = system.exportInventoryReport();
        JOptionPane.showMessageDialog(this,
                success ? "Report exported to reports/driveflow_report.txt" : "Export failed.");
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
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
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
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(BORDER),
                new EmptyBorder(5, 8, 5, 8)));
        field.setMaximumSize(new Dimension(350, 32));
        field.setPreferredSize(new Dimension(200, 32));
        field.setBackground(Color.WHITE);
        field.setForeground(TEXT);
    }

    private JButton filledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
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
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
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
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));

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
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setFont(new Font("Segoe UI", Font.BOLD, 12));
                label.setForeground(MUTED);
                label.setBackground(Color.WHITE);
                label.setHorizontalAlignment(SwingConstants.LEFT);
                label.setBorder(new EmptyBorder(0, 16, 8, 0));

                return label;
            }
        };

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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

    private JPanel createCustomerManagementPanel() {
        JPanel panel = pagePanel("Customer Management", "View, search and display customer records.");

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(DARK_ACTIVE);
        container.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(DARK_ACTIVE);
        toolbar.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel leftTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftTools.setBackground(DARK_ACTIVE);

        txtSearchCustomer = field();
        txtSearchCustomer.setPreferredSize(new Dimension(230, 36));

        JButton search = filledButton("Search", PRIMARY);
        search.setPreferredSize(new Dimension(105, 36));

        JButton showAll = outlineButton("Show All");
        showAll.setPreferredSize(new Dimension(105, 36));

        search.addActionListener(e -> performCustomerSearch());
        showAll.addActionListener(e -> {
            txtSearchCustomer.setText("");
            refreshCustomerTable();
        });

        leftTools.add(txtSearchCustomer);
        leftTools.add(search);
        leftTools.add(showAll);

        toolbar.add(leftTools, BorderLayout.WEST);
        container.add(toolbar, BorderLayout.NORTH);

        String[] cols = { "Customer ID", "Name", "Phone" };

        customerTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblCustomers = new JTable(customerTableModel) {
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        };
        styleTable(tblCustomers);

        JScrollPane scrollPane = new JScrollPane(tblCustomers);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BG);

        container.add(scrollPane, BorderLayout.CENTER);

        body.add(container, BorderLayout.CENTER);
        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    private void refreshCustomerTable() {
        if (customerTableModel == null)
            return;
        customerTableModel.setRowCount(0);
        java.util.List<Customer> list = system.getAllCustomers();
        for (int i = list.size() - 1; i >= 0; i--) {
            Customer customer = list.get(i);
            customerTableModel.addRow(new Object[] { customer.getCustomerId(), customer.getName(), customer.getPhone() });
        }
    }

    private void performCustomerSearch() {
        if (customerTableModel == null)
            return;
        customerTableModel.setRowCount(0);
        java.util.List<Customer> list = system.searchCustomers(txtSearchCustomer.getText());
        for (int i = list.size() - 1; i >= 0; i--) {
            Customer customer = list.get(i);
            customerTableModel.addRow(new Object[] { customer.getCustomerId(), customer.getName(), customer.getPhone() });
        }
    }

    private JPanel createRentalRecordsManagementPanel() {
        JPanel panel = pagePanel("Rental Records", "View, search and display rental history.");

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(DARK_ACTIVE);
        container.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(DARK_ACTIVE);
        toolbar.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel leftTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftTools.setBackground(DARK_ACTIVE);

        txtSearchRentalRecord = field();
        txtSearchRentalRecord.setPreferredSize(new Dimension(230, 36));

        JButton search = filledButton("Search", PRIMARY);
        search.setPreferredSize(new Dimension(105, 36));

        JButton showAll = outlineButton("Show All");
        showAll.setPreferredSize(new Dimension(105, 36));

        search.addActionListener(e -> refreshRentalRecordTable());
        showAll.addActionListener(e -> {
            txtSearchRentalRecord.setText("");
            refreshRentalRecordTable();
        });

        leftTools.add(txtSearchRentalRecord);
        leftTools.add(search);
        leftTools.add(showAll);

        toolbar.add(leftTools, BorderLayout.WEST);
        container.add(toolbar, BorderLayout.NORTH);

        String[] cols = { "Rental ID", "Car ID", "Customer", "Phone", "Start Date", "Expected Return", "Total Price", "Rental Status" };

        rentalRecordTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblRentalRecords = new JTable(rentalRecordTableModel) {
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    Object val = getValueAt(row, 7);
                    if (val != null) {
                        String status = val.toString().trim().toUpperCase();
                        if (status.contains("RETURNED")) {
                            c.setBackground(new java.awt.Color(209, 250, 229)); // Soft translucent Mint Green
                        } else if (status.contains("ACTIVE")) {
                            c.setBackground(new java.awt.Color(254, 243, 199)); // Soft translucent Warning Amber
                        } else {
                            c.setBackground(row % 2 == 0 ? BG : BORDER);
                        }
                    } else {
                        c.setBackground(row % 2 == 0 ? BG : BORDER);
                    }
                }
                return c;
            }
        };
        styleTable(tblRentalRecords);

        JScrollPane scrollPane = new JScrollPane(tblRentalRecords);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BG);

        container.add(scrollPane, BorderLayout.CENTER);

        body.add(container, BorderLayout.CENTER);
        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    private void refreshRentalRecordTable() {
        if (rentalRecordTableModel == null)
            return;
        rentalRecordTableModel.setRowCount(0);
        String query = (txtSearchRentalRecord != null) ? txtSearchRentalRecord.getText().trim().toLowerCase() : "";
        java.util.List<Rental> rentals = system.getAllRentals();
        for (int i = rentals.size() - 1; i >= 0; i--) {
            Rental rental = rentals.get(i);
            boolean matches = query.isEmpty()
                    || rental.getRentalId().toLowerCase().contains(query)
                    || rental.getCar().getCarId().toLowerCase().contains(query)
                    || rental.getCustomer().getName().toLowerCase().contains(query)
                    || rental.getCustomer().getPhone().toLowerCase().contains(query)
                    || (rental.getRentalStartDate() != null && rental.getRentalStartDate().toLowerCase().contains(query))
                    || (rental.getExpectedReturnDate() != null && rental.getExpectedReturnDate().toLowerCase().contains(query))
                    || rental.getStatus().toLowerCase().contains(query);
            if (matches) {
                rentalRecordTableModel.addRow(new Object[] {
                        rental.getRentalId(),
                        rental.getCar().getCarId(),
                        rental.getCustomer().getName(),
                        rental.getCustomer().getPhone(),
                        rental.getRentalStartDate(),
                        rental.getExpectedReturnDate(),
                        String.format("RM%.2f", rental.getTotalPrice()),
                        rental.getStatus()
                });
            }
        }
    }

    private JPanel createCarListPanel() {
        JPanel panel = pagePanel("Car List", "View and select cars for rental.");

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(DARK_ACTIVE);
        container.setBorder(BorderFactory.createLineBorder(BORDER));

        JPanel toolbar = new JPanel(new BorderLayout());
        toolbar.setBackground(DARK_ACTIVE);
        toolbar.setBorder(new EmptyBorder(16, 16, 16, 16));

        JPanel leftTools = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftTools.setBackground(DARK_ACTIVE);

        txtSearchCarList = field();
        txtSearchCarList.setPreferredSize(new Dimension(230, 36));

        JButton search = filledButton("Search", PRIMARY);
        search.setPreferredSize(new Dimension(105, 36));

        JButton showAll = outlineButton("Show All");
        showAll.setPreferredSize(new Dimension(105, 36));

        search.addActionListener(e -> refreshCarListTable());
        showAll.addActionListener(e -> {
            txtSearchCarList.setText("");
            refreshCarListTable();
        });

        leftTools.add(txtSearchCarList);
        leftTools.add(search);
        leftTools.add(showAll);

        JPanel rightTools = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightTools.setBackground(DARK_ACTIVE);

        JButton btnSelectCar = filledButton("Select Car", GREEN);
        btnSelectCar.setPreferredSize(new Dimension(150, 36));
        btnSelectCar.addActionListener(e -> handleSelectCar());
        rightTools.add(btnSelectCar);

        toolbar.add(leftTools, BorderLayout.WEST);
        toolbar.add(rightTools, BorderLayout.EAST);
        container.add(toolbar, BorderLayout.NORTH);

        String[] cols = { "Car ID", "Brand", "Model", "Year", "Type", "Price / Day", "Car Status" };

        carListTableModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tblCarList = new JTable(carListTableModel) {
            public java.awt.Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                java.awt.Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(Color.WHITE);
                    Object val = getValueAt(row, 6);
                    if (val != null) {
                        String status = val.toString().trim().toUpperCase();
                        if (status.contains("AVAILABLE")) {
                            c.setBackground(new java.awt.Color(209, 250, 229)); // Soft translucent Mint Green
                        } else if (status.contains("RENTED")) {
                            c.setBackground(new java.awt.Color(254, 243, 199)); // Soft translucent Warning Amber
                        }
                    }
                }
                return c;
            }
        };
        styleTable(tblCarList);

        JScrollPane scrollPane = new JScrollPane(tblCarList);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(BG);

        container.add(scrollPane, BorderLayout.CENTER);

        body.add(container, BorderLayout.CENTER);
        panel.add(body, BorderLayout.CENTER);

        return panel;
    }

    private void handleSelectCar() {
        int row = tblCarList.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Please select a car.");
            return;
        }
        String carId = tblCarList.getValueAt(row, 0).toString();
        String status = tblCarList.getValueAt(row, 6).toString();

        if (status.trim().equalsIgnoreCase("RENTED")) {
            JOptionPane.showMessageDialog(this, "This car is currently rented and cannot be selected for rental.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Direct sidebar button highlight update
        if (btnSidebarRentalDesk != null) {
            if (activeSidebarButton != null) {
                activeSidebarButton.setBackground(DARK);
                activeSidebarButton.setForeground(new Color(190, 190, 195));
                activeSidebarButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            }
            activeSidebarButton = btnSidebarRentalDesk;
            activeSidebarButton.setBackground(DARK_ACTIVE);
            activeSidebarButton.setForeground(Color.WHITE);
            activeSidebarButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        }

        // Navigate
        contentLayout.show(contentPanel, "RENTAL_DESK");
        refreshAll();

        // Highlight selected car in Rent Car table
        if (tblRentalCars != null) {
            for (int i = 0; i < tblRentalCars.getRowCount(); i++) {
                if (tblRentalCars.getValueAt(i, 0).toString().equalsIgnoreCase(carId)) {
                    tblRentalCars.setRowSelectionInterval(i, i);
                    tblRentalCars.scrollRectToVisible(tblRentalCars.getCellRect(i, 0, true));
                    break;
                }
            }
        }
    }

    private void refreshCarListTable() {
        if (carListTableModel == null)
            return;
        carListTableModel.setRowCount(0);
        String query = (txtSearchCarList != null) ? txtSearchCarList.getText().trim().toLowerCase() : "";
        for (Car car : system.getAllCars()) {
            boolean matches = query.isEmpty()
                    || car.getCarId().toLowerCase().contains(query)
                    || car.getBrand().toLowerCase().contains(query)
                    || car.getModel().toLowerCase().contains(query)
                    || car.getType().toLowerCase().contains(query)
                    || car.getStatus().toLowerCase().contains(query);
            if (matches) {
                carListTableModel.addRow(new Object[] {
                        car.getCarId(),
                        car.getBrand(),
                        car.getModel(),
                        car.getYear(),
                        car.getType(),
                        String.format("RM%.2f", car.getPricePerDay()),
                        car.getStatus()
                });
            }
        }
    }

    private void resetRentalDeskToDefault() {
        if (internalRentLayout != null && internalRentPanel != null) {
            internalRentLayout.show(internalRentPanel, "RENT");
        }
        if (btnToggleRentTab != null && btnToggleReturnTab != null) {
            btnToggleRentTab.setBackground(PRIMARY);
            btnToggleRentTab.setForeground(Color.WHITE);
            btnToggleRentTab.setOpaque(true);
            btnToggleRentTab.setContentAreaFilled(true);
            btnToggleRentTab.setBorderPainted(false);

            btnToggleReturnTab.setBackground(Color.WHITE);
            btnToggleReturnTab.setForeground(PRIMARY);
            btnToggleReturnTab.setOpaque(true);
            btnToggleReturnTab.setContentAreaFilled(true);
            btnToggleReturnTab.setBorderPainted(true);
            btnToggleReturnTab.setBorder(BorderFactory.createLineBorder(BORDER));
        }
    }
}
