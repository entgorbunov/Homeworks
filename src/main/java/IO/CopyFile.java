package IO;


/* A version of CopyFile that uses try-with-resources.
   It demonstrates two resources (in this case files) being
   managed by a single try statement.
*/
import java.io.*;
class CopyFile {
    public static void main(String[] args) throws IOException
    {
        int i;
        // First, confirm that both files have been specified.
        if(args.length != 2) {
            System.out.println("Usage: CopyFile from to");
            return; }
        // Open and manage two files via the try statement.
        try (var fin = new FileInputStream(args[0]);
             var fout = new FileOutputStream(args[1]))
        {
            do {
                i = fin.read();
                if(i != -1) fout.write(i);
            } while(i != -1);
        } catch(IOException e) {
            System.out.println("I/O Error: " + e);
        } }
}
