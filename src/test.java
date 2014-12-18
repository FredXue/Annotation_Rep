import java.io.*;

public class test {

	static String srcStr = "E:\\test\\test1.java ";
	static String desStr = "E:\\test\\test1_rep.java ";

	// 处理文件
	private void profile(File src, File des) throws Exception {

		StringBuffer content = new StringBuffer();
		String temp;
		int start;
		int end;
		int from = 0;

		BufferedReader br = new BufferedReader(new FileReader(src));
		BufferedWriter bw = new BufferedWriter(new FileWriter(des));

		while ((temp = br.readLine()) != null) {
			content.append(temp);
			content.append("\n");
		}
		
		while ((start = content.indexOf("/*", from)) != -1) {
			end = content.indexOf("*/", start) + 2;
			content.delete(start, end);
			from = start;
		}


		int endofline;
		while ((start = content.indexOf("//", from)) != -1) {
			endofline = content.indexOf("\n", start);
			end = (endofline == -1) ? content.length() : (endofline + 1);
			content.delete(start, end);
			from = start;
		}

		bw.write(content.toString());
		br.close();
		bw.close();
	}

	// 处理目录
	private void prodir(File src, File des) throws Exception {

		des.mkdir(); // 创建目录
		File[] files = src.listFiles();

		for (int i = 0; i < files.length; i++) {
			// 下一级目录/文件
			File nf = new File(des, files[i].getName());
			if (files[i].isDirectory()) {
				// 是目录，递归
				prodir(files[i], nf);
			} else { // 文件
				profile(files[i], nf);
			}
		}
	}

	// 输入目录还是文件
	public void dispReq(File src, File des) throws Exception {

		if (src.isDirectory()) {
			prodir(src, des);
		} else {
			profile(src, des);
		}
	}

	public static void main(String[] args) {

		if (args.length >= 2) {
			srcStr = args[0];
			desStr = args[1];
		}
		File src = new File(srcStr);
		File des = new File(desStr);
		try {
			new test().dispReq(src, des);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

}
