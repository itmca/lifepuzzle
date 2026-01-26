현재 브랜치의 변경사항을 분석하고 PR을 생성해주세요.

## PR 제목 규칙 (Chris Beams)

1. **명령형 사용**: Add, Fix, Update, Remove, Refactor
2. **첫 글자 대문자**
3. **50자 이내**
4. **마침표 금지**

### 좋은 예시
- Add user authentication feature
- Fix memory leak in image processor

### 나쁜 예시
- ❌ Added user authentication
- ❌ fix bug

## PR 본문

프로젝트의 PR 템플릿이 있으면 따르고, 없으면:
- **작업 배경**: 왜 이 작업이 필요한지
- **작업 내용**: 무엇을 어떻게 변경했는지

## 실행

1. 변경사항 분석 (git diff, git log)
2. PR 제목 작성 (규칙 준수)
3. PR 본문 작성
4. `gh pr create` 실행
