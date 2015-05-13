package clientPackage;

import java.util.Arrays;

public class TagsTest {

	public static void main(String[] args) {
		Tags item1=new Tags(1);
		System.out.println(item1.getName());
		System.out.println(item1.getVal());
		System.out.println(Arrays.toString(item1.getBin()));
		Tags item2=new Tags("part1");
		System.out.println(item2.getName());
		System.out.println(item2.getVal());
		System.out.println(Arrays.toString(item2.getBin()));
		Tags item3=new Tags("part7");
		System.out.println(item3.getName());
		System.out.println(item3.getVal());
		System.out.println(Arrays.toString(item3.getBin()));

	}

}
