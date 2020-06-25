package com.example.corona2;

@Singleton
public class AnnouncerImpl implements Announcer {

    @InjectByType
    private Advisor advisor;

    public AnnouncerImpl() {
        System.out.println(advisor.getClass());
    }

    @Override
    public void announce(String s) {
        advisor.advise();
        System.out.println(s);
    }
}
