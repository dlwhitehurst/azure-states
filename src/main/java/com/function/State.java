/*
 * Copyright 2023 David L. Whitehurst
 *
 * Licensed under the Apache License, Version 2.0
 * (the "License"); You may not use this file except
 * in compliance with the License. You may obtain a
 * copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the
 * License.
 *
 */

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