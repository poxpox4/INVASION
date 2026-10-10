import javax.swing.*;//พื้นที่สำหรับวาดเกม
import java.awt.event.ActionEvent; //รับevent ตอนactionทำงาน
import java.awt.*; //นำ Class ต่าง ๆ เกี่ยวกับกราฟิกมาใช้
import java.util.ArrayList;
public class GamePanel extends JPanel {
    JButton homeButton;
    //img bg
    Image stage1Background;
    Image stage2Background;
    //pause
    boolean paused = false;
    JButton pauseButton;
    JDialog pauseDialog;
    //items
    ArrayList<Item> items = new ArrayList<>();
    boolean powerMode = false;
    long powerStartTime = 0;
    int powerDuration = 10000;
    Image powerItemImage;
    Image helthItemImage;
    Image helthmaxItemImage;
    boolean speedMode = false;
    long speedStartTime = 0;
    int speedDuration = 10000;
    int normalSpeed = 10;
    Image speedItemImage;
    int playerDamage = 1;
    boolean damageBoost = false;
    Image damageImage;
    Image smallBombImage;
    SmallBombItem currentBomb;
    Image bigBombImage;
    BigBombItem currentBigBomb;
    boolean bigBombEffect = false;
    long bigBombEffectStartTime = 0;
    int bigBombEffectDuration = 500;
    //bompEffect
    boolean bombEffect = false;
    long bombEffectStartTime = 0;
    int bombEffectDuration = 300;
    int bombEffectRange = 320;
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
    //bullets
    Image bulletImage;
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
    int enemyTarget = 30;
    Image Deputyboss1,Deputyboss2,enemyImage;
    int stage2SpawnCount = 0;
    int stage2SXWaveEvery = 6;
    //boss
    Image bossImage;
    Image bossBulletImage;
    boolean bossSpawned = false; 
    boolean bossWarning = false;
    long bossWarningTime = 0;
    int bossWarningDuration = 3000;
    Boss boss = null;
    DeputyBoss deputyBoss = null;
    boolean deputybossSpawned = false;
    //check stage
    int currentStage = 1;
    int restartGame = 1;
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
        bossBulletImage = new ImageIcon("images/bossbullet.png").getImage();
        bulletImage = new ImageIcon("images/bullet.png").getImage();
        helthItemImage = new ImageIcon("images/helthItem.png").getImage();
        powerItemImage = new ImageIcon("images/powerItem.png").getImage();
        helthmaxItemImage = new ImageIcon("images/helthmaxhp.png").getImage();
        speedItemImage = new ImageIcon("images/speedItem.png").getImage();
        damageImage = new ImageIcon("images/damageitem.png").getImage();
        smallBombImage = new ImageIcon("images/smallbombitem.png").getImage();
        bigBombImage = new ImageIcon("images/bigbombItem.png").getImage();
        stage1Background = new ImageIcon("images/stage1bg.png").getImage();
        stage2Background = new ImageIcon("images/stage2bg.png").getImage();
        
        setLayout(null);
        setPreferredSize(new Dimension(panelW, panelH)); //กำหนดขนาดพื้นที่เกม
        setBackground(Color.BLACK);
        // restartStageButton = new JButton("RESTART");
        // stage2Button = new JButton("STAGE 2");

        // restartStageButton.setBounds(300, 300, 200, 60);
        // stage2Button.setBounds(300, 400, 200, 60);

        // restartStageButton.setVisible(false);
        // stage2Button.setVisible(false);

        // add(restartStageButton);
        // add(stage2Button);
        int buttonW = 200;
        int buttonH = 50;
        int gap = 40;
        int totalW = buttonW * 2 + gap;
        int startX = (panelW - totalW) / 2;
        int buttonY = 620;
        Home home = new Home();
        restartStageButton = home.createButton(
            "RESTART",
            new Color(255, 65, 65)
        );
        stage2Button = home.createButton(
            "NEXT STAGE",
            new Color(40, 220, 110)
        );
        homeButton = home.createButton(
            "HOME",
            new Color(40, 170, 230)
        );
        // ตำแหน่งปุ่มหน้า Stage Clear
        restartStageButton.setBounds(300, 400, 200, 50);
        stage2Button.setBounds(
            startX,
            buttonY,
            buttonW,
            buttonH
        );

        homeButton.setBounds(
            startX + buttonW + gap,
            buttonY,
            buttonW,
            buttonH
        );

