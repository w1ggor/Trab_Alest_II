import java.util.Stack;

public class Percorrer {
    private char[][] mapa;
    private int i;
    private int j;
    private Stack<String> stack = new Stack<String>();
    private int direcao;
    private int soma;
    private int somaMaior;
    private char letra;

    public Percorrer(char[][] mapa, int inicialI, int inicialJ){
        this.mapa = mapa;
        this.i = inicialI;
        this.j = inicialJ;
        this.soma = 0;
        if(mapa[i][j] == '|'){
            direcao = 2;
        } else if (mapa[i][j] == '/'){
            direcao = 3;
        } else if (mapa[i][j] == '\\'){
            direcao = 1;
        }
    }

    public int percorre(){
        while (true) {
            if(Character.isDigit(mapa[i][j])){
                soma += Character.getNumericValue(mapa[i][j]);
                
            }
            switch (mapa[i][j]) {
                case 'V':
                addPilha("V", ""+i, ""+j, "" +soma);
                direcao = 1;
                    break;
                case 'W':
                addPilha("W", ""+i, ""+j, "" +soma);
                direcao = 1;
                    break;

                case '#':
                if(soma > somaMaior){
                    somaMaior = soma;
                }
                if(stack.empty()){
                    return somaMaior;
                }
                soma = Integer.parseInt(stack.pop());
                j = Integer.parseInt(stack.pop());
                i = Integer.parseInt(stack.pop());
                letra = stack.pop().charAt(0);
                switch(letra){
                    case 'W':
                        addPilha("V", ""+i, ""+j, "" +soma);
                        direcao = 2;
                        break;
                    case 'V':
                        direcao = 3;
                        break;
                    default:
                        break;
                }
                    break;
                default:
                    break;
            }
        
            switch(direcao){
                case 1: j--; break;
                case 3: j++; break;
                default:
                    break;
            }
            i--;
        }
    }

    public void addPilha(String letra, String i, String j, String soma){
        stack.push(letra);
        stack.push(i);
        stack.push(j);
        stack.push(soma);
    }
}
