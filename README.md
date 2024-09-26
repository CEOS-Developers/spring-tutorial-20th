# 

### 1️⃣ spring-boot-tutorial-20th를 완료해요!

./gradlew bootRun (터미널)로 실행시 80%가 뜨는데 정상적으로 어플리케이션이 작동된 것이 맞음.

build.gradle 수정 후 재빌드해야함

**yaml 파일 경로**

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/a5836003-a8d1-4201-bdfd-b04ef32ee5fb/96d3fc86-6790-4c0c-8d7b-0a3f5400b7af/image.png)

아무 생각없이 문서에 적혀있던대로 src/resources밑에 yaml파일 넣어줌 → h2에서 TEST 데이터베이스가 자동생성이 안됨

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/a5836003-a8d1-4201-bdfd-b04ef32ee5fb/2342194f-c374-44d5-b2ec-45189c12d9f9/image.png)

application.properties가 있는 경로 main/java/resources로 바꿔줌 → 성공

생성안돼서 스프링부트 버전도 낮춰보고 이것저것 해봤는데 왜 이 생각을 처음부터 못했지 싶다^_^

생성된 테이블에 INSERT문으로 값 넣어준 뒤 조회

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/a5836003-a8d1-4201-bdfd-b04ef32ee5fb/2ae1d6e9-d9ca-4956-a20d-db10eae4833f/image.png)

### 2️⃣ spring이 지원하는 기술들(IoC/DI, AOP, PSA 등)을 자유롭게 조사해요

### IoC (Inversion of Control / 제어의 역전)

**IoC**는 **제어의 흐름을 개발자가 아닌 프레임워크나 컨테이너에 넘기는 프로그래밍 원칙**

프레임워크 ⇒ 객체의 생성이나 라이프사이클 관리

개발자 ⇒  애플리케이션의 로직에 집중

### DI (Dependency Injection / 의존성 주입)

**DI**는 **객체 간의 의존성을 외부에서 주입하는 패턴**

즉, 객체가 자신의 의존성을 직접 생성하지 않고, 외부에서 제공받는다.

### IoC와 DI의 관계

- **IoC**는 제어의 흐름을 프레임워크에 넘기는 큰 개념이고, **DI**는 IoC의 한 가지 구체적인 구현 방식입니다. DI를 통해 의존성 관리가 IoC 컨테이너에 의해 이루어짐.

### 스프링이 DI를 처리하는 과정

1. **IoC 컨테이너**: 스프링이 애플리케이션 시작 시 빈(객체)을 IoC 컨테이너에 등록.
2. **의존성 관리**: 스프링은 각 빈이 필요한 의존성을 분석하고, 해당 의존성을 IoC 컨테이너에서 찾아 주입합니다.
3. **자동 주입**: 개발자가 `@Autowired`, `@Service`, `@Component` 등의 어노테이션을 붙이면, 스프링이 이를 보고 의존성을 자동으로 주입합니다.

### 개발자가 할 일과 스프링이 하는 일 비교

- **개발자가 할 일**:
    - 어노테이션(`@Autowired`, `@Component`, `@Service` 등)을 붙여 스프링에게 의존성을 주입해달라고 요청하는 것.
    - 필요한 인터페이스나 클래스 정의.
- **스프링이 하는 일**:
    - 객체(빈) 생성 및 관리.
    - 각 객체의 의존성을 분석하여 필요한 객체를 주입.
    - 애플리케이션 실행 중에 필요한 의존성을 동적으로 제공.

### 개발자가 직접 객체를 생성하지 않으면 생기는 장점

1. **유연성**: 객체의 구체적인 구현이 변경되더라도, 주입받는 코드에는 영향을 주지 않는다.
2. **테스트 용이성**: 테스트 시 모의 객체(mock)를 쉽게 주입할 수 있다.
3. **유지보수성**: 의존성이 명확하게 드러나고, 객체 간 결합도가 낮아져 코드 변경 시에도 유지보수가 쉬워진다.

### **AOP의 개념**

- **핵심 비즈니스 로직**과는 관계없는 **부가 기능(횡단 관심사, Cross-Cutting Concern)**을 분리하여 모듈화하는 프로그래밍 기법
- 주로 **로깅, 보안, 트랜잭션 관리**와 같은 기능들이 **여러 클래스나 메서드에 공통적으로 적용**될 때 사용
- AOP를 통해 코드의 중복을 줄이고, 유지보수성을 향상

