// public class Enemy implements Movable {
//     //ตน.
//     int x;
//     int y;
//     //ขนาด
//     int width = 40;
//     int height = 40;
//     //speed/
//     int speed = 2;
//     //hp
//     int hp = 3;

//     public Enemy(int x, int y) {
//         this.x = x;
//         this.y = y;
//     }
//     @Override 
//     public void move() {
//         this.y += speed;
//     }

//     public void takeDamage(int damage){
//         this.hp -= damage;
//     }

//     public boolean isDead(){
//         return this.hp<=0;
//     }
// }
public abstract class Enemy implements Movable {

    protected int x;
    protected int y;

    protected int width = 40;
    protected int height = 40;

    protected int speed = 2;
    protected int hp = 3;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public abstract void move();

    public void takeDamage(int damage) {
        this.hp -= damage;
    }

    public boolean isDead() {
        return this.hp <= 0;
    }
}