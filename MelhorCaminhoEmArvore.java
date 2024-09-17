import java.io.*;
import java.util.*;

public class MelhorCaminhoEmArvore {

    // Definir a classe Point para armazenar coordenadas
    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    // Classe para armazenar o grafo
    static class Grafo {
        Map<Point, List<Point>> adjacencias;

        public Grafo() {
            adjacencias = new HashMap<>();
        }

        public void adicionarAresta(Point de, Point para) {
            adjacencias.computeIfAbsent(de, k -> new ArrayList<>()).add(para);
        }

        public List<Point> getVizinhos(Point no) {
            return adjacencias.getOrDefault(no, new ArrayList<>());
        }
    }

    // Função para carregar a árvore de um arquivo txt
    public static char[][] carregarArvoreDeTxt(String nomeArquivo) throws IOException {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            br.readLine(); // Ignora a primeira linha (dimensões)
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        }

        // Converter a lista de strings para uma matriz de char
        char[][] arvore = new char[linhas.size()][];
        for (int i = 0; i < linhas.size(); i++) {
            arvore[i] = linhas.get(i).toCharArray();
        }
        return arvore;
    }

    // Função para transformar a árvore em um grafo
    public static Grafo arvoreParaGrafo(char[][] arvore) {
        Grafo grafo = new Grafo();
        int linhas = arvore.length;
        int colunas = arvore[0].length;

        for (int x = 0; x < linhas; x++) {
            for (int y = 0; y < colunas; y++) {
                if (arvore[x][y] != ' ') { // Ignorar espaços vazios
                    Point atual = new Point(x, y);

                    // Verificar as conexões
                    if (arvore[x][y] == '\\' && x + 1 < linhas && y - 1 >= 0) {
                        grafo.adicionarAresta(atual, new Point(x + 1, y - 1));
                    } else if (arvore[x][y] == '/' && x + 1 < linhas && y + 1 < colunas) {
                        grafo.adicionarAresta(atual, new Point(x + 1, y + 1));
                    } else {
                        if (x + 1 < linhas) {
                            grafo.adicionarAresta(atual, new Point(x + 1, y));
                        }
                    }
                }
            }
        }
        return grafo;
    }

    // Função para encontrar o melhor caminho com DFS
    public static int[] dfs(Point noAtual, Grafo grafo, char[][] arvore, int pontuacaoAtual, List<Point> caminhoAtual) {
        int linhas = arvore.length;
        int x = noAtual.x;
        int y = noAtual.y;

        // Somar pontuação se o caractere for um dígito
        if (Character.isDigit(arvore[x][y])) {
            pontuacaoAtual += Character.getNumericValue(arvore[x][y]);
        }

        // Se chegamos na última linha ou em uma folha '#'
        if (x == linhas - 1 || arvore[x][y] == '#') {
            return new int[]{pontuacaoAtual, caminhoAtual.size()};
        }

        // Inicializar as variáveis de melhor pontuacao e caminho
        int melhorPontuacao = pontuacaoAtual;
        List<Point> melhorCaminho = new ArrayList<>(caminhoAtual);

        // Explorar os vizinhos
        for (Point vizinho : grafo.getVizinhos(noAtual)) {
            caminhoAtual.add(vizinho);
            int[] resultado = dfs(vizinho, grafo, arvore, pontuacaoAtual, caminhoAtual);
            caminhoAtual.remove(caminhoAtual.size() - 1);

            // Atualizar se encontrarmos uma pontuação melhor
            if (resultado[0] > melhorPontuacao) {
                melhorPontuacao = resultado[0];
                melhorCaminho = new ArrayList<>(caminhoAtual);
            }
        }

        return new int[]{melhorPontuacao, melhorCaminho.size()};
    }

    public static int encontrarMelhorCaminho(Grafo grafo, char[][] arvore) {
        int melhorPontuacao = 0;
        int linhas = arvore.length;
        int colunas = arvore[0].length;

        // Explorar todos os nós da primeira linha
        for (int y = 0; y < colunas; y++) {
            if (arvore[0][y] != ' ') { // Considerar somente nós válidos
                List<Point> caminho = new ArrayList<>();
                caminho.add(new Point(0, y));
                int[] resultado = dfs(new Point(0, y), grafo, arvore, 0, caminho);
                melhorPontuacao = Math.max(melhorPontuacao, resultado[0]);
            }
        }

        return melhorPontuacao;
    }

    public static void main(String[] args) {
        try {
            // Carregar a árvore do arquivo
            String nomeArquivo = "C:/Users/xbox3/Documents/Facul/ALEST2/Trab_Alest_II/casos-30/casos-30/casoc30.txt";
            char[][] arvore = carregarArvoreDeTxt(nomeArquivo);

            // Converter a árvore em grafo
            Grafo grafo = arvoreParaGrafo(arvore);

            // Encontrar o melhor caminho
            int melhorPontuacao = encontrarMelhorCaminho(grafo, arvore);

            // Exibir o resultado
            System.out.println("Melhor Pontuação: " + melhorPontuacao);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
