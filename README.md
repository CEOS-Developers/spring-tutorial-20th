# spring-tutorial-20th
CEOS 20th BE Study - Spring Tutorial

## 2️⃣ Spring이 지원하는 기술들(IoC/DI, AOP, PSA 등)을 자유롭게 조사해요

---

### 🌿Spring

: 자바 엔터프라이즈 개발을 편하게 해주는 오픈소스 경량급 애플리케이션 프레임워크

**[스프링 정의로 보는 장점]**

> 엔터프라이즈 개발 ➡️ 비즈니스 로직 구현
> 
> 
> 오픈 소스 ➡️ 안정적 개발과 개선이 보장된 오픈소스
> 
> 경량급 ➡️ 개발자가 작성할 코드가 단순해 코드 복잡성을 낮춤
> 
> 애플리케이션 프레임워크 ➡️ 애플리케이션의 뼈대를 제공받아 빠르게 개발 가능!
> 

### POJO

: Plain Old Java Object → 오래된 방식의 순수한 자바 객체

➡️ **스프링은 POJO 프로그래밍을 지향**함.

```java
public class User {
	private String name;
	private int level;
	
	public Stirng getName() {
		return name;
	}
	
	public String getLevel() {
		return level;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
}
```

이렇게 Getter, Setter로 구성된 가장 순수한 형태의 기본 클래스

⇒ *객체지향적인 원리에 충실하면서, 환경과 기술에 종속되지 않고, 필요에 따라 재활용될 수 있는 방식으로 설계된 오브젝트*

왜냐하면, 순수 Java가 아닌 외부 기술을 사용하는 경우 → 기술 변경시, 해당 기술을 사용하고 있는 모든 객체 코드 변경 필요

but! 순수 Java만을 사용하는 경우 → **기술 혹은 환경에 종속되지 않기에, 유연하게 변화와 확장에 대처 가능 + 객체지향 설계 적용, 코드 단순으로 테스트와 디버깅 쉬워짐**

<aside>
🌱

**[스프링이 POJO 프로그래밍을 위해 지원하는 기술]**

**IoC/DI, AOP, PSA**

</aside>

### IoC/DI

**[IoC(Inversion of Control; 제어의 역전)]**

: 객체들의 제어를 스프링 프레임워크에 맡기는 것 → 설정 파일을 통해 객체의 생명주기, 클래스 등 을 프레임워크가 직접 제어

- 클래스 내에서 직접 객체를 생성하는 것이 아닌, 어디선가 받아온 객체를 할당
    
    ```java
    public class A {
    	//b = new B(); new 키워드로 객체 생성
    	private B b; // 어디선가 받아온 객체를 b에 할
    }
    ```
    
- IoC 컨테이너
    
    : spring은 IoC용 컨테이너를 제공해 줌. 빈(bean)을 만들고 엮어주며 제공해주는 역할
    
    → 스프링 프레임워크로 객체를 생성, 관리 및 의존성 관리
    
- IoC의 분류
    
    DL: 저장소에 저장되어 있는 Bean에 접근하기 위해 컨테이너가 제공하는 API를 이용해 Bean을 lockup
    
    DI: 각 클래스간의 의존관계를 빈 설정 정보를 바탕으로 컨테이너가 자동으로 연결
    

**[DI(Dependency Injection; 의존성 주입)]**

: 서버 컨테이너 기술, 디자인 패턴, 객체 간의 의존 관계를 느슨하게 해줌

```java
public class OrderController {
	public static void main(String[] args) {
		OrderService orderService = new OrderService();
		List<Order> orderList = orderService.getOrderList();
	}
}

public class OrderService {
	public List<Order> getOrderList() {
		return null;
	}
}

// OrderController가 OrderService에 의존
```

OrderController가 클라이언트의 요청을 받는 엔드포인트 역할

※ 엔드포인트: 클라이언트가 서버의 자원을 이용하기 위한 끝 지점

OrderService가 클라이언트의 요청을 처리하는 역할

**의존성 주인이 이뤄진다면,**

```java
public class Customer {
		public static void main(String[] args){
			OrderService orderService = new OrderService();
			OrderController controller = new OrderControlloer(orderService); //의존성 주입
			List <Order> orderList = controller.getOrders();
		}
	}

public class OrderController {
	private OrderService = orderService ;
	
	// 생성자로 객체를 입력받음
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public List<Order> getOrders() {
		return orderService.getOrderList();
	}
}

public class OrderService {
	public List<Order> getOrderList() {
		return null;
	}
}
```

