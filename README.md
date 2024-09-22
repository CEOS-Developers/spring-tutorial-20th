# spring-tutorial-20th
CEOS 20th BE Study - Spring Tutorial

## Spring Concept

- IoC (Inversion of Control)
  
  - 객체의 생성과 관리를 개발자가 하는 것이 아니라 프레임워크가 대신하는 것
  - 자바 코드를 작성해 객체를 생성할 때, 객체가 필요한 곳에서 직접 생성했었음
    ```java
    public class A {
	b = new B(); //클래스 A에서 new 키워드로 클래스 B의 객체를 생성
    }
    ```
  - 제어의 역전은 외부에서 관리하는 객체를 가져와 사용하는 것
    ```java
    pubic class A {
	private B b; //코드에서 객체를 생성하지 않음. 어디선가 받아온 객체를 b에 할당
    }
    ```
    
- DI (Dependency Injection)
    
    - 제어의 역전을 구현하기 위해 사용하는 방법
    - 어떤 클래스가 다른 클래스에 의존한다는 뜻
    - 외부에서 객체를 주입받아 사용하는 것
  ```java
  public class A {
	//A에서 B를 주입받음
	@Autowired
	B b; //B b; 라고 선언했을 뿐 직접 객체를 생성하지 않음. 즉, 객체를 주입받고 있음
  }
  ```
  - 스프링 컨테이너에서 객체를 주입한 것. 즉, 스프링 컨테이너가 B 객체를 만들어서 클래스 A에게 준 것
 


- 스프링 컨테이너
  - 스프링 컨테이너는 빈을 생성하고 관리
  - 즉, 빈이 생성되고 소멸되기까지 생명주기를 이 스프링 컨테이너가 관리하는 것


- 빈
  - 스프링 컨테이너가 생성하고 관리하는 객체 
  - 위 코드 중 B가 빈
  - 스프링은 빈을 스프링 컨테이너에 등록하기 위한 여러 방법이 있음
  - e.g. MyBean이라는 클래스에 `@Component` 애너테이션을 붙이면 MyBean 클래스가 빈으로 등록됨 
    
    → 이후 스프링 컨테이너에서 이 클래스를 관리함. 이때 빈의 이름은 클래스명의 첫 글자를 소문자로 바꿔 관리
  ```java
  @Component
  public class MyBean{
  }
  ```

- 관점 지향 프로그래밍 (AOP, Aspect Oriented Programming)
  - 프로그램에 대한 관점을 핵심 관점, 부가 관점으로 나누어서 관심 기준으로 모듈화하는 것
 
  - 부가 관점에 해당하는 로직을 모듈화해 개발할 수 있게 해줌

    → 프로그래머가 핵심 관점 코드에만 집중할 수 있게 될 뿐만 아니라 프로그램의 변경과 확장에도 대응 가능


- 이식 가능한 서비스 추상화 (PSA, Portable Service Abstraction)
  - 스프링에서 제공하는 다양한 기술들을 추상화해 개발자가 쉽게 사용하는 인터페이스
  - 어느 기술을 사용하던 일관된 방식으로 처리하도록 하는 것
  - e.g. WAS → 코드는 그대로 두고 WAS를 톰캣이 아닌 언더토우, 네티와 같은 다른 곳에서 실행해도 기존 코드를 그대로 사용할 수 있으니까


## Bean의 라이프사이클

1. bean 인스턴스화 (instantiation)

   spring 컨테이너가 설정 파일(XML, Java config 등) 또는 어노테이션을 기반으로 필요한 bean 생성
2. 의존성 주입 (Dependency Injection)

   bean의 속성 값을 설정하거나, 다른 bean을 주입
3. 초기화 단계 (Initialization)

   bean이 생성되고, 의존성이 주입된 후 bean이 사용될 준비를 하기 위한 초기화 작업이 진행됨
   초기화는 2가지 방식으로 실행됨:
   - initializingBean 인터페이스의 afterPropertiesSet() 메서드 구현
   - XML이나 Java Config에서 init-method 속성을 사용하거나 @PostConstruct 어노테이션을 사용하여 초기화 메서드 정의
4. Bean 사용 (Bean Usage)

   bean이 초기화된 후, 실제 애플리케이션 로직에서 이 bean을 사용하게 됨
   bean이 애플리케이션의 서비스나 비즈니스 로직을 수행하는 데 필요한 작업을 처리
5. 소멸 단계 (Destruction)

   애플리케이션 컨텍스트가 종료되거나 bean이 더 이상 필요하지 않을 때, bean 소멸
   소멸은 2가지 방식으로 실행됨:
   - DisposableBean 인터페이스의 destroy() 메서드 구현
   - XML이나 Java Config에서 destroy-method 속성을 사용하거나 @PreDestroy 어노테이션을 사용하여 소멸 메서드 정의

  
## 스프링 어노테이션

### 어노테이션이란?
- 자바에서 제공하는 메타데이터의 일종으로, 코드에 부가적인 정보를 제공하기 위해 사용됨
- 주로 컴파일 시간이나 런타임에 해석되어 특별한 동작을 유도하는 데 사용됨
- spring 프레임워크에서는 이러한 어노테이션을 적극적으로 활용해 bean을 등록하거나 의존성 주입을 관리
e.g.
```java
// 어노테이션 정의
@Retention(RetentionPolicy.RUNTIME)  // 런타임 시점에 어노테이션 유지
@Target(ElementType.METHOD)         // 메서드에만 적용
public @interface MyAnnotation {
    String value() default "default_value";
}

```
```java
// 어노테이션 정의
@Retention(RetentionPolicy.RUNTIME)  // 런타임 시점에 어노테이션 유지
@Target(ElementType.METHOD)         // 메서드에만 적용
public @interface MyAnnotation {
    String value() default "default_value";
}

```

### 어노테이션을 사용하여 스프링이 컴포넌트를 어떻게 탐색하고 찾을까?

1. 패키지 지정
   어노테이션이 스캔할 패키지를 지정할 수 있음. spring은 지정된 패키지 및 하위 패키지를 스캔하며너 어노테이션이 붙은 클래스를 찾음
2. 클래스 스캔
    스캔하는 동안 @Component, @Service, @Repository, @Controller 같은 어노테이션이 붙어 있는 클래스를 발견하면 이를 빈으로 등록할 준비를 함
3. bean 등록
   스캔한 결과로 빈으로 등록될 클래스가 있으면 Spring 컨테이너에 해당 클래스를 빈으로 등록


## 단위 테스트와 통합 테스트 탐구

소프트웨어 개발에서 코드의 품질을 유지하고 버그를 사전에 방지하는데 중요한 테스트 기법

### 단위 테스트
프로그램의 가장 작은 단위, 즉 개별 메서드나 클래스에 대해 수행하는 테스트
외부 시스템(e.g. 데이터베이스나 웹 서버)에 의존하지 않고 테스트하려는 코드만을 검증
- 주요 어노테이션: @Test, @BeforeEach, @AfterEach, @Mock, @InjectMocks

### 통합 테스트
여러 개의 모듈이 실제로 외부 시스템과 상호작용하는지를 확인하는 테스트
- 주요 어노테이션: @SpringBootTest, @Transactional, @TestRestTemplate, @MockBean
