# Dungeons and Capybaras

**Autores**:
- Henrique Eduardo dos Santos de Souza @henrique_eduardo_souza
- Vinicius Batista da Silva @viniciusbsilva
- Vitor Sasaki Venzel @vSasakiv

**Dependências**:
- Java SDK openjdk 11.0.18
- OpenJDK Runtime Environment
- OpenJDK 64-Bit Server VM

# O que é este projeto?

**Dungeons and capybaras** é um jogo no estilo RogueLike escrito na linguagem Java, com temática fortemente inspirada na **Universidade de São Paulo**, especialmente na **Escola Politécnica** e seus arredores. O jogo mescla as mecânicas de RPG clássicos com as de combate e exploração em **dungeons**, trazendo uma adaptação ao estilo escolhido.

# Como rodar este projeto?

Comece clonando o repositório para sua máquina local:
```
git clone git@gitlab.uspdigital.usp.br:henrique_eduardo_souza/dungeons-and-capybaras.git
```

Em seguida, abra o diretório do projeto
```
cd dungeons-n-capybaras
```

Compile o jogo, executando o Game.java:
```
javac Game.java
```

Depois, exeute o jogo:
```
java Game
```

> certifique-se ter ter em sua máquina uma versão igual ou superior ao java 17.
{: .prompt-warning }


# Relatório entrega 2

## Requisitos 
1. O mapa do jogo deve ser fortemente influenciado pelo mapa da USP. Não precisa ser uma réplica completamente fiel da universidade, apenas se inspire
fortemente na universidade.

2. Utilizando o teclado, o jogador poderá se movimentar nas várias dirções.
Ele deve ser capaz de andar pela grama, rua ou calçada, andar de bicicleta e nadar no Rio Pinheiros. Opcionalmente, pode ser implementado também o controle via joystick.

## Adaptações

Conforme proposto inicialmente, o jogo desenvolvido nesse projeto não seguira a implementação de um jogo inspirado em pokemon, mas sim em rogue-like. Na entrega atual as diferenças entre as duas propostas não são tão explícitas, porém adaptações foram feitas visando implementações futuras. Dentre elas estão:

* Pelo jogo se basear em explorações de dungeons, os mapas dele são dividos entre os "fortemente baseados na USP" e os de "dungeon". Os baseados na USP, especialmente na Escola Politécnica, são mapas em que o player pode se deslocar livremente, com finalidade de atingir outros lugares. Os mapas de dungeons não se baseam na universidade (até por questões éticas, pois há noções de combate violento) e neles o player equipa armas com finalidade de derrotar monstros.
* Não faz sentido para o contexto do jogo as atividades de interação como "andar de bicicleta" ou "nadar na raia". Como alternativa a isso, a mesma noção de "mudança de comportamento" do jogador foi implementada de duas formas diferentes: mudança de estado de batalha e mudança mudança de modo de exploração. 

Com essas adaptações, acreditamos que os requisitos são atendidos com implementações análogas às propostas. 

## Mapas
No estado atual do jogo, existem 3 mapas acessíveis: estacionamento principal da Poli , estacionamento do Biênio, e um mapa de zona aberta. Sendo os dois primeiros fortemente inspirados na USP e, o último, um exemplo de mapa de dungeon. Todos os mapas podem ser explorados se movimentando pelas teclas W, A, S e D do teclado. 

1. Estacionamento principal 
    
    Área inicial do player quando o jogo é aberto, inspirada no estacionamento localizado na parte central da Escola Politécnica. O player pode caminhar entre as ruas e calçadas e verificar toda paisagem. 
    
    Existem duas áreas que levam para outros mapas:

    * Próximo ao canto superior direito, em uma ponte que leva para uma parte de fora do mapa. O jogador que atravessar essa área será levado para o mapa de zona aberta.
    * Próximo ao canto inferior esquerdo, na rua que leva para uma parte de fora do mapa. O jagador que atravessar essa área será levado para o estacionamento do Biênio.

