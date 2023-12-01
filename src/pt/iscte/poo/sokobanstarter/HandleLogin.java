package pt.iscte.poo.sokobanstarter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import pt.iscte.poo.gui.ImageMatrixGUI;

public class HandleLogin {

	/* [FUNCTION THAT CHECKS IF THE PASSWORD IS CORRECT] */
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
			System.out.println("Creating users.txt file");
		}

		return false;
	}

	/* [FUNCTION THAT CREATES AN USER AND ADDS IT TO users/users.txt FILE] */
	public static void createUser(String userName, String userPassword) {
		String folderPath = "users/";
		String fileName = folderPath + "users.txt";

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {

			writer.write(userName.toLowerCase() + ":" + userPassword + System.lineSeparator());

			writer.close();
			System.out.println("User added to: " + fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* [CHECKS IF AN USER EXISTS ON THE USERS FILE] */
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
			System.out.println("Creating users.txt file...");
		}

		return false;
	}

	/* [LOGIN FUNCTION] */
	public static User login() {
		String userPassword = "";
		String userName = ImageMatrixGUI.getInstance().askUser("Username:");

		if(userName == null) throw new IllegalArgumentException("You canceled your Login");
		if(userName.contains(":")) throw new IllegalArgumentException("Name cannot contain ':'");

		if(userName.length() == 0)throw new IllegalArgumentException("User cannot be empty");

		if(existsUser(userName)){
			ImageMatrixGUI.getInstance().setMessage("Existing user, enter password");
			while(userPassword.equals("")){
				
				userPassword = ImageMatrixGUI.getInstance().askUser("Password:");
				if(userPassword == null) throw new IllegalArgumentException("You have canceled your Login");


				if(correctPassword(userName, userPassword)){
					ImageMatrixGUI.getInstance().setMessage("Have a good game!");
					return new User(userName, userPassword);
				} 
				
				userPassword = "";
				ImageMatrixGUI.getInstance().setMessage("Wrong Password");
			}

		} else {
			ImageMatrixGUI.getInstance().setMessage("New user, create a new password!");
			userPassword = ImageMatrixGUI.getInstance().askUser("Password:");
			createUser(userName, userPassword);
			return new User(userName, userPassword);

		}
		return null;
	}


}
