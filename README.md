# spring-tutorial-20th
### CEOS 20th BE Study - Spring Tutorial (20th 문서영)
## 1️⃣ spring-boot-tutorial-20th 완료

### 튜토리얼 결과 - h2 연결 (mac)

> **맥 H2 실행 명령어**
>
>
> (base) seona@seonaui-MacBookAir ~ % ./desktop/h2/bin/h2.sh
>

> **H2DB TCP 모드로 서버 접속** ⭐
>
>
> JDBC URL : jdbc:h2:tcp://localhost/~/ceos20
>
> User Name : sa
>

테스트 데이터 입력은 h2 insert를 이용해 수기로 입력해주었다.

ex) INSERT INTO TEST~~
![img.png](img.png)

## 2️⃣ spring이 지원하는 기술들(IoC/DI, AOP, PSA 등)을 자유롭게 조사해요

**Spring 삼각형**

POJO(Plain Old Java Object)를 기반으로 하는 **IoC/DI**, **AOP**, **PSA** 의 스프링 3대 프로그래밍 모델을 스프링 삼각형이라고 한다.

| **IoC/DI** | 의존 역전/의존성 주입은  @Autowired나 XML 설정을 통해서 강합 결합을 느슨한 결합으로 변경해주며, 코드를 유연하게 해준다. |
| --- | --- |
| **AOP** | 관점 지향 프로그래밍으로서 공통된 로직을 추출하여 메소드의 다양한 시점에 실행할 수 있게 해줄수 있으며, 코드를 줄여주고, 개발자가 공통 로직을 배제하고 핵심 관심사에 집중할 수 있도록 해준다. |
| **PSA** | Portable Service Abstraction으로 일관성 있는 서비스 추상화이다. 서비스 추상화의 대표적인 예를 JDBC로 들수 있으며, 어떠한 데이터 베이스를 사용하더라도 일관성있는 방식으로 제어할 수 있도록 공통의 인터페이스를 제공하는 것이 서비스 추상화라고 한다. |

## **IoC/DI - 제어의 역전/의존성 주입**

스프링에서는 제어의 역전을 의존성 주입이라고도 한다. 이를 더 잘 이해하기 위해서는 **의존성**을 이해해야 한다.

### 의존성이란?

의존성은 어렵게 생각하지 않아도 단순하게 예를 들자면 다음과 같다.

**운전자는 자동차를 생산한다. = new Car()**

**자동차는 내부적으로 타이어를 생산한다. = Car 객체 생성자에서 new Tire();**

따라서 **new 라는 키워드는 의존성**이라 할 수 있다. Car 객체 생성자에서 new 를 실행함으로 Car가 Tire에 의존한다고 볼 수 있다. 이렇게 의존이라는 것은 전체가 부분에 의존하는 것을 표현하며, 좀 더 깊게 들어가서 의존 관계 사이를 집합 관계와 구성 관계로 구분할 수 있으며, 의존 관계를 어떻게 맺냐에 따라서 강합 결합이냐 느슨한 결합이냐를 이야기할 수 있게 된다.

| 집합 관계 | 부분이 전체와 다른 생성 주기를 가질 수 있다. |
| --- | --- |
| 구성 관계 | 부분은 전체와 같은 생명 주기를 갖는다. |

### **강한 결합**

**객체 내부에서 다른 객체를 생성하는 것**은 강한 결합도를 가지는 구조이다. A 클래스 내부에서 B 라는 객체를 직접 생성하고 있다면, B 객체를 C 객체로 바꾸고 싶은 경우에 A 클래스도 수정해야 하는 방식이기 때문에 강한 결합이다. 위에서도 동일하게 자동차 내부에서 타이어를 생성하는 것은 다른 타이어를 생성하고자 해도 코드를 수정해야 되는 상황이 발생한다.

### **느슨한 결합**

객체를 주입 받는다는 것은 **외부에서 생성된 객체를 인터페이스를 통해서 넘겨받는 것**이다. 이렇게 하면 결합도를 낮출 수 있고, 런타임시에 의존관계가 결정되기 때문에 유연한 구조를 가진다.

SOLID 원칙에서 O 에 해당하는 **Open Closed Principle** 을 지키기 위해서 디자인 패턴 중 전략패턴을 사용하게 되는데, **생성자 주입을 사용하게 되면 전략패턴을 사용**하게 된다.

> @Autowired나 XML 설정을 통해 강한 결합을 느슨한 결합으로 변경해주며, 코드를 유연하게 해준다.
>

## **객체의 주입?**

의존관계 주입은 크게 4가지 방법이 있다.

1. 생성자 주입
2. 수정자 주입 (setter 주입)
3. 필드 주입
4. 일반 메서드 주입

### 생성자 주입

생성자에 `@Autowired`: `@Component`를 통해 스프링 빈에 등록될 때 스프링 컨테이너가 해당 빈을 찾아 주입 해준다. 이름 그대로 생성자를 통해서 의존 관계를 주입 받는 방법이다.

- 특징
    - 생성자 호출시점에 딱 1번만 호출되는 것이 보장된다. → 이후 값을 변경 못 하게 막을 수 있다.
    - **불변, 필수** 의존관계에 사용
        - **불변**: 생성자를 통해서***만*** 의존관계가 주입되고, 이후 외부에서 수정할 수 없다.
            - 당연히 한번 설정한 후, setter 메서드를 만들면 안된다!
        - **필수**: 무조건 값이 있어야 한다.
            - private final로 필드에 지정을 했기에, 반드시 값이 있어야 한다.

```java
@Component
public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

		// @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

**중요!** *생성자가 딱 1개만 있으면 `@Autowired`를 생략해도 **자동 주입** 된다*. 물론 스프링 빈에만 해당한다.

### 수정자 (setter 주입)

`setter` 라 불리는 필드의 값을 변경하는 수정자 메서드를 통해서 의존관계를 주입하는 방법이다.

- 특징
    - **선택, 변경** 가능성이 있는 의존관계에 사용 (final 필드 X)
        - 선택: 스프링 빈으로 등록되지 않은 인스턴스도 주입 가능, `required = false`로 주입할 대상이 없어도 동작하게 할 수 있다.
        - 변경: 변경 가능성 있는 의존관계 사용시 중간에 setter를 호출해 의존관계를 변경 가능
    - 자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법이다.

```java
@Component
public class OrderServiceImpl implements OrderService {
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Autowired
    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

