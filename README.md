# Distributed Computing "Cloud-Datenmanagement"

## Aufgabenstellung
Die detaillierte [Aufgabenstellung](TASK.md) beschreibt die notwendigen Schritte zur Realisierung.

## Design und Beschreibung
Die Übung wird mit Java, Spring Boot, Spring Security, Spring Data JPA, HSQL und JSP umgesetzt. Es gibt mehrere Möglichkeiten, man könnte es auch mit Python zum Beispiel lösen.  
Für diese Übung wurde das Tutorial von der Webseite [1] verwendet und abgeändert.  

Dieses Tutorial ist schon sehr komplex. Die Aufgabenstellung wäre auch ohne Spring-Security und Validierung möglich. Da das hier aber schon dabei ist, habe ich es nicht extra entfernt.

#### HSQL
HSQL ist eine in-Memory realationale Datenbank, die in Java geschrieben wurde. Die Dependency dafür im pom.xml File:  
```xml
	<dependency>
		<groupId>org.hsqldb</groupId>
		<artifactId>hsqldb</artifactId>
		<scope>runtime</scope>
	</dependency>
```
#### JSP
JSP steht für Java Server Pages und ist eine Web-Programmiersprache zur einfachen Erstellung von HTML Webseiten. Mit der Sprache kann man Java in HTML einbinden.

## Implementierung
Als erstes wurde das Projekt von der Tutorialwebseite geclont. Bei diesem wird die Userauthentifizierung allerdings mittels Usernamen gemacht. Aus diesem Grund wurden die Methoden so verändert, dass sie nun eine Email stattdessen verwenden.  

Hier wird zum abspeichern der Daten eine User Entity benützt.  
```java
	@Entity
	@Table(name = "user")
	public class User {
		private Long id;
		private String email;
		private String password;
		private String passwordConfirm;
		private Set<Role> roles;

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		public Long getId() {
			return id;
		}

		... getter/setter Methoden
	}
```
Weiters existiert auch eine Entity für die Klasse Role. Da diese für die simple Lösung der Aufgabenstellung aber nicht benötigt wird, spreche ich sie hier nicht an. (sowie alle anderen Klassen, die für die einfache Lösung nicht gebraucht werden)

### Repositories
Die JPA Repositories enthalten Funktionen wie findAll(),save(),... um die Daten zu bearbeiten oder Queries zu erstellen. In dem Fall ist dort die Methode findByEmail definiert (Interface).

### Controller
Die UserController Klasse handelt das RequestMapping. Wenn die eine Anfrage zum Registrieren kommt, dann überprüft die folgende Methode ob die angegebenen Eingaben valid sind und added dann den User.  
```java
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        userService.save(userForm);

        securityService.autologin(userForm.getEmail(), userForm.getPasswordConfirm());

        return "redirect:/welcome";
    }
```
Die __POST Methode von /login__ ist in dieser Klasse nicht auffindbar, da sie schon von SpringSecurity bereitgestellt wird.  

### Spring Security
Spring Security wird in diesem Beispiel verwendet um login/die Authentifizierung zu implementieren. Dort finden sich auch die Methoden, wie beispielsweise 'autologin()', die den User nach einer erfolgreichen Registrierung automatisch einloggen.  

Die Klasse userServiceImpl implementiert das Interface userService. Dort wird die Registrierung des Users gemacht. In der Save Methode wird die save Methode des JPA-Repositories aufgerufen und der User gespeichert.  

### Validation
Um zu überprüfen ob die Input-Daten das richtige Format haben werden sie mit den Einstellungen aus dem File _validation.properties_ verglichen.

### Darstellung
Für die Darstellung der HTML Seiten werden die .jsp Files verwendet. (src/main/webapp/xxx.jsp)  
In diesen Files kann man, wie vorher schon beschrieben, Java implementieren. Das Form für die Login Seite kann folgendermaßen aussehen:  
```html
	<form method="POST" action="${contextPath}/login" class="form-signin">
        <h2 class="form-heading">Log in</h2>

        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${message}</span>
            <input name="email" type="text" class="form-control" placeholder="Email"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <span>${error}</span>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
            <h4 class="text-center"><a href="${contextPath}/registration">Create an account</a></h4>
        </div>

    </form>
```
### Application Properties
Damit unteranderem die HSQL Datenbank funktioniert, muss man folgenden Inhalt in die application.properties einfügen:  
```yaml
	spring.mvc.view.prefix: /
	spring.mvc.view.suffix: .jsp
	spring.jpa.hibernate.ddl-auto=create
	spring.jpa.show-sql=true
	spring.messages.basename=validation
```
## Zusammenfassung
Hier nochmal kurz die Schritte, die man machen muss um die Aufgabenstellungen zu lösen:  

Um die REST Web-Schnittstelle zu erstellen wird Spring-Boot verwendet.  
Die Registrierung des Users wird im Controller gehandelt. Von dort wird der User mit Hilfe eines JPA-Repos gespeichert.  
Login übernimmt Spring-Security in diesem Beispiel.  

Für die Integrations Tests Klassen wird die Annotation @DataJpaTest verwendet. Die Test Methoden müssen mit der Annotation @Test markiert werden. Über Unit Test Klassen kommt @WebMvcTest. [2]  

Leider bin ich noch nicht dazu gekommen diese Tests zu schreiben, werde das vielleicht aber noch nachholen, sofern ich Zeit dazu habe. (Nach der Mathe-SA)

## Deployment
Als Deployment-Tool wurde Maven verwendet.  
```sh
	mvn spring-boot:run
```
Die WEB-REST Schnittstelle ist nun über localhost:8080 erreichbar.
## Quellen
[1] [https://hellokoding.com/registration-and-login-example-with-spring-security-spring-boot-spring-data-jpa-hsql-jsp/](https://hellokoding.com/registration-and-login-example-with-spring-security-spring-boot-spring-data-jpa-hsql-jsp/)  
[2] [https://www.baeldung.com/spring-boot-testing](https://www.baeldung.com/spring-boot-testing)  
