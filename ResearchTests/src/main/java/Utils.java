import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * A set of basic utilities to make our lives easier
 */
public class Utils {

    public byte[] convertImageToByteArray(File file) {
        try {
            return Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}
