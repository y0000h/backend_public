# 🚀 Backend Public Project
> **Clean Architecture & Hexagonal Architecture를 적용한 객체지향 백엔드 시스템**

본 프로젝트는 복잡한 비즈니스 로직을 기술적 세부사항(DB, Web, Message Broker)으로부터 분리하여, **유지보수성과 테스트 용이성을 극대화**하는 아키텍처를 학습하고 실천하기 위해 구축되었습니다.

---

## 🛠 Tech Stack
* **Language**: Java 17
* **Framework**: Spring Boot 3.x
* **Database**: Spring Data JPA / MySQL
* **Documentation**: Swagger / SpringDoc

---

## 🏗 Architecture Concept
이 프로젝트는 **도메인 중심 설계(DDD)**와 **헥사고날 아키텍처**를 기반으로 설계되었습니다.



### 1. Layers & Folders
* **`domain`**: 시스템의 핵심 비즈니스 규칙과 엔티티가 존재합니다. 외부 프레임워크나 라이브러리에 의존하지 않는 순수한 자바 영역입니다.
* **`application`**: 유스케이스를 정의하고 흐름을 조율합니다.
  * **`port.in`**: 외부에서 시스템을 가동시키기 위한 접점(Input Port)입니다.
  * **`port.out`**: 시스템이 외부(DB, 외부 API)와 소통하기 위한 규격(Output Port)입니다.
* **`adapter`**: 포트를 통해 실제 기술을 구현하거나 외부 요청을 처리합니다.
  * **`in.web`**: REST API 컨트롤러가 위치합니다.
  * **`out.persistence`**: JPA 등을 활용한 실제 데이터 저장 로직이 위치합니다.

---

## 🌟 Key Features & Philosophy
### ✅ 관심사의 분리 (Separation of Concerns)
- 웹 계층(`adapter.in.web`)과 영속성 계층(`adapter.out.persistence`)이 완전히 분리되어 있어, 기술 교체 시 비즈니스 로직이 보호됩니다.

### ✅ 의존성 역전 원칙 (DIP)
- 고수준의 정책이 저수준의 상세 기술에 의존하지 않도록 포트(Interface)를 활용하여 의존성 방향을 항상 안쪽(도메인)으로 유지합니다.

### ✅ 테스트 용이성
- 외부 의존성을 배제한 순수 도메인 로직에 대해 독립적이고 빠른 단위 테스트가 가능합니다.

---

## 📂 Project Structure
```text
com.grepp.backend5
├── product
│   ├── adapter
│   │   ├── in.web          # Web Controller (Driving Adapter)
│   │   └── out.persistence # Database Adapter (Driven Adapter)
│   ├── application
│   │   ├── port
│   │   │   ├── in          # Input Port (Use Case Interface)
│   │   │   └── out         # Output Port (Repository Interface)
│   │   └── service         # Application Service (Business Logic)
│   └── domain              # Pure Domain Entities
└── config                  # Infrastructure Configurations (Swagger, etc.)
