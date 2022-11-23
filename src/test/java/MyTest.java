import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {
	static int[] solution = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
	
	static int[] puzzleOne = {4, 2, 0, 7, 9, 1, 3, 6, 8, 11, 13, 15, 5, 12, 14, 10};
	static Nodes testOne = new Nodes(puzzleOne);
	static ArrayList<Nodes> pathOne;
	static A_IDS_A_15solver idsOne = new A_IDS_A_15solver();
	
	static int[] puzzleTwo = {0, 4, 2, 3, 5, 9, 6, 1, 12, 7, 13, 10, 14, 8, 15, 11};
	static ArrayList<Nodes> pathTwo;
	static Nodes testTwo = new Nodes(puzzleTwo);
	static A_IDS_A_15solver idsTwo = new A_IDS_A_15solver();
	
	static int[] puzzleThree = {4, 2, 6, 3, 5, 1, 7, 11, 12, 8, 14, 10, 9, 13, 15, 0};
	static ArrayList<Nodes> pathThree;
	static Nodes testThree = new Nodes(puzzleThree);
	static A_IDS_A_15solver idsThree = new A_IDS_A_15solver();
	
	static int[] puzzleFour = {0, 1, 3, 7, 4, 9, 2, 11, 5, 13, 6, 15, 8, 12, 14, 10};
	static ArrayList<Nodes> pathFour;
	static Nodes testFour = new Nodes(puzzleFour);
	static A_IDS_A_15solver idsFour = new A_IDS_A_15solver();
	
	static int[] puzzleFive = {4, 2, 3, 7, 8, 5, 1, 6, 9, 10, 11, 15, 0, 12, 13, 14};
	static ArrayList<Nodes> pathFive;
	static Nodes testFive = new Nodes(puzzleFive);
	static A_IDS_A_15solver idsFive = new A_IDS_A_15solver();
	
	static int[] puzzleSix = {4, 1, 3, 0, 5, 9, 2, 6, 8, 10, 14, 7, 12, 13, 15, 11};
	static ArrayList<Nodes> pathSix;
	static Nodes testSix = new Nodes(puzzleSix);
	static A_IDS_A_15solver idsSix = new A_IDS_A_15solver();
	
	static int[] puzzleSeven = {8, 1, 4, 2, 9, 5, 7, 3, 12, 14, 6, 10, 13, 0, 15, 11};
	static ArrayList<Nodes> pathSeven;
	static Nodes testSeven = new Nodes(puzzleSeven);
	static A_IDS_A_15solver idsSeven = new A_IDS_A_15solver();
	
	static int[] puzzleEight = {8, 4, 2, 6, 0, 1, 10, 3, 12, 5, 14, 7, 13, 9, 15, 11};
	static ArrayList<Nodes> pathEight;
	static Nodes testEight = new Nodes(puzzleEight);
	static A_IDS_A_15solver idsEight = new A_IDS_A_15solver();
	
	static int[] puzzleNine = {5, 4, 7, 2, 12, 1, 9, 3, 13, 6, 15, 11, 10, 8, 14, 0};
	static ArrayList<Nodes> pathNine;
	static Nodes testNine = new Nodes(puzzleNine);
	static A_IDS_A_15solver idsNine = new A_IDS_A_15solver();
	
	static int[] puzzleTen = {3, 8, 5, 15, 4, 0, 1, 2, 9, 13, 7, 6, 12, 14, 11, 10};
	static ArrayList<Nodes> pathTen;
	static Nodes testTen = new Nodes(puzzleTen);
	static A_IDS_A_15solver idsTen = new A_IDS_A_15solver();
	
	@BeforeAll
	static void setUp() {
		pathOne = idsOne.A_Star(testOne, "heuristicOne");
		pathTwo = idsTwo.A_Star(testOne, "heuristicTwo");
		pathThree = idsThree.A_Star(testOne, "heuristicOne");
		pathFour = idsFour.A_Star(testOne, "heuristicTwo");
		pathFive = idsFive.A_Star(testOne, "heuristicOne");
		
		pathSix = idsSix.A_Star(testOne, "heuristicTwo");
		pathSeven = idsSeven.A_Star(testOne, "heuristicOne");
		pathEight = idsEight.A_Star(testOne, "heuristicTwo");
		pathNine = idsNine.A_Star(testOne, "heuristicOne");
		pathTen = idsTen.A_Star(testOne, "heuristicTwo");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
	void test1(int val) {
		assertEquals(solution[val], pathOne.get(pathOne.size() - 1).getKey()[val], "TestOne: DIDNT SOLVE");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
	void test2(int val) {
		assertEquals(solution[val], pathTwo.get(pathTwo.size() - 1).getKey()[val], "TestTwo: DIDNT SOLVE");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
	void test3(int val) {
		assertEquals(solution[val], pathThree.get(pathThree.size() - 1).getKey()[val], "TestThree: DIDNT SOLVE");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
	void test4(int val) {
		assertEquals(solution[val], pathFour.get(pathFour.size() - 1).getKey()[val], "TestFour: DIDNT SOLVE");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
	void test5(int val) {
		assertEquals(solution[val], pathFive.get(pathFive.size() - 1).getKey()[val], "TestFive: DIDNT SOLVE");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
	void test6(int val) {
		assertEquals(solution[val], pathSix.get(pathSix.size() - 1).getKey()[val], "TestSix: DIDNT SOLVE");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
	void test7(int val) {
		assertEquals(solution[val], pathSeven.get(pathSeven.size() - 1).getKey()[val], "TestSeven: DIDNT SOLVE");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
	void test8(int val) {
		assertEquals(solution[val], pathEight.get(pathEight.size() - 1).getKey()[val], "TestEight: DIDNT SOLVE");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
	void test9(int val) {
		assertEquals(solution[val], pathNine.get(pathNine.size() - 1).getKey()[val], "TestNine: DIDNT SOLVE");
	}
	
	@ParameterizedTest
	@ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15})
	void test10(int val) {
		assertEquals(solution[val], pathTen.get(pathTen.size() - 1).getKey()[val], "TestTen: DIDNT SOLVE");
	}

}
