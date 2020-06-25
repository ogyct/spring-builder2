package com.example.corona2;

import javax.annotation.PostConstruct;

@Singleton
public class AnnouncerImpl implements Announcer {

    @InjectByType
    private Advisor advisor;

    @PostConstruct
    public void init() {
        System.out.println(advisor.getClass());
    }

    @Override
    public void announce(String s) {
        advisor.advise();
        System.out.println(s);
    }
}
