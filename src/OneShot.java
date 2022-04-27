
public class OneShot {
	public static void main(String[] args) {
		int[] numResources = {8,7,8};
		
		Process p1 = new Process(3,6,7,10);
		Process p2 = new Process(1,5,6,3);
		Process p3 = new Process(8,1,2,3);
		Process p4 = new Process(6,3,3,6);
		Process p5 = new Process(2,1,5,6);
		
		p1.start();
		p2.start();
		
		
//		while(true) {
//			try {
//				System.out.println("Current Available Resources: ");
//				System.out.println(numResources[0] + " " + numResources[1] + " " + numResources[2]);
//				p1.oneShot(numResources);
//				p2.oneShot(numResources);
//				p3.oneShot(numResources);
//				p4.oneShot(numResources);
//				p5.oneShot(numResources);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
}