        restartStageButton.setVisible(false);
        stage2Button.setVisible(false);
        homeButton.setVisible(false);

        add(restartStageButton);
        add(stage2Button);
        add(homeButton);
        homeButton.addActionListener(e -> {
            timer.stop();

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
        // ปุ่ม Pause
        pauseButton = new JButton(new ImageIcon("images/pausemenu.png"));
        pauseButton.setBounds(735, 15, 45, 45);
        pauseButton.setFocusable(false);
        pauseButton.setFocusPainted(false);
        pauseButton.setBorderPainted(false);
        pauseButton.setContentAreaFilled(false);    
        pauseButton.setRequestFocusEnabled(false);
        pauseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        add(pauseButton);

        pauseButton.addActionListener(e -> {
            showPauseMenu();
        });
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
    
    private void drawGameOver(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // พื้นหลังมืด
        g2.setColor(new Color(0, 0, 15, 225));
        g2.fillRect(0, 0, panelW, panelH);

        // GAME OVER
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        g2.setColor(Color.WHITE);

        String title = "STAGE " + currentStage;
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(title,
            (panelW - fm.stringWidth(title)) / 2, 100);

        g2.setFont(new Font("Arial", Font.BOLD, 58));
        g2.setColor(new Color(255, 65, 65));

        String overText = "GAME OVER";
        fm = g2.getFontMetrics();
        g2.drawString(overText,
            (panelW - fm.stringWidth(overText)) / 2, 175);

        // ข้อความแพ้
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        g2.setColor(Color.WHITE);

        String resultText = "YOUR SHIP WAS DESTROYED";
        fm = g2.getFontMetrics();
        g2.drawString(resultText,
            (panelW - fm.stringWidth(resultText)) / 2, 260);

        // คะแนน
        int score = enemyKilled * 100;

        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(new Color(150, 210, 255));

        String scoreLabel = "SCORE";
        fm = g2.getFontMetrics();
        g2.drawString(scoreLabel,
            (panelW - fm.stringWidth(scoreLabel)) / 2, 380);

        String scoreText = String.format("%08d", score);
        g2.setFont(new Font("Monospaced", Font.BOLD, 30));
        g2.setColor(Color.WHITE);
        fm = g2.getFontMetrics();
        g2.drawString(scoreText,
            (panelW - fm.stringWidth(scoreText)) / 2, 420);

        g2.dispose();
    }
    private void drawStageResult(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // พื้นหลัง
        g2.setColor(new Color(0, 0, 15, 225));
        g2.fillRect(0, 0, panelW, panelH);

        boolean stage1 = currentStage == 1;

        Color mainColor = stage1
            ? new Color(255, 190, 55)
            : new Color(220, 130, 255);


        // ชื่อ Stage
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        g2.setColor(Color.WHITE);

        String stageText = stage1 ? "STAGE 1" : "STAGE 2";
        FontMetrics fm = g2.getFontMetrics();

        g2.drawString(
            stageText,
            (panelW - fm.stringWidth(stageText)) / 2,
            100
        );

        // CLEAR!
        g2.setFont(new Font("Arial", Font.BOLD, 58));
        g2.setColor(mainColor);

        String clearText = "CLEAR!";
        fm = g2.getFontMetrics();

        g2.drawString(
            clearText,
            (panelW - fm.stringWidth(clearText)) / 2,
            165
        );

        // ดาว 3 ดวง
        g2.setFont(new Font("Serif", Font.PLAIN, 55));
        g2.setColor(mainColor);

        String stars = "★ ★ ★";
        fm = g2.getFontMetrics();

        g2.drawString(
            stars,
            (panelW - fm.stringWidth(stars)) / 2,
            230
        );

        // ข้อความตาม Stage
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        g2.setColor(Color.WHITE);

        String resultText = "BOSS DESTROYED!";

        fm = g2.getFontMetrics();

        g2.drawString(
            resultText,
            (panelW - fm.stringWidth(resultText)) / 2,
            300
        );

        // คะแนน
        int score = enemyKilled * 100;

        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.setColor(new Color(150, 210, 255));

        String scoreLabel = "SCORE";
        fm = g2.getFontMetrics();

        g2.drawString(
            scoreLabel,
            (panelW - fm.stringWidth(scoreLabel)) / 2,
            400
        );

        String scoreText = String.format("%08d", score);

        g2.setFont(new Font("Monospaced", Font.BOLD, 30));
        g2.setColor(Color.WHITE);
        fm = g2.getFontMetrics();

        g2.drawString(
            scoreText,
            (panelW - fm.stringWidth(scoreText)) / 2,
            440
        );

        g2.dispose();
        
    }
    private void showPauseMenu() {
        if (gameOver || gameWin || stageClear || paused) {
            return;
        }

        paused = true;

        JFrame frame = (JFrame)
            SwingUtilities.getWindowAncestor(this);

        if (frame == null) {
            paused = false;
            return;
        }

        pauseDialog = new JDialog(frame, "INVASION", true);
        pauseDialog.setSize(420, 300);
        pauseDialog.setResizable(false);
        pauseDialog.setDefaultCloseOperation(
            JDialog.DO_NOTHING_ON_CLOSE
        );

        // Panel หลัก
        JPanel panel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // พื้นหลังอวกาศตาม Stage ปัจจุบัน
                Image bg = currentStage == 1
                    ? stage1Background
                    : stage2Background;

                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);

                // Overlay สีเข้ม
                g.setColor(new Color(0, 5, 25, 200));
                g.fillRect(0, 0, getWidth(), getHeight());

                g.setFont(new Font("Arial", Font.BOLD, 30));
                FontMetrics fm = g.getFontMetrics();
                String title = "GAME PAUSED";

                g.setColor(Color.WHITE);
                g.drawString(
                    title,
                    (getWidth() - fm.stringWidth(title)) / 2,
                    62
                );
            }
        };

