import java.awt.*;
import java.util.ArrayList;

public class SmallBombItem extends Item {

    public SmallBombItem(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
        y += speed;
    }

    @Override
    public int applyEffect() {
        return 5;
    }
    
    public int getRange(){
        return 320; //รัศมีระเบิด
    }

    public void explode(ArrayList<Enemy> enemies,DeputyBoss deputyBoss,Boss boss,int playerX,int playerY,int playerWidth,int playerHeight) {
        int bombDamage = applyEffect();
        int bombRange = getRange();
        // จุดศูนย์กลางระเบิด = Player
        int bombCenterX = playerX + playerWidth / 2;
        int bombCenterY = playerY + playerHeight / 2;
        // Enemy
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Enemy enemy = enemies.get(i);
            int enemyCenterX = enemy.x + enemy.width / 2;
            int enemyCenterY = enemy.y + enemy.height / 2;
            double distance = Math.sqrt(
                Math.pow(bombCenterX - enemyCenterX, 2) +
                Math.pow(bombCenterY - enemyCenterY, 2)
            );
            if (distance <= bombRange) {
                enemy.takeDamage(bombDamage);
            }
        }
        // Deputy Boss
        if (deputyBoss != null) {
            int deputyCenterX = deputyBoss.x + deputyBoss.width / 2;
            int deputyCenterY = deputyBoss.y + deputyBoss.height / 2;
            double distance = Math.sqrt(
                Math.pow(bombCenterX - deputyCenterX, 2) +
                Math.pow(bombCenterY - deputyCenterY, 2)
            );
            if (distance <= bombRange) {
                deputyBoss.takeDamage(bombDamage);
            }
        }
        // Main Boss
        if (boss != null) {
            int bossCenterX = boss.x + boss.width / 2;
            int bossCenterY = boss.y + 50 + boss.height / 2;
            double distance = Math.sqrt(
                Math.pow(bombCenterX - bossCenterX, 2) +
                Math.pow(bombCenterY - bossCenterY, 2)
            );
            if (distance <= bombRange) {
                boss.takeDamage(bombDamage);
            }
        }
    }
    
    public void drawEffect(Graphics g, int playerX, int playerY,int playerWidth, int playerHeight,long bombEffectStartTime,int bombEffectDuration) {
        long currentTime = System.currentTimeMillis();
        long elapsed = currentTime - bombEffectStartTime;
        if (elapsed < bombEffectDuration) {
            int centerX = playerX + playerWidth / 2;
            int centerY = playerY + playerHeight / 2;
            double progress = (double) elapsed / bombEffectDuration;
            int currentRange = (int)(getRange() * progress);
            int diameter = currentRange * 2;
            // วงระเบิดด้านนอก
            g.setColor(Color.ORANGE);
            g.drawOval(
                centerX - currentRange,
                centerY - currentRange,
                diameter,
                diameter
            );
            // วงระเบิดด้านใน
            int innerRange = currentRange / 2;
            int innerDiameter = innerRange * 2;
            g.setColor(Color.YELLOW);
            g.drawOval(
                centerX - innerRange,
                centerY - innerRange,
                innerDiameter,
                innerDiameter
            );
            // เส้น 4 ทิศ
            g.setColor(Color.RED);
            g.drawLine(
                centerX,
                centerY - currentRange,
                centerX,
                centerY - currentRange - 20
            );
            g.drawLine(
                centerX,
                centerY + currentRange,
                centerX,
                centerY + currentRange + 20
            );
            g.drawLine(
                centerX - currentRange,
                centerY,
                centerX - currentRange - 20,
                centerY
            );
            g.drawLine(
                centerX + currentRange,
                centerY,
                centerX + currentRange + 20,
                centerY
            );
        }
    }
}