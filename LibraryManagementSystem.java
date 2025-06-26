import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagementSystem {

    // ---------- Book Class ----------
    static class Book {
        private String title;
        private String author;
        private boolean isIssued;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
            this.isIssued = false;
        }

        public String getTitle() {
            return title;
        }

        public boolean isIssued() {
            return isIssued;
        }

        public void issueBook() {
            isIssued = true;
        }

        public void returnBook() {
            isIssued = false;
        }

        public String toString() {
            return title + " by " + author + (isIssued ? " (Issued)" : " (Available)");
        }
    }

    // ---------- User Class ----------
    static class User {
        private String name;
        private ArrayList<Book> issuedBooks;

        public User(String name) {
            this.name = name;
            this.issuedBooks = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void borrowBook(Book book) {
            issuedBooks.add(book);
        }

        public void returnBook(Book book) {
            issuedBooks.remove(book);
        }

        public void showIssuedBooks() {
            System.out.println("Books issued to " + name + ":");
            for (Book book : issuedBooks) {
                System.out.println(" - " + book.getTitle());
            }
        }
    }

    // ---------- Library Class ----------
    static class Library {
        private ArrayList<Book> books;
        private ArrayList<User> users;

        public Library() {
            books = new ArrayList<>();
            users = new ArrayList<>();
        }

        public void addBook(Book book) {
            books.add(book);
        }

        public void addUser(User user) {
            users.add(user);
        }

        public void issueBook(String title, String userName) {
            Book book = findBook(title);
            User user = findUser(userName);

            if (book == null) {
                System.out.println("Book not found.");
                return;
            }
            if (user == null) {
                System.out.println("User not found.");
                return;
            }
            if (book.isIssued()) {
                System.out.println("Book is already issued.");
                return;
            }

            book.issueBook();
            user.borrowBook(book);
            System.out.println(userName + " issued \"" + title + "\"");
        }

        public void returnBook(String title, String userName) {
            Book book = findBook(title);
            User user = findUser(userName);

            if (book == null || user == null) {
                System.out.println("Return failed. Book or user not found.");
                return;
            }

            book.returnBook();
            user.returnBook(book);
            System.out.println(userName + " returned \"" + title + "\"");
        }

        private Book findBook(String title) {
            for (Book b : books) {
                if (b.getTitle().equalsIgnoreCase(title)) return b;
            }
            return null;
        }

        private User findUser(String name) {
            for (User u : users) {
                if (u.getName().equalsIgnoreCase(name)) return u;
            }
            return null;
        }

        public void showAllBooks() {
            System.out.println("Library Books:");
            for (Book b : books) {
                System.out.println(" - " + b);
            }
        }
    }

    // ---------- Main Method ----------
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        // Sample Data
        library.addBook(new Book("Java Basics", "James Gosling"));
        library.addBook(new Book("Clean Code", "Robert C. Martin"));
        library.addUser(new User("Alice"));
        library.addUser(new User("Bob"));

        int choice;
        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Show All Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    library.showAllBooks();
                    break;
                case 2:
                    System.out.print("Enter book title: ");
                    String issueTitle = scanner.nextLine();
                    System.out.print("Enter user name: ");
                    String issueUser = scanner.nextLine();
                    library.issueBook(issueTitle, issueUser);
                    break;
                case 3:
                    System.out.print("Enter book title: ");
                    String returnTitle = scanner.nextLine();
                    System.out.print("Enter user name: ");
                    String returnUser = scanner.nextLine();
                    library.returnBook(returnTitle, returnUser);
                    break;
                case 4:
                    System.out.println("Exiting Library System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 4);

        scanner.close();
    }
}