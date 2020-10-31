package machine;
/**
 * @author abuzreaq
 *
 */
import java.util.List;

public class Bucket {

	private Product product;
	private List<Coin> coin;
	private List<Note> note;
	
	
	public Bucket () {
		
	}
	
	
	// list of coin and notes 
	
	public Bucket(Product product, List<Coin> coines, List<Note> notes) {
		// TODO Auto-generated constructor stub
		this.product = product;
		this.coin = coines;
		this.note= notes;
	}
	
	public Bucket(List<Coin> coins) {
		// TODO Auto-generated constructor stub
		this.coin = coins;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public List<Coin> getCoin() {
		return coin;
	}
	public void setCoin(List<Coin> coin) {
		this.coin = coin;
	}
	
	public List<Note> getNotes() {
		return note;
	}
	public void setNote(List<Note> note) {
		this.note = note;
	}
	
	
}
