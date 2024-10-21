package com.assignment.security;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.owasp.encoder.Encode;
import org.springframework.stereotype.Component;

@Component
public class XSSProtected {

    public String encodeAllHTMLElement(String input) {
        String safeData = Encode.forHtml(input);
        return safeData;
    }

    public String sanitize(String input) {
        String safeHTML =  Jsoup.clean(input, Safelist.relaxed());
        return safeHTML;
    }

}
