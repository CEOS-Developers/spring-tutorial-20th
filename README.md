## 1. spring-tutorial-20th

---

## 2. Spring 지원 기술

스프링의 3대 프로그래밍 모델 IOC/DI, AOP, PSA

### 2-1. IoC/DI

#### IoC (제어 역전)

스프링에서 객체 관리를 개발자가 아닌 스프링 컨테이너에 맡기는 것 (제어 역전)
**스프링 컨테이너 :** 애플리케이션에서 객체(Bean)를 관리하는 공간
객체 생성, 라이프사이클, 빈 간의 의존성 관리 등을 스프링 컨테이너에서 담당
IoC는 디자인 패턴으로 DI는 그의 하위 개념

**스프링 컨테이너 역할**

- Bean 관리
- 의존성 관리
- 빈 라이프사이클 관리

### DI (의존성 주입)

스프링 컨테이너의 의존성 관리는 DI를 통해 구현
의존성 = 빈 간의 의존적 관계 ex) 자동차는 타이어에 의존적

#### DI의 필요성

DI를 사용하지 않고, Car 클래스 내부에서 Tire를 생성하여 의존성을 해결한 경우
두 가지 문제가 발생

```java

public class Car {

	Tire tire;

	public Car() {
		tire = new KoreaTire();
		// tire = new AmericaTire();
	}

}
```

1. 고정적인 의존 관계
   모든 Car는 KoreaTire를 사용
   객체 호출에 따라 다른 타이어를 사용할 수 없고, 타이어 사용을 유동적으로 할 수 없음

2. 직접 수정해야하는 번거로움
   코드의 확장으로 타이어 종류가 늘어날 때마다 수정해야하는 번거로움이 있음
   즉 Car과 Tire의 결합도가 강해, 코드의 확장성이 떨어짐

→ 이는 객체를 외부(컨테이너)에서 생성하여 주입하는 DI로 해결

#### DI 종류

대부분의 의존관계 주입은 불변해야하므로 생성자 주입을 사용하는 것이 좋음

- **1. 생성자 주입**
  - 가장 권장되는 의존 관계 주입 방식
    - 주로 불변, 필수 의존 관계에서 사용

```java
 public class Car {
	Tire tire;

	public Car(Tire tire) {
		this.tire = tire;
	}
}
```

- **2. setter 주입**
  - 선택, 변경이 있는 의존 관계에서 사용

```java
public class Car {

	Tire tire;

	public void setTire(Tire tire) {
		this.tire = tire
	}

}

```

- **3. 필드 주입**
  - 한 번 주입된 의존성을 변경하지 않고 유지하는 경우 적합
    - final 키워드 사용이 불가능하므로 불변성이 보장되지 않음

```java
 import org.springframework.beans.factory.annotation.Autowired;

public class Car {

	@Autowired
	Tire tire;

}
```

### 2-2. AOP

#### AOP란

AOP (Aspect-Oriented Programming) - 관점 지향 프로그래밍
AOP란 공통(횡단) 관심사항과 핵심 관심사항을 분리하는 것
ex) 시간 측정 로직 (공통)과 다른 기능 (핵심)을 분리해서, 원할 때 공통을 적용

#### 용어

- Aspect: 공통 기능 모듈화
- Target: Aspect가 적용될 대상, 메서드, 클래스
- Join Point: Aspect가 적용될 수 있는 시점
- Advice: Aspect 기능을 정의, 메서드 실행 전, 후, 예외 처리 발생 시 실행되는 코드
- Point cut: Advice를 적용할 메서드 범위 지정

#### 주요 어노테이션

- `@Aspect`: 해당 클래스를 Aspect로 사용
- `@Before`: 타겟 메서드 실행 전 Advice 실행
- `@AfterReturning`: 타겟 메서드가 정상 실행, 반환 후 Advice 실행
- `@AfterThrowing`: 타겟 메서드에서 예외 발생시 Advice 실행
- `@After`: 타겟 메서드 실행 후 Advice 실행
- `@Around`: 타겟 메서드 실행 전, 후 또는 예외 발생 시 Advice 실행

#### AOP 사용 방법

build.gradle에 의존성 추가

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-aop'
}

```

`@Aspect` 어노테이션 사용
각 어노테이션에 들어가는 것들은 타겟 메서드

```java
...
@Aspect
public class ExAspect{
    @Before("execution({리턴타입} {패키지명}.{클래스명}.{메서드명}({파라미터}))")
    public void exBefore(JointPoint jointPoint){
        // 타겟 메서드 실행 전 동작
    }

    @After("execution({리턴타입} {패키지명}.{클래스명}.{메서드명}({파라미터}))")
    public void exAfter(JointPoint jointPoint){
        // 타겟 메서드 실행 후 동작
    }

    @AfterReturning(pointcut = "execution({리턴타입} {패키지명}.{클래스명}.{메서드명}({파라미터}))", returning = "result")
    public void exAfterReturning(JointPoint jointPoint, Object result){
        // 타겟 메서드가 정상 종료된 후 동작
    }

    @AfterThrowing(pointcut = "execution({리턴타입} {패키지명}.{클래스명}.{메서드명}({파라미터}))", throwing = "e")
    public void exAfterThrowing(JointPoint jointPoint, Throwable e){
        // 예외 발생 후 동작
    }

