import javax.swing.AbstractAction;
import javax.swing.JPanel; //พื้นที่สำหรับวาดเกม
import javax.swing.KeyStroke; //แทนการกดปุ่มคีย์บอร์ด
import java.awt.event.ActionEvent; //รับevent ตอนactionทำงาน
import java.awt.*; //นำ Class ต่าง ๆ เกี่ยวกับกราฟิกมาใช้
import java.util.ArrayList;
import javax.swing.Timer;

public class GamePanel extends JPanel {
    //ตน.
    int playerX = 375;
    int playerY = 600; //ค่ายิ่งเยอะยิ่งอยู่ข้างล่าง
    //size
    int playerWidth = 50;
    int playerHeight = 50;

    int playerSpeed = 10;

    int panelW = 800,panelH = 750;

    ArrayList<Bullet> bullets = new ArrayList<>();
    // Bullet bullet = new Bullet(); 
    Timer timer;
    
    public GamePanel() {

        setPreferredSize(new Dimension(panelW, panelH)); //กำหนดขนาดพื้นที่เกม
        setBackground(Color.BLACK);

        setupKeyBindings();
        //เกมจะ Update ประมาณ 60 FPS
        timer = new Timer(16, e -> {

            updateGame();

            repaint();

        });

        timer.start();
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
        //bullets
        g.setColor(Color.YELLOW);
        for(int i=0;i<bullets.size();i++){
            g.fillRect(
                bullets.get(i).x,
                bullets.get(i).y,
                bullets.get(i).width,
                bullets.get(i).height);
        }
    }
    //actions w,a,s,d
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

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("SPACE"),"shoot");
        getActionMap().put("shoot", new AbstractAction() {
            @Override 
            public void actionPerformed(ActionEvent e){
                shoot();
                repaint();
            }
        });
    }
    //method ยิง
    private void shoot() {
        Bullet bullet = new Bullet();
        int bulletX = playerX + playerWidth / 2 - bullet.width / 2; //กระสุนออกกลางยาน
        int bulletY = playerY;
        bullets.add(new Bullet(bulletX, bulletY));
    }
    //method update
    private void updateGame() {
        for (int i = bullets.size()-1;i>=0;i--) {
            Bullet bullet = bullets.get(i);
            bullet.move();
            if(bullet.y+bullet.height<0) bullets.remove(i); //ลบกระสุน
        }
    }
}