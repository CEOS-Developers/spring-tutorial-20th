# spring-tutorial-20th

## 1️⃣ 스프링의 다양한 기술들

### 1. IoC : Inversion of Control (제어의 역전)

```java
public class Car {

    Tire tire;

    public Car() {
        tire = new KoreaTire();
    }

    public void introduceTire() {
        tire.introduce();
    }
}

interface Tire {
    void introduce();
}

class KoreaTire() {

    public void introduce() {
        System.out.println("나는 한국 타이어야");
    }
}

class AmericaTire() {

    public void introduce() {
        System.out.println("나는 미국 타이어야");
    }
}
```

위의 코드와 같이 Car 클래스는 Tire 클래스의 메서드를 사용하고 있다. 이를 **Car 클래스가 Tire 클래스를 의존**하고 있다고 말한다.

하지만 Car 클래스는 본인의 로직에 대한 책임뿐만 아니라 Tire 클래스의 구현에 대한 책임 또한 가지고 있는 것이다 (= 두 클래스의 결합도가 강하다)

⇒ Car 클래스는 Tire 클래스의 구현 책임을 지는 것이 부담스러워 제 3자에게 의존관계에 대한 책임을 위임하고자 한다. 이를 **IoC, 제어의 역전**이라고 말한다.

</br>

```java
public class Select() {
    
    public Car car() {
        return new Car();
    }

    public Tire tire() {
		    // return new KoreaTire();
        return new AmericaTire();
    }
}

public class Car {

    Tire tire;

    public Car() {
        tire = new Select().tire();
    }

    // ...이하 동일 ..
}
```

위와 같이 Select라는 클래스를 만들어 어떤 tire를 넘겨줄지에 대한 책임을 위임한다.

Car 클래스는 Select가 반환해준 tire만 사용하기 때문에, 추후에 KoreaTire를 사용하고 싶다고 해도 Car 클래스를 변경하는 것이 아니라 Select 클래스의 tire에 대한 return 부분만 수정해주면 된다.

이렇게 Car는 Tire의 **책임에 대한 분리**를 이루었다!

</br>

🚨 **하지만** **아직 Select 클래스는 Tire 에 대해서 책임을 분리하지 못 한거 아닌가요??**

**사실 스프링에서 제공하는 IoC 컨테이너는 Select 클래스가 담당하고 있는 의존성 관리와 객체 선택 기능을 자동으로 처리해준다.**

</br>

### IoC 컨테이너

객체의 생성과 의존성 주입, 관리, 소멸을 담당하는 중앙 관리 시스템이다.

- 빈(Bean) 관리
    - IoC 컨테이너는 애플리케이션에서 사용되는 객체(빈)를 생성하고 관리한다.
    - 개발자는 객체 생성과 관리에 대한 부분을 신경 쓰지 않아도 된다.
- 의존성 주입 (Dependency Injection)
    - IoC 컨테이너는 빈 간의 의존성을 관리하고 필요한 의존성을 주입한다.
    - ⇒ 객체 간의 결합도를 낮추고, 코드의 재사용성과 유지보수성을 향상시킨다.
- 라이프사이클 관리
    - IoC 컨테이너는 빈의 라이프사이클을 관리하며, 초기화와 소멸 시점에 콜백 메서드를 호출할 수 있다.
- 설정 관리
    - Spring IoC 컨테이너는 애플리케이션 설정을 관리하고, XML, Java 설정 클래스, 어노테이션 기반의 설정을 지원한다.

<img width="806" alt="스크린샷 2024-09-07 오후 4 19 58" src="https://github.com/user-attachments/assets/76e27756-1a6e-4feb-8975-5f59a97e3c52">

</br>

IoC 컨테이너는 **DI 패턴**을 통해 객체 간의 의존성을 관리하는데

그럼 이제 **DI 패턴**에 대해 알아보자!

</Br>

### 2. DI : Dependency Injection (의존성 주입)

객체간의 의존성을 외부에서 주입하는 것을 말하며, 구체적인 의존 오브젝트와 그것을 사용할 주체를 런타임 시에 연결해주는 작업을 말한다.


</br>
의존성을 주입하는 세가지 방법이 있다.

**1) 생성자 주입**

- 객체가 생성될 때 모든 의존성이 주입되어 불변성을 유지할 수 있다는 장점이 있다.

