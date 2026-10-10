import java.util.ArrayList;
import java.awt.*;

public class BigBombItem extends Item {

    public BigBombItem(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
        y += speed;
    }

    @Override
    public int applyEffect() {
        return 10;
    }

    // ระเบิดศัตรูทั้งแมพ
    public void explode(ArrayList<Enemy> enemies,DeputyBoss deputyBoss,Boss boss) {
        int bombDamage = applyEffect();
        // Enemy ทุกตัวโดน
        for (int i=0;i<enemies.size();i++) {
            Enemy enemy = enemies.get(i);
            enemy.takeDamage(bombDamage);
        }
        // Deputy Boss โดน
        if (deputyBoss != null) {
            deputyBoss.takeDamage(bombDamage);
        }
        // Main Boss โดน
        if (boss != null) {
            boss.takeDamage(bombDamage);
        }
    }

    // Effect เหมือน Small Bomb แต่ใหญ่เต็มแมพ
    public void drawEffect(Graphics g,int playerX,int playerY,int playerWidth,int playerHeight,long bigBombEffectStartTime,int bigBombEffectDuration,int panelW,int panelH ) {
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - bigBombEffectStartTime;

        if (elapsed < bigBombEffectDuration) {
            // จุดศูนย์กลางระเบิด
            int centerX = playerX + playerWidth / 2;
            int centerY = playerY + playerHeight / 2;
            // คำนวณ progress 0 → 1
            double progress =
                (double) elapsed / bigBombEffectDuration;
            // หา radius ที่ใหญ่พอจะคลุมทั้งหน้าจอ
            int maxRange = (int)Math.sqrt(
                panelW * panelW +
                panelH * panelH
            );
            int currentRange =
                (int)(maxRange * progress);

            int diameter = currentRange * 2;
            // วงนอก
            g.setColor(Color.ORANGE);
            g.drawOval(
                centerX - currentRange,
                centerY - currentRange,
                diameter,
                diameter
            );
            // วงด้านใน
            int innerRange = currentRange / 2;
            int innerDiameter = innerRange * 2;

            g.setColor(Color.YELLOW);

            g.drawOval(
                centerX - innerRange,
                centerY - innerRange,
                innerDiameter,
                innerDiameter
            );
            // เส้นระเบิด 8 ทิศ
            g.setColor(Color.RED);
            // บน
            g.drawLine(
                centerX,
                centerY - currentRange,
                centerX,
                centerY - currentRange - 20
            );
            // ล่าง
            g.drawLine(
                centerX,
                centerY + currentRange,
                centerX,
                centerY + currentRange + 20
            );
            // ซ้าย
            g.drawLine(
                centerX - currentRange,
                centerY,
                centerX - currentRange - 20,
                centerY
            );
            // ขวา
            g.drawLine(
                centerX + currentRange,
                centerY,
                centerX + currentRange + 20,
                centerY
            );
            // ซ้ายบน
            g.drawLine(
                centerX - currentRange,
                centerY - currentRange,
                centerX - currentRange - 15,
                centerY - currentRange - 15
            );
            // ขวาบน
            g.drawLine(
                centerX + currentRange,
                centerY - currentRange,
                centerX + currentRange + 15,
                centerY - currentRange - 15
            );
            // ซ้ายล่าง
            g.drawLine(
                centerX - currentRange,
                centerY + currentRange,
                centerX - currentRange - 15,
                centerY + currentRange + 15
            );
            // ขวาล่าง
            g.drawLine(
                centerX + currentRange,
                centerY + currentRange,
                centerX + currentRange + 15,
                centerY + currentRange + 15
            );
        }
    }
}