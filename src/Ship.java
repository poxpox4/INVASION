import java.util.ArrayList;
public abstract class Ship {
    protected int fireDelay;
    public Ship(int fireDelay){
        this.fireDelay = fireDelay;
    }
    public abstract void shoot(
        int playerX,
        int playerY,
        int playerWidth,
        ArrayList<Bullet> bullets
    );
    public int getFireDelay(){
        return this.fireDelay;
    }
}
