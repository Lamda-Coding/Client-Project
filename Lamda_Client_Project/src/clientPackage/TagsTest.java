package clientPackage;

import java.util.Arrays;

public class TagsTest {

	public static void main(String[] args) {
		Tag item1=new Tag(1);
		System.out.println(item1.getName());
		System.out.println(item1.getVal());
		System.out.println(Arrays.toString(item1.getBin()));
		Tag item2=new Tag("part1");
		System.out.println(item2.getName());
		System.out.println(item2.getVal());
		System.out.println(Arrays.toString(item2.getBin()));
		Tag item3=new Tag("part7");
		System.out.println(item3.getName());
		System.out.println(item3.getVal());
		System.out.println(Arrays.toString(item3.getBin()));

	}

}
