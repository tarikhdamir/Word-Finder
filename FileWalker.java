
package wordmap;
import java.io.File;
import java.io.FileReader; 
import java.io.BufferedReader; 
import java.io.FileNotFoundException;
import java.io.IOException;


public abstract class FileWalker
{

    public static boolean seemsOK( File f )
    {
        return f. exists( ) && f. canRead( ); 
    }

    public static boolean seemsOK( String filename )
    { 
        return seemsOK( new File( filename ));
    }

    public static Occurrences getOccurrences( String filename ) 
    throws FileNotFoundException, IOException
    {
        Occurrences occ = new Occurrences( ); 
        addOccurrences( new File( filename ), occ );
        return occ; 
    } 


    public static void addOccurrences( File file, Occurrences occ ) throws FileNotFoundException, IOException{
        if(file.isFile()){
            addOccurrences(new BufferedReader(new FileReader(file)), file.toString(), occ);
        }
        else{
            File[] contents = file.listFiles();
            for(File files : contents){
                addOccurrences(files, occ);
            }
        }
    }

    public static void addOccurrences( BufferedReader source, String sourcename, Occurrences occ ) throws IOException {
        int ch;
        String s = new String();
        int line = 1, column = 1;
        do{
            ch = source.read();
            if (!Syntax.isInWord((char)ch)) {
                if (s.length() > 0) {
                    occ.put(s.toLowerCase(), sourcename, new Position(line, column - s.length()));
                    s = "";
                }
            }
            else {
                s = s + (char) ch;
            }
            if (Syntax.isNewLine((char)ch)) {
                line++;
                column = 1;
                continue;
            }
            column++;
        } while(ch != -1);
    }

}