		@Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

만약, 생성자와 수정자가 둘 다 존재한다면 기본적으로 스프링은 **생성자 의존관계 주입을 먼저 수행**한다. 이후, 수정자 의존관계 주입을 진행한다. (수정자가 있다면 생성자는 생략 가능, 싱글톤 원칙에 따라 생성자와 수정자에서 주입된 스프링 빈은 동일하다!)

이때, 당연히 @Autowired 애노테이션이 빠져있으면 스프링이 인식하지 못하기에 주입되지 않는다.

> **참고:** @Autowired 의 기본 동작은 주입할 대상이 없으면 오류가 발생한다. 주입할 대상이 없어도 동작하게 하려면 `@Autowired(required = false)` 로 지정하면 된다.
>

> **참고:** 자바빈 프로퍼티, 자바에서는 과거부터 필드의 값을 직접 변경하지 않고, setXxx, getXxx 라는 메서 드를 통해서 값을 읽거나 수정하는 규칙을 만들었는데, 그것이 **자바빈 프로퍼티 규약**이다.
>

**자바빈 프로퍼티 규약 예시**

```java
class Data {
    private int age;
    public void **setAge**(int age) {
      this.age = age;
    }
    public int **getAge**() {
      return age;
		}
}
```

### 필드 주입

이름 그대로 필드에 바로 주입하는 방법이다.

- 특징
    - 코드가 간결해서 많은 개발자들을 유혹하지만 **외부에서 변경이 불가능해서 테스트 하기 힘들다**는 치명적인 단점이 있다.
    - DI 프레임워크가 없으면 아무것도 할 수 없다. 순수한 자바 코드로 테스트 할 수 없다...
    - **사용하지 말자!** 아래와 같은 경우엔 사용해도 된다.
        - 애플리케이션의 실제 코드와 관계 없는 테스트 코드

          `@SpringBootTest` 등…

