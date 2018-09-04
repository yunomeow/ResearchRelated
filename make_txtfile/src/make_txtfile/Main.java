package make_txtfile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader buf;
		try {
			buf = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\text_analysis\\facebook_compare\\raw_text_cond4.txt")));
			String tmp,res="";
			while((tmp = buf.readLine()) != null){
				res = res + tmp;
			}
			buf.close();
			String text[] = res.split("\t");
			System.out.println(text.length);
			int pnum = 0;
			int[] cond={1,2,3,4};
			for(int i=0;i<text.length;i++){
			//	if(i % 4 == 0)pnum++;
				pnum++;
				BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\text_analysis\\facebook_compare\\"+pnum+"_" + 4 + ".txt"));
				if(text[i].charAt(0)=='\"')
					bw.write(text[i].substring(1, text[i].length()-1));
				else
					bw.write(text[i]);
				bw.close();
				System.out.println(text[i]);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
