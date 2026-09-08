import javax.swing.*;//พื้นที่สำหรับวาดเกม
import java.awt.event.ActionEvent; //รับevent ตอนactionทำงาน
import java.awt.*; //นำ Class ต่าง ๆ เกี่ยวกับกราฟิกมาใช้
import java.util.ArrayList;
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
    long lastShotTime = 0; //เก็บเวลาที่เรายิงครั้งล่าสุด
    int fireDelay = 150; //ต้องรอ 150 มิลลิวินาทีก่อนยิงนัดต่อไป
    // Bullet bullet = new Bullet(); 
    Timer timer;
    //ตัวแปรเก็บสถานะปุ่ม
    boolean upPressed = false;
    boolean downPressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean spacefirepressed = false;
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
                .put(KeyStroke.getKeyStroke("pressed W"), "moveUp");
        //getActionMap() ชื่อ Action → ให้ทำอะไร
        //.put("moveUp", ...) กำหนด Action ชื่อ "moveUp" ทำงานตามโค้ดที่อยู่ด้านหลัง ชื่อนี้ต้องตรงกับที่ใส่ใน InputMap
        getActionMap().put("moveUp", new AbstractAction() { //new AbstractAction() สร้าง Action ใหม่ขึ้นมา
            @Override
            public void actionPerformed(ActionEvent e) { //ทำงานเมื่อ Action ถูกเรียก
                // playerY -= playerSpeed;
                // if(playerY<0) playerY=0; //ห้านเกินด้านบน
                // repaint();//วาดใหม่
                upPressed = true;
            }
        });
        //ตอนปล่อยปุ่ม
        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("released W"), "stopUp");

        getActionMap().put("stopUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upPressed = false;
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed S"), "moveDown");

        getActionMap().put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // playerY += playerSpeed;
                // if(playerY+playerHeight>panelH) playerY=panelH-playerHeight; //ห้ามเกินด้านล่าง
                // repaint();
                downPressed = true;
            }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("released S"), "stopDown");

        getActionMap().put("stopDown", new AbstractAction() {
            @Override 
            public void actionPerformed(ActionEvent e){
                downPressed = false;
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed A"), "moveLeft");

        getActionMap().put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // playerX -= playerSpeed;
                // if(playerX<0)playerX=0; //ห้ามเกินด้านซ้าย
                // repaint();
                leftPressed = true;
            }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("released A"), "stopLeft");

        getActionMap().put("stopLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftPressed = false;
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed D"), "moveRight");

        getActionMap().put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // playerX += playerSpeed;
                // if(playerX+playerWidth>panelW)playerX=panelW-playerWidth;
                // repaint();
                rightPressed = true;
            }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("released D"), "stopRight");

        getActionMap().put("stopRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightPressed = false;
            }
        });

        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed SPACE"),"shoot");
        getActionMap().put("shoot", new AbstractAction() {
            @Override 
            public void actionPerformed(ActionEvent e){
                spacefirepressed = true;
                // shoot();
                // repaint();
            }
        });
        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("released SPACE"), "stopSPACEfire");

        getActionMap().put("stopSPACEfire", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                spacefirepressed = false;
            }
        });
    }
    //method ยิง
    private void shoot() {
        long currentTime = System.currentTimeMillis();//เวลาปัจจุบันของเครื่อง
        if (currentTime - lastShotTime < fireDelay) { //ตรวจเวลาระหว่างการยิง
            return;
        }
        lastShotTime = currentTime;
        Bullet bullet = new Bullet();
        int bulletX = playerX + playerWidth / 2 - bullet.width / 2; //กระสุนออกกลางยาน
        int bulletY = playerY;
        bullets.add(new Bullet(bulletX, bulletY));
    }
    //method update
    private void updateGame() {
        if (upPressed) { //w
            playerY -= playerSpeed;
            if(playerY<0) playerY=0;
        }
        if (downPressed) { //s
            playerY += playerSpeed;
            if(playerY+playerHeight>panelH) playerY=panelH-playerHeight;
        }
        if (leftPressed) { //a
            playerX -= playerSpeed;
            if(playerX<0)playerX=0;
        }
        if (rightPressed) { //d
            playerX += playerSpeed;
            if(playerX+playerWidth>panelW)playerX=panelW-playerWidth;
        }
        if(spacefirepressed){ //spacefire
            shoot();
        }
        for (int i = bullets.size()-1;i>=0;i--) { //bullets
            Bullet bullet = bullets.get(i);
            bullet.move();
            if(bullet.y+bullet.height<0) bullets.remove(i); //ลบกระสุน
        }
        
    }
}