package WSD;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SentenceController {

    @GetMapping("/wsd")
    public String sentenceForm(Model model) {
        model.addAttribute("sentence", new Sentence());
        return "wsd";
    }

    @PostMapping("/wsd")
    public String sentenceSubmit(@ModelAttribute Sentence sentence) {
        return "result";
    }

}
