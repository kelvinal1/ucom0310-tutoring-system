package edu.uees.tutorias.notification;

import edu.uees.tutorias.domain.User;

public interface Notifier {

    void notify(User user, String message);
}
