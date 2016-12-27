package cc.openhome;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class MyClass {

	// 使用try、catch
	public static void exp1() {
		/* 例子：
		 * 
		 * 用户连续输入整数，最后输入0结束后会显示输入数的平均值。
		 * */
		System.out.println("第一轮开始:");
		average();
		
		/* 假如用户正确的输入数字，将不会有什么问题。
		 * 
		 * 如果用户不小心输入了字母或其他非数字，那么程序将会出现异常。
		 * 我们可以尝试（try） 捕获 （catch）代表错误的对象后做一些处理。
		 * 
		 * 改造如下：
		 * */ 
		System.out.println("第二轮开始:");
		try {
			average();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("必须输入整数!");
			/* 执行完 catch后，没有其他程序代码了，所以程序结束了*/
		}
		/* 这里使用了 try catch 语法，
		 * JVM会尝试执行try区块中的程序代码，如果发生错误，执行流程会跳离错误发生点，
		 * 然后比较catch括号中声明的类型，是否符合被抛出的错误对象类型，
		 * 如果是的话，就执行catck区块中的程序代码
		 * 执行完 catch后，没有其他程序代码了，所以程序结束了
		 *
		 * */
		
		 /* 有时候，在错误发生时，显示更加友好的错误信息，
		  * 可以在 捕捉处理后，尝试回到程序正常执行流程。
		  * 
		  * */
		System.out.println("第三轮开始:");
		Scanner scanner = new Scanner(System.in);
		double sum = 0;
		int count = 0;
		int number;
		while (true) {
			System.out.println("请输入整数:");
			try {
				number = scanner.nextInt();
				if (number == 0) {
					break;
				}
				sum = sum + number;
				count ++;
			} catch (Exception e) {
				/* 如果nextInt()发生了错误，执行流程就会跳转到 catch
				 * 
				 * 执行完catch之后，由于还在while循环中(注意 try和catch 写在while循环中)，所以还可以继续下一个循环流程。
				 * 
				 * */
				System.out.printf("略过非整数输入: %s\n", scanner.next()); // next获取刚刚输入的非整数。
			}
		}
		System.out.printf("平均: %.2f\n", sum / count);


	}
	public static void average() {
		Scanner scanner = new Scanner(System.in);
		double sum = 0;
		int count = 0;
		int number;
		while (true) {
			System.out.println("请输入整数:");
			number = scanner.nextInt();
			if (number == 0) {
				break;
			}
			sum = sum + number;
			count ++;
		}
		System.out.printf("平均: %.2f\n", sum / count);
	}
	// 继承异常架构
	public static void exp2() {
		/* 为什么以下的代码会 报错？
		 * 
		 * int ch = System.in.read();
		 * System.out.println(ch);
		 * 简单来说，编译程序认为调用 System.in.read()时有可能发生错误，
		 * 要求你一定要在程序中明确处理错误。
		 * 
		 * 
		 * 要解决这个错误 有两种方式：
		 * 1.使用try ，catch打包 System.in.read();
		 * 2.声明 throws java.io.IOException 
		 * */
		//1. 
		try {
			int ch = System.in.read();
		} catch (java.io.IOException ex) {
			// 打印出异常，但是还将显示出更深的调用信息。
			ex.printStackTrace();
		}
		/* 为什么 前面上一个 求平均 例子 Scanner(System.in) 也有可能出现错误，为什么编译程序单单就只要这里的范例，一定要处理错误呢？
		 * 
		 * 要了解这个问题，得先了解那些错误对象的继承框架：
		 * 	首先要了解错误会被包装为对象，这些对象都是可抛出的（了解throw语法）。
		 *  因此设计错误对象都继承自 java.lang.Throwable类，Throwable类定义了取得错误信息，堆栈追踪等方法。
		 *  
		 *  （1）Error 和 Exception区别：
		 *  Throwable类有两个子类：
		 *  	1.java.lang.Error: Error 与其子类实例 代表严重系统错误，如硬件层面的错误、JVM错误或者内存不足等问题。
		 *  		虽然也可以使用 try、catch 来处理Error对象，但并不建议，发生严重系统错误时，Java程序本身是无力回复的。
		 *  
		 *  	2.java.lang.Exception:通常程序中会使用 try、catch加以处理的错误，都是 Exception或其子类实例，所以通常称
		 *  		错误处理为异常处理，对于某些异常，可以用 try、catch语法尝试将应用程序回复至可执行状态。 
		 *  
		 *  如果 某个方法声明会抛出 Throwable、Exception 或子类实例，但又不属于java.lang.RuntimeExcption 或其子类实例，
		 *  	就必须明确使用 try、catch语法加以处理，或者用 throws 声明这个方法会抛出异常，否则会编译错误。
		 *  
		 *  （2）Exception 和 RuntimeExcption区别：
		 *  
		 *  Exception或其子对象，但非属于RuntimeExcption 或其子类对象，称为“受检异常”。
		 *  	受编译器检查，目的是：在于API设计者认定，调用这个方法时，出错机会较高，因此要求编译程序协助（提醒）调用API的用户明确使用语法处理。
		 *  
		 *  RuntimeExcption 衍生出来的类实例，通常是事先无法预测错误是否发生的执行时期异常，编译程序不会强迫一定得在语法上加以处理。称为“非受检异常”。
		 *  	例如：使用数组，若存取超出索引会抛出的异常，是一种RuntimeExcption，可以在API文件的开头找到继承框架图。
		 *  	在 average例子中，用户是否正确输入事先并未可知，因此是RuntimeExcption异常。
		 *  
		 *  
		 *  在了解了 Error 和 Excption 区别、Exception 和 RuntimeExcption 区别之外
		 *  
		 *  注意：
		 *  使用 try、catch 捕捉异常时要注意，如果父类异常对象在子类异常对象前被捕捉，则catch子类异常对象的区块将永远不会执行。
		 *  编译程序会检查出这个错误。 
		 *  例子如下：
		 * */
	
		try {
			System.in.read();
		} catch (java.io.IOException e) {
			// IOException是 Exception的子类，需要在Exception前捕获
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* 经常的
		 * 
		 * 会发现到某数个 类型的catch区块在做相同的事情，
		 * 这种情况经常发生在异常都要进行日志记录的情况。
		 * 
		 * 例如：
		 * */
/*		try {
			System.out.println("做一些事...");
		} catch (IOException e) {
			// 
			e.printStackTrace();
		} catch (InterruptedException e) {
			// 重复
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		
		*/
		
		/* 然而catch异常后的 区块内容重复了，撰写时不仅无趣而且对维护并没有帮助。
		 * 
		 * 在JDK7开始，可以使用 多重捕捉方法。
		 * 这样撰写方式简洁很多，catch区块在发生 IOException | InterruptedException | ClassCastException时执行，
		 * 
		 * (子类在前。)
		 * 不过仍得注意 异常的继承，catch括号中，左边的异常不得是右边的父类型。否则会发生编译错误。
		 * 
		 * */
/*		try {
			
		} catch (IOException | InterruptedException | ClassCastException e) {
			e.printStackTrace();
		}
		*/
	}
	
	// 要抓还是要抛
	public static void exp3() {
		/* 假设今天开发一个数据库，其中有个功能是读取纯文本文档，并以字符串返回文档中所有文字。
		 * 
		 * 也许会这样写：
		 * */
		System.out.println("处理异常:");
		FileUtil.readFile("");
		/* 由于创建 FileInputStream 时会抛出FileNotFoundException，于是捕捉FileNotFoundException
		 * 并且在 控制台显示异常信息。
		 * 
		 * 但是只有在控制台显示异常信息，Web用户怎么看得到？
		 * 直接在catch中写死处理异常 或输出异常错误信息的方式，并不符合需求。
		 * 
		 * 解决：
		 * 如果方法设计流程中发生异常，而你设计时并没有充足的信息知道该如何处理
		 * （例如不知道链接库会用在什么环境），那么可以抛出异常，让调用方法的客户端来处理。
		 * 
		 * 例如：
		 * */
		System.out.println("抛出异常:");
//		FileUtil.readFile2("");
		
		/* 1.操作对象的过程中如果会抛出受检异常（Exception）：
		 * 
		 * 操作对象的过程中如果会抛出受检异常，但目前环境信息不足以处理异常，
		 * 无法使用 try、catch处理时，可由方法的客户端依据当时调用的环境信息处理。
		 * 
		 * 为了告诉编译器这个事实，必须使用 throws声明此方法会抛出的异常类型或父类型。
		 * 编译程序才会让你通过编译。
		 * 
		 * 2.如果是非受检异常（RuntimeException）：
		 * 
		 * 原本就可以自行选择是否处理异常，因此不使用try、catch处理时也不用特别在方法上使用throws声明。
		 * 不出理非受检异常时，异常会自动往外传播。
		 * 
		 * 3.对于异常，如果想处理部分事项再抛出，可以如下
		 * 
		 * 例子：
		 * */
		System.out.println("处理部分事项，再抛出异常:");
//		FileUtil.readFile3("");
		/* 在流程中抛出异常，就直接跳离原来的流程，
		 * 
		 * 如果抛出的是 受检异常，则必须在方法上使用 throws 声明。
		 * 如果抛出的是 非受检异常，就不用。
		 * */
		
		/* 提示：
		 * 
		 * 如果使用继承时，父类某个方法声明throws某些异常。子类重新定义该方法时可以：
		 * 
		 * 1.不声明throws任何异常。
		 * 2.throws父类该方法中声明的某些 异常。
		 * 3.throws父类该方法中声明 异常的子类。
		 * 
		 * 不可以：
		 * 1.throws父类该方法中 未声明的其他 异常(注意是其他异常，不是该异常的子类)。
		 * 2.throws父类该方法中声明异常的	父类。
		 * */
	}
	
	// 认识堆栈追踪
	public static void exp4() {
		/* 在多重方法调用下，异常发生点可能是在某个方法之中，
		 * 若想得知异常发生的根源，以及多重方法调用下异常的堆栈传播，
		 * 可以利用异常对象自动收集的堆栈追踪（Stack Trace）来取得相关信息。
		 * 
		 * 异常追踪最简单的办法，就是直接调用 异常对象的 printStackTrace().
		 * 
		 * 例如：
		 * */
		try {
			Some.c();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		/* c()调用b()，b()调用a()方法，而a方法中会因text参考至 null
		 * 
		 * 而后试图调用toUpperCase() 引发NullPointerException。
		 * 假设事先并不知道这个调用的顺序，当异常发生而被捕捉后，可以调用printStackTrace()
		 * 在 控制台显示堆栈追踪。
		 * 
		 * java.lang.NullPointerException // 最顶层是异常的根源。
				at cc.openhome.Some.a(MyClass.java:273)
				at cc.openhome.Some.b(MyClass.java:276)
				at cc.openhome.Some.c(MyClass.java:279)
				at cc.openhome.MyClass.exp4(MyClass.java:261)
				at cc.openhome.Main.exp1(Main.java:20)
				at cc.openhome.Main.main(Main.java:7)
				
			注意：
			堆栈追踪信息中，显示了异常类型，最顶层是异常的根源。
			之后是调用方法的顺序。
			printStackTrace()还有几个版本，可以将堆栈信息写在 文档。
			
		 * 
		 * 如果想要取得个别的堆栈追踪元素进行处理，则可以使用getStackTrace()，这会返回StackTraceElement数组。
		 * 数组中 索引0为异常根源的相关信息，之后为各方法调用中的信息。
		 * 
		 * 可以使用 stackTraceElement 的
		 *  getClassName()、
		 *  getFileName()、
		 *  getLineNumber()、
		 *  getMeyhodName()等方法获取对应的信息。
		 *  
		 * */
		try {
			Some.c();
		} catch (NullPointerException e) {
			StackTraceElement[] stackTraceElements = e.getStackTrace();
			for (int i = 0; i < stackTraceElements.length; i++) {
				System.out.println("异常位置:" + stackTraceElements[i]);
				System.out.printf("详细:\nclassName:%s, \nfileName: %s, \nlineNumber: %s, \nmethodName: %s\n", stackTraceElements[i].getClassName(), 
													stackTraceElements[i].getFileName(), 
													stackTraceElements[i].getLineNumber(),
													stackTraceElements[i].getMethodName());
			}
		}
		/* 注意，
		 * 1.不可以在捕捉 异常(catch 里面)后，什么都不做
		 * 
		 * 这样的程序代码会对应用程序维护造成严重伤害，
		 * 因为异常信息会完全中止，之后调用此片段程序代码的客户端，
		 * 完全不知道发生了什么事。造成排除异常困难。甚至找不出错误根源。
		 * 
		 * 2.不可对异常做不适当的处理，或显示不正当的信息。
		 * */
		
		/* 重抛异常，改变堆栈追踪的起点
		 *
		 * */
		System.out.println("重抛异常:");
		try {
			Some2.c();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		/* 上面打印的两个堆栈信息都是一样的*/
		
		System.out.println("重抛异常 更改异常堆栈起点:");
		/* 如果想让 异常堆栈起点为重抛异常的地方，可以使用 fillInstackTrace()方法。
		 * 这个方法会重新装填异常堆栈，将起点设为重抛异常的地方，并返回Throwable对象。
		 * 例如：c2();
		 * */
		try {
			Some2.c2();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		/* 可见第二次打印的堆栈信息，异常堆栈起点已经变成了 
		 * at cc.openhome.Some2.c2(MyClass.java:374)
		 * c2中 刚好为重抛异常的地方。
		 * */
	}
	
	// 关于assert
	public static void exp5() {
		/* 异常是程序非 预期的错误，异常处理是在错误发生时采取的措施。
		 * 
		 * 有时候，需求或设计时候就可以确认，程序执行到某个时间点或某个情况下，必然处于或者不处于某种状态。
		 * 如果不是这种状态，则是个严重的错误，开发过程中发现这种严重错误，必须立即停止程序确认需求与设计。
		 * 
		 * “程序执行到某个时间点或某个情况下，必然处于或者不处于某种状态” 这是一种断言（assert）;
		 * 
		 * 断言的结果一定是成立或不成立。
		 * 
		 * Java在 JDK1.4之后提供assert语法，有两种使用的语法：
		 * assert boolean_expression;
		 * assert boolean_expression : detail_expression
		 * 
		 * 1.boolean_expression 若为true，则什么事都不发生，如果为false，则会发生 java.lang.AssertionError，
		 * 2.此时若采取的是第二个语法，则会将 detail_expression的结果显示出来，如果当中是个对象，则调用 toString()显示文字描述结果。
		 * 
		 * 注意：
		 * 在Java中，assert关键字是从JAVA SE 1.4 引入的，为了避免和老版本的Java代码中使用了assert关键字导致错误，
		 * Java在执行的时候默认是不启动断言检查的（这个时候，所有的断言语句都将忽略！），
		 * 如果要开启断言检查，则需要用开关-enableassertions或-ea来开启。
		 * */
		//1.
		System.out.println("取钱:");
		Bank bank = new Bank(-1000.0f);
		
		/* 另一个使用断言的时机为控制流程不变量判断
		 * 
		 * */
		bank.play(6);
		
		/* 注意：
		 * 
		 * 断言是判断程序中的某个执行点必然是或不是某个状态，
		 * 所以不能当作像if之类的判断式来使用，assert不应该作为程序流程的一部分。
		 * */
	}
	
}
//+++++++++++++关于assert+++++++++++++
class Bank {
	private double balace;
	
	public Bank(double balace) {
		/* 使用 assert 检查
		 * 一定不能是负数 */
		assert balace >= 0;  
		this.balace = balace;
	}
	// 取钱
	public void charge(int money) {
		
		if (money > 0) {
			if (money <= this.balace) {
				
				this.balace -= money;
				System.out.printf("成功取款 %d 元, 余额: %f", money, this.balace);
			} else {
				System.out.println("钱不够了!");
			}
		} else {
			 System.out.println("取负数？是玩我咯？");
		}	 
	}
	public void play(int a) {
		switch (a) {
		case 0:
			System.out.println("开始");
			break;
		case 1:
			System.out.println("暂停");
			break;
		case 2:
			System.out.println("快进");
			break;
		case 3:
			System.out.println("快退");
			break;
		default:
			/* 直接assert false，必然断言失败*/
			assert false : "非定义的判断";
		}
	}
}

//+++++++++++++认识堆栈追踪+++++++++++++
class Some2 {
	public static String a() {
		String text = null;
		return text.toUpperCase();
	}
	public static void b() {
		a();
	}
	public static void c() {
		try {
			b();
		} catch (NullPointerException e) {
			e.printStackTrace();
			/* 在使用throw重新抛出异常时， 异常的追踪堆栈起点，仍是异常发生的根源
			 * 而不是重抛异常的地方（这里）。
			 * 
			 * 如果想让 异常堆栈起点为重抛异常的地方，可以使用 fillInstackTrace()方法。
			 * 
			 * 这个方法会重新装填异常堆栈，将起点设为重抛异常的地方，并返回Throwable对象。
			 * c2();
			 * 
			 * 
			 * 注意：NullPointerException是 非受检异常(RuntimeException)。
			 * 	所以不需要在方法后 用throws 标示
			 * */
			
			throw e;
		}
	}
	public static void c2() {
		try {
			b();
		} catch (NullPointerException e) {
			e.printStackTrace();
			// 重抛异常。
			Throwable throwable = e.fillInStackTrace();
			throw (NullPointerException) throwable;
		}
	}
}
class Some {
	public static String a() {
		String text = null;
		return text.toUpperCase();
	}
	public static void b() {
		a();
	}
	public static void c() {
		b();
	}
}

//+++++++++++++要抓还是要抛+++++++++++++
class FileUtil {
	// 直接处理异常
	public static String readFile(String name) {
		StringBuilder builder = new StringBuilder();
		try {
			/* FileInputStream可指定档名来开启与读取文档内容，是 InputStream的子类。 
			 * */ 
			Scanner scanner = new Scanner(new FileInputStream(name));
			while (scanner.hasNext()) {
				builder.append(scanner.nextLine());
				builder.append('\n');
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件没有发现，打印详细异常信息:");
			e.printStackTrace();
		}
		return builder.toString();
	}
	// 直接抛出异常
	public static String readFile2(String name) throws FileNotFoundException {
		StringBuilder builder = new StringBuilder();
		Scanner scanner = new Scanner(new FileInputStream(name));
		while (scanner.hasNext()) {
			builder.append(scanner.nextLine());
			builder.append('\n');
		}
		return builder.toString();
	}
	// 可以处理部分事项后，再抛出异常
	public static String readFile3(String name) throws FileNotFoundException {
		StringBuilder builder = new StringBuilder();
		try {
			Scanner scanner = new Scanner(new FileInputStream(name));
			while (scanner.hasNext()) {
				builder.append(scanner.nextLine());
				builder.append('\n');
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e; // 执行时，抛出异常。
			/* 在catch 区块进行完部分错误处理后，可以使用 throw 将异常再抛出。
			 * 
			 * 在流程中抛出异常，就直接跳离原来的流程，
			 * 如果抛出的是 受检异常，则必须在方法上使用 throws 声明。
			 * 如果抛出的是 非受检异常，就不用。
			 * */
		}
		return builder.toString();
	}
}
