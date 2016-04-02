# README #

Deze Hartige Hap webapplicatie is gemaakt met het Spring MVC framework.

### What is this repository for? ###

De applicatie demonstreert de volgende niet-functionele aspecten:

* architectuur (gelaagde architectuur met MVC in de presentatielaag)
* persistentie in de data access laag met behulp van het object-relation mapping tool JPA 
* transactionaliteit (methodes op de service interfaces)
* tiling (header, footer, menu, body)
* i18n (Engels en Nederlands)
* security (autorisatie en personalisatie)
* theming
* input validatie
* pagination (opgevraagde content wordt geretourneerd in pagina's)
* ajax
* RESTful web service
* verschillende soorten test


Open eindjes:

* onvoldoende tests (foei!)

### How do I get set up? ###

#### Installatie van de ontwikkelomgeving "Spring Tool Suite": ####

* Zorg dat je (de laatste versie) van JDK 8 geïnstalleerd hebt van http://www.oracle.com/technetwork/java/javase/downloads/index.html. Kies duidelijk of je de 32-bit of 64-bit versie wilt gebruiken.
* De Spring bibliotheken kun je het beste downloaden door de bijhorende IDE te installeren die Spring Tool Suite (STS) heet. STS is gebaseerd op de Eclipse IDE. Als je STS installeert, blijft je eventuele andere Eclipse installatie behouden. Je hebt dan twee Eclipse installaties naast elkaar staan. STS kun je vinden op http://spring.io/tools/sts. Kies duidelijk of je de 32-bit of 64-bit versie wilt gebruiken.
* De Hartige Hap applicatie maakt gebruik van Lombok. Lombok is een bibliotheek die de hoeveelheid benodigde boiler plate code vermindert, bijvoorbeeld getters/setters, waardoor je minder hoeft te typen. Hiervoor moet STS worden aangepast, anders begrijpt STS de broncode niet en geeft foutieve foutmeldingen. Om dit te doen:
    1. download lombok.jar van http://projectlombok.org/
    2. dubbelklik op lombok.jar
    3. klik op install/update.

#### Importeren van een bestaande applicatie in STS (als Maven project): ####

* Als het goed is, bestaat de applicatie uit een src/ map en een pom.xml bestand.
* In STS, File/Import/Maven/Existing Maven Projects en kies de map waar pom.xml staat.

#### Installatie van de database waar de applicatie gebruik van maakt: ####

* De applicatie maakt gebruik van een MySQL database met de naam "ivh11a1b".
* De database moet aanwezig zijn om de applicatie te laten werken, maar tabellen hoeven niet handmatig te worden aangemaakt. Dat doet Hibernate, het object-relational-mapping tool voor je.
* Mocht je een eerdere versie van de Hartige Hap webapplicatie geïnstalleerd hebben gehad, dan de oude database volledig weggooien en een nieuwe, lege database creëren, aangezien de tabellenstructuur mogelijk anders is. 
* In het bestand src/main/resources/datasource-jpa-tx.xml staat de configuratie om de database te benaderen, die je indien nodig kunt aanpassen, met name username, password en URL. 
* In het bestand datasource-jpa-tx.xml staat de property <prop key="hibernate.hbm2ddl.auto">create</prop>. Met deze property worden de tabellen telkens weggegooid en opnieuw gecreëerd, als de applicatie wordt gedeployed. Als je dit niet wilt, verwijder dan deze property uit het bestand.

### Runnen van applicatie en unit testen in STS ###
* Voor het runnen van de applicatie of de unit testen in STS moet aspectjweaver aan de VM arguments worden toegevoegd:
    * Ga naar Run -> Run Configurations
    * Zoek de server waarop het project gaat runnen en klik hierop
    * Ga aan de rechter kant naar de twee tab (Argumenents) en voeg bij VM Arguments het volgende toe:
        * -javaagent:C:/Users/test/.m2/repository/org/aspectj/aspectjweaver/1.8.7/aspectjweaver-1.8.7.jar
        * Zorg ervoor dat C:/Users/test/.m2/ verwijst naar je eigen repository

### Instellen poort in Angular ###
* Eventueel kan de port in angular worden aangepast op regel 3 van src/main/webapp/scripts/app.js Standaard staat deze ingesteld op 8080

#### Gebruik van de het subsysteem van de vervroegde oplevering: ####

* deploy de applicatie:
    * rechter muisklik op project, "run as", "run on server"
    * check de console log op excepties
    * STS start automatisch een web browser op binnen STS
    * andere browser? Gebruik: http://localhost:8080/hh/static/index.html
* Voor een manager kan er ingelogd worden met email "manager@hh.nl" en wachtwoord "manager"
* In het menu staan nu een aantal opties:
	* "Zalen" geeft een overzicht van alle zalen en mogelijkheden tot CRUD
	* "Zaalopties" geeft een overzicht van alle zaalopties en mogelijkheden tot CRUD
	* "Reserveringen" geeft een overzicht van alle reserveringen en mogelijkheden tot CRUD
    * "Agenda" geeft alle reserveringen in een agenda weer
