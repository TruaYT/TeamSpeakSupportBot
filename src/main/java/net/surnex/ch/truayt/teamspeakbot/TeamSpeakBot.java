package net.surnex.ch.truayt.teamspeakbot;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.event.*;
import com.github.theholywaffle.teamspeak3.api.wrapper.Client;

public class TeamSpeakBot {

    public TS3Config config = new TS3Config();
    public TS3Query query;
    public TS3Api api;

    public void start() {
        config.setHost(TeamSpeakBotSettings.HOST_IP);
        System.out.println("TeamSpeak Bot connect to " + TeamSpeakBotSettings.HOST_IP);
        config.setFloodRate(TS3Query.FloodRate.UNLIMITED);
        query = new TS3Query(config);
        query.connect();

        api = query.getApi();
        api.login(TeamSpeakBotSettings.QUERY_LOGIN_NAME, TeamSpeakBotSettings.QUERY_LOGIN_PASSWORD);
        api.selectVirtualServerByPort(TeamSpeakBotSettings.PORT);
        api.setNickname(TeamSpeakBotSettings.BOT_NAME);
        System.out.println("TeamSpeak API is now Online..");
        System.out.println("Try to Load Event");
        loadEvent();
        System.out.println("The System is now Online!");
    }

    public void stop() {
        api.logout();
    }

    public void loadEvent() {
        getApi().registerAllEvents();
        getApi().addTS3Listeners(new TS3Listener() {
            public void onTextMessage(TextMessageEvent event) {
            }

            public void onServerEdit(ServerEditedEvent event) {
            }

            public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent event) {
            }

            public void onClientMoved(ClientMovedEvent event) {
                Client client = getApi().getClientInfo(event.getClientId());
                if (event.getTargetChannelId() == TeamSpeakBotSettings.SUPPORT_WAITING_CHANNEL_ID) {
                    if (true) {
                        int size = 0;
                        for (Client clients : getApi().getClients()) {
                            if (clients.isInServerGroup(TeamSpeakBotSettings.SUPPORT_NOTIFY_ROLE_ID)) {
                                size++;
                                getApi().sendPrivateMessage(clients.getId(), "Der Spieler [b]" + client.getNickname() + "[/b] braucht Support!");
                            }
                        }
                        if (size == 0) {
                            getApi().sendPrivateMessage(client.getId(), "Kein Teammitglied ist online!");
                            getApi().sendPrivateMessage(client.getId(), "Du kannst warten oder auf unseren Discord Serer Joinen um dort die Frage zu klären. Dort kannst du dann ein Ticket erstellen. Link zum Discord: [URL]http://dc.Surnex.net[/URL]");
                        } else {
                            getApi().sendPrivateMessage(client.getId(), "Es wurden [b]" + size-- + "[/b] Teammitglieder benachrichtigt!");
                        }
                    } else {
                        getApi().sendPrivateMessage(client.getId(), "Der TeamSpeak Support ist geschlossen! Wenn du fragen hast, kannst du gerne auf unserem Discord Joinen und dir dort ein Ticket erstellen!");
                    }
                }

            }

            public void onClientLeave(ClientLeaveEvent event) {

            }


            public void onClientJoin(ClientJoinEvent event) {
            }

            public void onChannelPasswordChanged(ChannelPasswordChangedEvent event) {
            }

            public void onChannelMoved(ChannelMovedEvent event) {
            }

            public void onChannelEdit(ChannelEditedEvent event) {
            }

            public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent event) {
            }

            public void onChannelDeleted(ChannelDeletedEvent event) {
            }

            public void onChannelCreate(ChannelCreateEvent event) {
            }
        });
    }

    public TS3Config getConfig() {
        return config;
    }

    public TS3Query getQuery() {
        return query;
    }

    public TS3Api getApi() {
        return api;
    }
}