- **Aspect**: 부가 기능을 모듈화한 것. 보통 로깅, 보안, 트랜잭션 등이 Aspect로 구현됩니다.
- **Advice**: **부가 기능이 실제로 적용되는 시점**을 정의한 것. 메서드가 호출되기 전, 후, 또는 예외가 발생했을 때 실행될 수 있습니다.
    - **Before**: 메서드 실행 전에 실행되는 Advice.
    - **After**: 메서드 실행 후에 실행되는 Advice.
    - **After Returning**: 메서드가 정상적으로 실행된 후 실행되는 Advice.
    - **After Throwing**: 메서드 실행 중 예외가 발생했을 때 실행되는 Advice.
    - **Around**: 메서드 실행 전후에 실행되며, 메서드 실행을 제어할 수 있는 Advice.
- **Join Point**: Advice가 적용될 수 있는 **메서드 실행 지점**을 의미합니다. 스프링에서는 보통 메서드 호출 지점이 Join Point입니다.
- **Pointcut**: **Advice가 적용될 Join Point를 선택**하는 것. 특정 메서드나 클래스에만 Advice를 적용하고 싶을 때 사용합니다.
- **Target**: Advice가 적용되는 실제 **비즈니스 로직을 담고 있는 객체**입니다.
- **Weaving**: Aspect를 **Target 객체에 적용하는 과정**을 의미합니다. 스프링은 런타임 시점에 Weaving을 수행합니다.

### 3. **AOP 사용 예시**

### AOP를 적용하기 위한 핵심 요소

- **Aspect 정의**: 부가 기능(예: 로깅)을 Aspect로 정의합니다.
- **Pointcut 설정**: 어느 메서드에 해당 Aspect를 적용할지 정의합니다.

### AOP 구현 예시: 로깅

1. **Aspect 클래스** 정의:

    ```java
    import org.aspectj.lang.annotation.Aspect;
    import org.aspectj.lang.annotation.Before;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.stereotype.Component;
    
    @Aspect
    @Component
    public class LoggingAspect {
        private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
        // 특정 패키지 내의 모든 메서드 실행 전 로깅
        @Before("execution(* com.example.service.*.*(..))")
        public void logBefore() {
            logger.info("Method is about to be called");
        }
    }
    
    ```

2. **Pointcut 정의**:
    - `"execution(* com.example.service.*.*(..))"`: `com.example.service` 패키지 내의 **모든 클래스의 모든 메서드**에 적용.
3. **빈으로 등록**:
    - `@Aspect`로 정의한 클래스는 부가 기능을 수행하는 **Aspect**로 등록되고, `@Component` 어노테이션을 통해 스프링이 관리하는 빈으로 등록됩니다.
4. **Advice 적용**:
    - `@Before`: 메서드가 호출되기 전에 실행되도록 설정한 **Advice**로, `com.example.service` 패키지에 있는 모든 메서드 실행 전에 로깅 작업이 수행됩니다.

   ### **스프링 PSA의 예 (Portable Service Abstraction)**

   스프링은 PSA를 통해 다양한 서비스 기술에 대한 추상화 계층을 제공합니다.

   ### 1) **데이터 접근 추상화 (JDBC, JPA)**

   스프링은 **JDBC** 및 **JPA**와 같은 다양한 데이터 접근 기술을 추상화합니다. 이를 통해, 개발자는 구체적인 데이터 접근 방식에 관계없이 일관된 방식으로 데이터베이스를 다룰 수 있습니다.

    - JDBC: `JdbcTemplate`을 사용하여 데이터베이스와 상호작용할 수 있습니다.
    - JPA: `EntityManager`나 `JpaRepository`를 통해 객체지향적으로 데이터베이스 작업을 수행할 수 있습니다.

    ```java
    // JdbcTemplate을 사용한 데이터 접근
    public class UserRepository {
        private final JdbcTemplate jdbcTemplate;
    
        public UserRepository(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
        }
    
        public List<User> findAll() {
            return jdbcTemplate.query("SELECT * FROM users", new UserRowMapper());
        }
    }
    
    ```


### 3️⃣ Spring Bean 이 무엇이고, Bean 의 라이프사이클은 어떻게 되는지 조사해요

### 1. **스프링 빈(Spring Bean)이란?**

