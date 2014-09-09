package com.dwis.shorten;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.nio.charset.StandardCharsets;

/**
 * Created with IntelliJ IDEA.
 * User: Dominik
 * Date: 09.09.14
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class ShortenController {

    @Autowired
    private StringRedisTemplate redis;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public void redirect(@PathVariable String id, HttpServletResponse response) throws Exception {
        final String url = redis.opsForValue().get(id);
        if (url != null)
            response.sendRedirect(url);
        else
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @RequestMapping(value = "/shortIt", method = RequestMethod.POST)
    @ResponseBody
    public String save(@RequestParam(value = "url") String longUrl, HttpServletResponse response) {
        final String[] validatorProps = new String[]{"http", "https"};
        final UrlValidator urlValidator = new UrlValidator(validatorProps);
        final String shortUrl;

        if (urlValidator.isValid(longUrl)) {
            final String id = Hashing.murmur3_32().hashString(longUrl, StandardCharsets.UTF_8).toString();
            redis.opsForValue().set(id, longUrl);
            shortUrl = "http://localhost:8080/short/" + id;
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            shortUrl = "NaN";
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        return shortUrl;
    }

    @RequestMapping(value = "/shorten")
    public String shorten() {
        return "shorten";
    }
}

