import java.util.ArrayList;

public class DeputyBoss extends Boss implements Movable{

    public DeputyBoss(int hp, int fireDelay, int speed) {
        super(hp, fireDelay, speed);
    }

    @Override
    public void move() {

        // เลื่อนลงมาจากด้านบน
        if (y < 100) {
            y += speed;
        }

        // เคลื่อนซ้ายขวา
        x += direction * speed;

        // ชนขอบซ้าย
        if (x <= 0) {
            x = 0;
            direction = 1;
        }

        // ชนขอบขวา
        if (x + width >= 800) {
            x = 800 - width;
            direction = -1;
        }
    }

    @Override
    public void shoot(ArrayList<BossBullet> bossBullets) {

        int bulletX = x + width / 2;
        int bulletY = y + height + 50;

        bossBullets.add(
            new BossBullet(bulletX, bulletY, 0)
        );
    }

    @Override
    public void updateBossPhase() {

        if (hp <= maxHP / 2 && hp > 0) {
            fireDelay = 800;
        }
        else {
            fireDelay = 1500;
        }
    }
}