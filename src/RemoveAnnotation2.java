import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class RemoveAnnotation2 {

	/**
	 * @Title AnnotationRemove version2
	 * @Discription using Finit state machine method
	 * @author Fred
	 * @time 2014/12/22
	 */
	static String inputFile = "E:\\test\\test1.java";
	static String outputFile = "E:\\test\\test1_rep1.java ";

	int[][] fsm = new int[10][128];

	int state = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		if (args.length >= 2) {
			inputFile = args[0];
			outputFile = args[1];
		}
		RemoveAnnotation2 me = new RemoveAnnotation2();
		me.initFSM();

		File input = new File(inputFile);
		File output = new File(outputFile);

		try {
			me.handleFile(input, output);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void initFSM() {
		//初始化状态机
		for (int i = 0; i < 128; i++) {
			fsm[0][i] = 0;
			fsm[1][i] = 0;
			fsm[2][i] = 2;
			fsm[3][i] = 3;
			fsm[4][i] = 3;
			fsm[5][i] = 5;
			fsm[6][i] = 5;
			fsm[7][i] = 7;
			fsm[8][i] = 7;
			fsm[9][i] = 0;
		}
		fsm[0]['/'] = 1;
		fsm[0]['"'] = 5;
		fsm[0]['\''] = 7;
		fsm[1]['/'] = 2;
		fsm[1]['*'] = 3;
		fsm[1]['"'] = 5;
		fsm[2]['\n'] = 9;
		fsm[3]['*'] = 4;
		fsm[4]['/'] = 9;
		fsm[4]['*'] = 4;
		fsm[5]['"'] = 0;
		fsm[5]['\\'] = 6;
		fsm[7]['\\'] = 8;
		fsm[7]['\''] = 0;
		fsm[9]['/'] = 1;
		fsm[9]['"'] = 5;
		fsm[9]['\''] = 7;

	}

	public int parseChar(char a) {
		int result = a;
		return result;
	}

	public char parseInt(int a) {
		char result = (char) a;
		return result;
	}

	public void handleFile(File in, File out) throws Exception {

		int c;
		String temp = "";
		char[] cbuf = null;

		BufferedReader br = new BufferedReader(new FileReader(in));
		BufferedWriter bw = new BufferedWriter(new FileWriter(out));

		while ((c = br.read()) != -1) {

			state = fsm[state][c];
			temp = temp + parseInt(c);
			switch (state) {
			case 0:
				bw.write(temp);
				temp = "";
				break;
			case 9:
				//注释结束态，将除了换行的字符全部替换为“ ”输出。
				cbuf = temp.toCharArray();
				for (int i = 0; i < cbuf.length; i++)
					if (cbuf[i] != '\n')
						cbuf[i] = ' ';

				bw.write(cbuf);
				temp = "";
				cbuf = null;
				break;
			}
		}

		br.close();
		bw.close();

	}

}