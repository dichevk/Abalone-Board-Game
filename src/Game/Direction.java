package Game;

public enum Direction {
	LEFT("left"), RIGHT("right"), UPLEFT("upleft"), UPRIGHT("upright"), DOWNLEFT("downleft"),
	DOWNRIGHT("downright");
	 
	
	private String value;

	Direction(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}