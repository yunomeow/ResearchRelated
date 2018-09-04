import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class Main {
	public static void main (String args[]){
		new Main().getAllpost();
	}
	private void getAllpost(){
		int startIndex = 6001;
		int endIndex = 6231;
		for(int i=startIndex;i<=endIndex;i++){
			String str = print_content("https://www.ptt.cc/bbs/ALLPOST/index"+ i +".html")[0];
			getArticleList(str);
			try {
				System.out.println("Page " + i + " is finished.");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	private String[] print_content(String https_url){
		try {	
			URL url;
			url = new URL(https_url);
			HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
			con.setRequestProperty("Cookie", "over18=1;");
			if(con!=null){	
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String input;
				String[] res = new String[2];
				res[0] = "";
				res[1] = "";
				while ((input = br.readLine()) != null){
					res[0] += input;
					res[1] += input;
					res[1] += "\n";
				}
				br.close();
				//System.out.println(res[1]);
				return res;

			}
		} catch (IOException e) {
			e.printStackTrace();
		}


		return null;

	}

	private void getArticleList(String input){
		String patternStr =  "<div class=\"r-ent\">.*?</a>";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(input);
		while (matcher.find()) {
			String tmp = matcher.group();
			// System.out.println("matcher.group():\t"+tmp);
			saveArticle(getArticleURL(tmp));

		}

	}
	private String getArticleURL(String input){
		String patternStr =  "<a href=.*?>";
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(input);
		String urlStr = null;
		if (matcher.find()) {
			urlStr = matcher.group();
		}
		urlStr = urlStr.split("<a href=\"|\">")[1];
		//System.out.println(urlStr);
		String boardName = null;
		patternStr =  "\\(.*?</a>";
		pattern = Pattern.compile(patternStr);
		matcher = pattern.matcher(input);
		while (matcher.find()) {
			boardName = matcher.group();
		}
		boardName = boardName.split("\\(|\\)")[1];
		//System.out.println(boardName);
		urlStr = "https://www.ptt.cc"+ urlStr.replace("ALLPOST", boardName);
		//System.out.println(urlStr);
		return urlStr;
	}
	private void saveArticle(String urlStr){
		String[] res =  print_content(urlStr);
		if(res == null)return;
		String result = res[1];
		String filename = urlStr.substring(urlStr.length()-23, urlStr.length());
		String pathname = "C:\\ptt\\";
		//System.out.println(urlStr);
		//System.out.println(filename);
		try {
			PrintWriter pw = new PrintWriter(new File(pathname+filename));
			pw.print(result);
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