2. Estacionamento do Biênio  
    Área inspirada na área de estacionamento que fica entre o Biênio e o prédio da Engenharia Civil. O pode pode andar pelas ruas, calçadas, grama e visitar o vão do Biênio. 

    Existe uma área que leva para outro mapa:
    
    * Na parte superior, ao lado esquerda da área verde e direito do prédio vermelho (Eng. Civil), há uma rua que leva para uma parte fora do mapa. Se o player atravessar essa área no topo, será redirecionado para estacionamento principal. 

3. Mapa de zona aberta

    Área de dungeon, um mapa de um exterior com caminhos de terra, elementos da natureza, um rio e monstros (na versão atual, pontinhos vermelhos que atiram flechas). Nesse mapa o player pode caminhar e atirar com uma arma que ele equipa. Para mirar, usa-se o mouse cliando na direção onde se pretende que o projétil vá (note que o arco no player segue essa direção). 
    
    Existe uma região que leva para outro mapa:
    *  Na parte inferior próxima ao centro, onde termina/começa o caminho de terra, existe uma região que, se atravessada pelo player, o leva de volta para o estacionamento principal. É a saída da "dungeon". 

## Comportamentos do player

Atualmente, há dois comportamentos, além do padrão, para o player que podem ser acessados com diferentes interações em diferentes lugares. Um deles está relacionado ao combate e outro ao modo de exploração. 

1. Padrão: o player inicialmente está em uma mapa de exploração, fortemente baseado na USP. Ele pode apenas andar e explorar o mapa, indo para lugares diferentes acessando outras áreas. Pode alternar para o modo ninja apertando a tecla "n" do teclado.

2. Ninja: em um mapa de exploração, assume forma de um ninja que anda rapidamente e pode passar de forma veloz pelos lugares do mapa. Pode alternar para o modo padrão apertando a tecla "n" do teclado.

3. Combate: ao entrar em um mapa de dungeon (mapa de zona aberta), o player equipa uma arma e agora pode atirar com ela. Agora ele também possui barras de vida (vermelha) e armadura (cinza), que podem diminuir caso seja atingido por projéteis de monstros. Sempre que estiver em uma dungeon, o player permanesse nesse modo sem poder mudar para o padrão ou para o ninja. 

## Combate

A implementação inicial do combate foi feita, com disponibilidade para armas de único disparo, de vários disparos e corpo-a-corpo. Na versão atual, as duas primeiras possuem imagens de "arco e flecha" associadas a elas. Para testar outras além da padrão, verifique os atributos de cada arma nas classes "AutomaticWeapon", "MultiShotWeapon" e "MeleeWeapon", presentes no package game_entity.weapons, e mude a arma do dungeonPlayer ou do enemyTemplate no construtor da classe "DungeonState", no package gameloop.game_states.

## Observações finais

Nessa entrega, o jogador prioritariamente apenas anda pelos mapas. A implementação do combate está ainda nos estágios iniciais, sendo apresentada atualmente apenas uma noção do sistema que irá vir futuramente. 

Os mapas possuem "bordas de mundo" visíveis, porém não acessíveis, ao jogador. Vale destacar que em entregas futuras os mapas serão extendidos/incrementados e o sistema de câmera do mundo aprimorado, para evitar/amenizar esse problema. 

Resquisitos de outras entregas, como implementação do padrão Strategy ou do padrão Fábrica Abstrata, foram atingidos de diferentes maneiras. Não iremos destacá-las agora, porém atestamos que algumas coisas comentadas nessa entrega serão novamente expostas nas próximas. 

Com os mapas de exploração, dos estacionamentos da Escola Politecnica, o projeto cumpre o requisito 1 de ter mapas "baseados na USP". Com a movimentação livre pelos mapas, através do teclado, e com a mudança de comportamento do player em diferentes contextos o jogo também cumpre o requisito 2, salvo as adaptações ja comentadas. 






