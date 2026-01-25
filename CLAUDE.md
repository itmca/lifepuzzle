# CLAUDE.md

LifePuzzle Monorepo - 소중한 사람들과의 추억을 기록하는 서비스

## 공통 가이드라인

다음 문서를 반드시 참조하세요:

- **[Git Workflow](./.team/base/GIT_WORKFLOW.md)** - 브랜치 전략, 커밋 컨벤션 (Chris Beams)
- **[PR Rules](./.team/base/PR_RULES.md)** - PR 제목/본문 작성 규칙

## 프로젝트 구조

```
lifepuzzle/
├── .team/                    # 팀 공통 설정 (submodule)
├── apps/
│   ├── rn/                   # React Native 앱
│   │   ├── CLAUDE.md         # RN 특화 가이드
│   │   └── ...
│   └── backend/              # Spring Boot + Go 백엔드
│       ├── CLAUDE.md         # Backend 특화 가이드
│       └── ...
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

## 커밋 컨벤션 (Chris Beams)

### 7가지 규칙

1. 제목과 본문을 빈 줄로 분리
2. **제목은 50자 이내**
3. **첫 글자 대문자**
4. **마침표 금지**
5. **명령형 사용** (Add, Fix, Update)
6. 본문은 72자에서 줄바꿈
7. 본문에 "무엇을", "왜"를 설명

### 예시

```
Add photo gallery to story detail

- Implement grid layout for photos
- Add pinch-to-zoom gesture
- Support lazy loading for performance
```

## 자주 사용하는 명령어

### Claude Code Skills

```bash
/create-pr      # PR 자동 생성
/new-feature    # 새 기능 브랜치
/code-review    # 코드 리뷰
/jira-dev       # JIRA 티켓 기반 개발
```

### 개발 환경

```bash
# RN
cd apps/rn && npm install && npm start

# Backend
cd apps/backend
./tools/scripts/start-infra.sh    # 인프라
./tools/scripts/start-full.sh     # 전체
```

## 릴리즈

서비스별 독립 릴리즈:

- `lifepuzzle-rn-v1.x.x` - RN 앱 릴리즈
- `lifepuzzle-backend-v1.x.x` - Backend 릴리즈

각 서비스의 `CHANGELOG.md`에서 릴리즈 노트 확인.
