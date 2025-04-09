# 🗂️ 03. ERD 설계 문서 

EduTrack 교육이력관리 시스템의 주요 테이블 구조와 관계를 정의합니다.  
공공데이터 명명 규칙에 따라 테이블명은 복수형, 컬럼명은 대문자 + 약어 중심으로 구성하며,  
모든 테이블에는 등록/수정/삭제 이력 컬럼이 공통 포함됩니다.

---

## ✅ 공통 컬럼 (모든 테이블 공통 적용)

| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| REG_DT | DATE | 등록일 |
| REG_USER_ID | VARCHAR(20) | 등록자 ID (FK: USR_USERS.USER_ID) |
| MOD_DT | DATE | 수정일 |
| MOD_USER_ID | VARCHAR(20) | 수정자 ID (FK: USR_USERS.USER_ID) |
| DEL_YN | CHAR(1) | 삭제 여부 (Y/N) |
| DEL_RSN | VARCHAR(255) | 삭제 사유 |

---

## ✅ 테이블 정의

---

### 📘 USR_USERS (사용자 테이블)

| 컬럼명 | 타입 | PK | FK | 설명 |
|--------|------|----|----|------|
| USER_ID | VARCHAR(20) | ✅ |  | 사용자 ID |
| USER_NM | VARCHAR(50) |  |  | 사용자명 |
| PSWD | VARCHAR(100) |  |  | 비밀번호 |
| EMAL | VARCHAR(100) |  |  | 이메일 |
| USER_TY | VARCHAR(10) |  |  | 사용자 유형 (USER / ADMIN) |
| JOIN_DT | DATE |  |  | 가입일 |
| + 공통 컬럼 |  |  |  | REG_DT, REG_USER_ID, ... |

---

### 📘 EDU_COURSES (교육과정 테이블)

| 컬럼명 | 타입 | PK | FK | 설명 |
|--------|------|----|----|------|
| COURSE_ID | NUMBER | ✅ |  | 교육과정 ID |
| COURSE_NM | VARCHAR(100) |  |  | 교육명 |
| COURSE_CN | CLOB |  |  | 교육 상세내용 |
| STT_DT | DATE |  |  | 시작일자 |
| END_DT | DATE |  |  | 종료일자 |
| REG_USER_ID | VARCHAR(20) |  | ✅ | 등록자 ID (관리자) |
| + 공통 컬럼 |  |  |  | REG_DT, MOD_DT, DEL_YN 등 |

---

### 📘 APLY_COURSE_APPLYS (교육신청 테이블)

| 컬럼명 | 타입 | PK | FK | 설명 |
|--------|------|----|----|------|
| APLY_ID | NUMBER | ✅ |  | 신청 ID |
| COURSE_ID | NUMBER |  | ✅ | 신청한 교육 ID |
| USER_ID | VARCHAR(20) |  | ✅ | 신청자 ID |
| APLY_DT | DATE |  |  | 신청일자 |
| APLY_STTUS_CD | VARCHAR(20) |  |  | 신청상태 코드 (신청/승인/취소 등) |
| + 공통 컬럼 |  |  |  | REG_DT, DEL_YN 등 포함 |

---

### 📘 PRGS_COURSE_PROGRESS (교육이수 테이블)

| 컬럼명 | 타입 | PK | FK | 설명 |
|--------|------|----|----|------|
| PRGS_ID | NUMBER | ✅ |  | 이수내역 ID |
| APLY_ID | NUMBER |  | ✅ | 신청 ID |
| CMPLT_YN | CHAR(1) |  |  | 이수 여부 (Y/N) |
| CMPLT_DT | DATE |  |  | 이수일자 |
| + 공통 컬럼 |  |  |  | REG_DT, MOD_DT, DEL_YN 등 |

---

### 📘 EXAM_EXAMS (시험 테이블)

| 컬럼명 | 타입 | PK | FK | 설명 |
|--------|------|----|----|------|
| EXAM_ID | NUMBER | ✅ |  | 시험 ID |
| COURSE_ID | NUMBER |  | ✅ | 해당 교육과정 ID |
| EXAM_NM | VARCHAR(100) |  |  | 시험명 |
| EXAM_DT | DATE |  |  | 시험일자 |
| REG_USER_ID | VARCHAR(20) |  | ✅ | 시험 등록자 ID |
| + 공통 컬럼 |  |  |  | REG_DT, DEL_YN 등 포함 |

---

### 📘 NTCE_NOTICES (공지사항 테이블)

| 컬럼명 | 타입 | PK | FK | 설명 |
|--------|------|----|----|------|
| NTCE_ID | NUMBER | ✅ |  | 공지사항 ID |
| NTCE_TTL | VARCHAR(200) |  |  | 제목 |
| NTCE_CN | CLOB |  |  | 내용 |
| INQ_CNT | NUMBER |  |  | 조회수 |
| REG_USER_ID | VARCHAR(20) |  | ✅ | 작성자 ID |
| + 공통 컬럼 |  |  |  | REG_DT, MOD_DT, DEL_YN 등 포함 |

---

## 🔗 테이블 간 관계 요약

```text
USR_USERS
 ├── 1:N → EDU_COURSES (REG_USER_ID)
 ├── 1:N → APLY_COURSE_APPLYS (USER_ID)
 ├── 1:N → NTCE_NOTICES (REG_USER_ID)
 ├── 1:N → EXAM_EXAMS (REG_USER_ID)

EDU_COURSES
 ├── 1:N → APLY_COURSE_APPLYS
 └── 1:N → EXAM_EXAMS

APLY_COURSE_APPLYS
 └── 1:1 → PRGS_COURSE_PROGRESS
