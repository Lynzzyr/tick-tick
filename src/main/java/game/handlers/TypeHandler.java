package game.handlers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/** Global handler managing typeface files and their input streams. */
public class TypeHandler {
    // FONTS

    private static ByteArrayInputStream ffRegular;

    private static ByteArrayInputStream ffBold;

    private static ByteArrayInputStream ffIcons;

    // constructor
    static {
        try (InputStream f = TypeHandler.class.getResourceAsStream("/type/Neue Haas Grotesk-Roman.otf")) {
            ffRegular = new ByteArrayInputStream(f.readAllBytes());
        } catch (Exception ignored) {}
        try (InputStream f = TypeHandler.class.getResourceAsStream("/type/Neue Haas Grotesk-Bold.otf")) {
            ffBold = new ByteArrayInputStream(f.readAllBytes());
        } catch (Exception ignored) {}
        try (InputStream f = TypeHandler.class.getResourceAsStream("/type/SF-Pro-Display-Regular.otf")) {
            ffIcons = new ByteArrayInputStream(f.readAllBytes());
        } catch (Exception ignored) {}
    }

    // GETTERS

    public static ByteArrayInputStream getFFRegular() {
        ffRegular.reset();
        return ffRegular;
    }

    public static ByteArrayInputStream getFFBold() {
        ffBold.reset();
        return ffBold;
    }

    public static ByteArrayInputStream getFFIcons() {
        ffIcons.reset();
        return ffIcons;
    }
}
