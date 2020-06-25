package com.example.corona2;

public class AdvisorImpl implements Advisor {

    @InjectProperty
    private String alcohol;

    @Override
    public void advise() {
        System.out.println("To protect from covid-19 drink "+ alcohol);
    }
}
