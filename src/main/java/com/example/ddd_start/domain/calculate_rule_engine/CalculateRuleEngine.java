package com.example.ddd_start.domain.calculate_rule_engine;

import java.util.List;

public interface CalculateRuleEngine {

  public void evalutate( List<?> facts);
}