- 의존관계에서 클래스 변경은 해당 클래스가 사용된 모든 곳에서 수정 필요 → 효율적이지 않고 버그 발생 확률 높아짐

### AOP

: Aspect-Oriented Programing (관점 지향 프로그래밍)

➡️ 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 모듈화

**[공통 관심 사항 & 핵심 관심 사항]**

- 공통 관심 사항: 모든 핵심 관심 사항에 공통적으로 적용되는 관심 사항
- 핵심 관심 사항: 애플리케이션의 핵심 기능과 관련된 관심 사항

⇒ 핵심 관심 사항과 공통 관심 사항이 모여 있으면 코드 중복 발생!

즉, 공통 관심 사항 수행 로직 변경 시 중복 코드의 수정 필요

기능들을 별도의 객체로 분리 필요

### PSA

: Portable Service Abstraction, 일관성 있는 서비스 추상화

➡️ 기존 작성된 코드 수정 없이 확장 가능 + 어느 특정 기술에 특화 X

![image](https://github.com/user-attachments/assets/743374a9-c713-4fca-86f7-1e44dc0b30e2)


```java
public class DbClient {
    public static void main(String[] args) {
        JdbcConnector connector = new SQLiteJdbcConnector();

        DataProcessor processor = new DataProcessor(connector);
        processor.insert();
    }
}

public class DataProcessor {
    private Connection connection;

    public DataProcessor(JdbcConnector connector) {
        this.connection = connector.getConnection();
    }

    public void insert() {
        System.out.println("inserted data");
    }
}

public interface JdbcConnector {
    Connection getConnection();
}

public class MariaDBJdbcConnector implements JdbcConnector {
    @Override
    public Connection getConnection() {
        return null;
    }
}

public class OracleJdbcConnector implements JdbcConnector {
    @Override
    public Connection getConnection() {
        return null;
    }
}

public class SQLiteJdbcConnector implements JdbcConnector {
    @Override
    public Connection getConnection() {
        return null;
    }
}
```

**[DB를 변경하게 될 경우]**

스프링이 데이터베이스 서비스를 추상화한 인터페이스인 JDBC를 제공하기에 동일 사용 방법을 유지한 채 데이터베이스 변경 가능

➡️ 데이터베이스에 접근하는 드라이버에 해당하는 Java 코드의 클래스가 JDBC 구현하는 것을 통해 기존 작성한 데이터베이스 접근 로직을 그대로 작성

## 3️⃣ Spring Bean이 무엇이고, Bean의 라이프 사이클은 어떻게 되는지 조사해요

---

### Bean

: 스프링 컨테이너에 의해 관리되는 재사용 가능한 소프트웨어 컴포넌트

➡️ 스프링 컨테이너가 관리하는 자바 객체 (인스턴스화 된 객체)

> new 키워드 대신 사용
> 

**[Spring Bean 사용 이유]**

: 스프링 간 객체가 의존관계를 관리
의존 관계 등록 시, 스프링 컨테이너에서 해당하는 빈을 찾고, 그 빈과 의존성 생성

**[Bean 등록 방법]**

: 빈은 클래스의 등록 정보, Getter/Setter 메서드 포함, 컨테이너에 사용되는 설정 메타데이터로 생성

1. **@Component(@Controller, @Service, @Repository)로 스프링 빈 자동 등록**
    
    ➡️ @Controller, @Service, @Repository 어노테이션은 @Component를 포함
    
    ```java
    package hello.hellospring.controller;
    
    import hello.hellospring.service.MemberService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Controller;
    
    @Controller
    public class MemberController {
    
        private final MemberService memberService;
    
    	@Autowired
        public MemberController(MemberService memberService) {
            this.memberService = memberService;
        }
    }
    
    @Service
    public class MemberService {
    
    	private final MemberRepository memberRepository;
    
    	@Autowired
    	public MemberService(MemberRepository memberRepository) {
    		this.memberRepository = memberRepository;
    	}
    }
    
    @Repository
    public class MemoryMemberRepository implements MemberRepository {}
    ```
    
    ※ @Autowired
    
    : @AutoWired를 통해 스프링이 연관된 객체를 스프링 컨테이너에 찾아서 넣어줌 (의존성 주입)
    
    ※ 스프링 빈 등록 시, 싱글톤으로 등록
    
    싱글톤 패턴: 특정 클래스가 단 하나만의 인스턴스를 생성하여 사용하기 위한 패턴 → 생성자 여러 번 호출해도 인스턴스가 하나만 존재하도록 보장하여 애플리케이션에서 동일한 객체 인스턴스에 접
    
    특별한 경우가 아니면 대부분 싱글톤 사
    
2. **@Configuration과 @Bean으로 스프링 등록**
    
    ```java
    package hello.hellospring.service;
    
    import hello.hellospring.repository.MemberRepository;
    import hello.hellospring.repository.MemoryMemberRepository;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    
    @Configuration
    public class SpringConfig {
    
        @Bean
        public  MemberService memberService() {
            return new MemberService(memberRepository());
        }
    
        @Bean
        public MemberRepository memberRepository() {
            return new MemoryMemberRepository();
        }
    }
    ```
    

+) xml로도 구현이 가능하지만 요즘은 사용하지 않음

