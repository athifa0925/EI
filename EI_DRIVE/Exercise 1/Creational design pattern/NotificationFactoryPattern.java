import java.util.Scanner;

interface Notification {
    void send(String message);
}

class SMSNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}

class EmailNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}

class PushNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending Push Notification: " + message);
    }
}

class InAppNotification implements Notification {
    @Override
    public void send(String message) {
        System.out.println("Sending In-App Message: " + message);
    }
}

abstract class NotificationFactory {
    public abstract Notification createNotification();

    public void sendNotification(String message) {
        Notification notification = createNotification();
        notification.send(message);
    }
}

class SMSNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification() {
        return new SMSNotification();
    }
}

class EmailNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification() {
        return new EmailNotification();
    }
}

class PushNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification() {
        return new PushNotification();
    }
}

class InAppNotificationFactory extends NotificationFactory {
    @Override
    public Notification createNotification() {
        return new InAppNotification();
    }
}

public class NotificationFactoryPattern {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter preferred notification channel (sms, email, push, inapp):");
        String channel = scanner.nextLine().toLowerCase();
        System.out.println("Enter notification message:");
        String message = scanner.nextLine();

        NotificationFactory factory;

        switch (channel) {
            case "sms":
                factory = new SMSNotificationFactory();
                break;
            case "email":
                factory = new EmailNotificationFactory();
                break;
            case "push":
                factory = new PushNotificationFactory();
                break;
            case "inapp":
                factory = new InAppNotificationFactory();
                break;
            default:
                System.out.println("Invalid notification channel. Defaulting to In-App.");
                factory = new InAppNotificationFactory();
        }

        factory.sendNotification(message);

        scanner.close();
    }
}