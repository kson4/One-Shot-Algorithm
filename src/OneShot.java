
public class OneShot {
	//public static int[] numResources = {8, 7, 8};
	public static void main(String[] args) {
		int[] numResources = {8,7,8};
		
		MultithreadProcess p1 = new MultithreadProcess("p1", 3, 6, 7, 10, numResources);
		p1.start();
		MultithreadProcess p2 = new MultithreadProcess("p2", 1, 5, 6, 3, numResources);
		p2.start();
		MultithreadProcess p3 = new MultithreadProcess("p3", 4, 2, 2, 5, numResources);
		p3.start();
		MultithreadProcess p4 = new MultithreadProcess("p4", 6, 3, 3, 6, numResources);
		p4.start();
		MultithreadProcess p5 = new MultithreadProcess("p5", 2, 1, 5, 8, numResources);
		p5.start();
	}
}
