import java.util.ArrayList;
public class RedShip extends Ship {
    public RedShip(){
        super(150);//speed bullet
    }
    @Override 
    public void shoot(int playerX,int playerY,int playerWidth,ArrayList<Bullet> bullets){
        Bullet bullet = new Bullet(
            playerX+playerWidth/2-5,playerY
        );
        bullets.add(bullet);
    }
}
