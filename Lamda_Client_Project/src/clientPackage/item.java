package clientPackage;

public abstract class item {
	public static int inventory;
	public int getInventory(){
		return inventory;
	}
	public void addInventory(){
		inventory++;
	}
	public void addInventory(int n){
		inventory+=n;
	}

}
