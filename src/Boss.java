public class Boss {
    int x;
    int y;

    int width = 150;
    int height = 100;

    int speed = 3;
    int direction = 1;

    int hp = 50;

    public Boss(int x, int y) {
        this.x = x;
        this.y = y;
    }

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

    public void takeDamage(int damage) {
        hp -= damage;
    }

    public boolean isDead() {
        return hp <= 0;
    }
}
