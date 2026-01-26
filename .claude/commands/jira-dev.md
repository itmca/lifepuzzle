# JIRA 티켓 기반 개발

JIRA 티켓을 기반으로 분석 → 브랜치 생성 → 구현까지 수행합니다.
커밋과 PR은 사용자 확인 후 별도로 진행합니다.

## 입력

- 티켓 ID: $ARGUMENTS (예: LP-123)

## 워크플로우

### 1. main 브랜치 최신화
```bash
git checkout main && git pull origin main
```

### 2. JIRA 티켓 가져오기

프로젝트 루트의 `.env` 파일을 찾아서 로드한 후 JIRA API를 호출합니다.

```bash
# 프로젝트 루트 찾기 (git root)
PROJECT_ROOT=$(git rev-parse --show-toplevel)
source "$PROJECT_ROOT/.env"

curl -s --user "${JIRA_EMAIL}:${JIRA_API_TOKEN}" \
  --header "Content-Type: application/json" \
  "${JIRA_BASE_URL}/rest/api/3/issue/$ARGUMENTS?expand=renderedFields" | jq .
```

티켓에서 추출할 정보:
- 제목 (summary): 작업 목표
- 설명 (description): 상세 요구사항
- 티켓 타입: Story, Bug, Task

### 3. 브랜치 생성
- Story/Task: `feat/$ARGUMENTS-<description>`
- Bug: `fix/$ARGUMENTS-<description>`

```bash
git checkout -b <branch-name>
```

### 4. 구현

티켓 요구사항에 따라 코드를 구현합니다.

### 5. 완료 안내

구현이 완료되면 사용자에게 다음 단계를 안내합니다:

```
구현이 완료되었습니다. 다음 단계:

1. 변경사항 확인: git diff
2. 커밋: /commit
3. PR 생성: /create-pr
```

## 중요

- 요구사항이 불명확하면 질문
- 대규모 변경은 계획을 먼저 확인
- 구현 완료 후 자동 커밋하지 않음
