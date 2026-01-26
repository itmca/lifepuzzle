# CLAUDE.md

LifePuzzle Monorepo - 소중한 사람들과의 추억을 기록하는 서비스

## 프로젝트 구조

```
lifepuzzle/
├── docs/                     # 공통 문서
├── infra/                    # 인프라 (Docker, Helm)
├── apps/
│   ├── rn/                   # React Native 앱
│   │   └── CLAUDE.md         # RN 특화 가이드
│   └── backend/              # Spring Boot + Go 백엔드
│       └── CLAUDE.md         # Backend 특화 가이드
└── packages/                 # 공유 패키지 (향후)
```

## 서비스별 가이드

| 서비스 | 경로 | 기술 스택 |
|--------|------|-----------|
| **RN App** | `apps/rn/` | React Native + TypeScript |
| **Backend API** | `apps/backend/` | Spring Boot + Go |

작업할 서비스의 `CLAUDE.md`를 참조하세요:
- [RN 가이드](./apps/rn/CLAUDE.md)
- [Backend 가이드](./apps/backend/CLAUDE.md)

## 커밋 & PR 규칙 (Chris Beams)

### 규칙
1. **명령형 사용**: Add, Fix, Update, Remove
2. **첫 글자 대문자**
3. **50자 이내**
4. **마침표 금지**

### 예시
```
Good: Add user authentication
Bad:  Added auth.
Bad:  add user authentication
Bad:  Add user authentication.
```

### 브랜치
```
feat/LP-123-description
fix/LP-456-bug-name
```

## Skills

```bash
/jira-dev       # JIRA 티켓 기반 개발 사이클
/new-feature    # 새 기능 브랜치 생성
/create-pr      # PR 자동 생성
/code-review    # 코드 리뷰
/test-gen       # 테스트 코드 생성
/commit         # 커밋 메시지 생성
/commit-on-main # main에 직접 커밋
/debug          # 에러 분석 및 해결
```

## 개발 환경

```bash
# RN
cd apps/rn && npm install && npm start

# Backend
cd apps/backend
./tools/scripts/start-infra.sh    # 인프라
./tools/scripts/start-full.sh     # 전체
```

## 릴리즈

- `lifepuzzle-rn-v1.x.x` - RN 앱 릴리즈
- `lifepuzzle-backend-v1.x.x` - Backend 릴리즈
