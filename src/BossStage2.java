import java.util.ArrayList;

public class BossStage2 extends Boss {
    private boolean reachedPosition = false;
    private int targetY = 80;
    private int phase = 1;

    public BossStage2(int hp, int fireDelay, int speed) {
        super(hp, fireDelay, speed);
    }

    @Override
    public void move() {
        if (!reachedPosition) {
            y += speed;
            if (y >= targetY) {
                y = targetY;
                reachedPosition = true;
            }
        }
        else {
            x += direction * speed;
            if (x <= 0) {
                x = 0;
                direction = 1;
            }
            else if (x + width >= 800) {
                x = 800 - width;
                direction = -1;
            }
        }
    }
    @Override
    public void shoot(ArrayList<BossBullet> bossBullets) {
        int centerX = x + width / 2;
        int bulletY = y + height;

        if (phase == 1) {
            // Phase 1: ยิง 3 ทิศทาง
            bossBullets.add(new BossBullet(centerX - 20, bulletY, -1));
            bossBullets.add(new BossBullet(centerX, bulletY, 0));
            bossBullets.add(new BossBullet(centerX + 20, bulletY, 1));
        }
        else if (phase == 2) {
            // Phase 2: ยิง 4 ทิศทาง
            bossBullets.add(new BossBullet(centerX - 30, bulletY, -2));
            bossBullets.add(new BossBullet(centerX - 10, bulletY, -1));
            bossBullets.add(new BossBullet(centerX + 10, bulletY, 1));
            bossBullets.add(new BossBullet(centerX + 30, bulletY, 2));
        }
        else if (phase == 3) {
            // Phase 3: ยิง 5 ทิศทาง
            bossBullets.add(new BossBullet(centerX - 40, bulletY, -2));
            bossBullets.add(new BossBullet(centerX - 20, bulletY, -1));
            bossBullets.add(new BossBullet(centerX, bulletY, 0));
            bossBullets.add(new BossBullet(centerX + 20, bulletY, 1));
            bossBullets.add(new BossBullet(centerX + 40, bulletY, 2));
        }
    }
    @Override
    public void updateBossPhase() {
        if (hp > 60) {
            phase = 1;
        }
        else if (hp > 30&&hp<=60) {
            phase = 2;
        }
        else if (hp<=30) {
            phase = 3;
        }
    }
}