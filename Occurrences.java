
package wordmap;

import com.sun.source.tree.Tree;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import java.util.TreeMap;
import java.util.TreeSet;

public class Occurrences  
{
    private Map< String, Map< String, Set< Position >>> occ; 
        // Maps words -> filename -> sets of their Positions in the file.
 
    public Occurrences( ) 
    {
        occ = new TreeMap<> ( ); 
    }

    public void put( String word, String filename, Position pos ) throws IOException {
        if(!occ.containsKey(word))
            occ.put(word, new TreeMap<>());
        if(!occ.get(word).containsKey(filename)){
            File file = new File(filename);
            file.createNewFile();
            occ.get(word).put(filename, new TreeSet<>());
        }
        occ.get(word).get(filename).add(pos);
    }


    public int countWords( ) {
        return occ.size();
    }

    public int count( ){
        int sz = 0;
        for (Map.Entry<String, Map<String, Set<Position>>> pair : occ.entrySet()){
            sz += count(pair.getKey());
        }
        return sz;
    }

    public int count( String word ) {
        if(occ.containsKey(word)){
            int sz = 0;
            for(Map.Entry<String, Set<Position>> filename: occ.get(word).entrySet()){
                sz += count(word, filename.getKey());
            }
            return sz;
        }
        return 0;
    }

    public int count( String word, String file ){
        if(FileWalker.seemsOK(file)){
            return occ.get(word).get(file).size();
        }
        return 0;
    }
 
    public String toString( ){
        StringBuilder s = new StringBuilder();
        s.append("\n");

        for (String w : occ.keySet())
        {
            s
                    .append("word \"")
                    .append(w)
                    .append("\" occurs ")
                    .append(count(w))
                    .append(" times:\n");

            for (Map.Entry<String, Set<Position>> f : occ.get(w).entrySet())
            {
                s
                        .append("    in file \"")
                        .append(f.getKey().replace("\\", "/"))
                        .append("\":\n");

                for(Position p : f.getValue()){
                    s
                            .append("        ")
                            .append(p.toString())
                            .append("\n");
                }
            }
        }

        s
                .append("\noccurrences     ")
                .append(count())
                .append("\ndistinct words     ")
                .append(countWords());
        return s.toString();
    }
}


