## 객체지향 쿼리 언어 - 기본 문법

---

### 객체지향 쿼리 언어 소개

#### JPA 의 다양한 쿼리 방법 지원

* JPQL
* JPA Criteria
* QueryDSL
* 네이티브 SQL
* JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용


#### JPQL

* 엔티티 객체를 대상으로 쿼리
* 단순한 String 문자열이므로 오타로 인한 잠재적 문제를 가질 수 있음


#### Criteria

* JPQL 의 잠재적 문제를 해결하기 위한 방안
* 분기를 통해서 동적 쿼리 작성 가능
* JPA 표준 스펙이나 너무 복잡하고 실용성이 없음


```java
class JPQL {
    void query() {
        // 기본적인 사용 방법
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        Root<Member> m = query.from(Member.class);

        CriteriaQuery<Member> cq = query.select(m);

        // 동적 쿼리
        String username = "dcun";
        if (username != null) {
            cq = cq.where(cb.equal(m.get("username"), "kim"));
        }

        List<Member> resultList = em.createQuery(cq).getResultList();
    }
}
```

#### QueryDSL

* 문자가 아닌 자바코드로 JPQL 작성
* JPQL 빌더 역할
* 컴파일 시점에 문법 오류 확인
* 동적 쿼리 작성 편리
* 실무에 사용 권장


#### 네이티브 SQL

    EntityManager.createNativeQuery()

* JPA 가 제공하는 SQL 직접 사용
* 특정 데이터베이스에 의존적인 기능에 사용


#### JDBC, SpringJdbcTemplate

* 주의사항으로 영속성 컨텍스트를 적절한 시점에 강제로 플러시 필요
* 위 방법은 JPA 를 우회하는 방법이므로 많은 체크가 필요


### JPQL(Java Persistence Query Language)

* 엔티티와 속성은 대소문자 구분함, 엔티티 이름 사용하며 테이블 이름이 아님
* JPQL 키워드는 대소문자 구분 x (SELECT, from, WHERE)
* 별칭은 필수, as 생략 가능


#### TypedQuery, Query

* 반환 타입이 명확할 때 TypedQuery


    TypedQuery<Member> typedQuery = em.createQuery("SELECT m FROM Member as m", Member.class);

* 반환 타입이 명확하지 않을 때 Query


    Query query = em.createQuery("SELECT m.name FROM Member as m");

#### 결과 조회 API

* EntityManager.createQuery("블라블라").getResultList()
  * 결과가 없으면 빈 리스트 반환
* EntityManager.createQuery("블라블라").getSingleResult()
  * 결과가 정확히 하나, 단일 객체 반환
  * 결과가 없으면 javax.persistence.NoResultException
  * 둘 이상이면 javax.persistence.NonUniqueResultException


#### 파라미터 바인딩

    List<Member> typedQuery =
        em.createQuery("SELECT m FROM Member as m WHERE m.name = :name", Member.class)
        .setParameter("name", "dcun")
        .getResultList();

* 별칭.컬럼 = :비교대상명 이후 .setParameter() 설정, 순번 의 경우 별칭.컬럼 = ?순번 
* 순번으로 사용하는 경우 잠재적 문제를 가질 수 있음


### 프로젝션

* SELECT 절에 조회할 대상을 지정하는 것
* 대상으로 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자, 기본 데이터 타입)
  * SELECT m FROM Member m: 엔티티 프로젝션
  * SELECT m.team FROM Member m: 엔티티 프로젝션
  * SELECT m.address FROM Member m: 임베디드 타입 프로젝션
  * SELECT m.username FROM Member m: 스칼라 타입 프로젝션
  * DISTINCT 로 중복 제거


#### 프로젝션 - 여러 값 조회

```
List<Member> typedQuery =
        em.createQuery("SELECT new 패키지.MemberDTO(m.name, m.age) FROM Member m", Member.class)
                .setParameter("name", "dcun")
                .getResultList();
```

* Query 타입 조회
* Object[] 타입 조회
* new 명령어 사용
  * SELECT new 패키지.MemberDTO(m.name, m.age) FROM Member m
  * 순서와 타입이 일치해야함


### 페이징 API

```
List<Member> typedQuery =
        em.createQuery("SELECT m.name FROM Member as m", Member.class)
                .setFirstResult(1)
                .setMaxResults(10)
                .getResultList();
```

* JPA 는 페이징을 두 API 로 추상화
* setFirstResult(int startPosition): 시작 위치, setMaxResults(int maxResult): 조회할 갯수


### 조인

* 내부 조인: SELECT m FROM Member m [INNER] JOIN m.team t
* 외부 조인: SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
* 세타 조인(억지 조인?): SELECT count(m) FROM Member m, Team t WHERE m.name = t.name