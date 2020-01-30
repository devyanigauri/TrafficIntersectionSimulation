package Traffic.RoadNetwork;//change as needed
import Traffic.RoadNetwork.BlockType;
abstract class Block{		
	
	private BlockType type; //0:normal,1:intersection,2:traffic-light
	private int BlockNo; //id
	private Auto vehicle; // object of the vehicle occupying the block
	private Block Next; // the next block on the lane
	//private Block Prev; // previous block on the lane
	//private boolean ProcessedFlag;//flag for indicate whether the block has been processed during current tick of the simulation
	
	protected Block(int no){
		this.setBlockNo(no);
		//ProcessedFlag = false;
	}
	public BlockType getType(){
		return this.type;
	}
	public void setType (BlockType type) {
		this.type = type;
	}
	public int getBlockNo(){
		return this.BlockNo;
	}
	public void setBlockNo(int posNo){
		this.BlockNo=posNo;
	}
	public Auto getAuto(){
		return this.vehicle;
	}
	public void setAuto(Auto v){
		this.vehicle = v;
	}
	public Block getNext(){
		return this.Next;
	}
	public void setNext(Block nextBlock){
		this.Next=nextBlock;			
	}
	/*
		public Block getPrev(){
			return this.Prev;
		}
		public void setPrev(Block prevBlock){
			this.Prev=prevBlock;			
		}*/

	//public abstract void SetNeighbhors(Block[] neighbors);//Initialization to set the road network
	public abstract boolean MoveForward();//method to move the vehicle to the next place in the road
}