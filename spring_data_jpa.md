##Spring Data Jpa

###쿼리 메소드 기능
####1. 메소드 이름으로 쿼리 생성
    이메일 AND 이름으로 회원 조회시 메소드 정의
    인터페이스에서 정의한 메소드를 실행시키면 메소드 이름을 분석하여 JPQL을 생성 및 실행
    * 메소드 명은 정해진 명칭 및 규칙에 맞춰 생성 되어야 한다 (findBy + 조건 대상 컬럼명 + 키워드)
    * 엔티티의 필드명이 변경되면 꼭 메소드 이름도 같이 변경되어야 한다.
```java
public interface MemberRepository extends Repository<Member, Long> {
    List<Member> findByEmailAndName(String email, String name);
}
```
```sql
실행 SQL :: SELECT m FROM Member m WHERE m.email = ?1 AND m.name =?2
```

####2. JPA NamedQuery
    메소드 이름으로 JPA Named 쿼리를 호출하는 기능을 제공하며 동일한 이름의 메소드를 찾지 못하는 경우 
    메소드 이름으로 쿼리 생성 전략을 사용하게 된다
    
```java
import javax.persistence.NamedQuery;

@NamedQuery(
        name = "Member.findByUsername",
        query = "SELECT m FROM Member m WHERE m.username = :username"
)
public class Member {
    //..getter ..setter
}
```

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findByUsername(@Param("username") String username);
}
```

####3. @Query, 레포지토리 메소드에 쿼리 정의
    공통 레포지토리에 없는 쿼리를 직접 상속받은 인터페이스 레포지토리에 정의하여 사용할 수 있다
    @org.springframework.data.jpa.repository.Query 어노테이션 사용
    어플리케이션 실행 시점에 문법 오류를 발견할 수 있다

```java
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //정적 쿼리, 이름 없는 Named 쿼리, 스프링 JPA = 1 부터 시작
    @Query("select m from Member m where m.username = ?1")
    Member findByUsername(String username);

    //네이티브 SQL = 0 부터 시작
    @Query(
            value = "select m from Member m where m.username = ?0",
            nativeQuery = true //네이티브 설정
    )
    Member findByUsername(String username);
}
```

###파라미터 바인딩
    위치 기반 파라미터 바인딩, 이름 기반 파라미터 바인딩
    ex) where m.username = ?1 //위치 기반
    ex) where m.username = :name //이름 기반
    위치 기반 설정이 기본이며 이름으로 바인딩 하고 싶은경우 인자에 @Param("name") Type T 와 같이 메소드 설정

###벌크성 수정 쿼리

```java
import org.jboss.logging.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public class bulkUpdateQueryClass {

    //@Modifying(clearAutomatically = true) 벌크성 수정 쿼리 이행 후 영속성을 초기화
    @Modifying //벌크성 수정 쿼리 어노테이션
    @Query(
            "update Product p" +
                    " set" +
                    " p.price = p.price * 1.1" +
                    " where" +
                    " p.stockAmount < :stockAmount"
    )
    int bulkPriceUp(@Param("stockAmount") String stockAmount);
}
```

###반환 타입
    결과가 한건 이상이면 컬렉션 지정, 단건이면 반환 타입을 지정
    조회 결과가 없을때 반환 타입이 컬렉션이면 빈 컬렉션 반환, 단건은 null 반환
    단건의 경우 결과가 2개 이상이면 NonUniqueResultException 예외 발생

###페이징, 정렬 

```java
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class pageUseCodeClass {

    PageRequest pageRequest =
            new PageRequest(0, 10, new Sort(Sort.Direction.DESC) "name");
    
    Page<Member> result = 
            memberRepository.findByNameStartingWith("김", pageRequest);
    
    List<Member> members = result.getContent(); //페이지에서 조회 된 데이터 사용하도록 핸들링
    int totalPages = result.getTotalPages(); //전체 페이지
    boolean hasNextPage = result.hasNextPage(); //다음 페이지 존재 여부
}
```

###힌트


###LOCK
