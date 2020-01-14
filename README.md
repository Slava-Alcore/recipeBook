Java проект "Книга Рецептов" с регистрацией/авторизацией и правами доступа на основе ролей (USER, ADMIN).
Администратор может создавать/редактировать/удалять пользователей, а пользователи - управлять своим профилем и данными (рецептами) через UI (по AJAX) и по REST интерфейсу с базовой авторизацией.
Возможна фильтрация рецептов по датам и времени.
Весь REST интерфейс покрывается JUnit тестами, используя Spring MVC Test и Spring Security Test.

Стек технологий:
        <a href="http://projects.spring.io/spring-security/">Spring Security</a>,
        <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html">Spring MVC</a>,
        <a href="http://projects.spring.io/spring-data-jpa/">Spring Data JPA</a>,
        <a href="http://spring.io/blog/2014/05/07/preview-spring-security-test-method-security">Spring Security
            Test</a>,
        <a href="http://hibernate.org/orm/">Hibernate ORM</a>,
        <a href="http://hibernate.org/validator/">Hibernate Validator</a>,
        <a href="http://www.slf4j.org/">SLF4J</a>,
        <a href="https://github.com/FasterXML/jackson">Json Jackson</a>,
        <a href="http://ru.wikipedia.org/wiki/JSP">JSP</a>,
        <a href="http://en.wikipedia.org/wiki/JavaServer_Pages_Standard_Tag_Library">JSTL</a>,
        <a href="http://tomcat.apache.org/">Apache Tomcat</a>,
        <a href="http://www.webjars.org/">WebJars</a>,
        <a href="http://datatables.net/">DataTables plugin</a>,
        <a href="http://www.postgresql.org/">PostgreSQL</a>,
        <a href="http://junit.org/">JUnit</a>,
        <a href="http://hamcrest.org/JavaHamcrest/">Hamcrest</a>,
        <a href="http://jquery.com/">jQuery</a>,
        <a href="http://ned.im/noty/">jQuery notification</a>,
        <a href="http://getbootstrap.com/">Bootstrap</a>.

В паланах:
1. Починить работу теста на сохранение сущности (при запуске через Maven выходит ошибка)
2. Добавить больше информации по рецепту (название, время готовки, сложность)
3. Добавить возможность загрузки изображения с хранением в БД
5. Поменять ед измерения на список вариантов
6. Улучшить внешний вид сайта
6. Перейти на Angular
7. Сделай деблпой на Heroku
8. Добавить история и возможность отмены последнего действия
9. Добавить больше опций в фильтр