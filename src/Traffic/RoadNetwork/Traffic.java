package Traffic.RoadNetwork;

public class Traffic extends Block{
	private Lights l;
	private int tick;

	public Traffic(int no) {
		super(no);
		this.setType(BlockType.BLOCK_TRAFFIC);
		this.setLight(Lights.GREEN);
		this.tick = 0;
	}
	public int getTick() {
		return tick;
	}

	public void setTick(int tick) {
		this.tick = tick;
	}
	public Lights getLight() {
		return l;
	}

	public void setLight(Lights l) {
		this.l = l;
	}
	
	public boolean MoveForward() {
		Block checkBlock = this.getNext();
		if(checkBlock.getAuto() == null && (this.l == Lights.GREEN || this.l == Lights.ORANGE) ) {
			return true;
		}
		return false;
	}

	
	
	

}
