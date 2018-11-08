# Distributed Computing "Cloud-Datenmanagement"

## Aufgabenstellung
Die detaillierte [Aufgabenstellung](TASK.md) beschreibt die notwendigen Schritte zur Realisierung.

## Design und Beschreibung
Die Übung wird mit Java, Spring Boot, Spring Security, Spring Data JPA, HSQL und JSP umgesetzt. Es gibt mehrere Möglichkeiten, man könnte es auch mit Python zum Beispiel lösen.  
Für diese Übung wurde das Tutorial von der Webseite [1] verwendet und abgeändert.  

Dieses Tutorial ist schon sehr komplex. Die Aufgabenstellung wäre auch ohne Spring-Security und Validierung möglich. Da das hier aber schon dabei ist, habe ich es nicht extra entfernt.

#### HSQL
HSQL ist eine in-Memory realationale Datenbank, die in Java geschrieben wurde. Die Dependency dafür im pom.xml File:  

	<dependency>
		<groupId>org.hsqldb</groupId>
		<artifactId>hsqldb</artifactId>
		<scope>runtime</scope>
	</dependency>

#### JSP
JSP steht für Java Server Pages und ist eine Web-Programmiersprache zur einfachen Erstellung von HTML Webseiten. Mit der Sprache kann man Java in HTML einbinden.

## Implementierung

## Deployment

## Quellen
[1] https://hellokoding.com/registration-and-login-example-with-spring-security-spring-boot-spring-data-jpa-hsql-jsp/  
