스테이징된 변경사항을 분석하고 커밋 후 push까지 수행합니다.

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

### 4. Push (충돌 시 rebase)

```bash
git push origin <current-branch>
```

push가 실패하면 (remote에 새 커밋이 있으면):

```bash
git pull --rebase origin <current-branch>
# 충돌 해결 후
git push origin <current-branch>
```

충돌 발생 시:
1. 충돌 파일 확인
2. 충돌 해결
3. `git add <resolved-files>`
4. `git rebase --continue`
5. 다시 push

## 주의

- 하나의 커밋 = 하나의 논리적 변경
- 관련 없는 변경은 분리 커밋 권장
- push 전 현재 브랜치 확인
