package com.example.corona2;

@Deprecated
public class CoronaDisinfectant {

    @InjectByType
    private Announcer announcer;
    @InjectByType
    private Policeman policeman;

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
