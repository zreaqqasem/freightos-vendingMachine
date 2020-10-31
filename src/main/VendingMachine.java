package main;
/**
 * @author abuzreaq
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import implementation.SnackVendingMachine;
import machine.Bucket;
import machine.Coin;
import machine.Note;
import machine.Product;

public class VendingMachine {
	
	public static void main(String[] args) {
		
		SnackVendingMachine vendingMachine = new SnackVendingMachine();
			
			System.out.println("\t############################");
			vendingMachine.itemInventory.getInvetory().keySet().forEach((product)-> {
				
				System.out.println("\t#");
					System.out.println("\t"+product.getItemName().toString() +
							" |" +"  it's price | " + (product.getItemPrice() >
							100 ? product.getItemPrice()/100+"$" :product.getItemPrice()+"C") );
									
			});
			
			
			runMain(vendingMachine);
			
			}
		
	public static void runMain (SnackVendingMachine vendingMachine) {
		
		int choice = 0;
		
		System.out.println("\t############################");

		System.out.println("\n Select item : ");
		
		Scanner x = new Scanner(System.in);
		
		choice = x.nextInt();
		
		x.close();
		switch (choice) {
		
		case 1 :
			
			chooceItem (vendingMachine,"TEST3");
			break;
			
		case 2 :
			chooceItem (vendingMachine,"COCKACOLA");
			break;
			
		case 3:
			chooceItem (vendingMachine,"TEST2");
			break;
			
		case 4:
			chooceItem (vendingMachine,"PEPSI");
			break;
			
		case 5 :
			chooceItem (vendingMachine,"TEST12");
			break;
			
		case 6 :
			chooceItem (vendingMachine,"PRINGLES");
			break;
			
		case 7 :
			chooceItem (vendingMachine,"TEST6");
			break;
			
		case 8 :
			chooceItem (vendingMachine,"WATER");
			break;
			
		case 9 :
			chooceItem (vendingMachine,"CHEPSI");
			break;
			
		case 10 :
			chooceItem (vendingMachine,"TEST4");
			break;
			
		case 11:
			chooceItem (vendingMachine,"TEST10");
			break;
			
		case 12 :
			chooceItem (vendingMachine,"TEST9");
			break;
			
		case 13 :
			chooceItem (vendingMachine,"JUICE");
			break;
			
		case 14 :
			chooceItem (vendingMachine,"TEST13");
			break;
			
		case 15 :
			chooceItem (vendingMachine,"TEST1");
			break;
			
		case 16 :
			chooceItem (vendingMachine,"DORATOS");
			break;
			
		case 17 :
			chooceItem (vendingMachine,"TEST14");
			break;
			
		case 18 :
			chooceItem (vendingMachine,"TEST");
			break;
			
		case 19 :
			chooceItem (vendingMachine,"LAYS");
			break;
			
		case 20 :
			chooceItem (vendingMachine,"TEST8");
			break;
		case 21 :
			chooceItem (vendingMachine,"PEER");
			break;
		case 22 :
			chooceItem (vendingMachine,"TEST5");
			break;
			
		case 23 :
			chooceItem (vendingMachine,"TEST11");
			break;
				
		case 24 :
			chooceItem (vendingMachine,"SNACKS");
			break;
				
		case 25 :
			chooceItem(vendingMachine,"TEST7");
			break;		
			default:
				
				System.out.println("Invalid input");
				chooceItem(vendingMachine,"noItem");

		}
	}
			
	public static void chooceItem (SnackVendingMachine vendingMachine,String itemName) {
		
		try {
			Product product = new Product(itemName);
			int itemPrice =	vendingMachine.selectItemGetPrice(product);
			System.out.println("Selected Item:"+ product.getItemName());
			System.out.println("Selected Item Price:"+ itemPrice);
		if(itemPrice != 0){
			List <Note> insertedNotes =new ArrayList<Note>();
			List <Coin> insertedCoins = new ArrayList<Coin>();
			
			
			insertedNotes.add(Note.FIFTYDOLLARS);
			insertedCoins.add(Coin.FIFTYCENTS);
			insertedCoins.add(Coin.TWENTYCENTS);
		

			Optional<Bucket> bucket = vendingMachine.insertMoney(insertedCoins, insertedNotes);
			vendingMachine.displayInsertedMoney(insertedCoins,insertedNotes);
			if(bucket.isPresent()){
				Bucket itemBucket = bucket.get();
				if(itemBucket.getProduct() !=null){
					System.out.println(" Return Item: "+ itemBucket.getProduct().getItemName());
					System.out.println("\n Item Price: "+ itemBucket.getProduct().getItemPrice());
					displayInsertedCoinValue(itemBucket.getCoin(),itemBucket.getNotes());
				}
			}
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void displayInsertedCoinValue(List<Coin> coin,List<Note> notes){
		
		Optional<Integer> userInsertedCoinVlue = coin.stream().map(e->e.getCoinValue()).collect(Collectors.toList()).stream().reduce(Integer::sum);
		Optional<Integer> userInsertedNotesVlue = notes.stream().map(e->e.getNotesValue()).collect(Collectors.toList()).stream().reduce(Integer::sum);

				
		System.out.println("\n Return Notes value: "+userInsertedNotesVlue.get()/100 + "$");
		System.out.println("\n Return Coin value: "+userInsertedCoinVlue.get());

	}

}
