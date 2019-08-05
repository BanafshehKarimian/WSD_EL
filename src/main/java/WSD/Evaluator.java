package WSD;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static java.util.stream.Collectors.toMap;

public class Evaluator {


    static Map<String,Integer> id_cnt = new HashMap<String, Integer>();
    static Map<String,String> id_link = new HashMap<String, String>();
    static Map<String,String> id_link_true = new HashMap<String, String>();
    static FNetAPI fna;


    Evaluator(String fileName){
        fna = new FNetAPI();
        String all = "";
        try {

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ="";
            while((line = bufferedReader.readLine()) != null) {
                String id = line.split(",")[0];
                String link = line.split(",")[1];//line.split("\"iri\":")[1].split(",")[0].replace(" ","");
                id_link_true.put(id,link);
            }
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;

    }

    public void getPOSDetails(){

        Vector<Long> ids = new Vector<Long>();
        for (Map.Entry<String, String> entry : id_link.entrySet()){

            ids.add(Long.valueOf(entry.getKey()));

        }
        Vector<String> poses = fna.getPOSfromID(ids);
        HashMap<String,Integer> frequencymap = new HashMap<String,Integer>();
        for(String a : poses) {
            if(frequencymap.containsKey(a)) {
                frequencymap.put(a, frequencymap.get(a)+1);
            }
            else{
                frequencymap.put(a, 1);
            }
        }
        for (Map.Entry<String, Integer> entry : frequencymap.entrySet())
            System.out.println(entry.getKey()+":"+entry.getValue());
        return;
    }


    public void precision_recall(){

        int count_total = id_link_true.size();
        int tp = 0;
        //System.out.println(count_total);
        //System.out.println(id_link.size());
        //Map<String, String> map = id_link_true.entrySet().stream()
        //        .collect(toMap(e -> e.getValue(), e -> id_link.get(e.getKey())));
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : id_link.entrySet()){

            if(id_link_true.containsKey(entry.getKey())){

                map.put(id_link_true.get(entry.getKey()),entry.getValue());

            }

        }
        int count_recieved = map.size();
        for (Map.Entry<String, String> entry : map.entrySet()){
            //System.out.println(entry.getValue()+"-"+entry.getKey());
            if(entry.getValue().equals(entry.getKey()))
                tp++;

        }
        //System.out.println(tp);
        float p = (float)tp /(float) count_recieved;
        float r = (float)tp/(float) count_total;
        //float r = (float)tp/(float) (count_total-tp);
        System.out.println("RESULT::");
        System.out.println("P:"+p);
        System.out.println("r:"+r);
    }

    public void calc(Map<String,Integer> st,int threshold){

        id_link.clear();
        id_cnt.clear();
        for (Map.Entry<String, Integer> entry : st.entrySet()){
            //System.out.println(entry.getValue());
            if (entry.getValue()>threshold){
                //System.out.println("1");
                if(id_link.containsKey(entry.getKey())){
                    //System.out.println("2");
                    String id = entry.getKey().split("-")[0];
                    String link = entry.getKey().split("-")[1];
                    int v = id_cnt.get(id);
                    if(v<entry.getValue()){

                        id_cnt.remove(id);
                        id_link.remove(id);
                        id_link.put(id,link);
                        id_cnt.put(id,entry.getValue());

                    }

                }
                else {
                    //System.out.println("3");
                    String id = entry.getKey().split("-")[0];
                    String link = entry.getKey().split("-")[1];
                    id_link.put(id,link);
                    id_cnt.put(id,entry.getValue());

                }
            }
        }

        precision_recall();
        return;
    }

}
