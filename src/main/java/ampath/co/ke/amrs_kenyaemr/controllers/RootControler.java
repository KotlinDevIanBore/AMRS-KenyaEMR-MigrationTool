package ampath.co.ke.amrs_kenyaemr.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@Transactional
//@RequestMapping("/")
public class RootControler {
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public ModelAndView admin() throws InterruptedException, IOException {
        ModelAndView modelAndView = new ModelAndView();
        return new ModelAndView("redirect:/locations/");
    }

}
