public class XEnemy extends Enemy {

    private int direction;
    private int moveDownDistance = 100;
    private int startY;

    public XEnemy(int x, int y, int direction) {
        super(x, y);
        this.direction = direction;
        this.startY = y;
        this.speed = 4;
    }

    @Override
    public void move() {
        // ระยะที่เคลื่อนลงมาตั้งแต่เริ่มเกิด
        int distanceDown = y - startY;

        if (distanceDown < moveDownDistance) {
            // ช่วงแรก: เคลื่อนลงตรง ๆ
            y += speed;
        } else {
            // ช่วงต่อมา: เคลื่อนเฉียงเข้าหากัน
            x += direction * speed;
            y += speed;
        }
    }
}