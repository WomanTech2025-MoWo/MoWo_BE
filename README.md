# 🏆 MoWo(모우) - 2025 서울 우먼테크 해커톤 우수상

**임신**과 **직장**을 모두 챙겨야 하는 여성을 위한 **AI 투두리스트**

![MoWo Intro](https://github.com/user-attachments/assets/962747a9-bc86-4b16-8ffa-02f8e4cde705)

> 🎖️ **2025 서울 우먼테크 해커톤 우수상 수상**   
> 본선 31팀 → 결선 10팀 → 수상 4팀 중 우수상

- **주제**: 여성·가족 친화 도시 서울
- **방법**: 생성형 AI 기술·도구를 활용하여 서비스 제안 및 MVP 개발
- **기간**: 2025.07.21 ~ 2025.08.26

[🔗 서울시여성가족재단 - ‘2025 서울 우먼테크 해커톤’ 행사 후기](https://www.seoulwomen.or.kr/sfwf/contents/sfwf-pressRelease2.do?&schM=view&id=30272)


## 🎯 프로젝트 개요

**MoWo**는 **임신과 직장 생활을 병행하는 여성**이 겪는 **경력 단절 불안**이라는 사회적 문제를 해결하기 위해 탄생한 AI 기반 투두리스트 서비스입니다. 건강, 업무, 개인 생활을 아우르는 통합 솔루션을 제공하여, 임신 기간 동안에도 커리어를 성공적으로 이어갈 수 있도록 돕습니다.


## ✨ 주요 기능

### 📋 투두 관리

- **우선순위 분류**: 건강, 업무, 개인 카테고리별 할 일 관리
- **일정 알림**: 지정 시간 기반 알림 제공
- **통합 대시보드**: 임신 관련 할 일과 업무 할 일의 통합 관리

### 🤖 AI 컨디션 예측
 
- **개인화된 건강 예측**: 사용자 증상 패턴 학습을 통한 컨디션 예측
- **'모우의 주간 편지'**: 따뜻한 편지 형태의 주간 컨디션 예측 리포트 제공

### 🏛️ 서울시 정책 정보

- **지역별 맞춤 필터링**: 거주 지역에 따른 정책 정보 제공
- **북마크 기능**: 중요한 정책 정보 저장 및 관리

### 💡 임신·육아 노하우 공유

- **노하우 공유**: 투두리스트 기반 임신·육아 경험 공유
- **임신 주차별 가이드**: 단계별 맞춤 정보 제공


## 📱 시연 자료

- [📱 MoWo 데모 사이트 바로가기](https://mowo-lake.vercel.app/login)

**<기능별 시연 이미지 및 상세 설명>**   

| 로그인 | 브리핑 | 투두 | 모우의 주간편지(AI)                                                                               | 정보(정책 및 노하우)                                                                              |
|-----|-----|----|--------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------|
| ![login](https://github.com/user-attachments/assets/bd0dfbf1-965d-4680-a7a0-126d4fec70bb)    | ![briefing](https://github.com/user-attachments/assets/50f02277-4454-4396-8d6d-927daa306a38)    | ![todo](https://github.com/user-attachments/assets/fc325e69-b7f5-4112-9f1b-4de9c5735d8e)   | ![ai](https://github.com/user-attachments/assets/9c12052f-926d-4de7-9f14-5f1ba13ff730)  | ![info](https://github.com/user-attachments/assets/82f35c75-5660-4ffb-9f7b-7b2592f953ea) |


## 👥 Team *We R Is*

임신과 커리어를 동시에 관리해야 하는 여성들을 위해 각기 다른 분야의 열정적인 5명이 모여 MoWo를 만들었습니다.

| 이름      | 역할          | GitHub                                                      |
|---------|-------------|-------------------------------------------------------------|
| 🎯 전예원  | 서비스 기획      | -                                                           |
| 🎨 최윤주  | UI/UX 디자인 | -                                                           |
| 💻 문소희  | 풀스택 개발      | [@soheetech](https://github.com/soheetech)                  |
| 🛠️ 이소정 | 백엔드 개발      | [@Sojeong0430](https://github.com/Sojeong0430)              |
| 🤖 홍재영  | AI 모델 개발    | [@jayoung531531-code](https://github.com/jayoung531531-code) |


## 🛠 기술 스택

[![React](https://img.shields.io/badge/React-61DAFB?style=flat-square&logo=React&logoColor=black)](https://reactjs.org/)
[![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=flat-square&logo=TypeScript&logoColor=white)](https://www.typescriptlang.org/)
[![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat-square&logo=Spring%20Boot&logoColor=white)](https://spring.io/)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white)](https://www.mysql.com/)
[![Python](https://img.shields.io/badge/Python-3776AB?style=flat-square&logo=Python&logoColor=white)](https://www.python.org/) 


## 📂 프로젝트 구조

```bash
# MoWo Backend Directory 구조

 mowo_be/                                                                   
  ├── src/main/java/com/womantech/mowo/                                      
  │   ├── MowoApplication.java            # Spring Boot 메인 클래스
  │   │                                                                      
  │   ├── domain/                         # 도메인별 비즈니스 로직 
  │   │   ├── common/                     # 공통 도메인(BaseEntity 등)
  │   │   ├── member/                     # 사용자 도메인(회원가입, 로그인, 온보딩)
  │   │   ├── policy/                     # 정책 도메인(정책 CRUD, 북마크)
  │   │   ├── knowhow/                    # 노하우 도메인(노하우 CRUD)
  │   │   ├── todo/                       # 할일 도메인(개인 할일 관리)
  │   │   └── predict/                    # AI 예측 도메인(건강 상태 예측)
  │   │
  │   └── global/                         # 전역 설정 및 공통 기능
  │       ├── security/                   # 보안(JWT, Spring Security)
  │       ├── config/                     # 전역 설정(Swagger, CORS, Web)
  │       ├── apiPayload/                 # API 응답 표준화 및 예외 처리
  │       └── ai/                         # AI 관련 공통 기능
  │
  ├── ai/                                 # Python AI 모델
  │   ├── predict.py                      # AI 건강 상태 예측 모델
  │   ├── model.pkl                       # 학습된 머신러닝 모델
  │   └── *.csv, *.json                   # 학습/테스트 데이터
  │
  ├── src/main/resources/                 # 리소스 파일
  ├── docs/                               # 문서
  ├── build.gradle                        # Gradle 빌드 설정
  └── README.md                           # 프로젝트 문서
```


## 📑 API 문서

- [🔗 MoWo OpenAPI 파일 바로가기](https://github.com/WomanTech2025-MoWo/MoWo_BE/blob/develop/docs/openapi.json)

```bash
# 주요 엔드포인트

- `/api/todos`    : TODO 관리 API
- `/api/members`  : 회원 관리 API
- `/api/policies` : 정책 관리 API
- `/api/knowhows` : 노하우 관리 API
- `/api/predict`  : AI 주간 컨디션 예측 API
```


## 🔗 관련 링크

- [📱 Demo Site](https://mowo-lake.vercel.app/login)
- [💻 Frontend Repository](https://github.com/WomanTech2025-MoWo/MoWo_FE)
- [🤖 AI Model Repository](https://github.com/WomanTech2025-MoWo/MoWo_AI)
- [📋 Final Presentation PDF](https://github.com/WomanTech2025-MoWo/MoWo_BE/blob/develop/docs/final_presentation.pdf)