    @Around("execution({리턴타입} {패키지명}.{클래스명}.{메서드명}({파라미터}))")
    public void exAround(ProceedingJointPoint jointPoint) throws Throwable {
        // 타겟 메서드 실행 전 동작
        Object result = jointPoint.proceed(); // 타겟 메서드 실행
        // 타겟 메서드 실행 후 동작
        return result;
    }
}
```

### 2-3. PSA

**1. PSA란(Portable Service Abstraction)**
PSA: 환경과 관계 없이 일관된 방식으로 접근 환경을 제공하는 추상화 구조
PSA를 통해 애플리케이션 요구사항 변경에 유연한 대처가 가능함
Spring은 Spring Web MVC, Spring Transaction, Spring Cache 등 다양한 PSA 제공

---

## 3. Spring Bean, Bean 라이프 사이클

### 3-1. Spring Bean이란

스프링 컨테이너에 의해 관리되는 인스턴스화 된 자바 객체
`@Component` 어노테이션이 있으면 스프링 빈으로 자동 등록
아래 어노테이션은 @Component를 포함하므로, 스프링 빈으로 등록됨

- `@Controller`
- `@Service`
- `@Repository`

### 3-2. Spring Bean 라이프 사이클

스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존 관계 주입
-> 초기화 콜백 -> 빈 사용 -> 소멸 전 콜백 -> 스프링 종료

초기화 작업은 의존 관계 주입이 끝난 후에 진행해야함

- `@PostConstruct`: 의존 관계 주입이 끝나면 초기화 콜백을 통해 시점을 알 수 있음
- `@PreDestory`: 스프링 컨테이너 종료 전 소멸 콜백을 통해 안전한 종료 가능

```java
@PostConstruct
public void init(){

}
@PreDestory
public void close(){

}
```

---

## 4. 스프링 어노테이션

### 4-1. 어노테이션이란

메타 데이터의 일종, @를 붙여서 해당 클래스, 메서드, 필드에 메타 데이터를 추가
이를 통해 원하는 동작을 수행시키는 것

### 4-2. 어노테이션을 통한 Bean 등록

어노테이션을 통한 Bean 등록은 수동등록 방식과 자동등록 (ComponentScan) 방식이 있음

#### 1) `@Bean`, `@Configuration`을 사용한 수동등록

AppConfig 파일에서 필요한 빈을 수동 등록할 수 있음
@Bean만 사용하여 등록할 수도 있으나, 싱글톤 유지를 위해 @Configuration과 함께 사용

```java
@Configuration
public class AppConfig{
    @Bean
    public MemberService memberService(){
        return memberService();
    }
}
```

ApplicationContext를 통해서 사용

```java
public class MemberApp{
    public static void main(String[] args){
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
    }
}
```

#### 2) `@ComponentScan`을 사용한 자동 등록

`@ComponentScan`: `@Component`가 붙은 컴포넌트를 다 가져와 등록
부분적인 컴포넌트 스캔을 원하는 경우 아래 옵션 지정 가능
디폴트는 `@ComponentScan`이 붙은 설정 정보 파일 패키지부터 시작

- basePackages: 탐색 위치 지정, 하위 패키지만 스캔
- basePackageClasses: 지정 클래스의 패키지부터 스캔

```java
@ComponentScan(
	basePackages = {"hello.core", "hello.service"},
    basePackageClasses = AutoAppConfig.class
)
```

- `@Component`: 컴포넌트 스캔에서 사용
- `@Controller`: 스프링 MVC 컨트롤러에서 사용
- `@Service`: 스프링 비즈니스 로직에서 사용
- `@Repository`: 스프링 데이터 접근 계층에서 사용
- `@Configuration`: 스프링 설정 정보에서 사용

@Component가 아니여도, 내부에 @Component를 가지고 있어서 스캔됨

---

## 5. 단위 테스트 vs 통합 테스트

**단위 테스트**
격리된 환경에서 필요한 부분만 테스트를 진행
각 기능 및 메서드가 독립적으로 올바르게 동작하는지 검증하는 것이 목적

**통합 테스트**
애플리케이션 전체 컨텍스트를 로드하여, 실제 운영환경과 유사한 조건에서 테스트 수행
애플리케이션의 부분이 어떻게 상호작용하는지 파악하는 것이 목적

### 테스트 코드 작성 방식

테스트는 given - when - then 으로 나눠 작성하는 것이 좋음
어떤 데이터를 줬을 때 (given), 어떤 함수를 실행하면 (when), 어떠한 결과가 나와야함 (then)

- `@Transactional`: 테스트 시작 전 트랜잭션 시작, 완료 후 롤백 -> DB에 반영되지 않음
- `@Rollback`: false 옵션시 롤백없이 커밋
- `@AfterEach`: 테스트 메서드 실행 후 무조건 실행
- `@BeforeEach`: 테스트 메서드 실행 전 무조건 실행

```java
@SpringBootTest
@Transactional
//@Rollback(false)
class MemberRepositoryTest {
    @Autowired MemberRepository memberRepository;

    @Test
    public void testMember() throws Exception {
        // given
        Member member = new Member();
        member.setUsername("memberA");

        // when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }
}
```

---

## 6. 레퍼런스

[AOP](https://adjh54.tistory.com/133)
[단위테스트, 통합 테스트](https://velog.io/@mon99745/%EC%9C%A0%EB%8B%9B-%ED%85%8C%EC%8A%A4%ED%8A%B8Unit-Test-%ED%86%B5%ED%95%A9-%ED%85%8C%EC%8A%A4%ED%8A%B8Integration-Test-%EA%B8%B0%EB%8A%A5-%ED%85%8C%EC%8A%A4%ED%8A%B8Funcional-Test%EB%9E%80)
[테스트](https://mimah.tistory.com/entry/Spring-Boot-AfterEach-BeforeEach-%EC%98%88%EC%A0%9C)
