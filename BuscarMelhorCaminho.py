def carregar_arvore_de_txt(nome_arquivo):
    with open(nome_arquivo, 'r') as arquivo:
        linhas = arquivo.readlines()
        
    arvore = []
    for linha in linhas[1:]:  # Pulando a primeira linha que contém as dimensões
        arvore.append(list(linha.strip()))  # Converte cada linha em uma lista de caracteres
    
    return arvore

def buscar_caminhos(arvore, x, y, pontuacao_atual):
    # Verifica se estamos fora dos limites da árvore
    if x >= len(arvore) or y < 0 or y >= len(arvore[0]):
        return pontuacao_atual

    # Se encontrarmos uma folha '#', retornamos a pontuação
    if arvore[x][y] == '#':
        return pontuacao_atual

    # Se encontrarmos um número, somamos à pontuação atual
    if arvore[x][y].isdigit():
        pontuacao_atual += int(arvore[x][y])

    # Definindo as próximas direções (cima, esquerda, direita)
    if arvore[x][y] == '\\':
        return buscar_caminhos(arvore, x + 1, y - 1, pontuacao_atual)
    elif arvore[x][y] == '/':
        return buscar_caminhos(arvore, x + 1, y + 1, pontuacao_atual)
    else:  # Caminho reto
        return buscar_caminhos(arvore, x + 1, y, pontuacao_atual)

def encontrar_melhor_caminho(arvore):
    melhor_pontuacao = 0
    for y in range(len(arvore[0])):  # Percorre todas as colunas da primeira linha
        melhor_pontuacao = max(melhor_pontuacao, buscar_caminhos(arvore, 0, y, 0))
    return melhor_pontuacao

# Carregar a árvore de um arquivo txt
nome_arquivo = r'C:\Users\xbox3\Documents\Facul\ALEST2\Trab_Alest_II\casos-30\casos-30\casoc30.txt'
arvore = carregar_arvore_de_txt(nome_arquivo)

# Encontrar o melhor caminho
melhor_caminho = encontrar_melhor_caminho(arvore)
print("A melhor pontuação possível é:", melhor_caminho)
