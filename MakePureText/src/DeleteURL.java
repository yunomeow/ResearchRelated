import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeleteURL {
	private BufferedWriter bw;
	private HashMap<String,Integer> hm;
	public DeleteURL(){
		hm = new HashMap<String,Integer>();
	}
	public String process(String name, String input){
		String res = null;
		Integer cnt = 0;
	//	System.out.println("process " + input);
		Pattern p = Pattern.compile("http\\S*+");
		Matcher m = p.matcher(input);

		while (m.find()) {
			String tmp = m.group();
			//System.out.println("matcher.group():\t"+tmp);
			cnt++;
		}
		res = input.replaceAll("http\\S*+", "");
		//System.out.println(cnt);
		hm.put(name, cnt);
		return res;
	}
	public void printResult(){
		try {
			bw = new BufferedWriter(new FileWriter("C:\\text_analysis\\urlResult.txt"));
			for (Object key : hm.keySet()) {
				bw.write(key + " " + hm.get(key) + "\n");
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
