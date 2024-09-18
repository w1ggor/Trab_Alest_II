import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Leitor{
    public static void main(String[] args) {
        String caminhoArquivo = "casoc300.txt";
        int inicialI = 0;
        int inicialJ = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            String[] dados = reader.readLine().split(" ");
            int tamI = Integer.parseInt(dados[0]);
            int tamJ = Integer.parseInt(dados[1]);
            char[][] mapa = new char[tamI][tamJ];
            for (int i = 0; i < tamI; i++) {
                linha = reader.readLine();
                for (int j = 0; j < tamJ; j++) {
                    mapa[i][j] = linha.charAt(j);
                    if(linha.charAt(j) != ' ' && i == tamI - 1){
                        inicialI = i;
                        inicialJ = j;
                    }
                }
            }
            Percorrer percorrer = new Percorrer(mapa, inicialI, inicialJ);
            System.out.println(percorrer.percorre());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
