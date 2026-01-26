# JIRA 티켓 기반 개발

JIRA 티켓을 기반으로 개발 사이클 수행: 분석 → 계획 → 구현 → 테스트 → PR

## 입력

- 티켓 ID: $ARGUMENTS (예: LP-123)

## 환경 변수 (필수)

- JIRA_BASE_URL: JIRA URL (예: https://your-domain.atlassian.net)
- JIRA_EMAIL: 계정 이메일
- JIRA_API_TOKEN: API 토큰

## 워크플로우

### 1. main 브랜치 최신화
```bash
git checkout main && git pull origin main
```

### 2. JIRA 티켓 가져오기
```bash
curl -s --user "${JIRA_EMAIL}:${JIRA_API_TOKEN}" \
  --header "Content-Type: application/json" \
  "${JIRA_BASE_URL}/rest/api/3/issue/$ARGUMENTS?expand=renderedFields" | jq .
```

### 3. 브랜치 생성
- Story/Task: `feat/$ARGUMENTS-<description>`
- Bug: `fix/$ARGUMENTS-<description>`

### 4. 구현 및 테스트

### 5. /code-review 실행

### 6. /create-pr 실행

## 중요

- 문제 발생 시 사용자에게 알림
- 요구사항 불명확하면 질문
- 대규모 변경은 계획 먼저 확인
