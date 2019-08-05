package WSD;
import edu.stanford.nlp.ling.TaggedWord;
import ir.ac.iust.nlp.jhazm.*;
import sun.font.TrueTypeFont;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

public class Preprocessor {



    public Vector<String> POStag(String i) throws IOException {

        //System.out.println("----------");
            POSTagger tagger = new POSTagger();
        //String[] input = new String[] { "من","آرام","به", "مدرسه","ی","زیبا","رفته بودم", "."};
        //String[] input = new String[] { "شیر","سلطان","جنگل", "سرسبز","است"};
        String [] input = Tokenizer(i).toArray(new String[0]);//i.split(" ");
       // System.out.println("----------");
        List<TaggedWord> actual = tagger.batchTag(Arrays.asList(input));
        Vector<String> pos = new Vector<String>();
        //System.out.println("---------------------------------------------------------");
        for (TaggedWord x : actual)
            pos.add(x.tag().replace("N","Noun").replace("AJ","Adjective").replace("V","Verb").replace("Noune","Noun").replace("ADVerb","Adverb").replace("PUNounC","PUNC"));//

       /*
       .replace("PRO","Noun")
        pos.add("Noun");
        pos.add("Noun");
       // pos.add("Verb");
        pos.add("Noun");
        //pos.add("Noun");
        pos.add("Adjective");
        pos.add("Verb");*/
        return pos;
    }

    public Vector<String> Tokenizer(String input){
        Vector<String> tokenized = new Vector<String>();
        List<String> actual;
        try {
            Normalizer normalizer1 = new Normalizer(true, false, false);
            Normalizer normalizer2 = new Normalizer(false, true, false);
            Normalizer normalizer3 = new Normalizer(false, false, true);
            WordTokenizer wordTokenizer = new WordTokenizer(false);
            input = normalizer1.run(normalizer2.run(normalizer3.run(input)));
            actual = wordTokenizer.tokenize(input);
            Lemmatizer lemmatizer = new Lemmatizer();

            Stemmer stemmer = new Stemmer();

            for(String x:actual) {
               // System.out.println(x);
                String s = lemmatizer.lemmatize(x);
                if (s.contains("#")&& !s.split("#")[0].equals(""))
                    tokenized.add(s.split("#")[0]+"ن");
                else
                    tokenized.add(s.replace("#",""));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokenized;
    }

    public Vector<String> SimpleTokenizer(String input){
        Vector<String> tokenized = new Vector<String>();
        String []sp = input.split(" ");
       // System.out.println("Tokenizing.....");
        for(int i=0; i< sp.length;i++){

            //System.out.println(sp[i]);
            tokenized.add(sp[i]);

        }
        return tokenized;
    }


    public String  normalizer(String input){

        return input;

    }
}
