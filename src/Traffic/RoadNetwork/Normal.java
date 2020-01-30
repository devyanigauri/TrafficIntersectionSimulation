package Traffic.RoadNetwork;

public class Normal extends Block{
	public Normal(int no) {
		super(no);
		this.setType(BlockType.BLOCK_NORMAL);
	}
	public boolean MoveForward() {
		try {
			Block checkBlock = this.getNext();
			if(checkBlock.getAuto() == null) {
				System.out.println("moveS");
				return true;
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

}
