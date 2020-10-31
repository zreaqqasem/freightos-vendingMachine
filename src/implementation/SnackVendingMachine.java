package implementation;
/**
 * @author abuzreaq
 *
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import api.Inventory;
import api.VendingMachine;
import errors.ItemNotSelectedException;
import errors.NotAChangedException;
import errors.NotFullPaidException;
import errors.ProductNotFoundExcepton;
import machine.Bucket;
import machine.Coin;
import machine.Note;
import machine.Product;
import util.InventoryUtil;

public class SnackVendingMachine implements VendingMachine {
	public  Inventory<Product, Integer> itemInventory= new Inventory<Product, Integer>();
	private Inventory<Coin, Integer> coinInventory = new Inventory<Coin, Integer>();
	private Inventory<Note, Integer> noteInventory = new Inventory<Note, Integer>();
	private Product currentItem;
	private int currentBalance;
	
	public SnackVendingMachine() {
		fillVindingMachine();
	}
	
	private void fillVindingMachine() {
		// first row
		this.itemInventory.putInventory(new Product("PEPSI", 30), 30);
		this.itemInventory.putInventory(new Product("COCKACOLA", 10), 10);
		this.itemInventory.putInventory(new Product("PEER", 50), 10);
		this.itemInventory.putInventory(new Product("JUICE", 100), 10);
		this.itemInventory.putInventory(new Product("WATER", 70), 10);
		// second row

		this.itemInventory.putInventory(new Product("LAYS", 80), 10);
		this.itemInventory.putInventory(new Product("DORATOS", 90), 10);
		this.itemInventory.putInventory(new Product("CHEPSI", 100), 10);
		this.itemInventory.putInventory(new Product("SNACKS", 120), 10);
		this.itemInventory.putInventory(new Product("PRINGLES", 130), 10);
		
		// third row

		this.itemInventory.putInventory(new Product("TEST", 60), 10);
		this.itemInventory.putInventory(new Product("TEST1", 70), 10);
		this.itemInventory.putInventory(new Product("TEST2", 80), 10);
		this.itemInventory.putInventory(new Product("TEST3", 33), 10);
		this.itemInventory.putInventory(new Product("TEST4", 22), 10);
		
		// fourth row

		this.itemInventory.putInventory(new Product("TEST5", 66),10);
		this.itemInventory.putInventory(new Product("TEST6", 70), 10);
		this.itemInventory.putInventory(new Product("TEST7", 35), 10);
		this.itemInventory.putInventory(new Product("TEST8", 66), 10);
		this.itemInventory.putInventory(new Product("TEST9", 66), 10);
		
		
		// fifth row
		
		this.itemInventory.putInventory(new Product("TEST10", 33), 10);
		this.itemInventory.putInventory(new Product("TEST11", 43), 10);
		this.itemInventory.putInventory(new Product("TEST12", 54), 10);
		this.itemInventory.putInventory(new Product("TEST13", 66), 10);
		this.itemInventory.putInventory(new Product("TEST14", 65), 10);

		// VM cash 
		
		this.coinInventory.putInventory(Coin.TENCENTS, 10);
		this.coinInventory.putInventory(Coin.ONEDOLLAR, 10);
		this.coinInventory.putInventory(Coin.FIFTYCENTS,10);
		this.coinInventory.putInventory(Coin.TWENTYCENTS,10);
		this.coinInventory.putInventory(Coin.TWENTYCENTS,10);
		this.noteInventory.putInventory(Note.FIFTYDOLLARS,10);
		this.noteInventory.putInventory(Note.TWENTYDOLLARS,1);

		
		this.setCurrentBalance();
	}
	
	

	private void setCurrentBalance() {
		
		if(this.coinInventory.getInvetory().size() >0 || this.noteInventory.getInvetory().size() >0){
		List<Integer> cashCoinList = this.coinInventory.getInvetory().entrySet()
				.stream().map(e->e.getKey().getCoinValue()*e.getValue())
				.collect(Collectors.toList());
		
		List<Integer> cashNotesList = this.noteInventory.getInvetory().entrySet()
				.stream().map(e->e.getKey().getNotesValue()*e.getValue())
				.collect(Collectors.toList());
		
		
		Optional<Integer> currentBalanceNotes = cashNotesList.stream().reduce(Integer::sum);			
		Optional<Integer> currentBalanceCoins = cashCoinList.stream().reduce(Integer::sum);	
		
		this.currentBalance = currentBalanceCoins.get().intValue() + currentBalanceNotes.get().intValue();
		
		}
		
		
	}
	

	@Override
	public int selectItemGetPrice(Product product) throws ProductNotFoundExcepton {
		List<Entry<Product, Integer>> productPrice = this.itemInventory.getInvetory().entrySet().stream().filter(e->e.getKey().getItemName().equals(product.getItemName())).collect(Collectors.toList());
		if(!productPrice.isEmpty()){
		Product selectedProduct = productPrice.get(0).getKey();
		this.currentItem = selectedProduct;
		return (int)selectedProduct.getItemPrice();
		}else{
			System.out.println("Product Not available");
			throw new ProductNotFoundExcepton("Product Not available");
		}
	}
	
	
	public void displayInsertedMoney(List<Coin> coins ,List<Note> notes){
		Optional<Integer> insertedCoinValue = coins.stream().map(e->e.getCoinValue()).collect(Collectors.toList()).stream().reduce(Integer::sum);
		Optional<Integer> insertedNoteValue = notes.stream().map(e->e.getNotesValue()).collect(Collectors.toList()).stream().reduce(Integer::sum);

		int insertedCoinsValue = insertedCoinValue.get().intValue();
		int insertedNotesValue = insertedNoteValue.get().intValue();

		
		System.out.println("\n Inserted Coin Value: "+insertedCoinsValue+"\n");
		System.out.println("\n Inserted Notes Value: "+insertedNotesValue/100 + "$"+"\n");

		
	}
		
	@Override
	public Optional<Bucket> insertMoney(List<Coin> coins ,List<Note> notes) throws ItemNotSelectedException, NotFullPaidException{
		Bucket bucket= null;
		if(currentItem != null){
			Optional<Integer> insertedCoinValue = coins.stream().map(e->e.getCoinValue()).collect(Collectors.toList()).stream().reduce(Integer::sum);
			Optional<Integer> insertedNoteValue = notes.stream().map(e->e.getNotesValue()).collect(Collectors.toList()).stream().reduce(Integer::sum);

			int insertedCoinsValue = insertedCoinValue.get().intValue();
			int insertedNotesValue = insertedNoteValue.get().intValue();
			
			
			if(insertedCoinsValue < this.currentItem.getItemPrice() && insertedNotesValue<this.currentItem.getItemPrice()){
				bucket=new Bucket(new Product("Not a fullPaid"), coins, notes);
			}else{
				try {
					
					bucket = this.getItemsAndChange(insertedCoinsValue,insertedNotesValue);
				} catch (NotAChangedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		
		Bucket returnBucket =  bucket==null ?new Bucket(new Product("You Are Not Select Any Item"),coins, notes):bucket;
		 Optional<Bucket> opt = Optional.ofNullable(returnBucket);
		return opt;
	}
	
	
	

	@Override
	public Bucket getItemsAndChange(int insertedCoins,int insertedNotes) throws NotAChangedException {
		// TODO Auto-generated method stub
		this.addToCashInventory(insertedCoins+insertedNotes);
		this.setCurrentBalance();
		
		int changeValue = this.getChanged(insertedCoins+insertedNotes, (int) this.currentItem.getItemPrice());
		this.subtractCoinsFromInventory(changeValue);
		this.currentBalance = this.currentBalance-changeValue;
		
		System.out.println(changeValue);

		System.out.println(changeValue % 100);

		
		this.removedItemFromInventory();
		
		InventoryUtil uitls = new InventoryUtil();
		return new Bucket(this.currentItem, uitls.convertToCoin(new ArrayList<Coin>(),changeValue % 100),uitls.convertToNotes(new ArrayList<Note>(), changeValue));
	}
	

	private void removedItemFromInventory(){
		int itemCount= this.itemInventory.getInvetory().get(currentItem);
		this.itemInventory.getInvetory().put(currentItem, itemCount-1);
	}
	
	
	private void subtractCoinsFromInventory(int changedValue){
		
		if(changedValue>=Coin.FIFTYCENTS.getCoinValue()){
			int value =  this.putCoinAndDecrement(Coin.FIFTYCENTS, changedValue);
			if(value!=0){
				subtractCoinsFromInventory(value);
			}

		}else if(changedValue>=Coin.ONEDOLLAR.getCoinValue()){
			int value = this.putCoinAndDecrement(Coin.ONEDOLLAR, changedValue);
			if(value!=0){
				subtractCoinsFromInventory(value);
			}

		}else if(changedValue>=Coin.TENCENTS.getCoinValue()){
			int value = this.putCoinAndDecrement(Coin.TENCENTS, changedValue);
			if(value!=0){
				subtractCoinsFromInventory(value);
			}

		}else if(changedValue>=Coin.TWENTYCENTS.getCoinValue()){
			int value = this.putCoinAndDecrement(Coin.TWENTYCENTS, changedValue);
			if(value!=0){
				subtractCoinsFromInventory(value);
			}
		}
		
		else subtractNoteFromInventory (changedValue);
		
	}
	
	private void subtractNoteFromInventory(int changedValue) {
		
		 if (changedValue>=Note.FIFTYDOLLARS.getNotesValue()){
			int value  = this.putNotesAndDecrement(Note.FIFTYDOLLARS, changedValue);
			if(value !=0){
				subtractCoinsFromInventory(value);
			}

		}else if(changedValue>=Note.TWENTYDOLLARS.getNotesValue()){
			int value = this.putNotesAndDecrement(Note.TWENTYDOLLARS, changedValue);
			if(value!=0){
				subtractCoinsFromInventory(value);
			}
		}
	}
	
	private void addToCashInventory(int insertedMoney){
			if(insertedMoney>=Coin.TENCENTS.getCoinValue()){
				int value = this.putCoinAndIncreament(Coin.TENCENTS, insertedMoney);
				if(value!=0){
					addToCashInventory(value);
				}

			}else if(insertedMoney>=Coin.FIFTYCENTS.getCoinValue()){
				int value = this.putCoinAndIncreament(Coin.FIFTYCENTS, insertedMoney);
				if(value!=0){
					addToCashInventory(value);
				}
			}else if(insertedMoney>=Coin.TWENTYCENTS.getCoinValue()){
				int value = this.putCoinAndIncreament(Coin.TWENTYCENTS, insertedMoney);
				if(value!=0){
					addToCashInventory(value);
				}
			}else if(insertedMoney>=Coin.ONEDOLLAR.getCoinValue()){
				int value = this.putCoinAndIncreament(Coin.ONEDOLLAR, insertedMoney);
				if(value!=0){
					addToCashInventory(value);
				}
				
			}else if(insertedMoney>=Note.FIFTYDOLLARS.getNotesValue()){
				int value = this.putNotesAndIncrement(Note.FIFTYDOLLARS, insertedMoney);
				if(value!=0){
					addToCashInventory(value);
				}
			}else if(insertedMoney>=Note.TWENTYDOLLARS.getNotesValue()){
				int value = this.putNotesAndIncrement(Note.TWENTYDOLLARS, insertedMoney);
				if(value!=0){
					addToCashInventory(value);
				}
			}
	}
	
	private int putCoinAndDecrement(Coin coin, int changedValue){
		int reminder = changedValue/coin.getCoinValue();
		int numberOfCoin =this.coinInventory.getInvetory().get(coin);
		if(numberOfCoin>reminder)
			numberOfCoin=numberOfCoin-reminder;
		this.coinInventory.getInvetory().put(coin, numberOfCoin);
		int value  =  changedValue-(reminder*coin.getCoinValue());
		return value;
	}
	
	private int putNotesAndDecrement(Note note, int changedValue){
		int reminder = changedValue/note.getNotesValue();
		int numberOfNote =this.noteInventory.getInvetory().get(note);
		if(numberOfNote>reminder)
			numberOfNote=numberOfNote-reminder;
		this.noteInventory.getInvetory().put(note, numberOfNote);
		int value  =  changedValue-(reminder*note.getNotesValue());
		return value;
	}
	
	private int putCoinAndIncreament(Coin coin, int insertedCoinValue){
		int reminder = insertedCoinValue/coin.getCoinValue();
		int numberOfCoin =this.coinInventory.getInvetory().get(coin);
		numberOfCoin=reminder+numberOfCoin;
		this.coinInventory.getInvetory().put(coin, numberOfCoin);
		int value  =  insertedCoinValue-(reminder*coin.getCoinValue());
		return value;
	}
	
	
	private int putNotesAndIncrement(Note note, int insertedCoinValue){
		int reminder = insertedCoinValue/note.getNotesValue();
		int numberOfNote =this.noteInventory.getInvetory().get(note);
		numberOfNote=reminder+numberOfNote;
		this.noteInventory.getInvetory().put(note, numberOfNote);
		int value  =  insertedCoinValue-(reminder*note.getNotesValue());
		return value;
	}
	private int getChanged(int insertedValue, int itemPrice){
		if(insertedValue > itemPrice){
			return insertedValue-itemPrice;
		}else{
			
			return itemPrice - insertedValue;
		}
	}
	public void reset() {
		this.currentItem = null;
	}

}