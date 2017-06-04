package cc.openhome;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		exp1();
		exp2();
	}
	/* ********************8.2 异常与资源管理******************** */
	public static void exp2() {
		/* 程序中因错误而抛出异常时，原本的执行流程就会中断，抛出异常处之后的程序代码就不会被执行，
		 * 如果程序开启了相关资源，使用完毕后你是否考虑的关闭资源呢？
		 * 
		 * */
		
//		MyClass2.exp1(); // 8.2.1 使用finally
		MyClass2.exp2(); // 8.2.2 自动尝试关闭资源
		MyClass2.exp3(); // 8.2.3 java.lang.AutoCloseable 接口
	}
	
	/* ********************8.1 接口语法细节******************** */
	public static void exp1() {
		/* 程序中，总有些意想不到的状况所引发的错误，Java中的错误也以对象方式呈现，
		 * 	为java.lang.Throwable 的各种子类实例。
		 * 只要你能捕捉包转错误的对象，就可以针对该错误做一些处理。
		 * 	例如：恢复正常流程、进行日志记录、以某种形式提醒用户等。
		 * 
		 * */
//		MyClass.exp1(); // 8.1.1 使用try、catch
//		MyClass.exp2(); // 8.1.2 继承异常架构
//		MyClass.exp3(); // 8.1.3 要抓还是要抛
		MyClass.exp4(); // 8.1.4 认识堆栈追踪
		MyClass.exp5(); // 8.1.5 关于assert
	}

}
