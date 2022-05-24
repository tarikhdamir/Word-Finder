package wordmap;

import wordmap.FileWalker;
import wordmap.Position;
import wordmap.Occurrences;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        wordmap.Occurrences occ = new wordmap.Occurrences( );
        occ = FileWalker.getOccurrences("test_dir");
        System.out.println(occ);
    }
}
