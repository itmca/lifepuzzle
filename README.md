# LifePuzzle

소중한 사람들과의 추억을 기록하는 서비스

## 프로젝트 구조

lifepuzzle/
├── apps/
│   ├── rn/           # React Native 앱
│   └── backend/      # Spring Boot + Go 백엔드
├── packages/         # 공유 패키지 (Future Use)
├── infra/            # 공통 인프라 (Docker, Helm)
└── .team/            # 팀 공통 설정 (submodule)

## 시작하기

### 레포지토리 클론

```bash
# submodule 포함 클론
git clone --recurse-submodules git@github.com:itmca/lifepuzzle.git

# 이미 클론한 경우
git submodule update --init
```

### RN 앱 개발

```bash
cd apps/rn
npm install
npm start
```

### Backend 개발

```bash
cd apps/backend
./tools/scripts/start-infra.sh    # 인프라 실행
./gradlew bootRun                  # API 서버 실행
```

## AI 코딩 에이전트

이 프로젝트는 Claude Code, Cursor, Codex 등 AI 코딩 에이전트와 함께 사용하도록 설계되었습니다.

- `CLAUDE.md` - Claude Code 가이드
- `AGENT.md` - 범용 AI 에이전트 가이드
- `.team/` - 팀 공통 설정 및 skills

### Skills (Claude Code)

```bash
/create-pr      # PR 자동 생성
/new-feature    # 새 기능 브랜치
/code-review    # 코드 리뷰
/jira-dev       # JIRA 티켓 기반 개발
```

## 릴리즈

서비스별 독립 릴리즈:

- `lifepuzzle-rn-v1.x.x` - RN 앱 릴리즈
- `lifepuzzle-backend-v1.x.x` - Backend 릴리즈

## 문서

- [RN 개발 가이드](./apps/rn/CLAUDE.md)
- [Backend 개발 가이드](./apps/backend/CLAUDE.md)
- [팀 공통 Git 워크플로우](./.team/base/GIT_WORKFLOW.md)
