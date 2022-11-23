import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PuzzleButton extends Button {
	private Button b;
	private int xRow;
	private int yCol;
	private int pieceNum;
	private int wantedX;
	private int wantedY;
	
	public PuzzleButton(int x, int y, int n) {
		this.xRow = x;
		this.yCol = y;
		this.pieceNum = n;
		this.b = new Button();
		b.setFont(Font.font(null, FontWeight.BOLD, 34));
		b.setText(String.valueOf(n));
		b.setStyle("-fx-text-fill: #0000ff");
		b.setPrefHeight(100);
		b.setPrefWidth(100);
		b.setOpacity(50);
		setBackground();
	}
	
	// returns a String that holds the image url of each puzzle piece's background 
	public String setBackground() {
		
		String text = "Moana_";
		text = text + String.valueOf(pieceNum) + ".0.jpg";
		
		BackgroundImage img = new BackgroundImage(new Image(text, 100, 100, false, false), BackgroundRepeat.NO_REPEAT,
		BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
							
		Background img_bg = new Background(img);					
		b.setBackground(img_bg);
		
		return text;
	}
	
	public Button getButton() {
		return this.b;
	}
	
	public int getWantedX() {
		return this.wantedX;
	}
	
	public int getWantedY() {
		return this.wantedY;
	}
	
	public int getXRow() {
		return this.xRow;
	}
	
	public int getYCol() {
		return this.yCol;
	}
	
	public int getPieceNum() {
		return this.pieceNum;
	}
	
	public void setXRow(int x) {
		this.xRow = x;
	}
	
	public void setYCol(int y) {
		this.yCol = y;
	}
	
	public void setPieceNum(int n) {
		this.pieceNum = n;
		this.b.setText(String.valueOf(n));
		setBackground();
	}
	
} 



