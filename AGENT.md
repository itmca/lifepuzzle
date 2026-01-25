# AGENT.md

AI 코딩 에이전트를 위한 LifePuzzle Monorepo 가이드

## 시작 전 필수

team-config 최신화:
```bash
cd .team && git pull origin main && cd ..
```

## 핵심 규칙 (Chris Beams)

### 커밋 메시지

- **명령형 사용**: Add, Fix, Update, Remove
- **50자 이내**
- **첫 글자 대문자**
- **마침표 금지**

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

### PR 제목

커밋 메시지와 동일한 규칙 적용.

## 구조

```
apps/
├── rn/           # React Native (CLAUDE.md 참조)
└── backend/      # Spring Boot + Go (CLAUDE.md 참조)
```

## 서비스별 작업

| 서비스 | 가이드 | 명령어 |
|--------|--------|--------|
| RN | `apps/rn/CLAUDE.md` | `npm start` |
| Backend | `apps/backend/CLAUDE.md` | `./gradlew bootRun` |

## 상세 가이드

- `.team/base/GIT_WORKFLOW.md` - Git 상세 가이드
- `.team/base/PR_RULES.md` - PR 규칙 상세
