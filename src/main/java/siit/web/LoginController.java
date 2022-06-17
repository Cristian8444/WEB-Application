package siit.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/login")
public class LoginController {

    private int value = 0;

    @RequestMapping(method = RequestMethod.GET)
    protected String displayLoginForm() {
        return "login";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected ModelAndView performLogin(HttpSession session,
                                        @RequestParam String user, @RequestParam String password) {

        ModelAndView mav = new ModelAndView();

        if (user.equals(password)) {
            session.setAttribute("logged_user", user);
            mav.setViewName("redirect:/customers");
        } else {
            value++;
            String error = "User and password do not match! " + value;
            mav.setViewName("login");
            mav.addObject("error", error);
        }

        return mav;
    }
}
