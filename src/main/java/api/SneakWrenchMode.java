package api;

public enum SneakWrenchMode {
	DISMANTE("Dismantle");
	
	private final String NAME;       

    private SneakWrenchMode(String s) {
    	NAME = s;
    }
    public boolean equals(String otherName) {
        return (otherName == null) ? false : NAME.equals(otherName);
    }
    public String toString() {
       return NAME;
    }
}