        - 스프링 설정을 목적으로 하는 `@Configuration` 같은 곳에서만 특별한 용도로 사용

```java
@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private DiscountPolicy discountPolicy;
}
```

> 참고: 순수한 자바 테스트 코드에는 당연히 @Autowired가 동작하지 않는다. @SpringBootTest 처럼 스프링 컨테이너를 테스트에 통합한 경우에만 가능하다.
>

```java
@Bean
OrderService orderService(MemberRepository memberRepoisitory, DiscountPolicy discountPolicy) {
    new OrderServiceImpl(memberRepository, discountPolicy)
}
```

> 참고: 다음 코드와 같이 @Bean 에서 파라미터에 의존관계는 자동 주입된다. 수동 등록시 자동 등록된 빈의 의존관계가 필요할 때 문제를 해결할 수 있다.
>

### 일반 메서드 주입

일반 메서드를 통해서 주입 받을 수 있다.

- 특징
    - 한번에 여러 필드를 주입 받을 수 있다.
    - 일반적으로 잘 사용하지 않는다. (생성자/수정자 주입 내에 대부분 끝난다)

```java
@Component
public class OrderServiceImpl implements OrderService {
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

		@Autowired
    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }
}
```

> 참고: **의존관계 자동 주입은 스프링 컨테이너가 관리하는 스프링 빈이어야 동작한다**. 스프링 빈이 아닌 Member 같은 클래스에서 @Autowired 코드를 적용해도 아무 기능도 동작하지 않는다.
>

## 생성자 주입을 선택해라!

과거에는 수정자 주입과 필드 주입을 많이 사용했지만, 최근에는 스프링을 포함한 DI 프레임워크 대부분이 **생성자 주입을 권장**한다. 그 이유는 다음과 같다.

### **불변**

- 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종료시점까지 의존관계를 변경할 일이 없다. 오히려 대부분의 의존관계는 애플리케이션 종료 전까지 변하면 안된다. (불변해야 한다.)
- 수정자 주입을 사용하면, setXxx 메서드를 **public**으로 열어두어야 한다.
- 누군가 실수로 변경할 수도 있고, 변경하면 안되는 메서드를 열어두는 것은 좋은 설계 방법이 아니다.
- 생성자 주입은 객체를 생성할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없다. 따라서 **불변하게 설계**할 수 있다.

### **누락**

프레임워크 없이 순수한 자바 코드를 단위 테스트 하는 경우에, 아래와 같이 **수정자 의존관계**인 경우

```java
public class OrderServiceImpl implements OrderService {
	private MemberRepository memberRepository;
	private DiscountPolicy discountPolicy;

	@Autowired
	public void setMemberRepository(MemberRepository memberRepository) {
	  this.memberRepository = memberRepository;
	}

	@Autowired
	public void setDiscountPolicy(DiscountPolicy discountPolicy) {
	  this.discountPolicy = discountPolicy;
	}
	//...
}
```

`@Autowired` 가 프레임워크 안에서 동작할 때는 의존관계가 없으면 오류가 발생하지만,

지금은 프레임워크 없이 순수한 자바 코드로만 단위 테스트를 수행하고 있다.

```java
@Test
void createOrder() {
    OrderServiceImpl orderService = new OrderServiceImpl();
    orderService.createOrder(1L, "itemA", 10000);
}
```

그런데 막상 실행 결과는 *NPE(Null Point Exception)* 이 발생하는데, `memberRepository`, `discountPolicy` 모두 **의존관계 주입이 누락**되었기 때문이다.

테스트를 작성할 때는, 의존관계가 잘 보이지 않지만 생성자 주입을 사용하면 다음처럼 주입 데이터를 누락했을 때 **컴파일 오류**가 발생한다. 그리고 IDE에서 바로 어떤 값을 필수로 주입해야 하는지 알 수 있다.

### final 키워드

생성자 주입을 사용하면 필드에 *final* 키워드를 사용할 수 있다.

그래서 **생성자에서 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에 막아준다.**

```java
@Component
public class OrderServiceImpl implements OrderService {
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;

	@Autowired
	public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
	this.memberRepository = memberRepository;
	}
	...
}
```

필수 필드인 `discountPolicy` 에 값을 설정해야 하는데, 이 부분이 누락되었다.

자바는 컴파일 시점에 다음 오류를 발생시킨다. 컴파일 오류는 세상에서 가장 빠르고, 좋은 오류다!

```java
java: variable discountPolicy might not have been initialized
```

> 참고: 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로, 필드에 final 키워드를 사용할 수 없다.
>
>
> **오직 생성자 주입 방식만 *final* 키워드를 사용할 수 있다.**
>

### 정리

- 생성자 주입 방식을 선택하는 이유는 여러가지가 있지만, 프레임워크에 의존하지 않고, 순수한 자바 언어의 특징을 잘 살리는 방법이기도 하다.
- 기본으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다. 생성자 주입과 수정자 주입을 동시에 사용할 수 있다.
- **항상 생성자 주입을 선택해라!** 그리고 가끔 옵션이 필요하면 수정자 주입을 선택해라. 필드 주입은 사용하지 않는 게 좋다.

## **AOP (Aspect-Oriented Programming) : 관점 지향 프로그래밍**

스프링의 DI가 의존성의 주입이라면 AOP는 코드 주입이라고 할 수 있다. 여러 모듈을 개발하다보면 모듈들에서 공통적으로 등장하는 로직이 존재한다. 예를 들어서 입금 출금 이체와 같은 부분에서 보안적인 부분이나 트랜잭션 로그를 남기고자 하는 코드 부분들이 분명히 공통적으로 등장할 것이다.

이렇게 공통적으로 등장하는 부분은 **횡단 관심사(cross-cutting concern)**이라고 한다. 일반적으로 코드는 **핵심 관심사 + 횡단 관심사**로 구성된다. 따라서 이러한 부분을 @Aspect 어노테이션을 통해서 추출하여 특정 메소드가 호출될 때 특정 시점에 동작하도록 할 수 있는 것이다.

이를 통해서 스프링이 얻고자 하는 부분은 어떤 것인가? 공통적으로 등장하는 횡단 관심사를 어느 한 사람이 잘 정의하여 코드를 작성했다면, 다른 개발자들은 이를 재사용할 수 있을 것이다. 이를 통해서 기존의 횡단 관심사를 계속해서 코딩해야 되는 불편함이 사라지고 오직 개발자들은 핵심 관심사에만 집중하여 개발을 할 수 잇게 되는 것이다. 또한 핵심 관심사에만 집중함으로 자연스럽게 SRP을 적용할 수 있게 된다.

### AOP 적용

**AOP**: Aspect Oriented Programming (관점 지향 프로그래밍)

**공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리**
![img_1.png](img_1.png)

```java
package seona.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* seona.hellospring..*(..))") //타겟을 적용시킬 수 있음.
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("Start : "+joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
            System.out.println("End : "+joinPoint.toString()+" "+ timeMs+"ms");
        }
    }
}
```

Aop 패키지를 만들어서 원하는 공통 관심 사항을 메소드로 만든다. 이후, Around를 통해 타겟을 적용 후 코드를 실행하면 하나하나 코드를 만들 필요 없이 타겟 메소드에 해당 관심 사항을 적용할 수 있다.

### 해결

- 회원가입, 회원 조회등 핵심 관심사항과 시간을 측정하는 공통 관심 사항을 분리한다.
- 시간을 측정하는 로직을 별도의 공통 로직으로 만들었다.
- 핵심 관심 사항을 깔끔하게 유지할 수 있다.
- 변경이 필요하면 이 로직만 변경하면 된다.
- 원하는 적용 대상을 선택할 수 있다.

## **PSA (Portable Service Abstraction) : 일관성 있는 서비스 추상화**

PSA는 일관성있는 추상화이다. 서비스 추상화의 대표적인 예로 JDBC를 두는데 이러한 표준 스펙 덕분에 개발자는 오라클을 사용하든, MySQL을 사용하든, MS-SQL을 사용하던 어떠한 데이터베이스를 사용하던 공통된 방식으로 코드를 작성할 수 있다. 데이터베이스 종류에 관계없이 같은 방식으로 제어할 수 있는 이유는 디자인 패턴에서 설명했던 어댑터 패턴을 활용했기 때문이다. 이처럼 어댑터 패턴을 적용해 같은 일을 하는 다수의 기술을 공통의 인터페이스로 제어할 수 있게 한 것을 서비스 추상화라고 한다.

> **Reference**
>
>
> https://guy-who-writes-sourcecode.tistory.com/40
>
> 김영한 - 스프링 핵심원리 강의

## 3️⃣ Spring Bean 이 무엇이고, Bean 의 라이프사이클은 어떻게 되는지 조사해요

### 스프링 빈이란?

빈(Bean)은 **스프링 컨테이너에 의해 관리되는 재사용 가능한 소프트웨어 컴포넌트**이다.

즉, 스프링 컨테이너가 관리하는 자바 객체를 뜻하며, 하나 이상의 빈(Bean)을 관리한다. 빈은 인스턴스화된 객체를 의미하며, 스프링 컨테이너에 등록된 객체를 스프링 빈이라고 한다.

`@Bean` 어노테이션을 통해 메서드로부터 반환된 객체를 스프링 컨테이너에 등록한다. 빈은 클래스의 등록 정보, Getter/Setter 메서드를 포함하며, 컨테이너에 사용되는 설정 메타데이터로 생성된다.

### 스프링 빈의 라이프 사이클

Spring의 Bean은 Java 또는 XML bean 정의를 기반으로 IoC 컨테이너가 시작될 때 인스턴스화 되어야 한다. Bean을 사용 가능한 상태로 만들기 위해 사전, 사후 초기화 단계를 수행해야 할 수 있다. 그 후 Bean이 더 이상 필요하지 않으면 IoC Container에서 제거된다. 다른 시스템 리소스를 해제하기 위해 사전 및 사후 소멸 단계를 수행해야 할 수 있다.

> **Bean Life cycle**이란 해당 객체가 언제 어떻게 생성되어 소멸되기 전까지 어떤 작업을 수행하고 언제, 어떻게 소멸되는지 일련의 과정을 이르는 말이다.
>

Spring Container는 이런 빈 객체의 생명주기를 컨테이너의 생명주기 내에서 관리하고, 생성이나 소멸 시 호출될 수 있는 콜백 메서드를 제공하고 있다.

### Spring Bean Life Cycle 흐름 정리

1. 스프링 컨테이너 생성
2. 스프링 빈 생성
3. 의존성 주입
4. 초기화 콜백 : 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
5. 사용
6. 소멸전 콜백 :빈이 소멸되기 직전에 호출
7. 스프링 종료

### Spring bean Life Cycle Call back

1. 인터페이스 (InitializaingBean, DisposableBean)

    ```java
    public class Example1 implements InitialzingBean, DisposableBean {
    	```
        @Override
        public void afterPropertiestSet() throws Exception {
        	// 초기화 로직
        }
    
