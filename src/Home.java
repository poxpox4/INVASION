import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {
    Image homeBackground;
    JButton startGameButton;
    JButton selectStageButton;
    JButton exitButton;
    int panelW = 800;
    int panelH = 750;

    public Home() {
        homeBackground = new ImageIcon("images/homebg.png").getImage();
        setLayout(null);
        // ขนาดหน้า Home
        setPreferredSize(new Dimension(panelW, panelH));
        // START GAME
        startGameButton = new JButton("START GAME");
        startGameButton.setBounds(
            300,
            350,
            200,
            60
        );
        add(startGameButton);
        // SELECT STAGE
        selectStageButton = new JButton("SELECT STAGE");
        selectStageButton.setBounds(
            300,
            430,
            200,
            60
        );
        add(selectStageButton);
        // EXIT
        exitButton = new JButton("EXIT");
        exitButton.setBounds(
            300,
            510,
            200,
            60
        );
        add(exitButton);
        // START GAME
        // เลือกยาน → Stage 1
        startGameButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(
                new StageSelect()
            );
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        });
        // SELECT STAGE ไปหน้าเลือก Stage
        selectStageButton.addActionListener(e -> {
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
            frame.setContentPane(
                new StageSelect()
            );
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        });
        // EXIT
        exitButton.addActionListener(e -> {

            System.exit(0);

        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
            homeBackground,
            0,
            0,
            panelW,
            panelH,
            this
        );
    }
}