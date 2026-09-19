import javax.swing.*;//พื้นที่สำหรับวาดเกม
import java.awt.event.ActionEvent; //รับevent ตอนactionทำงาน
import java.awt.*; //นำ Class ต่าง ๆ เกี่ยวกับกราฟิกมาใช้
import java.util.ArrayList;
public class GamePanel extends JPanel {
    //select ship
    Ship playerShip;
    Image playerImage;
    //ตน.
    int playerX = 375;
    int playerY = 600; //ค่ายิ่งเยอะยิ่งอยู่ข้างล่างก
    //size
    int playerWidth = 60;
    int playerHeight = 60;
    int playerSpeed = 10;//speed
    //player hp
    int playerHP = 10;
    int playerMaxHP = 10;

    boolean gameOver = false;
    boolean gameWin = false;

    int panelW = 800,panelH = 750;

    ArrayList<Bullet> bullets = new ArrayList<>();//player bullet
    ArrayList<BossBullet> bossBullets = new ArrayList<>();//boss bullet
    long lastShotTime = 0; //เก็บเวลาที่เรายิงครั้งล่าสุด
    // int fireDelay = 150; //ต้องรอ 150 มิลลิวินาทีก่อนยิงนัดต่อไป
    // Bullet bullet = new Bullet(); 
    Timer timer;
    //ตัวแปรเก็บสถานะปุ่ม
    boolean upPressed = false;
    boolean downPressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;
    boolean spacefirepressed = false;
    //Enemy
    ArrayList<Enemy> enemies = new ArrayList<>();
    long lastEnemySpawnTime = 0;
    int enemySpawnDelay = 1000;
    int enemyKilled = 0;
    int enemyTarget = 7;
    Image Deputyboss1,Deputyboss2,enemyImage;
    //boss
    Image bossImage;
    boolean bossSpawned = false; 
    boolean bossWarning = false;
    long bossWarningTime = 0;
    int bossWarningDuration = 3000;
    Boss boss = null;
    DeputyBoss deputyBoss = null;
    boolean deputybossSpawned = false;
    //check stage
    int currentStage = 1;
    boolean stageClear = false;
    JButton restartStageButton;
    JButton stage2Button;

