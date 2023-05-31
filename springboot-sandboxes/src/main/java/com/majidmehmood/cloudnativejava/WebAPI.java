package com.majidmehmood.cloudnativejava;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebAPI {
  @GetMapping(path = "/")
  public String name() {
    return "Majid";
  }
}