- *스프링 빈(Bean)**은 **스프링 IoC(Inversion of Control) 컨테이너에 의해 관리되는 객체**를 의미합니다. 즉, 애플리케이션에서 사용할 객체를 스프링이 생성, 초기화, 관리, 소멸까지 책임지는 객체를 빈이라고 부릅니다. 스프링 빈은 스프링 애플리케이션의 구성 요소이며, 스프링이 애플리케이션의 흐름을 제어하는 중요한 개념입니다.

스프링 빈은 개발자가 명시적으로 빈을 등록하거나, 스프링이 자동으로 빈을 탐색하고 등록할 수 있습니다. 이를 통해 객체 간 의존성을 관리하고, 결합도를 낮출 수 있습니다.

### 스프링 빈을 정의하는 방법

1. **어노테이션 기반 등록**:
    - 클래스에 `@Component`, `@Service`, `@Repository`, `@Controller` 등의 어노테이션을 사용하면 스프링이 자동으로 해당 클래스를 빈으로 등록합니다.

    ```java
    @Service
    public class MyService {
        // Business logic here
    }
    
    ```

2. **XML 또는 Java 설정 파일을 통한 등록**:
    - XML

    ```xml
    <bean id="myBean" class="com.example.MyClass" />
    
    ```

   또는 Java 설정 파일에서 직접 빈을 정의할 수도 있습니다.니

    ```java
    @Configuration
    public class AppConfig {
        @Bean
        public MyService myService() {
            return new MyService();
        }
    }
    
    ```


### 개발자의 역할

개발자는 의존성 주입을 설정하기 위해 필요한 코드를 작성합니다. 예를 들어, 클래스에 `@Autowired` 어노테이션을 추가하거나, XML 설정 파일에서 의존성을 정의합니다. 이를 통해 스프링 컨테이너가 어떤 의존성을 주입해야 하는지 명시합니다.

### 스프링의 역할

스프링 프레임워크는 의존성 주입을 자동으로 관리합니다. 개발자가 설정한 의존성 주입 정보를 바탕으로, 스프링 컨테이너는 다음과 같은 역할을 수행합니다:

1. **빈 생성**: 스프링 컨테이너는 애플리케이션이 시작될 때 빈을 생성합니다.
2. **의존성 주입**: 생성된 빈에 필요한 의존성을 자동으로 주입합니다. 개발자가 직접 객체를 생성하고 연결할 필요 없이, 스프링 컨테이너가 이를 처리합니다.
3. **라이프사이클 관리**: 스프링 컨테이너는 빈의 라이프사이클을 관리합니다. 빈의 초기화와 소멸 메서드를 호출하여 리소스를 정리합니다.

### 예시

```java
@Component
public class MyService {
    // MyService 클래스 정의
}

@Component
public class MyController {
    private final MyService myService;

    @Autowired
    public MyController(MyService myService) {
        this.myService = myService;
    }
}

```

위의 예시에서 개발자는 `@Component`와 `@Autowired` 어노테이션을 사용하여 의존성 주입을 설정합니다. 스프링 컨테이너는 애플리케이션이 시작될 때 `MyService` 빈을 생성하고, `MyController` 빈에 주입합니다.

### 2. **스프링 빈의 라이프사이클**

스프링 빈의 라이프사이클은 **객체가 생성되고 관리되며 소멸되는 과정**을 의미합니다. 스프링은 빈의 생애주기를 IoC 컨테이너에서 관리하며, 이를 통해 개발자는 객체 생성 및 소멸에 대해 신경 쓸 필요가 없습니다.

### **스프링 빈 라이프사이클의 주요 단계**

1. **빈 생성(Instantiation)**:
    - 스프링 컨테이너는 빈을 생성합니다. 빈 정의에 따라 스프링은 빈의 클래스 정보를 보고 해당 클래스로 객체를 생성합니다.

    ```java
    public class MyBean {
        public MyBean() {
            System.out.println("빈 생성됨!");
        }
    }
    
    ```

2. **의존성 주입(Dependency Injection)**:
    - 빈이 생성된 후, 스프링은 해당 빈이 필요로 하는 의존성을 주입합니다. 이를 통해 다른 빈들이 이 빈에 주입될 수 있습니다. 주입 방식은 **생성자 주입**, **세터 주입**, 또는 **필드 주입** 등이 있습니다.

    ```java
    @Autowired
    private MyDependency dependency;
    
    ```

