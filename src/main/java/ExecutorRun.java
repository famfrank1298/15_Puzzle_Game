import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Consumer;

public class ExecutorRun implements Runnable{
	
	ExecutorService ex = Executors.newFixedThreadPool(5);
	
	int[] puzzle;
	String solver;
	private Consumer<ArrayList<Nodes>> callback;
	
	public ExecutorRun(int[] puz, String n, Consumer<ArrayList<Nodes>> call) {
		this.puzzle = puz;
		this.solver = n;
		callback = call;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Future<ArrayList<Nodes>> arr = ex.submit(new MyCall(puzzle, solver));
		//System.out.println("After ex.submit...");
	
		try {
			//System.out.println("Inside try block");
			ArrayList<Nodes> temp = arr.get();
			//System.out.println("Before callback...");
			callback.accept(temp);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ex.shutdown();
	}
}
