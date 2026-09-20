public class PowerItem extends Item {

    public PowerItem(int x, int y) {
        super(x, y);
    }

    @Override
    public void move() {
        y += speed;
    }

    @Override
    public int applyEffect() {
        // เพิ่มพลังยิง
        return 1;
    }
}