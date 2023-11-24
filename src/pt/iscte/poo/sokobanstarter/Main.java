package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.gui.ImageMatrixGUI;

public class Main {

    public static void main(String[] args) {
        Utilizador user = login();
        
        if (user == null) {
            System.out.println("Utilizador cancelou o Login");
            return;
        }

        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.setCurrentUser(user);
        gameEngine.start();
    }

    public static Utilizador login() {
        String username = ImageMatrixGUI.getInstance().askUser("Username:");
        String password = ImageMatrixGUI.getInstance().askUser("Password:");

        // Check if the user pressed Cancel
        if (username == null || password == null) {
            return null;
        }

        return new Utilizador(username, password);
    }
}