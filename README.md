# spring-tutorial-20th
CEOS 20th BE Study - Spring Tutorial


# IoC/DI

## IoC (제어의 역전) 

- #### 정의 
    객체의 생성과 관리 및 객체간의 의존성 관리를 개발자가 아닌 스프링 프레임워크가 담당하는 디자인패턴
- #### 효과 
    객체 간의 결합도를 줄이고 코드를 유연하게 작성할 수 있어, 가독성을 높이고 코드 중복을 줄이며 유지 보수를 편하게 할 수 있게 해준다.

    기존에는 객체를 클래스 내부에서 생성하고 사용했다면, IoC를 적용하면 미리 생성해놓은 객체를 주입받아 사용하기만 하면 된다


- #### Spring IoC 컨테이너의 생명주기
1. **Resource 로딩** 
    
   IoC 컨테이너가 사용할 설정 파일을 메모리에 로드한다.

   설정 파일에 어떤 객체를 생성할지, 객체 간의 의존성을 어떻게 설정할지에 대한 정보가 포함되어 있어, 이를 기반으로 Bean 객체를 정의

2. **Bean 설정**

    XML, Annotation에서 정의된 Bean 객체를 구성하는 메타데이터를 읽어들여 IoC 컨테이너가 사용할 수 있는 형태로 변환

3. **Bean 인스턴스화**

    Bean 설정 단계에서 변환한 정보를 기반으로 Bean 객체를 만든다
4. **의존성 주입**

    IoC 컨테이너가 Bean 객체가 의존하는 다른 Bean 객체를 검색하고, 주입한다.
5. **Bean 초기화 콜백**

    스프링 컨테이너가 Bean을 생성한 후, 사용하기 전에 수행해야 할 추가 작업을 정의한 메서드를 호출
6. **Bean 사용**

    IoC 컨테이너가 빈을 사용
7. **Bean 소멸 콜백**

    빈이 더 이상 필요하지 않을 때, IoC 컨테이너는 빈의 소멸 메소드를 호출하고 해당 빈을 제거
8. **Resource 해제**

    Ioc 컨테이너가 종료되어 빈들도 함께 소멸된다

## DI

- #### 정의 
    의존성을 외부에서 결정하고 주입시켜준다. IoC를 구현한 방법 중 하나이다.

<의존성 외부에서 주입 받지 않은 경우>

```
public class Car {

	Tire tire;

	public Car() {
		tire = new KoreaTire();
		// tire = new AmericaTire();
	}
	
}
```

 문제점 : Car 클래스와 Tire 클래스의 결합도가 강해서 코드의 확장성이 떨어진다.

모든 Car가 KoreaTire만 사용 가능, tire를 바꾸고 싶으면 Car 클래스의 생성자를 직접 수정해야 한다. 이를 해결하기 위해 의존성 주입을 사용한다.

- 의존성 주입 방법 3가지
 1. 생성자 주입

     ```
     public class Car {
    
         Tire tire;
    
         public Car(Tire tire) {
             this.tire = tire;
         }
    
     }
     ```

     ```
     Tire tire = new KoreaTire();
    
     **Car car = new Car(tire); // 생성자 주입**
     ```

    → 생성자를 통해 의존관계를 주입받는 방법

    → 가장 권장되는 방법! 주입받는 객체를 final로 선언할 수 있어, 해당 객체가 변경되지 않도록 할 수 있다



  2. Setter 주입

    
    public class Car {
    
    	Tire tire;
    
    	public void setTire(Tire tire) {
    		this.tire = tire
    	}
    
    }
    

    
    Tire tire = new KoreaTire();
    
    Car car = new Car();
    
    car.setTire(tire); // setter 주입
    

→ setter 에서 tire 를 받아서 tire 필드를 설정


3. 필드 주입

    ```
    import org.springframework.beans.factory.annotation.Autowired;
    
    public class Car {
    
    	@Autowired
    	@Qualifier("koreaTire")
    	Tire tire;
    
    }
    ```

    ```
    import org.springframework.stereotype.Component;
    
    @Component
    @Qualifier("koreaTire")
    public class KoreaTire implements Tire {
        // ...
    }
    ```

    ```
    import org.springframework.stereotype.Component;
    
    @Component
    @Qualifier("americaTire")
    public class AmericaTire implements Tire {
        // ...
    }
    ```

    → Tire 구현체가 여러 개라면 어떤 빈을 주입받아야 하는지 알 수 없는 문제가 발생할 수 있는데, 이때 Qualifier를 사용해 빈을 지정해줄 수 있다

---


# AOP (관점지향 프로그래밍)

