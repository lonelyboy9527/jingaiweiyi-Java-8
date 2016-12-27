package cc.openhome.class2;

public class Resource implements AutoCloseable {

	public void doSome() {
		System.out.println("做一些事");
	}
	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		System.out.println("资源Some被关闭");
	}

}
