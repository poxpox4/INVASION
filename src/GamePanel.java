import javax.swing.AbstractAction;
import javax.swing.JPanel; //พื้นที่สำหรับวาดเกม
import javax.swing.KeyStroke; //แทนการกดปุ่มคีย์บอร์ด
import java.awt.event.ActionEvent; //รับevent ตอนactionทำงาน
import java.awt.*; //นำ Class ต่าง ๆ เกี่ยวกับกราฟิกมาใช้

public class GamePanel extends JPanel {
    //ตน.
    int playerX = 375;
    int playerY = 600; //ค่ายิ่งเยอะยิ่งอยู่ข้างล่าง
    //size
    int playerWidth = 50;
    int playerHeight = 50;

    int playerSpeed = 10;

    int panelW = 800,panelH = 750;

    public GamePanel() {

        setPreferredSize(new Dimension(panelW, panelH)); //กำหนดขนาดพื้นที่เกม
        setBackground(Color.BLACK);

        setupKeyBindings();
    }

    @Override
    protected void paintComponent(Graphics g) { //paintComponent วาดสิ่งต่าง ๆ ลงบนหน้าจอ
        //Graphics g ปากกาวาด
        super.paintComponent(g); //สั่งให้ JPanel วาดพื้นหลังก่อน

        //ยานชั่วคราว
        g.setColor(Color.CYAN);

        g.fillRect(
                playerX,
                playerY,
                playerWidth,
                playerHeight
        );
    }

    private void setupKeyBindings() {
        //getInputMap().put()เมื่อกดปุ่ม ให้เรียก Action //.put()เพิ่มข้อมูลเข้าไปข้างใน
        getInputMap(WHEN_IN_FOCUSED_WINDOW)//KeyStroke.getKeyStroke("W") ตรวจปุ่มW
                .put(KeyStroke.getKeyStroke("W"), "moveUp");
        //getActionMap() ชื่อ Action → ให้ทำอะไร
        //.put("moveUp", ...) กำหนด Action ชื่อ "moveUp" ทำงานตามโค้ดที่อยู่ด้านหลัง ชื่อนี้ต้องตรงกับที่ใส่ใน InputMap
        getActionMap().put("moveUp", new AbstractAction() { //new AbstractAction() สร้าง Action ใหม่ขึ้นมา
            @Override
            public void actionPerformed(ActionEvent e) { //ทำงานเมื่อ Action ถูกเรียก
                playerY -= playerSpeed;
                if(playerY<0) playerY=0; //ห้านเกินด้านบน
                repaint();//วาดใหม่
            }
        });


        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("S"), "moveDown");

        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerY += playerSpeed;
                if(playerY+playerHeight>panelH) playerY=panelH-playerHeight; //ห้ามเกินด้านล่าง
                repaint();
            }
        });


        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("A"), "moveLeft");

        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerX -= playerSpeed;
                if(playerX<0)playerX=0; //ห้ามเกินด้านซ้าย
                repaint();
            }
        });


        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("D"), "moveRight");

        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerX += playerSpeed;
                if(playerX+playerWidth>panelW)playerX=panelW-playerWidth;
                repaint();
            }
        });
    }
}