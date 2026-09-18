import java.util.ArrayList;

public class BossStage1 extends Boss {

    public BossStage1(int hp,int fireDelay,int speed) {
        super(hp,fireDelay,speed);
    }

    @Override
    public void move() {
        if (y < 50) {
            y += speed;
        }
        x += direction*speed;//ขยับซ้าย/ขวา
        if(x<=0){//ขอบซ้าย
            x = 0;
            direction = 1;
        }
        if(x+width>=800){//ขอบขวา
            x = 800-width;
            direction = -1;
        }
    }

    @Override
    public void shoot(ArrayList<BossBullet> bossBullets) {
        int bulletX1 = super.x+super.width/2-20;
        int bulletX2 = super.x+super.width/2;
        int bulletX3 = super.x+super.width/2+20;
        int bulletY = super.y+super.height+50;
        bossBullets.add(new BossBullet(bulletX1, bulletY,-1));//l
        bossBullets.add(new BossBullet(bulletX2, bulletY,0));//c
        bossBullets.add(new BossBullet(bulletX3, bulletY,1));//r
    }

    @Override
    public void updateBossPhase(){
        if(super.hp>25&&super.hp<=50){
            super.fireDelay = 1500;
        }
        else if(super.hp>0&&super.hp<=25){
            super.fireDelay = 1000;
        }
        else{
            super.fireDelay = 2000;
        }
    }

}