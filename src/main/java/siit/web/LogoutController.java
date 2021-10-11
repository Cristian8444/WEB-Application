package siit.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/logout")
public class LogoutController {

//    private int value = 0;
//
//    @RequestMapping(method = RequestMethod.GET)
//    protected String displayLoginForm() {
//        return "login";
//    }

    @RequestMapping(method = RequestMethod.GET)
    protected ModelAndView performLogout(HttpSession session) {

        ModelAndView mav2 = new ModelAndView();
            session.invalidate();
            mav2.setViewName("redirect:/login");

        return mav2;
    }
}