        @Override
        public void destory() throws Exception {
        	// 객체 소멸 로직 (메모리 회수, 연결 종료 등)
        }
    }
    ```

   단점 : 코드를 고칠 수 없는 외부 라이브러리에 적용 불가능

2. **설정 정보에 초기화 메소드, 종료 메소드 지정**

   인터페이스를 구현하는 것이 아니라 @Bean Anootation에 initmethod,destroyMehod를 속성으로 초기화 하여 각각 지정한다.

    ```java
    public class ExampleBean {
        public void init() throws Exception {
        	//초기화 콜백
        }
    
        public void close() throws Exception {
            // 소멸 전 콜백
        }
    }
    
    @Configuration
    class LifeCycleConfig {
    	 @Bean(initMethod = "init", destroyMethod = "close")
         public ExampleBean exampleBean() {
    
         }
    }
    
    ```

   위 방법은 위에 명시한 두 인터페이스를 구현시킬 수 없는 클래스의 객체를 스프링 컨테이너에 등록할 때 유용하다.

   메소드 명을 자유롭게 부여할 수 있고, 스프링 코드에 의존하지 않는다.

   설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화 종료 메서드를 적용할 수 있다.

3. **@PostConstruct, @PreDestroy annotiation 사용**
    - `@PostConstruct`: 기본 생성자를 사용하여 빈이 생성된 후 인스턴스가 요청 객체에 반환되기 직전에 주석이 달린 메서드가 호출됩니다.
    - `@PreDestroy` : @PreDestroy주석이 달린 메소드는 bean이 bean 컨테이너 내부 에서 파괴되기 직전에 호출됩니다 .

    ```java
    import javax.annotation.PostConstruct;
    import javax.annotation.PreDestroy;
    
    public class DemoBean
    {
    	@PostConstruct
    	public void customInit()
    	{
    		System.out.println("Method customInit() invoked...");
    	}
    
    	@PreDestroy
    	public void customDestroy()
    	{
    		System.out.println("Method customDestroy() invoked...");
    	}
    }
    
    ```

   최신 스프링에서 **가장 권장하는 방법**으로 애노테이션 하나만 붙이면 되므로 매우 편리.

   패키지를 잘 보면 javax.annotation.PostConstruct 로 스프링에 종속적인 기술이 아니라 JSR-250 라는 자바 표준이다. 따라서 스프링이 아닌 다른 컨테이너에서도 동작한다.

   유일한 단점은 외부 라이브러리에는 적용하지 못한다는 점.


### 스프링 빈 조회 (메타데이터)

```java
@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext context) {
		return args -> {
			System.out.println("Let's inspect the beans provided by Spring Boot:");

			// Spring Boot 에서 제공되는 Bean 확인
			String[] beanNames = context.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		};
	}
```

- 스프링 컨테이너 **`ApplicationContext`**
    - `ApplicationContext` 를 스프링 컨테이너라 한다.

  ![img_2.png](img_2.png)

  기존에는 개발자가 AppConfig 를 사용해서 직접 객체를 생성하고 DI를 했지만, 이제부터는 스프링 컨테이너를 통해서 관리해줄 수 있다.

  스프링 컨테이너는 `@Configuration` 이 붙은 `AppConfig` 를 설정(구성) 정보로 사용한다. 여기서 `@Bean` 이라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다. 이렇게 스프링 컨테이너에 등록된 객체를 **스프링 빈**이라 한다.

  이때, 스프링 빈은 @Bean 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다. ( memberService, orderService ) 스프링 컨테이너에 객체를 스프링 빈으로 등록 하고, 스프링 컨테이너에서 스프링 빈을 찾아서 사용할 수 있다.

    - **(정리) Bean 요청시 처리 과정**
        1. ApplicationContext는 @Configuration이 붙은 클래스들을 설정 정보로 등록해두고, @Bean이 붙은 메소드의 이름으로 빈 목록을 생성한다.(**서비스 실행**)
        2. 클라이언트가 **해당 Bean을 요청한**다.
        3. ApplicationContext는 자신의 **빈 목록에서 요청한 이름이 있는지 찾는다**.
        4. ApplicationContext는 **설정 클래스로부터 빈 생성을 요청하고, 생성된 빈을 반환한다**.
    - **조회를 위한 메서드**
        - 빈 이름 조회

          **`ac.getBeanDefinitionNames();`** : 스프링에 등록된 모든 빈 이름을 조회

        - 빈 객체 조회

          **`ac.getBean(빈이름, 타입)`** : 빈 인스턴스 조회

          **`ac.getBean(타입)`** : 빈 인스턴스 조회(같은 타입의 스프링 빈이 둘 이상이면 예외 발생)

          **`ac.getBeansOfType(타입)`** : 해당 타입의 모든 빈 조회

          **`getRole()`** : 스프링 내부에서 사용하는 빈과 사용자가 등록한 빈을 구분할 수 있다.

          **`BeanDefinition.ROLE_APPLICATION`** : 일반적으로 사용자가 정의한 빈

          **`BeanDefinition.ROLE_INFRASTRUCTURE`** : 스프링이 내부에서 사용하는 빈


> Reference
>
>
> 김영한 - 스프링 핵심원리 강의
>
> [https://velog.io/@gehwan96/Spring-Boot-ApplicationContext에-대해-알아보자](https://velog.io/@gehwan96/Spring-Boot-ApplicationContext%EC%97%90-%EB%8C%80%ED%95%B4-%EC%95%8C%EC%95%84%EB%B3%B4%EC%9E%90)
>
> https://ittrue.tistory.com/221
>
> https://velog.io/@hosunghan0821/Spring-Spring-bean-life-cycle
>
## 4️⃣ 스프링 어노테이션을 심층 분석해요

- 어노테이션이란 무엇이며, Java에서 어떻게 구현될까요?
- 스프링에서 어노테이션을 통해 Bean을 등록할 때, 어떤 일련의 과정이 일어나는지 탐구해보세요.
- `@ComponentScan` 과 같은 어노테이션을 사용하여 스프링이 컴포넌트를 어떻게 탐색하고 찾는지의 과정을 깊게 파헤쳐보세요.

---

### 스프링 어노테이션이란?

**자바 어노테이션(Java Annotation)**은 자바 소스 코드에 추가하여 사용할 수 있는 **메타데이터의 일종**이다. 보통 `@` 기호를 앞에 붙여서 사용한다. JDK 1.5 버전 이상에서 사용 가능하다. 자바 애너테이션은 클래스 파일에 임베디드되어 컴파일러에 의해 생성된 후 자바 가상머신에 포함되어 작동한다. - 위키백과

중요한 내용은 `메타 데이터의 일종이다.` 라는 구문이다. `@`를 붙이는 것으로 해당 클래스, 메서드, 필드에게 메타 데이터를 추가하고, 어떠한 기술을 통해 원하는 동작을 수행시키는 것이 Spring에서 하는 일이다.

### 스프링 빈 등록

아래 어노테이션들은 Spring에서 사용되는 컴포넌트인 `Bean`과 관련된 어노테이션들이다.

### `@Bean`

이름과 같이 Bean을 등록하는 어노테이션이다. 해당 어노테이션의 docs를 보면 다음과 같이 작성되어 있다.

> Indicates that a method produces a bean to be managed by the Spring container.
>

`@Bean`은 아래 다른 어노테이션과 다르게 메서드에 붙여서 Bean을 등록한다. Bean은 기본적으론 메서드의 이름을 `camel case`로 변경한 id로 등록이 되지만 name을 입력하면 그 값으로 등록된다.

```java
@Bean
public MemberService memberService(MemberRepository memberRepository){
    return new MemberServiceImpl(memberRepository());
}