- 핵심 로직 사이에 부가 로직을 끼워넣는 작업
- 여러 모듈에 공통적으로 나타나는 로직에 해당하는 ‘횡단 관심사’(공통 관심 사항)을 각 모듈 별 핵심 로직에 해당하는 ‘핵심 관심 사항’에서 분리해 별도의 모듈로 관리하도록 하고, 이 횡단 관심사를 어디에 적용할지 선택하는 기능을 합한 하나의 모듈

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f4e3bff1-23e8-4efd-9422-ea72f614eb9e/66821e51-89fe-45b9-a541-120c39cdeef5/image.png)

- 횡단 관심사를 Aspect라는 단위로 분리하고, 애플리케이션의 특정 지점(JoinPoint)에 적용하여 소스 코드의 중복을 줄인다
  - 주요 용어
     - Aspect : Advice + Pointcut으로, AoP에서 횡단관심사를 aspect로 모듈화

        언제 어디서 무엇을 할 지 명시되어 있다

     - Advice : Target에 제공할 부가 작업을 담고 있는 모듈 (Aspect가 해야 하는 “작업”)
     -  Target : Advice가 적용될 객체
     - Join Point : Advice를 적용할 수 있는 지점들 (method진입시점, 생성자 호출 시점 등 target 객체가 구현한 인터페이스의 모든 메서드)
     - Pointcut : JoinPoint 중 Advice가 적용되는 지점을 결정


- #### 스프링이 제공하는 AoP 방법
    - @Asepct 어노테이션을 붙여 Aspect를 나타내는 클래스임을 명시 + @Component 어노테이션으로 빈으로 등록하기
    - Pointcut 설정 (”execution(* 패키지명.interface명.메소드명)”)
    - Advice 설정


  ex) com.exmaple.demo 패키지 하위에 있는 메소드 실행시간 체크하기

    ```
    @Aspect
    @Component
    public class TimeTraceAop {
    
    		@PointCut("execution(* com.example.demo..*(..)")  //com.exmaple.demo 패키지 하위에 있는 것에 다 적용해라
        private void Time(){}
        
        @Around("Time()") 
        public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{ //ProceedingJoinPoint : Advice가 적용되는 대상을 가리킨다
            long start=System.currentTimeMillis();
            System.out.println("Start : "+joinPoint.toString());
        
            Object result=joinPoint.proceed();  //메소드 실행
          
    	      long finish=System.currentTimeMillis();
            long timeMs=finish-start;
            System.out.println("Finish :"+joinPoint.toString() + timeMs + "ms" );
            }
    
        }
    }
    ```



- #### 메서드에 “언제” 로직을 주입하느냐에 따른 AoP annotation
     @Before : 대상 메서드가 실행되기 전에 Advice를 실행
  
     @After : 대상 메소드 실행 후 Advice를 실행 (성공적으로 실행되었든, exception을 던지든 상관없이 실행된다)
  
     @Around : 대상 메서드 실행 전, 후 또는 예외 발생 시에 Advice를 실행
  
     @AfterReturning : 대상 메서드가 “정상적으로 실행”되고 난 뒤 Advice를 실행 (exception 발생했을 때는 수행하지 않음)
  
     @AfterThrowing : 대상 메서드에 예외가 발생했을 때 Advice를 실행

---
# PSA

- 다양한 기술들을 인터페이스를 이용해 추상화하여 개발자가 일관된 방식으로 코드를 작성할 수 있도록 하는 추상화 구조

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f4e3bff1-23e8-4efd-9422-ea72f614eb9e/d4e8fa43-9138-4167-a395-4ea21b6e0e01/image.png)

OracleJdbcConnector, MariaDBJdbcConnector, SQLiteJdbcConnector와 같은 구현체에 직접적으로 연결해서 얻는 것이 아니라 JdbcConnector 인터페이스를 통해 간접적으로 연결되어 Connection 객체를 얻기 때문에 일관된 방식으로 기능을 사용할 수 있다

---

# Spring Bean

### Spring Bean이란

- Bean : 스프링 컨테이너에 의해 인스턴스화되고, 조립되고, 관리되는 객체
- Spring Bean : 스프링 컨테이너에 등록된 객체. 주로 @Bean, @Component, @Service 같은 어노테이션을 붙여 특정 객체를 빈으로 등록

### Spring Bean의 Life Cycle

스프링 컨테이너 생성 → Bean의 생성 → 의존성 주입 → 초기화 콜백 → Bean의 사용 → 소멸 전 콜백 → 스프링 종료

