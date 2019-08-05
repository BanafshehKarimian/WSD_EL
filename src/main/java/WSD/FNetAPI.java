package WSD;
import ir.sbu.nlp.wordnet.data.model.FNSense;
import ir.sbu.nlp.wordnet.data.model.FNSynset;
import ir.sbu.nlp.wordnet.data.model.FNSynsetsRelation;
import ir.sbu.nlp.wordnet.service.FNSynsetService;

import java.util.Collection;
import java.util.Vector;

public class FNetAPI {

    public static FNSynsetService service=new FNSynsetService();

    public Vector<FNSynset> getSynsets(String word) {

        Vector<FNSynset> fnSynsets = service.FindSynsetsByWord(word);
        return fnSynsets;

    }
    public Vector<Long> getSynsetIds(Vector<FNSynset> fnSynsets) {

        Vector<Long> ids = new Vector<>();
        for (int i = 0; i < fnSynsets.size(); i++) {

            ids.add(fnSynsets.get(i).getId());
        }
        return ids;

    }
    public Vector<String> getGlosses(Vector<FNSynset> fnSynsets){

        Vector<String > glosses = new Vector<String>();
        for(int i=0 ; i< fnSynsets.size();i++){

            glosses.add(fnSynsets.get(i).getGloss());

        }
        return glosses;

    }
    public Vector<String> getExamples(Vector<FNSynset> fnSynsets){

        Vector<String > example = new Vector<String>();
        for(int i=0 ; i< fnSynsets.size();i++){

            example.add(fnSynsets.get(i).getExample());

        }
        return example;

    }
    public Vector<String> getPOS(Vector<FNSynset> fnSynsets){

        Vector<String > pos = new Vector<String>();
        for(int i=0 ; i< fnSynsets.size();i++){

            pos.add(fnSynsets.get(i).getPos());

        }
        return pos;

    }
    public Vector<String> getSemCategory(Vector<FNSynset> fnSynsets){

        Vector<String > sem = new Vector<String>();
        for(int i=0 ; i< fnSynsets.size();i++){

            sem.add(fnSynsets.get(i).getSemanticCategory());

        }
        return sem;

    }
    public Vector<String> getPOSfromID(Vector<Long> fnIds){

        Vector<String> sem = new Vector<String>();
        for(int i=0 ; i< fnIds.size();i++){
            //System.out.println(fnIds.get(i));
            String s = service.findSynsetById(fnIds.get(i)).getPos();
            sem.add(s);
           //System.out.println(s);

        }
        return sem;

    }
    public void printSynsetElement(FNSynset fnSynset){


           // System.out.print("Synset:{");
            for (int j = 0; j < fnSynset.getSenses().size(); j++) {
                FNSense fnSense = fnSynset.getSenses().elementAt(j);
               // System.out.print(fnSense.getWord().getValue().elementAt(0) + "_" + fnSense.getId() + " ,");
            }
         //   System.out.println("}");


    }
    public  Vector<String > getSynsetElement(FNSynset fnSynset){

        Vector<String > elements = new Vector<String>();
        for (int j = 0; j < fnSynset.getSenses().size(); j++) {
            FNSense fnSense = fnSynset.getSenses().elementAt(j);
            String s = fnSense.getWord().getValue().elementAt(0).trim();
            elements.add(s);
            //System.out.print(s + "_" + fnSense.getId() + " ,");
        }
        return elements;

    }
    public static Vector<String > getSynsetElement_2(FNSynset fnSynset){

        Vector<String > elements = new Vector<String>();
        for (int j = 0; j < fnSynset.getSenses().size(); j++) {
            FNSense fnSense = fnSynset.getSenses().elementAt(j);
            String s = fnSense.getWord().getValue().elementAt(0).trim();
            elements.add(s);
        }
        return elements;

    }
    public Vector<String > getSynsetsRelations(FNSynset fnSynset){

        Vector<String> relatedWords = new Vector<String>();
        Vector<FNSynsetsRelation> rels=fnSynset.getSynsetsRelations();
        for(int k=0;k<rels.size();k++)
            if(rels.elementAt(k).getSynset_1().getId().equals(fnSynset.getId())) {
                FNSynset rel=rels.elementAt(k).getSynset_2();
                relatedWords.addAll(getSynsetElement_2(rel));
            }

        return relatedWords;

    }
    private static void FNAPIUse(String word) {
//instantiate a FNSynsetService
        FNSynsetService service=new FNSynsetService();
//find all synset containd the word
        Vector<FNSynset> fnSynsets=service.FindSynsetsByWord(word);
//print every synset
        for(int i=0;i<fnSynsets.size();i++){
           // System.out.print("Synset:{");
            FNSynset fnSynset=fnSynsets.elementAt(i);
            for(int j=0;j<fnSynset.getSenses().size();j++){
                FNSense fnSense=fnSynset.getSenses().elementAt(j);
                //System.out.print(fnSense.getWord().getValue().elementAt(0)+"_"+fnSense.getId()+" ,");
            }
           // System.out.println("}");
          //  System.out.println("Father[s]:");
            Vector<FNSynsetsRelation> fathers=fnSynset.getSynsetsRelations();
            for(int k=0;k<fathers.size();k++)
                if(fathers.elementAt(k).getSynset_1().getId().equals(fnSynset.getId())
                        &&fathers.elementAt(k).getRelType().equalsIgnoreCase("Hypernym")){
                   // System.out.print("Synset:{");
                    FNSynset father=fathers.elementAt(k).getSynset_2();
                    for(int j=0;j<father.getSenses().size();j++){
                        FNSense fnSense=father.getSenses().elementAt(j);
                        //System.out.print(fnSense.getWord().getValue().elementAt(0)+"_"+fnSense.getId()+" ,");
                    }
                   // System.out.println("}");
                }
            // .out.println("********************");
        }
    }


}


//System.out.println(".......................");
//System.out.println(fnSynsets.get(i).getGloss());
            /*System.out.println(".......................");
            Vector<FNSense> f = fnSynsets.get(i).getSenses();
            for(int j =0 ; j< f.size();j++){
                System.out.println(".......");
                Vector<String> w = f.get(j).getWord().getValue();
                for(int k=0; k<w.size();k++)
                    System.out.println(w.get(k)+"_"+f.get(j).getId());
            }*/