변경사항을 main 브랜치에 직접 커밋하고 push합니다.
PR 없이 빠르게 반영해야 하는 간단한 수정에 사용합니다.

## 실행

### 1. main 브랜치로 이동 및 최신화
```bash
git checkout main
git pull origin main
```

### 2. 변경사항 확인
```bash
git status
git diff
```

### 3. 커밋 메시지 (Chris Beams)

#### 규칙
- **명령형**: Add, Fix, Update, Remove
- **첫 글자 대문자**
- **50자 이내**
- **마침표 금지**

### 4. 스테이징 및 커밋
```bash
git add <files>
git commit -m "메시지"
```

### 5. Push
```bash
git push origin main
```

push 실패 시:
```bash
git pull --rebase origin main
git push origin main
```

## 사용 시나리오

- 설정 파일 수정
- 문서 업데이트
- 간단한 버그 수정
- 빌드/CI 설정 변경

## 주의

- main에 직접 커밋하므로 신중하게 사용
- 큰 기능 변경은 브랜치 + PR 권장
- 커밋 전 변경사항 꼼꼼히 확인