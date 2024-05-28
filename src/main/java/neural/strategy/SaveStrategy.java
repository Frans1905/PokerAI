package neural.strategy;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import neural.NeuralNetwork;

public class SaveStrategy implements NetworkStrategy {

	Scanner sc;
	
	public SaveStrategy() {
		sc = new Scanner(System.in);
	}
	
	@Override
	public void accept(NeuralNetwork net) {
		// TODO Auto-generated method stub
		
		String answer = null;
		while(true) {
			System.out.print("Would you like to save the best neural network(yes/no):");
			answer = sc.next();
			if (answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no")) {
				break;
			}
			System.out.printf("Invalid input: %s", answer);
		}
		
		if (answer.equalsIgnoreCase("no")) {
			return;
		}
		
		System.out.print("Network name: ");
		String name = sc.next();
		try (FileOutputStream stream = new FileOutputStream(String.format("/resources/networks/%s.bin",name))) {
			ObjectOutputStream ostream = new ObjectOutputStream(stream);
			ostream.writeObject(net);
			ostream.flush();
			ostream.close();
		}
		catch(Exception e) {
			System.out.println("Could not save to file:");
			e.printStackTrace();
		}
		System.out.println(String.format("Network %s saved sucessfuly!", name));
	}
	
}
