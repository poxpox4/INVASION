import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame frame = new JFrame("SPACE INVASION");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setContentPane(
            new Home()
        );

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setResizable(false);

        frame.setVisible(true);
    }
}