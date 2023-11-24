package pt.iscte.poo.sokobanstarter;

public class Main {

    public static void main(String[] args) {
        Utilizador user = HandleLogin.login();
        
        if (user == null) {
            System.out.println("Utilizador cancelou o Login");
            return;
        }

        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.setCurrentUser(user);
        gameEngine.start();
    }


}