지정된 코드에 대한 테스트 코드를 생성합니다.

## 사용법

```
/test-gen <파일경로>
```

## 프로세스

### 1. 코드 분석
- 함수/메서드 시그니처
- 입출력 타입
- 부수 효과

### 2. 테스트 케이스
- 정상 케이스 (happy path)
- 경계값 (boundary)
- 예외 (error)
- 엣지 케이스

## 프레임워크

### React Native (Jest)
```typescript
describe('함수명', () => {
  it('should 동작', () => {
    // given, when, then
  });
});
```

### Spring Boot (JUnit 5)
```java
@Test
@DisplayName("동작 설명")
void testMethod() {
    // given, when, then
}
```

### Go
```go
func TestFunction(t *testing.T) {
    // given, when, then
}
```

## 원칙

- AAA 패턴: Arrange, Act, Assert
- 한 테스트 = 한 검증
- 독립적인 테스트
