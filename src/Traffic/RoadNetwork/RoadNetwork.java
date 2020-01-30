package Traffic.RoadNetwork;
import java.util.Scanner;

public class RoadNetwork {
	private static double turnRate, entryRate;
	private static int [] lightLengths = {2,4,6};
	private static Block[][] Lanes;
	private static int span;
// first index=SN, second index=EW, third index=NS fourth index=WE 	
	
	public static void main(String[] args) {
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Enter total number of ticks the simulation should run for: " );
		int ticks = keyboard.nextInt();
		initLanes(keyboard);
		
		for (int h = 0; h < ticks; h++) {
			for (Block[] lane: Lanes) {
				for (int i = lane.length-1; i >= 0; i--) {
					if (i==0 && lane[i].getAuto()==null && (Math.random() > entryRate)) {
						//try to add car
						System.out.println("Car entering ");
						lane[i].setAuto(new Auto()); //add car to beginning
					} else {
						System.out.println("process " + i);
						processBlock(lane[i]);
					}
				}	
			}
		}
		
		
		System.out.println("ending simulation");
		keyboard.close();
	}

	
	public static void initLanes(Scanner scan) {
		Block temp;
		//Scanner scan = new Scanner(System.in);
		System.out.println("Enter span of lane: ");
		span = scan.nextInt();
		int count;
		System.out.println("Enter the length of ticks for Orange light: ");
		lightLengths[2] = scan.nextInt();
		System.out.println("Enter the length of ticks for Green light: ");
		lightLengths[1] = scan.nextInt();
		System.out.println("Enter entry rate(number b/w 0 and 1): ");
		entryRate = scan.nextDouble();
		System.out.println("Enter turn rate(number b/w 0 and 1): ");
		turnRate = scan.nextDouble();
		
		Lanes = new Block[4][(span*2)+2];
		for(Block[] lane: Lanes) {
			count = 0;
			for(int i = 0; i < span-1; i++) {
				lane[count] = new Normal(count);
				count++;
			}
			lane[count] = new Traffic(count);
			count++;
			
			lane[count] = new Intersection(count);
			count+=2; //skip over second intersection block
			
			for(int i = 0; i < span; i++) {
				lane[count] = new Normal(count);
				count++;
			}
			
		}
		for(int i = 0; i < Lanes.length; i++) {
			Lanes[i][span+1]=Lanes[(i-1+(Lanes.length)) % (Lanes.length)][span];
			//Reference 2nd intersection block to the first intersection block of another lane
		}
		for(Block[] lane: Lanes) {
			for(int i = 0; i < lane.length-1; i++) {
				lane[i].setNext(lane[i+1]);
			}
		}
		
		
		Traffic tempTraffic;
		tempTraffic = (Traffic)Lanes[1][span-1];
		tempTraffic.setTick(lightLengths[2]); //sets to red light
		tempTraffic = (Traffic)Lanes[3][span-1];
		tempTraffic.setTick(lightLengths[2]);
		
		Intersection tempInter;
		for (int i = 0; i < Lanes.length; i++) {
			tempInter = (Intersection) Lanes[i][span];
			tempInter.setSecondNext(Lanes[(i+1) % (Lanes.length)][span+2]); //turn right(1st block)
			tempInter = (Intersection) Lanes[i][span+1];
			tempInter.setSecondNext(Lanes[(i-1+(Lanes.length)) % (Lanes.length)][span+1]); //turn left(2nd block)
		}
	}
	
	public static void processBlock(Block check) {
		if (check.getType()==BlockType.BLOCK_TRAFFIC) {
			changeLight((Traffic)check);
		} 
		boolean moved = false;
		if (check.getType()==BlockType.BLOCK_INTERSECT && check.getAuto() != null) {
			moved = intersectionMove((Intersection)check); //turns if possible
		}
		if (!moved && check.getAuto()!=null) { //move auto forward
			System.out.println("moving forward");
			if (check.MoveForward()) {
				check.getNext().setAuto(check.getAuto());
				check.setAuto(null);
			}
		}
	}
	
	public static boolean intersectionMove(Intersection check) {
		if (check.getProcessed()) {
			check.setProcessed();
			if (check.getBlockNo() == span+1) {//second intersection block, so next and sideways are reversed
				if (Math.random() > turnRate && check.MoveForward()) { //"turning"
					System.out.println("Turning");
					check.getNext().setAuto(check.getAuto());
					check.setAuto(null);
					return true;
				} else if (check.MoveSideways()) { // moving "forward" with reversed ref
					System.out.println("moves forward");
					check.getSecondNext().setAuto(check.getAuto());
					check.setAuto(null);
					return true;
				}
			} else if (Math.random() > turnRate && check.MoveSideways()) { //turning regularly
				System.out.println("turns");
				check.getSecondNext().setAuto(check.getAuto());
				check.setAuto(null);
				return true;
			}
		}
		return false;
	}
	
	public static boolean changeLight (Traffic check) {
		int colorIdx = check.getLight().ordinal();
		Lights[] colors = Lights.values();
		boolean change = (check.getTick() >= lightLengths[colorIdx]);
		if(check.getTick()> lightLengths[2]) {
			check.setTick(0);
		}
		else {
			check.setTick(check.getTick()+1);
		}
		if (change) {
			check.setLight(colors[((colorIdx+1) % colors.length)]); //change light to next one
			System.out.println("Light is now: "+ check.getLight());//goes from: green to yellow to red
			return true;
		}
		return false;
	}
	
}
	
	
