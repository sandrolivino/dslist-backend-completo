# Projeto DSList - Intensivão Java Spring
O projeto tem como objetivo manter uma base de jogos e classificá-los em listas específicas, com a possibilidade de ordenação dos jogos dentro da lista

## Modelo de domínio DSList

![Modelo de domínio DSList](https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/dslist-model.png)

## Trechos de código

### Plug-in Maven

```xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-resources-plugin</artifactId>
	<version>3.1.0</version> <!--$NO-MVN-MAN-VER$ -->
</plugin>
```

### application.properties

```
spring.profiles.active=${APP_PROFILE:test}
spring.jpa.open-in-view=false

cors.origins=${CORS_ORIGINS:http://localhost:5173,http://localhost:3000}
```

### application-test.properties

```
# H2 Connection
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=

# H2 Client
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# Show SQL
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### application-dev.properties

```
#spring.jpa.properties.jakarta.persistence.schema-generation.create-source=metadata
#spring.jpa.properties.jakarta.persistence.schema-generation.scripts.action=create
#spring.jpa.properties.jakarta.persistence.schema-generation.scripts.create-target=create.sql
#spring.jpa.properties.hibernate.hbm2ddl.delimiter=;

spring.datasource.url=jdbc:postgresql://localhost:5432/dscatalog
spring.datasource.username=postgres
spring.datasource.password=1234567

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.hibernate.ddl-auto=none
```

### application-prod.properties
```
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
spring.jpa.hibernate.ddl-auto=none
```

### system.properties
```
java.runtime.version=17
```

### WebConfig

```java
@Configuration
public class WebConfig {

	@Value("${cors.origins}")
	private String corsOrigins;
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("*").allowedOrigins(corsOrigins);
			}
		};
	}
	
}
```

### GameRepository

```java
@Query(nativeQuery = true, value = """
		SELECT tb_game.id, tb_game.title, tb_game.game_year AS `year`, tb_game.img_url AS imgUrl,
		tb_game.short_description AS shortDescription, tb_belonging.position
		FROM tb_game
		INNER JOIN tb_belonging ON tb_game.id = tb_belonging.game_id
		WHERE tb_belonging.list_id = :listId
		ORDER BY tb_belonging.position
			""")
List<GameMinProjection> searchByList(Long listId);
```

### GameListRepository

```java
@Modifying
@Query(nativeQuery = true, value = "UPDATE tb_belonging SET position = :newPosition WHERE list_id = :listId AND game_id = :gameId")
void updateBelongingPosition(Long listId, Long gameId, Integer newPosition);
```

### import.sql

```sql
INSERT INTO tb_game_list (name) VALUES ('Aventura e RPG');
INSERT INTO tb_game_list (name) VALUES ('Jogos de plataforma');

INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES ('Mass Effect Trilogy', 4.8, 2012, 'Role-playing (RPG), Shooter', 'XBox, Playstation, PC', 'https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/1.png', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit esse officiis corrupti unde repellat non quibusdam! Id nihil itaque ipsum!', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi, quis maiores veniam. Incidunt dolorum, nisi deleniti dicta odit voluptatem nam provident temporibus reprehenderit blanditiis consectetur tenetur. Dignissimos blanditiis quod corporis iste, aliquid perspiciatis architecto quasi tempore ipsam voluptates ea ad distinctio, sapiente qui, amet quidem culpa.');
INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES ('Red Dead Redemption 2', 4.7, 2018, 'Role-playing (RPG), Adventure', 'XBox, Playstation, PC', 'https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/2.png', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit esse officiis corrupti unde repellat non quibusdam! Id nihil itaque ipsum!', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi, quis maiores veniam. Incidunt dolorum, nisi deleniti dicta odit voluptatem nam provident temporibus reprehenderit blanditiis consectetur tenetur. Dignissimos blanditiis quod corporis iste, aliquid perspiciatis architecto quasi tempore ipsam voluptates ea ad distinctio, sapiente qui, amet quidem culpa.');
INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES ('The Witcher 3: Wild Hunt', 4.7, 2014, 'Role-playing (RPG), Adventure', 'XBox, Playstation, PC', 'https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/3.png', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit esse officiis corrupti unde repellat non quibusdam! Id nihil itaque ipsum!', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi, quis maiores veniam. Incidunt dolorum, nisi deleniti dicta odit voluptatem nam provident temporibus reprehenderit blanditiis consectetur tenetur. Dignissimos blanditiis quod corporis iste, aliquid perspiciatis architecto quasi tempore ipsam voluptates ea ad distinctio, sapiente qui, amet quidem culpa.');
INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES ('Sekiro: Shadows Die Twice', 3.8, 2019, 'Role-playing (RPG), Adventure', 'XBox, Playstation, PC', 'https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/4.png', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit esse officiis corrupti unde repellat non quibusdam! Id nihil itaque ipsum!', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi, quis maiores veniam. Incidunt dolorum, nisi deleniti dicta odit voluptatem nam provident temporibus reprehenderit blanditiis consectetur tenetur. Dignissimos blanditiis quod corporis iste, aliquid perspiciatis architecto quasi tempore ipsam voluptates ea ad distinctio, sapiente qui, amet quidem culpa.');
INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES ('Ghost of Tsushima', 4.6, 2012, 'Role-playing (RPG), Adventure', 'XBox, Playstation, PC', 'https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/5.png', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit esse officiis corrupti unde repellat non quibusdam! Id nihil itaque ipsum!', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi, quis maiores veniam. Incidunt dolorum, nisi deleniti dicta odit voluptatem nam provident temporibus reprehenderit blanditiis consectetur tenetur. Dignissimos blanditiis quod corporis iste, aliquid perspiciatis architecto quasi tempore ipsam voluptates ea ad distinctio, sapiente qui, amet quidem culpa.');
INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES ('Super Mario World', 4.7, 1990, 'Platform', 'Super Ness, PC', 'https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/6.png', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit esse officiis corrupti unde repellat non quibusdam! Id nihil itaque ipsum!', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi, quis maiores veniam. Incidunt dolorum, nisi deleniti dicta odit voluptatem nam provident temporibus reprehenderit blanditiis consectetur tenetur. Dignissimos blanditiis quod corporis iste, aliquid perspiciatis architecto quasi tempore ipsam voluptates ea ad distinctio, sapiente qui, amet quidem culpa.');
INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES ('Hollow Knight', 4.6, 2017, 'Platform', 'XBox, Playstation, PC', 'https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/7.png', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit esse officiis corrupti unde repellat non quibusdam! Id nihil itaque ipsum!', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi, quis maiores veniam. Incidunt dolorum, nisi deleniti dicta odit voluptatem nam provident temporibus reprehenderit blanditiis consectetur tenetur. Dignissimos blanditiis quod corporis iste, aliquid perspiciatis architecto quasi tempore ipsam voluptates ea ad distinctio, sapiente qui, amet quidem culpa.');
INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES ('Ori and the Blind Forest', 4, 2015, 'Platform', 'XBox, Playstation, PC', 'https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/8.png', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit esse officiis corrupti unde repellat non quibusdam! Id nihil itaque ipsum!', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi, quis maiores veniam. Incidunt dolorum, nisi deleniti dicta odit voluptatem nam provident temporibus reprehenderit blanditiis consectetur tenetur. Dignissimos blanditiis quod corporis iste, aliquid perspiciatis architecto quasi tempore ipsam voluptates ea ad distinctio, sapiente qui, amet quidem culpa.');
INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES ('Cuphead', 4.6, 2017, 'Platform', 'XBox, Playstation, PC', 'https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/9.png', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit esse officiis corrupti unde repellat non quibusdam! Id nihil itaque ipsum!', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi, quis maiores veniam. Incidunt dolorum, nisi deleniti dicta odit voluptatem nam provident temporibus reprehenderit blanditiis consectetur tenetur. Dignissimos blanditiis quod corporis iste, aliquid perspiciatis architecto quasi tempore ipsam voluptates ea ad distinctio, sapiente qui, amet quidem culpa.');
INSERT INTO tb_game (title, score, game_year, genre, platforms, img_url, short_description, long_description) VALUES ('Sonic CD', 4, 1993, 'Platform', 'Sega CD, PC', 'https://raw.githubusercontent.com/devsuperior/java-spring-dslist/main/resources/10.png', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Odit esse officiis corrupti unde repellat non quibusdam! Id nihil itaque ipsum!', 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Delectus dolorum illum placeat eligendi, quis maiores veniam. Incidunt dolorum, nisi deleniti dicta odit voluptatem nam provident temporibus reprehenderit blanditiis consectetur tenetur. Dignissimos blanditiis quod corporis iste, aliquid perspiciatis architecto quasi tempore ipsam voluptates ea ad distinctio, sapiente qui, amet quidem culpa.');

