package pt.iscte.poo.sokobanstarter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;

public class HandleLogin {

	public static boolean correctPassword(String userName, String userPassword) {
		String folderPath = "users/";
		String fileName = folderPath + "users";

		try (Scanner s = new Scanner(new File(fileName + ".txt"))) {
			while (s.hasNext()) {
				String line = s.nextLine();
				String name = line.split(":")[0];
				String password = line.split(":")[1];

				if(name.equals(userName.toLowerCase()) && password.equals(userPassword)) return true;
			}
		} catch (IOException e) {
			System.out.println("A criar ficherio users.txt");
		}

		return false;
	}

	public static void createUser(String userName, String userPassword) {
		String folderPath = "users/";
		String fileName = folderPath + "users.txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {

			writer.write(userName.toLowerCase() + ":" + userPassword + System.lineSeparator());

			writer.close();
			System.out.println("Utilizador adicionado a: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean existsUser(String user) {
		String folderPath = "users/";
		String fileName = folderPath + "users";

		try (Scanner s = new Scanner(new File(fileName + ".txt"))) {
			while (s.hasNext()) {
				String line = s.nextLine();
				String name = line.split(":")[0];

				if(name.equals(user.toLowerCase())) return true;
			}
		} catch (IOException e) {
			System.out.println("A criar ficherio users.txt...");
		}

		return false;
	}

	public static Utilizador login() {
		String userPassword = "";
		String userName = ImageMatrixGUI.getInstance().askUser("Username:");

		if(userName == null) throw new IllegalArgumentException("Cancelou o seu Login");
		if(userName.contains(":")) throw new IllegalArgumentException("Nome não pode conter ':'");

		if(userName.length() == 0)throw new IllegalArgumentException("Utilizador não pode ser vazio");

		if(existsUser(userName)){
			ImageMatrixGUI.getInstance().setMessage("Utilizador já existente, indique a password");
			while(userPassword.equals("")){
				
				userPassword = ImageMatrixGUI.getInstance().askUser("Password:");
				if(userPassword == null) throw new IllegalArgumentException("Cancelou o seu Login");


				if(correctPassword(userName, userPassword)){
					ImageMatrixGUI.getInstance().setMessage("Have a good game!");
					return new Utilizador(userName, userPassword);
				} 
				
				userPassword = "";
				ImageMatrixGUI.getInstance().setMessage("Password errada");
			}

		} else {
			ImageMatrixGUI.getInstance().setMessage("Novo utlizador, crie uma nova password!");
			userPassword = ImageMatrixGUI.getInstance().askUser("Password:");
			createUser(userName, userPassword);
			return new Utilizador(userName, userPassword);

		}
		return null;
	}


}