@Bean(name="jdbcMemberRepository")
public MemberRepository memberRepository(){
    return new JDBCMemberRepository();
}

@Bean
public MemberRepository jpaMemberRepository(){
    return new JpaMemberRepository();
}
```

### `@Component`

컴포넌트 스캔을 통해서 감지되어 자동으로 Bean이 등록될 후보 클래스를 명시한다. 앞서 언급한 것처럼 Method가 아닌 Class단위에 붙이는 어노테이션이다. 아래 등장하는 `@Controller` 부터 `@Configuration` 까지는 전부 내부에 `@Component` 어노테이션을 가지고 있어, 컴포넌트 스캔의 대상이 된다.

```java
@Component
public class MemberServiceImpl implements MemberService{
}
```

### `@Controller` `@RestController`

Spring에서 이용될 Controller를 명시하는 어노테이션이다. 아래 등장할 @RequestMapping과 같이 매핑할 URL을 통해 요청을 받는다. `@Controller` VIEW를 리턴하는데, `@ResponseBody` 어노테이션을 붙인다면, Body에 다른 값을 담을 수 있다. `@RestController`는 `@Controller` + `@ResponseBody`이다.

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface RestController {
...
}
```

### `@ControllerAdvice` `@RestControllerAdvice`

아래 Docs의 내용을 살펴보면, Controller 클래스들에게 선언된 `@ExceptionHandler`, `@InitBinder`, `@ModelAttribute`를 공유한다고 한다.

> Specialization of @Component for classes that declare @ExceptionHandler, @InitBinder, or @ModelAttribute methods to be shared across multiple @Controller classes.
>

`@ExceptionHandler`와 `@ModelAttribute`는 아래에서 다루니 이 부분에서 설명은 생략한다.

