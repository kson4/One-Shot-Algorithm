import java.util.concurrent.Semaphore;
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
	private boolean canRun;
	private int[] numResources;
	private int numRunned;

	static Semaphore readWrite = new Semaphore(1);
	
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
		this.canRun = false;
		this.numResources = numResources;
		this.numRunned = 0;
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
	
	public boolean getCanRun() {
		return canRun;
	}
	
	public int getNumRunned() {
		return numRunned;
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
		this.status = status;
	}
	
	public void _setName(String name) {
		this.name = name;
	}
	
	public void setCanRun(boolean status) {
		this.canRun = status;
	}
	
	public void setNumRunned(int num) {
		this.numRunned = num;
	}
	
	public void incrementNumRunned() {
		setNumRunned(getNumRunned() + 1);
	}
	
	public void printStatus() {
		if (getStatus() == 0) {
			System.out.println(_getName() + " Status: Waiting...");
		}
		else if (getStatus() == 1) {
			System.out.println(_getName() + " Status: Running");
		}
	}
	
	public boolean checkAvailability() {
		return (numResources[0] >= (getRequiredResourceA() - getCurrentResourceA()) &&
					numResources[1] >= (getRequiredResourceB() - getCurrentResourceB()) &&
					numResources[2] >= (getRequiredResourceC() - getCurrentResourceC()));
	}
	
	public void addResources() {
		numResources[0] -= getRequiredResourceA();
		numResources[1] -= getRequiredResourceB();
		numResources[2] -= getRequiredResourceC();
		setCurrentResourceA(getRequiredResourceA());
		setCurrentResourceB(getRequiredResourceB());
		setCurrentResourceC(getRequiredResourceC());
		incrementNumRunned();
		setCanRun(true);
	}
	
	public void releaseResources() {
		numResources[0] += getRequiredResourceA();
		numResources[1] += getRequiredResourceB();
		numResources[2] += getRequiredResourceC();
		setCurrentResourceA(0);
		setCurrentResourceB(0);
		setCurrentResourceC(0);
		setCanRun(false);
	}
	
	public void run() {
		try {
			Thread.sleep(1000 * getBurstTime());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true) {
			// get read/write permission
			try {
				readWrite.acquire();
				try {
					// once permission is received, check to see if there are enough
					// resources to run
					if (checkAvailability()) {
						// if there are enough resources to run obtain the resources
						addResources();
					}
				}
				// release mutex lock
				finally {
					readWrite.release();
				}
			} catch (InterruptedException e) {
			}
			
			// check to see if process has enough resources
			// if a process has enough resources -> run
			if (getCanRun()) {
				// set status of process to run -> (1)
				setStatus(1);
				try {
					// run the resource depending on its burst time
					Thread.sleep(1000 * getBurstTime());
				} catch (InterruptedException e) {
				}
				
				// once process is done running, it must release allocated resources
				// attempt to obtain read/write permission
				try {
					readWrite.acquire();
					// once permission is obtain, release allocated resources
					try {
						releaseResources();
					}
					// release permission
					finally {
						readWrite.release();
					}
				} catch (InterruptedException e) {
				}
				
				// small buffer to prevent starvation
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
				
				// set process status to waiting -> (0)
				setStatus(0);
			}
		}
	}
}
