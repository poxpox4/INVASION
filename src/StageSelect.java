// import javax.swing.*;
// import java.awt.*;

// public class StageSelect extends JPanel {
//     JButton stage1Button;
//     JButton stage2Button;
//     JButton backButton;
//     int panelW = 800;
//     int panelH = 750;
//     public StageSelect() {
//         setLayout(null);
//         setPreferredSize(
//             new Dimension(panelW, panelH)
//         );
//         // STAGE 1
//         stage1Button = new JButton("STAGE 1");
//         stage1Button.setBounds(
//             300,
//             250,
//             200,
//             60
//         );
//         add(stage1Button);
//         // STAGE 2
//         stage2Button = new JButton("STAGE 2");
//         stage2Button.setBounds(
//             300,
//             330,
//             200,
//             60
//         );
//         add(stage2Button);
//         // BACK
//         backButton = new JButton("BACK");
//         backButton.setBounds(
//             300,
//             450,
//             200,
//             60
//         );
//         add(backButton);
//         // STAGE 1 ACTION
//         stage1Button.addActionListener(e -> {
//             startStage(1);
//         });
//         // STAGE 2 ACTION
//         stage2Button.addActionListener(e -> {
//             startStage(2);
//         });
//         // BACK ACTION
//         backButton.addActionListener(e -> {
//             JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
//             frame.setContentPane(
//                 new Home()
//             );
//             frame.pack();
//             frame.setLocationRelativeTo(null);
//             frame.revalidate();
//             frame.repaint();
//         });
//     }
//     private void startStage(int stage) {
//         Ship ship = ShipSelection.selectShip();
//         if (ship == null) {
//             return;
//         }
//         JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
//         GamePanel gamePanel = new GamePanel(ship);
//         // กำหนด Stage ที่ต้องการเล่น
//         if (stage == 2) {
//             gamePanel.startSelectedStage2();
//         }
//         frame.setContentPane(gamePanel);
//         frame.pack();
//         frame.setLocationRelativeTo(null);
//         frame.revalidate();
//         frame.repaint();
//     }

//     @Override
//     protected void paintComponent(Graphics g) {
//         super.paintComponent(g);
//         g.setColor(Color.BLACK);
//         g.fillRect(
//             0,
//             0,
//             panelW,
//             panelH
//         );
//         g.setColor(Color.WHITE);
//         g.setFont(
//             new Font("Arial", Font.BOLD, 40)
//         );
//         g.drawString(
//             "SELECT STAGE",
//             270,
//             150
//         );
//     }
// }

import javax.swing.*;
import java.awt.*;

public class StageSelect extends JPanel {

    JButton stage1Button;
    JButton stage2Button;
    JButton backButton;

    Image backgroundImage;
    Image logoImage;

    int panelW = 800;
    int panelH = 750;

    public StageSelect() {

        backgroundImage =
            new ImageIcon("images/homebg.png").getImage();

        logoImage =
            new ImageIcon("images/logo.png").getImage();

        setLayout(null);
        setPreferredSize(new Dimension(panelW, panelH));

        // STAGE 1
        stage1Button = createButton(
            "STAGE 1", new Color(40, 220, 110)
        );
        stage1Button.setBounds(300, 280, 200, 55);
        add(stage1Button);

        // STAGE 2
        stage2Button = createButton(
            "STAGE 2", new Color(40, 170, 230)
        );
        stage2Button.setBounds(300, 350, 200, 55);
        add(stage2Button);

        // BACK
        backButton = createButton(
            "BACK", new Color(220, 65, 65)
        );
        backButton.setBounds(300, 450, 200, 50);
        add(backButton);

        // STAGE 1 ACTION
        stage1Button.addActionListener(e -> {
            startStage(1);
        });

        // STAGE 2 ACTION
        stage2Button.addActionListener(e -> {
            startStage(2);
        });

        // BACK ACTION
        backButton.addActionListener(e -> {
            JFrame frame = (JFrame)
                SwingUtilities.getWindowAncestor(this);

            if (frame != null) {
                frame.setContentPane(new Home());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.revalidate();
                frame.repaint();
            }
        });
    }

    // สร้างปุ่มธีม Sci-fi
    private JButton createButton(
        String text,
        Color borderColor
    ) {
        JButton button = new JButton(text);

        button.setFont(
            new Font("SansSerif", Font.BOLD, 18)
        );
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(5, 22, 45));
        button.setOpaque(true);
        button.setFocusPainted(false);

        button.setBorder(
            BorderFactory.createLineBorder(
                borderColor, 2
            )
        );

        button.setCursor(
            new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    private void startStage(int stage) {

        Ship ship = ShipSelection.selectShip();

        if (ship == null) {
            return;
        }

        JFrame frame = (JFrame)
            SwingUtilities.getWindowAncestor(this);

        if (frame == null) {
            return;
        }

        GamePanel gamePanel = new GamePanel(ship);

        // กำหนด Stage ที่ต้องการเล่น
        if (stage == 2) {
            gamePanel.startSelectedStage2();
        }

        frame.setContentPane(gamePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.revalidate();
        frame.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.drawImage(
            backgroundImage,
            0, 0,
            getWidth(), getHeight(),
            this
        );

        // Logo
        g.drawImage(
            logoImage,
            170, 25,
            460, 100,
            this
        );


    }
}
