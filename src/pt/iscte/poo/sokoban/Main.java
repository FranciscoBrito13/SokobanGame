package pt.iscte.poo.sokoban;

public class Main {

	/* [RUNS THE GAME] */
    public static void main(String[] args) {
        User user = HandleLogin.login();
        
        if (user == null) {
            System.out.println("User canceled Login");
            return;
        }

        GameEngine gameEngine = GameEngine.getInstance();
        gameEngine.setCurrentUser(user);
        gameEngine.start();
    }


}