### Project Flow 설명

이 프로젝트는 Saga Pattern을 사용하여 분산 트랜잭션을 처리하는 간단한 Spring Boot 애플리케이션으로 보입니다.  
Saga Pattern은 여러 서비스에 걸쳐 트랜잭션을 관리하는 방법을 제공하는 마이크로서비스 아키텍처의 디자인 패턴입니다.

다음은 프로젝트의 흐름에 대한 간략한 설명입니다:

1. 클라이언트는 `UserController`에게 사용자 생성 요청을 보냅니다. 이는 요청 본문에 사용자 세부 정보와 함께 `/users` 엔드포인트로 `POST` 요청을 보내는 것으로 이루어집니다.

2. `UserController`는 요청 본문의 `User` 객체를 전달하여 `CreateUserStep` 서비스의 `execute` 메소드를 호출합니다.

3. `CreateUserStep` 서비스는 새 사용자를 생성하는 역할을 합니다. 이는 `UserMapper`의 `insert` 메소드를 호출하여 데이터베이스에 사용자를 삽입함으로써 이루어집니다.

4. `CreateUserStep` 서비스의 `execute` 메소드가 성공적으로 완료되면, 사용자가 성공적으로 생성되었다는 것을 나타내는 응답이 클라이언트에게 전송됩니다.

5. `CreateUserStep` 서비스의 `execute` 메소드 실행 중 예외가 발생하면 (예를 들어, 데이터베이스에 사용자를 삽입하는 중에 오류가 발생한 경우), `CreateUserStep` 서비스의 `compensate` 메소드가 호출됩니다.

6. `CreateUserStep` 서비스의 `compensate` 메소드는 `execute` 메소드의 효과를 취소하는 역할을 합니다. 이 경우, 이는 `UserMapper`의 `delete` 메소드를 호출하여 데이터베이스에서 사용자를 삭제함으로써 이루어집니다.

7. `compensate` 메소드가 호출된 후에는, 사용자 생성이 실패했다는 것을 나타내는 응답이 클라이언트에게 전송됩니다.

이 흐름은 사용자 생성 중 오류가 발생하면 시스템이 자동으로 변경 사항을 취소하여 데이터 일관성을 유지할 수 있도록 보장합니다.

### DB 관련 내용

`@Options(useGeneratedKeys = true, keyProperty = "id")` 설정은 MyBatis가 SQL 쿼리를 실행한 후에 생성된 키를 검색하도록 지시합니다.  
이 설정은 주로 자동 증가(auto-increment) 필드에 대해 사용됩니다.

PostgreSQL에서 이를 사용하려면, 먼저 데이터베이스 테이블에 자동 증가하는 ID 필드를 설정해야 합니다.  
이를 위해 `SERIAL` 또는 `BIGSERIAL` 데이터 타입을 사용할 수 있습니다.

다음은 `id` 필드가 자동 증가하는 `user` 테이블을 생성하는 SQL 쿼리 예제입니다:

```sql
CREATE TABLE public.user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    state VARCHAR(255)
);
```

이렇게 설정하면, 새로운 `user`를 삽입할 때마다 PostgreSQL이 자동으로 `id` 값을 증가시키고,  
MyBatis는 `@Options(useGeneratedKeys = true, keyProperty = "id")` 설정에 따라 이 새로운 `id` 값을 `User` 객체의 `id` 프로퍼티에 설정합니다.