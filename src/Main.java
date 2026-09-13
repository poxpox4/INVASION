public class Main{
    public static void main(String[] args) {
        Ship playerShip = ShipSelection.selectShip();//select ship
        if(playerShip==null)return ;
        new GameFrame(playerShip);
    }
}