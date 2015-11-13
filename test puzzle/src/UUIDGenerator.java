import java.util.UUID;

// sample UUID: 251baa74-dc82-4e46-ae58-d7479c06eff5
public class UUIDGenerator {
    // method generates a new id globally on each call
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
