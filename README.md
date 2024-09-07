# spring-tutorial-20th
CEOS 20th BE Study - Spring Tutorial

## 2. spring이 지원하는 기술들(IoC/DI, AOP, PSA 등)을 자유롭게 조사해요

스프링을 이용하여 만든 애플리케이션에서 사용하는 인스턴스의 생애주기를 관리하고 의존성을 관리하는 IoC 컨테이너를 제공합니다.
스프링이 관리하는 인스턴스를 스프링 빈이라고 합니다.

IoC는 제어의 역전이라는 개념을 의미합니다.
기존에 개발자가 직접 객체의 생명 주기와 의존성을 관리했다면,
IoC를 통해 이를 프레임워크에 맡기게 됩니다.

### 제어가 역전되는 과정
```
public class DataAcessObject{
    private DataSource dataSource;
    
    public DataAcessObject(){
        this.dataSource = new dataSource();
    }
}


```
DAO(Data Access Object)클래스가 있고
DAO 클래스는 DataSource 클래스에 직접적으로 의존하고 있습니다.
DAO 객체가 생성될 때 DataSource 객체도 함께 생성됩니다.
개발자가 직접 의존성을 관리하고 있습니다

```
public class DataAcessObject{
    private DataSource dataSource;
    
    public DataAcessObject(DataSource dataSource){
        this.dataSource = dataSource;
    }
}

```
DAO 클래스는 이제 DataSource 클래스에 직접적으로 의존하지 않습니다.
대신, Spring IoC 컨테이너를 통해 필요한 의존성이 주입됩니다.
이를 통해 객체 간의 결합도가 낮아지고,
코드의 유연성과 확장성이 향상됩니다.

IoC를 사용하면, 각 객체는 그들의 의존성을 외부에서 주입받게 되므로,
객체 간의 느슨한 연결이 가능해집니다.
그리고 코드의 유연성 및 확장성도 좋아집니다.
의존성 주입을 통해 개발자는 기존 코드를 변경하지 않고도
새로운 기능을 추가하거나 기존 기능을 변경할 수 있습니다.
마지막으로 실제 객체 대신 목업 객체를 주입하여
단위 테스트를 쉽게 수행할 수 있습니다.

## 3. Spring Bean 이 무엇이고, Bean 의 라이프사이클은 어떻게 되는지 조사해요

스프링은 애플리케이션의 복잡성을 줄이고 유지보수를 용이하게 하기 위해 객체의 생성, 설정 및 생명주기를 관리하는 스프링 컨테이너를 제공합니다.
이 컨테이너가 관리하는 객체를 스프링 빈이라고 하며, 이를 통해 의존성 주입 및 객체 관리가 자동화됩니다.

스프링 빈은 스프링 빈은 스프링 컨테이너 내부에서 생성되고, 스프링이 종료되기 전까지 생명주기(Life-Cycle)을 가지고 있습니다.
스프링 IoC 컨테이너 생성 → 스프링 빈 생성 → 의존관계 주입 → 초기화 콜백 메소드 호출 → 사용 → 소멸 전 콜백 메소드 호출 → 스프링 종료.

출처: https://dev-coco.tistory.com/170 [슬기로운 개발생활:티스토리]


## 4. 스프링 어노테이션을 심층 분석해요
   
Component Scan은 스프링 컨테이너에 등록된 스프링 빈을 자동으로 찾아서 등록하는 방법입니다.

@ComponentScan 어노테이션을 이용하여 스캔할 패키지를 지정할 수 있습니다.
```
...
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
        @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {
  ...
}

```
이 코드에서 @ComponentScan은 지정된 패키지(기본적으로 @SpringBootApplication이 선언된 클래스의 패키지)에서 빈을 스캔합니다.
그러나 **TypeExcludeFilter**와 **AutoConfigurationExcludeFilter**에 의해, 특정 타입이나 자동 설정과 관련된 클래스들은 스캔에서 제외됩니다.
그 외의 빈들은 모두 스프링 컨텍스트에 자동으로 등록됩니다.
## 5. 단위 테스트와 통합 테스트 탐구
테스트 범위에 따라 구분되는데, 유닛 테스트는 함수 하나하나와 같이 코드의 작은 부분을 테스트하는 것이고, 
통합 테스트는 서로 다른 시스템들의 상호작용이 잘 이루어 지는지 테스트하는 것입니다. 

유닛 테스트는 다른 컴포넌트들과 독립적으로 이루어지고, 예를 들어 함수 하나하나 개별로 테스트 코드를 작성하는 것입니다. 
반면 통합 테스트는 다른 컴포넌트들과 상호작용하는지 테스트 해야하고, 예를 들어 데이터베이스와 통신이 원활한지 확인하는 테스트입니다. 
