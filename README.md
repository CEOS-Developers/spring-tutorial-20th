# spring-tutorial-20th
CEOS 20th BE Study - Spring Tutorial


## tutorial 을 하면서 발생한 issue ...

---

### 1. 개발 환경 세팅
- IDE 설치 : **IntelliJ IDEA** / Eclipse IDE / VSCode
- Java 17 or later
- Gradle 7.5+ / Maven 3.5+
- Postman : 필수적인 것은 아니지만 편의성을 위해 설치합니다

[spring boot Guide](https://spring.io/guides/gs/spring-boot) / 
[velog reference](https://velog.io/@uijeong/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%8B%9C%EC%9E%91%ED%95%98%EA%B8%B0#-jdk-mavengradle-eclipse-%EC%84%A4%EC%B9%98%ED%95%98%EA%B8%B0)
### 2. java version 관련 build error
spring boot ver 3.0 부터는 Java 17 이상을 사용해야 한다고 해서 처음에 Java 22를 설치했다가 build error를 맞이했습니다. spring boot initializer에서 선택한 language에 맞추어 버전 설치하기!! 
### 3. 파일 구조 
Q1) `Test`, `TestController`, `TestRepository`, `TestService` class들이 test 폴더가 아닌 main 폴더에 있는 이유
- test 폴더는 `단위 테스트`에 사용되는데 해당 테스트 파일들을 작성한 목적은 `통합 테스트`이기 때문?
- 만약 test 폴더에 있다면 TestConroller 실행하면 되는건가요?!

Q2) `com.ceos20.spring_boot` 폴더 내부에 넣는 것과 외부에 넣는 것에 어떤 차이가 있는지?
- 뒤에 4번에서 다시...
### 4. buile 후 `TestController`에서 HTTP Request를 보내고 실행했을 때 404 error
GET `http://localhost:8080/tests` 요청에 대해 404 error가 발생했습니다

`Application`을 실행했을 때 `@SpringBootApplication` 작업이 먼저 실행되게 되는데, 이 때 scan하는 component의 범위가 **같은 폴더**입니다!!! <br/>
따라서 `TestController` 내부 컴포넌트까지 scan되기 위해서는 해당 파일이 `com.ceos20.spring_boot`폴더 내에 (`Application`과 같은 폴더) 존재해야 합니다 - _3번의 Q2에 대한 답변_
### 5. DB가 우선, 코드는 나중
h2 DB 서버에서 TEST table을 생성할 때 id나 name의 자료형을 자율적으로 설정할 수 있고, TEST 이외에 다른 table도 자유롭게 생성이 가능하다는 점을 확인했습니다.<br/>
코드를 보면 `TestController`의 기능이 DB에 있는 TEST 테이블을 가져오는 것이기 때문에 실제 DB에 id, name 외에 다른 column이 존재해도 무방합니다.<br/>

그렇다면 나아가서 백엔드 개발을 할 때, DB가 우선이 되고 그 데이터에 맞추어서 코드를 작성하는 것인가? 라는 궁금증으로 이어졌습니다.
```java
public class Test {
    @Id
    private Long id; // 이런 타입지정을 할 때, DB의 자료형을 확인한 후 맞춰서 지정하기
    private String name;
}
```

## 