```java
@Component
public class Car {

    private Tire tire;
		
		@Autowired
    public Car(@Qualifier("koreaTire") Tire tire) {
        this.tire = tire;
    }
}
```

```java
@Component
@RequiredArgsConstructor
public class Car {

    private final Tire tire;
		
}
```

첫번째 코드와 두번째 코드는 모두 생성자 주입의 예시 코드인데 `@RequiredArgsConstructor` 어노테이션을 사용하면 final로 선언한 필드와 @NonNull 어노테이션이 붙은 필드에 대해서 생성자를 자동으로 만들어준다.

</br>

**2) Setter 주입**

- 선택적 의존성이 필요한 경우에 사용하기 좋고, 객체 생성 이후에도 의존성을 변경할 수 있는 유연성을 제공한다.
- 의존성이 주입되지 않은 상태로도 객체 생성이 가능하므로 의존성 주입이 강제되지 않는 다는 단점이 있다.

```java
public class Car {
    private Tire tire;

    // Setter 주입
    public void setTire(Tire tire) {
        this.tire = tire;
    }
}
```

```java
// ..외부 코드..
Tire tire = new KoreaTire();
Car car = new Car();
car.setTire(tire);    // setter 주입

```

</br>

**3) 필드 주입**

- 스프링이 직접 필드에 주입하는 방식이다.
- 의존성이 외부에 명확하게 드러나지 않기 때문에 객체의 의존성을 파악하기 힘들다는 단점이 있다.

```java
@Component
public class Car {

    @Autowired
    @Qualifier("koreaTire")
    private Tire tire;
}
```

`@Autowired` 어노테이션을 사용하여 필드 주입을 수행합니다. Car 객체가 생성될 때 tire는 자동으로 스프링 IoC 컨테이너에 의해 주입된다.

koreaTire, americaTire와 같이 같은 타입의 빈이 있을 때, 내가 원하는 빈을 주입하기 위해 `@Qualifier` 어노테이션을 사용하여 주입하고자 하는 빈의 이름을 명시해준다.

</br>

**💡** 세가지 방법 중 **생성자 주입**을 권장한다.

- **불변성 유지**
    - 생성자 주입은 객체가 생성될 때 모든 의존성이 반드시 제공된다.
    - 객체 생성 후 변경되지 않음을 보장하고, 객체의 불변성을 유지할 수 있다.
- **필수 의존성 주입 보장**
    - 객체가 생성될 때, 모든 의존성을 모두 받아야 하기 때문에 의존성을 빼먹을 가능성이 없다.
    - (setter 주입 같은 경우는 setter 메서드를 호출하지 않고도 객체 생성이 가능하여 의존성을 빼먹을 수 있다)
- **테스트 용이성**
    - 의존성을 주입받는 객체를 쉽게 모킹할 수 있다.

</br>

### 3. AOP : Aspect Oriented Programming (관점 지향 프로그래밍)

어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고 그 관점을 기준으로 각각 모듈화하겠다는 의미이다.

- 핵심 관심사 : 우리가 적용하고자 하는 핵심 비즈니스 로직
- 횡단 관심사 : 여러 모듈에 공통적으로 나타나는 로직

<img width="490" alt="스크린샷 2024-09-05 오후 4 31 26" src="https://github.com/user-attachments/assets/485585dc-7c09-4dad-be65-32d2e1b72d92">

위의 그림처럼 노란 블록, 빨간 블록, 파란 블록이 여러 클래스에서 사용된다.

이는 여러 클래스에서 중복되는 메소드, 필드, 코드 들이 나타난다는 뜻이다. 만약 클래스 A의 주황 블록을 수정한다면 클래스 B,C의 주황 블록도 같이 수정해줘야 될 것이다. → 유지 보수하기 좋지 않음

이처럼 코드를 짜다보면 다른 부분에 계속 **반복해서 쓰는 코드**를 볼 수 있는데 이를 ‘흩어진 관심사 (Crosscutting Concerns)’라고 부른다

</br>

AOP는 aspect를 사용하여 흩어진 관심사를 해결합니다.

→ 이런 흩어진 관심사를 Aspect로 모듈화하고 **핵심적인 비즈니스 로직에서 분리하여 재사용**하겠다는 것이 AOP의 취지이다

