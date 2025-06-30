package com.palomino.confecontrol.security;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UsernameNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("redirect:/login");
        modelAndView.addObject("error", ex.getMessage());
        return modelAndView;
    }
}
