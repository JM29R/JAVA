package JMR.ModularTickets.auth.domain;

public interface PasswordHasher {

    String encode(String password);

    boolean matches(String rawPassword, String encodedPassword);
}
