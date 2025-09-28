
interface Document {
    void displayContent(String user);
}

class RealDocument implements Document {
    private String content;

    public RealDocument(String content) {
        this.content = content;
        System.out.println("RealDocument created: Loading sensitive content...");
    }

    @Override
    public void displayContent(String user) {
        System.out.println("Displaying content to " + user + ": " + content);
    }
}

class DocumentProxy implements Document {
    private RealDocument realDocument;
    private String content;
    private String[] authorizedUsers;

    public DocumentProxy(String content, String[] authorizedUsers) {
        this.content = content;
        this.authorizedUsers = authorizedUsers;
    }

    private boolean isUserAuthorized(String user) {
        for (String authUser : authorizedUsers) {
            if (authUser.equals(user)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void displayContent(String user) {
        if (isUserAuthorized(user)) {
            if (realDocument == null) {
                realDocument = new RealDocument(content);
            }
            realDocument.displayContent(user);
        } else {
            System.out.println("Access denied for user: " + user);
        }
    }
}
public class ProxyPatternExample {
    public static void main(String[] args) {
       
        String[] authorizedUsers = {"admin", "manager"};

        Document document = new DocumentProxy("Sensitive financial report", authorizedUsers);

        System.out.println("Attempting access by admin...");
        document.displayContent("admin");

        System.out.println("\nAttempting access by guest...");
        document.displayContent("guest");

        System.out.println("\nAttempting access by manager...");
        document.displayContent("manager");
    }
}