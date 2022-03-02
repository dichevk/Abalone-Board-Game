package Pieces;

public class Marble {

	private String color;
	private String symbol;

	public Marble(String color, String symbol) {
		setSymbol(symbol);
		setColor(color);
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "Marble [color=" + color + ", symbol=" + symbol + "]";
	}

}
