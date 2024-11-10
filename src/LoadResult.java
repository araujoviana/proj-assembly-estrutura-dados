/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo - 10420444 //
// Enzo Carvalho Pagliarini - 10425707     //
/////////////////////////////////////////////

public class LoadResult {
    public String fileName;
    public String error;

    public LoadResult(String error, String fileName) {
        this.fileName = fileName;
        this.error = error;
    }

    public boolean hasError() {
        return error != null;
    }
}
