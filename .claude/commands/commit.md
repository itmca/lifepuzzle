스테이징된 변경사항을 분석하고 커밋 메시지를 생성합니다.

## 실행

### 1. 변경사항 분석
```bash
git status
git diff --staged
```

### 2. 커밋 메시지 (Chris Beams)

#### 규칙
- **명령형**: Add, Fix, Update, Remove
- **첫 글자 대문자**
- **50자 이내**
- **마침표 금지**

#### 유형

| 유형 | 설명 | 예시 |
|------|------|------|
| Add | 새 기능 | Add user profile page |
| Fix | 버그 수정 | Fix null pointer in login |
| Update | 기존 개선 | Update validation logic |
| Remove | 삭제 | Remove deprecated API |
| Refactor | 리팩토링 | Refactor auth module |

### 3. 커밋

```bash
git commit -m "메시지"
```

## 주의

- 하나의 커밋 = 하나의 논리적 변경
- 관련 없는 변경은 분리 커밋 권장
