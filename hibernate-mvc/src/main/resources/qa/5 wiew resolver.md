Крок 5. Налаштовуємо ViewResolver
🎯 Мета

Навчити Spring знаходити JSP-сторінки.

Після цього Controller зможе повертати лише 
коротку назву View:

return "main-menu";

а Spring сам знайде файл
/WEB-INF/view/main-menu.jsp

Спочатку зрозуміємо проблему
Уявімо, що ми вже написали Controller.

@Controller
public class HelloController {

    @RequestMapping("/")
    public String showHomePage() {
        return "main-menu";
    }
}

Питання.

Що таке "main-menu" Для Java?
Нічого. Просто рядок.

Для DispatcherServlet?
Теж просто рядок.

Він не знає:

чи це JSP;
чи HTML;
чи Thymeleaf;
чи PDF;
чи JSON.

Потрібен компонент, який пояснить:

Якщо Controller повернув "main-menu", 
потрібно відкрити ось цей файл.

Саме ним є ViewResolver.
Що робить InternalResourceViewResolver

Він складає повний шлях із трьох частин.

prefix
+
viewName
+
suffix

тобто

"/WEB-INF/view/"
+
"main-menu"
+
".jsp"

отримуємо

/WEB-INF/view/main-menu.jsp

Саме цей файл відкриє DispatcherServlet.

Візуально
Controller
↓
return "main-menu"
↓
InternalResourceViewResolver
↓
prefix
/WEB-INF/view/
↓
view name
main-menu
↓
suffix
.jsp
↓
/WEB-INF/view/main-menu.jsp

# Реалізація

У MyConfig додаємо метод

@Bean

public InternalResourceViewResolver viewResolver() {

    InternalResourceViewResolver viewResolver =
            new InternalResourceViewResolver();

    viewResolver.setPrefix("/WEB-INF/view/");

    viewResolver.setSuffix(".jsp");

    return viewResolver;
}

Не забудь імпорти

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view
        .InternalResourceViewResolver;
Розберемо кожний рядок

@Bean

Ми вже бачили анотації

@Controller
@Service
@Repository

Вони кажуть Spring:
Створи Bean автоматично.

Але InternalResourceViewResolver — це клас 
бібліотеки Spring.

Ми не можемо поставити на нього @Component.
Тому самі створюємо об'єкт.

@Bean означає

"Те, що повертає цей метод, потрібно покласти 
до Spring Container."

Створення об'єкта
InternalResourceViewResolver viewResolver =
        new InternalResourceViewResolver();

Тут усе звичайний Java-код. 
Створюємо об'єкт.
Поки що він нічого не знає.

Prefix
viewResolver.setPrefix("/WEB-INF/view/");

Ми повідомляємо:
Усі JSP знаходяться ось тут.

Suffix
viewResolver.setSuffix(".jsp");

Тепер Spring знає, що кожне View закінчується

.jsp
Return
return viewResolver;

Оскільки метод позначений
@Bean

Spring збереже цей об'єкт у своєму контейнері.

Після запуску DispatcherServlet уже буде знати, 
де шукати сторінки.
Чому JSP лежать у WEB-INF?

Це дуже цікава особливість.
Якби сторінки лежали просто тут

webapp
index.jsp
employees.jsp

будь-хто міг би відкрити
http://localhost:8080/employees.jsp
і оминути Controller.

Тоді:

бізнес-логіка не виконається;
Model не заповниться;
перевірки доступу можна обійти.

Тому JSP майже завжди кладуть у
WEB-INF

Усе, що знаходиться всередині WEB-INF, 
не доступне напряму через браузер. 
Єдиний спосіб відобразити таку сторінку — 
пройти через DispatcherServlet, 
який викличе Controller, підготує дані 
та лише потім передасть керування ViewResolver.

Як це виглядає тепер
Browser
↓
DispatcherServlet
↓
HelloController
↓
return "main-menu"
↓
InternalResourceViewResolver
↓
/WEB-INF/view/main-menu.jsp
↓
HTML
↓
Browser

Порівняння зі старим web.xml

Ти якраз згадав web.xml. Раніше ViewResolver
часто описували в XML приблизно так:

<bean class="org.springframework.web.servlet
.view.InternalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/view/" />
    <property name="suffix" value=".jsp" />
</bean>

Тепер ми робимо те саме, але сучасним способом 
— через Java Config:

@Bean
public InternalResourceViewResolver viewResolver() {
    InternalResourceViewResolver resolver = 
        new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/view/");
    resolver.setSuffix(".jsp");
    return resolver;
}

Функціонально ці два варіанти еквівалентні. 
Відрізняється лише спосіб конфігурації: XML або Java.