3. **빈 초기화(Initialization)**:
    - 빈이 생성되고 의존성이 모두 주입된 후, 초기화 작업이 수행됩니다. 빈이 준비되는 과정에서 개발자가 특정 작업을 실행할 수 있습니다. 이를 위해 **`@PostConstruct`** 어노테이션이나 `InitializingBean` 인터페이스의 `afterPropertiesSet()` 메서드를 사용할 수 있습니다.

    ```java
    @PostConstruct
    public void init() {
        System.out.println("빈 초기화 중...");
    }
    
    ```

4. **빈 사용(Usage)**:
    - 빈이 초기화되면 스프링 애플리케이션에서 이 빈을 사용할 수 있습니다. 빈이 IoC 컨테이너에서 제공하는 방식에 따라 필요한 곳에서 주입되어 비즈니스 로직을 처리하게 됩니다.
5. **빈 소멸(Destruction)**:
    - 애플리케이션이 종료되거나 빈이 더 이상 필요 없을 때, 스프링은 빈을 소멸시킵니다. 이 과정에서 빈 소멸 전에 필요한 작업을 수행할 수 있습니다. 이를 위해 **`@PreDestroy`** 어노테이션이나 `DisposableBean` 인터페이스의 `destroy()` 메서드를 사용할 수 있습니다.

    ```java
    @PreDestroy
    public void cleanup() {
        System.out.println("빈 소멸 중...");
    }
    
    ```


### **스프링 빈 라이프사이클 메서드 요약**

- **`@PostConstruct`**: 빈이 초기화된 후 실행되는 메서드입니다. 빈이 의존성 주입을 받은 후 추가적인 초기화 작업을 처리할 수 있습니다.
- **`@PreDestroy`**: 빈이 소멸되기 직전에 실행되는 메서드입니다. 애플리케이션이 종료되기 전이나 빈이 소멸되기 전에 리소스를 정리하는 데 사용됩니다.
- **`InitializingBean.afterPropertiesSet()`**: 빈의 모든 의존성이 주입된 후 호출되는 초기화 메서드입니다.
- **`DisposableBean.destroy()`**: 빈이 소멸되기 전에 호출되는 메서드입니다.

### 3. **스프링 빈 스코프**

스프링 빈의 라이프사이클은 **빈 스코프**에 따라 달라질 수 있습니다. 빈 스코프는 빈이 언제 생성되고 소멸될지를 정의합니다.

1. **싱글톤(Singleton)** (기본값):
    - 스프링 애플리케이션 내에서 **빈이 단 하나만** 생성되고, 애플리케이션의 종료 시점에 소멸됩니다.
2. **프로토타입(Prototype)**:
    - 요청할 때마다 빈의 새로운 인스턴스가 생성됩니다. 빈이 소멸되더라도 스프링은 이를 관리하지 않으며, 개발자가 직접 소멸 처리를 해야 합니다.
3. **웹 관련 스코프**: **설정 방법-** `@Scope("prototype")` 어노테이션을 사용하여 설정.
    - **`request`**: HTTP 요청마다 새로운 빈 인스턴스를 생성.
    - **`session`**: HTTP 세션마다 빈의 인스턴스가 생성되고, 세션이 종료될 때 빈이 소멸됨.
    - **`application`**: 웹 애플리케이션 내에서 하나의 빈만 존재.
    - **`websocket`**: 웹소켓 세션마다 빈 인스턴스가 생성됨.

### 4. **스프링 빈 라이프사이클의 예시**

```java
@Component
public class MyBean {

    public MyBean() {
        System.out.println("빈 생성");
    }

    @PostConstruct
    public void init() {
        System.out.println("빈 초기화");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("빈 소멸");
    }
}

```

### 5. **스프링 빈 라이프사이클 콜백 인터페이스**

스프링은 **인터페이스**를 통해도 빈의 라이프사이클을 제어할 수 있습니다.

- **`InitializingBean`**: 빈이 초기화된 후 처리할 로직을 작성합니다. `afterPropertiesSet()` 메서드를 구현하여 사용합니다.
- **`DisposableBean`**: 빈이 소멸되기 전에 처리할 로직을 작성합니다. `destroy()` 메서드를 구현하여 사용합니다.

```java
public class MyBean implements InitializingBean, DisposableBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean: 빈 초기화");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean: 빈 소멸");
    }
}

```

