/////////////////////////////////////////////
// Matheus Gabriel Viana Araujo //
// Enzo Carvalho Pagliarini     //
/////////////////////////////////////////////

/*
 * Armazena o resultado do comando load, separando o
 * nome do arquivo com a mensagem retornada da função
 */
public class LoadResult {
    public String fileName;
    public String error;

    public LoadResult(String error, String fileName) {
        this.fileName = fileName;
        this.error = error;
    }

}
