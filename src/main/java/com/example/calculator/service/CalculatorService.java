package com.example.calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    final static double PI = 3.142;

    public int sum(int a, int b) {
        return a + b;
    }

}