### 결론

- **스프링 빈**은 스프링 컨테이너에서 관리되는 객체로, 애플리케이션의 핵심 구성 요소
- 스프링 빈의 **라이프사이클**은 빈이 생성되고 의존성을 주입받아 초기화된 후 사용되다가, 애플리케이션 종료 시 소멸되는 일련의 과정
- 빈의 라이프사이클은 개발자가 직접 관리하지 않아도 스프링이 자동으로 처리하지만, 특정 지점에서 초기화 및 소멸 작업을 정의할 수 있음
- **스코프**에 따라 빈의 라이프사이클은 달라질 수 있음

### 4️⃣ 스프링 어노테이션을 심층 분석해요

### 어노테이션이란 무엇이며, Java에서 어떻게 구현될까요?

`@Component`, `@Service`, `@Repository`, `@Controller`는 스프링에서 사용하는 **특정한 역할을 가지는 어노테이션**들로, 각각의 클래스가 **스프링 빈(Bean)**으로 등록되고, 애플리케이션에서 해당 클래스의 역할을 명확히 구분해줍니다. 모두 스프링이 제공하는 **스테레오타입 어노테이션**이라고도 불리며, 스프링이 클래스들을 스캔하여 빈으로 등록할 때 사용됩니다

### 1. **@Component**

`@Component`는 **가장 기본적인 스테레오타입 어노테이션**입니다. 스프링의 빈으로 관리되어야 하는 **모든 일반적인 클래스**에 사용할 수 있습니다. 기능에 특별한 구분 없이, 단순히 **빈으로 등록되어야 할 객체**라면 이 어노테이션을 사용합니다.

```java
@Component
public class MyComponent {
    public void doSomething() {
        System.out.println("Component is working!");
    }
}

```

- **역할**: 특정 역할이 없는 범용적인 빈을 등록할 때 사용.
- **사용처**: 비즈니스 로직, 데이터 처리 로직, 도메인 객체 등 모든 종류의 클래스를 빈으로 등록할 때 사용할 수 있음.

### 2. **@Service**

`@Service`는 **비즈니스 로직을 담당하는 클래스**에 사용됩니다. 이 어노테이션을 사용하면 스프링이 해당 클래스를 **서비스 계층**의 빈으로 등록하고 관리합니다. 기능적으로는 `@Component`와 동일하게 동작하지만, 코드의 **명확한 역할 구분**을 위해 **비즈니스 로직**을 처리하는 클래스에 주로 사용합니다.

```java

@Service
public class MyService {
    public String getBusinessLogic() {
        return "Business logic result";
    }
}

```

- **역할**: 서비스 계층에서 **비즈니스 로직**을 수행하는 빈을 등록할 때 사용.
- **사용처**: 사용자 요청을 처리하고 비즈니스 규칙을 적용하는 **서비스 계층**의 클래스.

### 3. **@Repository**

`@Repository`는 **데이터 접근 계층**에서 사용되는 어노테이션입니다. 이 어노테이션을 사용하면 스프링이 해당 클래스를 **DAO(Data Access Object)**로 인식하고, 데이터베이스와의 상호작용을 담당하는 빈으로 등록합니다. 스프링은 이 어노테이션을 통해 **데이터 예외를 스프링의 데이터 접근 예외로 변환**하는 역할도 수행합니다.

```java
@Repository
public class MyRepository {
    public List<String> findAll() {
        return List.of("Data1", "Data2");
    }
}
```

- **역할**: 데이터베이스나 외부 데이터를 처리하는 **DAO 클래스**를 빈으로 등록.
- **사용처**: **데이터베이스 접근 로직**을 처리하는 계층, 예를 들어 JPA를 사용하는 엔티티 저장소.

### 4. **@Controller**

`@Controller`는 **웹 계층**에서 사용되는 어노테이션입니다. 스프링 MVC에서 **사용자의 요청을 받아 처리하고, 응답을 반환하는 역할**을 하는 클래스에 사용됩니다. 주로 **HTTP 요청을 처리**하고, 결과를 웹 페이지나 API 응답으로 돌려줄 때 사용됩니다.

```java
@Controller
public class MyController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
}

```

- **역할**: **웹 요청과 응답을 처리**하는 컨트롤러를 빈으로 등록.
- **사용처**: **웹 계층**, 즉 브라우저나 클라이언트로부터 **HTTP 요청을 처리**하는 클래스.

