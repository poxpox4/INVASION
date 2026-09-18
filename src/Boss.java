import java.util.ArrayList;
public abstract class Boss{
    protected int fireDelay;
    protected long lastShotTime = 0;
    protected int x;
    protected int y;

    protected int width = 150;
    protected int height = 100;

    protected int speed = 3;
    protected int direction = 1;

    protected int hp = 50;
    protected int maxHP;

    public Boss(int hp,int fireDelay,int speed) {
        this.hp = hp;
        this.maxHP = hp;
        this.fireDelay = fireDelay;
        this.speed = speed;
    }
    public abstract void move();
    public abstract void shoot(ArrayList<BossBullet> bossBullets);
    public abstract void updateBossPhase();
    public int getFireDelay() {
        return fireDelay;
    }
    public boolean canShoot() {

        long currentTime = System.currentTimeMillis();

        if (currentTime - lastShotTime < fireDelay) {
            return false;
        }

        lastShotTime = currentTime;

        return true;
    }
    public boolean isDead(){
        return this.hp<=0;
    }
    public void takeDamage(int damage){
        this.hp -= damage;
        if(this.hp<=0){
            hp=0;
        }
    }
         
}