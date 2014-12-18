import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveAnnotation {

	/**
	 * @Title AnnotationRemove
	 * @Discription
	 * @author Fred
	 * @time 2014/12/14
	 */
	static String inputFile = "E:\\test\\test1.java";
	static String outputFile = "E:\\test\\test1_rep.java ";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (args.length >= 2) {
			inputFile = args[0];
			outputFile = args[1];
		}
		
			File input = new File(inputFile);
			File output = new File(outputFile);

			try {
				new RemoveAnnotation().handleFile(input, output);
			} catch (Exception ex) {
				ex.printStackTrace();
			}


		
	}

	public void handleFile(File in, File out) throws Exception {
		StringBuffer content = new StringBuffer();
		String temp;
		int start, end;
		int from = 0;

		BufferedReader br = new BufferedReader(new FileReader(in));
		BufferedWriter bw = new BufferedWriter(new FileWriter(out));
		while ((temp = br.readLine()) != null) {
			//����ʱ��ȥ��//��ʽ��ע��
			if (temp.indexOf("//") != -1) {
				if (isValid1(temp, temp.indexOf("//")))
					temp = temp.substring(0, temp.indexOf("//"));
			}

			content.append(temp);
			content.append("\n");
		}

		//ɾ��/* */��ʽ��ע��
		int endofline;
		String line;
		while ((start = content.indexOf("/*", from)) != -1) {

			end = content.indexOf("*/", start) + 2;

			endofline = content.indexOf("\n", end - 2);
			line = content.substring(end - 2, endofline);
			if (isValid2(line)) {
				content.delete(start, end);
				from = start;
			}else{
				from=end;
			}
		}

		bw.write(content.toString());
		br.close();
		bw.close();

	}

	public boolean isValid1(String line, int index) {
		// �ж��ַ�//ǰ�ַ����ĸ����Ƿ�Ϊż��
		boolean result = false;
		String temp = line.substring(0, index);

		if (countQuote(temp) % 2 == 0)
			result = true;

		return result;
	}

	public boolean isValid2(String line) {
		// �ж��ַ�*/���ַ����ĸ����Ƿ�Ϊż��
		boolean result = false;

		if (countQuote(line) % 2 == 0)
			result = true;

		return result;
	}

	public int countQuote(String line) {
		String reg = "\"";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(line);
		int count = 0;// ��¼����
		while (m.find()) {
			count++;
		}
		return count;
	}

}
