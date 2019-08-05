package WSD;
import ir.sbu.nlp.wordnet.data.model.FNSense;
import ir.sbu.nlp.wordnet.data.model.FNSynset;

import java.io.IOException;
import java.util.Vector;


public class Main {

    public static void main(String[] args) {

        System.out.println("maaaaaaaaaaaaaaaaaaaainnnnnnnnnnnnnnnnnnnnnnnnnnnnnn2");
        FNetAPI fnet  = new FNetAPI();
        Disambiguator da = new Disambiguator();
        Preprocessor preprocessor = new Preprocessor();
        String inputString = "شیر سلطان جنگل ها سرسبز است";
        //String inputString = "من غذا خوردن سیر شدن";

        //preprocessing the input text
        inputString = preprocessor.normalizer(inputString);
        Vector<String> tokens = preprocessor.Tokenizer(inputString);
        Vector<String > inputPOS  = null;
        try {
            inputPOS = preprocessor.POStag(inputString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Vector<Vector<String>> glosses = new Vector<Vector<String>>();
        Vector<Vector<String>> elements = new Vector<Vector<String>>();
        Vector<Vector<Double>> scores = new Vector<Vector<Double>>();
        Vector<Integer> maxScoreIndex = new Vector<Integer>();
        Vector<Vector<String>> examples = new Vector<Vector<String>>();
        Vector<Vector<String>> pos = new Vector<Vector<String>>();
        Vector<Vector<String>> semCat = new Vector<Vector<String>>();
        Vector<Vector<Long>> ids = new Vector<Vector<Long>>();
        Vector<Vector<FNSynset>> synsets = new Vector<Vector<FNSynset>>();
        for (int i=0; i< tokens.size(); i++){

            System.out.println("Word:"+tokens.get(i)+"_POS:"+inputPOS.get(i));
            synsets.add(fnet.getSynsets(tokens.get(i)));
            ids.add(fnet.getSynsetIds(synsets.get(i)));
            glosses.add(fnet.getGlosses(synsets.get(i)));
            examples.add(fnet.getExamples(synsets.get(i)));
            pos.add(fnet.getPOS(synsets.get(i)));
            semCat.add(fnet.getSemCategory(synsets.get(i)));

        }
        da.checkPOS(synsets,inputPOS,pos,ids,glosses,semCat,examples);
        for (int i=0; i< tokens.size(); i++) {
            Vector<Double> score = new Vector<Double>();
            Double max = Double.valueOf(0);
            maxScoreIndex.add(0);
            for (int j = 0; j < synsets.get(i).size(); j++) {

                //fnet.printSynsetElement(synsets.get(i).get(j));
                elements.add(fnet.getSynsetElement(synsets.get(i).get(j)));
                Integer score1 = da.scoreBasedOnSynsets(elements.lastElement(),inputString);
                Double score2 = da.cosineSimm(tokens,examples.get(i).get(j),glosses.get(i).get(j));
                Integer score3 = da.scoreBasedOnSynsets(fnet.getSynsetsRelations(synsets.get(i).get(j)),inputString);
                //System.out.print(score1+score2+score3+":"+j);
                score.add(score1+score2+score3);
                if (max<score1+score2+score3){

                    maxScoreIndex.remove(maxScoreIndex.size()-1);
                    maxScoreIndex.add(j);
                    max = score1+score2+score3;
                    //System.out.println(j);

                }
                System.out.println("Id:" + ids.get(i).get(j) + "_" + pos.get(i).get(j) + "_" + semCat.get(i).get(j) + "_gloss:" + glosses.get(i).get(j));
                System.out.println("Score:"+score1+"_"+score2+"_"+score3);
                System.out.println("Examples:"+examples.get(i).get(j));

            }
            scores.add(score);
        }


        for (int i=0; i<tokens.size();i++){

            Integer index = maxScoreIndex.get(i);
            if (glosses.get(i).size()!=0 && +scores.get(i).size()!=0) {
                System.out.println("Word:" + tokens.get(i) +"_Score:" + scores.get(i).get(index)+"_selected gloss:" + glosses.get(i).get(index));
            }
            else
                System.out.println("Word:"+tokens.get(i)+"_Score:None_selected gloss:None");

        }

    }


}
