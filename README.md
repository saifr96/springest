# Springust

Starter Spring App developed with SDET August Intake on Tue 29-Sep-2020.

Built with the following starters:

- Spring Web
- H2 Database
- Spring Data JPA
- Lombok

Requires the following Maven dependencies:

- [ModelMapper](https://mvnrepository.com/artifact/org.modelmapper/modelmapper/2.3.8)
- [Swagger-UI v3.0](https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter/3.0.0)

Requirements (if using Eclipse):

- [Spring Tool Suite](https://marketplace.eclipse.org/content/spring-tools-4-aka-spring-tool-suite-4)
- [Lombok](https://projectlombok.org/setup/eclipse)

## API

- Runs out-of-the-box on `localhost:8901` (can be changed within the `application-dev.properties` file)
- H2 console is accessible at the path `/h2` with JDBC URL `jdbc:h2:mem:springust` and default username/password
- Swagger UI showing API endpoints is accessible at the path `/swagger-ui/index.html`

## Authors

- **Nick Johnson** - [nickrstewarttds](https://github.com/nickrstewarttds) - original author
- **Jordan Harrison** - [JHarry444](https://github.com/jharry444) - QA's resident Java and Sping wizard
- **Vinesh Ghela** - [vghela](https://github.com/vghela) - debugged and fixed entity-relationship issues
- **Tay Dzonu** - [Tay-Dzonu-QA](https://github.com/Tay-Dzonu-QA) - fixed one final issue in the Service layer
