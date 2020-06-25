package com.example.corona2;

public class CoronaDisinfectant {

    private Announcer announcer = new AnnouncerImpl();
    private Policeman policeman = new PolicemanImpl();

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
