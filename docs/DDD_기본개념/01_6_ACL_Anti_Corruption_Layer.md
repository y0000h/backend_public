# ACL (Anti-Corruption Layer)

## 1. 한 줄 정의
**ACL(Anti-Corruption Layer)** 은  
외부 시스템 모델이 내부 도메인 모델을 오염시키지 않도록  
중간에서 번역/보호하는 계층이다.

---

## 2. 왜 필요한가?
외부 API나 레거시 시스템은 내부 도메인과 용어, 규칙이 다르다.

- 코드값 체계가 다름 (`status=00`, `status=OK`)
- 금액/날짜/시간대 포맷이 다름
- 필수 필드/검증 규칙이 다름

ACL 없이 직접 연결하면 외부 변경이 내부 도메인을 흔든다.

---

## 3. 핵심 역할
1. **모델 번역**: 외부 DTO <-> 내부 도메인 객체 변환
2. **의미 변환**: 코드값/상태값을 비즈니스 의미로 치환
3. **경계 보호**: 외부 계약 변경 영향 차단
4. **오류 격리**: 외부 장애와 내부 도메인 오류 분리

---

## 4. 구조 그림
```text
[External System]
      |
      v
[External DTO]
      |
      v
[ACL Mapper / Translator]
      |
      v
[Internal Domain Model]
```

핵심: 외부 DTO가 도메인 계층으로 직접 들어오지 않게 한다.

---

## 5. 예시 (PG 결제 응답 변환)
### 외부 응답
```text
status=00, amt=129000, trx_id=T-991
```

### 내부 모델
```java
public record PaymentApproval(boolean approved, Money amount, String transactionId) {}
```

### ACL 변환
```java
public class PgPaymentAclMapper {
    public PaymentApproval toDomain(PgPaymentResponse response) {
        boolean approved = "00".equals(response.status());
        Money amount = Money.of(response.amt(), "KRW");
        return new PaymentApproval(approved, amount, response.trxId());
    }
}
```

이렇게 하면 외부 코드 규칙(`00`)이 도메인 전역으로 퍼지지 않는다.

---

## 6. 장점과 단점
### 장점
- 도메인 모델 보호
- 외부 시스템 교체 비용 감소
- 계약 변경 영향 범위 축소

### 단점
- 변환 코드 증가
- 매핑 규칙 관리 비용 필요
- 초기에는 중복처럼 보일 수 있음

---

## 7. 자주 하는 실수
### 실수 1: Controller에서 바로 외부 DTO를 도메인에 전달
경계가 무너져 모델 오염이 빠르게 진행된다.

### 실수 2: 매핑 규칙 문서화 누락
누가 어떤 의미로 변환했는지 알기 어려워 유지보수가 어려워진다.

### 실수 3: 변환 실패 예외를 도메인 예외와 혼합
장애 원인 분석이 어려워진다. ACL 전용 예외로 분리하는 편이 좋다.

---

## 8. 어디에 두면 좋은가?
- `infra/acl` 또는 `integration/acl` 패키지
- 컨텍스트 경계 지점(외부 API Client 근처)
- Application Service에서 ACL을 호출하고, Domain에는 번역된 모델만 전달

---

## 9. 최소 체크리스트
- 외부 DTO와 내부 모델이 분리되어 있는가?
- 코드값/단위/시간대 변환 규칙이 명시되어 있는가?
- 외부 계약 변경 시 ACL만 수정해도 되는가?
- ACL 단위 테스트(정상/누락/오류 케이스)가 있는가?

---

## 10. 요약
ACL은  
**외부 모델과 내부 도메인 사이의 방화벽**이다.

핵심은 빠른 연동보다  
"도메인을 깨끗하게 유지하는 번역 경계"를 먼저 세우는 것이다.

