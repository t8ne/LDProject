
# Chinese Checkers

Projeto desenvolvido no âmbito da disciplina de Laboratório de Programação.

# Como Jogar:

## Setup das livrarias JavaFX

- Antes de tudo, ter a certeza de que tem o OpenJFX versão 21 (usada para LTS).

#### Para este projeto é recomendado utilizar o IntelliJ, já que foi neste programa que tudo foi desenvolvido.

Seguem-se os passos para fazer o programa dar run:

- Na toolbar, ir a Run -> Edit Configurations;
- Criar duas novas configurações, ambas Application e nomeá-las "Run Server" e "Run Client" respetivamente;
- Selecionar o Java 21 como JDK para as duas configurações;
- Escrever "run.Run" para a Main Class de ambas as configurações;
- Escolher "server" e "client", respetivamente, para o module das configurações;
- Dentro de ambas as configurações ir a Modify Options -> Add VM Options;

 #### Em cada uma das VM Options das configurações meter o caminho do seu computador que o leva ás pastas de JavaFX. Apresenta-se a seguir um código exemplo de como as suas VM Options poderão ficar em duas máquinas diferentes:

- 1º Computador:

``` --module-path C:\Users\t8ne\.m2\repository\org\openjfx\javafx-controls\21;C:\Users\t8ne\.m2\repository\org\openjfx\javafx-fxml\21;C:\Users\t8ne\.m2\repository\org\openjfx\javafx-graphics\21;C:\Users\t8ne\.m2\repository\org\openjfx\javafx-base\21 --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base```


- 2º Computador:

``` --module-path C:\Users\eduko\.m2\repository\org\openjfx\javafx-controls\21;C:\Users\eduko\.m2\repository\org\openjfx\javafx-fxml\21;C:\Users\eduko\.m2\repository\org\openjfx\javafx-graphics\21;C:\Users\eduko\.m2\repository\org\openjfx\javafx-base\21  --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base```
- A seguir aplicar e salvar ambas as configurações. Se quando der Run ao Servidor a palavra "LOOP" lhe aparecer no terminal, o seu servidor estará ligado. Se quando ligar o cliente lhe aparecer a devida página no ecrâ, tem o Cliente a funcionar.

#### É de notar que pode jogar com pessoas na mesma rede porém um dos jogadores terá que ter o servidor a correr. Se quiser jogar sozinho tem Bots implementados no jogo.
