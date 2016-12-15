class Test {
	public static void main(String[] args) {
		Test s = new Test();
		String ss = "hhhhhhhhhhhhhhh.java";
		int i = ss.indexOf('.');
		String aaa = ss.substring(0, i);
		System.out.println(aaa);
		// int i = s.peel(ss);
		// System.out.println(s.peel(ss));
	}
	public String peel(String str) {
		// if (str == null) 
		// 	return 0;
		// int len = str.length();
		int i = str.indexOf('.');
		
		String rst = str.substring(0, i);
		// System.out.print(len);
		return rst;
		// return i;
	}
}