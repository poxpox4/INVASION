import javax.swing.*;

public class ShipSelection {

    public static Ship selectShip() {

        String[] options = {"RED SHIP", "GRAY SHIP"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose your ship",
                "SELECT SHIP",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            return new RedShip();
        } 
        else if(choice==1){
            return new GrayShip();
        }
        else{
            return null;
        }
    }
}