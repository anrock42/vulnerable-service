package services.publics.vulnerable.api;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proxy")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class ProxyApi {

    @GetMapping
    public String redirect(@RequestParam("url") String url) {
        // open redirect vulnerability
        // reflective xss
        return "redirect:" + url;
    }
}