- #### 빈 생명주기 콜백 관리 방법은 3가지
    - 인터페이스 (InitializaingBean, DisposableBean 이용)

    ```
    public class ExampleBean implements InitializingBean, DisposableBean {
         @Override
         public void afterPropertiesSet() throws Exception {
             // 초기화 콜백
         }
          @Override
          public void destroy() throws Exception {
              // 소멸 전 콜백
          }
      }
    
    ```

  - 설정 정보에 초기화 메소드, 종료 메소드 지정

    ```
    public class ExampleBean {
         public void initialize() throws Exception {
             // 초기화 콜백 (의존관계 주입이 끝나면 호출)
         }
          public void close() throws Exception {
              // 소멸 전 콜백 (메모리 반납, 연결 종료와 같은 과정)
          }
      }
       
       
      @Configurationclass LifeCycleConfig {
            @Bean(initMethod = "initialize", destroyMethod = "close")
            public ExampleBean exampleBean() {
                // 생략
            }
        }
    
    ```

  - **@PostConstruct, @PreDestroy 어노테이션 사용** → 가장 권장되는 방법!

    ```
    public class ExampleBean {   
    
    //초기화 콜백  
    @PostConstruct    
    public void initialize() throws Exception {}     
    
    //소멸 전 콜백
    @PreDestroy    
    public void close() throws Exception {}
    }
    
    ```


---

# Spring Annotation

**1. 어노테이션이란?**

- 프로그램에게 추가적인 정보를 제공해주는 메타데이터 (데이터를 위한 데이터)

**2. 용도**

- 컴파일러에게 코드 작성 문법 에러를 체크하도록 정보 제공
- 소프트웨어 개발 툴이 빌드나 배치 시 코드를 자동으로 생성할 수 있도록 정보 제공
- 실행 시 (런타임) 특정 기능을 실행하도록 정보 제공

**3. 어노테이션 종류**

1) JDK 표준 어노테이션

   : 자바에서 기본적으로 제공하는 어노테이션 

    ex) @Override, @Deprecated, @SuppressWarnings

2) meta 어노테이션

   : 어노테이션을 정의할 때 사용되는 어노테이션으로, 어노테이션을 위한 어노테이션이다

    - @Retention : 어노테이션의 정보를 어느 범위까지 유지할 지 결정
        - RetentionPolicy.SOURCE : 컴파일 전까지만 유효하며 컴파일 이후에는 사라짐
        - RetentionPolicy.CLASS : 컴파일러가 클래스를 참조할 때까지 유효
        - RetentionPolicy.RUNTIME : 컴파일 이후에도 JVM에 의해 계속 참조 가능. 즉, Java로 작성한 코드가 돌아가는 동안에 계속 유지된다
    - @Target : 생성할 어노테이션이 적용될 수 있는 위치를 나열
        - ElementType.TYPE : 클래스, 인터페이스, enum에만 어노테이션 사용가
        - ElementType.METHOD : 메소드 선언시 어노테이션 사용 가능
    - @Documented : 어노테이션 정보가 javadoc 문서에 포함되도록 한다
    - @Inherited : 하위 클래스가 어노테이션을 상속받을 수 있도록 한다
    - @Repeatable : 반복적으로 어노테이션을 선언할 수 있다


3) 커스텀 어노테이션

   : 사용자가 메타 어노테이션을 이용하여 직접 구현한 어노테이션

    ```
    @Target({ElementType.[적용대상]})
    @Retention(RetentionPolicy.[정보유지 범위])
    public @interface [어노테이션명]{
    	public 타입 elementName() [default 값]
        ...
    }
    ```
    
   ---

   ## 스프링 빈 등록방법

1. 빈을 수동으로 등록 - @Bean 과 @Configuration 이용하기

    ```
    @Configuration
    public class ResourceConfig {
    
        @Bean
        public myBean myBean() {
            return new myBean();
        }
    
    }
    ```

   설정 클래스 파일에 @Configuration 어노테이션을 적용해주고, 그 안에서 스프링 빈으로 등록하고싶은 메소드에 대해 @Bean 어노테이션을 적용시켜주면 스프링 컨테이너에 빈이 등록된다.

    - 과정 : 스프링 컨테이너는 @Configuration이 붙어있는 클래스를 자동으로 빈으로 등록해주고, 해당 클래스를 파싱해서 @Bean이 있는 메소드를 찾아 빈을 생성한다
    - @Bean을 사용하는 클래스에는 반드시 @Configuration 어노테이션을 활용해서 해당 클래스에서 빈을 등록하겠다고 명시해주어야 한다.

      → @Bean 객체가 싱글톤으로 등록됨을 보장받기 위해

      → 싱글톤이란? : 어떠한 객체를 **단 한 번만 생성**하고, 생성된 객체를 **어디서든지 참조**할 수 있도록 하는 패턴

   
   2. Bean을 자동으로 등록 - @Component 이용하기 → 권장!

   스프링은 Component Scan을 사용해 @Component 어노테이션이 있는 클래스들을 찾아 자동으로 빈으로 등록해준다.

    
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Component
    public @interface Configuration {
    
        @AliasFor(annotation = Component.class)
        String value() default "";
    
        boolean proxyBeanMethods() default true;
    
    }
    

   @Configuration의 하위 어노테이션으로 @Configuration, @Repository, @Service, @Controller, @RestController 등이 있다.

    - @Bean과 @Component의 차이점
        - @Bean은 메소드에 적용시키고, 개발자가 직접 변경하기 어려운 대상(외부 라이브러리)에 사용하는 것이 권장됨
        - @Component는 클래스에 사용하고, 개발자가 직접 변경이 가능한 대상에 사용
