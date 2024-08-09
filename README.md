# 온라인 시험 프로그램

## 개요

이 프로젝트는 **온라인 시험 프로그램**으로, 사용자들이 웹을 통해 시험을 보고 제출할 수 있는 기능을 제공합니다. 학생과 강사는 각각 로그인 후 시험을 보고, 시험을 관리하고 결과를 확인할 수 있습니다.

## 프로젝트 기간

- **개발 기간:** 2024.08.05 ~ 2024.08.07
- **팀 구성:** 3명
- **참여도:** 개인 역할 기반으로 개발

## 사용 기술 및 개발 환경

- **Backend:**
  - **언어:** Java
  - **프레임워크:** JSP, Servlet API
  - **JDK 버전:** JDK 17

- **Frontend:**
  - **언어:** HTML, CSS
  - **프레임워크:** Bootstrap

- **도구:**
  - **개발 환경:** IntelliJ IDEA
  - **데이터베이스:** MariaDB
  - **협업 도구:** Git, GitHub

## 주요 기능

1. **로그인 기능**
   - **기능:** 강사 및 학생의 로그인 구현, 세션 관리
   - **관련 컨트롤러:** `LoginChoiceController`, `SLoginController`, `TLoginController`

2. **학생의 시험 관련 기능**
   - **기능:** 학생의 시험 목록 조회, 시험 응시 기능, 임시 답안 제공 및 쿠키를 통한 답안 처리
   - **관련 컨트롤러:** `StudentExamListController`

3. **시험 제출 및 결과 처리**
   - **기능:** 시험 응시 후 답안 제출 및 DB 기록, 시험 결과 조회
   - **관련 컨트롤러:** `ExamViewController`, `CheckAnswerController`, `AnswerSheetController`, `ExamSubmitController`, `StudentExamResultController`

## 구현된 컨트롤러 목록

- **로그인 관련:**
  - `LoginChoiceController`
  - `SLoginController`
  - `TLoginController`

- **학생 시험 목록 및 응시:**
  - `StudentExamListController`

- **시험 응시 및 제출:**
  - `ExamViewController`
  - `CheckAnswerController`
  - `AnswerSheetController`
  - `ExamSubmitController`

- **시험 결과 처리:**
  - `StudentExamResultController`

## 본인 역할

- **프론트엔드 개발:**
  - HTML, CSS, Bootstrap을 사용하여 사용자 인터페이스 구현

- **백엔드 개발:**
  - Java, JSP, Servlet API를 사용하여 서버 로직 및 데이터베이스와의 상호작용 구현

- **데이터베이스 설계 및 관리:**
  - MariaDB를 사용하여 데이터베이스 구조 설계 및 쿼리 작성

- **협업 도구 사용:**
  - Git 및 GitHub를 사용하여 소스 코드 버전 관리 및 팀 협업

## 프로젝트 산출물

### 요구사항 정의서

![요구사항 정의서](https://user-images.githubusercontent.com/68316076/87619085-4b243380-c756-11ea-91e3-6b97bf450be0.PNG)

### 화면 흐름(플로우차트)

![플로우차트](https://user-images.githubusercontent.com/68316076/87618863-d3560900-c755-11ea-8b80-78c1190ba0d9.png)

### ERD
[ERD 링크](https://www.erdcloud.com/d/yTLGLXQKajRkhoXqy)

### 기타 산출물

- **UI 디자인 및 스크린샷**
  - [기타 관련 이미지나 링크 추가]

## 설치 및 실행

1. **환경 설정:**
   - JDK 17 설치
   - MariaDB 데이터베이스 설정

2. **프로젝트 클론:**
   ```bash
   git clone [프로젝트 레포지토리 URL]
