package WSD;
import ir.sbu.nlp.wordnet.data.model.FNSynset;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Disambiguator {

    public Integer scoreBasedOnSynsets(Vector<String> elements, String tokens){

        Integer score = 0;

        for (int i=0;i<elements.size();i++){

            if (tokens.contains(elements.get(i))) {
                score++;
                //System.out.println("Found__"+elements.get(i));
            }

        }
        return score;
    }

    public Double cosineSimm(Vector<String>tokens,String example,String def){

        Vector<String> vector = new Vector<String>();
        for (int i=0; i <example.split(" ").length;i++)
            vector.add(example.split(" ")[i]);
        for (int i=0; i <def.split(" ").length;i++)
            vector.add(def.split(" ")[i]);
        Map<CharSequence, Integer> occurrences1 = new HashMap<CharSequence, Integer>();
        Map<CharSequence, Integer> occurrences2 = new HashMap<CharSequence, Integer>();


        for ( String word : tokens ) {
            Integer oldCount = occurrences1.get(word);
            if ( oldCount == null ) {
                oldCount = 0;
            }
            occurrences1.put(word, oldCount + 1);
        }
        for ( String word : vector ) {
            Integer oldCount = occurrences1.get(word);
            if ( oldCount == null ) {
                oldCount = 0;
            }
            occurrences2.put( word, (oldCount + 1));
        }
        CosineSimilarity cs = new CosineSimilarity( );
        return (Double) cs.cosineSimilarity(occurrences1,occurrences2);

    }


    public void checkPOS(Vector<Vector<FNSynset>> synsets,Vector<String > inputPOS, Vector<Vector<String>> pos, Vector<Vector<Long>> ids, Vector<Vector<String>> glosses, Vector<Vector<String>> semCat, Vector<Vector<String>> examples){

        for (int i =0; i< inputPOS.size(); i++){

            int counter = synsets.get(i).size();
            String targetPos = inputPOS.get(i);

            for (int j=synsets.get(i).size()-1; j >-1 ;j--){

                if(!targetPos.equals(pos.get(i).get(j))){

                    synsets.get(i).remove(j);
                    pos.get(i).remove(j);
                    ids.get(i).remove(j);
                    glosses.get(i).remove(j);
                    examples.get(i).remove(j);
                    semCat.get(i).remove(j);
                    counter--;

                }

            }
            if(counter==0);


        }

    }

    public void checkCount(Long threshold,Vector<Vector<Long>> count, Vector<Vector<FNSynset>> synsets, Vector<Vector<String>> pos, Vector<Vector<Long>> ids, Vector<Vector<String>> glosses, Vector<Vector<String>> semCat, Vector<Vector<String>> examples){

        for (int i =0; i< synsets.size(); i++){
            for (int j=synsets.get(i).size()-1; j >-1 ;j--){

                if(count.get(i).get(j)<threshold){

                    synsets.get(i).remove(j);
                    pos.get(i).remove(j);
                    ids.get(i).remove(j);
                    glosses.get(i).remove(j);
                    examples.get(i).remove(j);
                    semCat.get(i).remove(j);

                }

            }

        }

    }

    public void removeId(Vector<Long> ignoredIds, Vector<Vector<FNSynset>> synsets, Vector<Vector<String>> pos, Vector<Vector<Long>> ids, Vector<Vector<String>> glosses, Vector<Vector<String>> semCat, Vector<Vector<String>> examples){

        for (int i =0; i< synsets.size(); i++){
            for (int j=synsets.get(i).size()-1; j >-1 ;j--){

                if(ignoredIds.contains(ids.get(i).get(j))){

                    synsets.get(i).remove(j);
                    pos.get(i).remove(j);
                    ids.get(i).remove(j);
                    glosses.get(i).remove(j);
                    examples.get(i).remove(j);
                    semCat.get(i).remove(j);

                }

            }

        }

    }

}
