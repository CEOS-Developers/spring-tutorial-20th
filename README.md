# spring-tutorial-20th
CEOS 20th BE Study - Spring Tutorial

<br />

## 1. Spring이 지원하는 기술들

---
### 1.1. IoC (Inversion of Control)
> `IoC (Inversion of Control)` : **제어의 역전** (제어의 흐름을 바꾼다)

메소드나 객체의 호출 작업이 개발자가 아닌, 외부에서 결정되는 것을 의미한다. 즉, 다른 객체를 직접 생성하거나 제어하는 것이 아니라 외부에서 관리하는 객체를 가져와 사용하는 것을 말한다. 스프링에서의 외부 (= 객체를 관리하는 주체) 를 `스프링 컨테이너` 라고 한다.  

```java
// 제어의 역전 실행 이전

public class A {
    
    // 클래스 A 내부에서 new 키워드로 클래스 B의 객체 직접 생성
    b = new B(); 
}
```
```java
// 제어의 역전 실행 이후

public class A {
    
    // 클래스 A 내부에서 직접 객체 생성하지 않고, 스프링 컨테이너에서 받아온 객체를 b에 할당
    private B b; 
}
```
객체를 스스로가 만드는 것이 아니라 제어권을 스프링에게 위임하여 스프링이 만들어놓은 객체를 주입한다. `스프링 컨테이너` 가 애플리케이션 실행 시 객체(`빈`)를 생성하고, 필요할 때 이를 주입해주는 것이다. 스프링에서는 `ApplicationContext` 가 대표적인 IoC 컨테이너 역할을 한다.

이는 객체 간의 결합도를 낮추어 유연한 코드를 작성할 수 있도록 하며, 가독성을 높이고 코드 중복을 줄여 유지보수가 쉽게 해준다. 


### 1.2. DI (Dependency Injection)
> `DI (Dependency Injection)` : **의존성 주입**

`제어의 역전`을 구현하기 위해 사용하는 방법이 바로, `DI` 이다. `DI (의존성 주입)` 란, 객체 간의 의존성을 외부에서 주입해주는 방식이다. 즉, `스프링 컨테이너` 에서 객체 `빈` 을 먼저 생성해두고 생성한 객체를 지정한 객체에 주입하는 방식이다. 

이를 통해 객체의 의존성을 낮추어 유연하고 확장성있는 코드 개발이 가능해진다.

<br />

> **의존성 주입 방식** (3가지)

객체의 불변성 보장, 순환 참조 에러 방지 가능, 테스트 용이 등의 이유로 **생성자 주입**이 가장 권장된다.

#### 1. 필드 주입
- 클래스에 선언된 필드에 생성된 객체 주입하는 방식
- `@Autowired` 어노테이션을 주입할 필드 위에 명시
```java
@Controller
public class MemberController {
    
    @Autowired
    private MemberService memberService;
}
```

외부에서 변경이 불가능해서 테스트하기 힘들다는 치명적인 단점이 존재한다. 되도록 사용하지 않는 것이 좋다고 한다. 

#### 2. 수정자 주입
- 클래스의 수정자를 이용해 의존성을 주입하는 방식
- `@Autowired` 어노테이션을 수정자 메소드 위에 명시
- 선택, 변경 가능성이 있는 의존관계에 사용
```java
@Controller
public class MemberController {
    
    private MemberService memberService;
    
    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
}
```

#### 3. 생성자 주입
- 클래스의 생성자를 이용해 의존성을 주입하는 방식
- 생성자 호출 시점에 딱 한번만 호출되는 것이 보장됨
- 불변, 필수 의존관계에 사용
- 필드에 `final` 키워드와 함께 사용됨
```java
@Controller
public class MemberController {

    private final MemberService memberService;
    
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
```
```java
// Lombok 라이브러리를 이용한 생성자 주입 방식

@Controller
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberService memberService;
}
```
생성자가 딱 1개만 있으면 `@Autowired` 를 생략해도 자동 주입된다. 

---

💡`final`키워드란?
- 한 번 초기화 된 후에는 변경 불가 (불변성 보장)
- 객체 생성 시 반드시 초기화되어야 하므로, 생성자에서 필드를 설정해주어야 함

💡`@RequiredArgsConstructor` 란?
- `final` 로 선언된 필드만을 파라미터로 받는 생성자를 생성
- `final` 이 있으면 반드시 선언해줘야 함

💡`@Autowired` 를 이용한 의존성 주입
- 스프링이 관리하는 객체에서만 동작
- 스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않음을 주의하자

💡`의존성 객체` 란?
- 어떤 객체가 자신의 기능을 수행하는 데 필요한 다른 객체
- `의존성 객체`는 `의존성 주입`을 통해 클래스에 제공됨 (just 선언만 하면 자동으로)
- `A 객체`가 `B 객체`를 사용해야 작업을 수행할 수 있을 때, `B 객체`는 `A 객체`의 `의존성 객체`
- ex ) `MemberRepository` 는 `MemberService` 의 의존성 객체

### 1.3. AOP (Aspect-Oriented Programming)
> `AOP (Aspect-Oriented Programming)` : 관점 지향 프로그래밍

메소드나 객체의 기능을 `핵심 관심사(Core Concern)` 와 `공통 관심사(Cross-cutting Concern)` 로 나누어 프로그래밍하는 것을 말한다. 여러 개의 클래스에서 반복해서 사용하는 코드가 있다면 해당 코드를 모듈화해서 공통 관심사로 분리한다. 이를 통해 코드의 재사용성과 유지 보수성을 높일 수 있게 해준다. 