</br>

**AOP의 주요 키워드**

- Aspect : 여러 곳에서 쓰이는 공통 부분 코드를 모듈화한 것
- Target : Aspect가 적용되는 곳
- Advice : Aspect에서 실질적인 기능에 대한 구현체
- Joint Point: Advice가 Target에 적용되는 시점
- Point Cut : Joint Point의 상세 스펙을 정의한 것

</br>

그럼 이제 한번 예시 코드를 보며 이해해보자.

### 실행 시간 출력하기

```java
@Service
public class TodoService() {

    // todo 생성
	public void createTodo(TodoReq todoReq) {
		long begin = System.currentTimeMillis();
		try{
			todoRepository.save(todoReq);
		} finally {
			System.out.println(System.currentTimeMillis() - begin);
		}
	}

    // todo 삭제
    public void deleteTodo(Long id) {
        long begin = System.currentTimeMillis();
		try{
			todoRepository.deleteById(id);
		} finally {
			System.out.println(System.currentTimeMillis() - begin);
		}
    }
}
```

이렇게 todo를 생성, 삭제하는 코드에 시간을 측정하고 출력하는 코드가 중복이 된다.

지금은 메서드가 2개만 존재하는 상황이라 괜찮아 보이지만 만약 메서드가 엄청 많다면, 엄창나게 많은 중복 코드를 작성하게 될 것이다.

⇒ AOP를 사용하여 코드를 개선해보자!

</br>


**부가 기능을 분리할 Aspect 작성**

```java
@Aspect
@Component
public class Performance() {

    @Around("execution(* com.example.todo.TodoService.*(..))")
    public Object countTime(ProceedingJoinPoint joinPoint) throws Throwable {
		    // 메서드 실행 전 
        long begin = System.currentTimeMillis();
        
        // 메서드 실행
        Object proceed = joinPoint.proceed();
        
        // 메서드 실행 후
        System.out.println(System.currentTimeMillis() - begin);
        return proceed;
    }
}
```

- `@Aspect` 어노테이션을 사용하여 해당 클래스가 AOP의 Aspect임을 나타낸다.
  → `@Component` 어노테이션으로 Aspect 클래스를 빈으로 등록해 스프링 컨테이너로부터 의존성을 주입받아 사용한다 (AOP와 IoC의 관계)
- `@Around` 어노테이션을 사용하여 적용 범위를 설정한다
    - execution(* com.example.todo.TodoService.*(..)) : TodoService 클래스의 모든 메서드에 대해 Aspect를 적용한다
    - execution(* com.example.todo.TodoService.createTodo(..)) : createTodo 메서드에만 Aspect를 적용한다
- `ProceedingJoinPoint` 는 호출된 메서드를 감싸는 역할을 한다
    - proceed()를 호출하면 실제 비즈니스 로직이 실행된다

**이렇게 Aspect를 작성하면 비즈니스 로직과 부가 기능이 분리가 되므로 유지 보수성이 향상되고, 재사용성이 좋아진다.**

</br>

### 4. PSA : Portable Service Abstraction

환경의 변화와 관계없이 일관된 방식의 기술로의 접근 환경을 제공하는 추상화 구조이다.

우리는 JDBC Driver를 사용해 데이터베이스에 접근하지만 JDBC Driver가 어떻게 구현되어 있는지는 모른다.

이렇게 실제 구현부를 모르더라도 해당 서비스를 이용할 수 있도록 하는 것이 **서비스 추상화**이다.

</br>

그렇다면 **Portable한 서비스 추상화**는 뭘까?

PSA는 비즈니스 로직을 수정하지 않고 추상화 계층을 구현한 또 다른 서비스로 교체 가능한 것을 말한다.

```java
@Controller
public class MemberController {

		@GetMapping("/info")
		public void getInfo() {
				// ...
		}
}
```

```java
@Controller
public class MemberController {

		@RequestMapping(value="/info", method=RequestMethod.GET)
		public void getInfo() {
				// ...
		}
}
```

원하는 url로 매핑하고 싶을 때 @GetMapping, @RequestMapping(method=RequestMethod.GET) 둘 중 아무거나 사용해도 똑같은 기능을 한다.