---

   ## 컴포넌트 스캔의 동작과정

   **@ComponentScan이란**

   @ComponentScan 어노테이션이 작성된 패키지 이하의 모든 클래스들을 순회하여 Bean을 찾아 Spring Container에 등록한다. 스프링부트에서는 @SpringBootApplication이 @ComponentScan을 들고있다.

   ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f4e3bff1-23e8-4efd-9422-ea72f614eb9e/afa8089d-8653-4a04-acbb-407d1275331b/image.png)

   스프링부트는 `@SpringBootApplication` 어노테이션이 선언된 클래스의 패키지 및 그 하위 패키지만 스캔하기 때문에 내가 생성하는 클래스들을 Spring Boot 애플리케이션이 스캔하는 패키지 내에 있도록 해주어야 한다.

   ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f4e3bff1-23e8-4efd-9422-ea72f614eb9e/923ebe92-ac6d-4501-9b28-0cbab889f93a/image.png)

   위 사진과 같으면 클래스들이 @Application이 속한 com.ceos20.spring_boot 패키지 내에 있지 않아 내가 생성한 클래스들을 스캔하지 못하여 오류 생긴다.

   ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f4e3bff1-23e8-4efd-9422-ea72f614eb9e/72e61bc5-4a94-4acd-804b-738db521e1b8/image.png)

   따라서 com.ceos20.spring_boot 패키지 내에 클래스들이 위치하도록 해주어야 한다.

   **@ComponentScan 옵션**

   1) @ComponentScan의 탐색 범위를 직접 지정할 수 있다.

    
    @ComponentScan(basePackage = "경로")
    

   basePackage : 탐색할 패키지의 시작위치. 하지만 default 방식(@SpringBootApplication이 존재하는 패키지)을 권장한다.

   2) includeFilters & excludeFilters

   - includeFilters : ConponentScan의 대상이 아니지만, ComponentScan에 포함시켜야 하는 경우에 사용

    
    @ComponentScan(
         includeFilters = {
              @Filter(type = FilterType.상수, classes = 클래스명.class) })
    

- excludeFilters : ConponentScan의 대상이지만, ComponentScan에서 제외시켜야 하는 경우에 사용

    ```
    @ComponentScan(
         excludeFilters = {
              @Filter(type = FilterType.상수, classes = 클래스명.class) })
    ```



   **FilterType종류**

- ANNOTATION : 주어진 어노테이션이 명시된 컴포넌트를 필터
    - ASSIGNABLE_TYPE : 주어진 타입을 필터
    - ASPECTJ : AspectJ 패턴을 사용하여 필터
    - REGEX : 정규식을 이용한 필터
    - CUSTOM : 직접 만든 필터를 이용한 필터

    ex) Ignore라는 어노테이션이 붙인 빈들은 스캔에 포함시키지 않으려면 아래와 같이 필터링 할 수 있다.

    
    @ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Ignore.class))
    

   **@ComponentScan 동작과정**

    1. @Configuration 어노테이션 클래스를 파싱
    2.  ComponentScan 설정을 파싱한다.
    3.  base-package 밑의 모든 클래스들을 로딩하여 component 후보인지 확인한다.
    4.  맞다면 빈 생성을 위한 정의
    5.  이 정의를 토대로 빈을 생성하게 된다.

    ---

   # 테스트

   ## 단위 테스트

   소프트웨어의 개별 구성 요소 또는 모듈이 예상대로 작동하는지 검증하는 과정이다. 주로 메서드, 클래스와 같은 소프트웨어의 가장 작은 테스트 가능한 부분에 대한 테스트를 진행한다.

   개별 기능을 검증하는 것에 초점을 맞춘다.

   ## 통합 테스트

   소프트웨어의 개별 구성 요소들이 모여 전체 시스템에서 올바르게 동작하는지 검증하는 테스트 방법이다. 모듈이 서로 통합되어 상호작용할 때 발생할 수 있는 문제를 찾아내고 해결하는데 중점을 둔다.

---
   Reference : https://engineerinsight.tistory.com/66#google_vignette , [https://ittrue.tistory.com/214 ,](https://ittrue.tistory.com/214) https://mihee0703.tistory.com/207  ,  https://dev-coco.tistory.com/170  , https://zzang9ha.tistory.com/453