`@InitBinder` 어노테이션은 Controller에 들어오는 요청에 대한 추가 설정을 하는 것이다. 특히 `WebDataBinder`에 대한 설정을 할 수 있다. WebDataBinder와 @InitBinder에 대한 자세한 정보는 [이 블로그](https://joont92.github.io/spring/%EB%AA%A8%EB%8D%B8-%EB%B0%94%EC%9D%B8%EB%94%A9%EA%B3%BC-%EA%B2%80%EC%A6%9D/)에서 확인 가능하다.

주로 사용하는 것은 `@ExceptionHandler`와 관련된 처리를 하며, 이름에서 알 수 있듯이 `@RestControllerAdvice`는 `@ControllerAdvice`와 `@ResponseBody`가 합쳐진 것이다.

### `@Service`

Spring에서 비즈니스 로직을 처리하는 계층에 붙이는 어노테이션이다.

### `@Repository`

Spring에서 DB관련 로직을 처리하는 계층에 붙이는 어노테이션이다.

### `@Configuration`

`@Bean` 을 붙인 메서드들을 Bean으로 등록할 수 있는 어노테이션이다.

아래 내용은 Spring Docs에 작성된 내용으로, `@Configuration`의 주된 목적은 Bean을 정의하는 것이라고 한다.

> Annotating a class with @Configuration indicates that its primary purpose is as a source of bean definitions.
>

### 스프링 컴포넌트 탐색

### 1. @ComponentScan

![img_3.png](img_3.png)

- `@ComponentScan` 은 `@Component` 가 붙은 모든 클래스를 스프링 빈으로 등록한다.
- 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
    - **빈 이름 기본 전략**: MemberServiceImpl 클래스 → memberServiceImpl
    - **빈 이름 직접 지정**: 만약 스프링 빈의 이름을 직접 지정하고 싶으면 `@Component("memberService2")` 이런식으로 이름을 부여하면 된다.
    - 대부분 기본 이름을 사용하고, 특수한 경우에만 빈 이름을 직접 지정하는 것이 좋다!

### 2. @Autowired 의존관계 자동 주입

![img_4.png](img_4.png)

- **생성자에 `@Autowired` 를 지정**하면, 스프링 컨테이너가 **자동으로 해당 스프링 빈을 찾아서 주입한다.**
- 이때 기본 조회 전략은 **타입이 같은 빈**을 찾아서 주입한다.
    - `getBean(MemberRepository.class)` 와 동일하다고 이해하면 된다.
    - 더 자세한 내용은 뒤에서 설명한다.

![img_5.png](img_5.png)

- 생성자에 파라미터가 많아도 다 찾아서 자동으로 주입한다.

## 탐색 위치와 기본 스캔 대상

### 탐색할 패키지의 시작 위치 지정

모든 자바 클래스를 다 컴포넌트 스캔하면 시간이 오래 걸린다. 그래서 꼭 필요한 위치부터 탐색하도록 시작 위치를 지정할 수 있다.

```java
@ComponentScan(
		basePackages = "hello.core",
)
```

- `basePackages` : 탐색할 패키지의 시작 위치를 지정한다. 이 패키지를 포함해서 하위 패키지를 모두 탐색한다.
    - 만약 이런 시작 위치를 지정하지 않으면 모든 라이브러리를 포함하여 컴포넌트를 스캔하기 때문에,
    - `basePackages = {"hello.core", "hello.service"}` 이렇게 여러 시작 위치를 지정할 수도있다.
- `basePackageClasses` : 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.
    - 만약 지정하지 않으면 **`@ComponentScan` 이 붙은 설정 정보 클래스의 패키지가 시작 위치**가 된다.

**권장하는 방법**

개인적으로 즐겨 사용하는 방법은 패키지 위치를 지정하지 않고, **설정 정보 클래스의 위치를 나의 프로젝트 최상단**에 두는 것이다. 최근 스프링 부트도 이 방법을 기본으로 제공한다.

예를 들어서 프로젝트가 다음과 같이 구조가 되어 있으면

- `com.hello`
    - `com.hello.serivce`
    - `com.hello.repository`

`com.hello` → 프로젝트 시작 루트, 여기에 `AppConfig` 같은 메인 설정 정보를 두고, `@ComponentScan` 애노테이션을 붙이고, `basePackages` 지정은 생략한다.

이렇게 하면 com.hello 를 포함한 **하위는 모두 자동으로 컴포넌트 스캔의 대상**이 된다. 그리고 프로젝트 메인 설정 정보는 프로젝트를 대표하는 정보이기 때문에 **프로젝트 시작 루트 위치**에 두는 것이 좋다 생각한다.

참고로 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인 `@SpringBootApplication` 를 **이 프로젝트 시작 루트 위치에 두는 것이 관례이다. (그리고 이 설정안에 바로 `@ComponentScan` 이 들어있다!)**

```java
@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}
}
```

### 컴포넌트 스캔 기본 대상

컴포넌트 스캔은 @Component 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.

- `@Component` : 컴포넌트 스캔에서 사용
- `@Controller` : 스프링 MVC 컨트롤러에서 사용
- `@Service` : 스프링 비즈니스 로직에서 사용
- `@Repository` : 스프링 데이터 접근 계층에서 사용
- `@Configuration` : 스프링 설정 정보에서 사용

### 자주 사용하는 어노테이션 정리

## 요청/응답

아래 설명할 어노테이션들은 요청, 응답 시 사용되는 어노테이션들이다. 대체로 `@Controller`, `@RestController` 내부에서 사용되는 경우가 많다.

### URL Mapping

### `@RequestMapping`

요청에 대한 URL을 매핑하는 어노테이션이다. 다음과 같이 URL을 매핑할 수 있다. 아무것도 입력하지 않으면 모든 메서드에 대한 요청을 받는다. 두 번째 메서드(`hi()`)처럼 HTTP 메서드를 지정하면, 해당 메서드에 대한 응답만을 받을 수 있다.

```java
@RequestMapping("/hello")
public String hello(){
    return "hello?";
}

@RequestMapping(value = "/hi", method = RequestMethod.GET)
public String hi(){
    return "hi?";
}
```

`@RequestMapping`은 클래스 레벨에도 적용이 가능한 메서드이기 때문에, prefix가 있는 url의 경우 클래스 부분에 선언하면 쉽게 처리할 수 있다.

아래 코드에서 `member()`의 실제 url은 `/member/get`이 된다.

```java
@RequestMapping("/member")
@RestController
public class MemberController {
    @GetMapping("/get") // 실제 url은 /member/get
    public String member(){
        return "나는 멤버";
    }
}
```

아래 어노테이션들은 위에서 보여준 `@RequestMapping`의 HTTP 메서드 명시 부분을 미리 선언한 어노테이션들이다. `@RequestMapping`를 사용하는 것보다 아래 어노테이션들을 사용하는 것을 더 추천한다.

`@GetMapping` `@PostMapping` `@PutMapping` `@PatchMapping` `@DeleteMapping`

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(method = RequestMethod.GET)
public @interface GetMapping {
}
```

### 요청

아래 있는 어노테이션은 컨트롤러에서 요청을 받을 때 사용된다.

### `@RequestParam`

QueryString과 x-www-form-urlencoded에 관한 요청을 받는다.

아래 코드는 `/hello?name=...&age=...` 에 대한 요청을 받는 코드이다. 본래 Servlet에서는 Parameter가 String으로 넘어오지만, Spring에서 기본으로 설정해 주는 부분으로 인해 String, int와 같은 변수는 별도의 설정 없이 타입이 변환된다. 받겠다고 선언한 파라미터가 없을 경우 예외가 발생하며, `required=false`를 설정해 주면 예외가 발생하지 않는다.

```java
@GetMapping("/hello") // queryString
public void hello(@RequestParam("name") String name, @RequestParam("age") int age){
 ...
}

@PostMapping("/login") // x-www-form-urlencoded
public void login(@RequestParam String id, @RequestParam String password){
 ...
}
```

### `@RequestBody`

Body에 대한 요청을 처리한다. 일반적으로 Spring에서는 application/json에 대한 요청을 객체화시켜 주는 작업을 한다. 아래와 같이 Map 혹은 Java 객체로 받을 변환이 된다. 찾아보니 이는 Spring이 Jackson 라이브러리를 이용하기 때문에 json 요청을 객체로 변환시킬 수 있는 것이고, 다른 라이브러리를 이용하면 JSON이 아닌 다른 타입으로 받을 수 있다고 한다. 더 자세한 내용이 궁금하다면 이 [블로그](https://kkangdda.tistory.com/37)를 참고하길 바란다.

```java
    @PostMapping(value = "/post")
    public Map<String, Object> post(@RequestBody Map<String, Object> dto) {
        ...
    }
    @PostMapping(value = "/post")
    public Map<String, Object> post(@RequestBody RequestDto dto) {
        ...
    }
```

### `@RequestHeader`

요청에서 들어온 Header의 값을 전달받는다. 아래와 같은 형태로 이용한다.

```java
    @PostMapping(value = "/header")
    public Map<String, Object> post(@RequestHeader("EX-HEADER") String header){
        ...
    }
```

### `@PathVariable`

URL에 변수를 이용할 수 있게 해 준다. 아래와 같이 사용할 수 있다. PathVariable은 검색을 하면서도 굉장히 자주 볼 수 있는 부분이기 때문에, 이해하기 가장 쉬울 것 같다.

```java
    @GetMapping("/{memberId}")
    public String pv(@PathVariable("memberId") String memberId) {
     ...
    }
```

### `@RequestPart`

`multipart/form-data`의 데이터를 받는 데 특화된 어노테이션이다. 여러 개의 데이터가 서로 다른 타입으로 들어올 때 이용가능하다. 아래 예시 코드처럼 회원가입을 한다고 할 때 입력한 정보와 프로필 사진을 동시에 보내고 싶을 때 이용가능하다.

```java
    @PostMapping("/multipart")
    public void signUp(@RequestPart("userInfo") UserInfo userInfo, @RequestPart("profileImg")
            MultipartFile multipartFile) {

    }
```

### `@ModelAttribute`

`Spring MVC` 이용한다면 자주 사용할 어노테이션으로 요청으로부터 받은 값을 객체로 변환하여 주고, 그 객체를 `Model`에 담아준다. 이 `Model`은 RequestScope에 담기기 때문에, Forward를 한 경우엔 `HttpServletRequest`에서 꺼내줘야 한다. (`Model`에 담을 경우 템플릿 엔진에서 이용가능하다.)

```java
    @PostMapping("/item")
    public String registerItem(@ModelAttribute Item item){
        ...
    }
```

### 응답 시 사용

### `@ResponseBody`

응답을 할 때, Body에 데이터가 담긴다는 것을 명시한다. 사용하지 않을 경우, Spring에선 html 혹은 JSP, Thymeleaf와 같은 템플릿 엔진의 파일의 주소로 인식하여, 그 파일을 반환하려 한다.

### `@ResponseStatus`

응답을 할 때, 응답 코드를 설정한다. 아래와 같이 사용할 수 있다.

```java
    @PostMapping(value = "/post")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> post(@RequestBody Map<String, Object> dto) {
        ...
    }
```

> ResponseEntity라는 클래스를 리턴하는 경우에도, 응답코드를 설정할 수 있다. @ResponseStatus와 ResponseEntity가 동시에 있는 경우엔 ResponseEntity에 설정된 응답 코드가 적용된다.
>

# ETC

### `@Autowired`

의존성을 주입해 주는 어노테이션이다. Spring에서 의존성을 주입하는 방법은 3가지가 있는데, 필드 주입, setter주입, 생성자 주입에서 사용된다.

**필드 주입**

필드에 직접 `@Autowired` 어노테이션을 붙인다.

```java
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;
         ...
}
```

**setter 주입**

setter 메서드에 `@Autowired` 어노테이션을 붙인다. 런타임에서 의존성을 변경할 수 있다는 장점이 있다고 하는데, 그럴 일은 거의 없다고 한다.

```java
@RestController
public class MemberController {