        JButton resumeButton;
        JButton restartButton;
        JButton homeMenuButton;
        Home home = new Home();
        resumeButton = home.createButton(
            "RESUME",
            new Color(40, 170, 230)
        );

        restartButton = home.createButton(
            "RESTART",
            new Color(255, 65, 65)
        );

        homeMenuButton = home.createButton(
            "HOME",
            new Color(40, 170, 230)
        );

        resumeButton.setBounds(25, 135, 110, 50);
        restartButton.setBounds(150, 135, 120, 50);
        homeMenuButton.setBounds(285, 135, 110, 50);

        panel.add(resumeButton);
        panel.add(restartButton);
        panel.add(homeMenuButton);

        resumeButton.addActionListener(e -> {
            paused = false;
            pauseDialog.dispose();
            repaint();
        });

        restartButton.addActionListener(e -> {
            pauseDialog.dispose();
            paused = false;
            restartGame();
        });

        homeMenuButton.addActionListener(e -> {
            pauseDialog.dispose();
            paused = false;
            timer.stop();

            frame.setContentPane(new Home());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.revalidate();
            frame.repaint();
        });

        pauseDialog.setContentPane(panel);
        pauseDialog.setLocationRelativeTo(frame);

        // กด X ให้กลับไปเล่นต่อ
        pauseDialog.addWindowListener(
            new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(
                    java.awt.event.WindowEvent e
                ) {
                    paused = false;
                    pauseDialog.dispose();
                }
            }
        );

        pauseDialog.setVisible(true);
    }
    @Override
    protected void paintComponent(Graphics g) { //paintComponent วาดสิ่งต่าง ๆ ลงบนหน้าจอ
        //Graphics g ปากกาวาด
        super.paintComponent(g); //สั่งให้ JPanel วาดพื้นหลังก่อน
        // Background
        if (currentStage == 1) {
            g.drawImage(stage1Background,0,0,panelW, panelH,this);
        }
        else if (currentStage == 2) {
            g.drawImage(stage2Background,0,0,panelW,panelH,this);
        }
        //player ship
        g.drawImage(playerImage, playerX, playerY, playerWidth,playerHeight,this);
        //player bullets
        g.setColor(Color.YELLOW);
        for(int i=0;i<bullets.size();i++){
            Bullet bullet = bullets.get(i);
            g.drawImage(bulletImage, bullet.x, bullet.y, bullet.width,bullet.height,this);
        }
        //boss bullets
        g.setColor(Color.ORANGE);
        for(int i=0;i<bossBullets.size();i++){
            BossBullet bullet = bossBullets.get(i);
            g.drawImage(bossBulletImage, bullet.x, bullet.y, bullet.width,bullet.height,this);
        }
        //Enemy
        g.setColor(Color.RED);
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            g.drawImage(enemyImage, enemy.x, enemy.y, enemy.width,enemy.height,this);
        }
        // Items
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item instanceof HealthItem) {
                g.drawImage(helthItemImage, item.x, item.y, item.width,item.height,this);
            }
            else if(item instanceof HealthMaxHPItem){
                g.drawImage(helthmaxItemImage, item.x, item.y, item.width,item.height,this);
            }
            else if (item instanceof PowerItem) {
                g.drawImage(powerItemImage, item.x, item.y, item.width,item.height,this);
                // powerMode = true;
            }
            else if(item instanceof SpeedItem){
                g.drawImage(speedItemImage, item.x, item.y,item.width,item.height,this);
            }
            else if(item instanceof DamageItem){
                g.drawImage(damageImage, item.x, item.y, item.width,item.height,this);
            }
            else if(item instanceof SmallBombItem){
                g.drawImage(smallBombImage, item.x, item.y, item.width,item.height,this);
            }
            else if(item instanceof BigBombItem){
                g.drawImage(bigBombImage, item.x, item.y, item.width,item.height,this);
            }
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
        // Player HP Bar
        int hpBarX = 20;
        int hpBarY = panelH - 35;
        int hpBarW = 150;
        int hpBarH = 18;

        // พื้นหลังหลอดเลือด
        g.setColor(Color.DARK_GRAY);
        g.fillRect(hpBarX, hpBarY, hpBarW, hpBarH);

        // คำนวณความยาวหลอดเลือดตาม HP
        double hpRatio = (double) playerHP / playerMaxHP;
        hpRatio = Math.max(0, Math.min(1, hpRatio));

        int currentHPWidth = (int)(hpBarW * hpRatio);

        // สีเขียวเมื่อเลือดมาก สีเหลืองและแดงเมื่อเลือดต่ำ
        if (hpRatio > 0.5) {
            g.setColor(new Color(40, 230, 80));
        }
        else if (hpRatio > 0.25) {
            g.setColor(Color.YELLOW);
        }
        else {
            g.setColor(Color.RED);
        }

        g.fillRect(
            hpBarX,
            hpBarY,
            currentHPWidth,
            hpBarH
        );

        // ขอบหลอดเลือด
        g.setColor(Color.WHITE);
        g.drawRect(hpBarX, hpBarY, hpBarW, hpBarH);

        // ข้อความ HP ด้านซ้าย
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("HP", hpBarX, hpBarY - 7);
        if (bossWarning) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString(
                "WARNING! BOSS INCOMING!",
                130,
                300
            );
        }
        if (bombEffect && currentBomb != null) {
            currentBomb.drawEffect(
                g,
                playerX,
                playerY,
                playerWidth,
                playerHeight,
                bombEffectStartTime,
                bombEffectDuration
            );
        }
        if (bigBombEffect && currentBigBomb != null) {
            currentBigBomb.drawEffect(
                g,
                playerX,
                playerY,
                playerWidth,
                playerHeight,
                bigBombEffectStartTime,
                bigBombEffectDuration,
                panelW,
                panelH
            );
        }
        if (gameOver) {
            drawGameOver(g);
        }
        else if (stageClear || gameWin) {
            drawStageResult(g);
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
        if(powerMode){
            Bullet bullet1 = new Bullet(
                playerX + playerWidth / 2 - 20,
                playerY
            );
            Bullet bullet2 = new Bullet(
                playerX + playerWidth / 2 - 3,
                playerY
            );
            Bullet bullet3 = new Bullet(
                playerX + playerWidth / 2 + 14,
                playerY
            );

            bullets.add(bullet1);
            bullets.add(bullet2);
            bullets.add(bullet3);
        }
        else{
            playerShip.shoot(playerX,playerY,playerWidth,bullets);
        }
        
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
    //collision item
    private boolean isColliding(Item item) {
        Rectangle itemRect = new Rectangle(
            item.x,
            item.y,
            item.width,
            item.height
        );

        Rectangle playerRect = new Rectangle(
            playerX,
            playerY,
            playerWidth,
            playerHeight
        );

        return itemRect.intersects(playerRect);
    }
    //spawnItems
    private void spawnItem(int x, int y) {
        int random = (int)(Math.random() * 100);
        // 0 - 19 = HealthItem 20%
        if (random < 20) {
            items.add(new HealthItem(x, y));
        }
        // 20 - 29 = PowerItem 10%
        else if (random < 30&&random>=20) {
            items.add(new PowerItem(x, y));
        }
        //5%
        else if(random>=30&&random<35){
            items.add(new HealthMaxHPItem(x, y));
        }
        //10%
        else if(random>=35&&random<45){
            items.add(new SpeedItem(x, y));
        }
        //10%
        else if(random>=45&&random<55){
            items.add(new DamageItem(x, y));
        }
        //10%
        else if(random>=55&&random<65){
            items.add(new SmallBombItem(x, y));
        }
        //5%
        else if(random>=65&&random<70){
            items.add(new BigBombItem(x, y));
        }
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
        else if (currentStage == 2){
            spawnNormalEnemy();
            stage2SpawnCount++;
            if(stage2SpawnCount>=stage2SXWaveEvery){
                spawnXEnemies();
                stage2SpawnCount = 0;
            }
        }
    }
    private void spawnDeputyBoss() {
        if (currentStage == 1) {
            deputyBoss = new DeputyBoss(30, 1000, 2);
            Deputyboss1 = new ImageIcon("images/Deputyboss1.png").getImage();
        }
        else if(currentStage == 2){
            deputyBoss = new DeputyBoss(30, 800, 2);
            Deputyboss1 = new ImageIcon("images/Deputyboss2.png").getImage();
        }
        deputyBoss.x = panelW / 2 - deputyBoss.width / 2;
        deputyBoss.y = -deputyBoss.height;
    }
    private void spawnNormalEnemy() {
        if(currentStage==1){
            enemyImage = new ImageIcon("images/enemy1.png").getImage();
            Deputyboss1 = new ImageIcon("images/Deputyboss1.png").getImage();
        }
        else{
            enemyImage = new ImageIcon("images/enemy2.png").getImage();
            Deputyboss2 = new ImageIcon("images/Deputyboss2.png").getImage();

        }
        NormalEnemy enemy = new NormalEnemy(0, 0);
        int enemyX = (int)(Math.random() * (panelW - enemy.width));
        enemies.add(new NormalEnemy(enemyX, -enemy.height));
    }
    private void spawnXEnemies() {
        enemyImage = new ImageIcon("images/enemy2.png").getImage();
        Deputyboss1 = new ImageIcon("images/Deputyboss2.png").getImage();
        //คู่1 
        enemies.add(new XEnemy(0, -40, 1));//ซ้าย
        enemies.add(new XEnemy(panelW-40, -220, -1));//ขวา
        //คู่2 
        enemies.add(new XEnemy(200,-100,1));//ซ้าย
        enemies.add(new XEnemy(panelW-200,-300,-1));//ขวา
        //คู่3 ขวา
        enemies.add(new XEnemy(panelW-150,-200,-1));//ขวา
        enemies.add(new XEnemy(250,-200,1));//ซ้าย
        //คู่4 ซ้าย
        enemies.add(new XEnemy(50,-70,1));//ซ้าย
        enemies.add(new XEnemy(panelW-100,-150,-1));//ขวา
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
            boss = new BossStage2(80,1000,3);
            if(boss instanceof BossStage2){
                bossImage = new ImageIcon("images/bossStage2.png").getImage();
            }
        }
        boss.x = panelW / 2 - boss.width / 2;
        boss.y = -boss.height ;
    }
    private void checkBossSpawn() {
        if (enemyKilled >= enemyTarget && !bossSpawned) {
            bossSpawned = true;
            bossWarning = true;
            bossWarningTime = System.currentTimeMillis();
            spawnBoss();
        }
    }
    private void handleGameOver() {
        gameOver = true;
        playerHP = 0;
        paused = false;

        pauseButton.setVisible(false);
        restartStageButton.setVisible(true);
        stage2Button.setVisible(false);
        homeButton.setVisible(true);

        // จัดปุ่มให้อยู่ตรงกลาง
        int buttonW = 200;
        int buttonH = 50;
        int gap = 30;
        int startX = (panelW - (buttonW * 2 + gap)) / 2;
        int buttonY = 620;

        restartStageButton.setBounds(
            startX, buttonY, buttonW, buttonH
        );

        homeButton.setBounds(
            startX + buttonW + gap, buttonY, buttonW, buttonH
        );

        repaint();
    }
    private void handleBossDefeated() {
        boss = null;
        paused = false;
        pauseButton.setVisible(false);
        restartStageButton.setVisible(false);
        if (currentStage == 1) {
            stageClear = true;
            gameWin = false;
            stage2Button.setText("NEXT STAGE");
            stage2Button.setVisible(true);
            homeButton.setVisible(true);
        }
        else if (currentStage == 2) {
            gameWin = true;
            stageClear = false;
            stage2Button.setVisible(false);
            homeButton.setVisible(true);
            homeButton.setBounds(
                (panelW - 250) / 2,
                640,
                250,
                65
            );
        }
        repaint();
    }
    //method update
    private void updateGame() {
        if(gameOver||gameWin||stageClear||paused){
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
                handleGameOver();
            }
        }
        if (boss != null && isColliding(boss)) {
            playerHP--;

            if (playerHP <= 0) {
                handleGameOver();
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
                    if(damageBoost){
                        enemy.takeDamage(playerDamage);
                        damageBoost = false;
                        playerDamage = 1;
                    }
                    else{
                        enemy.takeDamage(1);// Enemy เสีย HP 1
                    }
                    
                    if(enemy.isDead()){// ถ้า HP หมด
                        enemies.remove(j);
                        enemyKilled++;
                        spawnItem(enemy.x, enemy.y);
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
                    if(damageBoost){
                        deputyBoss.takeDamage(playerDamage);
                        damageBoost = false;
                        playerDamage = 1;
                    }
                    else{
                        deputyBoss.takeDamage(1);
                    }
                    
                    if (deputyBoss.isDead()) {
                        spawnItem(deputyBoss.x, deputyBoss.y);
                        deputyBoss = null;
                        deputybossSpawned = false;
                        
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
                    if(damageBoost){
                        boss.takeDamage(playerDamage);
                        damageBoost = false;
                        playerDamage = 1;
                    }
                    else{
                        boss.takeDamage(1);
                    }
                    
                    // Boss ตาย
                    if (boss.isDead()) {
                        handleBossDefeated();
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
                    handleGameOver();
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
                    handleGameOver();
                }
                enemies.remove(i);
                continue;
            }
            if (enemy.y > panelH) {
                enemies.remove(i);
            }
        }
        // Items
        for (int i = items.size() - 1; i >= 0; i--) {
            Item item = items.get(i);
            item.move();
            // ถ้า Player ชน Item
            if (isColliding(item)) {
                // ถ้าเป็น HealthItem
                if (item instanceof HealthItem) {
                    playerHP += item.applyEffect();
                    if (playerHP > playerMaxHP) {
                        playerHP = playerMaxHP;
                    }
                }
                else if(item instanceof HealthMaxHPItem){
                    playerHP = playerMaxHP;
                }
                else if(item instanceof PowerItem){
                    powerMode = true;
                    powerStartTime = System.currentTimeMillis();
                }
                else if(item instanceof SpeedItem){
                    speedMode = true;
                    speedStartTime = System.currentTimeMillis();
                    playerSpeed = normalSpeed+item.applyEffect();
                }
                else if(item instanceof DamageItem){
                    damageBoost = true;
                    playerDamage += item.applyEffect();
                }
                else if(item instanceof SmallBombItem){
                    SmallBombItem bomb = (SmallBombItem)item;
                    bomb.explode(enemies, deputyBoss, boss,playerX,playerY,playerWidth,playerHeight);
                    bombEffect = true;
                    bombEffectStartTime = System.currentTimeMillis();
                    bombEffectRange = bomb.getRange();
                    currentBomb = bomb;
                    // จัดการ Enemy ที่ตาย
                    for (int j = enemies.size() - 1; j >= 0; j--) {
                        Enemy enemy = enemies.get(j);
                        if (enemy.isDead()) {
                            enemies.remove(j);
                            enemyKilled++;
                            spawnItem(enemy.x, enemy.y);
                        }
                    }
                    checkBossSpawn();
                    // จัดการ Deputy Boss ที่ตาย
                    if (deputyBoss != null && deputyBoss.isDead()) {
                        spawnItem(deputyBoss.x, deputyBoss.y);
                        deputyBoss = null;
                        deputybossSpawned = false;
                    }
                    // จัดการ Boss ที่ตาย
                    if (boss != null && boss.isDead()) {
                         handleBossDefeated();
                    }
                }
                else if(item instanceof BigBombItem){
                    BigBombItem bomb = (BigBombItem)item;
                    bomb.explode(
                        enemies,
                        deputyBoss,
                        boss
                    );
                    bigBombEffect = true;
                    bigBombEffectStartTime = System.currentTimeMillis();
                    currentBigBomb = bomb;
                    // จัดการ Enemy ที่ตาย
                    for (int j = enemies.size() - 1; j >= 0; j--) {
                        Enemy enemy = enemies.get(j);
                        if (enemy.isDead()) {
                            enemies.remove(j);
                            enemyKilled++;
                            spawnItem(
                                enemy.x,
                                enemy.y
                            );
                        }
                    }
                    checkBossSpawn();
                    // จัดการ Deputy Boss ที่ตาย
                    if (deputyBoss != null && deputyBoss.isDead()) {
                        spawnItem(
                            deputyBoss.x,
                            deputyBoss.y
                        );
                        deputyBoss = null;
                        deputybossSpawned = false;
                    }
                    // จัดการ Main Boss ที่ตาย
                    if (boss != null && boss.isDead()) {
                        handleBossDefeated();
                    }
                }
                // ลบ Item หลังจากเก็บได้
                items.remove(i);
                continue;
            }
            // ถ้า Item ตกออกจากหน้าจอ
            if (item.y > panelH) {
                items.remove(i);
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
        if(powerMode){
            long currentTime = System.currentTimeMillis();
            if(currentTime-powerStartTime >= powerDuration){
                powerMode = false;
            }
        }
        if(speedMode){
            long currentTime = System.currentTimeMillis();
            if(currentTime-speedStartTime>=speedDuration){
                speedMode = false;
                playerSpeed = normalSpeed;
            }
        }
        if(bombEffect){
            long currentTime = System.currentTimeMillis();
            if(currentTime-bombEffectStartTime>=bombEffectDuration){
                bombEffect = false;
                currentBomb = null;
            }
        }
        if(bigBombEffect){
            long currentTime = System.currentTimeMillis();
            if(currentTime-bigBombEffectStartTime>=bigBombEffectDuration){
                bigBombEffect = false;
                currentBigBomb = null;
            }
        }
    }
    public void startStage2() {
        items.clear();
        currentStage = 2;
        restartGame = 2;
        stageClear = false;

        playerHP = playerMaxHP;

        enemyKilled = 0;
        bossSpawned = false;
        boss = null;

        deputyBoss = null;
        deputybossSpawned = false;

        enemies.clear();
        bullets.clear();
        bossBullets.clear();

        lastEnemySpawnTime = 0;
        stage2SpawnCount = 0;

        bossWarning = false;
        // bossWarningDead = false;

        homeButton.setVisible(false);
        pauseButton.setVisible(true);
        restartStageButton.setVisible(false);
        stage2Button.setVisible(false);
    }
    public void startSelectedStage2() {
        playerHP = playerMaxHP;
        items.clear();
        currentStage = 2;
        restartGame = 2;
        stageClear = false;
        enemyKilled = 0;
        bossSpawned = false;
        boss = null;
        deputyBoss = null;
        deputybossSpawned = false;
        enemies.clear();
        bullets.clear();
        bossBullets.clear();
        lastEnemySpawnTime = 0;
        bossWarning = false;
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
        //items
        items.clear();
        powerMode = false;
        damageBoost = false;
        playerDamage = 1;
        // Player
        playerX = 375;
        playerY = 600;
        playerHP = playerMaxHP;
        speedMode = false;
        playerSpeed = normalSpeed;

        // Game
        gameOver = false;
        gameWin = false;
        currentStage = restartGame;
        stage2SpawnCount = 0;
        stageClear = false;
        homeButton.setVisible(false);
        pauseButton.setVisible(true);

        // Bullet
        bullets.clear();
        bossBullets.clear();

        // Enemy
        enemies.clear();
        enemyKilled = 0;
        lastEnemySpawnTime = 0;

        //deputyboss
        deputyBoss = null;
        deputybossSpawned = false;
        
        // Boss
        boss = null;
        bossSpawned = false;
        restartStageButton.setVisible(false);
        stage2Button.setVisible(false);

        //bombEffect
        bombEffect = false;
        currentBomb = null;
        bombEffectStartTime = 0;
        bigBombEffect = false;
        currentBigBomb = null;
        bigBombEffectStartTime = 0;

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