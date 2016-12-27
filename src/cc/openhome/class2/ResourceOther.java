package cc.openhome.class2;

public class ResourceOther implements AutoCloseable {
	public void doOther() {
		System.out.println("做其他事");
	}
	
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("资源Other被关闭");

	}
}