INSERT INTO tb_belonging (list_id, game_id, position) VALUES (1, 1, 0);
INSERT INTO tb_belonging (list_id, game_id, position) VALUES (1, 2, 1);
INSERT INTO tb_belonging (list_id, game_id, position) VALUES (1, 3, 2);
INSERT INTO tb_belonging (list_id, game_id, position) VALUES (1, 4, 3);
INSERT INTO tb_belonging (list_id, game_id, position) VALUES (1, 5, 4);

INSERT INTO tb_belonging (list_id, game_id, position) VALUES (2, 6, 0);
INSERT INTO tb_belonging (list_id, game_id, position) VALUES (2, 7, 1);
INSERT INTO tb_belonging (list_id, game_id, position) VALUES (2, 8, 2);
INSERT INTO tb_belonging (list_id, game_id, position) VALUES (2, 9, 3);
INSERT INTO tb_belonging (list_id, game_id, position) VALUES (2, 10, 4);
```

### Script Docker Compose

https://gist.github.com/acenelio/5e40b27cfc40151e36beec1e27c4ff71


### Comentários SANDRO

## Aula 02

[] Criar a classe GameList
- Construtores: remover o super
- Gerar os getters e setters
- Gerar hashCode & equals: só com o id é suficiente
[] Criar a classe Belonging: relação intermediária do relacionamento N to N entre Game e GameList
- Como o JpaRepository exige um id único "JpaRepository<T, ID>", não é possível criar uma chave primária em Belonging com os atributos:
-- private Game game;
-- private GameList list;
Será preciso criar uma classe auxiliar que conterá uma referência para Game e para GameList:

[] Criar a classe de apoio BelongingPK
@Embeddable
public class BelongingPK {

	@ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private GameList list;

	// Construtores
	
	// Getters and Setters
	
	// Equals & HashCodes: aqui tem que usar os dois atributos, pois serão a chave primária em Belonging
}

[] Classe Belonging após a criação de BelongingPK
@Entity
@Table(name = "tb_belonging")
public class Belonging {

	// @EmbeddedId porque estamos a clesse BelongingPK que contém ID composto entre Game e GameList
	@EmbeddedId
	private BelongingPK id = new BelongingPK();

	private Integer position;
	
	// Construtores: no construtor com parâmetros, passar o Game e o GameList em separado ao invés de passar o BelongingPK
	Ao invés disso:
	public Belonging(BelongingPK id, Integer position) {
		this.id = id;
		this.position = position;
	}
	Isso:
	public Belonging(Game game, GameList list, Integer position) {
		this.id.setGame(game);
		this.id.setList(list);
		this.position = position;
	}
	
	// Getters and Setters
	
	// HashCodes && Equals (com id e position)
}

[] Completar o seed do banco para os inserts de GameList e de Belonging

[] Criar o DTO GameDTO: para buscar todas os atributos do Game
- Lembrar que até então o DTO existente (GameMinDTO) trazia apenas dados resumidos do Game
- Nesse caso, como a cópia será exata (Entity to DTO), usar BeanUtils direto no construtor:
public GameDTO(Game entity) {
	BeanUtils.copyProperties(entity, this);
}
- Para o BeanUtils funcionar, o DTO tem que ter todos os Getters e Setters

[] Criar o método findById em GameService (Retorna um GameDTO que será usado pelo controller)
- Lembrar que o findById do repositório retorna um Optional, então, ao final, inserir ".get()"
@Transactional(readOnly = true) // Garante que a transação será ACID (atômica, consistente, isolada e durável)
public GameDTO findById(@PathVariable Long listId) {
	Game result = gameRepository.findById(listId).get();
	return new GameDTO(result);
}
- Lembrar também que, como o id pode não existir, devemos fazer um tratamento de exceções: FAZER DEPOIS

[] Criar o findById no GameController
- Lembrar de passar o value "{id}" no @GetMapping
@GetMapping(value = "/{id}")
public GameDTO findById(@PathVariable Long id) {
	GameDTO result = gameService.findById(id);
	return result;
}


[] Criar GameListDTO
- Somente com getters, pois não usou o BeanUtils

[] Criar o repositório de GameList GameListRepository
[] Criar o serviço de GameList GameListService
[] Criar o controller GameListControler

[] Criar consulta SQL Nativa
- Deve ser implementado em GameRepository, pois todos os acessos a dados partem daqui:
	@Query(nativeQuery = true, value = """
			SELECT tb_game.id, tb_game.title, tb_game.game_year AS `year`, tb_game.img_url AS imgUrl,
			tb_game.short_description AS shortDescription, tb_belonging.position
			FROM tb_game
			INNER JOIN tb_belonging ON tb_game.id = tb_belonging.game_id
			WHERE tb_belonging.list_id = :listId
			ORDER BY tb_belonging.position
				""")
	List<GameMinProjection> searchByList(Long listId);

- Quando se faz uma nativeQuery, o resultado da consulta deve ser uma interface, no nosso caso:
public interface GameMinProjection {
	Long getId();
	String getTitle();
	Integer getYear();
	String getImgUrl();
	String getShortDescription();
	Integer getPosition();
}
// A interface DEVE ter métodos GETTERS correspondentes à minha consulta.

[] Criar um endpoint para usar a consulta com a projection
- Esse endpoint é uma busca de games por lista
- Atenção: DEVE ser criado em GameService, pois é uma consulta que retorna Games
@Transactional(readOnly = true)
public List<GameMinDTO> findByGameList(Long listId) {
	List<GameMinProjection> games = gameRepository.searchByList(listId);
	return games.stream().map(GameMinDTO::new).toList();
}

[] Criar um novo construtor em GameMinDTO, que irá receber a projection como argumento
public GameMinDTO(GameMinProjection projection) {
	id = projection.getId();
	title = projection.getTitle();
	year = projection.getYear();
	imgUrl = projection.getImgUrl();
	shortDescription = projection.getShortDescription();
}

[] Criação do endpoint de fato: DEVE ser criado no Controller de Listas (Apesar de retornar Games)
@GetMapping(value = "/{listId}/games")
	public List<GameMinDTO> findGames(@PathVariable Long listId) {
	List<GameMinDTO> result = gameService.findByGameList(listId);
	return result;
}

- Não esquecer de injetar o GameService no controller de Listas
@Autowired
private GameListService gameListService;


## Aula 03

Até 11 min, como configurar o GITHub para portfólio
[] Perfis de projeto
- test: Banco H2
- homolog: Homologação em ambiente o mais parecido possível com o ambiente prod
- prod: 

[] Configurar o docker-compose.yaml para usar mysql, postgres, phpMyAdmin e pgAdmin
- Criei em C:\Sistemas\Estudos\Docker
- No curso ele não fala de mysql nem de phpMyAdmin

[] Configurar os ambientes e testar a aplicação no PostgreSQL. Até então a aplicação está no H2
- Em application.properties, criar a seguinte entrada:
spring.profiles.active=${APP_PROFILE:test}
Com isso, o application.properties irá redirecionar a aplicação para:
-- application-test.properties, onde esse -test, é o apelido dado em: ${APP_PROFILE:test}
-- Em application-test.properties nós temos as configurações do H2

[] Criar os profiles application-dev.properties e application-prod.properties
- Lembrar de mudar a porta (5433) para acesso de fora do container: jdbc:postgresql://localhost:5433/dsgamelist

[] Configurar o arquivo system.properties, com o conteúdo: java.runtime.version=17 (o mesmo do pom.xml)
- Fica na pasta raiz do projeto
- Algumas plataformas de nuvem, como o Hiroku, exigem esse arquivo.

[] Gerar script da base de dados para rodar no postgresql
- O spring gera isso automaticamente pra gente
- Descomentar as 4 primeiras linhas de application-dev.properties
- Mudar o perfil de test para dev em spring.profiles.active=${APP_PROFILE:test}
- Rodar a aplicação
- Verificar que o spring criou na pasta do projeto (C:\Sistemas\Estudos\dslist-backend-aula3) um arquivo chamado create.sql
- Copiar o conteúdo do arquivo e rodar do pgAdmin
- Testar a aplicação no Postman para ver se tudo está bem
-- Nesse momento, verificar que a consulta customizada, que funcionou no H2, não funcionou no PostgreSQL, pois a palavra year é reservada no PostgreSQL
-- Ajustar a consulta customizada, tirando as aspas simples de year e renomeando para gameYear
-- Alterar o campo também na projection e no DTO GameMinDTO.

[] Voltar o ambiente para test

Pré-requisitos para deploy CI/CD na nuvem
[] Criar conta no RailWay - http://railway.app
[] Login com o GitHub (conta com mais de 90 dias)
[] Projeto Spring Boot salvo no GitHub
[] Script do banco disponível
[] Aplicativo de gestão de banco instalado (local ou docker)

Passos RailWay
[] Prover um servidor de BD no RailWay
- Start a new project
- Provision PostgreSQL
- Conectar nosso PgAdmin (Docker) no PostgreSQL recém-criado no RailWay. Copiar DATABASE_URL e preencher os campos:
postgresql://postgres:z9dfZH8m60w6ux8mK3cp@containers-us-west-18.railway.app:8033/railway
Onde...
postgresql://
postgres = user
:
z9dfZH8m60w6ux8mK3cp = password
@
containers-us-west-18.railway.app = host
:
8033 = port
/
railway = database_name
-- Incluir também esse database_name na aba Advanced


[] Realizar seed de dados - com o create.sql que geramos nas aulas anteriores
- RailWay >> Schemas >> Public >> Tables: QueryTool
- Copiar e colar o conteúdo de create.sql

[] Criar uma aplicação RailWay vinculada a um repositório GitHub
- Start a new project
- Deploy from GitHub
- Conectar no GitHub e escolher o repositório do projeto
- Configurar variáveis de ambiente...

[] Configurar variáveis de ambiente
- APP_PROFILE
- DB_URL (Formato: jdbc:postgresql://host:porta/nome-da-base
- DB_USERNAME
- DB_PASSWORD
- CORS_ORIGINS

[] Gerar um domínio público no RailWay
- Settings >> Domain: Gennerate
-- resultado (muda de app para app): dslist-backend-completo-production.up.railway.app
-- Testar no browser: dslist-backend-completo-production.up.railway.app/games

[] Configurar o Postman com o endpoint na nuvem
- Criar variável de ambiente no postman

[] Configurar o CORS_ORIGINS
O que é o CORS? O CORS (Cross-origin Resource Sharing) é um mecanismo usado para adicionar cabeçalhos HTTP que informam aos navegadores para permitir que uma aplicação Web seja executada em uma origem e acesse recursos de outra origem diferente.
Criar a classe WebConfig no pacote config
- O conteúdo é padrão (CTRL C + CTRL V)
- Não testei as URLs, por isso não configurei o CORS_ORIGINS no RailWay

[] Testar esteira de CI/CD. RailWay integrado com GitHub
- Mudar alguma coisa no código e fazer o push no github pra ver a aplicação subindo no RailWay


## Aula 04
[] Configurar os métodos de ordenação da lista
- Criar consulta customizada no repositório de listas
- Lembrar que a atualização da lista é imdepotente, ou seja, todas as vezes que for rodada gerará um resultado diferente
