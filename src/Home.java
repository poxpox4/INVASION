import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {
    Image homeBackground;
    Image logoImage;
    Image shipImage;
    JButton startGameButton;
    JButton selectStageButton;
    JButton exitButton;
    int panelW = 800;
    int panelH = 750;

    public Home() {
        homeBackground = new ImageIcon("images/homebg.png").getImage();
        logoImage = new ImageIcon("images/logo.png").getImage();
        shipImage = new ImageIcon("images/homeship.png").getImage();
        setLayout(null);
        // ขนาดหน้า Home
        setPreferredSize(new Dimension(panelW, panelH));
        // START GAME
        startGameButton = createButton("START GAME", new Color(40, 220, 110)); 
        startGameButton.setBounds(300, 365, 200, 50); 
        add(startGameButton);
        // SELECT STAGE
        selectStageButton = createButton("STAGE SELECT", new Color(40, 170, 230)); 
        selectStageButton.setBounds(300, 425, 200, 50); 
        add(selectStageButton);
        // EXIT
        exitButton = createButton("EXIT", new Color(220, 65, 65)); 
        exitButton.setBounds(300, 485, 200, 50); 
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
    
    public JButton createButton(String text, Color borderColor) {
        JButton button = new JButton(text);

        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(5, 22, 45));
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(
            BorderFactory.createLineBorder(borderColor, 2)
        );
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(
            homeBackground,0,0,getWidth(),getHeight(),this
        );
        g.drawImage(logoImage, 175, 15, 460,100,this);
        g.drawImage(shipImage, 320, 140, 160,175,this);
    }
}