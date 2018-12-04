package api;

public enum RegularWrenchMode {
	ROTATE("Rotate"), TOGGLE_SIDES("Side Toggle");
	
	private final String NAME;       

    private RegularWrenchMode(String s) {
    	NAME = s;
    }
    public boolean equals(String otherName) {
        return (otherName == null) ? false : NAME.equals(otherName);
    }
    public String toString() {
       return NAME;
    }
}