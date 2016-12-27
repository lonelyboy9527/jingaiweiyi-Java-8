package cc.openhome;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cc.openhome.class2.Resource;
import cc.openhome.class2.ResourceOther;

public class MyClass2 {
	// 使用finally
	public static void exp1() {
		/* 上一节8.1.3撰写的FileUtil例子并不是很正确，之后在学习输入输出时会谈到，
		 * 如果创建FileInputStream实例就会开启文档，不使用时，应该调用close()关闭文档。
		 * 
		 * scanner对于有个close()方法，可以关闭Scanner相关资源与搭配的FileInputStream
		 * */
		/* 1.例子：
		 * 假如在readFile抛出异常，则没有办法调用到 scanner.close();方法了
		 * 
		 * 注意：因为 readFile抛出了异常，调用此方法时需要使用try, catch
		 * */
		try {
			FileUtil2.readFile("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* 2.使用finally ，在区块中调用close()方法。
		 * */
		try {
			FileUtil2.readFile2("");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* 如果程序撰写的流程中先 return了，而且也有 finally区块，
		 * 那么finally区块会先执行完后，再将值返回。
		 * 
		 * 下面的例子，会先显示 finally... ，再显示 1
		 * */
		System.out.printf("开始测试: %d", FileUtil2.test(true));
		
	}
	
	// 自动尝试关闭资源
	public static void exp2() {
		/* 经常的，在使用try、finally尝试关闭资源时，会发现程序撰写的流程是类似的，
		 * 如前面的例子FileUtil2，会先检查scanner是否为null，在调用close（）方法关闭scanner。
		 * 
		 * 在JDK之后，新增了尝试关闭资源语法，直接来看如何让使用。
		 * */
		try {
			FileUtil2.readFile3("");
		} catch (Exception e) {
			// TODO: handle exception
		}
		/* JDK7的尝试关闭资源语法也是个编译程序蜜糖，尝试反编译观察看看，有助于了解这个语法是否符合你的需求。
		 * 
		 * */
		
		
		/* 使用自动尝试关闭资源的语法时，也可以搭配catch。
		 * 
		 * 例如：
		 * */
		try {
			FileUtil2.readFile4("");
		} catch (Exception e) {
			// TODO: handle exception
		}
		/* 使用自动尝试关闭资源语法时，并不影响你对特定异常的处理，
		 *
		 * 实际上，自动尝试关闭资源语法也仅协助你关闭资源，而非用于异常处理。
		 * */
	}
	
	// java.lang.AutoCloseable 接口
	public static void exp3() {
		/* JDK7的尝试关闭资源语法可套用的对象，必须操作 java.lang.AutoCloseable 接口
		 * 
		 * 这可以在API文件上得知，
		 * 
		 * AutoCloseable是JDK7新增的接口，仅定义了 close()方法。
		 * 所有继承 AutoCloseable的子接口，或操作AutoCloseable的类，可在AutoCloseable的API文件上查询得知。
		 * 
		 * 简单示范：
		 * */
		try (Resource res = new Resource()) {
			res.doSome();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* 尝试关闭资源也可以同时关闭两个以上的对象资源，只要中间以分号 ; 分割。
		 * 
		 * 例子：
		 * */
		System.out.println("关闭两个的以上的对象资源:");
		/* 注意：
		 * 在try的括号中，越后面撰写的资源对象会越早被关闭
		 * */
		try (Resource resource = new Resource() ; ResourceOther other = new ResourceOther()){
			resource.doSome();
			other.doOther();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}
}

class FileUtil2 {
	public static String readFile(String name) throws FileNotFoundException {
		StringBuilder builder = new StringBuilder();
		Scanner scanner = new Scanner(new FileInputStream(name));
		while (scanner.hasNext()) {
			builder.append(scanner.nextLine());
			builder.append('\n');
		}
		/* 如果在scanner.close()前发生了异常，执行流程就会中断，
		 * 因此scanner.close()就可能不会执行，因此Scanner及搭配的FileInputStream就不会被关闭。
		 * 
		 * 此时，若写有finally区块，无论try区块中有没有发生异常，finally区块一定会被执行。
		 * 改进：readFile2
		 * */
		scanner.close();
		return builder.toString();
	}
	
	public static String readFile2(String name) throws FileNotFoundException {
		StringBuilder builder = new StringBuilder();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(name));
			while (scanner.hasNext()) {
				builder.append(scanner.nextLine());
				builder.append('\n');
			}
		} finally {
			
			/* 
			 * 此时，若写有finally区块，无论try区块中有没有发生异常，finally区块一定会被执行。
			 * 
			 * 注意:由于scanner开始等于null，若FileInputStream创建失败，
			 * 则scanner可能还是null，因此在finally区块中必须先检查scanner是否有参考对象。
			 * */
			if (scanner != null) {
				scanner.close();
			}
		}
		
		return builder.toString();
	}
	
	public static int test(boolean flag) {
		try {
			if (flag) {
				return 1;
			}
		} finally {
			System.out.println("finally...");
		}
		return 0;
	}
	
	/* 尝试自动关闭的写法 
	 * 
	 * 想要尝试自动关闭资源的对象，是撰写在try之后的括号中。
	 * 如果无须catch处理任何异常，可以不用撰写
	 * 也不用撰写finally自行尝试关闭资源。
	 * 
	 * */ 
	public static String readFile3(String name) throws FileNotFoundException {
		StringBuilder builder = new StringBuilder();
		try (Scanner scanner = new Scanner(new FileInputStream(name))) {
			while (scanner.hasNext()) {
				builder.append(scanner.nextLine());
				builder.append('\n');
			}
		}
		return builder.toString();
	}
	
	public static String readFile4(String name) throws FileNotFoundException {
		StringBuilder builder = new StringBuilder();
		try (Scanner scanner = new Scanner(new FileInputStream(name))) {
			while (scanner.hasNext()) {
				builder.append(scanner.nextLine());
				builder.append('\n');
			}
		} catch (FileNotFoundException e) {
			// 可以进行一些其他操作（比如追寻堆栈信息），再抛出异常。
			e.printStackTrace();
			throw e;
		}
		return builder.toString();
	}
}