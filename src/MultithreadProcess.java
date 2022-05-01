import java.util.concurrent.TimeUnit;

public class MultithreadProcess extends Thread {
	private String name;
	private int requiredResourceA;
	private int requiredResourceB;
	private int requiredResourceC;
	private int currentResourceA;
	private int currentResourceB;
	private int currentResourceC;
	private int burstTime;
	private int status;
	private int[] numResources;
	
	public MultithreadProcess(String name, int requiredResourceA, int requiredResourceB, 
							  int requiredResourceC, int burstTime, int[] numResources) {
		this.name = name;
		this.requiredResourceA = requiredResourceA;
		this.requiredResourceB = requiredResourceB;
		this.requiredResourceC = requiredResourceC;
		this.currentResourceA = 0;
		this.currentResourceB = 0;
		this.currentResourceC = 0;
		this.burstTime = burstTime;
		this.status = 0;
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
	
	public int getCurrentResourceA() {
		return currentResourceA;
	}
	
	public int getCurrentResourceB() {
		return currentResourceB;
	}
	
	public int getCurrentResourceC() {
		return currentResourceC;
	}
	
	public int getBurstTime() {
		return burstTime;
	}
	
	public int getStatus() {
		return status;
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
	
	public void setCurrentResourceA(int currentResourceA) {
		this.currentResourceA = currentResourceA;
	}
	
	public void setCurrentResourceB(int currentResourceB) {
		this.currentResourceB = currentResourceB;
	}
	
	public void setCurrentResourceC(int currentResourceC) {
		this.currentResourceC = currentResourceC;
	}
	
	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}
	
	public void setStatus(int status) {
		// 0 is waiting
		// 1 is running
		// 2 is requesting
		this.status = status;
	}
	
	public void _setName(String name) {
		this.name = name;
	}
	
	public void printStatus() {
		if (getStatus() == 0) {
			System.out.println(_getName() + " Status: Waiting...");
		}
		else if (getStatus() == 1) {
			System.out.println(_getName() + " Status: Running");
		}
		else if (getStatus() == 2) {
			System.out.println(_getName() + " Status: Holding Resources~");
		}
	}
	
	public void run() {
		try {
			Thread.sleep(1000 * getBurstTime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (numResources[0] >= getRequiredResourceA() &&
				numResources[1] >= getRequiredResourceB() &&
				numResources[2] >= getRequiredResourceC()) {
				
				//System.out.println(_getName() + " is running.");
				//System.out.println("Resources left: " + numResources[0] + " " + numResources[1] + 
				//		" " + numResources[2]);
			
				numResources[0] -= getRequiredResourceA();
				numResources[1] -= getRequiredResourceB();
				numResources[2] -= getRequiredResourceC();
				setStatus(1);
				setCurrentResourceA(getRequiredResourceA());
				setCurrentResourceB(getRequiredResourceB());
				setCurrentResourceC(getRequiredResourceC());
				try {
					Thread.sleep(1000 * getBurstTime());
				} catch (InterruptedException e) {
				}
				System.out.println(_getName() + " " + getCurrentResourceA());

				numResources[0] += getCurrentResourceA();
				numResources[1] += getCurrentResourceB();
				numResources[2] += getCurrentResourceC();
				setCurrentResourceA(0);
				setCurrentResourceB(0);
				setCurrentResourceC(0);
				System.out.println(_getName() + " " + getCurrentResourceA());
				setStatus(0);
			}
		}
	}
}