### Bean의 라이프사이클

스프링 IoC 컨테이너 생성 ➡️ 스프링 빈 생성 ➡️ 의존관계 주입 ➡️ 초기화 콜백 메소드 호출 ➡️ 사용 ➡️ 소멸 전 콜백 메소드 호출 ➡️ 스프링 종료

- 의존관게 주입이 완료되면 스프링 빈에게 콜백 메소드를 통해 초기화 시점 알려줌
- 스프링 컨테이너가 종료되기 직전에 소멸 콜백 메소드를 통해 종료 시점 알려줌

<aside>
❓

생성자 주입을 통해 빈 생성부터 초기화 콜백까지 동시에 일어나지 않는 이유

: 초기화는 생성된 값을 활용해 외부 커넥션을 연결하는 등 무거운 동작 수행

따라서, 생성자 안에서 무거운 초기화 작업을 함께 하는 것보다 객체 생성과 초기화 부분을 명확하게 나누는 것이 유지보수 관점에서 좋음

경우에 따라, 초기화 작업이 내부 값의 약간 변경 수준이라면 한번에 처리하는 것이 나을 수 있음

</aside>

**[Bean 콜백 3가지]**

1. 인터페이스
    
    인터페이스에 의존적 방법 ➡️ 초기화, 소멸 메소드명 변경이 어려우며, 코드 커스터마이징을 할 수 없는 외부 라이브러리에 적용 불가능
    
    거의 사용하지 않는 초창기 방법
    
    ```java
    public class ExampleBean implements InitializingBean, DisposableBean {
     
        @Override
        public void afterPropertiesSet() throws Exception {
            // 초기화 콜백 (의존관계 주입이 끝나면 호출)
        }
     
        @Override
        public void destroy() throws Exception {
            // 소멸 전 콜백 (Bean 종료 전 메모리 반납, 연결 종료와 같은 과정)
        }
    }
    ```
    
2. 설정 정보에 초기화 메소드, 종료 메소드 지정
    
    인터페이서의 단점 개선
    그러나, Bean 지정 시 initMethod와 destoryMethod 직접 지정 필요
    
    ```java
    public class ExampleBean {
     
        public void initialize() throws Exception {
            // 초기화 콜백 (의존관계 주입이 끝나면 호출)
        }
     
        public void close() throws Exception {
            // 소멸 전 콜백 (메모리 반납, 연결 종료와 같은 과정)
        }
    }
     
    @Configuration
    class LifeCycleConfig {
     
        @Bean(initMethod = "initialize", destroyMethod = "close") // 직접 지정
        public ExampleBean exampleBean() {
            // 생략
        }
    }
    ```
    
3. @PostConstruct, @PreDestory 어노테이션
최신 스프링에서 가장 권장하는 방법 & 자바 표준이기에 스프링이 아닌 다른 컨테이너에서도 동작 & 컴포넌트 스캔과 잘 어림
그러나, 커스터마이징이 불가능한 외부 라이브러리에 적용 불능
    
    ```java
    import javax.annotation.PostConstruct;
    import javax.annotation.PreDestroy;
     
    public class ExampleBean {
     
        @PostConstruct
        public void initialize() throws Exception {
            // 초기화 콜백 (의존관계 주입이 끝나면 호출)
        }
     
        @PreDestroy
        public void close() throws Exception {
            // 소멸 전 콜백 (메모리 반납, 연결 종료와 같은 과정)
        }
    }
    
    ```
    

## 4️⃣ 스프링 어노테이션을 심층 분석해요

---

### Annotation

: 프로그램에게 유용한 정보를 제공하기 위해 사용되는 것 (메타 데이터)

**[역할]**

- 컴파일러에게 문법 에러를 체크하도록 정보 제공
- 프로그램 빌드할 때 코드 자동 생성할 수 있도록 정보 제공
- 런타임에 특정 기능 실행하도록 정보 제공

**[종류]**

- 표준 어노테이션: 자바에서 기본적으로 제공
- 메타 어노테이션: 어노테이션 정의에 사용
- 사용자 어노테이션: 사용자가 직접 정의

