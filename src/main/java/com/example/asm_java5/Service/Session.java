package com.example.asm_java5.Service;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@Component
@Data
public class Session {
    private String keyword = "";
}
