import com.jayway.jsonpath.JsonPath;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class Main {
   public static String base_url = "http://content-dom-origin.api.beachbodyondemand.com/v2/trainers/";
    private static final  String USER_AGENT = "Mozilla/5.0";
    public static void main(String[] args) throws Exception {
        List<String> keys = returnKeys("/home/harut/IdeaProjects/generator/src/main/resources/trainers.txt");
        List<String> paths = returnKeys("/home/harut/IdeaProjects/generator/src/main/resources/paths.txt");
//        createFilesAndBodys("$.items[0].slug",keys,paths);

        replaceContent("/home/harut/IdeaProjects/generator/src/main/resources/trainer.feature",keys);

    }


    private static void createFilesAndBodys(String keyToCreateFiles,List<String> keysToRequest, List<String> keysForBody) throws Exception {
        for (int i = 0; i < keysToRequest.size(); i++) {
         String jsonString = sendGet(base_url + keysToRequest.get(i));
            String fileName = JsonPath.read(jsonString, keyToCreateFiles);
            System.out.println(fileName);
            File file = new File("/home/harut/IdeaProjects/generator/src/main/resources/trainersContent/" + fileName + ".properties");
            if (!file.exists()) file.createNewFile();
            BufferedWriter bf = new BufferedWriter(new FileWriter(file));
            for (int i1 = 0; i1 < keysForBody.size(); i1++) {
                String value = JsonPath.read(jsonString,keysForBody.get(i1)).toString();
                bf.write(keysForBody.get(i1) + "=" + value);
                bf.newLine();
            }
            bf.flush();
            bf.close();


        }
    }






    private static String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestProperty("User-Agent", USER_AGENT);


        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {

            response.append(inputLine);
        }
        in.close();


        return response.toString();
    }

    private static List<String> returnKeys(String file) throws FileNotFoundException {
        List<String> keys= new ArrayList<>();
        BufferedReader bf = new BufferedReader(new FileReader(file));
        bf.lines().forEach(keys :: add);

        return keys;
    }

    private static void replaceContent(String file,List<String> keys) throws IOException {
        BufferedReader bfr = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        bfr.lines().forEach(e -> sb.append(e + "\n"));
        bfr.close();
        String fullText = sb.toString();
        for (int i = 0; i < keys.size(); i++) {
            File fl = new File("/home/harut/IdeaProjects/generator/src/main/resources/verifyContent/verify_content_of_" + keys.get(i) + "trainer.feature");
            if (!fl.exists()) fl.createNewFile();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fl));
            bufferedWriter.write(fullText.replaceAll("trainerSlug",keys.get(i)));
            bufferedWriter.flush();
            bufferedWriter.close();
        }

    }
}