- **@Component**: 범용 빈 등록 어노테이션, 특별한 구분이 없는 클래스에 사용.
- **@Service**: **비즈니스 로직**을 처리하는 클래스에 사용.
- **@Repository**: **데이터 접근 로직**을 처리하는 클래스에 사용, 예외 처리를 스프링의 예외로 변환.
- **@Controller**: **웹 요청과 응답**을 처리하는 클래스에 사용, 주로 스프링 MVC에서 사용.

### 개발자가 해야 할 일: 어노테이션 설정

개발자는 **어노테이션**을 사용해 스프링에게 의존성을 어떻게 주입할지를 알려주기만 하면 된다.

객체 생성, 라이프사이클 관리, 의존성 주입은 스프링이 자동으로 처리한다.

### 예시: 의존성 주입 자동 설정

```java
@Service
public class UserService {
    private final UserRepository userRepository;

    // 의존성 주입을 위한 생성자
    @Autowired  // 스프링이 UserRepository를 주입함
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

```

위 코드를 보면, **`UserService`가 `UserRepository`에 의존**하고 있다.

하지만 `UserService`는 `UserRepository` 객체를 **직접 생성하지 않고**, 스프링이 해당 객체를 주입하도록 맡김.

- **`@Service`**: 스프링에게 이 클래스가 서비스 역할을 한다고 알려줌
- **`@Autowired`**: 스프링이 `UserRepository` 객체를 `UserService`에 자동으로 주입하게 만듬.

### 스프링에서 어노테이션을 통해 Bean을 등록할 때, 어떤 일련의 과정이 일어나는지 탐구해보세요.

`@ComponentScan`은 스프링 프레임워크가 **어노테이션이 붙은 클래스들을 자동으로 탐색하고 등록**하도록 합니다.

### 1) **@ComponentScan 어노테이션 설정**

- 스프링에서 `@ComponentScan`을 사용해 **특정 패키지를 지정**하면, 해당 패키지와 하위 패키지에서 **빈으로 등록될 클래스들을 자동으로 스캔**합니다.

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```

- `basePackages` 속성을 통해 탐색할 패키지를 지정하며, 이 패키지 내에 있는 `@Component`, `@Service`, `@Repository`, `@Controller` 어노테이션이 붙은 클래스들을 자동으로 스캔합니다.

### 2) **클래스 스캐닝 (ClassPathScanningCandidateComponentProvider)**

- `@ComponentScan`이 선언된 패키지를 기준으로, 스프링은 `ClassPathScanningCandidateComponentProvider`를 사용하여 클래스 패스에서 빈으로 등록될 **모든 후보 클래스**를 스캔합니다.
- 이때 `@Component`, `@Service`, `@Repository`, `@Controller` 어노테이션이 붙은 클래스들이 빈 등록 후보로 선정됩니다.

### 3) **빈 후보 선택 및 등록 (BeanDefinition 생성)**

- 스캔된 클래스들은 빈으로 등록되기 전에 **BeanDefinition** 객체로 변환됩니다.
    - `BeanDefinition`은 해당 클래스의 메타데이터(클래스 정보, 의존성 정보 등)를 포함한 객체입니다.
- 스프링 컨테이너는 이 `BeanDefinition`을 기반으로 빈을 생성하고 관리합니다.

### 4) **빈 생성과 의존성 주입**

- 스프링이 BeanDefinition을 기반으로 빈을 생성하고, 필요한 의존성을 주입합니다. 주입 방식은 **생성자 주입**, **세터 주입**, **필드 주입** 중 하나가 사용될 수 있습니다.

### 5) **빈 관리**

- 생성된 빈은 스프링 컨테이너에서 관리됩니다. 빈의 생명 주기와 스코프(싱글톤, 프로토타입 등)에 따라 스프링 컨테이너가 빈을 관리하며, 애플리케이션에서 빈이 필요할 때마다 이를 제공합니다.

### `@ComponentScan` 과 같은 어노테이션을 사용하여 스프링이 컴포넌트를 어떻게 탐색하고 찾는지의 과정을 깊게 파헤쳐보세요.

`@ComponentScan`은 스프링 프레임워크가 **어노테이션이 붙은 클래스들을 자동으로 탐색하고 등록**하도록 합니다. 이 과정은 다음과 같이 이루어집니다:

### 1) **@ComponentScan 어노테이션 설정**

- 스프링에서 `@ComponentScan`을 사용해 **특정 패키지를 지정**하면, 해당 패키지와 하위 패키지에서 **빈으로 등록될 클래스들을 자동으로 스캔**합니다.

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig {
}
```

