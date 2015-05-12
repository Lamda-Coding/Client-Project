package clientPackage;

public class TagsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tags item1=new Tags(1);
		System.out.println(item1.getName());
		System.out.println(item1.getVal());
		System.out.println(item1.getBin());
		Tags item2=new Tags("part1");
		System.out.println(item2.getName());
		System.out.println(item2.getVal());
		System.out.println(item2.getBin());
		Tags item3=new Tags("part6");
		System.out.println(item3.getName());
		System.out.println(item3.getVal());
		System.out.println(item3.getBin());

	}

}
