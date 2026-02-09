package com.JMR.IntegracionAI.Controller;


import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/imagen")
public class GeneradorImagenesController {

    private final ImageModel model;

    public GeneradorImagenesController(ImageModel model) {
        this.model = model;
    }

    @GetMapping
    public String generacionImagen(String prompt) {
        var options = ImageOptionsBuilder.builder()
                .height(1024)
                .width(1024)
                .build();
        var response = model.call(new ImagePrompt(prompt, options));
        return response.getResult().getOutput().getUrl();
    }
}

