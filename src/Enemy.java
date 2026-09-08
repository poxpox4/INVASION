public class Enemy {
    //ตน.
    int x;
    int y;
    //ขนาด
    int width = 40;
    int height = 40;
    //speed/
    int speed = 3;
    //hp
    int hp = 3;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        this.y += speed;
    }

    public void takeDamage(int damage){
        this.hp -= damage;
    }

    public boolean isDead(){
        return this.hp<=0;
    }
}