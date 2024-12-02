# 🪖높은 산 깊은 골🏔️ - MTVS-Final-BE 👩‍💻💻🚀  
⭐️[메타버스 아카데미] 최종 융합 프로젝트⭐️ - 🪖높은 산 깊은 골🏔️  

---

![image](https://github.com/user-attachments/assets/d42507e1-3fe4-44f3-9072-8ce56f44e728)  

---

## 💫 프로젝트 소개  
"군사 환경 제공 플랫폼"  
이 프로젝트는 메타버스 아카데미의 **XR-언리얼**, **백엔드(back-end)**, **AI**, **TA**, **기획** 팀이 협업하여 진행한 프로젝트입니다.  

---

## 🚀 기획 내용  
‘높은 산 깊은 골’은 메타버스 환경에서 사용자의 VR 군사 훈련 데이터를 제공하는 서비스 플랫폼입니다.  

---

## 🕰️ 개발 기간  
* **24.10.08 - 24.11.29**

---

### 🐣 백엔드 멤버 구성 [Backend Developer]  
|                                               BE 김혜진                                              |                                                              
|:-----------------------------------------------------------------------------------------------:|
| <img src = "https://avatars.githubusercontent.com/u/173024446?v=4" width = "100" height = "100"> | 
|                            [@dev-hjk](https://github.com/dev-hjk)                               |

---

### 🏆 백엔드 구현 내용  
1. **API 설계 및 구현**
   - **회원 관리**
     - **로그인 API**:
       - JWT 기반 인증 시스템 구현.
       - Spring Security와 연동하여 사용자 인증 및 세션 관리.
     - **회원가입 API**:
       - 사용자 데이터 검증 및 저장.
       - 중복 검사 및 비밀번호 암호화 적용.
   - **전투 데이터 관리**
     - **전투 성과 분석 API**:
       - 사용자 데이터를 받아 AI 분석 결과를 포함한 성과 데이터를 반환.
       - AWS S3와 연동하여 저장된 데이터를 불러와 XR 환경에 제공.
     - **훈련생별 누적 성과 리포트 API**:
       - 훈련 성과 요약 데이터를 PostgreSQL에서 조회 및 처리하여 반환.

2. **보안 및 인증**
   - **Spring Security와 JWT 적용**:
     - JWT 토큰 발급, 검증 및 사용자 권한 설정.
     - 보안 강화와 API 보호를 위한 `security.config` 구성.

3. **데이터 저장 및 서빙**
   - **AWS S3 연동**:
     - 전투 데이터 그래프 및 AI 피드백 바이너리 파일 저장 기능 구현.
     - S3에서 데이터를 읽어와 VR 환경과 연동 가능하도록 API 설계.
   - **End-to-End 데이터 파이프라인**:
     - XR 환경에서 수집된 데이터를 백엔드에서 처리하고, AI 분석 후 XR로 서빙.

4. **DB 설계 및 구축**
   - PostgreSQL에 아래 테이블 설계 및 구축:
     - **회원 테이블**: 사용자 정보 저장.
     - **전투 데이터 테이블**: 훈련 및 성과 기록 저장.
     - **훈련장 데이터 테이블**: 훈련 환경 관련 정보 저장.
     - **성과 요약 테이블**: 사용자별 누적 성과 데이터 저장.

5. **통합 테스트 및 최종 연동**
   - **기능별 테스트**:
     - 로그인/회원가입 API 및 데이터 저장/조회 기능 검증.
   - **데이터 흐름 검증**:
     - **백엔드 → S3 → VR**의 데이터 흐름 테스트.
     - AI 분석 결과를 XR 환경으로 전달하는 최종 테스트 완료.

---

### 🌠 아키텍쳐 설계 [System Architecture]  
![image](https://github.com/user-attachments/assets/8aea84fd-fa86-4c53-87ce-f05d8eb979a8)  

---

### 📢 개발 환경  
- **Language**: Java 17  
- **Framework**: Spring Boot  
- **Database**: PostgreSQL  
- **ORM**: JPA  
- **API 문서화**: Swagger  
- **Cloud**: AWS S3  
- **Security**: Spring Security, JWT  
- **Version Control**: GitHub  
- **Logging**: ELK Stack (Elasticsearch, Logstash, Kibana)  

---

### 🎯 프로젝트 성과  
- AI 분석 피드백 및 훈련 리포트 & 누적 리포트 -> S3 데이터 서빙을 통해 VR 환경에서 훈련 성과를 시각적으로 제공.  
- Spring Security와 JWT를 활용한 보안 강화로 안정성 높은 서비스 구현.  
- AWS S3와 ELK Stack을 통해 대량 데이터 저장 및 로깅 효율성 극대화.  

---

## :busts_in_silhouette: Team Members '🏔️ 높은 산 깊은 골 전체 멤버 구성 🪖'  

|                          BE 김혜진                           |                          XR 전승건                           |                          XR 최선우                           |                          XR 한수빈                           |                          TA 서현녕                           |                          AI 박진우                           |                          기획 김창선                           |
|:-----------------------------------------------------------:|:-----------------------------------------------------------:|:-----------------------------------------------------------:|:-----------------------------------------------------------:|:-----------------------------------------------------------:|:-----------------------------------------------------------:|:-----------------------------------------------------------:|
| <img src = "https://avatars.githubusercontent.com/u/173024446?v=4" width = "100" height = "100"> | <img src = "https://avatars.githubusercontent.com/u/112955666?v=4" width = "100" height = "100"> | <img src = "https://avatars.githubusercontent.com/u/101624921?v=4" width = "100" height = "100"> | <img src = "https://avatars.githubusercontent.com/u/80036437?v=4" width = "100" height = "100"> | <img src = "https://avatars.githubusercontent.com/u/173872482?v=4" width = "100" height = "100"> | <img src = "https://avatars.githubusercontent.com/u/176445886?v=4" width = "100" height = "100"> | <img src = "https://avatars.githubusercontent.com/u/129839798?v=4" width = "100" height = "100"> |
|       [@dev-hjk](https://github.com/dev-hjk)                |       [@JSG0626](https://github.com/JSG0626)                |       [@sunwchoi](https://github.com/sunwchoi)              |       [@so0oppy](https://github.com/so0oppy)                |       [@HyunneongSeo](https://github.com/HyunneongSeo)      |       [@silvercrown0730](https://github.com/silvercrown0730)|       [@joy98joy](https://github.com/joy98joy)              |

   
