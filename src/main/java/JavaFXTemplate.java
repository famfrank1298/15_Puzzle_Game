import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {

	int[] puzzle = new int[16]; // holds the order of the current puzzle
	int[][] allPuzzles = new int[10][16]; // holds all the 10 known solvable puzzles
	ArrayList<PuzzleButton> puzzlePieces = new ArrayList<PuzzleButton>(); //holds the 
	// order of the current puzzle with each button's corresponding PuzzleButton
	GridPane gpane = new GridPane();
	int currentPuzzle = 0; 
	Scene game_scene;
	Boolean won;
	Stage primaryStage;
	ExecutorRun prime;
	ExecutorService thread = Executors.newFixedThreadPool(3);
	ArrayList<Nodes> temp;
	PauseTransition pause = new PauseTransition(Duration.seconds(1));
	Boolean solutionSolver = false;
	int count = 0;
	int count10 = 0;
	Boolean solutionRun = false; // true if the solution AI is changing the grid
	Button h1 = new Button("Solve w/ AI H1");
	Button h2 = new Button("Solve w/ AI H2");
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Welcome to Threaded AI 15 Puzzle");

		// Welcome Scene :
		Text welcome_title_screen = new Text(10, 20, "!Threaded AI 15 Puzzle!");
		welcome_title_screen.setFill(Color.GOLD);
		welcome_title_screen.setFont(Font.font(null, FontWeight.BOLD, 50));

		InnerShadow inner = new InnerShadow();
		inner.setOffsetX(3.5);
		inner.setOffsetY(3.5);
		welcome_title_screen.setEffect(inner);
		welcome_title_screen.setTextAlignment(TextAlignment.CENTER);

		Image welcome_bimage = new Image("water_bg.jpg", 650, 550, false, false);
		
		BackgroundImage welcome_background_image = new BackgroundImage(welcome_bimage, BackgroundRepeat.NO_REPEAT,
		BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		
		Background welcome_background = new Background(welcome_background_image);
		
		Button welcome_start_button = new Button("Start");
		welcome_start_button.setPrefWidth(60);
		welcome_start_button.setPrefHeight(40);
		welcome_start_button.setStyle("-fx-font-size: 16");
		welcome_start_button.setStyle("-fx-font-color: blue");
		welcome_start_button.setStyle("-fx-background-color: red");
		welcome_start_button.setFont(Font.font(null, FontWeight.BOLD, 14));
		
		welcome_start_button.setOnAction(e -> {
			gameStage(primaryStage);
		});
		
		Button welcome_htp_button = new Button("How To Play");
		welcome_htp_button.setPrefWidth(100);
		welcome_htp_button.setPrefHeight(40);
		welcome_htp_button.setStyle("-fx-font-size: 14");
		welcome_htp_button.setStyle("-fx-font-color: red");
		welcome_htp_button.setStyle("-fx-background-color: blue");
		welcome_htp_button.setFont(Font.font(null, FontWeight.BOLD, 12));
		
		welcome_htp_button.setOnAction(e->instructionScene(primaryStage));
		
		BorderPane welcome_scene_layout = new BorderPane();
		welcome_scene_layout.setCenter(welcome_title_screen);
		welcome_scene_layout.setAlignment(welcome_title_screen, Pos.CENTER);
		welcome_scene_layout.setAlignment(welcome_htp_button, Pos.BOTTOM_LEFT);
		welcome_scene_layout.setLeft(welcome_htp_button);
		welcome_scene_layout.setAlignment(welcome_start_button, Pos.BOTTOM_RIGHT);
		welcome_scene_layout.setRight(welcome_start_button);
		
		welcome_scene_layout.setPrefSize(650, 550);
		HBox buttons = new HBox(200, welcome_htp_button, welcome_start_button);
		buttons.setAlignment(Pos.CENTER);
		buttons.setPadding(new Insets(10, 20, 10, 20));
		VBox welcome_screen_vbox = new VBox(welcome_title_screen, buttons);
		
		welcome_scene_layout.setPadding(new Insets(10, 20, 10, 20));
		welcome_screen_vbox.setAlignment(Pos.CENTER);
		welcome_screen_vbox.setBackground(welcome_background);
		
		Scene welcome_scene = new Scene(welcome_screen_vbox, 650, 550);
		
		populatePuzzles();
		puzzle = getPuzzle(); // generates puzzle immediately
		
		primaryStage.setScene(welcome_scene);
		primaryStage.show();
	}
	
	// creates the 10 puzzles 
	public void populatePuzzles() {
		
		int[] puzz0 = {3, 8, 5, 15, 4, 0, 1, 2, 9, 13, 7, 6, 12, 14, 11, 10};
		int[] puzz1 = {4, 2, 0, 7, 9, 1, 3, 6, 8, 11, 13, 15, 5, 12, 14, 10};
		int[] puzz2 = {0, 4, 2, 3, 5, 9, 6, 1, 12, 7, 13, 10, 14, 8, 15, 11};
		int[] puzz3 = {4, 2, 6, 3, 5, 1, 7, 11, 12, 8, 14, 10, 9, 13, 15, 0};
		int[] puzz4 = {0, 1, 3, 7, 4, 9, 2, 11, 5, 13, 6, 15, 8, 12, 14, 10};
		int[] puzz5 = {4, 2, 3, 7, 8, 5, 1, 6, 9, 10, 11, 15, 0, 12, 13, 14};
		int[] puzz6 = {4, 1, 3, 0, 5, 9, 2, 6, 8, 10, 14, 7, 12, 13, 15, 11};
		int[] puzz7 = {8, 1, 4, 2, 9, 5, 7, 3, 12, 14, 6, 10, 13, 0, 15, 11};
		int[] puzz8 = {8, 4, 2, 6, 0, 1, 10, 3, 12, 5, 14, 7, 13, 9, 15, 11};
		int[] puzz9 = {5, 4, 7, 2, 12, 1, 9, 3, 13, 6, 15, 11, 10, 8, 14, 0};
		
		allPuzzles[1] = puzz1;
		allPuzzles[2] = puzz2;
		allPuzzles[3] = puzz3;
		allPuzzles[4] = puzz4;
		allPuzzles[5] = puzz5;
		allPuzzles[6] = puzz6;
		allPuzzles[7] = puzz7;
		allPuzzles[8] = puzz8;
		allPuzzles[9] = puzz9;
		allPuzzles[0] = puzz0;
	}
	
	// based on the index of currentPuzzle returns the int array that holds the order of the puzzle numbers
	// if currentPuzzle were to ever exceed 10 a random order for puzzle would be generated (that can be unsolvable)
	public int[] generatePuzzle() {
		if(currentPuzzle < 10) {
			return allPuzzles[currentPuzzle];
		} 
		
		int[] temp = new int[16];
		
		ArrayList<Integer> num = new ArrayList<Integer>(16);
		for (int i = 0; i < 16; ++i) {
			num.add(i);
		}
		
		for(int i = 0; i < 16; i++) {
			Random random  = new Random();
			temp[i] = num.remove(random.nextInt(num.size()));
		} 
		
		return temp;
		
	}
	
	// returns and int array
	public int[] getPuzzle() {
		int[] puzzle = new int[16];
		puzzle = generatePuzzle();
		return puzzle;
	}
	
	// populates the gpane (GridPane) as well as puzzlePieces based on the order of numbers in puzzle (int[]) 
	public GridPane createPuzzle() {
		int count = 0;
		puzzlePieces.clear();
		gpane.getChildren().clear();

		for (int r = 0; r < 4; r++) {
			for (int c = 0; c < 4; c++) {
				PuzzleButton p = new PuzzleButton(r, c, puzzle[count]);	
				puzzlePieces.add(p);
				gpane.add(p.getButton(), c, r);
				count++;
			}
		}
		
		return gpane;
	}
	
	// the scene where the game is being played
	public Scene gameStage(Stage primaryStage) {
		
		Text title = new Text("!Solve the 15 Puzzle!");
		title.setFill(Color.GOLD);
		title.setFont(Font.font(null, FontWeight.BOLD, 34));

		InnerShadow inner = new InnerShadow();
		inner.setOffsetX(3.0);
		inner.setOffsetY(3.0);
		title.setEffect(inner);
		
		createPuzzle();
		
		Button newGame = new Button("New Game");
		newGame.setFont(Font.font(null, FontWeight.BOLD, 12));
		newGame.setStyle("-fx-background-color: white");
		newGame.setPrefHeight(30);
		newGame.setPrefWidth(100);
		newGame.setOnAction(e-> {currentPuzzle++; puzzle = getPuzzle(); loading(primaryStage);}); 
		newGame.setAlignment(Pos.CENTER);
		
		Button solution = new Button("See Solution");
		solution.setFont(Font.font(null, FontWeight.BOLD, 12));
		solution.setStyle("-fx-background-color: white");
		solution.setDisable(true);
		solution.setPrefHeight(30);
		solution.setPrefWidth(110);
		
		h1.setFont(Font.font(null, FontWeight.BOLD, 12));
		h1.setStyle("-fx-background-color: white");
		h2.setFont(Font.font(null, FontWeight.BOLD, 12));
		h2.setStyle("-fx-background-color: white");
		
		h1.setPrefHeight(30);
		h1.setPrefWidth(130);
		h1.setOnAction(x -> {h2.setDisable(true); h1.setDisable(true);
		thread.submit(new ExecutorRun(puzzle, "heuristicOne", 
				data -> {
			Platform.runLater(() -> {solution.setDisable(false); solution.setStyle("-fx-background-color: yellow");
			temp = data;});
				}));});
		
		h2.setPrefHeight(30);
		h2.setPrefWidth(130);
		h2.setOnAction(x -> { h1.setDisable(true); h2.setDisable(true);
		thread.submit(new ExecutorRun(puzzle, "heuristicTwo", 
				data -> {
			Platform.runLater(() -> {solution.setDisable(false); solution.setStyle("-fx-background-color: yellow");
			temp = data;});
				}));});
		
		solution.setOnAction(e -> { count10 = count + 10;
		if (count10 >= temp.size()) {
			count10 = temp.size()-1;
		}
			animatePuzzle(temp); solutionRun = true; solution.setDisable(true); solution.setStyle("-fx-background-color: white");
		h2.setDisable(true); h1.setDisable(true);});
		
		Button end = new Button("Exit");
		end.setFont(Font.font(null, FontWeight.BOLD, 12));
		end.setOnAction(e->Platform.exit());
		end.setStyle("-fx-background-color: white");
		end.setPrefHeight(30);
		end.setPrefWidth(100);
				
		VBox left_buttons = new VBox(newGame, h1, h2);
		left_buttons.setPadding(new Insets(10, 15, 10, 20));
		left_buttons.setSpacing(50);
		VBox right_buttons = new VBox(solution, end);
		right_buttons.setPadding(new Insets(10, 15, 10, 20));
		right_buttons.setSpacing(20);

		HBox allButtons = new HBox(h1, h2, solution, newGame, end);	
		allButtons.setPadding(new Insets(10, 15, 10, 20));
		allButtons.setSpacing(40);
		allButtons.setAlignment(Pos.BOTTOM_CENTER);

		gpane.setAlignment(Pos.CENTER);
		VBox game = new VBox(title, gpane, allButtons);
		game.setAlignment(Pos.CENTER);
		game_scene = new Scene(game, 650, 550);
		
		BackgroundImage background_image = new BackgroundImage( new Image("blue_bg.jpg", 650, 550, false, false),
		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		
		Background background = new Background(background_image);
		game.setBackground(background);
		
		game_scene.setOnKeyPressed(keyListener);
		primaryStage.setScene(game_scene);
		primaryStage.show();
		return game_scene;
	}
	
	// shows the animation for the 10 moves or less when the user selects solve with either ai
	public void animatePuzzle(ArrayList<Nodes> nodes) {
		puzzle = nodes.get(count).getKey();
		gameStage(primaryStage);
		checkWin();
		
		pause.play();
		pause.setOnFinished(e-> {
			if (count < count10) {
				count++;
				animatePuzzle(nodes);				
			} else { // reset
				temp.clear();
				count = 0;
				count10 = 0;
				solutionRun = false;
				h2.setDisable(false);
				h1.setDisable(false);
			}
		});
	}
	
	// loading screen for when the user clicks New Game
	public Scene loading(Stage primaryStage) {
				
		Image bimage = new Image("moana_load.jpg", 650, 550, false, false);
		BackgroundImage background_image = new BackgroundImage(bimage,
		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		
		Background background = new Background(background_image);
		
		Text load_txt = new Text(10, 20, "GENERATING NEW GAME.... ");
		load_txt.setFill(Color.WHITE);
		load_txt.setFont(Font.font(null, FontWeight.BOLD, 32));
		InnerShadow inner = new InnerShadow();
		inner.setOffsetX(1.0);
		inner.setOffsetY(1.0);
		load_txt.setEffect(inner);
		load_txt.setTextAlignment(TextAlignment.CENTER);
		
		VBox load_vbox = new VBox(load_txt);
		load_vbox.setBackground(background);
		load_vbox.setAlignment(Pos.CENTER);
		
		Scene load = new Scene(load_vbox, 650, 550);
		primaryStage.setScene(load);
		primaryStage.show();
		
		PauseTransition pause = new PauseTransition(Duration.seconds(2));
		pause.setOnFinished(event -> {gameStage(primaryStage);});
		pause.play();
		
		return load;
	}
	
	// returns the PuzzleButton that contains the button with the "0" text
	public PuzzleButton findBlank() {
		for(int i = 0; i < puzzlePieces.size(); i++) {
			if(puzzlePieces.get(i).getButton().getText().equals("0")) {
				return puzzlePieces.get(i);
			}
		}

		return null; // should never reach here
	}
	
	// returns the button located at the given x and y coordinate parameters
	public Button getButton(int xBlank, int yBlank) {
		for(Node n : gpane.getChildren()) {
			if(gpane.getRowIndex(n) == xBlank && gpane.getColumnIndex(n) == yBlank) {
				return (Button) n;
			}
		}

		return null; // should never reach here
	}
	
	// returns the position of the button inside the puzzlePieces arraylist
	public int getPuzzlePos(Button x) {
		for(int i = 0; i < puzzlePieces.size(); i++) {
			if(puzzlePieces.get(i).getButton().getText().equals(x.getText())) {
				return i;
			}
		}

		return -999; // never get here
	}
	
	// gives each button piece their background depending on the puzzle number they are
	public String setBackground(int pieceNum) {
		String text = "Moana_";
		text = text + String.valueOf(pieceNum) + ".0.jpg";
		return text;
	}
	
	// swaps the 2 button positions on the grid pane as well as their positions in puzzle and puzzlePieces
	public void swap(Button b, Button s) {
		
		int posBlank = getPuzzlePos(b);
		int posSwap = getPuzzlePos(s);
		
		PuzzleButton blank = puzzlePieces.get(posBlank);
		PuzzleButton swap = puzzlePieces.get(posSwap);
		
		puzzlePieces.set(posBlank, swap);
		puzzlePieces.set(posSwap, blank); 
		
		// updates the 2 integers position in the int[]
		puzzle[posBlank] = Integer.valueOf(swap.getButton().getText());
		puzzle[posSwap] = Integer.valueOf(blank.getButton().getText());
		
		PuzzleButton temp = new PuzzleButton(swap.getXRow(), swap.getYCol(), swap.getPieceNum());
		
		// updates the new value and background at the previous "blank" position
		for(Node n : gpane.getChildren()) {
			Button t = (Button) n;
			if(gpane.getColumnIndex(n) == blank.getYCol() && gpane.getRowIndex(n) == blank.getXRow()) {
				t.setText(swap.getButton().getText());
				swap.setXRow(blank.getXRow());
				swap.setYCol(blank.getYCol());
				
				PuzzleButton newPiece = new PuzzleButton(blank.getXRow(), blank.getYCol(), Integer.valueOf(swap.getButton().getText()));
				puzzlePieces.set(posBlank, newPiece);
				
				BackgroundImage img = new BackgroundImage(new Image(setBackground(Integer.valueOf(swap.getButton().getText())), 100, 100, false, false), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
													
				Background img_bg = new Background(img);					
				t.setBackground(img_bg);
				break;
			}
		}
		
		// updates the "blank" position at the integer it swapped with
		for(Node n : gpane.getChildren()) {
			Button t = (Button) n;
			if(gpane.getColumnIndex(n) == temp.getYCol() && gpane.getRowIndex(n) == temp.getXRow()) {
				t.setText("0");
				blank.setXRow(temp.getXRow());
				blank.setYCol(temp.getYCol());
				
				PuzzleButton newPiece = new PuzzleButton(temp.getXRow(), temp.getYCol(), 0);
				puzzlePieces.set(posSwap, newPiece);
				
				BackgroundImage img = new BackgroundImage(new Image(setBackground(0), 100, 100, false, false), BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
											
				Background img_bg = new Background(img);					
				t.setBackground(img_bg);	
				break;
			}
		}
	}
	
	// displays the instructions scene so the user will understand how the game is played
	public void instructionScene(Stage primaryStage) {
		primaryStage.setTitle("How to Play");
		
		Text objective = new Text("Objective: Move/Slide the zero\ntile around on the grid to order\nthe tiles in order from 0 to 15.");
		objective.setFill(Color.DARKGREEN);
		objective.setFont(Font.font(null, FontWeight.BOLD, 18));
		objective.setTextAlignment(TextAlignment.LEFT);
		
		Image puz_img = new Image("puzzle_img.JPG", 250, 250, false, false);
		ImageView puz_imgView = new ImageView();
		puz_imgView.setImage(puz_img);
		puz_imgView.setPreserveRatio(true);
		
		Image puz_img_keys = new Image("puzzle_buttons.JPG", 500, 40, false, false);
		ImageView puz_imgView_keys = new ImageView();
		puz_imgView_keys.setImage(puz_img_keys);
		puz_imgView_keys.setPreserveRatio(true);
				
		HBox one = new HBox(puz_imgView, objective);
		one.setAlignment(Pos.CENTER);
		one.setSpacing(50);
		one.setPadding(new Insets(10, 15, 10, 20));
		
		Text htp = new Text("How to Move Around Keys:\n");
		htp.setTextAlignment(TextAlignment.CENTER);
		htp.setFill(Color.BLACK);
		htp.setFont(Font.font(null, FontWeight.BOLD, 18));
		
		Text wasd = new Text("W: Moves 0 tile UP\n"
				+ "A: Moves 0 tile to the LEFT\n"
				+ "S: Moves 0 title DOWN\n"
				+ "D: Moves 0 title to the RIGHT");
		wasd.setTextAlignment(TextAlignment.LEFT);
		wasd.setFill(Color.CRIMSON);
		wasd.setFont(Font.font(null, FontWeight.BOLD, 14));
		
		Image wasd_img = new Image("wasd.jpg", 120, 120, false, false);
		ImageView wasd_imgView = new ImageView();
		wasd_imgView.setImage(wasd_img);
		wasd_imgView.setPreserveRatio(true);
		
		HBox two = new HBox(wasd_imgView, wasd);
		two.setAlignment(Pos.CENTER);
		two.setSpacing(50);
		two.setPadding(new Insets(10, 15, 10, 20));
		
		Button ready = new Button("Ready!");
		ready.setOnAction(e->gameStage(primaryStage));
		ready.setAlignment(Pos.BOTTOM_LEFT);
		
		Button ai = new Button("What are the AI Solvers?");
		
		Popup ai_popup = new Popup();
		Label ai_text = new Label("What are the AI Solvers?:\nThey are each their own algorithms that will give u the solution\n"
				+ "for the next 10 steps or less to help you solve the puzzle.\n"
				+ "\nSteps:\n1.Click on either one\n2.Wait for the \"See Solution\" to turn yellow then click!\n3.Then it will show you the next 10 steps\n"
				+ "\nReminder:\n1. The AIs can take a while to solve so the closer\nyou are to solving the puzzle, the quicker it can help you\n"
				+ "2. Dont ask the AI for help if you are not going to click \"See Solution\"\nbecause it will not know if you changed the board around");

		ai_text.setPrefHeight(290);
		ai_text.setPrefWidth(470);
		ai_text.setAlignment(Pos.CENTER);
		ai_text.setStyle("-fx-color: darkBlue");
		ai_text.setStyle("-fx-background-color: white");
		ai_text.setFont(Font.font(null, FontWeight.BOLD, 14));
		ai_popup.getContent().add(ai_text);
		ai_popup.setAutoHide(true);
		
		ai.setOnAction(e -> {
			if(!ai_popup.isShowing()) {
				ai_popup.show(primaryStage);
			} else {
				ai_popup.hide();
			}
		});
		
		HBox three = new HBox(ai, ready);
		three.setAlignment(Pos.CENTER);
		three.setSpacing(50);
		three.setPadding(new Insets(10, 15, 10, 20));
		
		VBox layout = new VBox(one, puz_imgView_keys, htp, two, three);
		layout.setAlignment(Pos.CENTER);
		Image bimage = new Image("blue_bg.jpg", 650, 550, false, false);
		BackgroundImage background_image = new BackgroundImage(bimage,
		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		
		Background background = new Background(background_image);
		layout.setBackground(background);
		
		Scene instr = new Scene(layout, 650, 550);
		primaryStage.setScene(instr);
		primaryStage.show();
	}
	
	// determines whether the puzzle has been solved by checking the order of the arraylist
	public void checkWin() {
		won = false;
		
		for(int i = 0; i < puzzlePieces.size(); i++) {
			if(puzzlePieces.get(i).getPieceNum() == i) {
				won = true;
			} else {
				won = false;
				break;
			}
		}
		
		if(won) {
			PauseTransition pause2 = new PauseTransition(Duration.seconds(1));
			pause2.setOnFinished(e -> winStage(primaryStage));
			pause2.play();
		}		
	}
	
	// displays the win stage where the user can either exit the program or solve another puzzle
	public void winStage(Stage primaryStage) {
		
		primaryStage.setTitle("WinSreen");
		Label win_text = new Label("!CONGRAGULATIONS YOU BEAT THE PUZZLE!");
		
		win_text.setTextFill(Color.BLUE);
		win_text.setFont(Font.font(null, FontWeight.BOLD, 28));
		win_text.setAlignment(Pos.CENTER);
		
		InnerShadow inner = new InnerShadow();
		inner.setOffsetX(5.0);
		inner.setOffsetY(5.0);
		win_text.setEffect(inner);
		
		Image win_bimage = new Image("moana_win.png", 650, 550, false, false);
		
		BackgroundImage win_background_image = new BackgroundImage(win_bimage,
		BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		
		Background win_background = new Background(win_background_image);
		
		Button win_scene_exit = new Button("Exit");
		win_scene_exit.setFont(Font.font("Gill Sans", FontWeight.BOLD, 16));
		win_scene_exit.setMaxWidth(100);
		win_scene_exit.setMaxHeight(75);
		
		win_scene_exit.setOnAction(e -> Platform.exit());
		
		Button win_scene_play = new Button("Solve Another Puzzle!");
		win_scene_play.setFont(Font.font("Gill Sans", FontWeight.BOLD, 16));
		win_scene_play.setMaxWidth(250);
		win_scene_play.setMaxHeight(75);
		
		win_scene_play.setOnAction(e -> {currentPuzzle++; puzzle = getPuzzle(); gameStage(primaryStage);
		});
		
		win_text.setAlignment(Pos.CENTER);
		win_scene_play.setAlignment(Pos.CENTER);
		win_scene_exit.setAlignment(Pos.CENTER);
		
		VBox win_scene_vbox = new VBox(win_text, win_scene_play, win_scene_exit);
		win_scene_vbox.setPadding(new Insets(10, 20, 10, 20));
		win_scene_vbox.setBackground(win_background);
		win_scene_vbox.setAlignment(Pos.CENTER);

		Scene win_scene = new Scene(win_scene_vbox, 650, 550);
		win_scene.setFill(new LinearGradient(
		        0, 0, 1, 1, true,                     
		        CycleMethod.NO_CYCLE,                  
		        new Stop(0, Color.web("#2F66A9")),     
		        new Stop(1, Color.web("#74D5DD")))
		);
		
		primaryStage.setScene(win_scene);
		primaryStage.show();
	}
	
	// event handler for when a key is pressed to swap the 0, "blank", and tile to its respoective postion
	// based on hey pressed
	public EventHandler<KeyEvent> keyListener = new EventHandler<KeyEvent>() {

		@Override
		public void handle(KeyEvent event) {
			// TODO Auto-generated method stub
			if(!solutionRun) { // user will not be to change pieces around until the solution AI is done running
				PuzzleButton pos = findBlank();
				int x = pos.getXRow();
				int y = pos.getYCol();
							
				if(event.getCode() == KeyCode.W) {
					if(x == 0) {
						// do nothing out of bounds
					} else {
						Button blank = getButton(x, y);
						Button swapper = getButton(x-1, y);
						swap(blank, swapper);
						checkWin();
					}
				} else if(event.getCode() == KeyCode.A) {
					if(y == 0) {
						// do nothing out of bounds
					} else {
						Button blank = getButton(x, y);
						Button swapper = getButton(x, y-1);
						swap(blank, swapper);
						checkWin();
					}
				} else if(event.getCode() == KeyCode.D) {
					if(y == 3) {
						// do nothing out of bounds
					} else {
						Button blank = getButton(x, y);
						Button swapper = getButton(x, y+1);
						swap(blank, swapper);
						checkWin();
					}
				} if(event.getCode() == KeyCode.S) {
					if(x == 3) {
						// do nothing out of bounds
					} else {
						Button blank = getButton(x, y);
						Button swapper = getButton(x+1, y);
						swap(blank, swapper);
						checkWin();
					}
				} else {
					//do nothing, not a proper key
				}
			}
			
		}
		
	};

}
