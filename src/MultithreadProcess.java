import java.util.concurrent.TimeUnit;

public class MultithreadProcess extends Thread {
	private String name;
	private int requiredResourceA;
	private int requiredResourceB;
	private int requiredResourceC;
	private int burstTime;
	private int[] numResources;
	
	public MultithreadProcess(String name, int requiredResourceA, int requiredResourceB, 
							  int requiredResourceC, int burstTime, int[] numResources) {
		this.name = name;
		this.requiredResourceA = requiredResourceA;
		this.requiredResourceB = requiredResourceB;
		this.requiredResourceC = requiredResourceC;
		this.burstTime = burstTime;
		this.numResources = numResources;
	}

	public int getRequiredResourceA() {
		return requiredResourceA;
	}
	
	public int getRequiredResourceB() {
		return requiredResourceB;
	}
	
	public int getRequiredResourceC() {
		return requiredResourceC;
	}
	
	public int getBurstTime() {
		return burstTime;
	}
	
	public String _getName() {
		return name;
	}
	
	public void setRequiredResourceA(int requiredResourceA) {
		this.requiredResourceA = requiredResourceA;
	}
	
	public void setRequiredResourceB(int requiredResourceB) {
		this.requiredResourceB = requiredResourceB;
	}
	
	public void setRequiredResourceC(int requiredResourceC) {
		this.requiredResourceC = requiredResourceC;
	}
	
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}
	
	public void _setName(String name) {
		this.name = name;
	}
	
	public void run() {
		try {
			Thread.sleep(1000 * getBurstTime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			if (numResources[0] >= getRequiredResourceA() &&
				numResources[1] >= getRequiredResourceB() &&
				numResources[2] >= getRequiredResourceC()) {
					System.out.println(_getName() + " is running.");
					System.out.println("Resources left: " + numResources[0] + " " + numResources[1] + 
							" " + numResources[2]);
					numResources[0] -= getRequiredResourceA();
					numResources[1] -= getRequiredResourceB();
					numResources[2] -= getRequiredResourceC();
					try {
						Thread.sleep(1000 * getBurstTime());
					} catch (InterruptedException e) {
					}
					System.out.println(_getName() + " is finished. Returning resources.");
					System.out.println("Resources: " + numResources[0] + " " + numResources[1] + 
							" " + numResources[2]);
					numResources[0] += getRequiredResourceA();
					numResources[1] += getRequiredResourceB();
					numResources[2] += getRequiredResourceC();
				}
				else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
					System.out.println(_getName() + " is currently waiting for resources.");
				}
		}
	}
}
