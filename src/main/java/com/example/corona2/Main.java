package com.example.corona2;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = Application.run("com.example.corona2", new HashMap<>(Map.of(Policeman.class, AngryPolicemanImpl.class)));
        CoronaDisinfectant coronaDisinfectant = context.getObject(CoronaDisinfectant.class);
        coronaDisinfectant.start();
    }

}
