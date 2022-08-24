## 기본값 타입

---

### 값 타입

#### JPA 의 데이터 타입 분류 - 최상위 타입

* 엔티티 타입
  * @Entity 로 정의하는 객체
  * 데이터가 변해도 식별자로 지속해서 추적 가능
* 값 타입
  * int, Integer, String 같이 값으로 사용하는 기본 타입
  * 변경시 추적 불가


#### JPA 의 데이터 타입 분류 - 값 타입

* 기본값 타입(자바 기본 타입, 래퍼 클래스(Integer), String)
  * 생명주기가 엔티티에 의존
  * 값 타입은 절대 공유 불가
* 임베디드 타입(복합 값 타입으로 무언가 묶어서 사용하고 싶을 때)
* 컬렉션 값 타입


### 임베디드 타입

* 임베디드 타입의 값이 null 이면 컬럼들 값 모두 null
* 장점
  * 재사용, 높은 응집도
  * 임베디드 타입도 값 타입이므로 엔티티 생명주기에 의존


적용 전

```java
@Entity
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
    
    private String city;
    private String address;
    private String zipcode;
}
```

적용 후

```java
@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
  
    private String name;
  
    @Embedded
    private Period period;
  
    @Embedded
    private Address address;
}

@Embeddable
public class Period {
    private String createdBy;
    private LocalDateTime createdDate;
    private String modifiedBy;
    private LocalDateTime modifiedDate;
}

@Embeddable
public class Address {
    private String city;
    private String street;
    private String zipcode;
}
```

#### 같은 임베디드 타입 2개 사용 방법

```java
@Entity
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
  
    private String name;
  
    @Embedded
    private Period period;
  
    @Embedded
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "second_city")),
            @AttributeOverride(name = "street", column = @Column(name = "second_street")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "second_zipcode"))
    })
    private Address secondAddress;
}
```

### 값 타입과 불변 객체

#### 값 타입 공유 참조

* 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험
* side effect 발생
* 불변 객체를 사용


#### 값 타입의 비교

* 임베디드 타입 같은 객체 비교에는 반드시 equals, hashcode 를 재정의 해서 사용
* primitive type = 동일성 비교, 그 외 나머지 타입은 동등성 비교 사용


### 값 타입 컬렉션

* 값 타입을 하나 이상 저장할 때
* @ElementCollection, @CollectionTable 사용
* 데이터베이스는 컬렉션을 테이블에 저장할 수 없음 컬렉션을 저장히기 위한 별도의 테이블 필요
* default 지연 로딩 사용