**[사용 순서]**

1. 어노테이션 정의
2. 클래스에 어노테이션 배치
3. 코드가 실행되는 중 Reflection을 이용해 추가 정보 획득 후 기능 실시

### Reflection

: 프로그램이 실행 중 자신의 구조와 동작을 검사하고 조사하고 수정하는 것

➡️ 클래스 정보를 클래스 로더를 통해 읽어와 해당 정보를 JVM 메모리에 저장

**[(커스텀)어노테이션의 구성]**

```java
@Target({ElementType.[적용대상]})
@Retention(RetentionPolicy.[정보유지되는 대상])
public @interface [어노테이션명]{
	public 타입 elementName() [default 값]
    ...
}
```

- @Target: 어노테이션 적용 위치 선택
- @Retention: 컴파일러가 어노테이션을 다루는 방법 기술 (어느 시점까지 영향 미칠지 결정)
- @interface: Annotation 클래스를 상속하며 내부 메소들은 abstract 키워드가 붙음
    
    **[제약]**
    
    - 어노테이션 인터페이스는 extends 절을 가질 수 없다.
    - 어노테이션 타입 선언은 제네릭일 수 없다.
    - 메소드는 매개변수를 가질 수 없다.
    - 메소드는 타입 매개변수를 가질 수 없다.
    - 메소드 선언은 throws 절을 가질 수 없다.

### Bean

: Spring Container에 Bean을 등록하도록 하는 메타데이터를 기입하는 어노테이션

➡️ 개발자가 직접 제어가 불가능한 외부 라이브러리 등을 Bean으로 만들어 할 때 사용

```java
@Configuration
public class ApplicationConfig {
	@Bean
	public ArrayList<String> array() {
		return new ArrayList<String>();
	}
}
```

별도로 해당하는 라이브러리, 객체를 반환하는 Method를 만들고 @Bean 등록

※ Bean 어노테이션에 값 지정 없으므로 Mthod 이름을 CamelCase로 변경한 것이 Bean id로 등록

**[@Configuration]**

: @Bean이 정의되어있는 클래스에서 사용되는 어노테이션으로, 스프링은 @Configuration이 선언된 클래스에서 @Bean을 찾아 빈 등록

등록하게 되면 해당 빈이라는 객체를 필요로하는 곳에서 자유롭게 사용 가

### ComponentScan

: @Component, @Service, @Repository, @Controller, @Configuration 어노테이션이 붙은 클래스 Bean들을 찾아 Context에 bean 등록 해주는 어노테이션

**[component sacn 시작 지점 설정 속성]**

- basePackages(): scan을 시작할 패키지를 문자열로 지정
- basePackageClasses(): 스캔을 시작할 클래스 타입 지정

➡️ @SpringBootApplication 내부에서 ComponentScan의 기본 패키지에 대해 설정되어 있기에 직접적으로 사용하지 않아도 작동 가

## 5️⃣ 단위 테스트와 통합 테스트 탐구

---

### 단위 테스트

: 응용 프로그램에서 테스트 가능한 가장 작은 소프트웨어를 실행하여 예상대로 동작하는지 확인하는 테스트

일반적으로 클래스 혹은 메소드 수준

➡️ 단위 테스트를 활용해 동작 표현이 쉬워짐

