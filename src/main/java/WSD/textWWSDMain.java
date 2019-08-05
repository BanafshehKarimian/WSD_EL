package WSD;
import org.json.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.min;
import java.util.Scanner;

public class textWWSDMain {


    public static String[] GetSentences(String fileName){

        String all = "";
        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ="";
            int j =0;
            while((line = bufferedReader.readLine()) != null) {
                all = all+ line+"\n";
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return all.replace("[\n  [","").replace("]\n]","").replaceAll("},( )*\n( )+","},").replace("}, {","},{").replace("---","\n").split("],\n  \\[");


    }

    public static void WSD(String input,String output,String output2){
        WordSenceDisambiguatorMain wsdm = new WordSenceDisambiguatorMain();
        String[] sentences = GetSentences(input);
        FileWriter fileWriter =
                null;
        BufferedWriter bufferedWriter =
                null;
        FileWriter fileWriter2 =
                null;

        BufferedWriter bufferedWriter2 =
                null;

        try {
            fileWriter = new FileWriter(output+".json");
            fileWriter2 = new FileWriter(output2+"result"+".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        bufferedWriter2 =
                new BufferedWriter(fileWriter2);
        bufferedWriter =
                new BufferedWriter(fileWriter);
        String result = "";
        for (int i=0;i<sentences.length;i++){

            String[] words = sentences[i].split("},\\{");
            String line = "";
            JSONObject obj = null;
            try {
                obj = new JSONObject(words[0].replace("\n","")+"}\n");
                words[0]=words[0].replace("\n","")+"}--";
                String n = obj.getString("word");
                //result = result+obj.getString("resource").replace("\n","")+"--";
                line= line + n +" ";
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int j=1;j<words.length-1;j++){

                obj = null;
                try {
                    obj = new JSONObject("{"+words[j].replace("\n","")+"}\n");
                    String n = obj.getString("word");
                    words[j]="{"+words[j].replace("\n","")+"}--";
                    line= line + n +" ";
                        //result = result+obj.getString("resource").replace("\n","")+"--";
                    //System.out.println("..........................................................................");
                      // b  System.out.println(obj.getString("resource"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //line=line+"{"+words[j].replace("\n","")+"}\n";

            }
            obj = null;
            try {
                obj = new JSONObject("{"+words[words.length-1].replace("\n",""));
                words[words.length-1]="{"+words[words.length-1].replace("\n","")+"--";
                String n = obj.getString("word");
                //result = result+obj.getString("resource").replace("\n","")+"--";
                line= line + n +" ";
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //line=line+"{"+words[words.length-1].replace("\n","")+"\n";
            //System.out.println(line);
            line=line.replace("#","!");
            String[] out =  wsdm.main2(line).split(",");

            try {

                //System.out.println(words.length);
                //System.out.println(out.length);
                int x = 0;
                for (int j = 0;j<min(out.length,words.length);j++) {
                    /*if (words[j].contains("\"word\": \"#\"")){

                        x++;
                        bufferedWriter.write(words[j].replace("}--","")+",\"FarseNetID\":0}");
                        continue;

                    }*/
                     System.out.println(words[j]);
                    if (words[j].contains("\"resource\":")) {
                        bufferedWriter2.write(out[j - x] + "::" + words[j].split("\"resource\":")[1].split("\"ambiguities\":")[0].replace("\n", ""));
                        bufferedWriter2.newLine();
                    }
                    bufferedWriter.write(words[j].replace("}--","")+",\"FarseNetID\":"+out[j-x]+"}");
                    bufferedWriter.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            //System.out.println(line);

        }

        try {
            bufferedWriter.close();
            bufferedWriter2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    static Map<String,Integer> st = new HashMap<String, Integer>();
    static Map<String,Integer> link = new HashMap<String, Integer>();
    public static void GetStatistic(String fileName){

        String all = "";
        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ="";
            while((line = bufferedReader.readLine()) != null) {
                String id = line.split("::")[0];
                String link = line.split("\"iri\":")[1].split(",")[0].replace(" ","");

                if (st.containsKey(id+"-"+link)){
                    Integer x  = st.get(id+"-"+link)+1;
                    st.remove(id+"-"+link);
                    st.put(id+"-"+link,x);

                }
                else {
                    st.put(id+"-"+link,1);
                }
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;//all.replace("[\n  [","").replace("]\n]","").replaceAll("},( )*\n( )+","},").replace("}, {","},{").replace("---","\n").split("],\n  \\[");


    }
    public static void final_map(String fileName){

        String all = "";
        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ="";
            while((line = bufferedReader.readLine()) != null) {
                String id = line.split(",")[0];
                String link = line.split(",")[1];//line.split("\"iri\":")[1].split(",")[0].replace(" ","");
                st.put(id+"-"+link, Integer.valueOf(line.split(",")[2]));
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;

    }
    public static void main(String[] args) {
       boolean getSt = false;
       boolean result_ready = true;
       if(getSt) {
           if (result_ready)
               final_map("./map_out_final.txt");
           else {
               FileWriter fileWriter =
                       null;
               BufferedWriter bufferedWriter =
                       null;
               try {
                   fileWriter = new FileWriter("./map_out.csv");
               } catch (IOException e) {
                   e.printStackTrace();
               }
               try {
                   bufferedWriter =
                           new BufferedWriter(new OutputStreamWriter(new FileOutputStream("./map_out.txt"), StandardCharsets.UTF_8));
               } catch (FileNotFoundException e) {
                   e.printStackTrace();
               }
               File folder = new File("./result");
                   File[] listOfFiles = folder.listFiles();
               int max = 0;
               for (int i = 0; i < listOfFiles.length; i++)
                   GetStatistic("./result/" + listOfFiles[i].getName());
               for (Map.Entry<String, Integer> entry : st.entrySet()) {
                   String id = entry.getKey().split("-")[0];
                   if (!id.equals("0") && !id.equals("}")) {
                       try {
                           bufferedWriter.write(entry.getKey().replace("-\"", ",\"") + "," + entry.getValue());
                           bufferedWriter.newLine();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                       if (entry.getValue() > max)
                           max = entry.getValue();
                       link.put(entry.getKey().replace("-\"", ",\"").split(",")[1], 0);
                   }
                   //System.out.println("id-link::" + entry.getKey() + "value::" + entry.getValue());
               }
               System.out.println(link.size());
               System.out.println(max);
               try {
                   bufferedWriter.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }
           Evaluator ev = new Evaluator("data.txt");
           for (int i=0;i<21;i++) {
                System.out.println(i+":");
               ev.calc(st, i);
               ev.getPOSDetails();
           }
           //ev.getPOSDetails();
       }
       else {
          // for (int i = 83; i < 776; i++)
            //   WSD("./corpus/T1A/output/T1A" + i + ".txt.json", "./corpus/T1A/T1A" + i, "./result/T1A" + i + "-");
           Scanner input = new Scanner(System.in);
           System.out.print("Enter input address: ");
           String myString = input.next().replace("\\","/");
           System.out.print("Enter output address: ");
           String myString2 = input.next().replace("\\","/");
          // myString="C:\\Intellij\\api-2\\api\\corpus\\data\\raw\\repository_new\\".replace("\\","/");
          // myString2="C:\\Intellij\\api-2\\api\\corpus\\data\\raw\\".replace("\\","/");
            ///home/app2/corpus/data/raw/repository_new/
           ///home/app2/corpus/data/raw/
           File folder = new File(myString);
           File[] listOfFiles = folder.listFiles();
           System.out.println(listOfFiles.length);
           for (int i = 0; i < listOfFiles.length; i++) {

               WSD(myString + listOfFiles[i].getName(), myString2+"1-" + i, myString2+"/result/1- " + i + "-");

           }
       }
       // System.out.println(sentences.length);
    }

}
