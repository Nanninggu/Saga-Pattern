정리해야할 내용!!
`@Options(useGeneratedKeys = true, keyProperty = "id")` 설정은 MyBatis가 SQL 쿼리를 실행한 후에 생성된 키를 검색하도록 지시합니다. 이 설정은 주로 자동 증가(auto-increment) 필드에 대해 사용됩니다.

PostgreSQL에서 이를 사용하려면, 먼저 데이터베이스 테이블에 자동 증가하는 ID 필드를 설정해야 합니다. 이를 위해 `SERIAL` 또는 `BIGSERIAL` 데이터 타입을 사용할 수 있습니다.

다음은 `id` 필드가 자동 증가하는 `user` 테이블을 생성하는 SQL 쿼리 예제입니다:

```sql
CREATE TABLE public.user (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255),
    email VARCHAR(255),
    state VARCHAR(255)
);
```

이렇게 설정하면, 새로운 `user`를 삽입할 때마다 PostgreSQL이 자동으로 `id` 값을 증가시키고, MyBatis는 `@Options(useGeneratedKeys = true, keyProperty = "id")` 설정에 따라 이 새로운 `id` 값을 `User` 객체의 `id` 프로퍼티에 설정합니다.