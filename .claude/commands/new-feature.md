새 기능 개발을 위한 브랜치를 생성합니다.

## 사용법

```
/new-feature <설명>
```

## 실행

### 1. main 최신화
```bash
git checkout main
git pull origin main
```

### 2. 브랜치 생성
- 형식: `feat/<ticket>-<subject>` 또는 `feat/<subject>`
```bash
git checkout -b feat/LP-123-feature-description
```

### 3. 작업 안내
- 관련 파일 위치
- 참고할 기존 코드

## 브랜치 타입

| 타입 | 용도 | 예시 |
|------|------|------|
| feat | 새 기능 | feat/LP-123-auth |
| fix | 버그 수정 | fix/LP-456-error |
| refactor | 리팩토링 | refactor/cleanup |
| docs | 문서 | docs/api-docs |
| test | 테스트 | test/unit-tests |

## 주의

- main에서 분기
- kebab-case
- 50자 이내
