import java.util.ArrayList;

public class GrayShip extends Ship {
    public GrayShip(){
        super(300);
    }
    @Override 
    public void shoot(int playerX,int playerY,int playerWidth,ArrayList<Bullet> bullets){
        Bullet bullet1 = new Bullet(
            playerX+playerWidth/2-15,playerY
        );
        Bullet bullet2 = new Bullet(
            playerX+playerWidth/2+8,playerY
        );
        bullets.add(bullet1);
        bullets.add(bullet2);
    }
}
