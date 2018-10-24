package ce325.hw1;

public class ArithmeticOperators{
	private Character symbol;	//operator
	private int index;	//index indicates the position of the operator
	private int level;	//the number of parentheses that the operator is into
	private int priority; //priority of the operator(1 is lowest(+), 5 is highest(^))

	//class constructor
	public ArithmeticOperators(Character symbol, int index, int level, int priority){
		this.symbol = symbol;
		this.index = index;
		this.level = level;
		this.priority = priority;
	}

	public Character getSymbol(){
		return symbol;
	}

	public int getIndex(){
		return index;
	}

	public int getLevel(){
		return level;
	}

	public int getPriority(){
		return priority;
	}

	public void setSymbol(Character symbol){
		this.symbol = symbol;
	}

	public void setIndex(int index){
		this.index = index;
	}

	public void setLevel(int level){
		this.level = level;
	}

	public void setPriority(int priority){
		this.priority = priority;
	}

	public String toString() {
		return "(Symbol: " + symbol + ", Index: " + index + ", Level: " + level + ", Priority: " + priority + ")";
	}
}
