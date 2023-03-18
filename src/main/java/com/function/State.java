package com.function;

public class State {

    private String name;
    private String code;  
  
    public State(String name, String code) {
      this.name = name;
      this.code = code;
    }
  
    public String getname() {
      return name;
    }
  
    public String getcode() {
      return code;
    }
  
    @Override
    public String toString() {
      return "State={name=" + name + ",code=" + code + "}";
    }
  }