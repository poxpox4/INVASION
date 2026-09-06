import javax.swing.JFrame;
// หน้าต่างโปรแกรม
public class GameFrame extends JFrame {

    public GameFrame() {

        setTitle("INVASION");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //ปิดโปรแกรม

        setResizable(false); //ลากขยายหน้าจอ

        GamePanel gamePanel = new GamePanel(); //พื้นที่วาดเกม

        add(gamePanel);

        pack(); //หน้าจอปรับขนาดตาม Component ข้างใน

        setLocationRelativeTo(null); //ทำให้หน้าจอเกมอยู่ตรงกลางหน้าจอ

        setVisible(true);//สั่งให้หน้าจอแสดงออกมา
    }

}