    private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService){
        this.memberService = memberService;
    }
    ...
}
```

**생성자 주입**

가장 많이 사용하는 방법인 생성자 주입이다. 그리고 이 방법을 가장 권장한다. 생성자 위에 `@Autowired`를 붙여준다. 생성자가 하나밖에 없을 경우, 그 생성자 기준으로 의존성 주입이 일어난다고 한다.

```java
@RestController
public class MemberController {

    private MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }
}
```

### `@ExceptionHandler`

명시한 예외가 들어올 경우, 그에 대한 처리를 해주는 어노테이션이다. 아래와 같은 형태로 주로 사용한다. 어떤 예외를 처리할 것인지에 대해서 명시하고, 그에 해당하는 작업을 처리한다.

```java
@ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseException(HttpServletRequest request) {
        ...
    }
```

### `@Value`

설정 파일(`.properties`, `.yml`)에 입력된 값을 변수에 담을 수 있다.

```java
    @Value("${key.kakao}")
    private String key;
```

> Reference
>
>
> [https://velog.io/@kgh2120/Spring의-유용한-어노테이션-정리](https://velog.io/@kgh2120/Spring%EC%9D%98-%EC%9C%A0%EC%9A%A9%ED%95%9C-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98-%EC%A0%95%EB%A6%AC)
>

### @RestController 와 @Controller의 차이점?

근본적인 차이점은 `@Controller`의 역할은 Model 객체를 만들어 데이터를 담고 View를 찾는 것이지만, `@RestController`는 단순히 객체만을 반환하고 객체 데이터는 JSON 또는 XML 형식으로 HTTP 응답에 담아서 전송한다. 물론 `@Controller`와 `@ResponseBody`를 사용하여 만들 수 있지만 이러한 방식은 **RESTful 웹서비스의 기본 동작**이기 때문에 Spring은 `@Controller`와 `@ResponseBody`의 동작을 조합한 `@RestController`을 도입했다.

```java
@Controller
@ResponseBody
public class MVCController{
	logic...
}
 
@RestController
public class ReftFulController{
	logic...
}
```

- **RESTful API란?**

  REST를 기반으로 만들어진 API를 의미한다.

  이때 REST(Representational State Transfer)는 **자원을 이름으로 구분**하여 해당 자원의 상태를 주고받는 모든 것을 의미하게 된다.

    - **REST란**
        1. HTTP URI(Uniform Resource Identifier)를 통해 자원(Resource)을 명시하고,
        2. HTTP Method(POST, GET, PUT, DELETE, PATCH 등)를 통해
        3. 해당 자원(URI)에 대한 CRUD Operation을 적용하는 것
    - **REST 구성요소**
        1. **자원(Resource) : HTTP URI**
        2. **자원에 대한 행위(Verb) : HTTP Method**
        3. **자원에 대한 행위의 내용 (Representations) : HTTP Message Pay Load**
    - **REST 특징**
        1. Server-Client(서버-클라이언트 구조)
        2. Stateless(무상태)
        3. Cacheable(캐시 처리 가능)
        4. Layered System(계층화)
        5. Uniform Interface(인터페이스 일관성)
- **ResponseBody란?**

  ![img_6.png](img_6.png)

  HTTP 프로토콜에 의해 서버와 클라이언트가 웹에서 비동기 통신을 할 때, 본문에 데이터를 담아서 보내야 한다. 클라이언트가 요청을 할 때는 requestBody, 응답을 할 때는 responseBody를 담아서 보내게 된다.

  주로 JSON 형태의 데이터를 통해 데이터를 주고 받는데, 여기에서 `@RequestBody` 어노테이션은 HTTP 요청 바디를 자바 객체로 변환해주고 `@ResponseBody` 어노테이션은 자바 객체를 HTTP 응답 바디로 변환해주는 역할을 한다.

- **결론) RestController의 역할**

  `@Controller`와는 다르게 `@RestController`는 리턴값에 자동으로 `@ResponseBody`가 붙게되어 별도 어노테이션을 명시해주지 않아도 HTTP 응답데이터(body)에 자바 객체가 매핑되어 전달 된다. `@Controller`인 경우에 바디를 자바객체로 받기 위해서는 `@ResponseBody` 어노테이션을 반드시 명시해주어야한다.


> **Reference**
>
>
> [https://khj93.tistory.com/entry/네트워크-REST-API란-REST-RESTful이란](https://khj93.tistory.com/entry/%EB%84%A4%ED%8A%B8%EC%9B%8C%ED%81%AC-REST-API%EB%9E%80-REST-RESTful%EC%9D%B4%EB%9E%80)
>
> https://cheershennah.tistory.com/179
>
> https://mangkyu.tistory.com/49
>
> https://dncjf64.tistory.com/288

## 5️⃣ **단위 테스트와 통합 테스트 탐구**

### **단위 테스트**

- 단위테스트는 하나의 모듈을 기준으로 독립적으로 진행되는 가장 작은 단위의 테스트다.
- 하나의 모듈이란 각 계층에서의 하나의 기능 또는 메소드로 이해할 수 있다.
- 하나의 기능이 올바르게 동작하는지를 독립적으로 테스트하는 것이다.

### **단위 테스트의 필요성**

- 일반적으로 테스트 코드를 작성한다고 하면 거의 단위 테스트를 의미한다.
- 통합 테스트는 실제 여러 컴포넌트들 간의 상호작용을 테스트 하기 때문에 모든 컴포넌트들이 구동된 상태에서 테스트를 하게 되므로, 캐시나 데이터베이스 등 다른 컴포넌트들과 실제 연결을 해야하고 어플리케이션을 구성하는 컴포넌트들이 많아 질수록 테스트를 위한 시간이 커진다.
- 하지만, 단위 테스트는 테스트하고자 하는 부분만 독립적으로 테스트를 하기 때문에 해당 단위를 유지 보수 또는 리팩토링 하더라도 빠르게 문제 여부를 확인 할 수 있다.

### **단위 테스트의 한계**

- 일반적으로 어플리케이션은 하나의 기능을 처리하기 위해 다른 객체들과 데이터를 주고 받는 복잡한 통신이 일어난다.
- 단위 테스트는 해당 기능에 대한 독립적인 테스트기 때문에 다른 객체와 데이터를 주고 받는 경우에 문제가 발생한다.
- 그래서, 이 문제를 해결하기 위해 테스트하고자 하는 기능과 연관된 모듈에서 가짜 데이터, 정해진 반환값이 필요하다.
- 즉 단위 테스트에서는, 테스트 하고자 하는 기능과 연관된 다른 모듈은 연결이 단절 되어야 비로소 독립적인 단위 테스트가 가능해 진다.

### **단위 테스트의 특징**

- 좋은 테스트 코드란, 계속해서 변하는 요구사항에 맞춰 변경된 코드는 버그의 가능성을 항상 내포하고 있으며, 이를 테스트 코드로 검증함으로써 해결할 수 있어야 한다.
- 실제 코드가 변경되면 테스트 코드도 변경이 필요할 수 있으며, 테스트 코드 역시 가독성 있게 작성하여 일관된 규칙과 일관된 목적으로 테스트 코드를 작성 해야한다.
- FIRST 규칙
    - Fast : 테스트는 빠르게 동작하고 자주 가동 해야한다.
    - Independent : 각각의 테스트는 독립적어이야 하며, 서로에 대한 의존성은 없어야 한다.
    - Repeatable : 어느 환경에서도 반복이 가능해야 한다.
    - Self-Validating : 테스트는 성공 또는 실패 값으로 결과를 내어 자체적으로 검증 되어야 한다.
    - Timely : 테스트는 테스트 하려는 실제 코드를 구현하기 직전에 구현 해야한다.

### **통합 테스트**

- 모듈을 통합하는 과정에서 모듈 간 호환성을 확인하기 위한 테스트다.
- 다른 객체들과 데이터를 주고받으며 복잡한 기능이 수행 될때, 연관된 객체들과 올바르게 동작하는지 검증하고자 하는 테스트다.
- 독립적인 기능보다 전체적인 연관 기능과 웹 페이지로 부터 API를 호출하여 올바르게 동작하는지 확인한다.

### 테스트 코드 뜯어보기

```java
@SpringBootTest
@AutoConfigureMockMvc
class HelloControllerTest {
    @Autowired
    private MockMvc mvc; //MockMvc를 주입받아서 사용할 준비를 한다

