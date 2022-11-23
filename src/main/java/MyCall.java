import java.util.ArrayList;
import java.util.concurrent.Callable;

public class MyCall implements Callable<ArrayList<Nodes>>{

	int[] board;
	String solver; 
	
	MyCall(int[] game, String s) {
		board = game;
		solver = s;
	}
	
	@Override
	public ArrayList<Nodes> call() throws Exception {
		// TODO Auto-generated method stub
		A_IDS_A_15solver ids = new A_IDS_A_15solver();
		//System.out.println();
		Nodes start = new Nodes(board);
		ArrayList<Nodes> solution = ids.A_Star(start, solver);
		//System.out.println("About to return call");
		return solution;
	}
	
	
}
