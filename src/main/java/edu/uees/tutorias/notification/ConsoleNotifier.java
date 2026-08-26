package edu.uees.tutorias.notification;

import edu.uees.tutorias.domain.User;

public class ConsoleNotifier implements Notifier {

    @Override
    public void notify(User user, String message) {
        System.out.println(
                "Notification for " + user.getName()
                        + " <" + user.getEmail() + ">: "
                        + message
        );
    }
}
