package net.surnex.ch.truayt.teamspeakbot;

public class Launcher {

    public static void main(String[] args) {
        System.out.println("Try to Start TeamSpeak Bot.");
        System.out.println("If there any Error, please write me on Discord; TruaYT#0507");
        TeamSpeakBot teamSpeakBot = new TeamSpeakBot();
        teamSpeakBot.start();
        Runtime.getRuntime().addShutdownHook(new Thread(teamSpeakBot::stop));
    }
}