    @DisplayName("DisplayName: 테스트 이름을 설정할 수 있습니다.")
    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(
                        content().string(equalTo("Greetings from Spring Boot!")));
    }
}
```

1. `@SpringBootTest`

- `@SpringBootTest`는 Spring Boot의 통합 테스트를 위한 어노테이션이다. 이를 사용하면 실제 애플리케이션의 모든 빈이 로드된 상태로 테스트가 진행된다.
- 이 어노테이션을 사용함으로써, 애플리케이션의 컨텍스트가 완전하게 로드된 상태에서 테스트할 수 있다. 즉, 실제 애플리케이션이 실행되는 환경과 유사한 상태에서 테스트가 진행된다. 이를 통해 애플리케이션의 전반적인 기능을 통합적으로 테스트할 수 있다.
- **특징**: 이 어노테이션은 주로 통합 테스트에 사용되며, 일반적으로 컨트롤러, 서비스, 리포지토리 등 여러 계층을 함께 테스트하고자 할 때 유용하다.

2. `@AutoConfigureMockMvc`

- `@AutoConfigureMockMvc`는 MockMvc를 자동으로 설정해주는 어노테이션이다. MockMvc는 Spring MVC의 동작을 테스트할 때 사용되는 유틸리티로, 실제 서버를 띄우지 않고도 MVC 동작을 모킹(mocking)하여 테스트할 수 있게 한다.
- MockMvc를 사용하면 HTTP 요청을 실제로 보내지 않고도, 컨트롤러 레벨에서의 요청 및 응답을 모의(mock)하여 테스트할 수 있다. 이 어노테이션을 통해 MockMvc 객체가 자동으로 설정되므로, 테스트 환경에서 이를 편리하게 사용할 수 있다.
- 실제 서블릿 컨테이너를 띄우지 않고도 테스트를 할 수 있으므로, 테스트 실행 속도가 빠르고, 통합 테스트보다 단위 테스트나 컨트롤러 테스트에 주로 사용된다.

```java
mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
    .andExpect(status().isOk())
    .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
```

- `MockMvcRequestBuilders.get("/")`: 루트 경로(`"/"`)에 GET 요청을 보낸다.
- `.accept(MediaType.APPLICATION_JSON)`: 응답은 JSON 형식으로 기대.
- `.andExpect(status().isOk())`: HTTP 상태 코드가 200 OK인지 확인.
- `.andExpect(content().string(equalTo("Greetings from Spring Boot!")))`: 응답 본문의 내용이 "Greetings from Spring Boot!"인지 확인.

> Reference
>
>
> [https://velog.io/@sussa3007/Spring-JUnit-Mockito-기반-Spring-단위-테스트-코드-작성](https://velog.io/@sussa3007/Spring-JUnit-Mockito-%EA%B8%B0%EB%B0%98-Spring-%EB%8B%A8%EC%9C%84-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1)

# 마치며
스프링 공부를 하다보면 가끔 서버 내부가 돌아가는 구조를 이해하지 않고 단순히 결과물만 만들어내는 행위를 반복하고 있다는 생각이 들 때가 있다. 그러한 점에서 기본기, 지식의 토대를 채우는 행위가 정말 중요하다는 것을 다시금 느낀다. 세오스에서 진행한 첫 스터디는 가장 기초적인 세션이면서 동시에 평소에 알고 있었던 것과 모르고 있었던 것을, 그리고 배웠지만 잊고 있었던 것을 전부 모아보는 시간이었던 것 같다. 그러다 보니, 기존에 기록했던 공부 내용을 가져와 채워넣으면서 분량이 생각보다 많아졌다. 기술 블로그 작성을 치일피일 미루고 있었는데, 이번을 계기로 다시 한번 기술블로그를 작성해봐야겠다는 생각으로 이번 스터디 기록을 마무리해보겠다.
다음주도 화이팅이라굿 👍🏻