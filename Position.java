package wordmap;


public class Position implements Comparable<Position> 
{
    final private int line;
    final private int column;

    public Position( int line, int column ) {
        this.line = line;
        this.column = column;
    }

    public int compareTo(Position pos) {
        if(line < pos.line || line == pos.line && column < pos.column)
            return -1;
        if(line == pos.line && column == pos.column)
            return 0;
        return 1;
    }

    public Position clone( ) 
    {
        return new Position( line, column );
    }

    public String toString() {
        return "at line " + line + ", column " + column;
    }

}


