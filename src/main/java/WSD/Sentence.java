package WSD;

import java.util.Vector;

public class Sentence {

    private String content;
    private Vector<String> meaning;
    private int i=0;

    public String getContent() {
        //content =  content+' ';
        System.out.println(content);
        if (content!=null)
            content = new WordSenceDisambiguatorMain().main1(content);
        //content = content.replace(" ","<br>")+"--";
        return content;
    }

    public Vector<String> getMeaning() {
        content =  content+' ';
        content = content.replace(" ","<br>")+"--";
        return meaning;
    }


    public void setContent(String content) {
        this.content = content;
    }

}
