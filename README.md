## 엔티티 매핑

* 객체와 테이블: @Entity, @Table
* 필드와 컬럼: @Column
* 기본 키: @Id
* 연관관계: @ManyToOne, @JoinColumn


---

### 객체와 테이블 매핑

#### @Entity

* @Entity 어노테이션이 붙은 클래스는 JPA 에서 관리, 엔티티라 부름
* JPA 를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
* 주의 사항
  * 기본 생성자 필수
  * final 클래스, enum, interface, inner 클래스 사용 불가
  * 저장할 필드에 final 사용 불가
* property 
  * name: JPA에서 사용할 엔티티 이름을 지정
  * name 기본값으로 클래스 이름을 그대로 사용, 가급적이면 기본값 사용을 추천


#### @Table

* @Table 은 엔티티와 매핑할 테이블 지정
* property
  * name: 매핑할 테이블 이름
  * catalog: 데이터베이스 catalog 매핑
  * schema: 데이터베이스 schema 매핑
  * uniqueConstraints: DDL 생성 시에 유니크 제약 조건 생성


### 데이터베이스 스키마 자동 생성

* DDL 을 어플리케이션 실행 시점에 자동 생성
* 테이블 중심 -> 객체 중심
* 데이터베이스 방언을 활용해서 데이터베이스에 맞는 DDL 생성
* 개발 장비에서만 사용하고 절대 운영서버에서는 사용 금지


    <property name="hibernate.hbm2ddl.auto" value=""/>

* value
  * create: 기존 테이블 삭제 후 다시 생성(DROP + CREATE)
  * create-drop: create 와 같으나 DROP 을 종료시점에
  * update: 변경분만 반영(운영 DB 사용금지)
  * validate: 엔티티와 테이블이 정상 매핑되었는지 확인
  * none: 사용하지 않음, none 은 없는 value 설정 값으로 위에 해당하지 않은 모든 값 동일


#### 데이터베이스 스키마 자동 생성 - 주의

* 운영 장비에는 절대 create, create-drop, update 사용하면 안됨
* 개발 초기 단계 create or update
* 테스트 서버 update or validate
* 스테이징, 운영 서버 validate or none


위 와 같이 기준을 정해주었지만 테스트 서버, 스테이징은 validate 를 권장하고
운영 서버에는 none 조차도 사용하지 않은 것을 추천하는 것 같다
김영한 강사: 데이터베이스 스키마 자동 생성을 사용하면서 큰 사고를 경험하고 얘기함

#### DDL 생성 기능

* DDL 을 자동 생성할 때 사용되며 JPA 실행 로직에는 영향을 주지 않음
* property
  * nullable: true, false 로 null 가능 여부
  * length: 입력 값 제한
  * unique: true, false 로 DB unique 설정


### 컬럼과 매핑

#### @Column

* property
  * name: 필드와 매핑할 테이블의 컬럼 이름
  * insertable, updatable: 등록, 변경 가능 여부
  * nullable(DDL): null 값 의 허용 여부 설정, false = not null
  * unique(DDL): @Table uniqueConstraints 와 같지만 컬럼에 간단히 설정 가능
  * columnDefinition: 데이터베이스 컬럼 설정을 직접 하는 방법
    * varchar(100) default 'EMPTY' 와 같이 직접 입력
  * length(DDL): 문자 길이 제약조건, String Type
  * precision, scale(DDL): BigDecimal, BigInteger Type 사용
    * precisionL: 소수점을 포함한 전체 자릿수
    * scale: 소수의 자리수
    * 정밀한 소수를 다룰 때 사용

#### @Temporal

  JDK 1.8 부터 사용할 수 있는 LocalDate, LocalDateTime 을 사용하는 경우 TemporalType 을 사용하지 않아도 됨
  (객체:DB): LocalDate -> date, LocalDateTime -> timestamp 로 적용

* property
  * value - TemporalType.DATE: 날짜, 데이터베이스 date 타입과 매핑 (2022-01-01)
  * value - TemporalType.TIME: 시간, 데이터베이스 time 타입과 매핑 (12:12:12)
  * value - TemporalType.TIMESTAMP: 날짜와 시간, 데이터베이스 timestamp 타입과 매핑 (2022-01-01 12:12:12)

#### @Enumerated

  EnumType.ORDINAL 를 사용하는 경우 치명적인 에러가 발생할 수 있음
  기존에 USER, ADMIN 2개의 값 을 가진 enum 클래스를 사용 중
  GUEST 가 필요하여 GUEST, USER, ADMIN 순으로 사용하게 된 경우
  기존의 값을 USER = 0 -> 1, ADMIN = 1 -> 2 로 변경한 후 적용하면 사용 가능하나
  문자열을 사용하는 경우 이런 고민 조차 할 필요가 없다

* property
  * value - EnumType.ORDINAL: enum 순서를 데이터베이스에 저장
  * value - EnumType.STRING: enum 이름을 데이터베이스에 저장


#### @Lob

* 데이터베이스 BLOB, CLOB 타입과 매핑
* @Lob 에는 속성이 없음
* 매핑하는 필드 타입이 문자면 CLOB, 나머지는 BLOB 매핑
  * CLOB: String, char[], java.sql.CLOB
  * BLOB: byte[], java.sql.BLOB

#### @Transient

* 필드 매핑을 원하지 않은 경우
* 데이터베이스에 저장, 조회를 하지 않음
* 메모리에 임시로 어떤 값을 보관하고 싶은 경우

### 기본 키 매핑

* 직접 할당: @Id 어노테이션만 사용
* 자동 생성: @GeneratedValue


#### @GeneratedValue - 