비즈니스 로직을 수정하지 않고, http url 매핑이 추상화 되어있는 또 다른 서비스로 교체 가능하기 때문에 PSA라고 할 수 있다. (@GetMapping에서 @RequestMapping으로)

</br>

---

## 2️⃣ SpringBean 이란?

**Spring Bean 🫛**

: 스프링 IoC 컨테이너가 관리하는 자바 객체를 말한다

</br>

### SpringBean의 생명주기

빈이 생성되고 사용된 후 소멸되는 전체 과정을 의미한다

간단하게 말하면 “**객체 생성 → 의존관계 주입”** 의 라이프 사이클을 가진다.

</br>

**1) 스프링 컨테이너 생성**

```java
// 구성, 설정 정보
@Configuration
public class AppConfig {
	
	// ...
}

public class SpringContainerExample {

    public static void main(Stirng[] args) {

        // ApplicationContext(스프링 컨테이너) 생성
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    }
}
```

<img width="623" alt="스크린샷 2024-09-06 오후 1 30 15" src="https://github.com/user-attachments/assets/a1d61edd-2646-4ac3-93ce-60daf2da4cfa">

스프링 컨테이너인 ApplicationContext가 스프링 빈을 관리한다.

AnnotationConfigApplicationContext() 생성자를 호출해 스프링 컨테이너를 생성한다. 이때, 구성정보 클래스인 AppConfig.class를 파라미터로 전달해준다 (→ 스프링부트 없이 스프링 컨테이너를 생성할때 직접 config를 파라미터로 전달한다고 한다)

</br>

**2) 빈 등록**

```java
// AppConfig.class

@Bean
public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
}

@Bean
public MemberRepository memberRepository() {
    return new MemoryMemberRepository();
}
```

<img width="548" alt="스크린샷 2024-09-06 오후 1 32 24" src="https://github.com/user-attachments/assets/5c9621b3-0d7e-40ee-b083-bced0e11de8d">

전달받은 AppConfig를 사용해서 스프링 빈을 등록해줍니다.

기본적으로 메서드 이름이 빈의 이름으로 설정됩니다.

</br>

**3) 의존성 주입**

AppConfig 내용을 참고해 의존관계를 주입합니다. 실제로는 스프링 빈을 등록하면, 생성자를 호출하여 의존관계 주입이 일어난다고 합니다.

<img width="652" alt="스크린샷 2024-09-06 오후 1 34 37" src="https://github.com/user-attachments/assets/6b9dc5ab-8ae6-41ac-91f8-117391093031">

AppConfig를 보면 memberService 메서드에서 memberRepository를 사용하는걸 볼 수 있습니다. 이 말은 “memberService가 memberRepository를 의존한다”라고 이해할 수 있습니다.

</br>

**4) 초기화**

의존관계 주입이 완료되면, 스프링은 스프링 빈에게 콜백 메서드를 통해 초기화 시점을 알려준다.

여기서 말하는 초기화 작업이란? 주로 데이터베이스 연결, 리소스 초기화 등 객체가 사용되기 전에 필요한 작업 등을 의미한다.

```java
@PostConstruct
public void init() {
    System.out.println("빈 초기화");
    // .. 초기화 로직 ..
}
```

`@PostConstruct` 어노테이션을 사용하면 의존관계 주입 후 해당 메서드를 호출해준다.

</br>

**5) 빈 사용**

빈의 초기화까지 완료되면, 애플리케이션은 빈을 사용합니다.

빈은 요청을 처리하거나 다른 서비스와 상호작용 할 수 있습니다.

</br>

**6) 소멸**

스프링은 스프링 컨테이너가 종료되기 직전에 소멸 콜백을 준다.

애플리케이션이 종료되거나, 빈이 더 이상 필요 없을때 소멸됩니다.

```java
@PreDestroy
public void destroy() {
    System.out.println("빈 소멸");
    // .. 소멸 로직..
}
```

`@PreDestroy` 어노테이션을 사용하면 스프링 컨테이너가 종료되기 직전에 해당 메서드를 호출해준다.

</br>

**7) 컨테이너 종료**

애플리케이션이 완전히 종료되면, 스프링 컨테이너는 자신이 관리하는 모든 빈을 소멸시키고 리소스를 해제합니다.

</br>

---

## 3️⃣ Annotation 에 대해 알아보자

