package com.lukaskucera.numberneighbors.game;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController {
    @ResponseBody
    @GetMapping(value = "/")
    public String welcome(
        @RequestParam(value = "name", defaultValue = "Spring") String name
    ) {
        return String.format("Welcome to Number Neighbors %s!", name);
    }
}
