package com.example.corona2;

public class AnnouncerImpl implements Announcer {

    private Advisor advisor = ObjectFactory.getOurInstance().createObject(Advisor.class);
    @Override
    public void announce(String s) {
        advisor.advise();
        System.out.println(s);
    }
}