### Annotation 이란?

자바에서 코드 사이에 특별한 의미, 기능을 수행하도록 하는 기술을 말한다.

클래스, 메서드, 필드 등의 요소에 메타데이터를 추가하여 동작 방식을 정의한다. 어노테이션을 통해 스프링은 다양한 기능을 자동으로 처리할 수 있다.

</br>

**어노테이션 만들어보기**

```java
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {

		// ..구현 내용
}
```

- `@Target`
    - 어노테이션이 적용될 수 있는 요소의 타입을 정의한다.
    - ElementType.PARAMETER 를 사용하면 메서드 파라미터에서 해당 어노테이션을 사용할 수 있음을 의미한다
    - ElementType.TYPE : 클래스, 인터페이스, 열거형, 어노테이션 타입에 적용된다
    - ElementType.METHOD : 메서드에 적용된다
    - ElementType.FIELD : 클래스의 필드에 적용된다
- `@Retention`
    - 어노테이션의 유지 기간을 정의한다.
    - RetentionPolicy.RUNTIME 는 어노테이션이 runtime에도 유지 됨을 의미한다
      ⇒ 이 어노테이션은 실행 중에도 사용할 수 있으면 reflection을 통해 접근 할 수 있다.
    - RetentionPolicy.SOURCE : 컴파일 시점에만 유지
    - RetentionPolicy.CLASS : 컴파일 후, 클래스 파일에 포함되지만 런타임에는 유지되지 않음
- `@interface`
    - 자바에서 어노테이션을 정의할때 사용한다.

</br>

```java
@RestController
public class MyController {

    @GetMapping("/user")
    public String getUser(@Login String userId) {
        
        // .. 구현 내용
        return (userId);
    }
}
```

현재 @Login 이라는 사용자 정의 어노테이션을 정의했고,  파라미터에서 사용할 수 있다.

userId 라는 파라미터에 특수 처리를 수행할 수 있다.

</br>

### 어노테이션을 사용해 빈을 등록하는 방법

스프링 컨테이너에게 제어권을 넘기려면 객체를 bean 으로 등록해야 했다.

앞에서 말했듯이 어노테이션을 사용해 빈을 등록할 수 있는데, 더 자세하게 알아보자!

</br>

**1) @Configuration + @Bean**

이 방식은 주로 개발자가 직접 제어 불가능한 외부 라이브러리 등을 bean으로 등록할 때 사용한다.

```java
@Configuration
public class AppConfig {
    
    @Bean
    public ArrayList<String> array() {
    
	    return new ArrayList<String>();
    }
}
```

스프링 컨테이너는 @Configuration 어노테이션이 붙은 클래스를 자동으로 빈으로 등록해준다.

@Bean이 붙은 메서드는 자바 객체를 반환하고, 반환된 자바 객체는 스프링 컨테이너의 빈으로 등록된다.

위의 예시는 ArrayList와 같은 외부 라이브러리를 bean으로 등록하기 위해 ArrayList를 반환하는 메서드를 만들고 @Bean 어노테이션을 추가해줘 반환된 객체가 빈으로 등록될 수 있게 하였다.

→ 외부 라이브러리는 직접 수정할 수 없기 때문에 위와 같은 방법을 사용하는 것이다.

</br>

**2) @Component**

개발자가 직접 작성한 class를 bean으로 등록시키기 위한 방법이다.

@Component는 해당 클래스를 빈으로 등록시키겠다는 의미이다.

```java
@Component
public class Car {

    private final Tire tire;

    @Autowired
    public Car(Tire tire) {
        this.tire = tire;
    }
}
```

`@Autowired` 어노테이션을 사용하면 스프링에게 자동으로 의존성을 주입하라고 지시한다.

스프링 컨테이너는 Car 빈을 생성할 때, Tire 타입의 빈을 찾아와 생성자에 주입한다.

</br>

🚨 **그럼 스프링 컨테이너는 Car 클래스가 어디있는지 알고 찾아와 빈을 생성해주는 것일까??**

### @ComponentScan

**이 어노테이션은 @Component 어노테이션이 붙은 클래스들을 자동으로 bean 으로 등록해주는 역할을 한다**

