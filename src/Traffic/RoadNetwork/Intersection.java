package Traffic.RoadNetwork;

public class Intersection extends Block {
	private Block secondNext;
	private boolean processed;
	
	public Intersection(int BlockNo) {
		super(BlockNo);
		this.setType(BlockType.BLOCK_INTERSECT);
		this.processed = false;
	}

	
	public Block getSecondNext() {
		return secondNext;
	}
	public void setSecondNext(Block secondNext) {
		this.secondNext = secondNext;
	}
	public boolean getProcessed() {
		return processed;
	}
	
	public void setProcessed() {
		processed = !processed;
	}
	@Override
	public boolean MoveForward() {
		Block checkBlock = this.getNext();
		if(checkBlock.getAuto() == null) {
			return true;
		}
		return false;
	}
	public boolean MoveSideways() {
		Block checkBlock = this.getSecondNext();
		if(checkBlock.getAuto() == null) {
			return true;
		}
		return false;
	}

}
