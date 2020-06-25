package com.example.corona2;

public class CoronaDisinfectant {

    private Announcer announcer = ObjectFactory.getOurInstance().createObject(Announcer.class);
    private Policeman policeman = ObjectFactory.getOurInstance().createObject(Policeman.class);

    public void start () {
        announcer.announce("starting disinfection, all out!");
        policeman.makePeopleLeave();

        disinfect();
        announcer.announce("risk it, get back!");

    }

    private void disinfect() {
        System.out.println("Prayer is being read: CORONA GO AWAY, AMEN!");
    }
}
