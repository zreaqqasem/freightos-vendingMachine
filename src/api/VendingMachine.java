package api;
/**
 * @author abuzreaq
 *
 */
import java.util.List;
import java.util.Optional;
import errors.ItemNotSelectedException;
import errors.NotAChangedException;
import errors.NotFullPaidException;
import errors.ProductNotFoundExcepton;
import machine.Bucket;
import machine.Coin;
import machine.Note;
import machine.Product;
public interface VendingMachine {
	public int selectItemGetPrice(Product product) throws ProductNotFoundExcepton;
	public Optional<Bucket> insertMoney(List<Coin> coins ,List<Note> notes) throws ItemNotSelectedException, NotFullPaidException;
	public Bucket getItemsAndChange(int insertedCoins,int incertedNotes) throws NotAChangedException ;
	
}