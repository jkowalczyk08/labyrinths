package labyrinths.model;

public class FileChecker {
    public static void checkSize(Integer h, Integer w) throws FileFormatException {
        if(h == null || w == null) {
            throw new FileFormatException();
        }
        if(h < 10 || h > 20) {
            throw new FileFormatException();
        }
        if(w < 10 || w > 40) {
            throw new FileFormatException();
        }
    }
    public static void checkStringLength(Integer h, Integer w, String s) throws FileFormatException {
        if(s == null) {
            throw new FileFormatException();
        }
        if(s.length() != h*w + 2*(h-1)) {
            throw new FileFormatException();
        }
    }
}