- `basePackages` 속성을 통해 탐색할 패키지를 지정하며, 이 패키지 내에 있는 `@Component`, `@Service`, `@Repository`, `@Controller` 어노테이션이 붙은 클래스들을 자동으로 스캔합니다.

### 2) **클래스 스캐닝 (ClassPathScanningCandidateComponentProvider)**

- `@ComponentScan`이 선언된 패키지를 기준으로, 스프링은 `ClassPathScanningCandidateComponentProvider`를 사용하여 클래스 패스에서 빈으로 등록될 **모든 후보 클래스**를 스캔합니다.
- 이때 `@Component`, `@Service`, `@Repository`, `@Controller` 어노테이션이 붙은 클래스들이 빈 등록 후보로 선정됩니다.

### 3) **빈 후보 선택 및 등록 (BeanDefinition 생성)**

- 스캔된 클래스들은 빈으로 등록되기 전에 **BeanDefinition** 객체로 변환됩니다.
    - `BeanDefinition`은 해당 클래스의 메타데이터(클래스 정보, 의존성 정보 등)를 포함한 객체입니다.
- 스프링 컨테이너는 이 `BeanDefinition`을 기반으로 빈을 생성하고 관리합니다.

### 4) **빈 생성과 의존성 주입**

- 스프링이 BeanDefinition을 기반으로 빈을 생성하고, 필요한 의존성을 주입합니다. 주입 방식은 **생성자 주입**, **세터 주입**, **필드 주입** 중 하나가 사용될 수 있습니다.

### 5) **빈 관리**

- 생성된 빈은 스프링 컨테이너에서 관리됩니다. 빈의 생명 주기와 스코프(싱글톤, 프로토타입 등)에 따라 스프링 컨테이너가 빈을 관리하며, 애플리케이션에서 빈이 필요할 때마다 이를 제공합니다.

### 5️⃣ **단위 테스트와 통합 테스트 탐구**

### 단위 테스트(Unit Test)

**단위 테스트**는 소프트웨어의 개별 구성 요소 또는 "단위"를 테스트하는 것을 말함. 이 단위는 일반적으로 함수, 메서드, 또는 클래스와 같은 작은 코드 조각이다. 

주요목적: 코드의 특정부분이 의도한 대로 작동하는지 검증

### 단위 테스트의 특징

- **독립성**: 외부 종속성을 최소화하여 코드 단위 자체만을 테스트합니다.
- **빠른 실행**: 테스트는 일반적으로 빠르게 실행됩니다.
- **화이트박스 테스트**: 소프트웨어 내부 구조나 구현 방법을 고려하여 테스트합니다.

### 통합 테스트(Integration Test)

**통합 테스트**는 여러 단위가 결합되어 상호 작용할 때, 시스템이 올바르게 동작하는지 확인하는 테스트이다. 

단위 테스트가 개별 구성 요소를 테스트하는 반면, 통합 테스트는 이들이 결합되었을 떄 발생할 수 있는 문제를 발견하는데 목적이 있음

### 통합 테스트의 특징

- **상호작용 테스트**: 여러 모듈 간의 상호작용을 테스트합니다.
- **복잡성**: 단위 테스트보다 더 복잡하고 시간이 더 걸릴 수 있습니다.
- **외부 시스템 포함**: 데이터베이스, 네트워크, 파일 시스템 등 외부 시스템과의 상호작용을 포함할 수 있습니다.

### 단위 테스트와 통합 테스트의 차이점

- **범위**: 단위 테스트는 개별 코드 단위를 테스트하는 반면, 통합 테스트는 여러 단위가 결합된 시스템을 테스트합니다.
- **목적**: 단위 테스트는 코드의 특정 부분이 올바르게 작동하는지 확인하는 데 중점을 두고, 통합 테스트는 시스템 전체가 올바르게 동작하는지 확인합니다.
- **복잡성**: 단위 테스트는 상대적으로 간단하고 빠르게 실행되지만, 통합 테스트는 더 복잡하고 시간이 걸릴 수 있습니다.