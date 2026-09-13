import javax.swing.*;
import java.awt.*;

public class ShipSelection extends JDialog {

    private Ship selectedShip = null;
    private boolean confirmed = false;

    private JLabel grayLabel;
    private JLabel redLabel;

    public ShipSelection() {

        setTitle("SELECT SHIP");
        setSize(600, 500);
        setResizable(false);
        setLocationRelativeTo(null);

        // ปิดหน้าต่างด้วย X
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel jtitle = new JPanel();
        JLabel title = new JLabel(
                "SELECT YOUR SHIP",
                SwingConstants.CENTER
        );
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        jtitle.add(title);
        jtitle.setBackground(Color.BLACK);
        // Panel สำหรับยาน
        JPanel shipPanel = new JPanel();
        shipPanel.setLayout(new GridLayout(1, 2, 30, 0));

        // รูป Gray Ship
        ImageIcon grayIcon = new ImageIcon("images/grayship.png");

        Image grayImage = grayIcon.getImage().getScaledInstance(
                180,
                170,
                Image.SCALE_SMOOTH
        );

        grayLabel = new JLabel(
                new ImageIcon(grayImage),
                SwingConstants.CENTER
        );

        // รูป Red Ship
        ImageIcon redIcon = new ImageIcon("images/redship.png");

        Image redImage = redIcon.getImage().getScaledInstance(
                180,
                170,
                Image.SCALE_SMOOTH
        );

        redLabel = new JLabel(
                new ImageIcon(redImage),
                SwingConstants.CENTER
        );

        // เพิ่มรูปลง Panel
        shipPanel.add(redLabel);
        shipPanel.add(grayLabel);
        shipPanel.setBackground(Color.BLACK);

        // ปุ่ม START
        JButton startButton = new JButton("START");
        startButton.setForeground(Color.WHITE);

        startButton.setFont(
                new Font("Arial", Font.BOLD, 18)
        );

        startButton.setEnabled(false);

        // คลิก Gray Ship
        grayLabel.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                selectedShip = new GrayShip();

                grayLabel.setBorder(
                        BorderFactory.createLineBorder(
                                Color.GRAY,
                                3
                        )
                );

                redLabel.setBorder(null);

                startButton.setEnabled(true);
            }
        });

        // คลิก Red Ship
        redLabel.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                selectedShip = new RedShip();

                redLabel.setBorder(
                        BorderFactory.createLineBorder(
                                Color.RED,
                                3
                        )
                );

                grayLabel.setBorder(null);

                startButton.setEnabled(true);
            }
        });

        // กด START
        startButton.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        // Layout
        JPanel centerPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        centerPanel.add(shipPanel, BorderLayout.CENTER);
        startButton.setBackground(Color.BLACK);
        add(jtitle, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.SOUTH);

        // ทำให้หน้าต่างเลือกยานอยู่ด้านหน้า
        setModal(true);
    }

    // เรียกใช้จาก Main / Restart
    public static Ship selectShip() {
        ShipSelection selection = new ShipSelection();

        selection.setVisible(true);

        if (!selection.confirmed) {
            return null;
        }
        return selection.selectedShip;

    }
}