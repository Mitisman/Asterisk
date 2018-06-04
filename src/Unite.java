
public class Unite {
	
	public boolean isOnField() {
		return onField;
	}
	public void setOnField(boolean onField) {
		this.onField = onField;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getPower() {
		return Power;
	}
	public void setPower(int power) {
		Power = power;
	}
	public int getPrioriteDEF() {
		return prioriteDEF;
	}
	public void setPrioriteDEF(int prioriteDEF) {
		this.prioriteDEF = prioriteDEF;
	}
	public int getPrioriteATT() {
		return prioriteATT;
	}
	public void setPrioriteATT(int prioriteATT) {
		this.prioriteATT = prioriteATT;
	}
	public int getCout() {
		return cout;
	}
	
	public int getMvtRestants() {
		return MvtRestants;
	}
	
	public void setMvtRestants(int nbr) {
		this.MvtRestants=nbr;
	}
	
	public Unite(boolean onField, String type, int iD, int power, int prioriteDEF, int prioriteATT, int cout) {
		super();
		this.onField = onField;
		this.type = type;
		ID = iD;
		Power = power;
		this.prioriteDEF = prioriteDEF;
		this.prioriteATT = prioriteATT;
		this.cout = cout;
	}

	boolean onField;
	String type;
	int ID;
	int Power;
	int prioriteDEF;
	int prioriteATT;
	int cout;
	int MvtRestants;
	
	
}
//