`Spring AOP` 에서는 로깅, 보안, 트랜잭션 관리 등과 같은 공통적인 관심사를 모듈화하여 코드 중복을 줄이고 유지 보수성을 향상하는데 도움을 준다. 

### 1.4. PSA (Portable Service Abstraction)
> `PSA (Portable Service Abstraction)` : 이식 가능한 서비스 추상화

환경의 변화와 관계없이 일관된 방식의 기술로의 접근 환경을 제공하는 추상화 구조를 말한다. 즉, 스프링에서 제공하는 다양한 기술들을 추상화하여 개발자가 쉽게 사용하는 인터페이스를 의미한다. 어느 기술을 사용하던 일관된 방식으로 처리하도록 해준다.

즉, `PSA` 가 적용된 코드는 기존에 작성된 코드를 수정하지 않으면서 확장할 수 있으며, 어느 특정 기술에 특화되어 있지 않아 유연한 사용을 가능하게 해준다.


<br />

## 2. Spring Bean 과 Bean 의 라이프사이클

---
### 2.1. Spring Bean 이란?
`스프링 컨테이너` 가 생성하고 관리하는 객체이다. 알기 쉽게 `스프링의 객체` 라고 생각하자.
`스프링 빈`은 `싱글톤`으로 관리되며 XML 설정 파일, 어노테이션 (`@Component`, `@Service`, `@Repository`, `@Controller` 등), 자바 설정 클래스 (`@Configuration`과 `@Bean` 사용) 를 통해 정의된다. 

### 2.2. Bean 의 라이프사이클
1. 스프링 컨테이너 생성
2. 스프링 빈 생성
3. 의존성 주입
4. 초기화
5. 사용
6. 소멸

`@PostConstruct`와 `@PreDestroy` 어노테이션을 통해 빈이 초기화되거나 소멸될 때 특정 동작 (콜백) 을 정의할 수 있다. 

---

💡`싱글톤` 이란?
- 인스턴스가 딱 1개만 생성되는 것을 보장하는 디자인 패턴
- (스프링 빈에서의 싱글톤) 하나의 빈 인스턴스만 생성되고 애플리케이션 전역에서 재사용되는 방식

<br />

## 3. 스프링 어노테이션

---
### 3.1. 어노테이션의 정의와 Java에서의 구현 방법
> `어노테이션(@, annotation)` : 코드에 대한 부가적인 정보를 제공하는 특수한 형태의 표기

프로그램 코드의 일부가 아닌 프로그램에 관한 데이터를 제공하고, 코드에 정보를 추가하는 정형화된 방법이다. 실제 데이터가 아닌 데이터를 위한 데이터로 `메타데이터` 라고도 불리운다.

Java에서 어노테이션은 `@interface` 로 정의되며, `메타 어노테이션` 을 사용하여 어노테이션의 동작을 제어할 수 있다. 

### 3.2. 어노테이션을 통한 Bean 등록 과정

어노테이션으로 `스프링 빈`을 등록하는 방법에는, `컴포넌트 스캔`과 자바 코드로 직접 등록하는 방식이 있다.

#### 1. 컴포넌트 스캔
`@Component` 를 명시하여 빈을 추가하는 방법이다. 클래스 위에 `@Component` 어노테이션이 있으면 `스프링 빈` 으로 자동 등록된다. 

추가로 `@Controller`, `@Service`, `@Repository`, `@Configuration` 은 `@Component` 를 포함하기 때문에 모두 컴포넌트 스캔의 대상이 된다. 따라서 이와 같은 어노테이션들도 자동으로 스프링 빈으로 등록된다.  

#### 2. 자바 코드로 직접 등록하기
`@Configuration` 과 `@Bean` 어노테이션을 이용하여 직접 빈을 등록할 수 있다.

클래스 위에 `@Configuration` 를 쓰고, 객체를 생성하는 메소드 위에 `@Bean` 을 명시하면 된다. 
```java
@Configuration
public class AppConfig {

    // @Bean 어노테이션을 사용해 Ceos 객체를 스프링 빈으로 등록
    @Bean
    public Ceos ceos() {
        return new Ceos();
    }
}
```

### 3.3. 스프링이 컴포넌트를 탐색하는 과정 (@ComponentScan)
`@ComponentScan` 어노테이션은 스프링이 패키지를 스캔하여 `@Component` 와 그 하위 어노테이션(`@Service`, `@Repository`, `@Controller` 등)이 붙은 클래스를 찾아서 빈으로 등록하도록 한다. 즉, 개발자가 수동으로 빈을 등록하지 않더라도 자동으로 빈을 등록하는 역할을 수행해준다. 

1. 패키지 탐색 시작 및 클래스 파일 검색
    : `@ComponentScan` 이 선언된 클래스의 패키지 또는 지정된 패키지를 기준으로, 그 하위 패키지에 있는 클래스들을 모두 탐색

2. 어노테이션 확인
    : 해당 패키지에서 발견된 클래스에 하위 어노테이션이 있는지 확인하고 해당 어노테이션이 붙어 있으면, 그 클래스를 스프링 빈으로 등록할 후보로 선택

3. 빈 등록
    : 어노테이션이 붙은 클래스는 스프링 컨테이너에 의해 빈으로 등록됨

4. 의존성 주입
    : 등록된 빈들은 스프링이 관리하는 빈 목록에 추가되며, 이를 통해 의존성 주입이 이루어짐

<br />

## 4. 단위 테스트와 통합 테스트 탐구

---
### 4.1. 단위 테스트

### 4.2. 통합 테스트