그럼 어디부터 @Component 탐색을 시작하나요??
 ⇒ @ComponentScan 이 붙은 클래스의 패키지 경로를 기반으로 탐색을 시작하여, @Component 가 붙은 클래스를 찾으면 빈으로 등록시켜준다.

그런데 기존에 프로젝트를 할때 @ComponentScan 어노테이션을 쓴 기억이 없는데요??

```java
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
```

프로젝트의 root에 Application 클래스가 있는데, 이 클래스의 `@SpringBootApplication` 어노테이션 내부를 살펴보면 아래와 같이 구현되어 있다.

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {

	// 구현
}
```

내부에 @ComponentScan 어노테이션이 있는 것을 볼 수 있다.

프로젝트 root에 @SpringBootApplication 이 있고, 그 내부에 @ComponentScan이 있어 항상 프로젝트 root부터 하위 패키지까지 @Component를 탐색 할 수 있었던 것이다.

</br>

우리가 자주 사용했던 어노테이션을 살펴보자

**@Controller**

<img width="390" alt="스크린샷 2024-09-06 오후 5 17 14" src="https://github.com/user-attachments/assets/5564194d-df0f-4552-9bac-1cc92b85b34c">

**@Repository**

<img width="423" alt="스크린샷 2024-09-06 오후 5 17 56" src="https://github.com/user-attachments/assets/327d3e64-388b-4019-b746-e53b6f677b3c">

**@Service**

<img width="392" alt="스크린샷 2024-09-06 오후 5 18 37" src="https://github.com/user-attachments/assets/bff0e3fe-15e4-4394-9e75-6083f3e8e1e4">

**@Configuration**

<img width="333" alt="스크린샷 2024-09-06 오후 5 16 49" src="https://github.com/user-attachments/assets/6324446d-e614-4535-af9b-0080a26e61b6">

모두 내부에 @Component 어노테이션을 가지고 있다. 따라서 다들 컴포넌트 스캔의 대상이 되어 자동 빈 등록이 되었던 것이다.

</br>

---

## 4️⃣ 단위 테스트 VS 통합 테스트

### 1. 단위 테스트

애플리케이션의 가장 작은 단위인 클래스나 메서드의 동작을 테스트하는 것을 말한다.

스프링에서 주로 Mockito나 JUnit과 같은 라이브러리를 사용하여 단위 테스트를 작성한다.

**단위 테스트의 장점**

- 작은 부분을 독립적으로 테스트 하므로 테스트하는 시간과 비용을 절감할 수 있다.
- 새로운 기능 추가 또는 변경 사항이 생겼을 때 빠르게 테스트 할 수 있다.
- 리팩토링 시에 안정성을 확보할 수 있다.

</br>

간단한 예를 통해 단위 테스트 작성법을 알아보자

```java
// .. 서비스 클래스
public class CalculateService {

		public int add(int a, int b) {
				return (a + b);
		}
}
```

```java
// .. 단위 테스트

public class CalculateServiceTest {

    private CalculateService calculateService;

    @BeforeEach
    public void setUp() {
        // 테스트 대상 클래스의 인스턴스를 생성한다.
        calculateService = new CalculateService();
    }

    @Test
    public void testAdd() {

        // given
        int a = 5;
        int b = 3;

        // when
        int result = calculateService.add(a, b);

        // then
        assertEquals(8, result, "5 + 3 = 8");
    }
}
```

- `@BeforeEach`
    - 각 테스트 메서드가 실행되기 전에 실행될 메서드를 정의한다.
    - CalculateService 인스턴스를 초기화 시킨다. 따라서 모든 메서드가 독립적으로 실행되면, 각각의 테스는 새로운 인스턴스를 사용한다.
- `@Test`
    - JUnit에서 제공하는 어노테이션이며, 이 메서드가 테스트 메서드임을 나타낸다.
- **📌 테스트 메서드 작성법**
    - **given** : 테스트에서 필요한 입력값을 준비하는 단계
    - **when** : 테스트 대상 메서드를 호출하는 단계
    - **then** : 결과를 검증하는 단계
- **📌 단위 테스트에서 자주 사용하는 assert 메서드**
    - **assertEquals(기대 값, 실제 값)** : 기대 값과 실제 값이 같은지 검증한다.
    - **assertTrue(조건)** : 조건이 참인지 검증한다.
    - **assertFalse(조건)** : 조건이 거짓인지 검증한다.
    - **assertNotNull(객체)** : 객체가 null이 아닌지 검증한다.

</br>

그럼 외부 의존성을 가진 클래스의 단위 테스트를 작성할 때는 어떻게 해야될까?

⇒ 의존성을 **모킹(Mock)** 하여 테스트하자

```java
// .. 서비스 클래스

