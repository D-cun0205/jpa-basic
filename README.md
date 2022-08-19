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