![image](https://github.com/user-attachments/assets/09f5eacb-1eb1-485b-8774-cb826a4e10e5)


- SUT: System Under Test (테스트 대상) ➡️ 다른 객체와 협력 관계 맺음(의존 관계)
    - 협력 관계의 객체와 묶어서 테스트를 진행하지 않음!
    협력 관계 객체는 Test Double로 대체
        
        ※ Test Double: SUT의 의존 구성요소를 사용할 수 없을 때 테스트 대상 코드와 상호작용하는 가짜 객체 (Stub, Mock, Object)
        
- Mocked Dependency: Test Doubld이자 Mock Object
- Method: Method under test ➡️ SUT 내부에 존재하는 기능
- GIVEN: 단위 테스트 시 주어진 특정한 상황. 테스트 시의 전재 조건들을 만들어둔 것
STU에 Mock 객체 전달
- WHEN: 특정 행위를 호출할 때
- THEN: WHEN의 호출 결과를 확인하는 것

**[JUnit]**

```java
@DisplayName("자동차가 전진한다")
@Test
public void moveCar() {
    // given
    Car car = new Car("dani");

    // when
    car.move(4);

    // then
    assertThat(car.getPosition()).isEqualTo(1);
}

@DisplayName("자동차가 멈춘다")
@Test
public void stopCar() {
    // given
    Car car = new Car("dani");

    // when
    car.move(3);

    // then
    assertThat(car.getPosition()).isEqualTo(0);
}
```

### 통합 테스트

: 단위 테스트보다 더 큰 동작을 달성하기 위해 여러 모듈들을 모아 이들이 의도대로 협력하는지 확인하는 테스트

개발자가 변경할 수 없는 부분(ex. 외부 라이브러리)까지 묶어 검증할 때 사용

➡️ 단위 테스트에서 발견하기 어려운 버그를 찾을 수 있음

그러나, 많은 코드 테스트로 인해 신뢰성이 떨어질 수 있고, 에러 발생 위치 확인이 어려워 유지 보수가 어려움

**[SpringBootTest]**

```java
@SpringBootTest
class SubwayApplicationTests {
    @Test
    void contextLoads() {
    }
}
```

- @SpringBootTest 어노테이션을 통해 스프링 부트 애플리케이션 테스트에 필요한 거의 모든 의존성 제공
- **옵션**
    - **properties**
        - {key=value} 형식으로 추가
            
            ```java
            @SpringBootTest(properties = {"name=Jimin"})
            public class SampleSpringBootTest {
                @Value("${name}")
                private String name;
            
                @Test
                public void testName(){
                    assertThat(name).isEqualTo("Jimin");
                }
            }
            ```
            
        - spring.config.location으로 설정하고 외부 파일 설정
            
            ```java
            Jimin:
            	name: Jimin
            	age: 24
            	
            @SpringBootTest(properties = {"spring.config.location = classpath:test.yml"})
            public class SampleSpringBootTest {
                @Value("${Jimin.age}")
                private int age;
            
                @Test
                public void testAge(){
                    assertThat(age).isEqualTo(24);
                }
            }
            ```
            
    - **webEnbironment**
        
        : 웹 테스트 환경 구성 가능
        
        - Mock: 실제 객체를 만들기 어렵거나 의존성이 길게 걸쳐진 경우 가짜 객체를 만들어 사용 (기본값)
            
            MockMvc를 사용한 테스트 진행 가능 (MockMvc는 브라우저에서 요청과 응답을 의미하는 객체)
            
        - RANDOM_PORT: 실제 서블릿 환경 구성 (TestRestTemplate 사용)
        - DEFINED_PORT: 실제 서블릿 환경을 구성하지만 포트는 애플리케이션 프로퍼티에서 지정한 포트 지정 (TestRestTemplate 사용)
            
            <aside>
            ❓
            
            **TestRestTemplate란?**
            
            통합테스트에 적합한 RestTemplate의 대안
            
            MockMvc는 서블릿 컨테이너를 만들지 않는 반면 TestRestTemplate은 서블릿 컨테이너 사용 ➡️ 마치 실제 서버가 동작하는 것처럼 테스트 수행 가능
            
            MockMvc는 서버 입장에서 구현한 API를 통해 비즈니스 로직이 문제없이 수행되는지 테스트
            
            TestRestTemplate는 클라이언트 입장에서 테스트 수행 가능
            
            </aside>
            
            <aside>
            ❓
            
            **서블릿?**
            
            동적 웹 페이지를 만들 때 사용되는 자바 기반의 웹 애플리케이션 프로그래밍 기술
            
            ![image](https://github.com/user-attachments/assets/4372574c-c653-4603-a5e7-eeb957eee5f3)

            
            </aside>
            
        - NONE: 기본적인 ApplicationContext 불러옴

## 6️⃣ 마치며…

---

스프링을 처음 공부하는거라 기본을 탄탄하게 공부하고 싶어 이것 저것 찾아보다 보니 방대한 양의 정보를 빠르게 학습하게 된 것 같아서 혼자 정리하는 시간이 필요할 거 같은데 이렇게 개념을 정리하다보니 스프리의 기본을 어느정도는 이해하게 된 것 같다. 다양한 사람들의 기술 블로그를 읽으며 쉽게 이해가 되지 않았던 내용은 정리를 하지 못했는데, 다음 스터디 전까지는 그 내용도 어느정도 이해를 해서 공부했던 내용을 더 정확하게 파악해야겠다!

공부를 하고 스프링 튜토리얼을 다시 보니 좀 더 코드가 이해하기가 쉬웠던 거 같다. 코드도 객체 지향에 적합한 읽기 좋은 코드로 수정할 수 있을 거 같아서 살짝 아쉬움이 남는다. 다음 과제는 조금 더 꼼꼼하게 진행해 내가 할 수 있는 최선의 결과를 내보고 싶다.