public class UserService {

    private final UserRepository UserRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

		// .. UserRepository를 의존하는 findUserById 메서드
    public User findUserById(Long id) {
        return UserRepository.findById(id).orElseThrow(() -> new RuntimeException("회원 없음"));
    }
}
```

```java
// .. 단위 테스트

public class UserServiceTest {

    @Mock
    private UserRepository UserRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindUserById() {

        // given
        User mockUser = new User(1L, "seoji");
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        // when
        User user = userService.findUserById(1L);

        // then
        assertEquals("seoji", user.getName());
        verify(userRepository).findById(1L);
    }
}
```

- `@Mock`
    - UserRepository를 모킹한다.
    - 실제 데이터베이스와 통신하는 것이 아니고 모킹된 repository 를 사용한다.
- `@InjectMocks`
    - UserService의 인스턴스를 생성하면 코딩된 UserRepository를 주입한다.
- when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser))
    - 우리는 지금 서비스 클래스를 테스트하는 것이므로 모킹된 repository가 어떤 값을 반환할 지를 정해줘야 한다.
    - ⇒ 📌 **서비스 클래스 자체의 로직만을 독립적으로 테스트하기 위해서**
    - userRepository.findById(1L)이 호출될 때, Optional.of(mockUser)이 반환되도록 한다.
- verify(userRepository).findById(1L)
    - 테스트가 끝난 후, userRepository.findById(1L)가 실제로 호출 되었는지 확인한다.

</br>

### 2. 통합 테스트

애플리케이션의 여러 구성 요소나 모듈이 실제로 잘 동작하는지 확인하기 위한 테스트이다.

서로 다른 모듈 (서비스, 데이터베이스 등)이 통합되어 원활하게 상호작용 하고 있는지 확인한다.

</br>

간단한 예시로 통합 테스트 작성법을 알아보자

```java
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc;

    @DisplayName("DisplayName : 테스트 이름을 설정할 수 있습니다")
    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }
}
```

- `@SpringBootTest`
    - 통합 테스트를 수행하기 위해 스프링 부트 애플리케이션 컨텍스트 전체를 로드한다.
    - 모든 빈을 컨테이너에 올리고 시작하므로 운영환경과 유사한 환경에서 테스트 할 수 있다. → 하지만 시간이 오래 걸림
    - 애플리케이션의 모든 bean과 설정이 로드되며, 이를 통해 여러 계층 (Controller, Service, Repository등)을 통합하여 테스트 할 수 있다.
- `@AutoConfigureMockMvc`
    - MockMvc를 자동으로 구성하여 스프링 MVC의 동작을 시뮬레이션하여 컨트롤러 계층을 테스트 할 수 있게 해준다.
- MockMvc
    - HTTP 요청을 모킹하여 컨트롤러에 대한 테스트를 수행할 수 있게 도와준다.
    - “**HTTP 요청을 모킹(Mock)한다**”는 것은 실제 네트워크를 통해 요청을 보내지 않고, 프로그램 내부에서 가상의 요청을 생성하여 요청하는 것을 말한다.
- mvc.perform(MockMvcRequestBuilders.get(”/”))
    - MockMvc를 사용하여 HTTP GET 요청을 “/” 경로로 보낸다.
    - 요청은 HelloController의 “/” 경로를 처리하는 메서드로 전달된다.
- andExpect(status().isOk())
    - HTTP 응답 상태가 200 OK 인지 확인한다
- andExpect(content().string(equalTo("Greetings from Spring Boot!")))
    - 컨트롤러의 메서드에서 반환되는 결과를 검증하는 코드이다.
    - 응답 본문 내용이 "Greetings from Spring Boot!"와 일치하는지 확인한다.

</br>

🌟 실제 코드가 변경되면 테스트 코드도 변경이 필요할 수 있다. **테스트 코드도 가독성 있게 잘 작성하도록 하자!**