    public GamePanel(Ship playerShip) {
        this.playerShip = playerShip;
        if(playerShip==null)return ;
        if(playerShip instanceof RedShip){
            playerImage = new ImageIcon("images/redship.png").getImage();
        }
        else if(playerShip instanceof GrayShip){
            playerImage = new ImageIcon("images/grayship.png").getImage();
        }
        setLayout(null);
        setPreferredSize(new Dimension(panelW, panelH)); //กำหนดขนาดพื้นที่เกม
        setBackground(Color.BLACK);
        restartStageButton = new JButton("RESTART");
        stage2Button = new JButton("STAGE 2");

        restartStageButton.setBounds(300, 300, 200, 60);
        stage2Button.setBounds(300, 400, 200, 60);

        restartStageButton.setVisible(false);
        stage2Button.setVisible(false);

        add(restartStageButton);
        add(stage2Button);

        // setupKeyBindings();

        //เกมจะ Update ประมาณ 60 FPS
        timer = new Timer(16, e -> {
            updateGame();
            repaint();
        });
        
        restartStageButton.addActionListener(e -> {
            restartGame();
        });
        stage2Button.addActionListener(e -> {
            startStage2();
        });
        setupKeyBindings();
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) { //paintComponent วาดสิ่งต่าง ๆ ลงบนหน้าจอ
        //Graphics g ปากกาวาด
        super.paintComponent(g); //สั่งให้ JPanel วาดพื้นหลังก่อน

        //player ship
        g.drawImage(playerImage, playerX, playerY, playerWidth,playerHeight,this);
        //player bullets
        g.setColor(Color.YELLOW);
        for(int i=0;i<bullets.size();i++){
            g.fillRect(
                bullets.get(i).x,
                bullets.get(i).y,
                bullets.get(i).width,
                bullets.get(i).height);
        }
        //boss bullets
        g.setColor(Color.ORANGE);
        for(int i=0;i<bossBullets.size();i++){
            g.fillRect(
                bossBullets.get(i).x,
                bossBullets.get(i).y,
                bossBullets.get(i).width,
                bossBullets.get(i).height
            );
        }
        //Enemy
        g.setColor(Color.RED);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            g.drawImage(enemyImage, enemy.x, enemy.y, enemy.width,enemy.height,this);
        }
        // Deputy Boss
        if (deputyBoss != null) {

            g.drawImage(
                Deputyboss1,
                deputyBoss.x,
                deputyBoss.y,
                deputyBoss.width,
                deputyBoss.height,
                this
            );
            
            int barWidth = 300;
            int barHeight = 15;

            int barX = (panelW - barWidth) / 2;
            int barY = 55;
            if(boss==null){
                // HP Bar
                g.setColor(Color.DARK_GRAY);
                g.fillRect(barX, barY, barWidth, barHeight);

                int hpWidth =
                    (int)((double)deputyBoss.hp /
                    deputyBoss.maxHP * barWidth);

                g.setColor(Color.ORANGE);
                g.fillRect(barX, barY, hpWidth, barHeight);
                g.setColor(Color.WHITE);
                g.drawRect(barX, barY, barWidth, barHeight);

                g.setFont(new Font("Arial", Font.BOLD, 18));

                g.drawString(
                    "DEPUTY BOSS HP: "
                    + deputyBoss.hp + "/" + deputyBoss.maxHP,
                    barX+35 ,
                    barY - 2
                );

            }
        }
        // Boss
        if (boss != null) {
            int barWidth = 350,barHeight = 20;
            int barX = (panelW-barWidth)/2,barY = 55;
            //bg hp
            g.setColor(Color.DARK_GRAY);
            g.fillRect(barX, barY, barWidth, barHeight);
            //hpที่เหลือ
            int hpWidth = (int)((double)boss.hp/boss.maxHP*barWidth);
            g.setColor(Color.RED);
            g.fillRect(barX, barY, hpWidth, barHeight);
            //ขอบ
            g.setColor(Color.WHITE);
            g.drawRect(barX, barY, hpWidth, barHeight);

            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString(
                "BOSS HP: " + boss.hp +"/"+ boss.maxHP,
                barX+100,
                barY -2
            );
            g.drawImage(bossImage, boss.x, boss.y+50, boss.width,boss.height,this);
        }
        //text
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString(
            "ENEMIES DESTROYED: " + enemyKilled,
            20,
            30
        );
        g.drawString(
            "BOSS AT: "+enemyTarget + " KILLS",
            20,
            55
        );
        g.setColor(Color.WHITE);
        g.drawString(
            "HP: " + playerHP + "/" + playerMaxHP,
            650,
            30
        );
        if (bossWarning) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString(
                "WARNING! BOSS INCOMING!",
                130,
                300
            );
        }
        if(stageClear){
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("STAGE 1 CLEAR!", 250, 250);
        }
        if(gameWin){
            g.setColor(Color.GREEN);
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.drawString("VICTORY!", 320, 300);
            
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("PRESS R TO RESTART", 310, 380);
        }
        
        if(gameOver){
            g.setColor(Color.RED);
            g.setFont(new Font("Arial",Font.BOLD,40));
            g.drawString("DEFEATED", 300, 300);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("PRESS R TO RESTART", 298, 350);
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
        getInputMap(WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("pressed R"), "restart");

        getActionMap().put("restart", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameOver||gameWin) {
                    restartGame();
                }
            }
        });
        
    }
    //method ยิง for player
    private void shoot() {
        long currentTime = System.currentTimeMillis();//เวลาปัจจุบันของเครื่อง
        if (currentTime - lastShotTime < playerShip.getFireDelay()) { //ตรวจเวลาระหว่างการยิง
            return;
        }
        lastShotTime = currentTime;
        playerShip.shoot(playerX,playerY,playerWidth,bullets);
    }
    //method ยิง for boss
    private void bossShoot(){
        if(boss==null)return ;
        if(boss.canShoot()){
            if(boss instanceof BossStage1){
                ((BossStage1)boss).shoot(bossBullets);
            }
            else if(boss instanceof BossStage2){
                ((BossStage2)boss).shoot(bossBullets);
            }
        }
        
    }
    //collision ว่าชนหรือไม่ชน
    //bullet collision enemy
    private boolean isColliding(Bullet bullet, Enemy enemy) {
        Rectangle bulletRect = new Rectangle(
            bullet.x,
            bullet.y,
            bullet.width,
            bullet.height
        );

        Rectangle enemyRect = new Rectangle(
            enemy.x,
            enemy.y,
            enemy.width,
            enemy.height
        );

        return bulletRect.intersects(enemyRect);
    }
    //bullet collision boss
    private boolean isColliding(Bullet bullet, Boss boss) {
        Rectangle bulletRect = new Rectangle(
            bullet.x,
            bullet.y,
            bullet.width,
            bullet.height
        );

        Rectangle bossRect = new Rectangle(
            boss.x,
            boss.y+50,
            boss.width,
            boss.height
        );

        return bulletRect.intersects(bossRect);
    }
    //boss collision player
    private boolean isColliding(Boss boss) {
        Rectangle playerRect = new Rectangle(
            playerX,
            playerY,
            playerWidth,
            playerHeight
        );

        Rectangle bossRect = new Rectangle(
            boss.x,
            boss.y + 50,
            boss.width,
            boss.height
        );

        return playerRect.intersects(bossRect);
    }
    //enemy collision player
    private boolean isColliding(Enemy enemy) {
        Rectangle enemyRect = new Rectangle(
            enemy.x,
            enemy.y,
            enemy.width,
            enemy.height
        );

        Rectangle playerRect = new Rectangle(
            playerX,
            playerY,
            playerWidth,
            playerHeight
        );

        return enemyRect.intersects(playerRect);
    }
    //boss bullet collision to player
    private  boolean isColliding(BossBullet bullet){
        Rectangle bulletRect = new Rectangle(
            bullet.x,
            bullet.y,
            bullet.width,
            bullet.height
        );
        Rectangle playerRect = new Rectangle(
            playerX,
            playerY,
            playerWidth,
            playerHeight
        );
        return  bulletRect.intersects(playerRect);
    }
    //player bullet hir deputy boss
    private boolean isColliding(Bullet bullet, DeputyBoss deputyBoss) {
        Rectangle bulletRect = new Rectangle(
            bullet.x,
            bullet.y,
            bullet.width,
            bullet.height
        );

        Rectangle deputyRect = new Rectangle(
            deputyBoss.x,
            deputyBoss.y,
            deputyBoss.width,
            deputyBoss.height
        );

        return bulletRect.intersects(deputyRect);
    }
    //deputy boss hit player
    private boolean isColliding(DeputyBoss deputyBoss) {
        Rectangle playerRect = new Rectangle(
            playerX,
            playerY,
            playerWidth,
            playerHeight
        );

        Rectangle deputyRect = new Rectangle(
            deputyBoss.x,
            deputyBoss.y,
            deputyBoss.width,
            deputyBoss.height
        );

        return playerRect.intersects(deputyRect);
    }
    //spawnenemy
    private void spawnEnemy() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEnemySpawnTime < enemySpawnDelay) {
            return;
        }
        lastEnemySpawnTime = currentTime;
        if (currentStage == 1) {
            spawnNormalEnemy();
        }
        else if (currentStage == 2) {
            spawnXEnemies();
        }
    }
    private void spawnDeputyBoss() {
        deputyBoss = new DeputyBoss(30, 1500, 2);
        deputyBoss.x = panelW / 2 - deputyBoss.width / 2;
        deputyBoss.y = -deputyBoss.height;
        if (currentStage == 1) {
            Deputyboss1 =
                new ImageIcon("images/Deputyboss1.png").getImage();
        }
    }
    private void spawnNormalEnemy() {
        enemyImage = new ImageIcon("images/enemy1.png").getImage();
        Deputyboss1 = new ImageIcon("images/Deputyboss1.png").getImage();
        NormalEnemy enemy = new NormalEnemy(0, 0);
        int enemyX = (int)(Math.random() * (panelW - enemy.width));
        enemies.add(new NormalEnemy(enemyX, -enemy.height));
    }
    private void spawnXEnemies() {
        enemyImage = new ImageIcon("images/enemy2.png").getImage();
        Deputyboss1 = new ImageIcon("images/Deputyboss2.png").getImage();
        int enemyY = -40;
        enemies.add(new XEnemy(100, enemyY, 1));
        enemies.add(new XEnemy(700, enemyY, -1));
    }
    //spawnboss
    private void spawnBoss() {
        if(currentStage==1){
            boss = new BossStage1(70,1000,3);
            if(boss instanceof BossStage1){
                bossImage = new ImageIcon("images/bossStage1.png").getImage();
            }
            
        }
        else if(currentStage==2){
            boss = new BossStage2(80,1000,5);
            if(boss instanceof BossStage2){
                bossImage = new ImageIcon("images/bossStage2.png").getImage();
            }
        }
        boss.x = panelW / 2 - boss.width / 2;
        boss.y = -boss.height ;
    }
    //method update
    private void updateGame() {
        if(gameOver||gameWin||stageClear){
            return ;
        }
        spawnEnemy();
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
        if (deputyBoss != null && isColliding(deputyBoss)) {
            playerHP--;
            if (playerHP <= 0) {
                playerHP = 0;
                gameOver = true;
            }
        }
        if (boss != null && isColliding(boss)) {
            playerHP--;

            if (playerHP <= 0) {
                gameOver = true;
            }
        }
        for (int i = bullets.size()-1;i>=0;i--) { //bullets + collsision
            Bullet bullet = bullets.get(i);
            bullet.move();
            if(bullet.y+bullet.height<0){
                bullets.remove(i); //ลบกระสุน
                continue;
            } 
            boolean bulletHit = false;
            //collision enemy
            for (int j = enemies.size() - 1; j >= 0; j--) {
                Enemy enemy = enemies.get(j);
                if (isColliding(bullet, enemy)) {
                    bullets.remove(i);
                    enemy.takeDamage(1);// Enemy เสีย HP 1
                    if(enemy.isDead()){// ถ้า HP หมด
                        enemies.remove(j);
                        enemyKilled++;
                        if(enemyKilled>=5&&!deputybossSpawned){
                            spawnDeputyBoss();
                            deputybossSpawned = true;
                        }
                        if (enemyKilled >= enemyTarget && !bossSpawned) {
                            bossSpawned = true;
                            bossWarning = true;
                            bossWarningTime = System.currentTimeMillis();
                            spawnBoss();
                        }
                    }
                    bulletHit = true; //กันกระสุนชนenemyกับbossพร้อมกัน
                    break;
                }
            }
            // bullet hit Deputy Boss
            if (!bulletHit && deputyBoss != null) {

                if (isColliding(bullet, deputyBoss)) {
                    bullets.remove(i);
                    deputyBoss.takeDamage(1);
                    if (deputyBoss.isDead()) {
                        deputyBoss = null;
                        deputybossSpawned = false;
                        // Deputy Boss ตาย
                        // ต่อไปเจอ Boss Stage 1
                        // spawnBoss();
                    }
                    bulletHit = true;
                }
            }
            //bullet hit boss
            if (!bulletHit && boss != null) {
                if (isColliding(bullet, boss)) {
                    // ลบกระสุน
                    bullets.remove(i);
                    // ลดเลือด Boss
                    boss.takeDamage(1);
                    // Boss ตาย
                    if (boss.isDead()) {
                        if(currentStage==1){
                            stageClear = true;
                            boss = null;
                            restartStageButton.setVisible(true);
                            stage2Button.setVisible(true);
                        }
                        else if(currentStage==2){
                            gameWin = true;
                        }
                    }
                }
            }
        }
        //boss bullet
        for(int i=bossBullets.size()-1;i>=0;i--){
            BossBullet bullet = bossBullets.get(i);
            bullet.move();
            //กระสุนชนplayer
            if(isColliding(bullet)){
                bossBullets.remove(i);
                playerHP--;
                if(playerHP<=0){
                    gameOver = true;
                    playerHP = 0;
                }
                continue;
            }
            if(bullet.y>panelH)bossBullets.remove(i);
        }
        //enemy
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            enemy.move();
            if(isColliding(enemy)){//enemy ชน player
                playerHP--;
                if(playerHP<=0){
                    playerHP=0;
                    gameOver = true;
                }
                enemies.remove(i);
                continue;
            }
            if (enemy.y > panelH) {
                enemies.remove(i);
            }
        }
        // Deputy Boss
        if (deputyBoss != null) {

            deputyBoss.move();
            deputyBoss.updateBossPhase();

            if (deputyBoss.canShoot()) {

                deputyBoss.shoot(bossBullets);
            }
        }
        //boss
        if (boss != null) {
            boss.move();
            boss.updateBossPhase();
            bossShoot();
        }
        if (bossWarning) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - bossWarningTime >= bossWarningDuration) {
                bossWarning = false;
            }
        }
        
    }
    private void startStage2() {

        currentStage = 2;
        stageClear = false;

        enemyKilled = 0;
        bossSpawned = false;
        boss = null;

        enemies.clear();
        bullets.clear();
        bossBullets.clear();

        lastEnemySpawnTime = 0;

        bossWarning = false;
        // bossWarningDead = false;

        restartStageButton.setVisible(false);
        stage2Button.setVisible(false);
    }
    private void restartGame() {
        Ship newShip = ShipSelection.selectShip();
        if(newShip==null)return ;
        playerShip = newShip;
        if(playerShip instanceof RedShip){
            playerImage = new ImageIcon("images/redship.png").getImage();
        }
        else if(playerShip instanceof GrayShip){
            playerImage = new ImageIcon("images/grayship.png").getImage();
        }
        // Player
        playerX = 375;
        playerY = 600;
        playerHP = playerMaxHP;

        // Game
        gameOver = false;
        gameWin = false;
        currentStage = 1;
        stageClear = false;

        // Bullet
        bullets.clear();
        bossBullets.clear();

        // Enemy
        enemies.clear();
        enemyKilled = 0;
        lastEnemySpawnTime = 0;

        // Boss
        boss = null;
        bossSpawned = false;
        restartStageButton.setVisible(false);
        stage2Button.setVisible(false);

        // Warning
        bossWarning = false;
        // bossWarningDead = false;

        // ป้องกันสถานะปุ่มค้าง
        upPressed = false;
        downPressed = false;
        leftPressed = false;
        rightPressed = false;
        spacefirepressed = false;
    }
}