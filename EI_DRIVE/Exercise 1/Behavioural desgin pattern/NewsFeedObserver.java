import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

interface NewsObserver {
    void update(String newsUpdate);
}

class Subscriber implements NewsObserver {
    private String name;

    public Subscriber(String name) {
        this.name = name;
    }

    @Override
    public void update(String newsUpdate) {
        System.out.println(name + " received news update: " + newsUpdate);
    }
}

class NewsPublisher {
    private List<NewsObserver> subscribers = new ArrayList<>();
    private String latestNews;

    public void addSubscriber(NewsObserver subscriber) {
        subscribers.add(subscriber);
    }

    public void removeSubscriber(NewsObserver subscriber) {
        subscribers.remove(subscriber);
    }

    public void publishNews(String newsUpdate) {
        this.latestNews = newsUpdate;
        notifySubscribers();
    }

    private void notifySubscribers() {
        for (NewsObserver subscriber : subscribers) {
            subscriber.update(latestNews);
        }
    }
}

public class NewsFeedObserver {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        NewsPublisher publisher = new NewsPublisher();

        System.out.println("Enter number of subscribers to register:");
        int numSubscribers = scanner.nextInt();
        scanner.nextLine(); 

        for (int i = 0; i < numSubscribers; i++) {
            System.out.println("Enter subscriber name:");
            String subscriberName = scanner.nextLine();
            publisher.addSubscriber(new Subscriber(subscriberName));
        }

        System.out.println("Enter news update (or 'quit' to exit):");
        while (true) {
            String newsUpdate = scanner.nextLine();
            if (newsUpdate.equalsIgnoreCase("quit")) {
                break;
            }
            publisher.publishNews(newsUpdate);
        }

        scanner.close();
    }
}