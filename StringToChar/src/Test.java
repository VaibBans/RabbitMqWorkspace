import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
	
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter text");
		String a = sc.next();
		char ch[] = new char[a.length()+1];
		for(int i = 0;i<a.length();i++){
			ch[i] =	a.charAt(i);
			if((ch[i]>=65&&ch[i]<90)||(ch[i]>=97&&ch[i]<122))
				System.out.println("Characters");
			else
				System.out.println("Other than Characters");
		}
	}
}